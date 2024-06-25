package jp.groupsession.v2.zsk.model;

import java.io.Serializable;

/**
 * <p>ZAI_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ZaiDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** ZAI_DATA_SIZE mapping */
    private long zaiDataSize__;

    /**
     * <p>Default Constructor
     */
    public ZaiDatausedSumModel() {
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
     * <p>get ZAI_DATA_SIZE value
     * @return ZAI_DATA_SIZE value
     */
    public long getZaiDataSize() {
        return zaiDataSize__;
    }

    /**
     * <p>set ZAI_DATA_SIZE value
     * @param zaiDataSize ZAI_DATA_SIZE value
     */
    public void setZaiDataSize(long zaiDataSize) {
        zaiDataSize__ = zaiDataSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(zaiDataSize__);
        return buf.toString();
    }

}
