package jp.groupsession.v2.cht.cht110kn;

import java.util.List;

import jp.groupsession.v2.cht.cht110.Cht110ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 *
 * <br>[機  能] チャットグループ追加/編集確認のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht110knParamModel extends Cht110ParamModel {

    /** カテゴリ名(表示用) */
    private String cht110knCategoryName__ = null;
    /** 備考(表示用) */
    private String cht110knBiko__ = null;
    /** 管理メンバー(表示用) */
    private List<UsrLabelValueBean> cht110knAdminMemberList__ = null;
    /** 一般メンバー(表示用) */
    private List<UsrLabelValueBean> cht110knGeneralMemberList__ = null;

    /**
     * <p>cht110knCategoryName を取得します。
     * @return cht110knCategoryName
     * @see jp.groupsession.v2.cht.cht110kn.Cht110knParamModel#cht110knCategoryName__
     */
    public String getCht110knCategoryName() {
        return cht110knCategoryName__;
    }

    /**
     * <p>cht110knCategoryName をセットします。
     * @param cht110knCategoryName cht110knCategoryName
     * @see jp.groupsession.v2.cht.cht110kn.Cht110knParamModel#cht110knCategoryName__
     */
    public void setCht110knCategoryName(String cht110knCategoryName) {
        cht110knCategoryName__ = cht110knCategoryName;
    }

    /**
     * <p>cht110knBiko を取得します。
     * @return cht110knBiko
     * @see jp.groupsession.v2.cht.cht110kn.Cht110knParamModel#cht110knBiko__
     */
    public String getCht110knBiko() {
        return cht110knBiko__;
    }

    /**
     * <p>cht110knBiko をセットします。
     * @param cht110knBiko cht110knBiko
     * @see jp.groupsession.v2.cht.cht110kn.Cht110knParamModel#cht110knBiko__
     */
    public void setCht110knBiko(String cht110knBiko) {
        cht110knBiko__ = cht110knBiko;
    }

    /**
     * <p>cht110knAdminMemberList を取得します。
     * @return cht110knAdminMemberList
     * @see jp.groupsession.v2.cht.cht110kn.Cht110knParamModel#cht110knAdminMemberList__
     */
    public List<UsrLabelValueBean> getCht110knAdminMemberList() {
        return cht110knAdminMemberList__;
    }

    /**
     * <p>cht110knAdminMemberList をセットします。
     * @param cht110knAdminMemberList cht110knAdminMemberList
     * @see jp.groupsession.v2.cht.cht110kn.Cht110knParamModel#cht110knAdminMemberList__
     */
    public void setCht110knAdminMemberList(
            List<UsrLabelValueBean> cht110knAdminMemberList) {
        cht110knAdminMemberList__ = cht110knAdminMemberList;
    }

    /**
     * <p>cht110knGeneralMemberList を取得します。
     * @return cht110knGeneralMemberList
     * @see jp.groupsession.v2.cht.cht110kn.Cht110knParamModel#cht110knGeneralMemberList__
     */
    public List<UsrLabelValueBean> getCht110knGeneralMemberList() {
        return cht110knGeneralMemberList__;
    }

    /**
     * <p>cht110knGeneralMemberList をセットします。
     * @param cht110knGeneralMemberList cht110knGeneralMemberList
     * @see jp.groupsession.v2.cht.cht110kn.Cht110knParamModel#cht110knGeneralMemberList__
     */
    public void setCht110knGeneralMemberList(
            List<UsrLabelValueBean> cht110knGeneralMemberList) {
        cht110knGeneralMemberList__ = cht110knGeneralMemberList;
    }
}
