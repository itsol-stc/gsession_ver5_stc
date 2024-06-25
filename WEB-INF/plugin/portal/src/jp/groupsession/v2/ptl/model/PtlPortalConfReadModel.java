package jp.groupsession.v2.ptl.model;

import java.io.Serializable;

/**
 * <p>PTL_PORTAL_CONF_READ Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortalConfReadModel implements Serializable {

    /** PTL_SID mapping */
    private int ptlSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** GRP_SID mapping */
    private int grpSid__;

    /**
     * <p>Default Constructor
     */
    public PtlPortalConfReadModel() {
    }

    /**
     * <p>get PTL_SID value
     * @return PTL_SID value
     */
    public int getPtlSid() {
        return ptlSid__;
    }

    /**
     * <p>set PTL_SID value
     * @param ptlSid PTL_SID value
     */
    public void setPtlSid(int ptlSid) {
        ptlSid__ = ptlSid;
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ptlSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        return buf.toString();
    }

}
