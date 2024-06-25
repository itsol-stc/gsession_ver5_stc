package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_KTBUNRUI Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpKtbunruiModel implements Serializable {

    /** NKB_SID mapping */
    private int nkbSid__;
    /** NKB_CODE mapping */
    private String nkbCode__;
    /** NKB_NAME mapping */
    private String nkbName__;
    /** NKB_TIEUP_KBN mapping */
    private int nkbTieupKbn__;
    /** NKB_AUID mapping */
    private int nkbAuid__;
    /** NKB_ADATE mapping */
    private UDate nkbAdate__;
    /** NKB_EUID mapping */
    private int nkbEuid__;
    /** NKB_EDATE mapping */
    private UDate nkbEdate__;
    /** NKS_SORT mapping */
    private int nksSort__;

    /**
     * <p>Default Constructor
     */
    public NtpKtbunruiModel() {
    }

    /**
     * <p>get NKB_SID value
     * @return NKB_SID value
     */
    public int getNkbSid() {
        return nkbSid__;
    }

    /**
     * <p>set NKB_SID value
     * @param nkbSid NKB_SID value
     */
    public void setNkbSid(int nkbSid) {
        nkbSid__ = nkbSid;
    }

    /**
     * <p>get NKB_CODE value
     * @return NKB_CODE value
     */
    public String getNkbCode() {
        return nkbCode__;
    }

    /**
     * <p>set NKB_CODE value
     * @param nkbCode NKB_CODE value
     */
    public void setNkbCode(String nkbCode) {
        nkbCode__ = nkbCode;
    }

    /**
     * <p>get NKB_NAME value
     * @return NKB_NAME value
     */
    public String getNkbName() {
        return nkbName__;
    }

    /**
     * <p>set NKB_NAME value
     * @param nkbName NKB_NAME value
     */
    public void setNkbName(String nkbName) {
        nkbName__ = nkbName;
    }

    /**
     * <p>get NKB_TIEUP_KBN value
     * @return NKB_TIEUP_KBN value
     */
    public int getNkbTieupKbn() {
        return nkbTieupKbn__;
    }

    /**
     * <p>set NKB_TIEUP_KBN value
     * @param nkbTieupKbn NKB_TIEUP_KBN value
     */
    public void setNkbTieupKbn(int nkbTieupKbn) {
        nkbTieupKbn__ = nkbTieupKbn;
    }

    /**
     * <p>get NKB_AUID value
     * @return NKB_AUID value
     */
    public int getNkbAuid() {
        return nkbAuid__;
    }

    /**
     * <p>set NKB_AUID value
     * @param nkbAuid NKB_AUID value
     */
    public void setNkbAuid(int nkbAuid) {
        nkbAuid__ = nkbAuid;
    }

    /**
     * <p>get NKB_ADATE value
     * @return NKB_ADATE value
     */
    public UDate getNkbAdate() {
        return nkbAdate__;
    }

    /**
     * <p>set NKB_ADATE value
     * @param nkbAdate NKB_ADATE value
     */
    public void setNkbAdate(UDate nkbAdate) {
        nkbAdate__ = nkbAdate;
    }

    /**
     * <p>get NKB_EUID value
     * @return NKB_EUID value
     */
    public int getNkbEuid() {
        return nkbEuid__;
    }

    /**
     * <p>set NKB_EUID value
     * @param nkbEuid NKB_EUID value
     */
    public void setNkbEuid(int nkbEuid) {
        nkbEuid__ = nkbEuid;
    }

    /**
     * <p>get NKB_EDATE value
     * @return NKB_EDATE value
     */
    public UDate getNkbEdate() {
        return nkbEdate__;
    }

    /**
     * <p>set NKB_EDATE value
     * @param nkbEdate NKB_EDATE value
     */
    public void setNkbEdate(UDate nkbEdate) {
        nkbEdate__ = nkbEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nkbSid__);
        buf.append(",");
        buf.append(nkbCode__);
        buf.append(",");
        buf.append(NullDefault.getString(nkbName__, ""));
        buf.append(",");
        buf.append(nkbTieupKbn__);
        buf.append(",");
        buf.append(nkbAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nkbAdate__, ""));
        buf.append(",");
        buf.append(nkbEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nkbEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>nksSort を取得します。
     * @return nksSort
     */
    public int getNksSort() {
        return nksSort__;
    }

    /**
     * <p>nksSort をセットします。
     * @param nksSort nksSort
     */
    public void setNksSort(int nksSort) {
        nksSort__ = nksSort;
    }

}
