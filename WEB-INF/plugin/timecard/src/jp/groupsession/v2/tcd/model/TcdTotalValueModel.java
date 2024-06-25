package jp.groupsession.v2.tcd.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] タイムカード情報の集計値を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdTotalValueModel extends AbstractModel {

    /** 集計月 */
    private String kadoMonth__ = null;
    /** 集計開始日付 */
    private UDate startDate__ = null;
    /** 集計終了日付 */
    private UDate endDate__ = null;
    //集計用
    /** 稼動基準日数 */
    private BigDecimal kadoBaseDays__ = null;
    /** 稼動実績日数 */
    private BigDecimal kadoDays__ = null;
    /** 稼動基準時間数 */
    private BigDecimal kadoBaseHours__ = null;
    /** 稼動実績時間数 */
    private BigDecimal kadoHours__ = null;
    /** 残業日数 */
    private BigDecimal zangyoDays__ = null;
    /** 残業時間数 */
    private BigDecimal zangyoHours__ = null;
    /** 深夜日数 */
    private BigDecimal sinyaDays__ = null;
    /** 深夜時間数 */
    private BigDecimal sinyaHours__ = null;
    /** 休日出勤日数 */
    private BigDecimal kyusyutuDays__ = null;
    /** 休日出勤時間数 */
    private BigDecimal kyusyutuHours__ = null;
    /** 遅刻日数 */
    private BigDecimal chikokuDays__ = null;
    /** 遅刻時間 */
    private BigDecimal chikokuTimes__ = null;
    /** 早退日数 */
    private BigDecimal soutaiDays__ = null;
    /** 早退時間 */
    private BigDecimal soutaiTimes__ = null;
    /** 有給休暇日数 */
    private BigDecimal yuukyuDays__ = null;
    /** 欠勤日数 */
    private BigDecimal kekkinDays__ = null;
    /** 休日区分SID */
    private int thiSid__ = 0;

    /** 休日区分SIDと休日日数のMapping */
    private Map<Integer, BigDecimal> holDaysMap__ = new HashMap<Integer, BigDecimal>();
    //画面表示用
    /** 稼動基準日数 */
    private String kadoBaseDaysStr__ = null;
    /** 稼動実績日数 */
    private String kadoDaysStr__ = null;
    /** 稼動基準時間数 */
    private String kadoBaseHoursStr__ = null;
    /** 稼動実績時間数 */
    private String kadoHoursStr__ = null;
    /** 残業日数 */
    private String zangyoDaysStr__ = null;
    /** 残業時間数 */
    private String zangyoHoursStr__ = null;
    /** 深夜日数 */
    private String sinyaDaysStr__ = null;
    /** 深夜時間数 */
    private String sinyaHoursStr__ = null;
    /** 休日出勤日数 */
    private String kyusyutuDaysStr__ = null;
    /** 休日出勤時間数 */
    private String kyusyutuHoursStr__ = null;
    /** 遅刻日数 */
    private String chikokuDaysStr__ = null;
    /** 遅刻時間 */
    private String chikokuTimesStr__ = null;
    /** 早退日数 */
    private String soutaiDaysStr__ = null;
    /** 早退時間 */
    private String soutaiTimesStr__ = null;
    /** 有給休暇日数 */
    private String yuukyuDaysStr__ = null;
    /** 欠勤日数 */
    private String kekkinDaysStr__ = null;
    /** 休日区分SIDと休日日数(表示用)のMapping */
    private Map<Integer, String> holDaysStrMap__ = new HashMap<Integer, String>();

    /**
     * <br>[機  能] 休日日数を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param thiSid 休日区分SID
     * @param days 日数
     */
    public void setHolDays(int thiSid, BigDecimal days) {
        holDaysMap__.put(thiSid, days);
    }
    /**
     * <br>[機  能] 休日日数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param thiSid 休日区分SID
     * @return 休日日数
     */
    public BigDecimal getHolDays(int thiSid) {
        return holDaysMap__.get(thiSid);
    }
    /**
     * <br>[機  能] 休日日数(表示用)を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param thiSid 休日区分SID
     * @param days 日数(表示用)
     */
    public void setHolDaysStr(int thiSid, String days) {
        holDaysStrMap__.put(thiSid, days);
    }
    /**
     * <br>[機  能] 休日日数(表示用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param thiSid 休日区分SID
     * @return 休日日数(表示用)
     */
    public String getHolDaysStr(int thiSid) {
        return holDaysStrMap__.get(thiSid);
    }
    /**
     * <p>sinyaDaysStr を取得します。
     * @return sinyaDaysStr
     */
    public String getSinyaDaysStr() {
        return sinyaDaysStr__;
    }
    /**
     * <p>sinyaDaysStr をセットします。
     * @param sinyaDaysStr sinyaDaysStr
     */
    public void setSinyaDaysStr(String sinyaDaysStr) {
        sinyaDaysStr__ = sinyaDaysStr;
    }
    /**
     * <p>sinyaHoursStr を取得します。
     * @return sinyaHoursStr
     */
    public String getSinyaHoursStr() {
        return sinyaHoursStr__;
    }
    /**
     * <p>sinyaHoursStr をセットします。
     * @param sinyaHoursStr sinyaHoursStr
     */
    public void setSinyaHoursStr(String sinyaHoursStr) {
        sinyaHoursStr__ = sinyaHoursStr;
    }
    /**
     * <p>sinyaDays を取得します。
     * @return sinyaDays
     */
    public BigDecimal getSinyaDays() {
        return sinyaDays__;
    }
    /**
     * <p>sinyaDays をセットします。
     * @param sinyaDays sinyaDays
     */
    public void setSinyaDays(BigDecimal sinyaDays) {
        sinyaDays__ = sinyaDays;
    }
    /**
     * <p>sinyaHours を取得します。
     * @return sinyaHours
     */
    public BigDecimal getSinyaHours() {
        return sinyaHours__;
    }
    /**
     * <p>sinyaHours をセットします。
     * @param sinyaHours sinyaHours
     */
    public void setSinyaHours(BigDecimal sinyaHours) {
        sinyaHours__ = sinyaHours;
    }
    /**
     * <p>chikokuDays を取得します。
     * @return chikokuDays
     */
    public BigDecimal getChikokuDays() {
        return chikokuDays__;
    }
    /**
     * <p>chikokuDays をセットします。
     * @param chikokuDays chikokuDays
     */
    public void setChikokuDays(BigDecimal chikokuDays) {
        chikokuDays__ = chikokuDays;
    }
    /**
     * <p>chikokuTimes を取得します。
     * @return chikokuTimes
     */
    public BigDecimal getChikokuTimes() {
        return chikokuTimes__;
    }
    /**
     * <p>chikokuTimes をセットします。
     * @param chikokuTimes chikokuTimes
     */
    public void setChikokuTimes(BigDecimal chikokuTimes) {
        chikokuTimes__ = chikokuTimes;
    }
    /**
     * <p>chikokuDaysStr を取得します。
     * @return chikokuDaysStr
     */
    public String getChikokuDaysStr() {
        return chikokuDaysStr__;
    }
    /**
     * <p>chikokuDaysStr をセットします。
     * @param chikokuDaysStr chikokuDaysStr
     */
    public void setChikokuDaysStr(String chikokuDaysStr) {
        chikokuDaysStr__ = chikokuDaysStr;
    }
    /**
     * <p>chikokuTimesStr を取得します。
     * @return chikokuTimesStr
     */
    public String getChikokuTimesStr() {
        return chikokuTimesStr__;
    }
    /**
     * <p>chikokuTimesStr をセットします。
     * @param chikokuTimesStr chikokuTimesStr
     */
    public void setChikokuTimesStr(String chikokuTimesStr) {
        chikokuTimesStr__ = chikokuTimesStr;
    }
    /**
     * <p>endDate を取得します。
     * @return endDate
     */
    public UDate getEndDate() {
        return endDate__;
    }
    /**
     * <p>endDate をセットします。
     * @param endDate endDate
     */
    public void setEndDate(UDate endDate) {
        endDate__ = endDate;
    }
    /**
     * <p>kadoBaseDays を取得します。
     * @return kadoBaseDays
     */
    public BigDecimal getKadoBaseDays() {
        return kadoBaseDays__;
    }
    /**
     * <p>kadoBaseDays をセットします。
     * @param kadoBaseDays kadoBaseDays
     */
    public void setKadoBaseDays(BigDecimal kadoBaseDays) {
        kadoBaseDays__ = kadoBaseDays;
    }
    /**
     * <p>kadoBaseDaysStr を取得します。
     * @return kadoBaseDaysStr
     */
    public String getKadoBaseDaysStr() {
        return kadoBaseDaysStr__;
    }
    /**
     * <p>kadoBaseDaysStr をセットします。
     * @param kadoBaseDaysStr kadoBaseDaysStr
     */
    public void setKadoBaseDaysStr(String kadoBaseDaysStr) {
        kadoBaseDaysStr__ = kadoBaseDaysStr;
    }
    /**
     * <p>kadoBaseHours を取得します。
     * @return kadoBaseHours
     */
    public BigDecimal getKadoBaseHours() {
        return kadoBaseHours__;
    }
    /**
     * <p>kadoBaseHours をセットします。
     * @param kadoBaseHours kadoBaseHours
     */
    public void setKadoBaseHours(BigDecimal kadoBaseHours) {
        kadoBaseHours__ = kadoBaseHours;
    }
    /**
     * <p>kadoBaseHoursStr を取得します。
     * @return kadoBaseHoursStr
     */
    public String getKadoBaseHoursStr() {
        return kadoBaseHoursStr__;
    }
    /**
     * <p>kadoBaseHoursStr をセットします。
     * @param kadoBaseHoursStr kadoBaseHoursStr
     */
    public void setKadoBaseHoursStr(String kadoBaseHoursStr) {
        kadoBaseHoursStr__ = kadoBaseHoursStr;
    }
    /**
     * <p>kadoDays を取得します。
     * @return kadoDays
     */
    public BigDecimal getKadoDays() {
        return kadoDays__;
    }
    /**
     * <p>kadoDays をセットします。
     * @param kadoDays kadoDays
     */
    public void setKadoDays(BigDecimal kadoDays) {
        kadoDays__ = kadoDays;
    }
    /**
     * <p>kadoDaysStr を取得します。
     * @return kadoDaysStr
     */
    public String getKadoDaysStr() {
        return kadoDaysStr__;
    }
    /**
     * <p>kadoDaysStr をセットします。
     * @param kadoDaysStr kadoDaysStr
     */
    public void setKadoDaysStr(String kadoDaysStr) {
        kadoDaysStr__ = kadoDaysStr;
    }
    /**
     * <p>kadoHours を取得します。
     * @return kadoHours
     */
    public BigDecimal getKadoHours() {
        return kadoHours__;
    }
    /**
     * <p>kadoHours をセットします。
     * @param kadoHours kadoHours
     */
    public void setKadoHours(BigDecimal kadoHours) {
        kadoHours__ = kadoHours;
    }
    /**
     * <p>kadoHoursStr を取得します。
     * @return kadoHoursStr
     */
    public String getKadoHoursStr() {
        return kadoHoursStr__;
    }
    /**
     * <p>kadoHoursStr をセットします。
     * @param kadoHoursStr kadoHoursStr
     */
    public void setKadoHoursStr(String kadoHoursStr) {
        kadoHoursStr__ = kadoHoursStr;
    }
    /**
     * <p>kyusyutuDays を取得します。
     * @return kyusyutuDays
     */
    public BigDecimal getKyusyutuDays() {
        return kyusyutuDays__;
    }
    /**
     * <p>kyusyutuDays をセットします。
     * @param kyusyutuDays kyusyutuDays
     */
    public void setKyusyutuDays(BigDecimal kyusyutuDays) {
        kyusyutuDays__ = kyusyutuDays;
    }
    /**
     * <p>kyusyutuDaysStr を取得します。
     * @return kyusyutuDaysStr
     */
    public String getKyusyutuDaysStr() {
        return kyusyutuDaysStr__;
    }
    /**
     * <p>kyusyutuDaysStr をセットします。
     * @param kyusyutuDaysStr kyusyutuDaysStr
     */
    public void setKyusyutuDaysStr(String kyusyutuDaysStr) {
        kyusyutuDaysStr__ = kyusyutuDaysStr;
    }
    /**
     * <p>kyusyutuHours を取得します。
     * @return kyusyutuHours
     */
    public BigDecimal getKyusyutuHours() {
        return kyusyutuHours__;
    }
    /**
     * <p>kyusyutuHours をセットします。
     * @param kyusyutuHours kyusyutuHours
     */
    public void setKyusyutuHours(BigDecimal kyusyutuHours) {
        kyusyutuHours__ = kyusyutuHours;
    }
    /**
     * <p>kyusyutuHoursStr を取得します。
     * @return kyusyutuHoursStr
     */
    public String getKyusyutuHoursStr() {
        return kyusyutuHoursStr__;
    }
    /**
     * <p>kyusyutuHoursStr をセットします。
     * @param kyusyutuHoursStr kyusyutuHoursStr
     */
    public void setKyusyutuHoursStr(String kyusyutuHoursStr) {
        kyusyutuHoursStr__ = kyusyutuHoursStr;
    }
    /**
     * <p>soutaiDays を取得します。
     * @return soutaiDays
     */
    public BigDecimal getSoutaiDays() {
        return soutaiDays__;
    }
    /**
     * <p>soutaiDays をセットします。
     * @param soutaiDays soutaiDays
     */
    public void setSoutaiDays(BigDecimal soutaiDays) {
        soutaiDays__ = soutaiDays;
    }
    /**
     * <p>soutaiTimes を取得します。
     * @return soutaiTimes
     */
    public BigDecimal getSoutaiTimes() {
        return soutaiTimes__;
    }
    /**
     * <p>soutaiTimes をセットします。
     * @param soutaiTimes soutaiTimes
     */
    public void setSoutaiTimes(BigDecimal soutaiTimes) {
        soutaiTimes__ = soutaiTimes;
    }
    /**
     * <p>soutaiDaysStr を取得します。
     * @return soutaiDaysStr
     */
    public String getSoutaiDaysStr() {
        return soutaiDaysStr__;
    }
    /**
     * <p>soutaiDaysStr をセットします。
     * @param soutaiDaysStr soutaiDaysStr
     */
    public void setSoutaiDaysStr(String soutaiDaysStr) {
        soutaiDaysStr__ = soutaiDaysStr;
    }
    /**
     * <p>soutaiTimesStr を取得します。
     * @return soutaiTimesStr
     */
    public String getSoutaiTimesStr() {
        return soutaiTimesStr__;
    }
    /**
     * <p>soutaiTimesStr をセットします。
     * @param soutaiTimesStr soutaiTimesStr
     */
    public void setSoutaiTimesStr(String soutaiTimesStr) {
        soutaiTimesStr__ = soutaiTimesStr;
    }
    /**
     * <p>startDate を取得します。
     * @return startDate
     */
    public UDate getStartDate() {
        return startDate__;
    }
    /**
     * <p>startDate をセットします。
     * @param startDate startDate
     */
    public void setStartDate(UDate startDate) {
        startDate__ = startDate;
    }
    /**
     * <p>yuukyuDaysStr を取得します。
     * @return yuukyuDays
     */
    public BigDecimal getYuukyuDays() {
        return yuukyuDays__;
    }
    /**
     * <p>yuukyuDays をセットします。
     * @param yuukyuDays yuukyuDays
     */
    public void setYuukyuDays(BigDecimal yuukyuDays) {
        yuukyuDays__ = yuukyuDays;
    }
    /**
     * <p>yuukyuDaysStr を取得します。
     * @return yuukyuDaysStr
     */
    public String getYuukyuDaysStr() {
        return yuukyuDaysStr__;
    }
    /**
     * <p>yuukyuDaysStr をセットします。
     * @param yuukyuDaysStr yuukyuDaysStr
     */
    public void setYuukyuDaysStr(String yuukyuDaysStr) {
        yuukyuDaysStr__ = yuukyuDaysStr;
    }
    /**
     * <p>kekkinDays を取得します。
     * @return kekkinDays
     * @see jp.groupsession.v2.tcd.model.TcdTotalValueModel#kekkinDays__
     */
    public BigDecimal getKekkinDays() {
        return kekkinDays__;
    }
    /**
     * <p>kekkinDays をセットします。
     * @param kekkinDays kekkinDays
     * @see jp.groupsession.v2.tcd.model.TcdTotalValueModel#kekkinDays__
     */
    public void setKekkinDays(BigDecimal kekkinDays) {
        kekkinDays__ = kekkinDays;
    }
    /**
     * <p>kekkinDaysStr を取得します。
     * @return kekkinDaysStr
     * @see jp.groupsession.v2.tcd.model.TcdTotalValueModel#kekkinDaysStr__
     */
    public String getKekkinDaysStr() {
        return kekkinDaysStr__;
    }
    /**
     * <p>kekkinDaysStr をセットします。
     * @param kekkinDaysStr kekkinDaysStr
     * @see jp.groupsession.v2.tcd.model.TcdTotalValueModel#kekkinDaysStr__
     */
    public void setKekkinDaysStr(String kekkinDaysStr) {
        kekkinDaysStr__ = kekkinDaysStr;
    }
    /**
     * <p>zangyoDays を取得します。
     * @return zangyoDays
     */
    public BigDecimal getZangyoDays() {
        return zangyoDays__;
    }
    /**
     * <p>zangyoDays をセットします。
     * @param zangyoDays zangyoDays
     */
    public void setZangyoDays(BigDecimal zangyoDays) {
        zangyoDays__ = zangyoDays;
    }
    /**
     * <p>zangyoDaysStr を取得します。
     * @return zangyoDaysStr
     */
    public String getZangyoDaysStr() {
        return zangyoDaysStr__;
    }
    /**
     * <p>zangyoDaysStr をセットします。
     * @param zangyoDaysStr zangyoDaysStr
     */
    public void setZangyoDaysStr(String zangyoDaysStr) {
        zangyoDaysStr__ = zangyoDaysStr;
    }
    /**
     * <p>zangyoHours を取得します。
     * @return zangyoHours
     */
    public BigDecimal getZangyoHours() {
        return zangyoHours__;
    }
    /**
     * <p>zangyoHours をセットします。
     * @param zangyoHours zangyoHours
     */
    public void setZangyoHours(BigDecimal zangyoHours) {
        zangyoHours__ = zangyoHours;
    }
    /**
     * <p>zangyoHoursStr を取得します。
     * @return zangyoHoursStr
     */
    public String getZangyoHoursStr() {
        return zangyoHoursStr__;
    }
    /**
     * <p>zangyoHoursStr をセットします。
     * @param zangyoHoursStr zangyoHoursStr
     */
    public void setZangyoHoursStr(String zangyoHoursStr) {
        zangyoHoursStr__ = zangyoHoursStr;
    }
    /**
     * <p>kadoMonth を取得します。
     * @return kadoMonth
     */
    public String getKadoMonth() {
        return kadoMonth__;
    }
    /**
     * <p>kadoMonth をセットします。
     * @param kadoMonth kadoMonth
     */
    public void setKadoMonth(String kadoMonth) {
        kadoMonth__ = kadoMonth;
    }
    /**
     * <p>thiSid を取得します。
     * @return thiSid
     * @see jp.groupsession.v2.tcd.model.TcdTotalValueModel#thiSid__
     */
    public int getThiSid() {
        return thiSid__;
    }
    /**
     * <p>thiSid をセットします。
     * @param thiSid thiSid
     * @see jp.groupsession.v2.tcd.model.TcdTotalValueModel#thiSid__
     */
    public void setThiSid(int thiSid) {
        thiSid__ = thiSid;
    }

    /**
     * <p>holDaysMap を取得します。
     * @return holDaysMap
     * @see jp.groupsession.v2.tcd.model.TcdTotalValueModel#holDaysMap__
     */
    public Map<Integer, BigDecimal> getHolDaysMap() {
        return holDaysMap__;
    }
    /**
     * <p>holDaysMap をセットします。
     * @param holDaysMap holDaysMap
     * @see jp.groupsession.v2.tcd.model.TcdTotalValueModel#holDaysMap__
     */
    public void setHolDaysMap(Map<Integer, BigDecimal> holDaysMap) {
        holDaysMap__ = holDaysMap;
    }
    /**
     * <p>holDaysStrMap を取得します。
     * @return holDaysStrMap
     * @see jp.groupsession.v2.tcd.model.TcdTotalValueModel#holDaysStrMap__
     */
    public Map<Integer, String> getHolDaysStrMap() {
        return holDaysStrMap__;
    }
    /**
     * <p>holDaysStrMap をセットします。
     * @param holDaysStrMap holDaysStrMap
     * @see jp.groupsession.v2.tcd.model.TcdTotalValueModel#holDaysStrMap__
     */
    public void setHolDaysStrMap(Map<Integer, String> holDaysStrMap) {
        holDaysStrMap__ = holDaysStrMap;
    }

}
