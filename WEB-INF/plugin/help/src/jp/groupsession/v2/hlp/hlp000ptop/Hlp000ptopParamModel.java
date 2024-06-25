package jp.groupsession.v2.hlp.hlp000ptop;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.hlp.hlp000.MenuInfo;

/**
 * <br>[機  能] ヘルプ トップメニューの情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp000ptopParamModel extends AbstractParamModel {
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