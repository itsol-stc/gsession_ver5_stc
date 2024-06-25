package jp.groupsession.v2.mem.model;

import java.io.Serializable;

/**
 * <p>MEMO_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoBinModel implements Serializable {

    /** MEM_SID mapping */
    private long memSid__;
    /** BIN_SID mapping */
    private long binSid__;

    /**
     * <p>Default Constructor
     */
    public MemoBinModel() {
    }

    /**
     * <p>get MEM_SID value
     * @return MEM_SID value
     */
    public long getMemSid() {
        return memSid__;
    }

    /**
     * <p>set MEM_SID value
     * @param memSid MEM_SID value
     */
    public void setMemSid(long memSid) {
        memSid__ = memSid;
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
        buf.append(memSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }

}
