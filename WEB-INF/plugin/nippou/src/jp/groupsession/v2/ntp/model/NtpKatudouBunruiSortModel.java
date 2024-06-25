package jp.groupsession.v2.ntp.model;

import java.io.Serializable;

/**
 * <p>NtpKatudouBunruiSort Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpKatudouBunruiSortModel implements Serializable {

    /** WAC_SID mapping */
    private int nkbSid__;
    /** WAS_SORT mapping */
    private int nksSort__;
    /**
     * <p>nkbSid を取得します。
     * @return nkbSid
     */
    public int getNkbSid() {
        return nkbSid__;
    }
    /**
     * <p>nkbSid をセットします。
     * @param nkbSid nkbSid
     */
    public void setNkbSid(int nkbSid) {
        nkbSid__ = nkbSid;
    }
    /**
     * <p>nksSort を取得します。
     * @return nksSort
     */
    public int getNksSort() {
        return nksSort__;
    }
    /**
     * <p>nksSort をセットします。
     * @param nksSort nksSort
     */
    public void setNksSort(int nksSort) {
        nksSort__ = nksSort;
    }

}
