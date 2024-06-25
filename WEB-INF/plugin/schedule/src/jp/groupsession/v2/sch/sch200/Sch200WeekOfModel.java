package jp.groupsession.v2.sch.sch200;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.sch.sch010.Sch010UsrModel;

/**
 * <br>[機  能] 1週間毎のスケジュール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch200WeekOfModel extends AbstractModel {

    /** ユーザ情報 */
    private Sch010UsrModel sch200UsrMdl__ = null;
    /** スケジュール情報リスト */
    private ArrayList<Sch200DayOfModel> sch200SchList__ = null;

    /**
     * @return sch200SchList を戻します。
     */
    public ArrayList<Sch200DayOfModel> getSch200SchList() {
        return sch200SchList__;
    }
    /**
     * @param sch200SchList 設定する sch200SchList。
     */
    public void setSch200SchList(ArrayList<Sch200DayOfModel> sch200SchList) {
        sch200SchList__ = sch200SchList;
    }
    /**
     * @return sch200UsrMdl を戻します。
     */
    public Sch010UsrModel getSch200UsrMdl() {
        return sch200UsrMdl__;
    }
    /**
     * @param sch200UsrMdl 設定する sch200UsrMdl。
     */
    public void setSch200UsrMdl(Sch010UsrModel sch200UsrMdl) {
        sch200UsrMdl__ = sch200UsrMdl;
    }



}
