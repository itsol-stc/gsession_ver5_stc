package jp.groupsession.v2.anp.anp160;

import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能]  メッセージ配信確認 送信者一覧(ポップアップ画面)の表示モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp160DspModel extends AbstractModel {
    /** ユーザSID */
    private int anp160UsrSid__;
    /** ユーザ名 */
    private String anp160Name__;
    /** メールアドレス設定フラグ */
    private int anp160MailFlg__;
    /** メールアドレス設定フラグ */
    private int anp160usrUkoFlg__;

    /**
     * <p>anp160UsrSid を取得します。
     * @return anp160UsrSid
     */
    public int getAnp160UsrSid() {
        return anp160UsrSid__;
    }
    /**
     * <p>anp160UsrSid をセットします。
     * @param anp160UsrSid anp160UsrSid
     */
    public void setAnp160UsrSid(int anp160UsrSid) {
        anp160UsrSid__ = anp160UsrSid;
    }
    /**
     * <p>anp160Name を取得します。
     * @return anp160Name
     */
    public String getAnp160Name() {
        return anp160Name__;
    }
    /**
     * <p>anp160Name をセットします。
     * @param anp160Name anp160Name
     */
    public void setAnp160Name(String anp160Name) {
        anp160Name__ = anp160Name;
    }
    /**
     * <p>anp160MailFlg を取得します。
     * @return anp160MailFlg
     */
    public int getAnp160MailFlg() {
        return anp160MailFlg__;
    }
    /**
     * <p>anp160MailFlg をセットします。
     * @param anp160MailFlg anp160MailFlg
     */
    public void setAnp160MailFlg(int anp160MailFlg) {
        anp160MailFlg__ = anp160MailFlg;
    }
    /**
     * <p>anp160usrUkoFlg を取得します。
     * @return anp160usrUkoFlg
     */
    public int getAnp160usrUkoFlg() {
        return anp160usrUkoFlg__;
    }
    /**
     * <p>anp160usrUkoFlg をセットします。
     * @param anp160usrUkoFlg anp160usrUkoFlg
     */
    public void setAnp160usrUkoFlg(int anp160usrUkoFlg) {
        anp160usrUkoFlg__ = anp160usrUkoFlg;
    }

}
