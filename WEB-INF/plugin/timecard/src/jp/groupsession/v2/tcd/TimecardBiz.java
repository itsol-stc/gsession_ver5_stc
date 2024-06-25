package jp.groupsession.v2.tcd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.dao.base.TcdAdmConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrInoutModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.dao.TcdDatausedSumDao;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.dao.TcdPriConfDao;
import jp.groupsession.v2.tcd.dao.TcdTcdataDao;
import jp.groupsession.v2.tcd.dao.TcdTimezoneDao;
import jp.groupsession.v2.tcd.dao.TcdTimezonePriDao;
import jp.groupsession.v2.tcd.dao.TcdUserGrpDao;
import jp.groupsession.v2.tcd.dao.TcdYukyuAlertDao;
import jp.groupsession.v2.tcd.dao.TimecardDao;
import jp.groupsession.v2.tcd.excel.ExcelUtil;
import jp.groupsession.v2.tcd.excel.TimeCardXlsParametarModel;
import jp.groupsession.v2.tcd.excel.TimecardLineData;
import jp.groupsession.v2.tcd.excel.WorkReportData;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;
import jp.groupsession.v2.tcd.model.TcdPriConfModel;
import jp.groupsession.v2.tcd.model.TcdTcdataModel;
import jp.groupsession.v2.tcd.model.TcdTimezoneModel;
import jp.groupsession.v2.tcd.model.TcdTimezonePriModel;
import jp.groupsession.v2.tcd.model.TcdTotalValueModel;
import jp.groupsession.v2.tcd.model.TcdUserGrpModel;
import jp.groupsession.v2.tcd.model.TcdYukyudataModel;
import jp.groupsession.v2.tcd.pdf.TcdPdfUtil;
import jp.groupsession.v2.tcd.tcd010.Tcd010Model;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;

/**
 * <br>[機  能] タイムカードプラグインで共通使用するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TimecardBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TimecardBiz.class);

    /** 最大時刻 */
    private static final int MAXIMUM_TIME = 2400;

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public TimecardBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public TimecardBiz() {
    }

    /**
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
     * <br>[機  能] タイムカード個人設定を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return TcdAdmConfModel
     * @throws SQLException SQL実行時例外
     */
    public TcdPriConfModel getTcdPriConfModel(int usrSid, Connection con) throws SQLException {

        log__.debug("タイムカード個人設定取得");
        TcdPriConfDao dao = new TcdPriConfDao(con);
        TcdPriConfModel model = dao.select(usrSid);
        if (model == null) {
            boolean commit = false;
            try {
                //デフォルト値で作成
                model = new TcdPriConfModel(usrSid);
                dao.insert(model);
                commit = true;
            } catch (SQLException e) {
                log__.error("タイムカード個人設定の登録に失敗しました。");
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
     * <br>[機  能] タイムカード時間帯区分のリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return ArrayList
     */
    public ArrayList < LabelValueBean > getZoneLabelList() {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String hour = gsMsg.getMessage("cmn.time");
        String normal = gsMsg.getMessage("tcd.103");
        String zangyo = gsMsg.getMessage("tcd.tcd010.09");
        String shinya = gsMsg.getMessage("tcd.tcd010.06");
        String kyukei = gsMsg.getMessage("tcd.100");

        labelList.add(new LabelValueBean(
                      normal + hour,
                      String.valueOf(GSConstTimecard.TIMEZONE_KBN_NORMAL)));
        labelList.add(new LabelValueBean(
                      zangyo + hour,
                      String.valueOf(GSConstTimecard.TIMEZONE_KBN_ZANGYO)));
        labelList.add(new LabelValueBean(
                      shinya + hour,
                      String.valueOf(GSConstTimecard.TIMEZONE_KBN_SINYA)));
        labelList.add(new LabelValueBean(
                      kyukei + hour,
                      String.valueOf(GSConstTimecard.TIMEZONE_KBN_KYUKEI)));


        return labelList;
    }


    /**
     * <br>[機  能] タイムカード時間帯設定を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param order ソート有無
     * @param con コネクション
     * @return TcdAdmConfModel
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<TcdTimezoneModel> getTimeZoneModel(
            boolean order, Connection con) throws SQLException {
        log__.debug("タイムカード管理者設定取得");
        TcdTimezoneDao dao = new TcdTimezoneDao(con);
        ArrayList<TcdTimezoneModel> list = null;
        list = dao.selectOrder(order);

        return list;
    }

    /**
     * <br>[機  能] 時のリストを取得する。
     * <br>[解  説] 00時～GSConstTimecard.MAX_TIMECARD_HOURまで
     * <br>[備  考]
     * @return ArrayList
     */
    public ArrayList < LabelValueBean > getHourLabelList() {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(" ", String.valueOf(-1))); //未設定
        int hour = 0;
        while (hour <= GSConstTimecard.MAX_TIMECARD_HOUR) {
            labelList.add(new LabelValueBean(
                    StringUtil.toDecFormat(String.valueOf(hour), "00"),
                    String.valueOf(hour)));
            hour++;
        }

        return labelList;
    }

    /**
     * <br>[機  能] 時のリストを取得する
     * <br>[解  説] 00時～GSConstTimecard.MAX_TIMECARD_HOURまで
     * <br>[備  考]
     * @param maxHour 最大時刻
     * @return ArrayList
     */
    public ArrayList < LabelValueBean > getHourLabelList(int maxHour) {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(" ", String.valueOf(-1))); //未設定
        int hour = 0;
        while (hour <= maxHour) {
            labelList.add(new LabelValueBean(
                    StringUtil.toDecFormat(String.valueOf(hour), "00"),
                    String.valueOf(hour)));
            hour++;
        }

        return labelList;
    }

    /**
     * <br>[機  能] 分のリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param admMdl 管理者設定
     * @return ArrayList
     */
    public ArrayList < LabelValueBean > getMinLabelList(TcdAdmConfModel admMdl) {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        //何分刻みか取得
        int interval = admMdl.getTacInterval();

        labelList.add(new LabelValueBean(" ", String.valueOf(-1))); //未設定
        int min = 0;
        if (interval < 60) {
            while (min < 60) {
                labelList.add(new LabelValueBean(
                        StringUtil.toDecFormat(String.valueOf(min), "00"),
                        String.valueOf(min)));
                min = min + interval;
            }
        } else {
            labelList.add(new LabelValueBean(
                    StringUtil.toDecFormat(String.valueOf(min), "00"),
                    String.valueOf(min)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 年のリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList < LabelValueBean > getYearLabelList(Connection con) throws SQLException {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        TcdTcdataDao tcDao = new TcdTcdataDao(con);

        GsMessage gsMsg = new GsMessage();
        int year = tcDao.getMinYear();

        int maxYear = tcDao.getMaxYear();
        while (year <= maxYear) {
            String strYear = gsMsg.getMessage(
                    "cmn.year",
                    new String[] {StringUtil.toDecFormat(String.valueOf(year), "0000")});
            labelList.add(new LabelValueBean(
                    strYear,
                    String.valueOf(year)));
            year++;
        }

        return labelList;
    }

    /**
     * <br>[機  能] 月のリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ArrayList
     */
    public ArrayList < LabelValueBean > getMonthLabelList() {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String strMonth = gsMsg.getMessage("cmn.month");

        int month = 1;
        while (month <= 12) {
            labelList.add(new LabelValueBean(
                    StringUtil.toDecFormat(String.valueOf(month), "00") + strMonth,
                    String.valueOf(month)));
            month++;
        }

        return labelList;
    }

    /**
     * <br>[機  能] メイン画面からの更新・表示対象のタイムカード情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param date 日付
     * @param con コネクション
     * @return TcdTcdataModel
     * @throws SQLException SQL実行時例外
     */
    public TcdTcdataModel getTargetTcddata(int userSid, UDate date, Connection con)
    throws SQLException {
        TcdTcdataModel ret = null;
        TcdTcdataDao tcDao = new TcdTcdataDao(con);
        //指定日付から24時間以内に始業打刻され、終業打刻されていない情報を取得
        ArrayList<TcdTcdataModel> tcdList = tcDao.getFailTimecardData(userSid, date);

        //管理者設定を取得
        TimecardBiz tBiz = new TimecardBiz();
        TcdAdmConfModel admConf = tBiz.getTcdAdmConfModel(userSid, con);
        int interval = 1;
        if (admConf != null) {
            interval = admConf.getTacInterval();
        }

        for (TcdTcdataModel tcdMdl : tcdList) {
            Time inTime = TimecardBiz.adjustIntime(tcdMdl.getTcdIntime(), interval);
            if (!isOver24HoursOrTenAM(tcdMdl.getTcdDate(), inTime, tcdMdl.getTcdStrikeIntime())) {
                ret = tcdMdl;
            }
        }
        if (ret == null) {
            //指定日付のタイムカード情報
            ret = tcDao.select(userSid, date.getYear(), date.getMonth(), date.getIntDay());
        }
        return ret;
    }

    /**
     * <br>[機  能] 不正データが存在するか判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @param admConf 管理者設定モデル
     * @return boolean true:存在する　false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean isFailDataExist(int userSid, Connection con, TcdAdmConfModel admConf)
    throws SQLException {
        UDate sysDate = new UDate();
        //当日以前にfromのみ登録しているデータが存在するかチェック
        TcdTcdataDao tcdDao = new TcdTcdataDao(con);
        ArrayList<TcdTcdataModel> tcdList = tcdDao.getFailTimecardData(userSid, sysDate);
        int interval = 1;
        if (admConf != null) {
            interval = admConf.getTacInterval();
        }

        for (TcdTcdataModel tcdMdl : tcdList) {
            Time inTime = TimecardBiz.adjustIntime(tcdMdl.getTcdIntime(), interval);
            if (isOver24HoursOrTenAM(tcdMdl.getTcdDate(), inTime, tcdMdl.getTcdStrikeIntime())) {
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能] 不正データが存在する場合、その年月日を取得する
     * <br>[解  説] 不正なデータが無い場合nullを返します。
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @return boolean true:存在する　false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public UDate getFailDataYmd(int userSid, Connection con) throws SQLException {
        UDate sysDate = new UDate();
        //当日以前にfromのみ登録しているデータが存在するかチェック
        TcdTcdataDao tcdDao = new TcdTcdataDao(con);
        ArrayList<TcdTcdataModel> tcdList = tcdDao.getFailTimecardData(userSid, sysDate);

        //管理者設定を取得
        TimecardBiz tBiz = new TimecardBiz();
        TcdAdmConfModel admConf = tBiz.getTcdAdmConfModel(userSid, con);

        int interval = 1;
        if (admConf != null) {
            interval = admConf.getTacInterval();
        }

        for (TcdTcdataModel tcdMdl : tcdList) {
            Time inTime = TimecardBiz.adjustIntime(tcdMdl.getTcdIntime(), interval);
            if (isOver24HoursOrTenAM(tcdMdl.getTcdDate(), inTime, tcdMdl.getTcdStrikeIntime())) {
                return tcdMdl.getTcdDate();
            }
        }
        return null;
    }

    /**
     * <br>[機  能] 始業から24時間を超えているか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 始業日
     * @param fr 始業時間
     * @param frStrike 打刻始業時間
     * @return boolean true:24時間を超えている false:24時間を超えていない
     */
    public boolean isOver24HoursOrTenAM(UDate frDate, Time fr, Time frStrike) {

        UDate sysDate = new UDate();
        sysDate.setSecond(0);
        sysDate.setMilliSecond(0);

        UDate tcDate = frDate.cloneUDate();
        tcDate.setHour(TimecardUtil.getTimeHour(fr));
        tcDate.setMinute(TimecardUtil.getTimeMin(fr));

        UDate tcStrDate = frDate.cloneUDate();
        tcStrDate.setHour(TimecardUtil.getTimeHour(frStrike));
        tcStrDate.setMinute(TimecardUtil.getTimeMin(frStrike));

        int days = UDateUtil.diffDay(sysDate, tcDate);
        int strDays = UDateUtil.diffDay(sysDate, tcStrDate);

        if (sysDate.compareDateYMD(tcDate) == UDate.SMALL) {
            //超えていない場合でも始業から24時間以上か判定
            if (days > 0 || strDays > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能] タイムカードエクセルを出力します。
     * <br>[解  説]
     * <br>[備  考]
     * @param parmModel タイムカード作成パラメータ
     * @param subId サブディレクトリID
     * @throws SQLException SQL実行例外
     * @throws IOException 入出力例外
     */
    public void createTimeCardXls(TimeCardXlsParametarModel parmModel, String subId)
    throws SQLException, IOException {

        log__.debug("勤務表出力を行います。");
        log__.info(" 勤務表データ計算 開始");
        OutputStream oStream = null;
        Connection con = parmModel.getCon();
        String appRootPath = parmModel.getAppRootPath();
        String outPath = parmModel.getOutTempDir();

        //基本設定取得
        TimecardBiz tmBiz = new TimecardBiz(reqMdl__);
        TcdAdmConfModel admMdl = tmBiz.getTcdAdmConfModel(parmModel.getTargetUserSid(), con);

        //時間帯設定取得
        TcdTimezoneDao tDao = new TcdTimezoneDao(con);
        HashMap<Integer, ArrayList<TcdTimezoneModel>> timeZoneMap = tDao.getTimezoneCalcData();

        //デフォルト時間帯の取得
        int defaultTtiSid = tmBiz.getDefTimezone(con, parmModel.getTargetUserSid(), admMdl);
        //デフォルト時間帯の取得
        ArrayList<TcdTimezoneModel> defalutTimezoneList =  timeZoneMap.get(defaultTtiSid);

        //ユーザ
        int userSid = parmModel.getTargetUserSid();
        CmnUsrmInfDao uinfDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel uinfModel = uinfDao.select(userSid);
        String name = uinfModel.getUsiSei() + "　" + uinfModel.getUsiMei();

        //対象年
        int year = parmModel.getTargetYear();
        //対象月
        int month = parmModel.getTargetMonth();

        //sheetデータ
        WorkReportData wReportData = new WorkReportData();
        //作業場所
        //名前
        wReportData.setUserName(name);
        //該当年
        wReportData.setYear(year);
        //該当月
        wReportData.setMonth(month);

        TimecardCalcBiz calBiz = new TimecardCalcBiz(reqMdl__);
        //基準:稼働日数・稼働時間
        BigDecimal kadoBaseDays = BigDecimal.ZERO;  //稼動基準日数
        BigDecimal kadoBaseHours = BigDecimal.ZERO; //稼動基準時間数
        BigDecimal baseDayOfHour = BigDecimal.ZERO; //稼動基準時間数/日
        UDate fdate = new UDate();
        UDate tdate = calBiz.setTimeCardCal(year, month, admMdl.getTacSimebi(), fdate);
        //休日情報取得
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayListFotTcd(fdate, tdate);
        kadoBaseDays = calBiz.getBaseDays(fdate, tdate, admMdl, holMap, defalutTimezoneList);
        kadoBaseHours = calBiz.getBaseHours(kadoBaseDays, admMdl, defalutTimezoneList);
        if (kadoBaseDays != null && !kadoBaseDays.equals(BigDecimal.ZERO)) {
            BigDecimal minBd = convertHourToMinBigDecimal(admMdl, kadoBaseHours);
            minBd = minBd.divide(kadoBaseDays, 0, RoundingMode.HALF_UP);
            baseDayOfHour = convertMinToHourBigDecimal(admMdl, minBd);
        }
        //休日区分情報を取得
        TcdHolidayInfDao holInfDao = new TcdHolidayInfDao(con);
        List<TcdHolidayInfModel> holDataList = holInfDao.getAllHolidayList();

        //基準稼働時間
        wReportData.setBaseHour(baseDayOfHour.toString());
        //基準稼働日数
        wReportData.setBaseDay(kadoBaseDays.toString());

        List<Tcd010Model> tcdList = parmModel.getTimeCardInfoList();

        //1ヶ月分の勤務表データを保持するリスト
        List<TimecardLineData> reportDataList =
            Collections.synchronizedList(new ArrayList<TimecardLineData>());

        //1行分のデータを保持するモデル
        TimecardLineData reportData = null;
        //休日集計情報
        BigDecimal yuukyuDays = BigDecimal.ZERO;    //集計区分：有給
        BigDecimal kekkinDays = BigDecimal.ZERO;    //集計区分：欠勤
        BigDecimal sonotaDays = BigDecimal.ZERO;    //集計区分：その他

        HashMap<Integer, ArrayList<Time>> timeFrTo = new HashMap<Integer, ArrayList<Time>>();
        //lineデータ作成（１ヶ月分のデータ）
        for (Tcd010Model tcdModel : tcdList) { //テストデータは１件のみ
            reportData = new TimecardLineData();
            UDate date = new UDate();
            if (!(tcdModel.getTcd010Ymd() == null)) {
                date.setDate(tcdModel.getTcd010Ymd());
            }

            //休日丸め処理（週末、祝日）
            int holiday = tcdModel.getHolKbn();
            if (date.getWeek() == UDate.SATURDAY || date.getWeek() == UDate.SUNDAY) {
                holiday = 1;
            }
            String strikeStartTime = tcdModel.getTcd010StrikeShigyouStr();
            String strikeEndTime = tcdModel.getTcd010StrikeSyugyouStr();
            String startTime = tcdModel.getTcd010ShigyouStr();
            String endTime = tcdModel.getTcd010SyugyouStr();
            ArrayList<TcdTimezoneModel> timeZoneList = timeZoneMap.get(tcdModel.getTtiSid());

            //稼働時間
            String utilTime = calBiz._getWorkHoursString(tcdModel, admMdl, timeZoneList);
            //残業時間
            String overTime = calBiz._getZangyoWorkHoursString(tcdModel, admMdl, timeZoneList);
            //深夜残業時間
            String nightOverTime = calBiz._getSinyaWorkHoursString(tcdModel, admMdl, timeZoneList);
            //備考
            String bikou = tcdModel.getTcd010Bikou();

            Time normalFrTime = null;
            Time normalToTime = null;
            int ttiSid = tcdModel.getTtiSid();
            if (tcdModel.getTcdChkkbn() == GSConstTimecard.CHK_KBN_SELECT
                    || tcdModel.getTcdSoukbn() == GSConstTimecard.SOU_KBN_SELECT) {
                ArrayList<Time> list = new ArrayList<Time>();
                if (timeFrTo.get(ttiSid) == null) {
                    normalFrTime = calBiz._getTimezoneFrTime(timeZoneList);
                    normalToTime = calBiz._getTimezoneToTime(timeZoneList);
                    list.add(normalFrTime);
                    list.add(normalToTime);
                    timeFrTo.put(ttiSid, list);
                } else {
                    list = timeFrTo.get(ttiSid);
                    normalFrTime = list.get(0);
                    normalToTime = list.get(1);
                }
            }

            //遅刻
            String tikoku = calBiz._getChikokuTimesString(tcdModel, admMdl, timeZoneList,
                                            normalFrTime, normalToTime);
            //早退
            String soutai = calBiz._getSoutaiTimesString(tcdModel, admMdl, timeZoneList,
                                            normalFrTime, normalToTime);
            //休日内容
            String holValue = NullDefault.getString(tcdModel.getTcdHolother(), "");

            //休日情報
            Map<Integer, String> holDaysMap = new HashMap<Integer, String>();
            for (TcdHolidayInfModel holMdl : holDataList) {
                if (tcdModel.getThiSid() == holMdl.getThiSid()) {
                    String holData = StringUtil.toCommaFormat(
                            __getHolidayDays(tcdModel, holMdl.getThiSid()));
                    holDaysMap.put(holMdl.getThiSid(), holData);


                    if (holMdl.getThiSid() == GSConstTimecard.THI_SID_KEITYO) {
                        //休日区分 = 慶弔の場合、有休日数に含める
                        yuukyuDays = yuukyuDays.add(tcdModel.getTcdHolcnt());
                        reportData.setHolKbn(GSConstTimecard.HOL_KBN_YUUKYUU);
                    } else if (holMdl.getThiHoltotalKbn() == GSConstTimecard.HOL_KBN_YUUKYUU) {
                        //休日集計区分 = 2:有休の場合、有休日数(消費日数)の合計を行う
                        yuukyuDays = yuukyuDays.add(tcdModel.getTcdHolcnt());
                        reportData.setHolKbn(GSConstTimecard.HOL_KBN_YUUKYUU);
                    } else if (holMdl.getThiHoltotalKbn() == GSConstTimecard.HOL_KBN_KEKKIN) {
                        //休日集計区分 = 1:欠勤の場合、欠勤日数(消費日数)の合計を行う
                        kekkinDays = kekkinDays.add(tcdModel.getTcdHolcnt());
                        reportData.setHolKbn(GSConstTimecard.HOL_KBN_KEKKIN);
                    } else if (holMdl.getThiHoltotalKbn() == GSConstTimecard.HOL_KBN_UNSELECT) {
                        //休日集計区分 = 0:指定無の場合、その他日数(消費日数)の合計を行う
                        sonotaDays = sonotaDays.add(tcdModel.getTcdHolcnt());
                        reportData.setHolKbn(GSConstTimecard.HOL_KBN_UNSELECT);
                    }

                    break;
                }
            }
            log__.debug("------------------------------------");
            log__.debug(">>>holiday" + holiday);
            log__.debug(">>>strikeStartTime" + strikeStartTime);
            log__.debug(">>>strikeEndTime" + strikeEndTime);
            log__.debug(">>>startTime" + startTime);
            log__.debug(">>>endTime" + endTime);
            log__.debug(">>>overTime" + overTime);
            log__.debug(">>>nightOverTime" + nightOverTime);
            log__.debug(">>>bikou" + bikou);
            log__.debug("------------------------------------");
            //日付
            reportData.setDate(date);
            //休日フラグ
            reportData.setHoliday(holiday);
            //打刻開始時間
            reportData.setStrikeStartTime(strikeStartTime);
            //打刻終了稼働時間
            reportData.setStrikeEndTime(strikeEndTime);
            //開始時間
            reportData.setStartTime(startTime);
            //終了稼働時間
            reportData.setEndTime(endTime);
            //稼働時間
            reportData.setUtilTime(utilTime);
            //残業時間
            reportData.setOverTime(overTime);
            //深夜残業時間
            reportData.setNightOverTime(nightOverTime);
            //遅刻
            reportData.setTikoku(tikoku);
            //早退
            reportData.setSoutai(soutai);
            //休日情報
            reportData.setHolDaysMap(holDaysMap);
            //休日内容
            reportData.setHolValue(holValue);
            //備考
            reportData.setBikou(bikou);
            reportDataList.add(reportData);
        }
        //休日集計情報
        wReportData.setSumYukyu(yuukyuDays.toString());
        wReportData.setSumKekkin(kekkinDays.toString());
        wReportData.setSumOther(sonotaDays.toString());

        //行データ
        wReportData.setLineDataList(reportDataList);

        log__.info(" 勤務表データ計算 終了");
        try {
            IOTools.isDirCheck(outPath, true);
            String bookName = parmModel.getOutBookTmpName();
            oStream = new FileOutputStream(outPath + bookName);
            ExcelUtil eUtil = new ExcelUtil(reqMdl__);
            eUtil.createWorkReport(con, outPath,
                    appRootPath, oStream, wReportData, admMdl, holDataList, subId);
        } catch (Exception e) {
            log__.error("勤務表出力に失敗しました。", e);
        } finally {
            oStream.flush();
            oStream.close();
        }
        log__.debug("勤務表出力を終了します。");
    }
    /**
     * <br>[機  能] タイムカードPDFを出力します。
     * <br>[解  説]
     * <br>[備  考]
     * @param parmModel タイムカード作成パラメータ
     * @throws SQLException SQL実行例外
     * @throws IOException 入出力例外
     */
    public void createTimeCardPdf(TimeCardXlsParametarModel parmModel)
    throws SQLException, IOException {

        log__.debug("勤務表PDF出力を行います。");

        OutputStream oStream = null;
        Connection con = parmModel.getCon();
        String appRootPath = parmModel.getAppRootPath();
        String outPath = parmModel.getOutTempDir();

        //基本設定取得
        TimecardBiz tmBiz = new TimecardBiz(reqMdl__);
        TcdAdmConfModel admMdl = tmBiz.getTcdAdmConfModel(parmModel.getTargetUserSid(), con);
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String strPlace = gsMsg.getMessage("tcd.107");
        //作業場所
        String place = strPlace;
        //ユーザ
        int userSid = parmModel.getTargetUserSid();
        CmnUsrmInfDao uinfDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel uinfModel = uinfDao.select(userSid);
        String name = uinfModel.getUsiSei() + "　" + uinfModel.getUsiMei();

        //対象年
        int year = parmModel.getTargetYear();
        //対象月
        int month = parmModel.getTargetMonth();

        //PDFへ出力するデータをWorkReportDataに設定
        WorkReportData wReportData = new WorkReportData();
        //作業場所
        wReportData.setPlace(place);
        //名前
        wReportData.setUserName(name);
        //該当年
        wReportData.setYear(year);
        //該当月
        wReportData.setMonth(month);
        //
        TimecardCalcBiz calBiz = new TimecardCalcBiz(reqMdl__);
        //基準:稼働日数・稼働時間
        BigDecimal kadoBaseDays = BigDecimal.ZERO;  //稼動基準日数
        BigDecimal kadoBaseHours = BigDecimal.ZERO; //稼動基準時間数
        BigDecimal baseDayOfHour = BigDecimal.ZERO; //稼動基準時間数/日
        UDate fdate = new UDate();
        UDate tdate = calBiz.setTimeCardCal(year, month, admMdl.getTacSimebi(), fdate);
        //休日情報取得
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayListFotTcd(fdate, tdate);
        //休日区分情報を取得
        TcdHolidayInfDao holInfDao = new TcdHolidayInfDao(con);
        List<TcdHolidayInfModel> holDataList = holInfDao.getAllHolidayList();
        //時間帯設定取得
        TcdTimezoneDao tDao = new TcdTimezoneDao(con);
        HashMap<Integer, ArrayList<TcdTimezoneModel>> timeZoneMap = tDao.getTimezoneCalcData();

        //デフォルト時間帯の取得
        int defaultTtiSid = tmBiz.getDefTimezone(con, parmModel.getTargetUserSid(), admMdl);
        //デフォルト時間帯の取得
        ArrayList<TcdTimezoneModel> defalutTimezoneList =  timeZoneMap.get(defaultTtiSid);

        //通常時間帯取得
        kadoBaseDays = calBiz.getBaseDays(fdate, tdate, admMdl, holMap, defalutTimezoneList);
        kadoBaseHours = calBiz.getBaseHours(kadoBaseDays, admMdl, defalutTimezoneList);
        if (kadoBaseDays != null && !kadoBaseDays.equals(BigDecimal.ZERO)) {
            BigDecimal minBd = convertHourToMinBigDecimal(admMdl, kadoBaseHours);
            minBd = minBd.divide(kadoBaseDays, 2, RoundingMode.HALF_UP);
            baseDayOfHour = convertMinToHourBigDecimal(admMdl, minBd);
        }
        //基準稼働時間
        wReportData.setBaseHour(baseDayOfHour.toString());
        //基準稼働日数
        wReportData.setBaseDay(kadoBaseDays.toString());

        List<Tcd010Model> tcdList = parmModel.getTimeCardInfoList();

        //1ヶ月分の勤務表データを保持するリスト
        List<TimecardLineData> reportDataList =
            Collections.synchronizedList(new ArrayList<TimecardLineData>());

        //1行分のデータを保持するモデル
        TimecardLineData reportData = null;
        //休日集計情報
        BigDecimal yuukyuDays = BigDecimal.ZERO;    //集計区分：有給
        BigDecimal kekkinDays = BigDecimal.ZERO;    //集計区分：欠勤
        BigDecimal sonotaDays = BigDecimal.ZERO;    //集計区分：その他
        HashMap<Integer, ArrayList<Time>> timeFrTo = new HashMap<Integer, ArrayList<Time>>();

        //lineデータ作成（１ヶ月分のデータ）
        for (Tcd010Model tcdModel : tcdList) { //テストデータは１件のみ
            reportData = new TimecardLineData();

            UDate date = new UDate();
            if (!(tcdModel.getTcd010Ymd() == null)) {
                date.setDate(tcdModel.getTcd010Ymd());
            }

            //休日丸め処理（週末、祝日）
            int holiday = tcdModel.getHolKbn();
            if (date.getWeek() == UDate.SATURDAY || date.getWeek() == UDate.SUNDAY) {
                holiday = 1;
            }
            String strikeStartTime = tcdModel.getTcd010StrikeShigyouStr();
            String strikeEndTime = tcdModel.getTcd010StrikeSyugyouStr();
            String startTime = tcdModel.getTcd010ShigyouStr();
            String endTime = tcdModel.getTcd010SyugyouStr();

            //休日情報
            Map<Integer, String> holDaysMap = new HashMap<Integer, String>();
            for (TcdHolidayInfModel holMdl : holDataList) {
                if (tcdModel.getThiSid() == holMdl.getThiSid()) {
                    String holData = StringUtil.toCommaFormat(
                            __getHolidayDays(tcdModel, holMdl.getThiSid()));
                    holDaysMap.put(holMdl.getThiSid(), holData);

                    //休日区分 = 慶弔の場合、有休日数に含める
                    if (holMdl.getThiSid() == GSConstTimecard.THI_SID_KEITYO) {
                        yuukyuDays = yuukyuDays.add(tcdModel.getTcdHolcnt());
                        reportData.setHolKbn(GSConstTimecard.HOL_KBN_YUUKYUU);
                    //休日集計区分 = 2:有休の場合、有休日数(消費日数)の合計を行う
                    } else if (holMdl.getThiHoltotalKbn() == GSConstTimecard.HOL_KBN_YUUKYUU) {
                        yuukyuDays = yuukyuDays.add(tcdModel.getTcdHolcnt());
                        reportData.setHolKbn(GSConstTimecard.HOL_KBN_YUUKYUU);
                    } else if (holMdl.getThiHoltotalKbn() == GSConstTimecard.HOL_KBN_KEKKIN) {
                    //休日集計区分 = 1:欠勤の場合、欠勤日数(消費日数)の合計を行う
                        kekkinDays = kekkinDays.add(tcdModel.getTcdHolcnt());
                        reportData.setHolKbn(GSConstTimecard.HOL_KBN_KEKKIN);
                    //休日集計区分 = 0:その他の場合、その他日数(消費日数)の合計を行う
                    } else if (holMdl.getThiHoltotalKbn() == GSConstTimecard.HOL_KBN_UNSELECT) {
                        sonotaDays = sonotaDays.add(tcdModel.getTcdHolcnt());
                        reportData.setHolKbn(GSConstTimecard.HOL_KBN_UNSELECT);
                    }
                    break;
                }
            }

            ArrayList<TcdTimezoneModel> timeZoneList = timeZoneMap.get(tcdModel.getTtiSid());

            //稼働時間
            String utilTime = StringUtil.toCommaFormat(
                    calBiz._getWorkHoursString(tcdModel, admMdl, timeZoneList));
            //残業時間
            String overTime = StringUtil.toCommaFormat(
                    calBiz._getZangyoWorkHoursString(tcdModel, admMdl, timeZoneList));
            //深夜残業時間
            String nightOverTime = StringUtil.toCommaFormat(
                    calBiz._getSinyaWorkHoursString(tcdModel, admMdl, timeZoneList));
            //備考
            String bikou = tcdModel.getTcd010Bikou();

            Time normalFrTime = null;
            Time normalToTime = null;
            int ttiSid = tcdModel.getTtiSid();
            if (tcdModel.getTcdChkkbn() == GSConstTimecard.CHK_KBN_SELECT
                    || tcdModel.getTcdSoukbn() == GSConstTimecard.SOU_KBN_SELECT) {
                ArrayList<Time> list = new ArrayList<Time>();
                if (timeFrTo.get(ttiSid) == null) {
                    normalFrTime = calBiz._getTimezoneFrTime(timeZoneList);
                    normalToTime = calBiz._getTimezoneToTime(timeZoneList);
                    list.add(normalFrTime);
                    list.add(normalToTime);
                    timeFrTo.put(ttiSid, list);
                } else {
                    list = timeFrTo.get(ttiSid);
                    normalFrTime = list.get(0);
                    normalToTime = list.get(1);
                }
            }

            //遅刻
            String tikoku = calBiz._getChikokuTimesString(tcdModel, admMdl, timeZoneList,
                                            normalFrTime, normalToTime);
            //早退
            String soutai = calBiz._getSoutaiTimesString(tcdModel, admMdl, timeZoneList,
                                            normalFrTime, normalToTime);
            //休日情報
            reportData.setHolDaysMap(holDaysMap);
            //休日内容
            reportData.setHolValue(tcdModel.getTcdHolother());
            //日付
            reportData.setDate(date);
            //休日フラグ
            reportData.setHoliday(holiday);
            //打刻開始時間
            reportData.setStrikeStartTime(strikeStartTime);
            //打刻終了稼働時間
            reportData.setStrikeEndTime(strikeEndTime);
            //開始時間
            reportData.setStartTime(startTime);
            //終了稼働時間
            reportData.setEndTime(endTime);
            //稼働時間
            reportData.setUtilTime(utilTime);
            //残業時間
            reportData.setOverTime(overTime);
            //深夜残業時間
            reportData.setNightOverTime(nightOverTime);
            //遅刻
            reportData.setTikoku(tikoku);
            //早退
            reportData.setSoutai(soutai);
            //備考
            reportData.setBikou(bikou);

            reportDataList.add(reportData);
        }
        //休日集計情報
        wReportData.setSumYukyu(yuukyuDays.toString());
        wReportData.setSumKekkin(kekkinDays.toString());
        wReportData.setSumOther(sonotaDays.toString());

        //行データ
        wReportData.setLineDataList(reportDataList);

        TcdTotalValueModel total
        = calBiz.getTotalValueModel(
                userSid, fdate, tdate,
                reqMdl__.getSmodel().getUsrsid(), con, reqMdl__);

        wReportData.setSumBaseHour(StringUtil.toCommaFromBigDecimal(kadoBaseHours));

        wReportData.setSumUtil(total.getKadoHoursStr());
        if (total.getZangyoDays().compareTo(BigDecimal.ZERO) > 0) {
            wReportData.setSumOver(total.getZangyoHoursStr());
        } else {
            wReportData.setSumOver(" ");
        }
        if (total.getSinyaDays().compareTo(BigDecimal.ZERO) > 0) {
            wReportData.setSumNight(total.getSinyaHoursStr());
        } else {
            wReportData.setSumNight(" ");
        }
        if (total.getChikokuTimes().compareTo(BigDecimal.ZERO) > 0) {
            wReportData.setSumTikoku(total.getChikokuTimesStr());
        } else {
            wReportData.setSumTikoku(" ");
        }
        if (total.getSoutaiTimes().compareTo(BigDecimal.ZERO) > 0) {
            wReportData.setSumSoutai(total.getSoutaiTimesStr());
        } else {
            wReportData.setSumSoutai(" ");
        }
        try {
            IOTools.isDirCheck(outPath, true);
            String bookName = parmModel.getOutBookTmpName();
            oStream = new FileOutputStream(outPath + bookName);
            TcdPdfUtil eUtil = new TcdPdfUtil(reqMdl__);
            eUtil.createWorkReport(appRootPath, oStream, wReportData, holDataList);
        } catch (Exception e) {
            log__.error("勤務表出力に失敗しました。", e);
        } finally {
            oStream.flush();
            oStream.close();
        }
        log__.debug("勤務表出力を終了します。");
    }

    /**
     * <br>[機  能] 休日ごとの日数を holDaysStrへ設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param model タイムカード情報
     * @param thiSid 休日区分Sid
     * @return holDaysStr 休日日数
     */
    private String __getHolidayDays(
            Tcd010Model model, int thiSid) {
        BigDecimal holDays = BigDecimal.ZERO;
        holDays = model.getTcdHolcnt();
        if (holDays == null) {
            holDays = BigDecimal.ZERO;
        }
        String holDaysStr = holDays.toString();


        return holDaysStr;
    }

    /**
     * <br>[機  能] 開始と終了時間の時差を分換算します。
     * <br>[解  説]
     * <br>[備  考]
     * @param startTime 開始
     * @param stopTime 終了
     * @return int 分
     */
    public int changeTime(int startTime, int stopTime) {
        int start = startTime;
        int stop = stopTime;
        if (start > stop) {
            stop += MAXIMUM_TIME;
        }
        return ((stop / 100) * 60 + (stop % 100)) - ((start / 100) * 60 + (start % 100));
    }

    /**
     * <br>[機  能] 指定時間に当てはまるかをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param from 開始時間
     * @param to 終了時間
     * @param target チェック対象時間
     * @return 現在の日付が範囲内ならtrue
     */
    public static boolean isRange(final UDate from, final UDate to, final UDate target) {

        if (from == null || to == null || target == null) {
            return false;
        }
        long fromNum = from.getTime();
        long toNum = to.getTime();
        long tarNum = target.getTime();
        return fromNum <= tarNum && tarNum <= toNum;
    }

    /**
     * <br>[機  能] 始業時間から遅刻か判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param frTime 始業時刻
     * @param con コネクション
     * @return boolean true:遅刻 false:遅刻していない
     * @throws SQLException SQL実行時例外
     */
    public boolean isChikoku(Time frTime, Connection con) throws SQLException {

        if (frTime == null) {
            return false;
        }
        //通常時間帯を取得
        TcdTimezoneDao tzDao = new TcdTimezoneDao(con);
        Time time = tzDao.getFrTimeMin(GSConstTimecard.TIMEZONE_KBN_NORMAL);
        if (time == null) {
            return false;
        }
        log__.debug("time.compareTo(frTime==>" + time.compareTo(frTime));
        if (time.compareTo(frTime) == UDate.LARGE) {
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 終業時間から早退か判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param toTime 終業時刻
     * @param con コネクション
     * @return boolean true:24時間以上 false:24時間未満
     * @throws SQLException SQL実行時例外
     */
    public boolean isSoutai(Time toTime, Connection con) throws SQLException {
        if (toTime == null) {
            return false;
        }
        //通常時間帯を取得
        TcdTimezoneDao tzDao = new TcdTimezoneDao(con);
        Time time = tzDao.getToTimeMax(GSConstTimecard.TIMEZONE_KBN_NORMAL);
        if (time == null) {
            return false;
        }
        log__.debug("time.compareTo(toTime==>" + time.compareTo(toTime));
        if (time.compareTo(toTime) == UDate.LARGE) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 基本設定の間隔に適したUDateを変換します。
     * <br>[解  説]
     * <br>[備  考]
     * @param date 変換元UDate
     * @param admConf 管理者設定
     * @param roundFlg true:切上げ(始業時刻) false:切捨て(終業時刻)
     * @return UDate 変換後UDate
     */
    public static UDate convertForDspTime(UDate date, TcdAdmConfModel admConf, boolean roundFlg) {

        int inv = admConf.getTacInterval();
        UDate ret = date.cloneUDate();
        ret.setSecond(0);
        ret.setMilliSecond(0);
        //分を変換
        int cnvMin = 0;
        int min = ret.getIntMinute();

        int div = min / inv;
        int zan = min % inv;
        if (roundFlg) {
            //切り上げ処理
            if (zan > 0) {
                cnvMin = div * inv + inv;
            } else {
                cnvMin = min;
            }
            ret.setMinute(cnvMin);

        } else {
            //切捨て処理
            cnvMin = div * inv;
            ret.setMinute(cnvMin);

        }

        return ret;
    }

    /**
     * <br>[機  能] long型の分データを基本設定の換算値の従がい時間情報へ変換します。
     * <br>[解  説]
     * <br>[備  考]
     * @param admMdl 基本設定
     * @param minBd 分情報
     * @return BigDecimal 時間情報
     */
    public static BigDecimal convertMinToHourBigDecimal(TcdAdmConfModel admMdl, BigDecimal minBd) {
        BigDecimal ret = BigDecimal.ZERO;
        //時間
        BigDecimal hourBd = minBd.divide(BigDecimal.valueOf(60), RoundingMode.DOWN);
        //分
        BigDecimal ansBd = minBd.subtract(hourBd.multiply(BigDecimal.valueOf(60)));
        //分を換算
        if (admMdl.getTacKansan() == GSConstTimecard.TIMECARD_SINSU[0]) {
            //10進数
            ret = ansBd.divide(BigDecimal.valueOf(60), 2, RoundingMode.DOWN);
        } else {
            //60進数
            ret = ansBd.divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN);
        }
        ret = ret.add(hourBd);
        return ret;
    }
    /**
     *
     * <br>[機  能] 小数点の桁数を表示用に合わせる
     * <br>[解  説]
     * <br>[備  考] 数値が0の場合は小数点以下を表示しない
     * @param src 変換もと数値
     * @param baseScale 小数点以下の桁数
     * @param roundMode srcの小数点以下の桁数がbaseScaleより多い場合の丸め方<br>RoundingMode.UNNECESSARYの場合は大きい方に合わす
     * @return 変換後数値型
     */
    public static BigDecimal convertDispBigDecimal(BigDecimal src,
            int baseScale,
            RoundingMode roundMode) {
        BigDecimal ret = src.stripTrailingZeros();
        // 数値が0の場合は小数点以下を表示しない
        if (ret.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        // srcの小数点以下の桁数がbaseScaleより多い場合
        if (ret.scale() > baseScale) {
            if (!roundMode.equals(RoundingMode.UNNECESSARY)) {
                ret = ret.setScale(baseScale, roundMode);
            }
        } else {
            ret = ret.setScale(baseScale);
        }
        return ret;
    }
    /**
     * <br>[機  能] 基本設定の換算値の従がった時間情報をlong型の分データへ変換します。
     * <br>[解  説]
     * <br>[備  考]
     * @param admMdl 基本設定
     * @param hourBd 時間情報
     * @return BigDecimal 分情報
     */
    public static BigDecimal convertHourToMinBigDecimal(TcdAdmConfModel admMdl, BigDecimal hourBd) {
        BigDecimal ret = BigDecimal.ZERO;
        BigDecimal left = BigDecimal.valueOf(hourBd.longValue());
        BigDecimal right = hourBd.subtract(BigDecimal.valueOf(hourBd.longValue()));
        ret.setScale(0);
        ret = ret.add(left.multiply(BigDecimal.valueOf(60)));

        if (admMdl.getTacKansan() == GSConstTimecard.TIMECARD_SINSU[0]) {
            //10進数
            ret = ret.add(right.multiply(BigDecimal.valueOf(60)));
        } else {
            //60進数
            ret = ret.add(right.movePointRight(2));
        }
        return ret;
    }
    /**
     * <br>[機  能] 始業時間の調整を行う
     * <br>[解  説] 始業時間が入力単位と異なる場合、始業時間の切り上げを行う
     * <br>[備  考]
     * @param inTime 始業時間
     * @param interval 入力単位
     * @return 始業時間
     */
    public static Time adjustIntime(Time inTime, int interval) {

        if (inTime != null) {
            UDate adjustTime = UDate.getInstance(inTime.getTime());
            if (adjustTime.getIntMinute() % interval != 0) {
                adjustTime.addMinute(interval - (adjustTime.getIntMinute() % interval));
                return new Time(adjustTime.getTime());
            }
        }

        return inTime;
    }

    /**
     * <br>[機  能] 終業時間の調整を行う
     * <br>[解  説] 終業時間が入力単位と異なる場合、 終業時間の切捨てを行う
     * <br>[備  考]
     * @param outTime 終業時間
     * @param interval 入力単位
     * @return 終業時間
     */
    public static Time adjustOuttime(Time outTime, int interval) {

        if (outTime != null) {
            UDate adjustTime = UDate.getInstance(outTime.getTime());
            if (adjustTime.getIntMinute() % interval != 0) {
                adjustTime.addMinute(-1 * (adjustTime.getIntMinute() % interval));
                return new Time(adjustTime.getTime());
            }
        }

        return outTime;
    }

    /**
     * タイムカード　個別ログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutTimecardLog(
            ActionMapping map,
            RequestModel reqMdl,
            Connection con,
            String opCode,
            String level,
            String value) {
        outPutTimecardLog(map, reqMdl, con, opCode, level, value, GSConstTimecard.TCD_LOG_FLG_NONE);
    }

    /**
     * タイムカード　個別ログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param logFlg ログ出力判別フラグ
     */
    public void outPutTimecardLog(
            ActionMapping map,
            RequestModel reqMdl,
            Connection con,
            String opCode,
            String level,
            String value,
            int logFlg) {

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String timecard = gsMsg.getMessage("tcd.50");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstTimecard.PLUGIN_ID_TIMECARD);
        logMdl.setLogPluginName(timecard);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(type));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (logFlg == GSConstTimecard.TCD_LOG_FLG_PDF) {
            logMdl.setLogCode(" PDFエクスポート");
        }

        LoggingBiz logBiz = new LoggingBiz(con);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * タイムカード　個別ログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param logFlg ログ出力判別フラグ
     * @param dspName 画面名
     */
    public void outPutTimecardLogNoDsp(
            ActionMapping map,
            RequestModel reqMdl,
            Connection con,
            String opCode,
            String level,
            String value,
            int logFlg,
            String dspName) {

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String timecard = gsMsg.getMessage("tcd.50");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstTimecard.PLUGIN_ID_TIMECARD);
        logMdl.setLogPluginName(timecard);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(dspName);
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (logFlg == GSConstTimecard.TCD_LOG_FLG_PDF) {
            logMdl.setLogCode(" PDFエクスポート");
        }

        LoggingBiz logBiz = new LoggingBiz(con);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }
    /**
     * タイムカードＡＰＩ全般全般のログ出力を行う
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param usid ユーザSID
     * @param pgId プログラムID
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutApiLog(
            RequestModel reqMdl,
            Connection con,
            int usid,
            String pgId,
            String opCode,
            String level,
            String value) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String timecard = gsMsg.getMessage("tcd.50");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstTimecard.PLUGIN_ID_TIMECARD);
        logMdl.setLogPluginName(timecard);
        logMdl.setLogPgId(pgId);
        logMdl.setLogPgName(getPgName(pgId));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }
    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @return String
     */
    public String getPgName(String id) {
        String ret = new String();
        if (id == null) {
            return ret;
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String logName = "";

        log__.info("プログラムID==>" + id);

        if (id.equals("jp.groupsession.v2.tcd.main.TcdMainAction")) {
            logName = gsMsg.getMessage("tcd.108");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd010.Tcd010Action")) {
            logName = gsMsg.getMessage("tcd.tcd010.18");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd020.Tcd020Action")) {
            logName = gsMsg.getMessage("tcd.109");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd040.Tcd040Action")) {
            logName = gsMsg.getMessage("tcd.110");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd050kn.Tcd050knAction")) {
            logName = gsMsg.getMessage("tcd.111");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd060.Tcd060Action")) {
            logName = gsMsg.getMessage("tcd.112");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd070.Tcd070Action")) {
            logName = gsMsg.getMessage("tcd.112");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd080.Tcd080Action")) {
            logName = gsMsg.getMessage("tcd.113");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd090kn.Tcd090knAction")) {
            logName = gsMsg.getMessage("tcd.114");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd120.Tcd120Action")) {
            logName = gsMsg.getMessage("tcd.193");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd130.Tcd130Action")) {
            logName = gsMsg.getMessage("tcd.194");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd140.Tcd140Action")) {
            logName = gsMsg.getMessage("tcd.195");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd160.Tcd160Action")) {
            logName = gsMsg.getMessage("tcd.196");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd170.Tcd170Action")) {
            logName = gsMsg.getMessage("tcd.197");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd170kn.Tcd170knAction")) {
            logName = gsMsg.getMessage("tcd.198");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd180.Tcd180Action")) {
            logName = gsMsg.getMessage("tcd.207");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd180kn.Tcd180knAction")) {
            logName = gsMsg.getMessage("tcd.208");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd200.Tcd200Action")) {
            logName = gsMsg.getMessage("cmn.admin.setting")
                    + " " + gsMsg.getMessage("tcd.tcd200.01");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd200kn.Tcd200knAction")) {
            logName = gsMsg.getMessage("cmn.admin.setting")
                    + " " + gsMsg.getMessage("tcd.tcd200.01");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd210kn.Tcd210knAction")) {
            logName = gsMsg.getMessage("tcd.58");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.tcd.tcd220.Tcd220Action")) {
            logName = gsMsg.getMessage("tcd.213");
            return logName;
        }

        if (id.equals("jp.groupsession.v2.api.timecard.dakoku.ApiDakokuAction")) {
            logName = gsMsg.getMessage("tcd.115");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.api.timecard.edit.ApiTcdEditAction")) {
            logName = gsMsg.getMessage("tcd.141");
            return logName;
        }
        return ret;
    }

    /**
     * <br>[機  能] 締め日を考慮しデフォルト表示年月を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 対象年月
     * @param admConf TcdAdmConfModel
     * @return UDate 表示年月
     */
    public static UDate getDspUDate(UDate date, TcdAdmConfModel admConf) {
        UDate ret = date.cloneUDate();
        int sime = admConf.getTacSimebi();
        if (sime == GSConstTimecard.TIMECARD_LIMITDAY[5]) {
            return ret;
        } else {
            int day = ret.getIntDay();
            if (sime >= day) {
                ret.addMonth(-1);
            }
            return ret;
        }
    }

    /**
    *
    * <br>[機  能] 打刻ボタンの表示チェックを行う。
    * <br>[解  説]
    * <br>[備  考]
    * @param toDayStart 今日の始業時間
    * @param toDayEnd 今日の終業時間
    * @param yersterDayStart 昨日の始業時間
    * @param yersterDayEnd 昨日の終業時間
    * @return ボタン表示区分
    */
    public int checkDakokuBtn(Time toDayStart,
                             Time toDayEnd,
                             Time yersterDayStart,
                             Time yersterDayEnd) {

        boolean inTimeFinFlg = false;
        boolean outTimeFinFlg = false;
        boolean yersterdayInTimeFinFlg = false;
        boolean yersterdayOutTimeFinFlg = false;

        if (toDayStart != null) {
            inTimeFinFlg = true;
        }

        if (toDayEnd != null) {
            outTimeFinFlg = true;
        }

        if (yersterDayStart != null) {
            yersterdayInTimeFinFlg = true;
        }

        if (yersterDayEnd != null) {
            yersterdayOutTimeFinFlg = true;
        }

        if (yersterdayInTimeFinFlg && !yersterdayOutTimeFinFlg) {
            //前日に始業したにも関わらず終業していない場合
            return GSConstTimecard.STATUS_NOT_END;
        } else if (!inTimeFinFlg && !outTimeFinFlg) {
            //未打刻のとき
            return GSConstTimecard.STATUS_FREE;
        } else if (inTimeFinFlg && !outTimeFinFlg) {
            //始業のみ打刻されているとき
            return GSConstTimecard.STATUS_HAFE;
        } else {
            //始業・終業時間がすでに打刻されているとき
            return GSConstTimecard.STATUS_FIN;
        }
    }

    /**
     * <br>[機  能] 指定したユーザのタイムカードを閲覧可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param tcdUserSid 判定対象のユーザ
     * @param usModel セッションユーザSID
     * @return true: 閲覧可能 false: 閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canViewTimecard(Connection con, int tcdUserSid, BaseUserModel usModel)
            throws SQLException {
        int userKbn = getUserKbn(con, usModel);

        //一般ユーザの場合、他ユーザのタイムカードは閲覧不可
        if (userKbn == GSConstTimecard.USER_KBN_NORMAL) {
            return tcdUserSid == usModel.getUsrsid();
        }

        //管理者の場合、全ユーザのタイムカードを閲覧可能
        if (userKbn == GSConstTimecard.USER_KBN_ADMIN) {
            return true;
        }

        //グループ管理者の場合、自身が管理者であるグループに所属するユーザのタイムカードを閲覧可能
        if (userKbn == GSConstTimecard.USER_KBN_GRPADM) {
            CmnGroupmDao gpDao = new CmnGroupmDao(con);
            List<CmnGroupmModel> grpDataList = gpDao.selectGroupAdmin(usModel.getUsrsid());
            GroupBiz grpBiz = new GroupBiz();
            for (CmnGroupmModel grpMdl : grpDataList) {
                if (grpBiz.isBelongGroup(tcdUserSid, grpMdl.getGrpSid(), con)) {
                    return true;
                }
            }
        }

        return false;
    }
    /**
     * <br>[機  能] セッションユーザの種別が一般・グループ管理者・管理者かを判定します。
     * <br>[解  説]
     * <br>[備  考] 管理者権限を持っている場合はグループ管理者権限よりも優先されます。
     * @param con コネクション
     * @param usModel セッションユーザ情報
     * @throws SQLException SQL実行時例外
     * @return int ユーザ種別
     */
    public int getUserKbn(Connection con, BaseUserModel usModel) throws SQLException {

        CommonBiz commonBiz = new CommonBiz();
        boolean adminUser = commonBiz.isPluginAdmin(con,
                usModel,
                GSConstTimecard.PLUGIN_ID_TIMECARD);

        if (adminUser) {
            return GSConstTimecard.USER_KBN_ADMIN;
        }
        //グループ管理者設定判定
        CmnGroupmDao gpDao = new CmnGroupmDao(con);
        List<CmnGroupmModel> list = gpDao.selectGroupAdmin(usModel.getUsrsid());
        if (list.size() > 0) {
            return GSConstTimecard.USER_KBN_GRPADM;
        }
        return GSConstTimecard.USER_KBN_NORMAL;
    }
    /**
     * <br>[機  能]オペレーションログ内容作成
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn 1:業務開始　2：業務終了　3：編集又は削除
     * @param days 複数選択時日付
     * @param date 対象年月or登録時
     * @return 内容
     */
    public String opLogContent(int kbn, String[] days, UDate date) {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        StringBuilder sbValue = new StringBuilder();
        String year = date.getStrYear() + gsMsg.getMessage("cmn.year2");
        String month = date.getStrMonth() + gsMsg.getMessage("cmn.month");
        if (kbn == 1 || kbn == 2) {
            String day = date.getStrDay() + gsMsg.getMessage("cmn.day");
            String hour = date.getStrHour() + gsMsg.getMessage("cmn.hour");
            String min = date.getStrMinute() + gsMsg.getMessage("cmn.minute");

            sbValue.append("[");
            if (kbn == 1) {
                sbValue.append(gsMsg.getMessage("tcd.tcdmain.06"));
            } else {
                sbValue.append(gsMsg.getMessage("tcd.tcdmain.05"));
            }
            sbValue.append("]");
            sbValue.append(year);
            sbValue.append(month);
            sbValue.append(day);
            sbValue.append(hour);
            sbValue.append(min);
        } else {
            sbValue.append("[");
            sbValue.append(gsMsg.getMessage("tcd.log.targetm"));
            sbValue.append("]");
            sbValue.append(year);
            sbValue.append(month);
            sbValue.append("\n");
            sbValue.append("[");
            sbValue.append(gsMsg.getMessage("tcd.log.targetd"));
            sbValue.append("]");
            if (days != null && days.length > 0) {
                for (int idx = 0; idx < days.length; idx++) {
                    if (idx != 0) {
                        sbValue.append("、");
                    }
                    sbValue.append(days[idx]);
                    sbValue.append(gsMsg.getMessage("cmn.day"));
                }
            }
        }
        return sbValue.toString();

    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @param subId サブディレクトリID
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl, String dirId, String... subId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getTempPath(reqMdl,
                                                GSConstTimecard.PLUGIN_ID_TIMECARD,
                                                dirId,
                                                subId);
        return tempDir;
    }
    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @param subId サブディレクトリID
     */
    public void deleteTempDir(RequestModel reqMdl, String dirId, String... subId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteTempPath(reqMdl,
                                    GSConstTimecard.PLUGIN_ID_TIMECARD,
                                    dirId,
                                    subId);
    }
    /**
     * <br>[機  能] テンポラリディレクトリを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @param subId サブディレクトリID
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void createTempDir(RequestModel reqMdl, String dirId, String... subId)
            throws IOToolsException {
                GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.createTempDir(reqMdl,
                                GSConstTimecard.PLUGIN_ID_TIMECARD,
                                dirId,
                                subId);
    }
    /**
     * <br>[機  能] テンポラリディレクトリの初期化を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @param subId サブディレクトリID
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void clearTempDir(RequestModel reqMdl, String dirId, String... subId)
            throws IOToolsException {
        //テンポラリディレクトリの削除後、再作成
        deleteTempDir(reqMdl, dirId, subId);
        createTempDir(reqMdl, dirId, subId);
    }


    /**
     * <br>[機  能] タイムカードの打刻に合わせて在席状況を更新します。
     * <br>[解  説]
     * <br>[備  考]
     * @param status 更新ステータス
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void zaisekiUpdate(int status, RequestModel reqMdl, Connection con)
    throws SQLException {
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int userSid = usModel.getUsrsid();

        UDate now = new UDate();
        CmnUsrInoutModel param = new CmnUsrInoutModel();
        param.setUioSid(userSid);
        param.setUioStatus(status);
        param.setUioBiko("");
        param.setUioAuid(userSid);
        param.setUioAdate(now);
        param.setUioEuid(userSid);
        param.setUioEdate(now);

        ZsjCommonBiz zbiz = new ZsjCommonBiz(reqMdl);
        zbiz.updateZskStatus(con, param);
    }
    /**
     * <br>[機  能] 基本設定に従い引数の分をコンバートし、TcdTcdataModelへ設定します。
     * <br>[解  説] 端数は切り上げ
     * <br>[備  考] 例：基本設定：15分間隔 min=16分の場合、30分に変換
     * @param tcMdl 設定先モデル
     * @param admConf 基本設定
     * @param inFlg true:始業時刻に設定 false:終業時刻へ設定
     */
    private void __setConvertYmdhm(TcdTcdataModel tcMdl, TcdAdmConfModel admConf, boolean inFlg) {

        UDate sysDate = new UDate();
        UDate tcdDate = sysDate.cloneUDate();

        tcdDate.setSecond(0);
        tcdDate.setMilliSecond(0);
        //分を変換
        Time time = null;
        if (inFlg) {
            time = new Time(tcdDate.getTime());

            tcMdl.setTcdIntime(time);
            tcMdl.setTcdStrikeIntime(time);
        } else {
            time = new Time(tcdDate.getTime());

            tcMdl.setTcdOuttime(time);
            tcMdl.setTcdStrikeOuttime(time);
        }

        tcdDate.setZeroHhMmSs();

    }

    /**
     *
     * <br>[機  能] タイムカードを打刻する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param sysDate 登録日時
     * @param flg 打刻区分 GSConstTimecard.DAKOKUKBN_NONE：自動設定<br>
     *                      GSConstTimecard.DAKOKUKBN_START 開始時間<br>
     *                      GSConstTimecard.DAKOKUKBN_END 終了時間<br>
     * @param isUpdateZsk 在籍管理更新有無
     * @return 登録タイムカード情報 打刻区分自動判別時にすでに登録完了済みの場合はnull
     * @throws SQLException SQL実行時例外
     */
    public TcdTcdataModel dakokuTcd(RequestModel reqMdl, Connection con, UDate sysDate,
            int flg, boolean isUpdateZsk) throws SQLException {

        BaseUserModel usModel = reqMdl.getSmodel();
        int userSid = usModel.getUsrsid();
        TcdAdmConfModel admConf = getTcdAdmConfModel(userSid, con);

        TcdTcdataModel tcMdl = getTargetTcddata(userSid, sysDate, con);
        if (tcMdl == null) {
            //ユーザのデフォルト時間帯を設定する
            TcdTimezonePriDao priDao = new TcdTimezonePriDao(con);
            TcdTimezonePriModel priMdl = priDao.getDefault(userSid);
            int defTzSid = 0;
            if (priMdl == null) {
                //ユーザのデフォルト時間帯設定がない場合は管理者設定デフォルト時間帯を適用する
                defTzSid = admConf.getTacDefTimezone();
            } else {
                defTzSid = priMdl.getTtiSid();
            }
            //なければ新規作成
            //多重登録防止のため空データで即時コミット
            UDate tcdDate = sysDate.cloneUDate();
            tcdDate.setSecond(0);
            tcdDate.setMilliSecond(0);
            tcdDate.setZeroHhMmSs();

            tcMdl = new TcdTcdataModel();
            tcMdl.setUsrSid(userSid);
            tcMdl.setTcdDate(tcdDate);
            tcMdl.setTtiSid(defTzSid);
            tcMdl.setTcdAuid(userSid);
            tcMdl.setTcdAdate(sysDate);
            tcMdl.setTcdEuid(userSid);
            tcMdl.setTcdEdate(sysDate);

            insertTcd(reqMdl, con, tcMdl);
            TcdDatausedSumDao tdsDao = new TcdDatausedSumDao(con);
            tdsDao.insertAddDiff(Arrays.asList(tcdDate), userSid);
        }

        //打刻区分自動判定
        if (flg == GSConstTimecard.DAKOKUKBN_NONE) {

            if (tcMdl.getTcdIntime() == null
                    && tcMdl.getTcdOuttime() == null) {
                flg = GSConstTimecard.DAKOKUKBN_START;
            } else if (tcMdl.getTcdIntime() != null
                    && tcMdl.getTcdOuttime() == null) {
                flg = GSConstTimecard.DAKOKUKBN_END;
            } else {
                return null;
            }
        }
        log__.debug("タイムカード更新");

        tcMdl.setThiSid(GSConstTimecard.HOL_KBN_UNSELECT);
        tcMdl.setTcdSoukbn(GSConstTimecard.SOU_KBN_UNSELECT);
        tcMdl.setTcdStatus(GSConstTimecard.TCD_STATUS_MAIN);
        tcMdl.setTcdAuid(userSid);
        tcMdl.setTcdAdate(sysDate);
        tcMdl.setTcdEuid(userSid);
        tcMdl.setTcdEdate(sysDate);

        TcdTcdataDao tcDao = new TcdTcdataDao(con);
        int cnt = 0;
        int zskFlg = 0;
        switch (flg) {
            case GSConstTimecard.DAKOKUKBN_START:
                //新規:始業時間登録
                tcMdl.setUsrSid(userSid);
                __setConvertYmdhm(tcMdl, admConf, true);
                if (isChikoku(tcMdl.getTcdIntime(), con)) {
                    log__.debug("tcMdl.getTcdIntime()==>" + tcMdl.getTcdIntime().toString());
                    tcMdl.setTcdChkkbn(GSConstTimecard.CHK_KBN_SELECT);
                } else {
                    tcMdl.setTcdChkkbn(GSConstTimecard.CHK_KBN_UNSELECT);
                }
                cnt = tcDao.updateDaily(tcMdl);
                zskFlg = GSConst.UIOSTS_IN;
                break;

            case GSConstTimecard.DAKOKUKBN_END:
                __setConvertYmdhm(tcMdl, admConf, false);
                if (isSoutai(tcMdl.getTcdOuttime(), con)) {
                    tcMdl.setTcdSoukbn(GSConstTimecard.SOU_KBN_SELECT);
                } else {
                    tcMdl.setTcdSoukbn(GSConstTimecard.SOU_KBN_UNSELECT);
                }
                cnt = tcDao.updateDaily(tcMdl);
                zskFlg = GSConst.UIOSTS_LEAVE;
                break;
            default:
                return tcMdl;
        }
        if (cnt > 0
                && isUpdateZsk) {
            zaisekiUpdate(zskFlg, reqMdl, con);
        }


        return tcMdl;

    }
    /**
     *
     * <br>[機  能] タイムカード情報を新規作成する
     * <br>[解  説] 二重登録対策のため即時コミットされる
     * <br>[備  考] SQLエラーではエクセプションを返さない
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param tcMdl タイムカード情報
     */
    public void insertTcd(RequestModel reqMdl, Connection con,
            TcdTcdataModel tcMdl) {
        Connection inicon = null;
        try {
            inicon = GroupSession.getConnection(reqMdl.getDomain());
            TcdTcdataDao tcDao = new TcdTcdataDao(inicon);
            tcDao.insert(tcMdl);
            inicon.commit();
        } catch (Exception e) {
            JDBCUtil.rollback(inicon);
        } finally {
            JDBCUtil.closeConnection(inicon);
        }
    }

    /**
     *
     * <br>[機  能] 画面表示用に休日日数を加工する
     * <br>[解  説] 例：1.000 → 1.0にする
     * <br>[備  考]
     * @param holidayStr 休日日数
     * @return String
     */
    public String dispSyukei(String holidayStr) {

        Pattern pFirst = Pattern.compile("\\..0{2}");
        Matcher mFirst = pFirst.matcher(holidayStr);
        Pattern pSecond = Pattern.compile("\\...0");
        Matcher mSecond = pSecond.matcher(holidayStr);
        if (mFirst.find()) {
            //小数点以下3桁すべてが0の場合は小数点第1位のみが表示されるように成形
            //例：1.000→1.0
            //例：0.100→0.1
            int index = holidayStr.indexOf(".") + 2;
            return holidayStr.substring(0, index);
        } else if (mSecond.find()) {
            //小数点以下3桁目0の場合はその0を削除する
            //例：0.010→0.1
            int index = holidayStr.indexOf(".") + 3;
            return holidayStr.substring(0, index);
        }
        return holidayStr;
    }

    /**
     *
     * <br>[機  能] デフォルト時間帯を取得する
     * <br>[解  説] 存在しなければ管理者設定で設定されたデフォルト時間帯を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param admMdl タイムカード管理者設定情報
     * @return デフォルト時間帯
     * @throws SQLException
     */
    public int getDefTimezone(Connection con, int userSid, TcdAdmConfModel admMdl)
            throws SQLException {
        int ttiSid = admMdl.getTacDefTimezone();
        //使用ユーザを設定
        TcdTimezonePriDao priDao = new TcdTimezonePriDao(con);
        TcdTimezonePriModel priMdl = new TcdTimezonePriModel();
        priMdl =  priDao.getDefault(userSid);
        if (priMdl != null) {
            ttiSid = priMdl.getTtiSid();
        }

        return ttiSid;

    }

    /**
     * <br>[機  能] 有休警告メッセージを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param yukyuUseCnt 有休使用数
     * @return 警告メッセージ
     * @throws SQLException SQL実行例外
     */
    public String getYukyuWarnMessage(Connection con,
        TcdYukyudataModel yukyuMdl, BigDecimal yukyuUseCnt) throws SQLException {

        String alertMsg = null;
        if (yukyuMdl == null) {
            return alertMsg;
        }
        UDate now = new UDate();
        now.setZeroHhMmSs();
        now.setDay(1);
        int nowMonth = now.getMonth();
        TimecardDao tcdDao = new TimecardDao(con);
        int kishuMonth = tcdDao.getKishuMonth();

        TcdYukyuAlertDao tyaDao = new TcdYukyuAlertDao(con);
        alertMsg = tyaDao.getAlertMessage(
            nowMonth, yukyuUseCnt, yukyuMdl.getTcyYukyu(), kishuMonth);
        return alertMsg;
    }

    /**
     * <br>[機  能] セッションユーザが選択されたグループのグループ管理者かを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param group グループ
     * @param sessionUsrSid セッションユーザSID
     * @param reqMdl リクエストモデル
     * @return true: グループ管理者 false: 一般ユーザ
     * @throws SQLException SQL実行時例外
     */
    public boolean isGroupAdmin(
        Connection con, String group, int sessionUsrSid, RequestModel reqMdl)
        throws SQLException {
        boolean result = false;
        List<LabelValueBean> groupLabel =
            getGroupLabelList(con, sessionUsrSid, GSConstTimecard.USER_KBN_GRPADM, reqMdl);
        int selectGrpSid = NullDefault.getInt(
            group, getInitGpSid(sessionUsrSid, groupLabel, con));

        for (LabelValueBean grpLabel : groupLabel) {
            if (selectGrpSid == Integer.parseInt(grpLabel.getValue())) {
                result = true;
            }

            //正常なグループが指定されてる場合、グループチェックを終了する
            if (result) {
                return true;
            }
        }

        return false;
    }

    /**
     * <br>[機  能] 表示グループ用のグループリストを取得する
     * <br>[解  説] 管理者設定の共有範囲が「ユーザ全員で共有」の場合有効な全てのグループを取得する。
     * <br>「所属グループ内のみ共有可」の場合、ユーザが所属するグループのみを返す。
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param usrKbn ユーザ種別
     * @param reqMdl リクエスト情報
     * @return ArrayList グループリストコンボ
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getGroupLabelList(Connection con,
            int usrSid, int usrKbn, RequestModel reqMdl) throws SQLException {

        List < LabelValueBean > labelList = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        if (usrKbn == GSConstTimecard.USER_KBN_ADMIN) {
            //管理者
            GroupBiz gpBiz = new GroupBiz();
            labelList = gpBiz.getGroupCombLabelList(con, true, gsMsg);
        } else if (usrKbn == GSConstTimecard.USER_KBN_GRPADM) {
            //グループ管理権限をもつグループのみ
            labelList = getGroupLabel(con, reqMdl, usrSid, false, 0);
        }

        return labelList;
    }

    /**
     * <br>[機  能] 階層構造を反映させたグループリストを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param userSid ユーザSID
     * @param sentakuFlg true:「非表示」のラベルを作成する, false:作成しない
     * @param mkCmbFlg コンボ作成フラグ 0:グループ管理権限をもつグループのみ 1:一般ユーザ
     * @return GroupModel in ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGroupLabel(Connection con,
                                                    RequestModel reqMdl,
                                                    int userSid,
                                                    boolean sentakuFlg,
                                                    int mkCmbFlg)
    throws SQLException {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        TcdUserGrpDao gpDao = new TcdUserGrpDao(con);

        //グループリストを取得
        List<TcdUserGrpModel> list = new ArrayList<TcdUserGrpModel>();
        if (mkCmbFlg == 0) {
            //グループ管理権限をもつグループのみ
            list = gpDao.selectGroupAdminOrderbyClass(userSid);

        } else if (mkCmbFlg == 1) {
            //一般ユーザ
            list = gpDao.selectGroupDataListOrderbyClass(userSid);
        }

        if (list == null || list.isEmpty()) {
            return null;
        }
        Iterator<TcdUserGrpModel> it = list.iterator();
        ArrayList<GroupModel> grpList = new ArrayList<GroupModel>();

        while (it.hasNext()) {
            TcdUserGrpModel gcmodel = it.next();
            GroupModel gtModel = new GroupModel();

            //グループSIDをセット
            gtModel.setGroupSid(gcmodel.getGroupSid());
            //グループ階層レベル
            gtModel.setClassLevel(gcmodel.getLowGrpLevel());
            //グループ名
            gtModel.setGroupName(__getGroupName(gtModel.getClassLevel(), gcmodel.getGroupName()));

            grpList.add(gtModel);
        }
        labelList = getGroupDspList(sentakuFlg, reqMdl, grpList);
        return labelList;
    }

    /**
     * <br>[機  能] グループ階層を反映させたグループ名を返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param classLevel グループ階層
     * @param groupName グループ名
     * @return グループ階層を反映させたグループ名
     */
    private String __getGroupName(int classLevel, String groupName) {

        String gpName = "";
        gpName = __getCombSpace(classLevel) + groupName;

        return gpName;
    }

    /**
     * <br>[機  能] グループ情報一覧を取得する(コンボボックス用)
     * <br>[解  説] 指定したユーザが属するグループの一覧を取得する
     * <br>         ユーザSID < 0 の場合は全グループの一覧を取得する
     * <br>[備  考]
     * @param level 階層ﾚﾍﾞﾙ
     * @return グループ情報一覧
     */
    private String __getCombSpace(int level) {

        String space = "";

        int spaceCount = level - 1;
        for (int count = 0; count < spaceCount; count++) {
            space += "　";
        }

        return space;
    }

    /**
     * <br>[機  能] 表示グループ用のグループリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sentakuFlg true:「選択してください」のラベルを作成する, false:作成しない
     * @param reqMdl リクエスト情報
     * @param gpList グループリスト
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGroupDspList(boolean sentakuFlg,
                                                    RequestModel reqMdl,
                                                    List<GroupModel> gpList) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        //選択してください
        String textSelect = gsMsg.getMessage("cmn.select.plz");

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        if (sentakuFlg) {
            labelList.add(new LabelValueBean(textSelect, String.valueOf(-1)));
        }

        //グループリスト取得
        for (GroupModel gmodel : gpList) {
            labelList.add(new LabelValueBean(gmodel.getGroupName(), String
                    .valueOf(gmodel.getGroupSid())));
        }
        return labelList;
    }

    /**
     * <br>[機  能] グループリストの中にデフォルトグループが存在した場合そのグループSIDを返します。
     * <br>[解  説] デフォルトグループが存在しない場合、リストの先頭を返します。
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param groupLabel グループラベルリスト
     * @param con コネクション
     * @return int グループSID
     * @throws SQLException SQL実行時例外
     */
    public int getInitGpSid(int userSid, List<LabelValueBean> groupLabel, Connection con)
    throws SQLException {
        int ret = 0;
        if (groupLabel == null) {
            return ret;
        }
        GroupBiz gpBiz = new GroupBiz();
        int dfGpSid = gpBiz.getDefaultGroupSid(userSid, con);
        LabelValueBean labelBean = null;
        for (int i = 0; i < groupLabel.size(); i++) {
            labelBean = groupLabel.get(i);
            if (Integer.parseInt(labelBean.getValue()) == dfGpSid) {
                ret = dfGpSid;
            }
        }
        if (ret == 0) {
            labelBean = groupLabel.get(0);
            ret = Integer.parseInt(labelBean.getValue());
        }
        return ret;
    }

    /**
     * <br>[機  能] アクセス権限の有無を判定する
     * <br>[解  説] システム管理者，プラグイン管理者及び、指定されたグループのグループ管理者かどうかを判定する
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @param group グループ
     * @param reqMdl リクエスト情報
     * @return true:アクセス可能 false:アクセス不可能
     * @throws SQLException SQL実行例外
     */
    public boolean isAccessOk(
            HttpServletRequest req,
            Connection con,
            String group,
            RequestModel reqMdl) throws SQLException {

        //セッション情報を取得
        con.setAutoCommit(true);
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        TimecardBiz tcdBiz = new TimecardBiz();
        int usrKbn = tcdBiz.getUserKbn(con, usModel);
        con.setAutoCommit(false);
        if (usrKbn == GSConstTimecard.USER_KBN_NORMAL) {
            return false;
        } else if (usrKbn == GSConstTimecard.USER_KBN_GRPADM) {
            return tcdBiz.isGroupAdmin(con, group, usModel.getUsrsid(), reqMdl);
        }

        return true;
    }

    /**
     * <br>[機  能] アクセス権限の有無を判定する
     * <br>[解  説] システム管理者，プラグイン管理者又はグループ管理者であればアクセス可能とする
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @return true:アクセス可能 false:アクセス不可能
     * @throws SQLException SQL実行例外
     */
    public boolean isAccessOk(HttpServletRequest req, Connection con) throws SQLException {

        //セッション情報を取得
        con.setAutoCommit(true);
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        TimecardBiz tcdBiz = new TimecardBiz();
        int usrKbn = tcdBiz.getUserKbn(con, usModel);
        con.setAutoCommit(false);
        if (usrKbn == GSConstTimecard.USER_KBN_NORMAL) {
            return false;
        }

        return true;
    }
    
    /**
     * <br>[機  能] 勤務表フォーマット用ひな形の出力処理
     * <br>[解  説]
     * <br>[備  考]
     * @param parmModel タイムカード作成パラメータ
     * @param subId サブディレクトリID
     * @throws SQLException SQL実行例外
     * @throws IOException 入出力例外
     */
    public void createTimeCardHina(TimeCardXlsParametarModel parmModel, String subId)
    throws SQLException, IOException {

        log__.debug("勤務表出力を行います。");

        OutputStream oStream = null;
        Connection con = parmModel.getCon();
        String appRootPath = parmModel.getAppRootPath();
        String outPath = parmModel.getOutTempDir();

        //基本設定取得
        TimecardBiz tmBiz = new TimecardBiz(reqMdl__);
        TcdAdmConfModel admMdl = tmBiz.getTcdAdmConfModel(parmModel.getTargetUserSid(), con);

        //休日区分情報を取得
        TcdHolidayInfDao holInfDao = new TcdHolidayInfDao(con);
        List<TcdHolidayInfModel> holDataList = holInfDao.getAllHolidayList();
        try {
            IOTools.isDirCheck(outPath, true);
            String bookName = parmModel.getOutBookTmpName();
            oStream = new FileOutputStream(outPath + bookName);
            ExcelUtil eUtil = new ExcelUtil(reqMdl__);
            eUtil.createHina(con, outPath,
                    appRootPath, oStream, admMdl, holDataList, subId);
        } catch (Exception e) {
            log__.error("勤務表出力に失敗しました。", e);
        } finally {
            oStream.flush();
            oStream.close();
        }
        log__.debug("勤務表出力を終了します。");
    }

}
