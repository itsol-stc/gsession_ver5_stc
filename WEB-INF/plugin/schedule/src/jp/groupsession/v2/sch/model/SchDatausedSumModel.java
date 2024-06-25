package jp.groupsession.v2.sch.model;

import java.io.Serializable;

/**
 * <p>SCH_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** SCH_DATA_SIZE mapping */
    private long schDataSize__;

    /**
     * <p>Default Constructor
     */
    public SchDatausedSumModel() {
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
     * <p>get SCH_DATA_SIZE value
     * @return SCH_DATA_SIZE value
     */
    public long getSchDataSize() {
        return schDataSize__;
    }

    /**
     * <p>set SCH_DATA_SIZE value
     * @param schDataSize SCH_DATA_SIZE value
     */
    public void setSchDataSize(long schDataSize) {
        schDataSize__ = schDataSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(schDataSize__);
        return buf.toString();
    }

}
