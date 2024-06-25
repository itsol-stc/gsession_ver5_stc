package jp.groupsession.v2.ntp.model;

import java.io.Serializable;

/**
 * <p>NTP_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpBinModel implements Serializable {

    /** NTP_SID mapping */
    private int ntpSid__;
    /** BIN_SID mapping */
    private Long binSid__ = new Long(0);

    /**
     * <p>Default Constructor
     */
    public NtpBinModel() {
    }

    /**
     * <p>get CIF_SID value
     * @return CIF_SID value
     */
    public int getNtpSid() {
        return ntpSid__;
    }

    /**
     * <p>set CIF_SID value
     * @param ntpSid CIF_SID value
     */
    public void setNtpSid(int ntpSid) {
        ntpSid__ = ntpSid;
    }

    /**
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public Long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(ntpSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }
}