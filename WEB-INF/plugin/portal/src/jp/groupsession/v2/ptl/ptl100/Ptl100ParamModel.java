package jp.groupsession.v2.ptl.ptl100;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.ptl.model.PtlPortletImageModel;
import jp.groupsession.v2.ptl.ptl090.Ptl090ParamModel;

/**
 * <br>[機  能] ポータル ポートレット登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl100ParamModel extends Ptl090ParamModel {
    /** 選択したポートレット画像SID */
    private long pltPortletImageSid__ = -1;

    /** ポートレット 内容 文章を入力 */
    public static final int PTL100_CONTENTTYPE_INPUT = 0;
    /** ポートレット 内容 URL */
    public static final int PTL100_CONTENTTYPE_URL = 1;
    /** ポートレット 内容 HTML */
    public static final int PTL100_CONTENTTYPE_HTML = 2;

    /** ポートレット タイトル */
    private String ptl100name__ = "";
    /** ポートレット 入力内容 */
    private String ptl100content__ = "";
    /** ポートレット 入力内容にプラスする画像タグ */
    private String ptl100contentPlusImage__ = "";
    /** ポートレット 枠線区分   */
    private int ptl100border__ = 0;
    /** ポートレット 説明 */
    private String ptl100description__ = "";
    /** ポートレット 登録カテゴリSID   */
    private int ptl100category__ = 0;

    /** ポートレット 内容 入力形式 */
    private int ptl100contentType__ = PTL100_CONTENTTYPE_INPUT;
    /** ポートレット 内容 URL */
    private String ptl100contentUrl__ = null;
    /** ポートレット 内容 HTML */
    private String ptl100contentHtml__ = null;


    /** 初期表示フラグ */
    private int ptl100init__ = 0;
    /** 内容に挿入したファイルの一時保存ディレクトリID */
    private String ptl100TempSaveId__ = null;

    /** カテゴリコンボボックス */
    ArrayList<LabelValueBean> ptl100CategoryList__ = null;
    /** 画像一覧 */
    private List<PtlPortletImageModel> ptl100ImageList__ = null;
    /**
     * @return ptl100name
     */
    public String getPtl100name() {
        return ptl100name__;
    }

    /**
     * @param ptl100name セットする ptl100name
     */
    public void setPtl100name(String ptl100name) {
        ptl100name__ = ptl100name;
    }

    /**
     * @return ptl100content
     */
    public String getPtl100content() {
        return ptl100content__;
    }

    /**
     * @param ptl100content セットする ptl100content
     */
    public void setPtl100content(String ptl100content) {
        ptl100content__ = ptl100content;
    }

    /**
     * @return ptl100border
     */
    public int getPtl100border() {
        return ptl100border__;
    }

    /**
     * @param ptl100border セットする ptl100border
     */
    public void setPtl100border(int ptl100border) {
        ptl100border__ = ptl100border;
    }

    /**
     * @return ptl100description
     */
    public String getPtl100description() {
        return ptl100description__;
    }

    /**
     * @param ptl100description セットする ptl100description
     */
    public void setPtl100description(String ptl100description) {
        ptl100description__ = ptl100description;
    }

    /**
     * @return ptl100category
     */
    public int getPtl100category() {
        return ptl100category__;
    }

    /**
     * @param ptl100category セットする ptl100category
     */
    public void setPtl100category(int ptl100category) {
        ptl100category__ = ptl100category;
    }

    /**
     * @return ptl100init
     */
    public int getPtl100init() {
        return ptl100init__;
    }

    /**
     * @param ptl100init セットする ptl100init
     */
    public void setPtl100init(int ptl100init) {
        ptl100init__ = ptl100init;
    }

    /**
     * @return ptl100CategoryList
     */
    public ArrayList<LabelValueBean> getPtl100CategoryList() {
        return ptl100CategoryList__;
    }

    /**
     * @param ptl100CategoryList セットする ptl100CategoryList
     */
    public void setPtl100CategoryList(ArrayList<LabelValueBean> ptl100CategoryList) {
        ptl100CategoryList__ = ptl100CategoryList;
    }

    /**
     * <p>ptl100contentType を取得します。
     * @return ptl100contentType
     */
    public int getPtl100contentType() {
        return ptl100contentType__;
    }

    /**
     * <p>ptl100contentType をセットします。
     * @param ptl100contentType ptl100contentType
     */
    public void setPtl100contentType(int ptl100contentType) {
        ptl100contentType__ = ptl100contentType;
    }

    /**
     * <p>ptl100contentUrl を取得します。
     * @return ptl100contentUrl
     */
    public String getPtl100contentUrl() {
        return ptl100contentUrl__;
    }

    /**
     * <p>ptl100contentUrl をセットします。
     * @param ptl100contentUrl ptl100contentUrl
     */
    public void setPtl100contentUrl(String ptl100contentUrl) {
        ptl100contentUrl__ = ptl100contentUrl;
    }

    /**
     * <p>pltPortletImageSid を取得します。
     * @return pltPortletImageSid
     */
    public long getPltPortletImageSid() {
        return pltPortletImageSid__;
    }

    /**
     * <p>pltPortletImageSid をセットします。
     * @param pltPortletImageSid pltPortletImageSid
     */
    public void setPltPortletImageSid(long pltPortletImageSid) {
        pltPortletImageSid__ = pltPortletImageSid;
    }

    /**
     * <p>ptl100contentPlusImage を取得します。
     * @return ptl100contentPlusImage
     */
    public String getPtl100contentPlusImage() {
        return ptl100contentPlusImage__;
    }

    /**
     * <p>ptl100contentPlusImage をセットします。
     * @param ptl100contentPlusImage ptl100contentPlusImage
     */
    public void setPtl100contentPlusImage(String ptl100contentPlusImage) {
        ptl100contentPlusImage__ = ptl100contentPlusImage;
    }

    /**
     * @return ptl100contentHtml
     */
    public String getPtl100contentHtml() {
        return ptl100contentHtml__;
    }

    /**
     * @param ptl100contentHtml セットする ptl100contentHtml
     */
    public void setPtl100contentHtml(String ptl100contentHtml) {
        ptl100contentHtml__ = ptl100contentHtml;
    }

    /**
     * <p>ptl100TempSaveId を取得します。
     * @return ptl100TempSaveId
     * @see jp.groupsession.v2.ptl.ptl100.Ptl100ParamModel#ptl100TempSaveId__
     */
    public String getPtl100TempSaveId() {
        return ptl100TempSaveId__;
    }

    /**
     * <p>ptl100TempSaveId をセットします。
     * @param ptl100TempSaveId ptl100TempSaveId
     * @see jp.groupsession.v2.ptl.ptl100.Ptl100ParamModel#ptl100TempSaveId__
     */
    public void setPtl100TempSaveId(String ptl100TempSaveId) {
        ptl100TempSaveId__ = ptl100TempSaveId;
    }

    /**
     * <p>ptl100ImageList を取得します。
     * @return ptl100ImageList
     */
    public List<PtlPortletImageModel> getPtl100ImageList() {
        return ptl100ImageList__;
    }

    /**
     * <p>ptl100ImageList をセットします。
     * @param ptl100ImageList ptl100ImageList
     */
    public void setPtl100ImageList(List<PtlPortletImageModel> ptl100ImageList) {
        ptl100ImageList__ = ptl100ImageList;
    }
}