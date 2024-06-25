package jp.groupsession.v2.anp.anp120;

import jp.groupsession.v2.anp.anp110.Anp110ParamModel;

/**
 * <br>[機  能] 管理者設定・緊急連絡先編集画面のパラメータモデル
 *
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp120ParamModel extends Anp110ParamModel {

    /** メールアドレス */
    private String anp120MailAdr__ = null;
    /** 電話番号 */
    private String anp120TelNo__ = null;

    /**
     * <p>メールアドレスを取得します
     * @return anp120MailAdr
     */
    public String getAnp120MailAdr() {
        return anp120MailAdr__;
    }

    /**
     * <p>メールアドレスを設定します
     * @param anp120MailAdr セットする anp120MailAdr
     */
    public void setAnp120MailAdr(String anp120MailAdr) {
        anp120MailAdr__ = anp120MailAdr;
    }

    /**
     * <p>電話番号を取得します
     * @return anp120TelNo
     */
    public String getAnp120TelNo() {
        return anp120TelNo__;
    }

    /**
     * <p>電話番号を設定します
     * @param anp120TelNo セットする anp120TelNo
     */
    public void setAnp120TelNo(String anp120TelNo) {
        anp120TelNo__ = anp120TelNo;
    }
}