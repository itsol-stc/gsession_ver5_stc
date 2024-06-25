package jp.groupsession.v2.ntp.model;

import java.io.Serializable;

/**
 * <p>NTP_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** NTP_DATA_SIZE mapping */
    private long ntpDataSize__;

    /**
     * <p>Default Constructor
     */
    public NtpDatausedSumModel() {
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
     * <p>get NTP_DATA_SIZE value
     * @return NTP_DATA_SIZE value
     */
    public long getNtpDataSize() {
        return ntpDataSize__;
    }

    /**
     * <p>set NTP_DATA_SIZE value
     * @param ntpDataSize NTP_DATA_SIZE value
     */
    public void setNtpDataSize(long ntpDataSize) {
        ntpDataSize__ = ntpDataSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(ntpDataSize__);
        return buf.toString();
    }

}
