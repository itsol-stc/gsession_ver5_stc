package jp.groupsession.v2.rsv.model;

import java.io.Serializable;

/**
 * <p>RSV_EXDATA_PUB Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvExdataPubModel implements Serializable {

    /** RSR_RSID mapping */
    private int rsrRsid__;
    /** REP_TYPE mapping */
    private int repType__;
    /** REP_PSID mapping */
    private int repPsid__;

    /**
     * <p>Default Constructor
     */
    public RsvExdataPubModel() {
    }

    /**
     * <p>get RSR_RSID value
     * @return RSR_RSID value
     */
    public int getRsrRsid() {
        return rsrRsid__;
    }

    /**
     * <p>set RSR_RSID value
     * @param rsrRsid RSR_RSID value
     */
    public void setRsrRsid(int rsrRsid) {
        rsrRsid__ = rsrRsid;
    }

    /**
     * <p>get REP_TYPE value
     * @return REP_TYPE value
     */
    public int getRepType() {
        return repType__;
    }

    /**
     * <p>set REP_TYPE value
     * @param repType REP_TYPE value
     */
    public void setRepType(int repType) {
        repType__ = repType;
    }

    /**
     * <p>get REP_PSID value
     * @return REP_PSID value
     */
    public int getRepPsid() {
        return repPsid__;
    }

    /**
     * <p>set REP_PSID value
     * @param repPsid REP_PSID value
     */
    public void setRepPsid(int repPsid) {
        repPsid__ = repPsid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rsrRsid__);
        buf.append(",");
        buf.append(repType__);
        buf.append(",");
        buf.append(repPsid__);
        return buf.toString();
    }

}
