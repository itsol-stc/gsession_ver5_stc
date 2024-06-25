package jp.groupsession.v2.hlp.hlp000top;

import java.util.List;

import jp.groupsession.v2.hlp.hlp000.MenuInfo;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] ヘルプ トップメニューのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp000topForm extends AbstractGsForm {

    /** メニュー情報 */
    private List<MenuInfo> menuInfoList__ = null;

    /**
     * <p>menuInfoList を取得します。
     * @return menuInfoList
     */
    public List<MenuInfo> getMenuInfoList() {
        return menuInfoList__;
    }

    /**
     * <p>menuInfoList をセットします。
     * @param menuInfoList menuInfoList
     */
    public void setMenuInfoList(List<MenuInfo> menuInfoList) {
        menuInfoList__ = menuInfoList;
    }

}
