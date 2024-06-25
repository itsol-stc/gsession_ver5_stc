package jp.groupsession.v2.rsv;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設予約 予約情報(1週間)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvWeekModel extends AbstractModel {

    /** 予約情報リスト1日 */
    private ArrayList<RsvYoyakuDayModel> yoyakuDayList__ = null;

    /**
     * <p>yoyakuDayList__ を取得します。
     * @return yoyakuDayList
     */
    public ArrayList<RsvYoyakuDayModel> getYoyakuDayList() {
        return yoyakuDayList__;
    }
    /**
     * <p>yoyakuDayList__ をセットします。
     * @param yoyakuDayList yoyakuDayList__
     */
    public void setYoyakuDayList(ArrayList<RsvYoyakuDayModel> yoyakuDayList) {
        yoyakuDayList__ = yoyakuDayList;
    }
}