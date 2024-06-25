package jp.groupsession.v2.ntp.ntp090;

import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp080.Ntp080Form;

/**
 * <br>[機  能] 日報 個人設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp090Form extends Ntp080Form {

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Ntp090Form() {
    }

    /** ショートメール通知設定 0:可 1:不可 */
    private int ntp090SmlNoticeKbn__ = GSConstNippou.SML_NOTICE_ADM;

    /**
     * <p>ntp090SmlNoticeKbn を取得します。
     * @return ntp090SmlNoticeKbn
     */
    public int getNtp090SmlNoticeKbn() {
        return ntp090SmlNoticeKbn__;
    }

    /**
     * <p>ntp090SmlNoticeKbn をセットします。
     * @param ntp090SmlNoticeKbn ntp090SmlNoticeKbn
     */
    public void setNtp090SmlNoticeKbn(int ntp090SmlNoticeKbn) {
        ntp090SmlNoticeKbn__ = ntp090SmlNoticeKbn;
    }


}
