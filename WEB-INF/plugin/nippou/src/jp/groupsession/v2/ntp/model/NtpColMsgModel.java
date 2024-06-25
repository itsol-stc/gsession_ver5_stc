package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_COL_MSG Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpColMsgModel implements Serializable {

    /** NCM_ID mapping */
    private int ncmId__;
    /** NCM_MSG mapping */
    private String ncmMsg__;
    /** NCM_AUID mapping */
    private int ncmAuid__;
    /** NCM_ADATE mapping */
    private UDate ncmAdate__;
    /** NCM_EUID mapping */
    private int ncmEuid__;
    /** NCM_EDATE mapping */
    private UDate ncmEdate__;

    /**
     * <p>Default Constructor
     */
    public NtpColMsgModel() {
    }

    /**
     * <p>get NCM_ID value
     * @return NCM_ID value
     */
    public int getNcmId() {
        return ncmId__;
    }

    /**
     * <p>set NCM_ID value
     * @param ncmId NCM_ID value
     */
    public void setNcmId(int ncmId) {
        ncmId__ = ncmId;
    }

    /**
     * <p>get NCM_MSG value
     * @return NCM_MSG value
     */
    public String getNcmMsg() {
        return ncmMsg__;
    }

    /**
     * <p>set NCM_MSG value
     * @param ncmMsg NCM_MSG value
     */
    public void setNcmMsg(String ncmMsg) {
        ncmMsg__ = ncmMsg;
    }

    /**
     * <p>get NCM_AUID value
     * @return NCM_AUID value
     */
    public int getNcmAuid() {
        return ncmAuid__;
    }

    /**
     * <p>set NCM_AUID value
     * @param ncmAuid NCM_AUID value
     */
    public void setNcmAuid(int ncmAuid) {
        ncmAuid__ = ncmAuid;
    }

    /**
     * <p>get NCM_ADATE value
     * @return NCM_ADATE value
     */
    public UDate getNcmAdate() {
        return ncmAdate__;
    }

    /**
     * <p>set NCM_ADATE value
     * @param ncmAdate NCM_ADATE value
     */
    public void setNcmAdate(UDate ncmAdate) {
        ncmAdate__ = ncmAdate;
    }

    /**
     * <p>get NCM_EUID value
     * @return NCM_EUID value
     */
    public int getNcmEuid() {
        return ncmEuid__;
    }

    /**
     * <p>set NCM_EUID value
     * @param ncmEuid NCM_EUID value
     */
    public void setNcmEuid(int ncmEuid) {
        ncmEuid__ = ncmEuid;
    }

    /**
     * <p>get NCM_EDATE value
     * @return NCM_EDATE value
     */
    public UDate getNcmEdate() {
        return ncmEdate__;
    }

    /**
     * <p>set NCM_EDATE value
     * @param ncmEdate NCM_EDATE value
     */
    public void setNcmEdate(UDate ncmEdate) {
        ncmEdate__ = ncmEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ncmId__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ncmMsg__, ""));
        buf.append(",");
        buf.append(ncmAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ncmAdate__, ""));
        buf.append(",");
        buf.append(ncmEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ncmEdate__, ""));
        return buf.toString();
    }

}
