package jp.groupsession.v2.fil.fil090;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.FilTreeBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDAccessConfDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.fil010.Fil010Biz;
import jp.groupsession.v2.fil.fil010.FileCabinetDspModel;
import jp.groupsession.v2.fil.model.FilChildTreeModel;
import jp.groupsession.v2.fil.model.FileAccessConfModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] フォルダ・ファイル移動画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil090Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil090Biz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** 移動元ディレクトリ オペレーションログ用ストック*/
    List<FileDirectoryModel> oldFileList__ = null;

    /** 選択複数移動区分 1ディレクトリ移動 */
    public static final int MOVE_PLURAL_NO = 0;
    /** 選択複数移動区分 複数移動 */
    public static final int MOVE_PLURAL_YES = 1;

    /** 画面モード 複数移動 */
    public static final int MODE_PLURAL = 2;



    /**
     * <p>Set Connection
     * @param reqMdl RequestModel
     * @param con Connection
     */
    public Fil090Biz(RequestModel reqMdl, Connection con) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil090ParamModel
     * @param buMdl セッションユーザモデル
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setInitData(Fil090ParamModel paramMdl,
                            BaseUserModel buMdl
                            )
                      throws SQLException, IOToolsException, IOException, TempFileException {
        log__.debug("Fil090Biz Start");

        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl__, con__);

        //キャビネットコンボを取得
        List<FileCabinetModel> cabList = new ArrayList<FileCabinetModel>();
        Fil010Biz fil010Biz = new Fil010Biz(reqMdl__);


        if (paramMdl.getFil010DspCabinetKbn().equals(
                String.valueOf(GSConstFile.CABINET_KBN_ERRL))) {
            //電帳法キャビネット一覧
            ArrayList<FileCabinetDspModel> cabinetLists = null;
            cabinetLists = fil010Biz.getAccessCabinetList(
                    buMdl,
                    con__,
                    false,
                    GSConstFile.CABINET_KBN_ERRL);
            if (cabinetLists != null) {
                for (FileCabinetDspModel cabMdl : cabinetLists) {
                    FileCabinetModel bean = new FileCabinetModel();
                    bean.setFcbSid(cabMdl.getFcbSid());
                    bean.setFcbName(cabMdl.getFcbName());
                    bean.setFcbPersonalFlg(GSConstFile.CABINET_KBN_PUBLIC);
                    cabList.add(bean);
                }
            }
        } else {
            //マイキャビネット
            if (cmnBiz.isCanMyCabinet(buMdl.getUsrsid())) {
                FileCabinetDao fcbDao = new FileCabinetDao(con__);
                FileCabinetDspModel myCabMdl = fcbDao.getMyCabinet(buMdl.getUsrsid());
                if (myCabMdl != null) {
                    FileCabinetModel bean = new FileCabinetModel();
                    bean.setFcbSid(myCabMdl.getFcbSid());
                    bean.setFcbName(buMdl.getUsiseimei());
                    bean.setFcbPersonalFlg(GSConstFile.CABINET_KBN_PRIVATE);
                    cabList.add(bean);
                }
            }

            //共有キャビネット一覧
            ArrayList<FileCabinetDspModel> cabinetLists = null;
            cabinetLists = fil010Biz.getAccessCabinetList(
                    buMdl,
                    con__,
                    false,
                    GSConstFile.CABINET_KBN_PUBLIC);
            if (cabinetLists != null) {
                for (FileCabinetDspModel cabMdl : cabinetLists) {
                    FileCabinetModel bean = new FileCabinetModel();
                    bean.setFcbSid(cabMdl.getFcbSid());
                    bean.setFcbName(cabMdl.getFcbName());
                    bean.setFcbPersonalFlg(GSConstFile.CABINET_KBN_PUBLIC);
                    cabList.add(bean);
                }
            }
        }
        paramMdl.setFil090CabinetCombo(cabList);

        //選択キャビネット値を設定
        __setSelectCabinetSid(paramMdl);

        //移動元ディレクトリ情報を設定する。
        if (paramMdl.getFil090SelectPluralKbn() == MOVE_PLURAL_NO) {
            //単一移動モード
            __setDirData(paramMdl);
        } else {
            //一括移動モード
            __setDirDataForPlural(paramMdl);
        }

        //移動先ディレクトリ階層の表示を設定する。
        __setTreeData(paramMdl);

        List<LabelValueBean> lvList = new ArrayList<LabelValueBean>();

        //更新者設定
        paramMdl.setFil090EditId(NullDefault.getString(
                paramMdl.getFil090EditId(), String.valueOf(buMdl.getUsrsid())));

        lvList.add(new LabelValueBean(
                buMdl.getUsiseimei(), String.valueOf(buMdl.getUsrsid())));

        //ユーザの所属グループを取得
        ArrayList<GroupModel> gpList = null;
        UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con__);
        gpList = gpDao.selectGroupNmListOrderbyClass(buMdl.getUsrsid());
        if (gpList != null && !gpList.isEmpty()) {
            for (GroupModel gpMdl : gpList) {
                lvList.add(new LabelValueBean(
                        gpMdl.getGroupName(), "G" + String.valueOf(gpMdl.getGroupSid())));
            }
        }
        paramMdl.setFil090groupList(lvList);
    }

    /**
     * <br>[機  能] 移動元ディレクトリ情報を取得しを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil090ParamModel
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private void __setDirData(Fil090ParamModel paramMdl)
                         throws SQLException, IOToolsException, IOException, TempFileException {

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);

        int dirSid = NullDefault.getInt(paramMdl.getFil090DirSid(), -1);

        //ディレクトリ情報を取得する。
        List<FileDirectoryModel> dirList = dirDao.getNewDirectoryList(new String[] {
                String.valueOf(dirSid)
        }, false);
        if (dirList == null || dirList.size() < 1) {
            return;
        }
        FileDirectoryModel dirModel = dirList.get(0);
        if (dirModel == null) {
            return;
        }

        paramMdl.setFil090Mode(String.valueOf(dirModel.getFdrKbn()));

        paramMdl.setFil090Biko(StringUtilHtml.transToHTmlPlusAmparsant(dirModel.getFdrBiko()));
        int cabSid = filBiz.getCabinetSid(dirSid);
        paramMdl.setFil090VerKbn(String.valueOf(filBiz.getVerKbn(cabSid, dirSid)));

        List<LabelValueBean> fileList = new ArrayList<LabelValueBean>();

        //添付ファイルをテンポラリディレクトリに保存する。
        if (paramMdl.getFil090Mode().equals(String.valueOf(GSConstFile.DIRECTORY_FOLDER))) {
            paramMdl.setFil090DirName(dirModel.getFdrName());
        } else {
            fileList.add(new LabelValueBean(
                    dirModel.getFdrName(),
                    String.valueOf(dirModel.getBinSid())
                    ));
        }
        //添付ファイルラベルリストの設定
        paramMdl.setFil090FileLabelList(fileList);

        //移動先ディレクトリパスの設定
        paramMdl.setFil090SltDirPath(__getDirPath(paramMdl));

    }

    /**
     * <br>[機  能] 移動元ディレクトリ情報を取得しを設定する。
     * <br>[解  説] 一括移動モード
     * <br>[備  考]
     * @param paramMdl Fil090ParamModel
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private void __setDirDataForPlural(Fil090ParamModel paramMdl)
                             throws SQLException, IOToolsException, IOException, TempFileException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);

        //移動するディレクトリ
        String[] selectDir = paramMdl.getFil040SelectDel();

        paramMdl.setFil090Mode(String.valueOf(MODE_PLURAL));

        if (selectDir == null || selectDir.length < 1) {
            return;
        }

        //ディレクトリ情報一覧を取得する。(最新バージョン)
        List<FileDirectoryModel> dirList = dirDao.getNewDirectoryList(selectDir, false);
        if (dirList == null || dirList.size() < 1) {
            return;
        }

        List<LabelValueBean> fileList = new ArrayList<LabelValueBean>();
        List<String> folderNameList = new ArrayList<String>();
        for (FileDirectoryModel model : dirList) {

            if (model.getFdrKbn() == GSConstFile.DIRECTORY_FILE) {
                //ファイル
                fileList.add(
                        new LabelValueBean(model.getFdrName(),
                                String.valueOf(model.getBinSid())));
            } else {
                //フォルダ
                folderNameList.add(model.getFdrName());
            }
        }

        paramMdl.setFil090FolderNameList(folderNameList);

        //添付ファイルラベルリストの設定
        paramMdl.setFil090FileLabelList(fileList);

        //移動先ディレクトリパスの設定
        paramMdl.setFil090SltDirPath(__getDirPath(paramMdl));

    }

    /**
     * <br>[機  能] フォルダ選択のディレクトリツリーを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil090ParamModel
     * @throws SQLException SQL実行例外
     */
    private void __setTreeData(Fil090ParamModel paramMdl) throws SQLException {

        int dirSid = NullDefault.getInt(paramMdl.getFil010SelectDirSid(), -1);

        paramMdl.setTreeFormLv0(null);
        paramMdl.setTreeFormLv1(null);
        paramMdl.setTreeFormLv2(null);
        paramMdl.setTreeFormLv3(null);
        paramMdl.setTreeFormLv4(null);
        paramMdl.setTreeFormLv5(null);
        paramMdl.setTreeFormLv6(null);
        paramMdl.setTreeFormLv7(null);
        paramMdl.setTreeFormLv8(null);
        paramMdl.setTreeFormLv9(null);
        paramMdl.setTreeFormLv10(null);

        //キャビネットSIDを取得する。
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        int cabinetSid = paramMdl.getFil090SelectCabinetSid();
        if (cabinetSid == -1) {
            cabinetSid = filBiz.getCabinetSid(dirSid);
            paramMdl.setFil090SelectCabinetSid(cabinetSid);
        }
        //ルートディレクトリSIDを設定する。
        FileDirectoryDao dirdao = new FileDirectoryDao(con__);
        FileDirectoryModel rootModel = dirdao.getRootDirectory(cabinetSid);
        if (rootModel == null) {
            return;
        }

        FileCabinetDao cabDao = new FileCabinetDao(con__);
        FileCabinetModel cabMdl = cabDao.select(cabinetSid);
        if (cabMdl == null) {
            return;
        }

        List<Integer> fdrSidList = new ArrayList<Integer>();

        if (paramMdl.getFil090SelectPluralKbn() == MOVE_PLURAL_NO) {
            //単一移動モード
            int fdrSid = NullDefault.getInt(paramMdl.getFil090DirSid(), 0);
            fdrSidList.add(fdrSid);
        } else {
            //一括移動モード
            String[] selectDir = paramMdl.getFil040SelectDel();
            if (selectDir != null && selectDir.length > 0) {
                for (String selectDirSid : selectDir) {
                    fdrSidList.add(NullDefault.getInt(selectDirSid, 0));
                }
            }
        }

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        if (cabMdl.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
            String userName = filBiz.getUserName(sessionUsrSid);
            if (userName != null && userName.length() > 0) {
                cabMdl.setFcbName(userName);
            }

            ArrayList<FileDirectoryModel> dirList =
                    dirdao.getAuthDirectoryList(cabMdl, sessionUsrSid);

            // キャビネット内の閲覧可能なディレクトリ一覧を取得
            if (dirList != null && dirList.size() > 0) {
                // 最上位階層のディレクトリ名をキャビネット名へ変更
                for (FileDirectoryModel dirMdl : dirList) {
                    if (dirMdl.getFdrLevel() == GSConstFile.DIRECTORY_LEVEL_0) {
                        dirMdl.setFdrName(cabMdl.getFcbName());
                    }
                }
                paramMdl.setTreeFormLv0(dirdao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_0, dirList, GSConstFile.ACCESS_KBN_WRITE));
                if (paramMdl.getTreeFormLv0() == null || paramMdl.getTreeFormLv0().length <= 0) {
                    return;
                }
                paramMdl.setTreeFormLv1(dirdao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_1, dirList, GSConstFile.ACCESS_KBN_WRITE));
                if (paramMdl.getTreeFormLv1() == null || paramMdl.getTreeFormLv1().length <= 0) {
                    return;
                }
                paramMdl.setTreeFormLv2(dirdao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_2, dirList, GSConstFile.ACCESS_KBN_WRITE));
                if (paramMdl.getTreeFormLv2() == null || paramMdl.getTreeFormLv2().length <= 0) {
                    return;
                }
                paramMdl.setTreeFormLv3(dirdao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_3, dirList, GSConstFile.ACCESS_KBN_WRITE));
                if (paramMdl.getTreeFormLv3() == null || paramMdl.getTreeFormLv3().length <= 0) {
                    return;
                }
                paramMdl.setTreeFormLv4(dirdao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_4, dirList, GSConstFile.ACCESS_KBN_WRITE));
                if (paramMdl.getTreeFormLv4() == null || paramMdl.getTreeFormLv4().length <= 0) {
                    return;
                }
                paramMdl.setTreeFormLv5(dirdao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_5, dirList, GSConstFile.ACCESS_KBN_WRITE));
                if (paramMdl.getTreeFormLv5() == null || paramMdl.getTreeFormLv5().length <= 0) {
                    return;
                }
                paramMdl.setTreeFormLv6(dirdao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_6, dirList, GSConstFile.ACCESS_KBN_WRITE));
                if (paramMdl.getTreeFormLv6() == null || paramMdl.getTreeFormLv6().length <= 0) {
                    return;
                }
                paramMdl.setTreeFormLv7(dirdao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_7, dirList, GSConstFile.ACCESS_KBN_WRITE));
                if (paramMdl.getTreeFormLv7() == null || paramMdl.getTreeFormLv7().length <= 0) {
                    return;
                }
                paramMdl.setTreeFormLv8(dirdao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_8, dirList, GSConstFile.ACCESS_KBN_WRITE));
                if (paramMdl.getTreeFormLv8() == null || paramMdl.getTreeFormLv8().length <= 0) {
                    return;
                }
                paramMdl.setTreeFormLv9(dirdao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_9, dirList, GSConstFile.ACCESS_KBN_WRITE));
                if (paramMdl.getTreeFormLv9() == null || paramMdl.getTreeFormLv9().length <= 0) {
                    return;
                }
                paramMdl.setTreeFormLv10(dirdao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_10, dirList, GSConstFile.ACCESS_KBN_WRITE));
            }
        } else {
            //特権ユーザ判定
            boolean superUser = filBiz.isEditCabinetUser(cabinetSid);

            //ツリー情報を取得する
            FilTreeBiz treeBiz = new FilTreeBiz(con__);
            Map<Integer, String[]> treeMap = treeBiz.getFileTreeMapForMove(
                    reqMdl__,
                    cabinetSid,
                    fdrSidList,
                    superUser
                    );

            paramMdl.setTreeFormLv0(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_0));
            if (paramMdl.getTreeFormLv0() == null || paramMdl.getTreeFormLv0().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv1(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_1));
            if (paramMdl.getTreeFormLv1() == null || paramMdl.getTreeFormLv1().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv2(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_2));
            if (paramMdl.getTreeFormLv2() == null || paramMdl.getTreeFormLv2().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv3(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_3));
            if (paramMdl.getTreeFormLv3() == null || paramMdl.getTreeFormLv3().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv4(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_4));
            if (paramMdl.getTreeFormLv4() == null || paramMdl.getTreeFormLv4().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv5(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_5));
            if (paramMdl.getTreeFormLv5() == null || paramMdl.getTreeFormLv5().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv6(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_6));
            if (paramMdl.getTreeFormLv6() == null || paramMdl.getTreeFormLv6().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv7(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_7));
            if (paramMdl.getTreeFormLv7() == null || paramMdl.getTreeFormLv7().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv8(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_8));
            if (paramMdl.getTreeFormLv8() == null || paramMdl.getTreeFormLv8().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv9(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_9));
            if (paramMdl.getTreeFormLv9() == null || paramMdl.getTreeFormLv9().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv10(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_10));
        }
    }

    /**
     * <br>[機  能] キャビネットSIDを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil090ParamModel
     * @throws SQLException SQL例外
     */
    private void __setSelectCabinetSid(Fil090ParamModel paramMdl) throws SQLException {

        int selectSid = paramMdl.getFil090SelectCabinetSid();

        boolean flg = false;
        for (FileCabinetModel mdl : paramMdl.getFil090CabinetCombo()) {
            if (mdl.getFcbSid() == selectSid) {
                flg = true;
                break;
            }
        }
        if (!flg) {
            FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
            int dirSid = NullDefault.getInt(paramMdl.getFil010SelectDirSid(), -1);
            selectSid = filBiz.getCabinetSid(dirSid);
            paramMdl.setFil090SelectCabinetSid(selectSid);
        }
    }

    /**
     * <br>[機  能] 移動先ディレクトリパスを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil090ParamModel
     * @return dirPath 移動先ディレクトリパス
     * @throws SQLException SQL実行時例外
     */
    private String __getDirPath(Fil090ParamModel paramMdl) throws SQLException {

        int dirSid = NullDefault.getInt(paramMdl.getMoveToDir(), 0);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        String dirPath = filBiz.getDirctoryPath(dirSid);

        return dirPath;
    }

    /**
     * <br>[機  能] 指定したディレクトリ以下からの最大階層数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dirSid ディレクトリSID
     * @return maxLevel 指定したディレクトリからの最大階層数
     * @throws SQLException SQL実行時例外
     */
    public int getMaxLevel(int dirSid) throws SQLException {

        int maxLevel = 0;

        //自ディレクトリ情報を取得する。
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel dirModel = dirDao.getNewDirectory(dirSid);
        if (dirModel == null) {
            return maxLevel;
        }
        if (dirModel.getFdrKbn() == GSConstFile.DIRECTORY_FILE) {
            //ファイルの場合は0階層
            return 0;
        }

        //子階層のデータ取得
        FilTreeBiz treeBiz = new FilTreeBiz(con__);
        FilChildTreeModel childMdl = treeBiz.getChildOfTarget(dirModel);

        //フォルダ階層の更新
        ArrayList<FileDirectoryModel> listOfDir = childMdl.getListOfDir();

        //最大階層数の取得
        int level = __getMaxLevel(listOfDir);

        //指定ディレクトリからの階層数を取得する。
        maxLevel = level - dirModel.getFdrLevel() + 1;

        return maxLevel;
    }

    /**
     * <br>[機  能] ディレクトリリストから最大階層数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param listOfDir ディレクトリリスト
     * @return maxLevel 指定したディレクトリからの最大階層数
     * @throws SQLException SQL実行時例外
     */
    private int __getMaxLevel(ArrayList<FileDirectoryModel> listOfDir) throws SQLException {
        int maxLevel = 0;

        for (FileDirectoryModel mdl : listOfDir) {
            if (mdl.getFdrLevel() > maxLevel) {
                maxLevel = mdl.getFdrLevel();
            }
        }

        return maxLevel;
    }
    /**ディレクトリ構造 エラーコード エラーなし*/
    public static final int ECODE_NONE = 0;
    /**ディレクトリ構造 エラーコード 階層オーバー*/
    public static final int ECODE_OVER_LEVEL = 1;
    /**ディレクトリ構造 エラーコード 自分への移動*/
    public static final int ECODE_MOVETOSELF = 2;
    /**ディレクトリ構造 エラーコード 自分の子階層への移動*/
    public static final int ECODE_MOVETOCHILD = 3;
    /**
     * <br>[機  能] ディレクトリ移動後の構造をチェックする。
     * <br>[解  説] 以下はエラーとする。
     * <br>        移動後ディレクトリ11階層以上
     * <br>        移動先ディレクトリが自分
     * <br>        移動先ディレクトリの親ディレクトリ
     * <br>[備  考]
     * @param toDirMdl 移動先ディレクトリ情報
     * @param moveDirSid 移動ディレクトリSID
     * @return エラーコード
     * @throws SQLException SQL実行例外
     */
    public int validateTree(
            FileDirectoryModel toDirMdl, int moveDirSid) throws SQLException {
        int sleDirLevel = toDirMdl.getFdrLevel();

        //移動するディレクトリの下階層数を取得する。
        int dirLevel = getMaxLevel(moveDirSid);

        if (__isOverLevel(sleDirLevel, dirLevel)) {
            return ECODE_OVER_LEVEL;
        }
        int toDirSid = toDirMdl.getFdrSid();
        //移動先ディレクトリが自分ではないかを確認
        if (toDirSid == moveDirSid) {
            return ECODE_MOVETOSELF;
        }
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);

        //移動先ディレクトリが自分の子階層ではないかを確認
        FileDirectoryModel dirModel = toDirMdl;
        for (int i = 0; i < GSConstFile.MAX_LEVEL; i++) {
            if (dirModel.getFdrParentSid() == 0) {
                break;
            }
            if (dirModel.getFdrParentSid() == moveDirSid) {
                return ECODE_MOVETOCHILD;
            }
            dirModel = dirDao.getNewDirectory(dirModel.getFdrParentSid());
        }
        return ECODE_NONE;
    }
    /**
     *
     * <br>[機  能] 階層数上限判定
     * <br>[解  説]
     * <br>[備  考]
     * @param sleDirLevel ディレクトリ以下階層数
     * @param dirLevel 移動先階層
     * @return true 階層上限Over
     */
    private boolean __isOverLevel(int sleDirLevel, int dirLevel) {
        if ((sleDirLevel + dirLevel) > GSConstFile.MAX_LEVEL) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] フォルダ・ファイルの移動処理。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil090ParamModel
     * @param buMdl セッションユーザモデル
     * @throws SQLException SQL実行例外
     */
    public void moveDir(
        Fil090ParamModel paramMdl,
        BaseUserModel buMdl) throws SQLException {
        //移動元ディレクトリ
        int dirSid = NullDefault.getInt(paramMdl.getFil090DirSid(), 0);

        //移動先ディレクトリ
        int sltDirSid = NullDefault.getInt(paramMdl.getMoveToDir(), 0);

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        //編集者グループSID（グループ名登録でない場合は0）
        int egid = filBiz.getGroupSid(paramMdl.getFil090EditId());
        moveDir(dirSid, sltDirSid, egid, buMdl.getUsrsid());
    }

    /**
     * <br>[機  能] フォルダ・ファイルの移動処理。
     * <br>[解  説]
     * <br>[備  考]
     * @param dirSid 移動元ディレクトリ
     * @param sltDirSid 移動先ディレクトリ
     * @param egid 編集者グループSID（グループ名登録でない場合は0）
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void moveDir(
            int dirSid,
            int sltDirSid,
            int egid,
            int sessionUsrSid) throws SQLException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDAccessConfDao daConfdao = new FileDAccessConfDao(con__);

        List<FileDirectoryModel> oldFileList = new ArrayList<>();

        //移動先ディレクトリ情報を取得する。
        FileDirectoryModel sltDirModel = dirDao.select(sltDirSid, GSConstFile.VERSION_NEW, false);
        if (sltDirModel == null) {
            return;
        }
        //移動元ディレクトリ情報を取得する。
        FileDirectoryModel dirModel = dirDao.select(dirSid, -1);
        if (dirModel == null) {
            return;
        }
        //移動元ディレクトリ情報をリクエスト変数に保管
        oldFileList.add(dirModel);
        oldFileList__ = oldFileList;

        //親ディレクトリ情報の更新
        __updateParent(sessionUsrSid, dirSid,
                dirModel.getFdrKbn(), sltDirModel, egid, sltDirModel.getFcbSid());

        if (sltDirModel.getFcbSid() == dirModel.getFcbSid()) {
            //フォルダ移動時親ディレクトリとのアクセス権限の差分を適用する
            __updateDAccessConf(daConfdao, dirSid, sltDirSid);
        } else {
            //キャビネット移動時_親ディレクトリと同じアクセス権限を設定する
            __deleteAccessConf(dirSid);
        }

        if (dirModel.getFdrKbn() == GSConstFile.DIRECTORY_FOLDER) {
            //子階層のキャビネットSIDの変更
            if (sltDirModel.getFcbSid() != dirModel.getFcbSid()) {
                __updateCabinet(sltDirModel.getFcbSid(), dirModel, sessionUsrSid);
            }
            //移動したディレクトリの階層の更新
            __updateLevel(sltDirSid, sessionUsrSid, dirModel, egid);
        }
        dirDao.updateAccessSid(sltDirSid);

    }

    /**
     * <br>[機  能] フォルダ・ファイルの移動処理。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil090ParamModel
     * @param buMdl セッションユーザモデル
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     */
    public void moveDirPlural(
        Fil090ParamModel paramMdl,
        BaseUserModel buMdl,
        String appRootPath) throws SQLException, IOToolsException, IOException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDAccessConfDao daConfdao = new FileDAccessConfDao(con__);

        //移動元ディレクトリ
        String[] fil040Select = paramMdl.getFil040SelectDel();

        //ディレクトリ情報一覧を取得する。(最新バージョン)
        List<FileDirectoryModel> dirList = dirDao.getNewDirectoryList(fil040Select, false);
        if (dirList == null || dirList.size() < 1) {
            return;
        }
        //移動元ディレクトリ情報をリクエスト変数に保管
        oldFileList__ = dirList;

        //移動先ディレクトリ
        int sltDirSid = NullDefault.getInt(paramMdl.getMoveToDir(), 0);
        //移動先ディレクトリ情報を取得する。
        FileDirectoryModel sltDirModel = dirDao.select(sltDirSid, GSConstFile.VERSION_NEW);
        if (sltDirModel == null) {
            return;
        }
        int cabinetSid = sltDirModel.getFcbSid();
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        List<String> oldFileList = new ArrayList<String>();

        for (FileDirectoryModel dirModel : dirList) {
            oldFileList.add(dirModel.getFdrName());

            //親ディレクトリ情報の更新
            int egid = filBiz.getGroupSid(paramMdl.getFil090EditId());
            __updateParent(buMdl.getUsrsid(),
                    dirModel.getFdrSid(), dirModel.getFdrKbn(), sltDirModel, egid, cabinetSid);
            if (cabinetSid == dirModel.getFcbSid()) {
                //フォルダ間の移動時 親ディレクトリとのアクセス権限の差分を適用する
                __updateDAccessConf(daConfdao, dirModel.getFdrSid(), sltDirSid);
            } else {
                //キャビネットの移動時 親ディレクトリと同じアクセス権限を設定する
                __deleteAccessConf(dirModel.getFdrSid());
            }
            if (dirModel.getFdrKbn() == GSConstFile.DIRECTORY_FOLDER) {
                //子階層のキャビネットSIDの変更
                if (sltDirModel.getFcbSid() != dirModel.getFcbSid()) {
                    __updateCabinet(sltDirModel.getFcbSid(), dirModel, buMdl.getUsrsid());
                }
                //移動したディレクトリの階層の更新
                __updateLevel(sltDirSid, buMdl.getUsrsid(), dirModel, egid);
            }
        }
        dirDao.updateAccessSid(sltDirSid);
    }

    /**
     * <br>[機  能] ディレクトリのアクセス設定の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param dirSid ディレクトリSID
     * @throws SQLException SQLException
     */
    private void __deleteAccessConf(int dirSid)
            throws SQLException {

        FileDAccessConfDao daDao = new FileDAccessConfDao(con__);
        //下位権限の論理チェックを行う
        List<FileAccessConfModel> subList = daDao.getAccessSubDirectoriesFiles(dirSid, false);
        if (subList == null || subList.size() <= 0) {
            return;
        }

        List<Integer> delSids = new ArrayList<Integer>();
        for (FileAccessConfModel confMdl : subList) {
            int subSid = confMdl.getFcbSid();
            delSids.add(subSid);
        }

        daDao.delete(delSids);
    }

    /**
     * <br>[機  能] 親ディレクトリ情報を更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid セッションユーザSID
     * @param dirSid 移動元ディレクトリ
     * @param fdrKbn ディレクトリ区分
     * @param sltDirModel 移動先ディレクトリ情報
     * @param egid 編集者グループSID ユーザ名登録の場合は0
     * @param cabinetSid キャビネットSID
     * @throws SQLException SQL実行時例外
     */
    private void __updateParent(
            int sessionUsrSid,
            int dirSid,
            int fdrKbn,
            FileDirectoryModel sltDirModel,
            int egid,
            int cabinetSid)
    throws SQLException {


        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel dirModel = new FileDirectoryModel();
        UDate now = new UDate();
        dirModel.setFcbSid(cabinetSid);
        dirModel.setFdrSid(dirSid);
        dirModel.setFdrLevel(sltDirModel.getFdrLevel() + 1);
        dirModel.setFdrEdate(now);
        dirModel.setFdrEuid(sessionUsrSid);
        dirModel.setFdrParentSid(sltDirModel.getFdrSid());
        dirModel.setFdrEgid(egid);

        //ファイル移動モード時は、更新履歴を更新する。
        if (fdrKbn == GSConstFile.DIRECTORY_FILE) {
            //親ディレクトリSIDとディレクトリ階層を更新する
            dirDao.updateParLv(dirModel);
        } else {
            //親ディレクトリを更新する
            if (dirModel.getFdrEgid() > 0) {
                dirDao.updateParentSidByGp(dirModel);
            } else {
                dirDao.updateParentSid(dirModel);
            }

        }
    }

    /**
    *
    * <br>[機  能] 移動先ディレクトリのアクセス制限にないサブディレクトリの個別アクセス制限を削除する
    * <br>[解  説]
    * <br>[備  考]
    * @param daDao FileDAccessConfDao
    * @param dirSid 移動対象ディレクトリ
    * @param sltDirSid 移動先ディレクトリ
    * @throws SQLException SQL実行時例外
    */
   private void __updateDAccessConf(FileDAccessConfDao daDao,
           int dirSid,
           int sltDirSid) throws SQLException {
       final int kbn_write = Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE);
       final int kbn_read = Integer.parseInt(GSConstFile.ACCESS_KBN_READ);
       String[] fullaccessGroups = daDao.getAccessParentGroup(sltDirSid, kbn_write);
       String[] fullaccessUsers = daDao.getAccessParentUser(sltDirSid, -1, kbn_write);
       String[] readGroups = daDao.getAccessParentGroup(sltDirSid, kbn_read);
       String[] readUsers = daDao.getAccessParentUser(sltDirSid, -1, kbn_read);

       //移動先に制限がない場合は処理無し
       if (fullaccessGroups.length == 0 && fullaccessUsers.length == 0
               && readGroups.length == 0 && readUsers.length == 0) {
           return;
       }
       //下位権限の論理チェックを行う
       List<FileAccessConfModel> subList = daDao.getAccessSubDirectoriesFiles(dirSid, false);
       if (subList == null || subList.size() <= 0) {
           return;
       }
       ArrayList<Integer> accessGroups = new ArrayList<Integer>();
       ArrayList<Integer> accessUsers = new ArrayList<Integer>();
       for (String sid : fullaccessGroups) {
           accessGroups.add(Integer.parseInt(sid.substring(1)));
       }
       for (String sid : readGroups) {
           accessGroups.add(Integer.parseInt(sid.substring(1)));
       }
       for (String sid : fullaccessUsers) {
           accessUsers.add(Integer.parseInt(sid));
       }
       for (String sid : readUsers) {
           accessUsers.add(Integer.parseInt(sid));
       }

       List<Integer> delSids = new ArrayList<Integer>();
       for (FileAccessConfModel confMdl : subList) {
           //親ディレクトリの権限に含まれないグループ・ユーザを削除
           boolean delFlg = false;
           int subSid = confMdl.getFcbSid();
           if (confMdl.getFaaAuth() == kbn_write) {
               //追加・変更・削除
               if (confMdl.getUsrKbn() == GSConstFile.USER_KBN_GROUP) {
                   //グループ
                   delFlg = __deleteNotInc(daDao,
                                           accessGroups,
                                           subSid,
                                           confMdl.getUsrSid(),
                                           GSConstFile.USER_KBN_GROUP);
               } else if (confMdl.getUsrKbn() == GSConstFile.USER_KBN_USER) {
                   //ユーザ
                   delFlg = __deleteNotInc(daDao,
                                           accessUsers,
                                           subSid,
                                           confMdl.getUsrSid(),
                                           GSConstFile.USER_KBN_USER);
               }

           } else if (confMdl.getFaaAuth() == kbn_read) {
               //閲覧
               if (confMdl.getUsrKbn() == GSConstFile.USER_KBN_GROUP) {
                   //グループ
                   delFlg = __deleteNotInc(daDao,
                                           accessGroups,
                                           subSid,
                                           confMdl.getUsrSid(),
                                           GSConstFile.USER_KBN_GROUP);
               } else if (confMdl.getUsrKbn() == GSConstFile.USER_KBN_USER) {
                   //ユーザ
                   delFlg = __deleteNotInc(daDao,
                                           accessUsers,
                                           subSid,
                                           confMdl.getUsrSid(),
                                           GSConstFile.USER_KBN_USER);
               }
           }

           //削除したSIDを追加
           if (delFlg && !delSids.contains(subSid)) {
               delSids.add(subSid);
           }
       }

       //削除したフォルダ・ファイルのアクセス権限をチェック
       for (int subSid : delSids) {
           int count = daDao.getCount(subSid);
           if (count <= 0) {
               //アクセス権限が一切なくなったフォルダ、ファイルを管理者のみの閲覧にする
               daDao.deleteSubDirectoriesFiles(subSid, false);
               FileAccessConfModel daConfMdl = new FileAccessConfModel();
               daConfMdl.setFcbSid(subSid);
               daConfMdl.setUsrSid(0);
               daConfMdl.setUsrKbn(GSConstFile.USER_KBN_USER);
               daConfMdl.setFaaAuth(Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
               daDao.insert(daConfMdl);
           }
       }
   }

   /**
    * 親の権限に含まれていないグループ・ユーザを削除する
    * @param dao FileDAccessConfDao
    * @param sids 親権限SIDリスト
    * @param fdrSid ディレクトリSID
    * @param usrSid ユーザSID
    * @param usrKbn ユーザ区分
    * @return true:削除実行 false:削除対象でない
    * @throws SQLException 実行例外
    */
   private boolean __deleteNotInc(FileDAccessConfDao dao,
                                  ArrayList<Integer> sids,
                                  int fdrSid,
                                  int usrSid,
                                  int usrKbn) throws SQLException {

       if (!sids.contains(Integer.valueOf(usrSid))) {
           //削除
           dao.delete(fdrSid, usrSid, usrKbn);
           return true;
       }
       return false;
   }

    /**
     * <br>[機  能] キャビネット情報を更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid セッションユーザSID
     * @param dirModel FileDirectoryModel
     * @param cabinetSid キャビネットSID
     * @throws SQLException SQL実行時例外
     */
    private void __updateCabinet(
            int cabinetSid,
            FileDirectoryModel dirModel,
            int sessionUsrSid)
    throws SQLException {

        //子階層のデータ取得
        FilTreeBiz treeBiz = new FilTreeBiz(con__);
        FilChildTreeModel childMdl = treeBiz.getChildOfTarget(dirModel);

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel koModel = new FileDirectoryModel();
        UDate now = new UDate();

        ArrayList<Integer> dirSidList = new ArrayList<Integer>();
        for (int idx = 0; idx < childMdl.getListOfDir().size(); idx++) {
            int sid = childMdl.getListOfDir().get(idx).getFdrSid();
            dirSidList.add(sid);
        }
        for (int idx = 0; idx < childMdl.getListOfFile().size(); idx++) {
            int sid = childMdl.getListOfFile().get(idx).getFdrSid();
            dirSidList.add(sid);
        }
        koModel.setFcbSid(cabinetSid);
        koModel.setFdrEdate(now);
        koModel.setFdrEuid(sessionUsrSid);
        dirDao.updateCabinetSid(koModel, dirSidList);
    }

    /**
     * <br>[機  能] 移動フォルダの階層情報を更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dirSid 移動先ディレクトリSID
     * @param sessionUsrSid セッションユーザSID
     * @param dirModel 移動元ディレクトリ情報
     * @param egid 編集者グループSID ユーザ名登録の場合は0
     * @throws SQLException SQL実行時例外
     */
    private void __updateLevel(int dirSid, int sessionUsrSid,
            FileDirectoryModel dirModel, int egid)
    throws SQLException {

        //子階層のデータ取得
        FilTreeBiz treeBiz = new FilTreeBiz(con__);
        FilChildTreeModel childMdl = treeBiz.getChildOfTarget(dirModel);

        //移動前後の階層の差を取得
        int margin = __getLevelMargin(dirModel.getFdrSid(), dirSid);

        //フォルダ階層の更新
        ArrayList<FileDirectoryModel> listOfDir = childMdl.getListOfDir();
        if (!listOfDir.isEmpty()) {
            __updateLevel(listOfDir, sessionUsrSid, margin, egid);
        }

        //ファイル階層更新
        ArrayList<FileDirectoryModel> listOfFile = childMdl.getListOfFile();
        if (!listOfFile.isEmpty()) {
            __updateLevel(listOfFile, sessionUsrSid, margin, egid);
        }
    }

    /**
     * <br>[機  能] 移動前後のディレクトリ階層差を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dirSid 移動元ディレクトリ
     * @param moveToDir 移動先ディレクトリ
     * @return margin 移動前後の階層差
     * @throws SQLException SQL実行時例外
     */
    private int __getLevelMargin(int dirSid, int moveToDir) throws SQLException {

        int margin = 0;
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel dirModel = dirDao.getNewDirectory(dirSid);

        //移動先ディレクトリ情報を取得する。
        FileDirectoryModel sltDirModel = dirDao.getNewDirectory(moveToDir);
        if (dirModel == null || sltDirModel == null) {
            return margin;
        }
        margin = sltDirModel.getFdrLevel() - dirModel.getFdrLevel() + 1;

        return margin;
    }

    /**
     * <br>[機  能] ディレクトリの階層を更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dirList 更新ディレクトリリスト
     * @param sessionUsrSid セッションユーザSID
     * @param margin 移動前後の階層差
     * @param egid 編集者グループSID ユーザ名登録の場合は0
     * @throws SQLException SQL実行時例外
     */
    private void __updateLevel(ArrayList<FileDirectoryModel> dirList,
                     int sessionUsrSid, int margin, int egid)
    throws SQLException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        UDate now = new UDate();

        for (FileDirectoryModel model : dirList) {
            model.setFdrEuid(sessionUsrSid);
            model.setFdrEgid(egid);
            model.setFdrEdate(now);
            model.setFdrLevel(model.getFdrLevel() + margin);
            dirDao.updateLevel(model);
        }

    }

    /**
     *
     * <br>[機  能] 移動ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param targetDirPath 移動元ディレクトリパス
     * @param paramMdl パラメータモデル
     * @return ログ内容
     * @throws SQLException SQL実行時例外
     */
    public String valueOpLog(Fil090ParamModel paramMdl,
            String targetDirPath) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        String textMoveFolder = gsMsg.getMessage("cmn.move.folder");
        String textMoveFile = gsMsg.getMessage("fil.move.file");
        String textMovePluralFile = gsMsg.getMessage("fil.116");

        //移動先ディレクトリ
        int sltDirSid = NullDefault.getInt(paramMdl.getMoveToDir(), 0);
        String newPath = filBiz.getDirctoryPath(sltDirSid);
        String logValue = "";
        if (paramMdl.getFil090SelectPluralKbn() == Fil090Biz.MOVE_PLURAL_NO) {
            if (paramMdl.getFil090Mode().equals(String.valueOf(GSConstFile.DIRECTORY_FILE))) {
                logValue = textMoveFile;
            } else {
                logValue = textMoveFolder;
            }
        } else {
            logValue = textMovePluralFile;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(logValue);
        sb.append("\r\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("fil.fil090kn.4"));
        sb.append("] ");
        sb.append(targetDirPath);
        sb.append("\r\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("cmn.target"));
        sb.append("] ");
        //移動元ディレクトリ
        if (oldFileList__ == null) {
            return sb.toString();
        }
        for (FileDirectoryModel dirMdl : oldFileList__) {
            sb.append("\r\n");
            sb.append(" ");
            sb.append(dirMdl.getFdrName());

        }
        sb.append("\r\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("fil.75"));
        sb.append("] ");
        sb.append(newPath);


        return sb.toString();


    }
}