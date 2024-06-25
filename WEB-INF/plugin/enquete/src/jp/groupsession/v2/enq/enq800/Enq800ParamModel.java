package jp.groupsession.v2.enq.enq800;

import jp.groupsession.v2.enq.enq010.Enq010ParamModel;

/**
 * <br>[機  能] アンケート 個人設定メニュー画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq800ParamModel extends Enq010ParamModel {

    /** 表示設定 使用可能フラグ */
    private int enq800DspUse__ = 0;
    /** メイン表示設定 使用可能フラグ */
    private int enq800DspMainUse__ = 0;

    /**
     * <p>enq800DspUse を取得します。
     * @return enq800DspUse
     */
    public int getEnq800DspUse() {
        return enq800DspUse__;
    }
    /**
     * <p>enq800DspUse をセットします。
     * @param enq800DspUse enq800DspUse
     */
    public void setEnq800DspUse(int enq800DspUse) {
        enq800DspUse__ = enq800DspUse;
    }
    /**
     * <p>enq800DspMainUse を取得します。
     * @return enq800DspMainUse
     */
    public int getEnq800DspMainUse() {
        return enq800DspMainUse__;
    }
    /**
     * <p>enq800DspMainUse をセットします。
     * @param enq800DspMainUse enq800DspMainUse
     */
    public void setEnq800DspMainUse(int enq800DspMainUse) {
        enq800DspMainUse__ = enq800DspMainUse;
    }
}
