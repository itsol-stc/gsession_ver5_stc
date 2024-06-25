package jp.groupsession.v2.sch.ptl010;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.sch.model.SimpleCalenderModel;
import jp.groupsession.v2.sch.sch010.Sch010WeekOfModel;

/**
 * <br>[機  能] スケジュール ポートレット グループスケジュール週間画面へ表示するスケジュール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchPtl010Model extends AbstractModel {

    /** 表示年月 */
    private String schWeekDspYm__;
    /** 週間カレンダー */
    private ArrayList<SimpleCalenderModel> schWeekCalendarList__ = null;
    /** スケジュールリスト */
    private ArrayList<Sch010WeekOfModel> schWeekTopList__ = null;
    /**
     * <p>schWeekDspYm を取得します。
     * @return schWeekDspYm
     */
    public String getSchWeekDspYm() {
        return schWeekDspYm__;
    }
    /**
     * <p>schWeekDspYm をセットします。
     * @param schWeekDspYm schWeekDspYm
     */
    public void setSchWeekDspYm(String schWeekDspYm) {
        schWeekDspYm__ = schWeekDspYm;
    }
    /**
     * <p>schWeekCalendarList を取得します。
     * @return schWeekCalendarList
     */
    public ArrayList<SimpleCalenderModel> getSchWeekCalendarList() {
        return schWeekCalendarList__;
    }
    /**
     * <p>schWeekCalendarList をセットします。
     * @param schWeekCalendarList schWeekCalendarList
     */
    public void setSchWeekCalendarList(
            ArrayList<SimpleCalenderModel> schWeekCalendarList) {
        schWeekCalendarList__ = schWeekCalendarList;
    }
    /**
     * <p>schWeekTopList を取得します。
     * @return schWeekTopList
     */
    public ArrayList<Sch010WeekOfModel> getSchWeekTopList() {
        return schWeekTopList__;
    }
    /**
     * <p>schWeekTopList をセットします。
     * @param schWeekTopList schWeekTopList
     */
    public void setSchWeekTopList(ArrayList<Sch010WeekOfModel> schWeekTopList) {
        schWeekTopList__ = schWeekTopList;
    }

}
