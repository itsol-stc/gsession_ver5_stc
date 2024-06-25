package jp.groupsession.v2.enq.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>ENQ_ANS_SUB Data Binding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqAnsSubModel implements Serializable {

    /** EMN_SID mapping */
    private long emnSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** EQM_SEQ mapping */
    private int eqmSeq__;
    /** EQS_SEQ mapping */
    private int eqsSeq__;
    /** EAS_ANS_TXT mapping */
    private String easAnsTxt__;
    /** EAS_ANS_NUM mapping */
    private BigDecimal easAnsNum__;
    /** EAS_ANS_DAT mapping */
    private UDate easAnsDat__;
    /** EAS_ANS mapping */
    private String easAns__;
    /** EQM_AUID mapping */
    private int eqmAuid__;
    /** EQM_ADATE mapping */
    private UDate eqmAdate__;
    /** EQM_EUID mapping */
    private int eqmEuid__;
    /** EQM_EDATE mapping */
    private UDate eqmEdate__;

    /**
     * <p>Default Constructor
     */
    public EnqAnsSubModel() {
    }

    /**
     * <p>get EMN_SID value
     * @return EMN_SID value
     */
    public long getEmnSid() {
        return emnSid__;
    }

    /**
     * <p>set EMN_SID value
     * @param emnSid EMN_SID value
     */
    public void setEmnSid(long emnSid) {
        emnSid__ = emnSid;
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
     * <p>get EQM_SEQ value
     * @return EQM_SEQ value
     */
    public int getEqmSeq() {
        return eqmSeq__;
    }

    /**
     * <p>set EQM_SEQ value
     * @param eqmSeq EQM_SEQ value
     */
    public void setEqmSeq(int eqmSeq) {
        eqmSeq__ = eqmSeq;
    }

    /**
     * <p>get EQS_SEQ value
     * @return EQS_SEQ value
     */
    public int getEqsSeq() {
        return eqsSeq__;
    }

    /**
     * <p>set EQS_SEQ value
     * @param eqsSeq EQS_SEQ value
     */
    public void setEqsSeq(int eqsSeq) {
        eqsSeq__ = eqsSeq;
    }

    /**
     * <p>get EAS_ANS_TXT value
     * @return EAS_ANS_TXT value
     */
    public String getEasAnsTxt() {
        return easAnsTxt__;
    }

    /**
     * <p>set EAS_ANS_TXT value
     * @param easAnsTxt EAS_ANS_TXT value
     */
    public void setEasAnsTxt(String easAnsTxt) {
        easAnsTxt__ = easAnsTxt;
    }

    /**
     * <p>get EAS_ANS_NUM value
     * @return EAS_ANS_NUM value
     */
    public BigDecimal getEasAnsNum() {
        return easAnsNum__;
    }

    /**
     * <p>set EAS_ANS_NUM value
     * @param easAnsNum EAS_ANS_NUM value
     */
    public void setEasAnsNum(BigDecimal easAnsNum) {
        easAnsNum__ = easAnsNum;
    }

    /**
     * <p>get EAS_ANS_DAT value
     * @return EAS_ANS_DAT value
     */
    public UDate getEasAnsDat() {
        return easAnsDat__;
    }

    /**
     * <p>set EAS_ANS_DAT value
     * @param easAnsDat EAS_ANS_DAT value
     */
    public void setEasAnsDat(UDate easAnsDat) {
        easAnsDat__ = easAnsDat;
    }

    /**
     * <p>get EAS_ANS value
     * @return EAS_ANS value
     */
    public String getEasAns() {
        return easAns__;
    }

    /**
     * <p>set EAS_ANS value
     * @param easAns EAS_ANS value
     */
    public void setEasAns(String easAns) {
        easAns__ = easAns;
    }

    /**
     * <p>get EQM_AUID value
     * @return EQM_AUID value
     */
    public int getEqmAuid() {
        return eqmAuid__;
    }

    /**
     * <p>set EQM_AUID value
     * @param eqmAuid EQM_AUID value
     */
    public void setEqmAuid(int eqmAuid) {
        eqmAuid__ = eqmAuid;
    }

    /**
     * <p>get EQM_ADATE value
     * @return EQM_ADATE value
     */
    public UDate getEqmAdate() {
        return eqmAdate__;
    }

    /**
     * <p>set EQM_ADATE value
     * @param eqmAdate EQM_ADATE value
     */
    public void setEqmAdate(UDate eqmAdate) {
        eqmAdate__ = eqmAdate;
    }

    /**
     * <p>get EQM_EUID value
     * @return EQM_EUID value
     */
    public int getEqmEuid() {
        return eqmEuid__;
    }

    /**
     * <p>set EQM_EUID value
     * @param eqmEuid EQM_EUID value
     */
    public void setEqmEuid(int eqmEuid) {
        eqmEuid__ = eqmEuid;
    }

    /**
     * <p>get EQM_EDATE value
     * @return EQM_EDATE value
     */
    public UDate getEqmEdate() {
        return eqmEdate__;
    }

    /**
     * <p>set EQM_EDATE value
     * @param eqmEdate EQM_EDATE value
     */
    public void setEqmEdate(UDate eqmEdate) {
        eqmEdate__ = eqmEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(emnSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(eqmSeq__);
        buf.append(",");
        buf.append(eqsSeq__);
        buf.append(",");
        buf.append(NullDefault.getString(easAnsTxt__, ""));
        buf.append(",");
        buf.append(easAnsNum__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(easAnsDat__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(easAns__, ""));
        buf.append(",");
        buf.append(eqmAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eqmAdate__, ""));
        buf.append(",");
        buf.append(eqmEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eqmEdate__, ""));
        return buf.toString();
    }

}
