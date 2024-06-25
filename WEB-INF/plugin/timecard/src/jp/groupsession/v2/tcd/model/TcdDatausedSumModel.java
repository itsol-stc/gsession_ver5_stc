package jp.groupsession.v2.tcd.model;

import java.io.Serializable;

/**
 * <p>TCD_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class TcdDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** TCD_TCDATA_SIZE mapping */
    private long tcdTcdataSize__;

    /**
     * <p>Default Constructor
     */
    public TcdDatausedSumModel() {
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
     * <p>get TCD_TCDATA_SIZE value
     * @return TCD_TCDATA_SIZE value
     */
    public long getTcdTcdataSize() {
        return tcdTcdataSize__;
    }

    /**
     * <p>set TCD_TCDATA_SIZE value
     * @param tcdTcdataSize TCD_TCDATA_SIZE value
     */
    public void setTcdTcdataSize(long tcdTcdataSize) {
        tcdTcdataSize__ = tcdTcdataSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(tcdTcdataSize__);
        return buf.toString();
    }

}
