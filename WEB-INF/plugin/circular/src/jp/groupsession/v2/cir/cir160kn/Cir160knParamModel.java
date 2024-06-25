package jp.groupsession.v2.cir.cir160kn;

import jp.groupsession.v2.cir.cir160.Cir160ParamModel;



/**
 * <br>[機  能] 回覧板 アカウント登録確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir160knParamModel extends Cir160ParamModel {
    /** 備考 表示用 */
    private String cir160knBiko__ = null;
    /** テーマ 表示用 */
    private String cir160knTheme__ = null;
    /**
     * <p>cir160knBiko を取得します。
     * @return cir160knBiko
     */
    public String getCir160knBiko() {
        return cir160knBiko__;
    }
    /**
     * <p>cir160knBiko をセットします。
     * @param cir160knBiko cir160knBiko
     */
    public void setCir160knBiko(String cir160knBiko) {
        cir160knBiko__ = cir160knBiko;
    }
    /**
     * <p>cir160knTheme を取得します。
     * @return cir160knTheme
     */
    public String getCir160knTheme() {
        return cir160knTheme__;
    }
    /**
     * <p>cir160knTheme をセットします。
     * @param cir160knTheme cir160knTheme
     */
    public void setCir160knTheme(String cir160knTheme) {
        cir160knTheme__ = cir160knTheme;
    }
}