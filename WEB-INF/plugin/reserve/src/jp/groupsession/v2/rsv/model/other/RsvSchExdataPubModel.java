package jp.groupsession.v2.rsv.model.other;

import java.io.Serializable;

/**
 * <p>SCH_EXDATA_PUB Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvSchExdataPubModel implements Serializable {

    /** SCE_SID mapping */
    private int sceSid__;
    /** SEP_TYPE mapping */
    private int sepType__;
    /** SEP_PSID mapping */
    private int sepPsid__;

    /**
     * <p>Default Constructor
     */
    public RsvSchExdataPubModel() {
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
     * <p>get SEP_TYPE value
     * @return SEP_TYPE value
     */
    public int getSepType() {
        return sepType__;
    }

    /**
     * <p>set SEP_TYPE value
     * @param sepType SEP_TYPE value
     */
    public void setSepType(int sepType) {
        sepType__ = sepType;
    }

    /**
     * <p>get SEP_PSID value
     * @return SEP_PSID value
     */
    public int getSepPsid() {
        return sepPsid__;
    }

    /**
     * <p>set SEP_PSID value
     * @param sepPsid SEP_PSID value
     */
    public void setSepPsid(int sepPsid) {
        sepPsid__ = sepPsid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sceSid__);
        buf.append(",");
        buf.append(sepType__);
        buf.append(",");
        buf.append(sepPsid__);
        return buf.toString();
    }

}
