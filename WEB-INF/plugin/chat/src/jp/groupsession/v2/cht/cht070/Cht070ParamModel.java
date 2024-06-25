package jp.groupsession.v2.cht.cht070;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht020.Cht020ParamModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] 統計情報画面のパラメータモデルクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Cht070ParamModel extends Cht020ParamModel {

    /** 統計情報の戻り先 */
    private String logCountBack__ = null;
    /** 初期フラグ */
    private int cht070InitFlg__ = GSConstMain.DSP_FIRST;

    /** メイン管理者権限フラグ */
    private boolean cht070GsAdminFlg__ = false;
    /** 使用可能フラグ WEBメール */
    private boolean cht070CtrlFlgWml__ = false;
    /** 使用可能フラグ ショートメール */
    private boolean cht070CtrlFlgSml__ = false;
    /** 使用可能フラグ 回覧板 */
    private boolean cht070CtrlFlgCir__ = false;
    /** 使用可能フラグ ファイル管理 */
    private boolean cht070CtrlFlgFil__ = false;
    /** 使用可能フラグ 掲示板 */
    private boolean cht070CtrlFlgBul__ = false;

    /** 日付表示単位 */
    private int cht070DateUnit__ = GSConstMain.STATS_DATE_UNIT_MONTH;
    /** 年指定コンボ */
    private List<LabelValueBean> cht070DspYearLabel__ = null;
    /** 月指定コンボ */
    private List<LabelValueBean> cht070DspMonthLabel__ = null;
    /** 週指定Fromコンボ */
    private List<LabelValueBean> cht070DspWeekFrLabel__ = null;
    /** 週指定Toコンボ */
    private List<LabelValueBean> cht070DspWeekToLabel__ = null;
    /** 月別 日付 開始年 */
    private String cht070DateMonthlyFrYear__ = null;
    /** 月別 日付 開始月 */
    private String cht070DateMonthlyFrMonth__ = null;
    /** 月別 日付 終了年 */
    private String cht070DateMonthlyToYear__ = null;
    /** 月別 日付 終了月 */
    private String cht070DateMonthlyToMonth__ = null;
    /** 週別 日付範囲 開始 */
    private String cht070DateWeeklyFrStr__ = null;
    /** 週別 日付範囲 終了 */
    private String cht070DateWeeklyToStr__ = null;
    /** 日別 日付範囲 開始 */
    private String cht070DateDailyFrStr__ = null;
    /** 日別 日付範囲 終了 */
    private String cht070DateDailyToStr__ = null;
    /** 表示件数 */
    private int cht070DspNum__ = GSConstChat.CHAT_LOG_DEFAULT_DSP_COUNT;
    /** 表示件数コンボ */
    private List<LabelValueBean> cht070DspNumLabel__ = null;

    /** 表示項目合計 */
    private int cht070DspSum__ = -1;
    /** 表示項目ユーザ */
    private int cht070DspUser__ = -1;
    /** 表示項目グループ */
    private int cht070DspGroup__ = -1;

    /** 現在ページ */
    private int cht070NowPage__ = 1;
    /** 表示ページ（上） */
    private int cht070DspPage1__ = 1;
    /** 表示ページ（下） */
    private int cht070DspPage2__ = 1;
    /** ページラベル */
    private List<LabelValueBean> cht070PageLabel__;
    /** 現在のグラフの項目 */
    private String cht070GraphItem__ = GSConstChat.CHAT_LOG_GRAPH_MESSAGE;

    /** 集計データ一覧 */
    private List<Cht070DspModel> cht070LogCountList__ = null;

    /** 合計件数 ユーザ */
    private String cht070TotalMessageUser__ = null;
    /** 合計件数 グループ */
    private String cht070TotalMessageGroup__ = null;
    /** 合計件数 合計 */
    private String cht070TotalMessage__ = null;
    /** 合計件数 送信 */
    private String cht070TotalSend__ = null;


    /** グラフデータ（日付） */
    private String jsonDateData__ = null;
    /** グラフデータ（ユーザ） */
    private String jsonUserData__ = null;
    /** グラフデータ（グループ） */
    private String jsonGroupData__ = null;
    /** グラフデータ（合計） */
    private String jsonSumData__ = null;
    /** グラフデータ（送信数） */
    private String jsonSendData__ = null;


    /**
     * <p>logCountBack を取得します。
     * @return logCountBack
     * @see jp.groupsession.v2.cht.cht070.Cht070Form#logCountBack__
     */
    public String getLogCountBack() {
        return logCountBack__;
    }

    /**
     * <p>logCountBack をセットします。
     * @param logCountBack logCountBack
     * @see jp.groupsession.v2.cht.cht070.Cht070Form#logCountBack__
     */
    public void setLogCountBack(String logCountBack) {
        logCountBack__ = logCountBack;
    }

    /**
     * <p>cht070GsAdminFlg を取得します。
     * @return cht070GsAdminFlg
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070GsAdminFlg__
     */
    public boolean isCht070GsAdminFlg() {
        return cht070GsAdminFlg__;
    }

    /**
     * <p>cht070GsAdminFlg をセットします。
     * @param cht070GsAdminFlg cht070GsAdminFlg
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070GsAdminFlg__
     */
    public void setCht070GsAdminFlg(boolean cht070GsAdminFlg) {
        cht070GsAdminFlg__ = cht070GsAdminFlg;
    }

    /**
     * <p>cht070CtrlFlgWml を取得します。
     * @return cht070CtrlFlgWml
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070CtrlFlgWml__
     */
    public boolean isCht070CtrlFlgWml() {
        return cht070CtrlFlgWml__;
    }

    /**
     * <p>cht070CtrlFlgWml をセットします。
     * @param cht070CtrlFlgWml cht070CtrlFlgWml
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070CtrlFlgWml__
     */
    public void setCht070CtrlFlgWml(boolean cht070CtrlFlgWml) {
        cht070CtrlFlgWml__ = cht070CtrlFlgWml;
    }

    /**
     * <p>cht070CtrlFlgSml を取得します。
     * @return cht070CtrlFlgSml
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070CtrlFlgSml__
     */
    public boolean isCht070CtrlFlgSml() {
        return cht070CtrlFlgSml__;
    }

    /**
     * <p>cht070CtrlFlgSml をセットします。
     * @param cht070CtrlFlgSml cht070CtrlFlgSml
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070CtrlFlgSml__
     */
    public void setCht070CtrlFlgSml(boolean cht070CtrlFlgSml) {
        cht070CtrlFlgSml__ = cht070CtrlFlgSml;
    }

    /**
     * <p>cht070CtrlFlgCir を取得します。
     * @return cht070CtrlFlgCir
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070CtrlFlgCir__
     */
    public boolean isCht070CtrlFlgCir() {
        return cht070CtrlFlgCir__;
    }

    /**
     * <p>cht070CtrlFlgCir をセットします。
     * @param cht070CtrlFlgCir cht070CtrlFlgCir
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070CtrlFlgCir__
     */
    public void setCht070CtrlFlgCir(boolean cht070CtrlFlgCir) {
        cht070CtrlFlgCir__ = cht070CtrlFlgCir;
    }

    /**
     * <p>cht070CtrlFlgFil を取得します。
     * @return cht070CtrlFlgFil
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070CtrlFlgFil__
     */
    public boolean isCht070CtrlFlgFil() {
        return cht070CtrlFlgFil__;
    }

    /**
     * <p>cht070CtrlFlgFil をセットします。
     * @param cht070CtrlFlgFil cht070CtrlFlgFil
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070CtrlFlgFil__
     */
    public void setCht070CtrlFlgFil(boolean cht070CtrlFlgFil) {
        cht070CtrlFlgFil__ = cht070CtrlFlgFil;
    }

    /**
     * <p>cht070CtrlFlgBul を取得します。
     * @return cht070CtrlFlgBul
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070CtrlFlgBul__
     */
    public boolean isCht070CtrlFlgBul() {
        return cht070CtrlFlgBul__;
    }

    /**
     * <p>cht070CtrlFlgBul をセットします。
     * @param cht070CtrlFlgBul cht070CtrlFlgBul
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070CtrlFlgBul__
     */
    public void setCht070CtrlFlgBul(boolean cht070CtrlFlgBul) {
        cht070CtrlFlgBul__ = cht070CtrlFlgBul;
    }

    /**
     * <p>cht070DateUnit を取得します。
     * @return cht070DateUnit
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateUnit__
     */
    public int getCht070DateUnit() {
        return cht070DateUnit__;
    }

    /**
     * <p>cht070DateUnit をセットします。
     * @param cht070DateUnit cht070DateUnit
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateUnit__
     */
    public void setCht070DateUnit(int cht070DateUnit) {
        cht070DateUnit__ = cht070DateUnit;
    }

    /**
     * <p>cht070DspYearLabel を取得します。
     * @return cht070DspYearLabel
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspYearLabel__
     */
    public List<LabelValueBean> getCht070DspYearLabel() {
        return cht070DspYearLabel__;
    }

    /**
     * <p>cht070DspYearLabel をセットします。
     * @param cht070DspYearLabel cht070DspYearLabel
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspYearLabel__
     */
    public void setCht070DspYearLabel(List<LabelValueBean> cht070DspYearLabel) {
        cht070DspYearLabel__ = cht070DspYearLabel;
    }

    /**
     * <p>cht070DspMonthLabel を取得します。
     * @return cht070DspMonthLabel
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspMonthLabel__
     */
    public List<LabelValueBean> getCht070DspMonthLabel() {
        return cht070DspMonthLabel__;
    }

    /**
     * <p>cht070DspMonthLabel をセットします。
     * @param cht070DspMonthLabel cht070DspMonthLabel
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspMonthLabel__
     */
    public void setCht070DspMonthLabel(List<LabelValueBean> cht070DspMonthLabel) {
        cht070DspMonthLabel__ = cht070DspMonthLabel;
    }

    /**
     * <p>cht070DspWeekFrLabel を取得します。
     * @return cht070DspWeekFrLabel
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspWeekFrLabel__
     */
    public List<LabelValueBean> getCht070DspWeekFrLabel() {
        return cht070DspWeekFrLabel__;
    }

    /**
     * <p>cht070DspWeekFrLabel をセットします。
     * @param cht070DspWeekFrLabel cht070DspWeekFrLabel
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspWeekFrLabel__
     */
    public void setCht070DspWeekFrLabel(List<LabelValueBean> cht070DspWeekFrLabel) {
        cht070DspWeekFrLabel__ = cht070DspWeekFrLabel;
    }

    /**
     * <p>cht070DspWeekToLabel を取得します。
     * @return cht070DspWeekToLabel
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspWeekToLabel__
     */
    public List<LabelValueBean> getCht070DspWeekToLabel() {
        return cht070DspWeekToLabel__;
    }

    /**
     * <p>cht070DspWeekToLabel をセットします。
     * @param cht070DspWeekToLabel cht070DspWeekToLabel
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspWeekToLabel__
     */
    public void setCht070DspWeekToLabel(List<LabelValueBean> cht070DspWeekToLabel) {
        cht070DspWeekToLabel__ = cht070DspWeekToLabel;
    }

    /**
     * <p>cht070DateMonthlyFrYear を取得します。
     * @return cht070DateMonthlyFrYear
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateMonthlyFrYear__
     */
    public String getCht070DateMonthlyFrYear() {
        return cht070DateMonthlyFrYear__;
    }

    /**
     * <p>cht070DateMonthlyFrYear をセットします。
     * @param cht070DateMonthlyFrYear cht070DateMonthlyFrYear
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateMonthlyFrYear__
     */
    public void setCht070DateMonthlyFrYear(String cht070DateMonthlyFrYear) {
        cht070DateMonthlyFrYear__ = cht070DateMonthlyFrYear;
    }

    /**
     * <p>cht070DateMonthlyFrMonth を取得します。
     * @return cht070DateMonthlyFrMonth
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateMonthlyFrMonth__
     */
    public String getCht070DateMonthlyFrMonth() {
        return cht070DateMonthlyFrMonth__;
    }

    /**
     * <p>cht070DateMonthlyFrMonth をセットします。
     * @param cht070DateMonthlyFrMonth cht070DateMonthlyFrMonth
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateMonthlyFrMonth__
     */
    public void setCht070DateMonthlyFrMonth(String cht070DateMonthlyFrMonth) {
        cht070DateMonthlyFrMonth__ = cht070DateMonthlyFrMonth;
    }

    /**
     * <p>cht070DateMonthlyToYear を取得します。
     * @return cht070DateMonthlyToYear
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateMonthlyToYear__
     */
    public String getCht070DateMonthlyToYear() {
        return cht070DateMonthlyToYear__;
    }

    /**
     * <p>cht070DateMonthlyToYear をセットします。
     * @param cht070DateMonthlyToYear cht070DateMonthlyToYear
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateMonthlyToYear__
     */
    public void setCht070DateMonthlyToYear(String cht070DateMonthlyToYear) {
        cht070DateMonthlyToYear__ = cht070DateMonthlyToYear;
    }

    /**
     * <p>cht070DateMonthlyToMonth を取得します。
     * @return cht070DateMonthlyToMonth
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateMonthlyToMonth__
     */
    public String getCht070DateMonthlyToMonth() {
        return cht070DateMonthlyToMonth__;
    }

    /**
     * <p>cht070DateMonthlyToMonth をセットします。
     * @param cht070DateMonthlyToMonth cht070DateMonthlyToMonth
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateMonthlyToMonth__
     */
    public void setCht070DateMonthlyToMonth(String cht070DateMonthlyToMonth) {
        cht070DateMonthlyToMonth__ = cht070DateMonthlyToMonth;
    }

    /**
     * <p>cht070DateWeeklyFrStr を取得します。
     * @return cht070DateWeeklyFrStr
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateWeeklyFrStr__
     */
    public String getCht070DateWeeklyFrStr() {
        return cht070DateWeeklyFrStr__;
    }

    /**
     * <p>cht070DateWeeklyFrStr をセットします。
     * @param cht070DateWeeklyFrStr cht070DateWeeklyFrStr
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateWeeklyFrStr__
     */
    public void setCht070DateWeeklyFrStr(String cht070DateWeeklyFrStr) {
        cht070DateWeeklyFrStr__ = cht070DateWeeklyFrStr;
    }

    /**
     * <p>cht070DateWeeklyToStr を取得します。
     * @return cht070DateWeeklyToStr
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateWeeklyToStr__
     */
    public String getCht070DateWeeklyToStr() {
        return cht070DateWeeklyToStr__;
    }

    /**
     * <p>cht070DateWeeklyToStr をセットします。
     * @param cht070DateWeeklyToStr cht070DateWeeklyToStr
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateWeeklyToStr__
     */
    public void setCht070DateWeeklyToStr(String cht070DateWeeklyToStr) {
        cht070DateWeeklyToStr__ = cht070DateWeeklyToStr;
    }

    /**
     * <p>cht070DateDailyFrStr を取得します。
     * @return cht070DateDailyFrStr
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateDailyFrStr__
     */
    public String getCht070DateDailyFrStr() {
        return cht070DateDailyFrStr__;
    }

    /**
     * <p>cht070DateDailyFrStr をセットします。
     * @param cht070DateDailyFrStr cht070DateDailyFrStr
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateDailyFrStr__
     */
    public void setCht070DateDailyFrStr(String cht070DateDailyFrStr) {
        cht070DateDailyFrStr__ = cht070DateDailyFrStr;
    }

    /**
     * <p>cht070DateDailyToStr を取得します。
     * @return cht070DateDailyToStr
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateDailyToStr__
     */
    public String getCht070DateDailyToStr() {
        return cht070DateDailyToStr__;
    }

    /**
     * <p>cht070DateDailyToStr をセットします。
     * @param cht070DateDailyToStr cht070DateDailyToStr
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DateDailyToStr__
     */
    public void setCht070DateDailyToStr(String cht070DateDailyToStr) {
        cht070DateDailyToStr__ = cht070DateDailyToStr;
    }

    /**
     * <p>cht070DspNum を取得します。
     * @return cht070DspNum
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspNum__
     */
    public int getCht070DspNum() {
        return cht070DspNum__;
    }

    /**
     * <p>cht070DspNum をセットします。
     * @param cht070DspNum cht070DspNum
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspNum__
     */
    public void setCht070DspNum(int cht070DspNum) {
        cht070DspNum__ = cht070DspNum;
    }

    /**
     * <p>cht070DspNumLabel を取得します。
     * @return cht070DspNumLabel
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspNumLabel__
     */
    public List<LabelValueBean> getCht070DspNumLabel() {
        return cht070DspNumLabel__;
    }

    /**
     * <p>cht070DspNumLabel をセットします。
     * @param cht070DspNumLabel cht070DspNumLabel
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspNumLabel__
     */
    public void setCht070DspNumLabel(List<LabelValueBean> cht070DspNumLabel) {
        cht070DspNumLabel__ = cht070DspNumLabel;
    }

    /**
     * <p>cht070DspSum を取得します。
     * @return cht070DspSum
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspSum__
     */
    public int getCht070DspSum() {
        return cht070DspSum__;
    }

    /**
     * <p>cht070DspSum をセットします。
     * @param cht070DspSum cht070DspSum
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspSum__
     */
    public void setCht070DspSum(int cht070DspSum) {
        cht070DspSum__ = cht070DspSum;
    }

    /**
     * <p>cht070DspUser を取得します。
     * @return cht070DspUser
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspUser__
     */
    public int getCht070DspUser() {
        return cht070DspUser__;
    }

    /**
     * <p>cht070DspUser をセットします。
     * @param cht070DspUser cht070DspUser
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspUser__
     */
    public void setCht070DspUser(int cht070DspUser) {
        cht070DspUser__ = cht070DspUser;
    }

    /**
     * <p>cht070DspGroup を取得します。
     * @return cht070DspGroup
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspGroup__
     */
    public int getCht070DspGroup() {
        return cht070DspGroup__;
    }

    /**
     * <p>cht070DspGroup をセットします。
     * @param cht070DspGroup cht070DspGroup
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspGroup__
     */
    public void setCht070DspGroup(int cht070DspGroup) {
        cht070DspGroup__ = cht070DspGroup;
    }

    /**
     * <p>cht070NowPage を取得します。
     * @return cht070NowPage
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070NowPage__
     */
    public int getCht070NowPage() {
        return cht070NowPage__;
    }

    /**
     * <p>cht070NowPage をセットします。
     * @param cht070NowPage cht070NowPage
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070NowPage__
     */
    public void setCht070NowPage(int cht070NowPage) {
        cht070NowPage__ = cht070NowPage;
    }

    /**
     * <p>cht070DspPage1 を取得します。
     * @return cht070DspPage1
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspPage1__
     */
    public int getCht070DspPage1() {
        return cht070DspPage1__;
    }

    /**
     * <p>cht070DspPage1 をセットします。
     * @param cht070DspPage1 cht070DspPage1
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspPage1__
     */
    public void setCht070DspPage1(int cht070DspPage1) {
        cht070DspPage1__ = cht070DspPage1;
    }

    /**
     * <p>cht070DspPage2 を取得します。
     * @return cht070DspPage2
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspPage2__
     */
    public int getCht070DspPage2() {
        return cht070DspPage2__;
    }

    /**
     * <p>cht070DspPage2 をセットします。
     * @param cht070DspPage2 cht070DspPage2
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070DspPage2__
     */
    public void setCht070DspPage2(int cht070DspPage2) {
        cht070DspPage2__ = cht070DspPage2;
    }

    /**
     * <p>cht070PageLabel を取得します。
     * @return cht070PageLabel
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070PageLabel__
     */
    public List<LabelValueBean> getCht070PageLabel() {
        return cht070PageLabel__;
    }

    /**
     * <p>cht070PageLabel をセットします。
     * @param cht070PageLabel cht070PageLabel
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070PageLabel__
     */
    public void setCht070PageLabel(List<LabelValueBean> cht070PageLabel) {
        cht070PageLabel__ = cht070PageLabel;
    }

    /**
     * <p>cht070GraphItem を取得します。
     * @return cht070GraphItem
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070GraphItem__
     */
    public String getCht070GraphItem() {
        return cht070GraphItem__;
    }

    /**
     * <p>cht070GraphItem をセットします。
     * @param cht070GraphItem cht070GraphItem
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070GraphItem__
     */
    public void setCht070GraphItem(String cht070GraphItem) {
        cht070GraphItem__ = cht070GraphItem;
    }

    /**
     * <p>cht070LogCountList を取得します。
     * @return cht070LogCountList
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070LogCountList__
     */
    public List<Cht070DspModel> getCht070LogCountList() {
        return cht070LogCountList__;
    }

    /**
     * <p>cht070LogCountList をセットします。
     * @param cht070LogCountList cht070LogCountList
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070LogCountList__
     */
    public void setCht070LogCountList(List<Cht070DspModel> cht070LogCountList) {
        cht070LogCountList__ = cht070LogCountList;
    }

    /**
     * <p>jsonDateData を取得します。
     * @return jsonDateData
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#jsonDateData__
     */
    public String getJsonDateData() {
        return jsonDateData__;
    }

    /**
     * <p>jsonDateData をセットします。
     * @param jsonDateData jsonDateData
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#jsonDateData__
     */
    public void setJsonDateData(String jsonDateData) {
        jsonDateData__ = jsonDateData;
    }

    /**
     * <p>jsonUserData を取得します。
     * @return jsonUserData
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#jsonUserData__
     */
    public String getJsonUserData() {
        return jsonUserData__;
    }

    /**
     * <p>jsonUserData をセットします。
     * @param jsonUserData jsonUserData
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#jsonUserData__
     */
    public void setJsonUserData(String jsonUserData) {
        jsonUserData__ = jsonUserData;
    }

    /**
     * <p>jsonGroupData を取得します。
     * @return jsonGroupData
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#jsonGroupData__
     */
    public String getJsonGroupData() {
        return jsonGroupData__;
    }

    /**
     * <p>jsonGroupData をセットします。
     * @param jsonGroupData jsonGroupData
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#jsonGroupData__
     */
    public void setJsonGroupData(String jsonGroupData) {
        jsonGroupData__ = jsonGroupData;
    }

    /**
     * <p>jsonSumData を取得します。
     * @return jsonSumData
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#jsonSumData__
     */
    public String getJsonSumData() {
        return jsonSumData__;
    }

    /**
     * <p>jsonSumData をセットします。
     * @param jsonSumData jsonSumData
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#jsonSumData__
     */
    public void setJsonSumData(String jsonSumData) {
        jsonSumData__ = jsonSumData;
    }

    /**
     * <p>jsonSendData を取得します。
     * @return jsonSendData
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#jsonSendData__
     */
    public String getJsonSendData() {
        return jsonSendData__;
    }

    /**
     * <p>jsonSendData をセットします。
     * @param jsonSendData jsonSendData
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#jsonSendData__
     */
    public void setJsonSendData(String jsonSendData) {
        jsonSendData__ = jsonSendData;
    }

    /**
     * <p>cht070TotalMessageUser を取得します。
     * @return cht070TotalMessageUser
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070TotalMessageUser__
     */
    public String getCht070TotalMessageUser() {
        return cht070TotalMessageUser__;
    }

    /**
     * <p>cht070TotalMessageUser をセットします。
     * @param cht070TotalMessageUser cht070TotalMessageUser
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070TotalMessageUser__
     */
    public void setCht070TotalMessageUser(String cht070TotalMessageUser) {
        cht070TotalMessageUser__ = cht070TotalMessageUser;
    }

    /**
     * <p>cht070TotalMessageGroup を取得します。
     * @return cht070TotalMessageGroup
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070TotalMessageGroup__
     */
    public String getCht070TotalMessageGroup() {
        return cht070TotalMessageGroup__;
    }

    /**
     * <p>cht070TotalMessageGroup をセットします。
     * @param cht070TotalMessageGroup cht070TotalMessageGroup
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070TotalMessageGroup__
     */
    public void setCht070TotalMessageGroup(String cht070TotalMessageGroup) {
        cht070TotalMessageGroup__ = cht070TotalMessageGroup;
    }

    /**
     * <p>cht070TotalMessage を取得します。
     * @return cht070TotalMessage
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070TotalMessage__
     */
    public String getCht070TotalMessage() {
        return cht070TotalMessage__;
    }

    /**
     * <p>cht070TotalMessage をセットします。
     * @param cht070TotalMessage cht070TotalMessage
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070TotalMessage__
     */
    public void setCht070TotalMessage(String cht070TotalMessage) {
        cht070TotalMessage__ = cht070TotalMessage;
    }

    /**
     * <p>cht070TotalSend を取得します。
     * @return cht070TotalSend
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070TotalSend__
     */
    public String getCht070TotalSend() {
        return cht070TotalSend__;
    }

    /**
     * <p>cht070TotalSend をセットします。
     * @param cht070TotalSend cht070TotalSend
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070TotalSend__
     */
    public void setCht070TotalSend(String cht070TotalSend) {
        cht070TotalSend__ = cht070TotalSend;
    }

    /**
     * <p>cht070InitFlg を取得します。
     * @return cht070InitFlg
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070InitFlg__
     */
    public int getCht070InitFlg() {
        return cht070InitFlg__;
    }

    /**
     * <p>cht070InitFlg をセットします。
     * @param cht070InitFlg cht070InitFlg
     * @see jp.groupsession.v2.cht.cht070.Cht070ParamModel#cht070InitFlg__
     */
    public void setCht070InitFlg(int cht070InitFlg) {
        cht070InitFlg__ = cht070InitFlg;
    }

}