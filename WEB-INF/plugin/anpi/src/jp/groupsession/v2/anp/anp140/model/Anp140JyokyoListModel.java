package jp.groupsession.v2.anp.anp140.model;

/**
 * <p>結果状況一覧MODEL
 *
 * @author JTS
 */
public class Anp140JyokyoListModel {

    /** 種別 */
    private int apsType__;
    /** ユーザSID */
    private int usrSid__;
    /** グループSID */
    private int grpSid__;
    /** 送信先(グループ) */
    private String grpNameTo__;
    /** 送信先(ユーザ) */
    private String nameTo__;
    /** メールアドレス */
    private String mailAddress__;
    /** 返信日時 */
    private String hensinDate__;
    /** 安否状況 */
    private int anpJyokyo__;
    /** ユーザ状態区分 */
    private int jyotaiKbn__;
    /** ログイン停止フラグ */
    private int usrUkoFlg__;

    /**
     * <p>送信先を取得する
     * @return nameTo
     */
    public String getNameTo() {
        return nameTo__;
    }

    /**<p>送信先を設定する
     * @param nameTo セットする nameTo
     */
    public void setNameTo(String nameTo) {
        nameTo__ = nameTo;
    }

    /**
     * <p>mailAddress を取得します。
     * @return mailAddress
     */
    public String getMailAddress() {
        return mailAddress__;
    }

    /**
     * <p>mailAddress をセットします。
     * @param mailAddress mailAddress
     */
    public void setMailAddress(String mailAddress) {
        mailAddress__ = mailAddress;
    }

    /**
     * <p>返信日時を取得する
     * @return hensinDate
     */
    public String getHensinDate() {
        return hensinDate__;
    }

    /**
     * <p>返信日時を設定する
     * @param hensinDate セットする hensinDate
     */
    public void setHensinDate(String hensinDate) {
        hensinDate__ = hensinDate;
    }

    /**
     * <p>安否状況を取得する
     * @return anpJyokyo
     */
    public int getAnpJyokyo() {
        return anpJyokyo__;
    }

    /**
     * <p>安否状況を設定する
     * @param anpJyokyo セットする anpJyokyo
     */
    public void setAnpJyokyo(int anpJyokyo) {
        anpJyokyo__ = anpJyokyo;
    }

    /**
     * <p>ユーザ状態区分を取得する
     * @return jyotaiKbn
     */
    public int getJyotaiKbn() {
        return jyotaiKbn__;
    }

    /**
     * <p>ユーザ状態区分を設定する
     * @param jyotaiKbn セットする jyotaiKbn
     */
    public void setJyotaiKbn(int jyotaiKbn) {
        jyotaiKbn__ = jyotaiKbn;
    }

    /**
     * <p>apsType を取得します。
     * @return apsType
     */
    public int getApsType() {
        return apsType__;
    }

    /**
     * <p>apsType をセットします。
     * @param apsType apsType
     */
    public void setApsType(int apsType) {
        apsType__ = apsType;
    }

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>grpNameTo を取得します。
     * @return grpNameTo
     */
    public String getGrpNameTo() {
        return grpNameTo__;
    }

    /**
     * <p>grpNameTo をセットします。
     * @param grpNameTo grpNameTo
     */
    public void setGrpNameTo(String grpNameTo) {
        grpNameTo__ = grpNameTo;
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
