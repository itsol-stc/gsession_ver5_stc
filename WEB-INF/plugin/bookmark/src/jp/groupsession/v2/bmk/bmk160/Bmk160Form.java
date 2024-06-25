package jp.groupsession.v2.bmk.bmk160;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.bmk100.Bmk100Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 セキュリティ設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk160Form extends Bmk100Form {
    /** 初期表示フラグ */
    private int bmk160initFlg__ = 0;
    /** ブックマーク情報取得制限区分 */
    private int bmk160LimitKbn__ = GSConstBookmark.LIMIT_YES;
    /**
     * <p>bmk160initFlg を取得します。
     * @return bmk160initFlg
     * @see jp.groupsession.v2.bmk.bmk160.Bmk160Form#bmk160initFlg__
     */
    public int getBmk160initFlg() {
        return bmk160initFlg__;
    }
    /**
     * <p>bmk160initFlg をセットします。
     * @param bmk160initFlg bmk160initFlg
     * @see jp.groupsession.v2.bmk.bmk160.Bmk160Form#bmk160initFlg__
     */
    public void setBmk160initFlg(int bmk160initFlg) {
        bmk160initFlg__ = bmk160initFlg;
    }
    /**
     * <p>bmk160LimitKbn を取得します。
     * @return bmk160LimitKbn
     * @see jp.groupsession.v2.bmk.bmk160.Bmk160Form#bmk160LimitKbn__
     */
    public int getBmk160LimitKbn() {
        return bmk160LimitKbn__;
    }
    /**
     * <p>bmk160LimitKbn をセットします。
     * @param bmk160LimitKbn bmk160LimitKbn
     * @see jp.groupsession.v2.bmk.bmk160.Bmk160Form#bmk160LimitKbn__
     */
    public void setBmk160LimitKbn(int bmk160LimitKbn) {
        bmk160LimitKbn__ = bmk160LimitKbn;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        if (bmk160LimitKbn__ != GSConstBookmark.LIMIT_NO
        && bmk160LimitKbn__ != GSConstBookmark.LIMIT_YES) {
            GsMessage gsMsg = new GsMessage(req);

            String eprefix = "bmk160LimitKbn.";
            ActionMessage msg = new ActionMessage("error.input.notvalidate.data",
                        gsMsg.getMessage("bmk.bmk160.01"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notvalidate.data");
        }
        return errors;
    }
}
