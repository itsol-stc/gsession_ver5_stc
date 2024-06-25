package jp.groupsession.v2.sch.sch020;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.sch.sch010.SimpleScheduleModel;

/**
 * <br>[機  能] ユーザ毎のスケジュール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch020WeekOfModel extends AbstractModel {

    /** 期間スケジュール存在フラグ */
    private int sch020PeriodRow__ = GSConstSchedule.PERIOD_NOT_EXSIST;
    /** スケジュール情報リスト(期間) */
    private ArrayList<ArrayList<SimpleScheduleModel>> sch020NoTimeSchList__ = null;

    /**
     * <p>sch020PeriodRow を取得します。
     * @return sch020PeriodRow
     */
    public int getSch020PeriodRow() {
        return sch020PeriodRow__;
    }
    /**
     * <p>sch020PeriodRow をセットします。
     * @param sch020PeriodRow sch020PeriodRow
     */
    public void setSch020PeriodRow(int sch020PeriodRow) {
        sch020PeriodRow__ = sch020PeriodRow;
    }
    /**
     * <p>sch020NoTimeSchList を取得します。
     * @return sch020NoTimeSchList
     */
    public ArrayList<ArrayList<SimpleScheduleModel>> getSch020NoTimeSchList() {
        return sch020NoTimeSchList__;
    }
    /**
     * <p>sch020NoTimeSchList をセットします。
     * @param sch020NoTimeSchList sch020NoTimeSchList
     */
    public void setSch020NoTimeSchList(
            ArrayList<ArrayList<SimpleScheduleModel>> sch020NoTimeSchList) {
        sch020NoTimeSchList__ = sch020NoTimeSchList;
    }
}
