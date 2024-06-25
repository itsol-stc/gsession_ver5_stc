package jp.groupsession.v2.cht.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CHT_GROUP_DATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtGroupDataModel implements Serializable {

    /** CGD_SID mapping */
    private long cgdSid__;
    /** CGI_SID mapping */
    private int cgiSid__;
    /** CGD_TEXT mapping */
    private String cgdText__;
    /** BIN_SID mapping */
    private long binSid__;
    /** CGD_SEND_UID mapping */
    private int cgdSendUid__;
    /** CGD_STATE_KBN mapping */
    private int cgdStateKbn__;
    /** CGD_AUID mapping */
    private int cgdAuid__;
    /** CGD_ADATE mapping */
    private UDate cgdAdate__;
    /** CGD_EUID mapping */
    private int cgdEuid__;
    /** CGD_EDATE mapping */
    private UDate cgdEdate__;

    /**
     * <p>Default Constructor
     */
    public ChtGroupDataModel() {
    }

    /**
     * <p>get CGD_SID value
     * @return CGD_SID value
     */
    public long getCgdSid() {
        return cgdSid__;
    }

    /**
     * <p>set CGD_SID value
     * @param cgdSid CGD_SID value
     */
    public void setCgdSid(long cgdSid) {
        cgdSid__ = cgdSid;
    }

    /**
     * <p>get CGI_SID value
     * @return CGI_SID value
     */
    public int getCgiSid() {
        return cgiSid__;
    }

    /**
     * <p>set CGI_SID value
     * @param cgiSid CGI_SID value
     */
    public void setCgiSid(int cgiSid) {
        cgiSid__ = cgiSid;
    }

    /**
     * <p>get CGD_TEXT value
     * @return CGD_TEXT value
     */
    public String getCgdText() {
        return cgdText__;
    }

    /**
     * <p>set CGD_TEXT value
     * @param cgdText CGD_TEXT value
     */
    public void setCgdText(String cgdText) {
        cgdText__ = cgdText;
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
     * <p>get CGD_SEND_UID value
     * @return CGD_SEND_UID value
     */
    public int getCgdSendUid() {
        return cgdSendUid__;
    }

    /**
     * <p>set CGD_SEND_UID value
     * @param cgdSendUid CGD_SEND_UID value
     */
    public void setCgdSendUid(int cgdSendUid) {
        cgdSendUid__ = cgdSendUid;
    }

    /**
     * <p>get CGD_STATE_KBN value
     * @return CGD_STATE_KBN value
     */
    public int getCgdStateKbn() {
        return cgdStateKbn__;
    }

    /**
     * <p>set CGD_STATE_KBN value
     * @param cgdStateKbn CGD_STATE_KBN value
     */
    public void setCgdStateKbn(int cgdStateKbn) {
        cgdStateKbn__ = cgdStateKbn;
    }

    /**
     * <p>get CGD_AUID value
     * @return CGD_AUID value
     */
    public int getCgdAuid() {
        return cgdAuid__;
    }

    /**
     * <p>set CGD_AUID value
     * @param cgdAuid CGD_AUID value
     */
    public void setCgdAuid(int cgdAuid) {
        cgdAuid__ = cgdAuid;
    }

    /**
     * <p>get CGD_ADATE value
     * @return CGD_ADATE value
     */
    public UDate getCgdAdate() {
        return cgdAdate__;
    }

    /**
     * <p>set CGD_ADATE value
     * @param cgdAdate CGD_ADATE value
     */
    public void setCgdAdate(UDate cgdAdate) {
        cgdAdate__ = cgdAdate;
    }

    /**
     * <p>get CGD_EUID value
     * @return CGD_EUID value
     */
    public int getCgdEuid() {
        return cgdEuid__;
    }

    /**
     * <p>set CGD_EUID value
     * @param cgdEuid CGD_EUID value
     */
    public void setCgdEuid(int cgdEuid) {
        cgdEuid__ = cgdEuid;
    }

    /**
     * <p>get CGD_EDATE value
     * @return CGD_EDATE value
     */
    public UDate getCgdEdate() {
        return cgdEdate__;
    }

    /**
     * <p>set CGD_EDATE value
     * @param cgdEdate CGD_EDATE value
     */
    public void setCgdEdate(UDate cgdEdate) {
        cgdEdate__ = cgdEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cgdSid__);
        buf.append(",");
        buf.append(cgiSid__);
        buf.append(",");
        buf.append(NullDefault.getString(cgdText__, ""));
        buf.append(",");
        buf.append(binSid__);
        buf.append(",");
        buf.append(cgdSendUid__);
        buf.append(",");
        buf.append(cgdStateKbn__);
        buf.append(",");
        buf.append(cgdAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cgdAdate__, ""));
        buf.append(",");
        buf.append(cgdEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cgdEdate__, ""));
        return buf.toString();
    }

}
