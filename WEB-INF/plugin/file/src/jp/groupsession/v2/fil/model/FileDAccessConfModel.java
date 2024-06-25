package jp.groupsession.v2.fil.model;

import java.io.Serializable;

/**
 * <p>FILE_DACCESS_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileDAccessConfModel implements Serializable {

    /** FDR_SID mapping */
    private int fdrSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** USR_KBN mapping */
    private int usrKbn__;
    /** FDA_AUTH mapping */
    private int fdaAuth__;

    /**
     * <p>Default Constructor
     */
    public FileDAccessConfModel() {
    }

    /**
     * <p>get FDR_SID value
     * @return FDR_SID value
     */
    public int getFdrSid() {
        return fdrSid__;
    }

    /**
     * <p>set FDR_SID value
     * @param fdrSid FDR_SID value
     */
    public void setFdrSid(int fdrSid) {
        fdrSid__ = fdrSid;
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
     * <p>get FDA_AUTH value
     * @return FDA_AUTH value
     */
    public int getFdaAuth() {
        return fdaAuth__;
    }

    /**
     * <p>set FDA_AUTH value
     * @param fdaAuth FDA_AUTH value
     */
    public void setFdaAuth(int fdaAuth) {
        fdaAuth__ = fdaAuth;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(fdrSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(usrKbn__);
        buf.append(",");
        buf.append(fdaAuth__);
        return buf.toString();
    }

}
