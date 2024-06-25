package jp.groupsession.v2.sch.sch110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.rsv.rsv180.Rsv180Biz;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュールインポート画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Sch110Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv180Biz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public Sch110Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }


    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Sch110ParamModel
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void setInitData(Sch110ParamModel paramMdl, String tempDir)
        throws SQLException, IOToolsException {

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        //画面に表示するファイルのリストを作成
        ArrayList<LabelValueBean> fileLblList = new ArrayList<LabelValueBean>();

        if (fileList != null) {

            log__.debug("ファイルの数×２(オブジェクトと本体) = " + fileList.size());

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                String[] value = fileName.split(GSConstCommon.ENDSTR_OBJFILE);

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                fileLblList.add(new LabelValueBean(fMdl.getFileName(), value[0]));
                log__.debug("ファイル名 = " + fMdl.getFileName());
                log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());
            }
        }
        paramMdl.setSch110FileLabelList(fileLblList);
        //グループ・ユーザコンボ設定
//      セッション情報を取得
        BaseUserModel usModel = reqMdl_.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        __setGroupUserCombo(paramMdl, sessionUsrSid, con_);

    }

    /**
     * グループ、ユーザコンボを生成する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch110Form
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setGroupUserCombo(
            Sch110ParamModel paramMdl,
            int sessionUsrSid,
            Connection con)
    throws SQLException {

        //グループSID
        SchCommonBiz scBiz = new SchCommonBiz(reqMdl_);

        //グループコンボのラベルを取得する。
        List<SchLabelValueModel> groupLabel = scBiz.getGroupLabelForSchedule(
                sessionUsrSid, con, false, GSConstSchedule.SSP_AUTHFILTER_EDIT);

        boolean myGroupFlg = false;
        //デフォルト表示グループSID
        String dfGpSidStr = scBiz.getDispDefaultGroupSidStr(con, sessionUsrSid);
        //画面表示グループSID
        String dspGpSidStr = NullDefault.getString(paramMdl.getSch110SltGroup(), dfGpSidStr);
        dspGpSidStr = scBiz.getEnableSelectGroup(groupLabel, dspGpSidStr, dfGpSidStr);
        if (SchCommonBiz.isMyGroupSid(dspGpSidStr)) {
            myGroupFlg = true;
        }
        paramMdl.setSch110SltGroup(dspGpSidStr);
        int dspGpSid = SchCommonBiz.getDspGroupSid(dspGpSidStr);

//        Sch010Biz sch010Biz = new Sch010Biz();
//        List<SchLabelValueModel> groupLabel = sch010Biz.getGroupLabelList(con, sessionUsrSid);
        paramMdl.setSch100GroupLabel(groupLabel);
        //ユーザコンボ
        List<UsrLabelValueBean> userLabel = getUserLabelList(
                con, dspGpSid, sessionUsrSid, myGroupFlg);
        paramMdl.setSch100UserLabel(userLabel);

        //登録対象ユーザSID
        int userSid = NullDefault.getInt(
                paramMdl.getSch110SltUser(),
                sessionUsrSid);
        //表示グループに登録対象ユーザが未所属の場合、
        if (myGroupFlg) {
            CmnMyGroupMsDao mgmsDao = new CmnMyGroupMsDao(con);
            String[] users = new String[]{String.valueOf(userSid)};
            //マイグループに所属するか？
            if (mgmsDao.getMyGroupMsInfo(
                    sessionUsrSid,
                    dspGpSid,
                    users, true).size() < 1) {
                //所属しない場合、所属ユーザの先頭を設定
                paramMdl.setSch110SltUser((userLabel.get(0)).getValue());
            } else {
                paramMdl.setSch110SltUser(String.valueOf(userSid));
            }
        } else {
            CmnBelongmDao dao = new CmnBelongmDao(con);
            CmnBelongmModel mdl = dao.select(userSid, dspGpSid);
            if (mdl == null) {
                //所属しない
                paramMdl.setSch110SltUser(GSConstSchedule.USER_NOT_SELECT);
            } else {
                paramMdl.setSch110SltUser(String.valueOf(userSid));
            }
        }
    }


    /**
     * <br>[機  能] グループメンバー存在チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param sessionUsrSid ユーザSID
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateGroupMemberExistCheck(
            Sch110ParamModel paramMdl,
            int sessionUsrSid) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        SchCommonBiz scBiz = new SchCommonBiz(reqMdl_);

        String gpSidStr = paramMdl.getSch110SltGroup();
        if (gpSidStr == null || gpSidStr.length() == 0) {
            gpSidStr = scBiz.getDispDefaultGroupSidStr(con_, sessionUsrSid);
        }
        //ユーザ一覧取得
        int dspGpSid = SchCommonBiz.getDspGroupSid(gpSidStr);
        boolean myGroupFlg = SchCommonBiz.isMyGroupSid(gpSidStr);
        List<UsrLabelValueBean> userLabel = __getUserLabelList(sessionUsrSid, dspGpSid, myGroupFlg);

        SchDao sDao = new SchDao(con_);
        boolean editOk = sDao.canRegistGroupSchedule(dspGpSid, sessionUsrSid);

        if ((userLabel == null || userLabel.size() < 1) && (myGroupFlg || !editOk)) {
            //デフォルトグループを設定
            String dfGpSidStr = scBiz.getDispDefaultGroupSidStr(con_, sessionUsrSid);
            userLabel = __getUserLabelList(
                    sessionUsrSid, SchCommonBiz.getDspGroupSid(dfGpSidStr),
                    SchCommonBiz.isMyGroupSid(dfGpSidStr));
            if (userLabel == null || userLabel.size() < 1) {
                GroupBiz gbiz = new GroupBiz();
                dfGpSidStr = String.valueOf(gbiz.getDefaultGroupSid(sessionUsrSid, con_));
            }
            paramMdl.setSch110SltGroup(dfGpSidStr);

            //グループ名を取得する。
            String grpName = "";
            if (SchCommonBiz.isMyGroupSid(gpSidStr)) {
                CmnMyGroupDao mygrpDao = new CmnMyGroupDao(con_);
                CmnMyGroupModel mygrpModel = mygrpDao.getMyGroupInfo(dspGpSid);
                if (mygrpModel != null) {
                    grpName = StringUtilHtml.transToHTmlPlusAmparsant(mygrpModel.getMgpName());
                }
            } else {
                CmnGroupmDao grpDao = new CmnGroupmDao(con_);
                CmnGroupmModel grpMdl =
                        grpDao.select(SchCommonBiz.getDspGroupSid(gpSidStr));
                if (grpMdl != null) {
                    grpName = StringUtilHtml.transToHTmlPlusAmparsant(grpMdl.getGrpName());
                }
            }
            //所属ユーザなしエラー
            msg = new ActionMessage("error.user.not.exist.belong", grpName);
            StrutsUtil.addMessage(errors, msg, "not.exist");
        }
        return errors;
    }


    /**
     * <br>[機  能] 指定グループに所属するユーザリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid セッションユーザSID
     * @param groupSid グループSID
     * @param myGroupFlg マイグループ選択
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    private List<UsrLabelValueBean> __getUserLabelList(
        int userSid, int groupSid, boolean myGroupFlg) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl_);
        List < UsrLabelValueBean > labelList = null;
        UserBiz usrBiz = new UserBiz();
        if (myGroupFlg) {
            labelList = usrBiz.getMyGroupUserLabelList(con_, userSid, groupSid, null);
        } else {
            labelList = usrBiz.getNormalUserLabelList(con_, groupSid, null, false, gsMsg);
        }

        //閲覧を許可されていないユーザを除外する
        SchDao schDao = new SchDao(con_);
        List<Integer> notAccessUserList = schDao.getNotAccessUserList(userSid);
        ArrayList<UsrLabelValueBean> userLabelList = new ArrayList<UsrLabelValueBean>();
        for (UsrLabelValueBean label : labelList) {
            if (notAccessUserList.indexOf(Integer.parseInt(label.getValue())) < 0) {
                userLabelList.add(label);
            }
        }
        labelList.clear();
        labelList.addAll(userLabelList);
        return labelList;
    }


    /**
     * <br>[機  能] 指定グループに所属するユーザリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param groupSid グループSID
     * @param userSid セッションユーザSID
     * @param myGroupFlg マイグループ選択
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<UsrLabelValueBean> getUserLabelList(Connection con,
            int groupSid, int userSid, boolean myGroupFlg)
            throws SQLException {
        //指定無し
        GsMessage gsMsg = new GsMessage(reqMdl_);
        String textWithoutSpecify = gsMsg.getMessage("cmn.without.specifying");
        List < UsrLabelValueBean > labelList = null;
        UserBiz usrBiz = new UserBiz();
        if (myGroupFlg) {
            labelList = usrBiz.getMyGroupUserLabelList(con, userSid, groupSid, null);
        } else {
            labelList = usrBiz.getNormalUserLabelList(con, groupSid, null, false, gsMsg);
        }

        //閲覧を許可されていないユーザを除外する
        SchDao schDao = new SchDao(con);
        List<Integer> notAccessUserList = schDao.getNotRegistUserList(userSid);
        ArrayList<UsrLabelValueBean> userLabelList = new ArrayList<UsrLabelValueBean>();
        for (UsrLabelValueBean label : labelList) {
            if (notAccessUserList.indexOf(Integer.parseInt(label.getValue())) < 0) {
                userLabelList.add(label);
            }
        }
        labelList.clear();
        labelList.addAll(userLabelList);
        List<Integer> notAccessGroupList = schDao.getNotRegistGrpList(userSid);
        if (!myGroupFlg && !notAccessGroupList.contains(groupSid)) {
            labelList.add(0, new UsrLabelValueBean(textWithoutSpecify, "-1"));
        }
        return labelList;
    }
}