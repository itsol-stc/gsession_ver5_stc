package jp.groupsession.v2.cht.cht040;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht020.Cht020Form;
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
 * <br>[機  能] チャット 基本設定のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht040Form extends Cht020Form implements ISelectorUseForm {

    /** 初期表示区分 */
    private int cht040InitFlg__ = GSConstChat.DSP_FIRST;
    /** ユーザ間チャットの使用制限 */
    private int cht040BetweenUsers__ = GSConstChat.PERMIT_BETWEEN_USERS;
    /** グループの作成制限 */
    private int cht040CreateGroup__ = GSConstChat.PERMIT_CREATE_GROUP;
    /** 既読の表示制限 */
    private int cht040AlreadyRead__ = GSConstChat.KIDOKU_DISP;
    /** グループの作成制限 制限方法 */
    private int cht040HowToLimit__ = GSConstChat.TARGET_LIMIT;
    /** 使用制限ユーザ グループ */
    private String cht040TargetUserGroup__ = null;
    /** 使用制限ユーザ 選択済みユーザ */
    private String[] cht040TargetUserList__ = null;
    /** 使用制限ユーザ UI */
    private UserGroupSelector cht040TargetUserUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("cht.cht040.02", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainSelect(
                        Select.builder()
                        .chainParameterName(
                                "cht040TargetUserList")
                    )
                .chainGroupSelectionParamName("cht040TargetUserGroup")
                .build();


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
    public ActionErrors validateCht040(
            RequestModel reqMdl, Connection con, Cht040Form form) throws SQLException {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);
        // ユーザ間チャットの使用
        if (cht040BetweenUsers__ != GSConstChat.PERMIT_BETWEEN_USERS
                && cht040BetweenUsers__ != GSConstChat.LIMIT_BETWEEN_USERS) {
                   ActionMessage msg =  new ActionMessage("error.input.format.file",
                           gsMsg.getMessage("cht.cht040.01"),
                           gsMsg.getMessage("main.man340.10"));
                   String eprefix = "betweenUser";
                   StrutsUtil.addMessage(errors, msg, eprefix);
        }

        // グループの作成
        if (cht040CreateGroup__ != GSConstChat.LIMIT_CREATE_GROUP
                && cht040CreateGroup__ != GSConstChat.PERMIT_CREATE_GROUP) {
                   ActionMessage msg =  new ActionMessage("error.input.format.file",
                           gsMsg.getMessage("cht.cht040.02"),
                           gsMsg.getMessage("main.man340.10"));
                   String eprefix = "createGroup";
                   StrutsUtil.addMessage(errors, msg, eprefix);
        }

        //制限対象
        if (cht040CreateGroup__ == GSConstChat.LIMIT_CREATE_GROUP) {

            if (cht040HowToLimit__ != GSConstChat.TARGET_LIMIT
             && cht040HowToLimit__ != GSConstChat.TARGET_PERMIT) {
                ActionMessage msg =  new ActionMessage("error.input.format.file",
                        gsMsg.getMessage("cmn.limit.target"),
                        gsMsg.getMessage("main.man340.10"));
                String eprefix = "howLimit";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }

            //制限ユーザ
            String[] limitList = getCht040TargetUserList();

            if (limitList == null || limitList.length == 0) {
                // 選択されていない場合 → エラーメッセージ
                ActionMessage msg =  new ActionMessage("error.select.required.text",
                        gsMsg.getMessage("cmn.limit.target"));
                String eprefix = "limitNotUser";
                StrutsUtil.addMessage(errors, msg, eprefix);
            } else {
                // 選択されている場合 → ユーザが存在するかチェック
                for (String limitSid:limitList) {
                    boolean errorFlg = false;
                    errorFlg = __existGrpUser(con, limitSid);
                    if (errorFlg) {
                        String limitUser = gsMsg.getMessage("cmn.limit.target");
                        ActionMessage msg
                        =  new ActionMessage("search.data.notfound", limitUser);
                        String eprefix = "limitUser";
                        StrutsUtil.addMessage(errors, msg, eprefix);
                        break;
                    }
                }
            }
        }

        // 既読表示
        if (cht040AlreadyRead__ != GSConstChat.KIDOKU_DISP
                && cht040AlreadyRead__ != GSConstChat.KIDOKU_NOT_DISP) {
                ActionMessage msg =  new ActionMessage("error.input.format.file",
                        gsMsg.getMessage("cht.cht040.03"),
                        gsMsg.getMessage("main.man340.10"));
                   String eprefix = "createGroup";
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
     * <p>cht040InitFlg を取得します。
     * @return cht040InitFlg
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040InitFlg__
     */
    public int getCht040InitFlg() {
        return cht040InitFlg__;
    }

    /**
     * <p>cht040InitFlg をセットします。
     * @param cht040InitFlg cht040InitFlg
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040InitFlg__
     */
    public void setCht040InitFlg(int cht040InitFlg) {
        cht040InitFlg__ = cht040InitFlg;
    }

    /**
     * <p>cht040BetweenUsers を取得します。
     * @return cht040BetweenUsers
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040BetweenUsers__
     */
    public int getCht040BetweenUsers() {
        return cht040BetweenUsers__;
    }

    /**
     * <p>cht040BetweenUsers をセットします。
     * @param cht040BetweenUsers cht040BetweenUsers
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040BetweenUsers__
     */
    public void setCht040BetweenUsers(int cht040BetweenUsers) {
        cht040BetweenUsers__ = cht040BetweenUsers;
    }

    /**
     * <p>cht040CreateGroup を取得します。
     * @return cht040CreateGroup
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040CreateGroup__
     */
    public int getCht040CreateGroup() {
        return cht040CreateGroup__;
    }

    /**
     * <p>cht040CreateGroup をセットします。
     * @param cht040CreateGroup cht040CreateGroup
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040CreateGroup__
     */
    public void setCht040CreateGroup(int cht040CreateGroup) {
        cht040CreateGroup__ = cht040CreateGroup;
    }

    /**
     * <p>cht040AlreadyRead を取得します。
     * @return cht040AlreadyRead
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040AlreadyRead__
     */
    public int getCht040AlreadyRead() {
        return cht040AlreadyRead__;
    }

    /**
     * <p>cht040AlreadyRead をセットします。
     * @param cht040AlreadyRead cht040AlreadyRead
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040AlreadyRead__
     */
    public void setCht040AlreadyRead(int cht040AlreadyRead) {
        cht040AlreadyRead__ = cht040AlreadyRead;
    }

    /**
     * <p>cht040HowToLimit を取得します。
     * @return cht040HowToLimit
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040HowToLimit__
     */
    public int getCht040HowToLimit() {
        return cht040HowToLimit__;
    }

    /**
     * <p>cht040HowToLimit をセットします。
     * @param cht040HowToLimit cht040HowToLimit
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040HowToLimit__
     */
    public void setCht040HowToLimit(int cht040HowToLimit) {
        cht040HowToLimit__ = cht040HowToLimit;
    }

    /**
     * <p>cht040TargetUserGroup を取得します。
     * @return cht040TargetUserGroup
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040TargetUserGroup__
     */
    public String getCht040TargetUserGroup() {
        return cht040TargetUserGroup__;
    }

    /**
     * <p>cht040TargetUserGroup をセットします。
     * @param cht040TargetUserGroup cht040TargetUserGroup
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040TargetUserGroup__
     */
    public void setCht040TargetUserGroup(String cht040TargetUserGroup) {
        cht040TargetUserGroup__ = cht040TargetUserGroup;
    }

    /**
     * <p>cht040TargetUserList を取得します。
     * @return cht040TargetUserList
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040TargetUserList__
     */
    public String[] getCht040TargetUserList() {
        return cht040TargetUserList__;
    }

    /**
     * <p>cht040TargetUserList をセットします。
     * @param cht040TargetUserList cht040TargetUserList
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040TargetUserList__
     */
    public void setCht040TargetUserList(String[] cht040TargetUserList) {
        cht040TargetUserList__ = cht040TargetUserList;
    }

    /**
     * <p>cht040TargetUserUI を取得します。
     * @return cht040TargetUserUI
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040TargetUserUI__
     */
    public UserGroupSelector getCht040TargetUserUI() {
        return cht040TargetUserUI__;
    }

    /**
     * <p>cht040TargetUserUI をセットします。
     * @param cht040TargetUserUI cht040TargetUserUI
     * @see jp.groupsession.v2.cht.cht040.Cht040Form#cht040TargetUserUI__
     */
    public void setCht040TargetUserUI(UserGroupSelector cht040TargetUserUI) {
        cht040TargetUserUI__ = cht040TargetUserUI;
    }

}
