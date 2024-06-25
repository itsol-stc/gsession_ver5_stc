package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <p>NTP_SHOHIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpShohinModel extends AbstractModel  {

    /** NHN_SID mapping */
    private int nhnSid__;
    /** NHN_CODE mapping */
    private String nhnCode__;
    /** NHN_NAME mapping */
    private String nhnName__;
    /** NHN_PRICE_SALE mapping */
    private int nhnPriceSale__;
    /** NHN_PRICE_COST mapping */
    private int nhnPriceCost__;
    /** NHN_HOSOKU mapping */
    private String nhnHosoku__;
    /** NHN_AUID mapping */
    private int nhnAuid__;
    /** NHN_ADATE mapping */
    private UDate nhnAdate__;
    /** NHN_EUID mapping */
    private int nhnEuid__;
    /** NHN_EDATE mapping */
    private UDate nhnEdate__;
    /** NSC_SID mapping */
    private int nscSid__;

    /**
     * <p>Default Constructor
     */
    public NtpShohinModel() {
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
     * <p>get NHN_CODE value
     * @return NHN_CODE value
     */
    public String getNhnCode() {
        return nhnCode__;
    }

    /**
     * <p>set NHN_CODE value
     * @param nhnCode NHN_CODE value
     */
    public void setNhnCode(String nhnCode) {
        nhnCode__ = nhnCode;
    }

    /**
     * <p>get NHN_NAME value
     * @return NHN_NAME value
     */
    public String getNhnName() {
        return nhnName__;
    }

    /**
     * <p>set NHN_NAME value
     * @param nhnName NHN_NAME value
     */
    public void setNhnName(String nhnName) {
        nhnName__ = nhnName;
    }

    /**
     * <p>get NHN_PRICE_SALE value
     * @return NHN_PRICE_SALE value
     */
    public int getNhnPriceSale() {
        return nhnPriceSale__;
    }

    /**
     * <p>set NHN_PRICE_SALE value
     * @param nhnPriceSale NHN_PRICE_SALE value
     */
    public void setNhnPriceSale(int nhnPriceSale) {
        nhnPriceSale__ = nhnPriceSale;
    }

    /**
     * <p>get NHN_PRICE_COST value
     * @return NHN_PRICE_COST value
     */
    public int getNhnPriceCost() {
        return nhnPriceCost__;
    }

    /**
     * <p>set NHN_PRICE_COST value
     * @param nhnPriceCost NHN_PRICE_COST value
     */
    public void setNhnPriceCost(int nhnPriceCost) {
        nhnPriceCost__ = nhnPriceCost;
    }

    /**
     * <p>get NHN_HOSOKU value
     * @return NHN_HOSOKU value
     */
    public String getNhnHosoku() {
        return nhnHosoku__;
    }

    /**
     * <p>set NHN_HOSOKU value
     * @param nhnHosoku NHN_HOSOKU value
     */
    public void setNhnHosoku(String nhnHosoku) {
        nhnHosoku__ = nhnHosoku;
    }

    /**
     * <p>get NHN_AUID value
     * @return NHN_AUID value
     */
    public int getNhnAuid() {
        return nhnAuid__;
    }

    /**
     * <p>set NHN_AUID value
     * @param nhnAuid NHN_AUID value
     */
    public void setNhnAuid(int nhnAuid) {
        nhnAuid__ = nhnAuid;
    }

    /**
     * <p>get NHN_ADATE value
     * @return NHN_ADATE value
     */
    public UDate getNhnAdate() {
        return nhnAdate__;
    }

    /**
     * <p>set NHN_ADATE value
     * @param nhnAdate NHN_ADATE value
     */
    public void setNhnAdate(UDate nhnAdate) {
        nhnAdate__ = nhnAdate;
    }

    /**
     * <p>get NHN_EUID value
     * @return NHN_EUID value
     */
    public int getNhnEuid() {
        return nhnEuid__;
    }

    /**
     * <p>set NHN_EUID value
     * @param nhnEuid NHN_EUID value
     */
    public void setNhnEuid(int nhnEuid) {
        nhnEuid__ = nhnEuid;
    }

    /**
     * <p>get NHN_EDATE value
     * @return NHN_EDATE value
     */
    public UDate getNhnEdate() {
        return nhnEdate__;
    }

    /**
     * <p>set NHN_EDATE value
     * @param nhnEdate NHN_EDATE value
     */
    public void setNhnEdate(UDate nhnEdate) {
        nhnEdate__ = nhnEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nhnSid__);
        buf.append(",");
        buf.append(nhnCode__);
        buf.append(",");
        buf.append(NullDefault.getString(nhnName__, ""));
        buf.append(",");
        buf.append(nhnPriceSale__);
        buf.append(",");
        buf.append(nhnPriceCost__);
        buf.append(",");
        buf.append(NullDefault.getString(nhnHosoku__, ""));
        buf.append(",");
        buf.append(nhnAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nhnAdate__, ""));
        buf.append(",");
        buf.append(nhnEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nhnEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>nscSid を取得します。
     * @return nscSid
     */
    public int getNscSid() {
        return nscSid__;
    }

    /**
     * <p>nscSid をセットします。
     * @param nscSid nscSid
     */
    public void setNscSid(int nscSid) {
        nscSid__ = nscSid;
    }

}
