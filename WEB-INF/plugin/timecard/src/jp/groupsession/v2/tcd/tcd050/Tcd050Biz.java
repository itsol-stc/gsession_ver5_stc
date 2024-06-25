package jp.groupsession.v2.tcd.tcd050;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.man.man020.Man020HolidayModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdTimezoneInfoDao;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;


/**
 * <br>[機  能] タイムカード 管理者設定 基本設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd050Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd050Biz.class);

    /** リクエスト */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public Tcd050Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Tcd050ParamModel paramMdl, Connection con) throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //基本設定値を取得
        TimecardBiz tcBiz = new TimecardBiz(reqMdl__);
        TcdAdmConfModel acMdl = tcBiz.getTcdAdmConfModel(sessionUsrSid, con);
        //入力単位コンボ生成
        setInputValueCombo(paramMdl, acMdl);
        //締日コンボ
        setLimitDayCombo(paramMdl, acMdl);
        //休日曜日
        setHolidayWeek(paramMdl, acMdl);
        //休日
        setHolidays(paramMdl, con);
        //時間帯
        __setTimezone(paramMdl, con, acMdl);

        paramMdl.setTcd050initFlg(1);
    }

    /**
     * <br>[機  能] 時間帯情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setTimezone(Tcd050ParamModel paramMdl, Connection con, TcdAdmConfModel acMdl)
            throws SQLException {
        //時間帯リストを取得
        TcdTimezoneInfoDao dao = new TcdTimezoneInfoDao(con);
        List<TcdTimezoneInfoModel> tzList = dao.selectCanUse();
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (TcdTimezoneInfoModel mdl : tzList) {
            labelList.add(new LabelValueBean(mdl.getTtiName(), String.valueOf(mdl.getTtiSid())));
        }
        paramMdl.setTcd050TimezoneList(labelList);
        if (acMdl != null) {
            paramMdl.setTcd050DefTimezone(acMdl.getTacDefTimezone());
        }
    }

    /**
     * <br>[機  能] YYYY年MM月DD,DD,DD,DD日形式の文字列を生成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param year 年
     * @param month 月
     * @param days 日配列
     * @param sep 区切り文字
     * @return String
     */
    public String getMultiDaysString(int year, int month, String[] days, String sep) {
        String ret = "";
        UDate udate = new UDate();
        udate.setYear(year);
        udate.setMonth(month);
        StringBuilder buf = new StringBuilder();
        buf.append(UDateUtil.getYymJ(udate, reqMdl__));
        int day = 0;
        for (int i = 0; i < days.length; i++) {
            day = Integer.parseInt(days[i]);
            buf.append(StringUtil.toDecFormat(String.valueOf(day), "00"));
            buf.append(sep);
        }
        buf.delete(buf.lastIndexOf(sep, buf.length()), buf.length());
        GsMessage gsMsg = new GsMessage();
        String msgDay = gsMsg.getMessage("cmn.day");

        buf.append(msgDay);
        ret = buf.toString();
        return ret;
    }

    /**
     * <br>[機  能] リストボックス用時間ラベルの生成
     * <br>[解  説]
     * <br>[備  考]
     * @return 時間ラベル
     */
    public ArrayList < LabelValueBean > getHourLabel() {
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("", "-1"));
        GsMessage gsMsg = new GsMessage();
        String msgHour = gsMsg.getMessage("cmn.hour");

        for (int hour = 0; hour < 24; hour++) {
            labelList.add(new LabelValueBean(hour + msgHour, String.valueOf(hour)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] リストボックス用分ラベルの生成
     * <br>[解  説]
     * <br>[備  考]
     * @return 分ラベル
     */
    public static ArrayList < LabelValueBean > getMinuteLabel() {
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("", "-1"));

        for (int hm = 0; hm < 6; hm++) {
            labelList.add(new LabelValueBean(String.valueOf(hm), String
                    .valueOf(hm)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 入力単位コンボをそれぞれ生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param acMdl 管理者設定
     */
    public void setInputValueCombo(Tcd050ParamModel paramMdl, TcdAdmConfModel acMdl) {

        ArrayList<LabelValueBean> betweenLabel = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msgMin = gsMsg.getMessage("cmn.minute");

        String[] betweenStr = {"1" + msgMin,
                               "10" + msgMin,
                               "15" + msgMin,
                               "30" + msgMin,
                               "60" + msgMin};
        String shinsu10 = gsMsg.getMessage("tcd.165");
        String shinsu60 = gsMsg.getMessage("tcd.164");

        String[] sinsuStr = {shinsu10, shinsu60};

        for (int i = 0; i < GSConstTimecard.TIMECARD_BETWEEN.length; i++) {
            String label = betweenStr[i];
            String value = Integer.toString(GSConstTimecard.TIMECARD_BETWEEN[i]);
            log__.debug("label==>" + label);
            log__.debug("value==>" + value);
            betweenLabel.add(new LabelValueBean(label, value));
        }
        //間隔リスト
        paramMdl.setTcd050BetweenLabel(betweenLabel);
        paramMdl.setTcd050BetweenSlt(
                NullDefault.getString(
                        paramMdl.getTcd050BetweenSlt(),
                        String.valueOf(acMdl.getTacInterval())));

        ArrayList<LabelValueBean> sinsuLabel = new ArrayList<LabelValueBean>();
        for (int i = 0; i < GSConstTimecard.TIMECARD_SINSU.length; i++) {
            String label = sinsuStr[i];
            String value = Integer.toString(GSConstTimecard.TIMECARD_SINSU[i]);
            log__.debug("label==>" + label);
            log__.debug("value==>" + value);
            sinsuLabel.add(new LabelValueBean(label, value));
        }
        //進数リスト
        paramMdl.setTcd050SinsuLabel(sinsuLabel);
        paramMdl.setTcd050SinsuSlt(
                NullDefault.getString(
                        paramMdl.getTcd050SinsuSlt(),
                        String.valueOf(acMdl.getTacKansan())));
    }

    /**
     * <br>[機  能] 締日コンボを生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param acMdl 管理者設定
     */
    public void setLimitDayCombo(Tcd050ParamModel paramMdl, TcdAdmConfModel acMdl) {
        ArrayList<LabelValueBean> limitLabel = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msgDay = gsMsg.getMessage("cmn.day");
        String matu = gsMsg.getMessage("tcd.tcd050kn.01");

        String[] limitDay = {"5" + msgDay,
                             "10" + msgDay,
                             "15" + msgDay,
                             "20" + msgDay,
                             "25" + msgDay,
                             matu};

        for (int i = 0; i < GSConstTimecard.TIMECARD_LIMITDAY.length; i++) {
            String label = limitDay[i];
            String value = Integer.toString(GSConstTimecard.TIMECARD_LIMITDAY[i]);
            log__.debug("label==>" + label);
            log__.debug("value==>" + value);
            limitLabel.add(new LabelValueBean(label, value));
        }
        //進数リスト
        paramMdl.setTcd050LimitDayLabel(limitLabel);
        paramMdl.setTcd050LimitDaySlt(
                NullDefault.getString(
                        paramMdl.getTcd050LimitDaySlt(),
                        String.valueOf(acMdl.getTacSimebi())));
    }

    /**
     * <br>[機  能] 定休曜日を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param acMdl 管理者設定モデル
     */
    public void setHolidayWeek(Tcd050ParamModel paramMdl, TcdAdmConfModel acMdl) {
        String[] weeks = paramMdl.getTcd050SelectWeek();
        StringBuilder buf = new StringBuilder();
        if (paramMdl.getTcd050initFlg() != 1 && weeks == null) {
            if (acMdl.getTacHolSun() == GSConstTimecard.HOL_HOLIDAY) {
                buf.append(String.valueOf(UDate.SUNDAY));
            }
            if (acMdl.getTacHolMon() == GSConstTimecard.HOL_HOLIDAY) {
                if (buf.length() > 0) {
                    buf.append(",");
                }
                buf.append(String.valueOf(UDate.MONDAY));
            }
            if (acMdl.getTacHolTue() == GSConstTimecard.HOL_HOLIDAY) {
                if (buf.length() > 0) {
                    buf.append(",");
                }
                buf.append(String.valueOf(UDate.TUESDAY));
            }
            if (acMdl.getTacHolWed() == GSConstTimecard.HOL_HOLIDAY) {
                if (buf.length() > 0) {
                    buf.append(",");
                }
                buf.append(String.valueOf(UDate.WEDNESDAY));
            }
            if (acMdl.getTacHolThu() == GSConstTimecard.HOL_HOLIDAY) {
                if (buf.length() > 0) {
                    buf.append(",");
                }
                buf.append(String.valueOf(UDate.THURSDAY));
            }
            if (acMdl.getTacHolFri() == GSConstTimecard.HOL_HOLIDAY) {
                if (buf.length() > 0) {
                    buf.append(",");
                }
                buf.append(String.valueOf(UDate.FRIDAY));
            }
            if (acMdl.getTacHolSat() == GSConstTimecard.HOL_HOLIDAY) {
                if (buf.length() > 0) {
                    buf.append(",");
                }
                buf.append(String.valueOf(UDate.SATURDAY));
            }
            weeks = buf.toString().split(",");
            paramMdl.setTcd050SelectWeek(weeks);
        }
    }

    /**
     * <br>[機  能] 休日情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setHolidays(Tcd050ParamModel paramMdl, Connection con) throws SQLException {

        UDate sysDate = new UDate();
        String strDspYear = NullDefault.getString(
                paramMdl.getTcd050DspHolidayYear(), sysDate.getStrYear());
        //表示年
        paramMdl.setTcd050DspHolidayYear(strDspYear);

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msgMonth = gsMsg.getMessage("cmn.month");
        String msgDay = gsMsg.getMessage("cmn.day");

        CmnHolidayDao holDao = new CmnHolidayDao(con);
        List<CmnHolidayModel> holList = holDao.getHoliDayList(Integer.parseInt(strDspYear));

        ArrayList<Man020HolidayModel> man020List = new ArrayList<Man020HolidayModel>();
        StringBuilder buf = new StringBuilder();
        String[] days = null;
        for (CmnHolidayModel holMdl : holList) {
            Man020HolidayModel man020Hol = new Man020HolidayModel();
            UDate holDate = holMdl.getHolDate();
            man020Hol.setHolName(holMdl.getHolName());
            man020Hol.setStrDate(holDate.getDateString());
            if (holMdl.getHolTcd() == GSConstTimecard.HOLIDAY_FLG) {
                buf.append(holDate.getDateString());
                buf.append(",");
            }
            StringBuilder viewDate = new StringBuilder();
            viewDate.append(String.valueOf(holDate.getMonth()));
            viewDate.append(msgMonth);
            viewDate.append(String.valueOf(holDate.getIntDay()));
            viewDate.append(msgDay);
            man020Hol.setViewDate(viewDate.toString());
            man020List.add(man020Hol);
        }


        if (paramMdl.getTcd050SelectHoliDay() == null) {
            days = buf.toString().split(",");
            paramMdl.setTcd050SelectHoliDay(days);
        }
        paramMdl.setTcd050HolidayInfoList(man020List);
    }
}
