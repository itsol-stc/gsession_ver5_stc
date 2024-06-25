package jp.groupsession.v2.mem.mem030;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メモ 管理者設定 自動削除設定画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem030Form extends AbstractGsForm {
    /** 自動削除フラグ */
    private int mem030Kbn__ = GSConstMemo.AUTO_DELETE_KBN_OFF;
    /** 経過年 */
    private int mem030Year__ = -1;
    /** 経過月 */
    private int mem030Month__ = -1;

    /** 経過年ラベルの選択値 */
    public static final String[] YEAR_VALUE
    = new String[] {"0", "1", "2", "3", "4", "5", "10"};
    /** 経過月ラベルの選択最小値 */
    public static final int MAX_MONTH = 11;
    /** 経過月ラベルの選択最小値 */
    public static final int MIN_MONTH = 0;
    /** 経過年ラベル */
    private List < LabelValueBean > mem030AtdelYearLabel__ = null;
    /** 経過月ラベル */
    private List < LabelValueBean > mem030AtdelMonthLabel__ = null;

    /** 初期表示フラグ */
    private int mem030InitFlg__ = GSConstMemo.INIT_FLG;

    /** バッチ処理実行時間 */
    private String batchTime__ = "";


    /**
     * <p>mem030Kbn を取得します。
     * @return mem030Kbn
     */
    public int getMem030Kbn() {
        return mem030Kbn__;
    }
    /**
     * mem030Kbn をセットします。
     * @param mem030Kbn mem030Kbn
     */
    public void setMem030Kbn(int mem030Kbn) {
        mem030Kbn__ = mem030Kbn;
    }
    /**
     * <p>mem030Year を取得します。
     * @return mem030Year
     */
    public int getMem030Year() {
        return mem030Year__;
    }
    /**
     * mem030Year をセットします。
     * @param mem030Year mem030Year
     */
    public void setMem030Year(int mem030Year) {
        mem030Year__ = mem030Year;
    }
    /**
     * <p>mem030Month を取得します。
     * @return mem030Month
     */
    public int getMem030Month() {
        return mem030Month__;
    }
    /**
     * mem030Month をセットします。
     * @param mem030Month mem030Month
     */
    public void setMem030Month(int mem030Month) {
        mem030Month__ = mem030Month;
    }
    /**
     * <p>mem030AtdelYearLabel を取得します。
     * @return mem030AtdelYearLabel
     */
    public List<LabelValueBean> getMem030AtdelYearLabel() {
        return mem030AtdelYearLabel__;
    }
    /**
     * mem030AtdelYearLabel をセットします。
     * @param mem030AtdelYearLabel mem030AtdelYearLabel
     */
    public void setMem030AtdelYearLabel(List<LabelValueBean> mem030AtdelYearLabel) {
        mem030AtdelYearLabel__ = mem030AtdelYearLabel;
    }
    /**
     * <p>mem030AtdelMonthLabel を取得します。
     * @return mem030AtdelMonthLabel
     */
    public List<LabelValueBean> getMem030AtdelMonthLabel() {
        return mem030AtdelMonthLabel__;
    }
    /**
     * mem030AtdelMonthLabel をセットします。
     * @param mem030AtdelMonthLabel mem030AtdelMonthLabel
     */
    public void setMem030AtdelMonthLabel(List<LabelValueBean> mem030AtdelMonthLabel) {
        mem030AtdelMonthLabel__ = mem030AtdelMonthLabel;
    }
    /**
     * <p>mem030InitFlg を取得します。
     * @return mem030InitFlg
     */
    public int getMem030InitFlg() {
        return mem030InitFlg__;
    }
    /**
     * mem030InitFlg をセットします。
     * @param mem030InitFlg mem030InitFlg
     */
    public void setMem030InitFlg(int mem030InitFlg) {
        mem030InitFlg__ = mem030InitFlg;
    }
    /**
     * <p>batchTime を取得します。
     * @return batchTime
     */
    public String getBatchTime() {
        return batchTime__;
    }
    /**
     * batchTime をセットします。
     * @param batchTime batchTime
     */
    public void setBatchTime(String batchTime) {
        batchTime__ = batchTime;
    }

    /**
     * <br>[機  能] フォームの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();

        //フラグチェック
        if (mem030Kbn__ != GSConstMemo.AUTO_DELETE_KBN_OFF
                && mem030Kbn__ != GSConstMemo.AUTO_DELETE_KBN_ON) {

            //メッセージ自動削除
            msg =  new ActionMessage("error.autodel.between",
                    gsMsg.getMessage("memo.01"),
                    gsMsg.getMessage("cmn.autodelete"));
            String eprefix = "memautoDel";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        if (mem030Kbn__ == GSConstMemo.AUTO_DELETE_KBN_ON) {
            //経過年
            boolean bYearFlg = false;
            for (int iYear: GSConst.DEL_YEAR_DATE) {
                if (mem030Year__ == iYear) {
                    bYearFlg = true;
                    break;
                }
            }
            //経過月
            boolean bMonthFlg = false;
            for (int m: GSConst.DEL_MONTH_DATE) {
                if (m == mem030Month__) {
                    bMonthFlg = true;
                    break;
                }
            }
            
            if (!bYearFlg || !bMonthFlg) {
                msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("memo.01"),
                        gsMsg.getMessage("cmn.autodelete"));
                String eprefix = "memautoDel";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }

            //経過年、月
            if (bYearFlg && bMonthFlg) {
                if (mem030Year__ == 0 && mem030Month__ == 0) {
                    msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("memo.01"),
                            gsMsg.getMessage("cmn.autodelete"),
                            gsMsg.getMessage("cht.cht050.02"));
                    String eprefix = "memautoDel";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            }
        }
        return errors;
    }
}