package jp.groupsession.v2.mem.mem050;

import java.util.List;

import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.mem040.Mem040Form;

/**
 * <br>[機  能] メモ帳 ラベルの管理画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem050Form extends Mem040Form {
    /** ラベル編集モード */
    private int memLabelCmdMode__ = GSConstMemo.CMDMODE_ADD;
    /** 編集対象ラベルSID */
    private int memEditLabelId__ = 0;
    /** ラベルリスト */
    private List<Mem050DisplayModel> lbnList__ = null;
    /** 編集対象ラベルSID */
    private String mem050editLabel__ = null;
    /** 並び替え対象のラベル */
    private String[] mem050sortLabel__ = null;
    /** チェックされている並び順 */
    private String mem050SortRadio__ = null;

    /**
     * <p>memLabelCmdMode を取得します。
     * @return memLabelCmdMode
     */
    public int getMemLabelCmdMode() {
        return memLabelCmdMode__;
    }
    /**
     * <p>memLabelCmdMode をセットします。
     * @param memLabelCmdMode memLabelCmdMode
     */
    public void setMemLabelCmdMode(int memLabelCmdMode) {
        memLabelCmdMode__ = memLabelCmdMode;
    }
    /**
     * <p>memEditLabelId を取得します。
     * @return memEditLabelId
     */
    public int getMemEditLabelId() {
        return memEditLabelId__;
    }
    /**
     * <p>memEditLabelId をセットします。
     * @param memEditLabelId memEditLabelId
     */
    public void setMemEditLabelId(int memEditLabelId) {
        memEditLabelId__ = memEditLabelId;
    }
    /**
     * <p>lbnList を取得します。
     * @return lbnList
     * @see jp.groupsession.v2.mem.mem050.Mem050Form#lbnList__
     */
    public List<Mem050DisplayModel> getLbnList() {
        return lbnList__;
    }
    /**
     * <p>lbnList をセットします。
     * @param lbnList lbnList
     * @see jp.groupsession.v2.mem.mem050.Mem050Form#lbnList__
     */
    public void setLbnList(List<Mem050DisplayModel> lbnList) {
        lbnList__ = lbnList;
    }
    /**
     * <p>mem050editLabel を取得します。
     * @return mem050editLabel
     */
    public String getMem050editLabel() {
        return mem050editLabel__;
    }
    /**
     * <p>mem050editLabel をセットします。
     * @param mem050editLabel mem050editLabel
     */
    public void setMem050editLabel(String mem050editLabel) {
        mem050editLabel__ = mem050editLabel;
    }
    /**
     * <p>mem050sortLabel を取得します。
     * @return mem050sortLabel
     */
    public String[] getMem050sortLabel() {
        return mem050sortLabel__;
    }
    /**
     * <p>mem050sortLabel をセットします。
     * @param mem050sortLabel mem050sortLabel
     */
    public void setMem050sortLabel(String[] mem050sortLabel) {
        mem050sortLabel__ = mem050sortLabel;
    }
    /**
     * <p>mem050SortRadio を取得します。
     * @return mem050SortRadio
     */
    public String getMem050SortRadio() {
        return mem050SortRadio__;
    }
    /**
     * <p>mem050SortRadio をセットします。
     * @param mem050SortRadio mem050SortRadio
     */
    public void setMem050SortRadio(String mem050SortRadio) {
        mem050SortRadio__ = mem050SortRadio;
    }
}