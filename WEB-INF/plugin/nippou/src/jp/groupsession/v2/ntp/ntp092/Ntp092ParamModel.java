package jp.groupsession.v2.ntp.ntp092;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import jp.groupsession.v2.ntp.ntp090.Ntp090ParamModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 日間表示時間帯設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp092ParamModel extends Ntp090ParamModel {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp092ParamModel.class);
    /** ラベル 時 */
    private List < LabelValueBean > ntp092HourLabel__ = null;
    /** 開始 時 */
    private int ntp092FrH__ = -1;
    /** 終了 時 */
    private int ntp092ToH__ = -1;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Ntp092ParamModel() {
        //ラベル(時)
        ntp092HourLabel__ = new ArrayList<LabelValueBean>();
        for (int i = 0; i < 24; i++) {
            ntp092HourLabel__.add(new LabelValueBean(i + "時", Integer.toString(i)));
        }
    }

    /**
     * <p>ntp092FrH を取得します。
     * @return ntp092FrH
     */
    public int getNtp092FrH() {
        return ntp092FrH__;
    }

    /**
     * <p>ntp092FrH をセットします。
     * @param ntp092FrH ntp092FrH
     */
    public void setNtp092FrH(int ntp092FrH) {
        ntp092FrH__ = ntp092FrH;
    }

    /**
     * <p>ntp092HourLabel を取得します。
     * @return ntp092HourLabel
     */
    public List<LabelValueBean> getNtp092HourLabel() {
        return ntp092HourLabel__;
    }

    /**
     * <p>ntp092HourLabel をセットします。
     * @param ntp092HourLabel ntp092HourLabel
     */
    public void setNtp092HourLabel(List<LabelValueBean> ntp092HourLabel) {
        ntp092HourLabel__ = ntp092HourLabel;
    }

    /**
     * <p>ntp092ToH を取得します。
     * @return ntp092ToH
     */
    public int getNtp092ToH() {
        return ntp092ToH__;
    }

    /**
     * <p>ntp092ToH をセットします。
     * @param ntp092ToH ntp092ToH
     */
    public void setNtp092ToH(int ntp092ToH) {
        ntp092ToH__ = ntp092ToH;
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
        log__.debug("ntp092FrH__==>" + ntp092FrH__);
        log__.debug("ntp092ToH__==>" + ntp092ToH__);
        if (ntp092FrH__ > ntp092ToH__) {
            //開始時刻 < 終了時刻の場合
            msg = new ActionMessage("error.input.comp.text", "日間表示時間帯", "開始時刻 < 終了時刻");
            errors.add("error.input.comp.text", msg);
        }
        return errors;
    }
}
