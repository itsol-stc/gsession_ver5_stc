package jp.groupsession.v2.fil.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>FILE_SHORTCUT_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileShortcutConfModel implements Serializable {

    /** FDR_SID mapping */
    private int fdrSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** FSC_ADATE mapping */
    private UDate fscAdate__;

    /**
     * <p>Default Constructor
     */
    public FileShortcutConfModel() {
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
     * <p>get FSC_ADATE value
     * @return FSC_ADATE value
     */
    public UDate getFscAdate() {
        return fscAdate__;
    }

    /**
     * <p>set FSC_ADATE value
     * @param fscAdate FSC_ADATE value
     */
    public void setFscAdate(UDate fscAdate) {
        fscAdate__ = fscAdate;
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
        buf.append(NullDefault.getStringFmObj(fscAdate__, ""));
        return buf.toString();
    }

}
