package jp.groupsession.v2.rsv.rsv120;

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
 * <br>[機  能] 施設予約 管理者設定 自動データ削除設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv120Form extends Rsv040Form {
    /** 自動削除バッチ区分 */
    private int rsv120batchKbn__ = GSConstReserve.RSV_RADIO_OFF;
    /** 年 */
    private int rsv120year__ = GSConstReserve.COMBO_DEFAULT_VALUE;
    /** 月 */
    private int rsv120month__ = GSConstReserve.COMBO_DEFAULT_VALUE;
    /** 年リスト */
    private ArrayList<LabelValueBean> rsv120yearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> rsv120monthLabelList__ = null;
    /** バッチ起動時間 */
    private String rsv120BatchTime__ = null;

    /** 初期表示フラグ */
    private int rsv120initDspFlg__ = 0;

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

        if (rsv120batchKbn__ == GSConst.AUTO_DEL_LIMIT) {
            int targetYear = rsv120year__;
            int targetMonth = rsv120month__;
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
                        gsMsg.getMessage("cmn.reserve"),
                        gsMsg.getMessage("cmn.autodelete"));
                String eprefix = "rsvautoDel";
                StrutsUtil.addMessage(errors, msg, eprefix);
            } else if (targetYear == 0 && targetMonth == 0) {
                ActionMessage msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("cmn.reserve"),
                        gsMsg.getMessage("cmn.autodelete"),
                        gsMsg.getMessage("cht.cht050.02"));
                String eprefix = "rsvautoDel";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        } else if (rsv120batchKbn__ == GSConst.AUTO_DEL_NO) {
        } else {
            ActionMessage msg =  new ActionMessage("error.autodel.between",
                    gsMsg.getMessage("cmn.reserve"),
                    gsMsg.getMessage("cmn.autodelete"));
            String eprefix = "rsvautoDel";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }
        return errors;
    }
    
    /**
     * <p>rsv120batchKbn を取得します。
     * @return rsv120batchKbn
     */
    public int getRsv120batchKbn() {
        return rsv120batchKbn__;
    }
    /**
     * <p>rsv120batchKbn をセットします。
     * @param rsv120batchKbn rsv120batchKbn
     */
    public void setRsv120batchKbn(int rsv120batchKbn) {
        rsv120batchKbn__ = rsv120batchKbn;
    }
    /**
     * <p>rsv120month を取得します。
     * @return rsv120month
     */
    public int getRsv120month() {
        return rsv120month__;
    }
    /**
     * <p>rsv120month をセットします。
     * @param rsv120month rsv120month
     */
    public void setRsv120month(int rsv120month) {
        rsv120month__ = rsv120month;
    }
    /**
     * <p>rsv120year を取得します。
     * @return rsv120year
     */
    public int getRsv120year() {
        return rsv120year__;
    }
    /**
     * <p>rsv120year をセットします。
     * @param rsv120year rsv120year
     */
    public void setRsv120year(int rsv120year) {
        rsv120year__ = rsv120year;
    }
    /**
     * <p>rsv120monthLabelList を取得します。
     * @return rsv120monthLabelList
     */
    public ArrayList<LabelValueBean> getRsv120monthLabelList() {
        return rsv120monthLabelList__;
    }
    /**
     * <p>rsv120monthLabelList をセットします。
     * @param rsv120monthLabelList rsv120monthLabelList
     */
    public void setRsv120monthLabelList(
            ArrayList<LabelValueBean> rsv120monthLabelList) {
        rsv120monthLabelList__ = rsv120monthLabelList;
    }
    /**
     * <p>rsv120yearLabelList を取得します。
     * @return rsv120yearLabelList
     */
    public ArrayList<LabelValueBean> getRsv120yearLabelList() {
        return rsv120yearLabelList__;
    }
    /**
     * <p>rsv120yearLabelList をセットします。
     * @param rsv120yearLabelList rsv120yearLabelList
     */
    public void setRsv120yearLabelList(ArrayList<LabelValueBean> rsv120yearLabelList) {
        rsv120yearLabelList__ = rsv120yearLabelList;
    }

    /**
     * <p>rsv120initDspFlg を取得します。
     * @return rsv120initDspFlg
     */
    public int getRsv120initDspFlg() {
        return rsv120initDspFlg__;
    }

    /**
     * <p>rsv120initDspFlg をセットします。
     * @param rsv120initDspFlg rsv120initDspFlg
     */
    public void setRsv120initDspFlg(int rsv120initDspFlg) {
        rsv120initDspFlg__ = rsv120initDspFlg;
    }
    /**
     * <p>rsv120BatchTime__ を取得します。
     * @return rsv120BatchTime
     */
    public String getRsv120BatchTime() {
        return rsv120BatchTime__;
    }
    /**
     * <p>rsv120BatchTime__ をセットします。
     * @param rsv120BatchTime rsv120BatchTime__
     */
    public void setRsv120BatchTime(String rsv120BatchTime) {
        rsv120BatchTime__ = rsv120BatchTime;
    }
}