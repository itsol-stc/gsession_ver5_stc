package jp.groupsession.v2.fil.model;

import java.io.Serializable;

/**
 * <p>FILE_CALL_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileCallConfModel implements Serializable {

    /** FDR_SID mapping */
    private int fdrSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** FUC_SMAIL_SEND mapping */
    private int fucSmailSend__;

    /**
     * <p>fucSmailSend を取得します。
     * @return fucSmailSend
     */
    public int getFucSmailSend() {
        return fucSmailSend__;
    }

    /**
     * <p>fucSmailSend をセットします。
     * @param fucSmailSend fucSmailSend
     */
    public void setFucSmailSend(int fucSmailSend) {
        fucSmailSend__ = fucSmailSend;
    }

    /**
     * <p>Default Constructor
     */
    public FileCallConfModel() {
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(fdrSid__);
        buf.append(",");
        buf.append(usrSid__);
        return buf.toString();
    }

}
