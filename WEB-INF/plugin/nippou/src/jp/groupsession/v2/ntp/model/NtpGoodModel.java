package jp.groupsession.v2.ntp.model;

import java.io.Serializable;

/**
 * <p>NtpGoodModel Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpGoodModel implements Serializable {

    /** NTP_SID mapping */
    private int ntpSid__;
    /** WAS_SORT mapping */
    private int usrSid__;
    /**
     * <p>ntpSid を取得します。
     * @return ntpSid
     */
    public int getNtpSid() {
        return ntpSid__;
    }
    /**
     * <p>ntpSid をセットします。
     * @param ntpSid ntpSid
     */
    public void setNtpSid(int ntpSid) {
        ntpSid__ = ntpSid;
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
}
