package jp.groupsession.v2.ntp.model;

import java.io.Serializable;

/**
 * <p>NTP_CONTACT_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpContactSortModel implements Serializable {

    /** NCN_SID mapping */
    private int ncnSid__;
    /** NCS_SORT mapping */
    private int ncsSort__;
    /**
     * <p>ncnSid を取得します。
     * @return ncnSid
     */
    public int getNcnSid() {
        return ncnSid__;
    }
    /**
     * <p>ncnSid をセットします。
     * @param ncnSid ncnSid
     */
    public void setNcnSid(int ncnSid) {
        ncnSid__ = ncnSid;
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
