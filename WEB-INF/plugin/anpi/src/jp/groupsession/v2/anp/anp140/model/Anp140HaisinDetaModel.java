package jp.groupsession.v2.anp.anp140.model;

/**
 * <p>安否確認配信データMODEL
 *
 * @author JTS
 */
public class Anp140HaisinDetaModel {

    /** 配信者 */
    private String name__;
    /** 配信者 */
    private int usrJkbn__;
    /** 配信者 */
    private int usrUkoFlg__;
    /** 件名 */
    private String subject__;
    /** 本文1 */
    private String text1__;
    /** 本文2 */
    private String text2__;
    /** 訓練モードフラグ */
    private int knrenFlg__ = 0;

    /**
     * <p>件名を取得する
     * @return subject
     */
    public String getSubject() {
        return subject__;
    }

    /**
     * <p>件名を設定する
     * @param subject セットする subject
     */
    public void setSubject(String subject) {
        subject__ = subject;
    }

    /**
     * <p>配信者を取得する
     * @return name
     */
    public String getName() {
        return name__;
    }

    /**
     * <p>配信者を設定する
     * @param name セットする name
     */
    public void setName(String name) {
        name__ = name;
    }

    /**
     * <p>本文1を取得する
     * @return text1
     */
    public String getText1() {
        return text1__;
    }

    /**
     * <p>本文1を設定する
     * @param text1 セットする text1
     */
    public void setText1(String text1) {
        text1__ = text1;
    }

    /**
     * <p>本文2を取得する
     * @return text2
     */
    public String getText2() {
        return text2__;
    }

    /**
     * <p>本文2を設定する
     * @param text2 セットする text2
     */
    public void setText2(String text2) {
        text2__ = text2;
    }

    /**
     * <p>knrenFlg を取得します。
     * @return knrenFlg
     */
    public int getKnrenFlg() {
        return knrenFlg__;
    }

    /**
     * <p>knrenFlg をセットします。
     * @param knrenFlg knrenFlg
     */
    public void setKnrenFlg(int knrenFlg) {
        knrenFlg__ = knrenFlg;
    }

    /**
     * <p>usrJkbn を取得します。
     * @return usrJkbn
     */
    public int getUsrJkbn() {
        return usrJkbn__;
    }

    /**
     * <p>usrJkbn をセットします。
     * @param usrJkbn usrJkbn
     */
    public void setUsrJkbn(int usrJkbn) {
        usrJkbn__ = usrJkbn;
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
