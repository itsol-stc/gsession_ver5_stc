package jp.groupsession.v2.cht.cht090;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cht.ChatValidate;
import jp.groupsession.v2.cht.cht080.Cht080Form;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;


/**
 * <br>[機  能] チャット 特例アクセス登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht090Form extends Cht080Form implements ISelectorUseForm {


    /** ユーザ情報 or アクセス制御コンボ(選択用)のグループコンボに設定する値 グループ一覧のVALUE */
    public static final int GROUP_COMBO_VALUE = -9;
    /** 特例アクセス名 MAX文字数 */
    public static final int MAXLEN_NAME = 50;
    /** 備考 MAX文字数 */
    public static final int MAXLEN_BIKO = 1000;
    /** 役職 権限区分 追加・変更・削除 */
    public static final int POTION_AUTH_EDIT = 0;
    /** 役職 権限区分 閲覧 */
    public static final int POTION_AUTH_VIEW = 1;

    /** 初期表示 */
    private int cht090initFlg__ = 0;

    /** 特例アクセス名称 */
    private String cht090name__ = null;
    /** 備考 */
    private String cht090biko__ = null;
    /** 役職 */
    private int cht090position__ = 0;
    /** 役職 権限区分 */
    private int cht090positionAuth__ = 0;
    /** 役職コンボ */
    private List<LabelValueBean> cht090positionCombo__  = null;

    /** 制限対象 */
    private String[] cht090subject__ = null;
    /** 制限対象 グループ */
    private int cht090subjectGroup__  = -1;
    /** 制限対象 UI */
    private UserGroupSelector cht090subjectUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("cht.cht090.04", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainSelect(
                        Select.builder()
                        .chainParameterName(
                                "cht090subject")
                    )
                .chainGroupSelectionParamName("cht090subjectGroup")
                .build();

    /** 許可ユーザ  */
    private String[] cht090accessUser__ = null;
    /** 許可ユーザ グループ */
    private int cht090accessGroup__  = -1;
    /** 許可ユーザ UI */
    private UserGroupSelector cht090accessUserUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("cht.cht090.06", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainSelect(
                        Select.builder()
                        .chainParameterName(
                                "cht090accessUser")
                    )
                .chainGroupSelectionParamName("cht090accessGroup")
                .build();


    /**
     * <p>cht090initFlg を取得します。
     * @return cht090initFlg
     */
    public int getCht090initFlg() {
        return cht090initFlg__;
    }
    /**
     * <p>cht090initFlg をセットします。
     * @param cht090initFlg cht090initFlg
     */
    public void setCht090initFlg(int cht090initFlg) {
        cht090initFlg__ = cht090initFlg;
    }
    /**
     * <p>cht090name を取得します。
     * @return cht090name
     */
    public String getCht090name() {
        return cht090name__;
    }
    /**
     * <p>cht090name をセットします。
     * @param cht090name cht090name
     */
    public void setCht090name(String cht090name) {
        cht090name__ = cht090name;
    }
    /**
     * <p>cht090biko を取得します。
     * @return cht090biko
     */
    public String getCht090biko() {
        return cht090biko__;
    }
    /**
     * <p>cht090biko をセットします。
     * @param cht090biko cht090biko
     */
    public void setCht090biko(String cht090biko) {
        cht090biko__ = cht090biko;
    }
    /**
     * <p>cht090position を取得します。
     * @return cht090position
     */
    public int getCht090position() {
        return cht090position__;
    }
    /**
     * <p>cht090position をセットします。
     * @param cht090position cht090position
     */
    public void setCht090position(int cht090position) {
        cht090position__ = cht090position;
    }
    /**
     * <p>cht090positionAuth を取得します。
     * @return cht090positionAuth
     */
    public int getCht090positionAuth() {
        return cht090positionAuth__;
    }
    /**
     * <p>cht090positionAuth をセットします。
     * @param cht090positionAuth cht090positionAuth
     */
    public void setCht090positionAuth(int cht090positionAuth) {
        cht090positionAuth__ = cht090positionAuth;
    }
    /**
     * <p>cht090positionCombo を取得します。
     * @return cht090positionCombo
     */
    public List<LabelValueBean> getCht090positionCombo() {
        return cht090positionCombo__;
    }
    /**
     * <p>cht090positionCombo をセットします。
     * @param cht090positionCombo cht090positionCombo
     */
    public void setCht090positionCombo(List<LabelValueBean> cht090positionCombo) {
        cht090positionCombo__ = cht090positionCombo;
    }
    /**
     * <p>cht090subject を取得します。
     * @return cht090subject
     */
    public String[] getCht090subject() {
        return cht090subject__;
    }
    /**
     * <p>cht090subject をセットします。
     * @param cht090subject cht090subject
     */
    public void setCht090subject(String[] cht090subject) {
        cht090subject__ = cht090subject;
    }
    /**
     * <p>cht090subjectGroup を取得します。
     * @return cht090subjectGroup
     */
    public int getCht090subjectGroup() {
        return cht090subjectGroup__;
    }
    /**
     * <p>cht090subjectGroup をセットします。
     * @param cht090subjectGroup cht090subjectGroup
     */
    public void setCht090subjectGroup(int cht090subjectGroup) {
        cht090subjectGroup__ = cht090subjectGroup;
    }
    /**
     * <p>cht090subjectUI を取得します。
     * @return cht090subjectUI
     * @see jp.groupsession.v2.cht.cht090.Cht090Form#cht090subjectUI__
     */
    public UserGroupSelector getCht090subjectUI() {
        return cht090subjectUI__;
    }
    /**
     * <p>cht090subjectUI をセットします。
     * @param cht090subjectUI cht090subjectUI
     * @see jp.groupsession.v2.cht.cht090.Cht090Form#cht090subjectUI__
     */
    public void setCht090subjectUI(UserGroupSelector cht090subjectUI) {
        cht090subjectUI__ = cht090subjectUI;
    }
    /**
     * <p>cht090accessUser を取得します。
     * @return cht090accessUser
     */
    public String[] getCht090accessUser() {
        return cht090accessUser__;
    }
    /**
     * <p>cht090accessUser をセットします。
     * @param cht090accessUser cht090accessUser
     */
    public void setCht090accessUser(String[] cht090accessUser) {
        cht090accessUser__ = cht090accessUser;
    }
    /**
     * <p>cht090accessGroup を取得します。
     * @return cht090accessGroup
     */
    public int getCht090accessGroup() {
        return cht090accessGroup__;
    }
    /**
     * <p>cht090accessGroup をセットします。
     * @param cht090accessGroup cht090accessGroup
     */
    public void setCht090accessGroup(int cht090accessGroup) {
        cht090accessGroup__ = cht090accessGroup;
    }
    /**
     * <p>cht090accessUserUI を取得します。
     * @return cht090accessUserUI
     * @see jp.groupsession.v2.cht.cht090.Cht090Form#cht090accessUserUI__
     */
    public UserGroupSelector getCht090accessUserUI() {
        return cht090accessUserUI__;
    }
    /**
     * <p>cht090accessUserUI をセットします。
     * @param cht090accessUserUI cht090accessUserUI
     * @see jp.groupsession.v2.cht.cht090.Cht090Form#cht090accessUserUI__
     */
    public void setCht090accessUserUI(UserGroupSelector cht090accessUserUI) {
        cht090accessUserUI__ = cht090accessUserUI;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        //特例アクセス名入力チェック
        ChatValidate.validateCmnFieldText(
                errors,
                gsMsg.getMessage("cht.cht090.03"),
                cht090name__,
                "cht090name",
                MAXLEN_NAME, true);

        //制限対象入力チェック
        __validateTargetCheck(errors, con, reqMdl);
        //アクセス許可入力チェック
        __validatePermitCheck(errors, con, reqMdl);

        //備考入力チェック
        ChatValidate.validateCmnFieldText(errors, cht090biko__,
                "cht090biko",
                gsMsg.getMessage("cmn.memo"),
                MAXLEN_BIKO,
                false);
        return errors;

    }

    /**
    *
    * <br>[機  能] 制限先入力チェック
    * <br>[解  説]
    * <br>[備  考]
    * @param errors アクションエラー
    * @param con コネクション
    * @param reqMdl リクエストモデル
    * @throws SQLException SQL実行時例外
    */
    private void __validateTargetCheck(ActionErrors errors, Connection con, RequestModel reqMdl)
    throws SQLException {
        //制限対象入力チェック
        GsMessage gsMsg = new GsMessage(reqMdl);
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        List<String> usrSids = new ArrayList<String>();
        if (cht090subject__ != null) {
            for (String target : cht090subject__) {
                if (target.startsWith("G")) {
                    grpSids.add(NullDefault.getInt(
                            target.substring(1), -1));
                } else {
                    if (NullDefault.getInt(
                            target, -1) > GSConstUser.USER_RESERV_SID) {
                        usrSids.add(target);
                    }
                }
            }
        }
        int count = 0;
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyClass(grpSids);
        if (glist != null) {
            count += glist.size();
        }
        UserBiz userBiz = new UserBiz();
        ArrayList<BaseUserModel> ulist
                = userBiz.getBaseUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        if (ulist != null) {
            count += ulist.size();
        }
        if (count == 0) {
            ChatValidate.validateNoSelect(errors, null,
                    "cht090subject",
                    gsMsg.getMessage("cht.cht090.05"));

        }
    }

    /**
   *
   * <br>[機  能] 許可入力チェック
   * <br>[解  説]
   * <br>[備  考]
   * @param errors アクションエラー
   * @param con コネクション
   * @param reqMdl リクエストモデル
   * @throws SQLException SQL実行時例外
   */
    private void __validatePermitCheck(ActionErrors errors, Connection con, RequestModel reqMdl)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        List<String> usrSids = new ArrayList<String>();

        if (cht090accessUser__ != null) {
            for (String target : cht090accessUser__) {
                if (target.startsWith("G")) {
                    grpSids.add(NullDefault.getInt(
                            target.substring(1), -1));
                } else {
                    if (NullDefault.getInt(
                            target, -1) > GSConstUser.USER_RESERV_SID) {
                        usrSids.add(target);
                    }
                }
            }
        }

        int count = 0;
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyClass(grpSids);
        if (glist != null) {
            count += glist.size();
        }
        UserBiz userBiz = new UserBiz();
        ArrayList<BaseUserModel> ulist
                = userBiz.getBaseUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        if (ulist != null) {
            count += ulist.size();
        }
        CmnPositionDao pdao = new CmnPositionDao(con);
        if (cht090position__ > 0 && pdao.getPosInfo(cht090position__) != null) {
            count++;
        }

        if (count == 0) {
            ChatValidate.validateNoSelect(errors, null,
                    "cht090accessUser",
                    gsMsg.getMessage("cht.cht090.06"));
        }
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("backScreen", this.getBackScreen());
        msgForm.addHiddenParam("cht010SelectPartner", this.getCht010SelectPartner());
        msgForm.addHiddenParam("cht010SelectKbn", this.getCht010SelectKbn());
        msgForm.addHiddenParam("cht090initFlg", cht090initFlg__);
        msgForm.addHiddenParam("cht090name", cht090name__);
        msgForm.addHiddenParam("cht090biko", cht090biko__);
        msgForm.addHiddenParam("cht090position", cht090position__);
        msgForm.addHiddenParam("cht090positionAuth", cht090positionAuth__);
        msgForm.addHiddenParam("cht090subject", cht090subject__);
        msgForm.addHiddenParam("cht090subjectGroup", cht090subjectGroup__);
        msgForm.addHiddenParam("cht090accessUser", cht090accessUser__);
        msgForm.addHiddenParam("cht090accessGroup", cht090accessGroup__);
    }

}
