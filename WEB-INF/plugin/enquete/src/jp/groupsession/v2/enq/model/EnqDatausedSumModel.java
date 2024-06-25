package jp.groupsession.v2.enq.model;

import java.io.Serializable;

/**
 * <p>ENQ_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** ENQ_DATA_SIZE mapping */
    private long enqDataSize__;

    /**
     * <p>Default Constructor
     */
    public EnqDatausedSumModel() {
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
     * <p>get ENQ_DATA_SIZE value
     * @return ENQ_DATA_SIZE value
     */
    public long getEnqDataSize() {
        return enqDataSize__;
    }

    /**
     * <p>set ENQ_DATA_SIZE value
     * @param enqDataSize ENQ_DATA_SIZE value
     */
    public void setEnqDataSize(long enqDataSize) {
        enqDataSize__ = enqDataSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(enqDataSize__);
        return buf.toString();
    }

}
