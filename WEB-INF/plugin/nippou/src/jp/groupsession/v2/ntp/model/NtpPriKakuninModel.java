package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_PRI_KAKUNIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpPriKakuninModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** NPK_DFT_USR mapping */
    private int npkDftUsr__;
    /** NPK_AUID mapping */
    private int npkAuid__;
    /** NPK_ADATE mapping */
    private UDate npkAdate__;
    /** NPK_EUID mapping */
    private int npkEuid__;
    /** NPK_EDATE mapping */
    private UDate npkEdate__;

    /**
     * <p>Default Constructor
     */
    public NtpPriKakuninModel() {
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
     * <p>get NPK_DFT_USR value
     * @return NPK_DFT_USR value
     */
    public int getNpkDftUsr() {
        return npkDftUsr__;
    }

    /**
     * <p>set NPK_DFT_USR value
     * @param npkDftUsr NPK_DFT_USR value
     */
    public void setNpkDftUsr(int npkDftUsr) {
        npkDftUsr__ = npkDftUsr;
    }

    /**
     * <p>get NPK_AUID value
     * @return NPK_AUID value
     */
    public int getNpkAuid() {
        return npkAuid__;
    }

    /**
     * <p>set NPK_AUID value
     * @param npkAuid NPK_AUID value
     */
    public void setNpkAuid(int npkAuid) {
        npkAuid__ = npkAuid;
    }

    /**
     * <p>get NPK_ADATE value
     * @return NPK_ADATE value
     */
    public UDate getNpkAdate() {
        return npkAdate__;
    }

    /**
     * <p>set NPK_ADATE value
     * @param npkAdate NPK_ADATE value
     */
    public void setNpkAdate(UDate npkAdate) {
        npkAdate__ = npkAdate;
    }

    /**
     * <p>get NPK_EUID value
     * @return NPK_EUID value
     */
    public int getNpkEuid() {
        return npkEuid__;
    }

    /**
     * <p>set NPK_EUID value
     * @param npkEuid NPK_EUID value
     */
    public void setNpkEuid(int npkEuid) {
        npkEuid__ = npkEuid;
    }

    /**
     * <p>get NPK_EDATE value
     * @return NPK_EDATE value
     */
    public UDate getNpkEdate() {
        return npkEdate__;
    }

    /**
     * <p>set NPK_EDATE value
     * @param npkEdate NPK_EDATE value
     */
    public void setNpkEdate(UDate npkEdate) {
        npkEdate__ = npkEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(npkDftUsr__);
        buf.append(",");
        buf.append(npkAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(npkAdate__, ""));
        buf.append(",");
        buf.append(npkEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(npkEdate__, ""));
        return buf.toString();
    }

}
