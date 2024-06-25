package jp.groupsession.v2.cht.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CHT_USER_DATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtUserDataModel implements Serializable {

    /** CUD_SID mapping */
    private long cudSid__;
    /** CUP_SID mapping */
    private int cupSid__;
    /** CUD_TEXT mapping */
    private String cudText__;
    /** BIN_SID mapping */
    private long binSid__;
    /** CUD_SEND_UID mapping */
    private int cudSendUid__;
    /** CUD_STATE_KBN mapping */
    private int cudStateKbn__;
    /** CUD_AUID mapping */
    private int cudAuid__;
    /** CUD_ADATE mapping */
    private UDate cudAdate__;
    /** CUD_EUID mapping */
    private int cudEuid__;
    /** CUD_EDATE mapping */
    private UDate cudEdate__;

    /**
     * <p>Default Constructor
     */
    public ChtUserDataModel() {
    }

    /**
     * <p>get CUD_SID value
     * @return CUD_SID value
     */
    public long getCudSid() {
        return cudSid__;
    }

    /**
     * <p>set CUD_SID value
     * @param cudSid CUD_SID value
     */
    public void setCudSid(long cudSid) {
        cudSid__ = cudSid;
    }

    /**
     * <p>get CUP_SID value
     * @return CUP_SID value
     */
    public int getCupSid() {
        return cupSid__;
    }

    /**
     * <p>set CUP_SID value
     * @param cupSid CUP_SID value
     */
    public void setCupSid(int cupSid) {
        cupSid__ = cupSid;
    }

    /**
     * <p>get CUD_TEXT value
     * @return CUD_TEXT value
     */
    public String getCudText() {
        return cudText__;
    }

    /**
     * <p>set CUD_TEXT value
     * @param cudText CUD_TEXT value
     */
    public void setCudText(String cudText) {
        cudText__ = cudText;
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
     * <p>get CUD_SEND_UID value
     * @return CUD_SEND_UID value
     */
    public int getCudSendUid() {
        return cudSendUid__;
    }

    /**
     * <p>set CUD_SEND_UID value
     * @param cudSendUid CUD_SEND_UID value
     */
    public void setCudSendUid(int cudSendUid) {
        cudSendUid__ = cudSendUid;
    }

    /**
     * <p>get CUD_STATE_KBN value
     * @return CUD_STATE_KBN value
     */
    public int getCudStateKbn() {
        return cudStateKbn__;
    }

    /**
     * <p>set CUD_STATE_KBN value
     * @param cudStateKbn CUD_STATE_KBN value
     */
    public void setCudStateKbn(int cudStateKbn) {
        cudStateKbn__ = cudStateKbn;
    }

    /**
     * <p>get CUD_AUID value
     * @return CUD_AUID value
     */
    public int getCudAuid() {
        return cudAuid__;
    }

    /**
     * <p>set CUD_AUID value
     * @param cudAuid CUD_AUID value
     */
    public void setCudAuid(int cudAuid) {
        cudAuid__ = cudAuid;
    }

    /**
     * <p>get CUD_ADATE value
     * @return CUD_ADATE value
     */
    public UDate getCudAdate() {
        return cudAdate__;
    }

    /**
     * <p>set CUD_ADATE value
     * @param cudAdate CUD_ADATE value
     */
    public void setCudAdate(UDate cudAdate) {
        cudAdate__ = cudAdate;
    }

    /**
     * <p>get CUD_EUID value
     * @return CUD_EUID value
     */
    public int getCudEuid() {
        return cudEuid__;
    }

    /**
     * <p>set CUD_EUID value
     * @param cudEuid CUD_EUID value
     */
    public void setCudEuid(int cudEuid) {
        cudEuid__ = cudEuid;
    }

    /**
     * <p>get CUD_EDATE value
     * @return CUD_EDATE value
     */
    public UDate getCudEdate() {
        return cudEdate__;
    }

    /**
     * <p>set CUD_EDATE value
     * @param cudEdate CUD_EDATE value
     */
    public void setCudEdate(UDate cudEdate) {
        cudEdate__ = cudEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cudSid__);
        buf.append(",");
        buf.append(cupSid__);
        buf.append(",");
        buf.append(NullDefault.getString(cudText__, ""));
        buf.append(",");
        buf.append(binSid__);
        buf.append(",");
        buf.append(cudSendUid__);
        buf.append(",");
        buf.append(cudStateKbn__);
        buf.append(",");
        buf.append(cudAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cudAdate__, ""));
        buf.append(",");
        buf.append(cudEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cudEdate__, ""));
        return buf.toString();
    }

}
