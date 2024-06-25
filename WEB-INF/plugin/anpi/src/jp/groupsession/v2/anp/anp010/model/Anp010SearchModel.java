package jp.groupsession.v2.anp.anp010.model;

import java.io.Serializable;

/**
 * <p>送信者一覧検索条件MODEL
 *
 * @author JTS
 */
public class Anp010SearchModel implements Serializable {

    /** 安否確認SID */
    private int anpiSid__;
    /** USR_SID */
    private int usrSid__;
    /** グループSID */
    private String gpSid__;
    /** 検索モード */
    private int searchKbn__;
    /** 配信状況  */
    private int sendKbn__;
    /** 回答状況  */
    private int answerKbn__;
    /** 安否状況   */
    private int anpKbn__;
    /** 現在地  */
    private int placeKbn__;
    /** 出社状況  */
    private int syusyaKbn__;

    /**
     * <p>anpiSid を取得します。
     * @return anpiSid
     */
    public int getAnpiSid() {
        return anpiSid__;
    }
    /**
     * <p>anpiSid をセットします。
     * @param anpiSid anpiSid
     */
    public void setAnpiSid(int anpiSid) {
        anpiSid__ = anpiSid;
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
     * <p>gpSid を取得します。
     * @return gpSid
     */
    public String getGpSid() {
        return gpSid__;
    }
    /**
     * <p>gpSid をセットします。
     * @param gpSid gpSid
     */
    public void setGpSid(String gpSid) {
        gpSid__ = gpSid;
    }
    /**
     * <p>searchKbn を取得します。
     * @return searchKbn
     */
    public int getSearchKbn() {
        return searchKbn__;
    }
    /**
     * <p>searchKbn をセットします。
     * @param searchKbn searchKbn
     */
    public void setSearchKbn(int searchKbn) {
        searchKbn__ = searchKbn;
    }
    /**
     * <p>sendKbn を取得します。
     * @return sendKbn
     */
    public int getSendKbn() {
        return sendKbn__;
    }
    /**
     * <p>sendKbn をセットします。
     * @param sendKbn sendKbn
     */
    public void setSendKbn(int sendKbn) {
        sendKbn__ = sendKbn;
    }
    /**
     * <p>answerKbn を取得します。
     * @return answerKbn
     */
    public int getAnswerKbn() {
        return answerKbn__;
    }
    /**
     * <p>answerKbn をセットします。
     * @param answerKbn answerKbn
     */
    public void setAnswerKbn(int answerKbn) {
        answerKbn__ = answerKbn;
    }
    /**
     * <p>anpKbn を取得します。
     * @return anpKbn
     */
    public int getAnpKbn() {
        return anpKbn__;
    }
    /**
     * <p>anpKbn をセットします。
     * @param anpKbn anpKbn
     */
    public void setAnpKbn(int anpKbn) {
        anpKbn__ = anpKbn;
    }
    /**
     * <p>placeKbn を取得します。
     * @return placeKbn
     */
    public int getPlaceKbn() {
        return placeKbn__;
    }
    /**
     * <p>placeKbn をセットします。
     * @param placeKbn placeKbn
     */
    public void setPlaceKbn(int placeKbn) {
        placeKbn__ = placeKbn;
    }
    /**
     * <p>syusyaKbn を取得します。
     * @return syusyaKbn
     */
    public int getSyusyaKbn() {
        return syusyaKbn__;
    }
    /**
     * <p>syusyaKbn をセットします。
     * @param syusyaKbn syusyaKbn
     */
    public void setSyusyaKbn(int syusyaKbn) {
        syusyaKbn__ = syusyaKbn;
    }
}
