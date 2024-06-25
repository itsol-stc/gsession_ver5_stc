package jp.groupsession.v2.ptl.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>PTL_PORTLET_CATEGORY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortletCategoryModel implements Serializable {

    /** PLC_SID mapping */
    private int plcSid__;
    /** PLC_NAME mapping */
    private String plcName__;
    /** PLC_BIKO mapping */
    private String plcBiko__;

    /** ソート順文字列 */
    private String strPlcSort__;
    /** ソート順 */
    private int plcSort__;

    /**
     * <p>Default Constructor
     */
    public PtlPortletCategoryModel() {
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
     * <p>get PLC_NAME value
     * @return PLC_NAME value
     */
    public String getPlcName() {
        return plcName__;
    }

    /**
     * <p>set PLC_NAME value
     * @param plcName PLC_NAME value
     */
    public void setPlcName(String plcName) {
        plcName__ = plcName;
    }

    /**
     * <p>get PLC_BIKO value
     * @return PLC_BIKO value
     */
    public String getPlcBiko() {
        return plcBiko__;
    }

    /**
     * <p>set PLC_BIKO value
     * @param plcBiko PLC_BIKO value
     */
    public void setPlcBiko(String plcBiko) {
        plcBiko__ = plcBiko;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(plcSid__);
        buf.append(",");
        buf.append(NullDefault.getString(plcName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(plcBiko__, ""));
        return buf.toString();
    }

    /**
     * @return strPlcSort
     */
    public String getStrPlcSort() {
        return strPlcSort__;
    }

    /**
     * @param strPlcSort セットする strPlcSort
     */
    public void setStrPlcSort(String strPlcSort) {
        strPlcSort__ = strPlcSort;
    }

    /**
     * @return plcSort
     */
    public int getPlcSort() {
        return plcSort__;
    }

    /**
     * @param plcSort セットする plcSort
     */
    public void setPlcSort(int plcSort) {
        plcSort__ = plcSort;
    }


}
