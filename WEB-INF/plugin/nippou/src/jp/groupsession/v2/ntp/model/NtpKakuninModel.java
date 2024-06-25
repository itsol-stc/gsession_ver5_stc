package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_KAKUNIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpKakuninModel implements Serializable {

    /** NKK_SID mapping */
    private int nkkSid__;
    /** NIP_SID mapping */
    private int nipSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** NKK_CHECK mapping */
    private int nkkCheck__;
    /** NKK_DATE_CHECK mapping */
    private UDate nkkDateCheck__;
    /** NKK_AUID mapping */
    private int nkkAuid__;
    /** NKK_ADATE mapping */
    private UDate nkkAdate__;
    /** NKK_EUID mapping */
    private int nkkEuid__;
    /** NKK_EDATE mapping */
    private UDate nkkEdate__;

    /**
     * <p>Default Constructor
     */
    public NtpKakuninModel() {
    }

    /**
     * <p>get NKK_SID value
     * @return NKK_SID value
     */
    public int getNkkSid() {
        return nkkSid__;
    }

    /**
     * <p>set NKK_SID value
     * @param nkkSid NKK_SID value
     */
    public void setNkkSid(int nkkSid) {
        nkkSid__ = nkkSid;
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
     * <p>get NKK_CHECK value
     * @return NKK_CHECK value
     */
    public int getNkkCheck() {
        return nkkCheck__;
    }

    /**
     * <p>set NKK_CHECK value
     * @param nkkCheck NKK_CHECK value
     */
    public void setNkkCheck(int nkkCheck) {
        nkkCheck__ = nkkCheck;
    }

    /**
     * <p>get NKK_DATE_CHECK value
     * @return NKK_DATE_CHECK value
     */
    public UDate getNkkDateCheck() {
        return nkkDateCheck__;
    }

    /**
     * <p>set NKK_DATE_CHECK value
     * @param nkkDateCheck NKK_DATE_CHECK value
     */
    public void setNkkDateCheck(UDate nkkDateCheck) {
        nkkDateCheck__ = nkkDateCheck;
    }

    /**
     * <p>get NKK_AUID value
     * @return NKK_AUID value
     */
    public int getNkkAuid() {
        return nkkAuid__;
    }

    /**
     * <p>set NKK_AUID value
     * @param nkkAuid NKK_AUID value
     */
    public void setNkkAuid(int nkkAuid) {
        nkkAuid__ = nkkAuid;
    }

    /**
     * <p>get NKK_ADATE value
     * @return NKK_ADATE value
     */
    public UDate getNkkAdate() {
        return nkkAdate__;
    }

    /**
     * <p>set NKK_ADATE value
     * @param nkkAdate NKK_ADATE value
     */
    public void setNkkAdate(UDate nkkAdate) {
        nkkAdate__ = nkkAdate;
    }

    /**
     * <p>get NKK_EUID value
     * @return NKK_EUID value
     */
    public int getNkkEuid() {
        return nkkEuid__;
    }

    /**
     * <p>set NKK_EUID value
     * @param nkkEuid NKK_EUID value
     */
    public void setNkkEuid(int nkkEuid) {
        nkkEuid__ = nkkEuid;
    }

    /**
     * <p>get NKK_EDATE value
     * @return NKK_EDATE value
     */
    public UDate getNkkEdate() {
        return nkkEdate__;
    }

    /**
     * <p>set NKK_EDATE value
     * @param nkkEdate NKK_EDATE value
     */
    public void setNkkEdate(UDate nkkEdate) {
        nkkEdate__ = nkkEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nkkSid__);
        buf.append(",");
        buf.append(nipSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(nkkCheck__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nkkDateCheck__, ""));
        buf.append(",");
        buf.append(nkkAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nkkAdate__, ""));
        buf.append(",");
        buf.append(nkkEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nkkEdate__, ""));
        return buf.toString();
    }

}
