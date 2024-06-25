package jp.groupsession.v2.sch.sch200;

/**
 * <br>[機  能] スケジュール 個人週間JSONデータ定義を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch200TitleFormat {
    /** month */
    private String month__ = null;
    /** week */
    private String week__ = null;
    /** day */
    private String day__ = null;
    /**
     * <p>day を取得します。
     * @return day
     */
    public String getDay() {
        return day__;
    }
    /**
     * <p>day をセットします。
     * @param day day
     */
    public void setDay(String day) {
        day__ = day;
    }
    /**
     * <p>month を取得します。
     * @return month
     */
    public String getMonth() {
        return month__;
    }
    /**
     * <p>month をセットします。
     * @param month month
     */
    public void setMonth(String month) {
        month__ = month;
    }
    /**
     * <p>week を取得します。
     * @return week
     */
    public String getWeek() {
        return week__;
    }
    /**
     * <p>week をセットします。
     * @param week week
     */
    public void setWeek(String week) {
        week__ = week;
    }
}
