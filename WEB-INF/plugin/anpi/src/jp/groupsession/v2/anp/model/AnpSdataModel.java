package jp.groupsession.v2.anp.model;

import java.io.Serializable;

/**
 * <p>ANP_SDATA Data Bindding JavaBean
 *
 * @author JTS
 */
public class AnpSdataModel implements Serializable {

    /** APH_SID mapping*/
    private int aphSid__;
    /** APS_TYPE mapping*/
    private int apsType__;
    /** USR_SID mapping*/
    private int usrSid__;
    /** GRP_SID mapping*/
    private int grpSid__;

    /**
     * <p>aphSid を取得します。
     * @return aphSid
     */
    public int getAphSid() {
        return aphSid__;
    }
    /**
     * <p>aphSid をセットします。
     * @param aphSid aphSid
     */
    public void setAphSid(int aphSid) {
        aphSid__ = aphSid;
    }
    /**
     * <p>apsType を取得します。
     * @return apsType
     */
    public int getApsType() {
        return apsType__;
    }
    /**
     * <p>apsType をセットします。
     * @param apsType apsType
     */
    public void setApsType(int apsType) {
        apsType__ = apsType;
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
}
