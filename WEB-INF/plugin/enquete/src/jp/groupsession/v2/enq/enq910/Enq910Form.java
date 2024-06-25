package jp.groupsession.v2.enq.enq910;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.enq900.Enq900Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 アンケート発信対象者設定画面のアクションフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq910Form extends Enq900Form implements ISelectorUseForm {

    /** 初期表示フラグ */
    private int enq910initFlg__ = 0;
    /** アンケート作成対象者区分 */
    private int enq910TaisyoKbn__ = 0;

    /** アンケート作成可能ユーザリスト */
    private String[] enq910MakeSenderList__ = null;

    /** グループSID */
    private String enq910GroupSid__ = null;

    /** アンケート作成可能ユーザ UI */
    private UserGroupSelector enq910MakeSenderListUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("enq.70", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainSelect(Select.builder()
                        .chainParameterName(
                                "enq910MakeSenderList")
                        )
                .chainGroupSelectionParamName("enq910GroupSid")
                .build();

    /**
     * <p>enq910initFlg を取得します。
     * @return enq910initFlg
     */
    public int getEnq910initFlg() {
        return enq910initFlg__;
    }
    /**
     * <p>enq910initFlg をセットします。
     * @param enq910initFlg enq910initFlg
     */
    public void setEnq910initFlg(int enq910initFlg) {
        enq910initFlg__ = enq910initFlg;
    }
    /**
     * <p>enq910TaisyoKbn を取得します。
     * @return enq910TaisyoKbn
     */
    public int getEnq910TaisyoKbn() {
        return enq910TaisyoKbn__;
    }
    /**
     * <p>enq910TaisyoKbn をセットします。
     * @param enq910TaisyoKbn enq910TaisyoKbn
     */
    public void setEnq910TaisyoKbn(int enq910TaisyoKbn) {
        enq910TaisyoKbn__ = enq910TaisyoKbn;
    }
    /**
     * <p>アンケート作成可能ユーザリスト を取得します。
     * @return enq910MakeSenderList
     */
    public String[] getEnq910MakeSenderList() {
        return enq910MakeSenderList__;
    }
    /**
     * <p>アンケート作成可能ユーザリスト をセットします。
     * @param enq910MakeSenderList アンケート作成可能ユーザリスト
     */
    public void setEnq910MakeSenderList(String[] enq910MakeSenderList) {
        enq910MakeSenderList__ = enq910MakeSenderList;
    }
    /**
     * <p>グループSID を取得します。
     * @return enq910GroupSid
     */
    public String getEnq910GroupSid() {
        return enq910GroupSid__;
    }
    /**
     * <p>グループSID をセットします。
     * @param enq910GroupSid グループSID
     */
    public void setEnq910GroupSid(String enq910GroupSid) {
        enq910GroupSid__ = enq910GroupSid;
    }
    /**
     * <p>enq910MakeSenderListUI を取得します。
     * @return enq910MakeSenderListUI
     */
    public UserGroupSelector getEnq910MakeSenderListUI() {
        return enq910MakeSenderListUI__;
    }
    /**
     * <p>enq910MakeSenderListUI をセットします。
     * @param enq910MakeSenderListUI enq910MakeSenderListUI
     */
    public void setEnq910MakeSenderListUI(
            UserGroupSelector enq910MakeSenderListUI) {
        enq910MakeSenderListUI__ = enq910MakeSenderListUI;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return エラー
     */
    public ActionErrors validateEnq910(RequestModel reqMdl) {

        ActionErrors errors = new ActionErrors();

        // アンケート発信対象者
        validateMakeSender(errors, reqMdl);

        return errors;
    }

    /**
     * <br>[機  能] アンケート発信対象者の入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエストモデル
     * @return 入力チェック結果 true:正常、false:不正
     */
    public boolean validateMakeSender(ActionErrors errors, RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage();

        if (enq910TaisyoKbn__ == 0) {
            if ((enq910MakeSenderList__ == null || enq910MakeSenderList__.length < 1)) {
                String msgKey = "error.select.required.text";
                ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage("enq.make.user"));
                StrutsUtil.addMessage(errors, msg, "enq910MakeSenderList." + msgKey);
                return false;
            }
        }
        return true;
    }

    /**
     * <br>[機  能] アンケート作成対象者更新時のオペレーションログ内容を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return [対象者区分]制限あり or 制限なし
     */
    public String getTargetLog(RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String ret = "[" + gsMsg.getMessage("enq.enq910.01") + "]";
        if (enq910TaisyoKbn__ == GSConstEnquete.CONF_TAISYO_LIMIT) {
            ret += gsMsg.getMessage("enq.enq910.02");
        } else {
            ret += gsMsg.getMessage("enq.enq910.03");
        }

        return ret;
    }
}
