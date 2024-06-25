package jp.groupsession.v2.sch.sch083;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.sch.sch100.Sch100Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール 手動データ削除設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch083Form extends Sch100Form {

    /** 経過年 */
    private int sch083DelYear__ = 3;
    /** 経過月 */
    private int sch083DelMonth__ = -1;
    /** 経過年ラベルの選択値 */
    public static final String[] YEAR_VALUE
        = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    /** 経過月ラベルの選択値 */
    public static final String[] MONTH_VALUE
        = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    /** 経過年ラベル */
    private List < LabelValueBean > sch083DelYearLabel__ = null;
    /** 経過月ラベル */
    private List < LabelValueBean > sch083DelMonthLabel__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Sch083Form() {
//        GsMessage gsMsg = new GsMessage();
//        //ヶ月
//        String textMonth = gsMsg.getMessage(req, "cmn.months");
//        //年
//        String textYear = gsMsg.getMessage(req, "cmn.year");
//        //年ラベル作成
//        sch083DelYearLabel__ = new ArrayList<LabelValueBean>();
//        for (String label : YEAR_VALUE) {
//            sch083DelYearLabel__.add(new LabelValueBean(label + textYear, label));
//        }
//        //月ラベル作成
//        sch083DelMonthLabel__ = new ArrayList<LabelValueBean>();
//        for (String label : MONTH_VALUE) {
//            sch083DelMonthLabel__.add(new LabelValueBean(label + textMonth, label));
//        }
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
        GsMessage gsMsg = new GsMessage(req);

        //経過年
        boolean yFlg = false;
        for (String sy : YEAR_VALUE) {
            int iy = Integer.parseInt(sy);
            if (sch083DelYear__ == iy) {
                yFlg = true;
                break;
            }
        }
        if (yFlg == false) {
            //経過年
            msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("schedule.108"),
                    gsMsg.getMessage("cmn.manual.delete2"),
                    gsMsg.getMessage("cmn.passage.year"));
            String eprefix = "schYear";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }


        //経過月
        boolean mFlg = false;
        for (String sm : MONTH_VALUE) {
            int im = Integer.parseInt(sm);
            if (sch083DelMonth__ == im) {
                mFlg = true;
                break;
            }
        }

        if (mFlg == false) {
            //経過月
            msg =  new ActionMessage("error.manualdel.between",
                    gsMsg.getMessage("schedule.108"),
                    gsMsg.getMessage("cmn.manual.delete2"),
                    gsMsg.getMessage("cmn.passage.month"));
            String eprefix = "schMonth";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        //経過年、月
        if (yFlg && mFlg) {
            if (sch083DelYear__ == 0 && sch083DelMonth__ == 0) {
                msg =  new ActionMessage("error.autodel.range0over",
                        gsMsg.getMessage("schedule.108"),
                        gsMsg.getMessage("cmn.manual.delete2"),
                        gsMsg.getMessage("cht.cht050.02"));
                String eprefix = "schLowLimit";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }
        }
        return errors;
    }

    /**
     * <p>sch083DelMonth を取得します。
     * @return sch083DelMonth
     */
    public int getSch083DelMonth() {
        return sch083DelMonth__;
    }

    /**
     * <p>sch083DelMonth をセットします。
     * @param sch083DelMonth sch083DelMonth
     */
    public void setSch083DelMonth(int sch083DelMonth) {
        sch083DelMonth__ = sch083DelMonth;
    }

    /**
     * <p>sch083DelMonthLabel を取得します。
     * @return sch083DelMonthLabel
     */
    public List<LabelValueBean> getSch083DelMonthLabel() {
        return sch083DelMonthLabel__;
    }

    /**
     * <p>sch083DelMonthLabel をセットします。
     * @param sch083DelMonthLabel sch083DelMonthLabel
     */
    public void setSch083DelMonthLabel(List<LabelValueBean> sch083DelMonthLabel) {
        sch083DelMonthLabel__ = sch083DelMonthLabel;
    }

    /**
     * <p>sch083DelYear を取得します。
     * @return sch083DelYear
     */
    public int getSch083DelYear() {
        return sch083DelYear__;
    }

    /**
     * <p>sch083DelYear をセットします。
     * @param sch083DelYear sch083DelYear
     */
    public void setSch083DelYear(int sch083DelYear) {
        sch083DelYear__ = sch083DelYear;
    }

    /**
     * <p>sch083DelYearLabel を取得します。
     * @return sch083DelYearLabel
     */
    public List<LabelValueBean> getSch083DelYearLabel() {
        return sch083DelYearLabel__;
    }

    /**
     * <p>sch083DelYearLabel をセットします。
     * @param sch083DelYearLabel sch083DelYearLabel
     */
    public void setSch083DelYearLabel(List<LabelValueBean> sch083DelYearLabel) {
        sch083DelYearLabel__ = sch083DelYearLabel;
    }
}
