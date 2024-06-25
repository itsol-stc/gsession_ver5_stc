package jp.groupsession.v2.sch.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>SCH_ADDRESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchAddressModel implements Serializable {

    /** SCD_SID mapping */
    private int scdSid__;
    /** ADR_SID mapping */
    private int adrSid__;
    /** ADC_SID mapping */
    private int adcSid__;
    /** SCA_AUID mapping */
    private int scaAuid__;
    /** SCA_ADATE mapping */
    private UDate scaAdate__;
    /** SCA_EUID mapping */
    private int scaEuid__;
    /** SCA_EDATE mapping */
    private UDate scaEdate__;

    /**
     * <p>Default Constructor
     */
    public SchAddressModel() {
    }

    /**
     * <p>get SCD_SID value
     * @return SCD_SID value
     */
    public int getScdSid() {
        return scdSid__;
    }

    /**
     * <p>set SCD_SID value
     * @param scdSid SCD_SID value
     */
    public void setScdSid(int scdSid) {
        scdSid__ = scdSid;
    }

    /**
     * <p>get ADR_SID value
     * @return ADR_SID value
     */
    public int getAdrSid() {
        return adrSid__;
    }

    /**
     * <p>set ADR_SID value
     * @param adrSid ADR_SID value
     */
    public void setAdrSid(int adrSid) {
        adrSid__ = adrSid;
    }

    /**
     * <p>get ADC_SID value
     * @return ADC_SID value
     */
    public int getAdcSid() {
        return adcSid__;
    }

    /**
     * <p>set ADC_SID value
     * @param adcSid ADC_SID value
     */
    public void setAdcSid(int adcSid) {
        adcSid__ = adcSid;
    }

    /**
     * <p>get SCA_AUID value
     * @return SCA_AUID value
     */
    public int getScaAuid() {
        return scaAuid__;
    }

    /**
     * <p>set SCA_AUID value
     * @param scaAuid SCA_AUID value
     */
    public void setScaAuid(int scaAuid) {
        scaAuid__ = scaAuid;
    }

    /**
     * <p>get SCA_ADATE value
     * @return SCA_ADATE value
     */
    public UDate getScaAdate() {
        return scaAdate__;
    }

    /**
     * <p>set SCA_ADATE value
     * @param scaAdate SCA_ADATE value
     */
    public void setScaAdate(UDate scaAdate) {
        scaAdate__ = scaAdate;
    }

    /**
     * <p>get SCA_EUID value
     * @return SCA_EUID value
     */
    public int getScaEuid() {
        return scaEuid__;
    }

    /**
     * <p>set SCA_EUID value
     * @param scaEuid SCA_EUID value
     */
    public void setScaEuid(int scaEuid) {
        scaEuid__ = scaEuid;
    }

    /**
     * <p>get SCA_EDATE value
     * @return SCA_EDATE value
     */
    public UDate getScaEdate() {
        return scaEdate__;
    }

    /**
     * <p>set SCA_EDATE value
     * @param scaEdate SCA_EDATE value
     */
    public void setScaEdate(UDate scaEdate) {
        scaEdate__ = scaEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(scdSid__);
        buf.append(",");
        buf.append(adrSid__);
        buf.append(",");
        buf.append(adcSid__);
        buf.append(",");
        buf.append(scaAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(scaAdate__, ""));
        buf.append(",");
        buf.append(scaEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(scaEdate__, ""));
        return buf.toString();
    }

}
