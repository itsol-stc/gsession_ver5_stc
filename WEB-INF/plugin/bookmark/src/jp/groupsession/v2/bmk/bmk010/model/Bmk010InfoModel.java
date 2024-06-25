package jp.groupsession.v2.bmk.bmk010.model;

import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.bmk.model.BmkUrlModel;

/**
 * <br>[機  能] ブックマークのサイド情報を保持するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk010InfoModel extends BmkUrlModel {

    /** タイトル(表示用) */
    private List<String> bmkTitleDspList__;

    /** タイトル(表示用) 改行文字数 */
    private int titleNewline__ = 30;

    /** 登録人数 */
    private int bmkPerCount__;
    /** 個人ブックマーク登録区分 */
    private int bmkMyKbn__;

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
     * <p>titleNewline を取得します。
     * @return titleNewline
     */
    public int getTitleNewline() {
        return titleNewline__;
    }

    /**
     * <p>titleNewline をセットします。
     * @param titleNewline titleNewline
     */
    public void setTitleNewline(int titleNewline) {
        titleNewline__ = titleNewline;
    }

    /**
     * <br>[機  能] タイトル(表示用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return タイトル(表示用)
     */
    public String getViewBmuTitle() {
        return StringUtilHtml.transToHTmlWithWbr(
                NullDefault.getString(getBmuTitle(), ""),
                titleNewline__);
    }
}