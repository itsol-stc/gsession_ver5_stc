package jp.groupsession.v2.cht.model;

import java.io.Serializable;

/**
 * <p>CHT_GROUP_TARGET Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtGroupTargetModel implements Serializable {

    /** CGT_TYPE mapping */
    private int cgtType__;
    /** CGT_SSID mapping */
    private int cgtSsid__;

    /**
     * <p>Default Constructor
     */
    public ChtGroupTargetModel() {
    }

    /**
     * <p>get CGT_TYPE value
     * @return CGT_TYPE value
     */
    public int getCgtType() {
        return cgtType__;
    }

    /**
     * <p>set CGT_TYPE value
     * @param cgtType CGT_TYPE value
     */
    public void setCgtType(int cgtType) {
        cgtType__ = cgtType;
    }

    /**
     * <p>get CGT_SSID value
     * @return CGT_SSID value
     */
    public int getCgtSsid() {
        return cgtSsid__;
    }

    /**
     * <p>set CGT_SSID value
     * @param cgtSsid CGT_SSID value
     */
    public void setCgtSsid(int cgtSsid) {
        cgtSsid__ = cgtSsid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cgtType__);
        buf.append(",");
        buf.append(cgtSsid__);
        return buf.toString();
    }

}
