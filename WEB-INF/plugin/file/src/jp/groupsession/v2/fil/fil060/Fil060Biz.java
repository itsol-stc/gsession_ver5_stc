package jp.groupsession.v2.fil.fil060;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCallConfDao;
import jp.groupsession.v2.fil.dao.FileDAccessConfDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileAccessConfModel;
import jp.groupsession.v2.fil.model.FileCallConfModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileParentAccessDspModel;

/**
 * <br>[機  能] フォルダ登録画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil060Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil060Biz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Fil060Biz(Connection con, RequestModel reqMdl) {
        reqMdl__ = reqMdl;
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil060ParamModel
     * @param appRoot アプリケーションのルートパス
     * @param buMdl セッションユーザモデル
     * @throws Exception 実行時例外
     */
    public void setInitData(
        Fil060ParamModel paramMdl,
        String appRoot,
        BaseUserModel buMdl)
        throws Exception {

        log__.debug("Fil060Biz Start");

        //画面モードを設定
        int dirSid = NullDefault.getInt(paramMdl.getFil050DirSid(), -1);
        int mode = GSConstFile.MODE_ADD;
        if (dirSid != -1) {
            mode = GSConstFile.MODE_EDIT;
        }
        paramMdl.setFil060CmdMode(mode);

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        if (paramMdl.getFil060PluginId() == null) {
            //初期表示

            if (mode == GSConstFile.MODE_EDIT) {
                //編集モードの場合DBより各項目を設定する。
                __setData(paramMdl, appRoot, buMdl);
            }

            //プラグインIDを設定
            paramMdl.setFil060PluginId(GSConstFile.PLUGIN_ID_FILE);

            //個人設定が無い場合は初期値で登録する。
            filBiz.getUserConf(buMdl.getUsrsid());

            paramMdl.setFile060AdaptIncFile(GSConstFile.ADAPT_FILE_INC_KBN_OFF);

        }

        List<LabelValueBean> lvList = new ArrayList<LabelValueBean>();

        //更新者設定
        paramMdl.setFil060EditId(NullDefault.getString(
                paramMdl.getFil060EditId(), String.valueOf(buMdl.getUsrsid())));

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
        paramMdl.setFil060groupList(lvList);

        //親ディレクトリSIDを取得する
        int parentSid = NullDefault.getInt(paramMdl.getFil050ParentDirSid(), -1);
        if (parentSid < 1) {
            parentSid = filBiz.getParentDirSid(dirSid);
        }
        paramMdl.setFil050ParentDirSid(String.valueOf(parentSid));

        if (__isParentZeroUser(parentSid)) {
            //親がゼロユーザ
            paramMdl.setFil060ParentZeroUser("1");
            return;
        } else {
            paramMdl.setFil060ParentZeroUser("0");
        }

        //親ディレクトリ（追加・変更・削除）アクセス制限リスト
        paramMdl.setFil060ParentEditList(__getParentAccessUser(
                paramMdl, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE)));
        //親ディレクトリ（閲覧）アクセス制限リスト
        paramMdl.setFil060ParentReadList(__getParentAccessUser(
                paramMdl, Integer.parseInt(GSConstFile.ACCESS_KBN_READ)));
        if (paramMdl.getFil060ParentEditList().size()
                > Integer.parseInt(GSConstFile.MAXCOUNT_PARENT_ACCESS)
         || paramMdl.getFil060ParentReadList().size()
                > Integer.parseInt(GSConstFile.MAXCOUNT_PARENT_ACCESS)) {
            paramMdl.setFil060ParentAccessAllDspFlg(1);
        }
    }

    /**
     * <br>[機  能] ディレクトリ情報DBより取得しを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil060ParamModel
     * @param appRoot アプリケーションのルートパス
     * @param buMdl BaseUserModel
     * @throws Exception 実行時例外
     */
    private void __setData(Fil060ParamModel paramMdl,
                                         String appRoot, BaseUserModel buMdl)
    throws Exception {

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        int dirSid = NullDefault.getInt(paramMdl.getFil050DirSid(), -1);

        //ディレクトリ情報
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel dirModel = dirDao.getNewDirectory(dirSid);

        if (dirModel == null) {
            return;
        }

        //ディレクトリが未選択の場合、ディレクトリSIDを設定
        if (NullDefault.getInt(paramMdl.getFil010SelectDirSid(), -1) < 0) {
            paramMdl.setFil010SelectDirSid(String.valueOf(dirSid));
        }

        //キャビネットが未選択の場合、キャビネットSIDを設定
        if (NullDefault.getInt(paramMdl.getFil010SelectCabinet(), -1) < 0) {
            paramMdl.setFil010SelectCabinet(String.valueOf(dirModel.getFcbSid()));
        }

        paramMdl.setFil060DirName(dirModel.getFdrName());
        paramMdl.setFil060Biko(dirModel.getFdrBiko());
        paramMdl.setFil060EditId(filBiz.setUpdateSid(buMdl.getUsrsid(),
                                                     dirModel.getFdrEgid()));

        //アクセス制限情報
        FileDAccessConfDao daConfDao = new FileDAccessConfDao(con__);
        paramMdl.setFil060AccessKbn(String.valueOf(GSConstFile.ACCESS_KBN_OFF));
        if (daConfDao.getCount(dirSid) > 0) {
            //追加・変更・削除
            String[] leftFull = daConfDao.getAccessUser(
                    dirSid, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
            if (leftFull != null && leftFull.length > 0) {
                //0ユーザチェック
                List<String> leftList = new ArrayList<String>();
                leftList.addAll(Arrays.asList(leftFull));
                if (leftList.contains("0")) {
                    leftList.remove("0");
                    leftFull = leftList.toArray(new String[leftList.size()]);
                }
            }
            paramMdl.setFil060SvAcFull(leftFull);
            //閲覧
            String[] leftRead = daConfDao.getAccessUser(
                    dirSid, Integer.parseInt(GSConstFile.ACCESS_KBN_READ));
            paramMdl.setFil060SvAcRead(leftRead);
            //個人キャビネット内で閲覧制限対象がいなければ、アクセス区分は「制限しない」
            int personalFlg = paramMdl.getFil040PersonalFlg();
            if (personalFlg == GSConstFile.CABINET_KBN_PUBLIC || leftRead.length > 0) {
                paramMdl.setFil060AccessKbn(String.valueOf(GSConstFile.ACCESS_KBN_ON));
            }
        }
    }

    /**
     * <br>[機  能] ディレクトリを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil060ParamModel
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOエラー
     */
    public void deleteDirectory(
            Fil060ParamModel paramMdl, int usrSid)
    throws SQLException, IOToolsException {



        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        filBiz.deleteDirectoryLogical(
                Stream.of(paramMdl.getFil050DirSid())
                    .toArray(String[]::new),
                null);

    }

    /**
     * 親ディレクトリのアクセス制限ユーザリストを取得する
     * @param paramMdl パラメータモデル
     * @param auth 権限区分
     * @return アクセス制限ユーザリスト
     * @throws SQLException 実行例外
     */
    private ArrayList<FileParentAccessDspModel> __getParentAccessUser(
            Fil060ParamModel paramMdl,
            int auth) throws SQLException {

        ArrayList<FileParentAccessDspModel> ret = new ArrayList<FileParentAccessDspModel>();

        GroupDao dao = new GroupDao(con__);
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con__);

        //グループを全て取得
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        ArrayList<GroupModel> allGpList = dao.getGroupTree(sortMdl);

        //親ディレクトリのアクセス権限を取得
        List<String> parentSids = new ArrayList<String>();
        String[] sids = null;
        int fdrSid = NullDefault.getInt(paramMdl.getFil050ParentDirSid(), -1);
        FileDAccessConfDao daConfDao = new FileDAccessConfDao(con__);
        sids = daConfDao.getAccessUser(fdrSid, auth);
        if (sids != null) {
            parentSids.addAll(Arrays.asList(sids));
        }

        //アクセス制限ユーザリスト作成（グループ）
        if (parentSids != null && parentSids.size() > 0) {
            for (GroupModel bean : allGpList) {
                if (!parentSids.contains(String.valueOf("G" + bean.getGroupSid()))) {
                    continue;
                }
                FileParentAccessDspModel dspBean = new FileParentAccessDspModel();
                dspBean.setUserName(bean.getGroupName());
                dspBean.setGrpFlg(1);
                ret.add(dspBean);
            }
        }

        //アクセス制限ユーザリスト作成（ユーザ）
        if (parentSids != null && parentSids.size() > 0) {
            for (int i = 0; i < parentSids.size(); i++) {
                if (parentSids.get(i).substring(0, 1).equals("G")
                 || parentSids.get(i).equals("0")) {
                    parentSids.remove(i);
                    i--;
                }
            }
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> usList
                = userBiz.getUserList(con__, (String[]) parentSids.toArray(new String[0]));
            for (int i = 0; i < usList.size(); i++) {
                CmnUsrmInfModel cuiMdl = usList.get(i);
                FileParentAccessDspModel dspBean = new FileParentAccessDspModel();
                dspBean.setUserName(cuiMdl.getUsiSei() + " " + cuiMdl.getUsiMei());
                dspBean.setGrpFlg(0);
                dspBean.setUsrUkoFlg(cuiMdl.getUsrUkoFlg());
                ret.add(dspBean);
            }
        }

        return ret;
    }

    /**
     * 親ディレクトリのアクセス権限がゼロユーザか判定する
     * @param parentSid 親ディレクトリSID
     * @return true:ゼロユーザ false:ゼロユーザでない
     * @throws SQLException 実行例外
     */
    private boolean __isParentZeroUser(int parentSid) throws SQLException {

        String[] sids = null;
        FileDAccessConfDao daConfDao = new FileDAccessConfDao(con__);
        sids = daConfDao.getAccessUser(
                parentSid, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
        if (sids != null && sids.length > 0) {
            List<String> parentSids = new ArrayList<String>();
            parentSids.addAll(Arrays.asList(sids));
            if (parentSids.contains("0")) {
                //ゼロユーザ
                return true;
            }
        }

        return false;
    }
    /**
     * <br>[機  能] フォルダを登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil060ParamModel
     * @param buMdl セッションユーザモデル
     * @param cntCon MlCountMtController
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return ディレクトリSID
     */
    public int registerData(
        Fil060ParamModel paramMdl,
        BaseUserModel buMdl,
        MlCountMtController cntCon,
        String appRootPath) throws SQLException, IOToolsException, TempFileException {

        int dirSid = -1;
        int usrSid = buMdl.getUsrsid();

        if (paramMdl.getFil060CmdMode()
                == Integer.parseInt(GSConstFile.CMN_MODE_ADD)) {
            //ディレクトリSIDを採番する。
            dirSid = (int) cntCon.getSaibanNumber(GSConstFile.PLUGIN_ID_FILE,
                            GSConstFile.SBNSID_SUB_DIRECTORY,
                            usrSid);
            //新規登録
            __insertDirectory(paramMdl, dirSid, usrSid);

            //親ディレクトリが更新通知を設定している場合、更新通知を設定する。
            insertCallConf(paramMdl, dirSid);

        } else {

            //ディレクトリSID
            dirSid = NullDefault.getInt(paramMdl.getFil050DirSid(), -1);

            //ディレクトリ情報を更新
            __updateDirectory(paramMdl, dirSid, usrSid);

        }

        //個人キャビネット内か判定
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        int personalFlg = filBiz.getPersonalFlg(
                Integer.parseInt(paramMdl.getFil010SelectCabinet()));

        //ディレクトリアクセス設定
        __insertDaccessConf(dirSid, paramMdl, personalFlg);
        FileDirectoryDao fdDao = new FileDirectoryDao(con__);

        fdDao.updateAccessSid(dirSid,
                Integer.parseInt(paramMdl.getFil060AccessKbn()),
                Integer.parseInt(paramMdl.getFile060AdaptIncFile()));

        return dirSid;
    }

    /**
     * <br>[機  能] ディレクトリ情報を登録する。（編集時）
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil060ParamModel
     * @param dirSid ディレクトリSID
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void __updateDirectory(
        Fil060ParamModel paramMdl,
        int dirSid,
        int usrSid) throws SQLException, IOToolsException {

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel newDirModel = new FileDirectoryModel();
        UDate now = new UDate();

        //最新バージョン情報取得
        FileDirectoryModel dirModel = dirDao.getNewDirectory(dirSid);
        if (dirModel == null) {
            return;
        }

        newDirModel.setFdrSid(dirSid);
        newDirModel.setFdrBiko(paramMdl.getFil060Biko());
        newDirModel.setFdrKbn(GSConstFile.DIRECTORY_FOLDER);
        newDirModel.setFdrLevel(dirModel.getFdrLevel());
        newDirModel.setFdrName(paramMdl.getFil060DirName());
        newDirModel.setFdrEdate(now);
        newDirModel.setFdrEuid(usrSid);
        newDirModel.setFdrEgid(filBiz.getGroupSid(paramMdl.getFil060EditId()));

        dirDao.updateFolder(newDirModel);
    }

    /**
     * <br>[機  能] ディレクトリ情報を登録する。（追加時）
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil060ParamModel
     * @param dirSid ディレクトリSID
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void __insertDirectory(Fil060ParamModel paramMdl, int dirSid,
            int usrSid)
    throws SQLException, IOToolsException {

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel newDirModel = new FileDirectoryModel();
        UDate now = new UDate();
        int parDirSid = NullDefault.getInt(paramMdl.getFil050ParentDirSid(), 0);

        //親ディレクトリ情報取得
        FileDirectoryModel dirModel = dirDao.select(parDirSid, -1);
        if (dirModel == null) {
            return;
        }

        newDirModel.setFdrSid(dirSid);
        newDirModel.setFcbSid(dirModel.getFcbSid());
        newDirModel.setFdrLevel(dirModel.getFdrLevel() + 1);
        newDirModel.setFdrParentSid(parDirSid);
        newDirModel.setFdrBiko(paramMdl.getFil060Biko());
        newDirModel.setFdrName(paramMdl.getFil060DirName());
        newDirModel.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
        newDirModel.setFdrKbn(GSConstFile.DIRECTORY_FOLDER);
        newDirModel.setFdrVerKbn(GSConstFile.VERSION_KBN_OFF);
        newDirModel.setFdrVersion(GSConstFile.VERSION_KBN_OFF);
        newDirModel.setFdrAuid(usrSid);
        newDirModel.setFdrAdate(now);
        newDirModel.setFdrEuid(usrSid);
        newDirModel.setFdrEgid(filBiz.getGroupSid(paramMdl.getFil060EditId()));
        newDirModel.setFdrEdate(now);

        dirDao.insert(newDirModel);
    }

    /**
     * <br>[機  能] 親ディレクトリに更新通知が設定されている場合、登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil060ParamModel
     * @param dirSid ディレクトリSID
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void insertCallConf(Fil060ParamModel paramMdl, int dirSid)
    throws SQLException, IOToolsException {

        int parDirSid = NullDefault.getInt(paramMdl.getFil050ParentDirSid(), 0);

        FileCallConfDao confDao = new FileCallConfDao(con__);

        //親ディレクトリに更新通知設定しているユーザ取得
        ArrayList<FileCallConfModel> confModelList = confDao.select(parDirSid);

        FileCallConfModel confModel = null;

        if (confModelList != null && confModelList.size() > 0) {
            //更新通知設定を登録
            for (FileCallConfModel model : confModelList) {
                confModel = new FileCallConfModel();
                confModel.setFdrSid(dirSid);
                confModel.setUsrSid(model.getUsrSid());
                confDao.insert(confModel);
            }
        }

    }

    /**
     * 更新者がユーザかグループかを判定する
     * <br>[機  能]先頭文字に"G"が有る場合はグループ
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public static boolean isEditGroup(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf("G");

        // 先頭文字に"G"が有る場合はグループ
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }

    /**
     * ディレクトリアクセス設定を登録する
     * @param dirSid ディレクトリSID
     * @param paramMdl パラメータモデル
     * @param personalFlg 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット
     * @throws SQLException 実行例外
     */
    private void __insertDaccessConf(int dirSid, Fil060ParamModel paramMdl, int personalFlg)
            throws SQLException {

        //現在の権限を削除
        FileDAccessConfDao dao = new FileDAccessConfDao(con__);
        dao.delete(dirSid);


        if (paramMdl.getFile060AdaptIncFile().equals(GSConstFile.ADAPT_FILE_INC_KBN_ON)) {
            //下位フォルダのアクセス設定を削除
            dao.deleteSubDirectoriesFiles(dirSid, false);
        }

        //個別設定しない
        if (paramMdl.getFil060AccessKbn().equals(String.valueOf(GSConstFile.ACCESS_KBN_OFF))) {
            //共有キャビネットの場合、ここで終了
            if (personalFlg == GSConstFile.CABINET_KBN_PUBLIC) {
                //個別設定しない
                return;
            //個人キャビネットの場合、閲覧アクセスユーザセレクトボックスを空にする
            } else if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
                paramMdl.setFil060SvAcRead(new String[0]);
            }
        }

        //フルアクセス
        ArrayList<Integer> groupFullList = new ArrayList<Integer>();
        ArrayList<Integer> userFullList = new ArrayList<Integer>();
        String[] full = paramMdl.getFil060SvAcFull();
        if (full != null) {
            for (int i = 0; i < full.length; i++) {
                String str = NullDefault.getString(full[i], "-1");
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    groupFullList.add(Integer.parseInt(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    userFullList.add(Integer.parseInt(str));
                }
            }
        }

        //個人キャビネットの場合、個人キャビネット所有者のみフルアクセス権限を与えられる
        if (personalFlg == Integer.parseInt(GSConstFile.DSP_CABINET_PRIVATE)) {
            BaseUserModel usModel = reqMdl__.getSmodel();
            int ownerSid = usModel.getUsrsid();
            dao.insert(dirSid, ownerSid,
                    GSConstFile.USER_KBN_USER, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
        //共有キャビネットの場合、選択したユーザ・グループに対してフルアクセス権限を与える
        } else if (personalFlg == GSConstFile.CABINET_KBN_PUBLIC) {
        //フルアクセスグループ登録
        dao.insert(dirSid, groupFullList,
                GSConstFile.USER_KBN_GROUP, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
        //フルアクセスユーザ登録
        dao.insert(dirSid, userFullList,
                GSConstFile.USER_KBN_USER, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
        }

        //閲覧アクセス
        ArrayList<Integer> groupReadList = new ArrayList<Integer>();
        ArrayList<Integer> userReadList = new ArrayList<Integer>();
        String[] read = paramMdl.getFil060SvAcRead();
        if (read != null) {
            for (int i = 0; i < read.length; i++) {
                String str = NullDefault.getString(read[i], "-1");
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    groupReadList.add(Integer.valueOf(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    userReadList.add(Integer.valueOf(str));
                }
            }
        }

        //閲覧アクセスグループ登録
        dao.insert(dirSid, groupReadList,
                GSConstFile.USER_KBN_GROUP, Integer.parseInt(GSConstFile.ACCESS_KBN_READ));
        //閲覧アクセスユーザ登録
        dao.insert(dirSid, userReadList,
                GSConstFile.USER_KBN_USER, Integer.parseInt(GSConstFile.ACCESS_KBN_READ));

        //共有キャビネット内において、下位フォルダに適用しない場合は下位権限の論理チェックを行う
        if (paramMdl.getFile060AdaptIncFile().equals(GSConstFile.ADAPT_FILE_INC_KBN_OFF)
                && personalFlg == Integer.parseInt(GSConstFile.DSP_CABINET_PUBLIC)) {
            List<FileAccessConfModel> subList = dao.getAccessSubDirectoriesFiles(dirSid);
            if (subList == null || subList.size() <= 0) {
                return;
            }

            groupReadList.addAll(groupFullList);
            userReadList.addAll(userFullList);

            List<Integer> delSids = new ArrayList<Integer>();
            for (FileAccessConfModel confMdl : subList) {
                //親ディレクトリの権限に含まれないグループ・ユーザを削除
                boolean delFlg = false;
                int subSid = confMdl.getFcbSid();
                if (confMdl.getFaaAuth() == Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE)) {
                    //追加・変更・削除
                    if (confMdl.getUsrKbn() == GSConstFile.USER_KBN_GROUP) {
                        //グループ
                        delFlg = __deleteNotInc(dao,
                                                groupReadList,
                                                subSid,
                                                confMdl.getUsrSid(),
                                                GSConstFile.USER_KBN_GROUP);
                    } else if (confMdl.getUsrKbn() == GSConstFile.USER_KBN_USER) {
                        //ユーザ
                        delFlg = __deleteNotInc(dao,
                                                userReadList,
                                                subSid,
                                                confMdl.getUsrSid(),
                                                GSConstFile.USER_KBN_USER);
                    }

                } else if (confMdl.getFaaAuth() == Integer.parseInt(GSConstFile.ACCESS_KBN_READ)) {
                    //閲覧
                    if (confMdl.getUsrKbn() == GSConstFile.USER_KBN_GROUP) {
                        //グループ
                        delFlg = __deleteNotInc(dao,
                                                groupReadList,
                                                subSid,
                                                confMdl.getUsrSid(),
                                                GSConstFile.USER_KBN_GROUP);
                    } else if (confMdl.getUsrKbn() == GSConstFile.USER_KBN_USER) {
                        //ユーザ
                        delFlg = __deleteNotInc(dao,
                                                userReadList,
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
                int count = dao.getCount(subSid);
                if (count <= 0) {
                    //アクセス権限が一切なくなったフォルダ、ファイルを管理者のみの閲覧にする
                    dao.deleteSubDirectoriesFiles(subSid, false);
                    FileAccessConfModel daConfMdl = new FileAccessConfModel();
                    daConfMdl.setFcbSid(subSid);
                    daConfMdl.setUsrSid(0);
                    daConfMdl.setUsrKbn(GSConstFile.USER_KBN_USER);
                    daConfMdl.setFaaAuth(Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
                    dao.insert(daConfMdl);
                }
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

}