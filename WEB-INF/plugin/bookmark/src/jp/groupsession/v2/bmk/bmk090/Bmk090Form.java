package jp.groupsession.v2.bmk.bmk090;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.bmk010.Bmk010Form;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 権限設定(グループブックマーク)画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk090Form extends Bmk010Form implements ISelectorUseForm {
    /** グループ別ブックマーク編集区分 */
    private int bmk090GrpEditKbn__ = -1;
    /** グループ名 */
    private String bmk090GrpName__ = null;
    /** 初期表示フラグ */
    private int bmk090initFlg__ = 0;

    //共有ブックマーク編集区分、グループ指定時
    /** 追加済みグループSID */
    private String[] bmk090GrpSid__ = null;
    /** 編集グループ UI */
    private UserGroupSelector bmk090GrpSidUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("cmn.edit.permissions", null))
                .chainType(EnumSelectType.GROUP)
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("cmn.editgroup", null))
                        .chainParameterName(
                                "bmk090GrpSid")
                    )
                .build();

    //共有ブックマーク編集区分、ユーザ指定時
    /** 追加済みユーザSID */
    private String[] bmk090UserSid__ = null;
    /** グループSID */
    private int bmk090GroupSid__ = 0;
    /** 編集ユーザ UI */
    private UserGroupSelector bmk090UserSidUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("cmn.edit.permissions", null))
                .chainType(EnumSelectType.USER)
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("cmn.edituser", null))
                        .chainParameterName(
                                "bmk090UserSid")
                    )
                .chainGroupSelectionParamName("bmk090GroupSid")
                .build();

    /**
     * <p>bmk090GrpEditKbn を取得します。
     * @return bmk090GrpEditKbn
     */
    public int getBmk090GrpEditKbn() {
        return bmk090GrpEditKbn__;
    }
    /**
     * <p>bmk090GrpEditKbn をセットします。
     * @param bmk090GrpEditKbn bmk090GrpEditKbn
     */
    public void setBmk090GrpEditKbn(int bmk090GrpEditKbn) {
        bmk090GrpEditKbn__ = bmk090GrpEditKbn;
    }

    /**
     * <p>bmk090GrpName を取得します。
     * @return bmk090GrpName
     */
    public String getBmk090GrpName() {
        return bmk090GrpName__;
    }
    /**
     * <p>bmk090GrpName をセットします。
     * @param bmk090GrpName bmk090GrpName
     */
    public void setBmk090GrpName(String bmk090GrpName) {
        bmk090GrpName__ = bmk090GrpName;
    }
    /**
     * <p>bmk090initFlg を取得します。
     * @return bmk090initFlg
     * @see jp.groupsession.v2.bmk.bmk090.Bmk090ParamModel#bmk090initFlg__
     */
    public int getBmk090initFlg() {
        return bmk090initFlg__;
    }
    /**
     * <p>bmk090initFlg をセットします。
     * @param bmk090initFlg bmk090initFlg
     * @see jp.groupsession.v2.bmk.bmk090.Bmk090ParamModel#bmk090initFlg__
     */
    public void setBmk090initFlg(int bmk090initFlg) {
        bmk090initFlg__ = bmk090initFlg;
    }
    /**
     * <p>bmk090GrpSid を取得します。
     * @return bmk090GrpSid
     */
    public String[] getBmk090GrpSid() {
        return bmk090GrpSid__;
    }
    /**
     * <p>bmk090GrpSid をセットします。
     * @param bmk090GrpSid bmk090GrpSid
     */
    public void setBmk090GrpSid(String[] bmk090GrpSid) {
        bmk090GrpSid__ = bmk090GrpSid;
    }
    /**
     * <p>bmk090GrpSidUI を取得します。
     * @return bmk090GrpSidUI
     * @see jp.groupsession.v2.bmk.bmk090.Bmk090Form#bmk090GrpSidUI__
     */
    public UserGroupSelector getBmk090GrpSidUI() {
        return bmk090GrpSidUI__;
    }
    /**
     * <p>bmk090GrpSidUI をセットします。
     * @param bmk090GrpSidUI bmk090GrpSidUI
     * @see jp.groupsession.v2.bmk.bmk090.Bmk090Form#bmk090GrpSidUI__
     */
    public void setBmk090GrpSidUI(UserGroupSelector bmk090GrpSidUI) {
        bmk090GrpSidUI__ = bmk090GrpSidUI;
    }
    /**
     * <p>bmk090UserSid を取得します。
     * @return bmk090UserSid
     */
    public String[] getBmk090UserSid() {
        return bmk090UserSid__;
    }
    /**
     * <p>bmk090UserSid をセットします。
     * @param bmk090UserSid bmk090UserSid
     */
    public void setBmk090UserSid(String[] bmk090UserSid) {
        bmk090UserSid__ = bmk090UserSid;
    }
    /**
     * <p>bmk090GroupSid を取得します。
     * @return bmk090GroupSid
     */
    public int getBmk090GroupSid() {
        return bmk090GroupSid__;
    }
    /**
     * <p>bmk090GroupSid をセットします。
     * @param bmk090GroupSid bmk090GroupSid
     */
    public void setBmk090GroupSid(int bmk090GroupSid) {
        bmk090GroupSid__ = bmk090GroupSid;
    }
    /**
     * <p>bmk090UserSidUI を取得します。
     * @return bmk090UserSidUI
     * @see jp.groupsession.v2.bmk.bmk090.Bmk090Form#bmk090UserSidUI__
     */
    public UserGroupSelector getBmk090UserSidUI() {
        return bmk090UserSidUI__;
    }
    /**
     * <p>bmk090UserSidUI をセットします。
     * @param bmk090UserSidUI bmk090UserSidUI
     * @see jp.groupsession.v2.bmk.bmk090.Bmk090Form#bmk090UserSidUI__
     */
    public void setBmk090UserSidUI(UserGroupSelector bmk090UserSidUI) {
        bmk090UserSidUI__ = bmk090UserSidUI;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, HttpServletRequest req)
    throws SQLException {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage();
        String msg2 = "";

        if (bmk090GrpEditKbn__ == GSConstBookmark.EDIT_POW_GROUP) {
            msg2 = gsMsg.getMessage(req, "cmn.editgroup");;
            //グループ指定：未選択チェック
            if (bmk090GrpSid__ == null || bmk090GrpSid__.length == 0) {
                String msgKey = "error.select.required.text";
                ActionMessage msg = new ActionMessage(msgKey, msg2);
                StrutsUtil.addMessage(
                        errors, msg, "bmk090GrpSid." + msgKey);
            }
        } else if (bmk090GrpEditKbn__ == GSConstBookmark.EDIT_POW_USER) {
            msg2 = gsMsg.getMessage(req, "cmn.edituser");;
            //ユーザ指定：未選択チェック
            if (bmk090UserSid__ == null || bmk090UserSid__.length == 0) {
                String msgKey = "error.select.required.text";
                ActionMessage msg = new ActionMessage(msgKey, msg2);
                StrutsUtil.addMessage(
                        errors, msg, "bmk090UserSid." + msgKey);
            }
        }
        return errors;
    }
}
