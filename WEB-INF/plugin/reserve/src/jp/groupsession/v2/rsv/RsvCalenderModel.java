package jp.groupsession.v2.rsv;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 1日分のカレンダー情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvCalenderModel extends AbstractModel {

    /** 日付(YYYYMMDD)*/
    private String dspDate__ = null;
    /** 日付文字列(DD日(Ｎ))*/
    private String dspDayString__ = null;
    /** 休日区分 */
    private String holidayKbn__ = null;
    /** 曜日区分 */
    private String weekKbn__ = null;
    /** 当日区分 */
    private String todayKbn__ = null;
    /** 六曜 */
    private String rokuyou__ = null;

    /**
     * <p>todayKbn__ を取得します。
     * @return todayKbn
     */
    public String getTodayKbn() {
        return todayKbn__;
    }
    /**
     * <p>todayKbn__ をセットします。
     * @param todayKbn todayKbn__
     */
    public void setTodayKbn(String todayKbn) {
        todayKbn__ = todayKbn;
    }
    /**
     * @return dspDate を戻します。
     */
    public String getDspDate() {
        return dspDate__;
    }
    /**
     * @param dspDate 設定する dspDate。
     */
    public void setDspDate(String dspDate) {
        dspDate__ = dspDate;
    }
    /**
     * @return dspDayString を戻します。
     */
    public String getDspDayString() {
        return dspDayString__;
    }
    /**
     * @param dspDayString 設定する dspDayString。
     */
    public void setDspDayString(String dspDayString) {
        dspDayString__ = dspDayString;
    }
    /**
     * @return holidayKbn を戻します。
     */
    public String getHolidayKbn() {
        return holidayKbn__;
    }
    /**
     * @param holidayKbn 設定する holidayKbn。
     */
    public void setHolidayKbn(String holidayKbn) {
        holidayKbn__ = holidayKbn;
    }
    /**
     * @return weekKbn を戻します。
     */
    public String getWeekKbn() {
        return weekKbn__;
    }
    /**
     * @param weekKbn 設定する weekKbn。
     */
    public void setWeekKbn(String weekKbn) {
        weekKbn__ = weekKbn;
    }
    /**
     * <p>rokuyou を取得します。
     * @return rokuyou
     * @see jp.groupsession.v2.rsv.RsvCalenderModel#rokuyou__
     */
    public String getRokuyou() {
        return rokuyou__;
    }
    /**
     * <p>rokuyou をセットします。
     * @param rokuyou rokuyou
     * @see jp.groupsession.v2.rsv.RsvCalenderModel#rokuyou__
     */
    public void setRokuyou(String rokuyou) {
        rokuyou__ = rokuyou;
    }
}