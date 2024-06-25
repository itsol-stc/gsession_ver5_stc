package jp.groupsession.v2.ntp.ntp089;


import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.ntp.ntp088.Ntp088ParamModel;


/**
 * <br>[機  能] 日報 テンプレート登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp089ParamModel extends Ntp088ParamModel {


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
    private UserGroupSelector ntp089subjectUI__ = null;

    /** 許可ユーザ 追加・変更・削除 */
    private String[] ntp089editUser__ = null;
    /** 許可ユーザ 閲覧 */
    private String[] ntp089accessUser__ = null;
    /** 許可ユーザ グループ */
    private int ntp089accessGroup__  = -1;
    /** 許可ユーザ 閲覧 UI */
    private UserGroupSelector ntp089accessUserUI__ = null;


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
     * @see jp.groupsession.v2.ntp.ntp089.Ntp089ParamModel#ntp089subjectUI__
     */
    public UserGroupSelector getNtp089subjectUI() {
        return ntp089subjectUI__;
    }
    /**
     * <p>ntp089subjectUI をセットします。
     * @param ntp089subjectUI ntp089subjectUI
     * @see jp.groupsession.v2.ntp.ntp089.Ntp089ParamModel#ntp089subjectUI__
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
     * @see jp.groupsession.v2.ntp.ntp089.Ntp089ParamModel#ntp089accessUserUI__
     */
    public UserGroupSelector getNtp089accessUserUI() {
        return ntp089accessUserUI__;
    }
    /**
     * <p>ntp089accessUserUI をセットします。
     * @param ntp089accessUserUI ntp089accessUserUI
     * @see jp.groupsession.v2.ntp.ntp089.Ntp089ParamModel#ntp089accessUserUI__
     */
    public void setNtp089accessUserUI(UserGroupSelector ntp089accessUserUI) {
        ntp089accessUserUI__ = ntp089accessUserUI;
    }
}
