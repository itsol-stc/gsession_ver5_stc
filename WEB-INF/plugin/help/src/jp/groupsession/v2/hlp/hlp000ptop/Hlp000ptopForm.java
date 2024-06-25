package jp.groupsession.v2.hlp.hlp000ptop;

import jp.groupsession.v2.hlp.hlp000.MenuInfo;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] ヘルプ トップメニューのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp000ptopForm extends AbstractGsForm {

    /** メニュー情報 */
    private MenuInfo menuInfo__ = null;

    /** プラグインID */
    private String pluginId__ = null;

    /**
     * <p>menuInfo を取得します。
     * @return menuInfo
     */
    public MenuInfo getMenuInfo() {
        return menuInfo__;
    }

    /**
     * <p>menuInfo をセットします。
     * @param menuInfo menuInfo
     */
    public void setMenuInfo(MenuInfo menuInfo) {
        menuInfo__ = menuInfo;
    }

    /**
     * <p>pluginId を取得します。
     * @return pluginId
     */
    public String getPluginId() {
        return pluginId__;
    }

    /**
     * <p>pluginId をセットします。
     * @param pluginId pluginId
     */
    public void setPluginId(String pluginId) {
        pluginId__ = pluginId;
    }


}
