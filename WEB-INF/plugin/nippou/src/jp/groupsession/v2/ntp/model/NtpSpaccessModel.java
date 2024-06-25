package jp.groupsession.v2.ntp.model;


import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_SPACCESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpSpaccessModel implements Serializable {

    /** NSA_SID mapping */
    private int nsaSid__;
    /** NSA_NAME mapping */
    private String nsaName__;
    /** NSA_BIKO mapping */
    private String nsaBiko__;
    /** NSA_AUID mapping */
    private int nsaAuid__;
    /** NSA_ADATE mapping */
    private UDate nsaAdate__;
    /** NSA_EUID mapping */
    private int nsaEuid__;
    /** NSA_EDATE mapping */
    private UDate nsaEdate__;

    /**
     * <p>Default Constructor
     */
    public NtpSpaccessModel() {
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
     * <p>get NSA_NAME value
     * @return NSA_NAME value
     */
    public String getNsaName() {
        return nsaName__;
    }

    /**
     * <p>set NSA_NAME value
     * @param nsaName NSA_NAME value
     */
    public void setNsaName(String nsaName) {
        nsaName__ = nsaName;
    }

    /**
     * <p>get NSA_BIKO value
     * @return NSA_BIKO value
     */
    public String getNsaBiko() {
        return nsaBiko__;
    }

    /**
     * <p>set NSA_BIKO value
     * @param nsaBiko NSA_BIKO value
     */
    public void setNsaBiko(String nsaBiko) {
        nsaBiko__ = nsaBiko;
    }

    /**
     * <p>get NSA_AUID value
     * @return NSA_AUID value
     */
    public int getNsaAuid() {
        return nsaAuid__;
    }

    /**
     * <p>set NSA_AUID value
     * @param nsaAuid NSA_AUID value
     */
    public void setNsaAuid(int nsaAuid) {
        nsaAuid__ = nsaAuid;
    }

    /**
     * <p>get NSA_ADATE value
     * @return NSA_ADATE value
     */
    public UDate getNsaAdate() {
        return nsaAdate__;
    }

    /**
     * <p>set NSA_ADATE value
     * @param nsaAdate NSA_ADATE value
     */
    public void setNsaAdate(UDate nsaAdate) {
        nsaAdate__ = nsaAdate;
    }

    /**
     * <p>get NSA_EUID value
     * @return NSA_EUID value
     */
    public int getNsaEuid() {
        return nsaEuid__;
    }

    /**
     * <p>set NSA_EUID value
     * @param nsaEuid NSA_EUID value
     */
    public void setNsaEuid(int nsaEuid) {
        nsaEuid__ = nsaEuid;
    }

    /**
     * <p>get NSA_EDATE value
     * @return NSA_EDATE value
     */
    public UDate getNsaEdate() {
        return nsaEdate__;
    }

    /**
     * <p>set NSA_EDATE value
     * @param nsaEdate NSA_EDATE value
     */
    public void setNsaEdate(UDate nsaEdate) {
        nsaEdate__ = nsaEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nsaSid__);
        buf.append(",");
        buf.append(NullDefault.getString(nsaName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(nsaBiko__, ""));
        buf.append(",");
        buf.append(nsaAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nsaAdate__, ""));
        buf.append(",");
        buf.append(nsaEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nsaEdate__, ""));
        return buf.toString();
    }

}
