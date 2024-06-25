package jp.groupsession.v2.sch.main;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.sch.model.SimpleCalenderModel;
import jp.groupsession.v2.sch.sch010.Sch010WeekOfModel;

/**
 * <br>[機  能] スケジュール(メイン画面表示用) 1週間のスケジュール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ScheduleWeekModel extends AbstractModel {

    /** 表示年月 */
    private String schWeekDspYm__;
    /** 週間カレンダー */
    private ArrayList<SimpleCalenderModel> schWeekCalendarList__ = null;
    /** スケジュールリスト */
    private ArrayList<Sch010WeekOfModel> schWeekTopList__ = null;

    /**
     * @return schWeekDspYm を戻します。
     */
    public String getSchWeekDspYm() {
        return schWeekDspYm__;
    }
    /**
     * @param schWeekDspYm 設定する schWeekDspYm。
     */
    public void setSchWeekDspYm(String schWeekDspYm) {
        schWeekDspYm__ = schWeekDspYm;
    }
    /**
     * @return schWeekCalendarList を戻します。
     */
    public ArrayList<SimpleCalenderModel> getSchWeekCalendarList() {
        return schWeekCalendarList__;
    }
    /**
     * @param schWeekCalendarList 設定する schWeekCalendarList。
     */
    public void setSchWeekCalendarList(ArrayList<SimpleCalenderModel> schWeekCalendarList) {
        schWeekCalendarList__ = schWeekCalendarList;
    }
    /**
     * @return schWeekTopList を戻します。
     */
    public ArrayList<Sch010WeekOfModel> getSchWeekTopList() {
        return schWeekTopList__;
    }
    /**
     * @param schWeekTopList 設定する schWeekTopList。
     */
    public void setSchWeekTopList(ArrayList<Sch010WeekOfModel> schWeekTopList) {
        schWeekTopList__ = schWeekTopList;
    }

}
