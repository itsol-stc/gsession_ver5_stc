package jp.groupsession.v2.sch.model;

import java.io.Serializable;

/**
 * <p>SCH_MYVIEWLIST_BELONG Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchMyviewlistBelongModel implements Serializable {

    /** SMY_SID mapping */
    private int smySid__;
    /** USR_SID mapping */
    private int usrSid__ = -1;
    /** GRP_SID mapping */
    private int grpSid__ = -1;
    /** SMV_ORDER mapping */
    private int smvOrder__;

    /**
     * <p>Default Constructor
     */
    public SchMyviewlistBelongModel() {
    }

    /**
     * <p>get SMY_SID value
     * @return SMY_SID value
     */
    public int getSmySid() {
        return smySid__;
    }

    /**
     * <p>set SMY_SID value
     * @param smySid SMY_SID value
     */
    public void setSmySid(int smySid) {
        smySid__ = smySid;
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
     * <p>get SMV_ORDER value
     * @return SMV_ORDER value
     */
    public int getSmvOrder() {
        return smvOrder__;
    }

    /**
     * <p>set SMV_ORDER value
     * @param smvOrder SMV_ORDER value
     */
    public void setSmvOrder(int smvOrder) {
        smvOrder__ = smvOrder;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(smySid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(smvOrder__);
        return buf.toString();
    }

}
