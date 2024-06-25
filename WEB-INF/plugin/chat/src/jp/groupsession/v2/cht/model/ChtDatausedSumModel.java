package jp.groupsession.v2.cht.model;

import java.io.Serializable;

/**
 * <p>CHT_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** CHT_DISK_SIZE mapping */
    private long chtDiskSize__;

    /**
     * <p>Default Constructor
     */
    public ChtDatausedSumModel() {
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
     * <p>get CHT_DISK_SIZE value
     * @return CHT_DISK_SIZE value
     */
    public long getChtDiskSize() {
        return chtDiskSize__;
    }

    /**
     * <p>set CHT_DISK_SIZE value
     * @param chtDiskSize CHT_DISK_SIZE value
     */
    public void setChtDiskSize(long chtDiskSize) {
        chtDiskSize__ = chtDiskSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(chtDiskSize__);
        return buf.toString();
    }

}
