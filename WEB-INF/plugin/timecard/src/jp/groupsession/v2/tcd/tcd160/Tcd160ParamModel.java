package jp.groupsession.v2.tcd.tcd160;

import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;
import jp.groupsession.v2.tcd.tcd030.Tcd030ParamModel;

/**
 * <br>[機  能] タイムカード 管理者設定 休日区分設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd160ParamModel extends Tcd030ParamModel {

    /**  休日区分情報編集モード */
    private int tcd170Mode__ = GSConst.CMDMODE_ADD;
    /** 編集対象 休日区分SID */
    private int tcd170EditSid__ = -1;

    /** 時間帯リスト */
    private List<TcdHolidayInfModel> tcd160HolidayList__;
    /** チェックされている並び順 */
    private String tcd160Order= null;

    /**
     * <p>tcd170Mode を取得します。
     * @return tcd170Mode
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd170Mode__
     */
    public int getTcd170Mode() {
        return tcd170Mode__;
    }

    /**
     * <p>tcd170Mode をセットします。
     * @param tcd170Mode tcd170Mode
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd170Mode__
     */
    public void setTcd170Mode(int tcd170Mode) {
        tcd170Mode__ = tcd170Mode;
    }

    /**
     * <p>tcd170EditSid を取得します。
     * @return tcd170EditSid
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd170EditSid__
     */
    public int getTcd170EditSid() {
        return tcd170EditSid__;
    }

    /**
     * <p>tcd170EditSid をセットします。
     * @param tcd170EditSid tcd170EditSid
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd170EditSid__
     */
    public void setTcd170EditSid(int tcd170EditSid) {
        tcd170EditSid__ = tcd170EditSid;
    }

    /**
     * <p>tcd160HolidayList を取得します。
     * @return tcd160HolidayList
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd160HolidayList__
     */
    public List<TcdHolidayInfModel> getTcd160HolidayList() {
        return tcd160HolidayList__;
    }

    /**
     * <p>tcd160HolidayList をセットします。
     * @param tcd160HolidayList tcd160HolidayList
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd160HolidayList__
     */
    public void setTcd160HolidayList(List<TcdHolidayInfModel> tcd160HolidayList) {
        tcd160HolidayList__ = tcd160HolidayList;
    }

    /**
     * <p>tcd160Order を取得します。
     * @return tcd160Order
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd160Order
     */
    public String getTcd160Order() {
        return tcd160Order;
    }

    /**
     * <p>tcd160Order をセットします。
     * @param tcd160Order tcd160Order
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd160Order
     */
    public void setTcd160Order(String tcd160Order) {
        this.tcd160Order = tcd160Order;
    }
}
