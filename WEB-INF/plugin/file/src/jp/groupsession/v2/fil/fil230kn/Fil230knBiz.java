package jp.groupsession.v2.fil.fil230kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.FilTreeBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileCallConfDao;
import jp.groupsession.v2.fil.dao.FileCallDataDao;
import jp.groupsession.v2.fil.dao.FileDAccessConfDao;
import jp.groupsession.v2.fil.dao.FileDirectoryBinDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.fil.dao.FileFileRekiDao;
import jp.groupsession.v2.fil.dao.FileFileTextDao;
import jp.groupsession.v2.fil.dao.FileShortcutConfDao;
import jp.groupsession.v2.fil.model.FilChildTreeModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryBinModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileFileBinModel;
import jp.groupsession.v2.fil.model.FileFileRekiModel;

/**
 * <br>[機  能] 管理者設定 ファイル一括削除確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil230knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil230knBiz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Fil230knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil230knParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Fil230knParamModel paramMdl) throws SQLException {

        log__.debug("fil230knBiz Start");

        //削除するフォルダパスを設定する。
        __setDeleteDir(paramMdl);

        //表示用の操作コメントを設定
        if (!StringUtil.isNullZeroString(paramMdl.getFil230Comment())) {
            paramMdl.setFil230knDspComment(
                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getFil230Comment())
            );
        }
    }

    /**
     * <br>[機  能] 削除するフォルダパスを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil230knParamModel
     * @throws SQLException SQL実行例外
     */
    private void __setDeleteDir(Fil230knParamModel paramMdl) throws SQLException {

        int dirPath = NullDefault.getInt(paramMdl.getFil230DeleteDirSid(), 0);

        FilCommonBiz filCmnBiz = new FilCommonBiz(reqMdl__, con__);
        String delPath = filCmnBiz.getDirctoryPath(dirPath);
        paramMdl.setFil230DeleteDir(delPath);

    }


    /**
     * <br>[機  能] 電帳法対応キャビネット内のファイルを削除する
     * <br>[解  説]
     * <br>[備  考] 電帳法対応かつ更新から10年経過していない場合は論理削除する
     * @param paramMdl Fil230knParamModel
     * @param appRootPath アプリケーションルートパス
     * @throws Exception 実行例外
     */
    public void deleteData(Fil230knParamModel paramMdl, String appRootPath) throws Exception {

        int delDirSid = NullDefault.getInt(paramMdl.getFil230DeleteDirSid(), -1);
        int deleteOpt = NullDefault.getInt(paramMdl.getFil230DeleteOpt(), -1);

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);

        //選択ディレクトリの情報取得
        FileDirectoryModel delDir = dirDao.getNewDirectory(delDirSid);
        if (delDir == null) {
            return;
        }

        //電帳法キャビネット内のフォルダを削除しようとしているか判定
        FileCabinetDao cabDao = new FileCabinetDao(con__);
        FileCabinetModel cabMdl = cabDao.select(
                Integer.parseInt(paramMdl.getFil230SltCabinetSid()));
        int errlKbn = cabMdl.getFcbErrl();

        //子階層のデータ取得
        FilTreeBiz treeBiz = new FilTreeBiz(con__);
        FilChildTreeModel childMdl = treeBiz.getChildOfTarget(delDir);

        ArrayList<FileDirectoryModel> listOfDir = childMdl.getListOfDir();
        ArrayList<FileDirectoryModel> listOfFile = childMdl.getListOfFile();

        //旧バージョンのデータを除外
        listOfDir = __removeOldVersionData(listOfDir);
        listOfFile = __removeOldVersionData(listOfFile);

        UDate date = new UDate();
        date.addYear(-10);

        int sessionUserSid = reqMdl__.getSmodel().getUsrsid();
        ArrayList<FileDirectoryModel> buturiDelList = listOfFile;
        ArrayList<FileDirectoryModel> ronriDelList = new ArrayList<FileDirectoryModel>();

        //ファイル情報削除
        if (!listOfFile.isEmpty()) {


            //論理削除対象と物理削除対象の判定
            if (errlKbn == GSConstFile.ERRL_KBN_ON) {
                buturiDelList = new ArrayList<FileDirectoryModel>();
                for (int index = 0; index < listOfFile.size(); index++) {
                    FileDirectoryModel fdrMdl = listOfFile.get(index);
                    if (date.compare(fdrMdl.getFdrEdate(), date) == UDate.LARGE) {
                        //最終更新が10年前の場合は物理削除
                        buturiDelList.add(fdrMdl);
                    } else {
                        ronriDelList.add(fdrMdl);
                    }
                }
            }

            //1,000件ずつ物理削除する
            for (int count = 0; count < buturiDelList.size(); count += 1000) {
                ArrayList<FileDirectoryModel> delList = new ArrayList<FileDirectoryModel>();
                for (int index = count;
                        index < count + 1000 && index < buturiDelList.size();
                        index++) {
                    delList.add(buturiDelList.get(index));
                }
                if (delList == null || delList.size() <= 0) {
                    continue;
                }

                __deleteFileData(delList, appRootPath, false, sessionUserSid);
            }

            //1,000件ずつ論理削除する
            for (int count = 0; count < ronriDelList.size(); count += 1000) {
                ArrayList<FileDirectoryModel> delList = new ArrayList<FileDirectoryModel>();
                for (int index = count;
                        index < count + 1000 && index < ronriDelList.size();
                        index++) {
                    delList.add(ronriDelList.get(index));
                }

                //削除対象から論理削除済みのものを除外
                delList.removeIf(mdl -> mdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE);
                if (delList == null || delList.size() <= 0) {
                    continue;
                }

                __deleteFileData(delList, appRootPath, true, sessionUserSid);
                __insertRirekiData(delList, paramMdl);
                __insertFileBinData(delList);
            }
        }

        //フォルダの削除
        if (!listOfDir.isEmpty() && deleteOpt == GSConstFile.DELETE_OPTION_FOLDER_FILE) {
            //電帳法キャビネット以外のフォルダは物理削除
            if (errlKbn == GSConstFile.ERRL_KBN_OFF) {
                //ディレクトリアクセス制限削除
                FileDAccessConfDao daConfDao = new FileDAccessConfDao(con__);
                if (delDir.getFdrParentSid() == GSConstFile.DIRECTORY_ROOT) {
                    //ルートディレクトリ
                    daConfDao.deleteSubDirectoriesFiles(delDirSid, true);
                } else {
                    daConfDao.deleteSubDirectoriesFiles(delDirSid, false);
                }

                //ディレクトリ情報の削除
                //1,000件ずつ削除する
                for (int count = 0; count < listOfDir.size(); count += 1000) {

                    ArrayList<FileDirectoryModel> delList = new ArrayList<FileDirectoryModel>();
                    for (int index = count;
                            index < count + 1000 && index < listOfDir.size();
                            index++) {
                        delList.add(listOfDir.get(index));
                    }
                    __deleteDirData(delList, appRootPath, false, sessionUserSid);
                }
                return;
            }
            //論理削除フォルダセット
            Set<Integer> ronriFolderSids = new HashSet<>();
            Set<Integer> deleteFolderSids = new HashSet<>();

            //論理削除ファイルを持つフォルダは論理削除
            ronriFolderSids.addAll(
                ronriDelList.stream()
                    .map(file -> file.getFdrParentSid())
                    .collect(Collectors.toSet())
                );


            //ディレクトリを階層毎にマッピング
            //階層の下の順にループが回せるようにする
            TreeMap<Integer, List<FileDirectoryModel>> levelMap =
                    new TreeMap<>(Collections.reverseOrder());
            for (FileDirectoryModel folder : listOfDir) {
                if (levelMap.containsKey(folder.getFdrLevel()) == false) {
                    levelMap.put(folder.getFdrLevel(), new ArrayList<>());
                }
                levelMap.get(folder.getFdrLevel()).add(folder);
            }
            int topLevel = levelMap.keySet()
                                .stream()
                                .min(Comparator.naturalOrder())
                                .orElse(0);
            //子階層が全て物理削除されたフォルダは物理削除する
            for (Entry<Integer, List<FileDirectoryModel>> entry : levelMap.entrySet()) {
                for (FileDirectoryModel folder : entry.getValue()) {
                    if (ronriFolderSids.contains(folder.getFdrSid())) {
                        //最上位のフォルダについては親ディレクトリを
                        //論理削除除外リストに追加する必要がない
                        if (entry.getKey() != topLevel) {
                            ronriFolderSids.add(folder.getFdrParentSid());
                        }
                        continue;
                    }
                    deleteFolderSids.add(folder.getFdrSid());
                }

            }
            List<Integer> ronriFolderSidList = new ArrayList<>(ronriFolderSids);
            List<Integer> deleteFolderSidList = new ArrayList<>(deleteFolderSids);


            //ディレクトリ情報の物理削除
            //1,000件ずつ削除する
            for (int count = 0; count < deleteFolderSidList.size(); count += 1000) {

                ArrayList<FileDirectoryModel> delList = new ArrayList<FileDirectoryModel>();
                for (int index = count;
                        index < count + 1000 && index < deleteFolderSidList.size();
                        index++) {
                    FileDirectoryModel mdl = new FileDirectoryModel();
                    mdl.setFdrSid(deleteFolderSidList.get(index));
                    delList.add(mdl);
                }
                __deleteDirData(delList, appRootPath, false, sessionUserSid);
            }

            //フォルダ情報の論理削除
            //1,000件ずつ削除する
            for (int count = 0; count < ronriFolderSidList.size(); count += 1000) {
                ArrayList<FileDirectoryModel> delList = new ArrayList<FileDirectoryModel>();
                for (int index = count;
                        index < count + 1000 && index < ronriFolderSidList.size();
                        index++) {
                    FileDirectoryModel mdl = new FileDirectoryModel();
                    mdl.setFdrSid(ronriFolderSidList.get(index));
                    delList.add(mdl);
                }
                __deleteDirData(delList, appRootPath, true, sessionUserSid);
            }


        }


    }

    /**
     * <br>[機  能] 論理削除したファイルの更新履歴の追加を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param delFileList 論理削除したファイル一覧
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    public void __insertRirekiData(ArrayList<FileDirectoryModel> delFileList, Fil230knParamModel paramMdl)
    throws SQLException {

        String[] fdrSidAry = delFileList.stream()
                .map(mdl -> String.valueOf(mdl.getFdrSid()))
                .toArray(String[] :: new);

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        List<FileDirectoryModel> dirList = dirDao.getNewDirectoryList(fdrSidAry);

        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        UDate now = new UDate();
        List<FileFileRekiModel> rekiList = new ArrayList<FileFileRekiModel>();
        for (FileDirectoryModel dirMdl : dirList) {
            FileFileRekiModel rekiMdl = new FileFileRekiModel();
            rekiMdl.setFdrSid(dirMdl.getFdrSid());
            rekiMdl.setFdrVersion(dirMdl.getFdrVersion());
            rekiMdl.setFfrFname(dirMdl.getFdrName());
            rekiMdl.setFfrKbn(GSConstFile.REKI_KBN_DELETE);
            rekiMdl.setFfrEuid(sessionUsrSid);
            rekiMdl.setFfrEdate(now);
            rekiMdl.setFfrParentSid(dirMdl.getFdrParentSid());
            rekiMdl.setFfrUpCmt(paramMdl.getFil230Comment());
            rekiMdl.setFdrTradeDate(dirMdl.getFdrTradeDate());
            rekiMdl.setFdrTradeTarget(dirMdl.getFdrTradeTarget());
            rekiMdl.setFdrTradeMoneykbn(dirMdl.getFdrTradeMoneykbn());
            rekiMdl.setFdrTradeMoney(dirMdl.getFdrTradeMoney());
            rekiMdl.setEmtSid(dirMdl.getEmtSid());

            rekiList.add(rekiMdl);
        }

        FileFileRekiDao rekiDao = new FileFileRekiDao(con__);
        rekiDao.insertMinData(rekiList);
    }

    /**
     * <br>[機  能] 論理削除したファイルのファイル情報を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param delFileList 論理削除したファイル一覧
     * @throws SQLException SQL実行例外
     */
    public void __insertFileBinData(ArrayList<FileDirectoryModel> delFileList) throws SQLException {

        FileFileBinDao fileBinDao = new FileFileBinDao(con__);
        fileBinDao.insertDelFileData(
                delFileList.stream()
                .map(mdl -> mdl.getFdrSid())
                .collect(Collectors.toList()));
    }

    /**
     * <br>[機  能] ファイルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param listOfDir 削除ディレクトリ情報リスト
     * @param appRootPath アプリケーションルートパス
     * @param ronriDelFlg 論理削除フラグ true:論理削除を行う, false:物理削除を行う
     * @param sessionUserSid セッションユーザSID
     * @throws Exception 実行例外
     */
    private void __deleteFileData(ArrayList<FileDirectoryModel> listOfDir,
            String appRootPath, boolean ronriDelFlg, int sessionUserSid)
    throws Exception {

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        if (ronriDelFlg) {
            //ファイルの論理削除
            List<Integer> delSid = listOfDir.stream()
                    .map(mdl -> mdl.getFdrSid())
                    .collect(Collectors.toList());
            dirDao.deleteDirectory(delSid, sessionUserSid);
            return;
        }

        //ディレクトリアクセス制御削除
        FileDAccessConfDao daConfDao = new FileDAccessConfDao(con__);
        for (FileDirectoryModel dirMdl : listOfDir) {
            daConfDao.delete(dirMdl.getFdrSid());
        }
        //ディレクトリ情報の削除
        dirDao.deleteDir(listOfDir);

        //ショートカット設定の削除
        FileShortcutConfDao scutDao = new FileShortcutConfDao(con__);
        scutDao.deleteDir(listOfDir);

        //更新通知設定の削除
        FileCallConfDao callConfDao = new FileCallConfDao(con__);
        callConfDao.deleteDir(listOfDir);

        //更新確認情報の削除
        FileCallDataDao callDataDao = new FileCallDataDao(con__);
        callDataDao.deleteDir(listOfDir);

        //ファイル履歴情報の削除
        FileFileRekiDao fileRekiDao = new FileFileRekiDao(con__);
        fileRekiDao.deleteDir(listOfDir);

        //添付ファイル情報の削除
        __deleteBinDataFile(listOfDir, appRootPath);

        //ファイル情報の削除
        FileFileBinDao fileBinDao = new FileFileBinDao(con__);
        fileBinDao.deleteDir(listOfDir);

        //ファイル内容情報の削除
        FileFileTextDao fileTextDao = new FileFileTextDao(con__);
        fileTextDao.deleteDir(listOfDir);

    }

    /**
     * <br>[機  能] フォルダを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param listOfDir 削除ディレクトリ情報リスト
     * @param appRootPath アプリケーションルートパス
     * @param logicDelFlg 論理削除フラグ true:論理削除，false:物理削除
     * @param sessionUserSid セッションユーザSID
     * @throws Exception 実行例外
     */
    private void __deleteDirData(
            ArrayList<FileDirectoryModel> listOfDir, String appRootPath, boolean logicDelFlg,
            int sessionUserSid)
    throws Exception {
        if (listOfDir == null || listOfDir.size() <= 0) {
            return;
        }
        //ディレクトリ情報の論理削除
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        if (logicDelFlg) {
            List<Integer> delSid = listOfDir.stream()
                    .map(mdl -> mdl.getFdrSid())
                    .collect(Collectors.toList());
            dirDao.deleteDirectory(delSid, sessionUserSid);

            return;
        }

        //ディレクトリ情報の削除
        dirDao.deleteDir(listOfDir);
        //添付情報の削除
        FileDirectoryBinDao dirBinDao = new FileDirectoryBinDao(con__);
        dirBinDao.deleteDir(listOfDir);

        //添付ファイル情報の削除
        __deleteBinDataDir(listOfDir, appRootPath);
    }

    /**
     * <br>[機  能] 添付ファイル情報をを削除する。
     * <br>[解  説] 添付ファイルの物理削除を行います。
     * <br>[備  考]
     * @param listOfDir 削除ディレクトリ情報リスト
     * @param appRootPath アプリケーションルートパス
     * @throws Exception 実行例外
     */
    private void __deleteBinDataFile(ArrayList<FileDirectoryModel> listOfDir, String appRootPath)
    throws Exception {

        FileFileBinDao fileBinDao = new FileFileBinDao(con__);

        //バイナリSID情報を取得する。
        List<FileFileBinModel> dirMdlList = fileBinDao.getFileListAllVersion(listOfDir);

        CmnBinfDao binDao = new CmnBinfDao(con__);
        CommonBiz cmnBiz = new CommonBiz();

        String[] binList = new String[dirMdlList.size()];
        int i = 0;
        for (FileFileBinModel model : dirMdlList) {
            binList[i] = String.valueOf(model.getBinSid());
            i++;
        }

        //バイナリ情報を取得する。
        List<CmnBinfModel> cmnBinList = binDao.select(binList);

        for (CmnBinfModel binMdl : cmnBinList) {

            //ファイルシステムより添付ファイルを削除する。
            cmnBiz.deleteFile(binMdl, appRootPath);
        }

        cmnBiz.deleteBinInf(con__, binList);
    }

    /**
     * <br>[機  能] 添付ファイル情報をを削除する。
     * <br>[解  説] 添付ファイルの物理削除を行います。
     * <br>[備  考]
     * @param listOfDir 削除ディレクトリ情報リスト
     * @param appRootPath アプリケーションルートパス
     * @throws Exception 実行例外
     */
    private void __deleteBinDataDir(ArrayList<FileDirectoryModel> listOfDir, String appRootPath)
    throws Exception {

        FileDirectoryBinDao dirBinDao = new FileDirectoryBinDao(con__);

        //バイナリSID情報を取得する。
        List<FileDirectoryBinModel> dirMdlList = dirBinDao.getFileListAllVersion(listOfDir);

        CmnBinfDao binDao = new CmnBinfDao(con__);
        CommonBiz cmnBiz = new CommonBiz();

        String[] binList = new String[listOfDir.size()];
        int i = 0;
        for (FileDirectoryBinModel model : dirMdlList) {
            binList[i] = String.valueOf(model.getFdrSid());
            i++;
        }

        //バイナリ情報を取得する。
        List<CmnBinfModel> cmnBinList = binDao.select(binList);

        for (CmnBinfModel binMdl : cmnBinList) {

            //ファイルシステムより添付ファイルを削除する。
            cmnBiz.deleteFile(binMdl, appRootPath);

        }

        cmnBiz.deleteBinInf(con__, binList);
    }

    /**
     * <br>[機  能] 重複したディレクトリ情報が存在する場合、「最新バージョンのみ」を一覧に設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param dirList ディレクトリ情報一覧
     * @return ディレクトリ情報一覧(旧バージョンデータ除外)
     */
    private ArrayList<FileDirectoryModel>
        __removeOldVersionData(ArrayList<FileDirectoryModel> dirList) {

        if (dirList == null || dirList.isEmpty()) {
            return dirList;
        }

        //最新バージョンのファイル情報のみを使用する
        Map<Integer, FileDirectoryModel> fileDataMap
            = new HashMap<Integer, FileDirectoryModel>();
        for (FileDirectoryModel fileData : dirList) {
            int fdrSid = fileData.getFdrSid();
            if (!fileDataMap.containsKey(fdrSid)
            || fileDataMap.get(fdrSid).getFdrVersion() < fileData.getFdrVersion()) {
                fileDataMap.put(fdrSid, fileData);
            }
        }

        ArrayList<FileDirectoryModel> result =
                new ArrayList<FileDirectoryModel>();
        result.addAll(
                fileDataMap.values().stream().collect(Collectors.toList()));

        return result;
    }
}