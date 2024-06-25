package jp.groupsession.v2.sch.pdf;

import java.util.ArrayList;

import jp.groupsession.v2.sch.sch010.Sch010WeekOfModel;

/**
 * <br>[機  能] スケジュール日間PDFへ出力する情報を格納するModelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchNikPdfModel {

    /** ファイル名 */
    private String fileName__ = null;
    /** ヘッダー表示用年月 */
    private String dspDate__ = null;
    /** ヘッダー表示用 六曜 */
    private String dspRokuyou__ = null;
    /** 表示グループ */
    private String dspGroup__ = null;
    /** スケジュール 上段＋下段 リスト  */
    private ArrayList<ArrayList<Sch010WeekOfModel>> allList__ = null;
    /** 表示開始時間(HH) */
    private int intFrom__;
    /** 表示終了時間(HH) */
    private int intTo__;
    /** 上段表示スケジュール区分 */
    private int intTop__;
    /** 管理者区分 1:権限有り 2:権限無し */
    private int intAdmin__;
    /** タイムチャートリスト */
    private ArrayList<String> timeChartList__ = null;
    /** １時間あたりのメモリ個数*/
    private int memoriCount__;

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
     * <p>dspRokuyou を取得します。
     * @return dspRokuyou
     * @see jp.groupsession.v2.sch.pdf.SchNikPdfModel#dspRokuyou__
     */
    public String getDspRokuyou() {
        return dspRokuyou__;
    }
    /**
     * <p>dspRokuyou をセットします。
     * @param dspRokuyou dspRokuyou
     * @see jp.groupsession.v2.sch.pdf.SchNikPdfModel#dspRokuyou__
     */
    public void setDspRokuyou(String dspRokuyou) {
        dspRokuyou__ = dspRokuyou;
    }
    /**
     * <p>intFrom を取得します。
     * @return intFrom
     */
    public int getIntFrom() {
        return intFrom__;
    }
    /**
     * <p>intFrom をセットします。
     * @param intFrom intFrom
     */
    public void setIntFrom(int intFrom) {
        intFrom__ = intFrom;
    }
    /**
     * <p>intTo を取得します。
     * @return intTo
     */
    public int getIntTo() {
        return intTo__;
    }
    /**
     * <p>intTo をセットします。
     * @param intTo intTo
     */
    public void setIntTo(int intTo) {
        intTo__ = intTo;
    }
    /**
     * <p>intTop を取得します。
     * @return intTop
     */
    public int getIntTop() {
        return intTop__;
    }
    /**
     * <p>intTop をセットします。
     * @param intTop intTop
     */
    public void setIntTop(int intTop) {
        intTop__ = intTop;
    }
    /**
     * <p>intAdmin を取得します。
     * @return intAdmin
     */
    public int getIntAdmin() {
        return intAdmin__;
    }
    /**
     * <p>intAdmin をセットします。
     * @param intAdmin intAdmin
     */
    public void setIntAdmin(int intAdmin) {
        intAdmin__ = intAdmin;
    }
    /**
     * <p>timeChartList を取得します。
     * @return timeChartList
     */
    public ArrayList<String> getTimeChartList() {
        return timeChartList__;
    }
    /**
     * <p>timeChartList をセットします。
     * @param timeChartList timeChartList
     */
    public void setTimeChartList(ArrayList<String> timeChartList) {
        timeChartList__ = timeChartList;
    }
    /**
     * <p>memoriCount を取得します。
     * @return memoriCount
     */
    public int getMemoriCount() {
        return memoriCount__;
    }
    /**
     * <p>memoriCount をセットします。
     * @param memoriCount memoriCount
     */
    public void setMemoriCount(int memoriCount) {
        memoriCount__ = memoriCount;
    }
    /**
     * <p>allList を取得します。
     * @return allList
     */
    public ArrayList<ArrayList<Sch010WeekOfModel>> getAllList() {
        return allList__;
    }
    /**
     * <p>allList をセットします。
     * @param allList allList
     */
    public void setAllList(ArrayList<ArrayList<Sch010WeekOfModel>> allList) {
        allList__ = allList;
    }
}
