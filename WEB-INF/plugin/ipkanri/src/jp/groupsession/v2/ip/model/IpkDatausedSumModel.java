package jp.groupsession.v2.ip.model;

import java.io.Serializable;

/**
 * <p>IPK_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class IpkDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** IPK_DATA_SIZE mapping */
    private long ipkDataSize__;

    /**
     * <p>Default Constructor
     */
    public IpkDatausedSumModel() {
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
     * <p>get IPK_DATA_SIZE value
     * @return IPK_DATA_SIZE value
     */
    public long getIpkDataSize() {
        return ipkDataSize__;
    }

    /**
     * <p>set IPK_DATA_SIZE value
     * @param ipkDataSize IPK_DATA_SIZE value
     */
    public void setIpkDataSize(long ipkDataSize) {
        ipkDataSize__ = ipkDataSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(ipkDataSize__);
        return buf.toString();
    }

}
