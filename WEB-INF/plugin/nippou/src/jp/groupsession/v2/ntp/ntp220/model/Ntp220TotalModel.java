package jp.groupsession.v2.ntp.ntp220.model;

/**
 * <br>[機  能] 集計情報を格納するモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp220TotalModel {
    /** 見積もり金額合計 */
    private int sumMitumoriPrice__;
    /** 受注金額合計  */
    private int sumJutyuPrice__;

    /** 受注案件数合計 */
    private int sumJutyuAnken__;
    /** 失注案件数合計 */
    private int sumSityuAnken__;
    /** 商談案件数合計 */
    private int sumShodanAnken__;

    /** SID */
    private int sid__;
    /** 名 */
    private String name__;
    /** 稼働日数 */
    private Long totalKadouDays__;
    /** 稼働時間合計 */
    private float sumKadouTime__;
    /** 全稼働時間合計 */
    private float totalKadouTime__;
    /** 稼働時間合計 分 */
    private long sumKadouTimeMins__;

    /** 日付文字列 */
    private String dateStr__;

    /**
     * <p>sumMitumoriPrice を取得します。
     * @return sumMitumoriPrice
     */
    public int getSumMitumoriPrice() {
        return sumMitumoriPrice__;
    }

    /**
     * <p>sumMitumoriPrice をセットします。
     * @param sumMitumoriPrice sumMitumoriPrice
     */
    public void setSumMitumoriPrice(int sumMitumoriPrice) {
        sumMitumoriPrice__ = sumMitumoriPrice;
    }

    /**
     * <p>sumJutyuPrice を取得します。
     * @return sumJutyuPrice
     */
    public int getSumJutyuPrice() {
        return sumJutyuPrice__;
    }

    /**
     * <p>sumJutyuPrice をセットします。
     * @param sumJutyuPrice sumJutyuPrice
     */
    public void setSumJutyuPrice(int sumJutyuPrice) {
        sumJutyuPrice__ = sumJutyuPrice;
    }

    /**
     * <p>sumJutyuAnken を取得します。
     * @return sumJutyuAnken
     */
    public int getSumJutyuAnken() {
        return sumJutyuAnken__;
    }

    /**
     * <p>sumJutyuAnken をセットします。
     * @param sumJutyuAnken sumJutyuAnken
     */
    public void setSumJutyuAnken(int sumJutyuAnken) {
        sumJutyuAnken__ = sumJutyuAnken;
    }

    /**
     * <p>sumSityuAnken を取得します。
     * @return sumSityuAnken
     */
    public int getSumSityuAnken() {
        return sumSityuAnken__;
    }

    /**
     * <p>sumSityuAnken をセットします。
     * @param sumSityuAnken sumSityuAnken
     */
    public void setSumSityuAnken(int sumSityuAnken) {
        sumSityuAnken__ = sumSityuAnken;
    }

    /**
     * <p>sumShodanAnken を取得します。
     * @return sumShodanAnken
     */
    public int getSumShodanAnken() {
        return sumShodanAnken__;
    }

    /**
     * <p>sumShodanAnken をセットします。
     * @param sumShodanAnken sumShodanAnken
     */
    public void setSumShodanAnken(int sumShodanAnken) {
        sumShodanAnken__ = sumShodanAnken;
    }

    /**
     * <p>dateStr を取得します。
     * @return dateStr
     */
    public String getDateStr() {
        return dateStr__;
    }

    /**
     * <p>dateStr をセットします。
     * @param dateStr dateStr
     */
    public void setDateStr(String dateStr) {
        dateStr__ = dateStr;
    }

    /**
     * <p>name を取得します。
     * @return name
     */
    public String getName() {
        return name__;
    }

    /**
     * <p>name をセットします。
     * @param name name
     */
    public void setName(String name) {
        name__ = name;
    }

    /**
     * <p>sid を取得します。
     * @return sid
     */
    public int getSid() {
        return sid__;
    }

    /**
     * <p>sid をセットします。
     * @param sid sid
     */
    public void setSid(int sid) {
        sid__ = sid;
    }

    /**
     * <p>totalKadouDays を取得します。
     * @return totalKadouDays
     */
    public Long getTotalKadouDays() {
        return totalKadouDays__;
    }

    /**
     * <p>totalKadouDays をセットします。
     * @param totalKadouDays totalKadouDays
     */
    public void setTotalKadouDays(Long totalKadouDays) {
        totalKadouDays__ = totalKadouDays;
    }

    /**
     * <p>sumKadouTime を取得します。
     * @return sumKadouTime
     */
    public float getSumKadouTime() {
        return sumKadouTime__;
    }

    /**
     * <p>sumKadouTime をセットします。
     * @param sumKadouTime sumKadouTime
     */
    public void setSumKadouTime(float sumKadouTime) {
        sumKadouTime__ = sumKadouTime;
    }

    /**
     * <p>totalKadouTime を取得します。
     * @return totalKadouTime
     */
    public float getTotalKadouTime() {
        return totalKadouTime__;
    }

    /**
     * <p>totalKadouTime をセットします。
     * @param totalKadouTime totalKadouTime
     */
    public void setTotalKadouTime(float totalKadouTime) {
        totalKadouTime__ = totalKadouTime;
    }

    /**
     * <p>sumKadouTimeMins を取得します。
     * @return sumKadouTimeMins
     */
    public long getSumKadouTimeMins() {
        return sumKadouTimeMins__;
    }

    /**
     * <p>sumKadouTimeMins をセットします。
     * @param sumKadouTimeMins sumKadouTimeMins
     */
    public void setSumKadouTimeMins(long sumKadouTimeMins) {
        sumKadouTimeMins__ = sumKadouTimeMins;
    }

}
