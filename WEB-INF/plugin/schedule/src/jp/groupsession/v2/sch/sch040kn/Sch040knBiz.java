package jp.groupsession.v2.sch.sch040kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.rap.mbh.push.IPushServiceOperator;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataPubModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.sch.sch040.Sch040Biz;
import jp.groupsession.v2.sch.sch040.model.Sch040AttendModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュール確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch040knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch040knBiz.class);
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスト情報 */
    public RequestModel reqMdl__ = null;
    /** 採番コントローラ */
    public MlCountMtController cntCon__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Sch040knBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl リクエスト情報
     * @param cntCon MlCountMtController
     */
    public Sch040knBiz(Connection con, RequestModel reqMdl, MlCountMtController cntCon) {
        con__ = con;
        reqMdl__ = reqMdl;
        cntCon__ = cntCon;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Sch040knParamModel
     * @param con コネクション
     * @return アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Sch040knParamModel getInitData(Sch040knParamModel paramMdl, Connection con)
    throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();

        //管理者設定を取得
        SchCommonBiz adminbiz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel adminConf = adminbiz.getAdmConfModel(con);

        //リクエストパラメータを取得
        //表示項目設定
        Sch040Biz biz = new Sch040Biz(con__, reqMdl__, cntCon__);
        if (paramMdl.getCmd().equals(GSConstSchedule.CMD_ADD)) {
//          新規モード
            //名前
            String uid = paramMdl.getSch010SelectUsrSid();
            String ukb = paramMdl.getSch010SelectUsrKbn();
            CmnUsrmDao cmDao = new CmnUsrmDao(con);
            CmnUsrmModel cuMdl = cmDao.select(Integer.valueOf(uid));
            log__.debug("uid=" + uid);
            log__.debug("ukb=" + ukb);
            paramMdl.setSch040UsrName(
                    biz.getUsrName(Integer.parseInt(uid), Integer.parseInt(ukb), con));
            if (cuMdl != null) {
                paramMdl.setSch040UsrUkoFlg(cuMdl.getUsrUkoFlg());
            }
            //登録者
            paramMdl.setSch040AddUsrName(usModel.getUsisei() + " " + usModel.getUsimei());
            //画面項目設定
            paramMdl.setSch040knIsEdit(GSConstSchedule.CAN_EDIT_TRUE);
            __setSch040knFormFromReq(paramMdl);

            CmnUsrmInfDao uinfDao = new CmnUsrmInfDao(con);
            //追加済みユーザSID
            ArrayList<Integer> list = null;
            ArrayList<CmnUsrmInfModel> selectUsrList = null;
            String[] users = paramMdl.getSv_users();
            if (users != null && users.length > 0) {
                list = new ArrayList<Integer>();
                for (int i = 0; i < users.length; i++) {
                    list.add(new Integer(users[i]));
                }
                selectUsrList = uinfDao.getUserList(list);
            }
            //同時登録ユーザラベル
            paramMdl.setSch040SelectUsrLabel(selectUsrList);
        } else if (paramMdl.getCmd().equals(GSConstSchedule.CMD_EDIT)) {
            GsMessage gsMsg = new GsMessage(reqMdl__);
//          閲覧モード
            String scdSid = paramMdl.getSch010SchSid();
            ScheduleSearchModel scdMdl = biz.getSchData(Integer.parseInt(scdSid),
                    adminConf,
                    GSConstSchedule.SSP_AUTHFILTER_VIEW,
                    con);
            if (scdMdl != null) {
                //対象のスケジュールを閲覧可能かを判定する
                int sessionUserSid = usModel.getUsrsid();
                SchCommonBiz schCmnBiz = new SchCommonBiz();
                if (!schCmnBiz.canAccessSchedule(con, scdMdl, sessionUserSid)) {
                    paramMdl.setSch040ViewFlg(false);
                } else {
                    //ユーザ名
                    if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                        paramMdl.setSch040UsrName(
                                biz.getUsrName(scdMdl.getScdUsrSid(), scdMdl.getScdUsrKbn(), con));

                    } else {
                        paramMdl.setSch040UsrName(
                                scdMdl.getScdUsrSei() + " " + scdMdl.getScdUsrMei());
                        paramMdl.setSch040UsrUkoFlg(scdMdl.getScdUsrUkoFlg());
                    }
                    //リマインダー通知
                    int usrKbn = NullDefault.getInt(paramMdl.getSch010SelectUsrKbn(), 0);
                    int selectUsrSid = NullDefault.getInt(paramMdl.getSch010SelectUsrSid(), -1);
                    BaseUserModel usMdl = reqMdl__.getSmodel();
                    int sessionUsrSid = usMdl.getUsrsid();
                    SchEnumRemindMode remindMode = SchEnumRemindMode.valueOf(usrKbn,
                            sessionUsrSid,
                            selectUsrSid);

                    paramMdl.setSch040ReminderEditMode(remindMode);
                    
                    //登録者
                    paramMdl.setSch040AddUsrName(
                            scdMdl.getScdAuidSei() + " " + scdMdl.getScdAuidMei());
                    paramMdl.setSch040AddUsrJkbn(String.valueOf(scdMdl.getScdAuidJkbn()));
                    paramMdl.setSch040AddUsrUkoFlg(scdMdl.getRegistUsrUkoFlg());
                    //登録日時
                    String textAddDate = gsMsg.getMessage("schedule.src.84");
                    paramMdl.setSch040AddDate(
                            textAddDate + " : "
                            + UDateUtil.getSlashYYMD(scdMdl.getScdAdate())
                            + " "
                            + UDateUtil.getSeparateHM(scdMdl.getScdAdate()));
                    //閲覧モードチェック
                    Sch010Biz sch010biz = new Sch010Biz(reqMdl__);
                    if (sch010biz.isEditOk(Integer.parseInt(scdSid), reqMdl__, con, false)) {
                        paramMdl.setSch040knIsEdit(GSConstSchedule.CAN_EDIT_TRUE);
                        //表示項目をアクションフォームから取得
                        __setSch040knFormFromReq(paramMdl);

                    } else {
                        //閲覧モード
                        paramMdl.setSch040knIsEdit(GSConstSchedule.CAN_EDIT_FALSE);
                        //表示項目をDBから取得
                        __setSch040knFormFromDb(paramMdl, scdMdl, con);
                        __setFileInfo(paramMdl, con, Integer.parseInt(scdSid));
                    }
                    paramMdl.setSch040DataFlg(true);
                    paramMdl.setSch040ViewFlg(true);
                }
            } else {
                paramMdl.setSch040DataFlg(false);
                paramMdl.setSch040ViewFlg(true);
            }

        }
        return paramMdl;
    }


    /**
     * <br>リクエストパラメータから画面項目を設定する
     * @param paramMdl Sch040knParamModel
     */
    private void __setSch040knFormFromReq(Sch040knParamModel paramMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl__);

        //開始日時
        StringBuilder frBuf = new StringBuilder();
        SchCommonBiz schBiz = new SchCommonBiz();
        List<String> frDateList = schBiz.getDateList(paramMdl.getSch040FrDate());
        frBuf.append(gsMsg.getMessage("cmn.date4",
                new String[] {frDateList.get(0),
                        frDateList.get(1),
                        frDateList.get(2)}));

        //終了日時
        StringBuilder toBuf = new StringBuilder();
        List<String> toDateList = schBiz.getDateList(paramMdl.getSch040ToDate());
        toBuf.append(gsMsg.getMessage("cmn.date4",
                new String[] {toDateList.get(0),
                        toDateList.get(1),
                        toDateList.get(2)}));

        //時間指定無し判定
        if (!paramMdl.getSch040FrHour().equals("-1")
                || !paramMdl.getSch040FrMin().equals("-1")
                || !paramMdl.getSch040ToHour().equals("-1")
                || !paramMdl.getSch040ToMin().equals("-1")
        ) {
            //時
            //From時間指定有り
            if (!paramMdl.getSch040FrHour().equals("-1")
             && !paramMdl.getSch040FrMin().equals("-1")) {
                String[] params = {paramMdl.getSch040FrHour(),
                                    StringUtil.toDecFormat(paramMdl.getSch040FrMin(), "00")};
                frBuf.append(gsMsg.getMessage("cmn.time.input", params));
            } else {
                //省略
                String[] params = {"0", "00"};
                frBuf.append(gsMsg.getMessage("cmn.time.input", params));
            }
            //To時間指定有り
            if (!paramMdl.getSch040ToHour().equals("-1")
             && !paramMdl.getSch040ToMin().equals("-1")) {

                String[] params = {paramMdl.getSch040ToHour(),
                        StringUtil.toDecFormat(paramMdl.getSch040ToMin(), "00")};
                toBuf.append(gsMsg.getMessage("cmn.time.input", params));
            } else {
                //省略
                String[] params = {"23", "59"};
                toBuf.append(gsMsg.getMessage("cmn.time.input", params));
            }
        }
        //開始日時
        paramMdl.setSch040knFromDate(frBuf.toString());
        //終了日時
        paramMdl.setSch040knToDate(toBuf.toString());
    }

    /**
     * <br>DBから画面項目を設定する
     * @param paramMdl Sch040knParamModel
     * @param scdMdl スケジュール情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setSch040knFormFromDb(
            Sch040knParamModel paramMdl,
            ScheduleSearchModel scdMdl,
            Connection con) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //開始日時
        UDate frDate = scdMdl.getScdFrDate();
        StringBuilder frBuf = new StringBuilder();
        frBuf.append(
                gsMsg.getMessage("cmn.date4",
                                            new String[] {
                                            String.valueOf(frDate.getYear()),
                                            String.valueOf(frDate.getMonth()),
                                            String.valueOf(frDate.getIntDay())
                                            }));

        //終了日時
        UDate toDate = scdMdl.getScdToDate();
        StringBuilder toBuf = new StringBuilder();
        toBuf.append(
                gsMsg.getMessage("cmn.date4",
                                            new String[] {
                                            String.valueOf(toDate.getYear()),
                                            String.valueOf(toDate.getMonth()),
                                            String.valueOf(toDate.getIntDay())
                                            }));

        //時間指定無し判定
        if (scdMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
            //時
            String[] paramsFr = {String.valueOf(frDate.getIntHour()),
                    StringUtil.toDecFormat(frDate.getIntMinute(), "00")};
            frBuf.append(gsMsg.getMessage("cmn.time.input", paramsFr));

            String[] paramsTo = {String.valueOf(toDate.getIntHour()),
                    StringUtil.toDecFormat(toDate.getIntMinute(), "00")};
            toBuf.append(gsMsg.getMessage("cmn.time.input", paramsTo));
        }
        //開始日時
        paramMdl.setSch040knFromDate(frBuf.toString());
        //終了日時
        paramMdl.setSch040knToDate(toBuf.toString());
        paramMdl.setSch040Bgcolor(String.valueOf(scdMdl.getScdBgcolor()));
        paramMdl.setSch040Title(scdMdl.getScdTitle());
        paramMdl.setSch040Value(scdMdl.getScdValue());
        paramMdl.setSch040Biko(scdMdl.getScdBiko());
        paramMdl.setSch040Public(String.valueOf(scdMdl.getScdPublic()));
        paramMdl.setSch040Edit(String.valueOf(scdMdl.getScdEdit()));
        paramMdl.setSch040TargetGroup(String.valueOf(scdMdl.getScdTargetGrp()));
        //同時登録者
        ArrayList<CmnUsrmInfModel> sameUserList = scdMdl.getUsrInfList();
        paramMdl.setSch040AddedUsrLabel(sameUserList);

        //出欠確認区分
        int attendKbn = scdMdl.getScdAttendKbn();
        paramMdl.setSch040AttendKbn(
                NullDefault.getString(paramMdl.getSch040AttendKbn(),
                        String.valueOf(attendKbn)));

        //出欠回答区分
        paramMdl.setSch040AttendAnsKbn(
                NullDefault.getString(paramMdl.getSch040AttendAnsKbn(),
                        String.valueOf(scdMdl.getScdAttendAns())));

        //出欠登録者区分
        int attendAnsUsrKbn = scdMdl.getScdAttendAuKbn();
        //スケジュール編集画面 表示モード
        Sch040Biz biz = new Sch040Biz(con, reqMdl__);
        int editDspMode = biz.getEditDspMode(attendKbn, attendAnsUsrKbn);
        paramMdl.setSch040EditDspMode(String.valueOf(editDspMode));

        if (editDspMode != GSConstSchedule.EDIT_DSP_MODE_NORMAL) {
            //出欠確認回答一覧
            ArrayList<Sch040AttendModel> ansList =
                    biz.getAttendAnsList(scdMdl.getScdGrpSid());
            //出欠確認回答一覧 全て表示リンク 表示フラグ
            if (ansList.size() > GSConstSchedule.ATTEND_LIST_MAX_NUM) {
                paramMdl.setSch040AttendLinkFlg(1);
                paramMdl.setSch040AttendAnsList(
                        ansList.subList(0, GSConstSchedule.ATTEND_LIST_MAX_NUM));
            } else {
                paramMdl.setSch040AttendLinkFlg(0);
                paramMdl.setSch040AttendAnsList(ansList);

            }
        }
        
        if (scdMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
            SchDataPubDao sdpDao = new SchDataPubDao(con); 
            List<SchDataPubModel> sdpMdlList = sdpDao.getDspTarget(scdMdl.getScdSid());
            List<String> targetSidList = new ArrayList<String>();
            for (SchDataPubModel sdpMdl : sdpMdlList) {
                String sid = String.valueOf(sdpMdl.getSdpPsid());
                if (sdpMdl.getSdpType() == GSConstSchedule.SDP_TYPE_GROUP) {
                    sid = "G" + sid;
                }
                targetSidList.add(sid);
            }
            CommonBiz cmnBiz = new CommonBiz();
            List<UsrLabelValueBean> dspTarget = cmnBiz.getUserLabelList(con, targetSidList.toArray(new String[0]));
            paramMdl.setSch040knDspTarget(dspTarget);
        }
    }
    
    /**
     * <br>
     * @param paramMdl Sch040knParamModel
     * @param scdMdl スケジュール情報
     * @param con コネクション
     * @param scdSid スケジュールSID
     * @throws SQLException SQL実行時例外
     */
    private void __setFileInfo(
            Sch040knParamModel paramMdl, Connection con, int scdSid) throws SQLException {
        
        SchBinDao sbDao = new SchBinDao(con);
        paramMdl.setSch040knFileList(sbDao.getBinInfo(scdSid));
    }
}
