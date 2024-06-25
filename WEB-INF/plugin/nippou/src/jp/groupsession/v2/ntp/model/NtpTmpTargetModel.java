package jp.groupsession.v2.ntp.model;

import java.io.Serializable;

/**
 * <p>NTP_TEMPLATE_TARGET Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpTmpTargetModel implements Serializable {

    /** NTT_SID mapping */
    private int nttSid__;
    /** NTG_SID mapping */
    private int ntgSid__;
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

}
