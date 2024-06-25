package jp.groupsession.v2.ntp.ntp082;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp080.Ntp080Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 自動データ削除設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp082Form extends Ntp080Form {

    /** 自動削除フラグ */
    private int ntp082AtdelFlg__ = GSConstNippou.AUTO_DELETE_OFF;
    /** 経過年 */
    private int ntp082AtdelYear__ = -1;
    /** 経過月 */
    private int ntp082AtdelMonth__ = -1;

    /** 経過年ラベルの選択値 */
    public static final String[] YEAR_VALUE
        = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    /** 経過月ラベルの選択値 */
    public static final String[] MONTH_VALUE
        = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    /** 経過年ラベル */
    private List < LabelValueBean > ntp082AtdelYearLabel__ = null;
    /** 経過月ラベル */
    private List < LabelValueBean > ntp082AtdelMonthLabel__ = null;

    /** バッチ処理実行時間 */
    private String batchTime__ = "";

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Ntp082Form() {
        //年ラベル作成
        ntp082AtdelYearLabel__ = new ArrayList<LabelValueBean>();
        for (String label : YEAR_VALUE) {
            ntp082AtdelYearLabel__.add(new LabelValueBean(label + "年", label));
        }
        //月ラベル作成
        ntp082AtdelMonthLabel__ = new ArrayList<LabelValueBean>();
        for (String label : MONTH_VALUE) {
            ntp082AtdelMonthLabel__.add(new LabelValueBean(label + "ヶ月", label));
        }
    }

    /**
     * <p>ntp082AtdelFlg を取得します。
     * @return ntp082AtdelFlg
     */
    public int getNtp082AtdelFlg() {
        return ntp082AtdelFlg__;
    }

    /**
     * <p>ntp082AtdelFlg をセットします。
     * @param ntp082AtdelFlg ntp082AtdelFlg
     */
    public void setNtp082AtdelFlg(int ntp082AtdelFlg) {
        ntp082AtdelFlg__ = ntp082AtdelFlg;
    }

    /**
     * <p>ntp082AtdelMonth を取得します。
     * @return ntp082AtdelMonth
     */
    public int getNtp082AtdelMonth() {
        return ntp082AtdelMonth__;
    }

    /**
     * <p>ntp082AtdelMonth をセットします。
     * @param ntp082AtdelMonth ntp082AtdelMonth
     */
    public void setNtp082AtdelMonth(int ntp082AtdelMonth) {
        ntp082AtdelMonth__ = ntp082AtdelMonth;
    }

    /**
     * <p>ntp082AtdelYear を取得します。
     * @return ntp082AtdelYear
     */
    public int getNtp082AtdelYear() {
        return ntp082AtdelYear__;
    }

    /**
     * <p>ntp082AtdelYear をセットします。
     * @param ntp082AtdelYear ntp082AtdelYear
     */
    public void setNtp082AtdelYear(int ntp082AtdelYear) {
        ntp082AtdelYear__ = ntp082AtdelYear;
    }

    /**
     * <p>ntp082AtdelMonthLabel を取得します。
     * @return ntp082AtdelMonthLabel
     */
    public List<LabelValueBean> getNtp082AtdelMonthLabel() {
        return ntp082AtdelMonthLabel__;
    }

    /**
     * <p>ntp082AtdelMonthLabel をセットします。
     * @param ntp082AtdelMonthLabel ntp082AtdelMonthLabel
     */
    public void setNtp082AtdelMonthLabel(List<LabelValueBean> ntp082AtdelMonthLabel) {
        this.ntp082AtdelMonthLabel__ = ntp082AtdelMonthLabel;
    }

    /**
     * <p>ntp082AtdelYearLabel を取得します。
     * @return ntp082AtdelYearLabel
     */
    public List<LabelValueBean> getNtp082AtdelYearLabel() {
        return ntp082AtdelYearLabel__;
    }

    /**
     * <p>ntp082AtdelYearLabel をセットします。
     * @param ntp082AtdelYearLabel ntp082AtdelYearLabel
     */
    public void setNtp082AtdelYearLabel(List<LabelValueBean> ntp082AtdelYearLabel) {
        ntp082AtdelYearLabel__ = ntp082AtdelYearLabel;
    }

    /**
     * <p>batchTime を取得します。
     * @return batchTime
     */
    public String getBatchTime() {
        return batchTime__;
    }

    /**
     * <p>batchTime をセットします。
     * @param batchTime batchTime
     */
    public void setBatchTime(String batchTime) {
        batchTime__ = batchTime;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        //フラグチェック
        if (ntp082AtdelFlg__ != GSConstNippou.AUTO_DELETE_OFF
                && ntp082AtdelFlg__ != GSConstNippou.AUTO_DELETE_ON) {
            msg =  new ActionMessage("error.autodel.between",
                    gsMsg.getMessage("ntp.1"),
                    gsMsg.getMessage("cmn.autodelete"));
            String eprefix = "ntpautoDel";
            StrutsUtil.addMessage(errors, msg, eprefix);        
        }

        if (ntp082AtdelFlg__ == GSConstNippou.AUTO_DELETE_ON) {
            //経過年
            boolean yFlg = false;
            for (int iy : GSConst.DEL_YEAR_DATE) {
                if (ntp082AtdelYear__ == iy) {
                    yFlg = true;
                    break;
                }
            }
            //経過月
            boolean mFlg = false;
            for (int im : GSConst.DEL_MONTH_DATE) {
                if (ntp082AtdelMonth__ == im) {
                    mFlg = true;
                    break;
                }
            }
            if (!yFlg || !mFlg) {
                msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("ntp.1"),
                        gsMsg.getMessage("cmn.autodelete"));
                String eprefix = "ntpautoDel";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }

            //経過年、月
            if (yFlg && mFlg) {
                if (ntp082AtdelYear__ == 0 && ntp082AtdelMonth__ == 0) {
                    msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("ntp.1"),
                            gsMsg.getMessage("cmn.autodelete"),
                            gsMsg.getMessage("cht.cht050.02"));
                    String eprefix = "ntpautoDel";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            }
        }
        return errors;
    }
}