package jp.groupsession.v2.rsv.model.other;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] スケジュール情報、およびユーザ名を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ScheduleRsvModel extends RsvSchDataModel {

    /** スケジュールユーザ　姓*/
    private String scdUsrSei__;
    /** スケジュールユーザ　名*/
    private String scdUsrMei__;
    /** スケジュール登録者　姓*/
    private String scdAuidSei__;
    /** スケジュール登録者　名*/
    private String scdAuidMei__;
    /** ユーザ状態区分 */
    private int scdUsrJkbn__;
    /** ユーザ無効フラグ */
    private int scdUsrUkoFlg__ = GSConst.YUKOMUKO_YUKO;
    /** 登録者状態区分 */
    private int scdAuidJkbn__;
    /** 登録者ユーザ無効フラグ */
    private int scdAuidUkoFlg__ = GSConst.YUKOMUKO_YUKO;
    /** 同時登録ユーザ情報 */
    private ArrayList<CmnUsrmInfModel> usrInfList__;
    /**
     * <p>scdAuidJkbn を取得します。
     * @return scdAuidJkbn
     */
    public int getScdAuidJkbn() {
        return scdAuidJkbn__;
    }
    /**
     * <p>scdAuidJkbn をセットします。
     * @param scdAuidJkbn scdAuidJkbn
     */
    public void setScdAuidJkbn(int scdAuidJkbn) {
        scdAuidJkbn__ = scdAuidJkbn;
    }
    /**
     * <p>scdAuidUkoFlg を取得します。
     * @return scdAuidUkoFlg
     */
    public int getScdAuidUkoFlg() {
        return scdAuidUkoFlg__;
    }
    /**
     * <p>scdAuidUkoFlg をセットします。
     * @param scdAuidUkoFlg scdAuidUkoFlg
     */
    public void setScdAuidUkoFlg(int scdAuidUkoFlg) {
        scdAuidUkoFlg__ = scdAuidUkoFlg;
    }
    /**
     * <p>scdAuidMei を取得します。
     * @return scdAuidMei
     */
    public String getScdAuidMei() {
        return scdAuidMei__;
    }
    /**
     * <p>scdAuidMei をセットします。
     * @param scdAuidMei scdAuidMei
     */
    public void setScdAuidMei(String scdAuidMei) {
        scdAuidMei__ = scdAuidMei;
    }
    /**
     * <p>scdAuidSei を取得します。
     * @return scdAuidSei
     */
    public String getScdAuidSei() {
        return scdAuidSei__;
    }
    /**
     * <p>scdAuidSei をセットします。
     * @param scdAuidSei scdAuidSei
     */
    public void setScdAuidSei(String scdAuidSei) {
        scdAuidSei__ = scdAuidSei;
    }
    /**
     * <p>scdUsrJkbn を取得します。
     * @return scdUsrJkbn
     */
    public int getScdUsrJkbn() {
        return scdUsrJkbn__;
    }
    /**
     * <p>scdUsrJkbn をセットします。
     * @param scdUsrJkbn scdUsrJkbn
     */
    public void setScdUsrJkbn(int scdUsrJkbn) {
        scdUsrJkbn__ = scdUsrJkbn;
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
    /**
     * <p>scdUsrMei を取得します。
     * @return scdUsrMei
     */
    public String getScdUsrMei() {
        return scdUsrMei__;
    }
    /**
     * <p>scdUsrMei をセットします。
     * @param scdUsrMei scdUsrMei
     */
    public void setScdUsrMei(String scdUsrMei) {
        scdUsrMei__ = scdUsrMei;
    }
    /**
     * <p>scdUsrSei を取得します。
     * @return scdUsrSei
     */
    public String getScdUsrSei() {
        return scdUsrSei__;
    }
    /**
     * <p>scdUsrSei をセットします。
     * @param scdUsrSei scdUsrSei
     */
    public void setScdUsrSei(String scdUsrSei) {
        scdUsrSei__ = scdUsrSei;
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