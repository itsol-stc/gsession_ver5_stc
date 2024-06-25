package jp.groupsession.v2.ntp.model;

import java.io.Serializable;

/**
 * <p>NTP_GYOMU_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpGyomuSortModel implements Serializable {

    /** NGY_SID mapping */
    private int ngySid__;
    /** NGS_SORT mapping */
    private int ngsSort__;
    /**
     * <p>ngySid を取得します。
     * @return ngySid
     */
    public int getNgySid() {
        return ngySid__;
    }
    /**
     * <p>ngySid をセットします。
     * @param ngySid ngySid
     */
    public void setNgySid(int ngySid) {
        ngySid__ = ngySid;
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
