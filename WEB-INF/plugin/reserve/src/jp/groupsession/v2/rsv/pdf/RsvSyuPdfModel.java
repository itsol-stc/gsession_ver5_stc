package jp.groupsession.v2.rsv.pdf;

import java.util.ArrayList;

import jp.groupsession.v2.rsv.RsvCalenderModel;
import jp.groupsession.v2.rsv.RsvSisetuModel;

/**
 * <br>[機  能] PDF出力用Modelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvSyuPdfModel {

    /** ファイル名 */
    private String fileName__ = null;
    /** 保存先ファイル名 */
    private String saveFileName__ = null;
    /** 年月 */
    private String headDate__ = null;
    /** 表示グループ */
    private String dispGroup__ = null;
    /** 週間カレンダー */
    private ArrayList<RsvCalenderModel> calendarList__ = null;
    /** 施設毎の予約情報リスト */
    private ArrayList<RsvSisetuModel> sisetuList__ = null;


    //**************************施設情報表示****************

    /** 表示項目2項目名称 */
    private String propHeaderName2__ = null;
    /** 表示項目3項目名称 */
    private String propHeaderName3__ = null;
    /** 表示項目4項目名称 */
    private String propHeaderName4__ = null;
    /** 表示項目5項目名称 */
    private String propHeaderName5__ = null;
    /** 表示項目6項目名称 */
    private String propHeaderName6__ = null;
    /** 表示項目7項目名称 */
    private String propHeaderName7__ = null;

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
    public ArrayList<RsvCalenderModel> getCalendarList() {
        return calendarList__;
    }
    /**
     * <p>calendarList をセットします。
     * @param calendarList calendarList
     */
    public void setCalendarList(ArrayList<RsvCalenderModel> calendarList) {
        calendarList__ = calendarList;
    }
    /**
     * <p>sisetuList を取得します。
     * @return sisetuList
     */
    public ArrayList<RsvSisetuModel> getSisetuList() {
        return sisetuList__;
    }
    /**
     * <p>sisetuList をセットします。
     * @param sisetuList sisetuList
     */
    public void setSisetuList(ArrayList<RsvSisetuModel> sisetuList) {
        sisetuList__ = sisetuList;
    }
    /**
     * <p>propHeaderName2 を取得します。
     * @return propHeaderName2
     */
    public String getPropHeaderName2() {
        return propHeaderName2__;
    }
    /**
     * <p>propHeaderName2 をセットします。
     * @param propHeaderName2 propHeaderName2
     */
    public void setPropHeaderName2(String propHeaderName2) {
        propHeaderName2__ = propHeaderName2;
    }
    /**
     * <p>propHeaderName3 を取得します。
     * @return propHeaderName3
     */
    public String getPropHeaderName3() {
        return propHeaderName3__;
    }
    /**
     * <p>propHeaderName3 をセットします。
     * @param propHeaderName3 propHeaderName3
     */
    public void setPropHeaderName3(String propHeaderName3) {
        propHeaderName3__ = propHeaderName3;
    }
    /**
     * <p>propHeaderName4 を取得します。
     * @return propHeaderName4
     */
    public String getPropHeaderName4() {
        return propHeaderName4__;
    }
    /**
     * <p>propHeaderName4 をセットします。
     * @param propHeaderName4 propHeaderName4
     */
    public void setPropHeaderName4(String propHeaderName4) {
        propHeaderName4__ = propHeaderName4;
    }
    /**
     * <p>propHeaderName5 を取得します。
     * @return propHeaderName5
     */
    public String getPropHeaderName5() {
        return propHeaderName5__;
    }
    /**
     * <p>propHeaderName5 をセットします。
     * @param propHeaderName5 propHeaderName5
     */
    public void setPropHeaderName5(String propHeaderName5) {
        propHeaderName5__ = propHeaderName5;
    }
    /**
     * <p>propHeaderName6 を取得します。
     * @return propHeaderName6
     */
    public String getPropHeaderName6() {
        return propHeaderName6__;
    }
    /**
     * <p>propHeaderName6 をセットします。
     * @param propHeaderName6 propHeaderName6
     */
    public void setPropHeaderName6(String propHeaderName6) {
        propHeaderName6__ = propHeaderName6;
    }
    /**
     * <p>propHeaderName7 を取得します。
     * @return propHeaderName7
     */
    public String getPropHeaderName7() {
        return propHeaderName7__;
    }
    /**
     * <p>propHeaderName7 をセットします。
     * @param propHeaderName7 propHeaderName7
     */
    public void setPropHeaderName7(String propHeaderName7) {
        propHeaderName7__ = propHeaderName7;
    }
    /**
     * <p>saveFileName を取得します。
     * @return saveFileName
     */
    public String getSaveFileName() {
        return saveFileName__;
    }
    /**
     * <p>saveFileName をセットします。
     * @param saveFileName saveFileName
     */
    public void setSaveFileName(String saveFileName) {
        saveFileName__ = saveFileName;
    }

}
