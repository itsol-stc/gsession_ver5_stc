package jp.groupsession.v2.ptl.model;

import java.io.Serializable;

/**
 * <p>PTL_PORTLET_CATEGORY_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortletCategorySortModel implements Serializable {

    /** PLC_SID mapping */
    private int plcSid__;
    /** PCS_SORT mapping */
    private int pcsSort__;

    /**
     * <p>Default Constructor
     */
    public PtlPortletCategorySortModel() {
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
     * <p>get PCS_SORT value
     * @return PCS_SORT value
     */
    public int getPcsSort() {
        return pcsSort__;
    }

    /**
     * <p>set PCS_SORT value
     * @param pcsSort PCS_SORT value
     */
    public void setPcsSort(int pcsSort) {
        pcsSort__ = pcsSort;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(plcSid__);
        buf.append(",");
        buf.append(pcsSort__);
        return buf.toString();
    }

}
