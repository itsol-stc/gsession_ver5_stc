package jp.groupsession.v2.hlp.hlp001;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] ヘルプ フレーム(ヘッダーとボディの)の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp001ParamModel extends AbstractParamModel {
    /** トップメニューのページ */
    private int menuPage__ = 1;
    /** BODYに表示するURL */
    private String url__ = null;

    /** プラグインID（ヘルプ用） */
    private String pluginid__ = null;
    /** プログラムID（ヘルプ用） */
    private String pgid__ = null;

    /**
     * <p>pgid を取得します。
     * @return pgid
     */
    public String getPgid() {
        return pgid__;
    }

    /**
     * <p>pgid をセットします。
     * @param pgid pgid
     */
    public void setPgid(String pgid) {
        pgid__ = pgid;
    }

    /**
     * <p>menuPage を取得します。
     * @return menuPage
     */
    public int getMenuPage() {
        return menuPage__;
    }

    /**
     * <p>menuPage をセットします。
     * @param menuPage menuPage
     */
    public void setMenuPage(int menuPage) {
        menuPage__ = menuPage;
    }

    /**
     * @return url を戻します。
     */
    public String getUrl() {
        return url__;
    }

    /**
     * @param url 設定する url。
     */
    public void setUrl(String url) {
        url__ = url;
    }
    /**
     * <p>pluginid を取得します。
     * @return pluginid
     */
    public String getPluginid() {
        return pluginid__;
    }

    /**
     * <p>pluginid をセットします。
     * @param pluginid pluginid
     */
    public void setPluginid(String pluginid) {
        pluginid__ = pluginid;
    }
}