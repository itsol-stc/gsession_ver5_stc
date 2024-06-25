package jp.groupsession.v2.ntp.ntp020;

import jp.groupsession.v2.ntp.ntp010.Ntp010DayOfModel;

/**
 * <br>[機  能] ユーザ別の1日分日報情報と当月区分、休日区分を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp020DayOfModel extends Ntp010DayOfModel {

    /** 表示用日付 */
    private String dspDay__;
    /** 休日区分 */
    private int holidayKbn__;
    /** 当月区分 */
    private int thisMonthKbn__;
    /** 表示用六曜 */
    private String dspRokuyo__;

    /**
     * @return dspDay を戻します。
     */
    public String getDspDay() {
        return dspDay__;
    }
    /**
     * @param dspDay 設定する dspDay。
     */
    public void setDspDay(String dspDay) {
        dspDay__ = dspDay;
    }
    /**
     * @return holidayKbn を戻します。
     */
    public int getHolidayKbn() {
        return holidayKbn__;
    }
    /**
     * @param holidayKbn 設定する holidayKbn。
     */
    public void setHolidayKbn(int holidayKbn) {
        holidayKbn__ = holidayKbn;
    }
    /**
     * @return thisMonthKbn を戻します。
     */
    public int getThisMonthKbn() {
        return thisMonthKbn__;
    }
    /**
     * @param thisMonthKbn 設定する thisMonthKbn。
     */
    public void setThisMonthKbn(int thisMonthKbn) {
        thisMonthKbn__ = thisMonthKbn;
    }
    /**
     * <p>dspRokuyo を取得します。
     * @return dspRokuyo
     * @see jp.groupsession.v2.ntp.ntp020.Ntp020DayOfModel#dspRokuyo__
     */
    public String getDspRokuyo() {
        return dspRokuyo__;
    }
    /**
     * <p>dspRokuyo をセットします。
     * @param dspRokuyo dspRokuyo
     * @see jp.groupsession.v2.ntp.ntp020.Ntp020DayOfModel#dspRokuyo__
     */
    public void setDspRokuyo(String dspRokuyo) {
        dspRokuyo__ = dspRokuyo;
    }
}
