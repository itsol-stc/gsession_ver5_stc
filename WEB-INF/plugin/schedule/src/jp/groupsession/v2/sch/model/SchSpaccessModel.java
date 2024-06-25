package jp.groupsession.v2.sch.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>SCH_SPACCESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchSpaccessModel implements Serializable {

    /** SSA_SID mapping */
    private int ssaSid__;
    /** SSA_NAME mapping */
    private String ssaName__;
    /** SSA_BIKO mapping */
    private String ssaBiko__;
    /** SSA_AUID mapping */
    private int ssaAuid__;
    /** SSA_ADATE mapping */
    private UDate ssaAdate__;
    /** SSA_EUID mapping */
    private int ssaEuid__;
    /** SSA_EDATE mapping */
    private UDate ssaEdate__;

    /**
     * <p>Default Constructor
     */
    public SchSpaccessModel() {
    }

    /**
     * <p>get SSA_SID value
     * @return SSA_SID value
     */
    public int getSsaSid() {
        return ssaSid__;
    }

    /**
     * <p>set SSA_SID value
     * @param ssaSid SSA_SID value
     */
    public void setSsaSid(int ssaSid) {
        ssaSid__ = ssaSid;
    }

    /**
     * <p>get SSA_NAME value
     * @return SSA_NAME value
     */
    public String getSsaName() {
        return ssaName__;
    }

    /**
     * <p>set SSA_NAME value
     * @param ssaName SSA_NAME value
     */
    public void setSsaName(String ssaName) {
        ssaName__ = ssaName;
    }

    /**
     * <p>get SSA_BIKO value
     * @return SSA_BIKO value
     */
    public String getSsaBiko() {
        return ssaBiko__;
    }

    /**
     * <p>set SSA_BIKO value
     * @param ssaBiko SSA_BIKO value
     */
    public void setSsaBiko(String ssaBiko) {
        ssaBiko__ = ssaBiko;
    }

    /**
     * <p>get SSA_AUID value
     * @return SSA_AUID value
     */
    public int getSsaAuid() {
        return ssaAuid__;
    }

    /**
     * <p>set SSA_AUID value
     * @param ssaAuid SSA_AUID value
     */
    public void setSsaAuid(int ssaAuid) {
        ssaAuid__ = ssaAuid;
    }

    /**
     * <p>get SSA_ADATE value
     * @return SSA_ADATE value
     */
    public UDate getSsaAdate() {
        return ssaAdate__;
    }

    /**
     * <p>set SSA_ADATE value
     * @param ssaAdate SSA_ADATE value
     */
    public void setSsaAdate(UDate ssaAdate) {
        ssaAdate__ = ssaAdate;
    }

    /**
     * <p>get SSA_EUID value
     * @return SSA_EUID value
     */
    public int getSsaEuid() {
        return ssaEuid__;
    }

    /**
     * <p>set SSA_EUID value
     * @param ssaEuid SSA_EUID value
     */
    public void setSsaEuid(int ssaEuid) {
        ssaEuid__ = ssaEuid;
    }

    /**
     * <p>get SSA_EDATE value
     * @return SSA_EDATE value
     */
    public UDate getSsaEdate() {
        return ssaEdate__;
    }

    /**
     * <p>set SSA_EDATE value
     * @param ssaEdate SSA_EDATE value
     */
    public void setSsaEdate(UDate ssaEdate) {
        ssaEdate__ = ssaEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ssaSid__);
        buf.append(",");
        buf.append(NullDefault.getString(ssaName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(ssaBiko__, ""));
        buf.append(",");
        buf.append(ssaAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ssaAdate__, ""));
        buf.append(",");
        buf.append(ssaEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ssaEdate__, ""));
        return buf.toString();
    }

}
