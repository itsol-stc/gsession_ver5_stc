package jp.groupsession.v2.zsk.zsk130;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.zsk070.Zsk070ParamModel;

/**
 * <br>[機  能] 在席管理 個人設定 座席表メンバー表示設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk130ParamModel extends Zsk070ParamModel {
    /** 表示順設定使用可能フラグ */
    private boolean zsk130SortConfAble__ = false;

    /** ソートキー1 */
    private int zsk130SortKey1__ = GSConstZaiseki.SORT_KEY_NAME;
    /** ソートキー1オーダー */
    private int zsk130SortOrder1__ = GSConst.ORDER_KEY_ASC;
    /** ソートキー2 */
    private int zsk130SortKey2__ = GSConstZaiseki.SORT_KEY_NAME;
    /** ソートキー2オーダー */
    private int zsk130SortOrder2__ = GSConst.ORDER_KEY_ASC;
    /** ソートキー ラベル */
    private List<LabelValueBean> zsk130SortKeyLabel__ = null;

    /** メイン画面表示フラグ */
    private int zsk130mainDspFlg__ = -1;

    /** 表示グループSID */
    private String zsk130mainDspGpSid__;
    /** グループコンボ */
    private List<SchLabelValueModel> zsk130mainDspGpLabelList__ = null;
    /** 画面遷移モード */
    private int zsk130Mode__ = GSConstZaiseki.MODE_ZAISEKI;

    /** ソートキー1 */
    private int zsk130mainDspSortKey1__ = GSConstZaiseki.SORT_KEY_NAME;
    /** ソートキー1オーダー */
    private int zsk130mainDspSortOrder1__ = GSConst.ORDER_KEY_ASC;
    /** ソートキー2 */
    private int zsk130mainDspSortKey2__ = GSConstZaiseki.SORT_KEY_NAME;
    /** ソートキー2オーダー */
    private int zsk130mainDspSortOrder2__ = GSConst.ORDER_KEY_ASC;
    /** ソートキー ラベル */
    private List<LabelValueBean> zsk130mainDspSortKeyLabel__ = null;
    /** ソートキー1 表示用 */
    private String zsk130mainDspSortKey1Name__;
    /** ソートキー2 表示用 */
    private String zsk130mainDspSortKey2Name__;


    /**
     * <p>zsk130SortConfAble を取得します。
     * @return zsk130SortConfAble
     * @see jp.groupsession.v2.zsk.zsk130.Zsk130ParamModel#zsk130SortConfAble__
     */
    public boolean isZsk130SortConfAble() {
        return zsk130SortConfAble__;
    }
    /**
     * <p>zsk130SortConfAble をセットします。
     * @param zsk130SortConfAble zsk130SortConfAble
     * @see jp.groupsession.v2.zsk.zsk130.Zsk130ParamModel#zsk130SortConfAble__
     */
    public void setZsk130SortConfAble(boolean zsk130SortConfAble) {
        zsk130SortConfAble__ = zsk130SortConfAble;
    }
    /**
     * <p>zsk130SortKey1 を取得します。
     * @return zsk130SortKey1
     */
    public int getZsk130SortKey1() {
        return zsk130SortKey1__;
    }
    /**
     * <p>zsk130SortKey1 をセットします。
     * @param zsk130SortKey1 zsk130SortKey1
     */
    public void setZsk130SortKey1(int zsk130SortKey1) {
        zsk130SortKey1__ = zsk130SortKey1;
    }
    /**
     * <p>zsk130SortOrder1 を取得します。
     * @return zsk130SortOrder1
     */
    public int getZsk130SortOrder1() {
        return zsk130SortOrder1__;
    }
    /**
     * <p>zsk130SortOrder1 をセットします。
     * @param zsk130SortOrder1 zsk130SortOrder1
     */
    public void setZsk130SortOrder1(int zsk130SortOrder1) {
        zsk130SortOrder1__ = zsk130SortOrder1;
    }
    /**
     * <p>zsk130SortKey2 を取得します。
     * @return zsk130SortKey2
     */
    public int getZsk130SortKey2() {
        return zsk130SortKey2__;
    }
    /**
     * <p>zsk130SortKey2 をセットします。
     * @param zsk130SortKey2 zsk130SortKey2
     */
    public void setZsk130SortKey2(int zsk130SortKey2) {
        zsk130SortKey2__ = zsk130SortKey2;
    }
    /**
     * <p>zsk130SortOrder2 を取得します。
     * @return zsk130SortOrder2
     */
    public int getZsk130SortOrder2() {
        return zsk130SortOrder2__;
    }
    /**
     * <p>zsk130SortOrder2 をセットします。
     * @param zsk130SortOrder2 zsk130SortOrder2
     */
    public void setZsk130SortOrder2(int zsk130SortOrder2) {
        zsk130SortOrder2__ = zsk130SortOrder2;
    }
    /**
     * <p>zsk130SortKeyLabel を取得します。
     * @return zsk130SortKeyLabel
     */
    public List<LabelValueBean> getZsk130SortKeyLabel() {
        return zsk130SortKeyLabel__;
    }
    /**
     * <p>zsk130SortKeyLabel をセットします。
     * @param zsk130SortKeyLabel zsk130SortKeyLabel
     */
    public void setZsk130SortKeyLabel(List<LabelValueBean> zsk130SortKeyLabel) {
        zsk130SortKeyLabel__ = zsk130SortKeyLabel;
    }

    /** メイン画面の在席管理メンバー スケジュール表示区分 デフォルト値 */
    private int zsk130mainDspSchViewDf__ = GSConstZaiseki.MAIN_SCH_DSP;

    /**
     * <p>zsk130mainDspSchViewDf を取得します。
     * @return zsk130mainDspSchViewDf
     */
    public int getZsk130mainDspSchViewDf() {
        return zsk130mainDspSchViewDf__;
    }

    /**
     * <p>zsk130mainDspSchViewDf をセットします。
     * @param zsk130mainDspSchViewDf zsk130mainDspSchViewDf
     */
    public void setZsk130mainDspSchViewDf(int zsk130mainDspSchViewDf) {
        zsk130mainDspSchViewDf__ = zsk130mainDspSchViewDf;
    }

    /**
     * <p>zsk130mainDspSortKey1Name を取得します。
     * @return zsk130mainDspSortKey1Name
     */
    public String getZsk130mainDspSortKey1Name() {
        return zsk130mainDspSortKey1Name__;
    }

    /**
     * <p>zsk130mainDspSortKey1Name をセットします。
     * @param zsk130mainDspSortKey1Name zsk130mainDspSortKey1Name
     */
    public void setZsk130mainDspSortKey1Name(String zsk130mainDspSortKey1Name) {
        zsk130mainDspSortKey1Name__ = zsk130mainDspSortKey1Name;
    }

    /**
     * <p>zsk130mainDspSortKey2Name を取得します。
     * @return zsk130mainDspSortKey2Name
     */
    public String getZsk130mainDspSortKey2Name() {
        return zsk130mainDspSortKey2Name__;
    }

    /**
     * <p>zsk130mainDspSortKey2Name をセットします。
     * @param zsk130mainDspSortKey2Name zsk130mainDspSortKey2Name
     */
    public void setZsk130mainDspSortKey2Name(String zsk130mainDspSortKey2Name) {
        zsk130mainDspSortKey2Name__ = zsk130mainDspSortKey2Name;
    }

    /**
     * <p>zsk130mainDspGpSid を取得します。
     * @return zsk130mainDspGpSid
     */
    public String getZsk130mainDspGpSid() {
        return zsk130mainDspGpSid__;
    }

    /**
     * <p>zsk130mainDspGpSid をセットします。
     * @param zsk130mainDspGpSid zsk130mainDspGpSid
     */
    public void setZsk130mainDspGpSid(String zsk130mainDspGpSid) {
        zsk130mainDspGpSid__ = zsk130mainDspGpSid;
    }

    /**
     * <p>zsk130mainDspGpLabelList を取得します。
     * @return zsk130mainDspGpLabelList
     */
    public List<SchLabelValueModel> getZsk130mainDspGpLabelList() {
        return zsk130mainDspGpLabelList__;
    }

    /**
     * <p>zsk130mainDspGpLabelList をセットします。
     * @param zsk130mainDspGpLabelList zsk130mainDspGpLabelList
     */
    public void setZsk130mainDspGpLabelList(List<SchLabelValueModel> zsk130mainDspGpLabelList) {
        zsk130mainDspGpLabelList__ = zsk130mainDspGpLabelList;
    }

    /**
     * <p>zsk130Mode を取得します。
     * @return zsk130Mode
     */
    public int getZsk130Mode() {
        return zsk130Mode__;
    }

    /**
     * <p>zsk130Mode をセットします。
     * @param zsk130Mode zsk130Mode
     */
    public void setZsk130Mode(int zsk130Mode) {
        zsk130Mode__ = zsk130Mode;
    }

    /**
     * <p>zsk130mainDspSortKey1 を取得します。
     * @return zsk130mainDspSortKey1
     */
    public int getZsk130mainDspSortKey1() {
        return zsk130mainDspSortKey1__;
    }

    /**
     * <p>zsk130mainDspSortKey1 をセットします。
     * @param zsk130mainDspSortKey1 zsk130mainDspSortKey1
     */
    public void setZsk130mainDspSortKey1(int zsk130mainDspSortKey1) {
        zsk130mainDspSortKey1__ = zsk130mainDspSortKey1;
    }

    /**
     * <p>zsk130mainDspSortKey2 を取得します。
     * @return zsk130mainDspSortKey2
     */
    public int getZsk130mainDspSortKey2() {
        return zsk130mainDspSortKey2__;
    }

    /**
     * <p>zsk130mainDspSortKey2 をセットします。
     * @param zsk130mainDspSortKey2 zsk130mainDspSortKey2
     */
    public void setZsk130mainDspSortKey2(int zsk130mainDspSortKey2) {
        zsk130mainDspSortKey2__ = zsk130mainDspSortKey2;
    }

    /**
     * <p>zsk130mainDspSortKeyLabel を取得します。
     * @return zsk130mainDspSortKeyLabel
     */
    public List<LabelValueBean> getZsk130mainDspSortKeyLabel() {
        return zsk130mainDspSortKeyLabel__;
    }

    /**
     * <p>zsk130mainDspSortKeyLabel をセットします。
     * @param zsk130mainDspSortKeyLabel zsk130mainDspSortKeyLabel
     */
    public void setZsk130mainDspSortKeyLabel(List<LabelValueBean> zsk130mainDspSortKeyLabel) {
        zsk130mainDspSortKeyLabel__ = zsk130mainDspSortKeyLabel;
    }

    /**
     * <p>zsk130mainDspSortOrder1 を取得します。
     * @return zsk130mainDspSortOrder1
     */
    public int getZsk130mainDspSortOrder1() {
        return zsk130mainDspSortOrder1__;
    }

    /**
     * <p>zsk130mainDspSortOrder1 をセットします。
     * @param zsk130mainDspSortOrder1 zsk130mainDspSortOrder1
     */
    public void setZsk130mainDspSortOrder1(int zsk130mainDspSortOrder1) {
        zsk130mainDspSortOrder1__ = zsk130mainDspSortOrder1;
    }

    /**
     * <p>zsk130mainDspSortOrder2 を取得します。
     * @return zsk130mainDspSortOrder2
     */
    public int getZsk130mainDspSortOrder2() {
        return zsk130mainDspSortOrder2__;
    }

    /**
     * <p>zsk130mainDspSortOrder2 をセットします。
     * @param zsk130mainDspSortOrder2 zsk130mainDspSortOrder2
     */
    public void setZsk130mainDspSortOrder2(int zsk130mainDspSortOrder2) {
        zsk130mainDspSortOrder2__ = zsk130mainDspSortOrder2;
    }

    /**
     * <p>zsk130mainDspFlg を取得します。
     * @return zsk130mainDspFlg
     */
    public int getZsk130mainDspFlg() {
        return zsk130mainDspFlg__;
    }

    /**
     * <p>zsk130mainDspFlg をセットします。
     * @param zsk130mainDspFlg zsk130mainDspFlg
     */
    public void setZsk130mainDspFlg(int zsk130mainDspFlg) {
        zsk130mainDspFlg__ = zsk130mainDspFlg;
    }
}