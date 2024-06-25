package jp.groupsession.v2.fil.model;

import java.io.Serializable;

/**
 * <p>FILE_ACCESS_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileAccessConfModel implements Serializable {

    /** FCB_SID mapping */
    private int fcbSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** USR_KBN mapping */
    private int usrKbn__;
    /** FAA_AUTH mapping */
    private int faaAuth__;

    /**
     * <p>Default Constructor
     */
    public FileAccessConfModel() {
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
     * <p>get USR_KBN value
     * @return USR_KBN value
     */
    public int getUsrKbn() {
        return usrKbn__;
    }

    /**
     * <p>set USR_KBN value
     * @param usrKbn USR_KBN value
     */
    public void setUsrKbn(int usrKbn) {
        usrKbn__ = usrKbn;
    }

    /**
     * <p>get FAA_AUTH value
     * @return FAA_AUTH value
     */
    public int getFaaAuth() {
        return faaAuth__;
    }

    /**
     * <p>set FAA_AUTH value
     * @param faaAuth FAA_AUTH value
     */
    public void setFaaAuth(int faaAuth) {
        faaAuth__ = faaAuth;
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
        buf.append(",");
        buf.append(usrKbn__);
        buf.append(",");
        buf.append(faaAuth__);
        return buf.toString();
    }

}
