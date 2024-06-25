package jp.groupsession.v2.cir.cir220;

import java.util.ArrayList;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir100.Cir100Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 回覧板登録制限設定画面のアクションフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir220Form extends Cir100Form {

    /** 初期表示フラグ */
    private int cir220initFlg__ = 0;
    /** 回覧板作成対象者区分 */
    private int cir220TaisyoKbn__ = 0;

    /** 回覧板作成可能ユーザリスト */
    private String[] cir220MakeSenderList__ = null;

    /** グループSID */
    private String cir220GroupSid__ = null;

    /** 回覧板送信対象者リスト（左） */
    private ArrayList<LabelValueBean> cir220AddSenderLabel__ = null;

    /** 回覧板送信対象者 選択UI*/
    private UserGroupSelector cir220MakeSenderSelector__
        = UserGroupSelector.builder()
            .chainGrpType(EnumGroupSelectType.GROUPONLY)
            .chainType(EnumSelectType.USERGROUP)
            .chainSelect(Select.builder()
                            .chainParameterName("cir220MakeSenderList"))
            .chainGroupSelectionParamName("cir220GroupSid")
            .build();

    /**
     * <p>cir220initFlg を取得します。
     * @return cir220initFlg
     */
    public int getCir220initFlg() {
        return cir220initFlg__;
    }
    /**
     * <p>cir220initFlg をセットします。
     * @param cir220initFlg cir220initFlg
     */
    public void setCir220initFlg(int cir220initFlg) {
        cir220initFlg__ = cir220initFlg;
    }
    /**
     * <p>cir220TaisyoKbn を取得します。
     * @return cir220TaisyoKbn
     */
    public int getCir220TaisyoKbn() {
        return cir220TaisyoKbn__;
    }
    /**
     * <p>cir220TaisyoKbn をセットします。
     * @param cir220TaisyoKbn cir220TaisyoKbn
     */
    public void setCir220TaisyoKbn(int cir220TaisyoKbn) {
        cir220TaisyoKbn__ = cir220TaisyoKbn;
    }
    /**
     * <p>回覧板作成可能ユーザリスト を取得します。
     * @return cir220MakeSenderList
     */
    public String[] getCir220MakeSenderList() {
        return cir220MakeSenderList__;
    }
    /**
     * <p>回覧板作成可能ユーザリスト をセットします。
     * @param cir220MakeSenderList 回覧板作成可能ユーザリスト
     */
    public void setCir220MakeSenderList(String[] cir220MakeSenderList) {
        cir220MakeSenderList__ = cir220MakeSenderList;
    }
    /**
     * <p>グループSID を取得します。
     * @return cir220GroupSid
     */
    public String getCir220GroupSid() {
        return cir220GroupSid__;
    }
    /**
     * <p>グループSID をセットします。
     * @param cir220GroupSid グループSID
     */
    public void setCir220GroupSid(String cir220GroupSid) {
        cir220GroupSid__ = cir220GroupSid;
    }
    /**
     * <p>回覧板送信対象者リスト（左） を取得します。
     * @return 回覧板送信対象者リスト（左）
     */
    public ArrayList<LabelValueBean> getCir220AddSenderLabel() {
        return cir220AddSenderLabel__;
    }
    /**
     * <p>回覧板送信対象者リスト（左） をセットします。
     * @param cir220AddSenderLabel 回覧板送信対象者リスト（左）
     */
    public void setCir220AddSenderLabel(
            ArrayList<LabelValueBean> cir220AddSenderLabel) {
        cir220AddSenderLabel__ = cir220AddSenderLabel;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return エラー
     */
    public ActionErrors validateCir220(RequestModel reqMdl) {

        ActionErrors errors = new ActionErrors();

        // 回覧板送信対象者
        validateMakeSender(errors, reqMdl);

        return errors;
    }

    /**
     * <br>[機  能] 回覧板送信対象者の入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl リクエストモデル
     * @return 入力チェック結果 true:正常、false:不正
     */
    public boolean validateMakeSender(ActionErrors errors, RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage();

        if (cir220TaisyoKbn__ == GSConstCircular.CAF_AREST_KBN_SELECT) {
            if ((cir220MakeSenderList__ == null || cir220MakeSenderList__.length < 1)) {
                String msgKey = "error.select.required.text";
                ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage("cir.cir220.6"));
                StrutsUtil.addMessage(errors, msg, "cir220MakeSenderList." + msgKey);
                return false;
            }
        }
        return true;
    }

    /**
     * <br>[機  能] 回覧板作成対象者更新時のオペレーションログ内容を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return [対象者区分]制限あり or 制限なし
     */
    public String getTargetLog(RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String ret = "[" + gsMsg.getMessage("cir.cir220.6") + "]";
        if (cir220TaisyoKbn__ == GSConstCircular.CAF_AREST_KBN_SELECT) {
            ret += gsMsg.getMessage("wml.32");
        } else {
            ret += gsMsg.getMessage("wml.31");
        }

        return ret;
    }
    /**
     * <p>cir220MakeSenderSelector を取得します。
     * @return cir220MakeSenderSelector
     * @see jp.groupsession.v2.cir.cir220.Cir220Form#cir220MakeSenderSelector__
     */
    public UserGroupSelector getCir220MakeSenderSelector() {
        return cir220MakeSenderSelector__;
    }
    /**
     * <p>cir220MakeSenderSelector をセットします。
     * @param cir220MakeSenderSelector cir220MakeSenderSelector
     * @see jp.groupsession.v2.cir.cir220.Cir220Form#cir220MakeSenderSelector__
     */
    public void setCir220MakeSenderSelector(UserGroupSelector cir220MakeSenderSelector) {
        cir220MakeSenderSelector__ = cir220MakeSenderSelector;
    }
}
