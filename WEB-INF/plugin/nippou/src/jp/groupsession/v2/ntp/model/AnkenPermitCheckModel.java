package jp.groupsession.v2.ntp.model;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;

/**
 *
 * <br>[機  能] 日報検索時、案件の権限確認関連の引数用オブジェクト
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AnkenPermitCheckModel {
    /** リクエストモデル*/
    RequestModel reqMdl__;
    /** 管理者権限をもつかどうか*/
    boolean isAdmin__;
    /**
     * コンストラクタ
     * @param reqMdl リクエストモデル
     * @param isAdmin 管理者権限をもつかどうか
     */
    public AnkenPermitCheckModel(RequestModel reqMdl, boolean isAdmin) {
        reqMdl__ = reqMdl;
        isAdmin__ = isAdmin;
    }
    /**
     * <p>reqMdl を取得します。
     * @return reqMdl
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }
    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <p>isAdmin を取得します。
     * @return isAdmin
     */
    public boolean isAdmin() {
        return isAdmin__;
    }
    /**
     * <p>isAdmin をセットします。
     * @param isAdmin isAdmin
     */
    public void setAdmin(boolean isAdmin) {
        isAdmin__ = isAdmin;
    }
    /**
     *
     * <br>[機  能] セッションユーザSIDの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return sessionUsrSid
     */
    public int getSessionUsrSid() {
        return reqMdl__.getSmodel().getUsrsid();
    }
    /**
     * <p>閲覧可能か判定します。
     * @param isAdmin 管理者権限
     * @param permKbn 案件権限区分
     * @param isTanto 担当者か
     * @param isPermitUsr 許可ユーザか
     * @param isPermitGrp 許可グループか
     * @return 閲覧可能か
     */
    public boolean hanteiViewable(boolean isAdmin,
            int permKbn,
            boolean isTanto,
            boolean isPermitUsr,
            boolean isPermitGrp) {
        if (isAdmin) {
            return true;
        }
        if (permKbn == GSConstNippou.NAP_KBN_ALL) {
            return true;
        }
        if (isTanto) {
            return true;
        }
        if (isPermitUsr) {
            return true;
        }
        if (isPermitGrp) {
            return true;
        }
        return false;
    }

}
