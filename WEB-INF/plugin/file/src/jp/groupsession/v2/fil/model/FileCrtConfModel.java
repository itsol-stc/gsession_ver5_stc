package jp.groupsession.v2.fil.model;

import java.io.Serializable;

/**
 * <p>FILE_CRT_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileCrtConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** USR_KBN mapping */
    private int usrKbn__;

    /**
     * <p>Default Constructor
     */
    public FileCrtConfModel() {
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(usrKbn__);
        return buf.toString();
    }

}
