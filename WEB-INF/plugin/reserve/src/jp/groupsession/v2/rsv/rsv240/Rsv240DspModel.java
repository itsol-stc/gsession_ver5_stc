package jp.groupsession.v2.rsv.rsv240;

import jp.groupsession.v2.rsv.model.RsvSisGrpModel;

/**
 * <br>[機  能] 施設予約 メイン表示設定画面へ表示する施設グループ情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv240DspModel extends RsvSisGrpModel {

    /** 予約時間経過表示区分 */
    private int rsmDspKbn__;

    /**
     * <p>rsmDspKbn を取得します。
     * @return rsmDspKbn
     */
    public int getRsmDspKbn() {
        return rsmDspKbn__;
    }

    /**
     * <p>rsmDspKbn をセットします。
     * @param rsmDspKbn rsmDspKbn
     */
    public void setRsmDspKbn(int rsmDspKbn) {
        rsmDspKbn__ = rsmDspKbn;
    }
}