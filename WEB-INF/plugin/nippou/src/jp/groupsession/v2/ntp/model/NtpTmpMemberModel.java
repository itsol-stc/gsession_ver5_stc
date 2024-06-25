package jp.groupsession.v2.ntp.model;

import java.io.Serializable;

/**
 * <p>NTP_TEMPLATE_MEMBER Data Bindding JavaBean
 *
 */
public class NtpTmpMemberModel implements Serializable {

    /** NSM_USR_SID mapping */
    private int ntmTmpSid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /**
     * <p>ntmTmpSid を取得します。
     * @return ntmTmpSid
     */
    public int getNtmTmpSid() {
        return ntmTmpSid__;
    }
    /**
     * <p>ntmTmpSid をセットします。
     * @param ntmTmpSid ntmTmpSid
     */
    public void setNtmTmpSid(int ntmTmpSid) {
        ntmTmpSid__ = ntmTmpSid;
    }
    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public int getGrpSid() {
        return grpSid__;
    }
    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
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
