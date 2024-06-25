package jp.groupsession.v2.sch.sch041kn;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.dao.base.TcdAdmConfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.model.SchEnumReminderTime;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.man.MaintenanceUtil;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.rsv.biz.IRsvYoyakuRegister;
import jp.groupsession.v2.rsv.dao.RsvDataPubDao;
import jp.groupsession.v2.rsv.dao.RsvExdataPubDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisKryrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisRyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvDataPubModel;
import jp.groupsession.v2.rsv.model.RsvExdataPubModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisKyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisRyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.sch.biz.ISchRegister;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchAddressDao;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchCompanyDao;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.SchExaddressDao;
import jp.groupsession.v2.sch.dao.SchExcompanyDao;
import jp.groupsession.v2.sch.dao.SchExdataBinDao;
import jp.groupsession.v2.sch.dao.SchExdataDao;
import jp.groupsession.v2.sch.dao.SchExdataPubDao;
import jp.groupsession.v2.sch.dao.SchPushListDao;
import jp.groupsession.v2.sch.dao.ScheduleReserveDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataGroupModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchDataPubModel;
import jp.groupsession.v2.sch.model.SchExaddressModel;
import jp.groupsession.v2.sch.model.SchExcompanyModel;
import jp.groupsession.v2.sch.model.SchExdataBinModel;
import jp.groupsession.v2.sch.model.SchExdataModel;
import jp.groupsession.v2.sch.model.SchExdataPubModel;
import jp.groupsession.v2.sch.model.ScheduleExSearchModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.sch040.Sch040Biz;
import jp.groupsession.v2.sch.sch041.Sch041DateSearchModel;
import jp.groupsession.v2.sch.sch041.Sch041DateSortModel;
import jp.groupsession.v2.sch.sch041.Sch041Form;
import jp.groupsession.v2.sch.sch041.Sch041ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] スケジュール繰り返し登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch041knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch041knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public Sch041knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch041knParamModel
     * @param reqMdl リクエスト情報
     * @param pconfig プラグインコンフィグ
     * @param con コネクション
     * @return アクションフォーム
     * @throws Exception 実行時例外
     */
    public Sch041knParamModel getInitData(Sch041knParamModel paramMdl,
            RequestModel reqMdl, PluginConfig pconfig, Connection con) throws Exception {

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

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

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

        //登録者
        paramMdl.setSch040AddUsrName(usModel.getUsisei() + " " + usModel.getUsimei());
        uMdl = cuDao.select(usModel.getUsrsid());
        paramMdl.setSch040AddUsrUkoFlg(uMdl.getUsrUkoFlg());

        //設定期間・時間
        __setSch041knFormFromReq(paramMdl);
        //登録予定日付
        HashMap<String, UDate> addDateMap = getInsertDateList(paramMdl, sessionUsrSid, con);
        paramMdl.setSch041knAftDateList(__getStrDateListFromMap(addDateMap));
        //同時登録ユーザ名リスト
        CmnUsrmInfDao uinfDao = new CmnUsrmInfDao(con);
        ArrayList<Integer> usrSids = new ArrayList<Integer>();
        usrSids.add(Integer.valueOf(GSConstUser.SID_ADMIN));
        usrSids.add(Integer.valueOf(uid));
        ArrayList < Integer > list = null;
        ArrayList < CmnUsrmInfModel > selectUsrList = null;
        String[] users = null;
        if (paramMdl.getCmd().equals(GSConstSchedule.CMD_DELETE)) {
            //削除確認用にDBスケジュール情報を設定
            users = __setSch041knFormFromDb(paramMdl, con);
        } else {
            users = paramMdl.getSch041SvUsers();
        }

        if (users != null && users.length > 0) {
            list = new ArrayList<Integer>();
            for (int i = 0; i < users.length; i++) {
                list.add(Integer.valueOf(users[i]));
                //同時登録ユーザを所属リストから除外する
                usrSids.add(Integer.valueOf(users[i]));
            }
            selectUsrList = uinfDao.getUserList(list);
        }
        paramMdl.setSch041knSelectUsrList(selectUsrList);

        String scdSid = paramMdl.getSch010SchSid();
        //スケジュール情報
        if (!paramMdl.getCmd().equals(GSConstSchedule.CMD_ADD)) {
            //登録済み日付
            ArrayList<String> oldDateList =
                getOldDateList(Integer.parseInt(scdSid), sessionUsrSid, con);
            paramMdl.setSch041knBefDateList(oldDateList);
        }
        //施設予約
        String[] reserves = null;
        if (paramMdl.getCmd().equals(GSConstSchedule.CMD_DELETE)) {
            reserves = setSch041knReservesFromDb(paramMdl, con);
        } else {
            reserves = paramMdl.getSch041SvReserve();
        }
        paramMdl.setSch041knReserveList(getReserveNameList(reserves, con));

        //会社情報を設定
        Sch040Biz biz040 = new Sch040Biz(con, reqMdl__);
        biz040.setCompanyData(paramMdl, con, sessionUsrSid, reqMdl__);

        //ボタン用の処理モードを設定する。
        String cmd = paramMdl.getCmd();
        String btnCmd = "";
        if (!StringUtil.isNullZeroStringSpace(cmd)) {
            btnCmd = StringUtil.toSingleCortationEscape(cmd);
        }
        paramMdl.setSch040BtnCmd(btnCmd);

        //公開対象
        __setPublicTargetData(paramMdl, con);

        //通知予定リスト
        __setReminderData(paramMdl);

        //添付ファイル
        __setFileInfo(paramMdl, reqMdl);

        return paramMdl;
    }

    /**
     * <br>[機  能] 施設SIDの配列から施設名のリストを生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param reserves 施設SID配列
     * @param con コネクション
     * @return ArrayList 施設リスト
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<RsvSisDataModel> getReserveNameList(String[] reserves, Connection con)
    throws SQLException {
        ArrayList<RsvSisDataModel> ret = null;
        RsvSisDataDao rsDao = new RsvSisDataDao(con);
        ret = rsDao.selectSisetuList(reserves);
        return ret;
    }

    /**
     * <br>[機  能] Mapに格納されている日付情報をArrayList(in String(yyyy年mm月dd日形式))に置き換えます。
     * <br>[解  説]
     * <br>[備  考]
     * @param map 日付情報
     * @return ArrayList 格納しなおした日付情報
     */
    private ArrayList<String> __getStrDateListFromMap(HashMap<String, UDate> map) {

        ArrayList<UDate> col = new ArrayList<UDate>(map.values());
        ArrayList<Sch041DateSortModel> sort = new ArrayList<Sch041DateSortModel>();
        Sch041DateSortModel sortMdl = null;
        for (UDate date : col) {
            sortMdl = new Sch041DateSortModel();
            sortMdl.setUdate(date);
            sort.add(sortMdl);
        }
        Collections.sort(sort);

        ArrayList<String> ret = new ArrayList<String>();
        for (Sch041DateSortModel model : sort) {
            ret.add(UDateUtil.getYymdJwithStrWeek(model.getUdate(), reqMdl__));
        }

        return ret;
    }

    /**
     * <br>[機  能] 日付指定条件に該当する日付リストを取得します。
     * <br>[解  説] 営業日の判定にはタイムカード基本設定を使用します
     * <br>[備  考]
     * @param model 抽出条件
     * @param sessionUsrSid セッションユーザ
     * @param con コネクション
     * @return HashMap ケジュールを登録する日付MAP
     * @throws SQLException SQL実行時例外
     */
    public HashMap<String, UDate> getDateList(
            Sch041DateSearchModel model, int sessionUsrSid, Connection con)
    throws SQLException {

        UDate frDate = model.getFromDate().cloneUDate();
        UDate toDate = model.getToDate().cloneUDate();
        UDate startDate = model.getFromDate().cloneUDate();

        if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_YEAR))) {
            int iMonth = NullDefault.getInt(model.getMonthOfYearly(), 0);
            int iDay = NullDefault.getInt(model.getDayOfYearly(), 0);

            if (frDate.getMonth() > iMonth
                    || (frDate.getIntDay() > iDay
                            && frDate.getMonth() == iMonth)) {
                frDate.addYear(1);
            }
            frDate.setMonth(iMonth);
            frDate.setDay(iDay);
        }

        //営業日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayListFotTcd(frDate, toDate);
        TcdAdmConfModel acMdl = getTcdAdmConfModel(sessionUsrSid, con);

        HashMap<String, UDate> dateMap = new HashMap<String, UDate>();
        UDate setDate = null;
        log__.debug("frDate.compareDateYMD(toDate)=>" + frDate.compareDateYMD(toDate));

        //一週間の終わりの曜日を取得
        int weekEnd = 0;
        if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_BIWEEK))) {
            UDate dateWeekEnd = startDate.cloneUDate();
            dateWeekEnd.addDay(6);
            weekEnd = dateWeekEnd.getWeek();
        }

        while (frDate.compareDateYMD(toDate) != UDate.SMALL) {
            //抽出条件に該当するか判定
            if (__isMatchCondition(frDate, model, startDate)) {
                //振替判定
                setDate = __getConvertDate(frDate, model, holMap, acMdl);
                if (setDate != null) {
                    dateMap.put(setDate.getDateString(), setDate);
                    log__.debug("登録日付==>" + setDate.getDateString());
                }
            }
            if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_YEAR))) {
                //毎年
                frDate.addYear(1);
            } else if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_BIWEEK))
                    && frDate.getWeek() ==  weekEnd) {
                //隔週かつ、週末
                frDate.addDay(8);
            } else {
                frDate.addDay(1);
            }
        }

        return dateMap;
    }

    /**
     * <br>[機  能] 日付指定条件に該当する日付リストを取得します。
     * <br>[解  説] 営業日の判定にはタイムカード基本設定を使用します
     * <br>[備  考]
     * @param model 抽出条件
     * @param sessionUsrSid セッションユーザ
     * @param con コネクション
     * @return HashMap ケジュールを登録する日付MAP
     * @throws SQLException SQL実行時例外
     */
    public HashMap<String, String> getStrDateList(
            Sch041DateSearchModel model, int sessionUsrSid, Connection con)
    throws SQLException {

        UDate frDate = model.getFromDate();
        UDate toDate = model.getToDate();
        UDate startDate = model.getFromDate().cloneUDate();
        //営業日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayListFotTcd(frDate, toDate);
        TcdAdmConfModel acMdl = getTcdAdmConfModel(sessionUsrSid, con);

        HashMap<String, String> dateMap = new HashMap<String, String>();
        UDate setDate = null;
        while (frDate.compareDateYMD(toDate) != UDate.SMALL) {
            //抽出条件に該当するか判定
            if (__isMatchCondition(frDate, model, startDate)) {
                //振替判定
                setDate = __getConvertDate(frDate, model, holMap, acMdl);
                if (setDate != null) {
                    dateMap.put(setDate.getDateString(), setDate.getDateString());
                    log__.debug("登録日付==>" + setDate.getDateString());
                }
            }
            frDate.addDay(1);
        }

        return dateMap;
    }

    /**
     * <br>[機  能] 営業日判定を行い非営業日の場合、振替設定によって日付をコンバートします。
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日付
     * @param model 抽出条件
     * @param holMap 休日情報
     * @param acMdl タイムカード管理設定
     * @return UDate コンバート結果
     */
    private UDate __getConvertDate(
            UDate date,
            Sch041DateSearchModel model,
            HashMap<String, CmnHolidayModel> holMap,
            TcdAdmConfModel acMdl) {

        UDate ret = date.cloneUDate();
        int tran = Integer.parseInt(model.getTranKbn());
        log__.debug("振替区分==>" + tran);
        int addDay = 0;
        if (tran == GSConstSchedule.FURIKAE_KBN_AFT) {
            addDay = 1;
        } else if (tran == GSConstSchedule.FURIKAE_KBN_BEF) {
            addDay = -1;
        } else if (tran == GSConstSchedule.FURIKAE_KBN_NOADD) {
            addDay = 0;
        } else {
            return ret;
        }

        //休日として扱う曜日か判定
        boolean fin = true;
        while (fin) {
            if (__isMatchWeek(ret.getWeek(), acMdl)
             || holMap.containsKey(ret.getDateString())) {
                log__.debug("休日として認識==>" + ret.getDateString());
                //休日は登録しない場合
                if (tran == GSConstSchedule.FURIKAE_KBN_NOADD) {
                    return null;
                }
                ret.addDay(addDay);
            } else {
                fin = false;
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] 抽出条件に該当する日付か判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param date 判定対象の日付
     * @param model 抽出条件
     * @param startDate 設定期間開始日付
     * @return boolean true:該当 false:該当しない
     */
    private boolean __isMatchCondition(
            UDate date,
            Sch041DateSearchModel model,
            UDate startDate) {

        if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_DAY))) {
            //毎日
            return true;
        } else if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_WEEK))
                || model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_BIWEEK))) {
            //毎週
            return __isMatchWeek(date.getWeek(), model);

        } else if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_MONTH))) {
            //毎月
            if (model.getWeekOfMonthly() != null) {
                //週・曜日指定
                int weekNo = Integer.parseInt(model.getWeekOfMonthly());
                if (__isMatchWeek(date.getWeek(), model)) {
                    UDate wkDate = date.cloneUDate();
                    int wkWeekOfMonth
                        = MaintenanceUtil.getAccurateWeekOfMonth(
                                                        wkDate, wkDate.getWeek());
                    log__.debug("wkDate==>" + wkDate.getDateString());
                    log__.debug("weekNo==>" + weekNo);
                    log__.debug("wkWeekOfMonth==>" + wkWeekOfMonth);
                    if (weekNo == wkWeekOfMonth) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                //日指定
                int selectDay = Integer.parseInt(model.getDayOfMonthly());
                ArrayList<UDate> frList = new ArrayList<UDate>();
                ArrayList<UDate> toList = new ArrayList<UDate>();
                UDate fr = model.getFromDate().cloneUDate();
                UDate to = model.getToDate().cloneUDate();
                UDate roopDate = model.getFromDate().cloneUDate();
                roopDate.setDay(1);
                to.setDay(to.getMaxDayOfMonth());
                while (roopDate.compareDateYMD(to) != UDate.SMALL) {

                    UDate kikanFrDate = roopDate.cloneUDate();
                    UDate kikanToDate = roopDate.cloneUDate();

                    UDate hikakuDate = roopDate.cloneUDate();
                    if (selectDay == GSConstCommon.LAST_DAY_OF_MONTH) {
                        hikakuDate.setDay(hikakuDate.getMaxDayOfMonth());
                    } else {
                        hikakuDate.setDay(selectDay);
                    }

                    if (hikakuDate.compareDateYMD(fr) == UDate.LARGE) {
                        roopDate.addMonth(1);
                        continue;
                    }
                    if (hikakuDate.compareDateYMD(to) == UDate.SMALL) {
                        roopDate.addMonth(1);
                        continue;
                    }
                    if (selectDay == GSConstCommon.LAST_DAY_OF_MONTH) {
                        int lastyDay = roopDate.getMaxDayOfMonth();
                        kikanFrDate.setDay(lastyDay);
                        kikanToDate.setDay(lastyDay);
                    } else {
                        kikanFrDate.setDay(selectDay);
                        kikanToDate.setDay(selectDay);
                    }
                    if (model.getDayOfMonthStart() != null) {
                        int betweenDays = Integer.parseInt(model.getDayOfMonthStart());
                        kikanToDate.addDay(betweenDays - 1);
                    } else if (model.getDayOfMonthEnd() != null) {
                        int betweenDays = Integer.parseInt(model.getDayOfMonthEnd());
                        betweenDays = betweenDays * -1;
                        kikanFrDate.addDay(betweenDays + 1);
                    }
                    frList.add(kikanFrDate);
                    toList.add(kikanToDate);
                    roopDate.addMonth(1);
                }
                for (int idx = 0; idx < frList.size(); idx++) {
                    UDate kikanFrDate = frList.get(idx);
                    UDate kikanToDate = toList.get(idx);
                    if (kikanFrDate.compareDateYMD(model.getFromDate()) == UDate.LARGE) {
                        kikanFrDate = model.getFromDate().cloneUDate();
                    }
                    if (kikanToDate.compareDateYMD(model.getToDate()) == UDate.SMALL) {
                        kikanToDate = model.getToDate().cloneUDate();
                    }
                    if (date.betweenYMDHM(kikanFrDate, kikanToDate)) {
                        return true;
                    }
                }
                return false;
            }
        } else if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_YEAR))) {
            //毎年
            String yearly = NullDefault.getString(model.getDayOfYearly(), "");
            return (model.getMonthOfYearly().equals(String.valueOf(date.getMonth()))
                    && CommonBiz.getExDay(date, yearly).equals(String.valueOf(date.getIntDay())));
        } else {
            return false;
        }

    }

    /**
     * <br>[機  能] 指定した曜日が指定されているか判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param week 週
     * @param acMdl 管理設定
     * @return true:休日曜日　false:休日曜日以外
     */
    private boolean __isMatchWeek(int week, TcdAdmConfModel acMdl) {
        switch (week) {
        case UDate.SUNDAY:
            if (acMdl.getTacHolSun() == 0) {
                return false;
            }
            break;
        case UDate.MONDAY:
            if (acMdl.getTacHolMon() == 0) {
                return false;
            }
            break;
        case UDate.TUESDAY:
            if (acMdl.getTacHolTue() == 0) {
                return false;
            }
            break;
        case UDate.WEDNESDAY:
            if (acMdl.getTacHolWed() == 0) {
                return false;
            }
            break;
        case UDate.THURSDAY:
            if (acMdl.getTacHolThu() == 0) {
                return false;
            }
            break;
        case UDate.FRIDAY:
            if (acMdl.getTacHolFri() == 0) {
                return false;
            }
            break;
        case UDate.SATURDAY:
            if (acMdl.getTacHolSat() == 0) {
                return false;
            }
            break;
        default:
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 指定した曜日が指定されているか判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param week 週
     * @param model 抽出条件
     * @return true:指定されている　false:指定されていない
     */
    private boolean __isMatchWeek(int week, Sch041DateSearchModel model) {
        switch (week) {
        case UDate.SUNDAY:
            if (model.getWeekOfWeekly1() == null) {
                return false;
            }
            break;
        case UDate.MONDAY:
            if (model.getWeekOfWeekly2() == null) {
                return false;
            }
            break;
        case UDate.TUESDAY:
            if (model.getWeekOfWeekly3() == null) {
                return false;
            }
            break;
        case UDate.WEDNESDAY:
            if (model.getWeekOfWeekly4() == null) {
                return false;
            }
            break;
        case UDate.THURSDAY:
            if (model.getWeekOfWeekly5() == null) {
                return false;
            }
            break;
        case UDate.FRIDAY:
            if (model.getWeekOfWeekly6() == null) {
                return false;
            }
            break;
        case UDate.SATURDAY:
            if (model.getWeekOfWeekly7() == null) {
                return false;
            }
            break;
        default:
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 画面パラメータからスケジュールを登録する日付リストを取得します。
     * <br>[解  説] 営業日の判定にはタイムカード基本設定を使用します
     * <br>[備  考]
     * @param paramMdl Sch041knParamModel
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @return HashMap ケジュールを登録する日付MAP
     * @throws SQLException SQL実行時例外
     */
    public HashMap<String, UDate> getInsertDateList(
            Sch041ParamModel paramMdl, int sessionUsrSid, Connection con)
    throws SQLException {

        Sch041DateSearchModel model = new Sch041DateSearchModel();
        //抽出区分
        model.setDateKbn(paramMdl.getSch041ExtKbn());

        if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_DAY))) {
            model.setTranKbn(paramMdl.getSch041TranKbn());
        } else if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_WEEK))) {
            __setWeekOfWeekly(paramMdl.getSch041Dweek(), model);
            model.setTranKbn(paramMdl.getSch041TranKbn());
        } else if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_MONTH))) {
            if (paramMdl.getSch041WeekOrDay().equals(
                    String.valueOf(GSConstSchedule.EXTEND_MONTH_WEEK))) {
                __setWeekOfWeekly(paramMdl.getSch041Dweek(), model);
                model.setWeekOfMonthly(paramMdl.getSch041Week());
            } else {
                if (paramMdl.getSch041ConfKbn().equals(
                        String.valueOf(GSConstSchedule.CONF_KBN_ONLY))) {
                    model.setDayOfMonthly(paramMdl.getSch041Day());
                } else if (paramMdl.getSch041ConfKbn().equals(
                        String.valueOf(GSConstSchedule.CONF_KBN_FROM))) {
                    model.setDayOfMonthly(paramMdl.getSch041Day());
                    model.setDayOfMonthStart(paramMdl.getSch041DayOfMonth());
                } else if (paramMdl.getSch041ConfKbn().equals(
                        String.valueOf(GSConstSchedule.CONF_KBN_TO))) {
                    model.setDayOfMonthly(paramMdl.getSch041Day());
                    model.setDayOfMonthEnd(paramMdl.getSch041DayOfMonth());
                }
            }
            model.setTranKbn(paramMdl.getSch041TranKbn());
        } else if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_YEAR))) {
           model.setDayOfYearly(paramMdl.getSch041DayOfYearly());
           model.setMonthOfYearly(paramMdl.getSch041MonthOfYearly());
           model.setTranKbn(paramMdl.getSch041TranKbn());
        } else if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_BIWEEK))) {
            __setWeekOfWeekly(paramMdl.getSch041Dweek(), model);
            model.setTranKbn(paramMdl.getSch041TranKbn());
        }
        //抽出期間
        UDate frDate = new UDate();
        frDate.setYear(Integer.parseInt(paramMdl.getSch041FrYear()));
        frDate.setMonth(Integer.parseInt(paramMdl.getSch041FrMonth()));
        frDate.setDay(Integer.parseInt(paramMdl.getSch041FrDay()));
        frDate.setZeroHhMmSs();
        model.setFromDate(frDate);
        UDate toDate = new UDate();
        toDate.setYear(Integer.parseInt(paramMdl.getSch041ToYear()));
        toDate.setMonth(Integer.parseInt(paramMdl.getSch041ToMonth()));
        toDate.setDay(Integer.parseInt(paramMdl.getSch041ToDay()));
        toDate.setZeroHhMmSs();
        model.setToDate(toDate);
        //日付リストを取得
        HashMap<String, UDate> dateMap = getDateList(model, sessionUsrSid, con);

        return dateMap;
    }

    /**
     * <br>[機  能] 画面パラメータからスケジュールを登録する日付リストを取得します。
     * <br>[解  説] 営業日の判定にはタイムカード基本設定を使用します
     * <br>[備  考]
     * @param paramMdl Sch041knParamModel
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @return HashMap ケジュールを登録する日付MAP
     * @throws SQLException SQL実行時例外
     */
    public HashMap<String, UDate> getInsertDateList(
            Sch041Form paramMdl, int sessionUsrSid, Connection con)
    throws SQLException {
        Sch041DateSearchModel model = new Sch041DateSearchModel();
        //抽出区分
        model.setTranKbn(paramMdl.getSch041TranKbn());
        model.setDateKbn(paramMdl.getSch041ExtKbn());
        
        if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_WEEK))) {
            __setWeekOfWeekly(paramMdl.getSch041Dweek(), model);
        } else if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_MONTH))) {
            if (paramMdl.getSch041WeekOrDay().equals(
                    String.valueOf(GSConstSchedule.EXTEND_MONTH_WEEK))) {
                __setWeekOfWeekly(paramMdl.getSch041Dweek(), model);
                model.setWeekOfMonthly(paramMdl.getSch041Week());
            } else {
                if (paramMdl.getSch041ConfKbn().equals(
                        String.valueOf(GSConstSchedule.CONF_KBN_ONLY))) {
                    model.setDayOfMonthly(paramMdl.getSch041Day());
                } else if (paramMdl.getSch041ConfKbn().equals(
                        String.valueOf(GSConstSchedule.CONF_KBN_FROM))) {
                    model.setDayOfMonthly(paramMdl.getSch041Day());
                    model.setDayOfMonthStart(paramMdl.getSch041DayOfMonth());
                } else if (paramMdl.getSch041ConfKbn().equals(
                        String.valueOf(GSConstSchedule.CONF_KBN_TO))) {
                    model.setDayOfMonthly(paramMdl.getSch041Day());
                    model.setDayOfMonthEnd(paramMdl.getSch041DayOfMonth());
                }
            }
        } else if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_YEAR))) {
            model.setDayOfYearly(paramMdl.getSch041DayOfYearly());
            model.setMonthOfYearly(paramMdl.getSch041MonthOfYearly());
        } else if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_BIWEEK))) {
            __setWeekOfWeekly(paramMdl.getSch041Dweek(), model);
        }
        //抽出期間
        UDate frDate = new UDate();
        frDate.setYear(Integer.parseInt(paramMdl.getSch041FrYear()));
        frDate.setMonth(Integer.parseInt(paramMdl.getSch041FrMonth()));
        frDate.setDay(Integer.parseInt(paramMdl.getSch041FrDay()));
        frDate.setZeroHhMmSs();
        model.setFromDate(frDate);
        UDate toDate = new UDate();
        toDate.setYear(Integer.parseInt(paramMdl.getSch041ToYear()));
        toDate.setMonth(Integer.parseInt(paramMdl.getSch041ToMonth()));
        toDate.setDay(Integer.parseInt(paramMdl.getSch041ToDay()));
        toDate.setZeroHhMmSs();
        model.setToDate(toDate);
        //日付リストを取得
        HashMap<String, UDate> dateMap = getDateList(model, sessionUsrSid, con);

        return dateMap;
    }

    /**
     * <br>[機  能] 画面パラメータからスケジュールを登録する日付リストを取得します。
     * <br>[解  説] 営業日の判定にはタイムカード基本設定を使用します
     * <br>[備  考]
     * @param paramMdl Sch041knParamModel
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @return HashMap ケジュールを登録する日付MAP
     * @throws SQLException SQL実行時例外
     */
    public HashMap<String, String> getInsertStrDateList(
            Sch041ParamModel paramMdl, int sessionUsrSid, Connection con)
    throws SQLException {
        Sch041DateSearchModel model = new Sch041DateSearchModel();
        //抽出区分
        model.setDateKbn(paramMdl.getSch041ExtKbn());
        model.setTranKbn(String.valueOf(GSConstSchedule.FURIKAE_KBN_NONE));
        if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_WEEK))
                || model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_BIWEEK))) {
            __setWeekOfWeekly(paramMdl.getSch041Dweek(), model);
            model.setTranKbn(paramMdl.getSch041TranKbn());
        } else if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_MONTH))) {
            if (paramMdl.getSch041WeekOrDay().equals(
                    String.valueOf(GSConstSchedule.EXTEND_MONTH_WEEK))) {
                __setWeekOfWeekly(paramMdl.getSch041Dweek(), model);
                model.setWeekOfMonthly(paramMdl.getSch041Week());
            } else {
                if (paramMdl.getSch041ConfKbn().equals(
                        String.valueOf(GSConstSchedule.CONF_KBN_ONLY))) {
                    model.setDayOfMonthly(paramMdl.getSch041Day());
                } else if (paramMdl.getSch041ConfKbn().equals(
                        String.valueOf(GSConstSchedule.CONF_KBN_FROM))) {
                    model.setDayOfMonthly(paramMdl.getSch041Day());
                    model.setDayOfMonthStart(paramMdl.getSch041DayOfMonth());
                } else if (paramMdl.getSch041ConfKbn().equals(
                        String.valueOf(GSConstSchedule.CONF_KBN_TO))) {
                    model.setDayOfMonthly(paramMdl.getSch041Day());
                    model.setDayOfMonthEnd(paramMdl.getSch041DayOfMonth());
                }
            }
            model.setTranKbn(paramMdl.getSch041TranKbn());
        } else if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_YEAR))) {
            model.setDayOfYearly(paramMdl.getSch041DayOfYearly());
            model.setMonthOfYearly(paramMdl.getSch041MonthOfYearly());
            model.setTranKbn(paramMdl.getSch041TranKbn());
        }
        //抽出期間
        UDate frDate = new UDate();
        frDate.setYear(Integer.parseInt(paramMdl.getSch041FrYear()));
        frDate.setMonth(Integer.parseInt(paramMdl.getSch041FrMonth()));
        frDate.setDay(Integer.parseInt(paramMdl.getSch041FrDay()));
        frDate.setZeroHhMmSs();
        model.setFromDate(frDate);
        UDate toDate = new UDate();
        toDate.setYear(Integer.parseInt(paramMdl.getSch041ToYear()));
        toDate.setMonth(Integer.parseInt(paramMdl.getSch041ToMonth()));
        toDate.setDay(Integer.parseInt(paramMdl.getSch041ToDay()));
        toDate.setZeroHhMmSs();
        model.setToDate(toDate);
        //日付リストを取得
        HashMap<String, String> dateMap = getStrDateList(model, sessionUsrSid, con);

        return dateMap;
    }

    /**
     * <br>[機  能] スケジュールSIDから登録済スケジュールの日付リストを取得します。
     * <br>[解  説] 拡張情報が未登録の場合はsize=0のリストを返します。
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @return ArrayList ケジュールを登録する日付リスト
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<String> getOldDateList(int scdSid, int sessionUsrSid, Connection con)
    throws SQLException {

        ArrayList<String> ret = new ArrayList<String>();
        //管理者設定を取得
        SchCommonBiz cBiz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel adminConf = cBiz.getAdmConfModel(con);

        HashMap<String, UDate> dateMap = null;
        Sch040Biz biz = new Sch040Biz(con, reqMdl__);
        ScheduleSearchModel scdMdl = biz.getSchData(scdSid, adminConf, con);
        if (scdMdl == null) {
            return ret;
        }
        ScheduleExSearchModel sceMdl = biz.getSchExData(scdSid, adminConf, con);
        if (sceMdl != null) {
            //拡張登録から登録日付を設定
            Sch041DateSearchModel model = new Sch041DateSearchModel();
            //抽出区分
            model.setDateKbn(String.valueOf(sceMdl.getSceKbn()));

            if (model.getDateKbn().equals(String.valueOf(GSConstSchedule.EXTEND_KBN_WEEK))
                    || model.getDateKbn().equals(
                            String.valueOf(GSConstSchedule.EXTEND_KBN_BIWEEK))) {
                String[] weeks = __getDayWeekStringList(sceMdl);
                __setWeekOfWeekly(weeks, model);
            } else if (model.getDateKbn().equals(
                    String.valueOf(GSConstSchedule.EXTEND_KBN_MONTH))) {
                if (sceMdl.getSceWeek() != 0) {
                    String[] weeks = __getDayWeekStringList(sceMdl);
                    __setWeekOfWeekly(weeks, model);
                    model.setWeekOfMonthly(String.valueOf(sceMdl.getSceWeek()));
                } else {
                    if (sceMdl.getScePeriodKbn() == GSConstSchedule.CONF_KBN_ONLY) {
                        model.setDayOfMonthly(String.valueOf(sceMdl.getSceDay()));
                    } else if (sceMdl.getScePeriodKbn() == GSConstSchedule.CONF_KBN_FROM) {
                        model.setDayOfMonthly(String.valueOf(sceMdl.getSceDay()));
                        model.setDayOfMonthStart(String.valueOf(sceMdl.getSceDaysMonth()));
                    } else if (sceMdl.getScePeriodKbn() == GSConstSchedule.CONF_KBN_TO) {
                        model.setDayOfMonthly(String.valueOf(sceMdl.getSceDay()));
                        model.setDayOfMonthEnd(String.valueOf(sceMdl.getSceDaysMonth()));
                    }
                }
            } else if (model.getDateKbn().equals(
                    String.valueOf(GSConstSchedule.EXTEND_KBN_YEAR))) {
                model.setDayOfYearly(String.valueOf(sceMdl.getSceDayOfYearly()));
                model.setMonthOfYearly(String.valueOf(sceMdl.getSceMonthOfYearly()));
            }
            //抽出期間
            model.setTranKbn(String.valueOf(sceMdl.getSceTranKbn()));
            model.setFromDate(sceMdl.getSceDateFr());
            model.setToDate(sceMdl.getSceDateTo());
            //日付リストを取得
            dateMap = getDateList(model, sessionUsrSid, con);
            ret = __getStrDateListFromMap(dateMap);
        } else {
            String frDate = UDateUtil.getYymdJ(scdMdl.getScdFrDate(), reqMdl__);
            String toDate = UDateUtil.getYymdJ(scdMdl.getScdToDate(), reqMdl__);
            ret.add(frDate + "～" + toDate);
        }
        return ret;
    }

    /**
     * <br>[機  能] 曜日を抽出用モデルに設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param weeks 曜日指定
     * @param model 抽出用モデル
     */
    private void __setWeekOfWeekly(String[] weeks, Sch041DateSearchModel model) {
        if (weeks == null) {
            return;
        }

        for (String week : weeks) {
            int intWeek = Integer.parseInt(week);
            switch (intWeek) {
            case 1:
                model.setWeekOfWeekly1("1");
                break;
            case 2:
                model.setWeekOfWeekly2("1");
                break;
            case 3:
                model.setWeekOfWeekly3("1");
                break;
            case 4:
                model.setWeekOfWeekly4("1");
                break;
            case 5:
                model.setWeekOfWeekly5("1");
                break;
            case 6:
                model.setWeekOfWeekly6("1");
                break;
            case 7:
                model.setWeekOfWeekly7("1");
                break;
            default:
                break;
            }
        }
    }

    /**
     *
     * <br>[機  能] タイムカード管理者設定を取得する。
     * <br>[解  説] レコードが存在しない場合、デフォルト値で作成します。
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return TcdAdmConfModel
     * @throws SQLException SQL実行時例外
     */
    public TcdAdmConfModel getTcdAdmConfModel(int usrSid, Connection con) throws SQLException {
        log__.debug("タイムカード管理者設定取得");
        TcdAdmConfDao dao = new TcdAdmConfDao(con);
        TcdAdmConfModel model = dao.select();
        if (model == null) {
            boolean commit = false;
            try {
                model = new TcdAdmConfModel(usrSid);
                dao.insert(model);
                commit = true;
            } catch (SQLException e) {
                log__.error("タイムカード管理者設定の登録に失敗しました。");
                throw e;
            } finally {
                if (commit) {
                    con.commit();
                }

            }
        }
        return model;
    }

    /**
     * <br>リクエストパラメータから画面項目を設定する
     * @param paramMdl Sch041knParamModel
     */
    private void __setSch041knFormFromReq(Sch041knParamModel paramMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textMonth = gsMsg.getMessage("cmn.month");
        String textDay = gsMsg.getMessage("cmn.day");
        //開始日時
        StringBuilder frBuf = new StringBuilder();
        StringBuilder toBuf = new StringBuilder();

        String frDateStr = paramMdl.getSch041FrDate();
        if (frDateStr != null
        && ValidateUtil.isSlashDateFormat(frDateStr)
        && ValidateUtil.isExistDateYyyyMMdd(frDateStr.replaceAll("/", ""))) {
            String[] frDate = frDateStr.split("/");
            paramMdl.setSch041FrYear(frDate[0]);
            paramMdl.setSch041FrMonth(frDate[1]);
            paramMdl.setSch041FrDay(frDate[2]);

            frBuf.append(gsMsg.getMessage("cmn.year", new String[] {paramMdl.getSch041FrYear()}));
            frBuf.append(paramMdl.getSch041FrMonth());
            frBuf.append(textMonth);
            frBuf.append(paramMdl.getSch041FrDay());
            frBuf.append(textDay);
        }

        String toDateStr = paramMdl.getSch041ToDate();
        if (toDateStr != null
        && ValidateUtil.isSlashDateFormat(toDateStr)
        && ValidateUtil.isExistDateYyyyMMdd(toDateStr.replaceAll("/", ""))) {
            String[] toDate = toDateStr.split("/");
            paramMdl.setSch041ToYear(toDate[0]);
            paramMdl.setSch041ToMonth(toDate[1]);
            paramMdl.setSch041ToDay(toDate[2]);

            toBuf.append(gsMsg.getMessage("cmn.year", new String[] {paramMdl.getSch041ToYear()}));
            toBuf.append(paramMdl.getSch041ToMonth());
            toBuf.append(textMonth);
            toBuf.append(paramMdl.getSch041ToDay());
            toBuf.append(textDay);
        }

        String frTimeStr = paramMdl.getSch041FrTime();
        if (frTimeStr != null && ValidateUtil.isTimeFormat(frTimeStr)) {
            String[] frTime = frTimeStr.split(":");
            paramMdl.setSch041FrHour(frTime[0]);
            paramMdl.setSch041FrMin(frTime[1]);
        }
        String toTimeStr = paramMdl.getSch041ToTime();
        if (toTimeStr != null && ValidateUtil.isTimeFormat(toTimeStr)) {
            String[] toTime = toTimeStr.split(":");
            paramMdl.setSch041ToHour(toTime[0]);
            paramMdl.setSch041ToMin(toTime[1]);
        }

        StringBuilder frTimeBuf = new StringBuilder();
        StringBuilder toTimeBuf = new StringBuilder();
        //時間指定無し判定
        if (paramMdl.getSch041TimeKbn().equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {
            //時
            //From時間指定有り
            if (!paramMdl.getSch041FrHour().equals("-1")
             && !paramMdl.getSch041FrMin().equals("-1")) {
                String[] params = {paramMdl.getSch041FrHour(),
                        StringUtil.toDecFormat(paramMdl.getSch041FrMin(), "00")};
                frTimeBuf.append(gsMsg.getMessage("cmn.time.input", params));
            } else {
                //省略
                String[] params = {"0", "00"};
                frTimeBuf.append(gsMsg.getMessage("cmn.time.input", params));
            }
            //To時間指定有り
            if (!paramMdl.getSch041ToHour().equals("-1")
             && !paramMdl.getSch041ToMin().equals("-1")) {
                String[] params = {paramMdl.getSch041ToHour(),
                        StringUtil.toDecFormat(paramMdl.getSch041ToMin(), "00")};
                toTimeBuf.append(gsMsg.getMessage("cmn.time.input", params));
            } else {
                //省略
                String[] params = {"23", "59"};
                toTimeBuf.append(gsMsg.getMessage("cmn.time.input", params));
            }
        }

        //開始日時
        paramMdl.setSch041knFromDate(frBuf.toString());
        //終了日時
        paramMdl.setSch041knToDate(toBuf.toString());
        //開始時間
        paramMdl.setSch041knFromTime(frTimeBuf.toString());
        //終了時間
        paramMdl.setSch041knToTime(toTimeBuf.toString());
        //内容
        paramMdl.setSch041knDspValue(
                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSch041Value()));
        //備考
        paramMdl.setSch041knDspBiko(
                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSch041Biko()));

    }

    /**
     * <br>スケジュール拡張情報から削除確認用画面項目を設定する
     * @param paramMdl Sch041knParamModel
     * @param con コネクション
     * @return String[] 同時登録ユーザ
     * @throws SQLException SQL実行時例外
     */
    private String[] __setSch041knFormFromDb(Sch041knParamModel paramMdl, Connection con)
    throws SQLException {

        String[] users = null;
        int scdSid = NullDefault.getInt(paramMdl.getSch010SchSid(), -1);
        //管理者設定を取得
        SchCommonBiz cBiz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel adminConf = cBiz.getAdmConfModel(con);
        Sch040Biz biz = new Sch040Biz(con, reqMdl__);
        ScheduleSearchModel scdMdl = biz.getSchData(scdSid, adminConf, con);
        if (scdMdl == null) {
            return null;
        }
        ScheduleExSearchModel sceMdl = biz.getSchExData(scdSid, adminConf, con);

        if (sceMdl != null) {
            String frDate = UDateUtil.getYymdJ(sceMdl.getSceDateFr(), reqMdl__);
            String toDate = UDateUtil.getYymdJ(sceMdl.getSceDateTo(), reqMdl__);
            //開始日時
            paramMdl.setSch041knDelFrDate(frDate);
            //終了日時
            paramMdl.setSch041knDelToDate(toDate);

            String frTime = UDateUtil.getSeparateHMJ(sceMdl.getSceTimeFr(), reqMdl__);
            String toTime = UDateUtil.getSeparateHMJ(sceMdl.getSceTimeTo(), reqMdl__);
            //開始時間
            paramMdl.setSch041knDelFrTime(frTime);
            //終了時間
            paramMdl.setSch041knDelToTime(toTime);
            //タイトル
            paramMdl.setSch041knDelTitle(sceMdl.getSceTitle());
            //タイトル色
            paramMdl.setSch041knDelBgcolor(String.valueOf(sceMdl.getSceBgcolor()));
            //内容
            paramMdl.setSch041knDelValue(
                    StringUtilHtml.transToHTmlPlusAmparsant(sceMdl.getSceValue()));
            //備考
            paramMdl.setSch041knDelBiko(
                    StringUtilHtml.transToHTmlPlusAmparsant(sceMdl.getSceBiko()));
            //公開
            paramMdl.setSch041knDelPublic(String.valueOf(sceMdl.getScePublic()));
            //振替
            paramMdl.setSch041knDelTranKbn(String.valueOf(sceMdl.getSceTranKbn()));
            //編集
            paramMdl.setSch041knDelEdit(String.valueOf(sceMdl.getSceEdit()));

            //同時登録ユーザ一覧
            users = biz.getSaveUsersForDbEx(sceMdl.getUsrInfList());
        }
        return users;
    }

    /**
     * <br>[機  能] 添付ファイル情報をセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch041knParamModel
     * @param reqMdl リクエスト情報
     * @throws IOToolsException 添付ファイル情報の取得に失敗
     */
    private void __setFileInfo(
            Sch041knParamModel paramMdl, RequestModel reqMdl) throws IOToolsException {

        SchCommonBiz schBiz = new SchCommonBiz();
        String tempDir = schBiz.getTempDir(reqMdl, GSConstSchedule.SCR_ID_SCH041);
        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setSch041knFileList(cmnBiz.getTempFileLabelList(tempDir));
    }

    /**
     * <br>[機  能] 削除時の確認画面用にDBに登録されている施設予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return String[] 施設SIDの配列
     */
    public String[] setSch041knReservesFromDb(Sch041knParamModel paramMdl, Connection con)
    throws SQLException {
        String[] reserves = null;

        //管理者設定を取得
        SchCommonBiz cBiz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel adminConf = cBiz.getAdmConfModel(con);
        int scdSid = Integer.parseInt(paramMdl.getSch010SchSid());
        Sch040Biz biz = new Sch040Biz(con, reqMdl__);
        ScheduleSearchModel scdMdl = biz.getSchData(scdSid, adminConf, con);
        if (scdMdl == null) {
            return null;
        }
        int sceSid = scdMdl.getSceSid();
        reserves = biz.getSaveReserveForDbEx(sceSid, con);

        return reserves;
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
     * <br>スケジュールを新規登録します
     * @param paramMdl Sch041knParamModel
     * @param cntCon 採番コントローラ
     * @param userSid 登録者SID
     * @param appRootPath アプリケーションRoot
     * @param plconf プラグイン設定
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエスト情報
     * @throws Exception SQL実行時例外
     */
    public void insertScheduleData(
            Sch041knParamModel paramMdl,
            MlCountMtController cntCon,
            int userSid,
            String appRootPath,
            PluginConfig plconf,
            boolean smailPluginUseFlg,
            RequestModel reqMdl) throws Exception {

        UDate now = new UDate();
        SchDataModel schMdl = null;
        SchCommonBiz schBiz = new SchCommonBiz(con__, reqMdl);
        CommonBiz cmnBiz = new CommonBiz();

        //登録日付
        HashMap<String, UDate> addDateMap = getInsertDateList(paramMdl, userSid, con__);
        ArrayList<UDate> dateList = new ArrayList<UDate>(addDateMap.values());
        //登録予定日付
        ArrayList<String> strDateList = __getStrDateListFromMap(addDateMap);
        //登録モデルを作成
        schMdl = new SchDataModel();
        if (paramMdl.getSch041TimeKbn().equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {
            schMdl.setScdDaily(GSConstSchedule.TIME_EXIST);
        } else {
            schMdl.setScdDaily(GSConstSchedule.TIME_NOT_EXIST);
        }

        schMdl.setScdBgcolor(
                NullDefault.getInt(paramMdl.getSch041Bgcolor(), GSConstSchedule.DF_BG_COLOR));
        schMdl.setScdTitle(paramMdl.getSch041Title());
        schMdl.setScdValue(paramMdl.getSch041Value());
        schMdl.setScdBiko(paramMdl.getSch041Biko());
        schMdl.setScdPublic(
                NullDefault.getInt(paramMdl.getSch041Public(), GSConstSchedule.DSP_PUBLIC));
        schMdl.setScdAuid(userSid);
        schMdl.setScdAdate(now);
        schMdl.setScdEuid(userSid);
        schMdl.setScdEdate(now);
        //編集区分
        schMdl.setScdEdit(
                NullDefault.getInt(paramMdl.getSch041Edit(), GSConstSchedule.EDIT_CONF_NONE));
        //拡張登録SID
        int extSid = GSConstSchedule.DF_SCHGP_ID;
        schMdl.setSceSid(extSid);
        String[] svReserves = paramMdl.getSch041SvReserve();

        String[] svUsers = paramMdl.getSch041SvUsers();
        //スケジュールグループSID（同時登録有りの場合）
        int scdGpSid = GSConstSchedule.DF_SCHGP_ID;
        int scdResSid = GSConstSchedule.DF_SCHGP_ID;
        int scdSid = GSConstSchedule.DF_SCHGP_ID;
        schMdl.setScdGrpSid(scdGpSid);
        schMdl.setScdRsSid(scdResSid);
        schMdl.setScdSid(scdSid);

        int frHour = GSConstSchedule.DAY_START_HOUR;
        int frMin = GSConstSchedule.DAY_START_MINUTES;
        int toHour = GSConstSchedule.DAY_END_HOUR;
        int toMin = GSConstSchedule.DAY_END_MINUTES;
        if (paramMdl.getSch041TimeKbn().equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {
            frHour = Integer.parseInt(paramMdl.getSch041FrHour());
            frMin = Integer.parseInt(paramMdl.getSch041FrMin());
            toHour = Integer.parseInt(paramMdl.getSch041ToHour());
            toMin = Integer.parseInt(paramMdl.getSch041ToMin());
        }
        UDate frDate = now.cloneUDate();
        UDate toDate = frDate.cloneUDate();

        if (frHour != -1 && frMin != -1) {
            frDate.setZeroHhMmSs();
            frDate.setHour(frHour);
            frDate.setMinute(frMin);
        }

        if (toHour != -1 && toMin != -1) {
            toDate.setZeroHhMmSs();
            toDate.setHour(toHour);
            toDate.setMinute(toMin);
        }
        schMdl.setScdFrDate(frDate);
        schMdl.setScdToDate(toDate);


        schMdl.setScdUsrSid(Integer.parseInt(paramMdl.getSch010SelectUsrSid()));
        schMdl.setScdUsrKbn(Integer.parseInt(paramMdl.getSch010SelectUsrKbn()));
        schMdl.setScdReminder(
                    NullDefault.getInt(
                            paramMdl.getSch041ReminderTime(),
                            GSConstSchedule.REMINDER_TIME_NO)
                    );
        schMdl.setScdTargetGrp(
                    NullDefault.getInt(
                            paramMdl.getSch041TargetGroup(),
                            GSConstSchedule.REMINDER_USE_NO)
                    );

        ISchRegister.Builder regBld;
        regBld = ISchRegister.kurikaesiRegistBuilder(con__, reqMdl, cntCon, schMdl, dateList,
                __createSchExModel(paramMdl));
        //添付ファイルの登録
        String tempDir = schBiz.getTempDir(reqMdl, GSConstSchedule.SCR_ID_SCH041);
        List<Long> binSidList = cmnBiz.insertBinInfo(
                con__, tempDir, appRootPath, cntCon, userSid, new UDate())
                .stream()
                .map(str -> Long.parseLong(str))
                .collect(Collectors.toList());
        regBld.setBinSidList(binSidList);

        //公開対象の登録
        if (schMdl.getScdPublic() == GSConstSchedule.DSP_USRGRP) {
            UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con__);
            UserBiz userBiz = new UserBiz();

            ArrayList<Integer> grpSids = new ArrayList<Integer>();
            List<String> usrSids = new ArrayList<String>();
            for (String target : paramMdl.getSch041DisplayTarget()) {
                if (target.startsWith("G")) {
                    grpSids.add(NullDefault.getInt(
                            target.substring(1), -1));
                } else {
                    if (NullDefault.getInt(
                            target, -1) > GSConstUser.USER_RESERV_SID) {
                        usrSids.add(target);
                    }
                }
            }
            ArrayList<GroupModel> glist = new ArrayList<GroupModel>();
            ArrayList<BaseUserModel> ulist = new ArrayList<BaseUserModel>();
            //グループ存在チェック
            if (!grpSids.isEmpty()) {
                glist = gdao.selectGroupNmListOrderbyClass(grpSids);
            }
            //ユーザ存在チェック
            if (!usrSids.isEmpty()) {
                ulist = userBiz.getBaseUserList(con__,
                                                usrSids.toArray(new String[usrSids.size()]));
            }
            String[] checkPubUsrGrpSid = new String[glist.size() + ulist.size()];
            int i = 0;
            for (GroupModel gMdl : glist) {
                checkPubUsrGrpSid[i] = "G" + gMdl.getGroupSid();
                i++;
            }
            for (BaseUserModel uMdl : ulist) {
                checkPubUsrGrpSid[i] = String.valueOf(uMdl.getUsrsid());
                i++;
            }
            paramMdl.setSch041DisplayTarget(checkPubUsrGrpSid);
            regBld.setPubList(
                Stream.of(paramMdl.getSch041DisplayTarget())
                .map(targetSid -> {
                    SchDataPubModel sdpMdl = new SchDataPubModel();
                    if (targetSid.startsWith("G")) {
                        sdpMdl.setSdpType(GSConstSchedule.SDP_TYPE_GROUP);
                        sdpMdl.setSdpPsid(Integer.parseInt(targetSid.substring(1)));
                    } else {
                        sdpMdl.setSdpType(GSConstSchedule.SDP_TYPE_USER);
                        sdpMdl.setSdpPsid(Integer.parseInt(targetSid));
                    }
                    return sdpMdl;
                })
                .collect(Collectors.toList())
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
        regBld.setUseContact((paramMdl.getSch041contact() == 1));
        regBld.setAdrSidArr(paramMdl.getSch041AddressId());
        regBld.setAbaSidArr(paramMdl.getSch041CompanyBaseSid());
        regBld.setAcoSidArr(paramMdl.getSch041CompanySid());

        regBld.setUseRsv(svReserves != null && svReserves.length > 0);

        //スケジュール登録ロジッククラス設定完了
        ISchRegister reg = regBld.build();

        //スケジュール・関連情報登録実行
        reg.regist();

        //ショートメール通知
        if (smailPluginUseFlg) {
            Set<Integer> svUserList = new HashSet<>(
                         Stream.of(svUsers)
                             .map(Integer::valueOf)
                             .filter(sid -> sid != userSid)
                             .collect(Collectors.toSet())
                    );
            //対象ユーザを追加
            int selectUsrSid = Integer.parseInt(paramMdl.getSch010SelectUsrSid());
            if (!svUserList.contains(selectUsrSid)
                    && userSid != selectUsrSid) {
                svUserList.add(Integer.parseInt(paramMdl.getSch010SelectUsrSid()));
            }
            for (Entry<SchDataGroupModel, Map<Integer, Integer>> entGrp
                    : reg.getScdSidMap().entrySet()) {
                SchDataGroupModel grp = entGrp.getKey();
                for (Entry<Integer, Integer> entry : entGrp.getValue().entrySet()) {
                    int addUserSid = entry.getKey();
                    SchDataModel smlBaseSch = reg.getSchModel(grp, addUserSid);
                    //URL取得
                    String url;
                    //選択登録先
                    if (addUserSid == userSid) {
                        continue;
                    } else if (!svUserList.contains(addUserSid)) {
                        break;
                    }
                    //同時登録
                    //URL取得
                    url = __createScheduleUrlRepeat(reqMdl, GSConstSchedule.CMD_EDIT,
                            String.valueOf(smlBaseSch.getScdSid()),
                            String.valueOf(smlBaseSch.getScdUsrSid()),
                            paramMdl);
                    schBiz.sendPlgSmailEx(cntCon, smlBaseSch, appRootPath,
                            plconf, strDateList, smailPluginUseFlg, url);
                    svUserList.remove(smlBaseSch.getScdUsrSid());
                }
                if (svUserList.size() == 0) {
                    break;
                }
            }
        }

        ArrayList<int []> sidDataList = new ArrayList<int []>();
        //施設予約を登録する場合
        if (svReserves != null && svReserves.length > 0) {
            RsvSisYrkModel yrkParam = __createRsyMdl(schMdl);
            yrkParam.setRsdSid(
                    Stream.of(svReserves)
                        .map(str -> Integer.parseInt(str))
                        .findAny().get());
            IRsvYoyakuRegister.Builder regRsvBld =
                    IRsvYoyakuRegister.kurikaesiRegistBuilder(
                            con__, reqMdl, cntCon, appRootPath, yrkParam, dateList,
                            __createRsvRyrkModel(paramMdl));
            regRsvBld.setUseSch(true);
            regRsvBld.setSchResSidMap(
                    reg.getScdGrpsList().stream()
                        .collect(Collectors.toMap(
                                grp -> grp.getTargetDate().getDateString("/"),
                                grp -> grp.getScdResSid()))
                    );
            regRsvBld.setRsdSids(Stream.of(svReserves)
                        .map(str -> Integer.parseInt(str))
                        .collect(Collectors.toSet()));

            //公開対象の登録
            if (Objects.equals(yrkParam.getRsyPublic(), GSConstReserve.PUBLIC_KBN_USRGRP)) {
                regRsvBld.setPubList(
                    Stream.of(paramMdl.getSch041DisplayTarget())
                        .map(targetSid -> {
                            RsvDataPubModel rdpMdl = new RsvDataPubModel();
                            if (targetSid.startsWith("G")) {
                                rdpMdl.setRdpType(GSConstSchedule.SDP_TYPE_GROUP);
                                rdpMdl.setRdpPsid(Integer.parseInt(targetSid.substring(1)));
                            } else {
                                rdpMdl.setRdpType(GSConstSchedule.SDP_TYPE_USER);
                                rdpMdl.setRdpPsid(Integer.parseInt(targetSid));
                            }
                            return rdpMdl;
                        })
                        .collect(Collectors.toList())
                    );

            }
            //施設予約を登録
            IRsvYoyakuRegister regRsv = regRsvBld.build();
            regRsv.regist();
            sidDataList.addAll(
                regRsv.getRsySidMap().values().stream()
                    .flatMap(map -> map.entrySet().stream())
                    .map(entry -> new int []{entry.getValue(), entry.getKey()})
                    .collect(Collectors.toList())
                );

        }
    }
    /**
     *
     * <br>[機  能] 施設予約拡張データを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @return 施設予約拡張データ
     */
    private RsvSisRyrkModel __createRsvRyrkModel(Sch041knParamModel paramMdl) {
        int userSid = reqMdl__.getSmodel().getUsrsid();
        RsvSisRyrkModel ret = new RsvSisRyrkModel();
        UDate now = new UDate();
        //区分
        ret.setRsrKbn(Integer.parseInt(paramMdl.getSch041ExtKbn()));
        String[] weeks = paramMdl.getSch041Dweek();
        ret.setRsrDweek1(0);
        ret.setRsrDweek2(0);
        ret.setRsrDweek3(0);
        ret.setRsrDweek4(0);
        ret.setRsrDweek5(0);
        ret.setRsrDweek6(0);
        ret.setRsrDweek7(0);
        if (weeks != null) {
            for (String week : weeks) {
                int intWeek = Integer.parseInt(week);
                switch (intWeek) {
                case UDate.SUNDAY:
                    ret.setRsrDweek1(1);
                    break;
                case UDate.MONDAY:
                    ret.setRsrDweek2(1);
                    break;
                case UDate.TUESDAY:
                    ret.setRsrDweek3(1);
                    break;
                case UDate.WEDNESDAY:
                    ret.setRsrDweek4(1);
                    break;
                case UDate.THURSDAY:
                    ret.setRsrDweek5(1);
                    break;
                case UDate.FRIDAY:
                    ret.setRsrDweek6(1);
                    break;
                case UDate.SATURDAY:
                    ret.setRsrDweek7(1);
                    break;
                default:
                    break;
                }
            }
        }
        //日
        ret.setRsrDay(NullDefault.getInt(paramMdl.getSch041Day(), 0));
        //週
        ret.setRsrWeek(NullDefault.getInt(paramMdl.getSch041Week(), 0));
        //月
        ret.setRsrMonthYearly(NullDefault.getInt(paramMdl.getSch041MonthOfYearly(), 0));
        //日
        ret.setRsrDayYearly(NullDefault.getInt(paramMdl.getSch041DayOfYearly(), 0));

        //時分from
        UDate frDate = new UDate();
        UDate toDate = frDate.cloneUDate();
        int frYear = Integer.parseInt(paramMdl.getSch041FrYear());
        int frMonth = Integer.parseInt(paramMdl.getSch041FrMonth());
        int frDay = Integer.parseInt(paramMdl.getSch041FrDay());
        int frHour = Integer.parseInt(paramMdl.getSch041FrHour());
        int frMin = Integer.parseInt(paramMdl.getSch041FrMin());
        frDate.setDate(frYear, frMonth, frDay);
        frDate.setZeroHhMmSs();
        frDate.setHour(frHour);
        frDate.setMinute(frMin);
        int toYear = Integer.parseInt(paramMdl.getSch041ToYear());
        int toMonth = Integer.parseInt(paramMdl.getSch041ToMonth());
        int toDay = Integer.parseInt(paramMdl.getSch041ToDay());
        int toHour = Integer.parseInt(paramMdl.getSch041ToHour());
        int toMin = Integer.parseInt(paramMdl.getSch041ToMin());
        toDate.setDate(toYear, toMonth, toDay);
        toDate.setZeroHhMmSs();
        toDate.setHour(toHour);
        toDate.setMinute(toMin);
        //時間from
        ret.setRsrTimeFr(frDate);
        //時間to
        ret.setRsrTimeTo(toDate);
        //反映期間from
        ret.setRsrDateFr(frDate);
        //反映期間to
        ret.setRsrDateTo(toDate);
        //振替区分
        ret.setRsrTranKbn(NullDefault.getInt(paramMdl.getSch041TranKbn(), 0));

        //利用目的
        String moku = NullDefault.getString(paramMdl.getSch041Title(), "");
        if (moku.length() > 20) {
            ret.setRsrMok(moku.substring(0, 20));
        } else {
            ret.setRsrMok(moku);
        }
        //内容
        ret.setRsrBiko(NullDefault.getString(paramMdl.getSch041Value(), ""));
        //編集権限設定
        ret.setRsrEdit(Integer.parseInt(paramMdl.getSch041Edit()));
        //登録者ID
        ret.setRsrAuid(userSid);
        //登録日時
        ret.setRsrAdate(now);
        //更新者ID
        ret.setRsrEuid(userSid);
        //更新日時
        ret.setRsrEdate(now);
        //公開区分
        int rsvPublic = GSConstReserve.PUBLIC_KBN_ALL;
        int schPublic = NullDefault.getInt(
                paramMdl.getSch041Public(), GSConstSchedule.DSP_ALL);
        if (schPublic == GSConstSchedule.DSP_NOT_PUBLIC
                || schPublic == GSConstSchedule.DSP_YOTEIARI) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_PLANS;
        } else if (schPublic == GSConstSchedule.DSP_BELONG_GROUP) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_GROUP;
        } else if (schPublic == GSConstSchedule.DSP_USRGRP) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_USRGRP;
        } else if (schPublic == GSConstSchedule.DSP_TITLE) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_TITLE;
        }
        ret.setRsrPublic(rsvPublic);
        return ret;
    }

    /**
     *
     * <br>[機  能] スケジュール拡張データを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl
     * @return 施設予約拡張データ
     */
    private SchExdataModel __createSchExModel(
            Sch041knParamModel paramMdl) {
        int userSid = reqMdl__.getSmodel().getUsrsid();

        SchExdataModel exMdl = new SchExdataModel();
        exMdl.setSceKbn(Integer.parseInt(paramMdl.getSch041ExtKbn()));
        String[] weeks = paramMdl.getSch041Dweek();
        exMdl.setSceDweek1(0);
        exMdl.setSceDweek2(0);
        exMdl.setSceDweek3(0);
        exMdl.setSceDweek4(0);
        exMdl.setSceDweek5(0);
        exMdl.setSceDweek6(0);
        exMdl.setSceDweek7(0);
        int weekDayFlg = Integer.parseInt(paramMdl.getSch041WeekOrDay());

        if (weeks != null) {
            if ((exMdl.getSceKbn() == GSConstSchedule.EXTEND_KBN_MONTH
                    && weekDayFlg == GSConstSchedule.EXTEND_MONTH_WEEK)
                    || exMdl.getSceKbn() == GSConstSchedule.EXTEND_KBN_BIWEEK
                    || exMdl.getSceKbn() == GSConstSchedule.EXTEND_KBN_WEEK) {
                for (String week : weeks) {
                    int intWeek = Integer.parseInt(week);
                    switch (intWeek) {
                    case UDate.SUNDAY:
                        exMdl.setSceDweek1(1);
                        break;
                    case UDate.MONDAY:
                        exMdl.setSceDweek2(1);
                        break;
                    case UDate.TUESDAY:
                        exMdl.setSceDweek3(1);
                        break;
                    case UDate.WEDNESDAY:
                        exMdl.setSceDweek4(1);
                        break;
                    case UDate.THURSDAY:
                        exMdl.setSceDweek5(1);
                        break;
                    case UDate.FRIDAY:
                        exMdl.setSceDweek6(1);
                        break;
                    case UDate.SATURDAY:
                        exMdl.setSceDweek7(1);
                        break;
                    default:
                        break;
                    }
                }
            }
        }
        if (exMdl.getSceKbn() == GSConstSchedule.EXTEND_KBN_MONTH
                && weekDayFlg == GSConstSchedule.EXTEND_MONTH_WEEK) {
            exMdl.setSceWeek(NullDefault.getInt(paramMdl.getSch041Week(), 0));
        }
        if (exMdl.getSceKbn() == GSConstSchedule.EXTEND_KBN_MONTH
                && weekDayFlg == GSConstSchedule.EXTEND_MONTH_DAY) {
            exMdl.setSceDay(NullDefault.getInt(paramMdl.getSch041Day(), 0));
            exMdl.setScePeriodKbn(NullDefault.getInt(paramMdl.getSch041ConfKbn(), 0));
            exMdl.setSceDaysMonth(NullDefault.getInt(paramMdl.getSch041DayOfMonth(), 0));
        }
        if (exMdl.getSceKbn() == GSConstSchedule.EXTEND_KBN_YEAR) {
            exMdl.setSceMonthOfYearly(NullDefault.getInt(paramMdl.getSch041MonthOfYearly(), 0));
            exMdl.setSceDayOfYearly(NullDefault.getInt(paramMdl.getSch041DayOfYearly(), 0));
        }

        exMdl.setSceTranKbn(NullDefault.getInt(paramMdl.getSch041TranKbn(), 0));
        UDate frDate = new UDate();
        UDate toDate = new UDate();
        int frYear = Integer.parseInt(paramMdl.getSch041FrYear());
        int frMonth = Integer.parseInt(paramMdl.getSch041FrMonth());
        int frDay = Integer.parseInt(paramMdl.getSch041FrDay());

        int frHour = GSConstSchedule.DAY_START_HOUR;
        int frMin = GSConstSchedule.DAY_START_MINUTES;
        int toHour = GSConstSchedule.DAY_END_HOUR;
        int toMin = GSConstSchedule.DAY_END_MINUTES;
        if (paramMdl.getSch041TimeKbn().equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {
            frHour = Integer.parseInt(paramMdl.getSch041FrHour());
            frMin = Integer.parseInt(paramMdl.getSch041FrMin());
            toHour = Integer.parseInt(paramMdl.getSch041ToHour());
            toMin = Integer.parseInt(paramMdl.getSch041ToMin());
        }

        frDate.setDate(frYear, frMonth, frDay);
        frDate.setZeroHhMmSs();
        frDate.setHour(frHour);
        frDate.setMinute(frMin);
        int toYear = Integer.parseInt(paramMdl.getSch041ToYear());
        int toMonth = Integer.parseInt(paramMdl.getSch041ToMonth());
        int toDay = Integer.parseInt(paramMdl.getSch041ToDay());
        toDate.setDate(toYear, toMonth, toDay);
        toDate.setZeroHhMmSs();
        toDate.setHour(toHour);
        toDate.setMinute(toMin);

        exMdl.setSceDateFr(frDate);
        exMdl.setSceDateTo(toDate);
        exMdl.setSceTimeFr(frDate);
        exMdl.setSceTimeTo(toDate);
        exMdl.setSceBgcolor(
                NullDefault.getInt(
                        paramMdl.getSch041Bgcolor(),
                        GSConstSchedule.DF_BG_COLOR));

        exMdl.setSceTitle(paramMdl.getSch041Title());
        exMdl.setSceValue(paramMdl.getSch041Value());
        exMdl.setSceBiko(paramMdl.getSch041Biko());
        exMdl.setScePublic(NullDefault.getInt(
                paramMdl.getSch041Public(), GSConstSchedule.DSP_PUBLIC));
        exMdl.setSceEdit(Integer.parseInt(paramMdl.getSch041Edit()));
        exMdl.setSceDaily(NullDefault.getInt(
                paramMdl.getSch041TimeKbn(), GSConstSchedule.TIME_EXIST));
        exMdl.setSceAuid(userSid);
        exMdl.setSceAdate(new UDate());
        exMdl.setSceEuid(userSid);
        exMdl.setSceEdate(new UDate());
        exMdl.setSceReminder(NullDefault.getInt(
                paramMdl.getSch041ReminderTime(), GSConstSchedule.REMINDER_TIME_NO));
        exMdl.setSceTargetGrp(NullDefault.getInt(
                paramMdl.getSch041TargetGroup(), GSConstSchedule.REMINDER_USE_NO));

        return exMdl;

    }
    /**
     * <br>[機  能] 登録用施設予約モデルの生成
     * <br>[解  説]
     * <br>[備  考]
     * @param baseMdl 生成元 スケジュールモデル
     * @return 登録用施設予約モデル
     */
    private RsvSisYrkModel __createRsyMdl(SchDataModel baseMdl) {
        RsvSisYrkModel yrkParam = new RsvSisYrkModel();
        String moku = NullDefault.getString(baseMdl.getScdTitle(), "");
        yrkParam.setRsyMok(moku);
        yrkParam.setRsyFrDate(baseMdl.getScdFrDate());
        yrkParam.setRsyToDate(baseMdl.getScdToDate());
        yrkParam.setRsyBiko(NullDefault.getString(baseMdl.getScdValue(), ""));
        yrkParam.setRsyAuid(baseMdl.getScdEuid());
        yrkParam.setRsyAdate(baseMdl.getScdEdate());
        yrkParam.setRsyEuid(baseMdl.getScdEuid());
        yrkParam.setRsyEdate(baseMdl.getScdEdate());
        yrkParam.setScdRsSid(GSConstSchedule.DF_SCHGP_ID);
        yrkParam.setRsyEdit(baseMdl.getScdEdit());

        //施設予約と公開区分が異なるので、変換してからセット
        int schPublic = baseMdl.getScdPublic();
        int rsvPublic = GSConstReserve.PUBLIC_KBN_ALL;
        if (schPublic == GSConstSchedule.DSP_NOT_PUBLIC
                || schPublic == GSConstSchedule.DSP_YOTEIARI) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_PLANS;
        } else if (schPublic == GSConstSchedule.DSP_BELONG_GROUP) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_GROUP;
        } else if (schPublic == GSConstSchedule.DSP_USRGRP) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_USRGRP;
        } else if (schPublic == GSConstSchedule.DSP_TITLE) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_TITLE;
        }
        yrkParam.setRsyPublic(rsvPublic);

        return yrkParam;
    }

    /**
     * <br>[機  能] スケジュール拡張情報を登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param extSid スケジュール拡張SID
     * @param paramMdl フォーム
     * @param userSid セッションユーザSID
     * @param con コネクション
     * @param cntCon 採番コントローラ
     * @param tempDir テンポラリディレクトリパス
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     * @throws TempFileException ファイル登録時例外
     */
    public void insertScheduleExData(
            int extSid,
            Sch041knParamModel
            paramMdl,
            int userSid,
            Connection con,
            MlCountMtController cntCon,
            String tempDir,
            String appRootPath) throws SQLException, TempFileException {

        SchExdataModel exMdl = new SchExdataModel();
        exMdl.setSceSid(extSid);
        exMdl.setSceKbn(Integer.parseInt(paramMdl.getSch041ExtKbn()));
        String[] weeks = paramMdl.getSch041Dweek();
        exMdl.setSceDweek1(0);
        exMdl.setSceDweek2(0);
        exMdl.setSceDweek3(0);
        exMdl.setSceDweek4(0);
        exMdl.setSceDweek5(0);
        exMdl.setSceDweek6(0);
        exMdl.setSceDweek7(0);
        int weekDayFlg = Integer.parseInt(paramMdl.getSch041WeekOrDay());

        if (weeks != null) {
            if ((exMdl.getSceKbn() == GSConstSchedule.EXTEND_KBN_MONTH
                    && weekDayFlg == GSConstSchedule.EXTEND_MONTH_WEEK)
                    || exMdl.getSceKbn() == GSConstSchedule.EXTEND_KBN_BIWEEK
                    || exMdl.getSceKbn() == GSConstSchedule.EXTEND_KBN_WEEK) {
                for (String week : weeks) {
                    int intWeek = Integer.parseInt(week);
                    switch (intWeek) {
                    case UDate.SUNDAY:
                        exMdl.setSceDweek1(1);
                        break;
                    case UDate.MONDAY:
                        exMdl.setSceDweek2(1);
                        break;
                    case UDate.TUESDAY:
                        exMdl.setSceDweek3(1);
                        break;
                    case UDate.WEDNESDAY:
                        exMdl.setSceDweek4(1);
                        break;
                    case UDate.THURSDAY:
                        exMdl.setSceDweek5(1);
                        break;
                    case UDate.FRIDAY:
                        exMdl.setSceDweek6(1);
                        break;
                    case UDate.SATURDAY:
                        exMdl.setSceDweek7(1);
                        break;
                    default:
                        break;
                    }
                }
            }
        }
        if (exMdl.getSceKbn() == GSConstSchedule.EXTEND_KBN_MONTH
                && weekDayFlg == GSConstSchedule.EXTEND_MONTH_WEEK) {
            exMdl.setSceWeek(NullDefault.getInt(paramMdl.getSch041Week(), 0));
        }
        if (exMdl.getSceKbn() == GSConstSchedule.EXTEND_KBN_MONTH
                && weekDayFlg == GSConstSchedule.EXTEND_MONTH_DAY) {
            exMdl.setSceDay(NullDefault.getInt(paramMdl.getSch041Day(), 0));
            exMdl.setScePeriodKbn(NullDefault.getInt(paramMdl.getSch041ConfKbn(), 0));
            exMdl.setSceDaysMonth(NullDefault.getInt(paramMdl.getSch041DayOfMonth(), 0));
        }
        if (exMdl.getSceKbn() == GSConstSchedule.EXTEND_KBN_YEAR) {
            exMdl.setSceMonthOfYearly(NullDefault.getInt(paramMdl.getSch041MonthOfYearly(), 0));
            exMdl.setSceDayOfYearly(NullDefault.getInt(paramMdl.getSch041DayOfYearly(), 0));
        }

        exMdl.setSceTranKbn(NullDefault.getInt(paramMdl.getSch041TranKbn(), 0));
        UDate frDate = new UDate();
        UDate toDate = new UDate();
        int frYear = Integer.parseInt(paramMdl.getSch041FrYear());
        int frMonth = Integer.parseInt(paramMdl.getSch041FrMonth());
        int frDay = Integer.parseInt(paramMdl.getSch041FrDay());

        int frHour = GSConstSchedule.DAY_START_HOUR;
        int frMin = GSConstSchedule.DAY_START_MINUTES;
        int toHour = GSConstSchedule.DAY_END_HOUR;
        int toMin = GSConstSchedule.DAY_END_MINUTES;
        if (paramMdl.getSch041TimeKbn().equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {
            frHour = Integer.parseInt(paramMdl.getSch041FrHour());
            frMin = Integer.parseInt(paramMdl.getSch041FrMin());
            toHour = Integer.parseInt(paramMdl.getSch041ToHour());
            toMin = Integer.parseInt(paramMdl.getSch041ToMin());
        }

        frDate.setDate(frYear, frMonth, frDay);
        frDate.setZeroHhMmSs();
        frDate.setHour(frHour);
        frDate.setMinute(frMin);
        int toYear = Integer.parseInt(paramMdl.getSch041ToYear());
        int toMonth = Integer.parseInt(paramMdl.getSch041ToMonth());
        int toDay = Integer.parseInt(paramMdl.getSch041ToDay());
        toDate.setDate(toYear, toMonth, toDay);
        toDate.setZeroHhMmSs();
        toDate.setHour(toHour);
        toDate.setMinute(toMin);

        exMdl.setSceDateFr(frDate);
        exMdl.setSceDateTo(toDate);
        exMdl.setSceTimeFr(frDate);
        exMdl.setSceTimeTo(toDate);
        exMdl.setSceBgcolor(
                NullDefault.getInt(
                        paramMdl.getSch041Bgcolor(),
                        GSConstSchedule.DF_BG_COLOR));

        exMdl.setSceTitle(paramMdl.getSch041Title());
        exMdl.setSceValue(paramMdl.getSch041Value());
        exMdl.setSceBiko(paramMdl.getSch041Biko());
        exMdl.setScePublic(NullDefault.getInt(
                paramMdl.getSch041Public(), GSConstSchedule.DSP_PUBLIC));
        exMdl.setSceEdit(Integer.parseInt(paramMdl.getSch041Edit()));
        exMdl.setSceDaily(NullDefault.getInt(
                paramMdl.getSch041TimeKbn(), GSConstSchedule.TIME_EXIST));
        exMdl.setSceAuid(userSid);
        exMdl.setSceAdate(new UDate());
        exMdl.setSceEuid(userSid);
        exMdl.setSceEdate(new UDate());
        SchExdataDao exDao = new SchExdataDao(con);
        exDao.insert(exMdl);

        //添付ファイルの登録
        CommonBiz cmnBiz = new CommonBiz();
        List<String> binSidList = cmnBiz.insertBinInfo(
                con, tempDir, appRootPath, cntCon, userSid, new UDate());
        SchExdataBinDao sbeDao = new SchExdataBinDao(con);
        SchExdataBinModel sbeMdl = new SchExdataBinModel();
        sbeMdl.setSceSid(extSid);
        for (String binSid : binSidList) {
            sbeMdl.setBinSid(Integer.parseInt(binSid));
            sbeDao.insert(sbeMdl);
        }

        //公開対象の登録
        String [] targetSidAry = paramMdl.getSch041DisplayTarget();
        if (exMdl.getScePublic() == GSConstSchedule.DSP_USRGRP && targetSidAry != null) {
            SchExdataPubDao sepDao = new SchExdataPubDao(con__);
            SchExdataPubModel sepMdl = new SchExdataPubModel();
            sepMdl.setSceSid(extSid);
            for (String targetSid : targetSidAry) {
                if (targetSid.startsWith("G")) {
                    sepMdl.setSepType(GSConstSchedule.SDP_TYPE_GROUP);
                    sepMdl.setSepPsid(Integer.parseInt(targetSid.substring(1)));
                } else {
                    sepMdl.setSepType(GSConstSchedule.SDP_TYPE_USER);
                    sepMdl.setSepPsid(Integer.parseInt(targetSid));
                }
                sepDao.insert(sepMdl);
            }
        }

        //スケジュール拡張情報-会社情報Mappingを登録
        __insertExSchCompany(
                con, cntCon, paramMdl, exMdl.getSceSid(), userSid, exMdl.getSceEdate());
    }

    /**
     * <br>[機  能] 施設予約拡張情報を登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param extSid スケジュール拡張SID
     * @param paramMdl フォーム
     * @param userSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void insertReserveExData(
            int extSid, Sch041knParamModel paramMdl, int userSid, Connection con)
    throws SQLException {

        RsvSisRyrkModel ret = new RsvSisRyrkModel();
        UDate now = new UDate();
        //施設予約拡張SID
        ret.setRsrRsid(extSid);
        //区分
        ret.setRsrKbn(Integer.parseInt(paramMdl.getSch041ExtKbn()));
        String[] weeks = paramMdl.getSch041Dweek();
        ret.setRsrDweek1(0);
        ret.setRsrDweek2(0);
        ret.setRsrDweek3(0);
        ret.setRsrDweek4(0);
        ret.setRsrDweek5(0);
        ret.setRsrDweek6(0);
        ret.setRsrDweek7(0);
        if (weeks != null) {
            for (String week : weeks) {
                int intWeek = Integer.parseInt(week);
                switch (intWeek) {
                case UDate.SUNDAY:
                    ret.setRsrDweek1(1);
                    break;
                case UDate.MONDAY:
                    ret.setRsrDweek2(1);
                    break;
                case UDate.TUESDAY:
                    ret.setRsrDweek3(1);
                    break;
                case UDate.WEDNESDAY:
                    ret.setRsrDweek4(1);
                    break;
                case UDate.THURSDAY:
                    ret.setRsrDweek5(1);
                    break;
                case UDate.FRIDAY:
                    ret.setRsrDweek6(1);
                    break;
                case UDate.SATURDAY:
                    ret.setRsrDweek7(1);
                    break;
                default:
                    break;
                }
            }
        }
        //日
        ret.setRsrDay(NullDefault.getInt(paramMdl.getSch041Day(), 0));
        //週
        ret.setRsrWeek(NullDefault.getInt(paramMdl.getSch041Week(), 0));
        //月
        ret.setRsrMonthYearly(NullDefault.getInt(paramMdl.getSch041MonthOfYearly(), 0));
        //日
        ret.setRsrDayYearly(NullDefault.getInt(paramMdl.getSch041DayOfYearly(), 0));

        //時分from
        UDate frDate = new UDate();
        UDate toDate = frDate.cloneUDate();
        int frYear = Integer.parseInt(paramMdl.getSch041FrYear());
        int frMonth = Integer.parseInt(paramMdl.getSch041FrMonth());
        int frDay = Integer.parseInt(paramMdl.getSch041FrDay());
        int frHour = Integer.parseInt(paramMdl.getSch041FrHour());
        int frMin = Integer.parseInt(paramMdl.getSch041FrMin());
        frDate.setDate(frYear, frMonth, frDay);
        frDate.setZeroHhMmSs();
        frDate.setHour(frHour);
        frDate.setMinute(frMin);
        int toYear = Integer.parseInt(paramMdl.getSch041ToYear());
        int toMonth = Integer.parseInt(paramMdl.getSch041ToMonth());
        int toDay = Integer.parseInt(paramMdl.getSch041ToDay());
        int toHour = Integer.parseInt(paramMdl.getSch041ToHour());
        int toMin = Integer.parseInt(paramMdl.getSch041ToMin());
        toDate.setDate(toYear, toMonth, toDay);
        toDate.setZeroHhMmSs();
        toDate.setHour(toHour);
        toDate.setMinute(toMin);
        //時間from
        ret.setRsrTimeFr(frDate);
        //時間to
        ret.setRsrTimeTo(toDate);
        //反映期間from
        ret.setRsrDateFr(frDate);
        //反映期間to
        ret.setRsrDateTo(toDate);
        //振替区分
        ret.setRsrTranKbn(NullDefault.getInt(paramMdl.getSch041TranKbn(), 0));

        //利用目的
        String moku = NullDefault.getString(paramMdl.getSch041Title(), "");
        if (moku.length() > 20) {
            ret.setRsrMok(moku.substring(0, 20));
        } else {
            ret.setRsrMok(moku);
        }
        //内容
        ret.setRsrBiko(NullDefault.getString(paramMdl.getSch041Value(), ""));
        //編集権限設定
        ret.setRsrEdit(Integer.parseInt(paramMdl.getSch041Edit()));
        //登録者ID
        ret.setRsrAuid(userSid);
        //登録日時
        ret.setRsrAdate(now);
        //更新者ID
        ret.setRsrEuid(userSid);
        //更新日時
        ret.setRsrEdate(now);
        //公開区分
        int rsvPublic = GSConstReserve.PUBLIC_KBN_ALL;
        int schPublic = NullDefault.getInt(
                paramMdl.getSch041Public(), GSConstSchedule.DSP_ALL);
        if (schPublic == GSConstSchedule.DSP_NOT_PUBLIC
                || schPublic == GSConstSchedule.DSP_YOTEIARI) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_PLANS;
        } else if (schPublic == GSConstSchedule.DSP_BELONG_GROUP) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_GROUP;
        } else if (schPublic == GSConstSchedule.DSP_USRGRP) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_USRGRP;
        } else if (schPublic == GSConstSchedule.DSP_TITLE) {
            rsvPublic = GSConstReserve.PUBLIC_KBN_TITLE;
        }
        ret.setRsrPublic(rsvPublic);

        //公開対象の登録
        if (rsvPublic == GSConstReserve.PUBLIC_KBN_USRGRP) {
            String [] targetSidAry = paramMdl.getSch041DisplayTarget();
            RsvExdataPubDao repDao = new RsvExdataPubDao(con);
            RsvExdataPubModel repMdl = new RsvExdataPubModel();
            repMdl.setRsrRsid(extSid);

            for (String targetSid : targetSidAry) {
                if (targetSid.startsWith("G")) {
                    repMdl.setRepType(GSConstSchedule.SDP_TYPE_GROUP);
                    repMdl.setRepPsid(Integer.parseInt(targetSid.substring(1)));
                } else {
                    repMdl.setRepType(GSConstSchedule.SDP_TYPE_USER);
                    repMdl.setRepPsid(Integer.parseInt(targetSid));
                }
                repDao.insert(repMdl);
            }
        }

        RsvSisRyrkDao ryrkDao = new RsvSisRyrkDao(con);
        ryrkDao.insert(ret);
    }

    /**
     * <br>スケジュールを更新します
     * @param paramMdl Sch041knParamModel
     * @param cntCon 採番コントローラ
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションRoot
     * @param plconf プラグイン設定
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエスト情報
     * @param oldMdl 編集前データ
     * @return 更新件数
     * @throws Exception SQL実行時例外
     */
    public int updateScheduleDate(Sch041knParamModel paramMdl,
            MlCountMtController cntCon,
            int userSid,
            String appRootPath,
            PluginConfig plconf,
            boolean smailPluginUseFlg,
            RequestModel reqMdl,
            ScheduleSearchModel oldMdl) throws Exception {

        //管理者設定を取得
        SchCommonBiz biz = new SchCommonBiz(reqMdl);
        SchAdmConfModel adminConf = biz.getAdmConfModel(con__);
        int cnt = 0;

        //スケジュールを新規登録
        insertScheduleData(
                paramMdl, cntCon, userSid, appRootPath, plconf, smailPluginUseFlg, reqMdl);

        Sch040Biz sch040biz = new Sch040Biz(con__, reqMdl);
        int scdSid = Integer.parseInt(paramMdl.getSch010SchSid());
        //旧同時登録施設予約を削除
        deleteReserves(scdSid, con__, oldMdl);

        int sceSid = oldMdl.getSceSid();
        SchDataDao scdDao = new SchDataDao(con__);
        ScheduleSearchDao ssDao = new ScheduleSearchDao(con__);
        SchCommonBiz cmnBiz = new SchCommonBiz(con__, reqMdl);

        SchBinDao binDao = new SchBinDao(con__);

        List<Integer> delScdList = new ArrayList<Integer>();
        if (sceSid != GSConstSchedule.DEF_SCE_SID) {
            __deleteSchMapping(con__, sceSid, paramMdl.getSch041contact(), userSid);

            //拡張SIDを指定し、スケジュール情報を削除
            ArrayList<Integer> scds = ssDao.getExScheduleUsrs(
                    sceSid,
                    userSid,
                    GSConstSchedule.SSP_AUTHFILTER_EDIT);


            scdDao.delete(scds);
            cmnBiz.deletePushList(scds);

            //添付ファイル情報を削除
            binDao.deleteTempFile(scds);

            //スケジュール公開対象を削除
            SchDataPubDao sdpDao = new SchDataPubDao(con__);
            sdpDao.delete(scds);

            //スケジュール拡張情報を削除
            __deleteSchExData(con__, sceSid, userSid);

        } else {
            ArrayList<Integer> scds = new ArrayList<>(
                Stream.concat(
                            ssDao.getScheduleUsrs(
                                scdSid,
                                userSid,
                                adminConf.getSadCrange(),
                                GSConstSchedule.SSP_AUTHFILTER_EDIT
                                ).stream(),
                            Stream.of(scdSid)
                        )
                    .collect(Collectors.toSet()));

            cnt = scdDao.delete(scds);
            cmnBiz.deletePushList(scds);
            //添付ファイル情報を削除
            binDao.deleteTempFile(scds);

            //スケジュール公開対象を削除
            SchDataPubDao sdpDao = new SchDataPubDao(con__);
            sdpDao.delete(scds);

            delScdList.addAll(scds);

            sch040biz.deleteSchCompany(con__, delScdList, 0);
        }

        return cnt;
    }

    /**
     *
     * <br>[機  能]スケジュール編集時に、施設予約登録者IDおよび登録日時を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @param svReserve 施設SID
     * @param yrkParam 施設予約モデル
     * @param now 実行時間
     * @throws SQLException SQL実行時例外
     *
     */
    public void yrkAddDataEdit(
            Sch041knParamModel paramMdl,
            RequestModel reqMdl,
            String svReserve,
            RsvSisYrkModel yrkParam,
            UDate now
            ) throws SQLException {

        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con__);
            //編集前スケジュールを取得
            SchCommonBiz schBiz = new SchCommonBiz(reqMdl);
            SchAdmConfModel adminConf = schBiz.getAdmConfModel(con__);
            Sch040Biz sch040Biz = new Sch040Biz(con__, reqMdl);
            ScheduleSearchModel oldMdl = sch040Biz.getSchData(
                    Integer.parseInt(paramMdl.getSch010SchSid()), adminConf, con__);
            //編集前施設予約を取得
            if (oldMdl.getScdRsSid() > 0) {
                RsvSisYrkModel oldYrkParam = yrkDao.select(
                        Integer.parseInt(svReserve), oldMdl.getScdRsSid());
                if (oldYrkParam != null) {
                    yrkParam.setRsyAuid(oldYrkParam.getRsyAuid());
                    yrkParam.setRsyAdate(oldYrkParam.getRsyAdate());
                    return;
                }
            }
            yrkParam.setRsyAuid(reqMdl.getSmodel().getUsrsid());
            yrkParam.setRsyAdate(now);

    }

    /**
     *
     * <br>[機  能]スケジュール編集時に、施設予約登録者IDおよび登録日時を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @param svReserve 施設SID
     * @param kyrkMdl 施設予約区分別情報モデル
     * @param now 実行時間
     * @throws SQLException SQL実行時例外
     *
     */
    public void kyrkAddDataEdit(
            Sch041knParamModel paramMdl,
            RequestModel reqMdl,
            String svReserve,
            RsvSisKyrkModel kyrkMdl, UDate now
            ) throws SQLException {

        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con__);
            //編集前スケジュールを取得
            SchCommonBiz schBiz = new SchCommonBiz(reqMdl);
            SchAdmConfModel adminConf = schBiz.getAdmConfModel(con__);
            Sch040Biz sch040Biz = new Sch040Biz(con__, reqMdl);
            ScheduleSearchModel oldMdl = sch040Biz.getSchData(
                    Integer.parseInt(paramMdl.getSch010SchSid()), adminConf, con__);
            //編集前施設予約区分情報を取得
            if (oldMdl.getScdRsSid() > 0) {
                RsvSisYrkModel oldYrkParam = yrkDao.select(
                        Integer.parseInt(svReserve), oldMdl.getScdRsSid());
                RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con__);
                if (oldYrkParam != null) {
                    RsvSisKyrkModel oldKyrkMdl = kyrkDao.select(oldYrkParam.getRsySid());
                    if (oldKyrkMdl != null) {
                        kyrkMdl.setRkyAuid(oldKyrkMdl.getRkyAuid());
                        kyrkMdl.setRkyAdate(oldKyrkMdl.getRkyAdate());
                        return;
                    }
                }
            }
            kyrkMdl.setRkyAuid(reqMdl.getSmodel().getUsrsid());
            kyrkMdl.setRkyAdate(now);

    }

    /**
     * <br>スケジュール拡張情報を削除(物理削除)します
     * @param sceSid スケジュール拡張SID
     * @param con コネクション
     * @param sessionUserSid セッションユーザSID
     * @return 削除レコード件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteScheduleEx(int sceSid, Connection con, int sessionUserSid)
    throws SQLException {

        __deleteSchMapping(con, sceSid, 0, sessionUserSid);

        int cnt = 0;
        SchDataDao scdDao = new SchDataDao(con);
//        scdDao.extendDataDelete(sceSid);
        ScheduleSearchDao ssDao = new ScheduleSearchDao(con__);
        ArrayList<Integer> scds = ssDao.getExScheduleUsrs(
                sceSid,
                sessionUserSid,
                GSConstSchedule.SSP_AUTHFILTER_EDIT);

        scdDao.delete(scds);

        //スケジュール公開対象を削除
        SchDataPubDao schPubDao = new SchDataPubDao(con__);
        schPubDao.delete(scds);

        //リマインダー通知データの削除
        SchPushListDao splDao = new SchPushListDao(con__);
        splDao.delete(scds);

        //添付ファイル情報を削除
        SchBinDao binDao = new SchBinDao(con__);
        binDao.deleteTempFile(scds);

        //関連するスケジュールの件数が2件未満の場合、スケジュール拡張情報を削除する
        __deleteSchExData(con, sceSid, sessionUserSid);

        return cnt;
    }

    /**
     * <br>施設予約情報を削除(物理削除)します
     * <br>スケジュールSIDで関連付いている予約情報全てを削除します。
     * @param scdSid スケジュール拡張SID
     * @param con コネクション
     * @param oldMdl 編集前データ
     * @return 削除レコード件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteReserves(int scdSid, Connection con,
            ScheduleSearchModel oldMdl) throws SQLException {

        int cnt = 0;
        int sceSid = oldMdl.getSceSid();
        //旧スケジュールに関連付いている予約SIDを取得する
        ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);
        ArrayList<Integer> resList = schRsvDao.getScheduleReserveSidFromExSid(sceSid);
        RsvSisYrkDao rsDao = new RsvSisYrkDao(con);
        if (oldMdl.getScdRsSid() > 0) {
            ArrayList<RsvSisYrkModel> yrkList = rsDao.getScheduleRserveData(oldMdl.getScdRsSid());
            if (yrkList.size() > 0) {
                RsvSisYrkModel yrkMdl = yrkList.get(0);
                //拡張SIDが設定されていれば拡張を削除
                if (yrkMdl.getRsrRsid() > 0) {
                    RsvSisRyrkDao ryrkDao = new RsvSisRyrkDao(con);
                    ryrkDao.delete(yrkMdl.getRsrRsid());

                    //施設予約拡張区分別情報削除
                    RsvSisKryrkDao kryrkDao = new RsvSisKryrkDao(con__);
                    kryrkDao.delete(yrkMdl.getRsrRsid());

                    //施設予約拡張公開情報削除
                    RsvExdataPubDao repDao = new RsvExdataPubDao(con__);
                    repDao.delete(yrkMdl.getRsrRsid());
                }
            }
            for (RsvSisYrkModel yrk : yrkList) {
                resList.add(yrk.getRsySid());
            }

        }
        //予約情報を削除
        cnt = rsDao.deleteRsySid(resList);

        if (resList != null && resList.size() > 0) {
            //施設予約区分別情報を削除
            RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con);
            kyrkDao.delete(resList);

            //施設予約公開情報を削除
            RsvDataPubDao rdpDao = new RsvDataPubDao(con);
            rdpDao.deleteList(resList);
        }

        return cnt;
    }

    /**
     * <br>[機  能] 曜日指定情報をString[]へ設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param model ScheduleExSearchModel
     * @return String[] 設定値配列
     */
    private String[] __getDayWeekStringList(ScheduleExSearchModel model) {

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
        return (String[]) dWeekList.toArray(new String[dWeekList.size()]);
    }

    /**
     * <br>[機  能] スケジュール繰り返し登録確認URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param cmd 処理モード
     * @param sch010SchSid スケジュールSID
     * @param usrSid ユーザーSID
     * @param paramMdl フォーム
     * @return スケジュール繰り返し登録確認URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    private String __createScheduleUrlRepeat(RequestModel reqMdl, String cmd,
                                            String sch010SchSid, String usrSid,
                                            Sch041knParamModel paramMdl)
    throws UnsupportedEncodingException {

        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl__);
            paramUrl +=  "/" + GSConstSchedule.PLUGIN_ID_SCHEDULE;

            paramUrl += "/sch041.do";
            paramUrl += "?sch010SelectDate=" + paramMdl.getSch010SelectDate();
            paramUrl += "&cmd=" + cmd;
            paramUrl += "&sch010SchSid=" + sch010SchSid;
            paramUrl += "&sch010SelectUsrSid=" + usrSid;
            paramUrl += "&sch010SelectUsrKbn=" + paramMdl.getSch010SelectUsrKbn();
            paramUrl += "&sch010DspDate=" + paramMdl.getSch010DspDate();
            paramUrl += "&dspMod=" + paramMdl.getDspMod();
            paramUrl += "&sch010DspGpSid=" + paramMdl.getSch010DspGpSid();
            paramUrl += "&dspKbn=" + GSConstSchedule.LINK_INIT_FLG;

            return urlBiz.getAccessUrl(reqMdl__, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }

    }


    /**
     * <br>[機  能] スケジュール拡張情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sceSid スケジュール拡張SID
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteSchExData(Connection con, int sceSid, int sessionUserSid)
    throws SQLException {

        ScheduleSearchDao schSearchDao = new ScheduleSearchDao(con);
        int exCount = schSearchDao.getExCount(sceSid);
        if (exCount < 2) {
            if (exCount == 1) {
                SchDataDao scdDao = new SchDataDao(con);
                scdDao.updateSceSid(sceSid, -1);
            }

            SchExdataDao sceDao = new SchExdataDao(con);
            sceDao.delete(sceSid);

            SchExdataBinDao sbeDao = new SchExdataBinDao(con);
            sbeDao.deleteTempFile(Set.of(sceSid));

            //公開対象の削除
            SchExdataPubDao sepDao = new SchExdataPubDao(con);
            sepDao.delete(sceSid);

            //他プラグインのMapping情報を削除
            __deleteSchExMapping(con, sceSid, sessionUserSid);
        }

    }

    /**
     * <br>[機  能] 会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sceSid スケジュール拡張SID
     * @param contactFlg コンタクト履歴変更有無
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteSchMapping(Connection con, int sceSid, int contactFlg,
                                                        int sessionUserSid)
    throws SQLException {

        SchCompanyDao companyDao = new SchCompanyDao(con);
        SchAddressDao addressDao = new SchAddressDao(con);
        ScheduleSearchDao schSearchDao = new ScheduleSearchDao(con);
        SchDao dao = new SchDao(con);
        List<Integer> scdSidList = schSearchDao.getExScheduleUsrs(
                sceSid, sessionUserSid, GSConstSchedule.SSP_AUTHFILTER_EDIT);
        for (int scdSid : scdSidList) {
            companyDao.delete(scdSid);

            if (contactFlg == 1) {
                dao.deleteScheduleContact(con, scdSid);
            }
            addressDao.delete(scdSid);
        }
    }

    /**
     * <br>[機  能] 会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sceSid スケジュール拡張SID
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteSchExMapping(Connection con, int sceSid, int sessionUserSid)
    throws SQLException {

        SchExcompanyDao exCompanyDao = new SchExcompanyDao(con);
        exCompanyDao.delete(sceSid);

        SchExaddressDao exAddressDao = new SchExaddressDao(con);
        exAddressDao.delete(sceSid);

    }

    /**
     * <br>[機  能] 会社情報Mapping、アドレス帳情報Mapping、コンタクト履歴の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon 採番コントロール
     * @param paramMdl フォーム
     * @param sceSid スケジュールSID
     * @param userSid セッションユーザSID
     * @param date 更新日付
     * @throws SQLException SQL実行時例外
     */
    private void __insertExSchCompany(Connection con,
                                    MlCountMtController cntCon,
                                    Sch041knParamModel paramMdl,
                                    int sceSid,
                                    int userSid, UDate date)
    throws SQLException {

        //会社情報Mappingを登録
        String[] acoSidList = paramMdl.getSch041CompanySid();
        String[] abaSidList = paramMdl.getSch041CompanyBaseSid();
        if (acoSidList != null && acoSidList.length > 0) {
            SchExcompanyModel exCompanyModel = new SchExcompanyModel();
            exCompanyModel.setSceSid(sceSid);
            exCompanyModel.setSccAuid(userSid);
            exCompanyModel.setSccAdate(date);
            exCompanyModel.setSccEuid(userSid);
            exCompanyModel.setSccEdate(date);

            SchExcompanyDao exCompanyDao = new SchExcompanyDao(con);
            for (int index = 0; index < acoSidList.length; index++) {
                exCompanyModel.setAcoSid(Integer.parseInt(acoSidList[index]));
                exCompanyModel.setAbaSid(Integer.parseInt(abaSidList[index]));
                exCompanyDao.insert(exCompanyModel);
            }
        }

        //アドレス帳情報Mappingを登録する
        String[] adrSidList = paramMdl.getSch041AddressId();
        if (adrSidList != null && adrSidList.length > 0) {

            SchExaddressModel exAdrMdl = new SchExaddressModel();
            exAdrMdl.setSceSid(sceSid);
            exAdrMdl.setSeaAuid(userSid);
            exAdrMdl.setSeaAdate(date);
            exAdrMdl.setSeaEuid(userSid);
            exAdrMdl.setSeaEdate(date);

            SchExaddressDao exAddressDao = new SchExaddressDao(con);
            for (String adrSid : adrSidList) {
                exAdrMdl.setAdrSid(Integer.parseInt(adrSid));
                exAddressDao.insert(exAdrMdl);
            }
        }
    }


    /**
     * <br>[機  能] 表示するリマインダー通知情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException 実行例外
     */
    private void __setPublicTargetData(
            Sch041knParamModel paramMdl, Connection con) throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        String[] targetSidAry = paramMdl.getSch041DisplayTarget();
        if (targetSidAry != null && targetSidAry.length > 0) {
            paramMdl.setSch041TargetList(cmnBiz.getUserLabelList(con, targetSidAry));
        }
    }



    /**
     * <br>[機  能] 表示するリマインダー通知情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException 実行例外
     */
    private void __setReminderData(
            Sch041knParamModel paramMdl) throws SQLException {

        int selectUsrSid = NullDefault.getInt(paramMdl.getSch010SelectUsrSid(), -1);
        BaseUserModel usMdl = reqMdl__.getSmodel();
        int sessionUsrSid = usMdl.getUsrsid();
        int usrKbn = NullDefault.getInt(paramMdl.getSch010SelectUsrKbn(), 0);
        GsMessage gsMsg = new GsMessage(reqMdl__);

        SchEnumRemindMode remindMode = SchEnumRemindMode.valueOf(
                usrKbn,
                sessionUsrSid,
                selectUsrSid);

        paramMdl.setSch041ReminderEditMode(remindMode);

        if (remindMode == SchEnumRemindMode.GROUP) {
            if (Objects.equals(NullDefault.getInt(paramMdl.getSch041TargetGroup(),
                    GSConstSchedule.REMINDER_USE_NO),
                GSConstSchedule.REMINDER_USE_YES)) {
                paramMdl.setSch041knTimeText(gsMsg.getMessage("cmn.notify"));

            } else {
                paramMdl.setSch041knTimeText(gsMsg.getMessage("cmn.dont.notify"));
            }
        } else {
            if (!StringUtil.isNullZeroString(paramMdl.getSch041ReminderTime())) {
                paramMdl.setSch041knTimeText(
                        gsMsg.getMessage(SchEnumReminderTime.valueOf(
                                Integer.parseInt(paramMdl.getSch041ReminderTime()))
                                .getMsgKey()));
            }
        }
    }
}
