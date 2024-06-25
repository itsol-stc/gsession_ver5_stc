package jp.groupsession.v2.sch.model;

import java.io.Serializable;

/**
 * <p>SCH_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchBinModel implements Serializable {

    /** SCD_SID mapping */
    private int scdSid__;
    /** BIN_SID mapping */
    private long binSid__;

    /**
     * <p>Default Constructor
     */
    public SchBinModel() {
    }

    /**
     * <p>get SCD_SID value
     * @return SCD_SID value
     */
    public int getScdSid() {
        return scdSid__;
    }

    /**
     * <p>set SCD_SID value
     * @param scdSid SCD_SID value
     */
    public void setScdSid(int scdSid) {
        scdSid__ = scdSid;
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
        buf.append(scdSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }

}
