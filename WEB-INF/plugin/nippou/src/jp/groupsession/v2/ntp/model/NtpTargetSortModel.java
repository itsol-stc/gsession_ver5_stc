package jp.groupsession.v2.ntp.model;

import java.io.Serializable;

/**
 * <p>NTP_TARGET_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpTargetSortModel implements Serializable {

    /** NTG_SID mapping */
    private int ntgSid__;
    /** NTS_SORT mapping */
    private int ntsSort__;
    /**
     * <p>ntgSid を取得します。
     * @return ntgSid
     */
    public int getNtgSid() {
        return ntgSid__;
    }
    /**
     * <p>ntgSid をセットします。
     * @param ntgSid ntgSid
     */
    public void setNtgSid(int ntgSid) {
        ntgSid__ = ntgSid;
    }
    /**
     * <p>ntsSort を取得します。
     * @return ntsSort
     */
    public int getNtsSort() {
        return ntsSort__;
    }
    /**
     * <p>ntsSort をセットします。
     * @param ntsSort ntsSort
     */
    public void setNtsSort(int ntsSort) {
        ntsSort__ = ntsSort;
    }


}
