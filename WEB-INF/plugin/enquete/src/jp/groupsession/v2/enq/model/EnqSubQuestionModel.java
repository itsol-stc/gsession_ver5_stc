package jp.groupsession.v2.enq.model;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqSubQuestionModel extends EnqQuestionDataModel {

    /** アンケートSID */
    private long enqSid__ = -1;
    /** 設問連番 */
    private int enqSeq__ = 0;
    /** 設問サブ連番 */
    private int enqSubSeq__ = 0;
    /** 表示順 */
    private int enqSubDspSec__ = 0;
    /** 初期値_文字 */
    private String enqInitStr__ = null;
    /** 初期値_数値 */
    private int enqInitInt__ = 0;
    /** 初期値_日付（年） */
    private String enqInitFrYear__ = null;
    /** 初期値_日付（月） */
    private String enqInitFrMonth__ = null;
    /** 初期値_日付（日） */
    private String enqInitFrDay__ = null;
    /** 初期値 */
    private String enqInit__ = null;

    /** 範囲_数値_開始 */
    private int enqRngFrInt__ = 0;
    /** 範囲_数値_開始 */
    private int enqRngToInt__ = 0;
    /** 範囲_開始年 */
    private String enqRngFrYear__ = null;
    /** 範囲_開始月 */
    private String enqRngFrMonth__ = null;
    /** 範囲_開始日 */
    private String enqRngFrDay__ = null;
    /** 範囲_終了年 */
    private String enqRngToYear__ = null;
    /** 範囲_終了月 */
    private String enqRngToMonth__ = null;
    /** 範囲_終了日 */
    private String enqRngToDay__ = null;
    /** 単位 */
    private String enqUnitNum__ = null;
    /**
     * <p>enqSid を取得します。
     * @return enqSid
     */
    public long getEnqSid() {
        return enqSid__;
    }
    /**
     * <p>enqSid をセットします。
     * @param enqSid enqSid
     */
    public void setEnqSid(long enqSid) {
        enqSid__ = enqSid;
    }
    /**
     * <p>enqSeq を取得します。
     * @return enqSeq
     */
    public int getEnqSeq() {
        return enqSeq__;
    }
    /**
     * <p>enqSeq をセットします。
     * @param enqSeq enqSeq
     */
    public void setEnqSeq(int enqSeq) {
        enqSeq__ = enqSeq;
    }
    /**
     * <p>enqSubSeq を取得します。
     * @return enqSubSeq
     */
    public int getEnqSubSeq() {
        return enqSubSeq__;
    }
    /**
     * <p>enqSubSeq をセットします。
     * @param enqSubSeq enqSubSeq
     */
    public void setEnqSubSeq(int enqSubSeq) {
        enqSubSeq__ = enqSubSeq;
    }
    /**
     * <p>enqSubDspSec を取得します。
     * @return enqSubDspSec
     */
    public int getEnqSubDspSec() {
        return enqSubDspSec__;
    }
    /**
     * <p>enqSubDspSec をセットします。
     * @param enqSubDspSec enqSubDspSec
     */
    public void setEnqSubDspSec(int enqSubDspSec) {
        enqSubDspSec__ = enqSubDspSec;
    }
    /**
     * <p>enqInitStr を取得します。
     * @return enqInitStr
     */
    public String getEnqInitStr() {
        return enqInitStr__;
    }
    /**
     * <p>enqInitStr をセットします。
     * @param enqInitStr enqInitStr
     */
    public void setEnqInitStr(String enqInitStr) {
        enqInitStr__ = enqInitStr;
    }
    /**
     * <p>enqInitInt を取得します。
     * @return enqInitInt
     */
    public int getEnqInitInt() {
        return enqInitInt__;
    }
    /**
     * <p>enqInitInt をセットします。
     * @param enqInitInt enqInitInt
     */
    public void setEnqInitInt(int enqInitInt) {
        enqInitInt__ = enqInitInt;
    }
    /**
     * <p>enqInitFrYear を取得します。
     * @return enqInitFrYear
     */
    public String getEnqInitFrYear() {
        return enqInitFrYear__;
    }
    /**
     * <p>enqInitFrYear をセットします。
     * @param enqInitFrYear enqInitFrYear
     */
    public void setEnqInitFrYear(String enqInitFrYear) {
        enqInitFrYear__ = enqInitFrYear;
    }
    /**
     * <p>enqInitFrMonth を取得します。
     * @return enqInitFrMonth
     */
    public String getEnqInitFrMonth() {
        return enqInitFrMonth__;
    }
    /**
     * <p>enqInitFrMonth をセットします。
     * @param enqInitFrMonth enqInitFrMonth
     */
    public void setEnqInitFrMonth(String enqInitFrMonth) {
        enqInitFrMonth__ = enqInitFrMonth;
    }
    /**
     * <p>enqInitFrDay を取得します。
     * @return enqInitFrDay
     */
    public String getEnqInitFrDay() {
        return enqInitFrDay__;
    }
    /**
     * <p>enqInitFrDay をセットします。
     * @param enqInitFrDay enqInitFrDay
     */
    public void setEnqInitFrDay(String enqInitFrDay) {
        enqInitFrDay__ = enqInitFrDay;
    }
    /**
     * <p>enqInit を取得します。
     * @return enqInit
     */
    public String getEnqInit() {
        return enqInit__;
    }
    /**
     * <p>enqInit をセットします。
     * @param enqInit enqInit
     */
    public void setEnqInit(String enqInit) {
        enqInit__ = enqInit;
    }
    /**
     * <p>enqRngFrInt を取得します。
     * @return enqRngFrInt
     */
    public int getEnqRngFrInt() {
        return enqRngFrInt__;
    }
    /**
     * <p>enqRngFrInt をセットします。
     * @param enqRngFrInt enqRngFrInt
     */
    public void setEnqRngFrInt(int enqRngFrInt) {
        enqRngFrInt__ = enqRngFrInt;
    }
    /**
     * <p>enqRngToInt を取得します。
     * @return enqRngToInt
     */
    public int getEnqRngToInt() {
        return enqRngToInt__;
    }
    /**
     * <p>enqRngToInt をセットします。
     * @param enqRngToInt enqRngToInt
     */
    public void setEnqRngToInt(int enqRngToInt) {
        enqRngToInt__ = enqRngToInt;
    }
    /**
     * <p>enqRngFrYear を取得します。
     * @return enqRngFrYear
     */
    public String getEnqRngFrYear() {
        return enqRngFrYear__;
    }
    /**
     * <p>enqRngFrYear をセットします。
     * @param enqRngFrYear enqRngFrYear
     */
    public void setEnqRngFrYear(String enqRngFrYear) {
        enqRngFrYear__ = enqRngFrYear;
    }
    /**
     * <p>enqRngFrMonth を取得します。
     * @return enqRngFrMonth
     */
    public String getEnqRngFrMonth() {
        return enqRngFrMonth__;
    }
    /**
     * <p>enqRngFrMonth をセットします。
     * @param enqRngFrMonth enqRngFrMonth
     */
    public void setEnqRngFrMonth(String enqRngFrMonth) {
        enqRngFrMonth__ = enqRngFrMonth;
    }
    /**
     * <p>enqRngFrDay を取得します。
     * @return enqRngFrDay
     */
    public String getEnqRngFrDay() {
        return enqRngFrDay__;
    }
    /**
     * <p>enqRngFrDay をセットします。
     * @param enqRngFrDay enqRngFrDay
     */
    public void setEnqRngFrDay(String enqRngFrDay) {
        enqRngFrDay__ = enqRngFrDay;
    }
    /**
     * <p>enqRngToYear を取得します。
     * @return enqRngToYear
     */
    public String getEnqRngToYear() {
        return enqRngToYear__;
    }
    /**
     * <p>enqRngToYear をセットします。
     * @param enqRngToYear enqRngToYear
     */
    public void setEnqRngToYear(String enqRngToYear) {
        enqRngToYear__ = enqRngToYear;
    }
    /**
     * <p>enqRngToMonth を取得します。
     * @return enqRngToMonth
     */
    public String getEnqRngToMonth() {
        return enqRngToMonth__;
    }
    /**
     * <p>enqRngToMonth をセットします。
     * @param enqRngToMonth enqRngToMonth
     */
    public void setEnqRngToMonth(String enqRngToMonth) {
        enqRngToMonth__ = enqRngToMonth;
    }
    /**
     * <p>enqRngToDay を取得します。
     * @return enqRngToDay
     */
    public String getEnqRngToDay() {
        return enqRngToDay__;
    }
    /**
     * <p>enqRngToDay をセットします。
     * @param enqRngToDay enqRngToDay
     */
    public void setEnqRngToDay(String enqRngToDay) {
        enqRngToDay__ = enqRngToDay;
    }
    /**
     * <p>enqUnitNum を取得します。
     * @return enqUnitNum
     */
    public String getEnqUnitNum() {
        return enqUnitNum__;
    }
    /**
     * <p>enqUnitNum をセットします。
     * @param enqUnitNum enqUnitNum
     */
    public void setEnqUnitNum(String enqUnitNum) {
        enqUnitNum__ = enqUnitNum;
    }


}
