package jp.groupsession.v2.sch.sch030;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.sch.sch020.Sch020Form;

/**
 * <br>[機  能] スケジュール 日間画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch030Form extends Sch020Form {


    /** 表示開始時間(HH) */
    private String sch030FromHour__;
    /** 表示終了時間(HH) */
    private String sch030ToHour__;
    /** 表示全体Cols */
    private String sch030TotalCols__;
    /** 自動リロード時間 */
    private int sch030Reload__ = GSConstSchedule.AUTO_RELOAD_10MIN;

    //表示項目
    /** ヘッダー表示年月日 */
    private String sch030StrDate__;
    /** ヘッダー表示 六曜 */
    private String sch030StrRokuyou__ = null;
    /** タイムチャートリスト */
    private ArrayList<String> sch030TimeChartList__;

    /** １時間あたりのメモリ個数*/
    private String sch030MemoriCount__;

    /**
     * <p>sch030MemoriCount を取得します。
     * @return sch030MemoriCount
     */
    public String getSch030MemoriCount() {
        return sch030MemoriCount__;
    }
    /**
     * <p>sch030MemoriCount をセットします。
     * @param sch030MemoriCount sch030MemoriCount
     */
    public void setSch030MemoriCount(String sch030MemoriCount) {
        sch030MemoriCount__ = sch030MemoriCount;
    }
    /**
     * <p>sch030TotalCols を取得します。
     * @return sch030TotalCols
     */
    public String getSch030TotalCols() {
        return sch030TotalCols__;
    }
    /**
     * <p>sch030TotalCols をセットします。
     * @param sch030TotalCols sch030TotalCols
     */
    public void setSch030TotalCols(String sch030TotalCols) {
        sch030TotalCols__ = sch030TotalCols;
    }
    /**
     * <p>sch030ToHour を取得します。
     * @return sch030ToHour
     */
    public String getSch030ToHour() {
        return sch030ToHour__;
    }
    /**
     * <p>sch030ToHour をセットします。
     * @param sch030ToHour sch030ToHour
     */
    public void setSch030ToHour(String sch030ToHour) {
        sch030ToHour__ = sch030ToHour;
    }
    /**
     * @return sch030TimeChartList を戻します。
     */
    public ArrayList<String> getSch030TimeChartList() {
        return sch030TimeChartList__;
    }
    /**
     * @param sch030TimeChartList 設定する sch030TimeChartList。
     */
    public void setSch030TimeChartList(ArrayList<String> sch030TimeChartList) {
        sch030TimeChartList__ = sch030TimeChartList;
    }
    /**
     * @return sch030FromHour を戻します。
     */
    public String getSch030FromHour() {
        return sch030FromHour__;
    }
    /**
     * @param sch030FromHour 設定する sch030FromHour。
     */
    public void setSch030FromHour(String sch030FromHour) {
        sch030FromHour__ = sch030FromHour;
    }
    /**
     * @return sch030StrDate を戻します。
     */
    public String getSch030StrDate() {
        return sch030StrDate__;
    }
    /**
     * @param sch030StrDate 設定する sch030StrDate。
     */
    public void setSch030StrDate(String sch030StrDate) {
        sch030StrDate__ = sch030StrDate;
    }
    /**
     * <p>sch030Reload を取得します。
     * @return sch030Reload
     */
    public int getSch030Reload() {
        return sch030Reload__;
    }
    /**
     * <p>sch030Reload をセットします。
     * @param sch030Reload sch030Reload
     */
    public void setSch030Reload(int sch030Reload) {
        sch030Reload__ = sch030Reload;
    }
    /**
     * <p>sch030StrRokuyou を取得します。
     * @return sch030StrRokuyou
     * @see jp.groupsession.v2.sch.sch030.Sch030Form#sch030StrRokuyou
     */
    public String getSch030StrRokuyou() {
        return sch030StrRokuyou__;
    }
    /**
     * <p>sch030StrRokuyou をセットします。
     * @param sch030StrRokuyou sch030StrRokuyou
     * @see jp.groupsession.v2.sch.sch030.Sch030Form#sch030StrRokuyou
     */
    public void setSch030StrRokuyou(String sch030StrRokuyou) {
        sch030StrRokuyou__ = sch030StrRokuyou;
    }
}
