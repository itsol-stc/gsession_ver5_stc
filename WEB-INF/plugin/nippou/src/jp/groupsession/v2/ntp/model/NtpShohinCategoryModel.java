package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_SHOHIN_CATEGORY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpShohinCategoryModel implements Serializable {

    /** NSC_SID mapping */
    private int nscSid__;
    /** NSC_NAME mapping */
    private String nscName__;
    /** NSC_BIKO mapping */
    private String nscBiko__;
    /** NSC_SORT mapping */
    private int nscSort__;
    /** NSC_AUID mapping */
    private int nscAuid__;
    /** NSC_ADATE mapping */
    private UDate nscAdate__;
    /** NSC_EUID mapping */
    private int nscEuid__;
    /** NSC_EDATE mapping */
    private UDate nscEdate__;
    /** 表示順(画面用) */
    private String nscValue__;


    /**
     * <p>Default Constructor
     */
    public NtpShohinCategoryModel() {
    }

    /**
     * <p>get NSC_SID value
     * @return NSC_SID value
     */
    public int getNscSid() {
        return nscSid__;
    }

    /**
     * <p>set NSC_SID value
     * @param nscSid NSC_SID value
     */
    public void setNscSid(int nscSid) {
        nscSid__ = nscSid;
    }

    /**
     * <p>get NSC_NAME value
     * @return NSC_NAME value
     */
    public String getNscName() {
        return nscName__;
    }

    /**
     * <p>set NSC_NAME value
     * @param nscName NSC_NAME value
     */
    public void setNscName(String nscName) {
        nscName__ = nscName;
    }

    /**
     * <p>get NSC_BIKO value
     * @return NSC_BIKO value
     */
    public String getNscBiko() {
        return nscBiko__;
    }

    /**
     * <p>set NSC_BIKO value
     * @param nscBiko NSC_BIKO value
     */
    public void setNscBiko(String nscBiko) {
        nscBiko__ = nscBiko;
    }

    /**
     * <p>get NSC_SORT value
     * @return NSC_SORT value
     */
    public int getNscSort() {
        return nscSort__;
    }

    /**
     * <p>set NSC_SORT value
     * @param nscSort NSC_SORT value
     */
    public void setNscSort(int nscSort) {
        nscSort__ = nscSort;
    }

    /**
     * <p>get NSC_AUID value
     * @return NSC_AUID value
     */
    public int getNscAuid() {
        return nscAuid__;
    }

    /**
     * <p>set NSC_AUID value
     * @param nscAuid NSC_AUID value
     */
    public void setNscAuid(int nscAuid) {
        nscAuid__ = nscAuid;
    }

    /**
     * <p>get NSC_ADATE value
     * @return NSC_ADATE value
     */
    public UDate getNscAdate() {
        return nscAdate__;
    }

    /**
     * <p>set NSC_ADATE value
     * @param nscAdate NSC_ADATE value
     */
    public void setNscAdate(UDate nscAdate) {
        nscAdate__ = nscAdate;
    }

    /**
     * <p>get NSC_EUID value
     * @return NSC_EUID value
     */
    public int getNscEuid() {
        return nscEuid__;
    }

    /**
     * <p>set NSC_EUID value
     * @param nscEuid NSC_EUID value
     */
    public void setNscEuid(int nscEuid) {
        nscEuid__ = nscEuid;
    }

    /**
     * <p>get NSC_EDATE value
     * @return NSC_EDATE value
     */
    public UDate getNscEdate() {
        return nscEdate__;
    }

    /**
     * <p>set NSC_EDATE value
     * @param nscEdate NSC_EDATE value
     */
    public void setNscEdate(UDate nscEdate) {
        nscEdate__ = nscEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(nscSid__);
        buf.append(",");
        buf.append(NullDefault.getString(nscName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(nscBiko__, ""));
        buf.append(",");
        buf.append(nscSort__);
        buf.append(",");
        buf.append(nscAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nscAdate__, ""));
        buf.append(",");
        buf.append(nscEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nscEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>nscValue を取得します。
     * @return nscValue
     */
    public String getNscValue() {
        return nscValue__;
    }

    /**
     * <p>nscValue をセットします。
     * @param nscValue nscValue
     */
    public void setNscValue(String nscValue) {
        nscValue__ = nscValue;
    }

}
