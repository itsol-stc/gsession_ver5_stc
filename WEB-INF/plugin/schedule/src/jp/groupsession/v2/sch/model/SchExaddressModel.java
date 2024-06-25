package jp.groupsession.v2.sch.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>SCH_EXADDRESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchExaddressModel implements Serializable {

    /** SCE_SID mapping */
    private int sceSid__;
    /** ADR_SID mapping */
    private int adrSid__;
    /** SEA_AUID mapping */
    private int seaAuid__;
    /** SEA_ADATE mapping */
    private UDate seaAdate__;
    /** SEA_EUID mapping */
    private int seaEuid__;
    /** SEA_EDATE mapping */
    private UDate seaEdate__;

    /**
     * <p>Default Constructor
     */
    public SchExaddressModel() {
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
     * <p>get SEA_AUID value
     * @return SEA_AUID value
     */
    public int getSeaAuid() {
        return seaAuid__;
    }

    /**
     * <p>set SEA_AUID value
     * @param seaAuid SEA_AUID value
     */
    public void setSeaAuid(int seaAuid) {
        seaAuid__ = seaAuid;
    }

    /**
     * <p>get SEA_ADATE value
     * @return SEA_ADATE value
     */
    public UDate getSeaAdate() {
        return seaAdate__;
    }

    /**
     * <p>set SEA_ADATE value
     * @param seaAdate SEA_ADATE value
     */
    public void setSeaAdate(UDate seaAdate) {
        seaAdate__ = seaAdate;
    }

    /**
     * <p>get SEA_EUID value
     * @return SEA_EUID value
     */
    public int getSeaEuid() {
        return seaEuid__;
    }

    /**
     * <p>set SEA_EUID value
     * @param seaEuid SEA_EUID value
     */
    public void setSeaEuid(int seaEuid) {
        seaEuid__ = seaEuid;
    }

    /**
     * <p>get SEA_EDATE value
     * @return SEA_EDATE value
     */
    public UDate getSeaEdate() {
        return seaEdate__;
    }

    /**
     * <p>set SEA_EDATE value
     * @param seaEdate SEA_EDATE value
     */
    public void setSeaEdate(UDate seaEdate) {
        seaEdate__ = seaEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(sceSid__);
        buf.append(",");
        buf.append(adrSid__);
        buf.append(",");
        buf.append(seaAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(seaAdate__, ""));
        buf.append(",");
        buf.append(seaEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(seaEdate__, ""));
        return buf.toString();
    }

}
