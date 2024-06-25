package jp.groupsession.v2.cir.model;

import java.io.Serializable;

/**
 * <p>CIR_INF_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CirInfLabelModel implements Serializable {

    /** CIF_SID mapping */
    private int cifSid__;
    /** CAC_SID mapping */
    private int cacSid__;
    /** CLA_SID mapping */
    private int claSid__;

    /**
     * <p>Default Constructor
     */
    public CirInfLabelModel() {
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
     * <p>get CAC_SID value
     * @return CAC_SID value
     */
    public int getCacSid() {
        return cacSid__;
    }

    /**
     * <p>set CAC_SID value
     * @param cacSid CAC_SID value
     */
    public void setCacSid(int cacSid) {
        cacSid__ = cacSid;
    }

    /**
     * <p>get CLA_SID value
     * @return CLA_SID value
     */
    public int getClaSid() {
        return claSid__;
    }

    /**
     * <p>set CLA_SID value
     * @param claSid CLA_SID value
     */
    public void setClaSid(int claSid) {
        claSid__ = claSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cifSid__);
        buf.append(",");
        buf.append(cacSid__);
        buf.append(",");
        buf.append(claSid__);
        return buf.toString();
    }

}
