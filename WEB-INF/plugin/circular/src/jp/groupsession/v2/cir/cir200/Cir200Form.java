package jp.groupsession.v2.cir.cir200;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir100.Cir100Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 管理者設定 ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir200Form extends Cir100Form {
    /** 初期表示フラグ */
    private int cir200InitFlg__ = 0;
    /** ショートメール通知区分 */
    private int cir200SmlSendKbn__ = GSConstCircular.CAF_SML_NTF_USER;
    /** ショートメール通知 受信・完了*/
    private int cir200SmlSend__ = GSConstCircular.CAF_SML_NTF_KBN_YES;
    /** ショートメール通知 メモ・確認時添付*/
    private int cir200SmlMemo__ = GSConstCircular.CAF_SML_NTF_KBN_YES;
    /** ショートメール通知 編集*/
    private int cir200SmlEdt__ = GSConstCircular.CAF_SML_NTF_KBN_YES;
    /**
     * <p>cir200InitFlg を取得します。
     * @return cir200InitFlg
     */
    public int getCir200InitFlg() {
        return cir200InitFlg__;
    }
    /**
     * <p>cir200InitFlg をセットします。
     * @param cir200InitFlg cir200InitFlg
     */
    public void setCir200InitFlg(int cir200InitFlg) {
        cir200InitFlg__ = cir200InitFlg;
    }
    /**
     * <p>cir200SmlSendKbn を取得します。
     * @return cir200SmlSendKbn
     */
    public int getCir200SmlSendKbn() {
        return cir200SmlSendKbn__;
    }
    /**
     * <p>cir200SmlSendKbn をセットします。
     * @param cir200SmlSendKbn cir200SmlSendKbn
     */
    public void setCir200SmlSendKbn(int cir200SmlSendKbn) {
        cir200SmlSendKbn__ = cir200SmlSendKbn;
    }
    /**
     * <p>cir200SmlSend を取得します。
     * @return cir200SmlSend
     */
    public int getCir200SmlSend() {
        return cir200SmlSend__;
    }
    /**
     * <p>cir200SmlSend をセットします。
     * @param cir200SmlSend cir200SmlSend
     */
    public void setCir200SmlSend(int cir200SmlSend) {
        cir200SmlSend__ = cir200SmlSend;
    }
    /**
     * <p>cir200SmlMemo を取得します。
     * @return cir200SmlMemo
     * @see jp.groupsession.v2.cir.cir200.Cir200Form#cir200SmlMemo__
     */
    public int getCir200SmlMemo() {
        return cir200SmlMemo__;
    }
    /**
     * <p>cir200SmlMemo をセットします。
     * @param cir200SmlMemo cir200SmlMemo
     * @see jp.groupsession.v2.cir.cir200.Cir200Form#cir200SmlMemo__
     */
    public void setCir200SmlMemo(int cir200SmlMemo) {
        cir200SmlMemo__ = cir200SmlMemo;
    }
    /**
     * <p>cir200SmlEdt を取得します。
     * @return cir200SmlEdt
     * @see jp.groupsession.v2.cir.cir200.Cir200Form#cir200SmlEdt__
     */
    public int getCir200SmlEdt() {
        return cir200SmlEdt__;
    }
    /**
     * <p>cir200SmlEdt をセットします。
     * @param cir200SmlEdt cir200SmlEdt
     * @see jp.groupsession.v2.cir.cir200.Cir200Form#cir200SmlEdt__
     */
    public void setCir200SmlEdt(int cir200SmlEdt) {
        cir200SmlEdt__ = cir200SmlEdt;
    }
    
    /**
     *
     * <br>[機  能] 入力チェック処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return エラーメッセージ
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        
        String circular = gsMsg.getMessage("cir.5");
        String msgKey = "error.input.sml.setting";
        
        //ショートメール通知
        if (cir200SmlSendKbn__ != GSConstCircular.CAF_SML_NTF_ADMIN
                && cir200SmlSendKbn__ != GSConstCircular.CAF_SML_NTF_USER) {
            String fieldfix = "cir160smail.kbn.";
            String checkObj = gsMsg.getMessage("shortmail.notification");
            msg = new ActionMessage(msgKey, circular, checkObj);
            StrutsUtil.addMessage(errors, msg, fieldfix);
            
        }
        // 受信時・確認完了時判定 （入力値が0か1以外ならエラー）
        if (cir200SmlSend__ != GSConstCircular.CAF_SML_NTF_KBN_YES
                && cir200SmlSend__ != GSConstCircular.CAF_SML_NTF_KBN_NO) {
            String fieldfix = "cir160smail.ntf.";
            String checkObj = gsMsg.getMessage("cir.cir160.2");
            msg = new ActionMessage(msgKey, circular, checkObj);
            StrutsUtil.addMessage(errors, msg, fieldfix);
        }
        // メモ・添付更新時判定 （入力値が0か1以外ならエラー）
        if (cir200SmlMemo__ != GSConstCircular.CAF_SML_NTF_KBN_YES
                && cir200SmlMemo__ != GSConstCircular.CAF_SML_NTF_KBN_NO) {
            String fieldfix = "cir160smail.memo.";
            String checkObj = gsMsg.getMessage("cir.cir160.4");
            msg = new ActionMessage(msgKey, circular, checkObj);
            StrutsUtil.addMessage(errors, msg, fieldfix);
        }
        // 回覧板編集時判定 （入力値が0か1以外ならエラー）
        if (cir200SmlEdt__ != GSConstCircular.CAF_SML_NTF_KBN_YES
                && cir200SmlEdt__ != GSConstCircular.CAF_SML_NTF_KBN_NO) {
            String fieldfix = "cir160smail.edit.";
            String checkObj = gsMsg.getMessage("cir.cir160.6");
            msg = new ActionMessage(msgKey, circular, checkObj);
            StrutsUtil.addMessage(errors, msg, fieldfix);
        }
        return errors;
    }
}