package jp.groupsession.v2.sch.sch082;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.sch.sch100.Sch100Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール 自動データ削除設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch082Form extends Sch100Form {

    /** 自動削除フラグ */
    private int sch082AtdelFlg__ = GSConstSchedule.AUTO_DELETE_OFF;
    /** 経過年 */
    private int sch082AtdelYear__ = -1;
    /** 経過月 */
    private int sch082AtdelMonth__ = -1;

    /** 経過年ラベルの選択値 */
    public static final String[] YEAR_VALUE
        = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    /** 経過月ラベルの選択値 */
    public static final String[] MONTH_VALUE
        = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    /** 経過年ラベル */
    private List < LabelValueBean > sch082AtdelYearLabel__ = null;
    /** 経過月ラベル */
    private List < LabelValueBean > sch082AtdelMonthLabel__ = null;

    /** バッチ処理実行時間 */
    private String batchTime__ = "";

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Sch082Form() {
//        GsMessage gsMsg = new GsMessage();
//        //ヶ月
//        String textMonth = gsMsg.getMessage(req, "cmn.months");
//        //年
//        String textYear = gsMsg.getMessage(req, "cmn.year");
//        //年ラベル作成
//        sch082AtdelYearLabel__ = new ArrayList<LabelValueBean>();
//        for (String label : YEAR_VALUE) {
//            sch082AtdelYearLabel__.add(new LabelValueBean(label + textYear, label));
//        }
//        //月ラベル作成
//        sch082AtdelMonthLabel__ = new ArrayList<LabelValueBean>();
//        for (String label : MONTH_VALUE) {
//            sch082AtdelMonthLabel__.add(new LabelValueBean(label + textMonth, label));
//        }
    }

    /**
     * <p>sch082AtdelFlg を取得します。
     * @return sch082AtdelFlg
     */
    public int getSch082AtdelFlg() {
        return sch082AtdelFlg__;
    }

    /**
     * <p>sch082AtdelFlg をセットします。
     * @param sch082AtdelFlg sch082AtdelFlg
     */
    public void setSch082AtdelFlg(int sch082AtdelFlg) {
        sch082AtdelFlg__ = sch082AtdelFlg;
    }

    /**
     * <p>sch082AtdelMonth を取得します。
     * @return sch082AtdelMonth
     */
    public int getSch082AtdelMonth() {
        return sch082AtdelMonth__;
    }

    /**
     * <p>sch082AtdelMonth をセットします。
     * @param sch082AtdelMonth sch082AtdelMonth
     */
    public void setSch082AtdelMonth(int sch082AtdelMonth) {
        sch082AtdelMonth__ = sch082AtdelMonth;
    }

    /**
     * <p>sch082AtdelYear を取得します。
     * @return sch082AtdelYear
     */
    public int getSch082AtdelYear() {
        return sch082AtdelYear__;
    }

    /**
     * <p>sch082AtdelYear をセットします。
     * @param sch082AtdelYear sch082AtdelYear
     */
    public void setSch082AtdelYear(int sch082AtdelYear) {
        sch082AtdelYear__ = sch082AtdelYear;
    }

    /**
     * <p>sch082AtdelMonthLabel を取得します。
     * @return sch082AtdelMonthLabel
     */
    public List<LabelValueBean> getSch082AtdelMonthLabel() {
        return sch082AtdelMonthLabel__;
    }

    /**
     * <p>sch082AtdelMonthLabel をセットします。
     * @param sch082AtdelMonthLabel sch082AtdelMonthLabel
     */
    public void setSch082AtdelMonthLabel(List<LabelValueBean> sch082AtdelMonthLabel) {
        this.sch082AtdelMonthLabel__ = sch082AtdelMonthLabel;
    }

    /**
     * <p>sch082AtdelYearLabel を取得します。
     * @return sch082AtdelYearLabel
     */
    public List<LabelValueBean> getSch082AtdelYearLabel() {
        return sch082AtdelYearLabel__;
    }

    /**
     * <p>sch082AtdelYearLabel をセットします。
     * @param sch082AtdelYearLabel sch082AtdelYearLabel
     */
    public void setSch082AtdelYearLabel(List<LabelValueBean> sch082AtdelYearLabel) {
        sch082AtdelYearLabel__ = sch082AtdelYearLabel;
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
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();

        //フラグチェック
        if (sch082AtdelFlg__ != GSConstSchedule.AUTO_DELETE_OFF
                && sch082AtdelFlg__ != GSConstSchedule.AUTO_DELETE_ON) {

            //メッセージ 自動削除
            msg =  new ActionMessage("error.autodel.between",
                    gsMsg.getMessage("schedule.108"),
                    gsMsg.getMessage("cmn.autodelete"));
            String eprefix = "schautoDel";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        if (sch082AtdelFlg__ == GSConstSchedule.AUTO_DELETE_ON) {
            //経過年
            boolean yFlg = false;
            for (int iy : GSConst.DEL_YEAR_DATE) {
                if (sch082AtdelYear__ == iy) {
                    yFlg = true;
                    break;
                }
            }
            //経過月
            boolean mFlg = false;
            for (int im : GSConst.DEL_MONTH_DATE) {
                if (sch082AtdelMonth__ == im) {
                    mFlg = true;
                    break;
                }
            }
            if (yFlg == false || mFlg == false) {
                msg =  new ActionMessage("error.autodel.between",
                        gsMsg.getMessage("schedule.108"),
                        gsMsg.getMessage("cmn.autodelete"));
                String eprefix = "schautoDel";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
            //経過年、月
            if (yFlg && mFlg) {
                if (sch082AtdelYear__ == 0 && sch082AtdelMonth__ == 0) {
                    msg =  new ActionMessage("error.autodel.range0over",
                            gsMsg.getMessage("schedule.108"),
                            gsMsg.getMessage("cmn.autodelete"),
                            gsMsg.getMessage("cht.cht050.02"));
                    String eprefix = "schautoDel";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
            }
        }
        return errors;
    }
}
