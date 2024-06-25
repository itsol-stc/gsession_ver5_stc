package jp.groupsession.v2.prj.model;

import java.io.Serializable;

/**
 * <p>PRJ_TODO_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodoBinModel implements Serializable {

    /** PRJ_SID mapping */
    private int prjSid__;
    /** PTD_SID mapping */
    private int ptdSid__;
    /** BIN_SID mapping */
    private Long binSid__ = new Long(0);
    /** USR_SID mapping */
    private int usrSid__;

    /**
     * <p>Default Constructor
     */
    public PrjTodoBinModel() {
    }

    /**
     * <p>get PRJ_SID value
     * @return PRJ_SID value
     */
    public int getPrjSid() {
        return prjSid__;
    }

    /**
     * <p>set PRJ_SID value
     * @param prjSid PRJ_SID value
     */
    public void setPrjSid(int prjSid) {
        prjSid__ = prjSid;
    }

    /**
     * <p>get PTD_SID value
     * @return PTD_SID value
     */
    public int getPtdSid() {
        return ptdSid__;
    }

    /**
     * <p>set PTD_SID value
     * @param ptdSid PTD_SID value
     */
    public void setPtdSid(int ptdSid) {
        ptdSid__ = ptdSid;
    }

    /**
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public Long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
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
        StringBuilder buf = new StringBuilder();
        buf.append(prjSid__);
        buf.append(",");
        buf.append(ptdSid__);
        buf.append(",");
        buf.append(binSid__);
        buf.append(",");
        buf.append(usrSid__);
        return buf.toString();
    }

}
