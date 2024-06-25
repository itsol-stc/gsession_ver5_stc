package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_COL_MSG Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpMikomidoMsgModel implements Serializable {

    /** NMM_ID mapping */
    private int nmmId__;
    /** NMM_MSG mapping */
    private String nmmMsg__;
    /** NMM_AUID mapping */
    private int nmmAuid__;
    /** NMM_ADATE mapping */
    private UDate nmmAdate__;
    /** NMM_EUID mapping */
    private int nmmEuid__;
    /** NMM_EDATE mapping */
    private UDate nmmEdate__;

    /** NMM_NAME mapping */
    private String nmmName__;

    /**
     * <p>Default Constructor
     */
    public NtpMikomidoMsgModel() {
    }

    /**
     * <p>get NMM_ID value
     * @return NMM_ID value
     */
    public int getNmmId() {
        return nmmId__;
    }

    /**
     * <p>set NMM_ID value
     * @param nmmId NMM_ID value
     */
    public void setNmmId(int nmmId) {
        nmmId__ = nmmId;
    }

    /**
     * <p>get NMM_MSG value
     * @return NMM_MSG value
     */
    public String getNmmMsg() {
        return nmmMsg__;
    }

    /**
     * <p>set NMM_MSG value
     * @param nmmMsg NMM_MSG value
     */
    public void setNmmMsg(String nmmMsg) {
        nmmMsg__ = nmmMsg;
    }

    /**
     * <p>get NMM_AUID value
     * @return NMM_AUID value
     */
    public int getNmmAuid() {
        return nmmAuid__;
    }

    /**
     * <p>set NMM_AUID value
     * @param nmmAuid NMM_AUID value
     */
    public void setNmmAuid(int nmmAuid) {
        nmmAuid__ = nmmAuid;
    }

    /**
     * <p>get NMM_ADATE value
     * @return NMM_ADATE value
     */
    public UDate getNmmAdate() {
        return nmmAdate__;
    }

    /**
     * <p>set NMM_ADATE value
     * @param nmmAdate NMM_ADATE value
     */
    public void setNmmAdate(UDate nmmAdate) {
        nmmAdate__ = nmmAdate;
    }

    /**
     * <p>get NMM_EUID value
     * @return NMM_EUID value
     */
    public int getNmmEuid() {
        return nmmEuid__;
    }

    /**
     * <p>set NMM_EUID value
     * @param nmmEuid NMM_EUID value
     */
    public void setNmmEuid(int nmmEuid) {
        nmmEuid__ = nmmEuid;
    }

    /**
     * <p>get NMM_EDATE value
     * @return NMM_EDATE value
     */
    public UDate getNmmEdate() {
        return nmmEdate__;
    }

    /**
     * <p>set NMM_EDATE value
     * @param nmmEdate NMM_EDATE value
     */
    public void setNmmEdate(UDate nmmEdate) {
        nmmEdate__ = nmmEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nmmId__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nmmMsg__, ""));
        buf.append(",");
        buf.append(nmmAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nmmAdate__, ""));
        buf.append(",");
        buf.append(nmmEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nmmEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>nmmName を取得します。
     * @return nmmName
     */
    public String getNmmName() {
        return nmmName__;
    }

    /**
     * <p>nmmName をセットします。
     * @param nmmName nmmName
     */
    public void setNmmName(String nmmName) {
        nmmName__ = nmmName;
    }

}
