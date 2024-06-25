package jp.groupsession.v2.sch.sch088;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.sch100.Sch100Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch088Form extends Sch100Form {

    /** ショートメール通知区分 */
    private String sch088smlSendKbn__;
    /** ショートメール通知設定 */
    private String sch088Smail__;
    /** グループスケジュールショートメール通知設定 */
    private String sch088SmailGroup__;
    /** 出欠確認時ショートメール通知設定 */
    private String sch088SmailAttend__;
    /**
     * <p>sch088Smail を取得します。
     * @return sch088Smail
     */
    public String getSch088Smail() {
        return sch088Smail__;
    }
    /**
     * <p>sch088Smail をセットします。
     * @param sch088Smail sch088Smail
     */
    public void setSch088Smail(String sch088Smail) {
        sch088Smail__ = sch088Smail;
    }
    /**
     * <p>sch088SmailGroup を取得します。
     * @return sch088SmailGroup
     */
    public String getSch088SmailGroup() {
        return sch088SmailGroup__;
    }
    /**
     * <p>sch088SmailGroup をセットします。
     * @param sch088SmailGroup sch088SmailGroup
     */
    public void setSch088SmailGroup(String sch088SmailGroup) {
        sch088SmailGroup__ = sch088SmailGroup;
    }
    /**
     * <p>sch088smlSendKbn を取得します。
     * @return sch088smlSendKbn
     */
    public String getSch088smlSendKbn() {
        return sch088smlSendKbn__;
    }
    /**
     * <p>sch088smlSendKbn をセットします。
     * @param sch088smlSendKbn sch088smlSendKbn
     */
    public void setSch088smlSendKbn(String sch088smlSendKbn) {
        sch088smlSendKbn__ = sch088smlSendKbn;
    }
    /**
     * <p>sch088SmailAttend を取得します。
     * @return sch088SmailAttend
     */
    public String getSch088SmailAttend() {
        return sch088SmailAttend__;
    }
    /**
     * <p>sch088SmailAttend をセットします。
     * @param sch088SmailAttend sch088SmailAttend
     */
    public void setSch088SmailAttend(String sch088SmailAttend) {
        sch088SmailAttend__ = sch088SmailAttend;
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

        String schedule = gsMsg.getMessage("schedule.108");
        String msgKey = "error.input.sml.setting";
        String eprefix = "sch088.";
        
        // 数値範囲チェック
        String sendKbn = NullDefault.getString(sch088smlSendKbn__, "");
        if (!sendKbn.equals(String.valueOf(GSConstSchedule.SMAIL_SEND_KBN_USER))
                && !sendKbn.equals(String.valueOf(GSConstSchedule.SMAIL_SEND_KBN_ADMIN))) {
            // ショートメール通知
            String checkObj = gsMsg.getMessage("shortmail.notification");
            msg = new ActionMessage(msgKey, schedule, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        String smail = NullDefault.getString(sch088Smail__, "");
        if (!smail.equals(String.valueOf(GSConstSchedule.SML_NTF_KBN_YES))
                && !smail.equals(String.valueOf(GSConstSchedule.SML_NTF_KBN_NO))) {
            // 他ユーザ登録時
            String checkObj = gsMsg.getMessage("schedule.sch088.4");
            msg = new ActionMessage(msgKey, schedule, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        String smailGroup = NullDefault.getString(sch088SmailGroup__, "");
        if (!smailGroup.equals(String.valueOf(GSConstSchedule.SML_NTF_KBN_YES))
                && !smailGroup.equals(String.valueOf(GSConstSchedule.SML_NTF_KBN_NO))) {
            // 所属グループ登録時
            String checkObj = gsMsg.getMessage("schedule.sch088.5");
            msg = new ActionMessage(msgKey, schedule, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        String smailAttend  = NullDefault.getString(sch088SmailAttend__, "");
        if (!smailAttend.equals(String.valueOf(GSConstSchedule.SML_NTF_KBN_YES))
                && !smailAttend.equals(String.valueOf(GSConstSchedule.SML_NTF_KBN_NO))) {
            // 出欠確認依頼・完了時
            String checkObj = gsMsg.getMessage("schedule.sch088.3");
            msg = new ActionMessage(msgKey, schedule, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        return errors;
    }
 
}
