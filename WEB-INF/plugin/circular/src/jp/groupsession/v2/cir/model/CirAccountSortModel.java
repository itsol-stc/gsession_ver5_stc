package jp.groupsession.v2.cir.model;

import java.io.Serializable;

/**
 * <p>CIR_ACCOUNT_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirAccountSortModel implements Serializable {

    /** SAC_SID mapping */
    private int cacSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** SAS_SORT mapping */
    private long casSort__;
    /**
     * <p>cacSid を取得します。
     * @return cacSid
     */
    public int getCacSid() {
        return cacSid__;
    }
    /**
     * <p>cacSid をセットします。
     * @param cacSid cacSid
     */
    public void setCacSid(int cacSid) {
        cacSid__ = cacSid;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>casSort を取得します。
     * @return casSort
     */
    public long getCasSort() {
        return casSort__;
    }
    /**
     * <p>casSort をセットします。
     * @param casSort casSort
     */
    public void setCasSort(long casSort) {
        casSort__ = casSort;
    }

}
