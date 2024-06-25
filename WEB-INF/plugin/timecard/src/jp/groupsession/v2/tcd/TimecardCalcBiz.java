package jp.groupsession.v2.tcd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.dao.TcdTcdataDao;
import jp.groupsession.v2.tcd.dao.TcdTimezoneDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;
import jp.groupsession.v2.tcd.model.TcdTimeModel;
import jp.groupsession.v2.tcd.model.TcdTimezoneModel;
import jp.groupsession.v2.tcd.model.TcdTotalValueModel;
import jp.groupsession.v2.tcd.tcd010.Tcd010Model;

/**
 * <br>[機  能] タイムカードプラグインで共通使用するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TimecardCalcBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TimecardCalcBiz.class);

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /** 最小時刻 */
    private static final int MINIMUM_TIME = 0;
    /** 最大時刻 */
    private static final int MAXIMUM_TIME = 2400;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public TimecardCalcBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public TimecardCalcBiz() {
    }

    /**
     * <br>[機  能] ユーザSIDと年月を指定しタイムカード集計値を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param date 開始日
     * @param endDate 終了日
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return TcdTotalValueModel 集計モデル
     * @throws SQLException SQL実行時例外
     */
    public TcdTotalValueModel getTotalValueModel(
            int usrSid,
            UDate date,
            UDate endDate,
            int sessionUsrSid,
            Connection con,
            RequestModel reqMdl)
    throws SQLException {

        //基本設定取得
        TimecardBiz tmBiz = new TimecardBiz(reqMdl);
        TcdAdmConfModel admMdl = tmBiz.getTcdAdmConfModel(sessionUsrSid, con);
        //時間帯設定取得
        TcdTimezoneDao tDao = new TcdTimezoneDao(con);
        HashMap<Integer, ArrayList<TcdTimezoneModel>> timeZoneMap = tDao.getTimezoneCalcData();

        //デフォルト時間帯の取得
        int defaultTtiSid = tmBiz.getDefTimezone(con, usrSid, admMdl);
        //休日情報取得
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayListFotTcd(date, endDate);
        //休日区分情報一覧を取得
        TcdHolidayInfDao holInfDao = new TcdHolidayInfDao(con);
        List<TcdHolidayInfModel> holDataList = holInfDao.getAllHolidayList();

        //休日区分毎の日数Mapping
        Map<Integer, BigDecimal> holDaysMap = new HashMap<Integer, BigDecimal>();

        //タイムカード情報取得
        TcdTcdataDao tcDao = new TcdTcdataDao(con);
        List<Tcd010Model> tcd010List = tcDao.select(usrSid, date, endDate);

        BigDecimal kadoBaseDays = BigDecimal.ZERO;  //稼動基準日数
        BigDecimal workDays = BigDecimal.ZERO;      //稼動実績日数
        BigDecimal kadoBaseHours = BigDecimal.ZERO; //稼動基準時間数
        BigDecimal workHours = BigDecimal.ZERO;     //稼動実績時間数
        BigDecimal zangyoDays = BigDecimal.ZERO;    //残業日数
        BigDecimal zangyoHours = BigDecimal.ZERO;   //残業時間数
        BigDecimal sinyaDays = BigDecimal.ZERO;     //深夜日数
        BigDecimal sinyaHours = BigDecimal.ZERO;    //深夜時間数
        BigDecimal kyusyutuDays = BigDecimal.ZERO;  //休日出勤日数
        BigDecimal kyusyutuHours = BigDecimal.ZERO; //休日出勤時間数
        BigDecimal chikokuDays = BigDecimal.ZERO;   //遅刻日数
        BigDecimal chikokuTimes = BigDecimal.ZERO;  //遅刻時間
        BigDecimal soutaiDays = BigDecimal.ZERO;    //早退日数
        BigDecimal soutaiTimes = BigDecimal.ZERO;    //早退時間
        BigDecimal yuukyuDays = BigDecimal.ZERO;    //有給休暇日数
        BigDecimal kekkinDays = BigDecimal.ZERO;    //欠勤日数

        //基準:稼働日数・稼働時間
        ArrayList<TcdTimezoneModel> defalutTimezoneList =  timeZoneMap.get(defaultTtiSid);
        kadoBaseDays = getBaseDays(date, endDate, admMdl, holMap, defalutTimezoneList);
        kadoBaseHours = getBaseHours(kadoBaseDays, admMdl, defalutTimezoneList);

        //集計処理
        Tcd010Model model = null;
        HashMap<Integer, ArrayList<Time>> timeFrTo = new HashMap<Integer, ArrayList<Time>>();
        for (int i = 0; i < tcd010List.size(); i++) {
            model = tcd010List.get(i);
            int ttiSid = model.getTtiSid();
            ArrayList<TcdTimezoneModel> timeZoneList = timeZoneMap.get(ttiSid);
            if (model.getTcdOuttime() != null) {
                //終業時刻が未登録な場合集計対象外
                //実績稼動
                workDays = __getWorkDays(workDays, model);
                workHours = __getWorkHours(workHours, model, admMdl, timeZoneList); //分単位に集計
                zangyoDays = __getZangyoWorkDays(zangyoDays, model, admMdl, timeZoneList);
                zangyoHours = __getZangyoWorkHours(zangyoHours, model, admMdl, timeZoneList);
                sinyaDays = __getSinyaWorkDays(sinyaDays, model, admMdl, timeZoneList);
                sinyaHours = __getSinyaWorkHours(sinyaHours, model, admMdl, timeZoneList);
                //休日曜日
                kyusyutuDays = __getHolidayWorkDays(kyusyutuDays,
                        model, admMdl, holMap, timeZoneList);
                //休日出勤稼動
                kyusyutuHours = __getHolidayWorkHours(
                        kyusyutuHours, model, admMdl, holMap, timeZoneList);
            }
            Time normalFrTime = null;
            Time normalToTime = null;
            if (model.getTcdChkkbn() == GSConstTimecard.CHK_KBN_SELECT
                    || model.getTcdSoukbn() == GSConstTimecard.SOU_KBN_SELECT) {
                ArrayList<Time> list = new ArrayList<Time>();
                if (timeFrTo.get(ttiSid) == null) {
                    normalFrTime = _getTimezoneFrTime(timeZoneList);
                    normalToTime = _getTimezoneToTime(timeZoneList);
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
            if (model.getTcdChkkbn() == GSConstTimecard.CHK_KBN_SELECT) {
                chikokuDays = __getChikokuDays(chikokuDays, model);
                chikokuTimes = __getChikokuTimes(chikokuTimes, model, admMdl, timeZoneList,
                        normalFrTime, normalToTime);
            }

            //早退
            if (model.getTcdSoukbn() == GSConstTimecard.SOU_KBN_SELECT) {
                soutaiDays = __getSoutaiDays(soutaiDays, model);
                soutaiTimes = __getSoutaiTimes(soutaiTimes, model, admMdl, timeZoneList,
                                            normalFrTime, normalToTime);
            }

            //休日区分毎の休日日数をを集計
            for (TcdHolidayInfModel holData : holDataList) {
                if (model.getThiSid() == holData.getThiSid()) {
                    holDaysMap = __getHolidayDays(model, holDaysMap, holData.getThiSid());

                    //休日集計区分 = 2:有休の場合、有休日数(消費日数)の合計を行う
                    if (holData.getThiHoltotalKbn() == GSConstTimecard.HOL_KBN_YUUKYUU) {
                        yuukyuDays = yuukyuDays.add(model.getTcdHolcnt());
                    }
                    //休日集計区分 = 1:欠勤の場合、欠勤日数(消費日数)の合計を行う
                    if (holData.getThiHoltotalKbn() == GSConstTimecard.HOL_KBN_KEKKIN) {
                        kekkinDays = kekkinDays.add(model.getTcdHolcnt());
                    }
                }
            }
        }
        TcdTotalValueModel ret = new TcdTotalValueModel();
        ret.setStartDate(date);
        ret.setEndDate(endDate);
        ret.setKadoBaseDays(kadoBaseDays);
        ret.setKadoDays(workDays);
        ret.setKadoBaseHours(kadoBaseHours);
        ret.setKadoHours(TimecardBiz.convertMinToHourBigDecimal(admMdl, workHours));
        ret.setZangyoDays(zangyoDays);
        ret.setZangyoHours(TimecardBiz.convertMinToHourBigDecimal(admMdl, zangyoHours));
        ret.setSinyaDays(sinyaDays);
        ret.setSinyaHours(TimecardBiz.convertMinToHourBigDecimal(admMdl, sinyaHours));
        ret.setKyusyutuDays(kyusyutuDays);
        ret.setKyusyutuHours(TimecardBiz.convertMinToHourBigDecimal(admMdl, kyusyutuHours));
        ret.setChikokuDays(chikokuDays);
        ret.setChikokuTimes(TimecardBiz.convertMinToHourBigDecimal(admMdl, chikokuTimes));
        ret.setSoutaiDays(soutaiDays);
        ret.setSoutaiTimes(TimecardBiz.convertMinToHourBigDecimal(admMdl, soutaiTimes));
        ret.setYuukyuDays(
                TimecardBiz.convertDispBigDecimal(yuukyuDays,
                        1, RoundingMode.UNNECESSARY));
        ret.setKekkinDays(
                TimecardBiz.convertDispBigDecimal(kekkinDays,
                        1, RoundingMode.UNNECESSARY));
        ret.setYuukyuDaysStr(StringUtil.toCommaFromBigDecimal(ret.getYuukyuDays()));
        ret.setKekkinDaysStr(StringUtil.toCommaFromBigDecimal(ret.getKekkinDays()));
        //休日区分毎の休日日数を設定
        for (TcdHolidayInfModel holData : holDataList) {
            BigDecimal holDays = holDaysMap.get(holData.getThiSid());
            if (holDays == null) {
                holDays = new BigDecimal(0);
            }
            ret.setHolDays(holData.getThiSid(), holDays);
        }

        //表示用フォーマットを設定
        setTotalValueModelDspString(ret, con);

        return ret;
    }

    /**
     * <br>[機  能] 指定された時間帯の通常開始時間を取得する
     * <br>[解  説] 夜勤を想定した時間帯の開始時間も考慮する
     * <br>1.通常又は休憩時間帯が00：00から始まる場合　→　2へ
     * <br>2.通常又は休憩時間帯が00：00で終わる場合　→　夜勤判定
     * <br>1.2に当てはまらない場合、日中勤務判定　通常時間帯内で最も時間が早い開始時間を返す
     * <br>夜勤判定　残業時間帯の中で最も遅い時間の次の通常時間帯又休憩時間の開始時間を返す
     * <br>[備  考]
     * @param timezoneList 時間帯情報
     * @return ret time型
     */
    protected Time _getTimezoneFrTime(ArrayList<TcdTimezoneModel> timezoneList) {

        Time ret = null;
        if (timezoneList != null && timezoneList.size() > 0) {
            ArrayList<TcdTimezoneModel> timeList = new ArrayList<TcdTimezoneModel>();
            //深夜時間帯を除いたリストを作成する
            for (TcdTimezoneModel tmdl : timezoneList) {
                if (tmdl.getTtzKbn() != GSConstTimecard.TIMEZONE_KBN_SINYA) {
                    timeList.add(tmdl);
                }
            }
            boolean yakin = __yakinHantei(timeList);
            if (yakin) {
                if (timeList.size() == 2) {
                    return timeList.get(timeList.size() - 1).getTtzFrtime();
                }
                //3.各時間帯判定（夜勤判定後）
                //3-1.残業時間の最大時間を取得
                Time hikaku = null;
                for (TcdTimezoneModel roopMdl : timeList) {
                    if (roopMdl.getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_ZANGYO) {
                        if (hikaku == null) {
                            hikaku = roopMdl.getTtzTotime();
                        } else if (hikaku.compareTo(roopMdl.getTtzTotime()) < 0) {
                            hikaku = roopMdl.getTtzTotime();
                        }
                    }
                }
                if (hikaku == null) {
                    return __gettimeForTimezoneFrTime(timeList);
                }
                //3-2.残業時間以降の通常時間又は休憩時間の開始時間を取得
                Time hikaku2 = null;
                for (TcdTimezoneModel roopMdl : timeList) {
                    if ((roopMdl.getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_NORMAL
                            || roopMdl.getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_KYUKEI)
                            && hikaku.compareTo(roopMdl.getTtzFrtime()) >= 0
                            && hikaku2 == null) {
                        hikaku2 = roopMdl.getTtzFrtime();
                    } else if ((roopMdl.getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_NORMAL
                            || roopMdl.getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_KYUKEI)
                            && hikaku.compareTo(roopMdl.getTtzFrtime()) >= 0
                            && hikaku2 != null
                            && hikaku2.compareTo(roopMdl.getTtzFrtime()) < 0) {
                        hikaku2 = roopMdl.getTtzFrtime();
                    }
                }
                ret =  hikaku2;
            } else {
                ret =  __gettimeForTimezoneFrTime(timeList);
            }
            if (ret == null) {
                ret = __gettimeForTimezoneFrTime(timeList);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定された時間帯の通常終了時間を取得する
     * <br>[解  説] 夜勤を想定した時間帯の開始時間も考慮する
     * <br>1.通常又は休憩時間帯が00：00から始まる場合　→　2へ
     * <br>2.通常又は休憩時間帯が00：00で終わる場合　→　夜勤判定
     * <br>1.2に当てはまらない場合、日中勤務判定　通常時間帯内で最も時間が遅い終了時間を返す
     * <br>夜勤判定　残業時間帯の中で最も早い時間の前の通常時間帯又は休憩時間帯の終了時間を返す
     * <br>[備  考]
     * @param timezoneList timezoneList
     * @return ret time型
     */
    protected Time _getTimezoneToTime(ArrayList<TcdTimezoneModel> timezoneList) {

        Time ret = null;
        if (timezoneList != null && timezoneList.size() > 0) {
            ArrayList<TcdTimezoneModel> timeList = new ArrayList<TcdTimezoneModel>();
            //深夜時間帯を除いたリストを作成する
            for (TcdTimezoneModel tmdl : timezoneList) {
                if (tmdl.getTtzKbn() != GSConstTimecard.TIMEZONE_KBN_SINYA) {
                    timeList.add(tmdl);
                }
            }
            boolean yakin = __yakinHantei(timeList);
            if (yakin) {
                if (timeList.size() == 2) {
                    return timeList.get(0).getTtzTotime();
                }
                for (int idx = timeList.size() - 2; idx >= 0; idx--) {
                    //3.各時間帯判定
                    Time saveTime = null;
                    for (TcdTimezoneModel tMdl : timeList) {
                        if (tMdl.getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_NORMAL
                                || tMdl.getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_KYUKEI) {
                            saveTime = tMdl.getTtzTotime();
                        }
                        if (saveTime != null && tMdl.getTtzKbn()
                                == GSConstTimecard.TIMEZONE_KBN_ZANGYO) {
                            ret = saveTime;
                            break;
                        }
                    }
                }
            } else {
                ret = __gettimeForTimezoneToTime(timeList);
            }
            if (ret == null) {
                ret = __gettimeForTimezoneToTime(timeList);
            }
        }
        return ret;
    }


    /**
     * <br>[機  能] 夜勤かどうかの判定を行う（遅刻・早退用）
     * <br>[解  説]
     * <br>[備  考]
     * @param timeList 深夜時間帯を抜いた時間帯リスト
     * @return ret true:夜勤 false:日勤
     */
    private boolean __yakinHantei(ArrayList<TcdTimezoneModel> timeList) {

        boolean ret = false;
        if (timeList != null && timeList.size() > 0) {
            UDate baseDate = new UDate();
            baseDate.setZeroHhMmSs();
            UDate frDate = baseDate.cloneUDate();
            UDate toDate = baseDate.cloneUDate();
            UDate hikakuDate = baseDate.cloneUDate();
            Time frTime = timeList.get(0).getTtzFrtime();
            frDate.setHour(TimecardUtil.getTimeHour(frTime));
            frDate.setMinute(TimecardUtil.getTimeMin(frTime));
            hikakuDate.setHour(0);
            hikakuDate.setMinute(0);
            //1.通常又は休憩時間帯が00：00から始まるか
            if (frDate.equalsTimeStamp(hikakuDate)
                    && (timeList.get(0).getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_NORMAL
                    ||  timeList.get(0).getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_KYUKEI)) {
                Time toTime = timeList.get(timeList.size() - 1).getTtzTotime();
                toDate.setHour(TimecardUtil.getTimeHour(toTime));
                toDate.setMinute(TimecardUtil.getTimeMin(toTime));
                if (timeList.size() == 1) {
                    ret = false;
                }
                //2.通常又は休憩時間帯が00：00で終わるか
                if (toDate.equalsTimeStamp(hikakuDate)
                        && (timeList.get(0).getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_NORMAL
                        ||  timeList.get(0).getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_KYUKEI)) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] __getTimezoneFrTimeメソッドでの通常時間帯の開始時間を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param timeList
     * @return
     */
    private Time __gettimeForTimezoneFrTime(ArrayList<TcdTimezoneModel> timeList) {

        Time ret = null;
        for (int idx = 0; idx < timeList.size();  idx++) {
            if (timeList.get(idx).getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_NORMAL) {
                ret = timeList.get(idx).getTtzFrtime();
                break;
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] __getTimezoneToTimeメソッドでの通常時間帯の最終時間を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param timeList
     * @return
     */
    private Time __gettimeForTimezoneToTime(ArrayList<TcdTimezoneModel> timeList) {

        Time ret = null;
        for (int idx = timeList.size() - 1; idx >= 0;  idx--) {
            if (timeList.get(idx).getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_NORMAL) {
                ret = timeList.get(idx).getTtzTotime();
                break;
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 休日ごとの日数を引数のMap<Integer, BigDecimal> holDaysMapへ集計します。
     * <br>[解  説]
     * <br>[備  考]
     * @param model タイムカード情報
     * @param holDaysMap 休日区分,日数
     * @param thiSid 休日区分Sid
     * @return holDaysMap 休日日数集計結果
     */
    private Map<Integer, BigDecimal> __getHolidayDays(
            Tcd010Model model, Map<Integer, BigDecimal> holDaysMap, int thiSid) {
        BigDecimal holDays = BigDecimal.ZERO;
        holDays = holDaysMap.get(thiSid);
        if (holDays == null) {
            holDays = BigDecimal.ZERO;
        }
        holDays = holDays.add(model.getTcdHolcnt());
        holDaysMap.put(thiSid, holDays);

        return holDaysMap;
    }

    /**
     * <br>[機  能] 集計モデルに表示用フォーマットを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ttlMdl 集計モデル
     * @param con コネクション
     * @throws SQLException
     */
    public void setTotalValueModelDspString(TcdTotalValueModel ttlMdl,
            Connection con) throws SQLException {

        ttlMdl.setKadoBaseDaysStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getKadoBaseDays()));
        ttlMdl.setKadoDaysStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getKadoDays()));
        ttlMdl.setKadoBaseHoursStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getKadoBaseHours()));
        ttlMdl.setKadoHoursStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getKadoHours()));
        ttlMdl.setZangyoDaysStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getZangyoDays()));
        ttlMdl.setZangyoHoursStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getZangyoHours()));
        ttlMdl.setSinyaDaysStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getSinyaDays()));
        ttlMdl.setSinyaHoursStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getSinyaHours()));
        ttlMdl.setKyusyutuDaysStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getKyusyutuDays()));
        ttlMdl.setKyusyutuHoursStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getKyusyutuHours()));
        ttlMdl.setChikokuDaysStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getChikokuDays()));
        ttlMdl.setChikokuTimesStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getChikokuTimes()));
        ttlMdl.setSoutaiDaysStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getSoutaiDays()));
        ttlMdl.setSoutaiTimesStr(StringUtil.toCommaFromBigDecimal(ttlMdl.getSoutaiTimes()));

        TcdHolidayInfDao holInfDao = new TcdHolidayInfDao(con);
        List<TcdHolidayInfModel> holDataList = holInfDao.getAllHolidayList();
        for (TcdHolidayInfModel holData : holDataList) {
            BigDecimal holiday = ttlMdl.getHolDays(holData.getThiSid());
            if (holiday == null) {
                holiday = new BigDecimal(0);
            }
            TimecardBiz tBiz = new TimecardBiz();
            String holidayStr = StringUtil.toCommaFromBigDecimal(holiday);
            holidayStr = tBiz.dispSyukei(holidayStr);
            ttlMdl.setHolDaysStr(holData.getThiSid(), holidayStr);
        }
    }

    /**
     * <br>[機  能] 稼動実績日数を引数のBigDecimal workDaysへ集計します。
     * <br>[解  説]
     * <br>[備  考]
     * @param kadoDays 稼動実績日数(集計先)
     * @param model タイムカード情報
     * @return BigDecimal 稼動実績日数
     */
    private BigDecimal __getWorkDays(BigDecimal kadoDays, Tcd010Model model) {
        if (model.getTcdOuttime() != null) {
            kadoDays = kadoDays.add(BigDecimal.valueOf(1));
        }
        return kadoDays;
    }

    /**
     * <br>[機  能] 稼動実績時間数を引数のBigDecimal workHoursへ集計します。
     * <br>[解  説]
     * <br>[備  考]
     * @param kadoHours 稼動実績時間数(集計先)
     * @param model タイムカード情報
     * @param admMdl 基本設定
     * @param timeZoneList 時間帯情報
     * @return BigDecimal 稼動実績時間数
     */
    private BigDecimal __getWorkHours(
            BigDecimal kadoHours,
            Tcd010Model model,
            TcdAdmConfModel admMdl,
            ArrayList<TcdTimezoneModel> timeZoneList) {

        if (model.getTcdOuttime() != null) {
            List<UDate> udateList = __getTimeToUDate(model, admMdl);

            UDate cvFrDate = udateList.get(0);
            UDate cvToDate = udateList.get(1);

            if (cvToDate.compareDateYMDHM(cvFrDate) != UDate.LARGE && timeZoneList != null) {
                long min = UDateUtil.diffMinute(cvFrDate, cvToDate);
                long submin = __getRepetitionTimeZoneMinute(
                        cvFrDate, cvToDate, timeZoneList, GSConstTimecard.TIMEZONE_KBN_KYUKEI);
                kadoHours = kadoHours.add(BigDecimal.valueOf(min - submin));
            }
        }

        return kadoHours;
    }

    /**
     * <br>[機  能] Time型からUdate型に変換する
     * <br>[解  説] 始業時間の方が終業時間より大きい場合に日付を1日増やす
     * <br>[備  考]
     * @param model タイムカード情報
     * @param admMdl 基本設定
     * @return udateList 0:始業時間 1:終業時間
     */
    private List<UDate> __getTimeToUDate(Tcd010Model model,
            TcdAdmConfModel admMdl) {
        UDate frDate = new UDate();
        frDate.setZeroHhMmSs();

        UDate toDate = frDate.cloneUDate();
        Time frTime = model.getTcdIntime();
        Time toTime = model.getTcdOuttime();
        frDate.setHour(TimecardUtil.getTimeHour(frTime));
        frDate.setMinute(TimecardUtil.getTimeMin(frTime));
        toDate.setHour(TimecardUtil.getTimeHour(toTime));
        toDate.setMinute(TimecardUtil.getTimeMin(toTime));
        List<UDate> udateList = new ArrayList<UDate>();

        if (toDate.compareDateYMDHM(frDate) == UDate.LARGE) {
            toDate.addDay(1);
        }

        UDate cvFrDate = TimecardBiz.convertForDspTime(frDate, admMdl, true);
        UDate cvToDate = TimecardBiz.convertForDspTime(toDate, admMdl, false);
        udateList.add(cvFrDate);
        udateList.add(cvToDate);

        return udateList;
    }

    /**
     * <br>[機  能] 残業実績日数を引数のBigDecimal zangyoDaysへ集計します。
     * <br>[解  説]
     * <br>[備  考]
     * @param zangyoDays 残業実績日数(集計先)
     * @param model タイムカード情報
     * @param admMdl 基本設定
     * @param timeZoneList 時間帯情報
     * @return BigDecimal 残業実績日数
     */
    private BigDecimal __getZangyoWorkDays(
            BigDecimal zangyoDays,
            Tcd010Model model,
            TcdAdmConfModel admMdl,
            ArrayList<TcdTimezoneModel> timeZoneList) {

        List<UDate> udateList = __getTimeToUDate(model, admMdl);

        UDate cvFrDate = udateList.get(0);
        UDate cvToDate = udateList.get(1);

        //始業時間 > 終了時間の場合、計算を行わない
        if (cvToDate.compareDateYMDHM(cvFrDate) != UDate.LARGE && timeZoneList != null) {
            long zanmin = __getRepetitionTimeZoneMinute(
                    cvFrDate, cvToDate, timeZoneList, GSConstTimecard.TIMEZONE_KBN_ZANGYO);
            if (zanmin > 0) {
                zangyoDays = zangyoDays.add(BigDecimal.valueOf(1));
            }
        }
        return zangyoDays;
    }

    /**
     * <br>[機  能] 残業実績日数を引数のBigDecimal kadoDaysへ集計します。
     * <br>[解  説]
     * <br>[備  考]
     * @param zangyoHours 残業実績時間数(集計先)
     * @param model タイムカード情報
     * @param admMdl 基本設定
     * @param timeZoneList 時間帯情報
     * @return BigDecimal 稼動実績日数
     */
    private BigDecimal __getZangyoWorkHours(
            BigDecimal zangyoHours,
            Tcd010Model model,
            TcdAdmConfModel admMdl,
            ArrayList<TcdTimezoneModel> timeZoneList) {

        List<UDate> udateList = __getTimeToUDate(model, admMdl);

        UDate cvFrDate = udateList.get(0);
        UDate cvToDate = udateList.get(1);

        //始業時間 > 終了時間の場合、計算を行わない
        if (cvToDate.compareDateYMDHM(cvFrDate) != UDate.LARGE && timeZoneList != null) {
            long zanmin = __getRepetitionTimeZoneMinute(
                    cvFrDate, cvToDate, timeZoneList, GSConstTimecard.TIMEZONE_KBN_ZANGYO);
            zangyoHours = zangyoHours.add(BigDecimal.valueOf(zanmin));
        }
        return zangyoHours;
    }

    /**
     * <br>[機  能] 深夜実績日数を引数のBigDecimal sinyaDaysへ集計します。
     * <br>[解  説]
     * <br>[備  考]
     * @param sinyaDays 残業実績日数(集計先)
     * @param model タイムカード情報
     * @param admMdl 基本設定
     * @param timeZoneList 時間帯情報
     * @return BigDecimal 残業実績日数
     */
    private BigDecimal __getSinyaWorkDays(
            BigDecimal sinyaDays,
            Tcd010Model model,
            TcdAdmConfModel admMdl,
            ArrayList<TcdTimezoneModel> timeZoneList) {

        List<UDate> udateList = __getTimeToUDate(model, admMdl);

        UDate cvFrDate = udateList.get(0);
        UDate cvToDate = udateList.get(1);

        //始業時間 > 終了時間の場合、計算を行わない
        if (cvToDate.compareDateYMDHM(cvFrDate) != UDate.LARGE && timeZoneList != null) {
            long sinyamin = __getRepetitionTimeZoneMinute(
                    cvFrDate, cvToDate, timeZoneList, GSConstTimecard.TIMEZONE_KBN_SINYA);

            if (sinyamin > 0) {
                sinyaDays = sinyaDays.add(BigDecimal.valueOf(1));
            }
        }
        return sinyaDays;
    }

    /**
     * <br>[機  能] 深夜実績時間数を引数のBigDecimal sinyaHoursへ集計します。
     * <br>[解  説]
     * <br>[備  考]
     * @param sinyaHours 深夜実績時間(集計先)
     * @param model タイムカード情報
     * @param admMdl 基本設定
     * @param timeZoneList 時間帯情報
     * @return BigDecimal 深夜実績時間
     */
    private BigDecimal __getSinyaWorkHours(
            BigDecimal sinyaHours,
            Tcd010Model model,
            TcdAdmConfModel admMdl,
            ArrayList<TcdTimezoneModel> timeZoneList) {

        List<UDate> udateList = __getTimeToUDate(model, admMdl);

        UDate cvFrDate = udateList.get(0);
        UDate cvToDate = udateList.get(1);

        //始業時間 > 終了時間の場合、計算を行わない
        if (cvToDate.compareDateYMDHM(cvFrDate) != UDate.LARGE && timeZoneList != null) {

            long sinyamin = __timeSinya(timeZoneList, cvFrDate, cvToDate);
            sinyaHours = sinyaHours.add(BigDecimal.valueOf(sinyamin));
        }
        return sinyaHours;
    }

    /**
     * <br>[機  能] 深夜時間を取得する
     * <br>[解  説] 実稼働時間から休憩時間を引いた深夜時間帯を計算して取得する
     * <br>[備  考]
     * @param timeZoneList 時間情報
     * @param frDateZis 実績の開始時間
     * @param toDateZis 実績の終了時間
     * @return long 分
     */
    private long __timeSinya(ArrayList<TcdTimezoneModel> timeZoneList, UDate frDateZis,
            UDate toDateZis) {

        long ret = 0;
        //深夜時間帯リスト
        ArrayList<TcdTimeModel> sinyaList = new ArrayList<TcdTimeModel>();
        //休憩時間帯リスト
        ArrayList<TcdTimeModel> kyukeiList = new ArrayList<TcdTimeModel>();
        for (TcdTimezoneModel tzMdl : timeZoneList) {
            //実稼働時間から深夜時間を深夜時間リストに設定
            if (tzMdl.getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_SINYA) {
                Time sinyaFrTz = tzMdl.getTtzFrtime();
                Time sinyaToTz = tzMdl.getTtzTotime();
                int sinyaFrHm =
                        TimecardUtil.getTimeHour(sinyaFrTz) * 100
                        + TimecardUtil.getTimeMin(sinyaFrTz);
                int sinyaToHm =
                        TimecardUtil.getTimeHour(sinyaToTz) * 100
                        + TimecardUtil.getTimeMin(sinyaToTz);
                if (sinyaToHm < sinyaFrHm) {
                    sinyaToHm += 2400;
                }
                int[] frHm;
                int[] toHm;
                if (toDateZis.getIntHour() * 100 + toDateZis.getIntMinute()
                  < frDateZis.getIntHour() * 100 + frDateZis.getIntMinute()) {
                    frHm = new int[2];
                    toHm = new int[2];
                    frHm[0] = frDateZis.getIntHour() * 100 + frDateZis.getIntMinute();
                    toHm[0] = MAXIMUM_TIME;
                    frHm[1] = MINIMUM_TIME;
                    toHm[1] = toDateZis.getIntHour() * 100 + toDateZis.getIntMinute();
                } else {
                    frHm = new int[1];
                    toHm = new int[1];
                    frHm[0] = frDateZis.getIntHour() * 100 + frDateZis.getIntMinute();
                    toHm[0] = toDateZis.getIntHour() * 100 + toDateZis.getIntMinute();
                }
                for (int j = 0; j < frHm.length; j++) {
                    TcdTimeModel ttMdl = new TcdTimeModel();
                    if ((frHm[j] < sinyaToHm) && (toHm[j] > sinyaFrHm)) {
                        // 開始時間
                        int nStart = frHm[j];
                        if (nStart < sinyaFrHm) {
                            nStart = sinyaFrHm;
                        }
                        // 終了時間
                        int nStop = toHm[j];
                        if (nStop > sinyaToHm) {
                            nStop = sinyaToHm;
                        }
                        ttMdl.setTcdFrMin(nStart);
                        ttMdl.setTcdToMin(nStop);
                        sinyaList.add(ttMdl);
                    }
                }
            }
            //休憩時間リスト作成
            if (tzMdl.getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_KYUKEI) {
                Time kyukeiFrTz = tzMdl.getTtzFrtime();
                Time kyukeiToTz = tzMdl.getTtzTotime();
                UDate kyukeiFr = new UDate();
                kyukeiFr.setZeroHhMmSs();
                kyukeiFr.setMilliSecond(0);
                UDate kyukeiTo = kyukeiFr.cloneUDate();
                kyukeiFr.setHour(TimecardUtil.getTimeHour(kyukeiFrTz));
                kyukeiFr.setMinute(TimecardUtil.getTimeMin(kyukeiFrTz));
                kyukeiTo.setHour(TimecardUtil.getTimeHour(kyukeiToTz));
                kyukeiTo.setMinute(TimecardUtil.getTimeMin(kyukeiToTz));
                int kyukeiFrHm =
                        TimecardUtil.getTimeHour(kyukeiFrTz) * 100
                        + TimecardUtil.getTimeMin(kyukeiFrTz);
                int kyukeiToHm =
                        TimecardUtil.getTimeHour(kyukeiToTz) * 100
                        + TimecardUtil.getTimeMin(kyukeiToTz);
                if (kyukeiToHm < kyukeiFrHm) {
                    kyukeiToHm += 2400;
                }
                if (kyukeiFr.getTimeMillis() > kyukeiTo.getTimeMillis()) {
                    kyukeiTo.addDay(1);
                }
                TcdTimeModel ttMdl = new TcdTimeModel();
                ttMdl.setTcdFrDate(kyukeiFr);
                ttMdl.setTcdToDate(kyukeiTo);
                ttMdl.setTcdFrMin(kyukeiFrHm);
                ttMdl.setTcdToMin(kyukeiToHm);
                kyukeiList.add(ttMdl);
            }
        }
        if (sinyaList.size() == 0) {
            return ret;
        }
        long jogai = 0;
        //深夜時間と休憩時間が重複する場合は休憩時間帯をひいた深夜時間を取得する
        for(TcdTimeModel snyMdl: sinyaList) {
            ret += changeTime(snyMdl.getTcdFrMin(), snyMdl.getTcdToMin());
            for (TcdTimeModel zyogai : kyukeiList) {
                UDate frDate = zyogai.getTcdFrDate();
                UDate toDate = zyogai.getTcdToDate();
                int[] frHm;
                int[] toHm;
                if (toDate.getIntHour() * 100 + toDate.getIntMinute()
                  < frDate.getIntHour() * 100 + frDate.getIntMinute()) {
                    frHm = new int[2];
                    toHm = new int[2];
                    frHm[0] = frDate.getIntHour() * 100 + frDate.getIntMinute();
                    toHm[0] = MAXIMUM_TIME;
                    frHm[1] = MINIMUM_TIME;
                    toHm[1] = toDate.getIntHour() * 100 + toDate.getIntMinute();
                } else {
                    frHm = new int[1];
                    toHm = new int[1];
                    frHm[0] = frDate.getIntHour() * 100 + frDate.getIntMinute();
                    toHm[0] = toDate.getIntHour() * 100 + toDate.getIntMinute();
                }

                for (int j = 0; j < frHm.length; j++) {

                    if ((frHm[j] < snyMdl.getTcdToMin()) && (toHm[j] > snyMdl.getTcdFrMin())) {
                        // 開始時間
                        int nStart = frHm[j];
                        if (nStart < snyMdl.getTcdFrMin()) {
                            nStart = snyMdl.getTcdFrMin();
                        }
                        // 終了時間
                        int nStop = toHm[j];
                        if (nStop > snyMdl.getTcdToMin()) {
                            nStop = snyMdl.getTcdToMin();
                        }
                        jogai += changeTime(nStart, nStop);
                    }
                }
            }
        }
        return ret - jogai;
    }

    /**
     * <br>[機  能] 休日出勤実績日数を引数のBigDecimal kyusyutuDaysへ集計します。
     * <br>[解  説]
     * <br>[備  考]
     * @param kyusyutuDays 休日出勤実績日数(集計先)
     * @param model タイムカード情報
     * @param admMdl 基本設定情報
     * @param holMap 休日情報
     * @return BigDecimal 休日出勤実績日数
     */
    private BigDecimal __getHolidayWorkDays(
            BigDecimal kyusyutuDays,
            Tcd010Model model,
            TcdAdmConfModel admMdl,
            HashMap<String, CmnHolidayModel> holMap,
            ArrayList<TcdTimezoneModel> timeZoneList) {

        UDate tcDate = model.getTcdDate();
        tcDate.setZeroHhMmSs();

        if (isHoliday(tcDate, admMdl, holMap, timeZoneList)) {
            kyusyutuDays = kyusyutuDays.add(BigDecimal.valueOf(1));
        }

        return kyusyutuDays;
    }

    /**
     * <br>[機  能] 休日出勤実績稼働時間を引数のBigDecimal kyusyutuHoursへ集計します。
     * <br>[解  説]
     * <br>[備  考]
     * @param kyusyutuHours 休日出勤実績日数(集計先)
     * @param model タイムカード情報
     * @param admMdl 基本設定情報
     * @param holMap 休日情報
     * @param timeZoneList 時間帯情報
     * @return BigDecimal 休日出勤実績日数
     */
    private BigDecimal __getHolidayWorkHours(
            BigDecimal kyusyutuHours,
            Tcd010Model model,
            TcdAdmConfModel admMdl,
            HashMap<String, CmnHolidayModel> holMap,
            ArrayList<TcdTimezoneModel> timeZoneList) {

        UDate tcDate = model.getTcdDate();
        tcDate.setZeroHhMmSs();

        if (isHoliday(tcDate, admMdl, holMap, timeZoneList)) {
            //終了登録済み
            if (model.getTcdOuttime() != null) {
                List<UDate> udateList = __getTimeToUDate(model, admMdl);

                UDate cvFrDate = udateList.get(0);
                UDate cvToDate = udateList.get(1);

                //始業時間 > 終了時間の場合、計算を行わない
                if (cvToDate.compareDateYMDHM(cvFrDate) != UDate.LARGE && timeZoneList != null) {

                    long min = UDateUtil.diffMinute(cvFrDate, cvToDate);
                    long submin = __getRepetitionTimeZoneMinute(
                            cvFrDate, cvToDate, timeZoneList, GSConstTimecard.TIMEZONE_KBN_KYUKEI);
                    kyusyutuHours = kyusyutuHours.add(BigDecimal.valueOf(min - submin));
                }
            }
        }
        return kyusyutuHours;
    }

    /**
     * <br>[機  能] 遅刻日数を引数のBigDecimal chikokuDaysへ集計します。
     * <br>[解  説]
     * <br>[備  考]
     * @param chikokuDays 遅刻実績日数(集計先)
     * @param model タイムカード情報
     * @return BigDecimal 遅刻実績日数
     */
    private BigDecimal __getChikokuDays(BigDecimal chikokuDays, Tcd010Model model) {

        if (model.getTcdChkkbn() == GSConstTimecard.CHK_KBN_SELECT) {
            chikokuDays = chikokuDays.add(BigDecimal.valueOf(1));
        }
        return chikokuDays;
    }



    /**
     * <br>[機  能] 遅刻時間を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param chikokuTimes 遅刻時間
     * @param model タイムカード情報
     * @param admMdl 基本設定
     * @param timeZoneList 時間帯情報
     * @param normalFrTime 通常時間帯開始時刻
     * @param normalToTime 通常時間帯終了時刻
     * @return BigDecimal 遅刻実績時間
     */
    private BigDecimal __getChikokuTimes(BigDecimal chikokuTimes,
                                        Tcd010Model model, TcdAdmConfModel admMdl,
                                        ArrayList<TcdTimezoneModel> timeZoneList,
                                        Time normalFrTime, Time normalToTime) {

        BigDecimal chikokuTime = null;
        //遅刻区分が設定済み
        //通常時間帯の開始時刻が取得済み
        //始業時間が取得済み
        if (model.getTcdChkkbn() == GSConstTimecard.CHK_KBN_SELECT
        && normalFrTime != null
        && model.getTcdIntime() != null) {

            //時間初期化用変数
            UDate initDate = new UDate();
            initDate.setZeroHhMmSs();

            /*通常時間帯開始時間*/
            UDate normalFrDate = initDate.cloneUDate();
            normalFrDate.setHour(TimecardUtil.getTimeHour(normalFrTime));
            normalFrDate.setMinute(TimecardUtil.getTimeMin(normalFrTime));

            /*通常時間帯終了時間*/
            UDate normalToDate = initDate.cloneUDate();
            if (normalToTime != null) {
                normalToDate.setHour(TimecardUtil.getTimeHour(normalToTime));
                normalToDate.setMinute(TimecardUtil.getTimeMin(normalToTime));
            }

            /*始業時刻*/
            UDate frDate = initDate.cloneUDate();
            Time frTime = model.getTcdIntime();
            if (admMdl != null) {
                frTime = TimecardBiz.adjustIntime(frTime, admMdl.getTacInterval());
            }
            frDate.setHour(TimecardUtil.getTimeHour(frTime));
            frDate.setMinute(TimecardUtil.getTimeMin(frTime));

            UDate toDate = initDate.cloneUDate();

            //始業時刻 > 通常時間帯終了時間の場合、始業時刻 = 通常時間帯終了時刻とする
            //(遅刻時間は通常時間帯で計算する)
            if (normalToTime != null) {
                /* 夜勤を想定とした場合の遅刻時*/
                if (normalToDate.compareDateYMDHM(normalFrDate) == UDate.LARGE) {
                    normalToDate.addDay(1);
                    if (frDate.compareDateYMDHM(normalFrDate) == UDate.LARGE) {
                        frDate.addDay(1);
                    }
                }
                if (normalToDate.compareDateYMDHM(frDate) == UDate.LARGE) {
                    toDate = normalToDate.cloneUDate();
                } else {
                    toDate = frDate.cloneUDate();
                }
            }

            if (toDate.compareDateYMDHM(normalFrDate) == UDate.SMALL) {
                UDate cvFrDate = TimecardBiz.convertForDspTime(normalFrDate, admMdl, true);
                UDate cvToDate = TimecardBiz.convertForDspTime(toDate, admMdl, false);

                //通常時間帯開始時刻 > 始業時間の場合、計算を行わない
                if (cvToDate.compareDateYMDHM(cvFrDate) != UDate.LARGE) {
                    long min = UDateUtil.diffMinute(cvFrDate, cvToDate);
                    long submin = __getRepetitionTimeZoneMinute(
                            cvFrDate, cvToDate, timeZoneList, GSConstTimecard.TIMEZONE_KBN_KYUKEI);
                    chikokuTime = BigDecimal.valueOf(min - submin);
                }

            }
        }
        if (chikokuTime != null) {
            chikokuTimes = chikokuTimes.add(chikokuTime);
        }
        return chikokuTimes;
    }

    /**
     * <br>[機  能] 早退日数を引数のBigDecimal soutaiDaysへ集計します。
     * <br>[解  説]
     * <br>[備  考]
     * @param soutaiDays 早退実績日数(集計先)
     * @param model タイムカード情報
     * @return BigDecimal 早退実績日数
     */
    private BigDecimal __getSoutaiDays(BigDecimal soutaiDays, Tcd010Model model) {

        if (model.getTcdSoukbn() == GSConstTimecard.SOU_KBN_SELECT) {
            soutaiDays = soutaiDays.add(BigDecimal.valueOf(1));
        }
        return soutaiDays;
    }

    /**
     * <br>[機  能] 早退時間を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param soutaiTimes 早退時間
     * @param model タイムカード情報
     * @param admMdl 基本設定
     * @param timeZoneList 時間帯情報
     * @param normalFrTime 通常時間帯開始時刻
     * @param normalToTime 通常時間帯終了時刻
     * @return BigDecimal 早退実績時間
     */
    private BigDecimal __getSoutaiTimes(BigDecimal soutaiTimes,
            Tcd010Model model, TcdAdmConfModel admMdl,
            ArrayList<TcdTimezoneModel> timeZoneList,
            Time normalFrTime, Time normalToTime) {

        BigDecimal soutaiTime = null;

        //早退区分
        //通常時間帯の開始時間が取得済み
        //通常時間帯の終了時間が取得済み
        //終業時刻が取得済み
        if (model.getTcdSoukbn() == GSConstTimecard.SOU_KBN_SELECT
        && normalFrTime != null
        && normalToTime != null
        && model.getTcdOuttime() != null
        && model.getTcdOuttime() != null) {

            //時間初期化用変数
            UDate initDate = new UDate();
            initDate.setZeroHhMmSs();

            /*通常時間帯開始時間*/
            UDate normalFrDate = initDate.cloneUDate();
            TimecardUtil.setTime(normalFrDate, normalFrTime);
            /*通常時間帯終了時間*/
            UDate normalToDate = initDate.cloneUDate();
            TimecardUtil.setTime(normalToDate, normalToTime);
            /*始業時刻*/
            UDate frDate = initDate.cloneUDate();
            Time frTime = model.getTcdIntime();
            if (admMdl != null) {
                frTime = TimecardBiz.adjustIntime(frTime, admMdl.getTacInterval());
            }
            TimecardUtil.setTime(frDate, frTime);
            /*終業時刻*/
            UDate toDate = initDate.cloneUDate();
            Time toTime = model.getTcdOuttime();
            if (admMdl != null) {
                toTime = TimecardBiz.adjustOuttime(toTime, admMdl.getTacInterval());
            }
            TimecardUtil.setTime(toDate, toTime);

            //時間の前後関係の修正
            if (toDate.compareDateYMDHM(frDate) == UDate.LARGE) {
                toDate.addDay(1);
            }
            boolean hiokuriFlg = false;
            if (normalToDate.compareDateYMDHM(normalFrDate) == UDate.LARGE) {
                normalToDate.addDay(1);
                hiokuriFlg = true;
            }

            //夜勤中での早退を考え時間の前後関係の修正
            if (normalFrDate.compareDateYMDHM(frDate) == UDate.SMALL
                    && normalFrDate.compareDateYMDHM(toDate) == UDate.SMALL
                    && hiokuriFlg) {
                frDate.addDay(1);
                toDate.addDay(1);
            }
            if (normalFrDate.compareDateYMDHM(toDate) == UDate.LARGE
            && normalToDate.compareDateYMDHM(toDate) == UDate.SMALL) {
                UDate cvFrDate = TimecardBiz.convertForDspTime(toDate, admMdl, true);
                UDate cvToDate = TimecardBiz.convertForDspTime(normalToDate, admMdl, false);

                //通常時間帯終了時刻 < 終業時間の場合、計算を行わない
                if (cvToDate.compareDateYMDHM(cvFrDate) != UDate.LARGE) {
                    long min = UDateUtil.diffMinute(cvFrDate, cvToDate);
                    long submin = __getRepetitionTimeZoneMinute(
                            cvFrDate, cvToDate, timeZoneList, GSConstTimecard.TIMEZONE_KBN_KYUKEI);
                    soutaiTime = BigDecimal.valueOf(min - submin);
                }
            }
        }
        if (soutaiTime != null) {
            soutaiTimes = soutaiTimes.add(soutaiTime);
        }
        return soutaiTimes;
    }

    /**
     * <br>[機  能] 時間帯情報から指定した時間帯と重複する時間数を分単位で取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 開始時間
     * @param toDate 終了時間
     * @param timeZoneList 時間帯情報
     * @param zoneKbn 取得する時間帯の区分
     * @return long 分
     */
    private long __getRepetitionTimeZoneMinute(
            UDate frDate,
            UDate toDate,
            ArrayList<TcdTimezoneModel> timeZoneList,
            int zoneKbn) {

        long ret = 0;
        TcdTimezoneModel tzMdl = null;
        for (int i = 0; i < timeZoneList.size(); i++) {
            tzMdl = timeZoneList.get(i);
            if (tzMdl.getTtzKbn() == zoneKbn) {
                // 重複時間
                Time frTz = tzMdl.getTtzFrtime();
                Time toTz = tzMdl.getTtzTotime();
                int tzFrHm = TimecardUtil.getTimeHour(frTz) * 100 + TimecardUtil.getTimeMin(frTz);
                int tzToHm = TimecardUtil.getTimeHour(toTz) * 100 + TimecardUtil.getTimeMin(toTz);
                if (tzToHm < tzFrHm) {
                    tzToHm += 2400;
                }

                int[] frHm;
                int[] toHm;
                if (toDate.getIntHour() * 100 + toDate.getIntMinute()
                  < frDate.getIntHour() * 100 + frDate.getIntMinute()) {
                    frHm = new int[2];
                    toHm = new int[2];
                    frHm[0] = frDate.getIntHour() * 100 + frDate.getIntMinute();
                    toHm[0] = MAXIMUM_TIME;
                    frHm[1] = MINIMUM_TIME;
                    toHm[1] = toDate.getIntHour() * 100 + toDate.getIntMinute();
                } else {
                    frHm = new int[1];
                    toHm = new int[1];
                    frHm[0] = frDate.getIntHour() * 100 + frDate.getIntMinute();
                    toHm[0] = toDate.getIntHour() * 100 + toDate.getIntMinute();
                }
                for (int j = 0; j < frHm.length; j++) {
                    if ((frHm[j] < tzToHm) && (toHm[j] > tzFrHm)) {
                        // 開始時間
                        int nStart = frHm[j];
                        if (nStart < tzFrHm) {
                            nStart = tzFrHm;
                        }
                        // 終了時間
                        int nStop = toHm[j];
                        if (nStop > tzToHm) {
                            nStop = tzToHm;
                        }
                        ret += changeTime(nStart, nStop);
                    }

                }
            }

        }

        return ret;
    }

    /**
     * <br>[機  能] 稼動基準日を算出する。
     * <br>[解  説]
     * <br>[備  考] 基準日では無い場合はBigDecimal(0)を返します。
     * @param fdate 開始日付
     * @param tdate 終了日付
     * @param admMdl 基本設定モデル
     * @param holMap 休日情報
     * @param timeZoneList 時間帯リスト
     * @return BigDecimal 基準日数
     */
    public BigDecimal getBaseDays(
            UDate fdate,
            UDate tdate,
            TcdAdmConfModel admMdl,
            HashMap<String, CmnHolidayModel> holMap,
            ArrayList<TcdTimezoneModel> timeZoneList) {

        UDate date = fdate.cloneUDate();
        int count = 0;

        while (date.compareDateYMD(tdate) != UDate.SMALL) {
            if (!isHoliday(date, admMdl, holMap, timeZoneList)) {
                count++;
            }
            //日付を進める
            date.addDay(1);
        }
        BigDecimal ret = new BigDecimal(count);
        return ret;
    }

    /**
     * <br>[機  能] 基準稼働時間を取得算出する。
     * <br>[解  説]
     * <br>[備  考]
     * @param baseDays 稼動日数
     * @param admMdl 基本設定
     * @param timeZoneList 時間帯設定
     * @return BigDecimal 基準稼働時間
     */
    public BigDecimal getBaseHours(
            BigDecimal baseDays,
            TcdAdmConfModel admMdl,
            ArrayList<TcdTimezoneModel> timeZoneList) {
        BigDecimal ret = BigDecimal.ZERO;
        ret.setScale(2);
        UDate frDate = new UDate();
        frDate.setZeroHhMmSs();
        frDate.setMilliSecond(0);
        UDate toDate = frDate.cloneUDate();
        long min = 0;
        //1日の稼動基準時間を取得
        if (timeZoneList == null) {
            return ret;
        }
        for (TcdTimezoneModel tzMdl : timeZoneList) {
            if (tzMdl.getTtzKbn() == GSConstTimecard.TIMEZONE_KBN_NORMAL) {

                frDate.setZeroHhMmSs();
                toDate.setZeroHhMmSs();
                Time frTime = tzMdl.getTtzFrtime();
                Time toTime = tzMdl.getTtzTotime();

                frDate.setHour(TimecardUtil.getTimeHour(frTime));
                frDate.setMinute(TimecardUtil.getTimeMin(frTime));
                toDate.setHour(TimecardUtil.getTimeHour(toTime));
                toDate.setMinute(TimecardUtil.getTimeMin(toTime));
                if (frDate.compareDateYMDHM(toDate) == UDate.SMALL) {
                    toDate.addDay(1);
                }
                min = min + UDateUtil.diffMinute(frDate, toDate);
            }
        }

        BigDecimal minBd = baseDays.multiply(BigDecimal.valueOf(min));
        ret = TimecardBiz.convertMinToHourBigDecimal(admMdl, minBd);
        return ret;
    }

    /**
     * 休日として扱うか判定します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日付
     * @param admMdl 基本設定
     * @param holMap 休日設定
     * @param timeZoneList 時間帯リスト
     * @return boolean true:休日　false:非休日
     */
    public boolean isHoliday(
            UDate date,
            TcdAdmConfModel admMdl,
            HashMap<String, CmnHolidayModel> holMap,
            ArrayList<TcdTimezoneModel> timeZoneList) {

        CmnHolidayModel holMdl = null;
        //休日設定日か判定(休日設定日は基準日にカウントしない)
        holMdl = holMap.get(date.getDateString());
        if (holMdl != null) {
            return true;
        } else {
            //休日換算する曜日か判定(休日曜日は基準日にカウントしない)
            int week = date.getWeek();
            switch (week) {
            case UDate.SUNDAY:
                if (admMdl.getTacHolSun() == GSConstTimecard.HOLIDAY_FLG) {
                    return true;
                }
                break;
            case UDate.MONDAY:
                if (admMdl.getTacHolMon() == GSConstTimecard.HOLIDAY_FLG) {
                    return true;
                }
                break;
            case UDate.TUESDAY:
                if (admMdl.getTacHolTue() == GSConstTimecard.HOLIDAY_FLG) {
                    return true;
                }
                break;
            case UDate.WEDNESDAY:
                if (admMdl.getTacHolWed() == GSConstTimecard.HOLIDAY_FLG) {
                    return true;
                }
                break;
            case UDate.THURSDAY:
                if (admMdl.getTacHolThu() == GSConstTimecard.HOLIDAY_FLG) {
                    return true;
                }
                break;
            case UDate.FRIDAY:
                if (admMdl.getTacHolFri() == GSConstTimecard.HOLIDAY_FLG) {
                    return true;
                }
                break;
            case UDate.SATURDAY:
                if (admMdl.getTacHolSat() == GSConstTimecard.HOLIDAY_FLG) {
                    return true;
                }
                break;
            default:
                return false;
            }
        }
        //休日として扱う時間帯の場合
        for (TcdTimezoneModel ttzMdl : timeZoneList) {
            if (ttzMdl.getTtiHoliday() == GSConstTimecard.HOLKBN_HOLDAY) {
                return true;
            }

        }
        return false;
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
     * <br>[機  能] 表示年月と締め日を元に表示するカレンダーのfrom～toを設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param year 表示年
     * @param month 表示月
     * @param sime 締め日
     * @param frDate カレンダー開始日付(設定されます)
     * @return toDate カレンダー終了日付(設定されます)
     */
    public UDate setTimeCardCal(int year, int month, int sime, UDate frDate) {

        UDate toDate = null;
        frDate.setYear(year);
        frDate.setMonth(month);
        frDate.setZeroHhMmSs();

        if (sime == GSConstTimecard.TIMECARD_LIMITDAY[5]) {
            frDate.setDay(1);
            toDate = frDate.cloneUDate();
            toDate.setDay(frDate.getMaxDayOfMonth());
        } else {
            frDate.setDay(sime + 1);
            toDate = frDate.cloneUDate();
            toDate.addMonth(1);
            toDate.setDay(sime);
        }

        return toDate;
    }

    /**
     * <br>[機  能]稼働時間をString型で返す
     * <br>[解  説]
     * <br>[備  考]
     * @param model タイムカード情報
     * @param admMdl 設定情報
     * @param timeZoneList 時間帯情報
     * @return String 稼働時間
     */
    protected String _getWorkHoursString(
            Tcd010Model model,
            TcdAdmConfModel admMdl,
            ArrayList<TcdTimezoneModel> timeZoneList) {

        BigDecimal workHours = BigDecimal.ZERO;
        if (model.getTcdOuttime() != null) {
            workHours = __getWorkHours(workHours, model, admMdl, timeZoneList);
        }
        return StringUtil.toCommaFromBigDecimal(TimecardBiz.convertMinToHourBigDecimal(admMdl, workHours));
    }

    /**
     * <br>[機  能]残業時間をString型で返す
     * <br>[解  説]
     * <br>[備  考]
     * @param model タイムカード情報
     * @param admMdl 設定情報
     * @param timeZoneList 時間帯情報
     * @return String 残業時間
     */
    protected String _getZangyoWorkHoursString(
            Tcd010Model model,
            TcdAdmConfModel admMdl,
            ArrayList<TcdTimezoneModel> timeZoneList) {
        BigDecimal zangyoHours = BigDecimal.ZERO;

        if (model.getTcdOuttime() != null) {
            zangyoHours = __getZangyoWorkHours(zangyoHours, model, admMdl, timeZoneList);
        }
        return StringUtil.toCommaFromBigDecimal(TimecardBiz.convertMinToHourBigDecimal(admMdl, zangyoHours));
    }

    /**
     * <br>[機  能]深夜時間をString型で返す
     * <br>[解  説]
     * <br>[備  考]
     * @param model タイムカード情報
     * @param admMdl 設定情報
     * @param timeZoneList 時間帯情報
     * @return String 深夜時間
     */
    protected String _getSinyaWorkHoursString(
            Tcd010Model model,
            TcdAdmConfModel admMdl,
            ArrayList<TcdTimezoneModel> timeZoneList) {

        BigDecimal sinyaHours = BigDecimal.ZERO;
        if (model.getTcdOuttime() != null) {
            sinyaHours = __getSinyaWorkHours(sinyaHours, model, admMdl, timeZoneList);
        }
        return StringUtil.toCommaFromBigDecimal(TimecardBiz.convertMinToHourBigDecimal(admMdl, sinyaHours));
    }

    /**
     * <br>[機  能]遅刻時間をString型で返す
     * <br>[解  説]
     * <br>[備  考]
     * @param model タイムカード情報
     * @param admMdl 設定情報
     * @param timeZoneList 時間帯情報
     * @return String 遅刻時間
     */
    protected String _getChikokuTimesString(
            Tcd010Model model, TcdAdmConfModel admMdl,
            ArrayList<TcdTimezoneModel> timeZoneList,
            Time normalFrTime, Time normalToTime) {

        BigDecimal chikokuTimes = BigDecimal.ZERO;
        chikokuTimes = __getChikokuTimes(chikokuTimes, model, admMdl, timeZoneList, normalFrTime, normalToTime);
        return StringUtil.toCommaFromBigDecimal(TimecardBiz.convertMinToHourBigDecimal(admMdl, chikokuTimes));
    }

    /**
     * <br>[機  能]早退時間をString型で返す
     * <br>[解  説]
     * <br>[備  考]
     * @param model タイムカード情報
     * @param admMdl 設定情報
     * @param timeZoneList 時間帯情報
     * @return String 早退時間
     */
    protected String _getSoutaiTimesString(
            Tcd010Model model, TcdAdmConfModel admMdl,
            ArrayList<TcdTimezoneModel> timeZoneList,
            Time normalFrTime, Time normalToTime) {

        BigDecimal soutaiTimes = BigDecimal.ZERO;
        soutaiTimes = __getSoutaiTimes(soutaiTimes, model, admMdl, timeZoneList, normalFrTime, normalToTime);
        return StringUtil.toCommaFromBigDecimal(TimecardBiz.convertMinToHourBigDecimal(admMdl, soutaiTimes));
    }


}
