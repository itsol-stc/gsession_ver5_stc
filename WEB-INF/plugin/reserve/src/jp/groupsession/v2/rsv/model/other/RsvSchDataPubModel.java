package jp.groupsession.v2.rsv.model.other;

import java.io.Serializable;

/**
 * <p>SCH_DATA_PUB Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvSchDataPubModel implements Serializable {

    /** SCD_SID mapping */
    private int scdSid__;
    /** SDP_TYPE mapping */
    private int sdpType__;
    /** SDP_PSID mapping */
    private int sdpPsid__;

    /**
     * <p>Default Constructor
     */
    public RsvSchDataPubModel() {
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
     * <p>get SDP_TYPE value
     * @return SDP_TYPE value
     */
    public int getSdpType() {
        return sdpType__;
    }

    /**
     * <p>set SDP_TYPE value
     * @param sdpType SDP_TYPE value
     */
    public void setSdpType(int sdpType) {
        sdpType__ = sdpType;
    }

    /**
     * <p>get SDP_PSID value
     * @return SDP_PSID value
     */
    public int getSdpPsid() {
        return sdpPsid__;
    }

    /**
     * <p>set SDP_PSID value
     * @param sdpPsid SDP_PSID value
     */
    public void setSdpPsid(int sdpPsid) {
        sdpPsid__ = sdpPsid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(scdSid__);
        buf.append(",");
        buf.append(sdpType__);
        buf.append(",");
        buf.append(sdpPsid__);
        return buf.toString();
    }

}
