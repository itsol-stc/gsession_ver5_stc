package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_SANKA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpSankaModel implements Serializable {

    /** NSN_SID mapping */
    private int nsnSid__;
    /** NIP_SID mapping */
    private int nipSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** NTA_AUID mapping */
    private int ntaAuid__;
    /** NTA_ADATE mapping */
    private UDate ntaAdate__;
    /** NTA_EUID mapping */
    private int ntaEuid__;
    /** NTA_EDATE mapping */
    private UDate ntaEdate__;

    /**
     * <p>Default Constructor
     */
    public NtpSankaModel() {
    }

    /**
     * <p>get NSN_SID value
     * @return NSN_SID value
     */
    public int getNsnSid() {
        return nsnSid__;
    }

    /**
     * <p>set NSN_SID value
     * @param nsnSid NSN_SID value
     */
    public void setNsnSid(int nsnSid) {
        nsnSid__ = nsnSid;
    }

    /**
     * <p>get NIP_SID value
     * @return NIP_SID value
     */
    public int getNipSid() {
        return nipSid__;
    }

    /**
     * <p>set NIP_SID value
     * @param nipSid NIP_SID value
     */
    public void setNipSid(int nipSid) {
        nipSid__ = nipSid;
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
     * <p>get NTA_AUID value
     * @return NTA_AUID value
     */
    public int getNtaAuid() {
        return ntaAuid__;
    }

    /**
     * <p>set NTA_AUID value
     * @param ntaAuid NTA_AUID value
     */
    public void setNtaAuid(int ntaAuid) {
        ntaAuid__ = ntaAuid;
    }

    /**
     * <p>get NTA_ADATE value
     * @return NTA_ADATE value
     */
    public UDate getNtaAdate() {
        return ntaAdate__;
    }

    /**
     * <p>set NTA_ADATE value
     * @param ntaAdate NTA_ADATE value
     */
    public void setNtaAdate(UDate ntaAdate) {
        ntaAdate__ = ntaAdate;
    }

    /**
     * <p>get NTA_EUID value
     * @return NTA_EUID value
     */
    public int getNtaEuid() {
        return ntaEuid__;
    }

    /**
     * <p>set NTA_EUID value
     * @param ntaEuid NTA_EUID value
     */
    public void setNtaEuid(int ntaEuid) {
        ntaEuid__ = ntaEuid;
    }

    /**
     * <p>get NTA_EDATE value
     * @return NTA_EDATE value
     */
    public UDate getNtaEdate() {
        return ntaEdate__;
    }

    /**
     * <p>set NTA_EDATE value
     * @param ntaEdate NTA_EDATE value
     */
    public void setNtaEdate(UDate ntaEdate) {
        ntaEdate__ = ntaEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nsnSid__);
        buf.append(",");
        buf.append(nipSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(ntaAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ntaAdate__, ""));
        buf.append(",");
        buf.append(ntaEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ntaEdate__, ""));
        return buf.toString();
    }

}
