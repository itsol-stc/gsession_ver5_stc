package jp.groupsession.v2.ntp.ntp030.model;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.model.NtpCommentModel;

/**
 * <br>[機  能] 日報コメントデータを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp030CommentModel {
    /** コメントデータmodel  */
    private NtpCommentModel ntp030CommentMdl__ = null;
    /** ユーザ情報model  */
    private CmnUsrmInfModel ntp030UsrInfMdl__ = null;
    /** 投稿日時  */
    private String ntp030CommentDate__ = null;
    /** コメント削除フラグ 0:削除不可 1:削除可  */
    private int ntp030CommentDelFlg__ = 0;
    /**
     * <p>ntp030CommentMdl を取得します。
     * @return ntp030CommentMdl
     */
    public NtpCommentModel getNtp030CommentMdl() {
        return ntp030CommentMdl__;
    }
    /**
     * <p>ntp030CommentMdl をセットします。
     * @param ntp030CommentMdl ntp030CommentMdl
     */
    public void setNtp030CommentMdl(NtpCommentModel ntp030CommentMdl) {
        ntp030CommentMdl__ = ntp030CommentMdl;
    }
    /**
     * <p>ntp030UsrInfMdl を取得します。
     * @return ntp030UsrInfMdl
     */
    public CmnUsrmInfModel getNtp030UsrInfMdl() {
        return ntp030UsrInfMdl__;
    }
    /**
     * <p>ntp030UsrInfMdl をセットします。
     * @param ntp030UsrInfMdl ntp030UsrInfMdl
     */
    public void setNtp030UsrInfMdl(CmnUsrmInfModel ntp030UsrInfMdl) {
        ntp030UsrInfMdl__ = ntp030UsrInfMdl;
    }
    /**
     * <p>ntp030CommentDate を取得します。
     * @return ntp030CommentDate
     */
    public String getNtp030CommentDate() {
        return ntp030CommentDate__;
    }
    /**
     * <p>ntp030CommentDate をセットします。
     * @param ntp030CommentDate ntp030CommentDate
     */
    public void setNtp030CommentDate(String ntp030CommentDate) {
        ntp030CommentDate__ = ntp030CommentDate;
    }
    /**
     * <p>ntp030CommentDelFlg を取得します。
     * @return ntp030CommentDelFlg
     */
    public int getNtp030CommentDelFlg() {
        return ntp030CommentDelFlg__;
    }
    /**
     * <p>ntp030CommentDelFlg をセットします。
     * @param ntp030CommentDelFlg ntp030CommentDelFlg
     */
    public void setNtp030CommentDelFlg(int ntp030CommentDelFlg) {
        ntp030CommentDelFlg__ = ntp030CommentDelFlg;
    }

}
