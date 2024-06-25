package jp.groupsession.v2.zsk.zsk140;

import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.zsk020.Zsk020Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 在席管理 管理者設定 座席表メンバーデフォルト表示設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk140Form extends Zsk020Form {

    /** 初期表示区分 */
    private int zsk140initKbn__ = 0;
    /** 表示区分 */
    private int zsk140SortKbn__ = GSConstZaiseki.ADM_SORTKBN_PRI;
    /** ソートキー1 */
    private int zsk140SortKey1__ = GSConstZaiseki.SORT_KEY_NAME;
    /** ソートキー1オーダー */
    private int zsk140SortOrder1__ = GSConst.ORDER_KEY_ASC;
    /** ソートキー2 */
    private int zsk140SortKey2__ = GSConstZaiseki.SORT_KEY_NAME;
    /** ソートキー2オーダー */
    private int zsk140SortOrder2__ = GSConst.ORDER_KEY_ASC;
    /** ソートキー ラベル */
    private List<LabelValueBean> zsk140SortKeyLabel__ = null;

    /**
     * <p>zsk140SortKbn を取得します。
     * @return zsk140SortKbn
     */
    public int getZsk140SortKbn() {
        return zsk140SortKbn__;
    }
    /**
     * <p>zsk140SortKbn をセットします。
     * @param zsk140SortKbn zsk140SortKbn
     */
    public void setZsk140SortKbn(int zsk140SortKbn) {
        zsk140SortKbn__ = zsk140SortKbn;
    }
    /**
     * <p>zsk140SortKey1 を取得します。
     * @return zsk140SortKey1
     */
    public int getZsk140SortKey1() {
        return zsk140SortKey1__;
    }
    /**
     * <p>zsk140SortKey1 をセットします。
     * @param zsk140SortKey1 zsk140SortKey1
     */
    public void setZsk140SortKey1(int zsk140SortKey1) {
        zsk140SortKey1__ = zsk140SortKey1;
    }
    /**
     * <p>zsk140SortOrder1 を取得します。
     * @return zsk140SortOrder1
     */
    public int getZsk140SortOrder1() {
        return zsk140SortOrder1__;
    }
    /**
     * <p>zsk140SortOrder1 をセットします。
     * @param zsk140SortOrder1 zsk140SortOrder1
     */
    public void setZsk140SortOrder1(int zsk140SortOrder1) {
        zsk140SortOrder1__ = zsk140SortOrder1;
    }
    /**
     * <p>zsk140SortKey2 を取得します。
     * @return zsk140SortKey2
     */
    public int getZsk140SortKey2() {
        return zsk140SortKey2__;
    }
    /**
     * <p>zsk140SortKey2 をセットします。
     * @param zsk140SortKey2 zsk140SortKey2
     */
    public void setZsk140SortKey2(int zsk140SortKey2) {
        zsk140SortKey2__ = zsk140SortKey2;
    }
    /**
     * <p>zsk140SortOrder2 を取得します。
     * @return zsk140SortOrder2
     */
    public int getZsk140SortOrder2() {
        return zsk140SortOrder2__;
    }
    /**
     * <p>zsk140SortOrder2 をセットします。
     * @param zsk140SortOrder2 zsk140SortOrder2
     */
    public void setZsk140SortOrder2(int zsk140SortOrder2) {
        zsk140SortOrder2__ = zsk140SortOrder2;
    }
    /**
     * <p>zsk140initKbn を取得します。
     * @return zsk140initKbn
     */
    public int getZsk140initKbn() {
        return zsk140initKbn__;
    }
    /**
     * <p>zsk140initKbn をセットします。
     * @param zsk140initKbn zsk140initKbn
     */
    public void setZsk140initKbn(int zsk140initKbn) {
        zsk140initKbn__ = zsk140initKbn;
    }
    /**
     * <p>zsk140SortKeyLabel を取得します。
     * @return zsk140SortKeyLabel
     */
    public List<LabelValueBean> getZsk140SortKeyLabel() {
        return zsk140SortKeyLabel__;
    }
    /**
     * <p>zsk140SortKeyLabel をセットします。
     * @param zsk140SortKeyLabel zsk140SortKeyLabel
     */
    public void setZsk140SortKeyLabel(List<LabelValueBean> zsk140SortKeyLabel) {
        zsk140SortKeyLabel__ = zsk140SortKeyLabel;
    }
}