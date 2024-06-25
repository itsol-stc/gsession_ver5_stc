package jp.groupsession.v2.cir.cir110;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir100.Cir100Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 管理者設定 自動削除設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir110Form extends Cir100Form {

    /** 自動削除区分 */
    private String cir110DelKbn__ = String.valueOf(GSConstCircular.CIR_ADEL_USR_KBN_ADM);
    /** 受信回覧板 処理区分 */
    private String cir110JdelKbn__ = null;
    /** 送信回覧板 処理区分 */
    private String cir110SdelKbn__ = null;
    /** ゴミ箱回覧板 処理区分 */
    private String cir110DdelKbn__ = null;
    /** 年リスト */
    private ArrayList<LabelValueBean> cir110YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> cir110MonthLabelList__ = null;
    /** 受信 年選択 */
    private String cir110JYear__ = null;
    /** 受信 月選択 */
    private String cir110JMonth__ = null;
    /** 送信済 年選択 */
    private String cir110SYear__ = null;
    /** 送信済 月選択 */
    private String cir110SMonth__ = null;
    /** ゴミ箱 年選択 */
    private String cir110DYear__ = null;
    /** ゴミ箱 月選択 */
    private String cir110DMonth__ = null;
    /** バッチ処理時間 */
    private String cir110BatchTime__ = null;
    
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
        if (NullDefault.getInt(cir110DelKbn__, 0) == GSConstCircular.CIR_ADEL_USR_KBN_ADM) {
            //受信
            if (NullDefault.getInt(cir110JdelKbn__, 3) == GSConst.AUTO_DEL_LIMIT) {
                int targetYear = NullDefault.getInt(cir110JYear__, 11);
                int targetMonth = NullDefault.getInt(cir110JMonth__, 12);
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
                            gsMsg.getMessage("cir.5"),
                            gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("cir.25"));
                    String eprefix = "cirautoDelRecive";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                } else if (targetYear == 0 && targetMonth == 0) {
                    ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("cir.5"),
                            gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("cir.25"),
                            gsMsg.getMessage("cht.cht050.02"));
                    String eprefix = "cirautoDelRecive";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            } else if (NullDefault.getInt(cir110JdelKbn__, 3) == GSConst.AUTO_DEL_NO) {
            } else {
                ActionMessage msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("cir.5"),
                        gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("cir.25"));
                String eprefix = "cirautoDelRecive";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            
            //送信
            if (NullDefault.getInt(cir110SdelKbn__, 3) == GSConst.AUTO_DEL_LIMIT) {
                int targetYear = NullDefault.getInt(cir110SYear__, 11);
                int targetMonth = NullDefault.getInt(cir110SMonth__, 12);
                bYearError = false;
                bMonthError = false;
                for (int y: GSConst.DEL_YEAR_DATE) {
                    if (y == targetYear) {
                        bYearError = true;
                        break;
                    }
                }
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
                            gsMsg.getMessage("cir.5"),
                            gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("cir.26"));
                    String eprefix = "cirautoDelSend";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                } else if (targetYear == 0 && targetMonth == 0) {
                    ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("cir.5"),
                            gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("cir.26"),
                            gsMsg.getMessage("cht.cht050.02"));
                    String eprefix = "cirautoDelSend";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            } else if (NullDefault.getInt(cir110SdelKbn__, 3) == GSConst.AUTO_DEL_NO) {
            } else {
                ActionMessage msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("cir.5"),
                        gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("cir.26"));
                String eprefix = "cirautoDelSend";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            
            //ゴミ箱
            if (NullDefault.getInt(cir110DdelKbn__, 3) == GSConst.AUTO_DEL_LIMIT) {
                int targetYear = NullDefault.getInt(cir110DYear__, 11);
                int targetMonth = NullDefault.getInt(cir110DMonth__, 12);
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
                            gsMsg.getMessage("cir.5"),
                            gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("cir.27"));
                    String eprefix = "cirautoDelTrash";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                } else if (targetYear == 0 && targetMonth == 0) {
                    ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("cir.5"),
                            gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("cir.27"),
                            gsMsg.getMessage("cht.cht050.02"));
                    String eprefix = "cirautoDelTrash";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            } else if (NullDefault.getInt(cir110DdelKbn__, 3) == GSConst.AUTO_DEL_NO) {
            } else {
                ActionMessage msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("cir.5"),
                        gsMsg.getMessage("cmn.autodelete") + " " + gsMsg.getMessage("cir.27"));
                String eprefix = "cirautoDelTrash";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } 

        return errors;
    }

    /**
     * <p>cir110BatchTime を取得します。
     * @return cir110BatchTime
     */
    public String getCir110BatchTime() {
        return cir110BatchTime__;
    }

    /**
     * <p>cir110BatchTime をセットします。
     * @param cir110BatchTime cir110BatchTime
     */
    public void setCir110BatchTime(String cir110BatchTime) {
        cir110BatchTime__ = cir110BatchTime;
    }

    /**
     * <p>cir110DdelKbn を取得します。
     * @return cir110DdelKbn
     */
    public String getCir110DdelKbn() {
        return cir110DdelKbn__;
    }

    /**
     * <p>cir110DdelKbn をセットします。
     * @param cir110DdelKbn cir110DdelKbn
     */
    public void setCir110DdelKbn(String cir110DdelKbn) {
        cir110DdelKbn__ = cir110DdelKbn;
    }

    /**
     * <p>cir110DelKbn を取得します。
     * @return cir110DelKbn
     */
    public String getCir110DelKbn() {
        return cir110DelKbn__;
    }

    /**
     * <p>cir110DelKbn をセットします。
     * @param cir110DelKbn cir110DelKbn
     */
    public void setCir110DelKbn(String cir110DelKbn) {
        cir110DelKbn__ = cir110DelKbn;
    }

    /**
     * <p>cir110DMonth を取得します。
     * @return cir110DMonth
     */
    public String getCir110DMonth() {
        return cir110DMonth__;
    }

    /**
     * <p>cir110DMonth をセットします。
     * @param cir110DMonth cir110DMonth
     */
    public void setCir110DMonth(String cir110DMonth) {
        cir110DMonth__ = cir110DMonth;
    }

    /**
     * <p>cir110DYear を取得します。
     * @return cir110DYear
     */
    public String getCir110DYear() {
        return cir110DYear__;
    }

    /**
     * <p>cir110DYear をセットします。
     * @param cir110DYear cir110DYear
     */
    public void setCir110DYear(String cir110DYear) {
        cir110DYear__ = cir110DYear;
    }

    /**
     * <p>cir110JdelKbn を取得します。
     * @return cir110JdelKbn
     */
    public String getCir110JdelKbn() {
        return cir110JdelKbn__;
    }

    /**
     * <p>cir110JdelKbn をセットします。
     * @param cir110JdelKbn cir110JdelKbn
     */
    public void setCir110JdelKbn(String cir110JdelKbn) {
        cir110JdelKbn__ = cir110JdelKbn;
    }

    /**
     * <p>cir110JMonth を取得します。
     * @return cir110JMonth
     */
    public String getCir110JMonth() {
        return cir110JMonth__;
    }

    /**
     * <p>cir110JMonth をセットします。
     * @param cir110JMonth cir110JMonth
     */
    public void setCir110JMonth(String cir110JMonth) {
        cir110JMonth__ = cir110JMonth;
    }

    /**
     * <p>cir110JYear を取得します。
     * @return cir110JYear
     */
    public String getCir110JYear() {
        return cir110JYear__;
    }

    /**
     * <p>cir110JYear をセットします。
     * @param cir110JYear cir110JYear
     */
    public void setCir110JYear(String cir110JYear) {
        cir110JYear__ = cir110JYear;
    }

    /**
     * <p>cir110MonthLabelList を取得します。
     * @return cir110MonthLabelList
     */
    public ArrayList<LabelValueBean> getCir110MonthLabelList() {
        return cir110MonthLabelList__;
    }

    /**
     * <p>cir110MonthLabelList をセットします。
     * @param cir110MonthLabelList cir110MonthLabelList
     */
    public void setCir110MonthLabelList(
            ArrayList<LabelValueBean> cir110MonthLabelList) {
        cir110MonthLabelList__ = cir110MonthLabelList;
    }

    /**
     * <p>cir110SdelKbn を取得します。
     * @return cir110SdelKbn
     */
    public String getCir110SdelKbn() {
        return cir110SdelKbn__;
    }

    /**
     * <p>cir110SdelKbn をセットします。
     * @param cir110SdelKbn cir110SdelKbn
     */
    public void setCir110SdelKbn(String cir110SdelKbn) {
        cir110SdelKbn__ = cir110SdelKbn;
    }

    /**
     * <p>cir110SMonth を取得します。
     * @return cir110SMonth
     */
    public String getCir110SMonth() {
        return cir110SMonth__;
    }

    /**
     * <p>cir110SMonth をセットします。
     * @param cir110SMonth cir110SMonth
     */
    public void setCir110SMonth(String cir110SMonth) {
        cir110SMonth__ = cir110SMonth;
    }

    /**
     * <p>cir110SYear を取得します。
     * @return cir110SYear
     */
    public String getCir110SYear() {
        return cir110SYear__;
    }

    /**
     * <p>cir110SYear をセットします。
     * @param cir110SYear cir110SYear
     */
    public void setCir110SYear(String cir110SYear) {
        cir110SYear__ = cir110SYear;
    }

    /**
     * <p>cir110YearLabelList を取得します。
     * @return cir110YearLabelList
     */
    public ArrayList<LabelValueBean> getCir110YearLabelList() {
        return cir110YearLabelList__;
    }

    /**
     * <p>cir110YearLabelList をセットします。
     * @param cir110YearLabelList cir110YearLabelList
     */
    public void setCir110YearLabelList(ArrayList<LabelValueBean> cir110YearLabelList) {
        cir110YearLabelList__ = cir110YearLabelList;
    }
}