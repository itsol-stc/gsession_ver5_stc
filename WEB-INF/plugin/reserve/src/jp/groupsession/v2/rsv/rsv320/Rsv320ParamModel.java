package jp.groupsession.v2.rsv.rsv320;

import jp.groupsession.v2.rsv.rsv040.Rsv040ParamModel;

/**
 * <br>[機  能] 施設予約 管理者設定 ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv320ParamModel extends Rsv040ParamModel {

    /** ショートメール通知区分 0:ユーザが設定する 1:管理者が設定する */
    private int rsv320SmailSendKbn__ = 0;
    /** 通知設定 0=通知する 1=通知しない*/
    private int rsv320SmailSend__ = 0;
    /**
     * <p>rsv320SmailSendKbn を取得します。
     * @return rsv320SmailSendKbn
     */
    public int getRsv320SmailSendKbn() {
        return rsv320SmailSendKbn__;
    }
    /**
     * <p>rsv320SmailSendKbn をセットします。
     * @param rsv320SmailSendKbn rsv320SmailSendKbn
     */
    public void setRsv320SmailSendKbn(int rsv320SmailSendKbn) {
        rsv320SmailSendKbn__ = rsv320SmailSendKbn;
    }
    /**
     * <p>rsv320SmailSend を取得します。
     * @return rsv320SmailSend
     */
    public int getRsv320SmailSend() {
        return rsv320SmailSend__;
    }
    /**
     * <p>rsv320SmailSend をセットします。
     * @param rsv320SmailSend rsv320SmailSend
     */
    public void setRsv320SmailSend(int rsv320SmailSend) {
        rsv320SmailSend__ = rsv320SmailSend;
    }


}