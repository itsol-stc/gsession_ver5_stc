package jp.groupsession.v2.tcd.tcd050kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.dao.base.TcdAdmConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.man.man020.Man020HolidayModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.dao.TcdTimezoneInfoDao;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;


/**
 * <br>[機  能] タイムカード 管理者設定 基本設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd050knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd050knBiz.class);

    /**
     * <br>[機  能] 初期表示画面情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Tcd050knParamModel paramMdl, RequestModel reqMdl, Connection con)
    throws SQLException {
        log__.debug("setInitData");

        //曜日休日
        setHolidayWeek(paramMdl);
        //休日
        setHolidays(paramMdl, con, reqMdl);
        //時間帯
        __setTimezone(paramMdl, con);
    }

    /**
     * <br>[機  能] 定休曜日を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setHolidayWeek(Tcd050knParamModel paramMdl) {
        paramMdl.setWeekSun("0");
        paramMdl.setWeekMon("0");
        paramMdl.setWeekTue("0");
        paramMdl.setWeekWed("0");
        paramMdl.setWeekThu("0");
        paramMdl.setWeekFri("0");
        paramMdl.setWeekSat("0");
        String[] weeks = paramMdl.getTcd050SelectWeek();
        if (weeks != null) {
            for (int i = 0; i < weeks.length; i++) {
                int week = Integer.parseInt(weeks[i]);
                switch (week) {
                case UDate.SUNDAY:
                    paramMdl.setWeekSun("1");
                    break;
                case UDate.MONDAY:
                    paramMdl.setWeekMon("1");
                    break;
                case UDate.TUESDAY:
                    paramMdl.setWeekTue("1");
                    break;
                case UDate.WEDNESDAY:
                    paramMdl.setWeekWed("1");
                    break;
                case UDate.THURSDAY:
                    paramMdl.setWeekThu("1");
                    break;
                case UDate.FRIDAY:
                    paramMdl.setWeekFri("1");
                    break;
                case UDate.SATURDAY:
                    paramMdl.setWeekSat("1");
                    break;
                default:
                    break;
                }
            }
        }
    }

    /**
     * <br>[機  能] 休日情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setHolidays(Tcd050knParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {

        UDate sysDate = new UDate();
        String strDspYear = NullDefault.getString(
                paramMdl.getTcd050DspHolidayYear(), sysDate.getStrYear());
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        List<CmnHolidayModel> holList = holDao.getHoliDayList(Integer.parseInt(strDspYear));

        ArrayList<Man020HolidayModel> man020List = new ArrayList<Man020HolidayModel>();
        HashMap<String, String> dayMap = new HashMap<String, String>();

        if (paramMdl.getTcd050SelectHoliDay() != null) {
            String[] days = paramMdl.getTcd050SelectHoliDay();
            for (int i = 0; i < days.length; i++) {
                dayMap.put(days[i], days[i]);
            }
        }
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msgMonth = gsMsg.getMessage("cmn.month");
        String msgDay = gsMsg.getMessage("cmn.day");

        for (CmnHolidayModel holMdl : holList) {
            Man020HolidayModel man020Hol = new Man020HolidayModel();
            UDate holDate = holMdl.getHolDate();
            man020Hol.setHolName(holMdl.getHolName());
            man020Hol.setStrDate(holDate.getDateString());
            StringBuilder viewDate = new StringBuilder();
            viewDate.append(String.valueOf(holDate.getMonth()));
            viewDate.append(msgMonth);
            viewDate.append(String.valueOf(holDate.getIntDay()));
            viewDate.append(msgDay);
            man020Hol.setViewDate(viewDate.toString());
            if (dayMap.containsKey(holDate.getDateString())) {
                man020List.add(man020Hol);
            }
        }
        paramMdl.setTcd050HolidayInfoList(man020List);
    }

    /**
     * <br>[機  能] 情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setTimezone(Tcd050knParamModel paramMdl, Connection con) throws SQLException {
        //時間帯情報名を設定
        TcdTimezoneInfoDao dao = new TcdTimezoneInfoDao(con);
        TcdTimezoneInfoModel mdl= dao.select(paramMdl.getTcd050DefTimezone());
        if (mdl != null) {
            paramMdl.setTcd050knDefTimezoneName(mdl.getTtiName());
        }

    }

    /**
     * <br>[機  能] タイムカード基本設定を更新します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return int 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int updateTcdAdmConf(Tcd050knParamModel paramMdl, int usrSid, Connection con)
    throws SQLException {
        int ret = 0;
        TcdAdmConfModel admMdl = new TcdAdmConfModel();
        //更新内容を設定
        admMdl.setTacInterval(
                NullDefault.getInt(paramMdl.getTcd050BetweenSlt(), GSConstTimecard.DF_INTERVAL));
        admMdl.setTacKansan(
                NullDefault.getInt(paramMdl.getTcd050SinsuSlt(), GSConstTimecard.DF_KANSAN));
        admMdl.setTacSimebi(
                NullDefault.getInt(paramMdl.getTcd050LimitDaySlt(), GSConstTimecard.DF_SIMEBI));
        admMdl.clearWeeks();
        admMdl.setTacDefTimezone(paramMdl.getTcd050DefTimezone());
        if (paramMdl.getTcd050SelectWeek() != null) {
            String[] weeks = paramMdl.getTcd050SelectWeek();
            for (int i = 0; i < weeks.length; i++) {
                int week = Integer.parseInt(weeks[i]);
                switch (week) {
                case UDate.SUNDAY:
                    admMdl.setTacHolSun(GSConstTimecard.HOLIDAY_FLG);
                    break;
                case UDate.MONDAY:
                    admMdl.setTacHolMon(GSConstTimecard.HOLIDAY_FLG);
                    break;
                case UDate.TUESDAY:
                    admMdl.setTacHolTue(GSConstTimecard.HOLIDAY_FLG);
                    break;
                case UDate.WEDNESDAY:
                    admMdl.setTacHolWed(GSConstTimecard.HOLIDAY_FLG);
                    break;
                case UDate.THURSDAY:
                    admMdl.setTacHolThu(GSConstTimecard.HOLIDAY_FLG);
                    break;
                case UDate.FRIDAY:
                    admMdl.setTacHolFri(GSConstTimecard.HOLIDAY_FLG);
                    break;
                case UDate.SATURDAY:
                    admMdl.setTacHolSat(GSConstTimecard.HOLIDAY_FLG);
                    break;
                default:
                    break;
                }
            }
        }
        admMdl.setTacEuid(usrSid);
        admMdl.setTacEdate(new UDate());

        TcdAdmConfDao dao = new TcdAdmConfDao(con);
        int baseCnt = dao.updateBase(admMdl);
        //休日設定テーブル更新
        CmnHolidayDao hDao = new CmnHolidayDao(con);
        ArrayList<UDate> targetDate = null;
        hDao.updateTcdHoliday(
                Integer.parseInt(paramMdl.getTcd050DspHolidayYear()),
                GSConstTimecard.HOL_NOTHOLIDAY,
                targetDate,
                usrSid);
        int holCnt = 0;
        if (__isSelectHoliday(paramMdl.getTcd050SelectHoliDay())) {
            String[] hols = paramMdl.getTcd050SelectHoliDay();
            UDate holDate = null;
            targetDate = new ArrayList<UDate>();
            for (int i = 0; i < hols.length; i++) {
                String holiday = hols[i];
                if (!StringUtil.isNullZeroStringSpace(holiday)) {
                    holDate = new UDate();
                    holDate.setDate(holiday);
                    holDate.setZeroHhMmSs();
                    targetDate.add(holDate);
                }
            }
            //休日扱いをする日付を更新
            holCnt = hDao.updateTcdHoliday(
                    Integer.parseInt(paramMdl.getTcd050DspHolidayYear()),
                    GSConstTimecard.HOL_HOLIDAY,
                    targetDate,
                    usrSid);
        }
        ret = baseCnt + holCnt;
        return ret;
    }

    /**
     * <br>[機  能] 休日設定が選択されているか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param hols 休日選択
     * @return boolean true:選択されている false:未選択
     */
    private boolean __isSelectHoliday(String[] hols) {
        for (int i = 0; i < hols.length; i++) {
            String holiday = hols[i];
            if (!StringUtil.isNullZeroStringSpace(holiday)) {
                return true;
            }
        }
        return false;
    }
}
