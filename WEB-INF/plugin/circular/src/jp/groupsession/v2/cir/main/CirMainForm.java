package jp.groupsession.v2.cir.main;

import jp.groupsession.v2.cir.cir010.Cir010Form;

/**
 * <br>[機  能] 回覧板(メイン画面表示用)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirMainForm extends Cir010Form {

    /** 回覧板トップ画面URL */
    private String  cirTopUrl__;

    /**
     * @return cirTopUrl__ を戻します。
     */
    public String getCirTopUrl() {
        return cirTopUrl__;
    }
    /**
     * @param cirTopUrl 設定する cirTopUrl__。
     */
    public void setCirTopUrl(String cirTopUrl) {
        cirTopUrl__ = cirTopUrl;
    }

}
