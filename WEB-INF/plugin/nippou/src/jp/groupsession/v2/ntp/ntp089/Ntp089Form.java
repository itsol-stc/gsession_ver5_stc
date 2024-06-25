package jp.groupsession.v2.ntp.ntp089;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
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
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.ntp088.Ntp088Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;


/**
 * <br>[機  能] 日報 テンプレート登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp089Form extends Ntp088Form implements ISelectorUseForm {


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
    private int ntp089initFlg__ = 0;

    /** 特例アクセス名称 */
    private String ntp089name__ = null;
    /** 備考 */
    private String ntp089biko__ = null;
    /** 役職 */
    private int ntp089position__ = 0;
    /** 役職 権限区分 */
    private int ntp089positionAuth__ = 0;
    /** 役職コンボ */
    private List<LabelValueBean> ntp089positionCombo__  = null;

    /** 制限対象 */
    private String[] ntp089subject__ = null;
    /** 制限対象 グループ */
    private int ntp089subjectGroup__  = -1;
    /** 制限対象 UI */
    private UserGroupSelector ntp089subjectUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("schedule.sch240.05", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainSelect(
                        Select.builder()
                        .chainParameterName(
                                "ntp089subject")
                    )
                .chainGroupSelectionParamName("ntp089subjectGroup")
                .build();

    /** 許可ユーザ 追加・変更・削除 */
    private String[] ntp089editUser__ = null;
    /** 許可ユーザ 閲覧 */
    private String[] ntp089accessUser__ = null;
    /** 許可ユーザ グループ */
    private int ntp089accessGroup__  = -1;
    /** 許可ユーザ 閲覧 UI */
    private UserGroupSelector ntp089accessUserUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("schedule.sch240.05", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainSelect(
                        Select.builder()
                        .chainParameterName(
                                "ntp089accessUser")
                    )
                .chainGroupSelectionParamName("ntp089accessGroup")
                .build();


    /**
     * <p>ntp089initFlg を取得します。
     * @return ntp089initFlg
     */
    public int getNtp089initFlg() {
        return ntp089initFlg__;
    }
    /**
     * <p>ntp089initFlg をセットします。
     * @param ntp089initFlg ntp089initFlg
     */
    public void setNtp089initFlg(int ntp089initFlg) {
        ntp089initFlg__ = ntp089initFlg;
    }
    /**
     * <p>ntp089name を取得します。
     * @return ntp089name
     */
    public String getNtp089name() {
        return ntp089name__;
    }
    /**
     * <p>ntp089name をセットします。
     * @param ntp089name ntp089name
     */
    public void setNtp089name(String ntp089name) {
        ntp089name__ = ntp089name;
    }
    /**
     * <p>ntp089biko を取得します。
     * @return ntp089biko
     */
    public String getNtp089biko() {
        return ntp089biko__;
    }
    /**
     * <p>ntp089biko をセットします。
     * @param ntp089biko ntp089biko
     */
    public void setNtp089biko(String ntp089biko) {
        ntp089biko__ = ntp089biko;
    }
    /**
     * <p>ntp089position を取得します。
     * @return ntp089position
     */
    public int getNtp089position() {
        return ntp089position__;
    }
    /**
     * <p>ntp089position をセットします。
     * @param ntp089position ntp089position
     */
    public void setNtp089position(int ntp089position) {
        ntp089position__ = ntp089position;
    }
    /**
     * <p>ntp089positionAuth を取得します。
     * @return ntp089positionAuth
     */
    public int getNtp089positionAuth() {
        return ntp089positionAuth__;
    }
    /**
     * <p>ntp089positionAuth をセットします。
     * @param ntp089positionAuth ntp089positionAuth
     */
    public void setNtp089positionAuth(int ntp089positionAuth) {
        ntp089positionAuth__ = ntp089positionAuth;
    }
    /**
     * <p>ntp089positionCombo を取得します。
     * @return ntp089positionCombo
     */
    public List<LabelValueBean> getNtp089positionCombo() {
        return ntp089positionCombo__;
    }
    /**
     * <p>ntp089positionCombo をセットします。
     * @param ntp089positionCombo ntp089positionCombo
     */
    public void setNtp089positionCombo(List<LabelValueBean> ntp089positionCombo) {
        ntp089positionCombo__ = ntp089positionCombo;
    }
    /**
     * <p>ntp089subject を取得します。
     * @return ntp089subject
     */
    public String[] getNtp089subject() {
        return ntp089subject__;
    }
    /**
     * <p>ntp089subject をセットします。
     * @param ntp089subject ntp089subject
     */
    public void setNtp089subject(String[] ntp089subject) {
        ntp089subject__ = ntp089subject;
    }
    /**
     * <p>ntp089subjectGroup を取得します。
     * @return ntp089subjectGroup
     */
    public int getNtp089subjectGroup() {
        return ntp089subjectGroup__;
    }
    /**
     * <p>ntp089subjectGroup をセットします。
     * @param ntp089subjectGroup ntp089subjectGroup
     */
    public void setNtp089subjectGroup(int ntp089subjectGroup) {
        ntp089subjectGroup__ = ntp089subjectGroup;
    }
    /**
     * <p>ntp089subjectUI を取得します。
     * @return ntp089subjectUI
     * @see jp.groupsession.v2.ntp.ntp089.Ntp089Form#ntp089subjectUI__
     */
    public UserGroupSelector getNtp089subjectUI() {
        return ntp089subjectUI__;
    }
    /**
     * <p>ntp089subjectUI をセットします。
     * @param ntp089subjectUI ntp089subjectUI
     * @see jp.groupsession.v2.ntp.ntp089.Ntp089Form#ntp089subjectUI__
     */
    public void setNtp089subjectUI(UserGroupSelector ntp089subjectUI) {
        ntp089subjectUI__ = ntp089subjectUI;
    }
    /**
     * <p>ntp089editUser を取得します。
     * @return ntp089editUser
     */
    public String[] getNtp089editUser() {
        return ntp089editUser__;
    }
    /**
     * <p>ntp089editUser をセットします。
     * @param ntp089editUser ntp089editUser
     */
    public void setNtp089editUser(String[] ntp089editUser) {
        ntp089editUser__ = ntp089editUser;
    }
    /**
     * <p>ntp089accessUser を取得します。
     * @return ntp089accessUser
     */
    public String[] getNtp089accessUser() {
        return ntp089accessUser__;
    }
    /**
     * <p>ntp089accessUser をセットします。
     * @param ntp089accessUser ntp089accessUser
     */
    public void setNtp089accessUser(String[] ntp089accessUser) {
        ntp089accessUser__ = ntp089accessUser;
    }
    /**
     * <p>ntp089accessGroup を取得します。
     * @return ntp089accessGroup
     */
    public int getNtp089accessGroup() {
        return ntp089accessGroup__;
    }
    /**
     * <p>ntp089accessGroup をセットします。
     * @param ntp089accessGroup ntp089accessGroup
     */
    public void setNtp089accessGroup(int ntp089accessGroup) {
        ntp089accessGroup__ = ntp089accessGroup;
    }
    /**
     * <p>ntp089accessUserUI を取得します。
     * @return ntp089accessUserUI
     * @see jp.groupsession.v2.ntp.ntp089.Ntp089Form#ntp089accessUserUI__
     */
    public UserGroupSelector getNtp089accessUserUI() {
        return ntp089accessUserUI__;
    }
    /**
     * <p>ntp089accessUserUI をセットします。
     * @param ntp089accessUserUI ntp089accessUserUI
     * @see jp.groupsession.v2.ntp.ntp089.Ntp089Form#ntp089accessUserUI__
     */
    public void setNtp089accessUserUI(UserGroupSelector ntp089accessUserUI) {
        ntp089accessUserUI__ = ntp089accessUserUI;
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
        GSValidateNippou.validateCmnFieldText(
                errors,
                gsMsg.getMessage("schedule.sch240.04"),
                ntp089name__,
                "ntp089name",
                MAXLEN_NAME, true);

        //制限対象入力チェック
        __validateTargetCheck(errors, con, reqMdl);
        //アクセス許可入力チェック
        __validatePermitCheck(errors, con, reqMdl);

        //備考入力チェック
        GSValidateNippou.validateCmnFieldText(errors, ntp089biko__,
                "ntp089biko",
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
        if (ntp089subject__ != null) {
            for (String target : ntp089subject__) {
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
            GSValidateNippou.validateNoSelect(errors, null,
                    "ntp089subject",
                    gsMsg.getMessage("schedule.sch240.06"));

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

        if (ntp089accessUser__ != null) {
            for (String target : ntp089accessUser__) {
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

        if (ntp089editUser__ != null) {
            for (String target : ntp089editUser__) {
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
        if (ntp089position__ > 0 && pdao.getPosInfo(ntp089position__) != null) {
            count++;
        }

        if (count == 0) {
            GSValidateNippou.validateNoSelect(errors, null,
                    "ntp089accessUser",
                    gsMsg.getMessage("schedule.sch240.08"));
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
        msgForm.addHiddenParam("ntp089initFlg", ntp089initFlg__);
        msgForm.addHiddenParam("ntp089name", ntp089name__);
        msgForm.addHiddenParam("ntp089biko", ntp089biko__);
        msgForm.addHiddenParam("ntp089position", ntp089position__);
        msgForm.addHiddenParam("ntp089positionAuth", ntp089positionAuth__);
        msgForm.addHiddenParam("ntp089subject", ntp089subject__);
        msgForm.addHiddenParam("ntp089subjectGroup", ntp089subjectGroup__);
        msgForm.addHiddenParam("ntp089editUser", ntp089editUser__);
        msgForm.addHiddenParam("ntp089accessUser", ntp089accessUser__);
        msgForm.addHiddenParam("ntp089accessGroup", ntp089accessGroup__);
    }

}
