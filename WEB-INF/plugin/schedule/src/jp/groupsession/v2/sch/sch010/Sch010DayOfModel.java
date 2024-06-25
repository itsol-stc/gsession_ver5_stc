package jp.groupsession.v2.sch.sch010;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ユーザ別の1日分スケジュール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch010DayOfModel extends AbstractModel {

    /** 祝日名称 */
    private String holidayName__ = null;
    /** 日付(YYYYMMDD) */
    private String schDate__ = null;
    /** ユーザSID */
    private int usrSid__;
    /** ユーザ区分 */
    private int usrKbn__;
    /** 曜日区分 */
    private int weekKbn__;
    /** 今日区分 */
    private int todayKbn__;

    /** スケジュール情報リスト(in SimpleScheduleModel) */
    private ArrayList < SimpleScheduleModel > schDataList__ = null;

    /**
     * <p>todayKbn を取得します。
     * @return todayKbn
     */
    public int getTodayKbn() {
        return todayKbn__;
    }
    /**
     * <p>todayKbn をセットします。
     * @param todayKbn todayKbn
     */
    public void setTodayKbn(int todayKbn) {
        todayKbn__ = todayKbn;
    }
    /**
     * @return holidayName を戻します。
     */
    public String getHolidayName() {
        return holidayName__;
    }
    /**
     * @param holidayName 設定する holidayName。
     */
    public void setHolidayName(String holidayName) {
        holidayName__ = holidayName;
    }
    /**
     * @return schDataList を戻します。
     */
    public ArrayList < SimpleScheduleModel > getSchDataList() {
        return schDataList__;
    }
    /**
     * @param schDataList 設定する schDataList。
     */
    public void setSchDataList(ArrayList < SimpleScheduleModel > schDataList) {
        schDataList__ = schDataList;
    }
    /**
     * @return schDate を戻します。
     */
    public String getSchDate() {
        return schDate__;
    }
    /**
     * @param schDate 設定する schDate。
     */
    public void setSchDate(String schDate) {
        schDate__ = schDate;
    }
    /**
     * @return usrKbn を戻します。
     */
    public int getUsrKbn() {
        return usrKbn__;
    }
    /**
     * @param usrKbn 設定する usrKbn。
     */
    public void setUsrKbn(int usrKbn) {
        usrKbn__ = usrKbn;
    }
    /**
     * @return usrSid を戻します。
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * @param usrSid 設定する usrSid。
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * @return weekKbn を戻します。
     */
    public int getWeekKbn() {
        return weekKbn__;
    }
    /**
     * @param weekKbn 設定する weekKbn。
     */
    public void setWeekKbn(int weekKbn) {
        weekKbn__ = weekKbn;
    }

}
