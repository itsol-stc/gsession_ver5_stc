package jp.groupsession.v2.cir.cir250;

import java.util.ArrayList;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir010.Cir010Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 ショートメール設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir250Form extends Cir010Form {

    /** 選択アカウントSID */
    private String cir250selectAccount__ = null;
    /** アカウントリスト */
    private ArrayList <LabelValueBean> cir250accountList__ = null;

    /** ショートメール通知 受信・完了*/
    private int cir250smlNtf__ = GSConstCircular.SMAIL_TSUUCHI;
    /** ショートメール通知 メモ・確認時添付*/
    private int cir250smlMemo__ = GSConstCircular.SMAIL_TSUUCHI;
    /** ショートメール通知 編集*/
    private int cir250smlEdt__ = GSConstCircular.SMAIL_TSUUCHI;
    
    /**
     * <p>cir250accountList を取得します。
     * @return cir250accountList
     * @see jp.groupsession.v2.cir.cir250.Cir250Form#cir250accountList__
     */
    public ArrayList<LabelValueBean> getCir250accountList() {
        return cir250accountList__;
    }
    /**
     * <p>cir250accountList をセットします。
     * @param cir250accountList cir250accountList
     * @see jp.groupsession.v2.cir.cir250.Cir250Form#cir250accountList__
     */
    public void setCir250accountList(ArrayList<LabelValueBean> cir250accountList) {
        cir250accountList__ = cir250accountList;
    }
    /**
     * <p>cir250selectAccount を取得します。
     * @return cir250selectAccount
     * @see jp.groupsession.v2.cir.cir250.Cir250Form#cir250selectAccount__
     */
    public String getCir250selectAccount() {
        return cir250selectAccount__;
    }
    /**
     * <p>cir250selectAccount をセットします。
     * @param cir250selectAccount cir250selectAccount
     * @see jp.groupsession.v2.cir.cir250.Cir250Form#cir250selectAccount__
     */
    public void setCir250selectAccount(String cir250selectAccount) {
        cir250selectAccount__ = cir250selectAccount;
    }
    
    /**
     * <p>cir250smlNtf を取得します。
     * @return cir250smlNtf
     * @see jp.groupsession.v2.cir.cir250.Cir250Form#cir250smlNtf__
     */
    public int getCir250smlNtf() {
        return cir250smlNtf__;
    }
    /**
     * <p>cir250smlNtf をセットします。
     * @param cir250smlNtf cir250smlNtf
     * @see jp.groupsession.v2.cir.cir250.Cir250Form#cir250smlNtf__
     */
    public void setCir250smlNtf(int cir250smlNtf) {
        cir250smlNtf__ = cir250smlNtf;
    }
    /**
     * <p>cir250smlMemo を取得します。
     * @return cir250smlMemo
     * @see jp.groupsession.v2.cir.cir250.Cir250Form#cir250smlMemo__
     */
    public int getCir250smlMemo() {
        return cir250smlMemo__;
    }
    /**
     * <p>cir250smlMemo をセットします。
     * @param cir250smlMemo cir250smlMemo
     * @see jp.groupsession.v2.cir.cir250.Cir250Form#cir250smlMemo__
     */
    public void setCir250smlMemo(int cir250smlMemo) {
        cir250smlMemo__ = cir250smlMemo;
    }
    /**
     * <p>cir250smlEdt を取得します。
     * @return cir250smlEdt
     * @see jp.groupsession.v2.cir.cir250.Cir250Form#cir250smlEdt__
     */
    public int getCir250smlEdt() {
        return cir250smlEdt__;
    }
    /**
     * <p>cir250smlEdt をセットします。
     * @param cir250smlEdt cir250smlEdt
     * @see jp.groupsession.v2.cir.cir250.Cir250Form#cir250smlEdt__
     */
    public void setCir250smlEdt(int cir250smlEdt) {
        cir250smlEdt__ = cir250smlEdt;
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
        String eprefix = "cir250.";

        // 受信時・確認完了時判定  （入力値が0か1以外ならエラー）
        if (cir250smlNtf__ != GSConstCircular.CAF_SML_NTF_KBN_YES
                && cir250smlNtf__ != GSConstCircular.CAF_SML_NTF_KBN_NO) {
            String checkObj = gsMsg.getMessage("cir.cir160.2");
            msg = new ActionMessage(msgKey, circular, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        // メモ・添付更新時判定 （入力値が0か1以外ならエラー）
        if (cir250smlMemo__ != GSConstCircular.CAF_SML_NTF_KBN_YES
                && cir250smlMemo__ != GSConstCircular.CAF_SML_NTF_KBN_NO) {
            String checkObj = gsMsg.getMessage("cir.cir160.4");
            msg = new ActionMessage(msgKey, circular, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        // 回覧板編集時判定 （入力値が0か1以外ならエラー）
        if (cir250smlEdt__ != GSConstCircular.CAF_SML_NTF_KBN_YES
                && cir250smlEdt__ != GSConstCircular.CAF_SML_NTF_KBN_NO) {
            String checkObj = gsMsg.getMessage("cir.cir160.6");
            msg = new ActionMessage(msgKey, circular, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        return errors;
    }
}
