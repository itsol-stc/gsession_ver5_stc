package jp.groupsession.v2.enq.enq960;

import java.util.ArrayList;

import jp.groupsession.v2.enq.enq900.Enq900ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 管理者設定 アンケート自動削除画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq960ParamModel extends Enq900ParamModel {

    /** 初期表示フラグ */
    private int enq960initFlg__ = 0;
    /** 発信 削除区分 */
    private String enq960SendDelKbn__ = null;
    /** 草稿 削除区分 */
    private String enq960DraftDelKbn__ = null;
    /** 年リスト */
    private ArrayList<LabelValueBean> enq960YearLabel__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> enq960MonthLabel__ = null;
    /** 発信 選択年 */
    private String enq960SelectSendYear__ = null;
    /** 発信 選択月 */
    private String enq960SelectSendMonth__ = null;
    /** 草稿 選択年 */
    private String enq960SelectDraftYear__ = null;
    /** 草稿 選択月 */
    private String enq960SelectDraftMonth__ = null;
    /**
     * <p>enq960initFlg を取得します。
     * @return enq960initFlg
     */
    public int getEnq960initFlg() {
        return enq960initFlg__;
    }
    /**
     * <p>enq960initFlg をセットします。
     * @param enq960initFlg enq960initFlg
     */
    public void setEnq960initFlg(int enq960initFlg) {
        enq960initFlg__ = enq960initFlg;
    }
    /**
     * <p>enq960SendDelKbn を取得します。
     * @return enq960SendDelKbn
     */
    public String getEnq960SendDelKbn() {
        return enq960SendDelKbn__;
    }
    /**
     * <p>enq960SendDelKbn をセットします。
     * @param enq960SendDelKbn enq960SendDelKbn
     */
    public void setEnq960SendDelKbn(String enq960SendDelKbn) {
        enq960SendDelKbn__ = enq960SendDelKbn;
    }
    /**
     * <p>enq960DraftDelKbn を取得します。
     * @return enq960DraftDelKbn
     */
    public String getEnq960DraftDelKbn() {
        return enq960DraftDelKbn__;
    }
    /**
     * <p>enq960DraftDelKbn をセットします。
     * @param enq960DraftDelKbn enq960DraftDelKbn
     */
    public void setEnq960DraftDelKbn(String enq960DraftDelKbn) {
        enq960DraftDelKbn__ = enq960DraftDelKbn;
    }
    /**
     * <p>enq960YearLabel を取得します。
     * @return enq960YearLabel
     */
    public ArrayList<LabelValueBean> getEnq960YearLabel() {
        return enq960YearLabel__;
    }
    /**
     * <p>enq960YearLabel をセットします。
     * @param enq960YearLabel enq960YearLabel
     */
    public void setEnq960YearLabel(ArrayList<LabelValueBean> enq960YearLabel) {
        enq960YearLabel__ = enq960YearLabel;
    }
    /**
     * <p>enq960MonthLabel を取得します。
     * @return enq960MonthLabel
     */
    public ArrayList<LabelValueBean> getEnq960MonthLabel() {
        return enq960MonthLabel__;
    }
    /**
     * <p>enq960MonthLabel をセットします。
     * @param enq960MonthLabel enq960MonthLabel
     */
    public void setEnq960MonthLabel(ArrayList<LabelValueBean> enq960MonthLabel) {
        enq960MonthLabel__ = enq960MonthLabel;
    }
    /**
     * <p>enq960SelectSendYear を取得します。
     * @return enq960SelectSendYear
     */
    public String getEnq960SelectSendYear() {
        return enq960SelectSendYear__;
    }
    /**
     * <p>enq960SelectSendYear をセットします。
     * @param enq960SelectSendYear enq960SelectSendYear
     */
    public void setEnq960SelectSendYear(String enq960SelectSendYear) {
        enq960SelectSendYear__ = enq960SelectSendYear;
    }
    /**
     * <p>enq960SelectSendMonth を取得します。
     * @return enq960SelectSendMonth
     */
    public String getEnq960SelectSendMonth() {
        return enq960SelectSendMonth__;
    }
    /**
     * <p>enq960SelectSendMonth をセットします。
     * @param enq960SelectSendMonth enq960SelectSendMonth
     */
    public void setEnq960SelectSendMonth(String enq960SelectSendMonth) {
        enq960SelectSendMonth__ = enq960SelectSendMonth;
    }
    /**
     * <p>enq960SelectDraftYear を取得します。
     * @return enq960SelectDraftYear
     */
    public String getEnq960SelectDraftYear() {
        return enq960SelectDraftYear__;
    }
    /**
     * <p>enq960SelectDraftYear をセットします。
     * @param enq960SelectDraftYear enq960SelectDraftYear
     */
    public void setEnq960SelectDraftYear(String enq960SelectDraftYear) {
        enq960SelectDraftYear__ = enq960SelectDraftYear;
    }
    /**
     * <p>enq960SelectDraftMonth を取得します。
     * @return enq960SelectDraftMonth
     */
    public String getEnq960SelectDraftMonth() {
        return enq960SelectDraftMonth__;
    }
    /**
     * <p>enq960SelectDraftMonth をセットします。
     * @param enq960SelectDraftMonth enq960SelectDraftMonth
     */
    public void setEnq960SelectDraftMonth(String enq960SelectDraftMonth) {
        enq960SelectDraftMonth__ = enq960SelectDraftMonth;
    }

}
