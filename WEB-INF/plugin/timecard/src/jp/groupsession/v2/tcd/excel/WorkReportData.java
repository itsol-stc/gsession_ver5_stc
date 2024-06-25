package jp.groupsession.v2.tcd.excel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <br>[機  能] 勤務表(Excel)へ出力する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WorkReportData {

    /** 作業場所 */
    private String place__ = "";
    /** ユーザ名称 */
    private String userName__ = "";
    /** タイムカード行データ一覧 */
    private List<TimecardLineData> lineDataList__ =
        Collections.synchronizedList(new ArrayList<TimecardLineData>(0));
    /** 該当年 */
    private int year__ = 0;
    /** 該当月 */
    private int month__ = 0;
    /** 1日基準稼働時間 */
    private String baseHour__ = "";
    /** 月基準稼働日数 */
    private String baseDay__ = "";
    /** 月基準稼働時間 */
    private String sumBaseHour__ = "";
    /** 稼働時間合計(PDF用) */
    private String sumUtil__ = "";
    /** 残業時間合計(PDF用) */
    private String sumOver__ = "";
    /** 深夜時間合計(PDF用) */
    private String sumNight__ = "";
    /** 遅刻時間合計(PDF用) */
    private String sumTikoku__ = "";
    /** 早退時間合計(PDF用) */
    private String sumSoutai__ = "";
    /** 休日集計情報 集計区分：有給 */
    private String sumYukyu__ = "";
    /** 休日集計情報 集計区分：欠勤 */
    private String sumKekkin__ = "";
    /** 休日集計情報 集計区分：その他 */
    private String sumOther__ = "";

    /**
     * <p>baseDay を取得します。
     * @return baseDay
     */
    public String getBaseDay() {
        return baseDay__;
    }
    /**
     * <p>baseDay をセットします。
     * @param baseDay baseDay
     */
    public void setBaseDay(String baseDay) {
        baseDay__ = baseDay;
    }
    /**
     * <p>baseHour を取得します。
     * @return baseHour
     */
    public String getBaseHour() {
        return baseHour__;
    }
    /**
     * <p>baseHour をセットします。
     * @param baseHour baseHour
     */
    public void setBaseHour(String baseHour) {
        baseHour__ = baseHour;
    }
    /**
     * @return lineDataList を戻します。
     */
    public List<TimecardLineData> getLineDataList() {
        return lineDataList__;
    }
    /**
     * @param lineDataList lineDataList を設定。
     */
    public void setLineDataList(List<TimecardLineData> lineDataList) {
        this.lineDataList__ = lineDataList;
    }
    /**
     * @return month を戻します。
     */
    public int getMonth() {
        return month__;
    }
    /**
     * @param month month を設定。
     */
    public void setMonth(int month) {
        this.month__ = month;
    }
    /**
     * @return userName を戻します。
     */
    public String getUserName() {
        return userName__;
    }
    /**
     * @param userName userName を設定。
     */
    public void setUserName(String userName) {
        this.userName__ = userName;
    }
    /**
     * @return year を戻します。
     */
    public int getYear() {
        return year__;
    }
    /**
     * @param year year を設定。
     */
    public void setYear(int year) {
        this.year__ = year;
    }
    /**
     * <p>place を取得します。
     * @return place
     */
    public String getPlace() {
        return place__;
    }
    /**
     * <p>place をセットします。
     * @param place place
     */
    public void setPlace(String place) {
        this.place__ = place;
    }
    /**
     * <p>sumUtil を取得します。
     * @return sumUtil
     */
    public String getSumUtil() {
        return sumUtil__;
    }
    /**
     * <p>sumUtil をセットします。
     * @param sumUtil sumUtil
     */
    public void setSumUtil(String sumUtil) {
        sumUtil__ = sumUtil;
    }
    /**
     * <p>sumOver を取得します。
     * @return sumOver
     */
    public String getSumOver() {
        return sumOver__;
    }
    /**
     * <p>sumOver をセットします。
     * @param sumOver sumOver
     */
    public void setSumOver(String sumOver) {
        sumOver__ = sumOver;
    }
    /**
     * <p>sumNight を取得します。
     * @return sumNight
     */
    public String getSumNight() {
        return sumNight__;
    }
    /**
     * <p>sumNight をセットします。
     * @param sumNight sumNight
     */
    public void setSumNight(String sumNight) {
        sumNight__ = sumNight;
    }
    /**
     * <p>sumTikoku を取得します。
     * @return sumTikoku
     */
    public String getSumTikoku() {
        return sumTikoku__;
    }
    /**
     * <p>sumTikoku をセットします。
     * @param sumTikoku sumTikoku
     */
    public void setSumTikoku(String sumTikoku) {
        sumTikoku__ = sumTikoku;
    }
    /**
     * <p>sumSoutai を取得します。
     * @return sumSoutai
     */
    public String getSumSoutai() {
        return sumSoutai__;
    }
    /**
     * <p>sumSoutai をセットします。
     * @param sumSoutai sumSoutai
     */
    public void setSumSoutai(String sumSoutai) {
        sumSoutai__ = sumSoutai;
    }
    /**
     * <p>sumBaseHour を取得します。
     * @return sumBaseHour
     */
    public String getSumBaseHour() {
        return sumBaseHour__;
    }
    /**
     * <p>sumBaseHour をセットします。
     * @param sumBaseHour sumBaseHour
     */
    public void setSumBaseHour(String sumBaseHour) {
        sumBaseHour__ = sumBaseHour;
    }
    /**
     * <p>sumYukyu を取得します。
     * @return sumYukyu
     * @see jp.groupsession.v2.tcd.excel.WorkReportData#sumYukyu__
     */
    public String getSumYukyu() {
        return sumYukyu__;
    }
    /**
     * <p>sumYukyu をセットします。
     * @param sumYukyu sumYukyu
     * @see jp.groupsession.v2.tcd.excel.WorkReportData#sumYukyu__
     */
    public void setSumYukyu(String sumYukyu) {
        sumYukyu__ = sumYukyu;
    }
    /**
     * <p>sumKekkin を取得します。
     * @return sumKekkin
     * @see jp.groupsession.v2.tcd.excel.WorkReportData#sumKekkin__
     */
    public String getSumKekkin() {
        return sumKekkin__;
    }
    /**
     * <p>sumKekkin をセットします。
     * @param sumKekkin sumKekkin
     * @see jp.groupsession.v2.tcd.excel.WorkReportData#sumKekkin__
     */
    public void setSumKekkin(String sumKekkin) {
        sumKekkin__ = sumKekkin;
    }
    /**
     * <p>sumOther を取得します。
     * @return sumOther
     * @see jp.groupsession.v2.tcd.excel.WorkReportData#sumOther__
     */
    public String getSumOther() {
        return sumOther__;
    }
    /**
     * <p>sumOther をセットします。
     * @param sumOther sumOther
     * @see jp.groupsession.v2.tcd.excel.WorkReportData#sumOther__
     */
    public void setSumOther(String sumOther) {
        sumOther__ = sumOther;
    }
}
