package jp.groupsession.v2.ntp.ntp020;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NippouDao;
import jp.groupsession.v2.ntp.dao.NippouSearchDao;
import jp.groupsession.v2.ntp.model.AnkenPermitCheckModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.ntp010.Ntp010Biz;
import jp.groupsession.v2.ntp.ntp010.Ntp010UsrModel;
import jp.groupsession.v2.ntp.ntp010.SimpleNippouModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 日報 月間画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp020Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp020Biz.class);
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Ntp020Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }
    /**
     * 初期表示画面情報を取得します
     * @param paramMdl Ntp020ParamModel
     * @param con コネクション
     * @return Ntp010Form アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Ntp020ParamModel getInitData(
            Ntp020ParamModel paramMdl, Connection con) throws SQLException {

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        if (StringUtil.isNullZeroStringSpace(paramMdl.getNtp010SelectUsrSid())) {
            paramMdl.setNtp010SelectUsrSid(String.valueOf(sessionUsrSid));
        }

        //表示ユーザを取得
        paramMdl.setDspMod(GSConstNippou.DSP_MOD_MONTH);
        int userSid = NullDefault.getInt(
                paramMdl.getNtp020SelectUsrSid(),
                Integer.parseInt(paramMdl.getNtp010SelectUsrSid()));
        int userKbn = GSConstNippou.USER_KBN_USER;

        log__.debug("Ntp020Biz.userSid==>" + userSid);
        log__.debug("Ntp020Biz.userKbn==>" + userKbn);

        //リクエストパラメータを取得
        //基準日
        String strDspDate = NullDefault.getString(
                paramMdl.getNtp010DspDate(), new UDate().getDateString());

        if (StringUtil.isNullZeroStringSpace(paramMdl.getNtp010DspDate())) {
            strDspDate = new UDate().getDateString();
            paramMdl.setNtp010DspDate(strDspDate);
        }
        log__.debug("表示年月日==>" + strDspDate);
        UDate dspDate = new UDate();
        dspDate.setDate(strDspDate);
        //カレンダー開始日付
        UDate frDate = __getStartCalenderDate(dspDate);
        log__.debug("カレンダー開始日付=" + frDate.getDateString());
        //カレンダー終了日付
        UDate toDate = __getEndCalenderDate(dspDate);
        log__.debug("カレンダー終了日付=" + toDate.getDateString());
        //グループコンボ生成
        NtpCommonBiz ncBiz = new NtpCommonBiz(con__, reqMdl__);
        //グループ設定
        List<NtpLabelValueModel> groupLabel = ncBiz.getGroupLabelForNippou(
                sessionUsrSid, con, false);
        paramMdl.setNtp010GpLabelList(groupLabel);

        //デフォルト表示グループ
        String dfGpSidStr = ncBiz.getDispDefaultGroupSidStr(con, sessionUsrSid);
        if (!isExistsUser(con, sessionUsrSid, dfGpSidStr)) {
            GroupBiz gbiz = new GroupBiz();
            dfGpSidStr = String.valueOf(gbiz.getDefaultGroupSid(sessionUsrSid, con));
        }

        int dfGpSid = NtpCommonBiz.getDspGroupSid(dfGpSidStr);
        int dspGpSid = 0;
        boolean myGroupFlg = false;
        //表示グループ
        if (StringUtil.isNullZeroStringSpace(paramMdl.getNtp010DspGpSid())) {
            paramMdl.setNtp010DspGpSid(dfGpSidStr);
        }
        String dspGpSidStr = NullDefault.getString(paramMdl.getNtp010DspGpSid(), dfGpSidStr);
        dfGpSidStr = ncBiz.getEnableSelectGroup(groupLabel, dspGpSidStr, dfGpSidStr);
        if (NtpCommonBiz.isMyGroupSid(dspGpSidStr)) {
            dspGpSid = NtpCommonBiz.getDspGroupSid(dspGpSidStr);
            myGroupFlg = true;
            paramMdl.setNtp010DspGpSid(dspGpSidStr);
        } else {
            dspGpSid = NullDefault.getInt(paramMdl.getNtp010DspGpSid(), dfGpSid);
            paramMdl.setNtp010DspGpSid(String.valueOf(dspGpSid));
        }
        log__.debug("paramMdl.getNtp010DspGpSid()-(1)==>" + paramMdl.getNtp010DspGpSid());

        //ユーザコンボ生成
        List<UsrLabelValueBean> userLabel = __getUserLabelList(
                con, sessionUsrSid, dspGpSid, myGroupFlg);
        paramMdl.setNtp020UsrLabelList(userLabel);
        //マイグループの場合、所属しているか判定
        if (myGroupFlg) {
            CmnMyGroupMsDao mgmsDao = new CmnMyGroupMsDao(con);
            String[] users = new String[]{String.valueOf(userSid)};
            //未所属の場合所属ユーザの先頭を設定
            if (mgmsDao.getMyGroupMsInfo(sessionUsrSid, dspGpSid, users, true).size() < 1) {
                userSid = Integer.valueOf((userLabel.get(0)).getValue());
            }
            paramMdl.setNtp020SelectUsrSid(String.valueOf(userSid));
        } else if (userKbn == GSConstNippou.USER_KBN_USER) {
            boolean userExist = false;
            if (userLabel != null && !userLabel.isEmpty()) {
                for (LabelValueBean lvb : userLabel) {
                    String lvbVal = lvb.getValue();
                    if (Integer.parseInt(lvbVal) == userSid) {
                        userExist = true;
                        break;
                    }
                }
                if (!userExist) {
                    if (!NtpCommonBiz.isMyGroupSid(paramMdl.getNtp010DspGpSid())) {
//                        userKbn = GSConstNippou.USER_KBN_GROUP;
                        userSid = Integer.parseInt(userLabel.get(0).getValue());
                    }
                }
            }
            paramMdl.setNtp020SelectUsrSid(String.valueOf(userSid));
        } else {
            paramMdl.setNtp020SelectUsrSid(String.valueOf(userSid));
        }

        //表示項目取得
        paramMdl.setNtp020StrDspDate(dspDate.getStrYear() + "年" + dspDate.getStrMonth() + "月");
        paramMdl.setNtp020NippouList(
                __getMonthNippouList(
                        paramMdl,
                        frDate.cloneUDate(),
                        toDate.cloneUDate(),
                        userSid,
                        userKbn,
                        sessionUsrSid,
                        con)
                        );

        //登録・編集権限を取得
        Ntp010Biz ntp010biz = new Ntp010Biz(con, reqMdl__);
        paramMdl.setAuthAddEditKbn(
                ntp010biz.isAddEditOk(Integer.valueOf(paramMdl.getNtp020SelectUsrSid()), con));

        CommonBiz commonBiz = new CommonBiz();
        if (commonBiz.isPluginAdmin(con, usModel, GSConstNippou.PLUGIN_ID_NIPPOU)) {
            paramMdl.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            paramMdl.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }

        paramMdl.setNtp010SessionUsrId(String.valueOf(sessionUsrSid));

        NtpCommonBiz ntBiz = new NtpCommonBiz(con__, reqMdl__);

        //案件履歴を取得
        paramMdl.setAnkenHistoryList(ntBiz.getAnkenHistoryList(con, sessionUsrSid));

        //企業・顧客履歴取得
        paramMdl.setCompanyHistoryList(ntBiz.getCompanyHistoryList(con, sessionUsrSid));

        //共有範囲を取得
        NippouDao ntpDao = new NippouDao(con);
        paramMdl.setNtp010CrangeKbn(ntpDao.getNadCrange());


        return paramMdl;
    }

    /**
     * <br>月間カレンダー表示時の開始日付を取得します
     * @param date 表示年月
     * @return UDate 開始日付
     */
    private UDate __getStartCalenderDate(UDate date) {

        UDate frDate = date.cloneUDate();
        frDate.setDay(1);
        //曜日を取得
        int week = -1;
        boolean flg = true;
        while (flg) {
            week = frDate.getWeek();
            if (week == UDate.SUNDAY) {
                flg = false;
            } else {
                frDate.addDay(-1);
            }
        }
        return frDate;
    }

    /**
     * <br>月間カレンダー表示時の終了日付を取得します
     * @param date 表示年月
     * @return UDate 終了日付
     */
    private UDate __getEndCalenderDate(UDate date) {
        UDate toDate = date.cloneUDate();
        int maxDay = toDate.getMaxDayOfMonth();
        toDate.setDay(maxDay);
        int week = -1;
        boolean flg = true;
        while (flg) {
            week = toDate.getWeek();
            if (week == UDate.SATURDAY) {
                flg = false;
            } else {
                toDate.addDay(1);
            }
        }
        return toDate;
    }

    /**
     * <br>指定ユーザの月間日報を取得します
     * @param paramMdl Ntp020ParamModel
     * @param frDate 開始日付
     * @param toDate 終了日付
     * @param usrSid ユーザSID
     * @param usrKbn ユーザ区分 0=一般ユーザ 1=グループ
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @return ArrayList グループ>指定ユーザの順に格納
     * @throws SQLException SQL実行時例外
     */
    @SuppressWarnings("all")
    private ArrayList __getMonthNippouList(
            Ntp020ParamModel paramMdl,
            UDate frDate,
            UDate toDate,
            int usrSid,
            int usrKbn,
            int sessionUsrSid,
            Connection con) throws SQLException {

        frDate.setHour(GSConstNippou.DAY_START_HOUR);
        frDate.setMinute(GSConstNippou.DAY_START_MINUTES);
        frDate.setSecond(GSConstNippou.DAY_START_SECOND);
        toDate.setHour(GSConstNippou.DAY_END_HOUR);
        toDate.setMinute(GSConstNippou.DAY_END_MINUTES);
        toDate.setSecond(GSConstNippou.DAY_END_SECOND);
        //休日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap < String, CmnHolidayModel > holMap = holDao.getHoliDayList(frDate, toDate);
        CmnHolidayModel holMdl = null;

        //グループ・指定ユーザのcolListを保持
        ArrayList<Ntp020MonthOfModel> rowList = new ArrayList<Ntp020MonthOfModel>();
        //ユーザ情報を保持
        Ntp010UsrModel usMdl = null;
        ArrayList < Ntp020DayOfModel > colList = null;
        //DB日報情報
        ArrayList < NtpDataModel > ntpDataList = null;
        //ユーザ別、１ヶ月間分の日報
        Ntp020MonthOfModel monthMdl = null;

        //指定ユーザ日報
        monthMdl = new Ntp020MonthOfModel();
        colList = new ArrayList<Ntp020DayOfModel>();
        usMdl = new Ntp010UsrModel();
        UserSearchDao usrDao = new UserSearchDao(con);

        log__.debug("usrSid=>" + usrSid);
        log__.debug("usrKbn=>" + usrKbn);
        //ユーザ日報
        CmnUsrmInfModel usrInfMdl = usrDao.getUserInfoJtkb(
                usrSid, GSConstUser.USER_JTKBN_ACTIVE);
        if (usrInfMdl != null) {
            paramMdl.setNtp020StrUserName(usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());
            usMdl.setUsrName(usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());
            usMdl.setUsrUkoFlg(usrInfMdl.getUsrUkoFlg());

        }

        usMdl.setUsrSid(usrSid);
        usMdl.setUsrKbn(GSConstNippou.USER_KBN_USER);
        monthMdl.setNtp020UsrMdl(usMdl);
        NtpCommonBiz ntpCmnBiz = new NtpCommonBiz(con, reqMdl__);
        AnkenPermitCheckModel napMdl = new AnkenPermitCheckModel(reqMdl__,
                ntpCmnBiz.isNippouAdmin(con, reqMdl__));
        //日報情報を取得(指定ユーザ)
        NippouSearchDao ntpDao = new NippouSearchDao(con);
        //グループ又はユーザの日報を取得
        ntpDataList = ntpDao.select(
                    usrSid,
                    usrKbn,
                    -1,
                    frDate,
                    toDate,
                    GSConstNippou.DSP_MOD_MONTH,
                    napMdl);
        UserSearchModel usrMdl = new UserSearchModel();
        usrMdl.setUsrSid(usrSid);
        ArrayList<UserSearchModel> belongList = new ArrayList<UserSearchModel>();
        belongList.add(usrMdl);
        Map<Integer, Integer> chkMap = ntpDao.getCheckCntMap(
                belongList,
                sessionUsrSid,
                GSConstNippou.USER_KBN_USER,
                -1,
                frDate,
                toDate,
                GSConstNippou.DSP_MOD_WEEK
                );
        Map<Integer, Integer> iinMap = ntpDao.getGoodCntMap(
                belongList,
                GSConstNippou.USER_KBN_USER,
                -1,
                frDate,
                toDate,
                GSConstNippou.DSP_MOD_WEEK
                );
        Map<Integer, Integer> cmmMap = ntpDao.getCommentCntMap(
                belongList,
                GSConstNippou.USER_KBN_USER,
                -1,
                frDate,
                toDate,
                GSConstNippou.DSP_MOD_WEEK
                );




        UDate dspDate = new UDate();
        dspDate.setDate(
                NullDefault.getString(paramMdl.getNtp010DspDate(), new UDate().getDateString()));

        CommonBiz cmnBiz = new CommonBiz();
        boolean rokuyoFlg = cmnBiz.getRokuyoDspKbn(con, sessionUsrSid);

        Ntp020DayOfModel dayMdl = null;
        ArrayList<SimpleNippouModel> dayMdlList = null;
        SimpleNippouModel dspNtpMdl = null;
        //システム日付
        UDate today = new UDate();
        while (frDate.compareDateYMD(toDate) != UDate.SMALL) {
            //１日分の日報
            dayMdlList = new ArrayList<SimpleNippouModel>();
            dayMdl = new Ntp020DayOfModel();
            // 休日名称
            holMdl = holMap.get(frDate.getDateString());
            if (holMdl != null) {
              dayMdl.setHolidayName(holMdl.getHolName());
              dayMdl.setHolidayKbn(GSConstNippou.HOLIDAY_TRUE);
            } else {
              dayMdl.setHolidayName(null);
              dayMdl.setHolidayKbn(GSConstNippou.HOLIDAY_FALSE);
            }
            //同月判定
            if (__isThisMonth(dspDate, frDate)) {
                dayMdl.setThisMonthKbn(1);
            } else {
                dayMdl.setThisMonthKbn(0);
            }
            dayMdl.setDspDay(String.valueOf(frDate.getIntDay()));
            if (rokuyoFlg) {
                dayMdl.setDspRokuyo(cmnBiz.getRokuyou(frDate));
            }
            dayMdl.setNtpDate(frDate.getDateString());
            dayMdl.setUsrSid(usrSid);
            dayMdl.setUsrKbn(usrKbn);
            dayMdl.setWeekKbn(frDate.getWeek());
            dayMdl.setIkkatuKbn(GSConstNippou.KAKUNIN_YES);
            dayMdl.setIkkatuChk(frDate.getDateString() + "-" + usrSid);
            if (today.getDateString().equals(frDate.getDateString())) {
                dayMdl.setTodayKbn(GSConstNippou.TODAY_TRUE);
            } else {
                dayMdl.setTodayKbn(GSConstNippou.TODAY_FALSE);
            }

            NtpDataModel ntpMdl = null;
            for (int j = 0; j < ntpDataList.size(); j++) {
                //日報１個
                ntpMdl = ntpDataList.get(j);
                //本日の日報か判定
                if (Ntp010Biz.isTodayNippou(ntpMdl, frDate)) {
                    dspNtpMdl = new SimpleNippouModel();
                    dspNtpMdl.setNtpSid(ntpMdl.getNipSid());
                    dspNtpMdl.setUserSid(String.valueOf(ntpMdl.getUsrSid()));
                    dspNtpMdl.setTitle(ntpMdl.getNipTitle());
                    dspNtpMdl.setTime(Ntp010Biz.getTimeString(ntpMdl, frDate));
                    dspNtpMdl.setTitleColor(ntpMdl.getNipTitleClo());
                    dspNtpMdl.setDetail(ntpMdl.getNipDetail());
                    dspNtpMdl.setUserName(ntpMdl.getntpUserName());

                    if (ntpMdl.getUsrSid() == sessionUsrSid) {
                        //本人

                    } else {
                        //他ユーザ
                        if (ntpMdl.getNipPublic() == GSConstNippou.DSP_YOTEIARI) {
                            //予定あり
                            dspNtpMdl.setTitle(GSConstNippou.DSP_YOTEIARI_STRING);
                        } else if (ntpMdl.getNipPublic() == GSConstNippou.DSP_NOT_PUBLIC) {
                            //非公開
                            continue;
                        }

                    }
                    // 追加： SimpleNippouModelに追加した区分・人数に値をセットする
                    Ntp010Biz biz = new Ntp010Biz(con__, reqMdl__);
                    //確認区分取得
                    dspNtpMdl.setNtp_chkKbn(biz.getCheckKbn(ntpMdl.getNipSid(), chkMap));
                    //コメント件数取得
                    dspNtpMdl.setNtp_cmtCnt(biz.existComment(ntpMdl.getNipSid(), cmmMap));
                    dspNtpMdl.setNtp_cmtkbn(biz.getCommentKbn(dspNtpMdl.getNtp_cmtCnt()));
                    //いいね件数取得
                    dspNtpMdl.setNtp_goodCnt(biz.existGood(ntpMdl.getNipSid(), iinMap));
                    dspNtpMdl.setNtp_goodkbn(biz.getCommentKbn(dspNtpMdl.getNtp_goodCnt()));
                    dayMdlList.add(dspNtpMdl);
                }
            }
            dayMdl.setNtpDataList(dayMdlList);
            colList.add(dayMdl);
            //日付を進める
            frDate.addDay(1);
        }
        monthMdl.setNtp020NtpList(colList);
        rowList.add(monthMdl);

        return rowList;
    }

    /**
     * <br>年月が同じが判定します
     * @param date 比較対象１
     * @param compDate 比較対象１
     * @return true:同年同月 false:同じではない
     */
    private boolean __isThisMonth(UDate date, UDate compDate) {
        if (date.equalsYear(compDate) && date.equalsMonth(compDate)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 指定グループに所属するユーザリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @param groupSid グループSID
     * @param myGroupFlg マイグループ選択

     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    private List<UsrLabelValueBean> __getUserLabelList(Connection con,
            int userSid, int groupSid, boolean myGroupFlg)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        NippouDao ntpDao = new NippouDao(con);
        List < UsrLabelValueBean > labelList = null;

        UserBiz usrBiz = new UserBiz();
        if (myGroupFlg) {
            labelList = usrBiz.getMyGroupUserLabelList(con, userSid, groupSid, null);

            //閲覧不可のユーザを除外する
            List<Integer> notAccessUserList = ntpDao.getNotAccessUserList(userSid);
            ArrayList<UsrLabelValueBean> labelList2 = new ArrayList<UsrLabelValueBean>();
            for (UsrLabelValueBean label : labelList) {
                if (notAccessUserList.indexOf(Integer.parseInt(label.getValue())) < 0) {
                    labelList2.add(label);
                }
            }
            labelList.clear();
            labelList.addAll(labelList2);

        } else {
            labelList = usrBiz.getNormalUserLabelList(con, groupSid, null, false, gsMsg);

          //閲覧不可のユーザを除外する
            List<Integer> notAccessUserList = ntpDao.getNotAccessUserList(groupSid, userSid);
            ArrayList<UsrLabelValueBean> labelList2 = new ArrayList<UsrLabelValueBean>();
            for (UsrLabelValueBean label : labelList) {
                if (notAccessUserList.indexOf(Integer.parseInt(label.getValue())) < 0) {
                    labelList2.add(label);
                }
            }
            labelList.clear();
            labelList.addAll(labelList2);

        }

        return labelList;
    }
    /**
     * <br>[機  能] 指定グループに所属するユーザがあるか
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @param groupSid グループSID
     * @return true 所属するユーザがある
     * @throws SQLException SQL実行時例外
     */
    public boolean isExistsUser(Connection con,
            int sessionUsrSid, String groupSid) throws SQLException {
        NtpCommonBiz scBiz = new NtpCommonBiz(con, reqMdl__);

        //デフォルト表示グループ
        String dfGpSidStr = scBiz.getDispDefaultGroupSidStr(con, sessionUsrSid);
        int dfGpSid = NtpCommonBiz.getDspGroupSid(dfGpSidStr);
        int dspGpSid = 0;

        boolean myGroupFlg = false;
        //表示グループ
        String dspGpSidStr = NullDefault.getString(groupSid, dfGpSidStr);

        if (NtpCommonBiz.isMyGroupSid(dspGpSidStr)) {
            dspGpSid = NtpCommonBiz.getDspGroupSid(dspGpSidStr);
            myGroupFlg = true;
        } else {
            dspGpSid = NullDefault.getInt(dspGpSidStr, dfGpSid);
        }

        //ユーザコンボ生成
        List<UsrLabelValueBean> userLabel = __getUserLabelList(
                con, sessionUsrSid, dspGpSid, myGroupFlg);

        return (userLabel != null && userLabel.size() > 0);
    }
    /**
     * <br>[機  能] グループメンバー存在チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch020ParamModel
     * @param sessionUsrSid ユーザSID
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateGroupMemberExistCheck(
            Ntp020ParamModel paramMdl,
            int sessionUsrSid) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        String dspGpSid = paramMdl.getNtp010DspGpSid();
        if (!isExistsUser(con__, sessionUsrSid,
                dspGpSid)) {
            paramMdl.setNtp010DspGpSid(null);

            //グループ名を取得する。
            String grpName = "";
            if (NtpCommonBiz.isMyGroupSid(dspGpSid)) {
                CmnMyGroupDao mygrpDao = new CmnMyGroupDao(con__);
                CmnMyGroupModel mygrpModel =
                        mygrpDao.getMyGroupInfo(NtpCommonBiz.getDspGroupSid(dspGpSid));
                if (mygrpModel != null) {
                    grpName = StringUtilHtml.transToHTmlPlusAmparsant(mygrpModel.getMgpName());
                }
            } else {
                CmnGroupmDao grpDao = new CmnGroupmDao(con__);
                CmnGroupmModel grpMdl =
                        grpDao.select(NtpCommonBiz.getDspGroupSid(dspGpSid));
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

}
