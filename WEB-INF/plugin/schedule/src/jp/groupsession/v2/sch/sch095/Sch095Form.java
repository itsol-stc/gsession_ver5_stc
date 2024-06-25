package jp.groupsession.v2.sch.sch095;

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
public class Sch095Form extends Sch100Form {

    /** ショートメール通知設定 */
    private String sch095Smail__;
    /** グループスケジュールショートメール通知設定 */
    private String sch095SmailGroup__;
    /** 出欠確認時ショートメール通知設定 */
    private String sch095SmailAttend__;
    /**
     * <p>sch095Smail を取得します。
     * @return sch095Smail
     */
    public String getSch095Smail() {
        return sch095Smail__;
    }
    /**
     * <p>sch095Smail をセットします。
     * @param sch095Smail sch095Smail
     */
    public void setSch095Smail(String sch095Smail) {
        sch095Smail__ = sch095Smail;
    }
    /**
     * <p>sch095SmailGroup を取得します。
     * @return sch095SmailGroup
     */
    public String getSch095SmailGroup() {
        return sch095SmailGroup__;
    }
    /**
     * <p>sch095SmailGroup をセットします。
     * @param sch095SmailGroup sch095SmailGroup
     */
    public void setSch095SmailGroup(String sch095SmailGroup) {
        sch095SmailGroup__ = sch095SmailGroup;
    }
    /**
     * <p>sch095SmailAttend を取得します。
     * @return sch095SmailAttend
     */
    public String getSch095SmailAttend() {
        return sch095SmailAttend__;
    }
    /**
     * <p>sch095SmailAttend をセットします。
     * @param sch095SmailAttend sch095SmailAttend
     */
    public void setSch095SmailAttend(String sch095SmailAttend) {
        sch095SmailAttend__ = sch095SmailAttend;
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
        String eprefix = "sch095.";

        String smail = NullDefault.getString(sch095Smail__, "");
        if (!smail.equals(String.valueOf(GSConstSchedule.SML_NTF_KBN_YES))
                && !smail.equals(String.valueOf(GSConstSchedule.SML_NTF_KBN_NO))) {
            // 他ユーザ登録時
            String checkObj = gsMsg.getMessage("schedule.sch095.8");
            msg = new ActionMessage(msgKey, schedule, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        String smailGroup = NullDefault.getString(sch095SmailGroup__, "");
        if (!smailGroup.equals(String.valueOf(GSConstSchedule.SML_NTF_KBN_YES))
                && !smailGroup.equals(String.valueOf(GSConstSchedule.SML_NTF_KBN_NO))) {
            // 所属グループ登録時
            String checkObj = gsMsg.getMessage("schedule.sch095.9");
            msg = new ActionMessage(msgKey, schedule, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        String smailAttend  = NullDefault.getString(sch095SmailAttend__, "");
        if (!smailAttend.equals(String.valueOf(GSConstSchedule.SML_NTF_KBN_YES))
                && !smailAttend.equals(String.valueOf(GSConstSchedule.SML_NTF_KBN_NO))) {
            // 出欠確認依頼・完了時
            String checkObj = gsMsg.getMessage("schedule.sch095.7");
            msg = new ActionMessage(msgKey, schedule, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        return errors;
    }

}
