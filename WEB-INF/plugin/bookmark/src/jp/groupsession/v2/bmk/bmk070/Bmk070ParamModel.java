package jp.groupsession.v2.bmk.bmk070;

import java.util.List;

import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.bmk080.Bmk080ParamModel;
import jp.groupsession.v2.bmk.model.BmkBookmarkDataModel;
import jp.groupsession.v2.bmk.model.BmkUrlDataModel;

import org.apache.struts.util.LabelValueBean;


/**
 * <p>コメント・評価画面のフォーム
 * @author JTS
 */
public class Bmk070ParamModel extends Bmk080ParamModel {
    /** ブックマーク表示フラグ */
    private int bmk070NotViewBmk__ = 0;
    /** ページ */
    private int bmk070Page__;
    /** ページ上段 */
    private int bmk070PageTop__;
    /** ページ下段 */
    private int bmk070PageBottom__;
    /** ページラベル */
    private List<LabelValueBean> bmk070PageLabelList__;
    /** 並び順 */
    private int bmk070OrderKey__ = GSConstBookmark.ORDERKEY_ASC;
    /** ソートキー */
    private int bmk070SortKey__ = GSConstBookmark.SORTKEY_ADATE;
    /** URL登録情報 */
    private BmkUrlDataModel bmk070ResultUrl__ = null;
    /** コメント・評価一覧 */
    private List<BmkBookmarkDataModel> bmk070ResultList__ = null;
    /** 遷移元 */
    private String bmk070ReturnPage__;
    /** 写真ファイル名 */
    private String photoFileName__ = null;

    /**
     * <p>bmk070OrderKey を取得します。
     * @return bmk070OrderKey
     */
    public int getBmk070OrderKey() {
        return bmk070OrderKey__;
    }
    /**
     * <p>bmk070OrderKey をセットします。
     * @param bmk070OrderKey bmk070OrderKey
     */
    public void setBmk070OrderKey(int bmk070OrderKey) {
        bmk070OrderKey__ = bmk070OrderKey;
    }
    /**
     * <p>bmk070Page を取得します。
     * @return bmk070Page
     */
    public int getBmk070Page() {
        return bmk070Page__;
    }
    /**
     * <p>bmk070Page をセットします。
     * @param bmk070Page bmk070Page
     */
    public void setBmk070Page(int bmk070Page) {
        bmk070Page__ = bmk070Page;
    }
    /**
     * <p>bmk070PageBottom を取得します。
     * @return bmk070PageBottom
     */
    public int getBmk070PageBottom() {
        return bmk070PageBottom__;
    }
    /**
     * <p>bmk070PageBottom をセットします。
     * @param bmk070PageBottom bmk070PageBottom
     */
    public void setBmk070PageBottom(int bmk070PageBottom) {
        bmk070PageBottom__ = bmk070PageBottom;
    }
    /**
     * <p>bmk070PageLabelList を取得します。
     * @return bmk070PageLabelList
     */
    public List<LabelValueBean> getBmk070PageLabelList() {
        return bmk070PageLabelList__;
    }
    /**
     * <p>bmk070PageLabelList をセットします。
     * @param bmk070PageLabelList bmk070PageLabelList
     */
    public void setBmk070PageLabelList(List<LabelValueBean> bmk070PageLabelList) {
        bmk070PageLabelList__ = bmk070PageLabelList;
    }
    /**
     * <p>bmk070PageTop を取得します。
     * @return bmk070PageTop
     */
    public int getBmk070PageTop() {
        return bmk070PageTop__;
    }
    /**
     * <p>bmk070PageTop をセットします。
     * @param bmk070PageTop bmk070PageTop
     */
    public void setBmk070PageTop(int bmk070PageTop) {
        bmk070PageTop__ = bmk070PageTop;
    }
    /**
     * <p>bmk070SortKey を取得します。
     * @return bmk070SortKey
     */
    public int getBmk070SortKey() {
        return bmk070SortKey__;
    }
    /**
     * <p>bmk070SortKey をセットします。
     * @param bmk070SortKey bmk070SortKey
     */
    public void setBmk070SortKey(int bmk070SortKey) {
        bmk070SortKey__ = bmk070SortKey;
    }
    /**
     * <p>bmk070ResultList を取得します。
     * @return bmk070ResultList
     */
    public List<BmkBookmarkDataModel> getBmk070ResultList() {
        return bmk070ResultList__;
    }
    /**
     * <p>bmk070ResultList をセットします。
     * @param bmk070ResultList bmk070ResultList
     */
    public void setBmk070ResultList(List<BmkBookmarkDataModel> bmk070ResultList) {
        bmk070ResultList__ = bmk070ResultList;
    }
    /**
     * <p>bmk070ResultUrl を取得します。
     * @return bmk070ResultUrl
     */
    public BmkUrlDataModel getBmk070ResultUrl() {
        return bmk070ResultUrl__;
    }
    /**
     * <p>bmk070ResultUrl をセットします。
     * @param bmk070ResultUrl bmk070ResultUrl
     */
    public void setBmk070ResultUrl(BmkUrlDataModel bmk070ResultUrl) {
        bmk070ResultUrl__ = bmk070ResultUrl;
    }
    /**
     * <p>bmk070ReturnPage を取得します。
     * @return bmk070ReturnPage
     */
    public String getBmk070ReturnPage() {
        return bmk070ReturnPage__;
    }
    /**
     * <p>bmk070ReturnPage をセットします。
     * @param bmk070ReturnPage bmk070ReturnPage
     */
    public void setBmk070ReturnPage(String bmk070ReturnPage) {
        bmk070ReturnPage__ = bmk070ReturnPage;
    }
    /**
     * <p>photoFileName を取得します。
     * @return photoFileName
     */
    public String getPhotoFileName() {
        return photoFileName__;
    }
    /**
     * <p>photoFileName をセットします。
     * @param photoFileName photoFileName
     */
    public void setPhotoFileName(String photoFileName) {
        photoFileName__ = photoFileName;
    }
    /**
     * <p>bmk070NotViewBmk を取得します。
     * @return bmk070NotViewBmk
     */
    public int getBmk070NotViewBmk() {
        return bmk070NotViewBmk__;
    }
    /**
     * <p>bmk070NotViewBmk をセットします。
     * @param bmk070NotViewBmk bmk070NotViewBmk
     */
    public void setBmk070NotViewBmk(int bmk070NotViewBmk) {
        bmk070NotViewBmk__ = bmk070NotViewBmk;
    }
}
