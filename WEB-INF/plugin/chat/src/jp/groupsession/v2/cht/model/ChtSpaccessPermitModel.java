package jp.groupsession.v2.cht.model;

import java.io.Serializable;

/**
 * <p>CHT_SPACCESS_PERMIT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtSpaccessPermitModel implements Serializable {

    /** CHS_SID mapping */
    private int chsSid__;
    /** CSP_TYPE mapping */
    private int cspType__;
    /** CSP_PSID mapping */
    private int cspPsid__;

    /**
     * <p>Default Constructor
     */
    public ChtSpaccessPermitModel() {
    }

    /**
     * <p>get CHS_SID value
     * @return CHS_SID value
     */
    public int getChsSid() {
        return chsSid__;
    }

    /**
     * <p>set CHS_SID value
     * @param chsSid CHS_SID value
     */
    public void setChsSid(int chsSid) {
        chsSid__ = chsSid;
    }

    /**
     * <p>get CSP_TYPE value
     * @return CSP_TYPE value
     */
    public int getCspType() {
        return cspType__;
    }

    /**
     * <p>set CSP_TYPE value
     * @param cspType CSP_TYPE value
     */
    public void setCspType(int cspType) {
        cspType__ = cspType;
    }

    /**
     * <p>get CSP_PSID value
     * @return CSP_PSID value
     */
    public int getCspPsid() {
        return cspPsid__;
    }

    /**
     * <p>set CSP_PSID value
     * @param cspPsid CSP_PSID value
     */
    public void setCspPsid(int cspPsid) {
        cspPsid__ = cspPsid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(chsSid__);
        buf.append(",");
        buf.append(cspType__);
        buf.append(",");
        buf.append(cspPsid__);
        return buf.toString();
    }

}
