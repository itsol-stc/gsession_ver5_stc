package jp.groupsession.v2.ntp.ntp030.model;

/**
 * <br>[機  能] 日報表示用コメントデータを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp030DspCommentModel {
    /** コメントSID  */
    private String commentSid__ = null;
    /** 投稿者名  */
    private String commentUserName__ = null;
    /** 投稿者 ログイン停止  */
    private int commentUserUkoFlg__ = 0;
    /** 投稿者BinSid  */
    private String commentUserBinSid__ = null;
    /** 投稿者写真公開区分  */
    private String commentUsiPictKf__ = null;
    /** 投稿日時  */
    private String commentDate__ = null;
    /** 投稿内容  */
    private String commentValue__ = null;
    /** コメント削除区分 0:削除不可 1:削除可  */
    private int commentDelKbn__ = 0;

    /**
     * <p>commentUserName を取得します。
     * @return commentUserName
     */
    public String getCommentUserName() {
        return commentUserName__;
    }
    /**
     * <p>commentUserName をセットします。
     * @param commentUserName commentUserName
     */
    public void setCommentUserName(String commentUserName) {
        commentUserName__ = commentUserName;
    }
    /**
     * <p>commentDate を取得します。
     * @return commentDate
     */
    public String getCommentDate() {
        return commentDate__;
    }
    /**
     * <p>commentDate をセットします。
     * @param commentDate commentDate
     */
    public void setCommentDate(String commentDate) {
        commentDate__ = commentDate;
    }
    /**
     * <p>commentValue を取得します。
     * @return commentValue
     */
    public String getCommentValue() {
        return commentValue__;
    }
    /**
     * <p>commentValue をセットします。
     * @param commentValue commentValue
     */
    public void setCommentValue(String commentValue) {
        commentValue__ = commentValue;
    }
    /**
     * <p>commentUserBinSid を取得します。
     * @return commentUserBinSid
     */
    public String getCommentUserBinSid() {
        return commentUserBinSid__;
    }
    /**
     * <p>commentUserBinSid をセットします。
     * @param commentUserBinSid commentUserBinSid
     */
    public void setCommentUserBinSid(String commentUserBinSid) {
        commentUserBinSid__ = commentUserBinSid;
    }
    /**
     * <p>commentUsiPictKf を取得します。
     * @return commentUsiPictKf
     */
    public String getCommentUsiPictKf() {
        return commentUsiPictKf__;
    }
    /**
     * <p>commentUsiPictKf をセットします。
     * @param commentUsiPictKf commentUsiPictKf
     */
    public void setCommentUsiPictKf(String commentUsiPictKf) {
        commentUsiPictKf__ = commentUsiPictKf;
    }
    /**
     * <p>commentSid を取得します。
     * @return commentSid
     */
    public String getCommentSid() {
        return commentSid__;
    }
    /**
     * <p>commentSid をセットします。
     * @param commentSid commentSid
     */
    public void setCommentSid(String commentSid) {
        commentSid__ = commentSid;
    }
    /**
     * <p>commentDelKbn を取得します。
     * @return commentDelKbn
     */
    public int getCommentDelKbn() {
        return commentDelKbn__;
    }
    /**
     * <p>commentDelKbn をセットします。
     * @param commentDelKbn commentDelKbn
     */
    public void setCommentDelKbn(int commentDelKbn) {
        commentDelKbn__ = commentDelKbn;
    }
    /**
     * <p>commentUserUkoFlg を取得します。
     * @return commentUserUkoFlg
     */
    public int getCommentUserUkoFlg() {
        return commentUserUkoFlg__;
    }
    /**
     * <p>commentUserUkoFlg をセットします。
     * @param commentUserUkoFlg commentUserUkoFlg
     */
    public void setCommentUserUkoFlg(int commentUserUkoFlg) {
        commentUserUkoFlg__ = commentUserUkoFlg;
    }
}
