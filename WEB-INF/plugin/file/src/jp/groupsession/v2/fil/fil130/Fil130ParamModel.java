package jp.groupsession.v2.fil.fil130;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.fil.fil120kn.Fil120knParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 個人設定 ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil130ParamModel extends Fil120knParamModel {

    /** ショートメール通知 */
    private String fil130SmailSend__ = null;

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

        //ショートメール通知
        if (fil130SmailSend__ == null) {
            GsMessage gsMsg = new GsMessage();
            String textSmailSend = gsMsg.getMessage(req, "shortmail.notification");
            msg = new ActionMessage("error.select.required.text", textSmailSend);
            StrutsUtil.addMessage(errors, msg, "fil130SmailSend");
        }

        return errors;
    }

    /**
     * <p>fil130SmailSend を取得します。
     * @return fil130SmailSend
     */
    public String getFil130SmailSend() {
        return fil130SmailSend__;
    }

    /**
     * <p>fil130SmailSend をセットします。
     * @param fil130SmailSend fil130SmailSend
     */
    public void setFil130SmailSend(String fil130SmailSend) {
        fil130SmailSend__ = fil130SmailSend;
    }

}