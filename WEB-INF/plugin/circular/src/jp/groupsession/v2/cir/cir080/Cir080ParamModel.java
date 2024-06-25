package jp.groupsession.v2.cir.cir080;

import java.util.ArrayList;

import jp.groupsession.v2.cir.cir070.Cir070ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 回覧板 個人設定 自動削除設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir080ParamModel extends Cir070ParamModel {
    //自動削除区分
    /** 受信タブ 処理区分 */
    private String cir080JdelKbn__ = null;
    /** 送信済タブ 処理区分 */
    private String cir080SdelKbn__ = null;
    /** ゴミ箱タブ 処理区分 */
    private String cir080DdelKbn__ = null;

    /** 年リスト */
    private ArrayList<LabelValueBean> cir080YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> cir080MonthLabelList__ = null;
    /** 受信タブ 年選択 */
    private String cir080JYear__ = null;
    /** 受信タブ 月選択 */
    private String cir080JMonth__ = null;
    /** 送信済タブ 年選択 */
    private String cir080SYear__ = null;
    /** 送信済タブ 月選択 */
    private String cir080SMonth__ = null;
    /** ゴミ箱タブ 年選択 */
    private String cir080DYear__ = null;
    /** ゴミ箱タブ 月選択 */
    private String cir080DMonth__ = null;

    /** バッチ処理時間 */
    private String cir080BatchTime__ = null;
    /**
     * <p>cir080BatchTime を取得します。
     * @return cir080BatchTime
     */
    public String getCir080BatchTime() {
        return cir080BatchTime__;
    }

    /**
     * <p>cir080BatchTime をセットします。
     * @param cir080BatchTime cir080BatchTime
     */
    public void setCir080BatchTime(String cir080BatchTime) {
        cir080BatchTime__ = cir080BatchTime;
    }

    /**
     * <p>cir080DdelKbn を取得します。
     * @return cir080DdelKbn
     */
    public String getCir080DdelKbn() {
        return cir080DdelKbn__;
    }

    /**
     * <p>cir080DdelKbn をセットします。
     * @param cir080DdelKbn cir080DdelKbn
     */
    public void setCir080DdelKbn(String cir080DdelKbn) {
        cir080DdelKbn__ = cir080DdelKbn;
    }

    /**
     * <p>cir080DMonth を取得します。
     * @return cir080DMonth
     */
    public String getCir080DMonth() {
        return cir080DMonth__;
    }

    /**
     * <p>cir080DMonth をセットします。
     * @param cir080DMonth cir080DMonth
     */
    public void setCir080DMonth(String cir080DMonth) {
        cir080DMonth__ = cir080DMonth;
    }

    /**
     * <p>cir080DYear を取得します。
     * @return cir080DYear
     */
    public String getCir080DYear() {
        return cir080DYear__;
    }

    /**
     * <p>cir080DYear をセットします。
     * @param cir080DYear cir080DYear
     */
    public void setCir080DYear(String cir080DYear) {
        cir080DYear__ = cir080DYear;
    }

    /**
     * <p>cir080JdelKbn を取得します。
     * @return cir080JdelKbn
     */
    public String getCir080JdelKbn() {
        return cir080JdelKbn__;
    }

    /**
     * <p>cir080JdelKbn をセットします。
     * @param cir080JdelKbn cir080JdelKbn
     */
    public void setCir080JdelKbn(String cir080JdelKbn) {
        cir080JdelKbn__ = cir080JdelKbn;
    }

    /**
     * <p>cir080JMonth を取得します。
     * @return cir080JMonth
     */
    public String getCir080JMonth() {
        return cir080JMonth__;
    }

    /**
     * <p>cir080JMonth をセットします。
     * @param cir080JMonth cir080JMonth
     */
    public void setCir080JMonth(String cir080JMonth) {
        cir080JMonth__ = cir080JMonth;
    }

    /**
     * <p>cir080JYear を取得します。
     * @return cir080JYear
     */
    public String getCir080JYear() {
        return cir080JYear__;
    }

    /**
     * <p>cir080JYear をセットします。
     * @param cir080JYear cir080JYear
     */
    public void setCir080JYear(String cir080JYear) {
        cir080JYear__ = cir080JYear;
    }

    /**
     * <p>cir080MonthLabelList を取得します。
     * @return cir080MonthLabelList
     */
    public ArrayList<LabelValueBean> getCir080MonthLabelList() {
        return cir080MonthLabelList__;
    }

    /**
     * <p>cir080MonthLabelList をセットします。
     * @param cir080MonthLabelList cir080MonthLabelList
     */
    public void setCir080MonthLabelList(
            ArrayList<LabelValueBean> cir080MonthLabelList) {
        cir080MonthLabelList__ = cir080MonthLabelList;
    }

    /**
     * <p>cir080SdelKbn を取得します。
     * @return cir080SdelKbn
     */
    public String getCir080SdelKbn() {
        return cir080SdelKbn__;
    }

    /**
     * <p>cir080SdelKbn をセットします。
     * @param cir080SdelKbn cir080SdelKbn
     */
    public void setCir080SdelKbn(String cir080SdelKbn) {
        cir080SdelKbn__ = cir080SdelKbn;
    }

    /**
     * <p>cir080SMonth を取得します。
     * @return cir080SMonth
     */
    public String getCir080SMonth() {
        return cir080SMonth__;
    }

    /**
     * <p>cir080SMonth をセットします。
     * @param cir080SMonth cir080SMonth
     */
    public void setCir080SMonth(String cir080SMonth) {
        cir080SMonth__ = cir080SMonth;
    }

    /**
     * <p>cir080SYear を取得します。
     * @return cir080SYear
     */
    public String getCir080SYear() {
        return cir080SYear__;
    }

    /**
     * <p>cir080SYear をセットします。
     * @param cir080SYear cir080SYear
     */
    public void setCir080SYear(String cir080SYear) {
        cir080SYear__ = cir080SYear;
    }

    /**
     * <p>cir080YearLabelList を取得します。
     * @return cir080YearLabelList
     */
    public ArrayList<LabelValueBean> getCir080YearLabelList() {
        return cir080YearLabelList__;
    }

    /**
     * <p>cir080YearLabelList をセットします。
     * @param cir080YearLabelList cir080YearLabelList
     */
    public void setCir080YearLabelList(ArrayList<LabelValueBean> cir080YearLabelList) {
        cir080YearLabelList__ = cir080YearLabelList;
    }
}