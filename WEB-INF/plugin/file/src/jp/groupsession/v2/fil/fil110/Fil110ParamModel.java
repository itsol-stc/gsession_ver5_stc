package jp.groupsession.v2.fil.fil110;

import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil100.Fil100ParamModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] 個人設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil110ParamModel extends Fil100ParamModel {

    /** ショートメール利用 */
    private String fil110smailUseKbn__ = String.valueOf(GSConstMain.PLUGIN_NOT_USE);
    /** ショートメール通知設定区分 */
    private int fil110SmailSendKbn__ = GSConstFile.FAC_SMAIL_SEND_KBN_USER;

    /**
     * <p>fil110smailUseKbn を取得します。
     * @return fil110smailUseKbn
     */
    public String getFil110smailUseKbn() {
        return fil110smailUseKbn__;
    }

    /**
     * <p>fil110smailUseKbn をセットします。
     * @param fil110smailUseKbn fil110smailUseKbn
     */
    public void setFil110smailUseKbn(String fil110smailUseKbn) {
        fil110smailUseKbn__ = fil110smailUseKbn;
    }

    /**
     * <p>fil110SmailSendKbn を取得します。
     * @return fil110SmailSendKbn
     */
    public int getFil110SmailSendKbn() {
        return fil110SmailSendKbn__;
    }

    /**
     * <p>fil110SmailSendKbn をセットします。
     * @param fil110SmailSendKbn fil110SmailSendKbn
     */
    public void setFil110SmailSendKbn(int fil110SmailSendKbn) {
        fil110SmailSendKbn__ = fil110SmailSendKbn;
    };

}