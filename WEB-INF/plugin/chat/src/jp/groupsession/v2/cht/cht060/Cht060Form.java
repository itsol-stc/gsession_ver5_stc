package jp.groupsession.v2.cht.cht060;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht020.Cht020Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] 手動データ削除のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht060Form extends Cht020Form {

    /** 手動削除 年 */
    private int cht060ReferenceYear__ = GSConstChat.DEL_YEAR_DATE[3];
    /** 手動削除 月 */
    private int cht060ReferenceMonth__ = GSConstChat.DEL_MONTH_DATE[0];
    /** 年コンボ */
    private List<LabelValueBean> cht060YearLabelList__ = null;
    /** 月コンボ */
    private List<LabelValueBean> cht060MonthLabelList__ = null;


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param form フォーム
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCht060(
            RequestModel reqMdl, Connection con, Cht060Form form) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        boolean chkYear = false;
        for (int year: GSConst.DEL_YEAR_DATE) {
            if (year == cht060ReferenceYear__) {
                chkYear = true;
             break;
            }
        }
        if (!chkYear) {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cht.01"),
                    gsMsg.getMessage("cmn.manual.delete2"),
                    gsMsg.getMessage("cmn.passage.year"));
            String eprefix = "chtYear";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        boolean chkMonth = false;
        for (int month: GSConst.DEL_MONTH_DATE) {
            if (month == cht060ReferenceMonth__) {
                chkMonth = true;
             break;
            }
        }
        if (!chkMonth) {
        ActionMessage msg =  new ActionMessage("error.manualdel.between",
                gsMsg.getMessage("cht.01"),
                gsMsg.getMessage("cmn.manual.delete2"),
                gsMsg.getMessage("cmn.passage.month"));
        String eprefix = "chtMonth";
        StrutsUtil.addMessage(errors, msg, eprefix);
        }
        if (cht060ReferenceYear__ == 0 && cht060ReferenceMonth__ == 0) {
            ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                    gsMsg.getMessage("cht.01"),
                    gsMsg.getMessage("cmn.manual.delete2"),
                    gsMsg.getMessage("cht.cht050.02"));
            String eprefix = "chtLowLimit";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        return errors;
    }


    /**
     * <p>cht060ReferenceYear を取得します。
     * @return cht060ReferenceYear
     * @see jp.groupsession.v2.cht.cht060.Cht060Form#cht060ReferenceYear__
     */
    public int getCht060ReferenceYear() {
        return cht060ReferenceYear__;
    }


    /**
     * <p>cht060ReferenceYear をセットします。
     * @param cht060ReferenceYear cht060ReferenceYear
     * @see jp.groupsession.v2.cht.cht060.Cht060Form#cht060ReferenceYear__
     */
    public void setCht060ReferenceYear(int cht060ReferenceYear) {
        cht060ReferenceYear__ = cht060ReferenceYear;
    }


    /**
     * <p>cht060ReferenceMonth を取得します。
     * @return cht060ReferenceMonth
     * @see jp.groupsession.v2.cht.cht060.Cht060Form#cht060ReferenceMonth__
     */
    public int getCht060ReferenceMonth() {
        return cht060ReferenceMonth__;
    }


    /**
     * <p>cht060ReferenceMonth をセットします。
     * @param cht060ReferenceMonth cht060ReferenceMonth
     * @see jp.groupsession.v2.cht.cht060.Cht060Form#cht060ReferenceMonth__
     */
    public void setCht060ReferenceMonth(int cht060ReferenceMonth) {
        cht060ReferenceMonth__ = cht060ReferenceMonth;
    }


    /**
     * <p>cht060YearLabelList を取得します。
     * @return cht060YearLabelList
     * @see jp.groupsession.v2.cht.cht060.Cht060Form#cht060YearLabelList__
     */
    public List<LabelValueBean> getCht060YearLabelList() {
        return cht060YearLabelList__;
    }


    /**
     * <p>cht060YearLabelList をセットします。
     * @param cht060YearLabelList cht060YearLabelList
     * @see jp.groupsession.v2.cht.cht060.Cht060Form#cht060YearLabelList__
     */
    public void setCht060YearLabelList(List<LabelValueBean> cht060YearLabelList) {
        cht060YearLabelList__ = cht060YearLabelList;
    }


    /**
     * <p>cht060MonthLabelList を取得します。
     * @return cht060MonthLabelList
     * @see jp.groupsession.v2.cht.cht060.Cht060Form#cht060MonthLabelList__
     */
    public List<LabelValueBean> getCht060MonthLabelList() {
        return cht060MonthLabelList__;
    }


    /**
     * <p>cht060MonthLabelList をセットします。
     * @param cht060MonthLabelList cht060MonthLabelList
     * @see jp.groupsession.v2.cht.cht060.Cht060Form#cht060MonthLabelList__
     */
    public void setCht060MonthLabelList(List<LabelValueBean> cht060MonthLabelList) {
        cht060MonthLabelList__ = cht060MonthLabelList;
    }




}
