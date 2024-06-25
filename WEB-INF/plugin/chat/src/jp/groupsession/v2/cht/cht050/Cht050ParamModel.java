package jp.groupsession.v2.cht.cht050;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht020.Cht020ParamModel;

/**
 *
 * <br>[機  能] 手動データ削除のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht050ParamModel extends Cht020ParamModel {

    /** 自動削除実行設定*/
    private int cht050DoAutoDel__ = 0;

    /**　バッジ処理時間*/
    private String cht050BatchTime__ = "";


    /** 自動削除 年 */
    private int cht050ReferenceYear__ = GSConstChat.DEL_YEAR_DATE[0];
    /** 自動削除 月 */
    private int cht050ReferenceMonth__ = GSConstChat.DEL_MONTH_DATE[0];
    /** 年コンボ */
    private List<LabelValueBean> cht050YearLabelList__ = null;
    /** 月コンボ */
    private List<LabelValueBean> cht050MonthLabelList__ = null;

    /** 初期表示フラグ*/
    private int cht050InitFlg__ = 0;

    /**
     * <p>cht050ReferenceYear を取得します。
     * @return cht050ReferenceYear
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050ReferenceYear__
     */
    public int getCht050ReferenceYear() {
        return cht050ReferenceYear__;
    }
    /**
     * <p>cht050ReferenceYear をセットします。
     * @param cht050ReferenceYear cht050ReferenceYear
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050ReferenceYear__
     */
    public void setCht050ReferenceYear(int cht050ReferenceYear) {
        cht050ReferenceYear__ = cht050ReferenceYear;
    }
    /**
     * <p>cht050ReferenceMonth を取得します。
     * @return cht050ReferenceMonth
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050ReferenceMonth__
     */
    public int getCht050ReferenceMonth() {
        return cht050ReferenceMonth__;
    }
    /**
     * <p>cht050ReferenceMonth をセットします。
     * @param cht050ReferenceMonth cht050ReferenceMonth
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050ReferenceMonth__
     */
    public void setCht050ReferenceMonth(int cht050ReferenceMonth) {
        cht050ReferenceMonth__ = cht050ReferenceMonth;
    }
    /**
     * <p>cht050YearLabelList を取得します。
     * @return cht050YearLabelList
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050YearLabelList__
     */
    public List<LabelValueBean> getCht050YearLabelList() {
        return cht050YearLabelList__;
    }
    /**
     * <p>cht050YearLabelList をセットします。
     * @param cht050YearLabelList cht050YearLabelList
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050YearLabelList__
     */
    public void setCht050YearLabelList(List<LabelValueBean> cht050YearLabelList) {
        cht050YearLabelList__ = cht050YearLabelList;
    }
    /**
     * <p>cht050MonthLabelList を取得します。
     * @return cht050MonthLabelList
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050MonthLabelList__
     */
    public List<LabelValueBean> getCht050MonthLabelList() {
        return cht050MonthLabelList__;
    }
    /**
     * <p>cht050MonthLabelList をセットします。
     * @param cht050MonthLabelList cht050MonthLabelList
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050MonthLabelList__
     */
    public void setCht050MonthLabelList(List<LabelValueBean> cht050MonthLabelList) {
        cht050MonthLabelList__ = cht050MonthLabelList;
    }
    /**
     * <p>cht050DoAutoDel を取得します。
     * @return cht050DoAutoDel
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050DoAutoDel__
     */
    public int getCht050DoAutoDel() {
        return cht050DoAutoDel__;
    }
    /**
     * <p>cht050DoAutoDel をセットします。
     * @param cht050DoAutoDel cht050DoAutoDel
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050DoAutoDel__
     */
    public void setCht050DoAutoDel(int cht050DoAutoDel) {
        cht050DoAutoDel__ = cht050DoAutoDel;
    }
    /**
     * <p>cht050BatchTime を取得します。
     * @return cht050BatchTime
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050BatchTime__
     */
    public String getCht050BatchTime() {
        return cht050BatchTime__;
    }
    /**
     * <p>cht050BatchTime をセットします。
     * @param cht050BatchTime cht050BatchTime
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050BatchTime__
     */
    public void setCht050BatchTime(String cht050BatchTime) {
        cht050BatchTime__ = cht050BatchTime;
    }
    /**
     * <p>cht050InitFlg を取得します。
     * @return cht050InitFlg
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050InitFlg__
     */
    public int getCht050InitFlg() {
        return cht050InitFlg__;
    }
    /**
     * <p>cht050InitFlg をセットします。
     * @param cht050InitFlg cht050InitFlg
     * @see jp.groupsession.v2.cht.cht050.Cht050ParamModel#cht050InitFlg__
     */
    public void setCht050InitFlg(int cht050InitFlg) {
        cht050InitFlg__ = cht050InitFlg;
    }



}
