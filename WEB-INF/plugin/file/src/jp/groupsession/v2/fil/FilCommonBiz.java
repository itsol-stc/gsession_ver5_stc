package jp.groupsession.v2.fil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.fil.dao.FileAccessConfDao;
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.dao.FileCabinetAdminDao;
import jp.groupsession.v2.fil.dao.FileCabinetBinDao;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileCallConfDao;
import jp.groupsession.v2.fil.dao.FileCallDataDao;
import jp.groupsession.v2.fil.dao.FileCrtConfDao;
import jp.groupsession.v2.fil.dao.FileDAccessConfDao;
import jp.groupsession.v2.fil.dao.FileDao;
import jp.groupsession.v2.fil.dao.FileDirectoryBinDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileDownloadLogDao;
import jp.groupsession.v2.fil.dao.FileErrlAdddataDao;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.fil.dao.FileFileRekiDao;
import jp.groupsession.v2.fil.dao.FileLogCountSumDao;
import jp.groupsession.v2.fil.dao.FileMoneyMasterDao;
import jp.groupsession.v2.fil.dao.FilePcbOwnerDao;
import jp.groupsession.v2.fil.dao.FileUconfDao;
import jp.groupsession.v2.fil.dao.FileUploadLogDao;
import jp.groupsession.v2.fil.fil010.FileCabinetDspModel;
import jp.groupsession.v2.fil.model.FilChildTreeModel;
import jp.groupsession.v2.fil.model.FileAccessConfModel;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.model.FileCabinetAdminModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileCallConfModel;
import jp.groupsession.v2.fil.model.FileCallDataModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileDownloadLogModel;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;
import jp.groupsession.v2.fil.model.FileFileBinModel;
import jp.groupsession.v2.fil.model.FileFileRekiModel;
import jp.groupsession.v2.fil.model.FileLogCountSumModel;
import jp.groupsession.v2.fil.model.FileMoneyMasterModel;
import jp.groupsession.v2.fil.model.FileUconfModel;
import jp.groupsession.v2.fil.model.FileUploadLogModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.SmlSender;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] ファイル管理の共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FilCommonBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilCommonBiz.class);
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** DBコネクション */
    private Connection con__ = null;

    /** アクセスエラーコード エラー無し*/
    public static final int ERR_NONE = 0;
    /** アクセスエラーコード 対象がない*/
    public static final int ERR_NOT_EXIST = 1;
    /** アクセスエラーコード キャビネット閲覧権限がない*/
    public static final int ERR_NONE_CAB_VIEW_POWER = 2;
    /** アクセスエラーコード キャビネット編集権限がない*/
    public static final int ERR_NONE_CAB_EDIT_POWER = 3;
    /** アクセスエラーコード ディレクトリ区分が一致しない*/
    public static final int ERR_NOT_DIRKBN = 4;

    /**
     * <p>コンストラクタ
     * @param reqMdl リクエストモデル
     * @param con Connection
     */
    public FilCommonBiz(RequestModel reqMdl, Connection con) {
        super();
        con__ = con;
        reqMdl__ = reqMdl;
    }
//    /**
//     * <br>[機  能] リクエストよりコマンドパラメータを取得する
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param req リクエスト
//     * @return CMDパラメータ
//     */
//    public static String getCmdProperty(HttpServletRequest req) {
//        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
//        cmd = cmd.trim();
//        log__.debug("--- cmd :" + cmd);
//        return cmd;
//    }

    /**
     * <br>[機  能] 選択用グループコンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param allGroupCombo ユーザコンボ
     * @param selectGroupSid 選択済みユーザSID
     * @return 選択用グループコンボ index=0:選択済 index=1:未選択
     * @throws SQLException SQL実行時例外
     */
    public List<List<LabelValueBean>> getGroupCombo(List<LabelValueBean> allGroupCombo,
                                                    String[] selectGroupSid)
    throws SQLException {

        List<String> selectGroupList = new ArrayList<String>();
        if (selectGroupSid != null) {
            selectGroupList = Arrays.asList(selectGroupSid);
        }

        //選択済みグループ、未選択グループのコンボ情報を作成する
        List<LabelValueBean> selectGroupCombo = new ArrayList<LabelValueBean>();
        List<LabelValueBean> noSelectGroupCombo = new ArrayList<LabelValueBean>();
        for (LabelValueBean groupMdl : allGroupCombo) {
            String grpSid = groupMdl.getValue();
            if (selectGroupList.contains(grpSid)) {
                selectGroupCombo.add(groupMdl);
            } else {
                noSelectGroupCombo.add(groupMdl);
            }
        }

        List<List<LabelValueBean>> groupComboList = new ArrayList<List<LabelValueBean>>();
        groupComboList.add(selectGroupCombo);
        groupComboList.add(noSelectGroupCombo);

        return groupComboList;
    }

    /**
     * <br>[機  能] 選択用ユーザコンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param allUserCombo ユーザコンボ
     * @param selectUserSid 選択済みユーザSID
     * @return 選択用ユーザコンボ index=0:選択済 index=1:未選択
     * @throws SQLException SQL実行時例外
     */
    public List<List<LabelValueBean>> getUserCombo(int grpSid,
                                                    List<LabelValueBean> allUserCombo,
                                                    String[] selectUserSid)
    throws SQLException {


        List<String> selectUserList = new ArrayList<String>();
        if (selectUserSid != null) {
            selectUserList = Arrays.asList(selectUserSid);
        }

        //未選択ユーザのコンボ情報を作成する
        List<LabelValueBean> noSelectUserCombo = new ArrayList<LabelValueBean>();
        List<LabelValueBean> selectUserCombo = new ArrayList<LabelValueBean>();

        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con__);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        FileDao fileDao = new FileDao(con__);
        List<LabelValueBean> userCombo
                            = fileDao.getUserListBelongGroup(grpSid, sortMdl);
        for (LabelValueBean userMdl : userCombo) {
            String userSid = userMdl.getValue();
            if (!selectUserList.contains(userSid)) {
                noSelectUserCombo.add(userMdl);
            }
        }

        //選択済みユーザのコンボ情報を設定する
        if (allUserCombo != null) {
            for (LabelValueBean userMdl : allUserCombo) {
                String userSid = userMdl.getValue();
                if (selectUserList.contains(userSid)) {
                    selectUserCombo.add(userMdl);
                }
            }
        }

        List<List<LabelValueBean>> userComboList = new ArrayList<List<LabelValueBean>>();
        userComboList.add(selectUserCombo);
        userComboList.add(noSelectUserCombo);
        return userComboList;
    }

    /**
     * <br>[機  能] キャビネットコンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param notSelectFlg true:選択してくださいを追加する。
     * @param cabinetKbn 0:共有フォルダ，
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */

    public List<LabelValueBean> getCabinetLabel(boolean notSelectFlg,
            int cabinetKbn)
    throws SQLException {

        //キャビネットリストを取得する。
        FileCabinetDao cabiDao = new FileCabinetDao(con__);

        ArrayList<FileCabinetDspModel> cabList =
                cabiDao.getFileCabinetDspModelsAll(cabinetKbn);

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        if (notSelectFlg) {
            GsMessage gsMsg = new GsMessage(reqMdl__);
            String textSelect = gsMsg.getMessage("cmn.select.plz");
            labelList.add(new LabelValueBean(textSelect, "-1"));
        }

        for (FileCabinetDspModel cabMdl : cabList) {
            labelList.add(new LabelValueBean(
                    cabMdl.getFcbName(), String.valueOf(cabMdl.getFcbSid())));
        }

        return labelList;
    }

    /**
     * <br>[機  能] ファイル・フォルダパスの文字列を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param directorySid ディレクトリSID
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */

    public String getDirctoryPath(int directorySid) throws SQLException {

        return getDirctoryPath(directorySid, false);
    }

    /**
     * <br>[機  能] ファイル・フォルダパスの文字列を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param directorySid ディレクトリSID
     * @param ignoreJkbnFlg true:論理削除キャビネットを含める, false:論理削除キャビネットを含めない
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */

    public String getDirctoryPath(
            int directorySid, boolean ignoreJkbnFlg) throws SQLException {

        String directoryPath = "";
        String cabinetName = "";
        String sep = "/";

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel dirModel = null;

        int fdrSid = directorySid;
        ArrayList<Integer> beforeParentSid = new ArrayList<>();
        for (int i = 1; i < 1000; i++) {

            //ディレクトリ情報を取得する。
            dirModel = dirDao.getNewDirectory(fdrSid);
            if (dirModel == null) {
                break;
            }
            //ディレクトリ構造にループが発生している場合は途中で切る
            if (beforeParentSid.indexOf(dirModel.getFdrParentSid()) != -1) {
                break;
            }
            if (dirModel.getFdrLevel() == GSConstFile.DIRECTORY_LEVEL_0) {

                //キャビネット名を取得する。
                cabinetName = getCabinetName(dirModel.getFcbSid(), ignoreJkbnFlg);

                break;
            } else if (i == 1)  {

                //ディレクトリパスに追加。（/を入れない）
                directoryPath = dirModel.getFdrName();
                fdrSid = dirModel.getFdrParentSid();
                if (dirModel.getFdrKbn() == GSConstFile.DIRECTORY_FOLDER) {
                    directoryPath += sep;
                }
            } else {
                //ディレクトリパスに追加。（/を入れる）
                directoryPath = dirModel.getFdrName() + sep + directoryPath;
                fdrSid = dirModel.getFdrParentSid();
            }

            beforeParentSid.add(fdrSid);

        }

        directoryPath = cabinetName + sep + directoryPath;

        return directoryPath;
    }

    /**
     * <br>[機  能] キャビネットSIDよりキャビネット名を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cabinetSid キャビネットSID
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */

    public String getCabinetName(int cabinetSid) throws SQLException {

        return getCabinetName(cabinetSid, false);
    }

    /**
     * <br>[機  能] キャビネットSIDよりキャビネット名を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cabinetSid キャビネットSID
     * @param ignoreJkbnFlg true:論理削除キャビネットを含める, false:論理削除キャビネットを含めない
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */
    public String getCabinetName(
            int cabinetSid, boolean ignoreJkbnFlg) throws SQLException {

        String cabinetName = null;
        FileCabinetDao cabiDao = new FileCabinetDao(con__);

        FileCabinetModel cabiModel = cabiDao.select(cabinetSid);
        if (cabiModel != null
                && (ignoreJkbnFlg || cabiModel.getFcbJkbn() != GSConstFile.JTKBN_DELETE)) {
            if (cabiModel.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
                String userName = this.getUserName(cabiModel.getFcbOwnerSid());
                if (userName != null && !userName.isEmpty()) {
                    cabiModel.setFcbName(userName);
                }
            }
            cabinetName = cabiModel.getFcbName();
        }

        return cabinetName;
    }

    /**
     * <br>[機  能] Rootから指定ディレクトリSIDまでのディレクトリ情報一覧を取得する。
     * <br>[解  説] 並び順はRoot→カレント
     * <br>[備  考]
     * @param cabModel キャビネット情報
     * @param dirModel カレントディレクトリ情報
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<FileDirectoryModel> getRootToCurrentDirctoryList(FileCabinetModel cabModel,
            FileDirectoryModel dirModel)
    throws SQLException {

        ArrayList<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);

        if (cabModel == null || dirModel == null) {
            return ret;
        }

        ArrayList<Integer> beforeParentSid = new ArrayList<>();

        ret.add(dirModel);
        int parentSid = dirModel.getFdrParentSid();
        beforeParentSid.add(parentSid);

        while (parentSid > 0) {
            dirModel = dirDao.getNewDirectory(parentSid);
            if (dirModel == null) {
                break;
            }
            parentSid = dirModel.getFdrParentSid();
            //ディレクトリ構造にループが発生している場合は途中で切る
            if (beforeParentSid.indexOf(parentSid) != -1) {
                break;
            }
            ret.add(0, dirModel);
            beforeParentSid.add(parentSid);
        }
        // 最上位階層はキャビネット名へ変更
        ret.get(0).setFdrName(cabModel.getFcbName());

        return ret;
    }
    /**
     * <br>[機  能] ディレクトリSIDよりキャビネットSIDを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid ディレクトリSID
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */

    public int getCabinetSid(int fdrSid)
    throws SQLException {

        int cabinetSid = 0;
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);

        FileDirectoryModel dirModel = dirDao.getNewDirectory(fdrSid);
        if (dirModel != null) {
            cabinetSid = dirModel.getFcbSid();
        }

        return cabinetSid;
    }

    /**
     * <br>[機  能] ディレクトリSIDよりキャビネット情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid ディレクトリSID
     * @return FileCabinetModel
     * @throws SQLException SQL実行時例外
     */

    public FileCabinetModel getCabinetModel(int fdrSid) throws SQLException {

        return getCabinetModel(fdrSid, false);
    }

    /**
     * <br>[機  能] ディレクトリSIDよりキャビネット情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid ディレクトリSID
     * @param ignoreJkbnFlg true:論理削除キャビネットを含める, false:論理削除キャビネットを含めない
     * @return FileCabinetModel
     * @throws SQLException SQL実行時例外
     */

    public FileCabinetModel getCabinetModel(
            int fdrSid, boolean ignoreJkbnFlg) throws SQLException {

        int cabinetSid = getCabinetSid(fdrSid);

        FileCabinetDao dao = new FileCabinetDao(con__);
        FileCabinetModel model = dao.select(cabinetSid);
        if (model == null || (!ignoreJkbnFlg && model.getFcbJkbn() == GSConstFile.JTKBN_DELETE)) {
            return null;
        }

        return model;
    }

    /**
     * <br>[機  能] フォルダ名を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param directorySid ディレクトリSID
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */

    public String getDirctoryName(int directorySid)
    throws SQLException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel dirModel = dirDao.getNewDirectory(directorySid);

        if (dirModel == null) {

            return "";
        }

        return dirModel.getFdrName();
    }

    /**
     * <br>[機  能] フォルダ名を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param directorySid ディレクトリSID
     * @param version バージョン
     * @return List in LabelValueBean
     * @throws SQLException SQL実行時例外
     */

    public String getDirctoryName(int directorySid, int version)
    throws SQLException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel dirModel = dirDao.select(directorySid, version);

        if (dirModel == null) {

            return "";
        }

        return dirModel.getFdrName();
    }

    /**
     * <br>[機  能] 親ディレクトリSIDを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param directorySid ディレクトリSID
     * @return parentDirSId 親ディレクトリSID
     * @throws SQLException SQL実行時例外
     */

    public int getParentDirSid(int directorySid)
    throws SQLException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel dirModel = dirDao.getNewDirectory(directorySid);

        if (dirModel == null) {
            return -1;
        }

        return dirModel.getFdrParentSid();
    }

    /**
     * <br>[機  能] ディレクトリの最新バージョン+1の値を取得する。
     * <br>[解  説]　存在しない場合は-1を返します。
     * <br>[備  考]
     * @param directorySid ディレクトリSID
     * @return version 最新バージョン + 1
     * @throws SQLException SQL実行時例外
     */

    public int getNextVersion(int directorySid)
    throws SQLException {

        int version = getNewVersion(directorySid);

        if (version != -1) {
            version += 1;
        }

        return version;
    }

    /**
     * <br>[機  能] ディレクトリの最新バージョンの値を取得する。
     * <br>[解  説]　存在しない場合は-1を返します。
     * <br>[備  考]
     * @param directorySid ディレクトリSID
     * @return version 最新バージョン
     * @throws SQLException SQL実行時例外
     */

    public int getNewVersion(int directorySid)
    throws SQLException {

        int version = -1;

        FileDirectoryDao dao = new FileDirectoryDao(con__);
        FileDirectoryModel model = dao.getNewDirectory(directorySid);
        if (model != null) {
            version = model.getFdrVersion();
        }

        return version;
    }

    /**
     * <br>[機  能] ディレクトリの最新バージョンの履歴を取得する。
     * <br>[解  説]　存在しない場合は-1を返します。
     * <br>[備  考]
     * @param directorySid ディレクトリSID
     * @return version 最新バージョン
     * @throws SQLException SQL実行時例外
     */

    public int getNewVersionReki(int directorySid)
    throws SQLException {

        int version = -1;

        FileFileRekiDao dao = new FileFileRekiDao(con__);
        FileFileRekiModel model = dao.getNewDirectoryReki(directorySid);
        if (model != null) {
            version = model.getFdrVersion();
        }

        return version;
    }

    /**
     * <br>[機  能] バージョン管理区分を取得する。
     * <br>[解  説]　管理しない場合は0を返します。
     * <br>[備  考]
     * @param cabinetSid ディレクトリSID
     * @param directorySid ディレクトリSID
     * @return version 最新バージョン
     * @throws SQLException SQL実行時例外
     */

    public int getVerKbn(int cabinetSid, int directorySid) throws SQLException {

        return getVerKbn(cabinetSid, directorySid, false);
    }

    /**
     * <br>[機  能] バージョン管理区分を取得する。
     * <br>[解  説]　管理しない場合は0を返します。
     * <br>[備  考]
     * @param cabinetSid ディレクトリSID
     * @param directorySid ディレクトリSID
     * @param ignoreJkbnFlg true:論理削除キャビネットを含める, false:論理削除キャビネットを含めない
     * @return version 最新バージョン
     * @throws SQLException SQL実行時例外
     */
    public int getVerKbn(int cabinetSid,
            int directorySid, boolean ignoreJkbnFlg) throws SQLException {

        int verKbn = GSConstFile.VERSION_KBN_OFF;

        //管理者設定のバージョン管理区分
        if (getVerKbnAdmin() == GSConstFile.VERSION_KBN_OFF) {
            return verKbn;
        }

        //キャビネットのバージョン管理区分
        FileCabinetDao cabDao = new FileCabinetDao(con__);
        FileCabinetModel cabModel = cabDao.select(cabinetSid);
        if (cabModel == null
                || (!ignoreJkbnFlg && cabModel.getFcbJkbn() == GSConstFile.JTKBN_DELETE)) {
            return verKbn;
        }

        if (cabModel.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
            // 個人キャビネットの場合のみ管理者情報で更新
            cabModel.setFileAconf(getFileAconfModel());
        }

        verKbn = cabModel.getFcbVerKbn();

        if (cabModel.getFcbVerallKbn() == GSConstFile.VERSION_ALL_KBN_ON) {
            return cabModel.getFcbVerKbn();
        }

        FileDirectoryDao dao = new FileDirectoryDao(con__);
        FileDirectoryModel dirModel = dao.getNewDirectory(directorySid);
        if (dirModel != null) {
            verKbn = dirModel.getFdrVerKbn();
        }

        return verKbn;
    }

    /**
     * <br>[機  能] 更新通知を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param updateDirSid 更新ディレクトリSID
     * @param cntCon 採番コントロール
     * @param appRootPath APP ROOTパス
     * @param pconfig PluginConfig
     * @param smailPluginUseFlg ショートメール利用可能フラグ
     * @param sessionUsrSid ユーザSID
     * @throws Exception 実行時例外
     */
    public void updateCall(
            int updateDirSid,
            MlCountMtController cntCon,
            String appRootPath,
            PluginConfig pconfig,
            boolean smailPluginUseFlg,
            int sessionUsrSid)
    throws Exception {
        updateCall(
                updateDirSid,
                cntCon,
                appRootPath,
                pconfig,
                smailPluginUseFlg,
                sessionUsrSid,
                false);
    }

    /**
     * <br>[機  能] 更新通知を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param updateDirSid 更新ディレクトリSID
     * @param cntCon 採番コントロール
     * @param appRootPath APP ROOTパス
     * @param pconfig PluginConfig
     * @param smailPluginUseFlg ショートメール利用可能フラグ
     * @param sessionUsrSid ユーザSID
     * @param ignoreJkbnFlg 論理削除を確認しない
     * @throws Exception 実行時例外
     */
    public void updateCall(
            int updateDirSid,
            MlCountMtController cntCon,
            String appRootPath,
            PluginConfig pconfig,
            boolean smailPluginUseFlg,
            int sessionUsrSid,
            boolean ignoreJkbnFlg)
    throws Exception {

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileCallConfDao callConfDao = new FileCallConfDao(con__);

        //ディレクトリ情報を取得する。
        FileDirectoryModel dirModel = dirDao.getNewDirectory(updateDirSid);
        if (dirModel == null) {
            return;
        }

        //個人キャビネットおよびそのサブフォルダ内では更新通知を行わない
        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        int fcbSid = dirModel.getFcbSid();
        FileCabinetModel fcbMdl = fcbDao.select(fcbSid);
        if (fcbMdl == null) {
            return;
        }
        if (fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE && ignoreJkbnFlg == false) {
            return;
        }

        if (fcbMdl.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
            return;
        }

        //更新通知設定を取得する。
        List<FileCallConfModel> confList
                = callConfDao.getCallConf(dirModel.getFdrSid(), sessionUsrSid);

//      更新通知設定
        insertCallData(
                confList, dirModel, cntCon, appRootPath, pconfig, smailPluginUseFlg, ignoreJkbnFlg);
    }

    /**
     * <br>[機  能] 複数の更新通知情報を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param beanList 更新情報リスト
     * @param dirModel 更新ディレクトリ情報
     * @param cntCon 採番コントロール
     * @param appRootPath APP ROOTパス
     * @param pconfig PluginConfig
     * @param smailPluginUseFlg ショートメール利用可能フラグ
     * @throws Exception 実行時例外
     */
    public void insertCallData(
            List<FileCallConfModel> beanList,
            FileDirectoryModel dirModel,
            MlCountMtController cntCon,
            String appRootPath,
            PluginConfig pconfig,
            boolean smailPluginUseFlg
            ) throws Exception {
        insertCallData(beanList, dirModel, cntCon, appRootPath, pconfig, smailPluginUseFlg, false);
    }
    /**
     * <br>[機  能] 複数の更新通知情報を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param beanList 更新情報リスト
     * @param dirModel 更新ディレクトリ情報
     * @param cntCon 採番コントロール
     * @param appRootPath APP ROOTパス
     * @param pconfig PluginConfig
     * @param smailPluginUseFlg ショートメール利用可能フラグ
     * @param ignoreJkbnFlg 論理削除を確認しない
     * @throws Exception 実行時例外
     */
    public void insertCallData(
            List<FileCallConfModel> beanList,
            FileDirectoryModel dirModel,
            MlCountMtController cntCon,
            String appRootPath,
            PluginConfig pconfig,
            boolean smailPluginUseFlg,
            boolean ignoreJkbnFlg
            ) throws Exception {

        if (beanList == null || beanList.size() < 1) {
            return;
        }
        FileCallDataDao callDataDao = new FileCallDataDao(con__);

        for (FileCallConfModel mdl : beanList) {
            callDataDao.delete(dirModel.getFdrSid(), mdl.getUsrSid());
        }
        for (FileCallConfModel mdl : beanList) {
            callDataDao.insert(transCallDataModel(mdl, dirModel.getFdrSid()));
        }
//      更新通知ショートメール
        sendPlgSmail(
                cntCon,
                dirModel,
                appRootPath,
                pconfig,
                smailPluginUseFlg,
                getFolderSyousaiUrlString(dirModel),
                beanList, ignoreJkbnFlg);
    }

    /**
     * <br>[機  能] 更新通知設定モデルを更新確認情報モデルに変換する。
     * <br>[解  説]
     * <br>[備  考]
     * @param callConfModel 更新通知設定モデル
     * @param updateDirSid 更新ディレクトリSID
     * @return callDataModel 更新確認情報モデル
     * @throws SQLException SQL実行時例外
     */
    public FileCallDataModel transCallDataModel(FileCallConfModel callConfModel, int updateDirSid)
    throws SQLException {

        FileCallDataModel callDataModel = new FileCallDataModel();
        callDataModel.setFdrSid(updateDirSid);
        callDataModel.setUsrSid(callConfModel.getUsrSid());

        return callDataModel;
    }

    /**
     * <br>[機  能] ファイル管理の個人設定の初期値を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return uconfModel ファイル管理個人設定モデル
     * @throws SQLException SQL実行時例外
     */
    public FileUconfModel insertUserConf(int usrSid)
    throws SQLException {

        FileUconfDao dao = new FileUconfDao(con__);
        FileUconfModel model = new FileUconfModel();
        model.setUsrSid(usrSid);
        model.setFucMainCall(GSConstFile.MAIN_CALL_DSP_CNT);
        model.setFucMainOkini(GSConstFile.MAIN_OKINI_DSP_ON);
        model.setFucRirekiCnt(GSConstFile.RIREKI_COUNT_DEFAULT);
        model.setFucSmailSend(GSConstFile.SMAIL_SEND_OFF);
        dao.insert(model);
        return model;
    }

    /**
     * <br>[機  能] ファイル管理の個人設定を登録する。
     * <br>[解  説] 存在しない場合は初期値で登録する。
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return uconfModel ファイル管理個人設定モデル
     * @throws SQLException SQL実行時例外
     */
    public FileUconfModel getUserConf(int usrSid)
    throws SQLException {

        FileUconfDao uconfDao = new FileUconfDao(con__);
        FileUconfModel uconfModel = uconfDao.select(usrSid);

        if (uconfModel == null) {
            boolean commitFlg = false;
            try {
                //個人設定が無い場合は初期値で登録する。
                uconfModel = insertUserConf(usrSid);
                commitFlg = true;

            } catch (SQLException e) {
                log__.error("SQLException", e);
                throw e;
            } finally {
                if (commitFlg) {
                    con__.commit();
                } else {
                    JDBCUtil.rollback(con__);
                }
            }
        }

        return uconfModel;
    }

    /**
     * <br>[機  能] ファイルがダウンロード可能か判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリSID
     * @return true:ダウンロード可 false:ダウンロード不可
     * @throws SQLException 実行例外
     */
    public boolean isDownloadAuthUser(long binSid)
            throws SQLException {
        return isDownloadAuthUser(binSid, true, false);
    }
    /**
     * <br>[機  能] ファイルがダウンロード可能か判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリSID
     * @param isOnlyNewVersion 最新のみダウンロードを許可
     * @return true:ダウンロード可 false:ダウンロード不可
     * @throws SQLException 実行例外
     */
    public boolean isDownloadAuthUser(long binSid, boolean isOnlyNewVersion) throws SQLException {
        return isDownloadAuthUser(binSid, isOnlyNewVersion, false);
    }

    /**
     * <br>[機  能] ファイルがダウンロード可能か判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリSID
     * @param isOnlyNewVersion 最新のみダウンロードを許可
     * @param ignoreJkbnFlg true:論理削除を無視する
     * @return true:ダウンロード可 false:ダウンロード不可
     * @throws SQLException 実行例外
     */
    public boolean isDownloadAuthUser(long binSid,
            boolean isOnlyNewVersion,
            boolean ignoreJkbnFlg) throws SQLException {
        //キャビネットSIDを取得
        FileFileBinDao filBinDao = new FileFileBinDao(con__);
        int fcbSid = filBinDao.getCabinetSid(binSid, isOnlyNewVersion);

        //キャビネット情報が存在しない、または論理削除されている(電帳法キャビネットは除く)場合
        //ダウンロード不可
        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        FileCabinetModel fcbMdl = fcbDao.select(fcbSid);
        if (fcbMdl == null) {
            return false;
        }

        //キャビネットが論理削除されている場合
        if (fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            //電帳法キャビネット以外はエラー
            if (fcbMdl.getFcbErrl() != GSConstFile.ERRL_KBN_ON) {
                return false;
            }

            //論理削除を確認する場合はエラー
            if (ignoreJkbnFlg == false) {
                return false;
            }

            //管理者ユーザ以外はエラー
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser = cmnBiz.isPluginAdmin(con__, reqMdl__.getSmodel(),
                                                    GSConstFile.PLUGIN_ID_FILE);
            if (adminUser == false) {
                return false;
            }
        }

        //対象ファイルの論理削除チェック
        boolean checkDelete = true;
        if (ignoreJkbnFlg) {
            //電子帳簿保存法キャビネット以外のキャビネット内のファイルは論理削除チェックを強制
            checkDelete = fcbMdl.getFcbErrl() != GSConstFile.ERRL_KBN_ON;
        }

        if (isOnlyNewVersion
        && !filBinDao.existsBinFile(binSid, isOnlyNewVersion, checkDelete)) {
            return false;
        }


        //特権ユーザチェック
        BaseUserModel umodel = reqMdl__.getSmodel();
        if (isEditCabinetUser(fcbMdl, -1)) {
            return true;
        }

        //ファイル自身のアクセス権限チェック
        FileDAccessConfDao daConfDao = new FileDAccessConfDao(con__);
        return daConfDao.isAccessBinFile(binSid, umodel.getUsrsid(), isOnlyNewVersion);
    }

    /**
     * ユーザがディレクトリアクセス権限を持っているか判定します。
     * システム管理者、管理者権限を持つユーザは権限有り
     * @param fcbSid キャビネットSID
     * @param dirSid ディレクトリSID
     * @param auth 権限区分
     * @return true:権限有り false:権限無し
     * @throws SQLException 実行例外
     */
    public boolean isDirAccessAuthUser(
            int fcbSid, int dirSid, int auth)
            throws SQLException {
        return isDirAccessAuthUser(fcbSid, dirSid, auth, false);
    }
    /**
     * ユーザがディレクトリアクセス権限を持っているか判定します。
     * システム管理者、管理者権限を持つユーザは権限有り
     * @param fcbSid キャビネットSID
     * @param dirSid ディレクトリSID
     * @param auth 権限区分
     * @param ignoreJkbnFlg true:論理削除を無視する
     * @return true:権限有り false:権限無し
     * @throws SQLException 実行例外
     */
    public boolean isDirAccessAuthUser(
            int fcbSid, int dirSid, int auth,
            boolean ignoreJkbnFlg)
                    throws SQLException {

        if (auth == Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE)) {
            // 編集権限チェック
            // キャビネットの編集権限あり => 以下の全ての階層で権限あり
            return isEditCabinetUser(
                    fcbSid,
                    dirSid,
                    ignoreJkbnFlg);
        }

        // 閲覧権限チェック
        return isAccessAuthUser(fcbSid, dirSid, ignoreJkbnFlg);
    }

    /**
     * <p>指定したディレクトリのサブディレクトリに対してアクセス権限があるか判定する
     * @param dirSid ディレクトリSID
     * @param auth 権限区分
     * @param isSelf 自身をチェックするかの判定フラグ
     * @return true:いずれかのサブディレクトリに対してアクセス権限を持つ
     * @throws SQLException SQL実行例外
     * */
    public boolean isSubDirAccessUser(int dirSid, int auth, boolean isSelf)
        throws SQLException {

        FileDAccessConfDao dao = new FileDAccessConfDao(con__);
        if (isSelf) {
            //指定ディレクトリに対してアクセス権限がある場合はtrueを返す
            if (dao.isAccessUser(dirSid, reqMdl__.getSmodel().getUsrsid(), auth)) {
                return true;
            }
        }

        //指定ディレクトリのいずれかのサブディレクトリに対してアクセス権限がある場合、trueを返す
        List<FileAccessConfModel> subList = dao.getAccessSubDirectoriesFiles(dirSid, true);
        for (FileAccessConfModel sub : subList) {
            int subDirSid = sub.getFcbSid();
            if (dao.isAccessUser(subDirSid,  reqMdl__.getSmodel().getUsrsid(), auth)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>権限区分の指定がない場合に、
     *        指定したディレクトリのサブディレクトリに対してアクセス権限があるか判定する
     * @param dirSid ディレクトリSID
     * @return true:いずれかのサブディレクトリに対してアクセス権限を持つ
     * @throws SQLException SQL実行例外
     * */
    public boolean isSubDirAccessUser(int dirSid)
        throws SQLException {
        return isSubDirAccessUser(dirSid, true);
    }

    /**
     * <p>権限区分の指定がない場合に、
     *        指定したディレクトリのサブディレクトリに対してアクセス権限があるか判定する
     * @param dirSid ディレクトリSID
     * @param isSelf 自身をチェックするかの判定フラグ
     * @return true:いずれかのサブディレクトリに対してアクセス権限を持つ
     * @throws SQLException SQL実行例外
     * */
    public boolean isSubDirAccessUser(int dirSid, boolean isSelf)
            throws SQLException {
        int write = Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE);
        int read = Integer.parseInt(GSConstFile.ACCESS_KBN_READ);
        if (isSubDirAccessUser(dirSid, write, isSelf)
         || isSubDirAccessUser(dirSid, read, isSelf)) {
            return true;
        }
        return false;
    }

    /**
    *
    * <br>[機  能] 指定ディレクトリのサブディレクトリを全て取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param fdrSid ディレクトリSID
    * @param isChildOnly true:指定ディレクトリを親に持つ子のみ、false:指定ディレクトリと子の両方
    * @return List in FILE_DIRECTORY
    * @throws SQLException SQL実行例外
    */
   public List<FileDirectoryModel> getAllSubDirectoriesSid(
           int fdrSid, boolean  isChildOnly) throws SQLException {
       ArrayList<FileDirectoryModel> ret = new ArrayList<FileDirectoryModel>();
       FileDirectoryDao dDao = new FileDirectoryDao(con__);
       List<FileDirectoryModel> sub;
       if (isChildOnly) {
         sub = dDao.getLowDirectory(fdrSid, -1);
       } else {
         sub = new ArrayList<FileDirectoryModel>();
         FileDirectoryModel bean = dDao.select(fdrSid, -1);
         if (bean != null) {
             sub.add(bean);
         }
       }
       for (FileDirectoryModel bean : sub) {
           FilTreeBiz treeBiz = new FilTreeBiz(con__);
           FilChildTreeModel tree = treeBiz.getChildOfTarget(bean);
           for (FileDirectoryModel dir : tree.getListOfDir()) {
               ret.add(dir);
           }
           for (FileDirectoryModel dir : tree.getListOfFile()) {
               ret.add(dir);
           }
       }
       return ret;
   }

    /**
     * ユーザがディレクトリアクセス権限を持っているか判定します。
     * システム管理者、管理者権限を持つユーザは権限有り
     * @param fcbSid キャビネットSID
     * @param dirSid ディレクトリSID
     * @param auth 権限区分
     * @return true:権限有り false:権限無し
     * @throws SQLException 実行例外
     */
    public boolean isDirAccessAuthUser(
            int fcbSid, String[] dirSid, int auth)
                    throws SQLException {
        return isDirAccessAuthUser(fcbSid, dirSid, auth, false);
    }
    /**
     * ユーザがディレクトリアクセス権限を持っているか判定します。
     * システム管理者、管理者権限を持つユーザは権限有り
     * @param fcbSid キャビネットSID
     * @param dirSid ディレクトリSID
     * @param auth 権限区分
     * @param ignoreJkbnFlg true:論理削除を無視する
     * @return true:権限有り false:権限無し
     * @throws SQLException 実行例外
     */
    public boolean isDirAccessAuthUser(
            int fcbSid, String[] dirSid, int auth, boolean ignoreJkbnFlg
            ) throws SQLException {


        if (auth == Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE)) {
            // ディレクトリ編集権限チェック
            if (dirSid != null && dirSid.length > 0) {
                BaseUserModel umodel = reqMdl__.getSmodel();

                FileCabinetDao fcbDao = new FileCabinetDao(con__);
                FileCabinetModel fcbMdl = fcbDao.select(fcbSid);
                if (fcbMdl == null) {
                    return false; // キャビネット情報取得に失敗したのでエラー
                }
                //キャビネット論理削除チェック
                if (fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_OFF
                && fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
                    return false;
                }
                if (ignoreJkbnFlg == false
                && fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
                    return false;
                }

                if (fcbMdl.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
                    // 個人キャビネットの場合 → 編集権限は全ディレクトリで共通(オーナーのみ編集できる)
                    if (!isEditCabinetUser(fcbMdl, -1)) {
                        return false;
                    }
                    return true;
                } else {
                    // 共有キャビネットの場合 → ディレクトリ毎に編集権限チェック(管理者権限チェックは共通)
                    // --------------------------------------------------------------------------
                    //システム管理者判定
                    CommonBiz  commonBiz = new CommonBiz();
                    boolean adminUser = commonBiz.isPluginAdmin(con__, umodel,
                                                                GSConstFile.PLUGIN_ID_FILE);
                    if (adminUser) {
                        return true;
                    }
                    //キャビネット管理者権限
                    if (isCabinetAdminUser(fcbSid, reqMdl__.getSmodel().getUsrsid())) {
                        return true;
                    }

                    FileDAccessConfDao dao = new FileDAccessConfDao(con__);
                    for (int i = 0; i < dirSid.length; i++) {
                        // ディレクトリ指定時は編集権限ユーザかチェック
                        int sid = NullDefault.getInt(dirSid[i], -1);
                        if (sid < 0
                                || !dao.isAccessUser(
                                        sid,
                                        reqMdl__.getSmodel().getUsrsid(),
                                        auth)
                                ) {
                            return false;
                        }
                    }
                    // --------------------------------------------------------------------------
                }
                return true;
            }
        } else {
            // キャビネット閲覧権限チェック
            if (!isAccessAuthUser(fcbSid, -1, ignoreJkbnFlg)) {
                // キャビネット閲覧権限なし
                return false;
            }

            // ディレクトリ権限チェック
            if (dirSid != null && dirSid.length > 0) {
                FileCabinetDao fcbDao = new FileCabinetDao(con__);
                FileCabinetModel fcbMdl = fcbDao.select(fcbSid);
                if (fcbMdl != null
                 && fcbMdl.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
                    // 個人キャビネットディレクトリ権限チェック → アクセス権限を持つ子ディレクトリを持っているか
                    for (int i = 0; i < dirSid.length; i++) {
                        int sid = NullDefault.getInt(dirSid[i], -1);
                        if (sid < 0
                                && !isSubDirAccessUser(
                                        sid)
                                ) {
                            return false;
                        }
                    }
                } else {
                    // 共有キャビネットディレクトリ権限チェック
                    FileDAccessConfDao dao = new FileDAccessConfDao(con__);
                    for (int i = 0; i < dirSid.length; i++) {
                        int sid = NullDefault.getInt(dirSid[i], -1);
                        if (sid < 0
                                || !dao.isAccessUser(
                                        sid,
                                        reqMdl__.getSmodel().getUsrsid(),
                                        auth)
                                ) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * ユーザがキャビネット管理者権限を持っているか判定します。
     * システム管理者、管理者権限を持つユーザは権限有り
     * @param fcbSid キャビネットSID
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditCabinetUser(int fcbSid)
    throws SQLException {
        return this.isEditCabinetUser(fcbSid, -1, false);
    }

    /**
     * ユーザがキャビネット管理者権限を持っているか判定します。
     * システム管理者、管理者権限を持つユーザは権限有り
     * @param fcbSid キャビネットSID
     * @param dirSid ディレクトリSID
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditCabinetUser(int fcbSid, int dirSid)
    throws SQLException {
        return isEditCabinetUser(fcbSid, dirSid, false);
    }
    /**
     * ユーザがキャビネット管理者権限を持っているか判定します。
     * システム管理者、管理者権限を持つユーザは権限有り
     * @param  fcbSid キャビネットSID
     * @param dirSid ディレクトリSID
     * @param ignoreJkbnFlg 論理削除を確認しない
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditCabinetUser(int fcbSid,
            int dirSid,
            boolean ignoreJkbnFlg)
            throws SQLException {

        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        return this.isEditCabinetUser(fcbDao.select(fcbSid), dirSid, ignoreJkbnFlg);
    }

    /**
     * ユーザがキャビネット管理者権限を持っているか判定します。
     * システム管理者、管理者権限を持つユーザは権限有り
     * @param fcbMdl キャビネット情報
     * @param dirSid ディレクトリSID
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditCabinetUser(FileCabinetModel fcbMdl, int dirSid)
    throws SQLException {
        return isEditCabinetUser(fcbMdl, dirSid, false);
    }

    /**
     * ユーザがキャビネット管理者権限を持っているか判定します。
     * システム管理者、管理者権限を持つユーザは権限有り
     * @param fcbMdl キャビネット情報
     * @param dirSid ディレクトリSID
     * @param ignoreJkbnFlg 論理削除を確認しない
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditCabinetUser(FileCabinetModel fcbMdl, int dirSid,
            boolean ignoreJkbnFlg)
    throws SQLException {

        if (fcbMdl == null) {
            return false;
        }
        if (ignoreJkbnFlg == false && fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            return false;
        }

        BaseUserModel umodel = reqMdl__.getSmodel();

        int usrSid = umodel.getUsrsid();
        int fcbSid = fcbMdl.getFcbSid();

        if (dirSid >= 0) {
            //ディレクトリ指定時はディレクトリの存在チェック
            FileDirectoryDao dirDao = new FileDirectoryDao(con__);
            FileDirectoryModel dirModel = dirDao.getNewDirectory(dirSid);
            if (dirModel == null) {
                return false;
            }
            if (dirModel.getFcbSid() != fcbMdl.getFcbSid()) {
                return false;
            }
            if (ignoreJkbnFlg == false && dirModel.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
                return false;
            }

        }

        // 使用キャビネットが個人キャビネットの場合、使用許可チェック
        if (fcbMdl.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
            FileAconfDao aconfDao = new FileAconfDao(con__);
            FileAconfModel aconf = aconfDao.select();
            if (aconf == null || aconf.getFacPersonalKbn() != GSConstFile.CABINET_PRIVATE_USE) {
                return false; // 管理者設定で個人キャビネット使用不可
            }

            if (fcbMdl.getFcbOwnerSid() <= GSConst.SYSTEM_USER_MAIL) {
                return false; // キャビネット所有者が通常のユーザSID以外(システムなど)の場合は不正チェックの為に使用不可
            }

            // マイキャビネット使用許可設定
            if (aconf.getFacUseKbn() == GSConstFile.CABINET_AUTH_USER) {
                FilePcbOwnerDao pcbDao = new FilePcbOwnerDao(con__);
                if (!pcbDao.isCreateAuth(fcbMdl.getFcbOwnerSid())) {
                    return false; // キャビネット所有者にマイキャビネット使用許可がない
                }
            }

            if (fcbMdl.getFcbOwnerSid() == usrSid) {
                return true;  // 自分のマイキャビネットの場合は無条件で許可
            }
        } else {


            //システム管理者判定
            CommonBiz  commonBiz = new CommonBiz();
            boolean adminUser = commonBiz.isPluginAdmin(con__, umodel, GSConstFile.PLUGIN_ID_FILE);
            if (adminUser) {
                return true;
            }
            //キャビネット管理者権限
            if (isCabinetAdminUser(fcbSid, usrSid)) {
                return true;
            }

            if (dirSid >= 0) {

                // ディレクトリ指定時は編集権限ユーザかチェック
                FileDAccessConfDao dao = new FileDAccessConfDao(con__);
                if (dao.isAccessUser(dirSid, usrSid,
                                     Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * ユーザがキャビネットへのアクセス権限を持っているか判定します。
     * 閲覧権限があればtrue
     * @param fcbSid キャビネットSID
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isAccessAuthUser(int fcbSid)
    throws SQLException {
        return this.isAccessAuthUser(fcbSid, -1);
    }

    /**
     * ユーザがキャビネットへのアクセス権限を持っているか判定します。
     * 閲覧権限があればtrue
     * @param fcbSid キャビネットSID
     * @param dirSid ディレクトリSID
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isAccessAuthUser(int fcbSid, int dirSid)
    throws SQLException {
        return isAccessAuthUser(fcbSid, -1, false);
    }
    /**
     * ユーザがキャビネットへのアクセス権限を持っているか判定します。
     * 閲覧権限があればtrue
     * @param fcbSid キャビネットSID
     * @param dirSid ディレクトリSID
     * @param ignoreJkbnFlg true:論理削除を無視する
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isAccessAuthUser(int fcbSid,
            int dirSid,
            boolean ignoreJkbnFlg) throws SQLException {

        if (fcbSid <= 0) {
            return false;
        }

        //アクセス制御をするキャビネットか判定
        FileCabinetDao cabDao = new FileCabinetDao(con__);
        return isAccessAuthUser(cabDao.select(fcbSid), dirSid, ignoreJkbnFlg);
    }

    /**
     * ユーザがキャビネットへのアクセス権限を持っているか判定します。
     * 閲覧権限があればtrue
     * @param cabMdl キャビネット情報
     * @param dirSid ディレクトリSID
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isAccessAuthUser(FileCabinetModel cabMdl,
            int dirSid)
    throws SQLException {
        return isAccessAuthUser(cabMdl, dirSid, false);
    }
    /**
     * ユーザがキャビネットへのアクセス権限を持っているか判定します。
     * 閲覧権限があればtrue
     * @param cabMdl キャビネット情報
     * @param dirSid ディレクトリSID
     * @param ignoreJkbnFlg true:論理削除を無視する
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isAccessAuthUser(FileCabinetModel cabMdl,
            int dirSid,
            boolean ignoreJkbnFlg)
    throws SQLException {


        if (cabMdl == null) {
            return false;
        }
        //キャビネット論理削除チェック
        if (cabMdl.getFcbErrl() == GSConstFile.ERRL_KBN_OFF
        && cabMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            return false;
        }
        if (ignoreJkbnFlg == false
        && cabMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            return false;
        }

        BaseUserModel umodel = reqMdl__.getSmodel();
        //システム管理者判定
        CommonBiz  commonBiz = new CommonBiz();
        boolean adminUser = commonBiz.isPluginAdmin(con__, umodel, GSConstFile.PLUGIN_ID_FILE);

        //論理削除済みキャビネット、フォルダをシステム管理者以外に表示できる画面はない
        if (adminUser == false
                && cabMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE
                ) {
            return false;
        }

        if (dirSid >= 0) {
            //ディレクトリ指定時はディレクトリの存在チェック
            FileDirectoryDao dirDao = new FileDirectoryDao(con__);
            FileDirectoryModel dirModel = dirDao.getNewDirectory(dirSid);
            if (dirModel == null) {
                return false;
            }
            if (dirModel.getFcbSid() != cabMdl.getFcbSid()) {
                return false;
            }
            if (cabMdl.getFcbErrl() == GSConstFile.ERRL_KBN_OFF
                    && dirModel.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
                return false;
            }

            if (ignoreJkbnFlg == false && dirModel.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
                return false;
            }
            //論理削除済みキャビネット、フォルダをシステム管理者以外に表示できる画面はない
            if (adminUser == false
                    && dirModel.getFdrJtkbn() == GSConstFile.JTKBN_DELETE
                    ) {
                return false;
            }


        }

        if (adminUser) {
            return true;
        }


        //個人キャビネット内の場合
        if (cabMdl != null
         && cabMdl.getFcbPersonalFlg() == Integer.parseInt(GSConstFile.DSP_CABINET_PRIVATE)) {
            //個人キャビネットを使用する管理者設定になっているか
            FileAconfModel aconfMdl = getFileAconfModel();
            if (aconfMdl == null
             || aconfMdl.getFacPersonalKbn() != GSConstFile.CABINET_PRIVATE_USE) {
                return false;
            }

            //マイキャビネット使用許可判定
            if (aconfMdl.getFacUseKbn() == GSConstFile.CABINET_AUTH_USER) {
                //キャビネットの所有者が、個人キャビネットの使用を許可されているか
                FilePcbOwnerDao fpoDao = new FilePcbOwnerDao(con__);
                List<Integer>authList = fpoDao.getUserSidList();
                if (!authList.contains(cabMdl.getFcbOwnerSid())) {
                    return false; // 許可されていない場合はアクセス不可
                }
            }

            // 自分のマイキャビネットの場合
            if (cabMdl.getFcbOwnerSid() == umodel.getUsrsid()) {
                return true;
            }

            if (dirSid < 0) {
                // 個人キャビネットの閲覧権限チェック
                if (isCanPrivateCabinet(cabMdl, umodel.getUsrsid(), false)) {
                    return true;
                }
            } else {
                // ディレクトリが指定されている場合 → アクセス権限を持つ子ディレクトリを持っているかチェック
                if (isSubDirAccessUser(dirSid, true)) {
                    return true;
                }
            }

        } else {

            int fcbSid = cabMdl.getFcbSid();
            //キャビネット管理者権限(共有キャビネットのみ)
            if (isCabinetAdminUser(fcbSid, umodel.getUsrsid())) {
                return true;
            }
            if (dirSid > 0) {
                FileDAccessConfDao dao = new FileDAccessConfDao(con__);
                if (dao.isAccessUser(dirSid, umodel.getUsrsid(), -1)) {
                    return true;
                }
            } else {
                if (cabMdl.getFcbAccessKbn() == GSConstFile.ACCESS_KBN_OFF) {
                    return true;
                }
                //アクセス制御から判定
                FileAccessConfDao accDao = new FileAccessConfDao(con__);
                if (accDao.isAccessUserGroup(fcbSid, umodel.getUsrsid(), -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * キャビネットのRootディレクトリSIDを取得する
     * @param bean FileCabinetDspModel
     * @return RootディレクトリSID
     * @throws SQLException SQL実行時例外
     */
    public int getRootDirSid(FileCabinetModel bean)
    throws SQLException {
        FileDirectoryDao dao = new FileDirectoryDao(con__);
        FileDirectoryModel mdl = dao.getRootDirectory(bean.getFcbSid());

        return mdl.getFdrSid();
    }

    /**
     * ユーザがファイルロック解除が可能か判定します。
     * システム管理者、管理者権限を持つユーザは権限有り
     * @param fcbSid キャビネットSID
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isCanFileUnlockUser(int fcbSid)
    throws SQLException {
        return isCanFileUnlockUser(fcbSid, false);
    }
    /**
     * ユーザがファイルロック解除が可能か判定します。
     * システム管理者、管理者権限を持つユーザは権限有り
     * @param fcbSid キャビネットSID
     * @param ignoreJkbnFlg 論理削除を確認しない
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isCanFileUnlockUser(int fcbSid, boolean ignoreJkbnFlg)
    throws SQLException {

        if (isEditCabinetUser(fcbSid, -1, ignoreJkbnFlg)) {
            //特権ユーザ
            return true;
        }

        return false;
    }

    /**
     * ユーザがキャビネット編集権限を持っているか判定します。
     * システム管理者、作成権限、管理者権限を持つユーザは権限有り
     * @param fcbSid キャビネットSID
     * @param usrSid ユーザSID
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isCabinetAdminUser(int fcbSid, int usrSid)
    throws SQLException {
        FileCabinetAdminDao dao = new FileCabinetAdminDao(con__);
        FileCabinetAdminModel mdl = dao.select(fcbSid, usrSid);
        if (mdl == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * ユーザがキャビネット作成権限を持っているか判定します。
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isCanCreateCabinetUser()
    throws SQLException {

        if (!isCanCreateCabinetAdmin()) {
            //管理者設定のキャビネット作成区分権限設定なし
            return true;
        }

        CommonBiz  commonBiz = new CommonBiz();
        boolean adminUser = commonBiz.isPluginAdmin(
                con__,
                reqMdl__.getSmodel(),
                GSConstFile.PLUGIN_ID_FILE);
        if (adminUser) {
            return true;
        }

        return __isCanCreateCabinetUser();
    }

    /**
     * ユーザがキャビネット作成権限を持っているか判定します。
     * @param cabinetKbn 作成するキャビネットの区分 0:共有キャビネット 1:個人キャビネット
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isCanCreateCabinetUser(String cabinetKbn)
    throws SQLException {

        BaseUserModel umodel = reqMdl__.getSmodel();
        CommonBiz  commonBiz = new CommonBiz();
        boolean adminUser = commonBiz.isPluginAdmin(con__, umodel, GSConstFile.PLUGIN_ID_FILE);
        if (adminUser) {
            return true; // 管理者権限あり
        }

        //マイキャビネット作成時
        if (cabinetKbn.equals(GSConstFile.DSP_CABINET_PRIVATE)) {

            FileAconfDao facDao = new FileAconfDao(con__);
            FileAconfModel facMdl = facDao.select();
            // 個人キャビネット使用許可設定
            if (facMdl.getFacPersonalKbn() == GSConstFile.CABINET_PRIVATE_USE) {
                if (facMdl.getFacUseKbn() == GSConstFile.CABINET_AUTH_ALL) {
                    //全ユーザに対してマイキャビネットの作成が許可されている場合
                    return true;
                }
                FilePcbOwnerDao fpoDao = new FilePcbOwnerDao(con__);
                List<Integer> ownerList = fpoDao.getUserSidList();
                if (ownerList.contains(umodel.getUsrsid())) {
                    //セッションユーザがマイキャビネットの作成を許可されている場合
                    return true;
                }
            }
            return false;
        } else {
            if (!isCanCreateCabinetAdmin()) {
                //管理者設定のキャビネット作成区分権限設定なし
                return true;
            }
        }

        return __isCanCreateCabinetUser();
    }

    /**
     * 管理者設定でキャビネット作成権限が設定されているか判定します。
     * @return true:権限設定あり false:権限設定なし
     * @throws SQLException SQL実行時例外
     */
    public boolean isCanCreateCabinetAdmin()
    throws SQLException {

        //管理者設定のキャビネット作成区分を取得
        FileAconfDao aconfDao = new FileAconfDao(con__);
        FileAconfModel aconfModel =  aconfDao.select();
        if (aconfModel != null) {
            if (aconfModel.getFacCrtKbn() == GSConstFile.CREATE_CABINET_PERMIT_NO) {
                return false;
            }
        }

        return true;
    }

    /**
     * ユーザがファイルの閲覧権限を持っているか判定します。
     * 閲覧権限があればtrue
     * @param binSid バイナリSID
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean canAccessFile(long binSid)
    throws SQLException {

        //キャビネットSIDを取得
        FileFileBinDao fileBinDao = new FileFileBinDao(con__);
        int fcbSid = fileBinDao.getCabinetSid(binSid);

        return this.isAccessAuthUser(fcbSid);
    }

    /**
     * <br>[機  能] 登録に使用する側のコンボで選択中のメンバーをメンバーリストから削除する
     *
     * <br>[解  説] 登録に使用する側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で削除矢印ボタンをクリックした場合、
     *              登録に使用する側のコンボで選択中の値(deleteSelectSid)をメンバーリストから削除する。
     *
     * <br>[備  考] 登録に使用する側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param deleteSelectSid 登録に使用する側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 削除済みのメンバーリスト
     */
    public String[] getDeleteMember(String[] deleteSelectSid, String[] memberSid) {

        if (deleteSelectSid == null) {
            return memberSid;
        }
        if (deleteSelectSid.length < 1) {
            return memberSid;
        }

        if (memberSid == null) {
            memberSid = new String[0];
        }

        //削除後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < memberSid.length; i++) {
            boolean setFlg = true;

            for (int j = 0; j < deleteSelectSid.length; j++) {
                if (memberSid[i].equals(deleteSelectSid[j])) {
                    setFlg = false;
                    break;
                }
            }

            if (setFlg) {
                list.add(memberSid[i]);
            }
        }

        log__.debug("削除後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * <br>[機  能] 追加側のコンボで選択中のメンバーをメンバーリストに追加する
     *
     * <br>[解  説] 画面右側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で追加矢印ボタンをクリックした場合、
     *              追加側のコンボで選択中の値(addSelectSid)をメンバーリストに追加する。
     *
     * <br>[備  考] 追加側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param addSelectSid 追加側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 追加済みのメンバーリスト
     */
    public String[] getAddMember(String[] addSelectSid, String[] memberSid) {

        if (addSelectSid == null) {
            return memberSid;
        }
        if (addSelectSid.length < 1) {
            return memberSid;
        }


        //追加後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        if (memberSid != null) {
            for (int j = 0; j < memberSid.length; j++) {
                if (!memberSid[j].equals("-1")) {
                    list.add(memberSid[j]);
                }
            }
        }

        for (int i = 0; i < addSelectSid.length; i++) {
            if (!addSelectSid[i].equals("-1")) {
                list.add(addSelectSid[i]);
            }
        }

        log__.debug("追加後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }


    /**
     * ユーザがキャビネット作成権限を持っているか判定します。
     * @return true:権限有り false:権限無し
     * @throws SQLException SQL実行時例外
     */
    private boolean __isCanCreateCabinetUser() throws SQLException {
        FileCrtConfDao dao = new FileCrtConfDao(con__);
        return dao.isCreateAuth(reqMdl__.getSmodel().getUsrsid());
    }

    /**
     * <br>[機  能] 添付ファイルをテンポラリディレクトリへ設定する
     * <br>[解  説] ファイル添付情報を取得する。
     * <br>[備  考]
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param fcbSid キャビネットSID
     * @return saveName
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public String setCabinetTempFile(
        String appRoot,
        String tempDir,
        int fcbSid)
        throws SQLException, IOToolsException, IOException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();

        String saveName = "";
        String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)

        FileCabinetBinDao cabinetBinDao = new FileCabinetBinDao(con__);
        //ファイル管理の添付ファイル情報を取得する
        String[] binSids = cabinetBinDao.getBinList(fcbSid);
        if (binSids == null || binSids.length < 1) {
            return saveName;
        }
        //添付ファイル情報を取得する
        List<CmnBinfModel> cmBinList = cmnBiz.getBinInfo(con__, binSids,
                reqMdl__.getDomain());

        int fileNum = 1;
        for (CmnBinfModel cbMdl : cmBinList) {

            //添付ファイルをテンポラリディレクトリにコピーする。
            cmnBiz.saveTempFile(dateStr, cbMdl, appRoot, tempDir, fileNum);

            fileNum++;
        }
        return saveName;
    }

    /**
     * <br>[機  能] 添付ファイルをテンポラリディレクトリへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param dirSid ディレクトリSID
     * @param version バージョン
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setFolderTempFile(
        String appRoot,
        String tempDir,
        int dirSid,
        int version)
        throws SQLException, IOToolsException, IOException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();

        String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)

        //添付ファイル情報を取得する
        FileDirectoryBinDao dirBinDao = new FileDirectoryBinDao(con__);
        List <Long> binSidList = dirBinDao.getBinSidList(dirSid, version);
        String[] binSids = new String[binSidList.size()];
        int i = 0;
        for (Long sid : binSidList) {
            binSids[i] = String.valueOf(sid);
            i++;
        }
        if (binSids == null || binSids.length < 1) {
            return;
        }
        List <CmnBinfModel> cmnBinList = cmnBiz.getBinInfo(con__, binSids,
                reqMdl__.getDomain());
        int fileNum = 1;
        for (CmnBinfModel cbMdl : cmnBinList) {

            //添付ファイルをテンポラリディレクトリにコピーする。
            cmnBiz.saveTempFile(dateStr, cbMdl, appRoot, tempDir, fileNum);
            fileNum++;
        }
    }

    /**
     * <br>[機  能] 添付ファイルをテンポラリディレクトリへ設定する
     * <br>[解  説] ファイル添付情報を取得する。
     * <br>[備  考]
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param dirSid ディレクトリSID
     * @param version バージョン
     * @return saveName
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public String setTempFile(
        String appRoot,
        String tempDir,
        int dirSid,
        int version)
        throws SQLException, IOToolsException, IOException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();

        String saveName = "";
        String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)

        //添付ファイル情報を取得する
        FileFileBinDao fileBinDao = new FileFileBinDao(con__);
        List<Long> binSidList = fileBinDao.getBinSidList(dirSid, version);
        if (binSidList == null || binSidList.size() < 1) {
            return saveName;
        }
        String[] binSids = new String[binSidList.size()];
        int i = 0;
        for (Long sid : binSidList) {
            binSids[i] = String.valueOf(sid);
            i++;
        }

        List<CmnBinfModel> cmBinList = cmnBiz.getBinInfo(con__, binSids,
                reqMdl__.getDomain());

        int fileNum = 1;
        for (CmnBinfModel cbMdl : cmBinList) {

            //添付ファイルをテンポラリディレクトリにコピーする。
            cmnBiz.saveTempFile(dateStr, cbMdl, appRoot, tempDir, fileNum);

            fileNum++;
        }
        return saveName;
    }

    /**
     * <br>[機  能] 指定したバイナリSIDのファイルをコピーする。
     * <br>[解  説] ファイル添付情報を取得する。
     * <br>[備  考]
     * @param appRoot アプリケーションのルートパス
     * @param binSid バイナリSID
     * @param cntCon MlCountMtController
     * @return newBinSid 採番バイナリSID
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public Long copyFile(
        String appRoot,
        Long binSid,
        MlCountMtController cntCon
        ) throws TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        Long newBinSid = cmnBiz.copyfile(appRoot,
                binSid,
                reqMdl__.getSmodel().getUsrsid(),
                con__,
                cntCon,
                reqMdl__.getDomain());

        return newBinSid;
    }

    /**
     * <br>[機  能] テンポラリディレクトリにあるファイルの合計ファイルサイズを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return fileSizeSum 合計ファイルサイズ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public BigDecimal getSumTempFileSize(String tempDir)
    throws SQLException, IOToolsException {

        //テンポラリディレクトリにあるファイル名称を取得
        List < String > fileList = IOTools.getFileNames(tempDir);

        //ファイルのリストを作成
        BigDecimal fileSizeSum = new BigDecimal(0);

        if (fileList != null) {

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);

                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                String name = fileName.replaceFirst(
                        GSConstCommon.ENDSTR_OBJFILE, GSConstCommon.ENDSTR_SAVEFILE);
                File file = new File(tempDir, name);
                long atattiSize = file.length();
                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                BigDecimal fileSize = BigDecimal.valueOf(atattiSize);
                fileSizeSum = fileSizeSum.add(fileSize);
            }
        }
        return fileSizeSum;
    }

    /**
     * <br>[機  能] バージョン管理コンボを取得する
     * <br>[解  説] ファイル添付情報を取得する。
     * <br>[備  考]
     * @return verKbnLabel バージョンラベルリスト
     */
    public List<LabelValueBean> getVerKbnLabelList() {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textSinai = gsMsg.getMessage("fil.65");

        List<LabelValueBean> verKbnLabel = new ArrayList<LabelValueBean>();
        verKbnLabel.add(new LabelValueBean(textSinai, "0"));
        for (int i = 1; i < 11; i++) {
            verKbnLabel.add(new LabelValueBean(
                    gsMsg.getMessage(
                            "fil.generations",
                            new String[] {String.valueOf(i)}), String.valueOf(i)));
        }

        return verKbnLabel;
    }

    /**
     * <br>[機  能] ファイルロック区分を更新する。
     * <br>[解  説] DBコミットする。
     * <br>[備  考]
     * @param dirSid ディレクトリSID
     * @param version バージョン
     * @param lockKbn ロック区分
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void updateLockKbnCommit(
            int dirSid, int version, int lockKbn, int usrSid)
    throws SQLException {

        boolean commitFlg = false;

        try {
            //ロックを更新する。
            FileFileBinDao fileBinDao = new FileFileBinDao(con__);
            FileFileBinModel fileBinModel = new FileFileBinModel();

            UDate now = new UDate();
            fileBinModel.setFdrSid(dirSid);
            fileBinModel.setFdrVersion(version);
            fileBinModel.setFflLockKbn(lockKbn);
            fileBinModel.setFflLockUser(usrSid);
            fileBinModel.setFflLockDate(now);
            fileBinDao.updateLockKbn(fileBinModel);

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] ファイルロック区分を更新する。
     * <br>[解  説] DBコミットする。
     * <br>[備  考]
     * @param dirSids ディレクトリSIDリスト
     * @param lockKbn ロック区分
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void updateLockKbnCommit(
            String[] dirSids, int lockKbn, int usrSid)
    throws SQLException {

        boolean commitFlg = false;

        try {
            //ロックを更新する。
            FileFileBinDao fileBinDao = new FileFileBinDao(con__);
            FileFileBinModel fileBinModel = new FileFileBinModel();
            UDate now = new UDate();
            fileBinModel.setFflLockKbn(lockKbn);
            fileBinModel.setFflLockUser(usrSid);
            fileBinModel.setFflLockDate(now);

            fileBinDao.updateLockKbn(fileBinModel, dirSids);

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] ファイルロック区分を更新する。
     * <br>[解  説]　DBコミットしない。
     * <br>[備  考]
     * @param dirSid ディレクトリSID
     * @param version バージョン
     * @param lockKbn ロック区分
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void updateLockKbn(int dirSid, int version, int lockKbn, int usrSid)
    throws SQLException {

        //ロックを更新する。
        FileFileBinDao fileBinDao = new FileFileBinDao(con__);
        FileFileBinModel fileBinModel = new FileFileBinModel();
        UDate now = new UDate();
        fileBinModel.setFdrSid(dirSid);
        fileBinModel.setFdrVersion(version);
        fileBinModel.setFflLockKbn(lockKbn);
        fileBinModel.setFflLockUser(usrSid);
        fileBinModel.setFflLockDate(now);
        fileBinDao.updateLockKbn(fileBinModel);

    }

    /**
     * <br>[機  能] ファイル編集可能か判定する。
     * <br>[解  説] ファイルロックを行う。すでにロックされている場合には、ユーザチェックを行う。
     * <br>[備  考]
     * @param dirSid ディレクトリSID
     * @return lock true:編集可能 false:編集不可能
     * @throws SQLException SQL実行例外
     */
    public boolean checkFileLock(int dirSid)
    throws SQLException {

        FileFileBinDao fileBinDao = new FileFileBinDao(con__);
        FileFileBinModel fileBinModel = fileBinDao.getNewFile(dirSid);

        if (fileBinModel == null) {
            return false;
        }

        if (fileBinModel.getFflLockKbn() == GSConstFile.LOCK_KBN_ON
                && fileBinModel.getFflLockUser() != reqMdl__.getSmodel().getUsrsid()) {
            //編集ユーザがログインユーザと異なった場合
            return false;
        }
        return true;
    }


    /**
     * ファイル管理個人設定を取得します。存在しない場合は初期値で作成します
     * @param usrSid ユーザSID
     * @return 個人設定
     * @throws SQLException SQL実行時例外
     */
    public FileUconfModel getFileUconfModel(int usrSid)
    throws SQLException {

        FileUconfDao udao = new FileUconfDao(con__);
        FileUconfModel uConfMdl = udao.select(usrSid);
        if (uConfMdl == null) {
            boolean commitFlg = false;
            try {
                uConfMdl = new FileUconfModel();
                uConfMdl.init(usrSid);
                udao.insert(uConfMdl);
                commitFlg = true;
            } catch (SQLException e) {
                log__.error("SQLException", e);
                throw e;
            } finally {
                if (commitFlg) {
                    con__.commit();
                } else {
                    JDBCUtil.rollback(con__);
                }
            }
            return uConfMdl;
        } else {
            return uConfMdl;
        }
    }
    /**
     * ファイル管理 管理者設定を取得します。存在しない場合は初期値で作成します
     * @return 管理者設定
     * @throws SQLException SQL実行時例外
     */
    public FileAconfModel getFileAconfModel()
    throws SQLException {

        CmnFileConfDao cfcDao = new CmnFileConfDao(con__);
        FileAconfDao aconfDao = new FileAconfDao(con__);
        FileAconfModel aconfMdl = aconfDao.select();
        if (aconfMdl == null) {
            boolean commitFlg = false;
            try {
                aconfMdl = new FileAconfModel();
                aconfMdl.init();
                CmnFileConfModel cfcMdl = cfcDao.select();
                if (cfcMdl != null) {
                    aconfMdl.setFacFileSize(cfcMdl.getFicMaxSize());
                }
                aconfDao.insert(aconfMdl);
                commitFlg = true;
            } catch (SQLException e) {
                log__.error("SQLException", e);
                throw e;
            } finally {
                if (commitFlg) {
                    con__.commit();
                } else {
                    JDBCUtil.rollback(con__);
                }
            }
            return aconfMdl;
        } else {
            return aconfMdl;
        }
    }
    /**
     * <br>管理者設定のロック機能区分を取得します。
     * @return lockKbn ロック機能区分（管理者設定）
     * @throws SQLException SQL実行時例外
     */
    public int getLockKbnAdmin() throws SQLException {

        FileAconfDao aconfDao = new FileAconfDao(con__);
        FileAconfModel aconfMdl = aconfDao.select();

        if (aconfMdl == null) {
            return GSConstFile.LOCK_KBN_ON;
        }

        return aconfMdl.getFacLockKbn();
    }

    /**
     * <br>管理者設定のバージョン管理区分を取得します。
     * @return lockKbn ロック機能区分（管理者設定）
     * @throws SQLException SQL実行時例外
     */
    public int getVerKbnAdmin() throws SQLException {

        FileAconfDao aconfDao = new FileAconfDao(con__);
        FileAconfModel aconfMdl = aconfDao.select();

        if (aconfMdl == null) {
            return GSConstFile.VERSION_KBN_OFF;
        }

        return aconfMdl.getFacVerKbn();
    }


    /**
     * <br>管理者設定の削除したファイルの保存期間を取得します。
     * @return saveDays 削除ファイル保存期間
     * @throws SQLException SQL実行時例外
     */
    public int getDelFileSaveDays()
    throws SQLException {

        FileAconfDao aconfDao = new FileAconfDao(con__);
        FileAconfModel aconfMdl = aconfDao.select();

        if (aconfMdl == null) {
            return 0;
        }

        return aconfMdl.getFacSaveDays();
    }
    /**
     * <br>[機  能] ショートメールプラグインで更新通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param cntCon MlCountMtController
     * @param dirModel ディレクトリ情報
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param url ファイル管理URL
     * @param beanList 通知先リスト
     * @throws Exception 実行例外
     */
    public void sendPlgSmail(
        MlCountMtController cntCon,
        FileDirectoryModel dirModel,
        String appRootPath,
        PluginConfig pluginConfig,
        boolean smailPluginUseFlg,
        String url,
        List<FileCallConfModel> beanList) throws Exception {
        sendPlgSmail(cntCon,
                dirModel,
                appRootPath,
                pluginConfig,
                smailPluginUseFlg,
                url,
                beanList,
                false);
    }
    /**
     * <br>[機  能] ショートメールプラグインで更新通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param cntCon MlCountMtController
     * @param dirModel ディレクトリ情報
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param url ファイル管理URL
     * @param beanList 通知先リスト
     * @param ignoreJkbnFlg 論理削除を確認しない
     * @throws Exception 実行例外
     */
    public void sendPlgSmail(
        MlCountMtController cntCon,
        FileDirectoryModel dirModel,
        String appRootPath,
        PluginConfig pluginConfig,
        boolean smailPluginUseFlg,
        String url,
        List<FileCallConfModel> beanList,
        boolean ignoreJkbnFlg
        ) throws Exception {

        if (!smailPluginUseFlg) {
            //ショートメールプラグインが無効の場合、ショートメールを送信しない。
            return;
        }
        CmnUsrmInfDao udao = new CmnUsrmInfDao(con__);
        CmnUsrmInfModel umodel = null;

        //管理者設定を取得
        FileAconfModel aconfMdl = getFileAconfModel();

        // 更新者
        int userSid = dirModel.getFdrEuid();
        int gpSid = dirModel.getFdrEgid();
        List<Integer> sidList = new ArrayList<Integer>();
        for (FileCallConfModel mdl : beanList) {
            //管理者設定ショートメール通知 「管理者が設定する」の場合
            if (aconfMdl.getFacSmailSendKbn() == GSConstFile.FAC_SMAIL_SEND_KBN_ADMIN) {
                //管理者設定ショートメール通知 「通知する」の場合
                if (aconfMdl.getFacSmailSend() == GSConstFile.FAC_SMAIL_SEND_YES) {
                    sidList.add(mdl.getUsrSid());
                }

            //管理者設定ショートメール通知 「各ユーザが設定する」の場合
            } else {
                //個人設定ショートメール通知 「通知する」の場合
                if (mdl.getFucSmailSend() == GSConstFile.SMAIL_SEND_ON) {
                    sidList.add(mdl.getUsrSid());
                }
            }
        }

        try {

            //更新日付
            String udate = UDateUtil.getSlashYYMD(dirModel.getFdrEdate())
            + " "
            + UDateUtil.getSeparateHMS(dirModel.getFdrEdate());

            //登録ユーザ名
            String editUserName = "";
            if (gpSid > 0) {
                GroupDao gpDao = new GroupDao(con__);
                CmnGroupmModel gpMdl = new CmnGroupmModel();
                gpMdl = gpDao.getGroup(gpSid);
                if (gpMdl != null) {
                    editUserName = gpMdl.getGrpName();
                }
            } else {
                umodel = udao.select(userSid);
                editUserName = umodel.getUsiSei() + " " + umodel.getUsiMei();
            }

            //更新ファイルフルパス
            String fullpath = getDirctoryPath(dirModel.getFdrSid());

            //備考
            String biko = dirModel.getFdrBiko();

            //本文
            String tmpPath = getSmlTemplateFilePathPlg(appRootPath); //テンプレートファイルパス取得
            String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
            Map<String, String> map = new HashMap<String, String>();
            map.put("UDATE", udate);
            map.put("UNAME", editUserName);
            map.put("FILENAME", fullpath);
            map.put("BIKO", biko);
            map.put("URL", url);

            GsMessage gsMsg = new GsMessage(reqMdl__);
            String textMessage = gsMsg.getMessage("cmn.mail.omit");

            String bodyml = StringUtil.merge(tmpBody, map);

            if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
                bodyml = textMessage + "\r\n\r\n" + bodyml;
                bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
            }

            //ショートメール送信用モデルを作成する。
            SmlSenderModel smlModel = new SmlSenderModel();
            //送信者(システムメールを指定)
            smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
            //TO
            smlModel.setSendToUsrSidArray(sidList);
            //タイトル
            String title = gsMsg.getMessage("fil.38");
            if (!StringUtil.isNullZeroString(fullpath)) {
                title += (new File(fullpath)).getName();
            }
            title = StringUtil.trimRengeString(title,
                    GSConstCommon.MAX_LENGTH_SMLTITLE);
            smlModel.setSendTitle(title);

            //本文
            smlModel.setSendBody(bodyml);
            //マーク
            smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);
            //メール形式
            smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);

            //メール送信処理開始
            SmlSender sender = new SmlSender(con__, cntCon, smlModel,
                                             pluginConfig, appRootPath, reqMdl__);
            sender.execute();
        } catch (Exception e) {
            log__.error("ショートメール送信に失敗しました。", e);
            throw e;
        }
    }

    /**
     * <br>[機  能] ファイル管理プラグインアプリケーションのルートパスから更新通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePathPlg(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/file/smail/koushin_tsuuchi.txt");
        return ret;
    }

    /**
     * ファイル詳細アクセス用のURLを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param dirModel ディレクトリSID
     * @return String URL
     * @throws SQLException SQL実行時例外
     * @throws UnsupportedEncodingException エンコーディング例外
     */
    public String getFolderSyousaiUrlString(
            FileDirectoryModel dirModel)
    throws SQLException, UnsupportedEncodingException {


        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl__);
            paramUrl +=  "/file/fil070.do";

            paramUrl += "?fil070DirSid=" + dirModel.getFdrSid();
            paramUrl += "&fil010SelectDirSid=" + dirModel.getFdrParentSid();

            return urlBiz.getAccessUrl(reqMdl__, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }

    }

    /**
     * ファイル管理全般のログ出力を行う
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param type type
     */
    public void outPutLog(
            String opCode,
            String level,
            String value,
            String type) {
        outPutLog(opCode, level, value, type, null);
    }

    /**
     * ファイル管理全般のログ出力を行う
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param type type
     * @param fileId 添付ファイルSID
     */
    public void outPutLog(
            String opCode,
            String level,
            String value,
            String type,
            String fileId) {

        BaseUserModel usModel = reqMdl__.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        GsMessage gsMsg = new GsMessage();
        String textFileKanri = gsMsg.getMessage("cmn.filekanri");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstFile.PLUGIN_ID_FILE);
        logMdl.setLogPluginName(textFileKanri);
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(type));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl__.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (fileId != null) {
            logMdl.setLogCode("binSid：" + fileId);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = reqMdl__.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * ファイル管理全般のログ出力を行う
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param type type
     * @param fileId 添付ファイルSID
     * @param dspName 画面名
     */
    public void outPutLogNoDspName(
            String opCode,
            String level,
            String value,
            String type,
            String fileId,
            String dspName) {

        BaseUserModel usModel = reqMdl__.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        GsMessage gsMsg = new GsMessage();
        String textFileKanri = gsMsg.getMessage("cmn.filekanri");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstFile.PLUGIN_ID_FILE);
        logMdl.setLogPluginName(textFileKanri);
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(dspName);
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl__.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (fileId != null) {
            logMdl.setLogCode("binSid：" + fileId);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = reqMdl__.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }
    /**
     * ファイル管理ＡＰＩ全般のログ出力を行う
     * @param usid ユーザSID
     * @param pgId プログラムID
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutApiLog(
            int usid,
            String pgId,
            String opCode,
            String level,
            String value) {
        outPutApiLog(usid, pgId, opCode, level, value, null);
    }
    /**
     * ファイル管理ＡＰＩ全般のログ出力を行う
     * @param usid ユーザSID
     * @param pgId プログラムID
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param fileId 添付ファイルSID
     */
    public void outPutApiLog(
            int usid,
            String pgId,
            String opCode,
            String level,
            String value,
            String fileId) {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textFileKanri = gsMsg.getMessage("cmn.filekanri");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstFile.PLUGIN_ID_FILE);
        logMdl.setLogPluginName(textFileKanri);
        logMdl.setLogPgId(pgId);
        logMdl.setLogPgName(getPgName(pgId));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl__.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (fileId != null) {
            logMdl.setLogCode("binSid：" + fileId);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
//        String domain = GroupSession.getResourceManager().getDomain(req);
        logBiz.outPutLog(logMdl, reqMdl__.getDomain());
    }

    /**
     * 更新者がユーザかグループかを判定する
     * <br>[機  能]先頭文字に"G"が有る場合はグループ
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public boolean isEditGroup(String gpSid) {
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
     * 更新者がユーザかグループかを判定する
     * <br>[機  能]先頭文字に"G"が有る場合はグループ
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public int getGroupSid(String gpSid) {

        if (gpSid == null) {
            return 0;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf("G");

        // 先頭文字に"G"が有る場合はグループID
        if (index == 0) {

            if (ValidateUtil.isNumber(gpSid.substring("G".length()))) {
                return Integer.valueOf(gpSid.substring("G".length()));
            } else {
                return 0;
            }

        } else {
            return 0;
        }
    }

    /**
    * 更新者を設定する
    * <br>[機  能]
    * <br>[解  説]
    * <br>[備  考]
    * @param usrSid ユーザSID
     * @param egSid グループSID
    * @return upSid 更新者SID
     * @throws Exception 実行時例外
    */
   public String setUpdateSid(
           int usrSid, int egSid) throws Exception {
       String upSid = String.valueOf(usrSid);

       if (egSid > 0) {
           //ユーザが選択グループに所属しているか判定
           GroupBiz gBiz = new GroupBiz();
           if (gBiz.isBelongGroup(usrSid, egSid, con__)) {
               upSid = "G" + egSid;
           }
       }

       return upSid;
   }

    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @return String
     */
    public String getPgName(String id) {
        String ret = new String();

        GsMessage gsMsg = new GsMessage(reqMdl__);

        if (id.equals("jp.groupsession.v2.api.file.add.ApiFileAddAction")) {
            String textTitle = gsMsg.getMessage("fil.58");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.api.file.addfolder.ApiFileAddFolderAction")) {
            String textTitle = gsMsg.getMessage("fil.39");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.api.file.editfolder.ApiFileEditFolderAction")) {
            String textTitle = gsMsg.getMessage("cmn.edit.folder");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.api.file.delete.ApiFileDeleteAction")) {
            String textTitle = gsMsg.getMessage("fil.41");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.api.file.download.ApiFileDownloadAction")) {
            String textTitle = gsMsg.getMessage("fil.42");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.api.file.update.ApiFileUpdateAction")) {
            String textTitle = gsMsg.getMessage("fil.43");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.api.file.lock.ApiFileLockAction")) {
            String textTitle = gsMsg.getMessage("fil.44");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.api.file.rename.ApiFileRenameAction")) {
            String textTitle = gsMsg.getMessage("fil.45");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.api.file.move.ApiFileMoveAction")) {
            String textTitle = gsMsg.getMessage("fil.46");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.api.file.unlock.ApiFileUnlockAction")) {
            String textTitle = gsMsg.getMessage("fil.48");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.api.file.shortcut.ApiFileShortcutAction")) {
            String textTitle = gsMsg.getMessage("fil.2");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil010.Fil010Action")) {
            String textTitle = gsMsg.getMessage("fil.49");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil020.Fil020Action")) {
            String textTitle = gsMsg.getMessage("fil.50");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil030.Fil030Action")) {
            String textTitle = gsMsg.getMessage("fil.51");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil030kn.Fil030knAction")) {
            String textTitle = gsMsg.getMessage("fil.52");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil040.Fil040Action")) {
            String textTitle = gsMsg.getMessage("fil.53");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil050.Fil050Action")) {
            String textTitle = gsMsg.getMessage("fil.54");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil060.Fil060Action")) {
            String textTitle = gsMsg.getMessage("fil.55");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil070.Fil070Action")) {
            String textTitle = gsMsg.getMessage("fil.57");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil080.Fil080Action")) {
            String textTitle = gsMsg.getMessage("fil.58");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil090.Fil090Action")) {
            String textTitle = gsMsg.getMessage("fil.46");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil100.Fil100Action")) {
            String textTitle = gsMsg.getMessage("fil.60");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil120kn.Fil120knAction")) {
            String textTitle = gsMsg.getMessage("cmn.display.settings.kn");
            String textKojinSettei = gsMsg.getMessage("cmn.preferences");
            return textKojinSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil130kn.Fil130knAction")) {
            String textTitle = gsMsg.getMessage("cmn.sml.notification.setting.kn");
            String textKojinSettei = gsMsg.getMessage("cmn.preferences");
            return textKojinSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil210kn.Fil210knAction")) {
            String textTitle = gsMsg.getMessage("cmn.preferences.kn");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil220.Fil220Action")) {
            String textTitle = gsMsg.getMessage("fil.62");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil230kn.Fil230knAction")) {
            String textTitle = gsMsg.getMessage("fil.100");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.filptl020.Filptl020Action")) {
            String textTitle = gsMsg.getMessage("fil.ptl020.1");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil290kn.Fil290knAction")) {
            String textTitle = gsMsg.getMessage("fil.151");
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil300.Fil300Action")) {
            String textTitle = gsMsg.getMessage("fil.160");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil310.Fil310Action")) {
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            String textTitle = gsMsg.getMessage("fil.132");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil320.Fil320Action")) {
            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
            String textTitle = gsMsg.getMessage("fil.133");
            return textKanriSettei + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.fil.fil330kn.Fil330knAction")) {
            String textTitle = gsMsg.getMessage("fil.fil330kn.1");
            return textTitle;
        }

        return ret;
    }

    /**
     * <br>[機  能] ファイル管理 管理者設定情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ファイル管理管理者設定情報
     * @throws SQLException SQL実行例外
     */
    public FileAconfModel getFileAdminData() throws SQLException {
        FileAconfDao admDao = new FileAconfDao(con__);
        FileAconfModel admData = admDao.select();
        if (admData == null) {
            //取得できなかった場合は初期値を設定する
            admData = new FileAconfModel();
            admData.init();
        }

        return admData;
    }

    /**
     * <br>[機  能] ファイル管理 個人設定情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザID
     * @return ファイル管理個人設定情報
     * @throws SQLException SQL実行例外
     */
    public FileUconfModel getFileUserData(int userSid) throws SQLException {
        FileUconfDao uconfDao = new FileUconfDao(con__);
        FileUconfModel uconfMdl = uconfDao.select(userSid);
        if (uconfMdl == null || uconfMdl.getUsrSid() <= 0) {
            //取得できなかった場合は初期値を設定する
            uconfMdl = new FileUconfModel();
            uconfMdl.init(userSid);
        }

        return uconfMdl;
    }

    /**
     * <br>[機  能] 個人設定でショートメール通知設定が可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canSetSmailConf() throws SQLException {
        FileAconfModel admData = getFileAdminData();
        return admData.getFacSmailSendKbn() != GSConstFile.FAC_SMAIL_SEND_KBN_ADMIN;
    }

    /**
     * <br>[機  能] 管理者設定でショートメール通知が許可されているが判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true:通知可能 false:通知不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canSendSmail() throws SQLException {
        FileAconfModel admData = getFileAdminData();
        return admData.getFacSmailSendKbn() != GSConstFile.FAC_SMAIL_SEND_KBN_ADMIN
                || admData.getFacSmailSend() == GSConstFile.FAC_SMAIL_SEND_YES;
    }
//    /**
//     * プログラムIDからプログラム名称を取得する
//     * @param id アクションID
//     * @return String
//     */
//    public String getPgName(String id) {
//        String ret = new String();
//        if (id == null) {
//            return ret;
//        }
//
//        log__.info("プログラムID==>" + id);
//
//        GsMessage gsMsg = new GsMessage(reqMdl__);
//
//        if (id.equals("jp.groupsession.v2.api.file.add.ApiFileAddAction")) {
//            String textTitle = gsMsg.getMessage("fil.58");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.api.file.addfolder.ApiFileAddFolderAction")) {
//            String textTitle = gsMsg.getMessage("fil.39");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.api.file.editfolder.ApiFileEditFolderAction")) {
//            String textTitle = gsMsg.getMessage("cmn.edit.folder");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.api.file.delete.ApiFileDeleteAction")) {
//            String textTitle = gsMsg.getMessage("fil.41");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.api.file.download.ApiFileDownloadAction")) {
//            String textTitle = gsMsg.getMessage("fil.42");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.api.file.update.ApiFileUpdateAction")) {
//            String textTitle = gsMsg.getMessage("fil.43");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.api.file.lock.ApiFileLockAction")) {
//            String textTitle = gsMsg.getMessage("fil.44");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.api.file.rename.ApiFileRenameAction")) {
//            String textTitle = gsMsg.getMessage("fil.45");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.api.file.move.ApiFileMoveAction")) {
//            String textTitle = gsMsg.getMessage("fil.46");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.api.file.unlock.ApiFileUnlockAction")) {
//            String textTitle = gsMsg.getMessage("fil.48");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil010.Fil010Action")) {
//            String textTitle = gsMsg.getMessage("fil.49");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil020.Fil020Action")) {
//            String textTitle = gsMsg.getMessage("fil.50");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil030.Fil030Action")) {
//            String textTitle = gsMsg.getMessage("fil.51");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil030kn.Fil030knAction")) {
//            String textTitle = gsMsg.getMessage("fil.52");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil040.Fil040Action")) {
//            String textTitle = gsMsg.getMessage("fil.53");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil050.Fil050Action")) {
//            String textTitle = gsMsg.getMessage("fil.54");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil060.Fil060Action")) {
//            String textTitle = gsMsg.getMessage("fil.55");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil060kn.Fil060knAction")) {
//            String textTitle = gsMsg.getMessage("fil.56");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil070.Fil070Action")) {
//            String textTitle = gsMsg.getMessage("fil.57");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil080.Fil080Action")) {
//            String textTitle = gsMsg.getMessage("fil.58");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil080kn.Fil080knAction")) {
//            String textTitle = gsMsg.getMessage("fil.59");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil090.Fil090Action")) {
//            String textTitle = gsMsg.getMessage("fil.46");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil090kn.Fil090knAction")) {
//            String textTitle = gsMsg.getMessage("fil.46");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil100.Fil100Action")) {
//            String textTitle = gsMsg.getMessage("fil.60");
//            return textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil120kn.Fil120knAction")) {
//            String textTitle = gsMsg.getMessage("cmn.display.settings.kn");
//            String textKojinSettei = gsMsg.getMessage("cmn.preferences");
//            return textKojinSettei + " " + textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil130kn.Fil130knAction")) {
//            String textTitle = gsMsg.getMessage("cmn.sml.notification.setting.kn");
//            String textKojinSettei = gsMsg.getMessage("cmn.preferences");
//            return textKojinSettei + " " + textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil210kn.Fil210knAction")) {
//            String textTitle = gsMsg.getMessage("cmn.preferences.kn");
//            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
//            return textKanriSettei + " " + textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil220.Fil220Action")) {
//            String textTitle = gsMsg.getMessage("fil.62");
//            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
//            return textKanriSettei + " " + textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.fil230kn.Fil230knAction")) {
//            String textTitle = gsMsg.getMessage("fil.100");
//            String textKanriSettei = gsMsg.getMessage("cmn.admin.setting");
//            return textKanriSettei + " " + textTitle;
//        }
//        if (id.equals("jp.groupsession.v2.fil.filptl020.Filptl020Action")) {
//            String textTitle = gsMsg.getMessage("fil.ptl020.1");
//            return textTitle;
//        }
//
//        return ret;
//    }
    /**
     *
     * <br>[機  能] ディレクトリの編集可能か確認し、エラーコードを返す
     * <br>[解  説]
     * <br>[備  考]　0:エラー無し 1:存在しない 2:ディレクトリの編集権限が無い 4:ディレクトリ区分の不一致
     * @param dirSid ディレクトリSID
     * @param kbn 0:フォルダとして編集 1:ファイルとして編集 -1:区別しない
     * @return エラーコード　0:エラー無し 1:存在しない 2:キャビネットの閲覧権限が無い 4:ディレクトリ区分の不一致
     * @throws SQLException SQL実行時例外
     */
    public int checkPowEditDir(int dirSid, int kbn) throws SQLException {
        return checkPowEditDir(dirSid, kbn, false);
    }
    /**
     *
     * <br>[機  能] ディレクトリの編集可能か確認し、エラーコードを返す
     * <br>[解  説]
     * <br>[備  考]　0:エラー無し 1:存在しない 2:ディレクトリの編集権限が無い 4:ディレクトリ区分の不一致
     * @param dirSid ディレクトリSID
     * @param kbn 0:フォルダとして編集 1:ファイルとして編集 -1:区別しない
     * @param ignoreJkbnFlg 論理削除を確認しない
     * @return エラーコード　0:エラー無し 1:存在しない 2:キャビネットの閲覧権限が無い 4:ディレクトリ区分の不一致
     * @throws SQLException SQL実行時例外
     */
    public int checkPowEditDir(int dirSid,
            int kbn,
            boolean ignoreJkbnFlg) throws SQLException {
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel dirModel = dirDao.getNewDirectory(dirSid);
        if (dirModel == null) {
            return ERR_NOT_EXIST;
        }
        if (ignoreJkbnFlg == false
              && dirModel.getFdrJtkbn() != GSConst.JTKBN_TOROKU) {
            return ERR_NOT_EXIST;
        }
        int fcbSid = dirModel.getFcbSid();
        //ディレクトリへのアクセス権限があるか判定する。
        if (!isDirAccessAuthUser(fcbSid,
                dirSid,
                Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE),
                ignoreJkbnFlg)) {
            return ERR_NONE_CAB_EDIT_POWER;
        }
        if (kbn != -1) {
            if (dirModel.getFdrKbn() != kbn) {
                return ERR_NOT_DIRKBN;
            }
        }
        return ERR_NONE;
    }

    /**
     *
     * <br>[機  能] ディレクトリの編集権限確認し、エラーコードを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param dirSid ディレクトリSID
     * @return エラーコード　0:エラー無し 1:存在しない 3:ディレクトリの編集権限が無い
     * @throws SQLException SQL実行時例外
     */
    public int checkPowEditDir(int dirSid) throws SQLException {
        return checkPowEditDir(dirSid, -1);
    }
    /**
     *
     * <br>[機  能] キャビネットの編集権限確認し、エラーコードを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @return エラーコード　0:エラー無し 1:存在しない 3:キャビネットの編集権限が無い
     * @throws SQLException SQL実行時例外
     */
    public int checkPowViewCab(int fcbSid) throws SQLException {
        return checkPowViewCab(fcbSid, false);
    }
    /**
     *
     * <br>[機  能] キャビネットの編集権限確認し、エラーコードを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param ignoreJkbnFlg true: 論理削除を無視する
     * @return エラーコード　0:エラー無し 1:存在しない 3:キャビネットの編集権限が無い
     * @throws SQLException SQL実行時例外
     */
    public int checkPowViewCab(int fcbSid, boolean ignoreJkbnFlg) throws SQLException {
        FileCabinetDao cabDao = new FileCabinetDao(con__);
        FileCabinetModel cabMdl = cabDao.select(fcbSid);
        if (cabMdl == null) {
            return ERR_NOT_EXIST;
        }
        if (ignoreJkbnFlg == false
                && cabMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            return ERR_NOT_EXIST;
        }
        if (!isAccessAuthUser(fcbSid)) {
            return ERR_NONE_CAB_VIEW_POWER;
        }
        return ERR_NONE;
    }

    /**
     *
     * <br>[機  能] キャビネットの編集権限確認し、エラーコードを返す
     * <br>[解  説]
     * <br>[備  考] キャビネットが論理削除されている場合は1
     * @param fcbSid キャビネットSID
     * @return エラーコード　0:エラー無し 1:存在しない 3:キャビネットの編集権限が無い
     * @throws SQLException SQL実行時例外
     */
    public int checkPowEditCab(int fcbSid) throws SQLException {
        return checkPowEditCab(fcbSid, false);
    }
    /**
    *
    * <br>[機  能] キャビネットの編集権限確認し、エラーコードを返す
    * <br>[解  説]
    * <br>[備  考]
    * @param fcbSid キャビネットSID
    * @param ignoreJkbnFlg 論理削除を確認しない
    * @return エラーコード　0:エラー無し 1:存在しない 3:キャビネットの編集権限が無い
    * @throws SQLException SQL実行時例外
    */
   public int checkPowEditCab(int fcbSid,
           boolean ignoreJkbnFlg) throws SQLException {

        FileCabinetDao cabDao = new FileCabinetDao(con__);
        FileCabinetModel cabMdl = cabDao.select(fcbSid);
        //キャビネットが存在しない
        if (cabMdl == null) {
            return ERR_NOT_EXIST;
        }
        if (ignoreJkbnFlg == false && cabMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            return ERR_NOT_EXIST;
        }
        //管理者であれば編集は可能
        CommonBiz  commonBiz = new CommonBiz();
        BaseUserModel umodel = reqMdl__.getSmodel();
        boolean adminUser = commonBiz.isPluginAdmin(con__, umodel, GSConstFile.PLUGIN_ID_FILE);
        if (adminUser) {
            return ERR_NONE;
        }
        //キャビネットの編集権限を持たない
        if (!isEditCabinetUser(fcbSid, -1, ignoreJkbnFlg)) {
            return ERR_NONE_CAB_EDIT_POWER;
        }
        return ERR_NONE;
    }

    /**
     *
     * <br>[機  能] キャビネットの編集権限のみを確認し、エラーコードを返す
     * <br>[解  説]
     * <br>[備  考] ディレクトリの
     * @param fcbSid キャビネットSID
     * @return エラーコード　0:エラー無し 1:存在しない 3:キャビネットの編集権限が無い
     * @throws SQLException SQL実行時例外
     */
    public int checkPowEditRootDir(int fcbSid) throws SQLException {
        return checkPowEditRootDir(fcbSid, false);
    }
    /**
     *
     * <br>[機  能] キャビネットの編集権限のみを確認し、エラーコードを返す
     * <br>[解  説]
     * <br>[備  考] ディレクトリの
     * @param fcbSid キャビネットSID
     * @param ignoreJkbnFlg true:論理削除を無視する
     * @return エラーコード　0:エラー無し 1:存在しない 3:キャビネットの編集権限が無い
     * @throws SQLException SQL実行時例外
     */
    public int checkPowEditRootDir(int fcbSid,
            boolean ignoreJkbnFlg) throws SQLException {

       FileCabinetDao cabDao = new FileCabinetDao(con__);
       FileCabinetModel cabMdl = cabDao.select(fcbSid);
       //キャビネットが存在しない
       if (cabMdl == null) {
           return ERR_NOT_EXIST;
       }
       if (ignoreJkbnFlg == false
               && cabMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
           return ERR_NOT_EXIST;
       }
       //管理者であれば編集は可能
       CommonBiz  commonBiz = new CommonBiz();
       BaseUserModel umodel = reqMdl__.getSmodel();
       boolean adminUser = commonBiz.isPluginAdmin(con__, umodel, GSConstFile.PLUGIN_ID_FILE);
       if (adminUser) {
           return ERR_NONE;
       }

       //キャビネットのアクセス区分が設定されていない
       if (cabMdl.getFcbAccessKbn() == GSConstFile.ACCESS_KBN_OFF) {
           return ERR_NONE;
       }

       //キャビネット管理者であれば編集可能
       int userSid = reqMdl__.getSmodel().getUsrsid();
       if (isCabinetAdminUser(fcbSid, userSid)) {
           return ERR_NONE;
       }

       //キャビネットの編集権限を持たない
       FileAccessConfDao facDao = new FileAccessConfDao(con__);
       boolean accessFlg = facDao.isAccessUserGroup(fcbSid, userSid, GSConstFile.EDIT_AUTH_OK);
       if (!accessFlg) {
           return ERR_NONE_CAB_EDIT_POWER;
       }

       return ERR_NONE;
    }

    /**
     *
     * <br>[機  能] 指定したキャビネットが個人キャビネットか判定し、個人キャビネット判定フラグを返します。
     * <br>[解  説]
     * <br>[備  考]
     * @param cabSid キャビネットSID
     * @return 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット
     * @throws SQLException SQL実行例外
     */
    public int getPersonalFlg(int cabSid) throws SQLException {

        return getPersonalFlg(cabSid, false);
    }

    /**
     *
     * <br>[機  能] 指定したキャビネットが個人キャビネットか判定し、個人キャビネット判定フラグを返します。
     * <br>[解  説]
     * <br>[備  考]
     * @param cabSid キャビネットSID
     * @param ignoreJkbnFlg true:論理削除キャビネットを含める, false:論理削除キャビネットを含めない
     * @return 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット
     * @throws SQLException SQL実行例外
     */
    public int getPersonalFlg(int cabSid, boolean ignoreJkbnFlg) throws SQLException {

        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        FileCabinetModel fcbMdl = fcbDao.select(cabSid);
        if (fcbMdl != null && (ignoreJkbnFlg || fcbMdl.getFcbJkbn() != GSConstFile.JTKBN_DELETE)) {
            return fcbMdl.getFcbPersonalFlg();
        }
        return GSConstFile.CABINET_KBN_PUBLIC;
    }

    /**
     * <br>[機  能] 指定したバイナリのデータが取得可能かチェックします。
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param binSid バイナリSID
     * @throws SQLException SQL実行時例外
     * @return true:可能  false:不可能
     */
    public boolean isCheckFileIcon(int fcbSid, Long binSid)
            throws SQLException {
        return isCheckFileIcon(fcbSid, binSid, false);
    }
    /**
     * <br>[機  能] 指定したバイナリのデータが取得可能かチェックします。
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param binSid バイナリSID
     * @param ignoreJkbnFlg 論理削除を確認しない
     * @throws SQLException SQL実行時例外
     * @return true:可能  false:不可能
     */
    public boolean isCheckFileIcon(int fcbSid,
            Long binSid,
            boolean ignoreJkbnFlg)
            throws SQLException {

        FileCabinetDao dao = new FileCabinetDao(con__);
        boolean iconFlg = dao.isCheckFileIcon(fcbSid, binSid);
        //キャビネットSIDとバイナリSIDの組み合わせがOK且つ
        //キャビネットにアクセス可能である。
        if (iconFlg && isAccessAuthUser(fcbSid, -1, ignoreJkbnFlg)) {
            return true;
        }
        return false;
    }


    /**
     * <br>[機  能] ファイルダウンロード時の集計用データを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param binSid バイナリSID
     * @param date ダウンロード日時
     * @throws SQLException SQL実行時例外
     */
    public void regFileDownloadLogCnt(
            int usrSid, long binSid, UDate date)
                    throws SQLException {

        FileFileBinDao filBinDao = new FileFileBinDao(con__);

        //バイナリSIDの存在チェック
        int count = filBinDao.getBinCount(binSid);
        if (count > 0) {
            //キャビネットSIDを取得
            int fcbSid = filBinDao.getCabinetSid(binSid, false);
            regFileDownloadLogCnt(usrSid, fcbSid, binSid, date);
        }
    }

    /**
     * <br>[機  能] ファイルダウンロード時の集計用データを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param fcbSid ファイルキャビネットSID
     * @param binSid バイナリSID
     * @param date ダウンロード日時
     * @throws SQLException SQL実行時例外
     */
    public void regFileDownloadLogCnt(
            int usrSid, int fcbSid, long binSid, UDate date)
                    throws SQLException {

        boolean commitFlg = false;
        con__.setAutoCommit(false);
        //ロックの解除処理
        try {
            FileDownloadLogModel mdl = new FileDownloadLogModel();
            mdl.setUsrSid(usrSid);
            mdl.setFcbSid(fcbSid);
            mdl.setBinSid(binSid);
            mdl.setFdlDate(date);
            FileDownloadLogDao dao = new FileDownloadLogDao(con__);
            dao.insert(mdl);

            //集計
            __registLogCntSum(GSConstFile.FLS_KBN_DOWNLOAD, date);

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("ダウンロード時集計用データの登録に失敗" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                con__.rollback();
            }
        }

    }

    /**
     * <br>[機  能] ファイルアップロード時の集計用データを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param grpSid グループID
     * @param fulRegKbn 登録区分  0:新規 1:更新
     * @param fcbSid ファイルキャビネットSID
     * @param binSid バイナリSID
     * @param date アップロード日時
     * @throws SQLException SQL実行時例外
     */
    public void regFileUploadLogCnt(
            int usrSid, int grpSid, int fulRegKbn,
            int fcbSid, long binSid, UDate date)
                    throws SQLException {
        FileUploadLogModel mdl = new FileUploadLogModel();
        mdl.setUsrSid(usrSid);
        mdl.setGrpSid(grpSid);
        mdl.setFulRegKbn(fulRegKbn);
        mdl.setFcbSid(fcbSid);
        mdl.setBinSid(binSid);
        mdl.setFulDate(date);
        FileUploadLogDao dao = new FileUploadLogDao(con__);
        dao.insert(mdl);

        //集計
        __registLogCntSum(GSConstFile.FLS_KBN_UPLOAD, date);
    }

    /**
     * <br>[機  能] ファイル管理 全文検索を使用するか判別する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @return true:使用する  false:使用しない
     */
    public static synchronized boolean isUseAllTextSearch(String appRootPath) {
        return getAllTextSearchUseKbn(appRootPath) == GSConstFile.FIL_ALL_SEARCH_USE_YES;
    }

    /**
     * <br>[機  能] ファイル管理 全文検索の使用区分を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @return 0:使用しない  1:使用する
     */
    public static synchronized int getAllTextSearchUseKbn(String appRootPath) {
        return getConfValue(appRootPath,
                GSConstFile.FIL_ALL_SEARCH_USE,
                GSConstFile.FIL_ALL_SEARCH_USE_NO);
    }

    /**
     * <br>[機  能] ファイル管理全文検索の設定ファイルの設定値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @param confKey 設定値のキー
     * @param defValue デフォルト値
     * @return 設定値
     */
    public static synchronized int getConfValue(String appRootPath, String confKey, int defValue) {

        String confValue = FilConfigBundle.getValue(confKey);

        if (StringUtil.isNullZeroString(confValue)) {
            try {
                FilConfigBundle.readConfig(appRootPath);
                confValue = FilConfigBundle.getValue(confKey);
            } catch (IOException e) {
                log__.error("ファイル管理設定ファイルの読み込みに失敗", e);
            }
        }

        if (!StringUtil.isNullZeroString(confValue)) {
            return Integer.parseInt(confValue);
        }

        return defValue;
    }

    /**
     * <br>[機  能] ファイル管理集計データ_集計結果を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param flsKbn ログ区分
     * @param date 日時
     * @throws SQLException SQL実行時例外
     */
    private void __registLogCntSum(
            int flsKbn, UDate date) throws SQLException {

        FileLogCountSumDao logSumDao = new FileLogCountSumDao(con__);
        FileLogCountSumModel logSumMdl = logSumDao.getSumLogCount(flsKbn, date);
        if (logSumMdl != null) {
            if (logSumDao.update(logSumMdl) == 0) {
                logSumDao.insert(logSumMdl);
            }
        }
    }

    /**
     * バージョン管理コンボのラベル一覧を取得する
     * @return ArrayList<LabelValueBean> バージョン管理ラベル一覧
     */
    public ArrayList<LabelValueBean> getVersionLabelList() {

        //バージョンコンボ
        ArrayList<LabelValueBean> versionLavel = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl__);

        LabelValueBean lavelBean = null;
        for (int i = 0; i < 11; i++) {
            lavelBean = new LabelValueBean();
            lavelBean.setLabel(gsMsg.getMessage("fil.generations",
                    new String[] {String.valueOf(i)}));
            lavelBean.setValue(String.valueOf(i));
            versionLavel.add(lavelBean);
        }
        return versionLavel;
    }

    /**
     * 容量警告コンボのラベル一覧を取得する
     * @return ArrayList<LabelValueBean> 容量警告ラベル一覧
     */
    public ArrayList<LabelValueBean> getCapaWarnLabelList() {

        //警告コンボ
        ArrayList<LabelValueBean> capaWarnLavel = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textSiteiNasi = gsMsg.getMessage("cmn.specified.no");

        LabelValueBean lavelBean = null;
        lavelBean = new LabelValueBean();
        lavelBean.setLabel(textSiteiNasi);
        lavelBean.setValue("0");
        capaWarnLavel.add(lavelBean);
        for (int i = 50; i < 100; i += 5) {
            lavelBean = new LabelValueBean();
            lavelBean.setLabel(i + "%");
            lavelBean.setValue(String.valueOf(i));
            capaWarnLavel.add(lavelBean);
        }
        return capaWarnLavel;
    }

    /**
     * <br>[機  能] マイキャビネット使用判定
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid 使用ユーザSID
     * @return true: 使用可 / false: 使用不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isCanMyCabinet(int userSid) throws SQLException {

        boolean ret = false;

        FileAconfDao aconfDao = new FileAconfDao(con__);
        FileAconfModel aconf = aconfDao.select();
        if (aconf != null && aconf.getFacPersonalKbn() == GSConstFile.CABINET_PRIVATE_USE) {
            if (aconf.getFacUseKbn() == GSConstFile.CABINET_AUTH_ALL) {
                ret = true; // 全ユーザ使用許可
            } else {
                // 指定ユーザのみなのでセッションユーザが使用可能か判定
                FilePcbOwnerDao pcbDao = new FilePcbOwnerDao(con__);
                ret = pcbDao.isCreateAuth(userSid);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 個人キャビネット使用判定
     * <br>[解  説]
     * <br>[備  考]
     * @param cabMdl キャビネットモデルデータ
     * @param userSid 使用ユーザSID
     * @param isSelf 自身をチェックするかの判定フラグ
     * @return true: 使用可 / false: 使用不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isCanPrivateCabinet(FileCabinetModel cabMdl, int userSid, boolean isSelf)
            throws SQLException {

        int fcbSid = cabMdl.getFcbSid();
        int dirSid = getRootDirSid(cabMdl); // ディレクトリSID未指定時はルートディレクトリ指定

        //アクセス権限を持つ子ディレクトリを持っているか(ルートディレクトリは含めない)
        if (isSubDirAccessUser(dirSid, isSelf)) {
            return true;
        }

        // 子ディレクトリにアクセス権限があるものがない → キャビネット権限チェック
        if (cabMdl != null && cabMdl.getFcbAccessKbn() == GSConstFile.ACCESS_KBN_OFF) {
            return false; // アクセス制限なし
        }

        //アクセス制御から判定
        FileAccessConfDao accDao = new FileAccessConfDao(con__);
        if (accDao.isAccessUser(fcbSid, userSid, -1)) {
            return true;
        }
        if (accDao.isAccessUserForBelongGroup(fcbSid, userSid, -1)) {
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] キャビネットの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cabSid 削除するキャビネットSID
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void deleteCabinet(int cabSid, String appRootPath)
            throws SQLException, IOToolsException {
        FileCabinetDao fcbDao = new FileCabinetDao(con__);

        //キャビネット情報を論理削除
        fcbDao.deleteCabinet(cabSid, reqMdl__.getSmodel().getUsrsid());
    }


    /**
     * <br>[機  能] ユーザ名を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return ユーザ名
     * @throws SQLException SQL実行時例外
     */
    public String getUserName(int userSid)
            throws SQLException {
        // ユーザ名取得
        FileDao filDao = new FileDao(con__);
        Integer key = Integer.valueOf(userSid);
        ArrayList<Integer> userSidList = new ArrayList<Integer>();
        userSidList.add(key);
        HashMap<Integer, String> userNameMap = filDao.getUserNameMap(userSidList);

        if (userNameMap.size() > 0 && userNameMap.containsKey(key)) {
            return userNameMap.get(key);
        }
        return null;
    }

    /**
     * <br>[機  能] キャビネットが論理削除されているかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @return true:論理削除されている/false:論理削除されていない
     * @throws SQLException SQL実行例外
     */
    public boolean isExistCabinet(int fcbSid) throws SQLException {

        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        return fcbDao.isLogicalDelCabinet(fcbSid);
    }

    /**
     * <br>[機  能] ディレクトリが論理削除されているかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid ディレクトリSID
     * @return true:論理削除されている/false:論理削除されていない
     * @throws SQLException SQL実行例外
     */
    public boolean isExistDirectory(int fdrSid) throws SQLException {

        FileDirectoryDao fcbDao = new FileDirectoryDao(con__);
        return fcbDao.isLogicalDelDirectory(fdrSid);
    }

    /**
     * <br>[機  能] 自動先振り分け機能の保存先ディレクトリSIDを取得します。
     * <br>[解  説]
     * <br>[備  考] 保存先ディレクトリが存在しない場合、作成します。
     * @param fcbSid キャビネットSID
     * @param uploadDate アップロード年月
     * @param tradeDate 取引年月
     * @param tradeTarget 取引対象
     * @param cntCon 採番コントローラ
     * @param userSid 実行ユーザSID
     * @param feaDefGrpName 登録時のデフォルトグループ名
     * @return 自動振り分け先ディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException
     */
    public FileDirectoryModel getErrlDirModel(int fcbSid, UDate uploadDate, UDate tradeDate,
            String tradeTarget, MlCountMtController cntCon, int userSid,
            String feaDefGrpName)
                    throws SQLException, IOToolsException {
        return getErrlDirModel(fcbSid, uploadDate, tradeDate, tradeTarget, cntCon, userSid, feaDefGrpName, false);
    }
    /**
     * <br>[機  能] 自動先振り分け機能の保存先ディレクトリSIDを取得します。
     * <br>[解  説]
     * <br>[備  考] 保存先ディレクトリが存在しない場合、作成します。
     * @param fcbSid キャビネットSID
     * @param uploadDate アップロード年月
     * @param tradeDate 取引年月
     * @param tradeTarget 取引対象
     * @param cntCon 採番コントローラ
     * @param userSid 実行ユーザSID
     * @param feaDefGrpName 登録時のデフォルトグループ名
     * @param ignoreJkbnFlg true:論理削除キャビネットを含める, false:論理削除キャビネットを含めない
     * @return 自動振り分け先ディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException
     */
    public FileDirectoryModel getErrlDirModel(int fcbSid,
            UDate uploadDate,
            UDate tradeDate,
            String tradeTarget,
            MlCountMtController cntCon,
            int userSid,
            String feaDefGrpName,
            boolean ignoreJkbnFlg) throws SQLException, IOToolsException {

        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        FileCabinetModel fcbMdl = fcbDao.select(fcbSid);
        if (fcbMdl == null) {
            return null;
        }
        if (ignoreJkbnFlg && fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            return null;
        }

        String dirName =  __getAutoSortDirName(fcbMdl.getFcbSortFolder1(),
                                                uploadDate,
                                                tradeDate,
                                                tradeTarget,
                                                feaDefGrpName);

        FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
        FileDirectoryModel directoryMdl = fdrDao.getRootDirectory(fcbSid);

        //第一フォルダが存在しない場合、フォルダの作成を行う
        directoryMdl =
            __createSortFolder(con__, cntCon,
                            fcbSid, dirName,
                            directoryMdl.getFdrSid(),
                            GSConstFile.DIRECTORY_LEVEL_1,
                            directoryMdl.getFdrAccessSid(),
                            userSid);

        //第二フォルダを使用しない場合は、第一フォルダのディレクトリSIDを返す
        if (fcbMdl.getFcbSortFolder2() == GSConstFile.SORT_FOLDER_NOT_USE) {
            return directoryMdl;
        }

        //第二フォルダを使用する場合
        dirName =  __getAutoSortDirName(fcbMdl.getFcbSortFolder2(),
                                        uploadDate,
                                        tradeDate,
                                        tradeTarget,
                                        feaDefGrpName);

        //第二フォルダが存在しない場合、フォルダの作成を行う
        directoryMdl =
            __createSortFolder(con__, cntCon,
                            fcbSid, dirName,
                            directoryMdl.getFdrSid(),
                            GSConstFile.DIRECTORY_LEVEL_2,
                            directoryMdl.getFdrAccessSid(),
                            userSid);

        //第三フォルダを使用しない場合は、第二フォルダのディレクトリSIDを返す
        if (fcbMdl.getFcbSortFolder3() == GSConstFile.SORT_FOLDER_NOT_USE) {
            return directoryMdl;
        }

        //第三フォルダを使用する場合
        dirName =  __getAutoSortDirName(fcbMdl.getFcbSortFolder3(),
                                        uploadDate,
                                        tradeDate,
                                        tradeTarget,
                                        feaDefGrpName);

        //第三フォルダが存在しない場合、フォルダの作成を行う
        directoryMdl =
            __createSortFolder(con__, cntCon,
                            fcbSid, dirName,
                            directoryMdl.getFdrSid(),
                            GSConstFile.DIRECTORY_LEVEL_3,
                            directoryMdl.getFdrAccessSid(),
                            userSid);

        return directoryMdl;
    }

    /**
     * 自動振り分け先ディレクトリの名称を取得する
     * @param sortFolder 自動振り分け先
     * @param uploadDate アップロード日
     * @param tradeDate 取引日
     * @param tradeTarget 取引先
     * @param feaDefGrpName デフォルトグループ名
     * @return　ディレクトリ名称
     */
    private String __getAutoSortDirName(int sortFolder,
                                        UDate uploadDate,
                                        UDate tradeDate,
                                        String tradeTarget,
                                        String feaDefGrpName) {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String year = gsMsg.getMessage("cmn.year2");
        String month = gsMsg.getMessage("cmn.month");

        String dirName = "";
        switch (sortFolder) {
            case GSConstFile.SORT_FOLDER_UPLOAD_DATE :
                dirName = uploadDate.getYear() + year
                        + StringUtil.toDecFormat(uploadDate.getMonth(), "00") + month;
                break;
            case GSConstFile.SORT_FOLDER_TRADE_DATE :
                dirName = tradeDate.getYear() + year
                        + StringUtil.toDecFormat(tradeDate.getMonth(), "00") + month;
                break;
            case GSConstFile.SORT_FOLDER_TRADE_TARGET :
                dirName = tradeTarget;
                break;
            case GSConstFile.SORT_FOLDER_TRADE_DEFGROUP :
                dirName = feaDefGrpName;
                break;
            default :
                break;
        }

        return dirName;
    }

    /**
     * 指定した親ディレクトリ内に同名のディレクトリが存在しない場合、新規作成する
     * @param con コネクション
     * @param cntCon 採番コントローラ
     * @param fcbSid キャビネットSID
     * @param dirName 振り分け先ディレクトリ名称
     * @param parentDirSid 親ディレクトリSID
     * @param dirLevel ディレクトリ階層
     * @param fdrAccessSid アクセス設定SID
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 更新通知設定の登録に失敗
     */
    private FileDirectoryModel __createSortFolder(Connection con,
                                    MlCountMtController cntCon,
                                    int fcbSid,
                                    String dirName,
                                    int parentDirSid,
                                    int dirLevel,
                                    int fdrAccessSid,
                                    int userSid)

    throws SQLException, IOToolsException {

        //振り分け先ディレクトリ情報を取得する
        FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
        FileDirectoryModel dirMdl = fdrDao.getErrlDirModel(fcbSid, parentDirSid, dirName);

        //対象ディレクトリが存在しない場合、ディレクトリの新規作成を行う
        if (dirMdl == null || dirMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
            int dirSid = (int) cntCon.getSaibanNumber(
                    GSConstFile.PLUGIN_ID_FILE,
                    GSConstFile.SBNSID_SUB_DIRECTORY,
                    reqMdl__.getSmodel().getUsrsid());
            UDate now = new UDate();
            dirMdl = new FileDirectoryModel();
            dirMdl.setFdrSid(dirSid);
            dirMdl.setFdrVerKbn(0);
            dirMdl.setFcbSid(fcbSid);
            dirMdl.setFdrParentSid(parentDirSid);
            dirMdl.setFdrKbn(GSConstFile.DIRECTORY_KBN_FOLDER);
            dirMdl.setFdrVerKbn(0);
            dirMdl.setFdrLevel(dirLevel);
            dirMdl.setFdrName(dirName);
            dirMdl.setFdrBiko("");
            dirMdl.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
            dirMdl.setFdrAccessSid(fdrAccessSid);
            dirMdl.setFdrAuid(userSid);
            dirMdl.setFdrAdate(now);
            dirMdl.setFdrEuid(userSid);
            dirMdl.setFdrEdate(now);
            fdrDao.insert(dirMdl);

            //更新通知設定
            insertCallConf(dirSid, parentDirSid);

        }

        return dirMdl;
    }

    /**
     * <br>[機  能] 外貨のラベルコンボを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return 外貨のラベルコンボ
     * @throws SQLException SQL実行例外
     */
    public List<LabelValueBean> getMoneyLabel() throws SQLException {

        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();

        FileMoneyMasterDao fmmDao = new FileMoneyMasterDao(con__);
        List<FileMoneyMasterModel> fmmList = fmmDao.select();
        for (FileMoneyMasterModel fmmMdl : fmmList) {
            ret.add(new LabelValueBean(fmmMdl.getFmmName(), String.valueOf(fmmMdl.getFmmSid())));
        }

        return ret;
    }

    /**
     * <br>[機  能] キャビネット 論理削除チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param cabSid キャビネットSID
     * @return 判定結果
     * @throws SQLException SQL実行例外
     */
    public boolean checkCabinetDeleteKbn(int cabSid) throws SQLException {

        FileCabinetDao cabDao = new FileCabinetDao(con__);
        FileCabinetModel cabMdl = cabDao.select(cabSid);
        if (cabMdl == null) {
            return false;
        }
        if (cabMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * <br>[機  能] ディレクトリ 論理削除チェック
     * <br>[解  説] キャビネットが論理削除されている場合は削除として返す
     * <br>[備  考]
     * @param dirSid ディレクトリSID
     * @return true: 生存 false 論理削除
     * @throws SQLException SQL実行例外
     */
    public boolean checkDirectoryDeleteKbn(int dirSid) throws SQLException {
        return checkDirectoryDeleteKbn(dirSid, false);
    }
    /**
     * <br>[機  能] ディレクトリ 論理削除チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param dirSid ディレクトリSID
     * @param ignoreFcbJkbnFlg キャビネット削除フラグを無視
     * @return true: 生存 false 論理削除
     * @throws SQLException SQL実行例外
     */
    public boolean checkDirectoryDeleteKbn(
            int dirSid,
            boolean ignoreFcbJkbnFlg) throws SQLException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel dirMdl = dirDao.getNewDirectory(dirSid);
        if (dirMdl == null) {
            return false;
        }

        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        FileCabinetModel fcbMdl = fcbDao.select(dirMdl.getFcbSid());
        if (fcbMdl == null) {
            return false;
        }
        if (fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE
                && ignoreFcbJkbnFlg == false) {
            return false;
        }

        if (dirMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
            return fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON;
        } else {
            return true;
        }
    }
    /**
     *
     * <br>[機  能] 指定されたディレクトリを論理削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param delSids 削除対象SID
     * @param comment 操作コメント
     * @return 論理削除されたディレクトリリスト
     * @throws SQLException
     */
    public List<FileDirectoryModel> deleteDirectoryLogical(String[] delSids, String comment) throws SQLException {
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileFileBinDao filBinDao = new FileFileBinDao(con__);
        FileFileRekiDao fileRekiDao = new FileFileRekiDao(con__);

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID


        List<FileDirectoryModel> fdrList = dirDao.getNewDirectoryList(delSids);
        if (fdrList.isEmpty()) {
            return fdrList;
        }
        //ディレクトリ区分で仕分け
        List<FileDirectoryModel> fileList = new ArrayList<>();
        List<FileDirectoryModel> folderList = new ArrayList<>();
        for (FileDirectoryModel dir : fdrList) {
            if (dir.getFdrKbn() == GSConstFile.DIRECTORY_KBN_FILE) {
                fileList.add(dir);
            } else {
                folderList.add(dir);
            }
        }
        ArrayList<FileDirectoryModel> exeList = new ArrayList<>();
        //フォルダの論理削除
        //500件ずつ
        Iterator<FileDirectoryModel> itr = folderList.iterator();
        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }
            List<Integer> sidList = exeList.stream()
                    .map(mdl -> mdl.getFdrSid())
                    .collect(Collectors.toList());
            dirDao.deleteDirectory(
                    sidList,
                    sessionUsrSid);
            exeList.clear();
        }

        //ファイルの論理削除
        //500件ずつ
        itr = fileList.iterator();;
        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }
            List<Integer> sidList = exeList.stream()
                    .map(mdl -> mdl.getFdrSid())
                    .collect(Collectors.toList());
            dirDao.deleteDirectory(sidList, sessionUsrSid);
            //削除履歴の追加
            filBinDao.insertDelFileData(sidList);
            List<FileFileRekiModel> rekiList = new ArrayList<FileFileRekiModel>();
            for (FileDirectoryModel dirMdl : exeList) {
                FileFileRekiModel rekiMdl = new FileFileRekiModel();
                rekiMdl.setFdrSid(dirMdl.getFdrSid());
                rekiMdl.setFdrVersion(dirMdl.getFdrVersion() + 1);
                rekiMdl.setFfrFname(dirMdl.getFdrName());
                rekiMdl.setFfrKbn(GSConstFile.REKI_KBN_DELETE);
                rekiMdl.setFfrEuid(sessionUsrSid);
                rekiMdl.setFfrEdate(new UDate());
                rekiMdl.setFfrParentSid(dirMdl.getFdrParentSid());
                rekiMdl.setFfrUpCmt(comment);
                rekiMdl.setFdrTradeDate(dirMdl.getFdrTradeDate());
                rekiMdl.setFdrTradeTarget(dirMdl.getFdrTradeTarget());
                rekiMdl.setFdrTradeMoneykbn(dirMdl.getFdrTradeMoneykbn());
                rekiMdl.setFdrTradeMoney(dirMdl.getFdrTradeMoney());
                rekiMdl.setEmtSid(dirMdl.getEmtSid());
                rekiList.add(rekiMdl);
            }
            fileRekiDao.insertMinData(rekiList);

            exeList.clear();
        }

        return fdrList;
    }

    /**
     * <br>[機  能] 親ディレクトリに更新通知が設定されている場合、登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dirSid ディレクトリSID
     * @param parentSid 親ディレクトリSID
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void insertCallConf(int dirSid, int parentSid)
    throws SQLException, IOToolsException {

        FileCallConfDao confDao = new FileCallConfDao(con__);

        //親ディレクトリに更新通知設定しているユーザ取得
        ArrayList<FileCallConfModel> confModelList = confDao.select(parentSid);

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
     *
     * <br>[機  能] 指定した仮登録ファイルを編集可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param feaSid 仮登録ファイルSID
     * @return true: 編集可能、false: 編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean checkEditErrlData(int feaSid) throws SQLException {

        return checkEditErrlData(
                Collections.singletonList(feaSid));
    }

    /**
     *
     * <br>[機  能] 指定した仮登録ファイルを編集可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param feaSidList 仮登録ファイルSID
     * @return true: 編集可能、false: 編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean checkEditErrlData(Collection<Integer> feaSidList) throws SQLException {

        if (feaSidList == null || feaSidList.isEmpty()) {
            return false;
        }

        FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
        List<FileErrlAdddataModel> errlDataList =
                feaDao.getErrlAddDataList(feaSidList, reqMdl__.getSmodel());


        return feaSidList.size() == errlDataList.size();
    }

}
