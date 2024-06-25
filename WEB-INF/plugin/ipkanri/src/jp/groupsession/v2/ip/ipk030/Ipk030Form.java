package jp.groupsession.v2.ip.ipk030;

import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.ip.ipk020.Ipk020Form;

/**
 * <br>[機  能] IP管理 全ネットワーク管理者設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk030Form extends Ipk020Form implements ISelectorUseForm {
    /** ネットワーク管理者 UI */
    private UserGroupSelector adminSidListUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("ipk.ipk030.2", null))
                .chainType(EnumSelectType.USER)
                .chainSelect(
                        Select.builder()
                        .chainParameterName(
                                "adminSidList")
                    )
                .chainGroupSelectionParamName("groupSelect")
                .build();

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