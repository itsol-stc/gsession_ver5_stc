package jp.groupsession.v2.anp.anp080kn;

import jp.groupsession.v2.anp.anp080.Anp080Form;

/**
 * <br>[機  能] 管理者設定・基本設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp080knForm extends Anp080Form {

    /** 表示用パスワード */
    private String anp080knDspSendPass__;
    /** 送信暗号プロトコル 表示用 */
    private String anp080knSendEncrypt__ = null;
    /** プロバイダ 表示用 */
    private String anp080knDspProvider__ = null;

    /**
     * <p>anp080knDspSendPass を取得します。
     * @return anp080knDspSendPass
     */
    public String getAnp080knDspSendPass() {
        return anp080knDspSendPass__;
    }

    /**
     * <p>anp080knDspSendPass をセットします。
     * @param anp080knDspSendPass anp080knDspSendPass
     */
    public void setAnp080knDspSendPass(String anp080knDspSendPass) {
        anp080knDspSendPass__ = anp080knDspSendPass;
    }

    /**
     * <p>anp080knSendEncrypt を取得します。
     * @return anp080knSendEncrypt
     * @see jp.groupsession.v2.anp.anp080kn.Anp080knForm#anp080knSendEncrypt__
     */
    public String getAnp080knSendEncrypt() {
        return anp080knSendEncrypt__;
    }

    /**
     * <p>anp080knSendEncrypt をセットします。
     * @param anp080knSendEncrypt anp080knSendEncrypt
     * @see jp.groupsession.v2.anp.anp080kn.Anp080knForm#anp080knSendEncrypt__
     */
    public void setAnp080knSendEncrypt(String anp080knSendEncrypt) {
        anp080knSendEncrypt__ = anp080knSendEncrypt;
    }

    /**
     * <p>anp080knDspProvider を取得します。
     * @return anp080knDspProvider
     * @see jp.groupsession.v2.anp.anp080kn.Anp080knForm#anp080knDspProvider__
     */
    public String getAnp080knDspProvider() {
        return anp080knDspProvider__;
    }

    /**
     * <p>anp080knDspProvider をセットします。
     * @param anp080knDspProvider anp080knDspProvider
     * @see jp.groupsession.v2.anp.anp080kn.Anp080knForm#anp080knDspProvider__
     */
    public void setAnp080knDspProvider(String anp080knDspProvider) {
        anp080knDspProvider__ = anp080knDspProvider;
    }
}