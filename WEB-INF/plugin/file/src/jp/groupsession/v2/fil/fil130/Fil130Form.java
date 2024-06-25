package jp.groupsession.v2.fil.fil130;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil120kn.Fil120knForm;
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
public class Fil130Form extends Fil120knForm {

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
    
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return アクションエラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        
        String filekanri = gsMsg.getMessage("cmn.filekanri");
        String msgKey = "error.input.sml.setting";
        String eprefix = "fil130.";
        
        // 数値範囲チェック
        String smailSend = NullDefault.getString(fil130SmailSend__, "");
        if (!smailSend.equals(String.valueOf(GSConstFile.SMAIL_SEND_ON))
                && !smailSend.equals(String.valueOf(GSConstFile.SMAIL_SEND_OFF))) {
            // フォルダ内更新時
            String checkObj = gsMsg.getMessage("fil.fil130.2");
            msg = new ActionMessage(msgKey, filekanri, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        
        return errors;
    }

}