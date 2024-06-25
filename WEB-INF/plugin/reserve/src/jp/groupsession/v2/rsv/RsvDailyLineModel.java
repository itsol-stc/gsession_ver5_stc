package jp.groupsession.v2.rsv;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設別のスケジュールタイムチャートを行別に格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvDailyLineModel extends AbstractModel {

    /** 予約1行分のRsvDailyValueModelを格納 */
    private ArrayList<RsvDailyValueModel> lineList__;

    /**
     * <p>lineList__ を取得します。
     * @return lineList
     */
    public ArrayList<RsvDailyValueModel> getLineList() {
        return lineList__;
    }
    /**
     * <p>lineList__ をセットします。
     * @param lineList lineList__
     */
    public void setLineList(ArrayList<RsvDailyValueModel> lineList) {
        lineList__ = lineList;
    }
}