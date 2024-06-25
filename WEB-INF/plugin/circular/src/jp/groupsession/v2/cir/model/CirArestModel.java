package jp.groupsession.v2.cir.model;

import java.io.Serializable;

/**
 * <p>CIR_AREST Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirArestModel implements Serializable {

    /** GRP_SID mapping */
    private int grpSid__;
    /** USR_SID mapping */
    private int usrSid__;

    /**
     * <p>Default Constructor
     */
    public CirArestModel() {
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(grpSid__);
        buf.append(",");
        buf.append(usrSid__);
        return buf.toString();
    }

}
