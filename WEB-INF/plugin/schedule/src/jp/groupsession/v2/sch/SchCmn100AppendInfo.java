package jp.groupsession.v2.sch;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.cmn100.Cmn100AppendInfo;
import jp.groupsession.v2.cmn.cmn100.Cmn100AppendInfoModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ユーザ情報ポップアップで表示する情報を取得する
 * <br>[解  説]
 * <br>[備  考] スケジュール情報を表示する
 *
 * @author JTS
 */
public class SchCmn100AppendInfo implements Cmn100AppendInfo {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchCmn100AppendInfo.class);

    /**
     * <br>[機  能] ユーザ情報へ追加する情報を取得する。
     * <br>[解  説]
     * <br>
     * <br>[備  考]
     * @param paramMap パラメータ
     * @param usid ユーザSID
     * @param sessionUid セッションUID
     * @param gsMsg GSメッセージ
     * @param con DBコネクション
     * @return メッセージのリスト
     */
    public List<Cmn100AppendInfoModel> getAppendInfo(
            Map<String, Object> paramMap, int usid, int sessionUid,
            Connection con, GsMessage gsMsg) {

        ArrayList<Cmn100AppendInfoModel> appList = null;
        //本日のスケジュールを取得
        ScheduleSearchDao schSearchDao = new ScheduleSearchDao(con);
        UDate frDate = new UDate();
        frDate.setHour(GSConstSchedule.DAY_START_HOUR);
        frDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        UDate toDate = frDate.cloneUDate();
        toDate.setHour(GSConstSchedule.DAY_END_HOUR);
        toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_END_SECOND);
        SchDataModel schMdl = null;
        ArrayList < SchDataModel > schDataList = null;

        //本日のスケジュール
        String textTodaySch = gsMsg.getMessage("schedule.137");
        try {
            schDataList = schSearchDao.select(
                    usid,
                    GSConstSchedule.USER_KBN_USER,
                    GSConstSchedule.DSP_ALL,
                    frDate,
                    toDate,
                    GSConstSchedule.DSP_MOD_DAY,
                    sessionUid);

            log__.debug("本日のスケジュール件数==>" + schDataList.size());
            Cmn100AppendInfoModel model = new Cmn100AppendInfoModel();
            model.setOrder(2);
            model.setFilter(GSConst.BEAN_WRITE_FILTER_YES);
            model.setTitle(textTodaySch);

            ArrayList<String> msgList = new ArrayList<String>();

            //予定あり
            String textYoteiari = gsMsg.getMessage("schedule.src.9");
            SchDataPubDao sdpDao = new SchDataPubDao(con);
            for (int j = 0; j < schDataList.size(); j++) {
                //スケジュール１個
                schMdl = schDataList.get(j);
                //表示するか判定する
                if (__isDspOkSchedule(schMdl, sessionUid, con, gsMsg)) {
                    //メッセージを作成する。
                    String msg = getDspScheduleString(schMdl, frDate, toDate, sessionUid, gsMsg);
                    if (msg != null) {
                        msgList.add(msg);
                    }
                } else {
                    if (schMdl.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP
                            || schMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
                        //公開区分が所属グループのみの場合は予定有と表示
                        StringBuilder msgBuf = new StringBuilder();
                        msgBuf.append(getTimeString(schMdl, frDate, toDate, gsMsg));
                        msgBuf.append(" ");
                        msgBuf.append(textYoteiari);
                        msgList.add(msgBuf.toString());
                    }
                }
            }
            model.setTitleRow(String.valueOf(msgList.size()));
            model.setMessage(msgList);
            model.setLinkType(GSConstCommon.APPENDINFO_LINK_TYPE_SCH);
            appList = new ArrayList<Cmn100AppendInfoModel>();
            appList.add(model);
        } catch (SQLException e) {
            log__.error("スケジュール情報の取得に失敗", e);
        }

        return appList;
    }

    /**
     * セッションユーザIDから表示して良いスケジュールか判定します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param schMdl スケジュール情報
     * @param sessionUid セッションユーザID
     * @param con コネクション
     * @param gsMsg GSメッセージ
     * @return boolean true:表示OK false:表示NG
     * @throws SQLException SQL実行時例外
     */
    private boolean __isDspOkSchedule(SchDataModel schMdl, int sessionUid,
            Connection con, GsMessage gsMsg)
    throws SQLException {
        //共有範囲設定取得
        SchCommonBiz biz = new SchCommonBiz();
        SchAdmConfModel admConf = biz.getAdmConfModel(con);
        GroupBiz gpBiz = new GroupBiz();

        if (admConf.getSadCrange() == GSConstSchedule.CRANGE_SHARE_ALL) {
            //登録者・被登録者は無条件で表示
            if (schMdl.getScdAuid() == sessionUid
                    || schMdl.getScdEuid() == sessionUid
                    || schMdl.getScdUsrSid() == sessionUid) {
                return true;
            }
            //共有設定 すべて
            if (schMdl.getScdPublic() == GSConstSchedule.DSP_PUBLIC) {
                return true;
            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC) {
                return false;
            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_YOTEIARI) {
                return true;
            }  else if (schMdl.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP) {
                if (gpBiz.isBothBelongGroup(sessionUid, schMdl.getScdUsrSid(), con)) {
                  return true;
                } else {
                    return false;
                }
            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
                SchDataPubDao sdpDao = new SchDataPubDao(con);
                if (sdpDao.select(schMdl.getScdSid(), sessionUid)) {
                    return true;
                } else {
                    return false;
                }
            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_TITLE) {
                return true;
            } else {
                return false;
            }
        } else {

            return true;
        }
    }

    /**
     * スケジュール表示文字列を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]自分以外の非公開スケジュールはnullを返します
     * @param schMdl スケジュール情報
     * @param frDate 開始日付
     * @param toDate 終了日付
     * @param sessionUid セッションユーザSID
     * @param gsMsg GSメッセージ
     * @return String スケジュールメッセージ
     */
    private String getDspScheduleString(
            SchDataModel schMdl,
            UDate frDate,
            UDate toDate,
            int sessionUid,
            GsMessage gsMsg) {

        StringBuilder msgBuf = new StringBuilder();
        //予定あり
        String textYoteiari = gsMsg.getMessage("schedule.src.9");
        if (schMdl.getScdAuid() == sessionUid
                || schMdl.getScdEuid() == sessionUid
                || schMdl.getScdUsrSid() == sessionUid) {
            //登録者・被登録者の場合は表示する
            msgBuf.append(getTimeString(schMdl, frDate, toDate, gsMsg));
            msgBuf.append(" ");
            msgBuf.append(schMdl.getScdTitle());

        } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_YOTEIARI) {
            //予定あり
            msgBuf.append(getTimeString(schMdl, frDate, toDate, gsMsg));
            msgBuf.append(" ");
            msgBuf.append(textYoteiari);
        } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC
                && schMdl.getScdUsrSid() != sessionUid) {
            //非公開
            return null;
        } else {
            msgBuf.append(getTimeString(schMdl, frDate, toDate, gsMsg));
            msgBuf.append(" ");
            msgBuf.append(schMdl.getScdTitle());
        }
        return msgBuf.toString();
    }

    /**
     * <br>スケジュール時間表示を画面表示用に編集します
     * @param schMdl スケジュール情報
     * @param dFrDate 表示開始日時
     * @param dToDate 表示終了日時
     * @param gsMsg GSメッセージ
     * @return String 画面表示用時間
     */
    public static String getTimeString(
            SchDataModel schMdl, UDate dFrDate, UDate dToDate, GsMessage gsMsg) {

        StringBuilder buf = new StringBuilder();
        UDate frDate = schMdl.getScdFrDate();
        UDate toDate = schMdl.getScdToDate();
        UDate cmpToDate = null;
        if (dToDate.getIntHour() == GSConstSchedule.DAY_END_HOUR) {
            cmpToDate = dToDate.cloneUDate();
            cmpToDate.addDay(1);
            cmpToDate.setHour(GSConstSchedule.DAY_START_HOUR);
            cmpToDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
            cmpToDate.setSecond(GSConstSchedule.DAY_END_SECOND);
            cmpToDate.setMilliSecond(GSConstSchedule.DAY_END_MILLISECOND);
        } else {
            cmpToDate = dToDate.cloneUDate();
        }


        if (schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
            boolean flg = false;
            //スケジュール開始日時が表示範囲か判定
            if (frDate.betweenYMDHM(dFrDate, dToDate)) {
                buf.append(frDate.getStrHour());
                buf.append(":");
                buf.append(frDate.getStrMinute());
                buf.append("-");
                flg = true;
            }
            //スケジュール終了日時が表示範囲か判定
            if (toDate.betweenYMDHM(dFrDate, cmpToDate)) {
                if (flg == false) {
                    buf.append("-");
                }
                if (toDate.getIntHour() == GSConstSchedule.DAY_START_HOUR
                        && toDate.getIntMinute() == GSConstSchedule.DAY_START_MINUTES) {
                    buf.append("24");
                    buf.append(":");
                    buf.append("00");
                } else {
                    buf.append(toDate.getStrHour());
                    buf.append(":");
                    buf.append(toDate.getStrMinute());
                }

            }
        } else {
            buf.append(gsMsg.getMessage("schedule.7"));
        }
        log__.debug("getTimeString ==>" + buf.toString());
        return buf.toString();
    }
}
