package jp.groupsession.v2.rsv.model.other;

import java.io.Serializable;

/**
 * <p>SCH_EXDATA_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvSchExdataBinModel implements Serializable {

    /** SCE_SID mapping */
    private int sceSid__;
    /** BIN_SID mapping */
    private long binSid__;

    /**
     * <p>Default Constructor
     */
    public RsvSchExdataBinModel() {
    }

    /**
     * <p>get SCE_SID value
     * @return SCE_SID value
     */
    public int getSceSid() {
        return sceSid__;
    }

    /**
     * <p>set SCE_SID value
     * @param sceSid SCE_SID value
     */
    public void setSceSid(int sceSid) {
        sceSid__ = sceSid;
    }

    /**
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sceSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }

}
