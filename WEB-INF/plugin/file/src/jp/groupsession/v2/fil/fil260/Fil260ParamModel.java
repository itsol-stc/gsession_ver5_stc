package jp.groupsession.v2.fil.fil260;

import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil200.Fil200ParamModel;

/**
 * <br>[機  能] 管理者設定 ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil260ParamModel extends Fil200ParamModel {
    /** 初期表示フラグ */
    private int fil260initFlg__ = 0;
    /** ショートメール通知区分 */
    private int fil260smlSendKbn__ = GSConstFile.FAC_SMAIL_SEND_KBN_USER;
    /** ショートメール通知 */
    private int fil260smlSend__ = GSConstFile.FAC_SMAIL_SEND_YES;
    /**
     * <p>fil260initFlg を取得します。
     * @return fil260initFlg
     */
    public int getFil260initFlg() {
        return fil260initFlg__;
    }
    /**
     * <p>fil260initFlg をセットします。
     * @param fil260initFlg fil260initFlg
     */
    public void setFil260initFlg(int fil260initFlg) {
        fil260initFlg__ = fil260initFlg;
    }
    /**
     * <p>fil260smlSendKbn を取得します。
     * @return fil260smlSendKbn
     */
    public int getFil260smlSendKbn() {
        return fil260smlSendKbn__;
    }
    /**
     * <p>fil260smlSendKbn をセットします。
     * @param fil260smlSendKbn fil260smlSendKbn
     */
    public void setFil260smlSendKbn(int fil260smlSendKbn) {
        fil260smlSendKbn__ = fil260smlSendKbn;
    }
    /**
     * <p>fil260smlSend を取得します。
     * @return fil260smlSend
     */
    public int getFil260smlSend() {
        return fil260smlSend__;
    }
    /**
     * <p>fil260smlSend をセットします。
     * @param fil260smlSend fil260smlSend
     */
    public void setFil260smlSend(int fil260smlSend) {
        fil260smlSend__ = fil260smlSend;
    }
}