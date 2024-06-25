package jp.groupsession.v2.sch.sch041;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.model.SchEnumReminderTime;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvUserDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchColMsgDao;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.SchExaddressDao;
import jp.groupsession.v2.sch.dao.SchExcompanyDao;
import jp.groupsession.v2.sch.dao.SchExdataBinDao;
import jp.groupsession.v2.sch.dao.SchPriConfDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchExaddressModel;
import jp.groupsession.v2.sch.model.SchExcompanyModel;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.model.SchRepeatKbnModel;
import jp.groupsession.v2.sch.model.ScheduleExSearchModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.sch.sch040.Sch040Biz;
import jp.groupsession.v2.sch.sch040.Sch040Dao;
import jp.groupsession.v2.sch.sch040.model.Sch040AddressModel;
import jp.groupsession.v2.sch.sch040.model.Sch040CompanyModel;
import jp.groupsession.v2.sch.sch041kn.Sch041knBiz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュール繰り返し登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch041Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch041Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Sch041Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * 初期表示画面情報を取得します
     * @param paramMdl アクションフォーム
     * @param pconfig プラグインコンフィグ
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param tempRoot テンポラリディレクトリ
     * @param domain ドメイン
     * @return アクションフォーム
     * @throws Exception 実行時例外
     */
    public Sch041ParamModel getInitData(
            Sch041ParamModel paramMdl,
            PluginConfig pconfig,
            String appRootPath,
            String tempRoot,
            String domain,
            Connection con) throws Exception {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        Sch040Biz sch040biz = new Sch040Biz(con, reqMdl__);
        CommonBiz cmnBiz = new CommonBiz();
        //施設予約の管理者
        boolean rsvAdmin = cmnBiz.isPluginAdmin(con, usModel, GSConstSchedule.PLUGIN_ID_RESERVE);

        paramMdl.setSch010SelectUsrSid(
                NullDefault.getString(
                        paramMdl.getSch010SelectUsrSid(), String.valueOf(sessionUsrSid)));

        Sch010Biz sch010biz = new Sch010Biz(reqMdl__);
        //施設予約使用有無
        if (pconfig.getPlugin("reserve") != null) {
            paramMdl.setReservePluginKbn(GSConstSchedule.RESERVE_PLUGIN_USE);
        } else {
            paramMdl.setReservePluginKbn(GSConstSchedule.RESERVE_PLUGIN_NOT_USE);
        }

        //アドレス帳使用有無
        if (pconfig.getPlugin("address") != null) {
            paramMdl.setAddressPluginKbn(GSConstSchedule.PLUGIN_USE);
            log__.debug("アドレス帳使用");
        } else {
            paramMdl.setAddressPluginKbn(GSConstSchedule.PLUGIN_NOT_USE);
            log__.debug("アドレス帳使用不可");
        }

        //WEB検索使用有無
        if (pconfig.getPlugin("search") != null) {
            paramMdl.setSearchPluginKbn(GSConstSchedule.PLUGIN_USE);
            log__.debug("WEB検索使用");
        } else {
            paramMdl.setSearchPluginKbn(GSConstSchedule.PLUGIN_NOT_USE);
            log__.debug("WEB検索使用不可");
        }

        //個人設定を取得
        SchPriConfModel confMdl = sch010biz.getPrivateConf(sessionUsrSid, con);
        //管理者設定を取得
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel adminConf = biz.getAdmConfModel(con);

        //タイトル色区分
        paramMdl.setSch041colorKbn(adminConf.getSadMsgColorKbn());

        //共有範囲
        paramMdl.setSch040CrangeKbn(adminConf.getSadCrange());

        //時間マスタ
        //午前
        paramMdl.setSch040AmFrHour(adminConf.getSadAmFrH());
        paramMdl.setSch040AmFrMin(adminConf.getSadAmFrM());
        paramMdl.setSch040AmToHour(adminConf.getSadAmToH());
        paramMdl.setSch040AmToMin(adminConf.getSadAmToM());

        //午後
        paramMdl.setSch040PmFrHour(adminConf.getSadPmFrH());
        paramMdl.setSch040PmFrMin(adminConf.getSadPmFrM());
        paramMdl.setSch040PmToHour(adminConf.getSadPmToH());
        paramMdl.setSch040PmToMin(adminConf.getSadPmToM());

        //終日
        paramMdl.setSch040AllDayFrHour(adminConf.getSadAllFrH());
        paramMdl.setSch040AllDayFrMin(adminConf.getSadAllFrM());
        paramMdl.setSch040AllDayToHour(adminConf.getSadAllToH());
        paramMdl.setSch040AllDayToMin(adminConf.getSadAllToM());

        //リクエストパラメータを取得
        //表示開始日
        UDate dspDate = new UDate();
        String strDspDate = NullDefault.getString(paramMdl.getSch010DspDate(), "");
        if (strDspDate.length() == 8 && ValidateUtil.isNumber(strDspDate)) {
            dspDate.setDate(strDspDate);
        }
        paramMdl.setSch010DspDate(dspDate.getDateString());


        //表示項目設定
        SchCommonBiz schBiz = new SchCommonBiz(reqMdl__);
        UDate iniFrDate = new UDate();
        UDate iniToDate = new UDate();
        int iniPub = 0;
        int iniFcolor = GSConstSchedule.DF_BG_COLOR;
        if (confMdl != null) {
            iniFrDate = schBiz.getInitFrDateAuth(con, confMdl);
            iniToDate = schBiz.getInitToDateAuth(con, confMdl);
            iniPub = confMdl.getSccIniPublic();
            if (NullDefault.getString(
                    paramMdl.getSch010SelectUsrKbn(), "").equals(
                            String.valueOf(GSConstSchedule.USER_KBN_GROUP))
                && iniPub != GSConstSchedule.DSP_PUBLIC
                && iniPub != GSConstSchedule.DSP_NOT_PUBLIC) {
                iniPub = GSConstSchedule.DSP_PUBLIC;
            }
            iniFcolor = biz.getUserColor(confMdl.getSccIniFcolor(), con);
        }

//      新規モード,編集共通初期設定

        //名前
        String uid = paramMdl.getSch010SelectUsrSid();
        String ukb = paramMdl.getSch010SelectUsrKbn();
        log__.debug("uid=" + uid);
        log__.debug("ukb=" + ukb);

        paramMdl.setSch040UsrName(getUsrName(Integer.parseInt(uid), Integer.parseInt(ukb), con));
        CmnUsrmDao cuDao = new CmnUsrmDao(con);
        CmnUsrmModel uMdl = cuDao.select(Integer.valueOf(uid));
        if (uMdl != null) {
            paramMdl.setSch040UsrUkoFlg(uMdl.getUsrUkoFlg());
        }
        //拡張情報を画面パラメータへ設定
        int schSid = NullDefault.getInt(paramMdl.getSch010SchSid(), -1);
        Sch040Biz sch040Biz = new Sch040Biz(con, reqMdl__);

        ScheduleExSearchModel extMdl =
            sch040Biz.getSchExData(schSid, adminConf, con);

        UDate frDate = null;
        UDate toDate = null;
        //年月日初期選択値
        UDate uDate = new UDate();
        uDate.setDate(paramMdl.getSch010SelectDate());
        //時間
        if (confMdl != null) {
            frDate = confMdl.getSccIniFrDate();
            toDate = confMdl.getSccIniToDate();
        } else {
            frDate = new UDate();
            toDate = new UDate();
            frDate.setHour(GSConstSchedule.DF_FROM_HOUR);
            frDate.setMinute(GSConstSchedule.DF_FROM_MINUTES);
            toDate.setHour(GSConstSchedule.DF_TO_HOUR);
            toDate.setMinute(GSConstSchedule.DF_TO_MINUTES);
        }

        //リマインダー通知設定
        __setReminderData(paramMdl, reqMdl__, con);

        GsMessage gsMsg = new GsMessage(reqMdl__);

//      既存繰り返しスケジュール情報がある場合
        if (extMdl != null) {
            //登録者
            paramMdl.setSch040AddUsrName(extMdl.getSceAuidSei() + " " + extMdl.getSceAuidMei());
            paramMdl.setSch040AddUsrUkoFlg(extMdl.getAddUsrUkoFlg());

            //登録日時
            String textAddDate = gsMsg.getMessage("schedule.src.84");
            paramMdl.setSch040AddDate(
                    textAddDate + " : "
                    + UDateUtil.getSlashYYMD(extMdl.getSceAdate())
                    + " "
                    + UDateUtil.getSeparateHM(extMdl.getSceAdate()));

            //拡張SID
            int sceSid = extMdl.getSceSid();

            //編集権限のない施設数を取得する。
            int count = sch040biz.getCanNotEditRsvCountEx(
                    extMdl.getSceSid(), sessionUsrSid, con, rsvAdmin);

            //添付ファイル
            if (paramMdl.getSch041InitFlg().equals(String.valueOf(GSConstSchedule.INIT_FLG))) {
                SchExdataBinDao sebDao = new SchExdataBinDao(con);
                String[] binSids = sebDao.getBinSids(sceSid);
                if (binSids != null || binSids.length > 0) {
                    biz.setTempFile(
                            binSids, GSConstSchedule.SCR_ID_SCH041,
                            con, appRootPath, tempRoot, domain);
                }
            }

            paramMdl.setSch040CantReadRsvCount(count);
            //繰り返し登録情報の初期化はURLの直アクセス以外は前画面SCH040で行っている
            if (paramMdl.getDspKbn() == GSConstSchedule.LINK_INIT_FLG) {
                //背景色
                paramMdl.setSch041Bgcolor(NullDefault.getString(
                        String.valueOf(extMdl.getSceBgcolor()), String.valueOf(iniFcolor)));
                paramMdl.setSch040Bgcolor(NullDefault.getString(
                        String.valueOf(extMdl.getSceBgcolor()), String.valueOf(iniFcolor)));

                //公開非公開
                paramMdl.setSch041Public(NullDefault.getString(
                        String.valueOf(extMdl.getScePublic()), String.valueOf(iniPub)));
                paramMdl.setSch040Public(NullDefault.getString(
                        String.valueOf(extMdl.getScePublic()), String.valueOf(iniPub)));
                paramMdl.setSch041Edit(
                        NullDefault.getString(String.valueOf(extMdl.getSceEdit()),
                                String.valueOf(GSConstSchedule.EDIT_CONF_NONE)));
                paramMdl.setSch040Edit(
                        NullDefault.getString(String.valueOf(extMdl.getSceEdit()),
                                String.valueOf(GSConstSchedule.EDIT_CONF_NONE)));


                int sch041FrYear = extMdl.getSceDateFr().getYear();
                int sch041FrMonth = extMdl.getSceDateFr().getMonth();
                int sch041FrDay = extMdl.getSceDateFr().getIntDay();

                int sch041ToYear = extMdl.getSceDateTo().getYear();
                int sch041ToMonth = extMdl.getSceDateTo().getMonth();
                int sch041ToDay = extMdl.getSceDateTo().getIntDay();

                paramMdl.setSch041FrYear(
                        NullDefault.getString(String.valueOf(sch041FrYear),
                                String.valueOf(uDate.getYear())));
                paramMdl.setSch041FrMonth(
                        NullDefault.getString(String.valueOf(sch041FrMonth),
                                String.valueOf(uDate.getMonth())));
                paramMdl.setSch041FrDay(
                        NullDefault.getString(String.valueOf(sch041FrDay),
                                String.valueOf(uDate.getIntDay())));
                paramMdl.setSch041FrDate(
                        biz.getDateSlash(
                                paramMdl.getSch041FrYear(),
                                paramMdl.getSch041FrMonth(),
                                paramMdl.getSch041FrDay()));

                paramMdl.setSch041ToDate(
                        NullDefault.getString(extMdl.getSceDateTo().getDateString("/"),
                        String.valueOf(uDate.getDateString("/"))));
                paramMdl.setSch041ToYear(
                        NullDefault.getString(String.valueOf(sch041ToYear),
                                String.valueOf(uDate.getYear())));
                paramMdl.setSch041ToMonth(
                        NullDefault.getString(String.valueOf(sch041ToMonth),
                                String.valueOf(uDate.getMonth())));
                paramMdl.setSch041ToDay(
                        NullDefault.getString(String.valueOf(sch041ToDay),
                                String.valueOf(uDate.getIntDay())));
                paramMdl.setSch041ToDate(
                        biz.getDateSlash(
                                paramMdl.getSch041ToYear(),
                                paramMdl.getSch041ToMonth(),
                                paramMdl.getSch041ToDay()));

                paramMdl.setSch040FrYear(
                        NullDefault.getString(String.valueOf(sch041FrYear),
                                String.valueOf(uDate.getYear())));
                paramMdl.setSch040FrMonth(
                        NullDefault.getString(String.valueOf(sch041FrMonth),
                                String.valueOf(uDate.getMonth())));
                paramMdl.setSch040FrDay(
                        NullDefault.getString(String.valueOf(sch041FrDay),
                                String.valueOf(uDate.getIntDay())));
                paramMdl.setSch040ToYear(
                        NullDefault.getString(String.valueOf(sch041ToYear),
                                String.valueOf(uDate.getYear())));
                paramMdl.setSch040ToMonth(
                        NullDefault.getString(String.valueOf(sch041ToMonth),
                                String.valueOf(uDate.getMonth())));
                paramMdl.setSch040ToDay(
                        NullDefault.getString(String.valueOf(sch041ToDay),
                                String.valueOf(uDate.getIntDay())));
                paramMdl.setSch040FrDate(NullDefault.getString(
                        extMdl.getSceDateFr().getDateString("/"), uDate.getDateString("/")));
                paramMdl.setSch040ToDate(NullDefault.getString(
                        extMdl.getSceDateTo().getDateString("/"), uDate.getDateString("/")));


                //時間
                int sch041FrHour = extMdl.getSceTimeFr().getIntHour();
                int sch041FrMinute = extMdl.getSceTimeFr().getIntMinute();
                int sch041ToHour = extMdl.getSceTimeTo().getIntHour();
                int sch041ToMinute = extMdl.getSceTimeTo().getIntMinute();

                paramMdl.setSch041TimeKbn(String.valueOf(extMdl.getSceDaily()));

                if (paramMdl.getSch041TimeKbn().equals(
                         String.valueOf(GSConstSchedule.TIME_NOT_EXIST))) {
                    paramMdl.setSch041FrHour(iniFrDate.getStrHour());
                    paramMdl.setSch041FrMin(iniFrDate.getStrMinute());
                    paramMdl.setSch041FrTime(schBiz.getTimeColon(
                            iniFrDate.getStrHour(), iniFrDate.getStrMinute()));

                    paramMdl.setSch041ToHour(iniToDate.getStrHour());
                    paramMdl.setSch041ToMin(iniToDate.getStrMinute());
                    paramMdl.setSch041ToTime(schBiz.getTimeColon(
                            iniToDate.getStrHour(), iniToDate.getStrMinute()));

                    paramMdl.setSch040FrHour(iniFrDate.getStrHour());
                    paramMdl.setSch040FrMin(iniFrDate.getStrMinute());
                    paramMdl.setSch040FrTime(schBiz.getTimeColon(
                            iniFrDate.getStrHour(), iniFrDate.getStrMinute()));

                    paramMdl.setSch040ToHour(iniToDate.getStrHour());
                    paramMdl.setSch040ToMin(iniToDate.getStrMinute());
                    paramMdl.setSch040ToTime(schBiz.getTimeColon(
                            iniToDate.getStrHour(), iniToDate.getStrMinute()));
                } else {
                    paramMdl.setSch041FrHour(
                            NullDefault.getString(String.valueOf(sch041FrHour),
                            String.valueOf(frDate.getIntHour())));
                    paramMdl.setSch041FrMin(
                            NullDefault.getString(String.valueOf(sch041FrMinute),
                                    String.valueOf(frDate.getIntMinute())));
                    paramMdl.setSch041FrTime(
                            biz.getTimeColon(
                                    paramMdl.getSch041FrHour(), paramMdl.getSch041FrMin()));

                    paramMdl.setSch041ToHour(
                            NullDefault.getString(String.valueOf(sch041ToHour),
                                    String.valueOf(toDate.getIntHour())));
                    paramMdl.setSch041ToMin(
                            NullDefault.getString(String.valueOf(sch041ToMinute),
                                    String.valueOf(toDate.getIntMinute())));
                    paramMdl.setSch041ToTime(
                            biz.getTimeColon(
                                    paramMdl.getSch041ToHour(), paramMdl.getSch041ToMin()));

                    paramMdl.setSch040FrHour(
                            NullDefault.getString(String.valueOf(sch041FrHour),
                            String.valueOf(frDate.getIntHour())));
                    paramMdl.setSch040FrMin(
                            NullDefault.getString(String.valueOf(sch041FrMinute),
                                    String.valueOf(frDate.getIntMinute())));
                    paramMdl.setSch040FrTime(
                            biz.getTimeColon(
                                    paramMdl.getSch041FrHour(), paramMdl.getSch041FrMin()));
                    paramMdl.setSch040ToHour(
                            NullDefault.getString(String.valueOf(sch041ToHour),
                                    String.valueOf(toDate.getIntHour())));
                    paramMdl.setSch040ToMin(
                            NullDefault.getString(String.valueOf(sch041ToMinute),
                                    String.valueOf(toDate.getIntMinute())));
                    paramMdl.setSch040ToTime(
                            biz.getTimeColon(
                                    paramMdl.getSch041ToHour(), paramMdl.getSch041ToMin()));
                }

                //タイトル
                paramMdl.setSch041Title(extMdl.getSceTitle());
                paramMdl.setSch040Title(extMdl.getSceTitle());
                //内容
                paramMdl.setSch041Value(extMdl.getSceValue());
                paramMdl.setSch040Value(extMdl.getSceValue());
                //備考
                paramMdl.setSch041Biko(extMdl.getSceBiko());
                paramMdl.setSch040Biko(extMdl.getSceBiko());

                //追加済みユーザSID
                if (paramMdl.getSch041SvUsers() == null
                        || paramMdl.getSch041SvUsers().length == 0) {
                    //拡張登録で同時登録されたユーザの一覧を取得
                    paramMdl.setSch041SvUsers(
                            sch040Biz.getSaveUsersForDbEx(extMdl.getUsrInfList()));
                }

                //同時登録施設
                if (paramMdl.getSch041SvReserve() == null
                        || paramMdl.getSch041SvReserve().length == 0) {
                    //拡張登録で同時登録された施設の一覧を取得
                    paramMdl.setSch041SvReserve(sch040Biz.getSaveReserveForDbEx(extMdl.getSceSid(),
                                                                            con));
                }

                paramMdl.setSch041ExtSid(String.valueOf(sceSid));

                //拡張区分
                paramMdl.setSch041ExtKbn(
                        NullDefault.getString(paramMdl.getSch041ExtKbn(),
                                String.valueOf(extMdl.getSceKbn())));
                //週
                paramMdl.setSch041Week(
                        NullDefault.getString(paramMdl.getSch041Week(),
                                String.valueOf(extMdl.getSceWeek())));
                //日
                paramMdl.setSch041Day(
                        NullDefault.getString(paramMdl.getSch041Day(),
                                String.valueOf(extMdl.getSceDay())));
                //毎年 日
                paramMdl.setSch041DayOfYearly(
                        NullDefault.getString(paramMdl.getSch041DayOfYearly(),
                                String.valueOf(extMdl.getSceDayOfYearly())));

                //毎年 月
                paramMdl.setSch041MonthOfYearly(
                        NullDefault.getString(paramMdl.getSch041MonthOfYearly(),
                        String.valueOf(extMdl.getSceMonthOfYearly())));

                //毎月 週指定・日付指定
                if (extMdl.getSceKbn() == GSConstSchedule.EXTEND_KBN_MONTH
                        && extMdl.getSceDaysMonth() > 0) {
                    paramMdl.setSch041WeekOrDay(String.valueOf(GSConstSchedule.EXTEND_MONTH_DAY));
                } else {
                    paramMdl.setSch041WeekOrDay(String.valueOf(GSConstSchedule.EXTEND_MONTH_WEEK));
                }
                //毎月 日付指定 期間区分
                paramMdl.setSch041ConfKbn(NullDefault.getString(
                        paramMdl.getSch041ConfKbn(), String.valueOf(extMdl.getScePeriodKbn())));

                //毎月 日付指定 対象日数
                paramMdl.setSch041DayOfMonth(NullDefault.getString(paramMdl.getSch041DayOfMonth(),
                        String.valueOf(extMdl.getSceDaysMonth())));

                //振替区分
                paramMdl.setSch041TranKbn(
                        NullDefault.getString(paramMdl.getSch041TranKbn(),
                                String.valueOf(extMdl.getSceTranKbn())));
                //曜日
                if (paramMdl.getSch041Dweek() == null
                || paramMdl.getSch041Dweek().length <= 0) {
                    __setDayWeekToForm(paramMdl, extMdl);
                }

                //会社情報、アドレス帳情報を設定
                SchExcompanyDao exCompanyDao = new SchExcompanyDao(con);
                List<SchExcompanyModel> exCompanyList = exCompanyDao.select(extMdl.getSceSid());
                if (!exCompanyList.isEmpty()) {

                    String[] acoSidList = new String[exCompanyList.size()];
                    String[] abaSidList = new String[exCompanyList.size()];

                    for (int index = 0; index < exCompanyList.size(); index++) {
                        SchExcompanyModel exCompanyModel = exCompanyList.get(index);
                        acoSidList[index] = String.valueOf(exCompanyModel.getAcoSid());
                        abaSidList[index] = String.valueOf(exCompanyModel.getAbaSid());
                    }

                    paramMdl.setSch041CompanySid(acoSidList);
                    paramMdl.setSch041CompanyBaseSid(abaSidList);
                }

                SchExaddressDao exAddressDao = new SchExaddressDao(con);
                List<SchExaddressModel> addressList = exAddressDao.select(sceSid);
                if (addressList != null) {
                    String[] addressId = new String[addressList.size()];
                    for (int index = 0; index < addressList.size(); index++) {
                        addressId[index] = String.valueOf(addressList.get(index).getAdrSid());
                    }
                    paramMdl.setSch041AddressId(addressId);

                    Sch040Dao sch040Dao = new Sch040Dao(con);
                    if (sch040Dao.isExistAdrContact(sceSid)) {
                        paramMdl.setSch041contact(1);
                    }
                }

                //添付ファイル
                SchExdataBinDao sebDao = new SchExdataBinDao(con);
                String[] binSids = sebDao.getBinSids(sceSid);
                if (binSids != null || binSids.length > 0) {
                    biz.setTempFile(
                            binSids, GSConstSchedule.SCR_ID_SCH041,
                            con, appRootPath, tempRoot, domain);
                }
            }

        } else {

            //登録日時の初期化
            paramMdl.setSch040AddDate(null);

            //セッションユーザを登録者へ
            paramMdl.setSch040AddUsrName(usModel.getUsisei() + " " + usModel.getUsimei());
            uMdl = cuDao.select(Integer.valueOf(uid));
            if (uMdl != null) {
                paramMdl.setSch040AddUsrUkoFlg(uMdl.getUsrUkoFlg());
            }
            //背景色
            paramMdl.setSch041Bgcolor(NullDefault.getString(
                    paramMdl.getSch041Bgcolor(), String.valueOf(iniFcolor)));
            //公開非公開
            paramMdl.setSch041Public(NullDefault.getString(
                    paramMdl.getSch041Public(), String.valueOf(iniPub)));
            paramMdl.setSch041Edit(
                    NullDefault.getString(paramMdl.getSch041Edit(),
                            String.valueOf(GSConstSchedule.EDIT_CONF_NONE)));
            paramMdl.setSch041FrDate(
                    NullDefault.getString(paramMdl.getSch041FrDate(), uDate.getDateString("/")));
            paramMdl.setSch041ToDate(
                    NullDefault.getString(paramMdl.getSch041ToDate(), uDate.getDateString("/")));
            paramMdl.setSch041FrYear(
                    NullDefault.getString(paramMdl.getSch041FrYear(),
                            String.valueOf(uDate.getYear())));
            paramMdl.setSch041FrMonth(
                    NullDefault.getString(paramMdl.getSch041FrMonth(),
                            String.valueOf(uDate.getMonth())));
            paramMdl.setSch041FrDay(
                    NullDefault.getString(paramMdl.getSch041FrDay(),
                            String.valueOf(uDate.getIntDay())));
            String frDateStr = biz.getDateSlash(
                    paramMdl.getSch041FrYear(),
                    paramMdl.getSch041FrMonth(), paramMdl.getSch041FrDay());
            paramMdl.setSch041FrDate(
                    NullDefault.getString(paramMdl.getSch041FrDate(), frDateStr));

            paramMdl.setSch041ToYear(
                    NullDefault.getString(paramMdl.getSch041ToYear(),
                            String.valueOf(uDate.getYear())));
            paramMdl.setSch041ToMonth(
                    NullDefault.getString(paramMdl.getSch041ToMonth(),
                            String.valueOf(uDate.getMonth())));
            paramMdl.setSch041ToDay(
                    NullDefault.getString(paramMdl.getSch041ToDay(),
                            String.valueOf(uDate.getIntDay())));
            String toDateStr = biz.getDateSlash(
                    paramMdl.getSch041ToYear(),
                    paramMdl.getSch041ToMonth(),
                    paramMdl.getSch041ToDay());
            paramMdl.setSch041ToDate(
                    NullDefault.getString(paramMdl.getSch041ToDate(), toDateStr));

            //時間
            String initFrTime = iniFrDate.getStrHour() + ":" + iniFrDate.getStrMinute();
            paramMdl.setSch041FrTime(
                    NullDefault.getString(paramMdl.getSch041FrTime(), initFrTime));

            String initToTime = iniToDate.getStrHour() + ":" + iniToDate.getStrMinute();
            paramMdl.setSch041ToTime(
                    NullDefault.getString(paramMdl.getSch041ToTime(), initToTime));

            paramMdl.setSch041FrHour(
                    NullDefault.getString(
                            paramMdl.getSch041FrHour(),
                            String.valueOf(iniFrDate.getIntHour())
                            )
                    );
            paramMdl.setSch041FrMin(
                    NullDefault.getString(
                            paramMdl.getSch041FrMin(),
                            String.valueOf(iniFrDate.getIntMinute())
                            )
                    );
            paramMdl.setSch041ToHour(
                    NullDefault.getString(
                            paramMdl.getSch041ToHour(),
                            String.valueOf(iniToDate.getIntHour())
                            )
                    );
            paramMdl.setSch041ToMin(
                    NullDefault.getString(
                            paramMdl.getSch041ToMin(),
                            String.valueOf(iniToDate.getIntMinute())
                            )
                    );

        }
        paramMdl.setSch041DayOfYearly(
                String.valueOf(NullDefault.getInt(paramMdl.getSch041DayOfYearly(),
                        uDate.getIntDay())));

        paramMdl.setSch041MonthOfYearly(
                String.valueOf(NullDefault.getInt(paramMdl.getSch041MonthOfYearly(),
                uDate.getMonth())));

        paramMdl.setSch041DayOfMonth(
                String.valueOf(NullDefault.getInt(paramMdl.getSch041DayOfMonth(), 1)));

        //グループラベル
        List<SchLabelValueModel> gpLabelList = sch010biz.getGroupLabelList(con, sessionUsrSid);
        log__.info("画面へ設定するグループコンボサイズ==>" + gpLabelList.size());
        paramMdl.setSch040GroupLabel(gpLabelList);

        //デフォルト表示グループ
        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
        String dfGpSidStr = scBiz.getCommboDefaultGroupSidStr(con, sessionUsrSid);
        int dfGpSid = -1;
        boolean myGroupFlg = false;
        int dspGpSid = 0;
        if (ukb.equals(String.valueOf(GSConstSchedule.USER_KBN_USER))) {
            dfGpSid = SchCommonBiz.getDspGroupSid(dfGpSidStr);
        }

        //同時登録コンボの設定
        String dspGpSidStr = NullDefault.getString(paramMdl.getSch041GroupSid(), dfGpSidStr);
        dfGpSidStr = scBiz.getEnableSelectGroup(gpLabelList, dspGpSidStr, dfGpSidStr);
        if (SchCommonBiz.isMyGroupSid(dspGpSidStr)) {
            dspGpSid = SchCommonBiz.getDspGroupSid(dspGpSidStr);

            paramMdl.setSch041GroupSid(dspGpSidStr);
            myGroupFlg = true;
        } else {
            dspGpSid = NullDefault.getInt(paramMdl.getSch041GroupSid(), dfGpSid);
            paramMdl.setSch041GroupSid(dspGpSidStr);
        }
        paramMdl.setSch040GroupSid(paramMdl.getSch041GroupSid());

        //追加済みユーザSID
        ArrayList<Integer> usrSids = new ArrayList<Integer>();
        usrSids.add(Integer.valueOf(GSConstUser.SID_ADMIN));
        usrSids.add(Integer.valueOf(uid));
        UserBiz userBiz = new UserBiz();

        ArrayList < Integer > list = null;
        ArrayList < CmnUsrmInfModel > selectUsrList = null;
        String[] users = paramMdl.getSch041SvUsers();
        if (users != null && users.length > 0) {
            list = new ArrayList<Integer>();
            for (int i = 0; i < users.length; i++) {
                list.add(Integer.valueOf(users[i]));
                //同時登録ユーザを所属リストから除外する
                usrSids.add(Integer.valueOf(users[i]));
                log__.debug("users[i] ==>" + Integer.valueOf(users[i]));
            }
            selectUsrList = (ArrayList<CmnUsrmInfModel>) userBiz.getUserList(con, users);
        }

        ArrayList<CmnUsrmInfModel> belongList =
            schBiz.getBelongUserList(
                    con,
                    dspGpSid,
                    usrSids,
                    sessionUsrSid,
                    myGroupFlg);

        //グループ所属ユーザラベル
        Sch040Biz biz040 = new Sch040Biz(con, reqMdl__);
        biz040.removeNotRegistUser(con, belongList, sessionUsrSid);
        //同時登録ユーザラベル
        paramMdl.setSch040SelectUsrLabel(selectUsrList);

        //公開対象コンボの設定
//        String dspTargetGpSidStr
//            = NullDefault.getString(paramMdl.getSch041DisplayTargetGroup(), dfGpSidStr);
        String dspTargetGpSidStr
                = NullDefault.getString(paramMdl.getSch041DisplayTargetGroup(),
                                        String.valueOf(GSConst.GROUP_COMBO_VALUE));
        dfGpSidStr = scBiz.getEnableSelectGroup(gpLabelList, dspTargetGpSidStr, dfGpSidStr);

        int dspTargetGpSid = 0;
        if (SchCommonBiz.isMyGroupSid(dspTargetGpSidStr)) {
            dspTargetGpSid = SchCommonBiz.getDspGroupSid(dspTargetGpSidStr);
            paramMdl.setSch041DisplayTargetGroup(dspTargetGpSidStr);
            myGroupFlg = true;
        } else {
            dspTargetGpSid = NullDefault.getInt(paramMdl.getSch041DisplayTargetGroup(),
                                                GSConst.GROUP_COMBO_VALUE);
            paramMdl.setSch041DisplayTargetGroup(dspTargetGpSidStr);
        }


        paramMdl.setSch041DisplayTargetGroup(
                NullDefault.getString(paramMdl.getSch041DisplayTargetGroup(), dspTargetGpSidStr));

        List <UsrLabelValueBean> selectTargetList = null;
        String[] targetUsers = paramMdl.getSch041DisplayTarget();
        if (targetUsers != null && targetUsers.length > 0) {
            selectTargetList = cmnBiz.getUserLabelList(con, paramMdl.getSch041DisplayTarget());
        }

        //施設予約部分
        paramMdl.setSch040ReserveGroupLabel(
                sch040biz.getReserveGroupLabelList(con, sessionUsrSid, rsvAdmin, reqMdl__));

//      除外する施設SIDを設定
        ArrayList<Integer> resSids = new ArrayList<Integer>();
        RsvSisDataDao dataDao = new RsvSisDataDao(con);
        //追加済み施設SID
        ArrayList < Integer > resList = null;
        ArrayList<RsvSisDataModel> selectResList = null;
        String[] reservs = paramMdl.getSch041SvReserve();
        if (reservs != null && reservs.length > 0) {
            resList = new ArrayList<Integer>();
            for (int i = 0; i < reservs.length; i++) {
                resList.add(Integer.valueOf(reservs[i]));
                //同時登録施設を所属リストから除外する
                resSids.add(Integer.valueOf(reservs[i]));
                log__.debug("reservs[i] ==>" + Integer.valueOf(reservs[i]));
            }
            if (rsvAdmin) {
                //全施設
                selectResList =
                    dataDao.selectGrpSisetuList(resList);
            } else {
                //閲覧権限のある施設
                selectResList =
                    dataDao.selectGrpSisetuCanReadList(resList, sessionUsrSid);
            }
        }

        paramMdl.setSch040ReserveSelectLabel(selectResList);

//      施設予約個人設定を取得
        RsvUserModel rsvUserConf = getRevUserModel(sessionUsrSid, con);
        int dfReservGpSid = GSConstReserve.COMBO_DEFAULT_VALUE;
        if (rsvUserConf != null) {
            dfReservGpSid = rsvUserConf.getRsgSid();
            if (dfReservGpSid < GSConstReserve.COMBO_DEFAULT_VALUE) {
                dfReservGpSid = GSConstReserve.COMBO_DEFAULT_VALUE;
            }
        }

        ArrayList<RsvSisDataModel> belongResList = null;
        if (rsvAdmin) {
            //全施設を取得する
            belongResList =
                dataDao.selectGrpSisetuList(
                    NullDefault.getInt(
                            paramMdl.getSch041ReserveGroupSid(), dfReservGpSid), resSids);
        } else {
            //アクセス権限のある施設を取得
            belongResList =
                dataDao.selectGrpSisetuCanEditList(
                NullDefault.getInt(
                     paramMdl.getSch041ReserveGroupSid(), dfReservGpSid), resSids, sessionUsrSid);
        }

        //共通項目
        //拡張設定 日コンボを作成
        paramMdl.setSch041DayLabel(getDayLabel(false, false, false));
        paramMdl.setSch041ExDayLabel(getDayLabel(true, true, true));
        paramMdl.setSch041ExDayOfYearlyLabel(getDayLabel(true, true, true));
        //拡張設定 週コンボを作成
        paramMdl.setSch041WeekLabel(getWeekLabel());

        //拡張設定 日付設定区分コンボ
        paramMdl.setSch041ConfKbnLabel(getConfKbnLabel());

        //カラーコメント
        SchColMsgDao msgDao = new SchColMsgDao(con);
        ArrayList<String> msgList = msgDao.selectMsg();
        paramMdl.setSch040ColorMsgList(msgList);
        //年コンボを作成
        paramMdl.setSch040YearLabel(getYearLabel(dspDate.getYear()));
        //月コンボを作成
        paramMdl.setSch040MonthLabel(getMonthLabel());
        //日コンボを作成
        paramMdl.setSch040DayLabel(getDayLabel(false, true, true));
        //分の表示単位を設定
        paramMdl.setHourDivision(schBiz.getHourDivision(con));

        CommonBiz commonBiz = new CommonBiz();
        boolean adminUser = commonBiz.isPluginAdmin(
                con, usModel, GSConstSchedule.PLUGIN_ID_SCHEDULE);

        if (adminUser) {
            paramMdl.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            paramMdl.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }

        //ボタン用の処理モードを設定する。
        String cmd = paramMdl.getCmd();
        String btnCmd = "";
        if (!StringUtil.isNullZeroStringSpace(cmd)) {
            btnCmd = StringUtil.toSingleCortationEscape(cmd);
        }
        paramMdl.setSch040BtnCmd(btnCmd);

        //閲覧不可のグループ、ユーザを設定
        SchDao schDao = new SchDao(con);
        paramMdl.setSchNotAccessGroupList(schDao.getNotRegistGrpList(sessionUsrSid));
        paramMdl.setSchNotAccessUserList(schDao.getNotRegistUserList(sessionUsrSid));

        //添付ファイル
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(reqMdl__,
                GSConstSchedule.PLUGIN_ID_SCHEDULE, GSConstSchedule.SCR_ID_SCH041);
        paramMdl.setFileLabel(cmnBiz.getTempFileLabelList(tempDir));
        paramMdl.setSch041InitFlg(String.valueOf(GSConstSchedule.NOT_INIT_FLG));

        return paramMdl;
    }


    /**
     * <br>ユーザSIDとユーザ区分からユーザ氏名を取得する
     * @param usrSid ユーザSID
     * @param usrKbn ユーザ区分
     * @param con コネクション
     * @return String ユーザ氏名
     * @throws SQLException SQL実行時例外
     */
    public String getUsrName(int usrSid, int usrKbn, Connection con)
    throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String ret = "";
        if (usrKbn == GSConstSchedule.USER_KBN_GROUP) {
            if (usrSid == GSConstSchedule.SCHEDULE_GROUP) {
                //グループ
                String textGroup = gsMsg.getMessage("cmn.group");
                ret = textGroup;
            } else {
                GroupDao grpDao = new GroupDao(con);
                ret = grpDao.getGroup(usrSid).getGrpName();
            }

        } else {
            UserSearchDao uDao = new UserSearchDao(con);
            CmnUsrmInfModel uMdl = uDao.getUserInfoJtkb(usrSid, GSConstUser.USER_JTKBN_ACTIVE);
            ret = uMdl.getUsiSei() + " " + uMdl.getUsiMei();
        }
        return ret;
    }

    /**
     * <br>表示開始日から年コンボを生成します
     * @param year 基準年
     * @return ArrayList (in LabelValueBean)  年コンボ
     */
    public ArrayList<LabelValueBean> getYearLabel(int year) {
        year--;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int i = 0; i < GSConstSchedule.YEAR_LIST_CNT; i++) {
            labelList.add(
                    new LabelValueBean(
                         gsMsg.getMessage("cmn.year", new String[] {String.valueOf(year)}),
                         String.valueOf(year)));
            year++;
        }
        return labelList;
    }

    /**
     * <br>月コンボを生成します
     * @return ArrayList (in LabelValueBean)  月コンボ
     */
    public ArrayList<LabelValueBean> getMonthLabel() {
        int month = 1;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //メッセージ 月
        String strMonth = gsMsg.getMessage("cmn.month");
        for (int i = 0; i < 12; i++) {
            labelList.add(
                    new LabelValueBean(month + strMonth, String.valueOf(month)));
            month++;
        }
        return labelList;
    }


    /**
     * <br>日コンボを生成します
     * @param settingFlg 未設定ラベル有無
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    public ArrayList<LabelValueBean> getDayLabel(boolean settingFlg) {
        return getDayLabel(settingFlg, false);
    }

    /**
     * <br>日コンボを生成します
     * @param settingFlg 未設定ラベル有無
     * @param eomFlg 末日の有無
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    public ArrayList<LabelValueBean> getDayLabel(boolean settingFlg, boolean eomFlg) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        /** メッセージ 日 **/
        String strDay = gsMsg.getMessage("cmn.day");
        //登録日時
        String textNoSet = gsMsg.getMessage("cmn.noset2");
        int day = 1;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        if (settingFlg) {
            labelList.add(new LabelValueBean(textNoSet, String.valueOf(0)));
        }
        for (int i = 0; i < 31; i++) {
            labelList.add(
                    new LabelValueBean(day + strDay, String.valueOf(day)));
            day++;
        }

        //末日
        if (eomFlg) {
            labelList.add(
                    new LabelValueBean(gsMsg.getMessage("tcd.tcd050kn.01"),
                                    Integer.toString(GSConstCommon.LAST_DAY_OF_MONTH)));
        }

        return labelList;
    }

    /**
     * <br>日コンボを生成します
     * @param eomFlg 末日の有無
     * @param firstFlg 1日表示フラグ
     * @param strDayFlg メッセージ(日 or 日間)フラグ
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    public ArrayList<LabelValueBean> getDayLabel(
            boolean eomFlg, boolean firstFlg, boolean strDayFlg) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        /** メッセージ 日 **/
        String strDay = null;
        if (strDayFlg) {
            strDay = gsMsg.getMessage("cmn.day");
        } else {
            strDay = gsMsg.getMessage("cmn.days2");
        }

        //登録日時
        int day = 1;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        for (int i = 0; i < 31; i++) {
            if (i == 0 && !firstFlg) {
                day++;
                continue;
            }
            labelList.add(
                    new LabelValueBean(day + strDay, String.valueOf(day)));
            day++;
        }

        //末日
        if (eomFlg) {
            labelList.add(
                    new LabelValueBean(gsMsg.getMessage("tcd.tcd050kn.01"),
                                    Integer.toString(GSConstCommon.LAST_DAY_OF_MONTH)));
        }

        return labelList;
    }

    /**
     * <br>週コンボを生成します
     * @return ArrayList (in LabelValueBean)  週コンボ
     */
    public ArrayList<LabelValueBean> getWeekLabel() {
        //登録日時
        int week = 1;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int i = 0; i < 5; i++) {
            labelList.add(
                    new LabelValueBean(
                            getWeek(week, reqMdl__),
                            String.valueOf(week)));
            week++;
        }
        return labelList;
    }

    /**
     * <br>[機  能]日付設定区分コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return labelList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getConfKbnLabel() {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        labelList.add(
                new LabelValueBean(gsMsg.getMessage("schedule.sch041.13"), Integer.toString(0)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("schedule.sch041.14"), Integer.toString(1)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("schedule.sch041.15"), Integer.toString(2)));
        return labelList;

    }

    /**
     * <br>ユーザコンボを生成します
     * @param uList (in CmnUsrmInfModel) ユーザ情報リスト
     * @return ArrayList (in LabelValueBean)  年コンボ
     */
    public ArrayList<LabelValueBean> getUserLabel(ArrayList < CmnUsrmInfModel > uList) {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        if (uList != null) {
            CmnUsrmInfModel uMdl = null;
            for (int i = 0; i < uList.size(); i++) {
                uMdl = uList.get(i);
                String name = uMdl.getUsiSei() + " " + uMdl.getUsiMei();
                labelList.add(
                        new LabelValueBean(name, String.valueOf(uMdl.getUsrSid())));
            }
        }

        return labelList;
    }
    /**
     * <br>[機  能]施設予約個人設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @return RsvUserModel
     * @throws SQLException SQL実行時例外
     */
    public RsvUserModel getRevUserModel(int userSid, Connection con) throws SQLException {
        RsvUserDao dao = new RsvUserDao(con);
        RsvUserModel ret = dao.select(userSid);
        return ret;
    }
    /**
     * <br>拡張登録スケジュールも含め編集権限があるか判定する
     * @param scdSid スケジュールSID
     * @param reqMdl リクエスト
     * @param con コネクション
     * @return boolean true:権限あり　false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isAllEditOkEx(
            int scdSid,
            RequestModel reqMdl,
            Connection con) throws SQLException {

        boolean baseEdit = false;
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        CommonBiz commonBiz = new CommonBiz();
        Sch010Biz sch010biz = new Sch010Biz(reqMdl);
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstSchedule.PLUGIN_ID_SCHEDULE);
        //管理者権限の有無
        if (isAdmin) {
            return true;
        }

        SchDataDao scdDao = new SchDataDao(con);
        SchDataModel scdMdl = scdDao.getSchData(scdSid);
        if (scdMdl == null) {
            return false;
        }

        //編集元に対する編集権限チェック
        if (scdMdl.getScdEdit() == GSConstSchedule.EDIT_CONF_OWN) {
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                //自分のスケジュールか
                if (scdMdl.getScdUsrSid() == sessionUsrSid) {
                    baseEdit = true;
                }
                //自分が登録したスケジュールか
                if (scdMdl.getScdAuid() == sessionUsrSid) {
                    baseEdit =  true;
                }
            }
        } else if (scdMdl.getScdEdit() == GSConstSchedule.EDIT_CONF_GROUP) {
            GroupBiz gpBiz = new GroupBiz();
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                //自分が登録したスケジュールか
                if (scdMdl.getScdAuid() == sessionUsrSid) {
                    baseEdit = true;
                }
                //自分も所属しているグループメンバーか
                if (gpBiz.isBothBelongGroup(scdMdl.getScdUsrSid(), sessionUsrSid, con)) {
                    baseEdit = true;
                }
            } else {
                //自分が所属しているグループか
                if (gpBiz.isBelongGroup(sessionUsrSid, scdMdl.getScdUsrSid(), con)) {
                    baseEdit = true;
                }
            }
        } else {
            //編集権限未設定
            baseEdit = true;
        }
        ArrayList<Integer> schDataList = null;
        if (baseEdit) {
            SchCommonBiz cbiz = new SchCommonBiz(reqMdl);
            SchAdmConfModel adminConf = cbiz.getAdmConfModel(con);

            //同時登録スケジュールに対する編集権限チェック
            ScheduleSearchDao scDao = new ScheduleSearchDao(con);
            schDataList = scDao.getScheduleUsrs(scdSid, sessionUsrSid,
                    adminConf.getSadCrange(),
                    GSConstSchedule.SSP_AUTHFILTER_EDIT);
            for (Integer sid : schDataList) {
                if (!sch010biz.isEditOk(sid, reqMdl, con, true)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * スケジュールデータの存在チェックを行う
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param schSid スケジュールSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return boolean true=存在する false:存在しない
     */
    public boolean isExistData(int schSid, Connection con) throws SQLException {
        SchCommonBiz adminbiz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel adminConf = adminbiz.getAdmConfModel(con);
        Sch040Biz biz = new Sch040Biz(con, reqMdl__);
        ScheduleSearchModel scdMdl = biz.getSchData(schSid, adminConf, con);
        if (scdMdl == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 曜日指定パラメータを設定します
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch041ParamModel
     * @param model ScheduleExSearchModel
     */
    private void __setDayWeekToForm(Sch041ParamModel paramMdl, ScheduleExSearchModel model) {

        ArrayList<String> dWeekList = new ArrayList<String>();
        if (model.getSceDweek1() == 1) {
            dWeekList.add("1");
        }
        if (model.getSceDweek2() == 1) {
            dWeekList.add("2");
        }
        if (model.getSceDweek3() == 1) {
            dWeekList.add("3");
        }
        if (model.getSceDweek4() == 1) {
            dWeekList.add("4");
        }
        if (model.getSceDweek5() == 1) {
            dWeekList.add("5");
        }
        if (model.getSceDweek6() == 1) {
            dWeekList.add("6");
        }
        if (model.getSceDweek7() == 1) {
            dWeekList.add("7");
        }
        paramMdl.setSch041Dweek((String[]) dWeekList.toArray(new String[dWeekList.size()]));
    }


    /**
     * <br>[機  能] 会社情報を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch041ParamModel
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setCompanyData(
            Sch041ParamModel paramMdl, Connection con, int userSid, RequestModel reqMdl)
    throws SQLException {

        Sch040Dao dao040 = new Sch040Dao(con);
        Sch040Biz sch040Biz = new Sch040Biz(con, reqMdl);

        String[] acoSidList = paramMdl.getSch041CompanySid();
        String[] abaSidList = paramMdl.getSch041CompanyBaseSid();

        List<String> companyIdList = new ArrayList<String>();
        Map<String, Sch040CompanyModel> companyMap = new HashMap<String, Sch040CompanyModel>();

        Sch040CompanyModel noCompanyModel = new Sch040CompanyModel();
        GsMessage gsMsg = new GsMessage(reqMdl);
        //会社登録無し
        String textCmpDataNone = gsMsg.getMessage("schedule.src.87");
        noCompanyModel.setCompanyName(textCmpDataNone);
        noCompanyModel.setCompanyAddress(null);
        noCompanyModel.setCompanySid(0);
        noCompanyModel.setCompanyBaseSid(0);
        companyMap.put("0:0", noCompanyModel);

        if (acoSidList != null && abaSidList != null) {
            Sch040CompanyModel companyData = null;
            for (int index = 0; index < acoSidList.length; index++) {
                int acoSid = Integer.parseInt(acoSidList[index]);
                int abaSid = Integer.parseInt(abaSidList[index]);

                companyData = sch040Biz.createCompanyData(dao040, acoSid, abaSid);
                if (companyData != null) {
                    String companyId = acoSid + ":" + abaSid;
                    companyMap.put(companyId, companyData);
                    companyIdList.add(companyId);
                }
            }
        }

        //アドレス情報を取得
        Sch040Dao sch040Dao = new Sch040Dao(con);
        List<Sch040AddressModel> addressList
                    = sch040Dao.getAddressList(con, paramMdl.getSch041AddressId(), userSid);
        List<String> addressSidList = new ArrayList<String>();

        if (addressList != null) {

            for (Sch040AddressModel adrData : addressList) {
                String companyId = adrData.getCompanySid() + ":" + adrData.getCompanyBaseSid();
                Sch040CompanyModel companyData = companyMap.get(companyId);
                if (companyData == null) {
                    companyData = sch040Biz.createCompanyData(dao040,
                                                    adrData.getCompanySid(),
                                                    adrData.getCompanyBaseSid());
                    companyMap.put(companyId, companyData);
                    companyIdList.add(companyId);
                }

                addressSidList.add(String.valueOf(adrData.getAdrSid()));
                companyData.getAddressDataList().add(adrData);
                companyMap.put(companyId, companyData);
            }
        }

        String[] companySidArray = new String[companyIdList.size()];
        String[] companyBaseSidArray = new String[companyIdList.size()];
        List<Sch040CompanyModel> companyList = new ArrayList<Sch040CompanyModel>();

        if (!companyMap.get("0:0").getAddressDataList().isEmpty()) {
            companyList.add(companyMap.get("0:0"));
        }

        for (int index = 0; index < companyIdList.size(); index++) {
            String companyId = companyIdList.get(index);
            companySidArray[index] = companyId.split(":")[0];
            companyBaseSidArray[index] = companyId.split(":")[1];
            companyList.add(companyMap.get(companyId));
        }

        paramMdl.setSch041CompanySid(companySidArray);
        paramMdl.setSch041CompanyBaseSid(companyBaseSidArray);
        paramMdl.setSch041AddressId(addressSidList.toArray(new String[addressSidList.size()]));
        paramMdl.setSch041CompanyList(companyList);

    }

    /**
     * <br>[機  能] 重複登録の警告スケジュール一覧を取得する。
     * <br>[解  説] 繰り返し登録用
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl Sch041ParamModel
     * @param sessionUsrSid ユーザSID
     * @param con コネクション
     * @param mode 1:NG 2:警告を表示
     * @param oldMdl 編集前データ
     * @return 警告スケジュールリスト
     * @throws SQLException SQLExceptionm
     */
    public List<SchDataModel> getExSchWarningList(
            RequestModel reqMdl,
            Sch041ParamModel paramMdl,
            int sessionUsrSid,
            Connection con,
            int mode,
            ScheduleSearchModel oldMdl
            ) throws SQLException {

        List<SchDataModel> rptSchList = new ArrayList<SchDataModel>();

        if (paramMdl.getSch041TimeKbn().equals(String.valueOf(GSConstSchedule.TIME_NOT_EXIST))) {
            return rptSchList;
        }

        SchDataDao schDao = new SchDataDao(con);

        //同時登録メンバー
        String[] sv_users = paramMdl.getSch041SvUsers();

        //個人設定を取得する。
        SchPriConfDao priConfDao = new SchPriConfDao(con);
        SchPriConfModel priModel = priConfDao.select(sessionUsrSid);

        //自分の予定の場合は編集可能フラグ
        SchCommonBiz schBiz = new SchCommonBiz(reqMdl__);
        SchRepeatKbnModel repertMdl = schBiz.getRepertKbn(con, priModel, sessionUsrSid);
        boolean mySchOkFlg = repertMdl.getRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG
                            && repertMdl.getRepeatMyKbn() == GSConstSchedule.SCH_REPEAT_MY_KBN_OK;

        //ユーザリストを作成
        List<Integer> usrList = new ArrayList<Integer>();
        if (sv_users != null && sv_users.length > 0) {
            for (int i = 0; i < sv_users.length; i++) {
                if (mySchOkFlg && sessionUsrSid == Integer.parseInt(sv_users[i])) {
                    continue;
                }
                usrList.add(Integer.parseInt(sv_users[i]));
            }
        }

        //ユーザリストに被登録者を含める
        if (!mySchOkFlg || sessionUsrSid != Integer.parseInt(paramMdl.getSch010SelectUsrSid())) {
            usrList.add(Integer.parseInt(paramMdl.getSch010SelectUsrSid()));
        }

        //登録日付リストを取得
        Sch041knBiz knBiz = new Sch041knBiz(con, reqMdl);
        HashMap<String, UDate> addDateMap = knBiz.getInsertDateList(paramMdl, sessionUsrSid, con);
        ArrayList<UDate> dateList = __getSortDateListFromMap(addDateMap);



        //予約開始
        UDate chkFrDate = new UDate();
        int frHour = GSConstSchedule.DAY_START_HOUR;
        int frMin = GSConstSchedule.DAY_START_MINUTES;

        //予約終了
        UDate chkToDate = new UDate();
        int toHour = GSConstSchedule.DAY_END_HOUR;
        int toMin = GSConstSchedule.DAY_END_MINUTES;
        int toSec = GSConstSchedule.DAY_START_SECOND;
        int toMiliSec = GSConstSchedule.DAY_START_MILLISECOND;


        if (paramMdl.getSch041TimeKbn().equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {
            frHour = Integer.parseInt(paramMdl.getSch041FrHour());
            frMin = Integer.parseInt(paramMdl.getSch041FrMin());
            toHour = Integer.parseInt(paramMdl.getSch041ToHour());
            toMin = Integer.parseInt(paramMdl.getSch041ToMin());
            toSec = GSConstSchedule.DAY_START_SECOND;
            toMiliSec = GSConstSchedule.DAY_START_MILLISECOND;
        }

        chkToDate.setSecond(toSec);
        chkToDate.setMilliSecond(toMiliSec);

        //スケジュールSID
        int scdSid = NullDefault.getInt(paramMdl.getSch010SchSid(), 0);

        //拡張ID
        int sceSid = 0;
        if (scdSid > 0 && oldMdl != null) {
            sceSid = oldMdl.getSceSid();
        }

        int scdUsrKbn = GSConstSchedule.USER_KBN_USER;
        String selectUsrKbn = NullDefault.getString(paramMdl.getSch010SelectUsrKbn(), "");
        if (selectUsrKbn.equals(String.valueOf(GSConstSchedule.USER_KBN_GROUP))) {
            scdUsrKbn = GSConstSchedule.USER_KBN_GROUP;
        }
        SchAdmConfModel admConf = schBiz.getAdmConfModel(con);
        boolean canEditRepeatKbn = schBiz.canEditRepertKbn(admConf);
        List<SchDataModel> svList = null;
        if (mode == GSConstSchedule.SCH_REPEAT_KBN_NG) {

            //重複登録不可にしているユーザリストを取得
            List<Integer> ngUsrList = null;
            if (canEditRepeatKbn) {
                //重複登録不可にしているユーザリストを取得
                ngUsrList = priConfDao.getUsrSidListRepeatKbn(usrList,
                                                            GSConstSchedule.SCH_REPEAT_KBN_NG);
            } else {
                if (admConf.getSadRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG) {
                    ngUsrList = new ArrayList<Integer>();
                    ngUsrList.addAll(usrList);
                }
            }

            if (ngUsrList != null && ngUsrList.size() > 0) {
                if (dateList != null && dateList.size() > 0) {

                    for (UDate date : dateList) {
                        chkFrDate = date.cloneUDate();
                        chkFrDate.setHour(frHour);
                        chkFrDate.setMinute(frMin);
                        chkFrDate.setSecond(GSConstSchedule.DAY_START_SECOND);
                        chkFrDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);

                        chkToDate = date.cloneUDate();
                        chkToDate.setHour(toHour);
                        chkToDate.setMinute(toMin);
                        chkToDate.setSecond(toSec);
                        chkToDate.setMilliSecond(toMiliSec);

                        //重複登録しているスケジュール一覧を取得する。
                        svList = schDao.getSchDataEx(ngUsrList, sceSid, chkFrDate, chkToDate,
                                                                    scdUsrKbn);
                        if (svList != null && svList.size() > 0) {
                            rptSchList.addAll(svList);
                        }
                    }

                }
            }

        } else if (mode == GSConstSchedule.SCH_REPEAT_KBN_WARNING) {

            //重複登録警告にしているユーザリストを取得
            List<Integer> warningUsrList = null;
            if (canEditRepeatKbn) {
                warningUsrList = priConfDao.getUsrSidListRepeatKbn(usrList,
                                                            GSConstSchedule.SCH_REPEAT_KBN_WARNING);
            } else {
                warningUsrList = new ArrayList<Integer>();
                if (admConf.getSadRepeatKbn() != GSConstSchedule.SCH_REPEAT_KBN_OK) {
                    warningUsrList.addAll(usrList);
                }
            }

            if (warningUsrList != null && warningUsrList.size() > 0) {
                if (dateList != null && dateList.size() > 0) {

                    for (UDate date : dateList) {
                        chkFrDate = date.cloneUDate();
                        chkFrDate.setHour(frHour);
                        chkFrDate.setMinute(frMin);
                        chkToDate = date.cloneUDate();
                        chkToDate.setHour(toHour);
                        chkToDate.setMinute(toMin);

                        //重複登録しているスケジュール一覧を取得する。
                        svList = schDao.getSchDataEx(warningUsrList, sceSid, chkFrDate, chkToDate,
                                                                    scdUsrKbn);
                        if (svList != null && svList.size() > 0) {
                            rptSchList.addAll(svList);
                        }
                    }

                }
            }
        }

        return rptSchList;
    }

    /**
     * <br>[機  能] Mapに格納されている日付情報を日付リストに置き換えます。
     * <br>[解  説]
     * <br>[備  考]
     * @param map 日付情報
     * @return ArrayList 格納しなおした日付情報
     */
    private ArrayList<UDate> __getSortDateListFromMap(HashMap<String, UDate> map) {

        ArrayList<UDate> col = new ArrayList<UDate>(map.values());
        ArrayList<Sch041DateSortModel> sort = new ArrayList<Sch041DateSortModel>();
        Sch041DateSortModel sortMdl = null;
        for (UDate date : col) {
            sortMdl = new Sch041DateSortModel();
            sortMdl.setUdate(date);
            sort.add(sortMdl);
        }
        Collections.sort(sort);

        ArrayList<UDate> ret = new ArrayList<UDate>();
        for (Sch041DateSortModel model : sort) {
            ret.add(model.getUdate());
        }

        return ret;
    }


    /**
     * <br>[機  能] 表示するリマインダー通知情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setReminderData(
            Sch041ParamModel paramMdl,
            RequestModel reqMdl,
            Connection con
            ) throws SQLException {

        int selectUsrSid = NullDefault.getInt(paramMdl.getSch010SelectUsrSid(), -1);
        BaseUserModel usMdl = reqMdl.getSmodel();
        int sessionUsrSid = usMdl.getUsrsid();
        int usrKbn = NullDefault.getInt(paramMdl.getSch010SelectUsrKbn(), 0);

        SchEnumRemindMode remindMode = SchEnumRemindMode.valueOf(
                usrKbn,
                sessionUsrSid,
                selectUsrSid);

        paramMdl.setSch041ReminderEditMode(remindMode);

        GsMessage gsMsg = new GsMessage(reqMdl);
        paramMdl.setReminderTimeList(
                SchEnumReminderTime.labelList(gsMsg));
    }

    /**
     * <br>[機  能] 第X (週)の文字列を返却します
     * <br>[解  説]
     * <br>[備  考]
     * @param week 週(1～5)
     * @param reqMdl リクエスト情報
     * @return 日本語文字列の曜日。
     */
    public static String getWeek(int week, RequestModel reqMdl) {
        String ret = "　　　";
        GsMessage gsMsg = new GsMessage(reqMdl);
        switch (week) {
        case 1:
            ret = gsMsg.getMessage("cmn.week.no.1");
            break;
        case 2:
            ret = gsMsg.getMessage("cmn.week.no.2");
            break;
        case 3:
            ret = gsMsg.getMessage("cmn.week.no.3");
            break;
        case 4:
            ret = gsMsg.getMessage("cmn.week.no.4");
            break;
        case 5:
            ret = gsMsg.getMessage("cmn.week.no.5");
            break;
        default:
        }
        return ret;
    }
}
