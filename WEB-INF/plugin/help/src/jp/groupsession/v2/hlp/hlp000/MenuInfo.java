package jp.groupsession.v2.hlp.hlp000;

/**
 * <br>[機  能] ヘルプ サイドメニュー プラグイン情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MenuInfo {

    /** プラグインID */
    private String pluginId__ = null;
    /** プラグイン名称 */
    private String name__ = null;
    /** URL */
    private String url__ = null;
    /** images URL */
    private String imagesUrl__ = null;
    /** 説明 */
    private String description__ = null;


    /**
     * <p>name を取得します。
     * @return name
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     */
    public void setName(String name) {
        name__ = name;
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
    /**
     * <p>url を取得します。
     * @return url
     */
    public String getUrl() {
        return url__;
    }
    /**
     * <p>url をセットします。
     * @param url url
     */
    public void setUrl(String url) {
        url__ = url;
    }
    /**
     * <p>imagesUrl を取得します。
     * @return imagesUrl
     */
    public String getImagesUrl() {
        return imagesUrl__;
    }
    /**
     * <p>imagesUrl をセットします。
     * @param imagesUrl imagesUrl
     */
    public void setImagesUrl(String imagesUrl) {
        imagesUrl__ = imagesUrl;
    }
    /**
     * <p>description を取得します。
     * @return description
     */
    public String getDescription() {
        return description__;
    }
    /**
     * <p>description をセットします。
     * @param description description
     */
    public void setDescription(String description) {
        description__ = description;
    }

}
