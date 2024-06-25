package jp.groupsession.v2.rsv.rsv290;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.rsv.rsv040.Rsv040Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 施設予約 管理者設定 日間表示時間帯デフォルト設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv290Form extends Rsv040Form {

    /** 時間 区分 */
    private int rsv290DateKbn__ = 0;
    /** 時間コンボ 選択 */
    private String rsv290SelectedFromSid__ = "";
    /** 時間コンボ 選択 */
    private String rsv290SelectedToSid__ = "";
    /** 初期表示フラグ */
    private int rsv290initDspFlg__ = 0;

    /** 時間コンボリスト */
    private ArrayList<LabelValueBean> rsv290HourLabelList__ = null;

    /**
     * <br>[機  能] 入力チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return errors
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        String eprefix = "reserve";

        //時間帯設定整合性チェック
        if (Integer.parseInt(rsv290SelectedFromSid__) > Integer.parseInt(rsv290SelectedToSid__)) {
            GsMessage gsMsg = new GsMessage();

            msg = new ActionMessage("error.input.comp.text",
                    gsMsg.getMessage(req, "cmn.show.timezone.days.setting"),
                    gsMsg.getMessage(req, "cmn.start.or.end"));
            StrutsUtil.addMessage(
                    errors, msg, eprefix + "rsv290SelectedFromSid__");
        }
        return errors;
    }
    /**
     * <p>rsv290DateKbn を取得します。
     * @return rsv290DateKbn
     */
    public int getRsv290DateKbn() {
        return rsv290DateKbn__;
    }
    /**
     * <p>rsv290DateKbn をセットします。
     * @param rsv290DateKbn rsv290DateKbn
     */
    public void setRsv290DateKbn(int rsv290DateKbn) {
        rsv290DateKbn__ = rsv290DateKbn;
    }
    /**
     * <p>rsv290initDspFlg を取得します。
     * @return rsv290initDspFlg
     */
    public int getRsv290initDspFlg() {
        return rsv290initDspFlg__;
    }
    /**
     * <p>rsv290initDspFlg をセットします。
     * @param rsv290initDspFlg rsv290initDspFlg
     */
    public void setRsv290initDspFlg(int rsv290initDspFlg) {
        rsv290initDspFlg__ = rsv290initDspFlg;
    }
    /**
     * <p>rsv290SelectedFromSid を取得します。
     * @return rsv290SelectedFromSid
     */
    public String getRsv290SelectedFromSid() {
        return rsv290SelectedFromSid__;
    }
    /**
     * <p>rsv290SelectedFromSid をセットします。
     * @param rsv290SelectedFromSid rsv290SelectedFromSid
     */
    public void setRsv290SelectedFromSid(String rsv290SelectedFromSid) {
        rsv290SelectedFromSid__ = rsv290SelectedFromSid;
    }
    /**
     * <p>rsv290SelectedToSid を取得します。
     * @return rsv290SelectedToSid
     */
    public String getRsv290SelectedToSid() {
        return rsv290SelectedToSid__;
    }
    /**
     * <p>rsv290SelectedToSid をセットします。
     * @param rsv290SelectedToSid rsv290SelectedToSid
     */
    public void setRsv290SelectedToSid(String rsv290SelectedToSid) {
        rsv290SelectedToSid__ = rsv290SelectedToSid;
    }
    /**
     * <p>rsv290HourLabelList を取得します。
     * @return rsv290HourLabelList
     */
    public ArrayList<LabelValueBean> getRsv290HourLabelList() {
        return rsv290HourLabelList__;
    }
    /**
     * <p>rsv290HourLabelList をセットします。
     * @param rsv290HourLabelList rsv290HourLabelList
     */
    public void setRsv290HourLabelList(ArrayList<LabelValueBean> rsv290HourLabelList) {
        rsv290HourLabelList__ = rsv290HourLabelList;
    }
}