package jp.groupsession.v2.tcd.model;

import java.io.Serializable;
import java.sql.Time;

import jp.co.sjts.util.NullDefault;

/**
 * <p>TCD_TIMEZONE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class TcdTimezoneModel implements Serializable {

    /** TTI_SID mapping */
    private int ttiSid__;
    /** TTZ_SID mapping */
    private int ttzSid__;
    /** TTZ_KBN mapping */
    private int ttzKbn__;
    /** TTZ_FRTIME mapping */
    private Time ttzFrtime__;
    /** TTZ_TOTIME mapping */
    private Time ttzTotime__;
    /** TTI_NAME mapping */
    private String ttiName__;
    /** TTI_RYAKU mapping */
    private String ttiRyaku__;
    /** TTI_SORT mapping */
    private int ttiSort__;
    /** TTI_USE mapping */
    private int ttiUse__;
    /** TTI_HOLIDAY mapping */
    private int ttiHoliday__;
    /** TTP_DEFAULT mapping */
    private int ttpDefault__;


    /**
     * <p>ttiSid を取得します。
     * @return ttiSid
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttiSid__
     */
    public int getTtiSid() {
        return ttiSid__;
    }

    /**
     * <p>ttiSid をセットします。
     * @param ttiSid ttiSid
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttiSid__
     */
    public void setTtiSid(int ttiSid) {
        ttiSid__ = ttiSid;
    }

    /**
     * <p>Default Constructor
     */
    public TcdTimezoneModel() {
    }

    /**
     * <p>get TTZ_SID value
     * @return TTZ_SID value
     */
    public int getTtzSid() {
        return ttzSid__;
    }

    /**
     * <p>set TTZ_SID value
     * @param ttzSid TTZ_SID value
     */
    public void setTtzSid(int ttzSid) {
        ttzSid__ = ttzSid;
    }

    /**
     * <p>get TTZ_KBN value
     * @return TTZ_KBN value
     */
    public int getTtzKbn() {
        return ttzKbn__;
    }

    /**
     * <p>set TTZ_KBN value
     * @param ttzKbn TTZ_KBN value
     */
    public void setTtzKbn(int ttzKbn) {
        ttzKbn__ = ttzKbn;
    }

    /**
     * <p>get TTZ_FRTIME value
     * @return TTZ_FRTIME value
     */
    public Time getTtzFrtime() {
        return ttzFrtime__;
    }

    /**
     * <p>set TTZ_FRTIME value
     * @param ttzFrtime TTZ_FRTIME value
     */
    public void setTtzFrtime(Time ttzFrtime) {
        ttzFrtime__ = ttzFrtime;
    }

    /**
     * <p>get TTZ_TOTIME value
     * @return TTZ_TOTIME value
     */
    public Time getTtzTotime() {
        return ttzTotime__;
    }

    /**
     * <p>set TTZ_TOTIME value
     * @param ttzTotime TTZ_TOTIME value
     */
    public void setTtzTotime(Time ttzTotime) {
        ttzTotime__ = ttzTotime;
    }

    /**
     * <p>ttiName を取得します。
     * @return ttiName
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttiName__
     */
    public String getTtiName() {
        return ttiName__;
    }

    /**
     * <p>ttiName をセットします。
     * @param ttiName ttiName
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttiName__
     */
    public void setTtiName(String ttiName) {
        ttiName__ = ttiName;
    }

    /**
     * <p>ttiRyaku を取得します。
     * @return ttiRyaku
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttiRyaku__
     */
    public String getTtiRyaku() {
        return ttiRyaku__;
    }

    /**
     * <p>ttiRyaku をセットします。
     * @param ttiRyaku ttiRyaku
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttiRyaku__
     */
    public void setTtiRyaku(String ttiRyaku) {
        ttiRyaku__ = ttiRyaku;
    }

    /**
     * <p>ttiSort を取得します。
     * @return ttiSort
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttiSort__
     */
    public int getTtiSort() {
        return ttiSort__;
    }

    /**
     * <p>ttiSort をセットします。
     * @param ttiSort ttiSort
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttiSort__
     */
    public void setTtiSort(int ttiSort) {
        ttiSort__ = ttiSort;
    }

    /**
     * <p>ttiUse を取得します。
     * @return ttiUse
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttiUse__
     */
    public int getTtiUse() {
        return ttiUse__;
    }

    /**
     * <p>ttiUse をセットします。
     * @param ttiUse ttiUse
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttiUse__
     */
    public void setTtiUse(int ttiUse) {
        ttiUse__ = ttiUse;
    }

    /**
     * <p>ttiHoliday を取得します。
     * @return ttiHoliday
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttiHoliday__
     */
    public int getTtiHoliday() {
        return ttiHoliday__;
    }

    /**
     * <p>ttiHoliday をセットします。
     * @param ttiHoliday ttiHoliday
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttiHoliday__
     */
    public void setTtiHoliday(int ttiHoliday) {
        ttiHoliday__ = ttiHoliday;
    }

    /**
     * <p>ttpDefault を取得します。
     * @return ttpDefault
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttpDefault__
     */
    public int getTtpDefault() {
        return ttpDefault__;
    }

    /**
     * <p>ttpDefault をセットします。
     * @param ttpDefault ttpDefault
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneModel#ttpDefault__
     */
    public void setTtpDefault(int ttpDefault) {
        ttpDefault__ = ttpDefault;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(ttzSid__);
        buf.append(",");
        buf.append(ttzKbn__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ttzFrtime__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ttzTotime__, ""));
        return buf.toString();
    }

}
