package jp.groupsession.v2.fil.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>FILE_DOWNLOAD_LOG Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class FileDownloadLogModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** FCB_SID mapping */
    private int fcbSid__;
    /** BIN_SID mapping */
    private long binSid__;
    /** FDL_DATE mapping */
    private UDate fdlDate__;

    /**
     * <p>Default Constructor
     */
    public FileDownloadLogModel() {
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
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>get FDL_DATE value
     * @return FDL_DATE value
     */
    public UDate getFdlDate() {
        return fdlDate__;
    }

    /**
     * <p>set FDL_DATE value
     * @param fdlDate FDL_DATE value
     */
    public void setFdlDate(UDate fdlDate) {
        fdlDate__ = fdlDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(fcbSid__);
        buf.append(",");
        buf.append(binSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(fdlDate__, ""));
        return buf.toString();
    }

}
