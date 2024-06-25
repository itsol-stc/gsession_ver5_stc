package jp.groupsession.v2.ptl.ptl100kn;

import jp.groupsession.v2.ptl.ptl100.Ptl100ParamModel;

/**
 * <br>[機  能] ポータル ポートレット登録確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl100knParamModel extends Ptl100ParamModel {
    /** ポートレットカテゴリ名 */
    private String ptl100knCategoryName__ = "";
    /** ポートレット 入力内容 */
    private String ptl100knContent__ = "";
    /** ポートレット 内容 URL */
    private String ptl100knContentUrl__ = "";
    /** ポートレット 内容 HTML */
    private String ptl100knContentHtml__ = "";
    /** ポートレット 説明(表示用) */
    private String ptl100knDescription__ = "";

    /**
     * @return ptl100knCategoryName
     */
    public String getPtl100knCategoryName() {
        return ptl100knCategoryName__;
    }

    /**
     * @param ptl100knCategoryName セットする ptl100knCategoryName
     */
    public void setPtl100knCategoryName(String ptl100knCategoryName) {
        ptl100knCategoryName__ = ptl100knCategoryName;
    }

    /**
     * <p>ptl100knContentUrl を取得します。
     * @return ptl100knContentUrl
     */
    public String getPtl100knContentUrl() {
        return ptl100knContentUrl__;
    }

    /**
     * <p>ptl100knContentUrl をセットします。
     * @param ptl100knContentUrl ptl100knContentUrl
     */
    public void setPtl100knContentUrl(String ptl100knContentUrl) {
        ptl100knContentUrl__ = ptl100knContentUrl;
    }

    /**
     * @return ptl100knContentHtml
     */
    public String getPtl100knContentHtml() {
        return ptl100knContentHtml__;
    }

    /**
     * @param ptl100knContentHtml セットする ptl100knContentHtml
     */
    public void setPtl100knContentHtml(String ptl100knContentHtml) {
        ptl100knContentHtml__ = ptl100knContentHtml;
    }


    /**
     * <p>ptl100knContent を取得します。
     * @return ptl100knContent
     */
    public String getPtl100knContent() {
        return ptl100knContent__;
    }

    /**
     * <p>ptl100knContent をセットします。
     * @param ptl100knContent ptl100knContent
     */
    public void setPtl100knContent(String ptl100knContent) {
        ptl100knContent__ = ptl100knContent;
    }

    /**
     * @return ptl100knDescription
     */
    public String getPtl100knDescription() {
        return ptl100knDescription__;
    }

    /**
     * @param ptl100knDescription セットする ptl100knDescription
     */
    public void setPtl100knDescription(String ptl100knDescription) {
        ptl100knDescription__ = ptl100knDescription;
    }
}