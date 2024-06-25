package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_AN_SHOHIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpAnShohinModel implements Serializable {

    /** NAN_SID mapping */
    private int nanSid__;
    /** NHN_SID mapping */
    private int nhnSid__;
    /** NAS_AUID mapping */
    private int nasAuid__;
    /** NAS_ADATE mapping */
    private UDate nasAdate__;
    /** NAS_EUID mapping */
    private int nasEuid__;
    /** NAS_EDATE mapping */
    private UDate nasEdate__;

    /**
     * <p>Default Constructor
     */
    public NtpAnShohinModel() {
    }

    /**
     * <p>get NAN_SID value
     * @return NAN_SID value
     */
    public int getNanSid() {
        return nanSid__;
    }

    /**
     * <p>set NAN_SID value
     * @param nanSid NAN_SID value
     */
    public void setNanSid(int nanSid) {
        nanSid__ = nanSid;
    }

    /**
     * <p>get NHN_SID value
     * @return NHN_SID value
     */
    public int getNhnSid() {
        return nhnSid__;
    }

    /**
     * <p>set NHN_SID value
     * @param nhnSid NHN_SID value
     */
    public void setNhnSid(int nhnSid) {
        nhnSid__ = nhnSid;
    }

    /**
     * <p>get NAS_AUID value
     * @return NAS_AUID value
     */
    public int getNasAuid() {
        return nasAuid__;
    }

    /**
     * <p>set NAS_AUID value
     * @param nasAuid NAS_AUID value
     */
    public void setNasAuid(int nasAuid) {
        nasAuid__ = nasAuid;
    }

    /**
     * <p>get NAS_ADATE value
     * @return NAS_ADATE value
     */
    public UDate getNasAdate() {
        return nasAdate__;
    }

    /**
     * <p>set NAS_ADATE value
     * @param nasAdate NAS_ADATE value
     */
    public void setNasAdate(UDate nasAdate) {
        nasAdate__ = nasAdate;
    }

    /**
     * <p>get NAS_EUID value
     * @return NAS_EUID value
     */
    public int getNasEuid() {
        return nasEuid__;
    }

    /**
     * <p>set NAS_EUID value
     * @param nasEuid NAS_EUID value
     */
    public void setNasEuid(int nasEuid) {
        nasEuid__ = nasEuid;
    }

    /**
     * <p>get NAS_EDATE value
     * @return NAS_EDATE value
     */
    public UDate getNasEdate() {
        return nasEdate__;
    }

    /**
     * <p>set NAS_EDATE value
     * @param nasEdate NAS_EDATE value
     */
    public void setNasEdate(UDate nasEdate) {
        nasEdate__ = nasEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nanSid__);
        buf.append(",");
        buf.append(nhnSid__);
        buf.append(",");
        buf.append(nasAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nasAdate__, ""));
        buf.append(",");
        buf.append(nasEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nasEdate__, ""));
        return buf.toString();
    }

}
