package jp.groupsession.v2.fil.fil210kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.dao.FileCrtConfDao;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.model.FileCrtConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 管理者設定 基本設定確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil210knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil210knBiz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Fil210knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210knParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Fil210knParamModel paramMdl) throws SQLException {

        log__.debug("fil210knBiz Start");
        int crtKbn = NullDefault.getInt(paramMdl.getFil210CrtKbn(), 0);

        if (crtKbn == GSConstFile.CREATE_CABINET_PERMIT_GROUP) {
            __setGroupName(paramMdl);
        } else if (crtKbn == GSConstFile.CREATE_CABINET_PERMIT_USER) {
            __setUserName(paramMdl);
        }

        //表示用ファイルサイズを設定する。
        __setDspFileSize(paramMdl);

        //表示削除ファイル保存期間を設定する。
        __setDspSaveDays(paramMdl);
    }

    /**
     * <br>[機  能] 表示用ファイルサイズを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210knParamModel
     */
    private void __setDspFileSize(Fil210knParamModel paramMdl) {

        int filSize = NullDefault.getInt(paramMdl.getFil210FileSize(), 0);
        String dspFileSize = null;
        if (filSize == -1) {

            GsMessage gsMsg = new GsMessage(reqMdl__);
            String textSeigenSinai = gsMsg.getMessage("cmn.not.limit");

            dspFileSize = textSeigenSinai;
        } else {
            dspFileSize = filSize + "MB";
        }

        paramMdl.setFil210knFileSize(dspFileSize);
    }

    /**
     * <br>[機  能] 表示用削除ファイル保存期間を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210knParamModel
     */
    private void __setDspSaveDays(Fil210knParamModel paramMdl) {

        int filSize = NullDefault.getInt(paramMdl.getFil210SaveDays(), 0);
        String dspSaveDays = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (filSize == -1) {

            String textHozonSinai = gsMsg.getMessage("main.man160.4");

            dspSaveDays = textHozonSinai;
        } else {
            String textDays = gsMsg.getMessage("cmn.days2");
            dspSaveDays = filSize + textDays;
        }

        paramMdl.setFil210knSaveDays(dspSaveDays);
    }

    /**
     * <br>[機  能] グループ名を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210knParamModel
     * @throws SQLException SQL実行例外
     */
    private void __setGroupName(Fil210knParamModel paramMdl) throws SQLException {

        String[] svGroups = paramMdl.getFil210SvGroups();
        if (svGroups == null || svGroups.length < 1) {
            return;
        }
        int [] groupSids = new int[svGroups.length];

        int i = 0;
        for (String grpSid : svGroups) {
            groupSids[i] = Integer.parseInt(grpSid);
            i++;
        }

        //表示するグループ情報一覧を取得する。
        GroupDao grpDao = new GroupDao(con__);
        List<CmnGroupmModel> groupList = grpDao.getGroups(groupSids);
        if (groupList == null || groupList.size() < 1) {
            return;
        }

        List<String> groupNameList = new ArrayList<String>();;

        for (CmnGroupmModel model : groupList) {
            groupNameList.add(model.getGrpName());
        }
        paramMdl.setFil210knGroupNameList(groupNameList);

    }

    /**
     * <br>[機  能] ユーザ名を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210knParamModel
     * @throws SQLException SQL実行例外
     */
    private void __setUserName(Fil210knParamModel paramMdl) throws SQLException {

        String[] svUsers = paramMdl.getFil210SvUsers();
        if (svUsers == null || svUsers.length < 1) {
            return;
        }

        //表示するユーザ情報一覧を取得する。
        CmnUsrmDao usrmDao = new CmnUsrmDao(con__);
        List<BaseUserModel> userList = usrmDao.getSelectedUserList(svUsers);
        List<String> userNameList = new ArrayList<String>();;

        for (BaseUserModel model : userList) {
            userNameList.add(model.getUsiseimei());
        }
        paramMdl.setFil210knUserNameList(userNameList);

    }

    /**
     * <br>[機  能] 登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210knParamModel
     * @param buMdl セッションユーザモデル
     * @throws SQLException SQL実行例外
     */
    public void registerData(Fil210knParamModel paramMdl, BaseUserModel buMdl) throws SQLException {

        log__.debug("fil210knBiz Start");

        //キャビネット作成権限情報を登録する。
        __insertCrtConf(paramMdl);

        //管理者設定を登録する。
        __insertAconf(paramMdl, buMdl.getUsrsid());

    }

    /**
     * <br>[機  能] キャビネット作成権限情報を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210knParamModel
     * @throws SQLException SQL実行例外
     */
    private void __insertCrtConf(Fil210knParamModel paramMdl) throws SQLException {

        //キャビネット作成権限情報を削除する。
        FileCrtConfDao crtConfDao = new FileCrtConfDao(con__);
        crtConfDao.delete();

        int crtKbn = NullDefault.getInt(paramMdl.getFil210CrtKbn(), 0);
        String[] usrSidList = null;
        int usrKbn = 0;

        if (crtKbn == GSConstFile.CREATE_CABINET_PERMIT_GROUP) {
            usrKbn = GSConstFile.USER_KBN_GROUP;
            usrSidList = paramMdl.getFil210SvGroups();
        } else if (crtKbn == GSConstFile.CREATE_CABINET_PERMIT_USER) {
            usrKbn = GSConstFile.USER_KBN_USER;
            usrSidList = paramMdl.getFil210SvUsers();
        } else {
            return;
        }

        if (usrSidList == null || usrSidList.length < 1) {
            return;
        }

        FileCrtConfModel uconfModel = null;

        for (String usrSid : usrSidList) {
            uconfModel = new FileCrtConfModel();
            uconfModel.setUsrKbn(usrKbn);
            uconfModel.setUsrSid(Integer.parseInt(usrSid));
            crtConfDao.insert(uconfModel);
        }
    }

    /**
     * <br>[機  能] キャビネット作成権限情報を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210knParamModel
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __insertAconf(Fil210knParamModel paramMdl, int sessionUsrSid) throws SQLException {

        FileAconfModel aconf = new FileAconfModel();
        UDate now = new UDate();

        aconf.setFacFileSize(NullDefault.getInt(paramMdl.getFil210FileSize(), 0));
        aconf.setFacCrtKbn(NullDefault.getInt(paramMdl.getFil210CrtKbn(), 0));
        aconf.setFacSaveDays(NullDefault.getInt(paramMdl.getFil210SaveDays(), 0));
        aconf.setFacLockKbn(NullDefault.getInt(paramMdl.getFil210LockKbn(), 0));
        aconf.setFacVerKbn(NullDefault.getInt(paramMdl.getFil210VerKbn(), 0));
        aconf.setFacEdate(now);
        aconf.setFacEuid(sessionUsrSid);

        FileAconfDao aconfDao = new FileAconfDao(con__);
        if (aconfDao.update(aconf) < 1) {
            aconf.setFacAdate(now);
            aconf.setFacAuid(sessionUsrSid);
            aconf.setFacWarnCnt(0);
            aconfDao.insert(aconf);
        }
    }
}