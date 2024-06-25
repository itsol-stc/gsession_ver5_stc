package jp.groupsession.v2.anp.model;

import java.io.Serializable;

/**
 * <p>ANP_MTEMP Data Bindding JavaBean
 *
 */
public class AnpMtempSortModel implements Serializable {

    /** APM_SID mapping */
    private int apmSid__;
    /** AMS_SORT mapping */
    private int amsSort__;
    /**
     * <p>apmSid を取得します。
     * @return apmSid
     */
    public int getApmSid() {
        return apmSid__;
    }
    /**
     * <p>apmSid をセットします。
     * @param apmSid apmSid
     */
    public void setApmSid(int apmSid) {
        apmSid__ = apmSid;
    }
    /**
     * <p>amsSort を取得します。
     * @return amsSort
     */
    public int getAmsSort() {
        return amsSort__;
    }
    /**
     * <p>amsSort をセットします。
     * @param amsSort amsSort
     */
    public void setAmsSort(int amsSort) {
        amsSort__ = amsSort;
    }

}
