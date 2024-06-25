package jp.groupsession.v2.rsv.rsv130;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.rsv040.Rsv040Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 管理者設定 手動データ削除画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv130Form extends Rsv040Form {
    /** 年 */
    private int rsv130year__ = 3;
    /** 月 */
    private int rsv130month__ = GSConstReserve.COMBO_DEFAULT_VALUE;
    /** 年リスト */
    private ArrayList<LabelValueBean> rsv130yearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> rsv130monthLabelList__ = null;

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

        int targetYear = rsv130year__;
        int targetMonth = rsv130month__;
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
                    gsMsg.getMessage("cmn.reserve"),
                    gsMsg.getMessage("cmn.manual.delete2"),
                    gsMsg.getMessage("cmn.passage.year"));
            String eprefix = "rsvYear";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        if (!bMonthError) {
            ActionMessage msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("cmn.reserve"),
                    gsMsg.getMessage("cmn.manual.delete2"),
                    gsMsg.getMessage("cmn.passage.month"));
            String eprefix = "rsvMonth";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        if (targetYear == 0 && targetMonth == 0) {
            ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                    gsMsg.getMessage("cmn.reserve"),
                    gsMsg.getMessage("cmn.manual.delete2"),
                    gsMsg.getMessage("cht.cht050.02"));
            String eprefix = "rsvLowLimit";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        return errors;
    }

    /**
     * <p>rsv130month を取得します。
     * @return rsv130month
     */
    public int getRsv130month() {
        return rsv130month__;
    }
    /**
     * <p>rsv130month をセットします。
     * @param rsv130month rsv130month
     */
    public void setRsv130month(int rsv130month) {
        rsv130month__ = rsv130month;
    }
    /**
     * <p>rsv130monthLabelList を取得します。
     * @return rsv130monthLabelList
     */
    public ArrayList<LabelValueBean> getRsv130monthLabelList() {
        return rsv130monthLabelList__;
    }
    /**
     * <p>rsv130monthLabelList をセットします。
     * @param rsv130monthLabelList rsv130monthLabelList
     */
    public void setRsv130monthLabelList(
            ArrayList<LabelValueBean> rsv130monthLabelList) {
        rsv130monthLabelList__ = rsv130monthLabelList;
    }
    /**
     * <p>rsv130year を取得します。
     * @return rsv130year
     */
    public int getRsv130year() {
        return rsv130year__;
    }
    /**
     * <p>rsv130year をセットします。
     * @param rsv130year rsv130year
     */
    public void setRsv130year(int rsv130year) {
        rsv130year__ = rsv130year;
    }
    /**
     * <p>rsv130yearLabelList を取得します。
     * @return rsv130yearLabelList
     */
    public ArrayList<LabelValueBean> getRsv130yearLabelList() {
        return rsv130yearLabelList__;
    }
    /**
     * <p>rsv130yearLabelList をセットします。
     * @param rsv130yearLabelList rsv130yearLabelList
     */
    public void setRsv130yearLabelList(ArrayList<LabelValueBean> rsv130yearLabelList) {
        rsv130yearLabelList__ = rsv130yearLabelList;
    }
}