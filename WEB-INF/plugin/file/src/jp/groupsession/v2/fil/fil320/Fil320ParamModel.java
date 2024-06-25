package jp.groupsession.v2.fil.fil320;

import jp.groupsession.v2.fil.fil310.Fil310ParamModel;

/**
 * <br>[機  能] ファイル管理 外貨登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil320ParamModel extends Fil310ParamModel {
    /** 外貨名 */
    private String fil320GaikaName__ = null;

    /**
     * <p>fil320GaikaName を取得します。
     * @return fil320GaikaName
     * @see jp.groupsession.v2.fil.fil320.Fil320Form#fil320GaikaName__
     */
    public String getFil320GaikaName() {
        return fil320GaikaName__;
    }
    /**
     * <p>fil320GaikaName をセットします。
     * @param fil320GaikaName fil320GaikaName
     * @see jp.groupsession.v2.fil.fil320.Fil320Form#fil320GaikaName__
     */
    public void setFil320GaikaName(String fil320GaikaName) {
        fil320GaikaName__ = fil320GaikaName;
    }
}