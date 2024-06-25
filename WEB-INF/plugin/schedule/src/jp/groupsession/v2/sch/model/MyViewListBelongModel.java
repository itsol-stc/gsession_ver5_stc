package jp.groupsession.v2.sch.model;

import java.io.Serializable;

/**
 * <br>[機  能] 表示リスト所属情報から取得したユーザ・グループ情報を保持するModel
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MyViewListBelongModel implements Serializable {
    /** 表示リストSID */
    private int smySid__;
    /** ユーザSID */
    private int usrSid__ = -1;
    /** ユーザ名 姓 */
    private String usrNameSei__ = null;
    /** ユーザ名 名 */
    private String usrNameMei__ = null;
    /** グループSID */
    private int grpSid__ = -1;
    /** グループ名 */
    private String grpName__ = null;
    /** 所属グループフラグ */
    private boolean belongGrpFlg__ = false;

    /**
     * <p>smySid を取得します。
     * @return smySid
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#smySid
     */
    public int getSmySid() {
        return smySid__;
    }
    /**
     * <p>smySid をセットします。
     * @param smySid smySid
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#smySid__
     */
    public void setSmySid(int smySid) {
        smySid__ = smySid;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>usrNameSei を取得します。
     * @return usrNameSei
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#usrNameSei
     */
    public String getUsrNameSei() {
        return usrNameSei__;
    }
    /**
     * <p>usrNameSei をセットします。
     * @param usrNameSei usrNameSei
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#usrNameSei
     */
    public void setUsrNameSei(String usrNameSei) {
        usrNameSei__ = usrNameSei;
    }
    /**
     * <p>usrNameMei を取得します。
     * @return usrNameMei
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#usrNameMei__
     */
    public String getUsrNameMei() {
        return usrNameMei__;
    }
    /**
     * <p>usrNameMei をセットします。
     * @param usrNameMei usrNameMei
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#usrNameMei
     */
    public void setUsrNameMei(String usrNameMei) {
        usrNameMei__ = usrNameMei;
    }
    /**
     * <p>grpSid を取得します。
     * @return grpSid
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#grpSid
     */
    public int getGrpSid() {
        return grpSid__;
    }
    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#grpSid
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }
    /**
     * <p>grpName を取得します。
     * @return grpName
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#grpName
     */
    public String getGrpName() {
        return grpName__;
    }
    /**
     * <p>grpName をセットします。
     * @param grpName grpName
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#grpName
     */
    public void setGrpName(String grpName) {
        grpName__ = grpName;
    }
    /**
     * <p>belongGrpFlg を取得します。
     * @return belongGrpFlg
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#belongGrpFlg
     */
    public boolean isBelongGrpFlg() {
        return belongGrpFlg__;
    }
    /**
     * <p>belongGrpFlg をセットします。
     * @param belongGrpFlg belongGrpFlg
     * @see jp.groupsession.v2.sch.model.MyViewListBelongModel#belongGrpFlg
     */
    public void setBelongGrpFlg(boolean belongGrpFlg) {
        belongGrpFlg__ = belongGrpFlg;
    }
}
