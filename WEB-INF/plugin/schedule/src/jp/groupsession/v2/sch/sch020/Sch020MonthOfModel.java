package jp.groupsession.v2.sch.sch020;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.sch.sch010.Sch010UsrModel;

/**
 * <br>[機  能] ユーザ毎のスケジュール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch020MonthOfModel extends AbstractModel {

    /** ユーザ情報 */
    private Sch010UsrModel sch020UsrMdl__ = null;
    /** スケジュール情報リスト */
    private ArrayList<Sch020DayOfModel> sch020SchList__ = null;
    /** 期間スケジュール情報リスト */
    private ArrayList<Sch020WeekOfModel> sch020PeriodSchList__ = null;

    /**
     * <p>sch020PeriodSchList を取得します。
     * @return sch020PeriodSchList
     */
    public ArrayList<Sch020WeekOfModel> getSch020PeriodSchList() {
        return sch020PeriodSchList__;
    }
    /**
     * <p>sch020PeriodSchList をセットします。
     * @param sch020PeriodSchList sch020PeriodSchList
     */
    public void setSch020PeriodSchList(
            ArrayList<Sch020WeekOfModel> sch020PeriodSchList) {
        sch020PeriodSchList__ = sch020PeriodSchList;
    }
    /**
     * @return sch020SchList を戻します。
     */
    public ArrayList<Sch020DayOfModel> getSch020SchList() {
        return sch020SchList__;
    }
    /**
     * @param sch020SchList 設定する sch020SchList。
     */
    public void setSch020SchList(ArrayList<Sch020DayOfModel> sch020SchList) {
        sch020SchList__ = sch020SchList;
    }
    /**
     * @return sch020UsrMdl を戻します。
     */
    public Sch010UsrModel getSch020UsrMdl() {
        return sch020UsrMdl__;
    }
    /**
     * @param sch020UsrMdl 設定する sch020UsrMdl。
     */
    public void setSch020UsrMdl(Sch010UsrModel sch020UsrMdl) {
        sch020UsrMdl__ = sch020UsrMdl;
    }



}
