package jp.groupsession.v2.rsv.rsv320;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.rsv040.Rsv040Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 管理者設定 ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv320Form extends Rsv040Form {

    /** ショートメール通知区分 0:ユーザが設定する 1:管理者が設定する */
    private int rsv320SmailSendKbn__ = 0;
    /** 通知設定 0=通知する 1=通知しない*/
    private int rsv320SmailSend__ = 0;
    /**
     * <p>rsv320SmailSendKbn を取得します。
     * @return rsv320SmailSendKbn
     */
    public int getRsv320SmailSendKbn() {
        return rsv320SmailSendKbn__;
    }
    /**
     * <p>rsv320SmailSendKbn をセットします。
     * @param rsv320SmailSendKbn rsv320SmailSendKbn
     */
    public void setRsv320SmailSendKbn(int rsv320SmailSendKbn) {
        rsv320SmailSendKbn__ = rsv320SmailSendKbn;
    }
    /**
     * <p>rsv320SmailSend を取得します。
     * @return rsv320SmailSend
     */
    public int getRsv320SmailSend() {
        return rsv320SmailSend__;
    }
    /**
     * <p>rsv320SmailSend をセットします。
     * @param rsv320SmailSend rsv320SmailSend
     */
    public void setRsv320SmailSend(int rsv320SmailSend) {
        rsv320SmailSend__ = rsv320SmailSend;
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
        
        String reserve = gsMsg.getMessage("cmn.reserve");
        String msgKey = "error.input.sml.setting";
        String eprefix = "rsv320.";
        
        // 数値範囲チェック
        if (rsv320SmailSendKbn__ != GSConstReserve.RAC_SMAIL_SEND_KBN_USER
                && rsv320SmailSendKbn__ !=  GSConstReserve.RAC_SMAIL_SEND_KBN_ADMIN) {
            // ショートメール通知
            String checkObj = gsMsg.getMessage("shortmail.notification");
            msg = new ActionMessage(msgKey, reserve, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        if (rsv320SmailSend__ != GSConstReserve.RAC_SMAIL_SEND_YES
                && rsv320SmailSend__ != GSConstReserve.RAC_SMAIL_SEND_NO) {
            // 他ユーザ変更時
            String checkObj = gsMsg.getMessage("reserve.rsv320.1");
            msg = new ActionMessage(msgKey, reserve, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        
        return errors;
    }
    
}