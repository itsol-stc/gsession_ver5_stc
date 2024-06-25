package jp.groupsession.v2.fil.fil270;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil200.Fil200Form;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] ファイル管理統計情報画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Fil270Form extends Fil200Form {

    /** メイン管理者権限フラグ */
    private boolean fil270GsAdminFlg__ = false;
    /** 使用可能フラグ WEBメール */
    private boolean fil270CtrlFlgWml__ = false;
    /** 使用可能フラグ ショートメール */
    private boolean fil270CtrlFlgSml__ = false;
    /** 使用可能フラグ 回覧板 */
    private boolean fil270CtrlFlgCir__ = false;
    /** 使用可能フラグ 掲示板 */
    private boolean fil270CtrlFlgBbs__ = false;
    /** 使用可能フラグ チャット */
    private boolean fil270CtrlFlgCht__ = false;

    /** 日付表示単位 */
    private int fil270DateUnit__ = GSConstMain.STATS_DATE_UNIT_MONTH;
    /** 年指定コンボ */
    private List<LabelValueBean> fil270DspYearLabel__;
    /** 月指定コンボ */
    private List<LabelValueBean> fil270DspMonthLabel__;
    /** 週指定Fromコンボ */
    private List<LabelValueBean> fil270DspWeekFrLabel__;
    /** 週指定Toコンボ */
    private List<LabelValueBean> fil270DspWeekToLabel__;
    /** 月別 日付 開始年 */
    private String fil270DateMonthlyFrYear__;
    /** 月別 日付 開始月 */
    private String fil270DateMonthlyFrMonth__;
    /** 月別 日付 終了年 */
    private String fil270DateMonthlyToYear__;
    /** 月別 日付 終了月 */
    private String fil270DateMonthlyToMonth__;
    /** 週別 日付範囲 開始 */
    private String fil270DateWeeklyFrStr__;
    /** 週別 日付範囲 終了 */
    private String fil270DateWeeklyToStr__;
    /** 日別 日付範囲 開始 */
    private String fil270DateDailyFrStr__;
    /** 日別 日付範囲 終了 */
    private String fil270DateDailyToStr__;
    /** 表示件数 */
    private int fil270DspNum__ = 40;
    /** 表示件数コンボ */
    private List<LabelValueBean> fil270DspNumLabel__;

    /** 現在ページ */
    private int fil270NowPage__ = 1;
    /** 表示ページ（上） */
    private int fil270DspPage1__;
    /** 表示ページ（下） */
    private int fil270DspPage2__;
    /** ページラベル */
    private List<LabelValueBean> fil270PageLabel__;
    /** 現在のグラフの項目 */
    private String fil270GraphItem__ = GSConstFile.FIL_LOG_GRAPH_DOWNLOAD;

    /** 集計データ一覧 */
    private List<Fil270DspModel> fil270LogCountList__;
    /** 平均件数 ファイルダウンロード */
    private String fil270AveDownloadNum__;
    /** 平均件数 ファイルアップロード */
    private String fil270AveUploadNum__;
    /** 合計件数 ファイルダウンロード */
    private String fil270SumDownloadNum__;
    /** 合計件数 ファイルアップロード */
    private String fil270SumUploadNum__;

    /** 統計情報の戻り先 */
    private String logCountBack__;

    /** グラフデータ（日付） */
    private String jsonDateData__;
    /** グラフデータ（ダウンロード数） */
    private String jsonDownloadData__;
    /** グラフデータ（アップロード数） */
    private String jsonUploadData__;

    /**
     * <p>fil270GsAdminFlg を取得します。
     * @return fil270GsAdminFlg
     */
    public boolean isFil270GsAdminFlg() {
        return fil270GsAdminFlg__;
    }
    /**
     * <p>fil270GsAdminFlg をセットします。
     * @param fil270GsAdminFlg fil270GsAdminFlg
     */
    public void setFil270GsAdminFlg(boolean fil270GsAdminFlg) {
        fil270GsAdminFlg__ = fil270GsAdminFlg;
    }
    /**
     * <p>fil270CtrlFlgWml を取得します。
     * @return fil270CtrlFlgWml
     */
    public boolean isFil270CtrlFlgWml() {
        return fil270CtrlFlgWml__;
    }
    /**
     * <p>fil270CtrlFlgWml をセットします。
     * @param fil270CtrlFlgWml fil270CtrlFlgWml
     */
    public void setFil270CtrlFlgWml(boolean fil270CtrlFlgWml) {
        fil270CtrlFlgWml__ = fil270CtrlFlgWml;
    }
    /**
     * <p>fil270CtrlFlgSml を取得します。
     * @return fil270CtrlFlgSml
     */
    public boolean isFil270CtrlFlgSml() {
        return fil270CtrlFlgSml__;
    }
    /**
     * <p>fil270CtrlFlgSml をセットします。
     * @param fil270CtrlFlgSml fil270CtrlFlgSml
     */
    public void setFil270CtrlFlgSml(boolean fil270CtrlFlgSml) {
        fil270CtrlFlgSml__ = fil270CtrlFlgSml;
    }
    /**
     * <p>fil270CtrlFlgCir を取得します。
     * @return fil270CtrlFlgCir
     */
    public boolean isFil270CtrlFlgCir() {
        return fil270CtrlFlgCir__;
    }
    /**
     * <p>fil270CtrlFlgCir をセットします。
     * @param fil270CtrlFlgCir fil270CtrlFlgCir
     */
    public void setFil270CtrlFlgCir(boolean fil270CtrlFlgCir) {
        fil270CtrlFlgCir__ = fil270CtrlFlgCir;
    }
    /**
     * <p>fil270CtrlFlgBbs を取得します。
     * @return fil270CtrlFlgBbs
     */
    public boolean isFil270CtrlFlgBbs() {
        return fil270CtrlFlgBbs__;
    }
    /**
     * <p>fil270CtrlFlgBbs をセットします。
     * @param fil270CtrlFlgBbs fil270CtrlFlgBbs
     */
    public void setFil270CtrlFlgBbs(boolean fil270CtrlFlgBbs) {
        fil270CtrlFlgBbs__ = fil270CtrlFlgBbs;
    }
    /**
     * <p>fil270DateUnit を取得します。
     * @return fil270DateUnit
     */
    public int getFil270DateUnit() {
        return fil270DateUnit__;
    }
    /**
     * <p>fil270DateUnit をセットします。
     * @param fil270DateUnit fil270DateUnit
     */
    public void setFil270DateUnit(int fil270DateUnit) {
        fil270DateUnit__ = fil270DateUnit;
    }
    /**
     * <p>fil270DspYearLabel を取得します。
     * @return fil270DspYearLabel
     */
    public List<LabelValueBean> getFil270DspYearLabel() {
        return fil270DspYearLabel__;
    }
    /**
     * <p>fil270DspYearLabel をセットします。
     * @param fil270DspYearLabel fil270DspYearLabel
     */
    public void setFil270DspYearLabel(List<LabelValueBean> fil270DspYearLabel) {
        fil270DspYearLabel__ = fil270DspYearLabel;
    }
    /**
     * <p>fil270DspMonthLabel を取得します。
     * @return fil270DspMonthLabel
     */
    public List<LabelValueBean> getFil270DspMonthLabel() {
        return fil270DspMonthLabel__;
    }
    /**
     * <p>fil270DspMonthLabel をセットします。
     * @param fil270DspMonthLabel fil270DspMonthLabel
     */
    public void setFil270DspMonthLabel(List<LabelValueBean> fil270DspMonthLabel) {
        fil270DspMonthLabel__ = fil270DspMonthLabel;
    }
    /**
     * <p>fil270DspWeekFrLabel を取得します。
     * @return fil270DspWeekFrLabel
     */
    public List<LabelValueBean> getFil270DspWeekFrLabel() {
        return fil270DspWeekFrLabel__;
    }
    /**
     * <p>fil270DspWeekFrLabel をセットします。
     * @param fil270DspWeekFrLabel fil270DspWeekFrLabel
     */
    public void setFil270DspWeekFrLabel(List<LabelValueBean> fil270DspWeekFrLabel) {
        fil270DspWeekFrLabel__ = fil270DspWeekFrLabel;
    }
    /**
     * <p>fil270DspWeekToLabel を取得します。
     * @return fil270DspWeekToLabel
     */
    public List<LabelValueBean> getFil270DspWeekToLabel() {
        return fil270DspWeekToLabel__;
    }
    /**
     * <p>fil270DspWeekToLabel をセットします。
     * @param fil270DspWeekToLabel fil270DspWeekToLabel
     */
    public void setFil270DspWeekToLabel(List<LabelValueBean> fil270DspWeekToLabel) {
        fil270DspWeekToLabel__ = fil270DspWeekToLabel;
    }
    /**
     * <p>fil270DateMonthlyFrYear を取得します。
     * @return fil270DateMonthlyFrYear
     */
    public String getFil270DateMonthlyFrYear() {
        return fil270DateMonthlyFrYear__;
    }
    /**
     * <p>fil270DateMonthlyFrYear をセットします。
     * @param fil270DateMonthlyFrYear fil270DateMonthlyFrYear
     */
    public void setFil270DateMonthlyFrYear(String fil270DateMonthlyFrYear) {
        fil270DateMonthlyFrYear__ = fil270DateMonthlyFrYear;
    }
    /**
     * <p>fil270DateMonthlyFrMonth を取得します。
     * @return fil270DateMonthlyFrMonth
     */
    public String getFil270DateMonthlyFrMonth() {
        return fil270DateMonthlyFrMonth__;
    }
    /**
     * <p>fil270DateMonthlyFrMonth をセットします。
     * @param fil270DateMonthlyFrMonth fil270DateMonthlyFrMonth
     */
    public void setFil270DateMonthlyFrMonth(String fil270DateMonthlyFrMonth) {
        fil270DateMonthlyFrMonth__ = fil270DateMonthlyFrMonth;
    }
    /**
     * <p>fil270DateMonthlyToYear を取得します。
     * @return fil270DateMonthlyToYear
     */
    public String getFil270DateMonthlyToYear() {
        return fil270DateMonthlyToYear__;
    }
    /**
     * <p>fil270DateMonthlyToYear をセットします。
     * @param fil270DateMonthlyToYear fil270DateMonthlyToYear
     */
    public void setFil270DateMonthlyToYear(String fil270DateMonthlyToYear) {
        fil270DateMonthlyToYear__ = fil270DateMonthlyToYear;
    }
    /**
     * <p>fil270DateMonthlyToMonth を取得します。
     * @return fil270DateMonthlyToMonth
     */
    public String getFil270DateMonthlyToMonth() {
        return fil270DateMonthlyToMonth__;
    }
    /**
     * <p>fil270DateMonthlyToMonth をセットします。
     * @param fil270DateMonthlyToMonth fil270DateMonthlyToMonth
     */
    public void setFil270DateMonthlyToMonth(String fil270DateMonthlyToMonth) {
        fil270DateMonthlyToMonth__ = fil270DateMonthlyToMonth;
    }
    /**
     * <p>fil270DateWeeklyFrStr を取得します。
     * @return fil270DateWeeklyFrStr
     */
    public String getFil270DateWeeklyFrStr() {
        return fil270DateWeeklyFrStr__;
    }
    /**
     * <p>fil270DateWeeklyFrStr をセットします。
     * @param fil270DateWeeklyFrStr fil270DateWeeklyFrStr
     */
    public void setFil270DateWeeklyFrStr(String fil270DateWeeklyFrStr) {
        fil270DateWeeklyFrStr__ = fil270DateWeeklyFrStr;
    }
    /**
     * <p>fil270DateWeeklyToStr を取得します。
     * @return fil270DateWeeklyToStr
     */
    public String getFil270DateWeeklyToStr() {
        return fil270DateWeeklyToStr__;
    }
    /**
     * <p>fil270DateWeeklyToStr をセットします。
     * @param fil270DateWeeklyToStr fil270DateWeeklyToStr
     */
    public void setFil270DateWeeklyToStr(String fil270DateWeeklyToStr) {
        fil270DateWeeklyToStr__ = fil270DateWeeklyToStr;
    }
    /**
     * <p>fil270DateDailyFrStr を取得します。
     * @return fil270DateDailyFrStr
     */
    public String getFil270DateDailyFrStr() {
        return fil270DateDailyFrStr__;
    }
    /**
     * <p>fil270DateDailyFrStr をセットします。
     * @param fil270DateDailyFrStr fil270DateDailyFrStr
     */
    public void setFil270DateDailyFrStr(String fil270DateDailyFrStr) {
        fil270DateDailyFrStr__ = fil270DateDailyFrStr;
    }
    /**
     * <p>fil270DateDailyToStr を取得します。
     * @return fil270DateDailyToStr
     */
    public String getFil270DateDailyToStr() {
        return fil270DateDailyToStr__;
    }
    /**
     * <p>fil270DateDailyToStr をセットします。
     * @param fil270DateDailyToStr fil270DateDailyToStr
     */
    public void setFil270DateDailyToStr(String fil270DateDailyToStr) {
        fil270DateDailyToStr__ = fil270DateDailyToStr;
    }
    /**
     * <p>fil270DspNum を取得します。
     * @return fil270DspNum
     */
    public int getFil270DspNum() {
        return fil270DspNum__;
    }
    /**
     * <p>fil270DspNum をセットします。
     * @param fil270DspNum fil270DspNum
     */
    public void setFil270DspNum(int fil270DspNum) {
        fil270DspNum__ = fil270DspNum;
    }
    /**
     * <p>fil270DspNumLabel を取得します。
     * @return fil270DspNumLabel
     */
    public List<LabelValueBean> getFil270DspNumLabel() {
        return fil270DspNumLabel__;
    }
    /**
     * <p>fil270DspNumLabel をセットします。
     * @param fil270DspNumLabel fil270DspNumLabel
     */
    public void setFil270DspNumLabel(List<LabelValueBean> fil270DspNumLabel) {
        fil270DspNumLabel__ = fil270DspNumLabel;
    }
    /**
     * <p>fil270NowPage を取得します。
     * @return fil270NowPage
     */
    public int getFil270NowPage() {
        return fil270NowPage__;
    }
    /**
     * <p>fil270NowPage をセットします。
     * @param fil270NowPage fil270NowPage
     */
    public void setFil270NowPage(int fil270NowPage) {
        fil270NowPage__ = fil270NowPage;
    }
    /**
     * <p>fil270DspPage1 を取得します。
     * @return fil270DspPage1
     */
    public int getFil270DspPage1() {
        return fil270DspPage1__;
    }
    /**
     * <p>fil270DspPage1 をセットします。
     * @param fil270DspPage1 fil270DspPage1
     */
    public void setFil270DspPage1(int fil270DspPage1) {
        fil270DspPage1__ = fil270DspPage1;
    }
    /**
     * <p>fil270DspPage2 を取得します。
     * @return fil270DspPage2
     */
    public int getFil270DspPage2() {
        return fil270DspPage2__;
    }
    /**
     * <p>fil270DspPage2 をセットします。
     * @param fil270DspPage2 fil270DspPage2
     */
    public void setFil270DspPage2(int fil270DspPage2) {
        fil270DspPage2__ = fil270DspPage2;
    }
    /**
     * <p>fil270PageLabel を取得します。
     * @return fil270PageLabel
     */
    public List<LabelValueBean> getFil270PageLabel() {
        return fil270PageLabel__;
    }
    /**
     * <p>fil270PageLabel をセットします。
     * @param fil270PageLabel fil270PageLabel
     */
    public void setFil270PageLabel(List<LabelValueBean> fil270PageLabel) {
        fil270PageLabel__ = fil270PageLabel;
    }
    /**
     * <p>fil270GraphItem を取得します。
     * @return fil270GraphItem
     */
    public String getFil270GraphItem() {
        return fil270GraphItem__;
    }
    /**
     * <p>fil270GraphItem をセットします。
     * @param fil270GraphItem fil270GraphItem
     */
    public void setFil270GraphItem(String fil270GraphItem) {
        fil270GraphItem__ = fil270GraphItem;
    }
    /**
     * <p>fil270LogCountList を取得します。
     * @return fil270LogCountList
     */
    public List<Fil270DspModel> getFil270LogCountList() {
        return fil270LogCountList__;
    }
    /**
     * <p>fil270LogCountList をセットします。
     * @param fil270LogCountList fil270LogCountList
     */
    public void setFil270LogCountList(List<Fil270DspModel> fil270LogCountList) {
        fil270LogCountList__ = fil270LogCountList;
    }
    /**
     * <p>fil270AveDownloadNum を取得します。
     * @return fil270AveDownloadNum
     */
    public String getFil270AveDownloadNum() {
        return fil270AveDownloadNum__;
    }
    /**
     * <p>fil270AveDownloadNum をセットします。
     * @param fil270AveDownloadNum fil270AveDownloadNum
     */
    public void setFil270AveDownloadNum(String fil270AveDownloadNum) {
        fil270AveDownloadNum__ = fil270AveDownloadNum;
    }
    /**
     * <p>fil270AveUploadNum を取得します。
     * @return fil270AveUploadNum
     */
    public String getFil270AveUploadNum() {
        return fil270AveUploadNum__;
    }
    /**
     * <p>fil270AveUploadNum をセットします。
     * @param fil270AveUploadNum fil270AveUploadNum
     */
    public void setFil270AveUploadNum(String fil270AveUploadNum) {
        fil270AveUploadNum__ = fil270AveUploadNum;
    }
    /**
     * <p>fil270SumDownloadNum を取得します。
     * @return fil270SumDownloadNum
     */
    public String getFil270SumDownloadNum() {
        return fil270SumDownloadNum__;
    }
    /**
     * <p>fil270SumDownloadNum をセットします。
     * @param fil270SumDownloadNum fil270SumDownloadNum
     */
    public void setFil270SumDownloadNum(String fil270SumDownloadNum) {
        fil270SumDownloadNum__ = fil270SumDownloadNum;
    }
    /**
     * <p>fil270SumUploadNum を取得します。
     * @return fil270SumUploadNum
     */
    public String getFil270SumUploadNum() {
        return fil270SumUploadNum__;
    }
    /**
     * <p>fil270SumUploadNum をセットします。
     * @param fil270SumUploadNum fil270SumUploadNum
     */
    public void setFil270SumUploadNum(String fil270SumUploadNum) {
        fil270SumUploadNum__ = fil270SumUploadNum;
    }
    /**
     * <p>logCountBack を取得します。
     * @return logCountBack
     */
    public String getLogCountBack() {
        return logCountBack__;
    }
    /**
     * <p>logCountBack をセットします。
     * @param logCountBack logCountBack
     */
    public void setLogCountBack(String logCountBack) {
        logCountBack__ = logCountBack;
    }
    /**
     * <p>jsonDateData を取得します。
     * @return jsonDateData
     */
    public String getJsonDateData() {
        return jsonDateData__;
    }
    /**
     * <p>jsonDateData をセットします。
     * @param jsonDateData jsonDateData
     */
    public void setJsonDateData(String jsonDateData) {
        jsonDateData__ = jsonDateData;
    }
    /**
     * <p>jsonDownloadData を取得します。
     * @return jsonDownloadData
     */
    public String getJsonDownloadData() {
        return jsonDownloadData__;
    }
    /**
     * <p>jsonDownloadData をセットします。
     * @param jsonDownloadData jsonDownloadData
     */
    public void setJsonDownloadData(String jsonDownloadData) {
        jsonDownloadData__ = jsonDownloadData;
    }
    /**
     * <p>jsonUploadData を取得します。
     * @return jsonUploadData
     */
    public String getJsonUploadData() {
        return jsonUploadData__;
    }
    /**
     * <p>jsonUploadData をセットします。
     * @param jsonUploadData jsonUploadData
     */
    public void setJsonUploadData(String jsonUploadData) {
        jsonUploadData__ = jsonUploadData;
    }
    /**
     * <p>fil270CtrlFlgCht を取得します。
     * @return fil270CtrlFlgCht
     * @see jp.groupsession.v2.fil.fil270.Fil270Form#fil270CtrlFlgCht__
     */
    public boolean isFil270CtrlFlgCht() {
        return fil270CtrlFlgCht__;
    }
    /**
     * <p>fil270CtrlFlgCht をセットします。
     * @param fil270CtrlFlgCht fil270CtrlFlgCht
     * @see jp.groupsession.v2.fil.fil270.Fil270Form#fil270CtrlFlgCht__
     */
    public void setFil270CtrlFlgCht(boolean fil270CtrlFlgCht) {
        fil270CtrlFlgCht__ = fil270CtrlFlgCht;
    }

}