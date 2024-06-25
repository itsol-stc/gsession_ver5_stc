package jp.groupsession.v2.sch.model;

import java.io.Serializable;

/**
 * <p>SCH_SPACCESS_PERMIT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchSpaccessPermitModel implements Serializable {

    /** SSA_SID mapping */
    private int ssaSid__;
    /** SSP_TYPE mapping */
    private int sspType__;
    /** SSP_PSID mapping */
    private int sspPsid__;
    /** SSP_AUTH mapping */
    private int sspAuth__;

    /**
     * <p>Default Constructor
     */
    public SchSpaccessPermitModel() {
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
     * <p>get SSP_TYPE value
     * @return SSP_TYPE value
     */
    public int getSspType() {
        return sspType__;
    }

    /**
     * <p>set SSP_TYPE value
     * @param sspType SSP_TYPE value
     */
    public void setSspType(int sspType) {
        sspType__ = sspType;
    }

    /**
     * <p>get SSP_PSID value
     * @return SSP_PSID value
     */
    public int getSspPsid() {
        return sspPsid__;
    }

    /**
     * <p>set SSP_PSID value
     * @param sspPsid SSP_PSID value
     */
    public void setSspPsid(int sspPsid) {
        sspPsid__ = sspPsid;
    }

    /**
     * <p>get SSP_AUTH value
     * @return SSP_AUTH value
     */
    public int getSspAuth() {
        return sspAuth__;
    }

    /**
     * <p>set SSP_AUTH value
     * @param sspAuth SSP_AUTH value
     */
    public void setSspAuth(int sspAuth) {
        sspAuth__ = sspAuth;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ssaSid__);
        buf.append(",");
        buf.append(sspType__);
        buf.append(",");
        buf.append(sspPsid__);
        buf.append(",");
        buf.append(sspAuth__);
        return buf.toString();
    }

}
