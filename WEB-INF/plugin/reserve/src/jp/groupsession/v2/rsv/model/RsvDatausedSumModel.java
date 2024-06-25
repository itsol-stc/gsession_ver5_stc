package jp.groupsession.v2.rsv.model;

import java.io.Serializable;

/**
 * <p>RSV_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** RSV_SYRK_SUM_SIZE mapping */
    private long rsvSyrkSumSize__;
    /** RSV_DATA_SUM_SIZE mapping */
    private long rsvDataSumSize__;

    /**
     * <p>Default Constructor
     */
    public RsvDatausedSumModel() {
    }

    /**
     * <p>get SUM_TYPE value
     * @return SUM_TYPE value
     */
    public int getSumType() {
        return sumType__;
    }

    /**
     * <p>set SUM_TYPE value
     * @param sumType SUM_TYPE value
     */
    public void setSumType(int sumType) {
        sumType__ = sumType;
    }

    /**
     * <p>get RSV_SYRK_SUM_SIZE value
     * @return RSV_SYRK_SUM_SIZE value
     */
    public long getRsvSyrkSumSize() {
        return rsvSyrkSumSize__;
    }

    /**
     * <p>set RSV_SYRK_SUM_SIZE value
     * @param rsvSyrkSumSize RSV_SYRK_SUM_SIZE value
     */
    public void setRsvSyrkSumSize(long rsvSyrkSumSize) {
        rsvSyrkSumSize__ = rsvSyrkSumSize;
    }

    /**
     * <p>get RSV_DATA_SUM_SIZE value
     * @return RSV_DATA_SUM_SIZE value
     */
    public long getRsvDataSumSize() {
        return rsvDataSumSize__;
    }

    /**
     * <p>set RSV_DATA_SUM_SIZE value
     * @param rsvDataSumSize RSV_DATA_SUM_SIZE value
     */
    public void setRsvDataSumSize(long rsvDataSumSize) {
        rsvDataSumSize__ = rsvDataSumSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(rsvSyrkSumSize__);
        buf.append(",");
        buf.append(rsvDataSumSize__);
        return buf.toString();
    }

}
