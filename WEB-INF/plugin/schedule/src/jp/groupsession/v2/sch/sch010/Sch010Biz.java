package jp.groupsession.v2.sch.sch010;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.UrlBuilder;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchAppendDataParam;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.biz.SchEasyRegisterBiz;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.SchMyviewlistBelongDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.MyViewListBelongModel;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchHidModel;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.model.SimpleCalenderModel;
import jp.groupsession.v2.sch.pdf.SchSyuPdfModel;
import jp.groupsession.v2.sch.pdf.SchSyuPdfUtil;
import jp.groupsession.v2.sch.sch040.Sch040Biz;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] スケジュール 週間画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch010Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** pconfig */
    private PluginConfig pconfig__ = null;
    /** 画面表示グループSID */
    private String dspGpSid__ = null;
    /** セッションユーザ所属グループSIDリスト */
    private List<Integer> belongGpSidList__ = null;

    /**
     * <p>Set RequestModel
     * @param reqMdl RequestModel
     */
    public Sch010Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <p>Set RequestModel
     * @param reqMdl RequestModel
     * @param pconfig PluginConfig
     */
    public Sch010Biz(RequestModel reqMdl, PluginConfig pconfig) {
        reqMdl__ = reqMdl;
        pconfig__ = pconfig;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Sch010ParamModel
     * @param con コネクション
     * @return アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Sch010ParamModel getInitData(
            Sch010ParamModel paramMdl,
            Connection con) throws SQLException {

        log__.debug("初期表示開始");
        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //セッションユーザの所属グループを格納
        CmnBelongmDao bdao = new CmnBelongmDao(con);
        belongGpSidList__ = bdao.selectUserBelongGroupSid(sessionUsrSid);

        //管理者設定を取得
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel adminConf = biz.getAdmConfModel(con);

        //共有範囲
        paramMdl.setSch010CrangeKbn(adminConf.getSadCrange());

        //個人設定取得&作成
        SchPriConfModel confMdl = getPrivateConf(sessionUsrSid, con);
        paramMdl.setSch010Reload(confMdl.getSccReload());

        //各ユーザで設定した週スケジュールの開始曜日を取得
        int startWeek = confMdl.getSccIniWeek();

        //リクエストパラメータを取得
        //表示開始日
        UDate dspDate = new UDate();
        String strDspDate = NullDefault.getString(paramMdl.getSch010DspDate(), "");
        if (strDspDate.length() == 8 && ValidateUtil.isNumber(strDspDate)) {
            dspDate.setDate(strDspDate);
        }

        //選択スケジュールSIDの初期化
        paramMdl.setSch010SchSid(null);

        //表示日付の移動を行ったかどうか
        if (paramMdl.getChangeDateFlg() == 0) {
            //表示開始曜日に今日を設定した場合
            if (startWeek == 0) {
                log__.debug("***今日の日付は" + dspDate.getDateString());
                dspDate.setDate(new UDate().getDateString());
            } else {

                int nowWeek = dspDate.getWeek();
                log__.debug("***対象の日付は" + dspDate.getDateString());
                //開始日付を取得
                int difWeek = startWeek - nowWeek;
                if (difWeek > 0) {
                    dspDate.addDay(-7 + difWeek);

                } else {
                    dspDate.addDay(difWeek);
                }
                log__.debug("***変更した日付は" + dspDate.getDateString());
            }
            //表示項目取得
            paramMdl.setSch010DspDate(dspDate.getDateString());
        }


        String dspGpSidStr = paramMdl.getSch010DspGpSid();

        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
        int dspGpSid = SchCommonBiz.getDspGroupSid(dspGpSidStr);
        dspGpSid__ = dspGpSidStr;
        boolean myGroupFlg = SchCommonBiz.isMyGroupSid(dspGpSidStr);
        boolean dspListFlg = SchCommonBiz.isDspListSid(dspGpSidStr);

        GsMessage gsMsg = new GsMessage(reqMdl__);
        //年
        String textYear = gsMsg.getMessage("cmn.year", new String[] {dspDate.getStrYear()});
        //月
        String textMonth = gsMsg.getMessage("cmn.month");
        paramMdl.setSch010StrDspDate(textYear + dspDate.getStrMonth() + textMonth);
        ArrayList<SimpleCalenderModel> weekCalender = getWeekCalender(dspDate.cloneUDate(), con);
        paramMdl.setSch010CalendarList(weekCalender);

        boolean onlyGrpFlg = false;

        //グループと本人のスケジュールを取得する。
        SchPriConfModel pconf = scBiz.getSchPriConfModel(con, sessionUsrSid);
        ArrayList<Sch010WeekOfModel> topList = new ArrayList<Sch010WeekOfModel>();
        if (!dspListFlg) {
            if (pconf.getSccGrpShowKbn() == 0) {
                //自分のスケジュールにグループのスケジュールを表示する
                topList = getWeekScheduleTopListWithBelongGroup(
                        dspDate.cloneUDate(), dspGpSid, sessionUsrSid, myGroupFlg, onlyGrpFlg, con);
            } else {
                //自分のスケジュールにグループのスケジュールを表示しない
                topList = getWeekScheduleTopList(
                        dspDate.cloneUDate(), dspGpSid, sessionUsrSid, myGroupFlg, onlyGrpFlg, con);
            }
        }
        paramMdl.setSch010TopList(topList);

        //グループメンバーのスケジュールを取得する。

        if (dspListFlg) {
            //表示リストのユーザ・グループスケジュールを取得
            __setWeekScheduleViewList(
                    paramMdl,
                    dspDate.cloneUDate(),
                    dspGpSid,
                    sessionUsrSid,
                    con,
                    confMdl);
            
            //ログインユーザのユーザ情報を設定
            Sch010UsrModel schUsrMdl = new Sch010UsrModel();
            UserSearchDao usrDao = new UserSearchDao(con);
            UserSearchModel usrInfMdl = usrDao.getUserInfoJtkb(
                    sessionUsrSid, GSConstUser.USER_JTKBN_ACTIVE);
            schUsrMdl.setUsrName(usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());
            schUsrMdl.setUsrSid(sessionUsrSid);
            schUsrMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
            schUsrMdl.setZaisekiKbn(usrInfMdl.getUioStatus());
            schUsrMdl.setZaisekiMsg(usrInfMdl.getUioComment());
            //ショートメールプラグインを使用していないユーザを除外する。
            //送信制限されているユーザを除外する。
            List<Integer> smlUsrs = new ArrayList<Integer>();
            smlUsrs.add(sessionUsrSid);
            CommonBiz commonBiz = new CommonBiz();
            smlUsrs = (ArrayList<Integer>) commonBiz.getCanUseSmailUser(con, smlUsrs);
            SmlCommonBiz smlCommonBiz = new SmlCommonBiz(con, reqMdl__);
            smlUsrs = smlCommonBiz.getValiableDestUsrSid(con,
                    reqMdl__.getSmodel().getUsrsid(), smlUsrs);
            //ショートメール有効無効設定
            if (!smlUsrs.contains(schUsrMdl.getUsrSid())) {
                schUsrMdl.setSmlAble(0);
            } else {
                schUsrMdl.setSmlAble(1);
            }
            paramMdl.setSch010UserData(schUsrMdl);

        } else {
            paramMdl.setSch010BottomList(
                    __getWeekScheduleBottomList(
                            dspDate.cloneUDate(), dspGpSid, sessionUsrSid, myGroupFlg, con));
        }

        CommonBiz commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstSchedule.PLUGIN_ID_SCHEDULE);
        if (isAdmin) {
            paramMdl.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            paramMdl.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }

        //閲覧不可のグループを設定
        SchDao schDao = new SchDao(con);
        paramMdl.setSchNotAccessGroupList(schDao.getNotAccessGrpList(sessionUsrSid));

        //初期表示済みにする
        paramMdl.setIniDsp(GSConstSchedule.NOT_INIT_FLG);

        //簡易登録画面用値設定
        SchEasyRegisterBiz serBiz = new SchEasyRegisterBiz(con, reqMdl__);
        paramMdl.setEasyRegister(serBiz.setInitDisp(adminConf, confMdl));

        //一括登録用保持用のパラメータ設定
        if (paramMdl.getSchIkkatsuViewMode() == GSConstSchedule.SCH_IKKATSUVIEWMODE_YES) {
            UDate frDate = dspDate.cloneUDate();
            frDate.setHour(GSConstSchedule.DAY_START_HOUR);
            frDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
            frDate.setSecond(GSConstSchedule.DAY_START_SECOND);

            UDate toDate = dspDate.cloneUDate();
            toDate.addDay(GSConstSchedule.WEEK_DAY_COUNT - 1);
            toDate.setHour(GSConstSchedule.DAY_END_HOUR);
            toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
            toDate.setSecond(GSConstSchedule.DAY_END_SECOND);

            paramMdl.setSchIkkatuTorokuHideList(setIkkatsuData(con, paramMdl));
        }

        //簡易登録画面用値設定
        paramMdl.setEasyRegister(serBiz.setInitDisp(adminConf, confMdl));

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
    public List<SchLabelValueModel> getGroupLabelList(Connection con,
            int usrSid) throws SQLException {

        List < SchLabelValueModel > labelList = null;

        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
        labelList = scBiz.getGroupLabelForSchedule(
                usrSid, con, false);

        return labelList;
    }
    /**
     * <br>UDateの曜日定数から曜日文字を取得する
     * @param week UDateの曜日定数
     * @param reqMdl RequestModel
     * @return String 曜日
     */
    public static String getStrWeek(int week, RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl);

        String str = "";
        switch (week) {
        case UDate.SUNDAY:
            str = gsMsg.getMessage("cmn.sunday");
            break;
        case UDate.MONDAY:
            str = gsMsg.getMessage("cmn.Monday");
            break;
        case UDate.TUESDAY:
            str = gsMsg.getMessage("cmn.tuesday");
            break;
        case UDate.WEDNESDAY:
            str = gsMsg.getMessage("cmn.wednesday");
            break;
        case UDate.THURSDAY:
            str = gsMsg.getMessage("cmn.thursday");
            break;
        case UDate.FRIDAY:
            str = gsMsg.getMessage("cmn.friday");
            break;
        case UDate.SATURDAY:
            str = gsMsg.getMessage("cmn.saturday");
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
        GsMessage gsMsg = new GsMessage(reqMdl__);

        ArrayList<SimpleCalenderModel> calList =
            new ArrayList<SimpleCalenderModel>(GSConstSchedule.WEEK_DAY_COUNT);
        //休日情報を取得する
        UDate frDate = dspDate.cloneUDate();
        frDate.setHour(GSConstSchedule.DAY_START_HOUR);
        frDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(GSConstSchedule.WEEK_DAY_COUNT - 1);
        toDate.setHour(GSConstSchedule.DAY_END_HOUR);
        toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_END_SECOND);
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap < String, CmnHolidayModel > holMap = holDao.getHoliDayList(frDate, toDate);
        CmnHolidayModel holMdl = null;
        CommonBiz cmnBiz = new CommonBiz();
        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid();

        boolean rokuyoDspFlg = cmnBiz.getRokuyoDspKbn(con, sessionUsrSid);

        UDate today = new UDate();
        //1週間分のカレンダーを設定
        SimpleCalenderModel calMdl = null;
        for (int i = 0; i < GSConstSchedule.WEEK_DAY_COUNT; i++) {
            calMdl = new SimpleCalenderModel();
            calMdl.setDspDate(dspDate.getDateString());
            calMdl.setWeekKbn(String.valueOf(dspDate.getWeek()));
            calMdl.setDspDayString(
                    String.valueOf(dspDate.getIntDay())
                    + gsMsg.getMessage("cmn.day")
                    + "("
                    + getStrWeek(dspDate.getWeek(), reqMdl__)
                    + ")");

            //休日情報を設定
            holMdl = holMap.get(dspDate.getDateString());
            if (holMdl != null) {
                calMdl.setHolidayKbn(String.valueOf(GSConstSchedule.HOLIDAY_TRUE));
            } else {
                calMdl.setHolidayKbn(String.valueOf(GSConstSchedule.HOLIDAY_FALSE));
            }
            //今日区分
            if (dspDate.getDateString().equals(today.getDateString())) {
                calMdl.setTodayKbn(String.valueOf(GSConstSchedule.TODAY_TRUE));
            } else {
                calMdl.setTodayKbn(String.valueOf(GSConstSchedule.TODAY_FALSE));
            }
            //六曜
            if (rokuyoDspFlg) {
                String rokuyouName = cmnBiz.getRokuyou(dspDate);
                int rkyKbn = cmnBiz.setRkyKbn(rokuyouName);
                calMdl.setRokuyou(String.valueOf(rkyKbn));
                calMdl.setRokuyouName(rokuyouName);
            } else {
                calMdl.setRokuyou("");
                calMdl.setRokuyouName("");
            }

            calList.add(calMdl);
            dspDate.addDay(1);
        }
        return calList;
    }

    /**
     * <br>グループと指定ユーザの週間スケジュールを取得します
     * @param dspDate 開始日付
     * @param grpSid グループSID
     * @param usrSid ユーザSID
     * @param myGpFlg マイグループ選択フラグ
     * @param con コネクション
     * @param onlyGrpFlg グループスケジュールのみ取得フラグ
     * @return ArrayList グループ>指定ユーザの順に格納
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Sch010WeekOfModel> getWeekScheduleTopList(
            UDate dspDate,
            int grpSid,
            int usrSid,
            boolean myGpFlg,
            boolean onlyGrpFlg,
            Connection con) throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        UDate frDate = dspDate.cloneUDate();
        frDate.setHour(GSConstSchedule.DAY_START_HOUR);
        frDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        frDate.setSecond(GSConstSchedule.DAY_START_SECOND);

        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(GSConstSchedule.WEEK_DAY_COUNT - 1);
        toDate.setHour(GSConstSchedule.DAY_END_HOUR);
        toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_END_SECOND);


        //グループ・指定ユーザのcolListを保持
        ArrayList<Sch010WeekOfModel> rowList = new ArrayList<Sch010WeekOfModel>();
        //ユーザ情報を保持
        Sch010UsrModel usMdl = null;
        ArrayList<Sch010DayOfModel> colList = null;
        //DBスケジュール情報
        ArrayList < SchDataModel > schDataList = null;
        //ユーザ別、１週間分のスケジュール
        Sch010WeekOfModel weekMdl = null;

        //時間指定なしスケジュール格納リスト
        LinkedHashMap<String, SimpleScheduleModel> periodMdlMap
                                   = new LinkedHashMap<String, SimpleScheduleModel>();

        //休日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayList(frDate, toDate);
        CmnHolidayModel holMdl = null;

        Sch010DayOfModel dayMdl = null;
        ArrayList<SimpleScheduleModel> dayMdlList = null;
        SimpleScheduleModel dspSchMdl = null;

        UDate date = dspDate.cloneUDate();
        ScheduleSearchDao schDao = new ScheduleSearchDao(con);

        //閲覧可能グループかを判定
        boolean accessGrp = true;
        SchDao scheduleDao = new SchDao(con);
        if (!myGpFlg) {
            accessGrp = scheduleDao.canAccessGroupSchedule(grpSid, usrSid);
        }

        //グループ
        if (!myGpFlg && accessGrp) {
            weekMdl = __createGrpScheduleModel(
                    date,
                    grpSid, frDate, toDate,
                    holMap,
                    sessionUsrSid, con);
            rowList.add(weekMdl);
        }


        if (onlyGrpFlg) {
            //グループスケジュールのみ取得の場合
            return rowList;
        }

        periodMdlMap = new LinkedHashMap<String, SimpleScheduleModel>();


        //指定ユーザ
        weekMdl = new Sch010WeekOfModel();
        colList = new ArrayList<Sch010DayOfModel>();
        usMdl = new Sch010UsrModel();
        UserSearchDao usrDao = new UserSearchDao(con);
        UserSearchModel usrInfMdl = usrDao.getUserInfoJtkb(
                usrSid, GSConstUser.USER_JTKBN_ACTIVE);
        usMdl.setUsrName(usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());
        usMdl.setUsrSid(usrSid);
        usMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
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

        //スケジュール登録可能ユーザかを判定
        usMdl.setSchRegistFlg(scheduleDao.canRegistUserSchedule(usrSid, usrSid));

        weekMdl.setSch010UsrMdl(usMdl);

        //スケジュール情報を取得(指定ユーザ)
        schDataList = schDao.select(
                usrSid,
                GSConstSchedule.USER_KBN_USER,
                -1,
                frDate,
                toDate,
                GSConstSchedule.DSP_MOD_WEEK,
                sessionUsrSid);


        //他プラグイン情報を取得
        __getAppendPlgData(dspDate, grpSid, usrSid, schDataList, con, 0);


        SchCommonBiz schBiz = new SchCommonBiz();
        date = dspDate.cloneUDate();
        for (int i = 0; i < GSConstSchedule.WEEK_DAY_COUNT; i++) {
            //１日分のスケジュール
            dayMdlList = new ArrayList<SimpleScheduleModel>();
            dayMdl = new Sch010DayOfModel();
            holMdl = holMap.get(date.getDateString());
            if (holMdl != null) {

                dayMdl.setHolidayName(holMdl.getHolName());
            } else {
                dayMdl.setHolidayName(null);
            }
            dayMdl.setSchDate(date.getDateString());
            dayMdl.setUsrSid(usrSid);
            dayMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
            dayMdl.setWeekKbn(date.getWeek());

            __checkToday(dayMdl, date);

            SchDataModel schMdl = null;
            for (int j = 0; j < schDataList.size(); j++) {
                //スケジュール１個
                schMdl = schDataList.get(j);
                //本日のスケジュールか判定
                if (isTodaySchedule(schMdl, date)) {
                    dspSchMdl = new SimpleScheduleModel();
                    GroupBiz gpBiz = new GroupBiz();
                    boolean belongGrpHnt = gpBiz.isBelongGroup(usrSid, grpSid, con);
                    if (schMdl.getScdAppendUrl() == null) {
                        //スケジュールのデータ
                        dspSchMdl.setSchSid(schMdl.getScdSid());
                        dspSchMdl.setUserKbn(String.valueOf(GSConstSchedule.USER_KBN_USER));
                    } else {
                        //スケジュール以外のプラグインのデータ
                        dspSchMdl.setSchAppendUrl(schMdl.getScdAppendUrl());
                        dspSchMdl.setUserKbn(schMdl.getScdAppendId());
                    }
                    dspSchMdl.setKjnEdKbn(GSConstSchedule.EDIT_CONF_OWN);
                    dspSchMdl.setTitle(schMdl.getScdTitle());
                    dspSchMdl.setTime(getTimeString(schMdl, date));
                    dspSchMdl.setPublic(schMdl.getScdPublic());
                    dspSchMdl.setTimeKbn(schMdl.getScdDaily());
                    dspSchMdl.setBgColor(schMdl.getScdBgcolor());
                    dspSchMdl.setValueStr(schMdl.getScdValue());
                    dspSchMdl.setPublicIconFlg(
                            schBiz.getPublicIconFlg(schMdl, usrSid, belongGrpHnt, false));

                    if (schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
                        dayMdlList.add(dspSchMdl);
                    } else {
                        dspSchMdl.setFromDate(schMdl.getScdFrDate());
                        dspSchMdl.setToDate(schMdl.getScdToDate());
                        if (schMdl.getScdAppendUrl() == null) {
                            //スケジュールのデータ
                            if (schMdl.getScdUsrSid() == sessionUsrSid) {
                                //本人
                                periodMdlMap.put(String.valueOf(dspSchMdl.getSchSid()), dspSchMdl);
                            } else {
                                //他ユーザ
                                if (schMdl.getScdPublic() != GSConstSchedule.DSP_NOT_PUBLIC) {
                                    //非公開以外のスケジュール
                                    periodMdlMap.put(String.valueOf(dspSchMdl.getSchSid()),
                                                                                         dspSchMdl);
                                }
                            }
                        } else {
                            //スケジュール以外のプラグインのデータ
                            periodMdlMap.put(schMdl.getScdAppendUrl(), dspSchMdl);
                        }
                    }
                }
            }
            dayMdl.setSchDataList(dayMdlList);
            colList.add(dayMdl);
            //日付を進める
            date.addDay(1);
        }
        weekMdl.setSch010SchList(colList);

        //期間スケジュールを取得
        weekMdl = __getPeriodSch(weekMdl, periodMdlMap, dspDate);


        rowList.add(weekMdl);

        return rowList;
    }

    /**
     * <br>指定グループと所属グループと指定ユーザの週間スケジュールを取得します
     * @param dspDate 開始日付
     * @param grpSid グループSID
     * @param usrSid ユーザSID
     * @param myGpFlg マイグループ選択フラグ
     * @param con コネクション
     * @param onlyGrpFlg グループスケジュールのみ取得フラグ
     * @return ArrayList グループ>指定ユーザの順に格納
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Sch010WeekOfModel> getWeekScheduleTopListWithBelongGroup(
            UDate dspDate,
            int grpSid,
            int usrSid,
            boolean myGpFlg,
            boolean onlyGrpFlg,
            Connection con) throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        UDate frDate = dspDate.cloneUDate();
        frDate.setHour(GSConstSchedule.DAY_START_HOUR);
        frDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        frDate.setSecond(GSConstSchedule.DAY_START_SECOND);

        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(GSConstSchedule.WEEK_DAY_COUNT - 1);
        toDate.setHour(GSConstSchedule.DAY_END_HOUR);
        toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_END_SECOND);


        //グループ・指定ユーザのcolListを保持
        ArrayList<Sch010WeekOfModel> rowList = new ArrayList<Sch010WeekOfModel>();
        //ユーザ情報を保持
        Sch010UsrModel usMdl = null;
        ArrayList<Sch010DayOfModel> colList = null;
        //DBスケジュール情報
        ArrayList < SchDataModel > schDataList = null;
        //DBスケジュール情報(グループ)
        ArrayList < SchDataModel > schGpDataList = null;
        //ユーザ別、１週間分のスケジュール
        Sch010WeekOfModel weekMdl = null;

        //休日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayList(frDate, toDate);
        CmnHolidayModel holMdl = null;

        Sch010DayOfModel dayMdl = null;
        //時間指定ありスケジュール格納マップ
        ArrayList<SimpleScheduleModel> dayMdlList = null;
        SimpleScheduleModel dspSchMdl = null;

        //時間指定なしスケジュール格納リスト
        LinkedHashMap<String, SimpleScheduleModel> periodMdlMap
                                   = new LinkedHashMap<String, SimpleScheduleModel>();

        UDate date = dspDate.cloneUDate();
        ScheduleSearchDao schDao = new ScheduleSearchDao(con);

        //表示グループに所属しているか判定
        GroupBiz gpBiz = new GroupBiz();
        boolean belongGrpHnt = gpBiz.isBelongGroup(usrSid, grpSid, con);

        //閲覧可能グループかを判定
        boolean accessGrp = true;
        SchDao scheduleDao = new SchDao(con);
        if (!myGpFlg) {
            accessGrp = scheduleDao.canAccessGroupSchedule(grpSid, usrSid);
        }

        //グループ
        if (!myGpFlg && accessGrp) {
            GroupDao grpDao = new GroupDao(con);
            CmnGroupmModel grpMdl = grpDao.getGroup(grpSid);

            weekMdl = new Sch010WeekOfModel();
            colList = new ArrayList<Sch010DayOfModel>();
            usMdl = new Sch010UsrModel();

            if (grpMdl != null) {
                usMdl.setUsrName(grpMdl.getGrpName());
            }
            usMdl.setUsrSid(grpSid);
            usMdl.setUsrKbn(GSConstSchedule.USER_KBN_GROUP);
            usMdl.setZaisekiKbn(GSConst.UIOSTS_IN);

            //スケジュール登録可能グループかを判定
            usMdl.setSchRegistFlg(scheduleDao.canRegistGroupSchedule(grpSid, usrSid));
            weekMdl.setSch010UsrMdl(usMdl);

            //スケジュール情報を取得(グループ)
            schDataList = schDao.select(
                    grpSid,
                    GSConstSchedule.USER_KBN_GROUP,
                    -1,
                    frDate,
                    toDate,
                    GSConstSchedule.DSP_MOD_WEEK,
                    sessionUsrSid);
            for (int i = 0; i < GSConstSchedule.WEEK_DAY_COUNT; i++) {
                //１日分のスケジュール
                dayMdlList = new ArrayList<SimpleScheduleModel>();
                dayMdl = new Sch010DayOfModel();
                holMdl = holMap.get(date.getDateString());
                if (holMdl != null) {
                  dayMdl.setHolidayName(holMdl.getHolName());
                } else {
                  dayMdl.setHolidayName(null);
                }

                dayMdl.setSchDate(date.getDateString());
                dayMdl.setUsrSid(grpSid);
                dayMdl.setUsrKbn(GSConstSchedule.USER_KBN_GROUP);
                dayMdl.setWeekKbn(date.getWeek());

                __checkToday(dayMdl, date);

                SchDataModel schMdl = null;
                for (int j = 0; j < schDataList.size(); j++) {
                    //スケジュール１個
                    schMdl = schDataList.get(j);
                    //本日のスケジュールか判定
                    if (isTodaySchedule(schMdl, date)) {
                        dspSchMdl = new SimpleScheduleModel();
                        dspSchMdl = __getSchDspGrpData(schMdl, date, belongGrpHnt, usrSid);
                        //鍵アイコン表示判定
                        if (dspSchMdl != null
                                && dspSchMdl.getPublic() == GSConstSchedule.DSP_NOT_PUBLIC) {
                            dspSchMdl.setPublicIconFlg(true);
                        }
                        if (dspSchMdl != null) {
                            dspSchMdl.setUserKbn(String.valueOf(GSConstSchedule.USER_KBN_GROUP));
                            if (schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
                                dayMdlList.add(dspSchMdl);
                            } else {
                                dspSchMdl.setFromDate(schMdl.getScdFrDate());
                                dspSchMdl.setToDate(schMdl.getScdToDate());
                                periodMdlMap.put(String.valueOf(dspSchMdl.getSchSid()), dspSchMdl);
                            }
                        }
                    }
                }
                dayMdl.setSchDataList(dayMdlList);
                colList.add(dayMdl);
                //日付を進める
                date.addDay(1);
            }
            weekMdl.setSch010SchList(colList);

            //期間スケジュールを取得
            weekMdl = __getPeriodSch(weekMdl, periodMdlMap, dspDate);
            rowList.add(weekMdl);
        }


        periodMdlMap = new LinkedHashMap<String, SimpleScheduleModel>();

        if (onlyGrpFlg) {
            //グループスケジュールのみ取得の場合
            return rowList;
        }

        //指定ユーザ
        weekMdl = new Sch010WeekOfModel();
        colList = new ArrayList<Sch010DayOfModel>();
        usMdl = new Sch010UsrModel();
        UserSearchDao usrDao = new UserSearchDao(con);
        UserSearchModel usrInfMdl = usrDao.getUserInfoJtkb(
                usrSid, GSConstUser.USER_JTKBN_ACTIVE);
        usMdl.setUsrName(usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());
        usMdl.setUsrSid(usrSid);
        usMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
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

        //スケジュール登録可能ユーザかを判定
        usMdl.setSchRegistFlg(scheduleDao.canRegistGroupSchedule(grpSid, usrSid));
        weekMdl.setSch010UsrMdl(usMdl);

        //スケジュール情報を取得(ユーザ)
        log__.debug("グループスケジュール取得SQL開始==>" + new UDate().getTimeMillis());
        //スケジュール情報を取得(グループ)
        //SQLチューニング
        CmnBelongmDao belongDao = new CmnBelongmDao(con);
        ArrayList<Integer> belongList = belongDao.selectUserBelongGroupSid(usrSid);
        schGpDataList = schDao.getBelongGroupSchData2(
                belongList,
                -1,
                frDate,
                toDate,
                GSConstSchedule.DSP_MOD_WEEK,
                sessionUsrSid);

        //スケジュール情報を取得(指定ユーザ)
        schDataList = schDao.select(
                usrSid,
                GSConstSchedule.USER_KBN_USER,
                -1,
                frDate,
                toDate,
                GSConstSchedule.DSP_MOD_WEEK,
                sessionUsrSid);

        //他プラグイン情報を取得
        __getAppendPlgData(dspDate, grpSid, usrSid, schDataList, con, 0);

        date = dspDate.cloneUDate();
        for (int i = 0; i < GSConstSchedule.WEEK_DAY_COUNT; i++) {
            //１日分のスケジュール
            dayMdlList = new ArrayList<SimpleScheduleModel>();
            dayMdl = new Sch010DayOfModel();
            holMdl = holMap.get(date.getDateString());
            if (holMdl != null) {
              dayMdl.setHolidayName(holMdl.getHolName());
            } else {
              dayMdl.setHolidayName(null);
            }
            dayMdl.setSchDate(date.getDateString());
            dayMdl.setUsrSid(usrSid);
            dayMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
            dayMdl.setWeekKbn(date.getWeek());

            __checkToday(dayMdl, date);

            SchDataModel schMdl = null;
            SchCommonBiz schBiz = new SchCommonBiz();
            for (int j = 0; j < schGpDataList.size(); j++) {
                //スケジュール１個
                schMdl = schGpDataList.get(j);
                //本日のスケジュールか判定
                if (isTodaySchedule(schMdl, date)) {
                    dspSchMdl = new SimpleScheduleModel();
                    dspSchMdl = __getSchMainData(schMdl, usrSid);
                    dspSchMdl.setSchSid(schMdl.getScdSid());
                    dspSchMdl.setTime(getTimeString(schMdl, date));
                    dspSchMdl.setPublic(schMdl.getScdPublic());
                    dspSchMdl.setTimeKbn(schMdl.getScdDaily());
                    dspSchMdl.setBgColor(schMdl.getScdBgcolor());
                    dspSchMdl.setUserSid(String.valueOf(schMdl.getScdUsrSid()));
                    dspSchMdl.setUserKbn(String.valueOf(GSConstSchedule.USER_KBN_GROUP));
                    dspSchMdl.setValueStr(schMdl.getScdValue());
                    dspSchMdl.setPublicIconFlg(
                            schBiz.getPublicIconFlg(schMdl, usrSid, belongGrpHnt, false));
                    if (schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
                        dayMdlList.add(dspSchMdl);
                    } else {
                        dspSchMdl.setFromDate(schMdl.getScdFrDate());
                        dspSchMdl.setToDate(schMdl.getScdToDate());
                        periodMdlMap.put(String.valueOf(dspSchMdl.getSchSid()), dspSchMdl);
                    }
                }
            }

            schMdl = null;
            for (int j = 0; j < schDataList.size(); j++) {
                //スケジュール１個
                schMdl = schDataList.get(j);
                //本日のスケジュールか判定
                if (isTodaySchedule(schMdl, date)) {
                    dspSchMdl = new SimpleScheduleModel();
                    dspSchMdl.setKjnEdKbn(GSConstSchedule.EDIT_CONF_OWN);
                    dspSchMdl.setSchSid(schMdl.getScdSid());
                    dspSchMdl.setTitle(schMdl.getScdTitle());
                    dspSchMdl.setTime(getTimeString(schMdl, date));
                    dspSchMdl.setPublic(schMdl.getScdPublic());
                    dspSchMdl.setTimeKbn(schMdl.getScdDaily());
                    dspSchMdl.setBgColor(schMdl.getScdBgcolor());
                    if (schMdl.getScdAppendUrl() == null) {
                        //スケジュールのデータ
                        dspSchMdl.setSchSid(schMdl.getScdSid());
                        dspSchMdl.setUserKbn(String.valueOf(GSConstSchedule.USER_KBN_USER));
                        dspSchMdl.setPublicIconFlg(
                                schBiz.getPublicIconFlg(schMdl, usrSid, belongGrpHnt, false));
                    } else {
                        //スケジュール以外のプラグインのデータ
                        dspSchMdl.setSchAppendUrl(schMdl.getScdAppendUrl());
                        dspSchMdl.setUserKbn(schMdl.getScdAppendId());
                    }
                    dspSchMdl.setValueStr(schMdl.getScdValue());
                    if (schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
                        dayMdlList.add(dspSchMdl);
                    } else {
                        dspSchMdl.setFromDate(schMdl.getScdFrDate());
                        dspSchMdl.setToDate(schMdl.getScdToDate());
                        if (schMdl.getScdAppendUrl() == null) {
                            //スケジュールのデータ
                            if (schMdl.getScdUsrSid() == sessionUsrSid) {
                                //本人
                                periodMdlMap.put(String.valueOf(dspSchMdl.getSchSid()), dspSchMdl);
                            } else {
                                //他ユーザ
                                if (schMdl.getScdPublic() != GSConstSchedule.DSP_NOT_PUBLIC) {
                                    //非公開以外のスケジュール
                                    periodMdlMap.put(String.valueOf(dspSchMdl.getSchSid()),
                                                                                         dspSchMdl);
                                }
                            }
                        } else {
                            //スケジュール以外のプラグインのデータ
                            periodMdlMap.put(schMdl.getScdAppendUrl(), dspSchMdl);
                        }
                    }
                }
            }

            dayMdl.setSchDataList(dayMdlList);

            colList.add(dayMdl);
            //日付を進める
            date.addDay(1);
        }

        weekMdl.setSch010SchList(colList);

        //期間スケジュールを取得
        weekMdl = __getPeriodSch(weekMdl, periodMdlMap, dspDate);

        rowList.add(weekMdl);

        return rowList;
    }

    /**
     * <br>グループ(複数)と指定ユーザの週間スケジュールを取得します
     * @param dspDate 開始日付
     * @param usrSid ユーザSID
     * @param myGpFlg マイグループ選択フラグ
     * @param mainDspFlg TODOとアクションリンクのメイン画面戻り先フラグ
     * @param con コネクション
     * @return ArrayList グループ>指定ユーザの順に格納
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Sch010WeekOfModel> getWeekScheduleTopListWithBelongGroup(
            UDate dspDate,
            int usrSid,
            boolean myGpFlg,
            boolean mainDspFlg,
            Connection con) throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        UDate frDate = dspDate.cloneUDate();
        frDate.setHour(GSConstSchedule.DAY_START_HOUR);
        frDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        frDate.setSecond(GSConstSchedule.DAY_START_SECOND);

        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(GSConstSchedule.WEEK_DAY_COUNT - 1);
        toDate.setHour(GSConstSchedule.DAY_END_HOUR);
        toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_END_SECOND);


        //グループ・指定ユーザのcolListを保持
        ArrayList<Sch010WeekOfModel> rowList = new ArrayList<Sch010WeekOfModel>();
        //ユーザ情報を保持
        Sch010UsrModel usMdl = null;
        ArrayList<Sch010DayOfModel> colList = null;
        //DBスケジュール情報
        ArrayList < SchDataModel > schDataList = null;
        //ユーザ別、１週間分のスケジュール
        Sch010WeekOfModel weekMdl = null;

        //休日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayList(frDate, toDate);
        CmnHolidayModel holMdl = null;

        //時間指定なしスケジュール格納リスト
        LinkedHashMap<String, SimpleScheduleModel> periodMdlMap
                                   = new LinkedHashMap<String, SimpleScheduleModel>();

        Sch010DayOfModel dayMdl = null;
        ArrayList<SimpleScheduleModel> dayMdlList = null;
        SimpleScheduleModel dspSchMdl = null;

        UDate date = dspDate.cloneUDate();
        ScheduleSearchDao schDao = new ScheduleSearchDao(con);
        log__.debug("グループスケジュール取得開始==>" + new UDate().getTimeMillis());

        //グループ
        if (!myGpFlg) {

            weekMdl = new Sch010WeekOfModel();
            colList = new ArrayList<Sch010DayOfModel>();
            usMdl = new Sch010UsrModel();

            usMdl.setUsrSid(-1);
            usMdl.setUsrKbn(GSConstSchedule.USER_KBN_GROUP);
            usMdl.setZaisekiKbn(GSConst.UIOSTS_IN);

            weekMdl.setSch010UsrMdl(usMdl);
            log__.debug("グループスケジュール取得SQL開始==>" + new UDate().getTimeMillis());
            //スケジュール情報を取得(グループ)
            //SQLチューニング
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            ArrayList<Integer> belongList = belongDao.selectUserBelongGroupSid(usrSid);
            schDataList = schDao.getBelongGroupSchData2(
                    belongList,
                    -1,
                    frDate,
                    toDate,
                    GSConstSchedule.DSP_MOD_WEEK,
                    sessionUsrSid);
            log__.debug("グループスケジュール取得SQL終了==>"  + new UDate().getTimeMillis());
            for (int i = 0; i < GSConstSchedule.WEEK_DAY_COUNT; i++) {
                //１日分のスケジュール
                dayMdlList = new ArrayList<SimpleScheduleModel>();
                dayMdl = new Sch010DayOfModel();
                holMdl = holMap.get(date.getDateString());
                if (holMdl != null) {
                  dayMdl.setHolidayName(holMdl.getHolName());
                } else {
                  dayMdl.setHolidayName(null);
                }

                dayMdl.setSchDate(date.getDateString());
                dayMdl.setUsrSid(-1);
                dayMdl.setUsrKbn(GSConstSchedule.USER_KBN_GROUP);
                dayMdl.setWeekKbn(date.getWeek());

                __checkToday(dayMdl, date);

                SchDataModel schMdl = null;
                for (int j = 0; j < schDataList.size(); j++) {
                    //スケジュール１個
                    schMdl = schDataList.get(j);
                    //本日のスケジュールか判定
                    if (isTodaySchedule(schMdl, date)) {
                        dspSchMdl = new SimpleScheduleModel();
                        dspSchMdl = __getSchMainData(schMdl, usrSid);
                        dspSchMdl.setSchSid(schMdl.getScdSid());
                        dspSchMdl.setTime(getTimeString(schMdl, date));
                        dspSchMdl.setPublic(schMdl.getScdPublic());
                        dspSchMdl.setTimeKbn(schMdl.getScdDaily());
                        dspSchMdl.setBgColor(schMdl.getScdBgcolor());
                        dspSchMdl.setUserSid(String.valueOf(schMdl.getScdUsrSid()));
                        dspSchMdl.setUserKbn(String.valueOf(GSConstSchedule.USER_KBN_GROUP));
                        dspSchMdl.setValueStr(schMdl.getScdValue());
                        //鍵アイコン表示判定
                        if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC) {
                            dspSchMdl.setPublicIconFlg(true);
                        }
                        if (schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
                            dayMdlList.add(dspSchMdl);
                        } else {
                            dspSchMdl.setFromDate(schMdl.getScdFrDate());
                            dspSchMdl.setToDate(schMdl.getScdToDate());

                            periodMdlMap.put(String.valueOf(dspSchMdl.getSchSid()), dspSchMdl);
                        }
                    }
                }
                dayMdl.setSchDataList(dayMdlList);
                colList.add(dayMdl);
                //日付を進める
                date.addDay(1);
            }

            weekMdl.setSch010SchList(colList);

            rowList.add(weekMdl);
        }

        log__.debug("グループスケジュール取得終了==>" + new UDate().getTimeMillis());

        //指定ユーザ
        weekMdl = new Sch010WeekOfModel();
        colList = new ArrayList<Sch010DayOfModel>();
        usMdl = new Sch010UsrModel();
        UserSearchDao usrDao = new UserSearchDao(con);
        UserSearchModel usrInfMdl = usrDao.getUserInfoJtkb(
                usrSid, GSConstUser.USER_JTKBN_ACTIVE);
        usMdl.setUsrName(usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());
        usMdl.setUsrSid(usrSid);
        usMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
        usMdl.setZaisekiKbn(usrInfMdl.getUioStatus());
        usMdl.setZaisekiMsg(usrInfMdl.getUioComment());
        usMdl.setSchRegistFlg(true);
        weekMdl.setSch010UsrMdl(usMdl);
        log__.debug("ユーザスケジュール取得SQL開始==>"  + new UDate().getTimeMillis());

        //スケジュール情報を取得(指定ユーザ)
        schDataList = schDao.select(
                usrSid,
                GSConstSchedule.USER_KBN_USER,
                -1,
                frDate,
                toDate,
                GSConstSchedule.DSP_MOD_WEEK,
                sessionUsrSid);
        log__.debug("ユーザスケジュール取得SQL終了==>"  + new UDate().getTimeMillis());

        //他プラグイン情報を取得
        int plgKbn = 0;
        if (mainDspFlg) {
            plgKbn = 1;
        }
        __getAppendPlgData(dspDate, -1, usrSid, schDataList, con, plgKbn);

        date = dspDate.cloneUDate();
        for (int i = 0; i < GSConstSchedule.WEEK_DAY_COUNT; i++) {
            //１日分のスケジュール
            dayMdlList = new ArrayList<SimpleScheduleModel>();
            dayMdl = new Sch010DayOfModel();
            dayMdl.setHolidayName(null);
            dayMdl.setSchDate(date.getDateString());
            dayMdl.setUsrSid(usrSid);
            dayMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
            dayMdl.setWeekKbn(date.getWeek());

            __checkToday(dayMdl, date);

            SchCommonBiz schBiz = new SchCommonBiz();
            SchDataModel schMdl = null;
            for (int j = 0; j < schDataList.size(); j++) {
                //スケジュール１個
                schMdl = schDataList.get(j);
                //本日のスケジュールか判定
                if (isTodaySchedule(schMdl, date)) {
                    dspSchMdl = new SimpleScheduleModel();
                    dspSchMdl.setSchSid(schMdl.getScdSid());
                    dspSchMdl.setTitle(schMdl.getScdTitle());
                    dspSchMdl.setTime(getTimeString(schMdl, date));
                    dspSchMdl.setPublic(schMdl.getScdPublic());
                    dspSchMdl.setTimeKbn(schMdl.getScdDaily());
                    dspSchMdl.setBgColor(schMdl.getScdBgcolor());
                    dspSchMdl.setUserSid(String.valueOf(usrSid));
                    dspSchMdl.setValueStr(schMdl.getScdValue());
                    dspSchMdl.setKjnEdKbn(GSConstSchedule.EDIT_CONF_OWN);
                    if (schMdl.getScdAppendUrl() == null) {
                        //スケジュールのデータ
                        dspSchMdl.setSchSid(schMdl.getScdSid());
                        dspSchMdl.setUserKbn(String.valueOf(GSConstSchedule.USER_KBN_USER));
                        dspSchMdl.setPublicIconFlg(
                                schBiz.getPublicIconFlg(schMdl, usrSid, false, false));
                    } else {
                        //スケジュール以外のプラグインのデータ
                        dspSchMdl.setSchAppendUrl(schMdl.getScdAppendUrl());
                        dspSchMdl.setUserKbn(schMdl.getScdAppendId());
                    }
                    if (schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
                        dayMdlList.add(dspSchMdl);
                    } else {
                        dspSchMdl.setFromDate(schMdl.getScdFrDate());
                        dspSchMdl.setToDate(schMdl.getScdToDate());

                        if (StringUtil.isNullZeroStringSpace(schMdl.getScdAppendUrl())) {
                            //スケジュールのデータ
                            if (schMdl.getScdUsrSid() == sessionUsrSid) {
                                //本人
                                periodMdlMap.put(String.valueOf(dspSchMdl.getSchSid()), dspSchMdl);
                            } else {
                                //他ユーザ
                                if (schMdl.getScdPublic() != GSConstSchedule.DSP_NOT_PUBLIC) {
                                    //非公開以外のスケジュール
                                    periodMdlMap.put(String.valueOf(dspSchMdl.getSchSid()),
                                                                                         dspSchMdl);
                                }
                            }
                        } else {
                            //スケジュール以外のプラグインのデータ
                            periodMdlMap.put(schMdl.getScdAppendUrl(), dspSchMdl);
                        }
                    }
                }
            }
            dayMdl.setSchDataList(dayMdlList);
            colList.add(dayMdl);
            //日付を進める
            date.addDay(1);
        }

        weekMdl.setSch010SchList(colList);

        //期間スケジュールを取得
        weekMdl = __getPeriodSch(weekMdl, periodMdlMap, dspDate);

        rowList.add(weekMdl);
        log__.debug("スケジュール取得終了==>"  + new UDate().getTimeMillis());
        return rowList;
    }
    /**
     * <br>表示グループに所属するユーザの週間スケジュールを取得します
     * @param dspDate 開始日付
     * @param gpSid 表示グループSID
     * @param usrSid セッションユーザSID
     * @param myGroupFlg マイグループ選択フラグ
     * @param con コネクション
     * @return ArrayList 役職>姓カナ>名カナの順に格納
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<Sch010WeekOfModel> __getWeekScheduleBottomList(
            UDate dspDate,
            int gpSid,
            int usrSid,
            boolean myGroupFlg,
            Connection con) throws SQLException {

        //所属ユーザを取得
        UserSearchDao usDao = new UserSearchDao(con);

        //除外するユーザSIDを設定
        ArrayList<Integer> usrSids = new ArrayList<Integer>();
        usrSids.add(GSConstUser.SID_ADMIN);
        usrSids.add(GSConstUser.SID_SYSTEM_MAIL);
        usrSids.add(usrSid); //本人も表示しない

        //スケジュール個人設定で取得した表示順を取得する。
        SchCommonBiz sBiz = new SchCommonBiz(reqMdl__);
        SchPriConfModel pconf = sBiz.getSchPriConfModel(con, usrSid);
        SchAdmConfModel aconf = sBiz.getAdmConfModel(con);

        int sortKey1 = -1;
        int orderKey1 = -1;
        int sortKey2 = -1;
        int orderKey2 = -1;

        //各ユーザが設定したメンバー表示順
        if (pconf.getSccSortEdit() == GSConstSchedule.MEM_EDIT_EXECUTE
               && aconf.getSadSortKbn() == GSConstSchedule.MEM_DSP_USR) {
            log__.debug("***ユーザが設定したソート順で表示します***");
            sortKey1 = pconf.getSccSortKey1();
            orderKey1 = pconf.getSccSortOrder1();
            sortKey2 = pconf.getSccSortKey2();
            orderKey2 = pconf.getSccSortOrder2();

        //管理者で設定したメンバー表示順
        } else {

            log__.debug("***管理者が設定したソート順で表示します***");
            sortKey1 = aconf.getSadSortKey1();
            orderKey1 = aconf.getSadSortOrder1();
            sortKey2 = aconf.getSadSortKey2();
            orderKey2 = aconf.getSadSortOrder2();
        }

        //表示するグループメンバーを取得
        SchDao schDao = new SchDao(con);
        ArrayList<UserSearchModel> belongList = null;
        List<Integer> notAccessUserList = null;
        if (!myGroupFlg) {
            belongList = usDao.getBelongUserInfoJtkb(gpSid,
                    usrSids, sortKey1, orderKey1, sortKey2, orderKey2);
            notAccessUserList = schDao.getNotAccessUserList(gpSid, usrSid);
        } else {
            belongList = usDao.getMyGroupBelongUserInfoJtkb(gpSid, usrSid,
                    usrSids, sortKey1, orderKey1, sortKey2, orderKey2);
            notAccessUserList = schDao.getNotAccessUserList(usrSid);
        }

        //ショートメール有効設定
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

        //表示グループに所属しているか判定
        GroupBiz gpBiz = new GroupBiz();
        boolean belongGrpHnt = gpBiz.isBelongGroup(usrSid, gpSid, con);

        //一括で生成する様に変更
        ArrayList<Sch010WeekOfModel> rowList = getWeekUserScheduleNew(
                belongList, dspDate.cloneUDate(), con, belongGrpHnt, myGroupFlg, gpSid);
        return rowList;
    }

    /**
     * <br>表示リストで指定されたユーザ・グループの週間スケジュールを設定します
     * @param paramMdl パラメータ情報
     * @param dspDate 開始日付
     * @param smySid 表示リストSID
     * @param usrSid セッションユーザSID
     * @param con コネクション
     * @param confMdl 個人設定情報
     * @throws SQLException SQL実行時例外
     */
    private void __setWeekScheduleViewList(
            Sch010ParamModel paramMdl,
            UDate dspDate,
            int smySid,
            int usrSid,
            Connection con,
            SchPriConfModel confMdl) throws SQLException {

        //閲覧を許可されていないグループ・ユーザを除外する
        SchDao schDao = new SchDao(con);
        List<Integer> notAccessUserList = schDao.getNotAccessUserList(usrSid);
        List<Integer> notAccessGroupList = schDao.getNotAccessGrpList(usrSid);

        //表示リストに指定されたグループ・ユーザを取得する
        SchMyviewlistBelongDao myViewBelongDao = new SchMyviewlistBelongDao(con);
        List<MyViewListBelongModel> viewList = myViewBelongDao.getBelongDataList(smySid, notAccessUserList, notAccessGroupList);

        List<Integer> smlUsrs = new ArrayList<Integer>();
        List<MyViewListBelongModel> viewList2 = new ArrayList<MyViewListBelongModel>();
        for (MyViewListBelongModel viewMdl : viewList) {
            if (viewMdl.getUsrSid() > GSConstUser.USER_RESERV_SID) {
                //ユーザ
                viewList2.add(viewMdl);
                smlUsrs.add(viewMdl.getUsrSid());
            } else if (viewMdl.getGrpSid() >= 0) {
                //グループ
                viewList2.add(viewMdl);
            }
        }
        viewList.clear();
        viewList.addAll(viewList2);

        //ショートメール有効設定
        //ショートメールプラグインを使用可能なユーザを取得する。
        CommonBiz commonBiz = new CommonBiz();
        smlUsrs = (ArrayList<Integer>) commonBiz.getCanUseSmailUser(con, smlUsrs);
        SmlCommonBiz smlCommonBiz = new SmlCommonBiz(con, reqMdl__);
        smlUsrs = smlCommonBiz.getValiableDestUsrSid(con,
                reqMdl__.getSmodel().getUsrsid(), smlUsrs);


        UDate frDate = dspDate.cloneUDate();
        frDate.setHour(GSConstSchedule.DAY_START_HOUR);
        frDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        frDate.setSecond(GSConstSchedule.DAY_START_SECOND);

        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(GSConstSchedule.WEEK_DAY_COUNT - 1);
        toDate.setHour(GSConstSchedule.DAY_END_HOUR);
        toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_END_SECOND);

        //休日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayList(frDate, toDate);

        UserSearchDao usrSearchDao = new UserSearchDao(con);
        ArrayList<Sch010WeekOfModel> rowList = new ArrayList<Sch010WeekOfModel>();
        Sch010WeekOfModel weekMdl = null;
        for (MyViewListBelongModel viewMdl : viewList) {
            if (viewMdl.getGrpSid() >= 0) {
                weekMdl = __createGrpScheduleModel(
                        dspDate,
                        viewMdl.getGrpSid(),
                        frDate, toDate,
                        holMap,
                        usrSid,
                        con);
                rowList.add(weekMdl);
            } else if (viewMdl.getUsrSid() >= 0) {
                UserSearchModel usrMdl
                    = usrSearchDao.getUserInfoJtkb(viewMdl.getUsrSid(),
                                                GSConstUser.USER_JTKBN_ACTIVE);
                //ショートメール有効無効設定
                if (!smlUsrs.contains(usrMdl.getUsrSid())) {
                    usrMdl.setSmlAble(0);
                } else {
                    usrMdl.setSmlAble(1);
                }

                ArrayList<UserSearchModel> usrSearchList = new ArrayList<UserSearchModel>();
                usrSearchList.add(usrMdl);
                
                //セッションユーザかつグループスケジュールを表示する場合、グループスケジュールも含めて取得
                if (usrMdl.getUsrSid() == usrSid
                        && confMdl.getSccGrpShowKbn() == GSConstSchedule.GROUP_SCH_SHOW) {
                    Sch010WeekOfModel usrGroupSchedule = 
                            getUserGroupSchedule(usrSid, usrMdl, dspDate, con);
                    paramMdl.setSch010UserSchedule(usrGroupSchedule);
                    rowList.add(usrGroupSchedule);
                } else {
                    //セッションユーザ以外の場合、そのユーザのスケジュールを取得
                    rowList.addAll(
                            getWeekUserScheduleNew(
                                    usrSearchList,
                                    dspDate.cloneUDate(),
                                    con,
                                    viewMdl.isBelongGrpFlg(),
                                    false,
                                    -1));
                }
            }
        }
        paramMdl.setSch010BottomList(rowList);
    }
    
    /**
     * <br>[機  能] 指定したユーザのスケジュール + 所属しているグループのスケジュールを取得します
     * <br>[解  説]
     * <br>[備  考] 指定したユーザがセッションユーザの場合、
     * @param usrSid セッションユーザSID
     * @param usrMdl UserSearchModel
     * @param dspDate スケジュール取得対象日
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return 指定したユーザのスケジュール + 所属しているグループのスケジュール
     */
    public Sch010WeekOfModel getUserGroupSchedule(int usrSid,
            UserSearchModel usrMdl, UDate dspDate, Connection con) throws SQLException {
        
        //セッションユーザかつグループスケジュールを表示する場合、グループスケジュールも含めて取得
        ArrayList<Sch010WeekOfModel> usrGrpSchList =
                getWeekScheduleTopListWithBelongGroup(
                        dspDate.cloneUDate(), usrSid, false, false, con);

        Sch010WeekOfModel usrWeekOfModel = null;
        ArrayList<SimpleScheduleModel> groupSchList = null;
        HashMap<Integer, ArrayList<SimpleScheduleModel>> groupSchMap = 
                new HashMap<Integer, ArrayList<SimpleScheduleModel>>();
        ArrayList<ArrayList<SimpleScheduleModel>> groupNoTimeSchList = 
                new ArrayList<ArrayList<SimpleScheduleModel>>();
        for (Sch010WeekOfModel model : usrGrpSchList) {
            if (model.getSch010UsrMdl().getUsrKbn() == GSConstSchedule.USER_KBN_USER
                    && model.getSch010UsrMdl().getUsrSid() == usrSid) {
                usrWeekOfModel = model;
            } else {
                //セッションユーザのグループスケジュール一覧の取得
                if (model.getSch010SchList() != null) {
                    //一日ごとに分けてグループスケジュールを保存
                    for (Sch010DayOfModel dayMdl : model.getSch010SchList()) {
                        if (dayMdl.getSchDataList() == null
                                || dayMdl.getSchDataList().size() == 0) {
                            continue;
                        }
                        groupSchList = groupSchMap.get(dayMdl.getWeekKbn());
                        if (groupSchList == null) {
                            groupSchList = new ArrayList<SimpleScheduleModel>();
                        }
                        groupSchList.addAll(dayMdl.getSchDataList());
                        groupSchMap.put(dayMdl.getWeekKbn(), groupSchList);
                    }
                }
                if (model.getSch010NoTimeSchList() != null) {
                    groupNoTimeSchList.addAll(model.getSch010NoTimeSchList());
                }
            }
        }

        if (usrWeekOfModel != null) {
            int key;
            //セッションユーザが所属しているグループのスケジュールをユーザスケジュールに追加
            for (Sch010DayOfModel dayMdl : usrWeekOfModel.getSch010SchList()) {
                key = dayMdl.getWeekKbn();
                if (groupSchMap.get(key) != null) {
                    dayMdl.getSchDataList().addAll(0, groupSchMap.get(key));
                }
            }
            if (usrWeekOfModel.getSch010NoTimeSchList() != null) {
                usrWeekOfModel.getSch010NoTimeSchList().addAll(0, groupNoTimeSchList);
            }
        }
        return usrWeekOfModel;
    }

    /**
     * <br>ユーザリストの１週間分のスケジュールを取得します
     * @param belongList ユーザ情報一覧
     * @param dspDate 表示開始日付
     * @param con コネクション
     * @param belongGrpHnt 所属グループ判定 true:所属している false:所属していない
     * @param myGroupFlg マイグループ選択フラグ
     * @param gpSid グループSID
     * @return Sch010WeekOfModel 週間スケジュール
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Sch010WeekOfModel> getWeekUserScheduleNew(
            ArrayList<UserSearchModel> belongList,
            UDate dspDate,
            Connection con,
            boolean belongGrpHnt,
            boolean myGroupFlg,
            int gpSid) throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        ArrayList<Sch010WeekOfModel> rowList = new ArrayList<Sch010WeekOfModel>();

        UDate fromDate = dspDate.cloneUDate();
        fromDate.setHour(GSConstSchedule.DAY_START_HOUR);
        fromDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        fromDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(6);
        toDate.setHour(GSConstSchedule.DAY_END_HOUR);
        toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_END_SECOND);
        Sch010WeekOfModel weekMdl = null;

        //休日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayList(fromDate, toDate);
        CmnHolidayModel holMdl = null;

        //時間指定なしスケジュール格納リスト
        LinkedHashMap<String, SimpleScheduleModel> periodMdlMap = null;

        //スケジュール情報を取得(指定ユーザ)
        ScheduleSearchDao schDao = new ScheduleSearchDao(con);
        ArrayList < SchDataModel > schDataList = schDao.selectUsers(
                belongList,
                GSConstSchedule.USER_KBN_USER,
                -1,
                fromDate,
                toDate,
                GSConstSchedule.DSP_MOD_WEEK);


        List<Integer> checkScdSidList =
                schDataList.stream()
                .map(s -> s.getScdSid())
                .collect(Collectors.toList());

        //指定公開スケジュールのスケジュールSIDを取得する
        SchDataPubDao schPubDao = new SchDataPubDao(con);
        List<Integer> pubScdSidList
            = schPubDao.getUserPubScdSidList(sessionUsrSid, checkScdSidList);

        //他プラグイン情報を取得
        List<Integer> usrSidList = new ArrayList<>();
        for (UserSearchModel usrMdl : belongList) {
            usrSidList.add(usrMdl.getUsrSid());
        }
        __getAppendPlgData(dspDate, gpSid, usrSidList, schDataList, con, 0);


        //スケジュール登録不可ユーザを取得
        SchDao scheduleDao = new SchDao(con);
        List<Integer> notRegistUserList
            = scheduleDao.getNotRegistUserList(sessionUsrSid);

        //所属ユーザループ
        UserSearchModel dbUsrMdl = null;
        for (int n = 0; n < belongList.size(); n++) {

            periodMdlMap = new LinkedHashMap<String, SimpleScheduleModel>();

            dbUsrMdl = belongList.get(n);
            //ユーザ別に１週間のスケジュールを取得
            weekMdl = new Sch010WeekOfModel();
            ArrayList<Sch010DayOfModel> colList = new ArrayList<Sch010DayOfModel>();

            //指定ユーザ
            int usrSid = dbUsrMdl.getUsrSid();
            Sch010UsrModel usMdl = new Sch010UsrModel();

            //スケジュール登録可能フラグ
            usMdl.setSchRegistFlg(notRegistUserList.indexOf(usrSid) < 0);

            usMdl.setUsrName(dbUsrMdl.getUsiSei() + " " + dbUsrMdl.getUsiMei());
            usMdl.setUsrSid(usrSid);
            usMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
            usMdl.setZaisekiKbn(dbUsrMdl.getUioStatus());
            usMdl.setZaisekiMsg(dbUsrMdl.getUioComment());
            usMdl.setSmlAble(dbUsrMdl.getSmlAble());
            usMdl.setSchUkoFlg(dbUsrMdl.getUsrUkoFlg());
            weekMdl.setSch010UsrMdl(usMdl);

            UDate date = dspDate.cloneUDate();
            ArrayList<SimpleScheduleModel> dayMdlList = null;
            Sch010DayOfModel dayMdl = null;
            SimpleScheduleModel dspSchMdl = null;
            for (int i = 0; i < GSConstSchedule.WEEK_DAY_COUNT; i++) {
                //１日分のスケジュール
                dayMdlList = new ArrayList<SimpleScheduleModel>();
                dayMdl = new Sch010DayOfModel();
                holMdl = holMap.get(date.getDateString());
                if (holMdl != null) {
                    dayMdl.setHolidayName(holMdl.getHolName());
                } else {
                    dayMdl.setHolidayName(null);
                }
                dayMdl.setSchDate(date.getDateString());
                dayMdl.setUsrSid(usrSid);
                dayMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
                dayMdl.setWeekKbn(date.getWeek());

                __checkToday(dayMdl, date);

                SchDataModel schMdl = null;
                for (int j = 0; j < schDataList.size(); j++) {
                    //スケジュール１個
                    schMdl = schDataList.get(j);
                    if (schMdl.getScdUsrSid() != usrSid) {
                        continue;
                    }

                    //本日のスケジュールか判定
                    if (isTodaySchedule(schMdl, date)) {
                        dspSchMdl = new SimpleScheduleModel();
                        dspSchMdl =
                                __getSchDspMdl(
                                        schMdl, date, belongGrpHnt, myGroupFlg, sessionUsrSid,
                                        pubScdSidList);
                        if (schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
                            dayMdlList.add(dspSchMdl);
                        } else {
                            dspSchMdl.setFromDate(schMdl.getScdFrDate());
                            dspSchMdl.setToDate(schMdl.getScdToDate());
                            if (schMdl.getScdAppendUrl() == null) {
                                //スケジュールのデータ
                                if (schMdl.getScdUsrSid() == sessionUsrSid) {
                                    //本人
                                    periodMdlMap.put(String.valueOf(dspSchMdl.getSchSid()),
                                                                                  dspSchMdl);
                                } else {
                                    //他ユーザ
                                    if (schMdl.getScdPublic() != GSConstSchedule.DSP_NOT_PUBLIC
                                            || schMdl.getScdAuid() == sessionUsrSid
                                            || schMdl.getScdEuid() == sessionUsrSid) {
                                        //非公開以外のスケジュール
                                        periodMdlMap.put(String.valueOf(dspSchMdl.getSchSid()),
                                                                                       dspSchMdl);
                                    }
                                }
                            } else {
                                //スケジュール以外のプラグインのデータ
                                periodMdlMap.put(schMdl.getScdAppendUrl(), dspSchMdl);
                            }
                        }
                    }
                }
                dayMdl.setSchDataList(dayMdlList);
                colList.add(dayMdl);
                //日付を進める
                date.addDay(1);
            }
            weekMdl.setSch010SchList(colList);

            //期間スケジュールを取得
            weekMdl = __getPeriodSch(weekMdl, periodMdlMap, dspDate);

            rowList.add(weekMdl);
        }

        return rowList;
    }

    /**
     * <br>ユーザ毎の１週間分のスケジュールを取得します
     * @param usrSid ユーザSID
     * @param dspDate 表示開始日付
     * @param con コネクション
     * @param sessionUserSid セッションユーザSID
     * @return Sch010WeekOfModel 週間スケジュール
     * @throws SQLException SQL実行時例外
     */
    public Sch010WeekOfModel getWeekUserSchedule(int usrSid, UDate dspDate, Connection con,
                                                                            int sessionUserSid)
    throws SQLException {

        UDate fromDate = dspDate.cloneUDate();
        fromDate.setHour(GSConstSchedule.DAY_START_HOUR);
        fromDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        fromDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(6);
        toDate.setHour(GSConstSchedule.DAY_END_HOUR);
        toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_END_SECOND);
        Sch010WeekOfModel weekMdl = new Sch010WeekOfModel();

        //指定ユーザ
        weekMdl = new Sch010WeekOfModel();
        ArrayList<Sch010DayOfModel> colList = new ArrayList<Sch010DayOfModel>();
        Sch010UsrModel usMdl = new Sch010UsrModel();
        UserSearchDao usrDao = new UserSearchDao(con);
        UserSearchModel usrInfMdl = usrDao.getUserInfoJtkb(
                usrSid, GSConstUser.USER_JTKBN_ACTIVE);
        usMdl.setUsrName(usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());
        usMdl.setUsrSid(usrSid);
        usMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
        usMdl.setZaisekiKbn(usrInfMdl.getUioStatus());
        weekMdl.setSch010UsrMdl(usMdl);

        //スケジュール情報を取得(指定ユーザ)
        //DBスケジュール情報
        ScheduleSearchDao schDao = new ScheduleSearchDao(con);
        ArrayList < SchDataModel > schDataList = schDao.select(
                usrSid,
                GSConstSchedule.USER_KBN_USER,
                -1,
                fromDate,
                toDate,
                GSConstSchedule.DSP_MOD_WEEK,
                sessionUserSid);

        //指定公開スケジュールのスケジュールSIDを取得する
        List<Integer> checkScdSidList =
                schDataList.stream()
                .map(s -> s.getScdSid())
                .collect(Collectors.toList());

        SchDataPubDao schPubDao = new SchDataPubDao(con);
        List<Integer> pubScdSidList
            = schPubDao.getUserPubScdSidList(sessionUserSid, checkScdSidList);

        SchCommonBiz schBiz = new SchCommonBiz();

        UDate date = dspDate.cloneUDate();
        ArrayList<SimpleScheduleModel> dayMdlList = null;
        Sch010DayOfModel dayMdl = null;
        SimpleScheduleModel dspSchMdl = null;
        for (int i = 0; i < GSConstSchedule.WEEK_DAY_COUNT; i++) {
            //１日分のスケジュール
            dayMdlList = new ArrayList<SimpleScheduleModel>();
            dayMdl = new Sch010DayOfModel();
            dayMdl.setHolidayName(null);
            dayMdl.setSchDate(date.getDateString());
            dayMdl.setUsrSid(usrSid);
            dayMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
            dayMdl.setWeekKbn(date.getWeek());

            __checkToday(dayMdl, date);

            SchDataModel schMdl = null;
            //予定あり
            GsMessage gsMsg = new GsMessage(reqMdl__);
            String textYoteiari = gsMsg.getMessage("schedule.src.9");
            for (int j = 0; j < schDataList.size(); j++) {
                //スケジュール１個
                schMdl = schDataList.get(j);
                //本日のスケジュールか判定
                if (isTodaySchedule(schMdl, date)) {
                    dspSchMdl = new SimpleScheduleModel();
                    if (schMdl.getScdPublic() == GSConstSchedule.DSP_YOTEIARI) {
                        //予定あり
                        dspSchMdl.setTitle(textYoteiari);
                    } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC) {
                        //非公開
                        continue;
                    } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
                        //指定ユーザ・グループのみ公開
                        if (pubScdSidList.contains(schMdl.getScdSid())) {
                            dspSchMdl.setTitle(schMdl.getScdTitle());
                        } else {
                            //公開対象グループ・ユーザに該当しない場合、「予定あり」
                            dspSchMdl.setTitle(textYoteiari);
                            dspSchMdl.setPublic(GSConstSchedule.DSP_YOTEIARI);
                        }
                    } else {
                        //公開
                        dspSchMdl.setTitle(schMdl.getScdTitle());
                    }
                    dspSchMdl.setSchSid(schMdl.getScdSid());
                    dspSchMdl.setTime(getTimeString(schMdl, date));
                    dspSchMdl.setPublic(schMdl.getScdPublic());
                    dspSchMdl.setTimeKbn(schMdl.getScdDaily());
                    dspSchMdl.setBgColor(schMdl.getScdBgcolor());
                    dspSchMdl.setPublicIconFlg(
                            schBiz.getPublicIconFlg(schMdl, sessionUserSid, false, false));
                    dayMdlList.add(dspSchMdl);
                }
            }
            dayMdl.setSchDataList(dayMdlList);
            colList.add(dayMdl);
            //日付を進める
            date.addDay(1);
        }
        weekMdl.setSch010SchList(colList);

        return weekMdl;
    }

    /**
     * <br>[機  能] 指定したグループのグループスケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dspDate 表示日付
     * @param grpSid グループSID
     * @param frDate 開始日付
     * @param toDate 終了日付
     * @param holMap 休日Mapping
     * @param sessionUsrSid セッションユーザSID
     * @param con DBコネクション
     * @return グループスケジュール情報
     * @throws SQLException
     */
    private Sch010WeekOfModel __createGrpScheduleModel(
                                            UDate dspDate,
                                            int grpSid, UDate frDate, UDate toDate,
                                            HashMap<String, CmnHolidayModel> holMap,
                                            int sessionUsrSid,
                                            Connection con)
    throws SQLException {

        Sch010WeekOfModel weekMdl = new Sch010WeekOfModel();
        ArrayList<Sch010DayOfModel> colList = new ArrayList<Sch010DayOfModel>();
        Sch010UsrModel usMdl = new Sch010UsrModel();

        GroupDao grpDao = new GroupDao(con);
        CmnGroupmModel grpMdl = grpDao.getGroup(grpSid);

        weekMdl = new Sch010WeekOfModel();
        colList = new ArrayList<Sch010DayOfModel>();
        usMdl = new Sch010UsrModel();

        if (grpMdl != null) {
            usMdl.setUsrName(grpMdl.getGrpName());
        }
        usMdl.setUsrSid(grpSid);
        usMdl.setUsrKbn(GSConstSchedule.USER_KBN_GROUP);
        usMdl.setZaisekiKbn(GSConst.UIOSTS_IN);

        //スケジュール登録可能グループかを判定
        SchDao scheduleDao = new SchDao(con);
        usMdl.setSchRegistFlg(scheduleDao.canRegistGroupSchedule(grpSid, sessionUsrSid));

        weekMdl.setSch010UsrMdl(usMdl);

        //スケジュール情報を取得(グループ)
        ScheduleSearchDao schDao = new ScheduleSearchDao(con);
        ArrayList<SchDataModel> schDataList
            = schDao.select(
                grpSid,
                GSConstSchedule.USER_KBN_GROUP,
                -1,
                frDate,
                toDate,
                GSConstSchedule.DSP_MOD_WEEK,
                sessionUsrSid);


        //表示グループに所属しているか判定
        GroupBiz gpBiz = new GroupBiz();
        boolean belongGrpHnt = gpBiz.isBelongGroup(sessionUsrSid, grpSid, con);

        UDate date = dspDate.cloneUDate();
        ArrayList<SimpleScheduleModel> dayMdlList = null;
        Sch010DayOfModel dayMdl = null;
        SimpleScheduleModel dspSchMdl = null;
        CmnHolidayModel holMdl = null;
        LinkedHashMap<String, SimpleScheduleModel> periodMdlMap
            = new LinkedHashMap<String, SimpleScheduleModel>();
        for (int i = 0; i < GSConstSchedule.WEEK_DAY_COUNT; i++) {
            //１日分のスケジュール
            dayMdlList = new ArrayList<SimpleScheduleModel>();
            dayMdl = new Sch010DayOfModel();
            if (holMap != null) {
                holMdl = holMap.get(date.getDateString());
                if (holMdl != null) {
                  dayMdl.setHolidayName(holMdl.getHolName());
                } else {
                  dayMdl.setHolidayName(null);
                }
            }
            dayMdl.setSchDate(date.getDateString());
            dayMdl.setUsrSid(grpSid);
            dayMdl.setUsrKbn(GSConstSchedule.USER_KBN_GROUP);
            dayMdl.setWeekKbn(date.getWeek());

            __checkToday(dayMdl, date);

            SchDataModel schMdl = null;
            for (int j = 0; j < schDataList.size(); j++) {
                //スケジュール１個
                schMdl = schDataList.get(j);
                //本日のスケジュールか判定
                if (isTodaySchedule(schMdl, date)) {
                    dspSchMdl = new SimpleScheduleModel();
                    dspSchMdl = __getSchDspGrpData(schMdl, date, belongGrpHnt, sessionUsrSid);
                    //鍵アイコン表示判定
                    if (dspSchMdl != null
                            && dspSchMdl.getPublic() == GSConstSchedule.DSP_NOT_PUBLIC) {
                        dspSchMdl.setPublicIconFlg(true);
                    }
                    if (dspSchMdl != null) {
                        dspSchMdl.setUserKbn(String.valueOf(GSConstSchedule.USER_KBN_GROUP));
                        if (schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
                            dayMdlList.add(dspSchMdl);
                        } else {
//                            dayMdlList.add(dspSchMdl);
                            dspSchMdl.setFromDate(schMdl.getScdFrDate());
                            dspSchMdl.setToDate(schMdl.getScdToDate());
                            periodMdlMap.put(String.valueOf(dspSchMdl.getSchSid()), dspSchMdl);
                        }
                    }
                }
            }
            dayMdl.setSchDataList(dayMdlList);
            colList.add(dayMdl);
            //日付を進める
            date.addDay(1);
        }
        weekMdl.setSch010SchList(colList);

        //期間スケジュールを取得
        weekMdl = __getPeriodSch(weekMdl, periodMdlMap, dspDate);

        return weekMdl;
    }

    /**
     * <br>スケジュール情報が指定日付のスケジュールか判定します
     * @param schMdl スケジュール情報
     * @param date 指定日付
     * @return true:指定日のスケジュール false:指定日以外のスケジュール
     */
    public static boolean isTodaySchedule(SchDataModel schMdl, UDate date) {
        boolean ret = false;
        UDate frDate = schMdl.getScdFrDate();
        UDate toDate = schMdl.getScdToDate();
        if (frDate.compareDateYMD(date) != UDate.SMALL
         && toDate.compareDateYMD(date) != UDate.LARGE) {

            //Toが0:00の場合は除外する(日またぎのスケジュールとしない)
            if (schMdl.getScdDaily() == 0
                    && toDate.getYear() == date.getYear()
                    && toDate.getMonth() == date.getMonth()
                    && toDate.getIntDay() == date.getIntDay()
                    && toDate.getIntHour() == GSConstSchedule.DAY_START_HOUR
                    && toDate.getIntMinute() == GSConstSchedule.DAY_START_MINUTES) {
            } else {
                ret = true;
            }
        }
        return ret;
    }

    /**
     * <br>スケジュール時間表示を画面表示用に編集します
     * @param schMdl スケジュール情報
     * @param date 指定日付
     * @return String 画面表示用時間
     */
    public static String getTimeString(SchDataModel schMdl, UDate date) {

        StringBuilder buf = new StringBuilder();
        UDate frDate = schMdl.getScdFrDate();
        UDate toDate = schMdl.getScdToDate();
        if (schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
            boolean flg = false;
            //スケジュール開始日が今日か判定
            if (date.compareDateYMD(frDate) == UDate.EQUAL) {
                buf.append(frDate.getStrHour());
                buf.append(":");
                buf.append(frDate.getStrMinute());
                buf.append("-");
                flg = true;
            }
            //スケジュール終了日が今日か判定
            if (date.compareDateYMD(toDate) == UDate.EQUAL) {
                if (flg == false) {
                    buf.append("-");
                }
                buf.append(toDate.getStrHour());
                buf.append(":");
                buf.append(toDate.getStrMinute());

            } else {
                //終了が翌日の0:00の場合、本日の24:00と表示する
                UDate nextDate = date.cloneUDate();
                nextDate.addDay(1);
                if (toDate.getYear() == nextDate.getYear()
                    && toDate.getMonth() == nextDate.getMonth()
                    && toDate.getIntDay() == nextDate.getIntDay()
                    && toDate.getIntHour() == GSConstSchedule.DAY_START_HOUR
                    && toDate.getIntMinute() == GSConstSchedule.DAY_START_MINUTES) {
                    if (flg == false) {
                        buf.append("-");
                    }
                    buf.append("24");
                    buf.append(":");
                    buf.append("00");
                }

            }
        }

        return buf.toString();
    }

    /**
     * <br>スケジュール個人設定を取得します
     * <br>データが存在しない場合は初期値で作成し取得します
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return SchPriConfModel 個人設定
     * @throws SQLException SQL実行時例外
     */
    public SchPriConfModel getPrivateConf(int usrSid, Connection con) throws SQLException {

        SchPriConfModel confBean = null;

        SchCommonBiz cbiz = new SchCommonBiz(reqMdl__);
        confBean = cbiz.getSchPriConfModel(con, usrSid);

        return confBean;
    }

    /**
     * <br>スケジュールに対して編集権限があるか判定する
     * @param scdSid スケジュールSID
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param sameSchFlg 同時登録スケジュール判定フラグ
     * @return boolean true:権限あり　false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditOk(
            int scdSid,
            RequestModel reqMdl,
            Connection con,
            boolean sameSchFlg) throws SQLException {
        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        CommonBiz commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstSchedule.PLUGIN_ID_SCHEDULE);

        SchDataDao scdDao = new SchDataDao(con);
        SchDataModel scdMdl = scdDao.getEditCheckData(scdSid);
        if (scdMdl == null) {
            return false;
        }
        SchDao schDao = new SchDao(con);
        if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
            //グループスケジュール登録権限チェック
            if (!schDao.canRegistGroupSchedule(scdMdl.getScdUsrSid(), sessionUsrSid)) {
                return false;
            }
        } else {
            //ユーザスケジュール登録権限チェック
            if (!schDao.canRegistUserSchedule(scdMdl.getScdUsrSid(), sessionUsrSid)) {
                return false;
            }
        }
        //管理者権限の有無
        if (isAdmin) {
            return true;
        }

        return isEditOk(scdMdl, sessionUsrSid, false, con, sameSchFlg);
    }
    /**
     * <br>スケジュールに対して編集権限があるか判定する
     * @param scdMdl モデル
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @param isAdmin 管理者権限
     * @param sameSchFlg 同時登録スケジュール編集フラグ
     * @return boolean true:権限あり　false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditOk(
            SchDataModel scdMdl,
            int sessionUsrSid,
            Boolean isAdmin,
            Connection con,
            boolean sameSchFlg) throws SQLException {

        SchCommonBiz schCmnBiz = new SchCommonBiz(reqMdl__);
        return schCmnBiz.isEditOk(scdMdl, sessionUsrSid, isAdmin, con, sameSchFlg);
    }

    /**
     *
     * <br>[機  能]公開範囲をチェックして閲覧できるか判定
     * <br>[解  説]
     * <br>[備  考]
     * @param scdMdl スケジュールモデル
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @return 閲覧権限(false:閲覧不可 true:閲覧可)
     * @throws SQLException SQL実行時例外
     */
    public boolean checkViewOk(SchDataModel scdMdl, int sessionUsrSid,
            Connection con) throws SQLException {

        SchCommonBiz schCmnBiz = new SchCommonBiz(reqMdl__);
        return schCmnBiz.checkViewOk(scdMdl, sessionUsrSid, con);
    }

    /**
     * <br>同時登録スケジュールも含め編集権限があるか判定する
     * @param scdMdl モデル
     * @param adminConf 管理者設定情報
     * @param reqMdl リクエスト情報
     * @param sessionUsrSid セッションユーザSID
     * @param isAdmin 管理者権限
     * @param con コネクション
     * @return boolean true:権限あり　false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isAllEditOk(
            SchDataModel scdMdl,
            SchAdmConfModel adminConf,
            RequestModel reqMdl,
            int sessionUsrSid,
            boolean isAdmin,
            Connection con) throws SQLException {

        SchCommonBiz schCmnBiz = new SchCommonBiz(reqMdl);
        return schCmnBiz.isAllEditOk(scdMdl, adminConf, sessionUsrSid, isAdmin, con);

    }
    /**
     * <br>同時登録スケジュールも含め編集権限があるか判定する
     * @param scdSid スケジュールSID
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return boolean true:権限あり　false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isAllEditOk(
            int scdSid,
            RequestModel reqMdl,
            Connection con) throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        CommonBiz commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstSchedule.PLUGIN_ID_SCHEDULE);
        //管理者権限の有無
        if (isAdmin) {
            return true;
        }
        //管理者設定を取得
        SchCommonBiz adminbiz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel adminConf = adminbiz.getAdmConfModel(con);

        Sch040Biz biz = new Sch040Biz(con, reqMdl);
        ScheduleSearchModel scdMdl = biz.getSchData(scdSid, adminConf, con);
        if (scdMdl == null) {
            return false;
        }
        return isAllEditOk(scdMdl, adminConf, reqMdl, sessionUsrSid, false, con);
    }

    /**
     * スケジュールリストの中に自分のスケジュールが含まれるか判定する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param schDataList スケジュールリスト
     * @param sessionUsrSid ユーザSID
     * @return boolean true:含まれる　false:含まれない
     */
    public boolean isIncludedMySchedule(
            ArrayList<ScheduleSearchModel> schDataList, int sessionUsrSid) {
        for (ScheduleSearchModel mdl : schDataList) {
            if (mdl.getScdUsrSid() == sessionUsrSid
                    && mdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能] 表示用スケジュールデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param schMdl スケジュールデータ
     * @param date 日時
     * @param grpBelongHnt 所属グループ判定 true:所属している false:所属していない
     * @param myGroupFlg マイグループ判定 true:マイグループ false:マイグループではない
     * @param sessionUsrSid セッションユーザSID
     * @param pubScdSidList セッションユーザが公開対象として指定されているスケジュールの一覧
     * @return SimpleScheduleModel 表示用モデル
     * @throws SQLException SQL実行時例外
     */
    private SimpleScheduleModel __getSchDspMdl(
            SchDataModel schMdl,
            UDate date,
            boolean grpBelongHnt,
            boolean myGroupFlg,
            int sessionUsrSid,
            List<Integer> pubScdSidList) throws SQLException {

        SimpleScheduleModel dspSchMdl = new SimpleScheduleModel();

        if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
            //ユーザスケジュールの場合は表示スケジュールユーザと同じグループに所属しているか判定
            grpBelongHnt = __getSchUsrBelongHnt(schMdl.getScdUserBlongGpList());
        }

        //予定あり
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textYoteiari = gsMsg.getMessage("schedule.src.9");

        boolean publicUserMatch = false;
        if (schMdl.getScdAuid() == sessionUsrSid
                || schMdl.getScdEuid() == sessionUsrSid) {
            //登録者の場合は表示する
            dspSchMdl.setTitle(schMdl.getScdTitle());
            dspSchMdl.setPublic(GSConstSchedule.DSP_PUBLIC);

        } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_YOTEIARI) {
            //予定あり
            dspSchMdl.setTitle(textYoteiari);
            dspSchMdl.setPublic(GSConstSchedule.DSP_YOTEIARI);

        } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC) {
            //非公開
            dspSchMdl.setPublic(schMdl.getScdPublic());
            return dspSchMdl;

        } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP
                 && grpBelongHnt) {
            //所属グループのみ公開
            dspSchMdl.setTitle(schMdl.getScdTitle());
            dspSchMdl.setGrpEdKbn(GSConstSchedule.EDIT_CONF_GROUP);
            dspSchMdl.setPublic(schMdl.getScdPublic());

        } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP
                && !(grpBelongHnt)) {

            //閲覧可能な所属グループではないユーザには「予定あり」
            dspSchMdl.setTitle(textYoteiari);
            dspSchMdl.setPublic(GSConstSchedule.DSP_YOTEIARI);

        } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
            //指定ユーザ・グループのみ公開
            if (pubScdSidList.contains(schMdl.getScdSid())) {
                dspSchMdl.setTitle(schMdl.getScdTitle());
                dspSchMdl.setPublic(GSConstSchedule.DSP_PUBLIC);
                publicUserMatch = true;
            } else {
                //公開対象グループ・ユーザに該当しない場合、「予定あり」
                dspSchMdl.setTitle(textYoteiari);
                dspSchMdl.setPublic(GSConstSchedule.DSP_YOTEIARI);
            }
        } else {
            //公開
            dspSchMdl.setTitle(schMdl.getScdTitle());
            dspSchMdl.setPublic(schMdl.getScdPublic());

        }

        dspSchMdl.setSchSid(schMdl.getScdSid());
        dspSchMdl.setTime(getTimeString(schMdl, date));
        dspSchMdl.setTimeKbn(schMdl.getScdDaily());
        dspSchMdl.setBgColor(schMdl.getScdBgcolor());
        dspSchMdl.setValueStr(schMdl.getScdValue());

        //ユーザ区分
        if (schMdl.getScdAppendUrl() == null) {
            //スケジュールのデータ
            dspSchMdl.setSchSid(schMdl.getScdSid());
            dspSchMdl.setUserKbn(String.valueOf(GSConstSchedule.USER_KBN_USER));
            SchCommonBiz schBiz = new SchCommonBiz();
            dspSchMdl.setPublicIconFlg(
                    schBiz.getPublicIconFlg(schMdl, sessionUsrSid, grpBelongHnt, publicUserMatch));
        } else {
            //スケジュール以外のプラグインのデータ
            dspSchMdl.setSchAppendUrl(schMdl.getScdAppendUrl());
            dspSchMdl.setUserKbn(schMdl.getScdAppendId());
        }

        return dspSchMdl;
    }

    /**
     * <br>[機  能] セッションユーザがスケジュールユーザと同じグループに所属しているか判定
     * <br>[解  説]
     * <br>[備  考]
     * @param belongSids 所属グループSID
     * @return 所属フラグ
     */
    private boolean __getSchUsrBelongHnt(ArrayList<Integer> belongSids) {
        boolean belongFlg = false;
        if (belongSids != null && !belongSids.isEmpty()) {
            for (int gpSid : belongSids) {
                if (belongGpSidList__ != null) {
                    if (belongGpSidList__.indexOf(gpSid) > -1) {
                        belongFlg = true;
                    }
                }
            }
        }
        return belongFlg;
    }

    /**
     * <br>[機  能] 表示用スケジュールデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param schMdl スケジュールデータ
     * @param date 日時
     * @param belongGrpHnt 所属グループ判定 true:所属している false:所属していない
     * @param usrSid ユーザSID
     * @return SimpleScheduleModel 表示用モデル
     * @throws SQLException SQL実行時例外
     */
    private SimpleScheduleModel __getSchDspGrpData(
            SchDataModel schMdl,
            UDate date,
            boolean belongGrpHnt,
            int usrSid) throws SQLException {

        SimpleScheduleModel dspSchMdl = new SimpleScheduleModel();

        //グループスケジュール
        if (usrSid == schMdl.getScdAuid()) {
            dspSchMdl.setKjnEdKbn(GSConstSchedule.EDIT_CONF_OWN);
        }

        if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC
                && !(belongGrpHnt)) {

            //グループのスケジュール
            if (schMdl.getScdAuid() == usrSid
                || schMdl.getScdEuid() == usrSid) {
            //登録者、編集者の場合は表示する
                dspSchMdl.setPublic(GSConstSchedule.DSP_PUBLIC);
                dspSchMdl.setTitle(schMdl.getScdTitle());
            } else {
                //非公開
                dspSchMdl.setPublic(schMdl.getScdPublic());
                return null;
            }

        } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC
                && belongGrpHnt) {

            //公開
            dspSchMdl.setTitle(schMdl.getScdTitle());
            dspSchMdl.setGrpEdKbn(GSConstSchedule.EDIT_CONF_GROUP);

        } else {
            //公開
            dspSchMdl.setTitle(schMdl.getScdTitle());
            dspSchMdl.setGrpEdKbn(GSConstSchedule.EDIT_CONF_GROUP);
        }

        //グループのスケジュール
        SchCommonBiz schBiz = new SchCommonBiz();
        dspSchMdl.setPublic(schMdl.getScdPublic());
        dspSchMdl.setUserSid(String.valueOf(schMdl.getScdUsrSid()));
        dspSchMdl.setSchSid(schMdl.getScdSid());
        dspSchMdl.setTime(getTimeString(schMdl, date));
        dspSchMdl.setTimeKbn(schMdl.getScdDaily());
        dspSchMdl.setBgColor(schMdl.getScdBgcolor());
        dspSchMdl.setValueStr(schMdl.getScdValue());
        dspSchMdl.setPublicIconFlg(schBiz.getPublicIconFlg(schMdl, usrSid, belongGrpHnt, false));
        return dspSchMdl;
    }

    /**
     * <br>[機  能] メイン画面表示用スケジュールデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param schMdl スケジュールデータ
     * @param usrSid ユーザSID
     * @return SimpleScheduleModel 表示用モデル
     */
    private SimpleScheduleModel __getSchMainData(
            SchDataModel schMdl,
            int usrSid) {

        SimpleScheduleModel dspSchMdl = new SimpleScheduleModel();

        //グループスケジュール
        if (usrSid == schMdl.getScdAuid()) {
            dspSchMdl.setKjnEdKbn(GSConstSchedule.EDIT_CONF_OWN);
        }
        dspSchMdl.setTitle(schMdl.getScdTitle());
        dspSchMdl.setGrpEdKbn(GSConstSchedule.EDIT_CONF_GROUP);

        return dspSchMdl;
    }

    /**
     * <br>[機  能] 期間スケジュールを作成
     * <br>[解  説]
     * <br>[備  考]
     * @param weekMdl スケジュールデータ
     * @param periodMdlMap 期間データ
     * @param dspDate 画面表示日付
     * @return Sch010WeekOfModel 表示用モデル
     */
    private Sch010WeekOfModel __getPeriodSch(
            Sch010WeekOfModel weekMdl,
            LinkedHashMap<String, SimpleScheduleModel> periodMdlMap,
            UDate dspDate) {
        /** 期間スケジュールを作成 **/
        //期間モデル
        Sch010PeriodModel prdMdl = null;
        //期間スケジュール格納リスト
        ArrayList<SimpleScheduleModel> periodSchList = new ArrayList<SimpleScheduleModel>();
        //期間指定スケジュールを取得
        if (!periodMdlMap.isEmpty()) {
            Set<Entry<String, SimpleScheduleModel>> set = periodMdlMap.entrySet();
            Iterator<Entry<String, SimpleScheduleModel>> it = set.iterator();
            while (it.hasNext()) {
                Entry<String, SimpleScheduleModel> entry
                    = (Entry<String, SimpleScheduleModel>) it.next();
                SimpleScheduleModel schPrdMdl = (SimpleScheduleModel) entry.getValue();
                //開始日、終了日、期間を設定
                prdMdl = new Sch010PeriodModel();
                UDate ddate = dspDate.cloneUDate();
                prdMdl = __getPeriodMdl(schPrdMdl, ddate);
                schPrdMdl.setPeriodMdl(prdMdl);
                periodSchList.add(schPrdMdl);
            }
        }

        //Listを開始日付でソート
        if (!periodSchList.isEmpty()) {
            Collections.sort(periodSchList, new Comparator<SimpleScheduleModel>() {
                public int compare(SimpleScheduleModel t1, SimpleScheduleModel t2) {
                  return t1.getFromDate().compareDateYMD(t2.getFromDate()) * -1;
                }
             });
        }

        //行データ作成済みSID保持リスト
        ArrayList<String> set = new ArrayList<String>();
        //データ追加用リスト
        ArrayList<SimpleScheduleModel> rowAddList = null;
        //行スケジュール格納リスト
        ArrayList<ArrayList<SimpleScheduleModel>> rowSchList
                                         = new ArrayList<ArrayList<SimpleScheduleModel>>();

        //行の作成
        int rowCnt = 1;
        while (set.size() != periodSchList.size()) {
            rowAddList = new ArrayList<SimpleScheduleModel>();
            for (SimpleScheduleModel ssm : periodSchList) {
                if (!set.isEmpty()) {
                    if (ssm.getSchAppendUrl() == null) {
                        //スケジュールのデータ
                        if (set.indexOf(String.valueOf(ssm.getSchSid())) == -1
                                                                     && rowAddList.isEmpty()) {
                            //データをまだ作成してないand行データなし
                            rowAddList.add(ssm);
                            set.add(String.valueOf(ssm.getSchSid()));
                        }
                        if (set.indexOf(String.valueOf(ssm.getSchSid())) == -1) {
                            //データをまだ作成してない
                            if (ssm.getFromDate().compareDateYMD(
                                    rowAddList.get(rowAddList.size() - 1).getToDate()) != 1
                                && ssm.getFromDate().compareDateYMD(
                                        rowAddList.get(rowAddList.size() - 1).getToDate()) != 0) {
                                //日付が現在の行のデータとかぶらない場合
                                rowAddList.add(ssm);
                                set.add(String.valueOf(ssm.getSchSid()));
                            }
                        }
                    } else {
                        //他のプラグインのデータ
                        if (set.indexOf(ssm.getSchAppendUrl()) == -1 && rowAddList.isEmpty()) {
                            //データをまだ作成してないand行データなし
                            rowAddList.add(ssm);
                            set.add(ssm.getSchAppendUrl());
                        }
                        if (set.indexOf(ssm.getSchAppendUrl()) == -1) {
                            //データをまだ作成してない
                            if (ssm.getFromDate().compareDateYMD(
                                    rowAddList.get(rowAddList.size() - 1).getToDate()) != 1
                                && ssm.getFromDate().compareDateYMD(
                                        rowAddList.get(rowAddList.size() - 1).getToDate()) != 0) {
                                //日付が現在の行のデータとかぶらない場合
                                rowAddList.add(ssm);
                                set.add(ssm.getSchAppendUrl());
                            }
                        }
                    }
                } else {
                    rowAddList.add(ssm);
                    if (ssm.getSchAppendUrl() == null) {
                        //スケジュールのデータ
                        set.add(String.valueOf(ssm.getSchSid()));
                    } else {
                        //他プラグインのデータ
                        set.add(ssm.getSchAppendUrl());
                    }
                }
            }

            //行データの中の登録されている日を取得
            HashMap<Integer, SimpleScheduleModel> weekCnt
                                              = new HashMap<Integer, SimpleScheduleModel>();
            for (SimpleScheduleModel ssm : rowAddList) {
                int col = ssm.getPeriodMdl().getSchPeriodStart();
                int colCnt = ssm.getPeriodMdl().getSchPeriodCnt();
                while (0 < colCnt) {
                    int val = col + colCnt - 1;
                    weekCnt.put(val, ssm);
                    colCnt = colCnt - 1;
                }
            }
            //行データの中で開いている日がある場合は空のモデルを挿入
            ArrayList<SimpleScheduleModel> rowResultList = new ArrayList<SimpleScheduleModel>();
            ArrayList<String> rslSet = new ArrayList<String>();
            for (int i = 1; i < 8; i++) {
                if (!weekCnt.isEmpty()) {
                    SimpleScheduleModel sm = new SimpleScheduleModel();
                    if (weekCnt.get(i) == null) {
                        rowResultList.add(sm);
                    } else {
                        sm = weekCnt.get(i);
                        if (!rslSet.isEmpty()) {
                            if (sm.getSchAppendUrl() == null) {
                                //スケジュールのデータ
                                if (rslSet.indexOf(String.valueOf(sm.getSchSid())) == -1) {
                                    rowResultList.add(sm);
                                    rslSet.add(String.valueOf(sm.getSchSid()));
                                }
                            } else {
                                //他のプラグインのデータ
                                if (rslSet.indexOf(sm.getSchAppendUrl()) == -1) {
                                    rowResultList.add(sm);
                                    rslSet.add(sm.getSchAppendUrl());
                                }
                            }
                        } else {
                            rowResultList.add(weekCnt.get(i));
                            if (sm.getSchAppendUrl() == null) {
                                //スケジュールのデータ
                                rslSet.add(String.valueOf(sm.getSchSid()));
                            } else {
                                //他のプラグインのデータ
                                rslSet.add(sm.getSchAppendUrl());
                            }
                        }
                    }
                }
            }
            rowSchList.add(rowResultList);
            rowCnt = rowCnt + 1;
        }
        if (!rowSchList.isEmpty()) {
            weekMdl.setSch010NoTimeSchList(rowSchList);
            weekMdl.setSch010PeriodRow(rowCnt);
        }
        return weekMdl;
    }

    /**
     * <br>[機  能] 期間スケジュール位置取得
     * <br>[解  説]
     * <br>[備  考]
     * @param schMdl スケジュールデータ
     * @param date 画面表示日付
     * @return Sch010PeriodModel 表示用モデル
     */
    private Sch010PeriodModel __getPeriodMdl(
            SimpleScheduleModel schMdl,
            UDate date) {

        date.setHour(0);
        date.setMinute(0);
        date.setSecond(0);

        UDate startDate = null;
        UDate endDate = null;

        Sch010PeriodModel prdMdl = new Sch010PeriodModel();
        //開始日時が画面表示日より前の場合は、画面表示日をセット
        if (schMdl.getFromDate().compareDateYMD(date) == 1) {
            startDate = date.cloneUDate();
        } else {
            startDate = schMdl.getFromDate();
        }

        //終了日時が画面表示終了日より後の場合は、画面表示終了日をセット
        UDate toDate = date.cloneUDate();
        toDate.addDay(6);
        toDate.setHour(0);
        toDate.setMinute(0);
        toDate.setSecond(0);
        if (schMdl.getToDate().compareDateYMD(toDate) == -1) {
            endDate = toDate.cloneUDate();
        } else {
            endDate = schMdl.getToDate();
        }

        startDate.setDate(startDate.getStrYear() + startDate.getStrMonth() + startDate.getStrDay());
        startDate.setHour(0);
        startDate.setMinute(0);
        startDate.setSecond(0);
        endDate.setDate(endDate.getStrYear() + endDate.getStrMonth() + endDate.getStrDay());
        endDate.setHour(0);
        endDate.setMinute(0);
        endDate.setSecond(0);

        //日数を計算
        int dayCnt = UDateUtil.diffDay(startDate, endDate) + 1;
        prdMdl.setSchPeriodCnt(dayCnt);

        //開始位置を取得
        int start = 1;
        for (int i = 1; i < 8; i++) {
            if (startDate.compareDateYMD(date) == 0) {
                start = i;
            }
            date.addDay(1);
        }
        prdMdl.setSchPeriodStart(start);

        return prdMdl;
    }

    /**
     * <br>[機  能] 他プラグインデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dspDate 画面日付
     * @param grpSid グループSID
     * @param usrSid ユーザSID
     * @param schDataList スケジュールデータ
     * @param con コネクション
     * @param plgKbn プラグイン区分 0:スケジュール  1:メイン
     */
    private void __getAppendPlgData(
            UDate dspDate,
            int grpSid,
            int usrSid,
            ArrayList <SchDataModel> schDataList,
            Connection con,
            int plgKbn) {
        List<Integer> usrSids = new ArrayList<>();
        usrSids.add(usrSid);
        __getAppendPlgData(dspDate, grpSid, usrSids, schDataList, con, plgKbn);

    }
    /**
     * <br>[機  能] 他プラグインデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dspDate 画面日付
     * @param grpSid グループSID
     * @param usrSidList ユーザSIDリスト
     * @param schDataList スケジュールデータ
     * @param con コネクション
     * @param plgKbn プラグイン区分 0:スケジュール  1:メイン
     */
    private void __getAppendPlgData(
            UDate dspDate,
            int grpSid,
            List<Integer> usrSidList,
            ArrayList <SchDataModel> schDataList,
            Connection con,
            int plgKbn) {

        //他プラグイン情報を取得
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        ArrayList<SchDataModel> apdSchList = new ArrayList<SchDataModel>();
        if (pconfig__ != null) {
            UDate prmFrDate = dspDate.cloneUDate();
            UDate prmToDate = prmFrDate.cloneUDate();
            prmToDate.addDay(6);
            SchAppendDataParam paramMdl = new SchAppendDataParam();
            paramMdl.setUsrSidList(usrSidList);
            paramMdl.setFrDate(prmFrDate);
            paramMdl.setToDate(prmToDate);
            paramMdl.setSrcId(GSConstSchedule.DSP_ID_SCH010);
            paramMdl.setGrpSid(String.valueOf(grpSid));
            paramMdl.setDspDate(dspDate.getStrYear() + dspDate.getStrMonth() + dspDate.getStrDay());
            if (plgKbn == 0) {
                paramMdl.setReturnUrl(createUrl(
                        dspDate.getStrYear() + dspDate.getStrMonth() + dspDate.getStrDay()));
            } else {
                paramMdl.setReturnUrl(createMainUrl());
            }

            try {
                apdSchList = biz.getAppendSchData(reqMdl__, con, pconfig__, paramMdl);
            } catch (Exception e) {
                log__.error("他プラグインのスケジュールデータ取得に失敗");
            }
        }
        if (!apdSchList.isEmpty()) {
            schDataList.addAll(apdSchList);
        }
    }

    /**
     * <br>[機  能]自画面のURLを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dspDate 画面日付
     * @return スレッドURL
     */
    public String createUrl(String dspDate) {

        UrlBuilder ub = new UrlBuilder(reqMdl__, GSConst.PLUGINID_SCH, "sch010");
        ub.addUrlParam("sch010DspDate", dspDate);
        ub.addUrlParam("sch010DspGpSid", dspGpSid__);
        ub.addUrlParam("changeDateFlg", 1);

        return ub.getUrl();
    }

    /**
     * <br>[機  能]メイン画面のURLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return スレッドURL
     */
    public String createMainUrl() {

        UrlBuilder ub = new UrlBuilder(reqMdl__, GSConst.PLUGINID_MAIN, "man001");

        return ub.getUrl();
    }


    /**
     * <br>[機  能] 週間スケジュールをPDF出力します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Sch010ParamModel
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param outTempDir テンポラリディレクトパス
     * @param tmpFileName テンポラリファイル名
     * @return pdfModel SmlPdfModel
     * @throws IOException IO実行時例外
     */
    public SchSyuPdfModel createSchSyuPdf(
            Sch010ParamModel paramMdl,
            Connection con,
            String appRootPath,
            String outTempDir,
            String tmpFileName)
        throws IOException {
        OutputStream oStream = null;

        //ヘッダー年月
        String headDate = paramMdl.getSch010StrDspDate();
        //表示グループ
        String dispGroup = new String();
        for (int i = 0; i < paramMdl.getSch010GpLabelList().size(); i++) {
            if (paramMdl.getSch010GpLabelList().get(i).getValue().equals(
                    paramMdl.getSch010DspGpSid())) {
                dispGroup = paramMdl.getSch010GpLabelList().get(i).getLabel();
            }
        }
        /** 週間カレンダー */
        ArrayList<SimpleCalenderModel> calendarList = paramMdl.getSch010CalendarList();
        ArrayList<Sch010WeekOfModel> topList = paramMdl.getSch010TopList();
        ArrayList<Sch010WeekOfModel> botList = paramMdl.getSch010BottomList();


        //PDFモデル
        SchSyuPdfModel pdfModel = new SchSyuPdfModel();
        //ヘッダー年月
        pdfModel.setHeadDate(headDate);
        //表示グループ
        pdfModel.setDispGroup(dispGroup);
        /** 週間カレンダー */
        pdfModel.setCalendarList(calendarList);
        pdfModel.setTopList(topList);
        pdfModel.setBottomList(botList);

        GsMessage msg = new GsMessage(reqMdl__);
        String outBookName = pdfModel.getHeadDate()
                + msg.getMessage("schedule.sch010.1");
        String encOutBookName = fileNameCheck(outBookName) + ".pdf";
        pdfModel.setFileName(encOutBookName);

        try {
            IOTools.isDirCheck(outTempDir, true);
            oStream = new FileOutputStream(outTempDir + tmpFileName);
            SchSyuPdfUtil pdfUtil = new SchSyuPdfUtil(reqMdl__);
            pdfUtil.createSchSyukanPdf(pdfModel, appRootPath, oStream);
        } catch (Exception e) {
            log__.error("スケジュールPDF出力に失敗しました。", e);
        } finally {
            if (oStream != null) {
                oStream.flush();
                oStream.close();
            }
        }
        log__.debug("スケジュールPDF出力を終了します。");

        return pdfModel;
    }

    /**
     * <br>[機  能] ファイル名として使用できるか文字列チェックする。
     * <br>[解  説] /\?*:|"<>. を除去
     *                    255文字超える場合は以降を除去
     * <br>[備  考]
     * @param fileName テンポラリディレクトリ
     * @return 出力したファイルのパス
     */
    private String fileNameCheck(String fileName) {
            String escName = fileName;

            escName = StringUtilHtml.replaceString(escName, "/", "");
            escName = StringUtilHtml.replaceString(escName, "\\", ""); //\
            escName = StringUtilHtml.replaceString(escName, "?", "");
            escName = StringUtilHtml.replaceString(escName, "*", "");
            escName = StringUtilHtml.replaceString(escName, ":", "");
            escName = StringUtilHtml.replaceString(escName, "|", "");
            escName = StringUtilHtml.replaceString(escName, "\"", ""); //"
            escName = StringUtilHtml.replaceString(escName, "<", "");
            escName = StringUtilHtml.replaceString(escName, ">", "");
            escName = StringUtilHtml.replaceString(escName, ".", "");
            escName = StringUtil.trimRengeString(escName, 251); //ファイル名＋拡張子=255文字以内

        return escName;
    }

    /**
     * 当日か否かを判別します。
     * @param dayMdl dayMdl
     * @param date date
     */
    private void __checkToday(Sch010DayOfModel dayMdl, UDate date) {
        UDate today = new UDate();
        if (date.getDateString().equals(today.getDateString())) {
            dayMdl.setTodayKbn(GSConstSchedule.TODAY_TRUE);
        } else {
            dayMdl.setTodayKbn(GSConstSchedule.TODAY_FALSE);
        }
    }

    /**
     * <br>[機  能] スケジュール一括登録で使用する各種情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Sch010ParamModel
     * @return 選択中リスト
     */
    public List<List<SchHidModel>> setIkkatsuData(Connection con, Sch010ParamModel paramMdl)
    throws SQLException {

        List<List<SchHidModel>> hidDataList = new ArrayList<List<SchHidModel>>();
        //重複を除外する。
        List<String> saveIdList = new ArrayList<String>();
        for (String id : paramMdl.getSchIkkatuTorokuKey()) {
            if (!saveIdList.contains(id)) {
                saveIdList.add(id);
            }
        }

        if (saveIdList != null && saveIdList.size() > 0) {
            paramMdl.setSchIkkatuTorokuSaveKey(
                    saveIdList.toArray(new String[saveIdList.size()]));

            ArrayList<Integer> grpSids = new ArrayList<Integer>();
            ArrayList<Integer> usrSids = new ArrayList<Integer>();
            for (String saveId : saveIdList) {
                String userId = saveId.substring(saveId.indexOf("-") + 1);
                if (userId.startsWith("G")) {
                    //グループ
                    grpSids.add(Integer.valueOf(userId.substring(1)));
                } else {
                    //ユーザ
                    usrSids.add(Integer.valueOf(userId));
                }
            }

            //グループ情報取得
            Map<String, GroupModel> groupMap = new HashMap<String, GroupModel>();
            UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
            ArrayList<GroupModel> grpList = gdao.selectGroupNmListOrderbyConf(grpSids);
            for (GroupModel grpMdl : grpList) {
                groupMap.put("G" + grpMdl.getGroupSid(), grpMdl);
            }

            //ユーザ情報取得
            Map<String, UserSearchModel> userMap = new HashMap<String, UserSearchModel>();
            UserSearchDao uDao = new UserSearchDao(con);
            ArrayList<UserSearchModel> userList = uDao.getUsersInfoJtkb(
                    usrSids,
                    GSConstUser.USER_SORT_NAME,
                    GSConst.ORDER_KEY_ASC,
                    GSConstUser.USER_SORT_NAME,
                    GSConst.ORDER_KEY_ASC);
            for (UserSearchModel usrMdl : userList) {
                userMap.put(String.valueOf(usrMdl.getUsrSid()), usrMdl);
            }

            List<String> dateList = new ArrayList<String>();
            Map<String, List<SchHidModel>> hidDataMap = new HashMap<String, List<SchHidModel>>();
            for (String saveId : saveIdList) {
                String dateStr = saveId.substring(0, saveId.indexOf("-"));
                String userId = saveId.substring(saveId.indexOf("-") + 1);
                if (userMap.containsKey(userId) || groupMap.containsKey(userId)) {

                    SchHidModel hidMdl = new SchHidModel();
                    hidMdl.setId(saveId);
                    if (userId.startsWith("G")) {
                        GroupModel grpMdl = groupMap.get(userId);
                        hidMdl.setSid(grpMdl.getGroupSid());
                        hidMdl.setName(grpMdl.getGroupName());
                        hidMdl.setUsrKbn(GSConstSchedule.USER_KBN_GROUP);
                    } else {
                        UserSearchModel usrMdl = userMap.get(userId);
                        hidMdl.setSid(usrMdl.getUsrSid());
                        hidMdl.setName(usrMdl.getUsiSei() + " " + usrMdl.getUsiMei());
                        hidMdl.setPhotoKbn(usrMdl.getUsiPictKf());
                        hidMdl.setPhotoSid(usrMdl.getBinSid());
                        hidMdl.setUsrUkoFlg(usrMdl.getUsrUkoFlg());
                        hidMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
                    }

                    UDate date = UDate.getInstanceStr(dateStr);
                    hidMdl.setDate(date);
                    hidMdl.setDateStr(__getHideDataDateString(date, reqMdl__));

                    if (!dateList.contains(dateStr)) {
                        dateList.add(dateStr);
                        hidDataMap.put(dateStr, new ArrayList<SchHidModel>());
                    }
                    hidDataMap.get(dateStr).add(hidMdl);
                }
            }

            Collections.sort(dateList);
            for (String dateStr : dateList) {
                hidDataList.add(hidDataMap.get(dateStr));
            }
        }
        return hidDataList;
    }

    /**
     * <br>一括登録 画面外情報の日付文字列を作成する
     * @param date 表示日付
     * @param reqMdl リクエスト情報
     * @return String 日付文字列(YYYY年MM月DD日(W))
     */
    private String __getHideDataDateString(UDate date, RequestModel reqMdl) {
        String ret = "";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (date != null) {
            //年
            String textYear = gsMsg.getMessage("cmn.year", new String[] {date.getStrYear()});
            StringBuilder buf = new StringBuilder();
            buf.append(textYear);
            buf.append(date.getStrMonth());
            buf.append(gsMsg.getMessage("cmn.month"));
            buf.append(date.getStrDay());
            buf.append(gsMsg.getMessage("cmn.day"));
            buf.append("(");
            buf.append(Sch010Biz.getStrWeek(date.getWeek(), reqMdl__));
            buf.append(")");
            ret = buf.toString();
        }
        return ret;
    }

    /**
     * <br>一括登録 画面外情報の日付文字列を作成する
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException
     */
    public void getIkkatsuList(Sch010ParamModel paramMdl,
            RequestModel reqMdl, Connection con)
            throws SQLException {
        paramMdl.setSchIkkatuTorokuHideList(setIkkatsuData(con, paramMdl));
    }
}