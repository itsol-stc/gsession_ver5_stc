package jp.groupsession.v2.cir.cir120;

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
import jp.groupsession.v2.cir.cir100.Cir100Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 管理者設定 手動削除画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir120Form extends Cir100Form {

    /** 設定アカウント区分 */
    private int cir120SelKbn__ = GSConstCircular.ACCOUNT_SEL;
    /** 手動削除設定のアカウントSID */
    private int cir120AccountSid__;
    /** メ手動削除設定のアカウント名 */
    private String cir120AccountName__;

    //手動削除区分
    /** 受信タブ 処理区分 */
    private String cir120JdelKbn__ = null;
    /** 送信タブ 処理区分 */
    private String cir120SdelKbn__ = null;
    /** ゴミ箱タブ 処理区分 */
    private String cir120DdelKbn__ = null;

    /** 年リスト */
    private ArrayList<LabelValueBean> cir120YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> cir120MonthLabelList__ = null;
    /** 受信タブ 年選択 */
    private String cir120JYear__ = null;
    /** 受信タブ 月選択 */
    private String cir120JMonth__ = null;
    /** 送信済タブ 年選択 */
    private String cir120SYear__ = null;
    /** 送信済タブ 月選択 */
    private String cir120SMonth__ = null;
    /** ゴミ箱タブ 年選択 */
    private String cir120DYear__ = null;
    /** ゴミ箱タブ 月選択 */
    private String cir120DMonth__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validatecir120(
            Connection con, HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(req);

        //ユーザチェック
        errors = validateCheck(req);

        //削除区分が全て削除しないの時
        if (NullDefault.getInt(cir120JdelKbn__, 3) == GSConst.MANUAL_DEL_NO
                && NullDefault.getInt(cir120SdelKbn__, 3) == GSConst.MANUAL_DEL_NO
                && NullDefault.getInt(cir120DdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {

            ActionMessage msg =  new ActionMessage("error.input.selectoen.check",
                    gsMsg.getMessage("cmn.cmn310.06"));
            String eprefix = "allNotDel";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        boolean bYearError = false;
        boolean bMonthError = false;
        //受信
        if (NullDefault.getInt(cir120JdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(cir120JYear__, 13);
            int targetMonth = NullDefault.getInt(cir120JMonth__, 12);
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
        } else if (NullDefault.getInt(cir120JdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
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
        if (NullDefault.getInt(cir120SdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(cir120SYear__, 13);
            int targetMonth = NullDefault.getInt(cir120SMonth__, 12);
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
        } else if (NullDefault.getInt(cir120SdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
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
        if (NullDefault.getInt(cir120DdelKbn__, 3) == GSConst.MANUAL_DEL_LIMIT) {
            int targetYear = NullDefault.getInt(cir120DYear__, 13);
            int targetMonth = NullDefault.getInt(cir120DMonth__, 12);
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
        } else if (NullDefault.getInt(cir120DdelKbn__, 3) == GSConst.MANUAL_DEL_NO) {
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
    * <br>[機  能] 入力チェックを行う
    * <br>[解  説]
    * <br>[備  考]
    * @param req リクエスト
    * @return エラー
    */
   public ActionErrors validateCheck(HttpServletRequest req) {
       ActionErrors errors = new ActionErrors();

       GsMessage gsMsg = new GsMessage(req);
       String textDeleteCir = gsMsg.getMessage("wml.102");


       ActionMessage msg = null;

       //対象アカウント 指定するの場合
       if (cir120SelKbn__ == GSConstCircular.ACCOUNT_SEL) {
           if (cir120AccountSid__ < 1) {
               msg = new ActionMessage("error.select.required.text", textDeleteCir);
               StrutsUtil.addMessage(errors, msg, "cir120AccountSid");
           }
       }

       return errors;
   }

    /**
     * <p>cir120DdelKbn を取得します。
     * @return cir120DdelKbn
     */
    public String getCir120DdelKbn() {
        return cir120DdelKbn__;
    }
    /**
     * <p>cir120DdelKbn をセットします。
     * @param cir120DdelKbn cir120DdelKbn
     */
    public void setCir120DdelKbn(String cir120DdelKbn) {
        cir120DdelKbn__ = cir120DdelKbn;
    }
    /**
     * <p>cir120DMonth を取得します。
     * @return cir120DMonth
     */
    public String getCir120DMonth() {
        return cir120DMonth__;
    }
    /**
     * <p>cir120DMonth をセットします。
     * @param cir120DMonth cir120DMonth
     */
    public void setCir120DMonth(String cir120DMonth) {
        cir120DMonth__ = cir120DMonth;
    }
    /**
     * <p>cir120DYear を取得します。
     * @return cir120DYear
     */
    public String getCir120DYear() {
        return cir120DYear__;
    }
    /**
     * <p>cir120DYear をセットします。
     * @param cir120DYear cir120DYear
     */
    public void setCir120DYear(String cir120DYear) {
        cir120DYear__ = cir120DYear;
    }
    /**
     * <p>cir120JdelKbn を取得します。
     * @return cir120JdelKbn
     */
    public String getCir120JdelKbn() {
        return cir120JdelKbn__;
    }
    /**
     * <p>cir120JdelKbn をセットします。
     * @param cir120JdelKbn cir120JdelKbn
     */
    public void setCir120JdelKbn(String cir120JdelKbn) {
        cir120JdelKbn__ = cir120JdelKbn;
    }
    /**
     * <p>cir120JMonth を取得します。
     * @return cir120JMonth
     */
    public String getCir120JMonth() {
        return cir120JMonth__;
    }
    /**
     * <p>cir120JMonth をセットします。
     * @param cir120JMonth cir120JMonth
     */
    public void setCir120JMonth(String cir120JMonth) {
        cir120JMonth__ = cir120JMonth;
    }
    /**
     * <p>cir120JYear を取得します。
     * @return cir120JYear
     */
    public String getCir120JYear() {
        return cir120JYear__;
    }
    /**
     * <p>cir120JYear をセットします。
     * @param cir120JYear cir120JYear
     */
    public void setCir120JYear(String cir120JYear) {
        cir120JYear__ = cir120JYear;
    }
    /**
     * <p>cir120MonthLabelList を取得します。
     * @return cir120MonthLabelList
     */
    public ArrayList<LabelValueBean> getCir120MonthLabelList() {
        return cir120MonthLabelList__;
    }
    /**
     * <p>cir120MonthLabelList をセットします。
     * @param cir120MonthLabelList cir120MonthLabelList
     */
    public void setCir120MonthLabelList(
            ArrayList<LabelValueBean> cir120MonthLabelList) {
        cir120MonthLabelList__ = cir120MonthLabelList;
    }
    /**
     * <p>cir120SdelKbn を取得します。
     * @return cir120SdelKbn
     */
    public String getCir120SdelKbn() {
        return cir120SdelKbn__;
    }
    /**
     * <p>cir120SdelKbn をセットします。
     * @param cir120SdelKbn cir120SdelKbn
     */
    public void setCir120SdelKbn(String cir120SdelKbn) {
        cir120SdelKbn__ = cir120SdelKbn;
    }
    /**
     * <p>cir120SMonth を取得します。
     * @return cir120SMonth
     */
    public String getCir120SMonth() {
        return cir120SMonth__;
    }
    /**
     * <p>cir120SMonth をセットします。
     * @param cir120SMonth cir120SMonth
     */
    public void setCir120SMonth(String cir120SMonth) {
        cir120SMonth__ = cir120SMonth;
    }
    /**
     * <p>cir120SYear を取得します。
     * @return cir120SYear
     */
    public String getCir120SYear() {
        return cir120SYear__;
    }
    /**
     * <p>cir120SYear をセットします。
     * @param cir120SYear cir120SYear
     */
    public void setCir120SYear(String cir120SYear) {
        cir120SYear__ = cir120SYear;
    }
    /**
     * <p>cir120YearLabelList を取得します。
     * @return cir120YearLabelList
     */
    public ArrayList<LabelValueBean> getCir120YearLabelList() {
        return cir120YearLabelList__;
    }
    /**
     * <p>cir120YearLabelList をセットします。
     * @param cir120YearLabelList cir120YearLabelList
     */
    public void setCir120YearLabelList(ArrayList<LabelValueBean> cir120YearLabelList) {
        cir120YearLabelList__ = cir120YearLabelList;
    }
    /**
     * <p>cir120SelKbn を取得します。
     * @return cir120SelKbn
     */
    public int getCir120SelKbn() {
        return cir120SelKbn__;
    }
    /**
     * <p>cir120SelKbn をセットします。
     * @param cir120SelKbn cir120SelKbn
     */
    public void setCir120SelKbn(int cir120SelKbn) {
        cir120SelKbn__ = cir120SelKbn;
    }
    /**
     * <p>cir120AccountName を取得します。
     * @return cir120AccountName
     */
    public String getCir120AccountName() {
        return cir120AccountName__;
    }
    /**
     * <p>cir120AccountName をセットします。
     * @param cir120AccountName cir120AccountName
     */
    public void setCir120AccountName(String cir120AccountName) {
        cir120AccountName__ = cir120AccountName;
    }
    /**
     * <p>cir120AccountSid を取得します。
     * @return cir120AccountSid
     */
    public int getCir120AccountSid() {
        return cir120AccountSid__;
    }
    /**
     * <p>cir120AccountSid をセットします。
     * @param cir120AccountSid cir120AccountSid
     */
    public void setCir120AccountSid(int cir120AccountSid) {
        cir120AccountSid__ = cir120AccountSid;
    }
}