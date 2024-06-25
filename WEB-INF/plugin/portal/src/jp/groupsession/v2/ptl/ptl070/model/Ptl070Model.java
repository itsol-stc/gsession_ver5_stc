package jp.groupsession.v2.ptl.ptl070.model;
import java.io.Serializable;

/**
 * <p>ポータルプレビューモデル
 *
 * @author JTS
 */
/**
 * <br>[機  能] ポータル プレビュー(ポップアップ)画面で表示する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl070Model implements Serializable {

    /** アイテムID */
    private String ptpItemid__;
    /** 表示区分 */
    private int ptpView__;
    /** 番号 */
    private String num__;
    /** 2=プラグインポートレット 1=プラグイン 0=ポートレット */
    private int partsKbn__;

    //プラグインの場合のみ設定
    /** プラグインID */
    private String pluginId__ = null;
    /** プラグイン名称 */
    private String pluginName__ = null;
    /** 画面URL */
    private String screenUrl__ = null;
    /** ID */
    private String id__ = null;
    //プラグインの場合ここまで

    //ポートレットの場合
    /** ポートレット タイトル */
    private String ptlTitle__ = null;
    /** ポートレット 内容 */
    private String ptlContent__ = null;
    /** ポートレット 区分 */
    private int ptlType__ = 0;

    /** ポートレット 枠線区分 0=あり 1=なし */
    private int ptlBorderKbn__ = 0;
    //ポートレットの場合ここまで


    /**
     * <p>ptpItemid を取得します。
     * @return ptpItemid
     */
    public String getPtpItemid() {
        return ptpItemid__;
    }
    /**
     * <p>ptpItemid をセットします。
     * @param ptpItemid ptpItemid
     */
    public void setPtpItemid(String ptpItemid) {
        ptpItemid__ = ptpItemid;
    }
    /**
     * <p>num を取得します。
     * @return num
     */
    public String getNum() {
        return num__;
    }
    /**
     * <p>num をセットします。
     * @param num num
     */
    public void setNum(String num) {
        num__ = num;
    }
    /**
     * <p>ptpView を取得します。
     * @return ptpView
     */
    public int getPtpView() {
        return ptpView__;
    }
    /**
     * <p>ptpView をセットします。
     * @param ptpView ptpView
     */
    public void setPtpView(int ptpView) {
        ptpView__ = ptpView;
    }
    /**
     * @return partsKbn
     */
    public int getPartsKbn() {
        return partsKbn__;
    }
    /**
     * @param partsKbn セットする partsKbn
     */
    public void setPartsKbn(int partsKbn) {
        partsKbn__ = partsKbn;
    }
    /**
     * @return pluginId
     */
    public String getPluginId() {
        return pluginId__;
    }
    /**
     * @param pluginId セットする pluginId
     */
    public void setPluginId(String pluginId) {
        pluginId__ = pluginId;
    }
    /**
     * @return pluginName
     */
    public String getPluginName() {
        return pluginName__;
    }
    /**
     * @param pluginName セットする pluginName
     */
    public void setPluginName(String pluginName) {
        pluginName__ = pluginName;
    }
    /**
     * @return screenUrl
     */
    public String getScreenUrl() {
        return screenUrl__;
    }
    /**
     * @param screenUrl セットする screenUrl
     */
    public void setScreenUrl(String screenUrl) {
        screenUrl__ = screenUrl;
    }
    /**
     * @return id
     */
    public String getId() {
        return id__;
    }
    /**
     * @param id セットする id
     */
    public void setId(String id) {
        id__ = id;
    }
    /**
     * @return ptlContent
     */
    public String getPtlContent() {
        return ptlContent__;
    }
    /**
     * @param ptlContent セットする ptlContent
     */
    public void setPtlContent(String ptlContent) {
        ptlContent__ = ptlContent;
    }
    /**
     * @return ptlBorderKbn
     */
    public int getPtlBorderKbn() {
        return ptlBorderKbn__;
    }
    /**
     * @param ptlBorderKbn セットする ptlBorderKbn
     */
    public void setPtlBorderKbn(int ptlBorderKbn) {
        ptlBorderKbn__ = ptlBorderKbn;
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
     * @see jp.groupsession.v2.ptl.ptl070.model.Ptl070Model#ptlType__
     */
    public int getPtlType() {
        return ptlType__;
    }
    /**
     * <p>ptlType をセットします。
     * @param ptlType ptlType
     * @see jp.groupsession.v2.ptl.ptl070.model.Ptl070Model#ptlType__
     */
    public void setPtlType(int ptlType) {
        ptlType__ = ptlType;
    }

}
