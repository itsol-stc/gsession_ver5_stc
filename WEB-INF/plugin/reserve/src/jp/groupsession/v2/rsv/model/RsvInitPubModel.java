package jp.groupsession.v2.rsv.model;

import java.io.Serializable;

/**
 * <p>RSV_INIT_PUB Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvInitPubModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** RIP_TYPE mapping */
    private int ripType__;
    /** RIP_PSID mapping */
    private int ripPsid__;

    /**
     * <p>Default Constructor
     */
    public RsvInitPubModel() {
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
     * <p>get RIP_TYPE value
     * @return RIP_TYPE value
     */
    public int getRipType() {
        return ripType__;
    }

    /**
     * <p>set RIP_TYPE value
     * @param ripType RIP_TYPE value
     */
    public void setRipType(int ripType) {
        ripType__ = ripType;
    }

    /**
     * <p>get RIP_PSID value
     * @return RIP_PSID value
     */
    public int getRipPsid() {
        return ripPsid__;
    }

    /**
     * <p>set RIP_PSID value
     * @param ripPsid RIP_PSID value
     */
    public void setRipPsid(int ripPsid) {
        ripPsid__ = ripPsid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(ripType__);
        buf.append(",");
        buf.append(ripPsid__);
        return buf.toString();
    }

}
