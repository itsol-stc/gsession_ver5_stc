package jp.groupsession.v2.anp.model;

import java.io.Serializable;

/**
 * <p>現在の状況集計MODEL
 *
 * @author JTS
 */
public class AnpStateModel implements Serializable {

    /** 配信日時 */
    private String haisinDate__;
    /** 再送日時 */
    private String resendDate__;
    /** 返信状況 */
    private String replyState__;
    /** 状況集計（状態・無事） */
    private String jokyoGood__;
    /** 状況集計（状態・軽症） */
    private String jokyoKeisyo__;
    /** 状況集計（状態・重症） */
    private String jokyoJusyo__;
    /** 状況集計（出社・可能） */
    private String syusyaOk__;
    /** 状況集計（出社・不可） */
    private String syusyaNo__;
    /** 最終回答日時 */
    private String lastDate__;

    /** 経過フラグ（1:全ユーザから返信あり） */
    private int stateFlg__ = 0;

    /**
     * <p>haisinDate を取得します。
     * @return haisinDate
     */
    public String getHaisinDate() {
        return haisinDate__;
    }
    /**
     * <p>haisinDate をセットします。
     * @param haisinDate haisinDate
     */
    public void setHaisinDate(String haisinDate) {
        haisinDate__ = haisinDate;
    }
    /**
     * <p>resendDate を取得します。
     * @return resendDate
     */
    public String getResendDate() {
        return resendDate__;
    }
    /**
     * <p>resendDate をセットします。
     * @param resendDate resendDate
     */
    public void setResendDate(String resendDate) {
        resendDate__ = resendDate;
    }
    /**
     * <p>replyState を取得します。
     * @return replyState
     */
    public String getReplyState() {
        return replyState__;
    }
    /**
     * <p>replyState をセットします。
     * @param replyState replyState
     */
    public void setReplyState(String replyState) {
        replyState__ = replyState;
    }
    /**
     * <p>jokyoGood を取得します。
     * @return jokyoGood
     */
    public String getJokyoGood() {
        return jokyoGood__;
    }
    /**
     * <p>jokyoGood をセットします。
     * @param jokyoGood jokyoGood
     */
    public void setJokyoGood(String jokyoGood) {
        jokyoGood__ = jokyoGood;
    }
    /**
     * <p>jokyoKeisyo を取得します。
     * @return jokyoKeisyo
     */
    public String getJokyoKeisyo() {
        return jokyoKeisyo__;
    }
    /**
     * <p>jokyoKeisyo をセットします。
     * @param jokyoKeisyo jokyoKeisyo
     */
    public void setJokyoKeisyo(String jokyoKeisyo) {
        jokyoKeisyo__ = jokyoKeisyo;
    }
    /**
     * <p>jokyoJusyo を取得します。
     * @return jokyoJusyo
     */
    public String getJokyoJusyo() {
        return jokyoJusyo__;
    }
    /**
     * <p>jokyoJusyo をセットします。
     * @param jokyoJusyo jokyoJusyo
     */
    public void setJokyoJusyo(String jokyoJusyo) {
        jokyoJusyo__ = jokyoJusyo;
    }
    /**
     * <p>syusyaOk を取得します。
     * @return syusyaOk
     */
    public String getSyusyaOk() {
        return syusyaOk__;
    }
    /**
     * <p>syusyaOk をセットします。
     * @param syusyaOk syusyaOk
     */
    public void setSyusyaOk(String syusyaOk) {
        syusyaOk__ = syusyaOk;
    }
    /**
     * <p>syusyaNo を取得します。
     * @return syusyaNo
     */
    public String getSyusyaNo() {
        return syusyaNo__;
    }
    /**
     * <p>syusyaNo をセットします。
     * @param syusyaNo syusyaNo
     */
    public void setSyusyaNo(String syusyaNo) {
        syusyaNo__ = syusyaNo;
    }
    /**
     * <p>stateFlg を取得します。
     * @return stateFlg
     */
    public int getStateFlg() {
        return stateFlg__;
    }
    /**
     * <p>stateFlg をセットします。
     * @param stateFlg stateFlg
     */
    public void setStateFlg(int stateFlg) {
        stateFlg__ = stateFlg;
    }
    /**
     * <p>lastDate を取得します。
     * @return lastDate
     */
    public String getLastDate() {
        return lastDate__;
    }
    /**
     * <p>lastDate をセットします。
     * @param lastDate lastDate
     */
    public void setLastDate(String lastDate) {
        lastDate__ = lastDate;
    }

}
