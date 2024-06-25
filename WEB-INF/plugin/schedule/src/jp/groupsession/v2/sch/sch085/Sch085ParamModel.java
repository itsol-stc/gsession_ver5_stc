package jp.groupsession.v2.sch.sch085;

import java.util.List;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.sch.sch100.Sch100ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] スケジュール 管理者設定 メンバー表示順設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch085ParamModel extends Sch100ParamModel {

    /** 管理者権限 ソートキー1 */
    private int sch085AdSortKey1__ = -1;
    /** 管理者権限 ソートキー1オーダー */
    private int sch085AdSortOrder1__ = -1;
    /** 管理者権限 ソートキー2 */
    private int sch085AdSortKey2__ = -1;
    /** 管理者権限 ソートキー2オーダー */
    private int sch085AdSortOrder2__ = -1;
    /** ソートキー ラベル */
    private List<LabelValueBean> sch085SortKeyLabel__ = null;

    /** メンバ表示設定区分 */
    private int sch085MemDspKbn__ = -1;

    /** デフォルト表示グループ */
    private int sch085DefGroup__ = GSConstSchedule.SAD_DSP_GROUP_USER;

    /**
     * <p>sch085SortKeyLabel を取得します。
     * @return sch085SortKeyLabel
     */
    public List<LabelValueBean> getSch085SortKeyLabel() {
        return sch085SortKeyLabel__;
    }

    /**
     * <p>sch085SortKeyLabel をセットします。
     * @param sch085SortKeyLabel sch085SortKeyLabel
     */
    public void setSch085SortKeyLabel(List<LabelValueBean> sch085SortKeyLabel) {
        sch085SortKeyLabel__ = sch085SortKeyLabel;
    }

    /**
     * @return sch085MemDspKbn
     */
    public int getSch085MemDspKbn() {
        return sch085MemDspKbn__;
    }

    /**
     * @param sch085MemDspKbn 設定する sch085MemDspKbn
     */
    public void setSch085MemDspKbn(int sch085MemDspKbn) {
        sch085MemDspKbn__ = sch085MemDspKbn;
    }

    /**
     * @return sch085AdSortKey1
     */
    public int getSch085AdSortKey1() {
        return sch085AdSortKey1__;
    }

    /**
     * @param sch085AdSortKey1 設定する sch085AdSortKey1
     */
    public void setSch085AdSortKey1(int sch085AdSortKey1) {
        sch085AdSortKey1__ = sch085AdSortKey1;
    }

    /**
     * @return sch085AdSortKey2
     */
    public int getSch085AdSortKey2() {
        return sch085AdSortKey2__;
    }

    /**
     * @param sch085AdSortKey2 設定する sch085AdSortKey2
     */
    public void setSch085AdSortKey2(int sch085AdSortKey2) {
        sch085AdSortKey2__ = sch085AdSortKey2;
    }

    /**
     * @return sch085AdSortOrder1
     */
    public int getSch085AdSortOrder1() {
        return sch085AdSortOrder1__;
    }

    /**
     * @param sch085AdSortOrder1 設定する sch085AdSortOrder1
     */
    public void setSch085AdSortOrder1(int sch085AdSortOrder1) {
        sch085AdSortOrder1__ = sch085AdSortOrder1;
    }

    /**
     * @return sch085AdSortOrder2
     */
    public int getSch085AdSortOrder2() {
        return sch085AdSortOrder2__;
    }

    /**
     * @param sch085AdSortOrder2 設定する sch085AdSortOrder2
     */
    public void setSch085AdSortOrder2(int sch085AdSortOrder2) {
        sch085AdSortOrder2__ = sch085AdSortOrder2;
    }

    /**
     * <p>sch085DefGroup を取得します。
     * @return sch085DefGroup
     */
    public int getSch085DefGroup() {
        return sch085DefGroup__;
    }

    /**
     * <p>sch085DefGroup をセットします。
     * @param sch085DefGroup sch085DefGroup
     */
    public void setSch085DefGroup(int sch085DefGroup) {
        sch085DefGroup__ = sch085DefGroup;
    }
}
