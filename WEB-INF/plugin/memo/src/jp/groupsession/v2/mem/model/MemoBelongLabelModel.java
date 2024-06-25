package jp.groupsession.v2.mem.model;

import java.io.Serializable;

/**
 * <p>MEMO_BELONG_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoBelongLabelModel implements Serializable {

    /** MEM_SID mapping */
    private long memSid__;
    /** MML_SID mapping */
    private int mmlSid__;

    /**
     * <p>Default Constructor
     */
    public MemoBelongLabelModel() {
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
     * <p>get MML_SID value
     * @return MML_SID value
     */
    public int getMmlSid() {
        return mmlSid__;
    }

    /**
     * <p>set MML_SID value
     * @param mmlSid MML_SID value
     */
    public void setMmlSid(int mmlSid) {
        mmlSid__ = mmlSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(memSid__);
        buf.append(",");
        buf.append(mmlSid__);
        return buf.toString();
    }

}
