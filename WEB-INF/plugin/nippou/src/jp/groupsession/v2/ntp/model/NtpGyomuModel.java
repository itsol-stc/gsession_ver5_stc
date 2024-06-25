package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_GYOMU Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpGyomuModel implements Serializable {

    /** NGY_SID mapping */
    private int ngySid__;
    /** NGY_CODE mapping */
    private String ngyCode__;
    /** NGY_NAME mapping */
    private String ngyName__;
    /** NGY_AUID mapping */
    private int ngyAuid__;
    /** NGY_ADATE mapping */
    private UDate ngyAdate__;
    /** NGY_EUID mapping */
    private int ngyEuid__;
    /** NGY_EDATE mapping */
    private UDate ngyEdate__;
    /** NGS_SORT mapping */
    private int ngsSort__;

    /**
     * <p>Default Constructor
     */
    public NtpGyomuModel() {
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
     * <p>get NGY_CODE value
     * @return NGY_CODE value
     */
    public String getNgyCode() {
        return ngyCode__;
    }

    /**
     * <p>set NGY_CODE value
     * @param ngyCode NGY_CODE value
     */
    public void setNgyCode(String ngyCode) {
        ngyCode__ = ngyCode;
    }

    /**
     * <p>get NGY_NAME value
     * @return NGY_NAME value
     */
    public String getNgyName() {
        return ngyName__;
    }

    /**
     * <p>set NGY_NAME value
     * @param ngyName NGY_NAME value
     */
    public void setNgyName(String ngyName) {
        ngyName__ = ngyName;
    }

    /**
     * <p>get NGY_AUID value
     * @return NGY_AUID value
     */
    public int getNgyAuid() {
        return ngyAuid__;
    }

    /**
     * <p>set NGY_AUID value
     * @param ngyAuid NGY_AUID value
     */
    public void setNgyAuid(int ngyAuid) {
        ngyAuid__ = ngyAuid;
    }

    /**
     * <p>get NGY_ADATE value
     * @return NGY_ADATE value
     */
    public UDate getNgyAdate() {
        return ngyAdate__;
    }

    /**
     * <p>set NGY_ADATE value
     * @param ngyAdate NGY_ADATE value
     */
    public void setNgyAdate(UDate ngyAdate) {
        ngyAdate__ = ngyAdate;
    }

    /**
     * <p>get NGY_EUID value
     * @return NGY_EUID value
     */
    public int getNgyEuid() {
        return ngyEuid__;
    }

    /**
     * <p>set NGY_EUID value
     * @param ngyEuid NGY_EUID value
     */
    public void setNgyEuid(int ngyEuid) {
        ngyEuid__ = ngyEuid;
    }

    /**
     * <p>get NGY_EDATE value
     * @return NGY_EDATE value
     */
    public UDate getNgyEdate() {
        return ngyEdate__;
    }

    /**
     * <p>set NGY_EDATE value
     * @param ngyEdate NGY_EDATE value
     */
    public void setNgyEdate(UDate ngyEdate) {
        ngyEdate__ = ngyEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ngySid__);
        buf.append(",");
        buf.append(ngyCode__);
        buf.append(",");
        buf.append(NullDefault.getString(ngyName__, ""));
        buf.append(",");
        buf.append(ngyAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ngyAdate__, ""));
        buf.append(",");
        buf.append(ngyEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ngyEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>ngsSort を取得します。
     * @return ngsSort
     */
    public int getNgsSort() {
        return ngsSort__;
    }

    /**
     * <p>ngsSort をセットします。
     * @param ngsSort ngsSort
     */
    public void setNgsSort(int ngsSort) {
        ngsSort__ = ngsSort;
    }

}
