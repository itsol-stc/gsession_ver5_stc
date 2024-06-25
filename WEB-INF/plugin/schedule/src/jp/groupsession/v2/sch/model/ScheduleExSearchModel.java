package jp.groupsession.v2.sch.model;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] スケジュール拡張情報、およびユーザ情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ScheduleExSearchModel extends SchExdataModel {

    /** スケジュールSID */
    private int scdSid__;
    /** ユーザSID(被登録者) */
    private int scdUsrSid__;
    /** 被登録者有効フラグ */
    private int addedUsrUkoFlg__;
    /** 登録者有効フラグ */
    private int addUsrUkoFlg__;
    /** ユーザ区分 */
    private int scdUsrKbn__;
    /** スケジュールグループSID */
    private int scdGrpSid__;

    /** スケジュールユーザ　姓*/
    private String sceUsrSei__;
    /** スケジュールユーザ　名*/
    private String sceUsrMei__;
    /** スケジュール登録者　姓*/
    private String sceAuidSei__;
    /** スケジュール登録者　名*/
    private String sceAuidMei__;
    /** ユーザ状態区分 */
    private int sceUsrJkbn__;
    /** 登録者状態区分 */
    private int sceAuidJkbn__;
    /** 同時登録ユーザ情報 */
    private ArrayList<CmnUsrmInfModel> usrInfList__;

    /**
     * <p>scdGrpSid を取得します。
     * @return scdGrpSid
     */
    public int getScdGrpSid() {
        return scdGrpSid__;
    }
    /**
     * <p>scdGrpSid をセットします。
     * @param scdGrpSid scdGrpSid
     */
    public void setScdGrpSid(int scdGrpSid) {
        scdGrpSid__ = scdGrpSid;
    }
    /**
     * <p>scdSid を取得します。
     * @return scdSid
     */
    public int getScdSid() {
        return scdSid__;
    }
    /**
     * <p>scdSid をセットします。
     * @param scdSid scdSid
     */
    public void setScdSid(int scdSid) {
        scdSid__ = scdSid;
    }
    /**
     * <p>scdUsrKbn を取得します。
     * @return scdUsrKbn
     */
    public int getScdUsrKbn() {
        return scdUsrKbn__;
    }
    /**
     * <p>scdUsrKbn をセットします。
     * @param scdUsrKbn scdUsrKbn
     */
    public void setScdUsrKbn(int scdUsrKbn) {
        scdUsrKbn__ = scdUsrKbn;
    }
    /**
     * <p>scdUsrSid を取得します。
     * @return scdUsrSid
     */
    public int getScdUsrSid() {
        return scdUsrSid__;
    }
    /**
     * <p>scdUsrSid をセットします。
     * @param scdUsrSid scdUsrSid
     */
    public void setScdUsrSid(int scdUsrSid) {
        scdUsrSid__ = scdUsrSid;
    }
    /**
     * <p>sceAuidJkbn を取得します。
     * @return sceAuidJkbn
     */
    public int getSceAuidJkbn() {
        return sceAuidJkbn__;
    }
    /**
     * <p>sceAuidJkbn をセットします。
     * @param sceAuidJkbn sceAuidJkbn
     */
    public void setSceAuidJkbn(int sceAuidJkbn) {
        sceAuidJkbn__ = sceAuidJkbn;
    }
    /**
     * <p>sceAuidMei を取得します。
     * @return sceAuidMei
     */
    public String getSceAuidMei() {
        return sceAuidMei__;
    }
    /**
     * <p>sceAuidMei をセットします。
     * @param sceAuidMei sceAuidMei
     */
    public void setSceAuidMei(String sceAuidMei) {
        sceAuidMei__ = sceAuidMei;
    }
    /**
     * <p>sceAuidSei を取得します。
     * @return sceAuidSei
     */
    public String getSceAuidSei() {
        return sceAuidSei__;
    }
    /**
     * <p>sceAuidSei をセットします。
     * @param sceAuidSei sceAuidSei
     */
    public void setSceAuidSei(String sceAuidSei) {
        sceAuidSei__ = sceAuidSei;
    }
    /**
     * <p>sceUsrJkbn を取得します。
     * @return sceUsrJkbn
     */
    public int getSceUsrJkbn() {
        return sceUsrJkbn__;
    }
    /**
     * <p>sceUsrJkbn をセットします。
     * @param sceUsrJkbn sceUsrJkbn
     */
    public void setSceUsrJkbn(int sceUsrJkbn) {
        sceUsrJkbn__ = sceUsrJkbn;
    }
    /**
     * <p>sceUsrMei を取得します。
     * @return sceUsrMei
     */
    public String getSceUsrMei() {
        return sceUsrMei__;
    }
    /**
     * <p>sceUsrMei をセットします。
     * @param sceUsrMei sceUsrMei
     */
    public void setSceUsrMei(String sceUsrMei) {
        sceUsrMei__ = sceUsrMei;
    }
    /**
     * <p>sceUsrSei を取得します。
     * @return sceUsrSei
     */
    public String getSceUsrSei() {
        return sceUsrSei__;
    }
    /**
     * <p>sceUsrSei をセットします。
     * @param sceUsrSei sceUsrSei
     */
    public void setSceUsrSei(String sceUsrSei) {
        sceUsrSei__ = sceUsrSei;
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

    /**
     * <p>addedUsrUkoFlg を取得します。
     * @return addedUsrUkoFlg
     */
    public int getAddedUsrUkoFlg() {
        return addedUsrUkoFlg__;
    }
    /**
     * <p>addedUsrUkoFlg をセットします。
     * @param addedUsrUkoFlg addedUsrUkoFlg
     */
    public void setAddedUsrUkoFlg(int addedUsrUkoFlg) {
        addedUsrUkoFlg__ = addedUsrUkoFlg;
    }
    /**
     * <p>addUsrUkoFlg を取得します。
     * @return addUsrUkoFlg
     */
    public int getAddUsrUkoFlg() {
        return addUsrUkoFlg__;
    }
    /**
     * <p>addUsrUkoFlg をセットします。
     * @param addUsrUkoFlg addUsrUkoFlg
     */
    public void setAddUsrUkoFlg(int addUsrUkoFlg) {
        addUsrUkoFlg__ = addUsrUkoFlg;
    }

}
