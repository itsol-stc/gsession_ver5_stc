package jp.groupsession.v2.enq.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>ENQ_TYPE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqTypeModel implements Serializable {

    /** ETP_SID mapping */
    private long etpSid__;
    /** ETP_DSP_SEQ mapping */
    private int etpDspSeq__;
    /** ETP_NAME mapping */
    private String etpName__;
    /** ETP_AUID mapping */
    private int etpAuid__;
    /** ETP_ADATE mapping */
    private UDate etpAdate__;
    /** ETP_EUID mapping */
    private int etpEuid__;
    /** ETP_EDATE mapping */
    private UDate etpEdate__;

    /**
     * <p>Default Constructor
     */
    public EnqTypeModel() {
    }

    /**
     * <p>get ETP_SID value
     * @return ETP_SID value
     */
    public long getEtpSid() {
        return etpSid__;
    }

    /**
     * <p>set ETP_SID value
     * @param etpSid ETP_SID value
     */
    public void setEtpSid(long etpSid) {
        etpSid__ = etpSid;
    }

    /**
     * <p>get ETP_DSP_SEQ value
     * @return ETP_DSP_SEQ value
     */
    public int getEtpDspSeq() {
        return etpDspSeq__;
    }

    /**
     * <p>set ETP_DSP_SEQ value
     * @param etpDspSeq ETP_DSP_SEQ value
     */
    public void setEtpDspSeq(int etpDspSeq) {
        etpDspSeq__ = etpDspSeq;
    }

    /**
     * <p>get ETP_NAME value
     * @return ETP_NAME value
     */
    public String getEtpName() {
        return etpName__;
    }

    /**
     * <p>set ETP_NAME value
     * @param etpName ETP_NAME value
     */
    public void setEtpName(String etpName) {
        etpName__ = etpName;
    }

    /**
     * <p>get ETP_AUID value
     * @return ETP_AUID value
     */
    public int getEtpAuid() {
        return etpAuid__;
    }

    /**
     * <p>set ETP_AUID value
     * @param etpAuid ETP_AUID value
     */
    public void setEtpAuid(int etpAuid) {
        etpAuid__ = etpAuid;
    }

    /**
     * <p>get ETP_ADATE value
     * @return ETP_ADATE value
     */
    public UDate getEtpAdate() {
        return etpAdate__;
    }

    /**
     * <p>set ETP_ADATE value
     * @param etpAdate ETP_ADATE value
     */
    public void setEtpAdate(UDate etpAdate) {
        etpAdate__ = etpAdate;
    }

    /**
     * <p>get ETP_EUID value
     * @return ETP_EUID value
     */
    public int getEtpEuid() {
        return etpEuid__;
    }

    /**
     * <p>set ETP_EUID value
     * @param etpEuid ETP_EUID value
     */
    public void setEtpEuid(int etpEuid) {
        etpEuid__ = etpEuid;
    }

    /**
     * <p>get ETP_EDATE value
     * @return ETP_EDATE value
     */
    public UDate getEtpEdate() {
        return etpEdate__;
    }

    /**
     * <p>set ETP_EDATE value
     * @param etpEdate ETP_EDATE value
     */
    public void setEtpEdate(UDate etpEdate) {
        etpEdate__ = etpEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(etpSid__);
        buf.append(",");
        buf.append(etpDspSeq__);
        buf.append(",");
        buf.append(NullDefault.getString(etpName__, ""));
        buf.append(",");
        buf.append(etpAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(etpAdate__, ""));
        buf.append(",");
        buf.append(etpEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(etpEdate__, ""));
        return buf.toString();
    }

}
