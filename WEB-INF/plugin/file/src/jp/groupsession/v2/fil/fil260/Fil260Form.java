package jp.groupsession.v2.fil.fil260;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil200.Fil200Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil260Form extends Fil200Form {
    /** 初期表示フラグ */
    private int fil260initFlg__ = 0;
    /** ショートメール通知区分 */
    private int fil260smlSendKbn__ = GSConstFile.FAC_SMAIL_SEND_KBN_USER;
    /** ショートメール通知 */
    private int fil260smlSend__ = GSConstFile.FAC_SMAIL_SEND_YES;
    /**
     * <p>fil260initFlg を取得します。
     * @return fil260initFlg
     */
    public int getFil260initFlg() {
        return fil260initFlg__;
    }
    /**
     * <p>fil260initFlg をセットします。
     * @param fil260initFlg fil260initFlg
     */
    public void setFil260initFlg(int fil260initFlg) {
        fil260initFlg__ = fil260initFlg;
    }
    /**
     * <p>fil260smlSendKbn を取得します。
     * @return fil260smlSendKbn
     */
    public int getFil260smlSendKbn() {
        return fil260smlSendKbn__;
    }
    /**
     * <p>fil260smlSendKbn をセットします。
     * @param fil260smlSendKbn fil260smlSendKbn
     */
    public void setFil260smlSendKbn(int fil260smlSendKbn) {
        fil260smlSendKbn__ = fil260smlSendKbn;
    }
    /**
     * <p>fil260smlSend を取得します。
     * @return fil260smlSend
     */
    public int getFil260smlSend() {
        return fil260smlSend__;
    }
    /**
     * <p>fil260smlSend をセットします。
     * @param fil260smlSend fil260smlSend
     */
    public void setFil260smlSend(int fil260smlSend) {
        fil260smlSend__ = fil260smlSend;
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
        String eprefix = "fil260.";
        
         // 数値範囲チェック
        if (fil260smlSendKbn__ != GSConstFile.FAC_SMAIL_SEND_KBN_USER
                && fil260smlSendKbn__ != GSConstFile.FAC_SMAIL_SEND_KBN_ADMIN) {
            // ショートメール通知設定
            String checkObj = gsMsg.getMessage("shortmail.notification");
            msg = new ActionMessage(msgKey, filekanri, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        if (fil260smlSend__ != GSConstFile.SMAIL_SEND_ON
                && fil260smlSend__ !=  GSConstFile.SMAIL_SEND_OFF) {
            // フォルダ内更新時
            String checkObj = gsMsg.getMessage("fil.fil260.1");
            msg = new ActionMessage(msgKey, filekanri, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        
        return errors;
    }
}