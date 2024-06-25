package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_PROCESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpProcessModel implements Serializable {

    /** NGP_SID mapping */
    private int ngpSid__;
    /** NGY_SID mapping */
    private int ngySid__;
    /** NGP_CODE mapping */
    private String ngpCode__;
    /** NGP_NAME mapping */
    private String ngpName__;
    /** NGP_ACCOUNT mapping */
    private String ngpAccount__;
    /** NGP_SORT mapping */
    private int ngpSort__;
    /** NGP_AUID mapping */
    private int ngpAuid__;
    /** NGP_ADATE mapping */
    private UDate ngpAdate__;
    /** NGP_EUID mapping */
    private int ngpEuid__;
    /** NGP_EDATE mapping */
    private UDate ngpEdate__;
    /** NPS_SORT */
    private int npsSort__;
    /** NGY_NAME */
    private String ngyName__;
    /** NGY_CODE */
    private String ngyCode__;

    /**
     * <p>Default Constructor
     */
    public NtpProcessModel() {
    }

    /**
     * <p>get NGP_SID value
     * @return NGP_SID value
     */
    public int getNgpSid() {
        return ngpSid__;
    }

    /**
     * <p>set NGP_SID value
     * @param ngpSid NGP_SID value
     */
    public void setNgpSid(int ngpSid) {
        ngpSid__ = ngpSid;
    }

    /**
     * <p>get NGY_SID value
     * @return NGY_SID value
     */
    public int getNgySid() {
        return ngySid__;
    }

    /**
     * <p>set NGY_SID value
     * @param ngySid NGY_SID value
     */
    public void setNgySid(int ngySid) {
        ngySid__ = ngySid;
    }

    /**
     * <p>get NGP_CODE value
     * @return NGP_CODE value
     */
    public String getNgpCode() {
        return ngpCode__;
    }

    /**
     * <p>set NGP_CODE value
     * @param ngpCode NGP_CODE value
     */
    public void setNgpCode(String ngpCode) {
        ngpCode__ = ngpCode;
    }

    /**
     * <p>get NGP_NAME value
     * @return NGP_NAME value
     */
    public String getNgpName() {
        return ngpName__;
    }

    /**
     * <p>set NGP_NAME value
     * @param ngpName NGP_NAME value
     */
    public void setNgpName(String ngpName) {
        ngpName__ = ngpName;
    }

    /**
     * <p>get NGP_ACCOUNT value
     * @return NGP_ACCOUNT value
     */
    public String getNgpAccount() {
        return ngpAccount__;
    }

    /**
     * <p>set NGP_ACCOUNT value
     * @param ngpAccount NGP_ACCOUNT value
     */
    public void setNgpAccount(String ngpAccount) {
        ngpAccount__ = ngpAccount;
    }

    /**
     * <p>get NGP_SORT value
     * @return NGP_SORT value
     */
    public int getNgpSort() {
        return ngpSort__;
    }

    /**
     * <p>set NGP_SORT value
     * @param ngpSort NGP_SORT value
     */
    public void setNgpSort(int ngpSort) {
        ngpSort__ = ngpSort;
    }

    /**
     * <p>get NGP_AUID value
     * @return NGP_AUID value
     */
    public int getNgpAuid() {
        return ngpAuid__;
    }

    /**
     * <p>set NGP_AUID value
     * @param ngpAuid NGP_AUID value
     */
    public void setNgpAuid(int ngpAuid) {
        ngpAuid__ = ngpAuid;
    }

    /**
     * <p>get NGP_ADATE value
     * @return NGP_ADATE value
     */
    public UDate getNgpAdate() {
        return ngpAdate__;
    }

    /**
     * <p>set NGP_ADATE value
     * @param ngpAdate NGP_ADATE value
     */
    public void setNgpAdate(UDate ngpAdate) {
        ngpAdate__ = ngpAdate;
    }

    /**
     * <p>get NGP_EUID value
     * @return NGP_EUID value
     */
    public int getNgpEuid() {
        return ngpEuid__;
    }

    /**
     * <p>set NGP_EUID value
     * @param ngpEuid NGP_EUID value
     */
    public void setNgpEuid(int ngpEuid) {
        ngpEuid__ = ngpEuid;
    }

    /**
     * <p>get NGP_EDATE value
     * @return NGP_EDATE value
     */
    public UDate getNgpEdate() {
        return ngpEdate__;
    }

    /**
     * <p>set NGP_EDATE value
     * @param ngpEdate NGP_EDATE value
     */
    public void setNgpEdate(UDate ngpEdate) {
        ngpEdate__ = ngpEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ngpSid__);
        buf.append(",");
        buf.append(ngySid__);
        buf.append(",");
        buf.append(NullDefault.getString(ngpCode__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(ngpName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(ngpAccount__, ""));
        buf.append(",");
        buf.append(ngpSort__);
        buf.append(",");
        buf.append(ngpAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ngpAdate__, ""));
        buf.append(",");
        buf.append(ngpEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ngpEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>npsSort を取得します。
     * @return npsSort
     */
    public int getNpsSort() {
        return npsSort__;
    }

    /**
     * <p>npsSort をセットします。
     * @param npsSort npsSort
     */
    public void setNpsSort(int npsSort) {
        npsSort__ = npsSort;
    }

    /**
     * <p>ngyName を取得します。
     * @return ngyName
     */
    public String getNgyName() {
        return ngyName__;
    }

    /**
     * <p>ngyName をセットします。
     * @param ngyName ngyName
     */
    public void setNgyName(String ngyName) {
        ngyName__ = ngyName;
    }

    /**
     * <p>ngyCode を取得します。
     * @return ngyCode
     */
    public String getNgyCode() {
        return ngyCode__;
    }

    /**
     * <p>ngyCode をセットします。
     * @param ngyCode ngyCode
     */
    public void setNgyCode(String ngyCode) {
        ngyCode__ = ngyCode;
    }

}
