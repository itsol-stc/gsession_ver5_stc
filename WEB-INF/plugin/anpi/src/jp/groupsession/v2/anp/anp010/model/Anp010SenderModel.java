package jp.groupsession.v2.anp.anp010.model;

import java.io.Serializable;

/**
 * <p>送信者一覧行MODEL
 *
 * @author JTS
 */
public class Anp010SenderModel implements Serializable {


    /** USR_SID */
    private String usrSid__;
    /** 氏名 */
    private String name__;
    /** ユーザ状態区分 */
    private int usrJkbn__;
    /** ユーザログイン停止フラグ */
    private int usrUkoFlg__ = 0;
    /** 配信日時 */
    private String haisinDate__;
    /** 返信日時 */
    private String replyDate__;
    /** 状態 */
    private String jokyoflg__;
    /** 現在地 */
    private String placeflg__;
    /** 出社 */
    private String syusyaflg__;
    /** コメント */
    private String comment__;
    /** 配信フラグ */
    private int haisinflg__;
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public String getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(String usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>name を取得します。
     * @return name
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     */
    public void setName(String name) {
        name__ = name;
    }
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
     * <p>replyDate を取得します。
     * @return replyDate
     */
    public String getReplyDate() {
        return replyDate__;
    }
    /**
     * <p>replyDate をセットします。
     * @param replyDate replyDate
     */
    public void setReplyDate(String replyDate) {
        replyDate__ = replyDate;
    }
    /**
     * <p>jokyoflg を取得します。
     * @return jokyoflg
     */
    public String getJokyoflg() {
        return jokyoflg__;
    }
    /**
     * <p>jokyoflg をセットします。
     * @param jokyoflg jokyoflg
     */
    public void setJokyoflg(String jokyoflg) {
        jokyoflg__ = jokyoflg;
    }
    /**
     * <p>placeflg を取得します。
     * @return placeflg
     */
    public String getPlaceflg() {
        return placeflg__;
    }
    /**
     * <p>placeflg をセットします。
     * @param placeflg placeflg
     */
    public void setPlaceflg(String placeflg) {
        placeflg__ = placeflg;
    }
    /**
     * <p>syusyaflg を取得します。
     * @return syusyaflg
     */
    public String getSyusyaflg() {
        return syusyaflg__;
    }
    /**
     * <p>syusyaflg をセットします。
     * @param syusyaflg syusyaflg
     */
    public void setSyusyaflg(String syusyaflg) {
        syusyaflg__ = syusyaflg;
    }
    /**
     * <p>comment を取得します。
     * @return comment
     */
    public String getComment() {
        return comment__;
    }
    /**
     * <p>comment をセットします。
     * @param comment comment
     */
    public void setComment(String comment) {
        comment__ = comment;
    }
    /**
     * <p>haisinflg を取得します。
     * @return haisinflg
     */
    public int getHaisinflg() {
        return haisinflg__;
    }
    /**
     * <p>haisinflg をセットします。
     * @param haisinflg haisinflg
     */
    public void setHaisinflg(int haisinflg) {
        haisinflg__ = haisinflg;
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
