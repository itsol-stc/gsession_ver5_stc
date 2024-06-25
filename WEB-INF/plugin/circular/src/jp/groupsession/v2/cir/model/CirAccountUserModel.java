package jp.groupsession.v2.cir.model;

import java.io.Serializable;

/**
 * <p>CIR_ACCOUNT_USER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirAccountUserModel implements Serializable {

    /** CAC_SID mapping */
    private int cacSid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** USR_SID mapping */
    private int usrSid__;
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
