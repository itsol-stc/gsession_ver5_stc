package jp.groupsession.v2.tcd.model;

import jp.co.sjts.util.date.UDate;


/**
 * <br>[機  能] タイムカード時間保持用モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdTimeModel {

    /** 開始日付 */
    private UDate tcdFrDate__;
    /** 終了日付 */
    private UDate tcdToDate__;
    /** 開始日付 */
    private int tcdFrMin__;
    /** 開始日付 */
    private int tcdToMin__;
    /**
     * <p>tcdFrDate を取得します。
     * @return tcdFrDate
     * @see jp.groupsession.v2.tcd.TcdTimeModel#tcdFrDate__
     */
    public UDate getTcdFrDate() {
        return tcdFrDate__;
    }
    /**
     * <p>tcdFrDate をセットします。
     * @param tcdFrDate tcdFrDate
     * @see jp.groupsession.v2.tcd.TcdTimeModel#tcdFrDate__
     */
    public void setTcdFrDate(UDate tcdFrDate) {
        tcdFrDate__ = tcdFrDate;
    }
    /**
     * <p>tcdToDate を取得します。
     * @return tcdToDate
     * @see jp.groupsession.v2.tcd.TcdTimeModel#tcdToDate__
     */
    public UDate getTcdToDate() {
        return tcdToDate__;
    }
    /**
     * <p>tcdToDate をセットします。
     * @param tcdToDate tcdToDate
     * @see jp.groupsession.v2.tcd.TcdTimeModel#tcdToDate__
     */
    public void setTcdToDate(UDate tcdToDate) {
        tcdToDate__ = tcdToDate;
    }
    /**
     * <p>tcdFrMin を取得します。
     * @return tcdFrMin
     * @see jp.groupsession.v2.tcd.TcdTimeModel#tcdFrMin__
     */
    public int getTcdFrMin() {
        return tcdFrMin__;
    }
    /**
     * <p>tcdFrMin をセットします。
     * @param tcdFrMin tcdFrMin
     * @see jp.groupsession.v2.tcd.TcdTimeModel#tcdFrMin__
     */
    public void setTcdFrMin(int tcdFrMin) {
        tcdFrMin__ = tcdFrMin;
    }
    /**
     * <p>tcdToMin__ を取得します。
     * @return tcdToMin__
     * @see jp.groupsession.v2.tcd.TcdTimeModel#tcdToMin__
     */
    public int getTcdToMin() {
        return tcdToMin__;
    }
    /**
     * <p>tcdToMin__ をセットします。
     * @param tcdToMin tcdToMin_
     * @see jp.groupsession.v2.tcd.TcdTimeModel#tcdToMin__
     */
    public void setTcdToMin(int tcdToMin) {
        tcdToMin__ = tcdToMin;
    }

}