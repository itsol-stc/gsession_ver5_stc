package jp.groupsession.v2.ntp.ntp010;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ユーザ別の1日分日報情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp010DayOfModel extends AbstractModel {

    /** 祝日名称 */
    private String holidayName__ = null;
    /** 日付(YYYYMMDD) */
    private String ntpDate__ = null;
    /** ユーザSID */
    private int usrSid__;
    /** 確認チェック表示区分 */
    private int ikkatuKbn__;
    /** 確認チェック区分 */
    private String ikkatuChk__;
    /** ユーザ区分 */
    private int usrKbn__;
    /** 曜日区分 */
    private int weekKbn__;
    /** 今日区分 */
    private int todayKbn__;

    /** 日報情報リスト(in SimpleNippouModel) */
    private ArrayList < SimpleNippouModel > ntpDataList__ = null;

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
     * @return ntpDataList を戻します。
     */
    public ArrayList < SimpleNippouModel > getNtpDataList() {
        return ntpDataList__;
    }
    /**
     * @param ntpDataList 設定する ntpDataList。
     */
    public void setNtpDataList(ArrayList < SimpleNippouModel > ntpDataList) {
        ntpDataList__ = ntpDataList;
    }
    /**
     * @return ntpDate を戻します。
     */
    public String getNtpDate() {
        return ntpDate__;
    }
    /**
     * @param ntpDate 設定する ntpDate。
     */
    public void setNtpDate(String ntpDate) {
        ntpDate__ = ntpDate;
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
    /**
    * @return ikkatuKbn　を戻します。
    */
    public int getIkkatuKbn() {
        return ikkatuKbn__;
    }
    /**
     * @param ikkatuKbn 設定する ikkatuKbn。
     */
    public void setIkkatuKbn(int ikkatuKbn) {
        ikkatuKbn__ = ikkatuKbn;
    }
    /**
     * @return ikkatuChk  を戻します。
     */
    public String getIkkatuChk() {
        return ikkatuChk__;
    }
    /**
     * @param ikkatuChk 設定する ikkatuChk。
     */
    public void setIkkatuChk(String ikkatuChk) {
        ikkatuChk__ = ikkatuChk;
    }

}
