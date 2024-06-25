package jp.groupsession.v2.enq.enq950;

import java.util.ArrayList;

import jp.groupsession.v2.enq.enq900.Enq900ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 管理者設定 アンケート手動削除画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq950ParamModel extends Enq900ParamModel {

    /** 発信 削除区分 */
    private String enq950SendDelKbn__ = null;
    /** 草稿 削除区分 */
    private String enq950DraftDelKbn__ = null;
    /** 年リスト */
    private ArrayList<LabelValueBean> enq950YearLabel__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> enq950MonthLabel__ = null;
    /** 発信 選択年 */
    private String enq950SelectSendYear__ = null;
    /** 発信 選択月 */
    private String enq950SelectSendMonth__ = null;
    /** 草稿 選択年 */
    private String enq950SelectDraftYear__ = null;
    /** 草稿 選択月 */
    private String enq950SelectDraftMonth__ = null;

    /** 削除したSIDリスト */
    private ArrayList<String> enq950DelList__ = null;

    /**
     * <p>発信 削除区分 を取得します。
     * @return 発信 削除区分
     */
    public String getEnq950SendDelKbn() {
        return enq950SendDelKbn__;
    }

    /**
     * <p>発信 削除区分 をセットします。
     * @param enq950SendDelKbn 発信 削除区分
     */
    public void setEnq950SendDelKbn(String enq950SendDelKbn) {
        enq950SendDelKbn__ = enq950SendDelKbn;
    }

    /**
     * <p>草稿 削除区分 を取得します。
     * @return 草稿 削除区分
     */
    public String getEnq950DraftDelKbn() {
        return enq950DraftDelKbn__;
    }

    /**
     * <p>草稿 削除区分 をセットします。
     * @param enq950DraftDelKbn 草稿 削除区分
     */
    public void setEnq950DraftDelKbn(String enq950DraftDelKbn) {
        enq950DraftDelKbn__ = enq950DraftDelKbn;
    }

    /**
     * <p>年リスト を取得します。
     * @return 年リスト
     */
    public ArrayList<LabelValueBean> getEnq950YearLabel() {
        return enq950YearLabel__;
    }

    /**
     * <p>年リスト をセットします。
     * @param enq950YearLabel 年リスト
     */
    public void setEnq950YearLabel(ArrayList<LabelValueBean> enq950YearLabel) {
        enq950YearLabel__ = enq950YearLabel;
    }

    /**
     * <p>月リスト を取得します。
     * @return 月リスト
     */
    public ArrayList<LabelValueBean> getEnq950MonthLabel() {
        return enq950MonthLabel__;
    }

    /**
     * <p>月リスト をセットします。
     * @param enq950MonthLabel 月リスト
     */
    public void setEnq950MonthLabel(ArrayList<LabelValueBean> enq950MonthLabel) {
        enq950MonthLabel__ = enq950MonthLabel;
    }

    /**
     * <p>発信 選択年 を取得します。
     * @return 発信 選択年
     */
    public String getEnq950SelectSendYear() {
        return enq950SelectSendYear__;
    }

    /**
     * <p>発信 選択年 をセットします。
     * @param enq950SelectSendYear 発信 選択年
     */
    public void setEnq950SelectSendYear(String enq950SelectSendYear) {
        enq950SelectSendYear__ = enq950SelectSendYear;
    }

    /**
     * <p>発信 選択月 を取得します。
     * @return 発信 選択月
     */
    public String getEnq950SelectSendMonth() {
        return enq950SelectSendMonth__;
    }

    /**
     * <p>発信 選択月 をセットします。
     * @param enq950SelectSendMonth 発信 選択月
     */
    public void setEnq950SelectSendMonth(String enq950SelectSendMonth) {
        enq950SelectSendMonth__ = enq950SelectSendMonth;
    }

    /**
     * <p>草稿 選択年 を取得します。
     * @return 草稿 選択年
     */
    public String getEnq950SelectDraftYear() {
        return enq950SelectDraftYear__;
    }

    /**
     * <p>草稿 選択年 をセットします。
     * @param enq950SelectDraftYear 草稿 選択年
     */
    public void setEnq950SelectDraftYear(String enq950SelectDraftYear) {
        enq950SelectDraftYear__ = enq950SelectDraftYear;
    }

    /**
     * <p>草稿 選択月 を取得します。
     * @return 草稿 選択月
     */
    public String getEnq950SelectDraftMonth() {
        return enq950SelectDraftMonth__;
    }

    /**
     * <p>草稿 選択月 をセットします。
     * @param enq950SelectDraftMonth 草稿 選択月
     */
    public void setEnq950SelectDraftMonth(String enq950SelectDraftMonth) {
        enq950SelectDraftMonth__ = enq950SelectDraftMonth;
    }

    /**
     * <br>[機  能] アンケート種類削除リストの配列を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @return アンケート種類削除[]
     */
    public String[] getEnq950DelList() {
        int size = 0;
        if (enq950DelList__ != null) {
            size = enq950DelList__.size();
        }
        return (String[]) enq950DelList__.toArray(new String[size]);
    }

}
