package jp.groupsession.v2.prj.prj140;

import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.prj.prj130.Prj130ParamModel;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトテンプレート登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj140ParamModel extends Prj130ParamModel {

    /** 処理モード */
    private String prj140cmdMode__ = null;
    /** 所属メンバー SIDのみ */
    private String[] prj140hdnMemberSid__;
    /** 所属メンバー UI*/
    private UserGroupSelector prj140hdnMemberUI__ = null;
    /** プロジェクト管理者 UI*/
    private Prj140AdminMemberSelector prj140adminSelectUI__ = null;
    /** グループSID 管理者 */
    private int prj140groupSidAdm__ = Integer.parseInt(GSConstBulletin.GROUP_COMBO_VALUE_USER);

    /**
     * <p>prj140cmdMode を取得します。
     * @return prj140cmdMode
     */
    public String getPrj140cmdMode() {
        return prj140cmdMode__;
    }

    /**
     * <p>prj140cmdMode をセットします。
     * @param prj140cmdMode prj140cmdMode
     */
    public void setPrj140cmdMode(String prj140cmdMode) {
        prj140cmdMode__ = prj140cmdMode;
    }

    /**
     * <p>prj140hdnMemberSid を取得します。
     * @return prj140hdnMemberSid
     * @see jp.groupsession.v2.prj.prj140.Prj140ParamModel#prj140hdnMemberSid__
     */
    public String[] getPrj140hdnMemberSid() {
        return prj140hdnMemberSid__;
    }

    /**
     * <p>prj140hdnMemberSid をセットします。
     * @param prj140hdnMemberSid prj140hdnMemberSid
     * @see jp.groupsession.v2.prj.prj140.Prj140ParamModel#prj140hdnMemberSid__
     */
    public void setPrj140hdnMemberSid(String[] prj140hdnMemberSid) {
        prj140hdnMemberSid__ = prj140hdnMemberSid;
    }

    /**
     * <p>prj140hdnMemberUI を取得します。
     * @return prj140hdnMemberUI
     * @see jp.groupsession.v2.prj.prj140.Prj140ParamModel#prj140hdnMemberUI__
     */
    public UserGroupSelector getPrj140hdnMemberUI() {
        return prj140hdnMemberUI__;
    }

    /**
     * <p>prj140hdnMemberUI をセットします。
     * @param prj140hdnMemberUI prj140hdnMemberUI
     * @see jp.groupsession.v2.prj.prj140.Prj140ParamModel#prj140hdnMemberUI__
     */
    public void setPrj140hdnMemberUI(UserGroupSelector prj140hdnMemberUI) {
        prj140hdnMemberUI__ = prj140hdnMemberUI;
    }

    /**
     * <p>prj140adminSelectUI を取得します。
     * @return prj140adminSelectUI
     * @see jp.groupsession.v2.prj.prj140.Prj140ParamModel#prj140adminSelectUI__
     */
    public Prj140AdminMemberSelector getPrj140adminSelectUI() {
        return prj140adminSelectUI__;
    }

    /**
     * <p>prj140adminSelectUI をセットします。
     * @param prj140adminSelectUI prj140adminSelectUI
     * @see jp.groupsession.v2.prj.prj140.Prj140ParamModel#prj140adminSelectUI__
     */
    public void setPrj140adminSelectUI(
            Prj140AdminMemberSelector prj140adminSelectUI) {
        prj140adminSelectUI__ = prj140adminSelectUI;
    }

    /**
     * <p>prj140groupSidAdm を取得します。
     * @return prj140groupSidAdm
     * @see jp.groupsession.v2.prj.prj140.Prj140ParamModel#prj140groupSidAdm__
     */
    public int getPrj140groupSidAdm() {
        return prj140groupSidAdm__;
    }

    /**
     * <p>prj140groupSidAdm をセットします。
     * @param prj140groupSidAdm prj140groupSidAdm
     * @see jp.groupsession.v2.prj.prj140.Prj140ParamModel#prj140groupSidAdm__
     */
    public void setPrj140groupSidAdm(int prj140groupSidAdm) {
        prj140groupSidAdm__ = prj140groupSidAdm;
    }
}