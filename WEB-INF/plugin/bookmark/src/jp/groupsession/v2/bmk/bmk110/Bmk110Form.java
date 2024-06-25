package jp.groupsession.v2.bmk.bmk110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.bmk100.Bmk100Form;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 権限設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk110Form extends Bmk100Form implements ISelectorUseForm {
    /** 共有ブックマーク編集区分 */
    private int bmk110PubEditKbn__ = -1;
    /** グループブックマーク編集区分 */
    private int bmk110GrpEditKbn__ = -1;
    /** 初期表示フラグ */
    private int bmk110initFlg__ = 0;

    //共有ブックマーク編集区分、グループ指定時
    /** 追加済みグループSID */
    private String[] bmk110GrpSid__ = null;
    /** 編集グループ UI */
    private UserGroupSelector bmk110GrpSidUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("bmk.34", null))
                .chainType(EnumSelectType.GROUP)
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("cmn.editgroup", null))
                        .chainParameterName(
                                "bmk110GrpSid")
                    )
                .build();


    //共有ブックマーク編集区分、ユーザ指定時
    /** 追加済みユーザSID */
    private String[] bmk110UserSid__ = null;

    /** グループSID */
    private int bmk110GroupSid__ = 0;
    /** 編集ユーザ UI */
    private UserGroupSelector bmk110UserSidUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("bmk.34", null))
                .chainType(EnumSelectType.USER)
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("cmn.edituser", null))
                        .chainParameterName(
                                "bmk110UserSid")
                    )
                .chainGroupSelectionParamName("bmk110GroupSid")
                .build();

    /**
     * <p>bmk110GrpEditKbn を取得します。
     * @return bmk110GrpEditKbn
     */
    public int getBmk110GrpEditKbn() {
        return bmk110GrpEditKbn__;
    }
    /**
     * <p>bmk110GrpEditKbn をセットします。
     * @param bmk110GrpEditKbn bmk110GrpEditKbn
     */
    public void setBmk110GrpEditKbn(int bmk110GrpEditKbn) {
        bmk110GrpEditKbn__ = bmk110GrpEditKbn;
    }
    /**
     * <p>bmk110PubEditKbn を取得します。
     * @return bmk110PubEditKbn
     */
    public int getBmk110PubEditKbn() {
        return bmk110PubEditKbn__;
    }
    /**
     * <p>bmk110PubEditKbn をセットします。
     * @param bmk110PubEditKbn bmk110PubEditKbn
     */
    public void setBmk110PubEditKbn(int bmk110PubEditKbn) {
        bmk110PubEditKbn__ = bmk110PubEditKbn;
    }
    /**
     * <p>bmk110initFlg を取得します。
     * @return bmk110initFlg
     * @see jp.groupsession.v2.bmk.bmk110.Bmk110Form#bmk110initFlg__
     */
    public int getBmk110initFlg() {
        return bmk110initFlg__;
    }
    /**
     * <p>bmk110initFlg をセットします。
     * @param bmk110initFlg bmk110initFlg
     * @see jp.groupsession.v2.bmk.bmk110.Bmk110Form#bmk110initFlg__
     */
    public void setBmk110initFlg(int bmk110initFlg) {
        bmk110initFlg__ = bmk110initFlg;
    }
    /**
     * <p>bmk110GrpSid を取得します。
     * @return bmk110GrpSid
     */
    public String[] getBmk110GrpSid() {
        return bmk110GrpSid__;
    }
    /**
     * <p>bmk110GrpSid をセットします。
     * @param bmk110GrpSid bmk110GrpSid
     */
    public void setBmk110GrpSid(String[] bmk110GrpSid) {
        bmk110GrpSid__ = bmk110GrpSid;
    }
    /**
     * <p>bmk110GrpSidUI を取得します。
     * @return bmk110GrpSidUI
     * @see jp.groupsession.v2.bmk.bmk110.Bmk110ParamModel#bmk110GrpSidUI__
     */
    public UserGroupSelector getBmk110GrpSidUI() {
        return bmk110GrpSidUI__;
    }
    /**
     * <p>bmk110GrpSidUI をセットします。
     * @param bmk110GrpSidUI bmk110GrpSidUI
     * @see jp.groupsession.v2.bmk.bmk110.Bmk110ParamModel#bmk110GrpSidUI__
     */
    public void setBmk110GrpSidUI(UserGroupSelector bmk110GrpSidUI) {
        bmk110GrpSidUI__ = bmk110GrpSidUI;
    }
    /**
     * <p>bmk110UserSid を取得します。
     * @return bmk110UserSid
     */
    public String[] getBmk110UserSid() {
        return bmk110UserSid__;
    }
    /**
     * <p>bmk110UserSid をセットします。
     * @param bmk110UserSid bmk110UserSid
     */
    public void setBmk110UserSid(String[] bmk110UserSid) {
        bmk110UserSid__ = bmk110UserSid;
    }
    /**
     * <p>bmk110GroupSid を取得します。
     * @return bmk110GroupSid
     */
    public int getBmk110GroupSid() {
        return bmk110GroupSid__;
    }
    /**
     * <p>bmk110GroupSid をセットします。
     * @param bmk110GroupSid bmk110GroupSid
     */
    public void setBmk110GroupSid(int bmk110GroupSid) {
        bmk110GroupSid__ = bmk110GroupSid;
    }
    /**
     * <p>bmk110UserSidUI を取得します。
     * @return bmk110UserSidUI
     * @see jp.groupsession.v2.bmk.bmk110.Bmk110Form#bmk110UserSidUI__
     */
    public UserGroupSelector getBmk110UserSidUI() {
        return bmk110UserSidUI__;
    }
    /**
     * <p>bmk110UserSidUI をセットします。
     * @param bmk110UserSidUI bmk110UserSidUI
     * @see jp.groupsession.v2.bmk.bmk110.Bmk110Form#bmk110UserSidUI__
     */
    public void setBmk110UserSidUI(UserGroupSelector bmk110UserSidUI) {
        bmk110UserSidUI__ = bmk110UserSidUI;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con DBコネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(HttpServletRequest req, Connection con)
    throws SQLException {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(req);

        //共有ブックマーク 選択値チェック
        if (bmk110PubEditKbn__ != GSConstBookmark.EDIT_POW_ADMIN
        && bmk110PubEditKbn__ != GSConstBookmark.EDIT_POW_GROUP
        && bmk110PubEditKbn__ != GSConstBookmark.EDIT_POW_USER
        && bmk110PubEditKbn__ != GSConstBookmark.EDIT_POW_ALL
        ) {
            String eprefix = "bmk110PubEditKbn.";
            ActionMessage msg = new ActionMessage("error.input.notvalidate.data",
                        gsMsg.getMessage("bmk.34"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notvalidate.data");
        }

        //グループブックマーク 選択値チェック
        if (bmk110GrpEditKbn__ != GSConstBookmark.GROUP_EDIT_ADMIN
        && bmk110GrpEditKbn__ != GSConstBookmark.GROUP_EDIT_GROUP
        && bmk110GrpEditKbn__ != GSConstBookmark.GROUP_EDIT_ALL
        ) {
            String eprefix = "bmk110GrpEditKbn.";
            ActionMessage msg = new ActionMessage("error.input.notvalidate.data",
                        gsMsg.getMessage("bmk.51"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notvalidate.data");
        }

        String msg2 = "";

        if (bmk110PubEditKbn__ == GSConstBookmark.EDIT_POW_GROUP) {
            //グループ指定：未選択チェック
            if (bmk110GrpSid__ == null || bmk110GrpSid__.length == 0) {
                msg2 = gsMsg.getMessage(req, "cmn.editgroup");
                String msgKey = "error.select.required.text";
                ActionMessage msg = new ActionMessage(msgKey, msg2);
                StrutsUtil.addMessage(
                        errors, msg, "bmk110GrpSid." + msgKey);
            } else {
                //グループ指定：存在チェック
                ArrayList<Integer> grpSidList = new ArrayList<Integer>();
                for (String grpSid : bmk110GrpSid__) {
                    grpSidList.add(Integer.parseInt(grpSid));
                }

                UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
                ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyClass(grpSidList);
                if (glist == null || glist.size() != bmk110GrpSid__.length) {
                    msg2 = gsMsg.getMessage("cmn.editgroup");
                    String msgKey = "error.input.notvalidate.data";
                    ActionMessage msg = new ActionMessage(msgKey, msg2);
                    StrutsUtil.addMessage(
                            errors, msg, "bmk110GrpSid" + msgKey);
                }
            }
        } else if (bmk110PubEditKbn__ == GSConstBookmark.EDIT_POW_USER) {
            //ユーザ指定：未選択チェック
            if (bmk110UserSid__ == null || bmk110UserSid__.length == 0) {
                msg2 = gsMsg.getMessage(req, "cmn.edituser");
                String msgKey = "error.select.required.text";
                ActionMessage msg = new ActionMessage(msgKey, msg2);
                StrutsUtil.addMessage(
                        errors, msg, "bmk110UserSid." + msgKey);
            } else {
                //ユーザ指定：存在チェック
                UserBiz userBiz = new UserBiz();
                ArrayList<BaseUserModel> ulist
                        = userBiz.getBaseUserList(con, bmk110UserSid__);
                if (ulist == null || ulist.size() != bmk110UserSid__.length) {
                    msg2 = gsMsg.getMessage("cmn.edituser");
                    String msgKey = "error.input.notvalidate.data";
                    ActionMessage msg = new ActionMessage(msgKey, msg2);
                    StrutsUtil.addMessage(
                            errors, msg, "bmk110UserSid" + msgKey);
                }
            }
        }
        return errors;
    }
}
