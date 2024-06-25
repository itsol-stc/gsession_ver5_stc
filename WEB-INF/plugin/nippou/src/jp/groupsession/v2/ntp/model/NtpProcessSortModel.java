package jp.groupsession.v2.ntp.model;

import java.io.Serializable;

/**
 * <p>NTP_PROCESS_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpProcessSortModel implements Serializable {

    /** NGP_SID mapping */
    private int ngpSid__;
    /** NGY_SID mapping */
    private int ngySid__;
    /** NPS_SORT */
    private int npsSort__;
    /**
     * <p>ngpSid を取得します。
     * @return ngpSid
     */
    public int getNgpSid() {
        return ngpSid__;
    }
    /**
     * <p>ngpSid をセットします。
     * @param ngpSid ngpSid
     */
    public void setNgpSid(int ngpSid) {
        ngpSid__ = ngpSid;
    }
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
     * <p>npsSort を取得します。
     * @return npsSort
     */
    public int getNpsSort() {
        return npsSort__;
    }
    /**
     * <p>npsSort をセットします。
     * @param npsSort npsSort
     */
    public void setNpsSort(int npsSort) {
        npsSort__ = npsSort;
    }

}
