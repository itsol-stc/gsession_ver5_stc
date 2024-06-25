package jp.groupsession.v2.ptl.model;

import java.io.Serializable;

/**
 * <p>PTL_PORTLET_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortletSortModel implements Serializable {

    /** PLT_SID mapping */
    private int pltSid__;
    /** PLS_SORT mapping */
    private int plsSort__;

    /**
     * <p>Default Constructor
     */
    public PtlPortletSortModel() {
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
     * <p>get PLS_SORT value
     * @return PLS_SORT value
     */
    public int getPlsSort() {
        return plsSort__;
    }

    /**
     * <p>set PLS_SORT value
     * @param plsSort PLS_SORT value
     */
    public void setPlsSort(int plsSort) {
        plsSort__ = plsSort;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(pltSid__);
        buf.append(",");
        buf.append(plsSort__);
        return buf.toString();
    }

}
