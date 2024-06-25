package jp.groupsession.v2.ntp;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] １日分のカレンダー情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SimpleCalenderModel extends AbstractModel {

    /** 日付(YYYYMMDD)*/
    private String dspDate__ = null;
    /** 日付文字列(DD日(Ｎ))*/
    private String dspDayString__ = null;
    /** 休日区分 */
    private String holidayKbn__ = null;
    /** 曜日区分 */
    private String weekKbn__ = null;
    /** 今日区分 */
    private String todayKbn__ = null;
    /** 六曜区分 */
    private int rokuyoKbn__ = 0;

    /**
     * <p>todayKbn を取得します。
     * @return todayKbn
     */
    public String getTodayKbn() {
        return todayKbn__;
    }
    /**
     * <p>todayKbn をセットします。
     * @param todayKbn todayKbn
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
     * <p>rokuyoKbn を取得します。
     * @return rokuyoKbn
     * @see jp.groupsession.v2.ntp.SimpleCalenderModel#rokuyoKbn__
     */
    public int getRokuyoKbn() {
        return rokuyoKbn__;
    }
    /**
     * <p>rokuyoKbn をセットします。
     * @param rokuyoKbn rokuyoKbn
     * @see jp.groupsession.v2.ntp.SimpleCalenderModel#rokuyoKbn__
     */
    public void setRokuyoKbn(int rokuyoKbn) {
        rokuyoKbn__ = rokuyoKbn;
    }
}
