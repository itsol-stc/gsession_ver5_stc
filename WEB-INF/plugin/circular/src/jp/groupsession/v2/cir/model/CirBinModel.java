package jp.groupsession.v2.cir.model;

import java.io.Serializable;

/**
 * <p>CIR_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirBinModel implements Serializable {

    /** CIF_SID mapping */
    private int cifSid__;
    /** BIN_SID mapping */
    private Long binSid__;

    /**
     * <p>Default Constructor
     */
    public CirBinModel() {
    }

    /**
     * <p>get CIF_SID value
     * @return CIF_SID value
     */
    public int getCifSid() {
        return cifSid__;
    }

    /**
     * <p>set CIF_SID value
     * @param cifSid CIF_SID value
     */
    public void setCifSid(int cifSid) {
        cifSid__ = cifSid;
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
        buf.append(cifSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }

}
