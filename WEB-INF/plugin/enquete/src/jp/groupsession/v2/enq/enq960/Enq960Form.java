package jp.groupsession.v2.enq.enq960;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.enq900.Enq900Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 アンケート自動削除画面のアクションフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq960Form extends Enq900Form {

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
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        boolean bYearError = false;
        boolean bMonthError = false;
            //発信
            if (NullDefault.getInt(enq960SendDelKbn__, 3)  == GSConst.AUTO_DEL_LIMIT) {
                int targetYear = NullDefault.getInt(enq960SelectSendYear__, 11);
                int targetMonth = NullDefault.getInt(enq960SelectSendMonth__, 12);
                for (int y: GSConst.DEL_YEAR_DATE) {
                    if (y == targetYear) {
                        bYearError = true;
                        break;
                    }
                }
                for (int m: GSConst.DEL_MONTH_DATE) {
                    if (m == targetMonth) {
                        bMonthError = true;
                        break;
                    }
                }
                if (!bYearError || !bMonthError) {
                    ActionMessage msg =  new ActionMessage("error.autodel.between",
                            gsMsg.getMessage("enq.plugin"),
                            gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("enq.13"));
                    String eprefix = "enqautoDelHassin";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                } else if (targetYear == 0 && targetMonth == 0) {
                    ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("enq.plugin"),
                            gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("enq.13"),
                            gsMsg.getMessage("cht.cht050.02"));
                    String eprefix = "enqautoDelHassin";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            } else if (NullDefault.getInt(enq960SendDelKbn__, 3)  == GSConst.AUTO_DEL_NO) {
            } else {
                ActionMessage msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("enq.plugin"),
                        gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("enq.13"));
                String eprefix = "enqautoDelHassin";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            
            //草稿
            if (NullDefault.getInt(enq960DraftDelKbn__, 3)  == GSConst.AUTO_DEL_LIMIT) {
                int targetYear = NullDefault.getInt(enq960SelectDraftYear__, 11);
                int targetMonth = NullDefault.getInt(enq960SelectDraftMonth__, 12);
                bYearError = false;
                bMonthError = false;
                for (int y: GSConst.DEL_YEAR_DATE) {
                    if (y == targetYear) {
                        bYearError = true;
                        break;
                    }
                }
                for (int m: GSConst.DEL_MONTH_DATE) {
                    if (m == targetMonth) {
                        bMonthError = true;
                        break;
                    }
                }
                if (!bYearError || !bMonthError) {
                    ActionMessage msg =  new ActionMessage("error.autodel.between",
                            gsMsg.getMessage("enq.plugin"),
                            gsMsg.getMessage("cmn.autodelete") + " " 
                            + gsMsg.getMessage("cmn.draft"));
                    String eprefix = "enqautoDelDraft";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                } else if (targetYear == 0 && targetMonth == 0) {
                    ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("enq.plugin"),
                            gsMsg.getMessage("cmn.autodelete") + " " 
                          + gsMsg.getMessage("cmn.draft"),
                            gsMsg.getMessage("cht.cht050.02"));
                    String eprefix = "enqautoDelDraft";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            } else if (NullDefault.getInt(enq960DraftDelKbn__, 3)  == GSConst.AUTO_DEL_NO) {
            } else {
                ActionMessage msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("enq.plugin"),
                        gsMsg.getMessage("cmn.autodelete") + " "
                        + gsMsg.getMessage("cmn.draft"));
                String eprefix = "enqautoDelDraft";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        return errors;
    }
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

    /**
     * <br>[機  能] 手動削除時のオペレーションログ内容を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return [発信フォルダ]削除しない or YY年MMヶ月
     *         [草稿フォルダ]表示する or 表示しない
     */
    public String getLogText(RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String ret = null;

        // 発信フォルダ
        ret = "[" + gsMsg.getMessage("enq.enq950.05") + "]";
        if (enq960SendDelKbn__.equals(String.valueOf(GSConstEnquete.DELETE_KBN_OFF))) {
            ret += gsMsg.getMessage("cmn.dont.delete") + System.getProperty("line.separator");
        } else {
            ret += gsMsg.getMessageVal0("cmn.year", enq960SelectSendYear__)
                 + gsMsg.getMessageVal0("cmn.months", enq960SelectSendMonth__)
                 + System.getProperty("line.separator");
        }
        // 草稿フォルダ
        ret += "[" + gsMsg.getMessage("enq.enq950.06") + "]";
        if (enq960DraftDelKbn__.equals(String.valueOf(GSConstEnquete.DELETE_KBN_OFF))) {
            ret += gsMsg.getMessage("cmn.dont.delete") + System.getProperty("line.separator");
        } else {
            ret += gsMsg.getMessageVal0("cmn.year", enq960SelectDraftYear__)
                 + gsMsg.getMessageVal0("cmn.months", enq960SelectDraftMonth__);
        }

       return ret;
    }
}
