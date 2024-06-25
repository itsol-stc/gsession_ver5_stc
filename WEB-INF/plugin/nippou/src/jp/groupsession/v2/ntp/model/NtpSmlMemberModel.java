package jp.groupsession.v2.ntp.model;

import java.io.Serializable;

/**
 * <p>CMN_PLUGIN_CONTROL_MEMBER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpSmlMemberModel implements Serializable {

    /** NSM_USR_SID mapping */
    private int nsmUsrSid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** USR_SID mapping */
    private int nsmGrpKbn__;
    /**
     * <p>nsmUsrSid を取得します。
     * @return nsmUsrSid
     */
    public int getNsmUsrSid() {
        return nsmUsrSid__;
    }
    /**
     * <p>nsmUsrSid をセットします。
     * @param nsmUsrSid nsmUsrSid
     */
    public void setNsmUsrSid(int nsmUsrSid) {
        nsmUsrSid__ = nsmUsrSid;
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
    /**
     * <p>nsmGrpKbn を取得します。
     * @return nsmGrpKbn
     */
    public int getNsmGrpKbn() {
        return nsmGrpKbn__;
    }
    /**
     * <p>nsmGrpKbn をセットします。
     * @param nsmGrpKbn nsmGrpKbn
     */
    public void setNsmGrpKbn(int nsmGrpKbn) {
        nsmGrpKbn__ = nsmGrpKbn;
    }


}
