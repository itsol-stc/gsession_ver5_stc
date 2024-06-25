package jp.groupsession.v2.cht.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CHT_USER_CORRECT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtUserDataSumModel implements Serializable {

    /** CUP_SID mapping */
    private int cupSid__;
    /** CUS_CNT mapping */
    private int cusCnt__;
    /** CUS_LASTSID mapping */
    private long cusLastsid__;
    /** CUS_LASTDATE mapping */
    private UDate cusLastdate__;

    /**
     * <p>Default Constructor
     */
    public ChtUserDataSumModel() {
    }

    /**
     * <p>get CUP_SID value
     * @return CUP_SID value
     */
    public int getCupSid() {
        return cupSid__;
    }

    /**
     * <p>set CUP_SID value
     * @param cupSid CUP_SID value
     */
    public void setCupSid(int cupSid) {
        cupSid__ = cupSid;
    }

    /**
     * <p>get CUS_CNT value
     * @return CUS_CNT value
     */
    public int getCusCnt() {
        return cusCnt__;
    }

    /**
     * <p>set CUS_CNT value
     * @param cucCnt CUS_CNT value
     */
    public void setCusCnt(int cucCnt) {
        cusCnt__ = cucCnt;
    }

    /**
     * <p>get CUS_LASTSID value
     * @return CUS_LASTSID value
     */
    public long getCusLastsid() {
        return cusLastsid__;
    }

    /**
     * <p>set CUS_LASTSID value
     * @param cucLastsid CUS_LASTSID value
     */
    public void setCusLastsid(long cucLastsid) {
        cusLastsid__ = cucLastsid;
    }

    /**
     * <p>get CUS_LASTDATE value
     * @return CUS_LASTDATE value
     */
    public UDate getCusLastdate() {
        return cusLastdate__;
    }

    /**
     * <p>set CUS_LASTDATE value
     * @param cucLastdate CUS_LASTDATE value
     */
    public void setCusLastdate(UDate cucLastdate) {
        cusLastdate__ = cucLastdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cupSid__);
        buf.append(",");
        buf.append(cusCnt__);
        buf.append(",");
        buf.append(cusLastsid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cusLastdate__, ""));
        return buf.toString();
    }

}
