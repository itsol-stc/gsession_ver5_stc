package jp.groupsession.v2.sch.sch200;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.dao.AdrContactDao;
import jp.groupsession.v2.adr.model.AdrContactModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.rap.mbh.push.IPushServiceOperator;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.rsv.RelationBetweenScdAndRsvChkBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvDataPubDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisKryrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisRyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvDataPubModel;
import jp.groupsession.v2.rsv.model.RsvSisKyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisRyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.rsv070.Rsv070Model;
import jp.groupsession.v2.rsv.rsv110.Rsv110Biz;
import jp.groupsession.v2.rsv.rsv210.Rsv210Model;
import jp.groupsession.v2.sch.biz.ISchRegister;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.biz.SchRemindConfWriter;
import jp.groupsession.v2.sch.dao.SchAddressDao;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchCompanyDao;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.SchPriConfDao;
import jp.groupsession.v2.sch.dao.SchPushListDao;
import jp.groupsession.v2.sch.dao.ScheduleReserveDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchAddressModel;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchCompanyModel;
import jp.groupsession.v2.sch.model.SchDataGroupModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;
import jp.groupsession.v2.sch.model.SchRepeatKbnModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.sch.sch010.Sch010UsrModel;
import jp.groupsession.v2.sch.sch040.Sch040Biz;
import jp.groupsession.v2.sch.sch040.Sch040Dao;
import jp.groupsession.v2.sch.sch040.Sch040ParamModel;
import jp.groupsession.v2.sch.sch040.model.Sch040AddressModel;
import jp.groupsession.v2.sch.sch040.model.Sch040CompanyModel;
import jp.groupsession.v2.sch.sch040.model.Sch040ContactModel;
import jp.groupsession.v2.sch.sch040.model.Sch040DBCompanyBaseModel;
import jp.groupsession.v2.sch.sch040.model.Sch040DBCompanyModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] スケジュール 個人週間画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch200Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch200Biz.class);
    /** DBコネクション */
    private Connection con__ = null;
    /** 採番コントローラ */
    public MlCountMtController cntCon__ = null;
    /** リクエスト情報 */
    public RequestModel reqMdl__ = null;
    /** セッションユーザ所属グループSIDリスト */
    private List<Integer> belongGpSidList__ = null;
    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param cntCon MlCountMtController
     * @param reqMdl RequestModel
     */
    public Sch200Biz(Connection con, MlCountMtController cntCon, RequestModel reqMdl) {
        con__ = con;
        cntCon__ = cntCon;
        reqMdl__ = reqMdl;
    }
    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Sch200Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }
    /**
     * <p>コンストラクタ
     * @param reqMdl RequestModel
     */
    public Sch200Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * 初期表示画面情報を取得します
     * @param paramMdl Sch200ParamModel
     * @return アクションフォーム
     * @throws Exception 処理例外
     */
    public Sch200ParamModel getInitData(Sch200ParamModel paramMdl) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //セッションユーザの所属グループを格納
        CmnBelongmDao bdao = new CmnBelongmDao(con__);
        belongGpSidList__ = bdao.selectUserBelongGroupSid(sessionUsrSid);

        //管理者設定を取得
        SchCommonBiz cmnbiz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel adminConf = cmnbiz.getAdmConfModel(con__);

        //共有範囲
        paramMdl.setSch010CrangeKbn(adminConf.getSadCrange());

        //表示ユーザを取得
        if (paramMdl.getSch100SelectUsrSid() == null) {
            //初期表示
            paramMdl.setSch200InitFlg(1);
        }

        String sch010UsrSid = paramMdl.getSch010SelectUsrSid();
        if (sch010UsrSid != null && sch010UsrSid.startsWith("G")) {
            sch010UsrSid = String.valueOf(sessionUsrSid);
        }
        paramMdl.setSch100SelectUsrSid(sch010UsrSid);

        paramMdl.setDspMod(GSConstSchedule.DSP_MOD_KOJIN_WEEK);

        int userSid = NullDefault.getInt(
                paramMdl.getSch100SelectUsrSid(), sessionUsrSid);
        paramMdl.setSch010SelectUsrSid(String.valueOf(userSid));
        paramMdl.setSch010SelectUsrKbn(NullDefault.getString(paramMdl.getSch010SelectUsrKbn(),
                String.valueOf(GSConstSchedule.USER_KBN_USER)));
        int userKbn = Integer.parseInt(paramMdl.getSch010SelectUsrKbn());


        //個人設定取得&作成
        SchPriConfModel confMdl = getPrivateConf(sessionUsrSid, con__);
        paramMdl.setSch010Reload(confMdl.getSccReload());

        //各ユーザで設定した週スケジュールの開始曜日を取得
        int startWeek = confMdl.getSccIniWeek();

        //カレンダー表示開始の曜日を取得
        UDate dspDate = new UDate();
        String strDspDate = NullDefault.getString(paramMdl.getSch010DspDate(), "");
        if (strDspDate.length() == 8 && ValidateUtil.isNumber(strDspDate)) {
            dspDate.setDate(strDspDate);
        }

        //初期表示時かつメイン画面から遷移していない時に、個人設定の初期表示設定を適用
        if (paramMdl.getIniDsp() == GSConstSchedule.INIT_FLG
                && paramMdl.getSch010FromMain() != GSConstSchedule.FROM_MAIN
                    && paramMdl.getChangeDateFlg() == 0) {
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
        }
        //表示項目取得
        paramMdl.setSch010DspDate(dspDate.getDateString());

        if (userSid < 0 && !SchCommonBiz.isMyGroupSid(paramMdl.getSch010DspGpSid())) {
            //グループスケジュール表示
            userKbn = GSConstSchedule.USER_KBN_GROUP;
            userSid = NullDefault.getInt(paramMdl.getSch010DspGpSid(), 0);
        } else {
            if (paramMdl.getSch010SelectUsrSid() != null) {
                userKbn = GSConstSchedule.USER_KBN_USER;
            }
        }
        log__.debug("SCH200Biz.userSid==>" + userSid);
        log__.debug("SCH200Biz.userKbn==>" + userKbn);

        //カレンダー開始日付
        UDate frDate = dspDate.cloneUDate();
        log__.debug("カレンダー開始日付=" + frDate.getDateString());
        paramMdl.setSch200FrDate(frDate.getDateString());
        //カレンダー終了日付
        UDate toDate = frDate.cloneUDate();
        toDate.addDay(6);
        log__.debug("カレンダー終了日付=" + toDate.getDateString());
        paramMdl.setSch200ToDate(toDate.getDateString());
        //表示日付
        paramMdl.setSch200Year(dspDate.getYear());
        paramMdl.setSch200Month(dspDate.getMonth() - 1);
        paramMdl.setSch200Day(dspDate.getIntDay());


        log__.debug("paramMdl.getSch010DspGpSid()-(1)==>" + paramMdl.getSch010DspGpSid());



        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con__,
                usModel, GSConstSchedule.PLUGIN_ID_SCHEDULE);
        if (adminUser) {
            paramMdl.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            paramMdl.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }
        //年
        String textYear = gsMsg.getMessage("cmn.year", new String[] {dspDate.getStrYear()});
        //月
        String textMonth = gsMsg.getMessage("cmn.month");
        paramMdl.setSch200StrDspDate(textYear + dspDate.getStrMonth() + textMonth);
        //自動リロード時間を設定する。
        paramMdl.setSch200Reload(__getReloadtime(sessionUsrSid));

        //閲覧不可のグループを設定
        SchDao schDao = new SchDao(con__);
        paramMdl.setSchNotAccessGroupList(schDao.getNotAccessGrpList(sessionUsrSid));

        //初期表示済みにする
        paramMdl.setIniDsp(GSConstSchedule.NOT_INIT_FLG);

        // スケジュールSIDを初期化
        paramMdl.setSch010SchSid(String.valueOf(-1));

        // 個人スケジュールなので、ユーザ区分は「ユーザ」
        paramMdl.setSch010SelectUsrKbn(Integer.toString(GSConstSchedule.USER_KBN_USER));

        return paramMdl;
    }


    /**
     * <br>指定ユーザの個人週間スケジュールを取得します
     * @param paramMdl Sch200ParamModel
     * @param usrSid ユーザSID
     * @param usrKbn ユーザ区分 0=一般ユーザ 1=グループ
     * @param sessionUsrSid セッションユーザSID
     * @param gpmemFlg グループメンバースケジュール表示有無
     * @param con コネクション
     * @return ArrayList グループ>指定ユーザの順に格納
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<Sch200WeekOfModel> __getWeekScheduleList(
            Sch200ParamModel paramMdl,
            int usrSid,
            int usrKbn,
            int sessionUsrSid,
            boolean gpmemFlg,
            Connection con) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        UDate frDate = new UDate();
        frDate.setDate(paramMdl.getSch200FrDate());
        UDate toDate = new UDate();
        toDate.setDate(paramMdl.getSch200ToDate());
        frDate.cloneUDate();
        toDate.cloneUDate();

        frDate.setHour(GSConstSchedule.DAY_START_HOUR);
        frDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        toDate.setHour(GSConstSchedule.DAY_END_HOUR);
        toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_END_SECOND);
        //休日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap < String, CmnHolidayModel > holMap = holDao.getHoliDayList(frDate, toDate);
        CmnHolidayModel holMdl = null;

        //グループ・指定ユーザのcolListを保持
        ArrayList<Sch200WeekOfModel> rowList = new ArrayList<Sch200WeekOfModel>();
        //ユーザ情報を保持
        Sch010UsrModel usMdl = null;
        ArrayList < Sch200DayOfModel > colList = null;
        //DBスケジュール情報
        ArrayList < SchDataModel > schDataList = null;
        //ユーザ別、一週間分のスケジュール
        Sch200WeekOfModel weekMdl = null;

        //指定ユーザスケジュール
        weekMdl = new Sch200WeekOfModel();
        colList = new ArrayList<Sch200DayOfModel>();
        usMdl = new Sch010UsrModel();
        UserSearchDao usrDao = new UserSearchDao(con);

        log__.debug("usrSid=>" + usrSid);
        log__.debug("usrKbn=>" + usrKbn);
        log__.debug("個人週間データ取得開始日" + frDate);

        //デフォルト表示グループ
        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
        String dfGpSidStr = scBiz.getDispDefaultGroupSidStr(con, sessionUsrSid);
        int dfGpSid = SchCommonBiz.getDspGroupSid(dfGpSidStr);

        int dspGpSid = 0;
        //表示グループ
        String dspGpSidStr = NullDefault.getString(paramMdl.getSch010DspGpSid(), dfGpSidStr);
        if (SchCommonBiz.isMyGroupSid(dspGpSidStr)) {
            dspGpSid = SchCommonBiz.getDspGroupSid(dspGpSidStr);
        } else {
            dspGpSid = NullDefault.getInt(paramMdl.getSch010DspGpSid(), dfGpSid);
        }

        //ユーザスケジュール
        if (usrKbn == GSConstSchedule.USER_KBN_USER) {
            CmnUsrmInfModel usrInfMdl = usrDao.getUserInfoJtkb(
                    usrSid, GSConstUser.USER_JTKBN_ACTIVE);
            if (usrInfMdl != null) {
                paramMdl.setSch200StrUserName(usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());
                usMdl.setUsrName(usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());
            }

            usMdl.setUsrSid(usrSid);
            usMdl.setUsrKbn(GSConstSchedule.USER_KBN_USER);
            weekMdl.setSch200UsrMdl(usMdl);
        //グループスケジュール
        } else if (usrKbn == GSConstSchedule.USER_KBN_GROUP) {

            String grpName = null;
            if (usrSid == GSConstSchedule.SCHEDULE_GROUP) {
                //グループ
                String textGroup = gsMsg.getMessage("cmn.group");
                grpName = textGroup;
            } else {
                GroupDao grpDao = new GroupDao(con);
                CmnGroupmModel gpMdl = grpDao.getGroup(usrSid);
                if (gpMdl != null) {
                    grpName = gpMdl.getGrpName();

                }
            }
            paramMdl.setSch200StrUserName(grpName);
            usMdl.setUsrName(grpName);
            usMdl.setUsrSid(usrSid);
            usMdl.setUsrKbn(GSConstSchedule.USER_KBN_GROUP);
            weekMdl.setSch200UsrMdl(usMdl);
        }


        //スケジュール情報を取得(指定ユーザ)
        ScheduleSearchDao schDao = new ScheduleSearchDao(con);
        if (gpmemFlg) {
            //グループスケジュールを取得
            schDataList = schDao.select(
                    usrSid,
                    usrKbn,
                    -1,
                    frDate,
                    toDate,
                    GSConstSchedule.DSP_MOD_WEEK,
                    sessionUsrSid);
            //除外するユーザSIDを設定
            ArrayList<Integer> usrSids = new ArrayList<Integer>();
            usrSids.add(Integer.valueOf(GSConstUser.SID_ADMIN));
            usrSids.add(Integer.valueOf(GSConstUser.SID_SYSTEM_MAIL));
            //所属ユーザを取得
            UserSearchDao usDao = new UserSearchDao(con);
            ArrayList<UserSearchModel> belongList = usDao.getBelongUserInfoJtkb(usrSid,
                    usrSids, GSConstUser.USER_SORT_YKSK, GSConst.ORDER_KEY_ASC, -1, -1);
            //メンバースケジュールを取得
            ArrayList <SchDataModel> memSchDataList = schDao.selectUsers(
                    belongList,
                    GSConstSchedule.USER_KBN_USER,
                    -1,
                    frDate,
                    toDate,
                    GSConstSchedule.DSP_MOD_WEEK);
            log__.debug("個人週間データ取得開始日２" + frDate);
            //グループとメンバースケジュールを合体
            schDataList.addAll(memSchDataList);
        } else {
            //グループ又はユーザのスケジュールを取得
            schDataList = schDao.select(
                    usrSid,
                    usrKbn,
                    -1,
                    frDate,
                    toDate,
                    GSConstSchedule.DSP_MOD_WEEK,
                    sessionUsrSid);
            log__.debug("個人週間データ取得開始日3" + frDate);
        }

        //指定公開スケジュールのスケジュールSIDを取得する
        List<Integer> checkScdSidList =
                schDataList.stream()
                .map(s -> s.getScdSid())
                .collect(Collectors.toList());
        SchDataPubDao schPubDao = new SchDataPubDao(con);
        List<Integer> pubScdSidList
            = schPubDao.getUserPubScdSidList(sessionUsrSid,
                                            checkScdSidList);

        UDate dspDate = new UDate();
        dspDate.setDate(
                NullDefault.getString(paramMdl.getSch010DspDate(), new UDate().getDateString()));

        //六曜表示/非表示を取得
        CommonBiz cmnBiz = new CommonBiz();
        boolean rokuyoDspFlg = cmnBiz.getRokuyoDspKbn(con, sessionUsrSid);

        Sch200DayOfModel dayMdl = null;
        ArrayList<Sch200SimpleModel> dayMdlList = null;
        Sch200SimpleModel dspSchMdl = null;
        //システム日付
        UDate today = new UDate();
        while (frDate.compareDateYMD(toDate) != UDate.SMALL) {
            //１日分のスケジュール
            dayMdlList = new ArrayList<Sch200SimpleModel>();
            dayMdl = new Sch200DayOfModel();
            // 休日名称
            holMdl = holMap.get(frDate.getDateString());
            if (holMdl != null) {
              dayMdl.setHolidayName(holMdl.getHolName());
              dayMdl.setHolidayKbn(GSConstSchedule.HOLIDAY_TRUE);
            } else {
              dayMdl.setHolidayName(null);
              dayMdl.setHolidayKbn(GSConstSchedule.HOLIDAY_FALSE);
            }
            //同月判定
            if (__isThisMonth(dspDate, frDate)) {
                dayMdl.setThisMonthKbn(1);
            } else {
                dayMdl.setThisMonthKbn(0);
            }
            dayMdl.setDspDay(String.valueOf(frDate.getIntDay()));
            dayMdl.setSchDate(frDate.getDateString());
            dayMdl.setUsrSid(usrSid);
            dayMdl.setUsrKbn(usrKbn);
            dayMdl.setWeekKbn(frDate.getWeek());
            if (today.getDateString().equals(frDate.getDateString())) {
                dayMdl.setTodayKbn(GSConstSchedule.TODAY_TRUE);
            } else {
                dayMdl.setTodayKbn(GSConstSchedule.TODAY_FALSE);
            }

            //六曜
            if (rokuyoDspFlg) {
                int rokuyoKbn = cmnBiz.setRkyKbn(cmnBiz.getRokuyou(frDate));
                switch (rokuyoKbn) {
                    case GSConst.RKY_KBN_SENSHOU:
                        dayMdl.setRokuyouClass("header_rokuyo-senshou");
                        break;
                    case GSConst.RKY_KBN_TOMOBIKI:
                        dayMdl.setRokuyouClass("header_rokuyo-tomobiki");
                        break;
                    case GSConst.RKY_KBN_SENBU:
                        dayMdl.setRokuyouClass("header_rokuyo-senbu");
                        break;
                    case GSConst.RKY_KBN_BUTSUMETSU:
                        dayMdl.setRokuyouClass("header_rokuyo-butsumetsu");
                        break;
                    case GSConst.RKY_KBN_TAIAN:
                        dayMdl.setRokuyouClass("header_rokuyo-taian");
                        break;
                    case GSConst.RKY_KBN_SHAKKOU:
                        dayMdl.setRokuyouClass("header_rokuyo-shakkou");
                        break;
                    default:
                }
            }

            SchDataModel schMdl = null;

            for (int j = 0; j < schDataList.size(); j++) {
                //スケジュール１個
                schMdl = schDataList.get(j);
                //本日のスケジュールか判定
                if (Sch010Biz.isTodaySchedule(schMdl, frDate)) {
                    dspSchMdl = new Sch200SimpleModel();

                    //表示スケジュールを設定
                    dspSchMdl = __getSchDspMdl(schMdl, sessionUsrSid,
                                         dspGpSid, frDate, toDate, paramMdl,
                                         pubScdSidList,
                                         con);

                    dayMdlList.add(dspSchMdl);
                }
            }
            //dayMdl.setSchDataList(dayMdlList);
            dayMdl.setSch200DataList(dayMdlList);
            colList.add(dayMdl);
            //日付を進める
            frDate.addDay(1);
        }
        weekMdl.setSch200SchList(colList);
        rowList.add(weekMdl);

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
     * @param userSid セッションユーザSID
     * @param groupSid グループSID
     * @param myGroupFlg マイグループ選択
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    private List<UsrLabelValueBean> __getUserLabelList(
        int userSid, int groupSid, boolean myGroupFlg) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List < UsrLabelValueBean > labelList = null;
        UserBiz usrBiz = new UserBiz();
        if (myGroupFlg) {
            labelList = usrBiz.getMyGroupUserLabelList(con__, userSid, groupSid, null);
        } else {
            labelList = usrBiz.getNormalUserLabelList(con__, groupSid, null, false, gsMsg);
        }

        //閲覧を許可されていないユーザを除外する
        SchDao schDao = new SchDao(con__);
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
     * <br>[機  能] 自動リロード時間を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid ユーザSID
     * @return int リロード時間
     * @throws SQLException SQL実行時例外
     */
    private int __getReloadtime(int sessionUsrSid)
    throws SQLException {

        int reloadTime = GSConstReserve.AUTO_RELOAD_10MIN;
        SchPriConfDao dao = new SchPriConfDao(con__);
        SchPriConfModel model = dao.select(sessionUsrSid);
        if (model != null) {
            reloadTime = model.getSccReload();
        }
        return reloadTime;
    }

    /**
     * <br>[機  能] 表示用スケジュールデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param schMdl スケジュールデータ
     * @param sessionUsrSid ユーザSID
     * @param grpSid グループSID
     * @param frDate 開始日時
     * @param toDate 終了日時
     * @param paramMdl Sch200ParamModel
     * @param pubScdSidList セッションユーザが公開対象として指定されているスケジュールの一覧
     * @param con コネクション
     * @return Sch200SimpleModel 表示用モデル
     * @throws SQLException SQL実行時例外
     */
    private Sch200SimpleModel __getSchDspMdl(
            SchDataModel schMdl,
            int sessionUsrSid,
            int grpSid,
            UDate frDate,
            UDate toDate,
            Sch200ParamModel paramMdl,
            List<Integer> pubScdSidList,
            Connection con) throws SQLException {

        Sch200SimpleModel dspSchMdl = new Sch200SimpleModel();
        //表示スケジュールユーザと同じグループに所属しているか判定
        boolean grpBelongHnt = __getSchUsrBelongHnt(schMdl.getScdUserBlongGpList());

        //予定あり
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textYoteiari = gsMsg.getMessage("schedule.src.9");

        boolean publicUserMatch = false;
        if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER
                && schMdl.getScdUsrSid() == sessionUsrSid) {
            //本人
            dspSchMdl.setPublic(GSConstSchedule.DSP_PUBLIC);
            dspSchMdl.setTitle(schMdl.getScdTitle());
        } else if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER
                && schMdl.getScdUsrSid() != sessionUsrSid) {

            //他ユーザ
            if (schMdl.getScdAuid() == sessionUsrSid
                    || schMdl.getScdAuid() == sessionUsrSid) {
                //登録者の場合は表示する
                dspSchMdl.setPublic(GSConstSchedule.DSP_PUBLIC);
                dspSchMdl.setTitle(schMdl.getScdTitle());

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

                if (!paramMdl.isSch200MyGroupFlg()) {
                    //所属グループのみ公開(マイグループは除く)
                    dspSchMdl.setTitle(schMdl.getScdTitle());
                    dspSchMdl.setGrpEdKbn(GSConstSchedule.EDIT_CONF_GROUP);
                    dspSchMdl.setPublic(schMdl.getScdPublic());
                } else {
                    //マイグループには「予定あり」
                    dspSchMdl.setTitle(textYoteiari);
                    dspSchMdl.setPublic(GSConstSchedule.DSP_YOTEIARI);
                }

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

            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_TITLE) {
                //タイトルのみ公開
                dspSchMdl.setTitle(schMdl.getScdTitle());
                dspSchMdl.setPublic(GSConstSchedule.DSP_TITLE);

            } else {
                //公開
                dspSchMdl.setTitle(schMdl.getScdTitle());
            }

        //グループスケジュール
        } else {

            if (sessionUsrSid == schMdl.getScdAuid()) {
                dspSchMdl.setKjnEdKbn(GSConstSchedule.EDIT_CONF_OWN);
            }

            if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC
                    && !(grpBelongHnt)) {

                if (schMdl.getScdAuid() == sessionUsrSid
                        || schMdl.getScdEuid() == sessionUsrSid) {
                    //登録者の場合は表示する
                    dspSchMdl.setPublic(GSConstSchedule.DSP_PUBLIC);
                    dspSchMdl.setTitle(schMdl.getScdTitle());
                } else {
                    //非公開
                    dspSchMdl.setPublic(schMdl.getScdPublic());
                    dspSchMdl.setSchSid(schMdl.getScdSid());
                    dspSchMdl.setUserSid(String.valueOf(schMdl.getScdUsrSid()));
                    dspSchMdl.setTime("");
                    dspSchMdl.setTimeKbn(schMdl.getScdDaily());
                    dspSchMdl.setBgColor(schMdl.getScdBgcolor());
                    dspSchMdl.setValueStr(schMdl.getScdValue());
                    dspSchMdl.setUserName(schMdl.getScdUserName());
                }
                return dspSchMdl;

            } else if (schMdl.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC
                    && grpBelongHnt) {

                //公開
                dspSchMdl.setTitle(schMdl.getScdTitle());
                dspSchMdl.setGrpEdKbn(GSConstSchedule.EDIT_CONF_GROUP);

            } else {
                //公開
                dspSchMdl.setTitle(schMdl.getScdTitle());
                dspSchMdl.setGrpEdKbn(GSConstSchedule.EDIT_CONF_GROUP);
            }

            //グループのスケジュール
            dspSchMdl.setPublic(schMdl.getScdPublic());
        }

        dspSchMdl.setSchSid(schMdl.getScdSid());
        dspSchMdl.setUserSid(String.valueOf(schMdl.getScdUsrSid()));
        dspSchMdl.setUserKbn(String.valueOf(schMdl.getScdUsrKbn()));
        dspSchMdl.setTime(Sch010Biz.getTimeString(schMdl, frDate));
        dspSchMdl.setFromDate(schMdl.getScdFrDate());
        dspSchMdl.setToDate(schMdl.getScdToDate());
        dspSchMdl.setTimeKbn(schMdl.getScdDaily());
        dspSchMdl.setBgColor(schMdl.getScdBgcolor());
        dspSchMdl.setValueStr(schMdl.getScdValue());
        dspSchMdl.setUserName(schMdl.getScdUserName());
        SchCommonBiz schBiz = new SchCommonBiz(reqMdl__);
        dspSchMdl.setPublicIconFlg(
                schBiz.getPublicIconFlg(schMdl,
                                        sessionUsrSid,
                                        grpBelongHnt,
                                        publicUserMatch));

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
     * <br>表示用のJSONデータを作成します。
     * @param paramMdl Sch200ParamModel
     * @param res HttpServletResponse
     * @param con コネクション
     * @throws SQLException sql実行例外
     * @throws Exception 処理例外
     */
    public void setJsonData(Sch200ParamModel paramMdl,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {
        boolean writeSuccess = false;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //d日（ddd）
        String textDday = gsMsg.getMessage("schedule.src.3");
        //yyyy年 M月 d日 dddd
        String textYyyyDddd = gsMsg.getMessage("schedule.src.4");
        //yyyy年 M月
        String textYyyyM = gsMsg.getMessage("schedule.src.5");
        //yyyy年M月 d日{ &#8212;[yyyy年 ][ M月] d日}
        String textYyyyMd = gsMsg.getMessage("schedule.src.6");
        //d日（ddd）
        String textddd = gsMsg.getMessage("schedule.src.3");
        try {

            //セッション情報を取得
            BaseUserModel usModel = reqMdl__.getSmodel();
            int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSIDs

            //セッションユーザの所属グループを格納
            CmnBelongmDao bdao = new CmnBelongmDao(con);
            belongGpSidList__ = bdao.selectUserBelongGroupSid(sessionUsrSid);

            //表示項目取得
            UDate dspDate = new UDate();
            String strDspDate = NullDefault.getString(paramMdl.getSch010DspDate(), "");
            if (strDspDate.length() == 8 && ValidateUtil.isNumber(strDspDate)) {
                dspDate.setDate(strDspDate);
            }
            paramMdl.setSch010DspDate(dspDate.getDateString());
            UDate frDate = new UDate();
            frDate.setDate(paramMdl.getSch200FrDate());
            UDate toDate = new UDate();
            toDate.setDate(paramMdl.getSch200ToDate());

            int userSid = Integer.valueOf(paramMdl.getSch100SelectUsrSid());
            int userKbn = NullDefault.getInt(paramMdl.getSch010SelectUsrKbn(),
                    GSConstSchedule.USER_KBN_USER);
            SchDao schDao = new SchDao(con);
            boolean editable = schDao.canRegistUserSchedule(userSid, sessionUsrSid);
            //グループメンバー表示フラグ
            boolean gpmemFlg = false;
            paramMdl.setSch200ScheduleList(
                    __getWeekScheduleList(
                            paramMdl,
                            userSid,
                            userKbn,
                            sessionUsrSid,
                            gpmemFlg,
                            con)
                            );

            LinkedHashMap<String, Sch200Event> hmap = new LinkedHashMap<String, Sch200Event>();
            if (!paramMdl.getSch200ScheduleList().isEmpty()) {
                for (Sch200WeekOfModel monthMdl : paramMdl.getSch200ScheduleList()) {
                    hmap = __getJsonData(paramMdl, hmap, monthMdl, con);
                }
            }

            //個人設定取得&作成
            Sch010Biz biz = new Sch010Biz(reqMdl__);
            SchPriConfModel confMdl = biz.getPrivateConf(sessionUsrSid, con);
            paramMdl.setSch020Reload(confMdl.getSccReload());

            //イベント作成
            ArrayList<Sch200Event> eventList = new ArrayList<Sch200Event>(hmap.values());
            Sch200Param param = new Sch200Param();

            //表示形式
            param.setDefaultView(GSConstSchedule.DEFAULT_VIEW);
            //先頭表示時間
            UDate date = new UDate();
            param.setFirstHour(NullDefault.getString(confMdl.getSccFrDate().getStrHour(),
                  String.valueOf(date.getIntHour())));
            //表示開始時間
            param.setMinTime("0:00");
            //表示終了時間
            param.setMaxTime("24:00");
            //先頭表示曜日
            if (strDspDate.length() == 8 && ValidateUtil.isNumber(strDspDate)) {
                date = UDate.getInstanceStr(paramMdl.getSch010DspDate());
            }

            //param.setFirstDay(0);
            param.setFirstDay(date.getWeek() - 1); //今日が左端
            //param.setFirstDay(now.getWeek() - 4); //今日が中央
            //timeFormat
            param.setTimeFormat(GSConstSchedule.DEFAULT_TIME_FORMAT);
            //aspectRatio
            param.setAspectRatio(GSConstSchedule.DEFAULT_ASPECT_RATIO);
            //theme
            param.setTheme(GSConstSchedule.DEFAULT_THEME);
            //selectable
            param.setSelectable(GSConstSchedule.DEFAULT_SELECTTABLE);
            //selectHelper
            param.setSelectHelper(GSConstSchedule.DEFAULT_SELECTHELPER);
            //header
            Sch200Header header = new Sch200Header();
            header.setCenter(GSConstSchedule.DEFAULT_HEADER_CENTER);
            header.setLeft(GSConstSchedule.DEFAULT_HEADER_LEFT);
            header.setRight(GSConstSchedule.DEFAULT_HEADER_RIGHT);
            param.setHeader(header);
            //slotMinutes
            param.setSlotMinutes(30);
            //ﾀｲﾄﾙフォーマット
            Sch200TitleFormat titleFormat = new Sch200TitleFormat();
            titleFormat.setMonth(textYyyyM);
            titleFormat.setWeek(textYyyyMd);
            titleFormat.setDay(textYyyyDddd);
            param.setTitleFormat(titleFormat);
            //コラムフォーマット
            Sch200ColumnFormat columnFormat = new Sch200ColumnFormat();
            columnFormat.setMonth(GSConstSchedule.DEFAULT_COLUMN_FORMAT_MONTH);
            columnFormat.setWeek(textDday);
            columnFormat.setDay(textddd);
            param.setColumnFormat(columnFormat);
            //ｲﾍﾞﾝﾄ
            if (!eventList.isEmpty()) {
                param.setEvents(eventList);
            }

            //日付名
            //日
            String textSun = gsMsg.getMessage("cmn.sunday");
            //月
            String textMon = gsMsg.getMessage("cmn.Monday");
            //火
            String textTues = gsMsg.getMessage("cmn.tuesday");
            //水
            String textWednes = gsMsg.getMessage("cmn.wednesday");
            //木
            String textThurs = gsMsg.getMessage("cmn.thursday");
            //金
            String textFri = gsMsg.getMessage("cmn.friday");
            //土
            String textSatur = gsMsg.getMessage("cmn.saturday");
            String[] defaultDayNamesShort =  new String[] {textSun, textMon, textTues,
                    textWednes, textThurs, textFri, textSatur};
            ArrayList<String> dayNameList = new ArrayList<String>();
            for (String dayName : defaultDayNamesShort) {
                dayNameList.add(dayName);
            }
            param.setDayNamesShort(dayNameList);

            //六曜表示/非表示を取得
            CommonBiz cmnBiz = new CommonBiz();
            boolean rokuyoDspFlg = cmnBiz.getRokuyoDspKbn(con, sessionUsrSid);
            ArrayList<String> rokuyoList = new ArrayList<String>();
            for (int i = 0; i < 7; i++) {
                if (rokuyoDspFlg) {
                    int rokuyoKbn = cmnBiz.setRkyKbn(cmnBiz.getRokuyou(frDate));
                    switch (rokuyoKbn) {
                        case GSConst.RKY_KBN_SENSHOU:
                            rokuyoList.add("kojinHeader_rokuyou-senshou");
                            break;
                        case GSConst.RKY_KBN_TOMOBIKI:
                            rokuyoList.add("kojinHeader_rokuyou-tomobiki");
                            break;
                        case GSConst.RKY_KBN_SENBU:
                            rokuyoList.add("kojinHeader_rokuyou-senbu");
                            break;
                        case GSConst.RKY_KBN_BUTSUMETSU:
                            rokuyoList.add("kojinHeader_rokuyou-butsumetsu");
                            break;
                        case GSConst.RKY_KBN_TAIAN:
                            rokuyoList.add("kojinHeader_rokuyou-taian");
                            break;
                        case GSConst.RKY_KBN_SHAKKOU:
                            rokuyoList.add("kojinHeader_rokuyou-shakkou");
                            break;
                        default:
                    }
                    frDate.addDay(1);
                } else {
                    rokuyoList.add("");
                }
            }
            param.setRokuyouList(rokuyoList);

            /** ダイアログ設定*/
            final String dialogSet__ = "{ autoOpen: true,"
                                     + "bgiframe: true,"
                                     + "resizable: false,"
                                     + "height: 180,"
                                     + "modal: true,"
                                     + "overlay: {"
                                     + "backgroundColor: '#000000',"
                                     + "opacity: 0.5"
                                     + "},";
            /** ダイアログ設定(施設予約解除)*/
            final String dialogSet2__ = "{ autoOpen: true,"
                                     + "bgiframe: true,"
                                     + "resizable: false,"
                                     +  "height: 200,"
                                     +  "modal: true,"
                                     +  "overlay: {"
                                     +  "backgroundColor: '#000000',"
                                     +  "opacity: 0.5"
                                     +  "},";
            /** drop登録処理*/
            final String dropDoevent__ = "if (!event.allDay) {"
                                       + "doEvent(event.uid, event.ukbn,"
                                       + " event.id, dayDelta, minuteDelta, 0);"
                                       + "} else {"
                                       + "doEventAllDay(event.id);"
                                       + "}";
            /** resize登録処理*/
            final String resizeDoevent__ = "if (!event.allDay) {"
                                         + "doEvent(event.uid, event.ukbn,"
                                         + " event.id, dayDelta, minuteDelta, 1);"
                                         + "} else {"
                                         + "doEventAllDay(event.id);"
                                         + "}";

            //select
            if (editable) {
                param.setSelect("function(startDate, endDate, allDay, jsEvent, view ) {"
                        + "var YYYY = startDate.getFullYear();"
                        + "var MM = startDate.getMonth() + 1;"
                        + "var DD = startDate.getDate();"
                        + "var hh = startDate.getHours();"
                        + "var mm = startDate.getMinutes();"
                        + "if (MM < 10) { MM = \'0\' + MM; };"
                        + "if (DD < 10) { DD = \'0\' + DD; };"
                        + "document.forms[0].CMD.value='add';"
                        + "document.forms[0].cmd.value='add';"
                        + "document.forms[0].sch040ToYear.value=endDate.getFullYear();"
                        + "document.forms[0].sch040ToMonth.value=endDate.getMonth() + 1;"
                        + "document.forms[0].sch040ToDay.value=endDate.getDate();"
                        + "if (!allDay) {"
                        +   "document.forms[0].sch040FrHour.value=hh;"
                        +   "document.forms[0].sch040FrMin.value=mm;"
                        +   "document.forms[0].sch040ToHour.value=endDate.getHours();"
                        +   "document.forms[0].sch040ToMin.value=endDate.getMinutes();"
                        + "} else {"
                        +   "document.forms[0].sch040TimeKbn.value=1;"
                        + "}"
                        + "document.forms[0].sch010SelectDate.value=YYYY + '' + MM + '' + DD;"
                        + "document.forms[0].submit();"
                        + "return false;"
                        + "}");
                //dayClick
                param.setDayClick("function(date, allDay, jsEvent, view) {"
                        //+ "location.href=" + GSConstSchedule.SELECT_URL + ";"
                        + "var YYYY = date.getFullYear();"
                        + "var MM = date.getMonth() + 1;"
                        + "var DD = date.getDate();"
                        + "var hh = date.getHours();"
                        + "var mm = date.getMinutes();"
                        + "if (MM < 10) { MM = \"0\" + MM; };"
                        + "if (DD < 10) { DD = \"0\" + DD; };"
                        + "document.forms[0].CMD.value='add';"
                        + "document.forms[0].cmd.value='add';"
                        + "if (!allDay) {"
                        +   "var afDate ="
                        +   " new Date(date.getTime()"
//                        + " + (1000 * 60 * " + adminConf.getSadHourDiv() + "));"
                        + " + (1000 * 60 * 30));"
                        +   "document.forms[0].sch040FrHour.value=hh;"
                        +   "document.forms[0].sch040FrMin.value=mm;"
                        +   "document.forms[0].sch040ToYear.value=afDate.getFullYear();"
                        +   "document.forms[0].sch040ToMonth.value=afDate.getMonth() + 1;"
                        +   "document.forms[0].sch040ToDay.value=afDate.getDate();"
                        +   "document.forms[0].sch040ToHour.value=afDate.getHours();"
                        +   "document.forms[0].sch040ToMin.value=afDate.getMinutes();"
                        + "} else {"
                        +   "document.forms[0].sch040TimeKbn.value=1;"
                        + "}"
                        + "document.forms[0].sch010SelectDate.value=YYYY + '' + MM + '' + DD;"
                        + "document.forms[0].submit();"
                        + "return false;"
                        + "}");

                param.setEventDrop("function(event,dayDelta,minuteDelta,allDay,revertFunc) {"
                        + "document.forms[0].cmd.value='drop';"
                        + "document.forms[0].CMD.value='drop';"
                        + "document.forms[0].sch010SelectUsrSid.value=event.uid;"
                        + "document.forms[0].sch010SelectUsrKbn.value=event.ukbn;"
                        + "document.forms[0].sch010SchSid.value=event.id;"
                        + "document.forms[0].sch200DayDelta.value=dayDelta;"
                        + "document.forms[0].sch200MinuteDelta.value=minuteDelta;"
                        + "if (allDay) {"
                           //時間指定なし
                        +   "document.forms[0].sch200EventPosition.value="
                        +   GSConstSchedule.DSP_ALL_DAY + ";"
                        +   "if (event.rsvFlg) {"
                            //時間指定なし　施設予約あり
                        +     "$('#dialog').dialog("
                        +       dialogSet2__
                        +       "buttons: {"
                        +         gsMsg.getMessage("mobile.13") + ": function() {"
                        +           "$(this).dialog('close');"
                                  //時間指定なし　施設予約あり　解除する
                        +           "if (event.svUsrFlg) {"
                                    //時間指定なし　施設予約あり　解除する　同時登録ユーザあり
                        +             "$('#dialog2').dialog("
                        +               dialogSet__
                        +               "buttons: {"
                        +                 gsMsg.getMessage("mobile.13") + ": function() {"
                        +                   "$(this).dialog('close');"
                                          //時間指定なし　施設予約あり　解除する　同時登録ユーザあり 　変更する
                        +                   "document.forms[0].sch200BatchRef.value=1;"
                        +                   dropDoevent__
                        +                 "},"
                        +                 gsMsg.getMessage("mobile.14") + ": function() {"
                        +                   "$(this).dialog('close');"
                                          //時間指定なし　施設予約あり　解除する　同時登録ユーザあり 　変更しない
                        +                   "document.forms[0].sch200BatchRef.value=0;"
                        +                   dropDoevent__
                        +                 "}"
                        +               "}"
                        +             "});"
                        +           "} else {"
                                    //時間指定なし　施設予約あり　解除する　同時登録ユーザなし
                        +             dropDoevent__
                        +           "}"
                        +         "},"
                        +         gsMsg.getMessage("mobile.14") + ": function() {"
                                  //時間指定なし　施設予約あり　解除しない
                        +           "revertFunc();"
                        +           "$(this).dialog('close');"
                        +           "return false;"
                        +         "}"
                        +       "}"
                        +     "});"
                        +   "} else {"
                            //時間指定なし 施設予約なし
                        +     "if (event.svUsrFlg) {"
                              //時間指定なし　施設予約なし　同時登録ユーザあり
                        +       "$('#dialog2').dialog("
                        +         dialogSet__
                        +         "buttons: {"
                        +           gsMsg.getMessage("mobile.13") + ": function() {"
                        +             "$(this).dialog('close');"
                                    //時間指定なし　施設予約なし　同時登録ユーザあり 　変更する
                        +             "document.forms[0].sch200BatchRef.value=1;"
                        +             dropDoevent__
                        +           "},"
                        +           gsMsg.getMessage("mobile.14") + ": function() {"
                        +             "$(this).dialog('close');"
                                    //時間指定なし　施設予約なし　同時登録ユーザあり 　変更しない
                        +             "document.forms[0].sch200BatchRef.value=0;"
                        +             dropDoevent__
                        +           "}"
                        +         "}"
                        +       "});"
                        +     "} else {"
                              //時間指定なし　施設予約なし　同時登録ユーザなし
                        +       dropDoevent__
                        +     "}"
                        +   "}"
                        + "}else{"
                          //時間指定あり
                        +   "document.forms[0].sch200EventPosition.value="
                        +   GSConstSchedule.DSP_REGULAR_TIME + ";"
                        +   "if (event.svUsrFlg) {"
                             //時間指定あり　同時登録あり
                        +     "$('#dialog2').dialog("
                        +       dialogSet__
                        +       "buttons: {"
                        +         gsMsg.getMessage("mobile.13") + ": function() {"
                        +           "$(this).dialog('close');"
                                  //時間指定あり　同時登録あり 同時登録ユーザ登録する
                        +           "document.forms[0].sch200BatchRef.value=1;"
                        +           "if (event.rsvFlg) {"
                                    //時間指定あり  同時登録あり　同時登録ユーザ登録する  施設予約あり
                        +               "$('#dialog3').dialog("
                        +               dialogSet__
                        +               "buttons: {"
                        +                 gsMsg.getMessage("mobile.13") + ": function() {"
                        +                   "$(this).dialog('close');"
                                          //時間指定あり  同時登録あり　同時登録ユーザ登録する  施設予約あり　施設予約変更する
                        +                   "document.forms[0].sch200ResBatchRef.value=1;"
                        +                   dropDoevent__
                        +                  "},"
                        +                 gsMsg.getMessage("mobile.14") + ": function() {"
                        +                   "$(this).dialog('close');"
                                          //時間指定あり  同時登録あり　同時登録ユーザ登録する  施設予約あり　施設予約変更しない
                        +                    "document.forms[0].sch200ResBatchRef.value=0;"
                        +                   dropDoevent__
                        +                 "}"
                        +               "}"
                        +            "});"
                        +           "}　else {"
                                    //時間指定あり　同時登録あり 同時登録ユーザ登録する  施設予約なし
                        +              dropDoevent__
                        +           "}"
                        +         "},"
                        +         gsMsg.getMessage("mobile.14") + ": function() {"
                        +           "$(this).dialog('close');"
                                  //時間指定あり　同時登録あり 同時登録ユーザ登録しない
                        +           "document.forms[0].sch200BatchRef.value=0;"
                        +           "if (event.rsvFlg) {"
                                    //時間指定あり　同時登録あり  同時登録ユーザ登録しない 施設予約あり
                        +               "$('#dialog3').dialog("
                        +               dialogSet__
                        +               "buttons: {"
                        +                 gsMsg.getMessage("mobile.13") + ": function() {"
                        +                   "$(this).dialog('close');"
                                          //時間指定あり　同時登録あり  同時登録ユーザ登録しない 施設予約あり　施設予約変更する
                        +                   "document.forms[0].sch200ResBatchRef.value=1;"
                        +                   dropDoevent__
                        +                 "},"
                        +                 gsMsg.getMessage("mobile.14") + ": function() {"
                        +                   "$(this).dialog('close');"
                                          //時間指定あり　同時登録あり  同時登録ユーザ登録しない 施設予約あり　施設予約変更しない
                        +                    "document.forms[0].sch200ResBatchRef.value=0;"
                        +                   dropDoevent__
                        +                 "}"
                        +               "}"
                        +            "});"
                        +           "}　else {"
                                    //時間指定あり　同時登録あり  同時登録ユーザ登録しない 施設予約なし
                        +              dropDoevent__
                        +           "}"
                        +         "}"
                        +       "}"
                        +    "});"
                        +   "} else {"
                             //時間指定あり　同時登録なし
                        +      "if (event.rsvFlg) {"
                                //時間指定あり　同時登録なし  施設予約あり
                        +         "$('#dialog3').dialog("
                        +         dialogSet__
                        +          "buttons: {"
                        +            gsMsg.getMessage("mobile.13") + ": function() {"
                        +              "$(this).dialog('close');"
                                    //時間指定あり　同時登録なし  施設予約あり　施設予約変更する
                        +              "document.forms[0].sch200ResBatchRef.value=1;"
                        +              dropDoevent__
                        +             "},"
                        +            gsMsg.getMessage("mobile.14") + ": function() {"
                        +              "$(this).dialog('close');"
                                    //時間指定あり　同時登録なし  施設予約あり　施設予約変更しない
                        +               "document.forms[0].sch200ResBatchRef.value=0;"
                        +              dropDoevent__
                        +            "}"
                        +          "}"
                        +       "});"
                        +      "}　else {"
                               //時間指定あり　同時登録なし　施設予約なし
                        +         dropDoevent__
                        +      "}"
                        +   "}"
                        + "}"
                        + "return false;"
                        + "}");

                //eventResize
                param.setEventResize("function(event,dayDelta,minuteDelta,revertFunc) {"
                        + "document.forms[0].cmd.value='resize';"
                        + "document.forms[0].CMD.value='resize';"
                        + "document.forms[0].sch010SelectUsrSid.value=event.uid;"
                        + "document.forms[0].sch010SelectUsrKbn.value=event.ukbn;"
                        + "document.forms[0].sch010SchSid.value=event.id;"
                        + "document.forms[0].sch200DayDelta.value=dayDelta;"
                        + "document.forms[0].sch200MinuteDelta.value=minuteDelta;"
                        + "if (event.allDay) {"
                           //時間指定なし
                        +   "document.forms[0].sch200EventPosition.value="
                        +   GSConstSchedule.DSP_ALL_DAY + ";"
                        +   "if (event.svUsrFlg) {"
                               //時間指定なし　同時登録ユーザチェック 同時登録あり
                        +     "$('#dialog2').dialog("
                        +       dialogSet__
                        +       "buttons: {"
                        +         gsMsg.getMessage("mobile.13") + ": function() {"
                        +           "$(this).dialog('close');"
                                  //時間指定なし　同時登録ユーザ登録する
                        +           "document.forms[0].sch200BatchRef.value=1;"
                        +           resizeDoevent__
                        +         "},"
                        +         gsMsg.getMessage("mobile.14") + ": function() {"
                        +           "$(this).dialog('close');"
                                  //時間指定なし　同時登録ユーザ登録しない
                        +           "document.forms[0].sch200BatchRef.value=0;"
                        +           resizeDoevent__
                        +         "}"
                        +       "}"
                        +    "});"
                        +   "}　else {"
                            //時間指定なし　同時登録ユーザチェック 同時登録なし
                        +      resizeDoevent__
                        +   "}"
                        + "} else {"
                           //時間指定あり
                        +   "document.forms[0].sch200EventPosition.value="
                        +   GSConstSchedule.DSP_REGULAR_TIME + ";"
                        +   "if (event.svUsrFlg) {"
                             //時間指定あり　同時登録あり
                        +     "$('#dialog2').dialog("
                        +       dialogSet__
                        +       "buttons: {"
                        +         gsMsg.getMessage("mobile.13") + ": function() {"
                        +           "$(this).dialog('close');"
                                  //時間指定あり　同時登録あり 同時登録ユーザ登録する
                        +           "document.forms[0].sch200BatchRef.value=1;"
                        +           "if (event.rsvFlg) {"
                                    //時間指定あり  同時登録あり　同時登録ユーザ登録する  施設予約あり
                        +               "$('#dialog3').dialog("
                        +               dialogSet__
                        +               "buttons: {"
                        +                 gsMsg.getMessage("mobile.13") + ": function() {"
                        +                   "$(this).dialog('close');"
                                          //時間指定あり  同時登録あり　同時登録ユーザ登録する  施設予約あり　施設予約変更する
                        +                   "document.forms[0].sch200ResBatchRef.value=1;"
                        +                   resizeDoevent__
                        +                  "},"
                        +                 gsMsg.getMessage("mobile.14") + ": function() {"
                        +                   "$(this).dialog('close');"
                                          //時間指定あり  同時登録あり　同時登録ユーザ登録する  施設予約あり　施設予約変更しない
                        +                    "document.forms[0].sch200ResBatchRef.value=0;"
                        +                   resizeDoevent__
                        +                 "}"
                        +               "}"
                        +            "});"
                        +           "}　else {"
                                    //時間指定あり　同時登録あり 同時登録ユーザ登録する  施設予約なし
                        +              resizeDoevent__
                        +           "}"

                        +         "},"
                        +         gsMsg.getMessage("mobile.14") + ": function() {"
                        +           "$(this).dialog('close');"
                                  //時間指定あり　同時登録あり 同時登録ユーザ登録しない
                        +           "document.forms[0].sch200BatchRef.value=0;"
                        +           "if (event.rsvFlg) {"
                                    //時間指定あり　同時登録あり  同時登録ユーザ登録しない 施設予約あり
                        +               "$('#dialog3').dialog("
                        +               dialogSet__
                        +               "buttons: {"
                        +                 gsMsg.getMessage("mobile.13") + ": function() {"
                        +                   "$(this).dialog('close');"
                                          //時間指定あり　同時登録あり  同時登録ユーザ登録しない 施設予約あり　施設予約変更する
                        +                   "document.forms[0].sch200ResBatchRef.value=1;"
                        +                   resizeDoevent__
                        +                  "},"
                        +                 gsMsg.getMessage("mobile.14") + ": function() {"
                        +                   "$(this).dialog('close');"
                                          //時間指定あり　同時登録あり  同時登録ユーザ登録しない 施設予約あり　施設予約変更しない
                        +                    "document.forms[0].sch200ResBatchRef.value=0;"
                        +                   resizeDoevent__
                        +                 "}"
                        +               "}"
                        +            "});"
                        +           "}　else {"
                                    //時間指定あり　同時登録あり  同時登録ユーザ登録しない 施設予約なし
                        +              resizeDoevent__
                        +           "}"
                        +         "}"
                        +       "}"
                        +    "});"
                        +   "} else {"
                             //時間指定あり　同時登録なし
                        +      "if (event.rsvFlg) {"
                                //時間指定あり　同時登録なし  施設予約あり
                        +         "$('#dialog3').dialog("
                        +         dialogSet__
                        +          "buttons: {"
                        +            gsMsg.getMessage("mobile.13") + ": function() {"
                        +              "$(this).dialog('close');"
                                    //時間指定あり　同時登録なし  施設予約あり　施設予約変更する
                        +              "document.forms[0].sch200ResBatchRef.value=1;"
                        +              resizeDoevent__
                        +             "},"
                        +            gsMsg.getMessage("mobile.14") + ": function() {"
                        +              "$(this).dialog('close');"
                                    //時間指定あり　同時登録なし  施設予約あり　施設予約変更しない
                        +               "document.forms[0].sch200ResBatchRef.value=0;"
                        +              resizeDoevent__
                        +            "}"
                        +          "}"
                        +       "});"
                        +      "}　else {"
                               //時間指定あり　同時登録なし　施設予約なし
                        +         resizeDoevent__
                        +      "}"
                        +   "}"
                        + "}"
                        + "return false;"
                        + "}");

            } else {

                param.setSelect("function(startDate, endDate, allDay, jsEvent, view ) {"
                        + "document.forms[0].CMD.value='';"
                        + "document.forms[0].sch200Cancel.value=1;"
                        + "document.forms[0].submit();"
//                        + "loadJson();"
                        + "return false;"
                        + "}");
                //dayClick
                param.setDayClick("function(date, allDay, jsEvent, view) {"
                        + "document.forms[0].CMD.value='';"
                        + "document.forms[0].sch200Cancel.value=1;"
                        + "document.forms[0].submit();"
                        + "return false;"
                        + "}");
                param.setEventDrop("function(event,dayDelta,minuteDelta,allDay,revertFunc) {"
                        + "document.forms[0].CMD.value='';"
                        + "document.forms[0].sch200Cancel.value=1;"
                        + "document.forms[0].submit();"
                        + "return false;"
                        + "}");

                //eventResize
                param.setEventResize("function(event,dayDelta,minuteDelta,revertFunc) {"
                        + "document.forms[0].CMD.value='';"
                        + "document.forms[0].sch200Cancel.value=1;"
                        + "document.forms[0].submit();"
                        + "return false;"
                        + "}");

            }

            //eventClick
            param.setEventClick("function(calEvent, jsEvent, view) {"
                    + "if (calEvent.editable) {"
                    +   "document.forms[0].cmd.value='edit';"
                    +   "document.forms[0].CMD.value='edit';"
                    +   "document.forms[0].sch010SelectDate.value=calEvent.ymd;"
                    +   "document.forms[0].sch010SelectUsrSid.value=calEvent.uid;"
                    +   "document.forms[0].sch010SelectUsrKbn.value=calEvent.ukbn;"
                    +   "document.forms[0].sch010SchSid.value=calEvent.id;"
                    +   "document.forms[0].submit();"
                    +   "return false;"
                    +  "}"
                    + "}");
            //eventDrop


            JSONObject jsonParam = JSONObject.fromObject(param);
            paramMdl.setSch200JsonEvent(jsonParam);
            writeSuccess = true;
        } catch (Exception e) {
            log__.error("スケジュールデータの取得に失敗", e);
        } finally {
            PrintWriter writer = null;
            try {
                res.setContentType("text/txt; charset=UTF-8");
                writer = res.getWriter();
                if (writeSuccess) {
                    writer.println(paramMdl.getSch200JsonEvent());
                } else {
                    writer.println("{\"errors\" : \"1\"}");
                }
                writer.flush();
            } catch (Exception e) {
                log__.debug("書き込みに失敗。");
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }
    /**
    * <br>JSONデータを取得します。
    * @param paramMdl Sch200ParamModel
    * @param schMap スケジュールデータ
    * @param monthMdl 月間データ
    * @param con コネクション
    * @return スケジュールデータ
    * @throws SQLException sql実行例外
    */
    private LinkedHashMap<String, Sch200Event> __getJsonData(Sch200ParamModel paramMdl,
            LinkedHashMap<String, Sch200Event> schMap,
            Sch200WeekOfModel monthMdl,
            Connection con) throws SQLException {

        if (!monthMdl.getSch200SchList().isEmpty()) {
            //管理者設定を取得
            SchCommonBiz biz = new SchCommonBiz(reqMdl__);
            SchAdmConfModel adminConf = biz.getAdmConfModel(con);
            for (Sch200DayOfModel dayMdl : monthMdl.getSch200SchList()) {
                schMap = __getSchJsonData(paramMdl, schMap, dayMdl, con, adminConf);
            }
        }
        return schMap;
    }
    /**
    * <br>JSON形式のスケジュールデータを作成します。
    * @param paramMdl Sch200ParamModel
    * @param schMap スケジュールデータ
    * @param dayMdl 日間データ
    * @param con コネクション
    * @param adminConf 管理者設定
    * @return スケジュールデータ
    * @throws SQLException sql実行例外
    */
    private LinkedHashMap<String, Sch200Event> __getSchJsonData(Sch200ParamModel paramMdl,
            LinkedHashMap<String, Sch200Event> schMap,
            Sch200DayOfModel dayMdl,
            Connection con,
            SchAdmConfModel adminConf) throws SQLException {
        if (!dayMdl.getSch200DataList().isEmpty()) {
           for (Sch200SimpleModel schMdl : dayMdl.getSch200DataList()) {
               int u_public = schMdl.getPublic();
               int u_grpEdKbn = schMdl.getGrpEdKbn();
               int u_kjnEdKbn = schMdl.getKjnEdKbn();

               Sch200Event event = new Sch200Event();
               //公開
               if (u_public == GSConstSchedule.DSP_PUBLIC
                       || u_public == GSConstSchedule.DSP_BELONG_GROUP
                       || u_kjnEdKbn == GSConstSchedule.EDIT_CONF_OWN
                       || u_grpEdKbn == GSConstSchedule.EDIT_CONF_GROUP
                       || u_public == GSConstSchedule.DSP_YOTEIARI
                       || u_public == GSConstSchedule.DSP_TITLE) {
                   //スケジュールSID
                   int schSid = 0;
                   //時間設定
                   int timeKb = 0;
                   boolean timeKbn = false;
                   if (schMdl.getValueStr() != null) {
                       schSid = schMdl.getSchSid();
                       timeKb = schMdl.getTimeKbn();
                       schMdl.getUserSid();
                       schMdl.getUserKbn();
                   } else {
                       schSid = schMdl.getSchSid();
                       timeKb = schMdl.getTimeKbn();
                       schMdl.getUserSid();
                       schMdl.getUserKbn();
                   }
                   if (timeKb != 0) {
                       timeKbn = true;
                   }

                   //css class名
                   String[] className = new String[] {
                           "cl_fontSchTitleBlue",
                           "bgc_fontSchTitleBlue"
                           };
                   if (schMdl.getBgColor() == 2) {
                       className = new String[] {
                               "cl_fontSchTitleRed",
                               "bgc_fontSchTitleRed"
                               };
                   } else if (schMdl.getBgColor() == 3) {
                       className = new String[] {
                               "cl_fontSchTitleGreen",
                               "bgc_fontSchTitleGreen"
                               };
                   } else if (schMdl.getBgColor() == 4) {
                       className = new String[] {
                               "cl_fontSchTitleYellow",
                               "bgc_fontSchTitleYellow"
                              };
                   } else if (schMdl.getBgColor() == 5) {
                       className = new String[] {
                               "cl_fontSchTitleBlack",
                               "bgc_fontSchTitleBlack"
                              };
                   } else if (schMdl.getBgColor() == 6) {
                       className = new String[] {
                               "cl_fontSchTitleNavy",
                               "bgc_fontSchTitleNavy"
                              };
                   } else if (schMdl.getBgColor() == 7) {
                       className = new String[] {
                               "cl_fontSchTitleWine",
                               "bgc_fontSchTitleWine"
                              };
                   } else if (schMdl.getBgColor() == 8) {
                       className = new String[] {
                               "cl_fontSchTitleCien",
                               "bgc_fontSchTitleCien"
                              };
                   } else if (schMdl.getBgColor() == 9) {
                       className = new String[] {
                               "cl_fontSchTitleGray",
                               "bgc_fontSchTitleGray"
                              };
                   } else if (schMdl.getBgColor() == 10) {
                       className = new String[] {
                               "cl_fontSchTitleMarine",
                               "bgc_fontSchTitleMarine"
                              };
                   }

                   //時間
                   String time = null;
                   UDate start = null;
                   String startStr = null;
                   UDate end = null;
                   String endStr = null;
                   if (schMdl.getTime() != null) {
                       time = schMdl.getTime();
                   }
                   if (schMdl.getFromDate() != null) {
                       start = schMdl.getFromDate();
                   }
                   if (schMdl.getToDate() != null) {
                       end = schMdl.getToDate();
                   }
                   if (time != null) {
                       startStr = String.valueOf(start.getTimeStamp2());
                       endStr = String.valueOf(end.getTimeStamp2());
                   }
                   //タイトル
                   String title = null;
                   title = schMdl.getTitle();

                   //編集権限チェック(公開=予定あり or タイトルのみ公開以外はクリックできるように)
                   boolean editFlg = true;
                   if (schMdl.getPublic() == GSConstSchedule.DSP_YOTEIARI
                   || schMdl.getPublic() == GSConstSchedule.DSP_TITLE) {
                       editFlg = __isAllEditOkEx(schSid, reqMdl__, con);
                        if (!editFlg) {
                            //予定ありの場合、タイトル色を規定の色に変更
                            className = new String[] {
                                    "",
                                    "bgc_fontSchTitleBlack"
                                   };
                        }
                   }

                   //同時登録ユーザチェック
                   boolean svUsrFlg = __getSaveUsr(schSid, adminConf, con);
                   //施設予約チェック
                   boolean rsvFlg = true;
                   rsvFlg = __getSaveReserve(schSid, con);

                   event.setId(schSid);
                   event.setAllDay(timeKbn);
                   event.setStart(startStr);
                   event.setEnd(endStr);
//                   event.setTextColor(textColor);
//                   event.setBackgroundColor(bgColor);
                   event.setClassName(className);
                   event.setTitle(title);
                   event.setEditable(editFlg);
                   event.setUid(schMdl.getUserSid());
                   event.setUkbn(schMdl.getUserKbn());
                   event.setYmd(dayMdl.getSchDate());
                   event.setRsvFlg(rsvFlg);
                   event.setSvUsrFlg(svUsrFlg);
                   event.setPublicIconFlg(schMdl.isPublicIconFlg());
                   if (!schMap.containsKey(String.valueOf(schSid))) {
                       schMap.put(String.valueOf(schSid), event);
                   } else {
                       //またぎスケジュール
                       Sch200Event data = new Sch200Event();
                       data = schMap.get(String.valueOf(schSid));
                       //data.setAllDay(true);
                       schMap.put(String.valueOf(schSid), data);
                   }
               } else {
                   //非公開
                   String time = null;
                   if (schMdl.getTime() != null) {
                       time = schMdl.getTime();
                   }
                   //ﾀｲﾄﾙ
                   String title = null;
                   title = schMdl.getTitle();
                   log__.debug("非公開データ＝" + time + "," + title);
               }
           }
        }
        return schMap;
    }
    /**
     * <br>[機  能] スケジュールを更新します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションRoot
     * @param plconf プラグイン設定
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param oldMdl 編集前データ
     * @return 更新件数
     * @throws Exception SQL実行時例外
     */
    public int updateScheduleDate(
            Sch200ParamModel paramMdl,
            int userSid,
            String appRootPath,
            PluginConfig plconf,
            boolean smailPluginUseFlg,
            ScheduleSearchModel oldMdl) throws Exception {

        //管理者設定を取得
        SchCommonBiz cmnBiz = new SchCommonBiz(con__, reqMdl__);
        SchAdmConfModel adminConf = cmnBiz.getAdmConfModel(con__);

        int cnt = 0;
        String scdSid = paramMdl.getSch010SchSid();

        //時間設定
        if (paramMdl.getSch200EventPosition() == 0) {
            paramMdl.setSch200TimeKbn(String.valueOf(GSConstSchedule.TIME_NOT_EXIST));
        } else {
            paramMdl.setSch200TimeKbn(String.valueOf(GSConstSchedule.TIME_EXIST));
        }

        SchDataModel scdMdl = new SchDataModel();
        UDate now = new UDate();

        //開始時間
        UDate frDate = oldMdl.getScdFrDate();
        if (paramMdl.getCmd().equals("drop")) {
            if (paramMdl.getSch200DayDelta() != 0) {
                frDate.addDay(paramMdl.getSch200DayDelta());
            }
            if (paramMdl.getSch200MinuteDelta() != 0) {
                frDate.addMinute(paramMdl.getSch200MinuteDelta());
            }
        }
        paramMdl.setSch040FrYear(frDate.getStrYear());
        paramMdl.setSch040FrMonth(frDate.getStrMonth());
        paramMdl.setSch040FrDay(frDate.getStrDay());
        paramMdl.setSch040FrHour(frDate.getStrHour());
        paramMdl.setSch040FrMin(frDate.getStrMinute());
        //終了時間
        UDate toDate = oldMdl.getScdToDate();
        if ((paramMdl.getCmd().equals("drop") && oldMdl.getScdDaily() == 1)
                || (paramMdl.getCmd().equals("resize") && oldMdl.getScdDaily() == 1)) {
            //時間設定なし
            if (paramMdl.getSch200DayDelta() != 0) {
                toDate.addDay(paramMdl.getSch200DayDelta());
            }
            if (paramMdl.getCmd().equals("drop")
                    && (Integer.valueOf(
                            paramMdl.getSch200TimeKbn()) == GSConstSchedule.TIME_EXIST)) {
                //時間指定なしから時間してありへ移動
                toDate.setHour(frDate.getIntHour());
                toDate.setMinute(frDate.getIntMinute());
                toDate.addMinute(30);
            }
        } else {
            if (paramMdl.getSch200DayDelta() != 0) {
                toDate.addDay(paramMdl.getSch200DayDelta());
            }
            if (paramMdl.getSch200MinuteDelta() != 0) {
                toDate.addMinute(paramMdl.getSch200MinuteDelta());
            }
        }
        paramMdl.setSch040ToYear(toDate.getStrYear());
        paramMdl.setSch040ToMonth(toDate.getStrMonth());
        paramMdl.setSch040ToDay(toDate.getStrDay());
        paramMdl.setSch040ToHour(toDate.getStrHour());
        paramMdl.setSch040ToMin(toDate.getStrMinute());

        //施設予約SID取得
        __setSaveReserveForDb(paramMdl, con__);
        //編集権限、同時登録、施設予約エラーチェック
        ActionErrors errors = __validateCheck(paramMdl, frDate, toDate, con__, oldMdl);

        if (errors.size() > 0) {
            paramMdl.setSch200ActionErrors(errors);
            if (paramMdl.getSch200ErrorsRowCnt() != 0) {
                paramMdl.setSch200ActionErrorsCnt(
                        (errors.size() - 1) + paramMdl.getSch200ErrorsRowCnt());
            } else {
                paramMdl.setSch200ActionErrorsCnt(errors.size());
            }
            return cnt;
        }

        paramMdl.setSch200Title(oldMdl.getScdTitle());
        scdMdl.setScdSid(Integer.parseInt(scdSid));
        scdMdl.setScdFrDate(frDate);
        scdMdl.setScdToDate(toDate);
        scdMdl.setScdDaily(Integer.valueOf(paramMdl.getSch200TimeKbn()));

        scdMdl.setScdBgcolor(oldMdl.getScdBgcolor());
        scdMdl.setScdTitle(oldMdl.getScdTitle());
        scdMdl.setScdValue(oldMdl.getScdValue());
        scdMdl.setScdBiko(oldMdl.getScdBiko());
        scdMdl.setScdPublic(
                NullDefault.getInt(String.valueOf(oldMdl.getScdPublic()),
                        GSConstSchedule.DSP_PUBLIC));

        scdMdl.setScdAuid(userSid);
        scdMdl.setScdAdate(now);
        scdMdl.setScdEuid(userSid);
        scdMdl.setScdEdate(now);

        //編集区分
        scdMdl.setScdEdit(
                NullDefault.getInt(String.valueOf(oldMdl.getScdEdit()),
                        GSConstSchedule.EDIT_CONF_NONE));

        //リマインダー通知
        int selectUsrSid = oldMdl.getScdUsrSid();
        IPushServiceOperator psOpe = PushServiceOperator.getInstance(con__, reqMdl__.getDomain());

        /**対象ユーザ デフォルト設定 対象ユーザと同時登録ユーザを一括取得*/
        Map<Integer, SchPriPushModel> svUsersConfMap =
                cmnBiz.getUserPriConf(
                        Stream.concat(
                                Stream.of(
                                        Optional.ofNullable(paramMdl.getSv_users()
                                          ).orElse(new String[] {})
                                ), Stream.of(Optional.ofNullable((String) null).orElseGet(() -> {
                                      if (Objects.equals(paramMdl.getSch010SelectUsrKbn(),
                                              String.valueOf(GSConstSchedule.USER_KBN_GROUP))) {
                                        return "-1";
                                      }
                                     return paramMdl.getSch010SelectUsrSid();
                                   }))).toArray(String[]::new));

        SchRemindConfWriter.builder()
        .setDefConf(
                svUsersConfMap.get(selectUsrSid)
                )
        .setOldConf(
                SchPriPushModel.getInstance(oldMdl)
                )
        .setReminder(oldMdl.getScdReminder())
        .setRemindMode(SchEnumRemindMode.OTHERS)
        .setTargetGrp(oldMdl.getScdTargetGrp())
        .setTimeKbn(
                NullDefault.getInt(
                        paramMdl.getSch200TimeKbn(),
                        GSConstSchedule.TIME_EXIST)
                )
        .setPushUseable(
                psOpe.isUseable())
        .build().write(scdMdl);


        SchDataDao schDao = new SchDataDao(con__);
        //拡張登録SID
        int extSid = oldMdl.getSceSid();
        scdMdl.setSceSid(extSid);
        //スケジュール施設予約SID
        int resSid = oldMdl.getScdRsSid();
        scdMdl.setScdRsSid(resSid);
        String[] svReserves = null;
        if (scdMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
            svReserves = paramMdl.getSvReserveUsers();
        }

        int scdResSid = GSConstSchedule.DF_SCHGP_ID;

        if (paramMdl.getSch200BatchRef().equals("0")) {
            //同時登録反映無しの場合
            scdMdl.setScdGrpSid(GSConstSchedule.DF_SCHGP_ID);
            //施設予約へ反映する場合、新たに採番
            if (paramMdl.getSch200ResBatchRef().equals("1")) {
                if (svReserves != null && svReserves.length > 0) {
                    //スケジュール施設予約SID（施設予約有りの場合）
                    scdResSid = (int) cntCon__.getSaibanNumber(SaibanModel.SBNSID_SCHEDULE,
                            SaibanModel.SBNSID_SUB_SCH_RES, userSid);
                    scdMdl.setScdRsSid(scdResSid);
                    schDao.updateRsSid(resSid, scdResSid);
                }
            }
            //選択スケジュールを更新
            cnt = schDao.updateSchedule(scdMdl);
            //古いスケジュールに紐づく通知予定リストの削除
            SchPushListDao splDao = new SchPushListDao(con__);
            splDao.delete(Integer.parseInt(scdSid));
            //通知予定リストへの登録
            SchPriPushModel priMdl = new SchPriPushModel();
            priMdl.setUsrSid(Integer.parseInt(paramMdl.getSch010SelectUsrSid()));
            priMdl.setSccReminder(scdMdl.getScdReminder());
            cmnBiz.insertPushInfUser(scdMdl.getScdSid(), priMdl, frDate.cloneUDate());

            //会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴を更新
            __updateSchCompany(paramMdl, scdMdl.getScdUsrSid(),
                            scdMdl.getScdEdate(), scdMdl.getScdEuid(), con__, frDate, toDate);

            //ユーザSID
            String usrSid = paramMdl.getSch010SelectUsrSid();
            //URL取得
            String url = __createScheduleUrlDefo(GSConstSchedule.CMD_EDIT,
                                               String.valueOf(scdSid), usrSid,
                                               paramMdl);
            cmnBiz.sendPlgSmail(
                    con__, cntCon__, scdMdl, appRootPath, plconf, smailPluginUseFlg, url);
        } else {
            //同時登録ユーザへ反映更新
            //新スケジュールを登録
            __setSaveUsersForDb(paramMdl, oldMdl.getUsrInfList());
            scdMdl.setScdUsrSid(Integer.parseInt(paramMdl.getSch010SelectUsrSid()));
            scdMdl.setScdUsrKbn(Integer.parseInt(paramMdl.getSch010SelectUsrKbn()));

            ISchRegister reg =  __deleteInsertScheduleDate(paramMdl,
                    appRootPath, plconf, smailPluginUseFlg, adminConf,
                    scdMdl,
                    psOpe);
            scdResSid =
                    reg.getScdGrpsList().stream()
                        .findAny()
                        .map(SchDataGroupModel::getScdResSid)
                        .orElse(GSConstSchedule.DF_SCHGP_ID);

        }

        int rsrSid = -1;
        //施設予約への更新判定 時間指定無しの場合は更新
        if (paramMdl.getSch200ResBatchRef().equals("1")
                || scdMdl.getScdDaily() == GSConstSchedule.TIME_NOT_EXIST) {

            //施設拡張取得
            RsvSisRyrkDao ryrkDao = new RsvSisRyrkDao(con__);
            RsvSisRyrkModel ryrkMdl = ryrkDao.selectFromScdSid(Integer.parseInt(scdSid));
            if (ryrkMdl != null) {
                rsrSid = ryrkMdl.getRsrRsid();
            }
            //施設予約を登録
            int yoyakuSid = -1;
            RsvSisYrkDao yrkDao = new RsvSisYrkDao(con__);
            RsvSisYrkModel rsvModel = null;
            
            //公開対象、公開対象削除一覧を取得
            RsvDataPubDao rdpDao = new RsvDataPubDao(con__);
            List<RsvDataPubModel> rdpList = new ArrayList<RsvDataPubModel>();
            List<RsvDataPubModel> insertPubList = new ArrayList<RsvDataPubModel>();
            ArrayList<Integer> deleteSidList = new ArrayList<Integer>();
            if (resSid > -1) {
                deleteSidList = yrkDao.getScheduleRserveSids(resSid);
                if (!deleteSidList.isEmpty()) {
                    rdpList = rdpDao.select(deleteSidList.get(0));
                }
            }
            RsvDataPubModel rdpMdl = null;
            
            if (svReserves != null) {
                RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
                for (int i = 0; i < svReserves.length; i++) {
                    yoyakuSid = (int) cntCon__.getSaibanNumber(
                            GSConstReserve.SBNSID_RESERVE,
                            GSConstReserve.SBNSID_SUB_YOYAKU,
                            userSid);
                    RsvSisYrkModel yrkParam = new RsvSisYrkModel();
                    yrkParam.setRsySid(yoyakuSid);
                    yrkParam.setRsdSid(Integer.parseInt(svReserves[i]));
                    String moku = NullDefault.getString(scdMdl.getScdTitle(), "");
                    yrkParam.setRsyMok(moku);
                    yrkParam.setRsyFrDate(frDate);
                    yrkParam.setRsyToDate(toDate);
                    yrkParam.setRsyBiko(NullDefault.getString(scdMdl.getScdValue(), ""));
                    yrkParam.setRsyAuid(userSid);
                    yrkParam.setRsyAdate(now);
                    yrkParam.setRsyEuid(userSid);
                    yrkParam.setRsyEdate(now);
                    yrkParam.setScdRsSid(scdResSid);

                    //旧施設予約情報取得
                    rsvModel = yrkDao.select(Integer.parseInt(svReserves[i]), resSid);
                    if (rsvModel != null) {
                        yrkParam.setRsyEdit(rsvModel.getRsyEdit());
                        yrkParam.setRsyPublic(rsvModel.getRsyPublic());
                    }
                    
                    //公開対象登録用リストに追加
                    if (yrkParam.getRsyPublic() == GSConstReserve.PUBLIC_KBN_USRGRP) {
                        for (RsvDataPubModel mdl : rdpList) {
                            rdpMdl = new RsvDataPubModel();
                            rdpMdl.setRsySid(yoyakuSid);
                            rdpMdl.setRdpType(mdl.getRdpType());
                            rdpMdl.setRdpPsid(mdl.getRdpPsid());
                            insertPubList.add(rdpMdl);
                        }
                    }

                    //施設拡張SID
                    yrkParam.setRsrRsid(rsrSid);

                    //承認状況
                    rsvCmnBiz.setSisYrkApprData(con__,  yrkParam.getRsdSid(), yrkParam, userSid);
                    yrkDao.insert(yrkParam);

                    //施設予約区分別情報を登録（スケジュールからの場合は全て初期値）
                    RsvSisDataDao dataDao = new RsvSisDataDao(con__);
                    Rsv070Model mdl = dataDao.getPopUpSisetuData(Integer.parseInt(svReserves[i]));
                    if (mdl != null) {
                        if (RsvCommonBiz.isRskKbnRegCheck(mdl.getRskSid())) {
                            RsvCommonBiz rsvBiz = new RsvCommonBiz();
                            RsvSisKyrkModel kyrkMdl =
                                    rsvBiz.getSisKbnInitData(
                                            con__, reqMdl__, mdl.getRskSid(), appRootPath);

                            kyrkMdl.setRsySid(yoyakuSid);
                            kyrkMdl.setRkyAuid(userSid);
                            kyrkMdl.setRkyAdate(now);
                            kyrkMdl.setRkyEuid(userSid);
                            kyrkMdl.setRkyEdate(now);

                            RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con__);
                            kyrkDao.insert(kyrkMdl);
                        }
                    }
                }
                //公開対象の登録
                rdpDao.insert(insertPubList);
            }

            if (resSid > -1) {
                //削除するの施設予約SIDを取得する
                RsvSisYrkDao rsyDao = new RsvSisYrkDao(con__);
                ArrayList<Integer> rsySidList = rsyDao.getScheduleRserveSids(resSid);
                //施設予約区分別情報を削除
                if (rsySidList != null && rsySidList.size() > 0) {
                    RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con__);
                    kyrkDao.delete(rsySidList);
                }
                
                //公開情報の削除
                rdpDao.deleteList(deleteSidList);

                //旧施設予約情報を削除
                yrkDao.deleteScdRsSid(resSid);
            }

            //ひも付いている施設予約情報が無くなった場合、予約拡張データを削除
            if (rsrSid > -1 && yrkDao.getYrkDataCnt(rsrSid) < 1) {
                //件数取得し0件の場合
                ryrkDao.delete(rsrSid);

                //施設予約拡張区分別情報削除
                RsvSisKryrkDao kryrkDao = new RsvSisKryrkDao(con__);
                kryrkDao.delete(rsrSid);
            }
        }

        return cnt;
    }
    /**
     * <br>[機  能] 既存スケジュールを削除し、新規登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ
     * @param appRootPath アプリケーションパス
     * @param plconf プラグインコンフィグ
     * @param smailPluginUseFlg ショートメール使用フラグ
     * @param admConf 管理者設定
     * @param scdMdl 登録ベーススケジュールモデル
     * @param psOpe Push通知実行クラスインタフェース
     * @return 登録実行後のISchRegister
     * @throws SQLException
     * @throws TempFileException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    private ISchRegister __deleteInsertScheduleDate(
            final Sch200ParamModel paramMdl, String appRootPath,
            PluginConfig plconf, boolean smailPluginUseFlg,
            SchAdmConfModel admConf,
            SchDataModel scdMdl,
            IPushServiceOperator psOpe)
            throws SQLException, TempFileException,
            UnsupportedEncodingException, Exception {
        int userSid = reqMdl__.getSmodel().getUsrsid();
        String scdSid = paramMdl.getSch010SchSid();
        int oldSid = Integer.parseInt(scdSid);
        String[] svUsers = paramMdl.getSv_users();
        String[] svReserves = null;

        if (scdMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
            svReserves = paramMdl.getSvReserveUsers();
        }
        /** 旧スケジュールの通知設定マップ*/
        Map<Integer, SchPriPushModel> oldPriPushMap = new HashMap<>();


        //同時登録スケジュールSIDリスト
        ScheduleSearchDao ssDao = new ScheduleSearchDao(con__);
        ArrayList<Integer> scds = ssDao.getScheduleUsrs(
                oldSid,
                userSid,
                admConf.getSadCrange(),
                GSConstSchedule.SSP_AUTHFILTER_EDIT
                );

        ArrayList<Integer> oldScdSids = new ArrayList<>(Stream.concat(
                        Stream.of(Integer.parseInt(scdSid)),
                        scds.stream())
                        .collect(Collectors.toList()));
        //ユーザスケジュールの場合、旧スケジュールの通知設定を取得
        if (scdMdl.getScdUsrKbn()
                == GSConstSchedule.USER_KBN_USER) {
            SchDataDao schDao = new SchDataDao(con__);
            oldPriPushMap.putAll(
                    schDao.getSchedules(oldScdSids).stream()
                        .collect(
                                Collectors.toMap(SchDataModel::getScdUsrSid,
                                        SchPriPushModel::getInstance))
                    );

        }



        //新スケジュールを登録
        ISchRegister.Builder regBld;
        regBld = ISchRegister.simpleRegistBuilder(con__, reqMdl__, cntCon__, scdMdl);

        //更新時引継ぐ情報を設定
        regBld.setSchExtSid(scdMdl.getSceSid());
        regBld.setOldPushMap(oldPriPushMap);

        //添付ファイルの登録
        SchBinDao binDao = new SchBinDao(con__);
        List<Long> binSidList =
                Stream.of(binDao.getBinSids(oldSid))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
        regBld.setBinSidList(binSidList);

        //公開対象の登録
        if (scdMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
            SchDataPubDao pubDao = new SchDataPubDao(con__);
            regBld.setPubList(
                pubDao.select(oldSid)
            );
        }
        //同時登録分
        if (svUsers != null) {
            regBld.setUsers(
                    Stream.of(svUsers)
                        .map(Integer::parseInt)
                        .collect(Collectors.toSet())
                    );
        }
        //会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴を登録
        regBld.setUseContact((paramMdl.getSch040contact() == 1));
        regBld.setAdrSidArr(paramMdl.getSch040AddressId());
        regBld.setAbaSidArr(paramMdl.getSch040CompanyBaseSid());
        regBld.setAcoSidArr(paramMdl.getSch040CompanySid());

        regBld.setUseRsv(svReserves != null && svReserves.length > 0);

        //スケジュール登録ロジッククラス設定完了
        ISchRegister reg = regBld.build();

        //スケジュール・関連情報登録実行
        reg.regist();


        //旧スケジュールを削除
        __deleteSchedule(
                Stream.concat(
                        Stream.of(Integer.parseInt(scdSid)),
                        scds.stream())
                        .collect(Collectors.toList()),
                paramMdl.getSch040contact()
                );


        return reg;
    }
    /**
     * <br>[機  能] 既存スケジュール情報の削除
     * <br>[解  説]
     * <br>[備  考]
     * @param deleteScdSidList 削除対象リスト
     * @param contactEditFlg コンタクト履歴編集フラグ
     * @throws SQLException
     */
    private void __deleteSchedule(
            List<Integer> deleteScdSidList, int contactEditFlg) throws SQLException {
        SchBinDao binDao = new SchBinDao(con__);
        SchPushListDao splDao = new SchPushListDao(con__);
        SchDataPubDao sdpDao = new SchDataPubDao(con__);
        SchDataDao schDao = new SchDataDao(con__);
        schDao.delete(new ArrayList<Integer>(deleteScdSidList));
        splDao.delete(new ArrayList<Integer>(deleteScdSidList));
        binDao.deleteTempFile(deleteScdSidList);

        //編集元の公開対象を削除
        sdpDao.delete(deleteScdSidList);

        //変更前スケジュールの会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴を削除
        deleteSchCompany(con__, deleteScdSidList, contactEditFlg);
    }

    /**
     * <br>[機  能] スケジュールSIDからスケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @param adminConf 管理者設定
     * @param con コネクション
     * @return ScheduleSearchModel
     * @throws SQLException SQL実行時例外
     */
    public ScheduleSearchModel getSchData(
            int scdSid,
            SchAdmConfModel adminConf,
            Connection con)
    throws SQLException {

        ScheduleSearchModel scdMdl = null;
        CmnUsrmInfModel uMdl = null;
        int sessionUserSid = reqMdl__.getSmodel().getUsrsid();
        try {
            ScheduleSearchDao scdDao = new ScheduleSearchDao(con);
            scdMdl = scdDao.getScheduleData(scdSid,
                    GSConstSchedule.SSP_AUTHFILTER_EDIT,
                    sessionUserSid);
            if (scdMdl == null) {
                return null;
            }
            UserSearchDao uDao = new UserSearchDao(con);
            CmnUsrmDao cuDao = new CmnUsrmDao(con);
            //登録者
            uMdl = uDao.getUserInfoJtkb(scdMdl.getScdAuid(), -1);
            if (uMdl != null) {
                scdMdl.setScdAuidSei(uMdl.getUsiSei());
                scdMdl.setScdAuidMei(uMdl.getUsiMei());
                scdMdl.setScdAuidJkbn(cuDao.getUserJkbn(scdMdl.getScdAuid()));
            }
            //対象ユーザ
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                uMdl = uDao.getUserInfoJtkb(scdMdl.getScdUsrSid(), -1);
                if (uMdl != null) {
                    scdMdl.setScdUsrSei(uMdl.getUsiSei());
                    scdMdl.setScdUsrMei(uMdl.getUsiMei());
                    scdMdl.setScdUsrJkbn(cuDao.getUserJkbn(scdMdl.getScdUsrSid()));
                }
            } else {
                scdMdl.setScdUsrSei(getUsrName(scdMdl.getScdUsrSid(), scdMdl.getScdUsrKbn(), con));
            }
        } catch (SQLException e) {
            log__.error("スケジュール情報の取得に失敗" + e);
            throw e;
        }

        return scdMdl;
    }
    /**
     * <br>[機  能] 会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param userSid 登録/更新ユーザSID
     * @param date 更新日付
     * @param con コネクション
     * @param sessionUserSid セッションユーザSID
     * @param frDate 開始時間
     * @param toDate 終了時間
     * @throws SQLException SQL実行時例外
     */
    private void __updateSchCompany(Sch200ParamModel paramMdl,
                                    int userSid, UDate date,
                                    int sessionUserSid,
                                    Connection con,
                                    UDate frDate,
                                    UDate toDate)
    throws SQLException {

        int scdId = Integer.valueOf(paramMdl.getSch010SchSid());
        List<Integer> scdSidList = new ArrayList<Integer>();
        scdSidList.add(scdId);

        deleteSchCompany(con, scdSidList, paramMdl.getSch040contact());

        Map<Integer, Integer> scdUserMap = new HashMap<Integer, Integer>();
        scdUserMap.put(scdId, userSid);
        __insertSchCompany(paramMdl, scdSidList, scdUserMap, sessionUserSid, date, frDate, toDate);
    }
    /**
     * <br>[機  能] スケジュール一般登録確認URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmd 処理モード
     * @param sch010SchSid スケジュールSID
     * @param usrSid ユーザーSID
     * @param paramMdl Sch040ParamModel
     * @return スケジュール一般登録確認URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    private String __createScheduleUrlDefo(String cmd,
                                          String sch010SchSid, String usrSid,
                                          Sch040ParamModel paramMdl)
    throws UnsupportedEncodingException {

        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl__);
            paramUrl +=  "/" + GSConstSchedule.PLUGIN_ID_SCHEDULE;

            paramUrl += "/sch040.do";
            paramUrl += "?sch010SelectDate=" + paramMdl.getSch010SelectDate();
            paramUrl += "&cmd=" + cmd;
            paramUrl += "&sch010SchSid=" + sch010SchSid;
            paramUrl += "&sch010SelectUsrSid=" + usrSid;
            paramUrl += "&sch010SelectUsrKbn=" + paramMdl.getSch010SelectUsrKbn();
            paramUrl += "&sch010DspDate=" + paramMdl.getSch010DspDate();
            paramUrl += "&dspMod=" + paramMdl.getDspMod();
            paramUrl += "&sch010DspGpSid=" + paramMdl.getSch010DspGpSid();

            return urlBiz.getAccessUrl(reqMdl__, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }

    }
    /**
     * <br>[機  能] 会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param scdSidList スケジュールSID
     * @param scdUserMap スケジュールSIDとユーザSIDのMapping
     * @param sessionUserSid セッションユーザSID
     * @param date 更新日付
     * @param frDate 開始日付
     * @param toDate 終了日付
     * @throws SQLException SQL実行時例外
     */
    private void __insertSchCompany(Sch200ParamModel paramMdl,
                                    List<Integer> scdSidList,
                                    Map<Integer, Integer> scdUserMap,
                                    int sessionUserSid,
                                    UDate date,
                                    UDate frDate,
                                    UDate toDate)
    throws SQLException {

        //会社情報Mappingを登録
        List<SchCompanyModel> companyList = createCompanyModel(scdSidList,
                                                            paramMdl.getSch040CompanySid(),
                                                            paramMdl.getSch040CompanyBaseSid(),
                                                            sessionUserSid, date);
        if (companyList != null) {
            SchCompanyDao companyDao = new SchCompanyDao(con__);
            for (SchCompanyModel companyModel : companyList) {
                companyDao.insert(companyModel);
            }
        }

        //アドレス帳情報Mapping、コンタクト履歴を登録する
        String[] addressId = paramMdl.getSch040AddressId();
        List<SchAddressModel> addressList = createAddressModel(scdSidList, addressId,
                                                            sessionUserSid, date);
        if (addressList != null) {
            SchAddressDao addressDao = new SchAddressDao(con__);
            boolean contactFlg = (paramMdl.getSch040contact() == 1);

            String contactTitle = paramMdl.getSch200Title();
            String[] startDate = new String[5];
            startDate[0] = frDate.getStrYear();
            startDate[1] = frDate.getStrMonth();
            startDate[2] = frDate.getStrDay();
            startDate[3] = frDate.getStrHour();
            startDate[4] = frDate.getStrMinute();
            String[] endDate = new String[5];
            endDate[0] = toDate.getStrYear();
            endDate[1] = toDate.getStrMonth();
            endDate[2] = toDate.getStrDay();
            endDate[3] = toDate.getStrHour();
            endDate[4] = toDate.getStrMinute();

            int adcGrpSid = -1;
            Map<Integer, Integer> contactMap = new HashMap<Integer, Integer>();
            if (contactFlg && addressId != null) {
                //アドレス帳情報が複数選択されている場合はコンタクト履歴グループSIDを採番する
                if (addressId.length > 1) {
                    adcGrpSid = (int) cntCon__.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                            GSConst.SBNSID_SUB_CONTACT_GRP,
                                                            sessionUserSid);
                }

                //コンタクト履歴の登録
                for (String adrSid : addressId) {
                    Sch040ContactModel contactMdl
                            = createContactModel(Integer.parseInt(adrSid), adcGrpSid,
                                                contactTitle, startDate, endDate,
                                                sessionUserSid, date);
                    AdrContactModel model = new AdrContactModel();
                    try {
                        BeanUtils.copyProperties(model, contactMdl);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    AdrContactDao adcDao = new AdrContactDao(con__);
                    adcDao.insert(model);

                    contactMap.put(contactMdl.getAdrSid(), contactMdl.getAdcSid());
                }

            }

            for (SchAddressModel adrMdl : addressList) {
                if (contactFlg) {
                    adrMdl.setAdcSid(contactMap.get(adrMdl.getAdrSid()));
                }

                addressDao.insert(adrMdl);
            }
        }
    }
    /**
     * <br>[機  能] 会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param scdSidList スケジュールSID
     * @param contactFlg コンタクト履歴変更有無
     * @throws SQLException SQL実行時例外
     */
    public void deleteSchCompany(Connection con, List<Integer> scdSidList, int contactFlg)
    throws SQLException {

        SchCompanyDao companyDao = new SchCompanyDao(con);
        companyDao.delete(scdSidList);

        SchDao dao = new SchDao(con);
        SchAddressDao addressDao = new SchAddressDao(con);
        for (Integer scdSid : scdSidList) {
            if (contactFlg == 1) {
                dao.deleteScheduleContact(con, scdSid);
            }
            addressDao.delete(scdSid);
        }
    }
    /**
     * <br>[機  能] ユーザSIDとユーザ区分からユーザ氏名を取得する
     * <br>[解  説]
     * <br>[備  考]
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
     * <br>[機  能] スケジュール-アドレス帳情報Mapping Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList スケジュールSID
     * @param adrSidList アドレス帳SID
     * @param userSid セッションユーザSID
     * @param date 登録/更新日付
     * @return スケジュール-会社情報Mapping Model
     */
    public List<SchAddressModel> createAddressModel(List<Integer> scdSidList, String[] adrSidList,
                                                    int userSid, UDate date) {

        List<SchAddressModel> addressList = null;

        if (adrSidList != null) {

            addressList = new ArrayList<SchAddressModel>();

            for (Integer scdSid : scdSidList) {
                for (String adrSid : adrSidList) {
                    SchAddressModel addressModel = new SchAddressModel();
                    addressModel.setScdSid(scdSid);
                    addressModel.setAdrSid(Integer.parseInt(adrSid));
                    addressModel.setScaAuid(userSid);
                    addressModel.setScaAdate(date);
                    addressModel.setScaEuid(userSid);
                    addressModel.setScaEdate(date);

                    addressList.add(addressModel);
                }
            }
        }

        return addressList;
    }
    /**
     * <br>[機  能] スケジュール-会社情報Mapping Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList スケジュールSID
     * @param acoSidList 会社SID
     * @param abaSidList 会社拠点SID
     * @param userSid セッションユーザSID
     * @param date 登録/更新日付
     * @return スケジュール-会社情報Mapping Model
     */
    public List<SchCompanyModel> createCompanyModel(List<Integer> scdSidList,
                                                    String[] acoSidList,
                                                    String[] abaSidList,
                                                    int userSid, UDate date) {

        List<SchCompanyModel> companyList = null;

        if (acoSidList != null && abaSidList != null) {

            companyList = new ArrayList<SchCompanyModel>();

            for (int scdSid : scdSidList) {
                for (int index = 0; index < acoSidList.length; index++) {
                    SchCompanyModel companyModel = new SchCompanyModel();
                    companyModel.setScdSid(scdSid);
                    companyModel.setAcoSid(Integer.parseInt(acoSidList[index]));
                    companyModel.setAbaSid(Integer.parseInt(abaSidList[index]));
                    companyModel.setSccAuid(userSid);
                    companyModel.setSccAdate(date);
                    companyModel.setSccEuid(userSid);
                    companyModel.setSccEdate(date);

                    companyList.add(companyModel);
                }
            }
        }

        return companyList;
    }
    /**
     * <br>[機  能] コンタクト履歴Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレスSID
     * @param adcGrpSid アドレスグループSID
     * @param title タイトル
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @param userSid 登録/更新セッションユーザSID
     * @param date 登録/更新日付
     * @return コンタクト履歴Model
     * @throws SQLException コンタクト履歴SIDの採番に失敗
     */
    public Sch040ContactModel createContactModel(int adrSid, int adcGrpSid,
                                                String title,
                                                String[] startDate, String[] endDate,
                                                int userSid, UDate date)
    throws SQLException {

        int adcSid = (int) cntCon__.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                GSConst.SBNSID_SUB_CONTACT,
                                                userSid);

        Sch040ContactModel contactMdl = new Sch040ContactModel();
        contactMdl.setAdcSid(adcSid);
        contactMdl.setAdrSid(adrSid);
        contactMdl.setAdcTitle(title);
        contactMdl.setAdcType(GSConst.CONTYP_MEETING);

        if (StringUtil.isNullZeroString(startDate[3])) {
            startDate[3] = "0";
        }
        if (StringUtil.isNullZeroString(startDate[4])) {
            startDate[4] = "0";
        }
        contactMdl.setAdcCttime(__createDate(startDate));

        if (StringUtil.isNullZeroString(endDate[3])) {
            endDate[3] = "23";
        }
        if (StringUtil.isNullZeroString(endDate[4])) {
            endDate[4] = "55";
        }
        contactMdl.setAdcCttimeTo(__createDate(endDate));

        contactMdl.setAdcAuid(userSid);
        contactMdl.setAdcAdate(date);
        contactMdl.setAdcEuid(userSid);
        contactMdl.setAdcEdate(date);
        contactMdl.setAdcGrpSid(adcGrpSid);

        return contactMdl;
    }
    /**
     * <br>[機  能] UDateの作成を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param dateElement 日時(年、月、日、時、分)
     * @return UDate
     */
    private UDate __createDate(String[] dateElement) {
        UDate date = new UDate();
        date.setZeroHhMmSs();
        date.setDate(Integer.parseInt(dateElement[0]),
                    Integer.parseInt(dateElement[1]),
                    Integer.parseInt(dateElement[2]));
        date.setHour(Integer.parseInt(dateElement[3]));
        date.setMinute(Integer.parseInt(dateElement[4]));

        return date;
    }
    /**
     * <br>[機  能] 会社情報、アドレス帳情報を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setCompanyData(
            Sch200ParamModel paramMdl, Connection con, int userSid)
    throws SQLException {

        //会社情報、アドレス帳情報を設定
        String scdSid = paramMdl.getSch010SchSid();

        if (NullDefault.getInt(paramMdl.getSch040InitFlg(), GSConstSchedule.INIT_FLG)
            == GSConstSchedule.INIT_FLG) {
            SchCompanyDao companyDao = new SchCompanyDao(con);
            List<SchCompanyModel> companyList = companyDao.select(Integer.parseInt(scdSid));

            if (!companyList.isEmpty()) {
                String[] companySid = new String[companyList.size()];
                String[] companyBaseSid = new String[companyList.size()];

                for (int index = 0; index < companyList.size(); index++) {
                    SchCompanyModel companyModel = companyList.get(index);
                    companySid[index] = String.valueOf(companyModel.getAcoSid());
                    companyBaseSid[index] = String.valueOf(companyModel.getAbaSid());
                }

                paramMdl.setSch040CompanySid(companySid);
                paramMdl.setSch040CompanyBaseSid(companyBaseSid);
            }

            SchAddressDao addressDao = new SchAddressDao(con);
            List<SchAddressModel> addressList = addressDao.select(Integer.parseInt(scdSid));
            if (addressList != null) {
                String[] addressId = new String[addressList.size()];
                for (int index = 0; index < addressList.size(); index++) {
                    addressId[index] = String.valueOf(addressList.get(index).getAdrSid());
                    if (addressList.get(index).getAdcSid() > 0) {
                        paramMdl.setSch040contact(1);
                    }
                }

                paramMdl.setSch040AddressId(addressId);
            }
        }

        Sch040Dao dao040 = new Sch040Dao(con);
        String[] acoSidList = paramMdl.getSch040CompanySid();
        String[] abaSidList = paramMdl.getSch040CompanyBaseSid();
        List<String> companyIdList = new ArrayList<String>();
        Map<String, Sch040CompanyModel> companyMap = new HashMap<String, Sch040CompanyModel>();

        Sch040CompanyModel noCompanyModel = new Sch040CompanyModel();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //会社登録無し
        String textCmpDataNone = gsMsg.getMessage("schedule.src.87");
        noCompanyModel.setCompanyName(textCmpDataNone);
        noCompanyModel.setCompanyAddress(null);
        noCompanyModel.setCompanySid(0);
        noCompanyModel.setCompanyBaseSid(0);
        companyMap.put("0:0", noCompanyModel);

        if (acoSidList != null && abaSidList != null) {

            for (int index = 0; index < acoSidList.length; index++) {
                int acoSid = Integer.parseInt(acoSidList[index]);
                int abaSid = Integer.parseInt(abaSidList[index]);

                Sch040CompanyModel companyData = createCompanyData(dao040, acoSid, abaSid);
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
                    = sch040Dao.getAddressList(con, paramMdl.getSch040AddressId(), userSid);
        List<String> addressSidList = new ArrayList<String>();
        if (addressList != null) {

            for (Sch040AddressModel adrData : addressList) {
                String companyId = adrData.getCompanySid() + ":" + adrData.getCompanyBaseSid();
                Sch040CompanyModel companyData = companyMap.get(companyId);
                if (companyData == null) {
                    companyData = createCompanyData(dao040,
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

        paramMdl.setSch040CompanySid(companySidArray);
        paramMdl.setSch040CompanyBaseSid(companyBaseSidArray);
        paramMdl.setSch040AddressId(addressSidList.toArray(new String[addressSidList.size()]));
        paramMdl.setSch040CompanyList(companyList);
    }
    /**
     * <br>[機  能] 会社情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dao040 スケジュール登録画面DAO
     * @param acoSid 会社SID
     * @param abaSid 会社拠点SID
     * @return 会社情報
     * @throws SQLException SQL実行時例外
     */
    public Sch040CompanyModel createCompanyData(Sch040Dao dao040, int acoSid, int abaSid)
    throws SQLException {

        Sch040CompanyModel companyData = null;

        Sch040DBCompanyModel model = dao040.getCompanyData(acoSid);
        if (model != null) {
            companyData = new Sch040CompanyModel();

            String companyName = model.getAcoName();
            String companyaddress = null;

            Sch040DBCompanyBaseModel baseModel = dao040.getCompanyBaseData(abaSid);
            if (baseModel != null) {
                companyName += " " + baseModel.getAbaName();
                companyaddress = baseModel.getAbaAddr1();
                if (!StringUtil.isNullZeroStringSpace(baseModel.getAbaAddr2())) {
                    companyaddress += baseModel.getAbaAddr2();
                }
            }

            companyData.setCompanySid(acoSid);
            companyData.setCompanyBaseSid(abaSid);
            companyData.setCompanyName(companyName);
            companyData.setCompanyNameSearch(model.getAcoName());
            companyData.setCompanyAddress(companyaddress);
        }

        return companyData;
    }
    /**
     * <br>[機  能] DBに登録されている同時登録ユーザ情報を画面パラメータへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param list 同時登録ユーザ情報リスト
     */
    private void __setSaveUsersForDb(Sch200ParamModel paramMdl, ArrayList<CmnUsrmInfModel> list) {
        ArrayList<String> sv_user_list = new ArrayList<String>();
        if (list != null) {
            for (CmnUsrmInfModel usrMdl : list) {
                sv_user_list.add(String.valueOf(usrMdl.getUsrSid()));
            }

            paramMdl.setSv_users((String[]) sv_user_list.toArray(new String[sv_user_list.size()]));
        }
    }
    /**
     * <br>[機  能] DBに登録されている同時登録施設情報を画面パラメータへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setSaveReserveForDb(
            Sch200ParamModel paramMdl, Connection con) throws SQLException {
        int scdSid = NullDefault.getInt(paramMdl.getSch010SchSid(), -1);
        //施設SIDリストを取得
        ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);
        ArrayList<Integer> list = schRsvDao.getScheduleReserveData(scdSid);
        ArrayList<String> sv_user_list = new ArrayList<String>();

        if (list != null) {
            for (Integer rsdSid : list) {
                sv_user_list.add(String.valueOf(rsdSid));
            }

            paramMdl.setSvReserveUsers(
                    (String[]) sv_user_list.toArray(new String[sv_user_list.size()]));
        }
    }
    /**
     * <br>[機  能] 同時登録、施設予約チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param frDate 開始時間
     * @param toDate 終了時間
     * @param con コネクション
     * @param oldMdl 編集前データ
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors __validateCheck(
            Sch200ParamModel paramMdl,
            UDate frDate,
            UDate toDate,
            Connection con,
            ScheduleSearchModel oldMdl) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);

        //同時登録スケジュールの編集権限チェック
        errors = validateSchPowerCheck(paramMdl, reqMdl__, errors, con);

        //同時登録施設予約の編集権限チェック
        errors = validateResPowerCheck(paramMdl, reqMdl__, errors, con);

        //施設予約エラーチェック
        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con);
        boolean yrkOkFlg = true;
        boolean errorFlg = false;
        String[] rsdSids = null;

        if (paramMdl.getSch200TimeKbn().equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {
            rsdSids = paramMdl.getSvReserveUsers();
        }

        Rsv210Model dataMdl = null;
        if (rsdSids != null) {

            Rsv110Biz rsv110biz = new Rsv110Biz(reqMdl__, con);
            for (String rsdSid : rsdSids) {
                dataMdl = rsv110biz.getGroupData(Integer.parseInt(rsdSid));
                if (dataMdl != null) {

                    //予約可能期限チェック(期限が設定されていればチェックする)
                    String kigen = dataMdl.getRsdProp6();
                    if (!StringUtil.isNullZeroString(kigen)) {

                        //施設グループ管理者の場合は予約可能期限チェックをパスする
                        RsvCommonBiz rsvBiz = new RsvCommonBiz();
                        if (!rsvBiz.isGroupAdmin(con, Integer.parseInt(rsdSid),
                                reqMdl__.getSmodel().getUsrsid())) {

                            UDate now = new UDate();
                            UDate udKigen = now.cloneUDate();
                            udKigen.addDay(Integer.parseInt(kigen));

                            String kigenYmd = udKigen.getDateString();
                            String chkYmd = toDate.getDateString();

                            if (Integer.parseInt(chkYmd) > Integer.parseInt(kigenYmd)) {
                                //開始・終了
                                String textDayAfter = gsMsg.getMessage("cmn.days.after");
                                String kigenStr =
                                        "※"
                                                + dataMdl.getRsdProp6()
                                                + textDayAfter;

                                msg = new ActionMessage("error.kigen.over2.sisetu", kigenStr);
                                StrutsUtil.addMessage(errors, msg, "sisetu");
                                errorFlg = true;
                            }
                        }
                    }
                }

                //重複のチェック(重複登録 = 不可の場合にチェック)
                String tyohuku = dataMdl.getRsdProp7();
                log__.debug("*****tyohuku = " + tyohuku);
                if (!errorFlg
                        && !StringUtil.isNullZeroString(tyohuku)
                        && Integer.parseInt(tyohuku) == GSConstReserve.PROP_KBN_HUKA) {
                    //施設予約重複チェック
                    //新規モード
                    ArrayList<RsvSisYrkModel> yrkList = null;
                    if (oldMdl.getScdRsSid() != -1) {
                        yrkList = yrkDao.getScheduleRserveData(
                                oldMdl.getScdRsSid()
                                );
                    }
                    RsvSisYrkModel yrkMdl = null;

                    yrkMdl = getReserveData(yrkList, Integer.parseInt(rsdSid));

                    if (yrkMdl == null) {
                        if (!yrkDao.isYrkOk(-1, Integer.parseInt(rsdSid), frDate, toDate)) {
                            yrkOkFlg = false;
                        }
                    } else {
                        if (!yrkDao.isYrkOk(
                                yrkMdl.getRsySid(), yrkMdl.getRsdSid(), frDate, toDate)) {
                            yrkOkFlg = false;
                        }
                    }

                    if (!yrkOkFlg) {
                        msg = new ActionMessage("rsv012.exist.reserve");
                        StrutsUtil.addMessage(errors, msg, "rsv110YrkErr");
                    }
                }
                if (errorFlg) {
                    break;
                }
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validatePowerCheck(
            Sch200ParamModel paramMdl,
            RequestModel reqMdl,
            Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        //同時登録スケジュールの編集権限チェック
        errors = validateSchPowerCheck(paramMdl, reqMdl, errors, con);
        //施設予約エラーチェック
        errors = validateResPowerCheck(paramMdl, reqMdl, errors, con);
        return errors;
    }
    /**
     * <br>[機  能] 同時登録スケジュールの編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param reqMdl リクエスト情報
     * @param errors アクションエラー
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateSchPowerCheck(
            Sch200ParamModel paramMdl,
            RequestModel reqMdl,
            ActionErrors errors,
            Connection con) throws SQLException {

        ActionMessage msg = null;

        int schSid = NullDefault.getInt(paramMdl.getSch010SchSid(), -1);
        //同時登録スケジュールの編集権限チェック
        if (paramMdl.getSch200BatchRef().equals("1")) {
            //
            Sch010Biz biz = new Sch010Biz(reqMdl);
            if (!biz.isAllEditOk(schSid, reqMdl, con)) {
                //同時登録スケジュールに対する編集
                msg = new ActionMessage("error.schedule.edit.cant.user");
                StrutsUtil.addMessage(errors, msg, "adduser");
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 同時登録施設予約の編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param reqMdl リクエスト情報
     * @param errors アクションエラー
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateResPowerCheck(
            Sch200ParamModel paramMdl,
            RequestModel reqMdl,
            ActionErrors errors,
            Connection con) throws SQLException {

        ActionMessage msg = null;
        int schSid = NullDefault.getInt(paramMdl.getSch010SchSid(), -1);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //アクセス権限チェック
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        CommonBiz cmnBiz = new CommonBiz();

        //編集権限のない施設数を取得する。
        if (paramMdl.getSch200ResBatchRef().equals("1")) {
            boolean rsvAdmin = cmnBiz.isPluginAdmin(
                    con, usModel, GSConstSchedule.PLUGIN_ID_RESERVE);
            int count = getCanNotEditRsvCount(paramMdl, usModel.getUsrsid(), con, rsvAdmin);

            if (count > 0) {
                //施設予約アクセス権限なし
                msg = new ActionMessage("error.myself.auth");
                StrutsUtil.addMessage(errors, msg, "error.myself.auth");
                return errors;
            }

        }


        //変更
        String textChange = gsMsg.getMessage("cmn.change");
        //同時登録施設予約の編集権限チェック
        if (paramMdl.getSch200ResBatchRef().equals("1")) {

            RelationBetweenScdAndRsvChkBiz rsvChkBiz
                = new RelationBetweenScdAndRsvChkBiz(reqMdl, con);
            int errorCd = rsvChkBiz.isRsvEdit(
                    schSid,
                    RelationBetweenScdAndRsvChkBiz.CHK_KBN_TANITU);
            log__.debug("施設予約の編集権限チェック:エラーコード==>" + errorCd);
            if (errorCd == RelationBetweenScdAndRsvChkBiz.ERR_CD_SCD_CANNOT_EDIT) {
                //施設予約に対する編集
                String textRsvEdit = gsMsg.getMessage("schedule.src.32");
                msg = new ActionMessage("error.edit.power.user", textRsvEdit, textChange);
                StrutsUtil.addMessage(errors, msg, "addres");
            }
        }
        return errors;
    }

    /**
     * 指定した施設SIDを配列から検索します。
     * <br>[機  能]配列に存在する場合はRsvSisYrkModelを返します。
     * <br>[解  説]配列に存在しない場合はnullを返します。
     * <br>[備  考]
     * @param yrkList 本スケジュールろ同時登録された施設予約情報リスト
     * @param rsdSid 検索される施設SID
     * @return RsvSisYrkModel
     */
    public RsvSisYrkModel getReserveData(ArrayList<RsvSisYrkModel> yrkList, int rsdSid) {
        RsvSisYrkModel ret = null;
        if (yrkList == null) {
            return ret;
        }

        for (RsvSisYrkModel yrkMdl : yrkList) {
            if (yrkMdl.getRsdSid() == rsdSid) {
                return yrkMdl;
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] DBに同時登録施設情報があるか確認
     * <br>[解  説]
     * <br>[備  考]
     * @param scdId スケジュールID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return rsvFlg 施設予約フラグ
     */
    private boolean __getSaveReserve(int scdId, Connection con) throws SQLException {
        ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);
        return schRsvDao.existScheduleReserveData(scdId);
    }
    /**
     * <br>[機  能] 同時登録ユーザ存在チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @param adminConf 管理者設定
     * @param con コネクション
     * @return ScheduleSearchModel
     * @throws SQLException SQL実行時例外
     */
    public boolean __getSaveUsr(
            int scdSid,
            SchAdmConfModel adminConf,
            Connection con)
    throws SQLException {
      ScheduleSearchDao scdDao = new ScheduleSearchDao(con);
      return scdDao.existWithUsers(scdSid);
    }

    /**
     * <br>[機  能] マイグループメンバー存在チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param sessionUsrSid ユーザSID
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateGroupMemberExistCheck(
            Sch200ParamModel paramMdl,
            int sessionUsrSid) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);

        String gpSidStr = paramMdl.getSch010DspGpSid();
        if (gpSidStr == null || gpSidStr.length() == 0) {
            gpSidStr = scBiz.getDispDefaultGroupSidStr(con__, sessionUsrSid);
        }
        //ユーザ一覧取得
        int dspGpSid = SchCommonBiz.getDspGroupSid(gpSidStr);
        List<UsrLabelValueBean> userLabel = __getUserLabelList(sessionUsrSid, dspGpSid,
                SchCommonBiz.isMyGroupSid(gpSidStr));


        if (userLabel == null || userLabel.size() < 1) {

            //デフォルトグループを設定
            String dfGpSidStr = scBiz.getDispDefaultGroupSidStr(con__, sessionUsrSid);
            userLabel = __getUserLabelList(
                    sessionUsrSid, SchCommonBiz.getDspGroupSid(dfGpSidStr),
                    SchCommonBiz.isMyGroupSid(dfGpSidStr));
            if (userLabel == null || userLabel.size() < 1) {
                GroupBiz gbiz = new GroupBiz();
                dfGpSidStr = String.valueOf(gbiz.getDefaultGroupSid(sessionUsrSid, con__));
            }
            paramMdl.setSch010DspGpSid(dfGpSidStr);

            //グループ名を取得する。
            String grpName = "";
            if (SchCommonBiz.isMyGroupSid(gpSidStr)) {
                CmnMyGroupDao mygrpDao = new CmnMyGroupDao(con__);
                CmnMyGroupModel mygrpModel = mygrpDao.getMyGroupInfo(dspGpSid);
                if (mygrpModel != null) {
                    grpName = StringUtilHtml.transToHTmlPlusAmparsant(mygrpModel.getMgpName());
                }
            } else {
                CmnGroupmDao grpDao = new CmnGroupmDao(con__);
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
     * <br>[機  能] 重複登録の警告スケジュール一覧を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param usrSid 選択ユーザSID
     * @param sessionUsrSid ユーザSID
     * @param con コネクション
     * @param oldMdl 変更スケジュール情報
     * @return 警告スケジュールリスト
     * @throws SQLException SQLExceptionm
     */
    public List<SchDataModel> getSchWarningList(
            Sch200ParamModel paramMdl,
            int usrSid,
            Connection con,
            ScheduleSearchModel oldMdl,
            int sessionUsrSid
            ) throws SQLException {
        paramMdl.setSch010SelectUsrSid(String.valueOf(usrSid));
        //同時登録メンバー
        __setSaveUsersForDb(paramMdl, oldMdl.getUsrInfList());
        String[] sv_users = paramMdl.getSv_users();

        //個人設定を取得する。
        SchPriConfDao priConfDao = new SchPriConfDao(con);
        SchPriConfModel priModel = priConfDao.select(
                Integer.valueOf(paramMdl.getSch010SelectUsrSid()));
        //重複登録区分 1:NG 2:警告を表示
        int mode = 0;

        SchCommonBiz schBiz = new SchCommonBiz(reqMdl__);
        SchRepeatKbnModel repertMdl
            = schBiz.getRepertKbn(con, priModel, Integer.valueOf(paramMdl.getSch010SelectUsrSid()));

        //自分の予定の場合は編集可能フラグ
        boolean mySchOkFlg = false;
        if (priModel != null
                && repertMdl.getRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG
                && repertMdl.getRepeatMyKbn() == GSConstSchedule.SCH_REPEAT_MY_KBN_OK
                && usrSid == sessionUsrSid) {
            mySchOkFlg = true;
            mode = 2;
        } else if (priModel != null
                    && repertMdl.getRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG
                    && repertMdl.getRepeatMyKbn() == GSConstSchedule.SCH_REPEAT_MY_KBN_OK
                    && usrSid != sessionUsrSid) {
                mode = 1;
        } else if (priModel != null
                && repertMdl.getRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG
                && repertMdl.getRepeatMyKbn() == GSConstSchedule.SCH_REPEAT_MY_KBN_NG) {
            mode = 1;
        } else if (priModel != null
                && paramMdl.getCmd().equals("repetCheck")) {
            mode = 2;
        }

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

        int frYear = Integer.parseInt(paramMdl.getSch040FrYear());
        int frMonth = Integer.parseInt(paramMdl.getSch040FrMonth());
        int frDay = Integer.parseInt(paramMdl.getSch040FrDay());

        int toYear = Integer.parseInt(paramMdl.getSch040ToYear());
        int toMonth = Integer.parseInt(paramMdl.getSch040ToMonth());
        int toDay = Integer.parseInt(paramMdl.getSch040ToDay());

        int frHour = GSConstSchedule.DAY_START_HOUR;
        int frMin = GSConstSchedule.DAY_START_MINUTES;

        int toHour = GSConstSchedule.DAY_END_HOUR;
        int toMin = GSConstSchedule.DAY_END_MINUTES;
        int toSec = GSConstSchedule.DAY_START_SECOND;
        int toMiliSec = GSConstSchedule.DAY_START_MILLISECOND;

        if (paramMdl.getSch040TimeKbn().equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {
            frHour = Integer.parseInt(paramMdl.getSch040FrHour());
            frMin = Integer.parseInt(paramMdl.getSch040FrMin());
            toHour = Integer.parseInt(paramMdl.getSch040ToHour());
            toMin = Integer.parseInt(paramMdl.getSch040ToMin());
            toSec = GSConstSchedule.DAY_START_SECOND;
            toMiliSec = GSConstSchedule.DAY_START_MILLISECOND;
        }

        //予約開始
        UDate chkFrDate = new UDate();
        chkFrDate.setDate(frYear, frMonth, frDay);
        chkFrDate.setHour(frHour);
        chkFrDate.setMinute(frMin);
        chkFrDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        chkFrDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);

        //予約終了
        UDate chkToDate = new UDate();
        chkToDate.setDate(toYear, toMonth, toDay);
        chkToDate.setHour(toHour);
        chkToDate.setMinute(toMin);
        chkToDate.setSecond(toSec);
        chkToDate.setMilliSecond(toMiliSec);


        //編集スケジュールSID
        int schSid = NullDefault.getInt(paramMdl.getSch010SchSid(), 0);

        List<SchDataModel> rptSchList = new ArrayList<SchDataModel>();
        SchDataDao schDao = new SchDataDao(con);
        int schGrpSid = -1;
        String batchRef = paramMdl.getSch200BatchRef();

        if (batchRef.equals("1")) {
            //同時修正する場合

            SchDataModel bean = new SchDataModel();
            bean.setScdSid(schSid);
            SchDataModel schModel = schDao.select(bean);

            if (schModel != null) {
                schGrpSid = schModel.getScdGrpSid();
            }
        }

        SchAdmConfModel admConf = schBiz.getAdmConfModel(con);
        boolean canEditRepeatKbn = schBiz.canEditRepertKbn(admConf);
        List<Integer> ngUsrList1 = null;
        if (canEditRepeatKbn) {
            //重複登録不可にしているユーザリストを取得
            ngUsrList1 = priConfDao.getUsrSidListRepeatKbn(usrList,
                                    GSConstSchedule.SCH_REPEAT_KBN_NG);
        } else {
            if (admConf.getSadRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG) {
                ngUsrList1 = new ArrayList<Integer>();
                ngUsrList1.addAll(usrList);
            }
        }

        if (ngUsrList1 != null && ngUsrList1.size() > 0) {
            //重複登録しているスケジュール一覧を取得する。
            List<SchDataModel> rptSchList1 = new ArrayList<SchDataModel>();
            rptSchList1 = schDao.getSchData(
                    ngUsrList1, schSid, chkFrDate, chkToDate, schGrpSid, "0");
            if (rptSchList1 != null && rptSchList1.size() > 0) {
                paramMdl.setSch200CantAddUserFlg(1);
            }
        }

        //重複登録警告にしているユーザリストを取得
        List<Integer> warningUsrList1 = null;
        if (canEditRepeatKbn) {
            warningUsrList1 = priConfDao.getUsrSidListRepeatKbn(usrList,
                                                            GSConstSchedule.SCH_REPEAT_KBN_WARNING);
        } else {
            warningUsrList1 = new ArrayList<Integer>();
            if (admConf.getSadRepeatKbn() != GSConstSchedule.SCH_REPEAT_KBN_OK) {
                warningUsrList1.addAll(usrList);
            }
        }

        if (warningUsrList1 != null && warningUsrList1.size() > 0) {
            //セッションユーザをチェックに含める
            if (mySchOkFlg
                    && sessionUsrSid == Integer.parseInt(paramMdl.getSch010SelectUsrSid())) {
                warningUsrList1.add(Integer.parseInt(paramMdl.getSch010SelectUsrSid()));
            }

            if (warningUsrList1 != null && warningUsrList1.size() > 0) {
                //重複登録しているスケジュール一覧を取得する。
                List<SchDataModel> rptSchList2 = new ArrayList<SchDataModel>();
                rptSchList2 = schDao.getSchData(
                        warningUsrList1, schSid, chkFrDate, chkToDate, schGrpSid, "0");
                if (rptSchList2 != null && rptSchList2.size() > 0) {
                    paramMdl.setSch200WarningAddUserFlg(1);
                }
            }
        }

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
                //重複登録しているスケジュール一覧を取得する。
                rptSchList = schDao.getSchData(
                        ngUsrList, schSid, chkFrDate, chkToDate, schGrpSid, "0");
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

            //セッションユーザをチェックに含める
            if (mySchOkFlg
                    && sessionUsrSid == Integer.parseInt(paramMdl.getSch010SelectUsrSid())) {
                warningUsrList.add(Integer.parseInt(paramMdl.getSch010SelectUsrSid()));
            }

            if (warningUsrList != null && warningUsrList.size() > 0) {
                //重複登録しているスケジュール一覧を取得する。
                rptSchList = schDao.getSchData(
                        warningUsrList, schSid, chkFrDate, chkToDate, schGrpSid, "0");
            }
        }

        return rptSchList;
    }
    /**
     * <br>重複登録警告画面
     * @param paramMdl Sch200ParamModel
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param usrSid ユーザsid
     * @param sessionUsrSid セッションユーザsid
     * @param oldMdl 編集前データ
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    public ActionMessage __doDupWarningCheck(Sch200ParamModel paramMdl,
            RequestModel reqMdl, Connection con,
            int usrSid, int sessionUsrSid,
            ScheduleSearchModel oldMdl)
    throws SQLException {
        ActionMessage error = null;
        Sch200Biz biz = new Sch200Biz(reqMdl);
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);

        //開始時間
        UDate frDate = oldMdl.getScdFrDate();
        if (paramMdl.getCmd().equals("repetCheck")
                && paramMdl.getSch200EventKbn() == GSConstSchedule.EVENT_DROP) {
            if (paramMdl.getSch200DayDelta() != 0) {
                frDate.addDay(paramMdl.getSch200DayDelta());
            }
            if (paramMdl.getSch200MinuteDelta() != 0) {
                frDate.addMinute(paramMdl.getSch200MinuteDelta());
            }
        }
        paramMdl.setSch040FrYear(frDate.getStrYear());
        paramMdl.setSch040FrMonth(frDate.getStrMonth());
        paramMdl.setSch040FrDay(frDate.getStrDay());
        paramMdl.setSch040FrHour(frDate.getStrHour());
        paramMdl.setSch040FrMin(frDate.getStrMinute());
        //終了時間
        UDate toDate = oldMdl.getScdToDate();
        if (paramMdl.getCmd().equals("repetCheck") && oldMdl.getScdDaily() == 1) {
            //時間指定なしから時間してありへ移動
            if (paramMdl.getSch200DayDelta() != 0) {
                toDate.addDay(paramMdl.getSch200DayDelta());
            }
            toDate.setHour(frDate.getIntHour());
            toDate.setMinute(frDate.getIntMinute());
            toDate.addMinute(30);
        } else {
            if (paramMdl.getSch200DayDelta() != 0) {
                toDate.addDay(paramMdl.getSch200DayDelta());
            }
            if (paramMdl.getSch200MinuteDelta() != 0) {
                toDate.addMinute(paramMdl.getSch200MinuteDelta());
            }
        }
        paramMdl.setSch040ToYear(toDate.getStrYear());
        paramMdl.setSch040ToMonth(toDate.getStrMonth());
        paramMdl.setSch040ToDay(toDate.getStrDay());
        paramMdl.setSch040ToHour(toDate.getStrHour());
        paramMdl.setSch040ToMin(toDate.getStrMinute());

        //重複登録 警告スケジュール一覧を取得する。
        List<SchDataModel> rptSchList
                 = biz.getSchWarningList(
                         paramMdl, usrSid, con, oldMdl, sessionUsrSid);

        String textSchList = "";
        if (rptSchList != null && rptSchList.size() > 0) {
            int i = 0;
            String title = "";
            for (SchDataModel model : rptSchList) {
                if (i > 0) {
                    textSchList += "<br>";
                }

                title = schBiz.getDspTitle(model, usrSid);

                textSchList += "・";
                textSchList += StringUtilHtml.transToHTmlPlusAmparsant(model.getScdUserName());
                textSchList += " ";
                textSchList += StringUtilHtml.transToHTmlPlusAmparsant(title);

                textSchList += "(";
                textSchList += UDateUtil.getYymdJ(model.getScdFrDate(), reqMdl__);
                textSchList += UDateUtil.getSeparateHMJ(model.getScdFrDate(), reqMdl__);
                textSchList += "～";
                textSchList += UDateUtil.getYymdJ(model.getScdToDate(), reqMdl__);
                textSchList += UDateUtil.getSeparateHMJ(model.getScdToDate(), reqMdl__);
                textSchList += ")";

                i++;
                error = new ActionMessage("errors.free.msg", textSchList);
            }
            paramMdl.setSch200ErrorsRowCnt(i);
        } else {
            //警告がなければNULLを返す
            return null;
        }
        return error;
    }
    /**
     * <br>拡張登録スケジュールも含め編集権限があるか判定する
     * @param scdSid スケジュールSID
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return boolean true:権限あり　false:権限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean __isAllEditOkEx(
            int scdSid,
            RequestModel reqMdl,
            Connection con) throws SQLException {

        boolean baseEdit = false;
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //管理者設定を取得
        SchCommonBiz adminbiz = new SchCommonBiz(reqMdl);
        SchAdmConfModel adminConf = adminbiz.getAdmConfModel(con);

        Sch040Biz biz = new Sch040Biz(con, reqMdl);
        ScheduleSearchModel scdMdl = biz.getSchData(scdSid, adminConf, con);
        if (scdMdl == null) {
            return false;
        }
        //自分のスケジュールか
        if (scdMdl.getScdUsrSid() == sessionUsrSid) {
            baseEdit = true;
        } else {
            baseEdit = false;
        }
        return baseEdit;
    }
    /**
     * <br>[機  能] アクセス権限のない施設数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch200ParamModel
     * @param sessionUsrSid ユーザSID
     * @param con コネクション
     * @param rsvAdmin 施設予約管理者
     * @return count 施設数
     * @throws SQLException SQLExceptionm
     */
    public int getCanNotEditRsvCount(
            Sch200ParamModel paramMdl,
            int sessionUsrSid,
            Connection con,
            boolean rsvAdmin
            ) throws SQLException {
        int count = 0;

        if (rsvAdmin) {
            return count;
        }

        ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);
        int scdSid = NullDefault.getInt(paramMdl.getSch010SchSid(), -1);

        ArrayList<Integer> allRsdList = schRsvDao.getScheduleReserveData(scdSid);
        if (allRsdList == null || allRsdList.size() == 0) {
            return count;
        }

        //施設SIDリストを取得
        ArrayList<Integer> rsdList
            = schRsvDao.getCanEditScheduleReserveData(scdSid, sessionUsrSid);

        if (rsdList.size() == allRsdList.size()) {
            return count;
        }

        for (Integer rsdSid : allRsdList) {
            if (!rsdList.contains(rsdSid)) {
                count++;
            }
        }

        return count;
    }

}
