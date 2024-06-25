package jp.groupsession.v2.cht.cht110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cht.ChatValidate;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht100.Cht100Form;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSValidateUser;

/**
 *
 * <br>[機  能] チャットグループ追加/編集のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht110Form extends Cht100Form implements ISelectorUseForm {

    /** 初期表示区分 */
    private int cht110InitFlg__ = GSConstChat.DSP_FIRST;
    /** チャットグループＩＤ */
    private String cht110groupId__ = null;
    /** グループ名 */
    private String cht110groupName__ = null;
    /** カテゴリ */
    private int cht110category__ = -1;

    /** メンバー グループ */
    private String cht110memberGroup__ = null;
    /** メンバー 管理メンバー */
    private String[] cht110memberAdmin__ = null;
    /** メンバー 一般メンバー */
    private String[] cht110memberNormal__ = null;
    /** メンバー UI */
    private UserGroupSelector cht110memberUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("cmn.member", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("cht.03", null))
                        .chainParameterName(
                                "cht110memberAdmin")
                    )
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("cht.04", null))
                        .chainParameterName(
                                "cht110memberNormal")
                    )
                .chainGroupSelectionParamName("cht110memberGroup")
                .build();

    /** 作成日時 */
    private String cht110createDate__ = null;
    /** 最終投稿日時 */
    private String cht110updateDate__ = null;
    /**  備考 */
    private String cht110biko__ = null;
    /** 状態区分 */
    private int cht110compFlg__ = GSConstChat.CHAT_ARCHIVE_NOT_MODE;
    /** カテゴリリスト*/
    private List <LabelValueBean> cht110CategoryList__ = null;
    /** 編集前所属ユーザ */
    private String[] oldMemberSids__ = null;


    /**
     * <br>[機  能] 削除チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param form フォーム
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public boolean validateDeleteCht110(
            RequestModel reqMdl, Connection con, Cht100Form form) throws SQLException {
        // グループ
        if (!ChatValidate.validateIsExitChatGroup(this.getCgiSid(), con)) {
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param form フォーム
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCht110(
            RequestModel reqMdl, Connection con, Cht110Form form) throws SQLException {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);

        // チャットグループＩＤ
        errors = ChatValidate.validateCmnFieldText(
                errors,
                gsMsg.getMessage("cht.01")
                + gsMsg.getMessage("main.src.man220.6"),
                cht110groupId__,
                "groupId",
                GSConstChat.MAX_LENGTH_GROUPID,
                true);

        errors = ChatValidate.validateIsExitGroupId(
                errors,
                form.getCgiSid(),
                gsMsg.getMessage("cht.01")
                + gsMsg.getMessage("main.src.man220.6"),
                cht110groupId__,
                "groupId",
                con);

        // グループ名
        errors = ChatValidate.validateCmnFieldText(
                errors,
                gsMsg.getMessage("cmn.group.name"),
                cht110groupName__,
                "groupName",
                GSConstChat.MAX_LENGTH_GROUPNAME,
                true);

        // カテゴリ
        errors = ChatValidate.validateIsExitCategory(
                errors,
                gsMsg.getMessage("user.47"),
                cht110category__,
                "category",
                con,
                true);

        // メンバー
        //管理者
        String[] limitAdminList = getCht110memberAdmin();

        if (limitAdminList == null || limitAdminList.length == 0) {
            // 選択されていない場合 → エラーメッセージ
            ActionMessage msg =  new ActionMessage("error.select.required.text",
                    gsMsg.getMessage("cht.03"));
            String eprefix = "adminUser";
            StrutsUtil.addMessage(errors, msg, eprefix);
        } else {
            // 選択されている場合 → ユーザが存在するかチェック
            for (String limitSid:limitAdminList) {
                boolean errorFlg = false;
                errorFlg = __existGrpUser(con, limitSid);
                if (errorFlg) {
                    String adminUser = gsMsg.getMessage("cht.03");
                    ActionMessage msg
                    =  new ActionMessage("search.data.notfound", adminUser);
                    String eprefix = "adminUser";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                    break;
                }
            }
        }

        //一般
        String[] limitGeneralList = getCht110memberNormal();
        if (limitGeneralList != null && limitGeneralList.length > 0) {
            for (String limitSid:limitGeneralList) {
                boolean errorFlg = false;
                errorFlg = __existGrpUser(con, limitSid);
                if (errorFlg) {
                    String generalUser = gsMsg.getMessage("cht.04");
                    ActionMessage msg
                    =  new ActionMessage("search.data.notfound", generalUser);
                    String eprefix = "generalUser";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                    break;
                }
            }
        }
        //備考
        errors = ChatValidate.validateTextarea(
                errors,
                cht110biko__,
                gsMsg.getMessage("cmn.memo"),
                GSConstChat.MAX_LENGTH_BIKO,
                false);
        //アーカイブ
        if (cht110compFlg__ != GSConstChat.CHAT_ARCHIVE_MODE
                && cht110compFlg__ != GSConstChat.CHAT_ARCHIVE_NOT_MODE) {
                   ActionMessage msg =  new ActionMessage("error.input.format.file",
                           gsMsg.getMessage("cht.cht110.03"),
                           gsMsg.getMessage("main.man340.10"));
                   String eprefix = "compFlg";
                   StrutsUtil.addMessage(errors, msg, eprefix);
        }
        return errors;
    }

    /**
    *
    * <br>[機  能]グループ・ユーザの存在を判定
    * <br>[解  説]
    * <br>[備  考]
    *@param con コネクション
    *@param  strSid 判定するSID
    *@return 存在可否(true:存在する)
    *@throws SQLException SQL実行時例外
    */
   private boolean __existGrpUser(Connection con, String strSid) throws SQLException {

       if (strSid.startsWith("G")) {
           int sid = NullDefault.getInt(strSid.substring(1), -1);
           if (!GSValidateUser.existGroup(sid, con)) {
               return true;
           }

       } else {
           CmnUsrmDao cuDao = new CmnUsrmDao(con);
           int sid = NullDefault.getInt(strSid, -1);
           if (!cuDao.isExistUser(sid)) {
               return true;
           }
       }
       return false;
   }


    /**
     * <p>cht110groupId を取得します。
     * @return cht110groupId
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110groupId__
     */
    public String getCht110groupId() {
        return cht110groupId__;
    }

    /**
     * <p>cht110groupId をセットします。
     * @param cht110groupId cht110groupId
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110groupId__
     */
    public void setCht110groupId(String cht110groupId) {
        cht110groupId__ = cht110groupId;
    }

    /**
     * <p>cht110groupName を取得します。
     * @return cht110groupName
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110groupName__
     */
    public String getCht110groupName() {
        return cht110groupName__;
    }

    /**
     * <p>cht110groupName をセットします。
     * @param cht110groupName cht110groupName
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110groupName__
     */
    public void setCht110groupName(String cht110groupName) {
        cht110groupName__ = cht110groupName;
    }

    /**
     * <p>cht110category を取得します。
     * @return cht110category
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110category__
     */
    public int getCht110category() {
        return cht110category__;
    }

    /**
     * <p>cht110category をセットします。
     * @param cht110category cht110category
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110category__
     */
    public void setCht110category(int cht110category) {
        cht110category__ = cht110category;
    }

    /**
     * <p>cht110biko を取得します。
     * @return cht110biko
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110biko__
     */
    public String getCht110biko() {
        return cht110biko__;
    }

    /**
     * <p>cht110biko をセットします。
     * @param cht110biko cht110biko
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110biko__
     */
    public void setCht110biko(String cht110biko) {
        cht110biko__ = cht110biko;
    }


    /**
     * <p>cht110CategoryList を取得します。
     * @return cht110CategoryList
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110CategoryList__
     */
    public List<LabelValueBean> getCht110CategoryList() {
        return cht110CategoryList__;
    }

    /**
     * <p>cht110CategoryList をセットします。
     * @param cht110CategoryList cht110CategoryList
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110CategoryList__
     */
    public void setCht110CategoryList(List<LabelValueBean> cht110CategoryList) {
        cht110CategoryList__ = cht110CategoryList;
    }

    /**
     * <p>cht110memberGroup を取得します。
     * @return cht110memberGroup
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110memberGroup__
     */
    public String getCht110memberGroup() {
        return cht110memberGroup__;
    }

    /**
     * <p>cht110memberGroup をセットします。
     * @param cht110memberGroup cht110memberGroup
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110memberGroup__
     */
    public void setCht110memberGroup(String cht110memberGroup) {
        cht110memberGroup__ = cht110memberGroup;
    }

    /**
     * <p>cht110memberAdmin を取得します。
     * @return cht110memberAdmin
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110memberAdmin__
     */
    public String[] getCht110memberAdmin() {
        return cht110memberAdmin__;
    }

    /**
     * <p>cht110memberAdmin をセットします。
     * @param cht110memberAdmin cht110memberAdmin
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110memberAdmin__
     */
    public void setCht110memberAdmin(String[] cht110memberAdmin) {
        cht110memberAdmin__ = cht110memberAdmin;
    }

    /**
     * <p>cht110memberNormal を取得します。
     * @return cht110memberNormal
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110memberNormal__
     */
    public String[] getCht110memberNormal() {
        return cht110memberNormal__;
    }

    /**
     * <p>cht110memberNormal をセットします。
     * @param cht110memberNormal cht110memberNormal
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110memberNormal__
     */
    public void setCht110memberNormal(String[] cht110memberNormal) {
        cht110memberNormal__ = cht110memberNormal;
    }

    /**
     * <p>cht110memberUI を取得します。
     * @return cht110memberUI
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110memberUI__
     */
    public UserGroupSelector getCht110memberUI() {
        return cht110memberUI__;
    }

    /**
     * <p>cht110memberUI をセットします。
     * @param cht110memberUI cht110memberUI
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110memberUI__
     */
    public void setCht110memberUI(UserGroupSelector cht110memberUI) {
        cht110memberUI__ = cht110memberUI;
    }

    /**
     * <p>cht110createDate を取得します。
     * @return cht110createDate
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110createDate__
     */
    public String getCht110createDate() {
        return cht110createDate__;
    }

    /**
     * <p>cht110createDate をセットします。
     * @param cht110createDate cht110createDate
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110createDate__
     */
    public void setCht110createDate(String cht110createDate) {
        cht110createDate__ = cht110createDate;
    }

    /**
     * <p>cht110updateDate を取得します。
     * @return cht110updateDate
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110updateDate__
     */
    public String getCht110updateDate() {
        return cht110updateDate__;
    }

    /**
     * <p>cht110updateDate をセットします。
     * @param cht110updateDate cht110updateDate
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110updateDate__
     */
    public void setCht110updateDate(String cht110updateDate) {
        cht110updateDate__ = cht110updateDate;
    }

    /**
     * <p>cht110compFlg を取得します。
     * @return cht110compFlg
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110compFlg__
     */
    public int getCht110compFlg() {
        return cht110compFlg__;
    }

    /**
     * <p>cht110compFlg をセットします。
     * @param cht110compFlg cht110compFlg
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110compFlg__
     */
    public void setCht110compFlg(int cht110compFlg) {
        cht110compFlg__ = cht110compFlg;
    }

    /**
     * <p>cht110InitFlg を取得します。
     * @return cht110InitFlg
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110InitFlg__
     */
    public int getCht110InitFlg() {
        return cht110InitFlg__;
    }

    /**
     * <p>cht110InitFlg をセットします。
     * @param cht110InitFlg cht110InitFlg
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#cht110InitFlg__
     */
    public void setCht110InitFlg(int cht110InitFlg) {
        cht110InitFlg__ = cht110InitFlg;
    }

    /**
     * <p>oldMemberSids を取得します。
     * @return oldMemberSids
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#oldMemberSids__
     */
    public String[] getOldMemberSids() {
        return oldMemberSids__;
    }

    /**
     * <p>oldMemberSids をセットします。
     * @param oldMemberSids oldMemberSids
     * @see jp.groupsession.v2.cht.cht110.Cht110Form#oldMemberSids__
     */
    public void setOldMemberSids(String[] oldMemberSids) {
        oldMemberSids__ = oldMemberSids;
    }




}
