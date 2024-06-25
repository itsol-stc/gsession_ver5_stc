package jp.groupsession.v2.cht.cht040;

import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht020.Cht020ParamModel;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

/**
 *
 * <br>[機  能] チャット 基本設定のパラメータ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht040ParamModel extends Cht020ParamModel {

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
     private UserGroupSelector cht040TargetUserUI__ = null;

     /**
     * <p>cht040InitFlg を取得します。
     * @return cht040InitFlg
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040InitFlg__
     */
    public int getCht040InitFlg() {
        return cht040InitFlg__;
    }
    /**
     * <p>cht040InitFlg をセットします。
     * @param cht040InitFlg cht040InitFlg
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040InitFlg__
     */
    public void setCht040InitFlg(int cht040InitFlg) {
        cht040InitFlg__ = cht040InitFlg;
    }
    /**
     * <p>cht040BetweenUsers を取得します。
     * @return cht040BetweenUsers
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040BetweenUsers__
     */
    public int getCht040BetweenUsers() {
        return cht040BetweenUsers__;
    }
    /**
     * <p>cht040BetweenUsers をセットします。
     * @param cht040BetweenUsers cht040BetweenUsers
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040BetweenUsers__
     */
    public void setCht040BetweenUsers(int cht040BetweenUsers) {
        cht040BetweenUsers__ = cht040BetweenUsers;
    }
    /**
     * <p>cht040CreateGroup を取得します。
     * @return cht040CreateGroup
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040CreateGroup__
     */
    public int getCht040CreateGroup() {
        return cht040CreateGroup__;
    }
    /**
     * <p>cht040CreateGroup をセットします。
     * @param cht040CreateGroup cht040CreateGroup
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040CreateGroup__
     */
    public void setCht040CreateGroup(int cht040CreateGroup) {
        cht040CreateGroup__ = cht040CreateGroup;
    }
    /**
     * <p>cht040AlreadyRead を取得します。
     * @return cht040AlreadyRead
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040AlreadyRead__
     */
    public int getCht040AlreadyRead() {
        return cht040AlreadyRead__;
    }
    /**
     * <p>cht040AlreadyRead をセットします。
     * @param cht040AlreadyRead cht040AlreadyRead
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040AlreadyRead__
     */
    public void setCht040AlreadyRead(int cht040AlreadyRead) {
        cht040AlreadyRead__ = cht040AlreadyRead;
    }
    /**
     * <p>cht040HowToLimit を取得します。
     * @return cht040HowToLimit
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040HowToLimit__
     */
    public int getCht040HowToLimit() {
        return cht040HowToLimit__;
    }
    /**
     * <p>cht040HowToLimit をセットします。
     * @param cht040HowToLimit cht040HowToLimit
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040HowToLimit__
     */
    public void setCht040HowToLimit(int cht040HowToLimit) {
        cht040HowToLimit__ = cht040HowToLimit;
    }
    /**
     * <p>cht040TargetUserGroup を取得します。
     * @return cht040TargetUserGroup
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040TargetUserGroup__
     */
    public String getCht040TargetUserGroup() {
        return cht040TargetUserGroup__;
    }
    /**
     * <p>cht040TargetUserGroup をセットします。
     * @param cht040TargetUserGroup cht040TargetUserGroup
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040TargetUserGroup__
     */
    public void setCht040TargetUserGroup(String cht040TargetUserGroup) {
        cht040TargetUserGroup__ = cht040TargetUserGroup;
    }
    /**
     * <p>cht040TargetUserList を取得します。
     * @return cht040TargetUserList
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040TargetUserList__
     */
    public String[] getCht040TargetUserList() {
        return cht040TargetUserList__;
    }
    /**
     * <p>cht040TargetUserList をセットします。
     * @param cht040TargetUserList cht040TargetUserList
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040TargetUserList__
     */
    public void setCht040TargetUserList(String[] cht040TargetUserList) {
        cht040TargetUserList__ = cht040TargetUserList;
    }
    /**
     * <p>cht040TargetUserUI を取得します。
     * @return cht040TargetUserUI
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040TargetUserUI__
     */
    public UserGroupSelector getCht040TargetUserUI() {
        return cht040TargetUserUI__;
    }
    /**
     * <p>cht040TargetUserUI をセットします。
     * @param cht040TargetUserUI cht040TargetUserUI
     * @see jp.groupsession.v2.cht.cht040.Cht040ParamModel#cht040TargetUserUI__
     */
    public void setCht040TargetUserUI(UserGroupSelector cht040TargetUserUI) {
        cht040TargetUserUI__ = cht040TargetUserUI;
    }
}
