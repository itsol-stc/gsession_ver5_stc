package jp.groupsession.v2.ntp.model;


import java.io.Serializable;

/**
 * <p>NTP_SPACCESS_TARGET Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpSpaccessTargetModel implements Serializable {

    /** NSA_SID mapping */
    private int nsaSid__;
    /** NST_TYPE mapping */
    private int nstType__;
    /** NST_TSID mapping */
    private int nstTsid__;

    /**
     * <p>Default Constructor
     */
    public NtpSpaccessTargetModel() {
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
     * <p>get NST_TYPE value
     * @return NST_TYPE value
     */
    public int getNstType() {
        return nstType__;
    }

    /**
     * <p>set NST_TYPE value
     * @param nstType NST_TYPE value
     */
    public void setNstType(int nstType) {
        nstType__ = nstType;
    }

    /**
     * <p>get NST_TSID value
     * @return NST_TSID value
     */
    public int getNstTsid() {
        return nstTsid__;
    }

    /**
     * <p>set NST_TSID value
     * @param nstTsid NST_TSID value
     */
    public void setNstTsid(int nstTsid) {
        nstTsid__ = nstTsid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nsaSid__);
        buf.append(",");
        buf.append(nstType__);
        buf.append(",");
        buf.append(nstTsid__);
        return buf.toString();
    }

}
