package jp.groupsession.v2.rsv.model;

import java.io.Serializable;

/**
 * <p>RSV_DATA_PUB Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvDataPubModel implements Serializable {

    /** RSY_SID mapping */
    private int rsySid__;
    /** RDP_TYPE mapping */
    private int rdpType__;
    /** RDP_PSID mapping */
    private int rdpPsid__;

    /**
     * <p>Default Constructor
     */
    public RsvDataPubModel() {
    }

    /**
     * <p>get RSY_SID value
     * @return RSY_SID value
     */
    public int getRsySid() {
        return rsySid__;
    }

    /**
     * <p>set RSY_SID value
     * @param rsySid RSY_SID value
     */
    public void setRsySid(int rsySid) {
        rsySid__ = rsySid;
    }

    /**
     * <p>get RDP_TYPE value
     * @return RDP_TYPE value
     */
    public int getRdpType() {
        return rdpType__;
    }

    /**
     * <p>set RDP_TYPE value
     * @param rdpType RDP_TYPE value
     */
    public void setRdpType(int rdpType) {
        rdpType__ = rdpType;
    }

    /**
     * <p>get RDP_PSID value
     * @return RDP_PSID value
     */
    public int getRdpPsid() {
        return rdpPsid__;
    }

    /**
     * <p>set RDP_PSID value
     * @param rdpPsid RDP_PSID value
     */
    public void setRdpPsid(int rdpPsid) {
        rdpPsid__ = rdpPsid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rsySid__);
        buf.append(",");
        buf.append(rdpType__);
        buf.append(",");
        buf.append(rdpPsid__);
        return buf.toString();
    }

}
