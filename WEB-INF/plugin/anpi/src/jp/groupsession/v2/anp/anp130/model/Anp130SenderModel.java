package jp.groupsession.v2.anp.anp130.model;

/**
 * <br>[機  能] 安否確認配信履歴MODEL
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp130SenderModel {

    /** 安否確認SID */
    private int anpiSid__;
    /** 件名 */
    private String subject__;
    /** 訓練モードフラグ */
    private int knrenFlg__ = 0;
    /** 配信ユーザ名 */
    private String name__;
    /** 配信日時 */
    private String haisinDate__;
    /** 完了日時 */
    private String kanryoDate__;
    /** ユーザ状態区分 */
    private int jyotaiKbn__;
    /** ログイン停止フラグ */
    private int usrUkoFlg__;

    /**
     * <p>安否確認SIDを取得する
     * @return anpiSid
     */
    public int getAnpiSid() {
        return anpiSid__;
    }

    /**
     * <p>安否確認SIDを設定する
     * @param anpiSid セットする anpiSid
     */
    public void setAnpiSid(int anpiSid) {
        anpiSid__ = anpiSid;
    }

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
     * <p>配信者名を取得する
     * @return name
     */
    public String getName() {
        return name__;
    }

    /**
     * <p>配信者名を設定する
     * @param name セットする name
     */
    public void setName(String name) {
        name__ = name;
    }

    /**
     * <p>配信日時を取得する
     * @return haisinDate
     */
    public String getHaisinDate() {
        return haisinDate__;
    }

    /**
     * <p>配信日時を設定する
     * @param haisinDate セットする haisinDate
     */
    public void setHaisinDate(String haisinDate) {
        haisinDate__ = haisinDate;
    }

    /**
     * <p>完了日時を取得する
     * @return kanryoDate
     */
    public String getKanryoDate() {
        return kanryoDate__;
    }

    /**
     * <p>完了日時を設定する
     * @param kanryoDate セットする kanryoDate
     */
    public void setKanryoDate(String kanryoDate) {
        kanryoDate__ = kanryoDate;
    }

    /**
     * <p>状態区分を取得する
     * @return jyotaiKbn
     */
    public int getJyotaiKbn() {
        return jyotaiKbn__;
    }

    /**
     * <p>状態区分を設定する
     * @param jyotaiKbn セットする jyotaiKbn
     */
    public void setJyotaiKbn(int jyotaiKbn) {
        jyotaiKbn__ = jyotaiKbn;
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
