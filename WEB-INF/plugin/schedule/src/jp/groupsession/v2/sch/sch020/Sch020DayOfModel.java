package jp.groupsession.v2.sch.sch020;

import jp.groupsession.v2.sch.sch010.Sch010DayOfModel;

/**
 * <br>[機  能] ユーザ別の1日分スケジュール情報、および当月区分、休日区分を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch020DayOfModel extends Sch010DayOfModel {

    /** 表示用日付 */
    private String dspDay__;
    /** 休日区分 */
    private int holidayKbn__;
    /** 当月区分 */
    private int thisMonthKbn__;
    /** 六曜 */
    private String schRokuyou__ = null;

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
     * <p>schRokuyou を取得します。
     * @return schRokuyou
     * @see jp.groupsession.v2.sch.sch020.Sch020DayOfModel#schRokuyou__
     */
    public String getSchRokuyou() {
        return schRokuyou__;
    }
    /**
     * <p>schRokuyou をセットします。
     * @param schRokuyou schRokuyou
     * @see jp.groupsession.v2.sch.sch020.Sch020DayOfModel#schRokuyou__
     */
    public void setSchRokuyou(String schRokuyou) {
        schRokuyou__ = schRokuyou;
    }
}
