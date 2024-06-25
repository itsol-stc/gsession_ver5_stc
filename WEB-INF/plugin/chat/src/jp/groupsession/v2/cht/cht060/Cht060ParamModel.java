package jp.groupsession.v2.cht.cht060;

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
public class Cht060ParamModel extends Cht020ParamModel {

    /** 手動削除 年 */
    private int cht060ReferenceYear__ = GSConstChat.DEL_YEAR_DATE[3];
    /** 手動削除 月 */
    private int cht060ReferenceMonth__ = GSConstChat.DEL_MONTH_DATE[0];
    /** 年コンボ */
    private List<LabelValueBean> cht060YearLabelList__ = null;
    /** 月コンボ */
    private List<LabelValueBean> cht060MonthLabelList__ = null;

    /**
     * <p>cht060ReferenceYear を取得します。
     * @return cht060ReferenceYear
     * @see jp.groupsession.v2.cht.cht060.Cht060ParamModel#cht060ReferenceYear__
     */
    public int getCht060ReferenceYear() {
        return cht060ReferenceYear__;
    }
    /**
     * <p>cht060ReferenceYear をセットします。
     * @param cht060ReferenceYear cht060ReferenceYear
     * @see jp.groupsession.v2.cht.cht060.Cht060ParamModel#cht060ReferenceYear__
     */
    public void setCht060ReferenceYear(int cht060ReferenceYear) {
        cht060ReferenceYear__ = cht060ReferenceYear;
    }
    /**
     * <p>cht060ReferenceMonth を取得します。
     * @return cht060ReferenceMonth
     * @see jp.groupsession.v2.cht.cht060.Cht060ParamModel#cht060ReferenceMonth__
     */
    public int getCht060ReferenceMonth() {
        return cht060ReferenceMonth__;
    }
    /**
     * <p>cht060ReferenceMonth をセットします。
     * @param cht060ReferenceMonth cht060ReferenceMonth
     * @see jp.groupsession.v2.cht.cht060.Cht060ParamModel#cht060ReferenceMonth__
     */
    public void setCht060ReferenceMonth(int cht060ReferenceMonth) {
        cht060ReferenceMonth__ = cht060ReferenceMonth;
    }
    /**
     * <p>cht060YearLabelList を取得します。
     * @return cht060YearLabelList
     * @see jp.groupsession.v2.cht.cht060.Cht060ParamModel#cht060YearLabelList__
     */
    public List<LabelValueBean> getCht060YearLabelList() {
        return cht060YearLabelList__;
    }
    /**
     * <p>cht060YearLabelList をセットします。
     * @param cht060YearLabelList cht060YearLabelList
     * @see jp.groupsession.v2.cht.cht060.Cht060ParamModel#cht060YearLabelList__
     */
    public void setCht060YearLabelList(List<LabelValueBean> cht060YearLabelList) {
        cht060YearLabelList__ = cht060YearLabelList;
    }
    /**
     * <p>cht060MonthLabelList を取得します。
     * @return cht060MonthLabelList
     * @see jp.groupsession.v2.cht.cht060.Cht060ParamModel#cht060MonthLabelList__
     */
    public List<LabelValueBean> getCht060MonthLabelList() {
        return cht060MonthLabelList__;
    }
    /**
     * <p>cht060MonthLabelList をセットします。
     * @param cht060MonthLabelList cht060MonthLabelList
     * @see jp.groupsession.v2.cht.cht060.Cht060ParamModel#cht060MonthLabelList__
     */
    public void setCht060MonthLabelList(List<LabelValueBean> cht060MonthLabelList) {
        cht060MonthLabelList__ = cht060MonthLabelList;
    }



}
