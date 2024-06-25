package jp.groupsession.v2.cir.model;

import java.io.Serializable;

/**
 * <p>CIR_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CirDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** CIR_DATA_SIZE mapping */
    private long cirDataSize__;

    /**
     * <p>Default Constructor
     */
    public CirDatausedSumModel() {
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
     * <p>get CIR_DATA_SIZE value
     * @return CIR_DATA_SIZE value
     */
    public long getCirDataSize() {
        return cirDataSize__;
    }

    /**
     * <p>set CIR_DATA_SIZE value
     * @param cirDataSize CIR_DATA_SIZE value
     */
    public void setCirDataSize(long cirDataSize) {
        cirDataSize__ = cirDataSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(cirDataSize__);
        return buf.toString();
    }

}
