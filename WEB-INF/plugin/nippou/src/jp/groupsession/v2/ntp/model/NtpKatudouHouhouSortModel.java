package jp.groupsession.v2.ntp.model;

import java.io.Serializable;

/**
 * <p>NtpKatudouHouhouSort Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpKatudouHouhouSortModel implements Serializable {

    /** NKH_SID mapping */
    private int nkhSid__;
    /** NHS_SORT mapping */
    private int nhsSort__;
    /**
     * <p>nkhSid を取得します。
     * @return nkhSid
     */
    public int getNkhSid() {
        return nkhSid__;
    }
    /**
     * <p>nkhSid をセットします。
     * @param nkhSid nkhSid
     */
    public void setNkhSid(int nkhSid) {
        nkhSid__ = nkhSid;
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
