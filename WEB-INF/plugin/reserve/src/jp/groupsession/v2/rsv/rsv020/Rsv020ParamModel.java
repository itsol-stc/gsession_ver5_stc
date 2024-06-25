package jp.groupsession.v2.rsv.rsv020;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.RsvSisetuModel;
import jp.groupsession.v2.rsv.rsv010.Rsv010ParamModel;

/**
 * <br>[機  能] 施設予約一覧 日間画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv020ParamModel extends Rsv010ParamModel {

    /** タイムチャートリスト */
    private ArrayList<String> rsv020TimeChartList__ = null;
    /** 表示開始時間(HH) */
    private String rsv020FromHour__ = null;
    /** 表示終了時間(HH) */
    private String rsv020ToHour__ = null;
    /** 予約情報リスト */
    private ArrayList<RsvSisetuModel> rsv020DaylyList__ = null;
    /** colSpan値(表示する時間From-Toによりspan値が変化) */
    private int rsv020ColSpan__ = 0;
    /** 取消し対象施設キー */
    private String rsv020ClearTargetKey__ = null;
    /** 自動リロード時間 */
    private int rsv020Reload__ = GSConstReserve.AUTO_RELOAD_10MIN;
    /** 時間間隔 区切り数 */
    private int rsv020HourDivCount__ = 0;

    /**
     * <p>rsv020HourDivCount を取得します。
     * @return rsv020HourDivCount
     */
    public int getRsv020HourDivCount() {
        return rsv020HourDivCount__;
    }
    /**
     * <p>rsv020HourDivCount をセットします。
     * @param rsv020HourDivCount rsv020HourDivCount
     */
    public void setRsv020HourDivCount(int rsv020HourDivCount) {
        rsv020HourDivCount__ = rsv020HourDivCount;
    }
    /**
     * <p>rsv020Reload を取得します。
     * @return rsv020Reload
     */
    public int getRsv020Reload() {
        return rsv020Reload__;
    }
    /**
     * <p>rsv020Reload をセットします。
     * @param rsv020Reload rsv020Reload
     */
    public void setRsv020Reload(int rsv020Reload) {
        rsv020Reload__ = rsv020Reload;
    }
    /**
     * <p>rsv020ClearTargetKey__ を取得します。
     * @return rsv020ClearTargetKey
     */
    public String getRsv020ClearTargetKey() {
        return rsv020ClearTargetKey__;
    }
    /**
     * <p>rsv020ClearTargetKey__ をセットします。
     * @param rsv020ClearTargetKey rsv020ClearTargetKey__
     */
    public void setRsv020ClearTargetKey(String rsv020ClearTargetKey) {
        rsv020ClearTargetKey__ = rsv020ClearTargetKey;
    }
    /**
     * <p>rsv020TimeChartList__ を取得します。
     * @return rsv020TimeChartList
     */
    public ArrayList<String> getRsv020TimeChartList() {
        return rsv020TimeChartList__;
    }
    /**
     * <p>rsv020TimeChartList__ をセットします。
     * @param rsv020TimeChartList rsv020TimeChartList__
     */
    public void setRsv020TimeChartList(ArrayList<String> rsv020TimeChartList) {
        rsv020TimeChartList__ = rsv020TimeChartList;
    }
    /**
     * <p>rsv020FromHour__ を取得します。
     * @return rsv020FromHour
     */
    public String getRsv020FromHour() {
        return rsv020FromHour__;
    }
    /**
     * <p>rsv020FromHour__ をセットします。
     * @param rsv020FromHour rsv020FromHour__
     */
    public void setRsv020FromHour(String rsv020FromHour) {
        rsv020FromHour__ = rsv020FromHour;
    }
    /**
     * <p>rsv020ToHour__ を取得します。
     * @return rsv020ToHour
     */
    public String getRsv020ToHour() {
        return rsv020ToHour__;
    }
    /**
     * <p>rsv020ToHour__ をセットします。
     * @param rsv020ToHour rsv020ToHour__
     */
    public void setRsv020ToHour(String rsv020ToHour) {
        rsv020ToHour__ = rsv020ToHour;
    }
    /**
     * <p>rsv020DaylyList__ を取得します。
     * @return rsv020DaylyList
     */
    public ArrayList<RsvSisetuModel> getRsv020DaylyList() {
        return rsv020DaylyList__;
    }
    /**
     * <p>rsv020DaylyList__ をセットします。
     * @param rsv020DaylyList rsv020DaylyList__
     */
    public void setRsv020DaylyList(ArrayList<RsvSisetuModel> rsv020DaylyList) {
        rsv020DaylyList__ = rsv020DaylyList;
    }
    /**
     * <p>rsv020ColSpan__ を取得します。
     * @return rsv020ColSpan
     */
    public int getRsv020ColSpan() {
        return rsv020ColSpan__;
    }
    /**
     * <p>rsv020ColSpan__ をセットします。
     * @param rsv020ColSpan rsv020ColSpan__
     */
    public void setRsv020ColSpan(int rsv020ColSpan) {
        rsv020ColSpan__ = rsv020ColSpan;
    }
}