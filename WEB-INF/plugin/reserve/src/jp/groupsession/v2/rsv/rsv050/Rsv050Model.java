package jp.groupsession.v2.rsv.rsv050;

import jp.groupsession.v2.rsv.model.RsvSisGrpModel;

/**
 * <br>[機  能] 施設予約 施設グループ情報設定画面で使用するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv050Model extends RsvSisGrpModel {

    /** ラジオキー */
    private String radioKey__ = null;

    /**
     * <p>radioKey__ を取得します。
     * @return radioKey
     */
    public String getRadioKey() {
        return radioKey__;
    }

    /**
     * <p>radioKey__ をセットします。
     * @param radioKey radioKey__
     */
    public void setRadioKey(String radioKey) {
        radioKey__ = radioKey;
    }
}