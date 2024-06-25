package jp.groupsession.v2.sch.model;

import java.io.Serializable;

/**
 * <p>SCH_INIT_PUB Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchInitPubModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** SIP_TYPE mapping */
    private int sipType__;
    /** SIP_PSID mapping */
    private int sipPsid__;

    /**
     * <p>Default Constructor
     */
    public SchInitPubModel() {
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get SIP_TYPE value
     * @return SIP_TYPE value
     */
    public int getSipType() {
        return sipType__;
    }

    /**
     * <p>set SIP_TYPE value
     * @param sipType SIP_TYPE value
     */
    public void setSipType(int sipType) {
        sipType__ = sipType;
    }

    /**
     * <p>get SIP_PSID value
     * @return SIP_PSID value
     */
    public int getSipPsid() {
        return sipPsid__;
    }

    /**
     * <p>set SIP_PSID value
     * @param sipPsid SIP_PSID value
     */
    public void setSipPsid(int sipPsid) {
        sipPsid__ = sipPsid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(sipType__);
        buf.append(",");
        buf.append(sipPsid__);
        return buf.toString();
    }

}
