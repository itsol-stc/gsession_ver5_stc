package jp.groupsession.v2.ptl.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>PTL_PORTLET Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PtlPortletModel implements Serializable {

    /** PLT_SID mapping */
    private int pltSid__;
    /** PLT_NAME mapping */
    private String pltName__;
    /** PLT_CONTENT mapping */
    private String pltContent__;
    /** PLT_CONTENT_TYPE mapping */
    private int pltContentType__;
    /** PLT_CONTENT_URL mapping */
    private String pltContentUrl__;
    /** PLT_DESCRIPTION mapping */
    private String pltDescription__;
    /** PLC_SID mapping */
    private int plcSid__;
    /** PLT_BORDER mapping */
    private int pltBorder__;
    /** PLT_AUID mapping */
    private int pltAuid__;
    /** PLT_ADATE mapping */
    private UDate pltAdate__;
    /** PLT_EUID mapping */
    private int pltEuid__;
    /** PLT_EDATE mapping */
    private UDate pltEdate__;

    /** ポートレットソート順 */
    private int pltSort__;
    /** ポートレットカテゴリ名 */
    private String plcName__;

    /** ポートレットソート順(文字列) */
    private String strPltSort__;


    /**
     * <p>Default Constructor
     */
    public PtlPortletModel() {
    }

    /**
     * <p>get PLT_SID value
     * @return PLT_SID value
     */
    public int getPltSid() {
        return pltSid__;
    }

    /**
     * <p>set PLT_SID value
     * @param pltSid PLT_SID value
     */
    public void setPltSid(int pltSid) {
        pltSid__ = pltSid;
    }

    /**
     * <p>get PLT_NAME value
     * @return PLT_NAME value
     */
    public String getPltName() {
        return pltName__;
    }

    /**
     * <p>set PLT_NAME value
     * @param pltName PLT_NAME value
     */
    public void setPltName(String pltName) {
        pltName__ = pltName;
    }

    /**
     * <p>get PLT_CONTENT value
     * @return PLT_CONTENT value
     */
    public String getPltContent() {
        return pltContent__;
    }

    /**
     * <p>set PLT_CONTENT value
     * @param pltContent PLT_CONTENT value
     */
    public void setPltContent(String pltContent) {
        pltContent__ = pltContent;
    }

    /**
     * <p>get PLT_CONTENT_TYPE value
     * @return PLT_CONTENT_TYPE value
     */
    public int getPltContentType() {
        return pltContentType__;
    }

    /**
     * <p>set PLT_CONTENT_TYPE value
     * @param pltContentType PLT_CONTENT_TYPE value
     */
    public void setPltContentType(int pltContentType) {
        pltContentType__ = pltContentType;
    }

    /**
     * <p>get PLT_CONTENT_URL value
     * @return PLT_CONTENT_URL value
     */
    public String getPltContentUrl() {
        return pltContentUrl__;
    }

    /**
     * <p>set PLT_CONTENT_URL value
     * @param pltContentUrl PLT_CONTENT_URL value
     */
    public void setPltContentUrl(String pltContentUrl) {
        pltContentUrl__ = pltContentUrl;
    }

    /**
     * <p>get PLT_DESCRIPTION value
     * @return PLT_DESCRIPTION value
     */
    public String getPltDescription() {
        return pltDescription__;
    }

    /**
     * <p>set PLT_DESCRIPTION value
     * @param pltDescription PLT_DESCRIPTION value
     */
    public void setPltDescription(String pltDescription) {
        pltDescription__ = pltDescription;
    }

    /**
     * <p>get PLC_SID value
     * @return PLC_SID value
     */
    public int getPlcSid() {
        return plcSid__;
    }

    /**
     * <p>set PLC_SID value
     * @param plcSid PLC_SID value
     */
    public void setPlcSid(int plcSid) {
        plcSid__ = plcSid;
    }

    /**
     * <p>get PLT_BORDER value
     * @return PLT_BORDER value
     */
    public int getPltBorder() {
        return pltBorder__;
    }

    /**
     * <p>set PLT_BORDER value
     * @param pltBorder PLT_BORDER value
     */
    public void setPltBorder(int pltBorder) {
        pltBorder__ = pltBorder;
    }

    /**
     * <p>get PLT_AUID value
     * @return PLT_AUID value
     */
    public int getPltAuid() {
        return pltAuid__;
    }

    /**
     * <p>set PLT_AUID value
     * @param pltAuid PLT_AUID value
     */
    public void setPltAuid(int pltAuid) {
        pltAuid__ = pltAuid;
    }

    /**
     * <p>get PLT_ADATE value
     * @return PLT_ADATE value
     */
    public UDate getPltAdate() {
        return pltAdate__;
    }

    /**
     * <p>set PLT_ADATE value
     * @param pltAdate PLT_ADATE value
     */
    public void setPltAdate(UDate pltAdate) {
        pltAdate__ = pltAdate;
    }

    /**
     * <p>get PLT_EUID value
     * @return PLT_EUID value
     */
    public int getPltEuid() {
        return pltEuid__;
    }

    /**
     * <p>set PLT_EUID value
     * @param pltEuid PLT_EUID value
     */
    public void setPltEuid(int pltEuid) {
        pltEuid__ = pltEuid;
    }

    /**
     * <p>get PLT_EDATE value
     * @return PLT_EDATE value
     */
    public UDate getPltEdate() {
        return pltEdate__;
    }

    /**
     * <p>set PLT_EDATE value
     * @param pltEdate PLT_EDATE value
     */
    public void setPltEdate(UDate pltEdate) {
        pltEdate__ = pltEdate;
    }

    /**
     * <p>plcName を取得します。
     * @return plcName
     */
    public String getPlcName() {
        return plcName__;
    }

    /**
     * <p>plcName をセットします。
     * @param plcName plcName
     */
    public void setPlcName(String plcName) {
        plcName__ = plcName;
    }

    /**
     * <p>pltSort を取得します。
     * @return pltSort
     */
    public int getPltSort() {
        return pltSort__;
    }

    /**
     * <p>pltSort をセットします。
     * @param pltSort pltSort
     */
    public void setPltSort(int pltSort) {
        pltSort__ = pltSort;
    }

    /**
     * <p>strPltSort を取得します。
     * @return strPltSort
     */
    public String getStrPltSort() {
        return strPltSort__;
    }

    /**
     * <p>strPltSort をセットします。
     * @param strPltSort strPltSort
     */
    public void setStrPltSort(String strPltSort) {
        strPltSort__ = strPltSort;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(pltSid__);
        buf.append(",");
        buf.append(NullDefault.getString(pltName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(pltContent__, ""));
        buf.append(",");
        buf.append(pltContentType__);
        buf.append(",");
        buf.append(NullDefault.getString(pltContentUrl__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(pltDescription__, ""));
        buf.append(",");
        buf.append(plcSid__);
        buf.append(",");
        buf.append(pltBorder__);
        buf.append(",");
        buf.append(pltAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(pltAdate__, ""));
        buf.append(",");
        buf.append(pltEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(pltEdate__, ""));
        return buf.toString();
    }

}
