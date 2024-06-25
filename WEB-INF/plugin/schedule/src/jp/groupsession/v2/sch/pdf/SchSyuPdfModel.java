package jp.groupsession.v2.sch.pdf;

import java.util.ArrayList;

import jp.groupsession.v2.sch.model.SimpleCalenderModel;
import jp.groupsession.v2.sch.sch010.Sch010WeekOfModel;

/**
 * <br>[機  能] スケジュール週間PDFへ出力する情報を格納するModelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchSyuPdfModel {

    /** ファイル名 */
    private String fileName__ = null;
    /** 年月 */
    private String headDate__ = null;
    /** 表示グループ */
    private String dispGroup__ = null;
    /** 週間カレンダー */
    private ArrayList<SimpleCalenderModel> calendarList__ = null;
    /** スケジュール上段リスト */
    private ArrayList<Sch010WeekOfModel> topList__ = null;
    /** スケジュール下段リスト */
    private ArrayList<Sch010WeekOfModel> bottomList__ = null;


    /**
     * <p>headDate を取得します。
     * @return headDate
     */
    public String getHeadDate() {
        return headDate__;
    }
    /**
     * <p>headDate をセットします。
     * @param headDate headDate
     */
    public void setHeadDate(String headDate) {
        headDate__ = headDate;
    }
    /**
     * <p>dispGroup を取得します。
     * @return dispGroup
     */
    public String getDispGroup() {
        return dispGroup__;
    }
    /**
     * <p>dispGroup をセットします。
     * @param dispGroup dispGroup
     */
    public void setDispGroup(String dispGroup) {
        dispGroup__ = dispGroup;
    }
    /**
     * <p>calendarList を取得します。
     * @return calendarList
     */
    public ArrayList<SimpleCalenderModel> getCalendarList() {
        return calendarList__;
    }
    /**
     * <p>calendarList をセットします。
     * @param calendarList calendarList
     */
    public void setCalendarList(ArrayList<SimpleCalenderModel> calendarList) {
        calendarList__ = calendarList;
    }
    /**
     * <p>topList を取得します。
     * @return topList
     */
    public ArrayList<Sch010WeekOfModel> getTopList() {
        return topList__;
    }
    /**
     * <p>topList をセットします。
     * @param topList topList
     */
    public void setTopList(ArrayList<Sch010WeekOfModel> topList) {
        topList__ = topList;
    }
    /**
     * <p>bottomList を取得します。
     * @return bottomList
     */
    public ArrayList<Sch010WeekOfModel> getBottomList() {
        return bottomList__;
    }
    /**
     * <p>bottomList をセットします。
     * @param bottomList bottomList
     */
    public void setBottomList(ArrayList<Sch010WeekOfModel> bottomList) {
        bottomList__ = bottomList;
    }
    /**
     * <p>fileName を取得します。
     * @return fileName
     */
    public String getFileName() {
        return fileName__;
    }
    /**
     * <p>fileName をセットします。
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }

}