package jp.groupsession.v2.fil.fil310;

import jp.groupsession.v2.fil.model.FileMoneyMasterModel;

/**
 * <br>[機  能] ラベル情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil310DisplayModel extends FileMoneyMasterModel {

    /** 表示順(画面用) */
    private String gaikaValue__ = null;

    /**
     * <p>gaikaValue を取得します。
     * @return gaikaValue
     * @see jp.groupsession.v2.fil.fil310.Fil310DisplayModel#gaikaValue__
     */
    public String getGaikaValue() {
        return gaikaValue__;
    }
    /**
     * <p>gaikaValue をセットします。
     * @param gaikaValue gaikaValue
     * @see jp.groupsession.v2.fil.fil310.Fil310DisplayModel#gaikaValue__
     */
    public void setGaikaValue(String gaikaValue) {
        gaikaValue__ = gaikaValue;
    }
}