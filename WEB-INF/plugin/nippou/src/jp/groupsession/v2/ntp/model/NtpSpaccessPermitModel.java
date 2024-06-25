package jp.groupsession.v2.ntp.model;


import java.io.Serializable;

/**
 * <p>NTP_SPACCESS_PERMIT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpSpaccessPermitModel implements Serializable {

    /** NSA_SID mapping */
    private int nsaSid__;
    /** NSP_TYPE mapping */
    private int nspType__;
    /** NSP_PSID mapping */
    private int nspPsid__;
    /** NSP_AUTH mapping */
    private int nspAuth__;

    /**
     * <p>Default Constructor
     */
    public NtpSpaccessPermitModel() {
    }

    /**
     * <p>get NSA_SID value
     * @return NSA_SID value
     */
    public int getNsaSid() {
        return nsaSid__;
    }

    /**
     * <p>set NSA_SID value
     * @param nsaSid NSA_SID value
     */
    public void setNsaSid(int nsaSid) {
        nsaSid__ = nsaSid;
    }

    /**
     * <p>get NSP_TYPE value
     * @return NSP_TYPE value
     */
    public int getNspType() {
        return nspType__;
    }

    /**
     * <p>set NSP_TYPE value
     * @param nspType NSP_TYPE value
     */
    public void setNspType(int nspType) {
        nspType__ = nspType;
    }

    /**
     * <p>get NSP_PSID value
     * @return NSP_PSID value
     */
    public int getNspPsid() {
        return nspPsid__;
    }

    /**
     * <p>set NSP_PSID value
     * @param nspPsid NSP_PSID value
     */
    public void setNspPsid(int nspPsid) {
        nspPsid__ = nspPsid;
    }

    /**
     * <p>get NSP_AUTH value
     * @return NSP_AUTH value
     */
    public int getNspAuth() {
        return nspAuth__;
    }

    /**
     * <p>set NSP_AUTH value
     * @param nspAuth NSP_AUTH value
     */
    public void setNspAuth(int nspAuth) {
        nspAuth__ = nspAuth;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nsaSid__);
        buf.append(",");
        buf.append(nspType__);
        buf.append(",");
        buf.append(nspPsid__);
        buf.append(",");
        buf.append(nspAuth__);
        return buf.toString();
    }

}
