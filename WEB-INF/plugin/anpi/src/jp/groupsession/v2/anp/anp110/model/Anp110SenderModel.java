package jp.groupsession.v2.anp.anp110.model;


/**
 * <p>安否確認 管理者設定・緊急連絡先設定状況画面 表示MODEL
 *
 * @author JTS
 */
public class Anp110SenderModel {

    /** USR_SID */
    private int usrSid__;
    /** 社員/職員番号 */
    private String syainNo__;
    /** 氏名 */
    private String name__;
    /** 役職 */
    private String post__;
    /** メールアドレス */
    private String mailAdr__;
    /** 電話番号 */
    private String telNo__;
    /** ログイン停止*/
    private int usrUkoFlg__;


    /**
     * <p>USR_SIDを取得します
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>USR_SIDを設定します
     * @param usrSid セットする usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>社員/職員番号を取得します
     * @return syainNo
     */
    public String getSyainNo() {
        return syainNo__;
    }

    /**
     * <p>社員/職員番号を設定します
     * @param syainNo セットする syainNo
     */
    public void setSyainNo(String syainNo) {
        syainNo__ = syainNo;
    }

    /**
     * <p>氏名を取得します
     * @return name
     */
    public String getName() {
        return name__;
    }

    /**
     * <p>氏名を設定します
     * @param name セットする name
     */
    public void setName(String name) {
        name__ = name;
    }

    /**
     * <p>メールアドレスを取得します
     * @return mailAdr
     */
    public String getMailAdr() {
        return mailAdr__;
    }

    /**
     * <p>メールアドレスを設定します
     * @param mailAdr セットする mailAdr
     */
    public void setMailAdr(String mailAdr) {
        mailAdr__ = mailAdr;
    }

    /**
     * <p>電話番号を取得します
     * @return telNo
     */
    public String getTelNo() {
        return telNo__;
    }

    /**
     * <p>電話番号を設定します
     * @param telNo セットする telNo
     */
    public void setTelNo(String telNo) {
        telNo__ = telNo;
    }

    /**
     * <p>post を取得します。
     * @return post
     */
    public String getPost() {
        return post__;
    }

    /**
     * <p>post をセットします。
     * @param post post
     */
    public void setPost(String post) {
        post__ = post;
    }

    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }

    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }

}
