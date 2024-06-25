package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_NI_SHOHIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpNiShohinModel implements Serializable {

    /** NSH_SID mapping */
    private int nshSid__;
    /** NIP_SID mapping */
    private int nipSid__;
    /** MHN_SID mapping */
    private int mhnSid__;
    /** NSH_NAME mapping */
    private String nshName__;
    /** NSH_PRICE_SALE mapping */
    private int nshPriceSale__;
    /** NSH_PRICE_COST mapping */
    private int nshPriceCost__;
    /** NSH_AUID mapping */
    private int nshAuid__;
    /** NSH_ADATE mapping */
    private UDate nshAdate__;
    /** NSH_EUID mapping */
    private int nshEuid__;
    /** NSH_EDATE mapping */
    private UDate nshEdate__;

    /**
     * <p>Default Constructor
     */
    public NtpNiShohinModel() {
    }

    /**
     * <p>get NSH_SID value
     * @return NSH_SID value
     */
    public int getNshSid() {
        return nshSid__;
    }

    /**
     * <p>set NSH_SID value
     * @param nshSid NSH_SID value
     */
    public void setNshSid(int nshSid) {
        nshSid__ = nshSid;
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
     * <p>get MHN_SID value
     * @return MHN_SID value
     */
    public int getMhnSid() {
        return mhnSid__;
    }

    /**
     * <p>set MHN_SID value
     * @param mhnSid MHN_SID value
     */
    public void setMhnSid(int mhnSid) {
        mhnSid__ = mhnSid;
    }

    /**
     * <p>get NSH_NAME value
     * @return NSH_NAME value
     */
    public String getNshName() {
        return nshName__;
    }

    /**
     * <p>set NSH_NAME value
     * @param nshName NSH_NAME value
     */
    public void setNshName(String nshName) {
        nshName__ = nshName;
    }

    /**
     * <p>get NSH_PRICE_SALE value
     * @return NSH_PRICE_SALE value
     */
    public int getNshPriceSale() {
        return nshPriceSale__;
    }

    /**
     * <p>set NSH_PRICE_SALE value
     * @param nshPriceSale NSH_PRICE_SALE value
     */
    public void setNshPriceSale(int nshPriceSale) {
        nshPriceSale__ = nshPriceSale;
    }

    /**
     * <p>get NSH_PRICE_COST value
     * @return NSH_PRICE_COST value
     */
    public int getNshPriceCost() {
        return nshPriceCost__;
    }

    /**
     * <p>set NSH_PRICE_COST value
     * @param nshPriceCost NSH_PRICE_COST value
     */
    public void setNshPriceCost(int nshPriceCost) {
        nshPriceCost__ = nshPriceCost;
    }

    /**
     * <p>get NSH_AUID value
     * @return NSH_AUID value
     */
    public int getNshAuid() {
        return nshAuid__;
    }

    /**
     * <p>set NSH_AUID value
     * @param nshAuid NSH_AUID value
     */
    public void setNshAuid(int nshAuid) {
        nshAuid__ = nshAuid;
    }

    /**
     * <p>get NSH_ADATE value
     * @return NSH_ADATE value
     */
    public UDate getNshAdate() {
        return nshAdate__;
    }

    /**
     * <p>set NSH_ADATE value
     * @param nshAdate NSH_ADATE value
     */
    public void setNshAdate(UDate nshAdate) {
        nshAdate__ = nshAdate;
    }

    /**
     * <p>get NSH_EUID value
     * @return NSH_EUID value
     */
    public int getNshEuid() {
        return nshEuid__;
    }

    /**
     * <p>set NSH_EUID value
     * @param nshEuid NSH_EUID value
     */
    public void setNshEuid(int nshEuid) {
        nshEuid__ = nshEuid;
    }

    /**
     * <p>get NSH_EDATE value
     * @return NSH_EDATE value
     */
    public UDate getNshEdate() {
        return nshEdate__;
    }

    /**
     * <p>set NSH_EDATE value
     * @param nshEdate NSH_EDATE value
     */
    public void setNshEdate(UDate nshEdate) {
        nshEdate__ = nshEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nshSid__);
        buf.append(",");
        buf.append(nipSid__);
        buf.append(",");
        buf.append(mhnSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nshName__, ""));
        buf.append(",");
        buf.append(nshPriceSale__);
        buf.append(",");
        buf.append(nshPriceCost__);
        buf.append(",");
        buf.append(nshAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nshAdate__, ""));
        buf.append(",");
        buf.append(nshEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nshEdate__, ""));
        return buf.toString();
    }

}
