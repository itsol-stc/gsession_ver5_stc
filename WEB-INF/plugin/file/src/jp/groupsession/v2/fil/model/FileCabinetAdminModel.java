package jp.groupsession.v2.fil.model;

import java.io.Serializable;

/**
 * <p>FILE_CABINET_ADMIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileCabinetAdminModel implements Serializable {

    /** FCB_SID mapping */
    private int fcbSid__;
    /** USR_SID mapping */
    private int usrSid__;

    /**
     * <p>Default Constructor
     */
    public FileCabinetAdminModel() {
    }

    /**
     * <p>get FCB_SID value
     * @return FCB_SID value
     */
    public int getFcbSid() {
        return fcbSid__;
    }

    /**
     * <p>set FCB_SID value
     * @param fcbSid FCB_SID value
     */
    public void setFcbSid(int fcbSid) {
        fcbSid__ = fcbSid;
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
        buf.append(fcbSid__);
        buf.append(",");
        buf.append(usrSid__);
        return buf.toString();
    }

}
