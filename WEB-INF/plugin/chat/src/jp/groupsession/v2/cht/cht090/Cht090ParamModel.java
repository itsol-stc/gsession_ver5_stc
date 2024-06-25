package jp.groupsession.v2.cht.cht090;


import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cht.cht080.Cht080ParamModel;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;


/**
 * <br>[機  能] チャット 特例アクセス登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht090ParamModel extends Cht080ParamModel {


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
    private UserGroupSelector cht090subjectUI__ = null;

    /** 許可ユーザ  */
    private String[] cht090accessUser__ = null;
    /** 許可ユーザ グループ */
    private int cht090accessGroup__  = -1;
    /** 許可ユーザ UI */
    private UserGroupSelector cht090accessUserUI__ = null;


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
     * @see jp.groupsession.v2.cht.cht090.Cht090ParamModel#cht090subjectUI__
     */
    public UserGroupSelector getCht090subjectUI() {
        return cht090subjectUI__;
    }
    /**
     * <p>cht090subjectUI をセットします。
     * @param cht090subjectUI cht090subjectUI
     * @see jp.groupsession.v2.cht.cht090.Cht090ParamModel#cht090subjectUI__
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
     * @see jp.groupsession.v2.cht.cht090.Cht090ParamModel#cht090accessUserUI__
     */
    public UserGroupSelector getCht090accessUserUI() {
        return cht090accessUserUI__;
    }
    /**
     * <p>cht090accessUserUI をセットします。
     * @param cht090accessUserUI cht090accessUserUI
     * @see jp.groupsession.v2.cht.cht090.Cht090ParamModel#cht090accessUserUI__
     */
    public void setCht090accessUserUI(UserGroupSelector cht090accessUserUI) {
        cht090accessUserUI__ = cht090accessUserUI;
    }
}
