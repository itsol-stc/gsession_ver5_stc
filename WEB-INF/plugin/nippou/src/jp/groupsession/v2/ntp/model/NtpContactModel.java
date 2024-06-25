package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_CONTACT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpContactModel implements Serializable {

    /** NCN_SID mapping */
    private int ncnSid__;
    /** NCN_CODE mapping */
    private String ncnCode__;
    /** NCN_NAME mapping */
    private String ncnName__;
    /** NCN_AUID mapping */
    private int ncnAuid__;
    /** NCN_ADATE mapping */
    private UDate ncnAdate__;
    /** NCN_EUID mapping */
    private int ncnEuid__;
    /** NCN_EDATE mapping */
    private UDate ncnEdate__;
    /** NCS_SORT mapping */
    private int ncsSort__;

    /**
     * <p>Default Constructor
     */
    public NtpContactModel() {
    }

    /**
     * <p>get NCN_SID value
     * @return NCN_SID value
     */
    public int getNcnSid() {
        return ncnSid__;
    }

    /**
     * <p>set NCN_SID value
     * @param ncnSid NCN_SID value
     */
    public void setNcnSid(int ncnSid) {
        ncnSid__ = ncnSid;
    }

    /**
     * <p>get NCN_CODE value
     * @return NCN_CODE value
     */
    public String getNcnCode() {
        return ncnCode__;
    }

    /**
     * <p>set NCN_CODE value
     * @param ncnCode NCN_CODE value
     */
    public void setNcnCode(String ncnCode) {
        ncnCode__ = ncnCode;
    }

    /**
     * <p>get NCN_NAME value
     * @return NCN_NAME value
     */
    public String getNcnName() {
        return ncnName__;
    }

    /**
     * <p>set NCN_NAME value
     * @param ncnName NCN_NAME value
     */
    public void setNcnName(String ncnName) {
        ncnName__ = ncnName;
    }

    /**
     * <p>get NCN_AUID value
     * @return NCN_AUID value
     */
    public int getNcnAuid() {
        return ncnAuid__;
    }

    /**
     * <p>set NCN_AUID value
     * @param ncnAuid NCN_AUID value
     */
    public void setNcnAuid(int ncnAuid) {
        ncnAuid__ = ncnAuid;
    }

    /**
     * <p>get NCN_ADATE value
     * @return NCN_ADATE value
     */
    public UDate getNcnAdate() {
        return ncnAdate__;
    }

    /**
     * <p>set NCN_ADATE value
     * @param ncnAdate NCN_ADATE value
     */
    public void setNcnAdate(UDate ncnAdate) {
        ncnAdate__ = ncnAdate;
    }

    /**
     * <p>get NCN_EUID value
     * @return NCN_EUID value
     */
    public int getNcnEuid() {
        return ncnEuid__;
    }

    /**
     * <p>set NCN_EUID value
     * @param ncnEuid NCN_EUID value
     */
    public void setNcnEuid(int ncnEuid) {
        ncnEuid__ = ncnEuid;
    }

    /**
     * <p>get NCN_EDATE value
     * @return NCN_EDATE value
     */
    public UDate getNcnEdate() {
        return ncnEdate__;
    }

    /**
     * <p>set NCN_EDATE value
     * @param ncnEdate NCN_EDATE value
     */
    public void setNcnEdate(UDate ncnEdate) {
        ncnEdate__ = ncnEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ncnSid__);
        buf.append(",");
        buf.append(ncnCode__);
        buf.append(",");
        buf.append(NullDefault.getString(ncnName__, ""));
        buf.append(",");
        buf.append(ncnAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ncnAdate__, ""));
        buf.append(",");
        buf.append(ncnEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ncnEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>ncsSort を取得します。
     * @return ncsSort
     */
    public int getNcsSort() {
        return ncsSort__;
    }

    /**
     * <p>ncsSort をセットします。
     * @param ncsSort ncsSort
     */
    public void setNcsSort(int ncsSort) {
        ncsSort__ = ncsSort;
    }

}
