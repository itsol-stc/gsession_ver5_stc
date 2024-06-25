package jp.groupsession.v2.sch.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>SCH_EXCOMPANY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchExcompanyModel implements Serializable {

    /** SCE_SID mapping */
    private int sceSid__;
    /** ACO_SID mapping */
    private int acoSid__;
    /** ABA_SID mapping */
    private int abaSid__;
    /** SCC_AUID mapping */
    private int sccAuid__;
    /** SCC_ADATE mapping */
    private UDate sccAdate__;
    /** SCC_EUID mapping */
    private int sccEuid__;
    /** SCC_EDATE mapping */
    private UDate sccEdate__;

    /**
     * <p>Default Constructor
     */
    public SchExcompanyModel() {
    }

    /**
     * <p>get SCE_SID value
     * @return SCE_SID value
     */
    public int getSceSid() {
        return sceSid__;
    }

    /**
     * <p>set SCE_SID value
     * @param sceSid SCE_SID value
     */
    public void setSceSid(int sceSid) {
        sceSid__ = sceSid;
    }

    /**
     * <p>get ACO_SID value
     * @return ACO_SID value
     */
    public int getAcoSid() {
        return acoSid__;
    }

    /**
     * <p>set ACO_SID value
     * @param acoSid ACO_SID value
     */
    public void setAcoSid(int acoSid) {
        acoSid__ = acoSid;
    }

    /**
     * <p>get ABA_SID value
     * @return ABA_SID value
     */
    public int getAbaSid() {
        return abaSid__;
    }

    /**
     * <p>set ABA_SID value
     * @param abaSid ABA_SID value
     */
    public void setAbaSid(int abaSid) {
        abaSid__ = abaSid;
    }

    /**
     * <p>get SCC_AUID value
     * @return SCC_AUID value
     */
    public int getSccAuid() {
        return sccAuid__;
    }

    /**
     * <p>set SCC_AUID value
     * @param sccAuid SCC_AUID value
     */
    public void setSccAuid(int sccAuid) {
        sccAuid__ = sccAuid;
    }

    /**
     * <p>get SCC_ADATE value
     * @return SCC_ADATE value
     */
    public UDate getSccAdate() {
        return sccAdate__;
    }

    /**
     * <p>set SCC_ADATE value
     * @param sccAdate SCC_ADATE value
     */
    public void setSccAdate(UDate sccAdate) {
        sccAdate__ = sccAdate;
    }

    /**
     * <p>get SCC_EUID value
     * @return SCC_EUID value
     */
    public int getSccEuid() {
        return sccEuid__;
    }

    /**
     * <p>set SCC_EUID value
     * @param sccEuid SCC_EUID value
     */
    public void setSccEuid(int sccEuid) {
        sccEuid__ = sccEuid;
    }

    /**
     * <p>get SCC_EDATE value
     * @return SCC_EDATE value
     */
    public UDate getSccEdate() {
        return sccEdate__;
    }

    /**
     * <p>set SCC_EDATE value
     * @param sccEdate SCC_EDATE value
     */
    public void setSccEdate(UDate sccEdate) {
        sccEdate__ = sccEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(sceSid__);
        buf.append(",");
        buf.append(acoSid__);
        buf.append(",");
        buf.append(abaSid__);
        buf.append(",");
        buf.append(sccAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sccAdate__, ""));
        buf.append(",");
        buf.append(sccEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sccEdate__, ""));
        return buf.toString();
    }

}
