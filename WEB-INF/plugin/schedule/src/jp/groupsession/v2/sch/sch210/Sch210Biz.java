package jp.groupsession.v2.sch.sch210;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.model.SchEnumReminderTime;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataPubModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.sch040.Sch040Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュール確認ポップアップのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch210Biz {

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
     * @param reqMdl RequestModel
     */
    public Sch210Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl RequestModel
     * @param cntCon MlCountMtController
     */
    public Sch210Biz(Connection con, RequestModel reqMdl, MlCountMtController cntCon) {
        con__ = con;
        reqMdl__ = reqMdl;
        cntCon__ = cntCon;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Sch210ParamModel
     * @param con コネクション
     * @return Sch010Form アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Sch210ParamModel getInitData(Sch210ParamModel paramMdl, Connection con)
    throws SQLException {

        //管理者設定を取得
        SchCommonBiz adminbiz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel adminConf = adminbiz.getAdmConfModel(con);

        //リクエストパラメータを取得
        //表示項目設定
        Sch040Biz biz = new Sch040Biz(con__, reqMdl__, cntCon__);

        GsMessage gsMsg = new GsMessage(reqMdl__);
        //修正モード
        String scdSid = paramMdl.getSch010SchSid();
        ScheduleSearchModel scdMdl = biz.getSchData(Integer.parseInt(scdSid), adminConf,
                GSConstSchedule.SSP_AUTHFILTER_VIEW, con);
        if (scdMdl != null) {

            //閲覧不可のスケジュールかを判定する
            int sessionUserSid = reqMdl__.getSmodel().getUsrsid();
            boolean canAccess = false;
            SchDao schDao = new SchDao(con);
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                canAccess = schDao.canAccessGroupSchedule(scdMdl.getScdUsrSid(), sessionUserSid);
            } else {
                canAccess = schDao.canAccessUserSchedule(scdMdl.getScdUsrSid(), sessionUserSid);
            }

            if (!canAccess || !__chekSchPublic(con, scdMdl, reqMdl__.getSmodel().getUsrsid())) {
                //閲覧不可
                paramMdl.setSch040AddDate(null);
                paramMdl.setSch040DataFlg(false);
            } else {
                SchEnumRemindMode remindMode = SchEnumRemindMode.valueOf(scdMdl.getScdUsrKbn(),
                        sessionUserSid,
                        scdMdl.getScdUsrSid()
                        );
                paramMdl.setSch040ReminderEditMode(remindMode);

                if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                    paramMdl.setSch040UsrName(
                            biz.getUsrName(scdMdl.getScdUsrSid(), scdMdl.getScdUsrKbn(), con));
                } else {
                    paramMdl.setSch040UsrName(scdMdl.getScdUsrSei() + " " + scdMdl.getScdUsrMei());

                    if (sessionUserSid == scdMdl.getScdUsrSid()) {
                        paramMdl.setSch210ReminderTimeText(gsMsg.getMessage("cmn.dont.notify"));

                        boolean pushUseFlg = PushServiceOperator.getInstance(
                                con__, reqMdl__.getDomain()).isUseable();

                        List<LabelValueBean> reminderTimeList =
                                SchEnumReminderTime.labelList(gsMsg);

                        for (LabelValueBean reminderTime : reminderTimeList) {
                            if ((scdMdl.getScdReminder()
                                    == GSConstSchedule.REMINDER_TIME_NO)) {
                                paramMdl.setSch210ReminderTimeText(
                                        gsMsg.getMessage("cmn.dont.notify"));
                            } else if (scdMdl.getScdReminder()
                                    == NullDefault.getInt(reminderTime.getValue(), -1)) {
                                paramMdl.setSch210ReminderTimeText(gsMsg.getMessage(
                                        "cmn.reminder.notify.text",
                                        new String[] {reminderTime.getLabel()}));
                            }
                        }
                    }
                }

                //ユーザ区分
                paramMdl.setSch010SelectUsrKbn(String.valueOf(scdMdl.getScdUsrKbn()));

                //登録者
                paramMdl.setSch040AddUsrName(scdMdl.getScdAuidSei() + " " + scdMdl.getScdAuidMei());
                paramMdl.setSch040AddUsrJkbn(String.valueOf(scdMdl.getScdAuidJkbn()));
                //登録日時
                String textAddDate = gsMsg.getMessage("schedule.src.84");
                paramMdl.setSch040AddDate(
                        textAddDate + " : "
                        + UDateUtil.getSlashYYMD(scdMdl.getScdAdate())
                        + " "
                        + UDateUtil.getSeparateHM(scdMdl.getScdAdate()));
                //閲覧モード
                paramMdl.setSch040knIsEdit(GSConstSchedule.CAN_EDIT_FALSE);
                //表示項目をDBから取得
                __setSch040knFormFromDb(paramMdl, scdMdl, con);
                paramMdl.setSch040DataFlg(true);
            }
        } else {
            paramMdl.setSch040AddDate(null);
            paramMdl.setSch040DataFlg(false);
        }


        return paramMdl;
    }

    /**
     * <br>DBから画面項目を設定する
     * @param paramMdl Sch210ParamModel
     * @param scdMdl スケジュール情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setSch040knFormFromDb(
            Sch210ParamModel paramMdl,
            ScheduleSearchModel scdMdl,
            Connection con) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //開始日時
        UDate frDate = scdMdl.getScdFrDate();
        StringBuilder frBuf = new StringBuilder();
        StringBuilder toBuf = new StringBuilder();
        frBuf.append(
                gsMsg.getMessage("cmn.date4",
                                            new String[] {
                                                String.valueOf(frDate.getYear()),
                                                String.valueOf(frDate.getMonth()),
                                                String.valueOf(frDate.getIntDay())
                                                }));
        UDate toDate = scdMdl.getScdToDate();
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
        //同時登録者
        paramMdl.setSch040AddedUsrLabel(scdMdl.getUsrInfList());
        //リマインダー通知項目
        paramMdl.setSch040TargetGroup(String.valueOf(scdMdl.getScdTargetGrp()));
        paramMdl.setSch040ReminderTime(String.valueOf(scdMdl.getScdReminder()));

        int selectUsrSid = scdMdl.getScdUsrSid();
        BaseUserModel usMdl = reqMdl__.getSmodel();
        int sessionUsrSid = usMdl.getUsrsid();
        int usrKbn = NullDefault.getInt(paramMdl.getSch010SelectUsrKbn(), 0);
        
        SchEnumRemindMode remindMode = SchEnumRemindMode.valueOf(
                usrKbn,
                sessionUsrSid,
                selectUsrSid);

        paramMdl.setSch040ReminderEditMode(remindMode);

        //添付ファイル情報
        SchBinDao sbDao = new SchBinDao(con);
        paramMdl.setSch040knFileList(sbDao.getBinInfo(scdMdl.getScdSid()));

        //公開対象
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
            List<UsrLabelValueBean> dspTarget
                = cmnBiz.getUserLabelList(
                        con, targetSidList.toArray(new String[targetSidList.size()]));
            paramMdl.setSch040knDspTarget(dspTarget);
        }
    }

    /**
     * <br>[機  能] スケジュール公開対象を確認し、対象スケジュールを閲覧可能か判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param scdMdl スケジュール情報
     * @param sessionUsrSid セッションユーザSID
     * @return 判定結果 true: 閲覧可能、false: 閲覧不可
     */
    private boolean __chekSchPublic(Connection con, ScheduleSearchModel scdMdl,
                                    int sessionUsrSid)
    throws SQLException {

        if (scdMdl == null) {
            return false;
        }

        //公開区分 = 公開の場合、閲覧可能
        if (scdMdl.getScdPublic() == GSConstSchedule.DSP_PUBLIC) {
            return true;
        }

        //公開区分 = 非公開 or 予定あり or タイトルのみの場合
        if (scdMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC
                || scdMdl.getScdPublic() == GSConstSchedule.DSP_YOTEIARI
                || scdMdl.getScdPublic() == GSConstSchedule.DSP_TITLE) {

            //登録者本人、また被登録者の場合、閲覧可能
            if (scdMdl.getScdAuid() == sessionUsrSid
                || scdMdl.getScdEuid() == sessionUsrSid
                || scdMdl.getScdUsrSid() == sessionUsrSid) {
                return true;
            }
        }

        //公開範囲が所属グループのみの場合
        if (scdMdl.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP) {
            GroupBiz grpBiz = new GroupBiz();

            //登録者本人ならば閲覧可能
            if (scdMdl.getScdAuid() == sessionUsrSid
                    || scdMdl.getScdEuid() == sessionUsrSid) {
                return true;
            }
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                //自分も所属しているなら編集可能
                if (scdMdl.getScdUsrSid() == sessionUsrSid) {
                    //被登録者ならば閲覧可能
                    return true;
                }
                if (grpBiz.isBothBelongGroup(scdMdl.getScdUsrSid(), sessionUsrSid, con)) {
                    return true;
                }
            } else {
                //所属グループならば編集可能
                if (grpBiz.isBelongGroup(sessionUsrSid, scdMdl.getScdUsrSid(), con)) {
                    return true;
                }
            }
        }
        
        //公開範囲が指定グループ・ユーザのみの場合
        if (scdMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
            SchDataPubDao sdpDao = new SchDataPubDao(con);
            return sdpDao.select(scdMdl.getScdSid(), sessionUsrSid);
        }

        return false;
    }
}
