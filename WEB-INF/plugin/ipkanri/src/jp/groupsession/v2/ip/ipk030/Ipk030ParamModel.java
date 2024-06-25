package jp.groupsession.v2.ip.ipk030;

import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.ip.ipk020.Ipk020ParamModel;

/**
 * <br>[機  能] IP管理 全ネットワーク管理者設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ipk030ParamModel extends Ipk020ParamModel {
    /** ネットワーク管理者 UI */
    private UserGroupSelector adminSidListUI__ = null;
    /**
     * <p>adminSidListUI を取得します。
     * @return adminSidListUI
     */
    public UserGroupSelector getAdminSidListUI() {
        return adminSidListUI__;
    }
    /**
     * <p>adminSidListUI をセットします。
     * @param adminSidListUI adminSidListUI
     */
    public void setAdminSidListUI(UserGroupSelector adminSidListUI) {
        adminSidListUI__ = adminSidListUI;
    }
}