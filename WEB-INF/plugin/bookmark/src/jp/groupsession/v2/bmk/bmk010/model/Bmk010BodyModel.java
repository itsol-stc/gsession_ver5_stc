package jp.groupsession.v2.bmk.bmk010.model;

import java.util.List;

import jp.groupsession.v2.bmk.model.BmkBookmarkModel;
import jp.groupsession.v2.bmk.model.BmkLabelModel;

/**
 * <br>[機  能] ブックマーク情報を保持するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk010BodyModel extends BmkBookmarkModel {

    /** タイトル(表示用) */
    private List<String> bmkTitleDspList__;

    /** URL */
    private String bmuUrl__;

    /** URL(表示用) */
    private List<String> bmuUrlDspList__;

    /** コメント改行区分 */
    private int bmkCmtBrKbn__;

    /** 登録人数 */
    private int bmkPerCount__;
    /** 個人ブックマーク登録区分 */
    private int bmkMyKbn__;

    /** ラベル一覧 */
    private List<BmkLabelModel> bmkLabelList__;

    /** 登録日(表示用) */
    private String bmkAdateDsp__;

    /**
     * <p>bmkMyKbn を取得します。
     * @return bmkMyKbn
     */
    public int getBmkMyKbn() {
        return bmkMyKbn__;
    }
    /**
     * <p>bmkMyKbn をセットします。
     * @param bmkMyKbn bmkMyKbn
     */
    public void setBmkMyKbn(int bmkMyKbn) {
        bmkMyKbn__ = bmkMyKbn;
    }
    /**
     * <p>bmkPerCount を取得します。
     * @return bmkPerCount
     */
    public int getBmkPerCount() {
        return bmkPerCount__;
    }
    /**
     * <p>bmkPerCount をセットします。
     * @param bmkPerCount bmkPerCount
     */
    public void setBmkPerCount(int bmkPerCount) {
        bmkPerCount__ = bmkPerCount;
    }
    /**
     * <p>bmuUrl を取得します。
     * @return bmuUrl
     */
    public String getBmuUrl() {
        return bmuUrl__;
    }
    /**
     * <p>bmuUrl をセットします。
     * @param bmuUrl bmuUrl
     */
    public void setBmuUrl(String bmuUrl) {
        bmuUrl__ = bmuUrl;
    }
    /**
     * <p>bmkLabelList を取得します。
     * @return bmkLabelList
     */
    public List<BmkLabelModel> getBmkLabelList() {
        return bmkLabelList__;
    }
    /**
     * <p>bmkLabelList をセットします。
     * @param bmkLabelList bmkLabelList
     */
    public void setBmkLabelList(List<BmkLabelModel> bmkLabelList) {
        bmkLabelList__ = bmkLabelList;
    }
    /**
     * <p>bmkAdateDsp を取得します。
     * @return bmkAdateDsp
     */
    public String getBmkAdateDsp() {
        return bmkAdateDsp__;
    }
    /**
     * <p>bmkAdateDsp をセットします。
     * @param bmkAdateDsp bmkAdateDsp
     */
    public void setBmkAdateDsp(String bmkAdateDsp) {
        bmkAdateDsp__ = bmkAdateDsp;
    }
    /**
     * <p>bmkCmtBrKbn を取得します。
     * @return bmkCmtBrKbn
     */
    public int getBmkCmtBrKbn() {
        return bmkCmtBrKbn__;
    }
    /**
     * <p>bmkCmtBrKbn をセットします。
     * @param bmkCmtBrKbn bmkCmtBrKbn
     */
    public void setBmkCmtBrKbn(int bmkCmtBrKbn) {
        bmkCmtBrKbn__ = bmkCmtBrKbn;
    }
    /**
     * <p>bmkTitleDspList を取得します。
     * @return bmkTitleDspList
     */
    public List<String> getBmkTitleDspList() {
        return bmkTitleDspList__;
    }
    /**
     * <p>bmkTitleDspList をセットします。
     * @param bmkTitleDspList bmkTitleDspList
     */
    public void setBmkTitleDspList(List<String> bmkTitleDspList) {
        bmkTitleDspList__ = bmkTitleDspList;
    }
    /**
     * <p>bmuUrlDspList を取得します。
     * @return bmuUrlDspList
     */
    public List<String> getBmuUrlDspList() {
        return bmuUrlDspList__;
    }
    /**
     * <p>bmuUrlDspList をセットします。
     * @param bmuUrlDspList bmuUrlDspList
     */
    public void setBmuUrlDspList(List<String> bmuUrlDspList) {
        bmuUrlDspList__ = bmuUrlDspList;
    }

}