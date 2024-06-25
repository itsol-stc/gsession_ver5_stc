package jp.groupsession.v2.rsv.rsv140;

import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.rsv030.Rsv030ParamModel;

/**
 * <br>[機  能] 施設予約 個人設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv140ParamModel extends Rsv030ParamModel {
    /** 初期値設定設定可能か */
    private boolean rsv140canEditIvs__ = false;
    /** 日間表示時間帯設定可能か */
    private boolean rsv140canEditDtm__ = false;
    /** ショートメール通知設定区分 */
    private int rsv140SmailSendKbn__ = GSConstReserve.RAC_SMAIL_SEND_KBN_USER;

    /**
     * <p>rsv140canEditDtm を取得します。
     * @return rsv140canEditDtm
     */
    public boolean isRsv140canEditDtm() {
        return rsv140canEditDtm__;
    }

    /**
     * <p>rsv140canEditDtm をセットします。
     * @param rsv140canEditDtm rsv140canEditDtm
     */
    public void setRsv140canEditDtm(boolean rsv140canEditDtm) {
        rsv140canEditDtm__ = rsv140canEditDtm;
    }

    /**
     * <p>rsv140SmailSendKbn を取得します。
     * @return rsv140SmailSendKbn
     */
    public int getRsv140SmailSendKbn() {
        return rsv140SmailSendKbn__;
    }

    /**
     * <p>rsv140SmailSendKbn をセットします。
     * @param rsv140SmailSendKbn rsv140SmailSendKbn
     */
    public void setRsv140SmailSendKbn(int rsv140SmailSendKbn) {
        rsv140SmailSendKbn__ = rsv140SmailSendKbn;
    }

    /**
     * <p>rsv140canEditIvs を取得します。
     * @return rsv140canEditIvs
     * @see jp.groupsession.v2.rsv.rsv140.Rsv140ParamModel#rsv140canEditIvs__
     */
    public boolean isRsv140canEditIvs() {
        return rsv140canEditIvs__;
    }

    /**
     * <p>rsv140canEditIvs をセットします。
     * @param rsv140canEditIvs rsv140canEditIvs
     * @see jp.groupsession.v2.rsv.rsv140.Rsv140ParamModel#rsv140canEditIvs__
     */
    public void setRsv140canEditIvs(boolean rsv140canEditIvs) {
        rsv140canEditIvs__ = rsv140canEditIvs;
    }
}