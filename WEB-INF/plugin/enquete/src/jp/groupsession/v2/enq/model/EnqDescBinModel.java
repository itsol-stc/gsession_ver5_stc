package jp.groupsession.v2.enq.model;

import java.io.Serializable;

/**
 * <p>ENQ_DESC_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqDescBinModel implements Serializable {

    /** EMN_SID mapping */
    private long emnSid__;
    /** EDB_SID mapping */
    private int edbSid__;
    /** BIN_SID mapping */
    private long binSid__;

    /**
     * <p>Default Constructor
     */
    public EnqDescBinModel() {
    }

    /**
     * <p>get EMN_SID value
     * @return EMN_SID value
     */
    public long getEmnSid() {
        return emnSid__;
    }

    /**
     * <p>set EMN_SID value
     * @param emnSid EMN_SID value
     */
    public void setEmnSid(long emnSid) {
        emnSid__ = emnSid;
    }

    /**
     * <p>get EDB_SID value
     * @return EDB_SID value
     */
    public int getEdbSid() {
        return edbSid__;
    }

    /**
     * <p>set EDB_SID value
     * @param edbSid EDB_SID value
     */
    public void setEdbSid(int edbSid) {
        edbSid__ = edbSid;
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
        buf.append(emnSid__);
        buf.append(",");
        buf.append(edbSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }

}
