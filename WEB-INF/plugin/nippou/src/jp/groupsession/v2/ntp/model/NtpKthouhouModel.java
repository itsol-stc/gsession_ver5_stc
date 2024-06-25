package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_KTHOUHOU Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpKthouhouModel implements Serializable {

    /** NKH_SID mapping */
    private int nkhSid__;
    /** NKH_CODE mapping */
    private String nkhCode__;
    /** NKH_NAME mapping */
    private String nkhName__;
    /** NKH_AUID mapping */
    private int nkhAuid__;
    /** NKH_ADATE mapping */
    private UDate nkhAdate__;
    /** NKH_EUID mapping */
    private int nkhEuid__;
    /** NKH_EDATE mapping */
    private UDate nkhEdate__;
    /** NHS_SORT mapping */
    private int nhsSort__;

    /**
     * <p>Default Constructor
     */
    public NtpKthouhouModel() {
    }

    /**
     * <p>get NKH_SID value
     * @return NKH_SID value
     */
    public int getNkhSid() {
        return nkhSid__;
    }

    /**
     * <p>set NKH_SID value
     * @param nkhSid NKH_SID value
     */
    public void setNkhSid(int nkhSid) {
        nkhSid__ = nkhSid;
    }

    /**
     * <p>get NKH_CODE value
     * @return NKH_CODE value
     */
    public String getNkhCode() {
        return nkhCode__;
    }

    /**
     * <p>set NKH_CODE value
     * @param nkhCode NKH_CODE value
     */
    public void setNkhCode(String nkhCode) {
        nkhCode__ = nkhCode;
    }

    /**
     * <p>get NKH_NAME value
     * @return NKH_NAME value
     */
    public String getNkhName() {
        return nkhName__;
    }

    /**
     * <p>set NKH_NAME value
     * @param nkhName NKH_NAME value
     */
    public void setNkhName(String nkhName) {
        nkhName__ = nkhName;
    }

    /**
     * <p>get NKH_AUID value
     * @return NKH_AUID value
     */
    public int getNkhAuid() {
        return nkhAuid__;
    }

    /**
     * <p>set NKH_AUID value
     * @param nkhAuid NKH_AUID value
     */
    public void setNkhAuid(int nkhAuid) {
        nkhAuid__ = nkhAuid;
    }

    /**
     * <p>get NKH_ADATE value
     * @return NKH_ADATE value
     */
    public UDate getNkhAdate() {
        return nkhAdate__;
    }

    /**
     * <p>set NKH_ADATE value
     * @param nkhAdate NKH_ADATE value
     */
    public void setNkhAdate(UDate nkhAdate) {
        nkhAdate__ = nkhAdate;
    }

    /**
     * <p>get NKH_EUID value
     * @return NKH_EUID value
     */
    public int getNkhEuid() {
        return nkhEuid__;
    }

    /**
     * <p>set NKH_EUID value
     * @param nkhEuid NKH_EUID value
     */
    public void setNkhEuid(int nkhEuid) {
        nkhEuid__ = nkhEuid;
    }

    /**
     * <p>get NKH_EDATE value
     * @return NKH_EDATE value
     */
    public UDate getNkhEdate() {
        return nkhEdate__;
    }

    /**
     * <p>set NKH_EDATE value
     * @param nkhEdate NKH_EDATE value
     */
    public void setNkhEdate(UDate nkhEdate) {
        nkhEdate__ = nkhEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nkhSid__);
        buf.append(",");
        buf.append(nkhCode__);
        buf.append(",");
        buf.append(NullDefault.getString(nkhName__, ""));
        buf.append(",");
        buf.append(nkhAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nkhAdate__, ""));
        buf.append(",");
        buf.append(nkhEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(nkhEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>nhsSort を取得します。
     * @return nhsSort
     */
    public int getNhsSort() {
        return nhsSort__;
    }

    /**
     * <p>nhsSort をセットします。
     * @param nhsSort nhsSort
     */
    public void setNhsSort(int nhsSort) {
        nhsSort__ = nhsSort;
    }

}
