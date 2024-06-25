package jp.groupsession.v2.bmk.bmk010.model;

import jp.groupsession.v2.bmk.model.BmkLabelModel;

/**
 * <br>[機  能] ブックマーク画面のラベル一覧を保持するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk010LabelModel extends BmkLabelModel {

    /** ブックマーク件数 */
    private int bmkLabelCount__ = 0;
    /** ラベル名（WEB検索用） */
    private String blbNameWebSearchWord__ = "";

    /**
     * <p>bmkLabelCount を取得します。
     * @return bmkLabelCount
     */
    public int getBmkLabelCount() {
        return bmkLabelCount__;
    }

    /**
     * <p>bmkLabelCount をセットします。
     * @param bmkLabelCount bmkLabelCount
     */
    public void setBmkLabelCount(int bmkLabelCount) {
        bmkLabelCount__ = bmkLabelCount;
    }

    /**
     * <p>blbNameWebSearchWord を取得します。
     * @return blbNameWebSearchWord
     */
    public String getBlbNameWebSearchWord() {
        return blbNameWebSearchWord__;
    }

    /**
     * <p>blbNameWebSearchWord をセットします。
     * @param blbNameWebSearchWord blbNameWebSearchWord
     */
    public void setBlbNameWebSearchWord(String blbNameWebSearchWord) {
        blbNameWebSearchWord__ = blbNameWebSearchWord;
    }
}