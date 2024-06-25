package jp.groupsession.v2.anp.anp070;


import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.anp.AbstractAnpiAdminAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 管理者設定メニュー画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp070Action extends AbstractAnpiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp070Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        Anp070Form uform = (Anp070Form) form;

        //管理者権限確認
        if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
            return getNotAdminSeniPage(map, req);
        }

        if (cmd.equals("anp070back")) {
            //戻る
            forward = __doBack(map, uform, req, res, con);

        } else if (cmd.equals("anp070base")) {
            //基本設定
            forward = map.findForward("base");

        } else if (cmd.equals("anp070mailtemp")) {
            //メールテンプレート管理
            forward = map.findForward("mailtemp");

        } else if (cmd.equals("anp070contact")) {
            //緊急連絡先設定状況
            forward = map.findForward("contact");

        } else if (cmd.equals("anp070allset")) {
            //緊急連絡先一括設定
            forward = map.findForward("allset");

        } else if (cmd.equals("anp070history")) {
            //配信履歴
            forward = map.findForward("history");

        } else {
            //初期化
            forward = map.getInputForward();
        }

        return forward;
    }

    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Anp070Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("戻る");

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            //メイン管理者画面へ遷移する。
            return map.findForward("mainAdmSetting");
        }

        return map.findForward("anpimain");
    }
}
