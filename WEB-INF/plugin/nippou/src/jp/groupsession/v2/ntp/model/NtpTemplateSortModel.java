package jp.groupsession.v2.ntp.model;

import java.io.Serializable;

/**
 * <p>NTP_TEMPLATE_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpTemplateSortModel implements Serializable {

    /** NTT_SID mapping */
    private int nttSid__;
    /** NPS_SORT mapping */
    private int npsSort__;
    /**
     * <p>nttSid を取得します。
     * @return nttSid
     */
    public int getNttSid() {
        return nttSid__;
    }
    /**
     * <p>nttSid をセットします。
     * @param nttSid nttSid
     */
    public void setNttSid(int nttSid) {
        nttSid__ = nttSid;
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
