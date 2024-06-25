package jp.groupsession.v2.ntp.ntp040.model;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.model.NtpCommentModel;

/**
 * <br>[機  能] 日報コメントデータを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp040CommentModel {
    /** コメントデータmodel  */
    private NtpCommentModel ntp040CommentMdl__ = null;
    /** ユーザ情報model  */
    private CmnUsrmInfModel ntp040UsrInfMdl__ = null;
    /** 投稿日時  */
    private String ntp040CommentDate__ = null;
    /** コメント削除フラグ 0:削除不可 1:削除可  */
    private int ntp040CommentDelFlg__ = 0;
    /**
     * <p>ntp040CommentMdl を取得します。
     * @return ntp040CommentMdl
     */
    public NtpCommentModel getNtp040CommentMdl() {
        return ntp040CommentMdl__;
    }
    /**
     * <p>ntp040CommentMdl をセットします。
     * @param ntp040CommentMdl ntp040CommentMdl
     */
    public void setNtp040CommentMdl(NtpCommentModel ntp040CommentMdl) {
        ntp040CommentMdl__ = ntp040CommentMdl;
    }
    /**
     * <p>ntp040UsrInfMdl を取得します。
     * @return ntp040UsrInfMdl
     */
    public CmnUsrmInfModel getNtp040UsrInfMdl() {
        return ntp040UsrInfMdl__;
    }
    /**
     * <p>ntp040UsrInfMdl をセットします。
     * @param ntp040UsrInfMdl ntp040UsrInfMdl
     */
    public void setNtp040UsrInfMdl(CmnUsrmInfModel ntp040UsrInfMdl) {
        ntp040UsrInfMdl__ = ntp040UsrInfMdl;
    }
    /**
     * <p>ntp040CommentDate を取得します。
     * @return ntp040CommentDate
     */
    public String getNtp040CommentDate() {
        return ntp040CommentDate__;
    }
    /**
     * <p>ntp040CommentDate をセットします。
     * @param ntp040CommentDate ntp040CommentDate
     */
    public void setNtp040CommentDate(String ntp040CommentDate) {
        ntp040CommentDate__ = ntp040CommentDate;
    }
    /**
     * <p>ntp040CommentDelFlg を取得します。
     * @return ntp040CommentDelFlg
     */
    public int getNtp040CommentDelFlg() {
        return ntp040CommentDelFlg__;
    }
    /**
     * <p>ntp040CommentDelFlg をセットします。
     * @param ntp040CommentDelFlg ntp040CommentDelFlg
     */
    public void setNtp040CommentDelFlg(int ntp040CommentDelFlg) {
        ntp040CommentDelFlg__ = ntp040CommentDelFlg;
    }
}
