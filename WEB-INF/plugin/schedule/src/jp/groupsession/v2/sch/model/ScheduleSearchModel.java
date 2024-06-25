package jp.groupsession.v2.sch.model;

import java.util.ArrayList;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] スケジュール情報、およびユーザ情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ScheduleSearchModel extends SchDataModel {

    /** スケジュールユーザ　姓*/
    private String scdUsrSei__;
    /** スケジュールユーザ　名*/
    private String scdUsrMei__;
    /** スケジュールユーザ有効フラグ */
    private int scdUsrUkoFlg__;
    /** スケジュール登録者　姓*/
    private String scdAuidSei__;
    /** スケジュール登録者　名*/
    private String scdAuidMei__;
    /** ユーザ状態区分 */
    private int scdUsrJkbn__;
    /** 登録者状態区分 */
    private int scdAuidJkbn__;
    /** 登録日時 */
    private UDate scdAdate__;
    /** 登録ユーザ有効フラグ */
    private int registUsrUkoFlg__;
    /** 同時登録ユーザ情報 */
    private ArrayList<CmnUsrmInfModel> usrInfList__;


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
     * @return scdAuidJkbn を戻します。
     */
    public int getScdAuidJkbn() {
        return scdAuidJkbn__;
    }
    /**
     * @param scdAuidJkbn 設定する scdAuidJkbn。
     */
    public void setScdAuidJkbn(int scdAuidJkbn) {
        scdAuidJkbn__ = scdAuidJkbn;
    }
    /**
     * @return scdUsrJkbn を戻します。
     */
    public int getScdUsrJkbn() {
        return scdUsrJkbn__;
    }
    /**
     * @param scdUsrJkbn 設定する scdUsrJkbn。
     */
    public void setScdUsrJkbn(int scdUsrJkbn) {
        scdUsrJkbn__ = scdUsrJkbn;
    }
    /**
     * @return scdAuidMei を戻します。
     */
    public String getScdAuidMei() {
        return scdAuidMei__;
    }
    /**
     * @param scdAuidMei 設定する scdAuidMei。
     */
    public void setScdAuidMei(String scdAuidMei) {
        scdAuidMei__ = scdAuidMei;
    }
    /**
     * @return scdAuidSei を戻します。
     */
    public String getScdAuidSei() {
        return scdAuidSei__;
    }
    /**
     * @param scdAuidSei 設定する scdAuidSei。
     */
    public void setScdAuidSei(String scdAuidSei) {
        scdAuidSei__ = scdAuidSei;
    }
    /**
     * @return scdUsrMei を戻します。
     */
    public String getScdUsrMei() {
        return scdUsrMei__;
    }
    /**
     * @param scdUsrMei 設定する scdUsrMei。
     */
    public void setScdUsrMei(String scdUsrMei) {
        scdUsrMei__ = scdUsrMei;
    }
    /**
     * @return scdUsrSei を戻します。
     */
    public String getScdUsrSei() {
        return scdUsrSei__;
    }
    /**
     * @param scdUsrSei 設定する scdUsrSei。
     */
    public void setScdUsrSei(String scdUsrSei) {
        scdUsrSei__ = scdUsrSei;
    }
    /**
     * <p>scdAdate を取得します。
     * @return scdAdate
     */
    public UDate getScdAdate() {
        return scdAdate__;
    }
    /**
     * <p>scdAdate をセットします。
     * @param scdAdate scdAdate
     */
    public void setScdAdate(UDate scdAdate) {
        scdAdate__ = scdAdate;
    }
    /**
     * <p>registUsrUkoFlg を取得します。
     * @return registUsrUkoFlg
     */
    public int getRegistUsrUkoFlg() {
        return registUsrUkoFlg__;
    }
    /**
     * <p>registUsrUkoFlg をセットします。
     * @param registUsrUkoFlg registUsrUkoFlg
     */
    public void setRegistUsrUkoFlg(int registUsrUkoFlg) {
        registUsrUkoFlg__ = registUsrUkoFlg;
    }
    /**
     * <p>scdUsrUkoFlg を取得します。
     * @return scdUsrUkoFlg
     */
    public int getScdUsrUkoFlg() {
        return scdUsrUkoFlg__;
    }
    /**
     * <p>scdUsrUkoFlg をセットします。
     * @param scdUsrUkoFlg scdUsrUkoFlg
     */
    public void setScdUsrUkoFlg(int scdUsrUkoFlg) {
        scdUsrUkoFlg__ = scdUsrUkoFlg;
    }
}