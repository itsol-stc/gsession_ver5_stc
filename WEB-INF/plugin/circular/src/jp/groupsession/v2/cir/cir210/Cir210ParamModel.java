package jp.groupsession.v2.cir.cir210;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir100.Cir100ParamModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] 回覧板統計情報画面のパラメータモデルクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Cir210ParamModel extends Cir100ParamModel {

    /** メイン管理者権限フラグ */
    private boolean cir210GsAdminFlg__ = false;
    /** 使用可能フラグ WEBメール */
    private boolean cir210CtrlFlgWml__ = false;
    /** 使用可能フラグ ショートメール */
    private boolean cir210CtrlFlgSml__ = false;
    /** 使用可能フラグ ファイル管理 */
    private boolean cir210CtrlFlgFil__ = false;
    /** 使用可能フラグ 掲示板 */
    private boolean cir210CtrlFlgBbs__ = false;
    /** 使用可能フラグ チャット */
    private boolean cir210CtrlFlgCht__ = false;

    /** 日付表示単位 */
    private int cir210DateUnit__ = GSConstMain.STATS_DATE_UNIT_MONTH;
    /** 年指定コンボ */
    private List<LabelValueBean> cir210DspYearLabel__;
    /** 月指定コンボ */
    private List<LabelValueBean> cir210DspMonthLabel__;
    /** 週指定Fromコンボ */
    private List<LabelValueBean> cir210DspWeekFrLabel__;
    /** 週指定Toコンボ */
    private List<LabelValueBean> cir210DspWeekToLabel__;
    /** 月別 日付 開始年 */
    private String cir210DateMonthlyFrYear__;
    /** 月別 日付 開始月 */
    private String cir210DateMonthlyFrMonth__;
    /** 月別 日付 終了年 */
    private String cir210DateMonthlyToYear__;
    /** 月別 日付 終了月 */
    private String cir210DateMonthlyToMonth__;
    /** 週別 日付範囲 開始 */
    private String cir210DateWeeklyFrStr__;
    /** 週別 日付範囲 終了 */
    private String cir210DateWeeklyToStr__;
    /** 日別 日付範囲 開始 */
    private String cir210DateDailyFrStr__;
    /** 日別 日付範囲 終了 */
    private String cir210DateDailyToStr__;
    /** 表示件数 */
    private int cir210DspNum__ = 40;
    /** 表示件数コンボ */
    private List<LabelValueBean> cir210DspNumLabel__;

    /** 現在ページ */
    private int cir210NowPage__ = 1;
    /** 表示ページ（上） */
    private int cir210DspPage1__;
    /** 表示ページ（下） */
    private int cir210DspPage2__;
    /** ページラベル */
    private List<LabelValueBean> cir210PageLabel__;
    /** 現在のグラフの項目 */
    private String cir210GraphItem__ = GSConstCircular.CIR_LOG_GRAPH_JMAIL;

    /** 集計データ一覧 */
    private List<Cir210DspModel> cir210LogCountList__;
    /** 平均件数 受信回覧板 */
    private String cir210AveJcirNum__;
    /** 平均件数 送信回覧板 */
    private String cir210AveScirNum__;
    /** 合計件数 受信回覧板 */
    private String cir210SumJcirNum__;
    /** 合計件数 送信回覧板 */
    private String cir210SumScirNum__;

    /** 統計情報の戻り先 */
    private String logCountBack__;

    /** グラフデータ（日付） */
    private String jsonDateData__;
    /** グラフデータ（受信数） */
    private String jsonJcirData__;
    /** グラフデータ（送信数） */
    private String jsonScirData__;

    /**
     * <p>cir210GsAdminFlg を取得します。
     * @return cir210GsAdminFlg
     */
    public boolean isCir210GsAdminFlg() {
        return cir210GsAdminFlg__;
    }
    /**
     * <p>cir210GsAdminFlg をセットします。
     * @param cir210GsAdminFlg cir210GsAdminFlg
     */
    public void setCir210GsAdminFlg(boolean cir210GsAdminFlg) {
        cir210GsAdminFlg__ = cir210GsAdminFlg;
    }
    /**
     * <p>cir210CtrlFlgWml を取得します。
     * @return cir210CtrlFlgWml
     */
    public boolean isCir210CtrlFlgWml() {
        return cir210CtrlFlgWml__;
    }
    /**
     * <p>cir210CtrlFlgWml をセットします。
     * @param cir210CtrlFlgWml cir210CtrlFlgWml
     */
    public void setCir210CtrlFlgWml(boolean cir210CtrlFlgWml) {
        cir210CtrlFlgWml__ = cir210CtrlFlgWml;
    }
    /**
     * <p>cir210CtrlFlgSml を取得します。
     * @return cir210CtrlFlgSml
     */
    public boolean isCir210CtrlFlgSml() {
        return cir210CtrlFlgSml__;
    }
    /**
     * <p>cir210CtrlFlgSml をセットします。
     * @param cir210CtrlFlgSml cir210CtrlFlgSml
     */
    public void setCir210CtrlFlgSml(boolean cir210CtrlFlgSml) {
        cir210CtrlFlgSml__ = cir210CtrlFlgSml;
    }
    /**
     * <p>cir210CtrlFlgFil を取得します。
     * @return cir210CtrlFlgFil
     */
    public boolean isCir210CtrlFlgFil() {
        return cir210CtrlFlgFil__;
    }
    /**
     * <p>cir210CtrlFlgFil をセットします。
     * @param cir210CtrlFlgFil cir210CtrlFlgFil
     */
    public void setCir210CtrlFlgFil(boolean cir210CtrlFlgFil) {
        cir210CtrlFlgFil__ = cir210CtrlFlgFil;
    }
    /**
     * <p>cir210CtrlFlgBbs を取得します。
     * @return cir210CtrlFlgBbs
     */
    public boolean isCir210CtrlFlgBbs() {
        return cir210CtrlFlgBbs__;
    }
    /**
     * <p>cir210CtrlFlgBbs をセットします。
     * @param cir210CtrlFlgBbs cir210CtrlFlgBbs
     */
    public void setCir210CtrlFlgBbs(boolean cir210CtrlFlgBbs) {
        cir210CtrlFlgBbs__ = cir210CtrlFlgBbs;
    }
    /**
     * <p>cir210DateUnit を取得します。
     * @return cir210DateUnit
     */
    public int getCir210DateUnit() {
        return cir210DateUnit__;
    }
    /**
     * <p>cir210DateUnit をセットします。
     * @param cir210DateUnit cir210DateUnit
     */
    public void setCir210DateUnit(int cir210DateUnit) {
        cir210DateUnit__ = cir210DateUnit;
    }
    /**
     * <p>cir210DspYearLabel を取得します。
     * @return cir210DspYearLabel
     */
    public List<LabelValueBean> getCir210DspYearLabel() {
        return cir210DspYearLabel__;
    }
    /**
     * <p>cir210DspYearLabel をセットします。
     * @param cir210DspYearLabel cir210DspYearLabel
     */
    public void setCir210DspYearLabel(List<LabelValueBean> cir210DspYearLabel) {
        cir210DspYearLabel__ = cir210DspYearLabel;
    }
    /**
     * <p>cir210DspMonthLabel を取得します。
     * @return cir210DspMonthLabel
     */
    public List<LabelValueBean> getCir210DspMonthLabel() {
        return cir210DspMonthLabel__;
    }
    /**
     * <p>cir210DspMonthLabel をセットします。
     * @param cir210DspMonthLabel cir210DspMonthLabel
     */
    public void setCir210DspMonthLabel(List<LabelValueBean> cir210DspMonthLabel) {
        cir210DspMonthLabel__ = cir210DspMonthLabel;
    }
    /**
     * <p>cir210DspWeekFrLabel を取得します。
     * @return cir210DspWeekFrLabel
     */
    public List<LabelValueBean> getCir210DspWeekFrLabel() {
        return cir210DspWeekFrLabel__;
    }
    /**
     * <p>cir210DspWeekFrLabel をセットします。
     * @param cir210DspWeekFrLabel cir210DspWeekFrLabel
     */
    public void setCir210DspWeekFrLabel(List<LabelValueBean> cir210DspWeekFrLabel) {
        cir210DspWeekFrLabel__ = cir210DspWeekFrLabel;
    }
    /**
     * <p>cir210DspWeekToLabel を取得します。
     * @return cir210DspWeekToLabel
     */
    public List<LabelValueBean> getCir210DspWeekToLabel() {
        return cir210DspWeekToLabel__;
    }
    /**
     * <p>cir210DspWeekToLabel をセットします。
     * @param cir210DspWeekToLabel cir210DspWeekToLabel
     */
    public void setCir210DspWeekToLabel(List<LabelValueBean> cir210DspWeekToLabel) {
        cir210DspWeekToLabel__ = cir210DspWeekToLabel;
    }
    /**
     * <p>cir210DateMonthlyFrYear を取得します。
     * @return cir210DateMonthlyFrYear
     */
    public String getCir210DateMonthlyFrYear() {
        return cir210DateMonthlyFrYear__;
    }
    /**
     * <p>cir210DateMonthlyFrYear をセットします。
     * @param cir210DateMonthlyFrYear cir210DateMonthlyFrYear
     */
    public void setCir210DateMonthlyFrYear(String cir210DateMonthlyFrYear) {
        cir210DateMonthlyFrYear__ = cir210DateMonthlyFrYear;
    }
    /**
     * <p>cir210DateMonthlyFrMonth を取得します。
     * @return cir210DateMonthlyFrMonth
     */
    public String getCir210DateMonthlyFrMonth() {
        return cir210DateMonthlyFrMonth__;
    }
    /**
     * <p>cir210DateMonthlyFrMonth をセットします。
     * @param cir210DateMonthlyFrMonth cir210DateMonthlyFrMonth
     */
    public void setCir210DateMonthlyFrMonth(String cir210DateMonthlyFrMonth) {
        cir210DateMonthlyFrMonth__ = cir210DateMonthlyFrMonth;
    }
    /**
     * <p>cir210DateMonthlyToYear を取得します。
     * @return cir210DateMonthlyToYear
     */
    public String getCir210DateMonthlyToYear() {
        return cir210DateMonthlyToYear__;
    }
    /**
     * <p>cir210DateMonthlyToYear をセットします。
     * @param cir210DateMonthlyToYear cir210DateMonthlyToYear
     */
    public void setCir210DateMonthlyToYear(String cir210DateMonthlyToYear) {
        cir210DateMonthlyToYear__ = cir210DateMonthlyToYear;
    }
    /**
     * <p>cir210DateMonthlyToMonth を取得します。
     * @return cir210DateMonthlyToMonth
     */
    public String getCir210DateMonthlyToMonth() {
        return cir210DateMonthlyToMonth__;
    }
    /**
     * <p>cir210DateMonthlyToMonth をセットします。
     * @param cir210DateMonthlyToMonth cir210DateMonthlyToMonth
     */
    public void setCir210DateMonthlyToMonth(String cir210DateMonthlyToMonth) {
        cir210DateMonthlyToMonth__ = cir210DateMonthlyToMonth;
    }
    /**
     * <p>cir210DateWeeklyFrStr を取得します。
     * @return cir210DateWeeklyFrStr
     */
    public String getCir210DateWeeklyFrStr() {
        return cir210DateWeeklyFrStr__;
    }
    /**
     * <p>cir210DateWeeklyFrStr をセットします。
     * @param cir210DateWeeklyFrStr cir210DateWeeklyFrStr
     */
    public void setCir210DateWeeklyFrStr(String cir210DateWeeklyFrStr) {
        cir210DateWeeklyFrStr__ = cir210DateWeeklyFrStr;
    }
    /**
     * <p>cir210DateWeeklyToStr を取得します。
     * @return cir210DateWeeklyToStr
     */
    public String getCir210DateWeeklyToStr() {
        return cir210DateWeeklyToStr__;
    }
    /**
     * <p>cir210DateWeeklyToStr をセットします。
     * @param cir210DateWeeklyToStr cir210DateWeeklyToStr
     */
    public void setCir210DateWeeklyToStr(String cir210DateWeeklyToStr) {
        cir210DateWeeklyToStr__ = cir210DateWeeklyToStr;
    }
    /**
     * <p>cir210DateDailyFrStr を取得します。
     * @return cir210DateDailyFrStr
     */
    public String getCir210DateDailyFrStr() {
        return cir210DateDailyFrStr__;
    }
    /**
     * <p>cir210DateDailyFrStr をセットします。
     * @param cir210DateDailyFrStr cir210DateDailyFrStr
     */
    public void setCir210DateDailyFrStr(String cir210DateDailyFrStr) {
        cir210DateDailyFrStr__ = cir210DateDailyFrStr;
    }
    /**
     * <p>cir210DateDailyToStr を取得します。
     * @return cir210DateDailyToStr
     */
    public String getCir210DateDailyToStr() {
        return cir210DateDailyToStr__;
    }
    /**
     * <p>cir210DateDailyToStr をセットします。
     * @param cir210DateDailyToStr cir210DateDailyToStr
     */
    public void setCir210DateDailyToStr(String cir210DateDailyToStr) {
        cir210DateDailyToStr__ = cir210DateDailyToStr;
    }
    /**
     * <p>cir210DspNum を取得します。
     * @return cir210DspNum
     */
    public int getCir210DspNum() {
        return cir210DspNum__;
    }
    /**
     * <p>cir210DspNum をセットします。
     * @param cir210DspNum cir210DspNum
     */
    public void setCir210DspNum(int cir210DspNum) {
        cir210DspNum__ = cir210DspNum;
    }
    /**
     * <p>cir210DspNumLabel を取得します。
     * @return cir210DspNumLabel
     */
    public List<LabelValueBean> getCir210DspNumLabel() {
        return cir210DspNumLabel__;
    }
    /**
     * <p>cir210DspNumLabel をセットします。
     * @param cir210DspNumLabel cir210DspNumLabel
     */
    public void setCir210DspNumLabel(List<LabelValueBean> cir210DspNumLabel) {
        cir210DspNumLabel__ = cir210DspNumLabel;
    }
    /**
     * <p>cir210NowPage を取得します。
     * @return cir210NowPage
     */
    public int getCir210NowPage() {
        return cir210NowPage__;
    }
    /**
     * <p>cir210NowPage をセットします。
     * @param cir210NowPage cir210NowPage
     */
    public void setCir210NowPage(int cir210NowPage) {
        cir210NowPage__ = cir210NowPage;
    }
    /**
     * <p>cir210DspPage1 を取得します。
     * @return cir210DspPage1
     */
    public int getCir210DspPage1() {
        return cir210DspPage1__;
    }
    /**
     * <p>cir210DspPage1 をセットします。
     * @param cir210DspPage1 cir210DspPage1
     */
    public void setCir210DspPage1(int cir210DspPage1) {
        cir210DspPage1__ = cir210DspPage1;
    }
    /**
     * <p>cir210DspPage2 を取得します。
     * @return cir210DspPage2
     */
    public int getCir210DspPage2() {
        return cir210DspPage2__;
    }
    /**
     * <p>cir210DspPage2 をセットします。
     * @param cir210DspPage2 cir210DspPage2
     */
    public void setCir210DspPage2(int cir210DspPage2) {
        cir210DspPage2__ = cir210DspPage2;
    }
    /**
     * <p>cir210PageLabel を取得します。
     * @return cir210PageLabel
     */
    public List<LabelValueBean> getCir210PageLabel() {
        return cir210PageLabel__;
    }
    /**
     * <p>cir210PageLabel をセットします。
     * @param cir210PageLabel cir210PageLabel
     */
    public void setCir210PageLabel(List<LabelValueBean> cir210PageLabel) {
        cir210PageLabel__ = cir210PageLabel;
    }
    /**
     * <p>cir210GraphItem を取得します。
     * @return cir210GraphItem
     */
    public String getCir210GraphItem() {
        return cir210GraphItem__;
    }
    /**
     * <p>cir210GraphItem をセットします。
     * @param cir210GraphItem cir210GraphItem
     */
    public void setCir210GraphItem(String cir210GraphItem) {
        cir210GraphItem__ = cir210GraphItem;
    }
    /**
     * <p>cir210LogCountList を取得します。
     * @return cir210LogCountList
     */
    public List<Cir210DspModel> getCir210LogCountList() {
        return cir210LogCountList__;
    }
    /**
     * <p>cir210LogCountList をセットします。
     * @param cir210LogCountList cir210LogCountList
     */
    public void setCir210LogCountList(List<Cir210DspModel> cir210LogCountList) {
        cir210LogCountList__ = cir210LogCountList;
    }
    /**
     * <p>cir210AveJcirNum を取得します。
     * @return cir210AveJcirNum
     */
    public String getCir210AveJcirNum() {
        return cir210AveJcirNum__;
    }
    /**
     * <p>cir210AveJcirNum をセットします。
     * @param cir210AveJcirNum cir210AveJcirNum
     */
    public void setCir210AveJcirNum(String cir210AveJcirNum) {
        cir210AveJcirNum__ = cir210AveJcirNum;
    }
    /**
     * <p>cir210AveScirNum を取得します。
     * @return cir210AveScirNum
     */
    public String getCir210AveScirNum() {
        return cir210AveScirNum__;
    }
    /**
     * <p>cir210AveScirNum をセットします。
     * @param cir210AveScirNum cir210AveScirNum
     */
    public void setCir210AveScirNum(String cir210AveScirNum) {
        cir210AveScirNum__ = cir210AveScirNum;
    }
    /**
     * <p>cir210SumJcirNum を取得します。
     * @return cir210SumJcirNum
     */
    public String getCir210SumJcirNum() {
        return cir210SumJcirNum__;
    }
    /**
     * <p>cir210SumJcirNum をセットします。
     * @param cir210SumJcirNum cir210SumJcirNum
     */
    public void setCir210SumJcirNum(String cir210SumJcirNum) {
        cir210SumJcirNum__ = cir210SumJcirNum;
    }
    /**
     * <p>cir210SumScirNum を取得します。
     * @return cir210SumScirNum
     */
    public String getCir210SumScirNum() {
        return cir210SumScirNum__;
    }
    /**
     * <p>cir210SumScirNum をセットします。
     * @param cir210SumScirNum cir210SumScirNum
     */
    public void setCir210SumScirNum(String cir210SumScirNum) {
        cir210SumScirNum__ = cir210SumScirNum;
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
     * <p>jsonJcirData を取得します。
     * @return jsonJcirData
     */
    public String getJsonJcirData() {
        return jsonJcirData__;
    }
    /**
     * <p>jsonJcirData をセットします。
     * @param jsonJcirData jsonJcirData
     */
    public void setJsonJcirData(String jsonJcirData) {
        jsonJcirData__ = jsonJcirData;
    }
    /**
     * <p>jsonScirData を取得します。
     * @return jsonScirData
     */
    public String getJsonScirData() {
        return jsonScirData__;
    }
    /**
     * <p>jsonScirData をセットします。
     * @param jsonScirData jsonScirData
     */
    public void setJsonScirData(String jsonScirData) {
        jsonScirData__ = jsonScirData;
    }
    /**
     * <p>cir210CtrlFlgCht を取得します。
     * @return cir210CtrlFlgCht
     * @see jp.groupsession.v2.cir.cir210.Cir210ParamModel#cir210CtrlFlgCht__
     */
    public boolean isCir210CtrlFlgCht() {
        return cir210CtrlFlgCht__;
    }
    /**
     * <p>cir210CtrlFlgCht をセットします。
     * @param cir210CtrlFlgCht cir210CtrlFlgCht
     * @see jp.groupsession.v2.cir.cir210.Cir210ParamModel#cir210CtrlFlgCht__
     */
    public void setCir210CtrlFlgCht(boolean cir210CtrlFlgCht) {
        cir210CtrlFlgCht__ = cir210CtrlFlgCht;
    }
}