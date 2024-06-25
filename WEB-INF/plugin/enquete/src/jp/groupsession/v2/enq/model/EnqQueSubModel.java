package jp.groupsession.v2.enq.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>ENQ_QUE_SUB Data Binding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqQueSubModel implements Serializable {

    /** EMN_SID mapping */
    private long emnSid__;
    /** EQM_SEQ mapping */
    private int eqmSeq__;
    /** EQS_SEQ mapping */
    private int eqsSeq__;
    /** EQS_DSP_SEC mapping */
    private int eqsDspSec__;
    /** EQS_DSP_NAME mapping */
    private String eqsDspName__;
    /** EQS_DEF_TXT mapping */
    private String eqsDefTxt__;
    /** EQS_DEF_NUM mapping */
    private BigDecimal eqsDefNum__;
    /** EQS_DEF_DAT mapping */
    private UDate eqsDefDat__;
    /** EQS_DEF mapping */
    private String eqsDef__;
    /** EQS_RNG_STR_NUM mapping */
    private BigDecimal eqsRngStrNum__;
    /** EQS_RNG_END_NUM mapping */
    private BigDecimal eqsRngEndNum__;
    /** EQS_RNG_STR_DAT mapping */
    private UDate eqsRngStrDat__;
    /** EQS_RNG_END_DAT mapping */
    private UDate eqsRngEndDat__;
    /** EQS_UNIT_NUM mapping */
    private String eqsUnitNum__;
    /** EQS_AUID mapping */
    private int eqsAuid__;
    /** EQS_ADATE mapping */
    private UDate eqsAdate__;
    /** EQS_EUID mapping */
    private int eqsEuid__;
    /** EQS_EDATE mapping */
    private UDate eqsEdate__;

    /**
     * <p>Default Constructor
     */
    public EnqQueSubModel() {
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
     * <p>get EQS_DSP_SEC value
     * @return EQS_DSP_SEC value
     */
    public int getEqsDspSec() {
        return eqsDspSec__;
    }

    /**
     * <p>set EQS_DSP_SEC value
     * @param eqsDspSec EQS_DSP_SEC value
     */
    public void setEqsDspSec(int eqsDspSec) {
        eqsDspSec__ = eqsDspSec;
    }

    /**
     * <p>get EQS_DSP_NAME value
     * @return EQS_DSP_NAME value
     */
    public String getEqsDspName() {
        return eqsDspName__;
    }

    /**
     * <p>set EQS_DSP_NAME value
     * @param eqsDspName EQS_DSP_NAME value
     */
    public void setEqsDspName(String eqsDspName) {
        eqsDspName__ = eqsDspName;
    }

    /**
     * <p>get EQS_DEF_TXT value
     * @return EQS_DEF_TXT value
     */
    public String getEqsDefTxt() {
        return eqsDefTxt__;
    }

    /**
     * <p>set EQS_DEF_TXT value
     * @param eqsDefTxt EQS_DEF_TXT value
     */
    public void setEqsDefTxt(String eqsDefTxt) {
        eqsDefTxt__ = eqsDefTxt;
    }

    /**
     * <p>get EQS_DEF_NUM value
     * @return EQS_DEF_NUM value
     */
    public BigDecimal getEqsDefNum() {
        return eqsDefNum__;
    }

    /**
     * <p>set EQS_DEF_NUM value
     * @param eqsDefNum EQS_DEF_NUM value
     */
    public void setEqsDefNum(BigDecimal eqsDefNum) {
        eqsDefNum__ = eqsDefNum;
    }

    /**
     * <p>get EQS_DEF_DAT value
     * @return EQS_DEF_DAT value
     */
    public UDate getEqsDefDat() {
        return eqsDefDat__;
    }

    /**
     * <p>set EQS_DEF_DAT value
     * @param eqsDefDat EQS_DEF_DAT value
     */
    public void setEqsDefDat(UDate eqsDefDat) {
        eqsDefDat__ = eqsDefDat;
    }

    /**
     * <p>get EQS_DEF value
     * @return EQS_DEF value
     */
    public String getEqsDef() {
        return eqsDef__;
    }

    /**
     * <p>set EQS_DEF value
     * @param eqsDef EQS_DEF value
     */
    public void setEqsDef(String eqsDef) {
        eqsDef__ = eqsDef;
    }

    /**
     * <p>get EQS_RNG_STR_NUM value
     * @return EQS_RNG_STR_NUM value
     */
    public BigDecimal getEqsRngStrNum() {
        return eqsRngStrNum__;
    }

    /**
     * <p>set EQS_RNG_STR_NUM value
     * @param eqsRngStrNum EQS_RNG_STR_NUM value
     */
    public void setEqsRngStrNum(BigDecimal eqsRngStrNum) {
        eqsRngStrNum__ = eqsRngStrNum;
    }

    /**
     * <p>get EQS_RNG_END_NUM value
     * @return EQS_RNG_END_NUM value
     */
    public BigDecimal getEqsRngEndNum() {
        return eqsRngEndNum__;
    }

    /**
     * <p>set EQS_RNG_END_NUM value
     * @param eqsRngEndNum EQS_RNG_END_NUM value
     */
    public void setEqsRngEndNum(BigDecimal eqsRngEndNum) {
        eqsRngEndNum__ = eqsRngEndNum;
    }

    /**
     * <p>get EQS_RNG_STR_DAT value
     * @return EQS_RNG_STR_DAT value
     */
    public UDate getEqsRngStrDat() {
        return eqsRngStrDat__;
    }

    /**
     * <p>set EQS_RNG_STR_DAT value
     * @param eqsRngStrDat EQS_RNG_STR_DAT value
     */
    public void setEqsRngStrDat(UDate eqsRngStrDat) {
        eqsRngStrDat__ = eqsRngStrDat;
    }

    /**
     * <p>get EQS_RNG_END_DAT value
     * @return EQS_RNG_END_DAT value
     */
    public UDate getEqsRngEndDat() {
        return eqsRngEndDat__;
    }

    /**
     * <p>set EQS_RNG_END_DAT value
     * @param eqsRngEndDat EQS_RNG_END_DAT value
     */
    public void setEqsRngEndDat(UDate eqsRngEndDat) {
        eqsRngEndDat__ = eqsRngEndDat;
    }

    /**
     * <p>get EQS_UNIT_NUM value
     * @return EQS_UNIT_NUM value
     */
    public String getEqsUnitNum() {
        return eqsUnitNum__;
    }

    /**
     * <p>set EQS_UNIT_NUM value
     * @param eqsUnitNum EQS_UNIT_NUM value
     */
    public void setEqsUnitNum(String eqsUnitNum) {
        eqsUnitNum__ = eqsUnitNum;
    }

    /**
     * <p>get EQS_AUID value
     * @return EQS_AUID value
     */
    public int getEqsAuid() {
        return eqsAuid__;
    }

    /**
     * <p>set EQS_AUID value
     * @param eqsAuid EQS_AUID value
     */
    public void setEqsAuid(int eqsAuid) {
        eqsAuid__ = eqsAuid;
    }

    /**
     * <p>get EQS_ADATE value
     * @return EQS_ADATE value
     */
    public UDate getEqsAdate() {
        return eqsAdate__;
    }

    /**
     * <p>set EQS_ADATE value
     * @param eqsAdate EQS_ADATE value
     */
    public void setEqsAdate(UDate eqsAdate) {
        eqsAdate__ = eqsAdate;
    }

    /**
     * <p>get EQS_EUID value
     * @return EQS_EUID value
     */
    public int getEqsEuid() {
        return eqsEuid__;
    }

    /**
     * <p>set EQS_EUID value
     * @param eqsEuid EQS_EUID value
     */
    public void setEqsEuid(int eqsEuid) {
        eqsEuid__ = eqsEuid;
    }

    /**
     * <p>get EQS_EDATE value
     * @return EQS_EDATE value
     */
    public UDate getEqsEdate() {
        return eqsEdate__;
    }

    /**
     * <p>set EQS_EDATE value
     * @param eqsEdate EQS_EDATE value
     */
    public void setEqsEdate(UDate eqsEdate) {
        eqsEdate__ = eqsEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(emnSid__);
        buf.append(",");
        buf.append(eqmSeq__);
        buf.append(",");
        buf.append(eqsSeq__);
        buf.append(",");
        buf.append(eqsDspSec__);
        buf.append(",");
        buf.append(NullDefault.getString(eqsDspName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(eqsDefTxt__, ""));
        buf.append(",");
        buf.append(eqsDefNum__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eqsDefDat__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(eqsDef__, ""));
        buf.append(",");
        buf.append(eqsRngStrNum__);
        buf.append(",");
        buf.append(eqsRngEndNum__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eqsRngStrDat__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eqsRngEndDat__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(eqsUnitNum__, ""));
        buf.append(",");
        buf.append(eqsAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eqsAdate__, ""));
        buf.append(",");
        buf.append(eqsEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eqsEdate__, ""));
        return buf.toString();
    }

}
