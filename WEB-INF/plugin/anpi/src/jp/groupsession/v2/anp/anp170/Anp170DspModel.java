package jp.groupsession.v2.anp.anp170;

import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能]  状況内容確認 結果状況ポップアップ画面の表示用モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp170DspModel extends AbstractModel {

    /** ユーザSID */
    private int anp170UsrSid__;
    /** 送信先(ユーザ) */
    private String anp170NameTo__;
    /** メールアドレス */
    private String anp170MailAddress__;
    /** 返信日時 */
    private String anp170HensinDate__;
    /** 安否状況 */
    private int anp170Jyokyo__;
    /** ユーザ状態区分 */
    private int anp170JyotaiKbn__;
    /**
     * <p>anp170UsrSid を取得します。
     * @return anp170UsrSid
     */
    public int getAnp170UsrSid() {
        return anp170UsrSid__;
    }
    /**
     * <p>anp170UsrSid をセットします。
     * @param anp170UsrSid anp170UsrSid
     */
    public void setAnp170UsrSid(int anp170UsrSid) {
        anp170UsrSid__ = anp170UsrSid;
    }
    /**
     * <p>anp170NameTo を取得します。
     * @return anp170NameTo
     */
    public String getAnp170NameTo() {
        return anp170NameTo__;
    }
    /**
     * <p>anp170NameTo をセットします。
     * @param anp170NameTo anp170NameTo
     */
    public void setAnp170NameTo(String anp170NameTo) {
        anp170NameTo__ = anp170NameTo;
    }
    /**
     * <p>anp170MailAddress を取得します。
     * @return anp170MailAddress
     */
    public String getAnp170MailAddress() {
        return anp170MailAddress__;
    }
    /**
     * <p>anp170MailAddress をセットします。
     * @param anp170MailAddress anp170MailAddress
     */
    public void setAnp170MailAddress(String anp170MailAddress) {
        anp170MailAddress__ = anp170MailAddress;
    }
    /**
     * <p>anp170HensinDate を取得します。
     * @return anp170HensinDate
     */
    public String getAnp170HensinDate() {
        return anp170HensinDate__;
    }
    /**
     * <p>anp170HensinDate をセットします。
     * @param anp170HensinDate anp170HensinDate
     */
    public void setAnp170HensinDate(String anp170HensinDate) {
        anp170HensinDate__ = anp170HensinDate;
    }
    /**
     * <p>anp170Jyokyo を取得します。
     * @return anp170Jyokyo
     */
    public int getAnp170Jyokyo() {
        return anp170Jyokyo__;
    }
    /**
     * <p>anp170Jyokyo をセットします。
     * @param anp170Jyokyo anp170Jyokyo
     */
    public void setAnp170Jyokyo(int anp170Jyokyo) {
        anp170Jyokyo__ = anp170Jyokyo;
    }
    /**
     * <p>anp170JyotaiKbn を取得します。
     * @return anp170JyotaiKbn
     */
    public int getAnp170JyotaiKbn() {
        return anp170JyotaiKbn__;
    }
    /**
     * <p>anp170JyotaiKbn をセットします。
     * @param anp170JyotaiKbn anp170JyotaiKbn
     */
    public void setAnp170JyotaiKbn(int anp170JyotaiKbn) {
        anp170JyotaiKbn__ = anp170JyotaiKbn;
    }
}
