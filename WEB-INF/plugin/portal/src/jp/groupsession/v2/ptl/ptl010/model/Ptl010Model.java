package jp.groupsession.v2.ptl.ptl010.model;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.man.GSConstPortal;

/**
 * <br>[機  能] ポータルの画面表示用情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl010Model extends AbstractModel {

    /** 種別 */
    private int ptpType__ = GSConstPortal.PTP_TYPE_PLUGIN;

    /** プラグインID */
    private String pluginId__ = null;
    /** 画面ID */
    private String screenId__ = null;
    /** プラグイン名称 */
    private String pluginName__ = null;
    /** 画面URL */
    private String screenUrl__ = null;
    /** ID */
    private String itemId__ = null;
    /** 表示の際、scriptの実行を行うか */
    private boolean loadScript__ = false;

    //ポートレットの場合
    /** ポートレット タイトル */
    private String ptlTitle__ = null;
    /** ポートレット 内容 */
    private String ptlContent__ = null;
    /** ポートレット 区分 */
    private int ptlType__ = 0;
    /** ポートレット 枠線区分 0=あり 1=なし */
    private int ptlBorderKbn__ = 0;

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
     * <p>pluginName を取得します。
     * @return pluginName
     */
    public String getPluginName() {
        return pluginName__;
    }
    /**
     * <p>pluginName をセットします。
     * @param pluginName pluginName
     */
    public void setPluginName(String pluginName) {
        pluginName__ = pluginName;
    }
    /**
     * <p>screenUrl を取得します。
     * @return screenUrl
     */
    public String getScreenUrl() {
        return screenUrl__;
    }
    /**
     * <p>screenUrl をセットします。
     * @param screenUrl screenUrl
     */
    public void setScreenUrl(String screenUrl) {
        screenUrl__ = screenUrl;
    }
    /**
     * <p>itemId を取得します。
     * @return itemId
     */
    public String getItemId() {
        return itemId__;
    }
    /**
     * <p>itemId をセットします。
     * @param itemId itemId
     */
    public void setItemId(String itemId) {
        itemId__ = itemId;
    }
    /**
     * <p>loadScript を取得します。
     * @return loadScript
     */
    public boolean isLoadScript() {
        return loadScript__;
    }
    /**
     * <p>loadScript をセットします。
     * @param loadScript loadScript
     */
    public void setLoadScript(boolean loadScript) {
        loadScript__ = loadScript;
    }
    /**
     * <p>ptpType を取得します。
     * @return ptpType
     */
    public int getPtpType() {
        return ptpType__;
    }
    /**
     * <p>ptpType をセットします。
     * @param ptpType ptpType
     */
    public void setPtpType(int ptpType) {
        ptpType__ = ptpType;
    }
    /**
     * <p>ptlContent を取得します。
     * @return ptlContent
     */
    public String getPtlContent() {
        return ptlContent__;
    }
    /**
     * <p>ptlContent をセットします。
     * @param ptlContent ptlContent
     */
    public void setPtlContent(String ptlContent) {
        ptlContent__ = ptlContent;
    }
    /**
     * <p>ptlBorderKbn を取得します。
     * @return ptlBorderKbn
     */
    public int getPtlBorderKbn() {
        return ptlBorderKbn__;
    }
    /**
     * <p>ptlBorderKbn をセットします。
     * @param ptlBorderKbn ptlBorderKbn
     */
    public void setPtlBorderKbn(int ptlBorderKbn) {
        ptlBorderKbn__ = ptlBorderKbn;
    }
    /**
     * <p>screenId を取得します。
     * @return screenId
     */
    public String getScreenId() {
        return screenId__;
    }
    /**
     * <p>screenId をセットします。
     * @param screenId screenId
     */
    public void setScreenId(String screenId) {
        screenId__ = screenId;
    }
    /**
     * @return ptlTitle
     */
    public String getPtlTitle() {
        return ptlTitle__;
    }
    /**
     * @param ptlTitle セットする ptlTitle
     */
    public void setPtlTitle(String ptlTitle) {
        ptlTitle__ = ptlTitle;
    }
    /**
     * <p>ptlType を取得します。
     * @return ptlType
     * @see jp.groupsession.v2.ptl.ptl010.model.Ptl010Model#ptlType__
     */
    public int getPtlType() {
        return ptlType__;
    }
    /**
     * <p>ptlType をセットします。
     * @param ptlType ptlType
     * @see jp.groupsession.v2.ptl.ptl010.model.Ptl010Model#ptlType__
     */
    public void setPtlType(int ptlType) {
        ptlType__ = ptlType;
    }

}
