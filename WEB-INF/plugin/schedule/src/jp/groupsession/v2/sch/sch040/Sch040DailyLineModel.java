package jp.groupsession.v2.sch.sch040;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ユーザ別のスケジュールタイムチャートを行別に格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch040DailyLineModel extends AbstractModel {

    /** スケジュール１行分のSch040DailyValueModelを格納 */
    private ArrayList < Sch040DailyValueModel > lineList__;

    /**
     * @return lineList を戻します。
     */
    public ArrayList < Sch040DailyValueModel > getLineList() {
        return lineList__;
    }

    /**
     * @param lineList 設定する lineList。
     */
    public void setLineList(ArrayList < Sch040DailyValueModel > lineList) {
        lineList__ = lineList;
    }

}
