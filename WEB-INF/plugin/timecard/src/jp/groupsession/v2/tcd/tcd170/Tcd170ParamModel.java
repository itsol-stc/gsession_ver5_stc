package jp.groupsession.v2.tcd.tcd170;

import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.tcd.tcd160.Tcd160ParamModel;

/**
 * <br>[機  能] タイムカード 管理者設定 休日区分登録画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd170ParamModel extends Tcd160ParamModel {

    /** 休日区分名 */
    private String tcd170HolidayName__= null;
    /** 休日集計区分 */
    private int tcd170HoliTotalKbn__ = GSConstTimecard.HOL_KBN_YUUKYUU;
    /** 使用制限 */
    private int tcd170Limit__ = GSConstTimecard.USE_KBN_OK;
    /** 休日内容区分 */
    private int tcd170HoliContentKbn__ = GSConstTimecard.HOLIDAY_CONTENT_KBN_NG;
    /** 初期表示Flg */
    private int tcd170initFlg__ = 0;
    /** 使用チェックFlg */
    private int tcd170useHolFlg__ = 0;

    /**
     * <p>tcd170HolidayName を取得します。
     * @return tcd170HolidayName
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170HolidayName__
     */
    public String getTcd170HolidayName() {
        return tcd170HolidayName__;
    }

    /**
     * <p>tcd170HolidayName をセットします。
     * @param tcd170HolidayName tcd170HolidayName
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170HolidayName__
     */
    public void setTcd170HolidayName(String tcd170HolidayName) {
        tcd170HolidayName__ = tcd170HolidayName;
    }

    /**
     * <p>tcd170HoliTotalKbn を取得します。
     * @return tcd170HoliTotalKbn
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170HoliTotalKbn__
     */
    public int getTcd170HoliTotalKbn() {
        return tcd170HoliTotalKbn__;
    }

    /**
     * <p>tcd170HoliTotalKbn をセットします。
     * @param tcd170HoliTotalKbn tcd170HoliTotalKbn
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170HoliTotalKbn__
     */
    public void setTcd170HoliTotalKbn(int tcd170HoliTotalKbn) {
        tcd170HoliTotalKbn__ = tcd170HoliTotalKbn;
    }

    /**
     * <p>tcd170Limit を取得します。
     * @return tcd170Limit
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170Limit__
     */
    public int getTcd170Limit() {
        return tcd170Limit__;
    }

    /**
     * <p>tcd170Limit をセットします。
     * @param tcd170Limit tcd170Limit
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170Limit__
     */
    public void setTcd170Limit(int tcd170Limit) {
        tcd170Limit__ = tcd170Limit;
    }

    /**
     * <p>tcd170HoliContentKbn を取得します。
     * @return tcd170HoliContentKbn
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170ParamModel#tcd170HoliContentKbn__
     */
    public int getTcd170HoliContentKbn() {
        return tcd170HoliContentKbn__;
    }

    /**
     * <p>tcd170HoliContentKbn をセットします。
     * @param tcd170HoliContentKbn tcd170HoliContentKbn
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170ParamModel#tcd170HoliContentKbn__
     */
    public void setTcd170HoliContentKbn(int tcd170HoliContentKbn) {
        tcd170HoliContentKbn__ = tcd170HoliContentKbn;
    }

    /**
     * <p>tcd170initFlg を取得します。
     * @return tcd170initFlg
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170initFlg__
     */
    public int getTcd170initFlg() {
        return tcd170initFlg__;
    }

    /**
     * <p>tcd170initFlg をセットします。
     * @param tcd170initFlg tcd170initFlg
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170initFlg__
     */
    public void setTcd170initFlg(int tcd170initFlg) {
        tcd170initFlg__ = tcd170initFlg;
    }

    /**
     * <p>tcd170useHolFlg を取得します。
     * @return tcd170useHolFlg
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170useHolFlg__
     */
    public int getTcd170useHolFlg() {
        return tcd170useHolFlg__;
    }

    /**
     * <p>tcd170useHolFlg をセットします。
     * @param tcd170useHolFlg tcd170useHolFlg
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170useHolFlg__
     */
    public void setTcd170useHolFlg(int tcd170useHolFlg) {
        tcd170useHolFlg__ = tcd170useHolFlg;
    }
}