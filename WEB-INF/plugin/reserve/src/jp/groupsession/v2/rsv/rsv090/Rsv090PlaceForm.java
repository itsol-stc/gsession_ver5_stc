package jp.groupsession.v2.rsv.rsv090;

import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] 施設予約 施設登録画面の地図情報を格納するフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv090PlaceForm extends AbstractGsForm {

    /** 場所/地図ファイルラベル */
    private String rsv090PlaceFileLabel__;
    /** 場所/地図ファイルバリュー */
    private String rsv090PlaceFileValue__;
    /** 場所/地図コメント */
    private String rsv090PlaceFileComment__;
    /** 場所/地図コメント表示区分 */
    private String rsv090PlaceFileCommentDspKbn__;

    /**
     * <p>rsv090PlaceFileComment を取得します。
     * @return rsv090PlaceFileComment
     */
    public String getRsv090PlaceFileComment() {
        return rsv090PlaceFileComment__;
    }

    /**
     * <p>rsv090PlaceFileComment をセットします。
     * @param rsv090PlaceFileComment rsv090PlaceFileComment
     */
    public void setRsv090PlaceFileComment(String rsv090PlaceFileComment) {
        rsv090PlaceFileComment__ = rsv090PlaceFileComment;
    }

    /**
     * <p>rsv090PlaceFileLabel を取得します。
     * @return rsv090PlaceFileLabel
     */
    public String getRsv090PlaceFileLabel() {
        return rsv090PlaceFileLabel__;
    }

    /**
     * <p>rsv090PlaceFileLabel をセットします。
     * @param rsv090PlaceFileLabel rsv090PlaceFileLabel
     */
    public void setRsv090PlaceFileLabel(String rsv090PlaceFileLabel) {
        rsv090PlaceFileLabel__ = rsv090PlaceFileLabel;
    }

    /**
     * <p>rsv090PlaceFileValue を取得します。
     * @return rsv090PlaceFileValue
     */
    public String getRsv090PlaceFileValue() {
        return rsv090PlaceFileValue__;
    }

    /**
     * <p>rsv090PlaceFileValue をセットします。
     * @param rsv090PlaceFileValue rsv090PlaceFileValue
     */
    public void setRsv090PlaceFileValue(String rsv090PlaceFileValue) {
        rsv090PlaceFileValue__ = rsv090PlaceFileValue;
    }

    /**
     * <p>rsv090PlaceFileCommentDspKbn を取得します。
     * @return rsv090PlaceFileCommentDspKbn
     */
    public String getRsv090PlaceFileCommentDspKbn() {
        return rsv090PlaceFileCommentDspKbn__;
    }

    /**
     * <p>rsv090PlaceFileCommentDspKbn をセットします。
     * @param rsv090PlaceFileCommentDspKbn rsv090PlaceFileCommentDspKbn
     */
    public void setRsv090PlaceFileCommentDspKbn(String rsv090PlaceFileCommentDspKbn) {
        rsv090PlaceFileCommentDspKbn__ = rsv090PlaceFileCommentDspKbn;
    }
}