package jp.groupsession.v2.mem.mem050;

import jp.groupsession.v2.mem.model.MemoLabelModel;

/**
 * <br>[機  能] ラベル情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem050DisplayModel extends MemoLabelModel {
    /** 表示順(画面用) */
    private String labelValue__ = null;

    /**
     * <p>labelValue を取得します。
     * @return labelValue
     * @see jp.groupsession.v2.mem.mem050.Mem050DisplayModel#labelValue__
     */
    public String getLabelValue() {
        return labelValue__;
    }
    /**
     * <p>labelValue をセットします。
     * @param labelValue labelValue
     * @see jp.groupsession.v2.mem.mem050.Mem050DisplayModel#labelValue__
     */
    public void setLabelValue(String labelValue) {
        labelValue__ = labelValue;
    }
}