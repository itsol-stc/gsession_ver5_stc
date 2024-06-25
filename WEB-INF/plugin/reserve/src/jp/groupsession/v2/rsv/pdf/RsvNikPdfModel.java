package jp.groupsession.v2.rsv.pdf;

import java.util.ArrayList;

import jp.groupsession.v2.rsv.RsvSisetuModel;

/**
 * <br>[機  能] 施設予約[日間]PDF出力用Modelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvNikPdfModel {

    /** ファイル名 */
    private String rsvNikFileName__ = null;
    /** 保存先ファイル名 */
    private String saveNikFileName__ = null;
    /**年月*/
    private String rsvNikDspDate__ = null;
    /** ヘッダー表示用年月日(曜) */
    private String rsvNikHeadDate__ = null;
    /** ヘッダー表示用 六曜 */
    private String dspRokuyou__ = null;
    /** 表示グループ */
    private String rsvNikdspGroup__ = null;
    /** 予約情報リスト */
    private ArrayList<RsvSisetuModel> rsvNikDaylyList__ = null;
    /** 表示開始時間(HH) */
    private int rsvNikFrom__;
    /** 表示終了時間(HH) */
    private int rsvNikTo__;
    /** colSpan値(表示する時間From-Toによりspan値が変化) */
    private int rsvNikColSpan__ = 0;
    /** タイムチャートリスト */
    private ArrayList<String> rsvNikTimeChartList__ = null;
    /** １時間あたりのメモリ個数*/
    private int rsvNikMemoriCount__;
    /**
     * <p>rsvNikFileName を取得します。
     * @return rsvNikFileName
     */
    public String getRsvNikFileName() {
        return rsvNikFileName__;
    }
    /**
     * <p>rsvNikFileName をセットします。
     * @param rsvNikFileName rsvNikFileName
     */
    public void setRsvNikFileName(String rsvNikFileName) {
        rsvNikFileName__ = rsvNikFileName;
    }
    /**
     * <p>saveNikFileName を取得します。
     * @return saveNikFileName
     */
    public String getSaveNikFileName() {
        return saveNikFileName__;
    }
    /**
     * <p>saveNikFileName をセットします。
     * @param saveNikFileName saveNikFileName
     */
    public void setSaveNikFileName(String saveNikFileName) {
        saveNikFileName__ = saveNikFileName;
    }
    /**
     * <p>rsvNikDspDate を取得します。
     * @return rsvNikDspDate
     */
    public String getRsvNikDspDate() {
        return rsvNikDspDate__;
    }
    /**
     * <p>rsvNikDspDate をセットします。
     * @param rsvNikDspDate rsvNikDspDate
     */
    public void setRsvNikDspDate(String rsvNikDspDate) {
        rsvNikDspDate__ = rsvNikDspDate;
    }
    /**
     * <p>rsvNikHeadDate を取得します。
     * @return rsvNikHeadDate
     */
    public String getRsvNikHeadDate() {
        return rsvNikHeadDate__;
    }
    /**
     * <p>rsvNikHeadDate をセットします。
     * @param rsvNikHeadDate rsvNikHeadDate
     */
    public void setRsvNikHeadDate(String rsvNikHeadDate) {
        rsvNikHeadDate__ = rsvNikHeadDate;
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
     * <p>rsvNikdspGroup を取得します。
     * @return rsvNikdspGroup
     */
    public String getRsvNikdspGroup() {
        return rsvNikdspGroup__;
    }
    /**
     * <p>rsvNikdspGroup をセットします。
     * @param rsvNikdspGroup rsvNikdspGroup
     */
    public void setRsvNikdspGroup(String rsvNikdspGroup) {
        rsvNikdspGroup__ = rsvNikdspGroup;
    }
    /**
     * <p>rsvNikDaylyList を取得します。
     * @return rsvNikDaylyList
     */
    public ArrayList<RsvSisetuModel> getRsvNikDaylyList() {
        return rsvNikDaylyList__;
    }
    /**
     * <p>rsvNikDaylyList をセットします。
     * @param rsvNikDaylyList rsvNikDaylyList
     */
    public void setRsvNikDaylyList(ArrayList<RsvSisetuModel> rsvNikDaylyList) {
        rsvNikDaylyList__ = rsvNikDaylyList;
    }
    /**
     * <p>rsvNikFrom を取得します。
     * @return rsvNikFrom
     */
    public int getRsvNikFrom() {
        return rsvNikFrom__;
    }
    /**
     * <p>rsvNikFrom をセットします。
     * @param rsvNikFrom rsvNikFrom
     */
    public void setRsvNikFrom(int rsvNikFrom) {
        rsvNikFrom__ = rsvNikFrom;
    }
    /**
     * <p>rsvNikTo を取得します。
     * @return rsvNikTo
     */
    public int getRsvNikTo() {
        return rsvNikTo__;
    }
    /**
     * <p>rsvNikTo をセットします。
     * @param rsvNikTo rsvNikTo
     */
    public void setRsvNikTo(int rsvNikTo) {
        rsvNikTo__ = rsvNikTo;
    }
    /**
     * <p>rsvNikColSpan を取得します。
     * @return rsvNikColSpan
     */
    public int getRsvNikColSpan() {
        return rsvNikColSpan__;
    }
    /**
     * <p>rsvNikColSpan をセットします。
     * @param rsvNikColSpan rsvNikColSpan
     */
    public void setRsvNikColSpan(int rsvNikColSpan) {
        rsvNikColSpan__ = rsvNikColSpan;
    }
    /**
     * <p>rsvNikTimeChartList を取得します。
     * @return rsvNikTimeChartList
     */
    public ArrayList<String> getRsvNikTimeChartList() {
        return rsvNikTimeChartList__;
    }
    /**
     * <p>rsvNikTimeChartList をセットします。
     * @param rsvNikTimeChartList rsvNikTimeChartList
     */
    public void setRsvNikTimeChartList(ArrayList<String> rsvNikTimeChartList) {
        rsvNikTimeChartList__ = rsvNikTimeChartList;
    }
    /**
     * <p>rsvNikMemoriCount を取得します。
     * @return rsvNikMemoriCount
     */
    public int getRsvNikMemoriCount() {
        return rsvNikMemoriCount__;
    }
    /**
     * <p>rsvNikMemoriCount をセットします。
     * @param rsvNikMemoriCount rsvNikMemoriCount
     */
    public void setRsvNikMemoriCount(int rsvNikMemoriCount) {
        rsvNikMemoriCount__ = rsvNikMemoriCount;
    }
}
