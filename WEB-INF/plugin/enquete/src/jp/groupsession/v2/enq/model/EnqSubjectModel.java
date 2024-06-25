package jp.groupsession.v2.enq.model;

import java.io.Serializable;

/**
 * <p>ENQ_SUBJECT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class EnqSubjectModel implements Serializable {

    /** EMN_SID mapping */
    private long emnSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** GRP_SID mapping */
    private int grpSid__;

    /**
     * <p>Default Constructor
     */
    public EnqSubjectModel() {
    }

    /**
     * <p>get EMN_SID value
     * @return EMN_SID value
     */
    public long getEmnSid() {
        return emnSid__;
    }

    /**
     * <p>set EMN_SID value
     * @param emnSid EMN_SID value
     */
    public void setEmnSid(long emnSid) {
        emnSid__ = emnSid;
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
        buf.append(emnSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        return buf.toString();
    }

}
