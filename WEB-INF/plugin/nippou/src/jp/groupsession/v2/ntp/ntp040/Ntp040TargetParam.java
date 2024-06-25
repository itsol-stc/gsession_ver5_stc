package jp.groupsession.v2.ntp.ntp040;

/**
 * <br>[機  能] 日報 日報登録画面の日報JSONデータ定義を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp040TargetParam {
    /** year */
    private int year__;
    /** month */
    private int month__;
    /** usrSid */
    private int usrSid__;
    /** ntgSid */
    private int ntgSid__;
    /** recordStr */
    private String recordStr__;
    /** targetStr */
    private String targetStr__;
    /**
     * <p>year を取得します。
     * @return year
     */
    public int getYear() {
        return year__;
    }
    /**
     * <p>year をセットします。
     * @param year year
     */
    public void setYear(int year) {
        year__ = year;
    }
    /**
     * <p>month を取得します。
     * @return month
     */
    public int getMonth() {
        return month__;
    }
    /**
     * <p>month をセットします。
     * @param month month
     */
    public void setMonth(int month) {
        month__ = month;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>ntgSid を取得します。
     * @return ntgSid
     */
    public int getNtgSid() {
        return ntgSid__;
    }
    /**
     * <p>ntgSid をセットします。
     * @param ntgSid ntgSid
     */
    public void setNtgSid(int ntgSid) {
        ntgSid__ = ntgSid;
    }
    /**
     * <p>recordStr を取得します。
     * @return recordStr
     */
    public String getRecordStr() {
        return recordStr__;
    }
    /**
     * <p>recordStr をセットします。
     * @param recordStr recordStr
     */
    public void setRecordStr(String recordStr) {
        recordStr__ = recordStr;
    }
    /**
     * <p>targetStr を取得します。
     * @return targetStr
     */
    public String getTargetStr() {
        return targetStr__;
    }
    /**
     * <p>targetStr をセットします。
     * @param targetStr targetStr
     */
    public void setTargetStr(String targetStr) {
        targetStr__ = targetStr;
    }

}
