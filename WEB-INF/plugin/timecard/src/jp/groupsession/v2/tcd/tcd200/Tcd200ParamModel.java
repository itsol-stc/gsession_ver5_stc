package jp.groupsession.v2.tcd.tcd200;

import jp.groupsession.v2.tcd.tcd190.Tcd190ParamModel;
/**
 * <br>[機  能] タイムカード 有休日数登録,編集画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd200ParamModel extends Tcd190ParamModel {

    /** グループ */
    private int tcd200Group__ = 0;
    /** ユーザ */
    private int tcd200Name__ = 0;
    /** 年度 */
    private int tcd200Nendo__;
    /** 有休日数 */
    private String tcd200YukyuDays__ = null;
    
    /** 初期表示フラグ */
    private int tcd200initFlg__;

    /**
     * <p>tcd200Group を取得します。
     * @return tcd200Group
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200Group__
     */
    public int getTcd200Group() {
        return tcd200Group__;
    }

    /**
     * <p>tcd200Group をセットします。
     * @param tcd200Group tcd200Group
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200Group__
     */
    public void setTcd200Group(int tcd200Group) {
        tcd200Group__ = tcd200Group;
    }

    /**
     * <p>tcd200Name を取得します。
     * @return tcd200Name
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200Name__
     */
    public int getTcd200Name() {
        return tcd200Name__;
    }

    /**
     * <p>tcd200Name をセットします。
     * @param tcd200Name tcd200Name
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200Name__
     */
    public void setTcd200Name(int tcd200Name) {
        tcd200Name__ = tcd200Name;
    }

    /**
     * <p>tcd200Nendo を取得します。
     * @return tcd200Nendo
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200Nendo__
     */
    public int getTcd200Nendo() {
        return tcd200Nendo__;
    }

    /**
     * <p>tcd200Nendo をセットします。
     * @param tcd200Nendo tcd200Nendo
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200Nendo__
     */
    public void setTcd200Nendo(int tcd200Nendo) {
        tcd200Nendo__ = tcd200Nendo;
    }

    /**
     * <p>tcd200YukyuDays を取得します。
     * @return tcd200YukyuDays
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200YukyuDays__
     */
    public String getTcd200YukyuDays() {
        return tcd200YukyuDays__;
    }

    /**
     * <p>tcd200YukyuDays をセットします。
     * @param tcd200YukyuDays tcd200YukyuDays
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200YukyuDays__
     */
    public void setTcd200YukyuDays(String tcd200YukyuDays) {
        tcd200YukyuDays__ = tcd200YukyuDays;
    }

    /**
     * <p>tcd200initFlg を取得します。
     * @return tcd200initFlg
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200initFlg__
     */
    public int getTcd200initFlg() {
        return tcd200initFlg__;
    }

    /**
     * <p>tcd200initFlg をセットします。
     * @param tcd200initFlg tcd200initFlg
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200initFlg__
     */
    public void setTcd200initFlg(int tcd200initFlg) {
        tcd200initFlg__ = tcd200initFlg;
    }
}
