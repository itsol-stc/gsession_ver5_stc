package jp.groupsession.v2.ntp.ntp010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.SimpleCalenderModel;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NippouDao;
import jp.groupsession.v2.ntp.dao.NippouSearchDao;
import jp.groupsession.v2.ntp.dao.NtpKakuninDao;
import jp.groupsession.v2.ntp.model.AnkenPermitCheckModel;
import jp.groupsession.v2.ntp.model.NippouSearchModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.ntp.model.NtpKakuninModel;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.model.NtpPriConfModel;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 日報 週間画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp010Biz.class);
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
    public Ntp010Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @return Ntp010ParamModel アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Ntp010ParamModel getInitData(
            Ntp010ParamModel paramMdl, Connection con) throws SQLException {
        log__.debug("初期表示開始");

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID


        //個人設定取得&作成
        NtpPriConfModel confMdl = getPrivateConf(sessionUsrSid, con);
        paramMdl.setNtp010Reload(confMdl.getNprAutoReload());

        //リクエストパラメータを取得
        //表示開始日
        if (StringUtil.isNullZeroStringSpace(paramMdl.getNtp010DspDate())) {
            paramMdl.setNtp010DspDate(new UDate().getDateString());
        }
        String strDspDate = NullDefault.getString(
                paramMdl.getNtp010DspDate(), new UDate().getDateString());
        UDate dspDate = new UDate();
        dspDate.setDate(strDspDate);

        //表示項目取得
        paramMdl.setNtp010DspDate(dspDate.getDateString());

        if (confMdl.getNprDspPosition() == GSConstNippou.DAY_POSITION_RIGHT) {
            //選択日付を右端へ移動
            dspDate.addDay(GSConstNippou.DAY_POSITION_RIGHT_PARAM);
        }

        //個人設定よりデフォルト表示グループSIDを取得する。
        //データが存在しない場合、グループが削除されていた場合はデフォルト所属グループを返す
        NtpCommonBiz ntBiz = new NtpCommonBiz(con__, reqMdl__);
        //グループ設定
        List<NtpLabelValueModel> groupLabel = ntBiz.getGroupLabelForNippou(
                sessionUsrSid, con, false);
        paramMdl.setNtp010GpLabelList(groupLabel);

        //デフォルト表示グループ
        String dfGpSidStr = ntBiz.getDispDefaultGroupSidStr(con, sessionUsrSid);
        int dfGpSid = NtpCommonBiz.getDspGroupSid(dfGpSidStr);
        boolean myGroupFlg = false;
        int dspGpSid = 0;
        //表示グループ
        if (StringUtil.isNullZeroStringSpace(paramMdl.getNtp010DspGpSid())) {
            paramMdl.setNtp010DspGpSid(dfGpSidStr);
        }
        String dspGpSidStr = NullDefault.getString(paramMdl.getNtp010DspGpSid(), dfGpSidStr);
        dspGpSidStr = ntBiz.getEnableSelectGroup(groupLabel, dspGpSidStr, dfGpSidStr);

        if (NtpCommonBiz.isMyGroupSid(dspGpSidStr)) {
            dspGpSid = NtpCommonBiz.getDspGroupSid(dspGpSidStr);
            myGroupFlg = true;
            paramMdl.setNtp010DspGpSid(dspGpSidStr);
        } else {
            dspGpSid = NullDefault.getInt(paramMdl.getNtp010DspGpSid(), dfGpSid);

            //共有チェック
            if (!ntBiz.checkGroupSidStr(con__, sessionUsrSid, String.valueOf(dspGpSid))
                    && !ntBiz.checkSpAccessDsp(sessionUsrSid, String.valueOf(dspGpSid), con__)) {
                dspGpSid = Integer.valueOf(dfGpSidStr);
            }
            paramMdl.setNtp010DspGpSid(dspGpSidStr);
        }

        paramMdl.setNtp010StrDspDate(dspDate.getStrYear() + "年" + dspDate.getStrMonth() + "月");
        ArrayList<SimpleCalenderModel> weekCalender = getWeekCalender(dspDate.cloneUDate(), con);
        paramMdl.setNtp010CalendarList(weekCalender);
        //本人の日報データを取得する。
        paramMdl.setNtp010TopList(
                getWeekNippouTopList(
                        dspDate.cloneUDate(), dspGpSid, sessionUsrSid, con, sessionUsrSid));


        //グループメンバーの日報データを取得する。
        paramMdl.setNtp010BottomList(
                __getWeekNippouBottomList(
                        dspDate.cloneUDate(), dspGpSid, sessionUsrSid, myGroupFlg, con));

        CommonBiz commonBiz = new CommonBiz();
        if (commonBiz.isPluginAdmin(con, usModel, GSConstNippou.PLUGIN_ID_NIPPOU)) {
            paramMdl.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            paramMdl.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }

        paramMdl.setNtp010SessionUsrId(String.valueOf(sessionUsrSid));

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
     * <br>[機  能] 表示グループ用のグループリストを取得する
     * <br>[解  説] 管理者設定の共有範囲が「ユーザ全員で共有」の場合有効な全てのグループを取得する。
     * <br>「所属グループ内のみ共有可」の場合、ユーザが所属するグループのみを返す。
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<NtpLabelValueModel> getGroupLabelList(Connection con,
            int usrSid) throws SQLException {

        List < NtpLabelValueModel > labelList = null;

        NtpCommonBiz ntpBiz = new NtpCommonBiz(con__, reqMdl__);
        labelList = ntpBiz.getGroupLabelForNippou2(
                usrSid, con, false);

        return labelList;
    }
    /**
     * <br>UDateの曜日定数から曜日文字を取得する
     * @param week UDateの曜日定数
     * @return String 曜日
     */
    public static String getStrWeek(int week) {
        String str = "";
        switch (week) {
        case UDate.SUNDAY:
            str = "日";
            break;
        case UDate.MONDAY:
            str = "月";
            break;
        case UDate.TUESDAY:
            str = "火";
            break;
        case UDate.WEDNESDAY:
            str = "水";
            break;
        case UDate.THURSDAY:
            str = "木";
            break;
        case UDate.FRIDAY:
            str = "金";
            break;
        case UDate.SATURDAY:
            str = "土";
            break;
        default:
            break;
        }
        return str;
    }

    /**
     * <br>指定日付からプラス６日分のカレンダーを取得する
     * @param dspDate 指定日付
     * @param con コネクション
     * @return ArrayList １週間分のカレンダー
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<SimpleCalenderModel> getWeekCalender(UDate dspDate,
            Connection con) throws SQLException {
        if (dspDate == null) {
            return null;
        }

        ArrayList<SimpleCalenderModel> calList =
            new ArrayList<SimpleCalenderModel>(GSConstNippou.WEEK_DAY_COUNT);
        //休日情報を取得する
        UDate frDate = dspDate.cloneUDate();
        frDate.setHour(GSConstNippou.DAY_START_HOUR);
        frDate.setMinute(GSConstNippou.DAY_START_MINUTES);
        frDate.setSecond(GSConstNippou.DAY_START_SECOND);
        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(GSConstNippou.WEEK_DAY_COUNT - 1);
        toDate.setHour(GSConstNippou.DAY_END_HOUR);
        toDate.setMinute(GSConstNippou.DAY_END_MINUTES);
        toDate.setSecond(GSConstNippou.DAY_END_SECOND);
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap < String, CmnHolidayModel > holMap = holDao.getHoliDayList(frDate, toDate);
        CmnHolidayModel holMdl = null;
        CommonBiz cmnBiz = new CommonBiz();
        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid();
        boolean rokuyoDspKbn = cmnBiz.getRokuyoDspKbn(con, sessionUsrSid);

        UDate today = new UDate();
        //1週間分のカレンダーを設定
        SimpleCalenderModel calMdl = null;
        for (int i = 0; i < GSConstNippou.WEEK_DAY_COUNT; i++) {
            calMdl = new SimpleCalenderModel();
            calMdl.setDspDate(dspDate.getDateString());
            calMdl.setWeekKbn(String.valueOf(dspDate.getWeek()));
            calMdl.setDspDayString(
                    String.valueOf(dspDate.getIntDay())
                    + "日"
                    + "("
                    + getStrWeek(dspDate.getWeek())
                    + ")");
            if (rokuyoDspKbn) {
                String rokuyo = cmnBiz.getRokuyou(dspDate);
                calMdl.setRokuyoKbn(cmnBiz.setRkyKbn(rokuyo));
            }

            //休日情報を設定
            holMdl = holMap.get(dspDate.getDateString());
            if (holMdl != null) {
                calMdl.setHolidayKbn(String.valueOf(GSConstNippou.HOLIDAY_TRUE));
            } else {
                calMdl.setHolidayKbn(String.valueOf(GSConstNippou.HOLIDAY_FALSE));
            }
            //今日区分
            if (dspDate.getDateString().equals(today.getDateString())) {
                calMdl.setTodayKbn(String.valueOf(GSConstNippou.TODAY_TRUE));
            } else {
                calMdl.setTodayKbn(String.valueOf(GSConstNippou.TODAY_FALSE));
            }

            calList.add(calMdl);
            dspDate.addDay(1);
        }
        return calList;
    }

    /**
     * <br>指定ユーザの週間日報データを取得します
     * @param dspDate 開始日付
     * @param grpSid グループSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @return ArrayList グループ>指定ユーザの順に格納
     * @throws SQLException SQL実行時例外
     */
    @SuppressWarnings("all")
    public ArrayList getWeekNippouTopList(
            UDate dspDate,
            int grpSid,
            int usrSid,
            Connection con,
            int sessionUsrSid) throws SQLException {

        UDate frDate = dspDate.cloneUDate();
        frDate.setHour(GSConstNippou.DAY_START_HOUR);
        frDate.setMinute(GSConstNippou.DAY_START_MINUTES);
        frDate.setSecond(GSConstNippou.DAY_START_SECOND);

        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(GSConstNippou.WEEK_DAY_COUNT - 1);
        toDate.setHour(GSConstNippou.DAY_END_HOUR);
        toDate.setMinute(GSConstNippou.DAY_END_MINUTES);
        toDate.setSecond(GSConstNippou.DAY_END_SECOND);

        //指定ユーザのcolListを保持
        ArrayList<Ntp010WeekOfModel> rowList = new ArrayList<Ntp010WeekOfModel>();
        //ユーザ情報を保持
        Ntp010UsrModel usMdl = null;
        ArrayList<Ntp010DayOfModel> colList = null;
        //DB日報情報
        ArrayList < NtpDataModel > ntpDataList = null;
        //ユーザ別、１週間分の日報データ
        Ntp010WeekOfModel weekMdl = null;

        //休日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayList(frDate, toDate);
        CmnHolidayModel holMdl = null;

        Ntp010DayOfModel dayMdl = null;
        ArrayList<SimpleNippouModel> dayMdlList = null;
        SimpleNippouModel dspNtpMdl = null;

        UDate date = dspDate.cloneUDate();
        NippouSearchDao ntpDao = new NippouSearchDao(con);

        //指定ユーザ
        weekMdl = new Ntp010WeekOfModel();
        colList = new ArrayList<Ntp010DayOfModel>();
        usMdl = new Ntp010UsrModel();
        UserSearchDao usrDao = new UserSearchDao(con);
        UserSearchModel usrInfMdl = usrDao.getUserInfoJtkb(
                usrSid, GSConstUser.USER_JTKBN_ACTIVE);
        usMdl.setUsrName(usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());
        usMdl.setUsrUkoFlg(usrInfMdl.getUsrUkoFlg());
        usMdl.setUsrSid(usrSid);
        usMdl.setUsrKbn(GSConstNippou.USER_KBN_USER);
        usMdl.setZaisekiKbn(usrInfMdl.getUioStatus());
        usMdl.setZaisekiMsg(usrInfMdl.getUioComment());
        //ショートメールプラグインを使用していないユーザを除外する。
        //送信制限されているユーザを除外する。
        List<Integer> smlUsrs = new ArrayList<Integer>();
        smlUsrs.add(usrSid);
        CommonBiz commonBiz = new CommonBiz();
        smlUsrs = (ArrayList<Integer>) commonBiz.getCanUseSmailUser(con, smlUsrs);
        SmlCommonBiz smlCommonBiz = new SmlCommonBiz(con, reqMdl__);
        smlUsrs = smlCommonBiz.getValiableDestUsrSid(con,
                reqMdl__.getSmodel().getUsrsid(), smlUsrs);
        //ショートメール有効無効設定
        if (!smlUsrs.contains(usMdl.getUsrSid())) {
            usMdl.setSmlAble(0);
        } else {
            usMdl.setSmlAble(1);
        }



        weekMdl.setNtp010UsrMdl(usMdl);
        NtpCommonBiz ntpCmnBiz = new NtpCommonBiz(con, reqMdl__);
        AnkenPermitCheckModel napMdl = new AnkenPermitCheckModel(reqMdl__,
                ntpCmnBiz.isNippouAdmin(con, reqMdl__));

        //日報情報を取得(指定ユーザ)
        ntpDataList = ntpDao.select(
                usrSid,
                GSConstNippou.USER_KBN_USER,
                -1,
                frDate,
                toDate,
                GSConstNippou.DSP_MOD_WEEK,
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

        date = dspDate.cloneUDate();
        for (int i = 0; i < GSConstNippou.WEEK_DAY_COUNT; i++) {
            //１日分の日報データ
            dayMdlList = new ArrayList<SimpleNippouModel>();
            dayMdl = new Ntp010DayOfModel();
            holMdl = holMap.get(date.getDateString());
            if (holMdl != null) {
              dayMdl.setHolidayName(holMdl.getHolName());
            } else {
              dayMdl.setHolidayName(null);
            }
            dayMdl.setNtpDate(date.getDateString());
            dayMdl.setUsrSid(usrSid);
            dayMdl.setUsrKbn(GSConstNippou.USER_KBN_USER);
            dayMdl.setWeekKbn(date.getWeek());
            dayMdl.setIkkatuKbn(GSConstNippou.KAKUNIN_YES);
            dayMdl.setIkkatuChk(date.getDateString() + "-" + usrSid);

            NtpDataModel ntpMdl = null;
            for (int j = 0; j < ntpDataList.size(); j++) {
                //日報１個
                ntpMdl = ntpDataList.get(j);
                //本日の日報データか判定
                if (isTodayNippou(ntpMdl, date)) {
                    dspNtpMdl = new SimpleNippouModel();
                    dspNtpMdl.setNtpSid(ntpMdl.getNipSid());
                    dspNtpMdl.setTitle(ntpMdl.getNipTitle());
                    dspNtpMdl.setTime(getTimeString(ntpMdl, date));
                    dspNtpMdl.setTitleColor(ntpMdl.getNipTitleClo());
                    dspNtpMdl.setDetail(ntpMdl.getNipDetail());

                    //確認区分取得
                    dspNtpMdl.setNtp_chkKbn(getCheckKbn(ntpMdl.getNipSid(), chkMap));
                    //コメント件数取得
                    dspNtpMdl.setNtp_cmtCnt(existComment(ntpMdl.getNipSid(), cmmMap));
                    dspNtpMdl.setNtp_cmtkbn(getCommentKbn(dspNtpMdl.getNtp_cmtCnt()));
                    //いいね件数取得
                    dspNtpMdl.setNtp_goodCnt(existGood(ntpMdl.getNipSid(), iinMap));
                    dspNtpMdl.setNtp_goodkbn(getCommentKbn(dspNtpMdl.getNtp_goodCnt()));

                    dayMdlList.add(dspNtpMdl);
                }
            }
            dayMdl.setNtpDataList(dayMdlList);
            colList.add(dayMdl);
            //日付を進める
            date.addDay(1);
        }
        weekMdl.setNtp010NtpList(colList);
        rowList.add(weekMdl);

        return rowList;
    }
    /**
     * <br>表示グループに所属するユーザの日報情報を取得します
     * @param dspDate 開始日付
     * @param gpSid 表示グループSID
     * @param usrSid セッションユーザSID
     * @param con コネクション
     * @param myGroupFlg true: マイグループ false: 通常グループ
     * @return ArrayList 役職>姓カナ>名カナの順に格納
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<Ntp010WeekOfModel> __getWeekNippouBottomList(
            UDate dspDate,
            int gpSid,
            int usrSid,
            boolean myGroupFlg,
            Connection con) throws SQLException {

        //所属ユーザを取得
        UserSearchDao usDao = new UserSearchDao(con);

        //除外するユーザSIDを設定
        ArrayList<Integer> usrSids = new ArrayList<Integer>();
        usrSids.add(new Integer(GSConstUser.SID_ADMIN));
        usrSids.add(new Integer(GSConstUser.SID_SYSTEM_MAIL));
        usrSids.add(new Integer(usrSid)); //本人も表示しない

        //日報個人設定で取得した表示順を取得する。
        NtpCommonBiz sBiz = new NtpCommonBiz(con__, reqMdl__);
        NtpPriConfModel pconf = sBiz.getNtpPriConfModel(con, usrSid);
        int sortKey1 = pconf.getNprSortKey1();
        int orderKey1 = pconf.getNprSortOrder1();
        int sortKey2 = pconf.getNprSortKey2();
        int orderKey2 = pconf.getNprSortOrder2();

        //表示するグループメンバーを取得
        NippouDao ntpDao = new NippouDao(con);
        ArrayList<UserSearchModel> belongList = null;
        List<Integer> notAccessUserList = null;
        if (!myGroupFlg) {
            belongList = usDao.getBelongUserInfoJtkb(gpSid,
                    usrSids, sortKey1, orderKey1, sortKey2, orderKey2);
            notAccessUserList = ntpDao.getSpAccessUserList(gpSid, usrSid, false, -1);
        } else {
            belongList = usDao.getMyGroupBelongUserInfoJtkb(gpSid, usrSid,
                    usrSids, sortKey1, orderKey1, sortKey2, orderKey2);
            notAccessUserList = ntpDao.getSpAccessUserList(-1, usrSid, false, -1);
        }

        //ショートメールプラグインを使用していないユーザを除外する。
        //送信制限されているユーザを除外する。
        List<Integer> smlUsrs = new ArrayList<Integer>();
        for (UserSearchModel usMdl: belongList) {
            smlUsrs.add(usMdl.getUsrSid());
        }
        CommonBiz commonBiz = new CommonBiz();
        smlUsrs = (ArrayList<Integer>) commonBiz.getCanUseSmailUser(con, smlUsrs);
        SmlCommonBiz smlCommonBiz = new SmlCommonBiz(con, reqMdl__);
        smlUsrs = smlCommonBiz.getValiableDestUsrSid(con,
                reqMdl__.getSmodel().getUsrsid(), smlUsrs);

        //閲覧を許可されていないユーザを除外する
        ArrayList<UserSearchModel> belongList2 = new ArrayList<UserSearchModel>();
        for (UserSearchModel userData : belongList) {
            if (notAccessUserList.indexOf(userData.getUsrSid()) < 0) {
                //ショートメール有効無効設定
                if (!smlUsrs.contains(userData.getUsrSid())) {
                    userData.setSmlAble(0);
                } else {
                    userData.setSmlAble(1);
                }

                belongList2.add(userData);
            }
        }
        belongList.clear();
        belongList.addAll(belongList2);

        //一括で生成する様に変更
        ArrayList<Ntp010WeekOfModel> rowList = getWeekUserNippouNew(
                belongList, dspDate.cloneUDate(), con, usrSid);
        return rowList;
    }

    /**
     * <br>ユーザリストの１週間分の日報情報を取得します
     * @param belongList ユーザ情報一覧
     * @param dspDate 表示開始日付
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @return Ntp010WeekOfModel 週間スケジュール
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Ntp010WeekOfModel> getWeekUserNippouNew(
            ArrayList<UserSearchModel> belongList,
            UDate dspDate,
            Connection con,
            int sessionUsrSid) throws SQLException {

        ArrayList<Ntp010WeekOfModel> rowList = new ArrayList<Ntp010WeekOfModel>();

        UDate fromDate = dspDate.cloneUDate();
        fromDate.setHour(GSConstNippou.DAY_START_HOUR);
        fromDate.setMinute(GSConstNippou.DAY_START_MINUTES);
        fromDate.setSecond(GSConstNippou.DAY_START_SECOND);
        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(6);
        toDate.setHour(GSConstNippou.DAY_END_HOUR);
        toDate.setMinute(GSConstNippou.DAY_END_MINUTES);
        toDate.setSecond(GSConstNippou.DAY_END_SECOND);
        Ntp010WeekOfModel weekMdl = null;
        NtpCommonBiz ntpCmnBiz = new NtpCommonBiz(con, reqMdl__);
        AnkenPermitCheckModel napMdl = new AnkenPermitCheckModel(reqMdl__,
                ntpCmnBiz.isNippouAdmin(con, reqMdl__));

        //日報情報を取得(指定ユーザ)
        NippouSearchDao ntpDao = new NippouSearchDao(con);
        ArrayList < NtpDataModel > ntpDataList = ntpDao.selectUsers(
                belongList,
                GSConstNippou.USER_KBN_USER,
                -1,
                fromDate,
                toDate,
                GSConstNippou.DSP_MOD_WEEK,
                napMdl);

        Map<Integer, Integer> chkMap = ntpDao.getCheckCntMap(
                belongList,
                sessionUsrSid,
                GSConstNippou.USER_KBN_USER,
                -1,
                fromDate,
                toDate,
                GSConstNippou.DSP_MOD_WEEK
                );
        Map<Integer, Integer> iinMap = ntpDao.getGoodCntMap(
                belongList,
                GSConstNippou.USER_KBN_USER,
                -1,
                fromDate,
                toDate,
                GSConstNippou.DSP_MOD_WEEK
                );
        Map<Integer, Integer> cmmMap = ntpDao.getCommentCntMap(
                belongList,
                GSConstNippou.USER_KBN_USER,
                -1,
                fromDate,
                toDate,
                GSConstNippou.DSP_MOD_WEEK
                );

        //所属ユーザループ
        UserSearchModel dbUsrMdl = null;
        for (int i = 0; i < belongList.size(); i++) {
            dbUsrMdl = belongList.get(i);
            //ユーザ別に１週間の日報データを取得
            weekMdl = __getUserWeekOfModel(dspDate,
                                    dbUsrMdl,
                                    ntpDataList,
                                    con,
                                    sessionUsrSid,
                                    chkMap,
                                    iinMap,
                                    cmmMap);
            rowList.add(weekMdl);
        }

        return rowList;
    }

    /**
     * ユーザ毎の一週間の日報モデルを生成する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param dspDate 表示開始日
     * @param dbUsrMdl 生成対象のユーザ情報
     * @param ntpDataList 日報情報
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @param chkMap 確認件数Map
     * @param iinMap いいね件数Map
     * @param cmmMap コメント件数Map
     * @return Ntp010WeekOfModel
     * @throws SQLException SQL実行時例外
     */
    private Ntp010WeekOfModel __getUserWeekOfModel(
            UDate dspDate,
            UserSearchModel dbUsrMdl,
            ArrayList < NtpDataModel > ntpDataList,
            Connection con,
            int sessionUsrSid,
            Map<Integer, Integer> chkMap,
            Map<Integer, Integer> iinMap,
            Map<Integer, Integer> cmmMap) throws SQLException {

        Ntp010WeekOfModel weekMdl = new Ntp010WeekOfModel();
        ArrayList<Ntp010DayOfModel> colList = new ArrayList<Ntp010DayOfModel>();

        //指定ユーザ
        int usrSid = dbUsrMdl.getUsrSid();
        Ntp010UsrModel usMdl = new Ntp010UsrModel();

        usMdl.setUsrName(dbUsrMdl.getUsiSei() + " " + dbUsrMdl.getUsiMei());
        usMdl.setUsrUkoFlg(dbUsrMdl.getUsrUkoFlg());
        usMdl.setUsrSid(usrSid);
        usMdl.setUsrKbn(GSConstNippou.USER_KBN_USER);
        usMdl.setZaisekiKbn(dbUsrMdl.getUioStatus());
        usMdl.setZaisekiMsg(dbUsrMdl.getUioComment());
        usMdl.setSmlAble(dbUsrMdl.getSmlAble());
        weekMdl.setNtp010UsrMdl(usMdl);

        UDate date = dspDate.cloneUDate();
        ArrayList<SimpleNippouModel> dayMdlList = null;
        Ntp010DayOfModel dayMdl = null;
        SimpleNippouModel dspNtpMdl = null;
        for (int i = 0; i < GSConstNippou.WEEK_DAY_COUNT; i++) {
            //１日分の日報データ
            dayMdlList = new ArrayList<SimpleNippouModel>();
            dayMdl = new Ntp010DayOfModel();
            dayMdl.setHolidayName(null);
            dayMdl.setNtpDate(date.getDateString());
            dayMdl.setUsrSid(usrSid);
            dayMdl.setUsrKbn(GSConstNippou.USER_KBN_USER);
            dayMdl.setWeekKbn(date.getWeek());
            dayMdl.setIkkatuKbn(GSConstNippou.KAKUNIN_YES);
            dayMdl.setIkkatuChk(date.getDateString() + "-" + usrSid);

            NtpDataModel ntpMdl = null;
            for (int j = 0; j < ntpDataList.size(); j++) {
                //日報１個
                ntpMdl = ntpDataList.get(j);
                if (ntpMdl.getUsrSid() != usrSid) {
                    continue;
                }
                //本日の日報データか判定
                if (isTodayNippou(ntpMdl, date)) {
                    dspNtpMdl = new SimpleNippouModel();
                    if (ntpMdl.getNipPublic() == GSConstNippou.DSP_YOTEIARI) {
                        //予定あり
                        dspNtpMdl.setTitle(GSConstNippou.DSP_YOTEIARI_STRING);
                    } else if (ntpMdl.getNipPublic() == GSConstNippou.DSP_NOT_PUBLIC) {
                        //非公開
                        continue;
                    } else {
                        //公開
                        dspNtpMdl.setTitle(ntpMdl.getNipTitle());
                    }
                    dspNtpMdl.setNtpSid(ntpMdl.getNipSid());
                    dspNtpMdl.setTime(getTimeString(ntpMdl, date));
                    dspNtpMdl.setTitleColor(ntpMdl.getNipTitleClo());
                    dspNtpMdl.setDetail(ntpMdl.getNipDetail());

                    //確認区分取得
                    dspNtpMdl.setNtp_chkKbn(getCheckKbn(ntpMdl.getNipSid(), chkMap));

                    //コメント件数取得
                    dspNtpMdl.setNtp_cmtCnt(existComment(ntpMdl.getNipSid(), cmmMap));
                    dspNtpMdl.setNtp_cmtkbn(getCommentKbn(dspNtpMdl.getNtp_cmtCnt()));

                    //いいね件数取得
                    dspNtpMdl.setNtp_goodCnt(existGood(ntpMdl.getNipSid(), iinMap));
                    dspNtpMdl.setNtp_goodkbn(getCommentKbn(dspNtpMdl.getNtp_goodCnt()));

                    dspNtpMdl.setAnkenViewable(ntpMdl.isAnkenViewable());
                    dayMdlList.add(dspNtpMdl);
                }
            }
            dayMdl.setNtpDataList(dayMdlList);
            colList.add(dayMdl);
            //日付を進める
            date.addDay(1);
        }
        weekMdl.setNtp010NtpList(colList);

        return weekMdl;
    }
    /**
     * <br>ユーザ毎の１週間分の日報情報を取得します
     * @param usrSid ユーザSID
     * @param dspDate 表示開始日付
     * @param con コネクション
     * @return Ntp010WeekOfModel 週間日報情報
     * @throws SQLException SQL実行時例外
     */
    public Ntp010WeekOfModel getWeekUserNippou(int usrSid, UDate dspDate, Connection con)
    throws SQLException {

        UDate fromDate = dspDate.cloneUDate();
        fromDate.setHour(GSConstNippou.DAY_START_HOUR);
        fromDate.setMinute(GSConstNippou.DAY_START_MINUTES);
        fromDate.setSecond(GSConstNippou.DAY_START_SECOND);
        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(6);
        toDate.setHour(GSConstNippou.DAY_END_HOUR);
        toDate.setMinute(GSConstNippou.DAY_END_MINUTES);
        toDate.setSecond(GSConstNippou.DAY_END_SECOND);
        Ntp010WeekOfModel weekMdl = new Ntp010WeekOfModel();

        //指定ユーザ
        weekMdl = new Ntp010WeekOfModel();
        ArrayList<Ntp010DayOfModel> colList = new ArrayList<Ntp010DayOfModel>();
        Ntp010UsrModel usMdl = new Ntp010UsrModel();
        UserSearchDao usrDao = new UserSearchDao(con);
        UserSearchModel usrInfMdl = usrDao.getUserInfoJtkb(
                usrSid, GSConstUser.USER_JTKBN_ACTIVE);
        usMdl.setUsrName(usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());
        usMdl.setUsrUkoFlg(usrInfMdl.getUsrUkoFlg());
        usMdl.setUsrSid(usrSid);
        usMdl.setUsrKbn(GSConstNippou.USER_KBN_USER);
        usMdl.setZaisekiKbn(usrInfMdl.getUioStatus());
        weekMdl.setNtp010UsrMdl(usMdl);
        NtpCommonBiz ntpCmnBiz = new NtpCommonBiz(con, reqMdl__);
        AnkenPermitCheckModel napMdl = new AnkenPermitCheckModel(reqMdl__,
                ntpCmnBiz.isNippouAdmin(con, reqMdl__));

        //スケジュール情報を取得(指定ユーザ)
        NippouSearchDao schDao = new NippouSearchDao(con);
        ArrayList < NtpDataModel > ntpDataList = schDao.select(
                usrSid,
                GSConstNippou.USER_KBN_USER,
                -1,
                fromDate,
                toDate,
                GSConstNippou.DSP_MOD_WEEK,
                napMdl);

        UDate date = dspDate.cloneUDate();
        ArrayList<SimpleNippouModel> dayMdlList = null;
        Ntp010DayOfModel dayMdl = null;
        SimpleNippouModel dspNtpMdl = null;
        for (int i = 0; i < GSConstNippou.WEEK_DAY_COUNT; i++) {
            //１日分のスケジュール
            dayMdlList = new ArrayList<SimpleNippouModel>();
            dayMdl = new Ntp010DayOfModel();
            dayMdl.setHolidayName(null);
            dayMdl.setNtpDate(date.getDateString());
            dayMdl.setUsrSid(usrSid);
            dayMdl.setUsrKbn(GSConstNippou.USER_KBN_USER);
            dayMdl.setWeekKbn(date.getWeek());
            dayMdl.setIkkatuKbn(GSConstNippou.KAKUNIN_YES);
            dayMdl.setIkkatuChk(date.getDateString() + "-" + usrSid);

            NtpDataModel schMdl = null;
            for (int j = 0; j < ntpDataList.size(); j++) {
                //スケジュール１個
                schMdl = ntpDataList.get(j);
                //本日のスケジュールか判定
                if (isTodayNippou(schMdl, date)) {
                    dspNtpMdl = new SimpleNippouModel();
                    if (schMdl.getNipPublic() == GSConstNippou.DSP_YOTEIARI) {
                        //予定あり
                        dspNtpMdl.setTitle(GSConstNippou.DSP_YOTEIARI_STRING);
                    } else if (schMdl.getNipPublic() == GSConstNippou.DSP_NOT_PUBLIC) {
                        //非公開
                        continue;
                    } else {
                        //公開
                        dspNtpMdl.setTitle(schMdl.getNipTitle());
                    }
                    dspNtpMdl.setNtpSid(schMdl.getNipSid());
                    dspNtpMdl.setTime(getTimeString(schMdl, date));
                    dspNtpMdl.setTitleColor(schMdl.getNipTitleClo());
                    dayMdlList.add(dspNtpMdl);
                }
            }
            dayMdl.setNtpDataList(dayMdlList);
            colList.add(dayMdl);
            //日付を進める
            date.addDay(1);
        }
        weekMdl.setNtp010NtpList(colList);

        return weekMdl;
    }

    /**
     * <br>日報情報が指定日付の日報か判定します
     * @param ntpMdl 日報情報
     * @param date 指定日付
     * @return true:指定日の日報 false:指定日以外の日報
     */
    public static boolean isTodayNippou(NtpDataModel ntpMdl, UDate date) {
        boolean ret = false;
        UDate nipDate = ntpMdl.getNipDate();
        if (nipDate.compareDateYMD(date) == UDate.EQUAL) {

            //Toが0:00の場合は除外する(日またぎのスケジュールとしない)
//            if (ntpMdl.getScdDaily() == 0
//                    && toDate.getYear() == date.getYear()
//                    && toDate.getMonth() == date.getMonth()
//                    && toDate.getIntDay() == date.getIntDay()
//                    && toDate.getIntHour() == GSConstSchedule.DAY_START_HOUR
//                    && toDate.getIntMinute() == GSConstSchedule.DAY_START_MINUTES) {
//            } else {
                ret = true;
//            }

        }

        return ret;
    }

    /**
     * <br>日報時間表示を画面表示用に編集します
     * @param ntpMdl 日報情報
     * @param date 指定日付
     * @return String 画面表示用時間
     */
    public static String getTimeString(NtpDataModel ntpMdl, UDate date) {

        StringBuffer buf = new StringBuffer();
        UDate frDate = ntpMdl.getNipFrTime();
        UDate toDate = ntpMdl.getNipToTime();

        if (ntpMdl.getNipDate().equalsDate(frDate)) {
            buf.append(frDate.getStrHour());
        } else {
            buf.append(String.valueOf(frDate.getIntHour() + 24));
        }

        buf.append(":");
        buf.append(frDate.getStrMinute());
        buf.append("-");

        if (frDate.equalsDate(toDate) && ntpMdl.getNipDate().equalsDate(frDate)) {
            buf.append(toDate.getStrHour());
        } else {
            buf.append(String.valueOf(toDate.getIntHour() + 24));
        }

        buf.append(":");
        buf.append(toDate.getStrMinute());

        return buf.toString();
    }

    /**
     * <br>日報個人設定を取得します
     * <br>データが存在しない場合は初期値で作成し取得します
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return NtpPriConfModel 個人設定
     * @throws SQLException SQL実行時例外
     */
    public NtpPriConfModel getPrivateConf(int usrSid, Connection con) throws SQLException {

        NtpPriConfModel confBean = null;

        NtpCommonBiz cbiz = new NtpCommonBiz(con__, reqMdl__);
        confBean = cbiz.getNtpPriConfModel(con, usrSid);

        return confBean;
    }

    /**
     * <br>同時登録日報情報も含め編集権限があるか判定する
     * @param nipSid 日報SID
     * @param con コネクション
     * @return boolean true:権限あり　false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isAllEditOk(
            int nipSid,
            Connection con) throws SQLException {

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        boolean isAdmin = usModel.getAdminFlg();
        //管理者権限の有無
        return isAdmin;
    }

    /**
     * 日報情報の中に自分の日報情報が含まれるか判定する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param ntpDataList 日報リスト
     * @param sessionUsrSid ユーザSID
     * @return boolean true:含まれる　false:含まれない
     */
    public boolean isIncludedMySchedule(
            ArrayList<NippouSearchModel> ntpDataList, int sessionUsrSid) {
        for (NippouSearchModel mdl : ntpDataList) {
            if (mdl.getUsrSid() == sessionUsrSid) {
                return true;
            }
        }
        return false;
    }

    /**
     * 日報確認情報の中の自分のユーザーSIDが未確認か判定する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param kakuninList 日報確認情報
     * @param nipSid 日報SID
     * @param usrSid ユーザーSID
     * @return int 日報確認者区分　-1:該当なし 0:未確認　1:確認済み
     */
    public int getNkkChkKbn(ArrayList<NtpKakuninModel> kakuninList, int nipSid, int usrSid) {

        int ret = -1;
        NtpKakuninModel kakuninMdl = null;
        for (int k = 0; k < kakuninList.size(); k++) {
            kakuninMdl = kakuninList.get(k);
            //同一日報SIDか判定
            if (kakuninMdl.getNipSid() == nipSid) {
                if  (kakuninMdl.getUsrSid() == usrSid) {
                    ret = kakuninMdl.getNkkCheck();
                    return ret;
                }
            }
        }
        return ret;
    }

    /**
     * 確認区分を取得
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param ntpSid 日報SID
     * @param map 件数map
     * @return kbn コメント区分
     * @throws SQLException sql実行例外
     */
    public int getCheckKbn(int ntpSid, Map<Integer, Integer> map) throws SQLException {

        int kbn = 0;
        if (map.containsKey(ntpSid)) {
            Integer cnt = null;
            cnt = map.get(ntpSid);
            if (cnt != null) {
                kbn = cnt;
            }
        }

        return kbn;
    }


    /**
     * コメントの件数を取得
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param nipSid 日報SID
     * @param map 件数map
     * @return int 確認件数
     * @throws SQLException sql実行例外
     */
    public int existComment(int nipSid, Map<Integer, Integer> map) throws SQLException {

        int kbn = 0;
        if (map.containsKey(nipSid)) {
            Integer cnt = null;
            cnt = map.get(nipSid);
            if (cnt != null) {
                kbn = cnt;
            }
        }

        return kbn;
    }
    /**
     * いいねの件数を取得
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param nipSid 日報SID
     * @param map 件数map
     * @return int 確認件数
     * @throws SQLException sql実行例外
     */
    public int existGood(int nipSid, Map<Integer, Integer> map) throws SQLException {

        int kbn = 0;
        if (map.containsKey(nipSid)) {
            Integer cnt = null;
            cnt = map.get(nipSid);
            if (cnt != null) {
                kbn = cnt;
            }
        }

        return kbn;
    }

    /**
     * コメント区分
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param cmtCnt コメント件数
     * @return int コメント区分
     * @throws SQLException sql実行例外
     */
    public int getCommentKbn(int cmtCnt) throws SQLException {

        int kbn = 0;
        int cmtLength = String.valueOf(cmtCnt).length();

        if (cmtLength == 2) {
            //2桁
            kbn = 1;
        } else if (cmtLength > 2) {
            //2桁以上
            kbn = 2;
        }

        return kbn;
    }


    /**
     * <br>[機  能] 入力内容をDBに反映する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl フォーム
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void doIkkatuUpdate(Ntp010ParamModel paramMdl,
                               Connection con) throws SQLException {

        ArrayList<String> hiddArray = new ArrayList<String>();
        String[] hiddStr = paramMdl.getRsvIkkatuKey();
        if (hiddStr != null && hiddStr.length > 0) {
            for (String key : hiddStr) {
                hiddArray.add(key);
            }
        }

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usrMdl =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usrMdl.getUsrsid(); //セッションユーザSID

        UDate now = new UDate();

        for (String key : hiddArray) {
            //日付取得
            String ymdStr = key.substring(0, key.indexOf("-"));
            UDate udYrk = new UDate();
            udYrk.setDate(ymdStr);
            udYrk.setHour(GSConstNippou.DAY_START_HOUR);
            udYrk.setMinute(GSConstNippou.DAY_START_MINUTES);
            udYrk.setSecond(GSConstNippou.DAY_START_SECOND);
            udYrk.setMilliSecond(GSConstNippou.DAY_START_MILLISECOND);

            //ユーザSID
            int usrSid = Integer.valueOf(key.substring(key.indexOf("-") + 1));
            //日報情報SID取得

            NtpKakuninDao kakuninDao = new NtpKakuninDao(con);
            NtpKakuninModel kakuninParm = new NtpKakuninModel();
            kakuninParm.setNkkDateCheck(now);
            kakuninParm.setUsrSid(sessionUsrSid);
            kakuninParm.setNkkEuid(sessionUsrSid);
            kakuninParm.setNkkEdate(now);

            kakuninDao.updateKakunin(kakuninParm, usrSid, udYrk);
        }
    }
    /**
     * <br>日報の登録・編集権限があるか判定する
     * @param dspUsrSid 画面表示ユーザ
     * @param con コネクション
     * @return int 0:権限あり　1:権限無し
     * @throws SQLException SQL実行時例外
     */
    public int isAddEditOk(
            int dspUsrSid,
            Connection con) throws SQLException {

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        CommonBiz commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstNippou.PLUGIN_ID_NIPPOU);
        //管理者権限の有無
        if (isAdmin) {
            return GSConstNippou.AUTH_ADD_EDIT;
        }

        //画面表示ユーザが自分か
        if (dspUsrSid == sessionUsrSid) {
            return GSConstNippou.AUTH_ADD_EDIT;
        }

        return GSConstNippou.AUTH_NOT_ADD_EDIT;
    }
}