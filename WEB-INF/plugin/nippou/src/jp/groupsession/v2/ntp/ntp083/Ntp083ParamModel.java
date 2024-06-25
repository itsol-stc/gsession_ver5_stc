package jp.groupsession.v2.ntp.ntp083;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.ntp.ntp080.Ntp080ParamModel;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 手動データ削除設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp083ParamModel extends Ntp080ParamModel {

    /** 経過年 */
    private int ntp083DelYear__ = 3;
    /** 経過月 */
    private int ntp083DelMonth__ = -1;
    /** 経過年ラベルの選択値 */
    public static final String[] YEAR_VALUE
        = new String[] {"0", "1", "2", "3", "4", "5", "10"};
    /** 経過月ラベルの選択値 */
    public static final String[] MONTH_VALUE
        = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    /** 経過年ラベル */
    private List < LabelValueBean > ntp083DelYearLabel__ = null;
    /** 経過月ラベル */
    private List < LabelValueBean > ntp083DelMonthLabel__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Ntp083ParamModel() {
        //年ラベル作成
        ntp083DelYearLabel__ = new ArrayList<LabelValueBean>();
        for (String label : YEAR_VALUE) {
            ntp083DelYearLabel__.add(new LabelValueBean(label + "年", label));
        }
        //月ラベル作成
        ntp083DelMonthLabel__ = new ArrayList<LabelValueBean>();
        for (String label : MONTH_VALUE) {
            ntp083DelMonthLabel__.add(new LabelValueBean(label + "ヶ月", label));
        }
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(ActionMapping map, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //経過年
        boolean yFlg = false;
        for (String sy : YEAR_VALUE) {
            int iy = Integer.parseInt(sy);
            if (ntp083DelYear__ == iy) {
                yFlg = true;
                break;
            }
        }
        if (yFlg == false) {
            String prefix = "ntp083DelYear";
            msg = new ActionMessage("error.input.notvalidate.data", "経過年");
            errors.add(prefix + "error.input.notvalidate.data", msg);
        }


        //経過月
        boolean mFlg = false;
        for (String sm : MONTH_VALUE) {
            int im = Integer.parseInt(sm);
            if (ntp083DelMonth__ == im) {
                mFlg = true;
                break;
            }
        }

        if (mFlg == false) {
            String prefix = "ntp083DelMonth";
            msg = new ActionMessage("error.input.notvalidate.data", "経過月");
            errors.add(prefix + "error.input.notvalidate.data", msg);
        }

        //経過年、月
        if (yFlg && mFlg) {
            //
            if (ntp083DelYear__ == 0 && ntp083DelMonth__ == 0) {
                //
                String prefix = "ntp083DelYM";
                msg = new ActionMessage("error.input.range0over.data", "経過年月", "1ヶ月");
                errors.add(prefix + "error.input.range0over.data", msg);
            }
        }
        return errors;
    }

    /**
     * <p>ntp083DelMonth を取得します。
     * @return ntp083DelMonth
     */
    public int getNtp083DelMonth() {
        return ntp083DelMonth__;
    }

    /**
     * <p>ntp083DelMonth をセットします。
     * @param ntp083DelMonth ntp083DelMonth
     */
    public void setNtp083DelMonth(int ntp083DelMonth) {
        ntp083DelMonth__ = ntp083DelMonth;
    }

    /**
     * <p>ntp083DelMonthLabel を取得します。
     * @return ntp083DelMonthLabel
     */
    public List<LabelValueBean> getNtp083DelMonthLabel() {
        return ntp083DelMonthLabel__;
    }

    /**
     * <p>ntp083DelMonthLabel をセットします。
     * @param ntp083DelMonthLabel ntp083DelMonthLabel
     */
    public void setNtp083DelMonthLabel(List<LabelValueBean> ntp083DelMonthLabel) {
        ntp083DelMonthLabel__ = ntp083DelMonthLabel;
    }

    /**
     * <p>ntp083DelYear を取得します。
     * @return ntp083DelYear
     */
    public int getNtp083DelYear() {
        return ntp083DelYear__;
    }

    /**
     * <p>ntp083DelYear をセットします。
     * @param ntp083DelYear ntp083DelYear
     */
    public void setNtp083DelYear(int ntp083DelYear) {
        ntp083DelYear__ = ntp083DelYear;
    }

    /**
     * <p>ntp083DelYearLabel を取得します。
     * @return ntp083DelYearLabel
     */
    public List<LabelValueBean> getNtp083DelYearLabel() {
        return ntp083DelYearLabel__;
    }

    /**
     * <p>ntp083DelYearLabel をセットします。
     * @param ntp083DelYearLabel ntp083DelYearLabel
     */
    public void setNtp083DelYearLabel(List<LabelValueBean> ntp083DelYearLabel) {
        ntp083DelYearLabel__ = ntp083DelYearLabel;
    }
}
