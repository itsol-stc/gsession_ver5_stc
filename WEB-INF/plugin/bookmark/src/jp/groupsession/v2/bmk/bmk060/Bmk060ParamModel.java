package jp.groupsession.v2.bmk.bmk060;

import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.bmk050.Bmk050ParamModel;
import jp.groupsession.v2.bmk.bmk060.ui.Bmk060LabelSelector;

/**
 * <br>[機  能] ラベル登録画面のパラメータ情報
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Bmk060ParamModel extends Bmk050ParamModel {
    /** ラベル名 */
    private String bmk060LblName__ = null;

    /** ラベル統合区分 */
    private int bmk060LblKbn__ = GSConstBookmark.LABEL_TOGO_NO;
    /** 統合ラベル(パラメータ保持用) */
    private String[] bmk060LabelList__ = null;
    /** 統合ラベル UI */
    private Bmk060LabelSelector bmk060LabelListUI__ = null;

    /**
     * <p>bmk060LblName を取得します。
     * @return bmk060LblName
     */
    public String getBmk060LblName() {
        return bmk060LblName__;
    }

    /**
     * <p>bmk060LblName をセットします。
     * @param bmk060LblName bmk060LblName
     */
    public void setBmk060LblName(String bmk060LblName) {
        bmk060LblName__ = bmk060LblName;
    }

    /**
     * <p>bmk060LabelList を取得します。
     * @return bmk060LabelList
     */
    public String[] getBmk060LabelList() {
        return bmk060LabelList__;
    }

    /**
     * <p>bmk060LabelList をセットします。
     * @param bmk060LabelList bmk060LabelList
     */
    public void setBmk060LabelList(String[] bmk060LabelList) {
        bmk060LabelList__ = bmk060LabelList;
    }

    /**
     * <p>bmk060LblKbn を取得します。
     * @return bmk060LblKbn
     */
    public int getBmk060LblKbn() {
        return bmk060LblKbn__;
    }

    /**
     * <p>bmk060LblKbn をセットします。
     * @param bmk060LblKbn bmk060LblKbn
     */
    public void setBmk060LblKbn(int bmk060LblKbn) {
        bmk060LblKbn__ = bmk060LblKbn;
    }

    /**
     * <p>bmk060LabelListUI を取得します。
     * @return bmk060LabelListUI
     * @see jp.groupsession.v2.bmk.bmk060.Bmk060ParamModel#bmk060LabelListUI__
     */
    public Bmk060LabelSelector getBmk060LabelListUI() {
        return bmk060LabelListUI__;
    }

    /**
     * <p>bmk060LabelListUI をセットします。
     * @param bmk060LabelListUI bmk060LabelListUI
     * @see jp.groupsession.v2.bmk.bmk060.Bmk060ParamModel#bmk060LabelListUI__
     */
    public void setBmk060LabelListUI(Bmk060LabelSelector bmk060LabelListUI) {
        bmk060LabelListUI__ = bmk060LabelListUI;
    }
}
