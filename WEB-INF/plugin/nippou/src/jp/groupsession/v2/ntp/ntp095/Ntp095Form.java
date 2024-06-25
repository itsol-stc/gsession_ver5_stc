package jp.groupsession.v2.ntp.ntp095;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp090.Ntp090Form;
import jp.groupsession.v2.ntp.ui.parts.smailmember.SmailMemberSelector;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp095Form extends Ntp090Form implements ISelectorUseForm {

    /** グループSID: グループ一覧 */
    public static final String GRP_SID_GRPLIST = "-9";

    /** 日報通知画面区分  0:表示しない 1:表示する*/
    private int ntp095NtpDspKbn__ = 0;
    /** コメント通知画面区分  0:表示しない 1:表示する*/
    private int ntp095CmtDspKbn__ = 0;
    /** いいね通知画面区分  0:表示しない 1:表示する*/
    private int ntp095GoodDspKbn__ = 0;

    /** 日報通知設定 */
    private String ntp095Smail__;
    /** コメント通知設定 */
    private String ntp095CmtSmail__;
    /** いいね通知設定 */
    private String ntp095GoodSmail__;

    /** グループ日報ショートメール通知設定 */
    private String ntp095SmailGroup__;

    /** 共有区分  0:全員 1:所属グループのみ*/
    private int ntp095KyoyuKbn__;

    /** 初期表示フラグ 0=初期 1=初期済み */
    private String ntp095InitFlg__ = String.valueOf(GSConstNippou.INIT_FLG);

    /** グループSID */
    private String ntp095groupSid__ = GRP_SID_GRPLIST;
    /** メンバーSID */
    private String[] ntp095memberSid__ = new String[0];
    /** 日報通知メンバー  UI */
    private SmailMemberSelector ntp095memberUI__ =
            SmailMemberSelector.builder()
                .chainLabel(new GsMessageReq("ntp.114", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainGrpType(EnumGroupSelectType.WITHMYGROUP)
                .chainSelect(
                        Select.builder()
                        .chainParameterName(
                                "ntp095memberSid")
                    )
                .chainGroupSelectionParamName("ntp095groupSid")
                .build();


    /**
     * <p>ntp095Smail を取得します。
     * @return ntp095Smail
     */
    public String getNtp095Smail() {
        return ntp095Smail__;
    }
    /**
     * <p>ntp095Smail をセットします。
     * @param ntp095Smail ntp095Smail
     */
    public void setNtp095Smail(String ntp095Smail) {
        ntp095Smail__ = ntp095Smail;
    }
    /**
     * <p>ntp095SmailGroup を取得します。
     * @return ntp095SmailGroup
     */
    public String getNtp095SmailGroup() {
        return ntp095SmailGroup__;
    }
    /**
     * <p>ntp095SmailGroup をセットします。
     * @param ntp095SmailGroup ntp095SmailGroup
     */
    public void setNtp095SmailGroup(String ntp095SmailGroup) {
        ntp095SmailGroup__ = ntp095SmailGroup;
    }
    /**
     * <p>ntp095memberSid を取得します。
     * @return ntp095memberSid
     */
    public String[] getNtp095memberSid() {
        return ntp095memberSid__;
    }
    /**
     * <p>ntp095memberSid をセットします。
     * @param ntp095memberSid ntp095memberSid
     */
    public void setNtp095memberSid(String[] ntp095memberSid) {
        ntp095memberSid__ = ntp095memberSid;
    }
    /**
     * <p>ntp095groupSid を取得します。
     * @return ntp095groupSid
     */
    public String getNtp095groupSid() {
        return ntp095groupSid__;
    }
    /**
     * <p>ntp095groupSid をセットします。
     * @param ntp095groupSid ntp095groupSid
     */
    public void setNtp095groupSid(String ntp095groupSid) {
        ntp095groupSid__ = ntp095groupSid;
    }
    /**
     * <p>ntp095memberUI を取得します。
     * @return ntp095memberUI
     * @see jp.groupsession.v2.ntp.ntp095.Ntp095Form#ntp095memberUI__
     */
    public SmailMemberSelector getNtp095memberUI() {
        return ntp095memberUI__;
    }
    /**
     * <p>ntp095memberUI をセットします。
     * @param ntp095memberUI ntp095memberUI
     * @see jp.groupsession.v2.ntp.ntp095.Ntp095Form#ntp095memberUI__
     */
    public void setNtp095memberUI(SmailMemberSelector ntp095memberUI) {
        ntp095memberUI__ = ntp095memberUI;
    }
    /**
     * <p>grpSidGrplist を取得します。
     * @return grpSidGrplist
     */
    public static String getGrpSidGrplist() {
        return GRP_SID_GRPLIST;
    }
    /**
     * <p>ntp095KyoyuKbn を取得します。
     * @return ntp095KyoyuKbn
     */
    public int getNtp095KyoyuKbn() {
        return ntp095KyoyuKbn__;
    }
    /**
     * <p>ntp095KyoyuKbn をセットします。
     * @param ntp095KyoyuKbn ntp095KyoyuKbn
     */
    public void setNtp095KyoyuKbn(int ntp095KyoyuKbn) {
        ntp095KyoyuKbn__ = ntp095KyoyuKbn;
    }
    /**
     * <p>ntp095InitFlg を取得します。
     * @return ntp095InitFlg
     */
    public String getNtp095InitFlg() {
        return ntp095InitFlg__;
    }
    /**
     * <p>ntp095InitFlg をセットします。
     * @param ntp095InitFlg ntp095InitFlg
     */
    public void setNtp095InitFlg(String ntp095InitFlg) {
        ntp095InitFlg__ = ntp095InitFlg;
    }
    /**
     * <p>ntp095CmtSmail を取得します。
     * @return ntp095CmtSmail
     */
    public String getNtp095CmtSmail() {
        return ntp095CmtSmail__;
    }
    /**
     * <p>ntp095CmtSmail をセットします。
     * @param ntp095CmtSmail ntp095CmtSmail
     */
    public void setNtp095CmtSmail(String ntp095CmtSmail) {
        ntp095CmtSmail__ = ntp095CmtSmail;
    }
    /**
     * <p>ntp095NtpDspKbn を取得します。
     * @return ntp095NtpDspKbn
     */
    public int getNtp095NtpDspKbn() {
        return ntp095NtpDspKbn__;
    }
    /**
     * <p>ntp095NtpDspKbn をセットします。
     * @param ntp095NtpDspKbn ntp095NtpDspKbn
     */
    public void setNtp095NtpDspKbn(int ntp095NtpDspKbn) {
        ntp095NtpDspKbn__ = ntp095NtpDspKbn;
    }
    /**
     * <p>ntp095CmtDspKbn を取得します。
     * @return ntp095CmtDspKbn
     */
    public int getNtp095CmtDspKbn() {
        return ntp095CmtDspKbn__;
    }
    /**
     * <p>ntp095CmtDspKbn をセットします。
     * @param ntp095CmtDspKbn ntp095CmtDspKbn
     */
    public void setNtp095CmtDspKbn(int ntp095CmtDspKbn) {
        ntp095CmtDspKbn__ = ntp095CmtDspKbn;
    }
    /**
     * <p>ntp095GoodDspKbn を取得します。
     * @return ntp095GoodDspKbn
     */
    public int getNtp095GoodDspKbn() {
        return ntp095GoodDspKbn__;
    }
    /**
     * <p>ntp095GoodDspKbn をセットします。
     * @param ntp095GoodDspKbn ntp095GoodDspKbn
     */
    public void setNtp095GoodDspKbn(int ntp095GoodDspKbn) {
        ntp095GoodDspKbn__ = ntp095GoodDspKbn;
    }
    /**
     * <p>ntp095GoodSmail を取得します。
     * @return ntp095GoodSmail
     */
    public String getNtp095GoodSmail() {
        return ntp095GoodSmail__;
    }
    /**
     * <p>ntp095GoodSmail をセットします。
     * @param ntp095GoodSmail ntp095GoodSmail
     */
    public void setNtp095GoodSmail(String ntp095GoodSmail) {
        ntp095GoodSmail__ = ntp095GoodSmail;
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
        
        String plgName = gsMsg.getMessage("ntp.1");
        String msgKey = "error.input.sml.setting";
        String eprefix = "ntp085.";

        // 日報通知判定
        String nippou = NullDefault.getString(ntp095Smail__, "");
        if (!nippou.equals(String.valueOf(GSConstNippou.SML_NOTICE_YES))
                && !nippou.equals(String.valueOf(GSConstNippou.SML_NOTICE_NO))) {
            // 日報通知
            String checkObj = gsMsg.getMessage("ntp.88");
            msg = new ActionMessage(msgKey, plgName, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        //コメント通知判定
        String comment = NullDefault.getString(ntp095CmtSmail__, "");
        if (!comment.equals(String.valueOf(GSConstNippou.SML_NOTICE_YES))
                && !comment.equals(String.valueOf(GSConstNippou.SML_NOTICE_NO))) {
            // コメント通知
            String checkObj = gsMsg.getMessage("ntp.89");
            msg = new ActionMessage(msgKey, plgName, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        //いいね通知判定
        String good = NullDefault.getString(ntp095GoodSmail__, "");
        if (!good.equals(String.valueOf(GSConstNippou.SML_NOTICE_YES))
                && !good.equals(String.valueOf(GSConstNippou.SML_NOTICE_NO))) {
            // いいね通知
            String checkObj = gsMsg.getMessage("ntp.9");
            msg = new ActionMessage(msgKey, plgName, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        return errors;
    }

}
