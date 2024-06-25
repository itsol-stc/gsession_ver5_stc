package jp.groupsession.v2.sch.pdf;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.sch.sch020.Sch020MonthOfModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] スケジュール月間PDFへ出力する情報を格納するModelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchGekPdfModel {

    /** ファイル名 */
    private String fileName__ = null;
    /** ヘッダー表示用年月 */
    private String dspDate__ = null;
    /** 表示グループ */
    private String dspGroup__ = null;
    /** 表示ユーザ */
    private String dspUser__ = null;
    /** 曜日リスト */
    private List<LabelValueBean> weekList__ = null;
    /** スケジュールリスト */
    private ArrayList<Sch020MonthOfModel> scheduleList__ = null;


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

    /**
     * <p>dspDate を取得します。
     * @return dspDate
     */
    public String getDspDate() {
        return dspDate__;
    }

    /**
     * <p>dspDate をセットします。
     * @param dspDate dspDate
     */
    public void setDspDate(String dspDate) {
        dspDate__ = dspDate;
    }

    /**
     * <p>dspGroup を取得します。
     * @return dspGroup
     */
    public String getDspGroup() {
        return dspGroup__;
    }

    /**
     * <p>dspGroup をセットします。
     * @param dspGroup dspGroup
     */
    public void setDspGroup(String dspGroup) {
        dspGroup__ = dspGroup;
    }

    /**
     * <p>dspUser を取得します。
     * @return dspUser
     */
    public String getDspUser() {
        return dspUser__;
    }

    /**
     * <p>dspUser をセットします。
     * @param dspUser dspUser
     */
    public void setDspUser(String dspUser) {
        dspUser__ = dspUser;
    }

    /**
     * <p>weekList を取得します。
     * @return weekList
     */
    public List<LabelValueBean> getWeekList() {
        return weekList__;
    }

    /**
     * <p>weekList をセットします。
     * @param weekList weekList
     */
    public void setWeekList(List<LabelValueBean> weekList) {
        weekList__ = weekList;
    }

    /**
     * <p>scheduleList を取得します。
     * @return scheduleList
     */
    public ArrayList<Sch020MonthOfModel> getScheduleList() {
        return scheduleList__;
    }

    /**
     * <p>scheduleList をセットします。
     * @param scheduleList scheduleList
     */
    public void setScheduleList(ArrayList<Sch020MonthOfModel> scheduleList) {
        scheduleList__ = scheduleList;
    }



}
