package jp.groupsession.v2.sch.sch240;

import java.util.List;

import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.sch.sch230.Sch230ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] スケジュール 特例アクセス登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch240ParamModel extends Sch230ParamModel {
    /** 初期表示 */
    private int sch240initFlg__ = 0;
    /** 特例アクセス名称 */
    private String sch240name__ = null;
    /** 備考 */
    private String sch240biko__ = null;
    /** 役職 */
    private int sch240position__ = 0;
    /** 役職 権限区分 */
    private int sch240positionAuth__ = 0;
    /** 役職コンボ */
    private List<LabelValueBean> sch240positionCombo__  = null;

    /** 制限対象 */
    private String[] sch240subject__ = null;
    /** 制限対象 グループ */
    private int sch240subjectGroup__  = -1;
    /** 制限対象(選択用) */
    private String[] sch240subjectSelect__  = null;
    /** 制限対象(未選択用) */
    private String[] sch240subjectNoSelect__ = null;

    /** 許可ユーザ 追加・変更・削除 */
    private String[] sch240editUser__ = null;
    /** 許可ユーザ 閲覧 */
    private String[] sch240accessUser__ = null;
    /** 許可ユーザ グループ */
    private int sch240accessGroup__  = -1;
    /** 許可ユーザ(選択用) */
    private String[] sch240editUserSelect__  = null;
    /** 許可ユーザ 追加・変更・削除(選択用) */
    private String[] sch240accessUserSelect__  = null;
    /** 許可ユーザ 閲覧(未選択用) */
    private String[] sch240accessUserNoSelect__ = null;
    /** 制限対象 ユーザコンボ */
    private List<UsrLabelValueBean> sch240subjectSelectCombo__  = null;
    /** 許可ユーザ 追加・変更・削除 ユーザコンボ */
    private List<UsrLabelValueBean> sch240editUserSelectCombo__  = null;
    /** 許可ユーザ 閲覧 ユーザコンボ */
    private List<UsrLabelValueBean> sch240accessSelectCombo__  = null;

    /** 制限ユーザ選択 UI*/
    private UserGroupSelector sch240subjectUI__ = null;
    /** 許可ユーザ選択 UI*/
    private UserGroupSelector sch240accessUI__ = null;

    /**
     * <p>sch240initFlg を取得します。
     * @return sch240initFlg
     */
    public int getSch240initFlg() {
        return sch240initFlg__;
    }

    /**
     * <p>sch240initFlg をセットします。
     * @param sch240initFlg sch240initFlg
     */
    public void setSch240initFlg(int sch240initFlg) {
        sch240initFlg__ = sch240initFlg;
    }

    /**
     * <p>sch240name を取得します。
     * @return sch240name
     */
    public String getSch240name() {
        return sch240name__;
    }

    /**
     * <p>sch240name をセットします。
     * @param sch240name sch240name
     */
    public void setSch240name(String sch240name) {
        sch240name__ = sch240name;
    }

    /**
     * <p>sch240biko を取得します。
     * @return sch240biko
     */
    public String getSch240biko() {
        return sch240biko__;
    }

    /**
     * <p>sch240biko をセットします。
     * @param sch240biko sch240biko
     */
    public void setSch240biko(String sch240biko) {
        sch240biko__ = sch240biko;
    }

    /**
     * <p>sch240position を取得します。
     * @return sch240position
     */
    public int getSch240position() {
        return sch240position__;
    }

    /**
     * <p>sch240position をセットします。
     * @param sch240position sch240position
     */
    public void setSch240position(int sch240position) {
        sch240position__ = sch240position;
    }

    /**
     * <p>sch240positionCombo を取得します。
     * @return sch240positionCombo
     */
    public List<LabelValueBean> getSch240positionCombo() {
        return sch240positionCombo__;
    }

    /**
     * <p>sch240positionCombo をセットします。
     * @param sch240positionCombo sch240positionCombo
     */
    public void setSch240positionCombo(List<LabelValueBean> sch240positionCombo) {
        sch240positionCombo__ = sch240positionCombo;
    }

    /**
     * <p>sch240subject を取得します。
     * @return sch240subject
     */
    public String[] getSch240subject() {
        return sch240subject__;
    }

    /**
     * <p>sch240subject をセットします。
     * @param sch240subject sch240subject
     */
    public void setSch240subject(String[] sch240subject) {
        sch240subject__ = sch240subject;
    }

    /**
     * <p>sch240subjectGroup を取得します。
     * @return sch240subjectGroup
     */
    public int getSch240subjectGroup() {
        return sch240subjectGroup__;
    }

    /**
     * <p>sch240subjectGroup をセットします。
     * @param sch240subjectGroup sch240subjectGroup
     */
    public void setSch240subjectGroup(int sch240subjectGroup) {
        sch240subjectGroup__ = sch240subjectGroup;
    }

    /**
     * <p>sch240subjectSelect を取得します。
     * @return sch240subjectSelect
     */
    public String[] getSch240subjectSelect() {
        return sch240subjectSelect__;
    }

    /**
     * <p>sch240subjectSelect をセットします。
     * @param sch240subjectSelect sch240subjectSelect
     */
    public void setSch240subjectSelect(String[] sch240subjectSelect) {
        sch240subjectSelect__ = sch240subjectSelect;
    }

    /**
     * <p>sch240subjectNoSelect を取得します。
     * @return sch240subjectNoSelect
     */
    public String[] getSch240subjectNoSelect() {
        return sch240subjectNoSelect__;
    }

    /**
     * <p>sch240subjectNoSelect をセットします。
     * @param sch240subjectNoSelect sch240subjectNoSelect
     */
    public void setSch240subjectNoSelect(String[] sch240subjectNoSelect) {
        sch240subjectNoSelect__ = sch240subjectNoSelect;
    }

    /**
     * <p>sch240accessUser を取得します。
     * @return sch240accessUser
     */
    public String[] getSch240accessUser() {
        return sch240accessUser__;
    }

    /**
     * <p>sch240accessUser をセットします。
     * @param sch240accessUser sch240accessUser
     */
    public void setSch240accessUser(String[] sch240accessUser) {
        sch240accessUser__ = sch240accessUser;
    }

    /**
     * <p>sch240accessGroup を取得します。
     * @return sch240accessGroup
     */
    public int getSch240accessGroup() {
        return sch240accessGroup__;
    }

    /**
     * <p>sch240accessGroup をセットします。
     * @param sch240accessGroup sch240accessGroup
     */
    public void setSch240accessGroup(int sch240accessGroup) {
        sch240accessGroup__ = sch240accessGroup;
    }

    /**
     * <p>sch240accessUserSelect を取得します。
     * @return sch240accessUserSelect
     */
    public String[] getSch240accessUserSelect() {
        return sch240accessUserSelect__;
    }

    /**
     * <p>sch240accessUserSelect をセットします。
     * @param sch240accessUserSelect sch240accessUserSelect
     */
    public void setSch240accessUserSelect(String[] sch240accessUserSelect) {
        sch240accessUserSelect__ = sch240accessUserSelect;
    }

    /**
     * <p>sch240accessUserNoSelect を取得します。
     * @return sch240accessUserNoSelect
     */
    public String[] getSch240accessUserNoSelect() {
        return sch240accessUserNoSelect__;
    }

    /**
     * <p>sch240accessUserNoSelect をセットします。
     * @param sch240accessUserNoSelect sch240accessUserNoSelect
     */
    public void setSch240accessUserNoSelect(String[] sch240accessUserNoSelect) {
        sch240accessUserNoSelect__ = sch240accessUserNoSelect;
    }

    /**
     * <p>sch240positionAuth を取得します。
     * @return sch240positionAuth
     */
    public int getSch240positionAuth() {
        return sch240positionAuth__;
    }

    /**
     * <p>sch240positionAuth をセットします。
     * @param sch240positionAuth sch240positionAuth
     */
    public void setSch240positionAuth(int sch240positionAuth) {
        sch240positionAuth__ = sch240positionAuth;
    }

    /**
     * <p>sch240editUser を取得します。
     * @return sch240editUser
     */
    public String[] getSch240editUser() {
        return sch240editUser__;
    }

    /**
     * <p>sch240editUser をセットします。
     * @param sch240editUser sch240editUser
     */
    public void setSch240editUser(String[] sch240editUser) {
        sch240editUser__ = sch240editUser;
    }

    /**
     * <p>sch240editUserSelect を取得します。
     * @return sch240editUserSelect
     */
    public String[] getSch240editUserSelect() {
        return sch240editUserSelect__;
    }

    /**
     * <p>sch240editUserSelect をセットします。
     * @param sch240editUserSelect sch240editUserSelect
     */
    public void setSch240editUserSelect(String[] sch240editUserSelect) {
        sch240editUserSelect__ = sch240editUserSelect;
    }
    /**
     * <p>sch240subjectSelectCombo を取得します。
     * @return sch240subjectSelectCombo
     * @see jp.groupsession.v2.sch.sch240.Sch240ParamModel#sch240subjectSelectCombo__
     */
    public List<UsrLabelValueBean> getSch240subjectSelectCombo() {
        return sch240subjectSelectCombo__;
    }

    /**
     * <p>sch240subjectSelectCombo をセットします。
     * @param sch240subjectSelectCombo sch240subjectSelectCombo
     * @see jp.groupsession.v2.sch.sch240.Sch240ParamModel#sch240subjectSelectCombo__
     */
    public void setSch240subjectSelectCombo(
            List<UsrLabelValueBean> sch240subjectSelectCombo) {
        sch240subjectSelectCombo__ = sch240subjectSelectCombo;
    }
    /**
     * <p>sch240editUserSelectCombo を取得します。
     * @return sch240editUserSelectCombo
     * @see jp.groupsession.v2.sch.sch240.Sch240ParamModel#sch240editUserSelectCombo__
     */
    public List<UsrLabelValueBean> getSch240editUserSelectCombo() {
        return sch240editUserSelectCombo__;
    }

    /**
     * <p>sch240editUserSelectCombo をセットします。
     * @param sch240editUserSelectCombo sch240editUserSelectCombo
     * @see jp.groupsession.v2.sch.sch240.Sch240ParamModel#sch240editUserSelectCombo__
     */
    public void setSch240editUserSelectCombo(
            List<UsrLabelValueBean> sch240editUserSelectCombo) {
        sch240editUserSelectCombo__ = sch240editUserSelectCombo;
    }

    /**
     * <p>sch240accessSelectCombo を取得します。
     * @return sch240accessSelectCombo
     * @see jp.groupsession.v2.sch.sch240.Sch240ParamModel#sch240accessSelectCombo__
     */
    public List<UsrLabelValueBean> getSch240accessSelectCombo() {
        return sch240accessSelectCombo__;
    }

    /**
     * <p>sch240accessSelectCombo をセットします。
     * @param sch240accessSelectCombo sch240accessSelectCombo
     * @see jp.groupsession.v2.sch.sch240.Sch240ParamModel#sch240accessSelectCombo__
     */
    public void setSch240accessSelectCombo(
            List<UsrLabelValueBean> sch240accessSelectCombo) {
        sch240accessSelectCombo__ = sch240accessSelectCombo;
    }

    /**
     * <p>sch240subjectUI を取得します。
     * @return sch240subjectUI
     * @see jp.groupsession.v2.sch.sch240.Sch240ParamModel#sch240subjectUI__
     */
    public UserGroupSelector getSch240subjectUI() {
        return sch240subjectUI__;
    }

    /**
     * <p>sch240subjectUI をセットします。
     * @param sch240subjectUI sch240subjectUI
     * @see jp.groupsession.v2.sch.sch240.Sch240ParamModel#sch240subjectUI__
     */
    public void setSch240subjectUI(UserGroupSelector sch240subjectUI) {
        sch240subjectUI__ = sch240subjectUI;
    }
    /**
     * <p>sch240accessUI を取得します。
     * @return sch240accessUI
     * @see jp.groupsession.v2.sch.sch240.Sch240ParamModel#sch240accessUI__
     */
    public UserGroupSelector getSch240accessUI() {
        return sch240accessUI__;
    }

    /**
     * <p>sch240accessUI をセットします。
     * @param sch240accessUI sch240accessUI
     * @see jp.groupsession.v2.sch.sch240.Sch240ParamModel#sch240accessUI__
     */
    public void setSch240accessUI(UserGroupSelector sch240accessUI) {
        sch240accessUI__ = sch240accessUI;
    }
}
