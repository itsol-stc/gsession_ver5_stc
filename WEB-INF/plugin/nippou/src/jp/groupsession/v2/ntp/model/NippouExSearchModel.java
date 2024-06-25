package jp.groupsession.v2.ntp.model;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] 日報拡張情報、ユーザ名を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NippouExSearchModel extends NtpExtenedModel {

    /** 日報SID */
    private int nipSid__;
    /** ユーザSID(被登録者) */
    private int nipUsrSid__;

    /** 日報ユーザ　姓*/
    private String nexUsrSei__;
    /** 日報ユーザ　名*/
    private String nexUsrMei__;
    /** 日報登録者　姓*/
    private String nexAuidSei__;
    /** スケジュール登録者　名*/
    private String nexAuidMei__;
    /** ユーザ状態区分 */
    private int nexUsrJkbn__;
    /** 登録者状態区分 */
    private int nexAuidJkbn__;
    /** 同時登録ユーザ情報 */
    private ArrayList<CmnUsrmInfModel> usrInfList__;

    /**
     * <p>nipSid を取得します。
     * @return nipSid
     */
    public int getNipSid() {
        return nipSid__;
    }
    /**
     * <p>nipSid をセットします。
     * @param nipSid nipSid
     */
    public void setNipSid(int nipSid) {
        nipSid__ = nipSid;
    }
    /**
     * <p>nipUsrSid を取得します。
     * @return nipUsrSid
     */
    public int getNipUsrSid() {
        return nipUsrSid__;
    }
    /**
     * <p>nipUsrSid をセットします。
     * @param nipUsrSid nipUsrSid
     */
    public void setNipUsrSid(int nipUsrSid) {
        nipUsrSid__ = nipUsrSid;
    }
    /**
     * <p>nexAuidJkbn を取得します。
     * @return nexAuidJkbn
     */
    public int getNexAuidJkbn() {
        return nexAuidJkbn__;
    }
    /**
     * <p>nexAuidJkbn をセットします。
     * @param nexAuidJkbn nexAuidJkbn
     */
    public void setNexAuidJkbn(int nexAuidJkbn) {
        nexAuidJkbn__ = nexAuidJkbn;
    }
    /**
     * <p>nexAuidMei を取得します。
     * @return nexAuidMei
     */
    public String getNexAuidMei() {
        return nexAuidMei__;
    }
    /**
     * <p>nexAuidMei をセットします。
     * @param nexAuidMei nexAuidMei
     */
    public void setNexAuidMei(String nexAuidMei) {
        nexAuidMei__ = nexAuidMei;
    }
    /**
     * <p>nexAuidSei を取得します。
     * @return nexAuidSei
     */
    public String getNexAuidSei() {
        return nexAuidSei__;
    }
    /**
     * <p>nexAuidSei をセットします。
     * @param nexAuidSei nexAuidSei
     */
    public void setNexAuidSei(String nexAuidSei) {
        nexAuidSei__ = nexAuidSei;
    }
    /**
     * <p>nexUsrJkbn を取得します。
     * @return nexUsrJkbn
     */
    public int getNexUsrJkbn() {
        return nexUsrJkbn__;
    }
    /**
     * <p>nexUsrJkbn をセットします。
     * @param nexUsrJkbn nexUsrJkbn
     */
    public void setNexUsrJkbn(int nexUsrJkbn) {
        nexUsrJkbn__ = nexUsrJkbn;
    }
    /**
     * <p>nexUsrMei を取得します。
     * @return nexUsrMei
     */
    public String getNexUsrMei() {
        return nexUsrMei__;
    }
    /**
     * <p>nexUsrMei をセットします。
     * @param nexUsrMei nexUsrMei
     */
    public void setNexUsrMei(String nexUsrMei) {
        nexUsrMei__ = nexUsrMei;
    }
    /**
     * <p>nexUsrSei を取得します。
     * @return nexUsrSei
     */
    public String getNexUsrSei() {
        return nexUsrSei__;
    }
    /**
     * <p>nexUsrSei をセットします。
     * @param nexUsrSei nexUsrSei
     */
    public void setNexUsrSei(String nexUsrSei) {
        nexUsrSei__ = nexUsrSei;
    }
    /**
     * <p>usrInfList を取得します。
     * @return usrInfList
     */
    public ArrayList<CmnUsrmInfModel> getUsrInfList() {
        return usrInfList__;
    }
    /**
     * <p>usrInfList をセットします。
     * @param usrInfList usrInfList
     */
    public void setUsrInfList(ArrayList<CmnUsrmInfModel> usrInfList) {
        usrInfList__ = usrInfList;
    }
}
