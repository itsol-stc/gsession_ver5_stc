package jp.groupsession.v2.cir.cir090;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir070.Cir070Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 個人設定 手動削除画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir090Form extends Cir070Form {

    /** 設定アカウント区分 */
    private int cir090SelKbn__ = GSConstCircular.ACCOUNT_SEL;
    /** 手動削除設定のアカウントSID */
    private int cir090AccountSid__;
    /** メ手動削除設定のアカウント名 */
    private String cir090AccountName__;

    //手動削除区分
    /** 受信タブ 処理区分 */
    private String cir090JdelKbn__ = null;
    /** 送信タブ 処理区分 */
    private String cir090SdelKbn__ = null;
    /** ゴミ箱タブ 処理区分 */
    private String cir090DdelKbn__ = null;

    /** 年リスト */
    private ArrayList<LabelValueBean> cir090YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> cir090MonthLabelList__ = null;
    /** 受信タブ 年選択 */
    private String cir090JYear__ = null;
    /** 受信タブ 月選択 */
    private String cir090JMonth__ = null;
    /** 送信タブ 年選択 */
    private String cir090SYear__ = null;
    /** 送信タブ 月選択 */
    private String cir090SMonth__ = null;
    /** ゴミ箱タブ 年選択 */
    private String cir090DYear__ = null;
    /** ゴミ箱タブ 月選択 */
    private String cir090DMonth__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validatecir090(
            Connection con, HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(req);

        boolean bYearError = false;
        boolean bMonthError = false;
        //受信
        if (NullDefault.getInt(cir090JdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(cir090JYear__, 13);
            int targetMonth = NullDefault.getInt(cir090JMonth__, 12);
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
            if (!bYearError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cir.5"),
                        gsMsg.getMessage("cmn.manual.delete2")
                        + " " + gsMsg.getMessage("cmn.receive"),
                        gsMsg.getMessage("cmn.passage.year"));
                String eprefix = "cirJYear";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (!bMonthError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cir.5"),
                        gsMsg.getMessage("cmn.manual.delete2")
                        + " " + gsMsg.getMessage("cmn.receive"),
                        gsMsg.getMessage("cmn.passage.month"));
                String eprefix = "cirJMonth";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (targetYear == 0 && targetMonth == 0) {
                ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("cir.5"),
                        gsMsg.getMessage("cmn.manual.delete2")
                        + " " + gsMsg.getMessage("cmn.receive"),
                        gsMsg.getMessage("cht.cht050.02"));
                String eprefix = "cirJLowLimit";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } else if (NullDefault.getInt(cir090JdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cir.5"),
                    gsMsg.getMessage("cmn.manual.delete2")
                    + " " + gsMsg.getMessage("cmn.receive"),
                    gsMsg.getMessage("cmn.cmn310.05"));
            String eprefix = "cirJDelKbn";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        //送信
        if (NullDefault.getInt(cir090SdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(cir090SYear__, 13);
            int targetMonth = NullDefault.getInt(cir090SMonth__, 12);
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
            if (!bYearError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cir.5"),
                        gsMsg.getMessage("cmn.manual.delete2")
                        + " " + gsMsg.getMessage("cmn.sent"),
                        gsMsg.getMessage("cmn.passage.year"));
                String eprefix = "cirSYear";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (!bMonthError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cir.5"),
                        gsMsg.getMessage("cmn.manual.delete2")
                        + " " + gsMsg.getMessage("cmn.sent"),
                        gsMsg.getMessage("cmn.passage.month"));
                String eprefix = "cirSMonth";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (targetYear == 0 && targetMonth == 0) {
                ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("cir.5"),
                        gsMsg.getMessage("cmn.manual.delete2")
                        + " " + gsMsg.getMessage("cmn.sent"),
                        gsMsg.getMessage("cht.cht050.02"));
                String eprefix = "cirSLowLimit";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } else if (NullDefault.getInt(cir090SdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cir.5"),
                    gsMsg.getMessage("cmn.manual.delete2")
                    + " " + gsMsg.getMessage("cmn.sent"),
                    gsMsg.getMessage("cmn.cmn310.05"));
            String eprefix = "cirSDelKbn";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        //ゴミ箱
        if (NullDefault.getInt(cir090DdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(cir090DYear__, 13);
            int targetMonth = NullDefault.getInt(cir090DMonth__, 12);
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
            if (!bYearError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cir.5"),
                        gsMsg.getMessage("cmn.manual.delete2")
                        + " " + gsMsg.getMessage("cmn.trash"),
                        gsMsg.getMessage("cmn.passage.year"));
                String eprefix = "cirDYear";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (!bMonthError) {
                ActionMessage msg =  new ActionMessage("error.manualdel.between",
                        gsMsg.getMessage("cir.5"),
                        gsMsg.getMessage("cmn.manual.delete2")
                        + " " + gsMsg.getMessage("cmn.trash"),
                        gsMsg.getMessage("cmn.passage.month"));
                String eprefix = "cirDMonth";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            if (targetYear == 0 && targetMonth == 0) {
                ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("cir.5"),
                        gsMsg.getMessage("cmn.manual.delete2")
                        + " " + gsMsg.getMessage("cmn.trash"),
                        gsMsg.getMessage("cht.cht050.02"));
                String eprefix = "cirDLowLimit";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } else if (NullDefault.getInt(cir090DdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cmn.shortmail"),
                    gsMsg.getMessage("cmn.manual.delete2")
                    + " " + gsMsg.getMessage("cmn.trash"),
                    gsMsg.getMessage("cmn.cmn310.05"));
            String eprefix = "cirDDelKbn";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        return errors;
    }

    /**
     * <p>cir090DdelKbn を取得します。
     * @return cir090DdelKbn
     */
    public String getCir090DdelKbn() {
        return cir090DdelKbn__;
    }
    /**
     * <p>cir090DdelKbn をセットします。
     * @param cir090DdelKbn cir090DdelKbn
     */
    public void setCir090DdelKbn(String cir090DdelKbn) {
        cir090DdelKbn__ = cir090DdelKbn;
    }
    /**
     * <p>cir090DMonth を取得します。
     * @return cir090DMonth
     */
    public String getCir090DMonth() {
        return cir090DMonth__;
    }
    /**
     * <p>cir090DMonth をセットします。
     * @param cir090DMonth cir090DMonth
     */
    public void setCir090DMonth(String cir090DMonth) {
        cir090DMonth__ = cir090DMonth;
    }
    /**
     * <p>cir090DYear を取得します。
     * @return cir090DYear
     */
    public String getCir090DYear() {
        return cir090DYear__;
    }
    /**
     * <p>cir090DYear をセットします。
     * @param cir090DYear cir090DYear
     */
    public void setCir090DYear(String cir090DYear) {
        cir090DYear__ = cir090DYear;
    }
    /**
     * <p>cir090JdelKbn を取得します。
     * @return cir090JdelKbn
     */
    public String getCir090JdelKbn() {
        return cir090JdelKbn__;
    }
    /**
     * <p>cir090JdelKbn をセットします。
     * @param cir090JdelKbn cir090JdelKbn
     */
    public void setCir090JdelKbn(String cir090JdelKbn) {
        cir090JdelKbn__ = cir090JdelKbn;
    }
    /**
     * <p>cir090JMonth を取得します。
     * @return cir090JMonth
     */
    public String getCir090JMonth() {
        return cir090JMonth__;
    }
    /**
     * <p>cir090JMonth をセットします。
     * @param cir090JMonth cir090JMonth
     */
    public void setCir090JMonth(String cir090JMonth) {
        cir090JMonth__ = cir090JMonth;
    }
    /**
     * <p>cir090JYear を取得します。
     * @return cir090JYear
     */
    public String getCir090JYear() {
        return cir090JYear__;
    }
    /**
     * <p>cir090JYear をセットします。
     * @param cir090JYear cir090JYear
     */
    public void setCir090JYear(String cir090JYear) {
        cir090JYear__ = cir090JYear;
    }
    /**
     * <p>cir090MonthLabelList を取得します。
     * @return cir090MonthLabelList
     */
    public ArrayList<LabelValueBean> getCir090MonthLabelList() {
        return cir090MonthLabelList__;
    }
    /**
     * <p>cir090MonthLabelList をセットします。
     * @param cir090MonthLabelList cir090MonthLabelList
     */
    public void setCir090MonthLabelList(
            ArrayList<LabelValueBean> cir090MonthLabelList) {
        cir090MonthLabelList__ = cir090MonthLabelList;
    }
    /**
     * <p>cir090SdelKbn を取得します。
     * @return cir090SdelKbn
     */
    public String getCir090SdelKbn() {
        return cir090SdelKbn__;
    }
    /**
     * <p>cir090SdelKbn をセットします。
     * @param cir090SdelKbn cir090SdelKbn
     */
    public void setCir090SdelKbn(String cir090SdelKbn) {
        cir090SdelKbn__ = cir090SdelKbn;
    }
    /**
     * <p>cir090SMonth を取得します。
     * @return cir090SMonth
     */
    public String getCir090SMonth() {
        return cir090SMonth__;
    }
    /**
     * <p>cir090SMonth をセットします。
     * @param cir090SMonth cir090SMonth
     */
    public void setCir090SMonth(String cir090SMonth) {
        cir090SMonth__ = cir090SMonth;
    }
    /**
     * <p>cir090SYear を取得します。
     * @return cir090SYear
     */
    public String getCir090SYear() {
        return cir090SYear__;
    }
    /**
     * <p>cir090SYear をセットします。
     * @param cir090SYear cir090SYear
     */
    public void setCir090SYear(String cir090SYear) {
        cir090SYear__ = cir090SYear;
    }
    /**
     * <p>cir090YearLabelList を取得します。
     * @return cir090YearLabelList
     */
    public ArrayList<LabelValueBean> getCir090YearLabelList() {
        return cir090YearLabelList__;
    }
    /**
     * <p>cir090YearLabelList をセットします。
     * @param cir090YearLabelList cir090YearLabelList
     */
    public void setCir090YearLabelList(ArrayList<LabelValueBean> cir090YearLabelList) {
        cir090YearLabelList__ = cir090YearLabelList;
    }
    /**
     * <p>cir090SelKbn を取得します。
     * @return cir090SelKbn
     */
    public int getCir090SelKbn() {
        return cir090SelKbn__;
    }
    /**
     * <p>cir090SelKbn をセットします。
     * @param cir090SelKbn cir090SelKbn
     */
    public void setCir090SelKbn(int cir090SelKbn) {
        cir090SelKbn__ = cir090SelKbn;
    }
    /**
     * <p>cir090AccountSid を取得します。
     * @return cir090AccountSid
     */
    public int getCir090AccountSid() {
        return cir090AccountSid__;
    }
    /**
     * <p>cir090AccountSid をセットします。
     * @param cir090AccountSid cir090AccountSid
     */
    public void setCir090AccountSid(int cir090AccountSid) {
        cir090AccountSid__ = cir090AccountSid;
    }
    /**
     * <p>cir090AccountName を取得します。
     * @return cir090AccountName
     */
    public String getCir090AccountName() {
        return cir090AccountName__;
    }
    /**
     * <p>cir090AccountName をセットします。
     * @param cir090AccountName cir090AccountName
     */
    public void setCir090AccountName(String cir090AccountName) {
        cir090AccountName__ = cir090AccountName;
    }

}