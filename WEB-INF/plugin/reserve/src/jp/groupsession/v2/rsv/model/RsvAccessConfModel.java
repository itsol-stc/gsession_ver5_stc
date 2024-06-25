package jp.groupsession.v2.rsv.model;

import java.io.Serializable;

/**
 * <p>RSV_ACCESS_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvAccessConfModel implements Serializable {

    /** RSG_SID mapping */
    private int rsgSid__;
    /** RSD_SID mapping */
    private int rsdSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** RAC_AUTH mapping */
    private int racAuth__;

    /**
     * <p>Default Constructor
     */
    public RsvAccessConfModel() {
    }

    /**
     * <p>get RSG_SID value
     * @return RSG_SID value
     */
    public int getRsgSid() {
        return rsgSid__;
    }

    /**
     * <p>set RSG_SID value
     * @param rsgSid RSG_SID value
     */
    public void setRsgSid(int rsgSid) {
        rsgSid__ = rsgSid;
    }

    /**
     * <p>get RSD_SID value
     * @return RSD_SID value
     */
    public int getRsdSid() {
        return rsdSid__;
    }

    /**
     * <p>set RSD_SID value
     * @param rsdSid RSD_SID value
     */
    public void setRsdSid(int rsdSid) {
        rsdSid__ = rsdSid;
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
     * <p>get RAC_AUTH value
     * @return RAC_AUTH value
     */
    public int getRacAuth() {
        return racAuth__;
    }

    /**
     * <p>set RAC_AUTH value
     * @param racAuth RAC_AUTH value
     */
    public void setRacAuth(int racAuth) {
        racAuth__ = racAuth;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rsgSid__);
        buf.append(",");
        buf.append(rsdSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(racAuth__);
        return buf.toString();
    }

}
