package jp.groupsession.v2.ptl.ptl020;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.ptl.AbstractPortalAdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ポータル 管理者設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl020Action extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl020Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
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
        log__.debug("START");

        ActionForward forward = null;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Ptl020Form ptlForm = (Ptl020Form) form;

        if (cmd.equals("ptlManager")) {
            //「ポータル管理」クリック
            forward = map.findForward("ptlManager");

        } else if (cmd.equals("pletManager")) {
            //「ポートレット管理」クリック
            forward = map.findForward("pletManager");

        } else if (cmd.equals("powManager")) {
            //「権限設定」クリック
            forward = map.findForward("powManager");

        } else if (cmd.equals("pletInitValue")) {
            //「初期値設定」クリック
            forward = map.findForward("pletInitValue");

        } else if (cmd.equals("backList")) {
            //戻るボタンクリック
            forward = __doBack(map, ptlForm, req, res, con);

        } else {
            //初期表示
            forward = map.getInputForward();
        }

        return forward;
    }

    /**
     * <br>[機  能] 遷移元画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doBack(ActionMapping map,
        Ptl020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException {

        ActionForward forward = null;

        if (form.getPtlBackPage() == GSConstPortal.BACKSCREEN_MAIN_ADMIN_MENU) {
            //メインの管理者メニュー画面へ遷移する。
            forward = map.findForward("gf_main_kanri");
        } else {
            if (form.getPtlMainSid() == GSConstPortal.PORTAL_SID_MAIN) {
                //メイン画面へ遷移する。
                forward = map.findForward("gsmain");
            } else {
                //ポータルメイン画面へ遷移する。
                forward = map.findForward("ptlList");
            }
        }

        return forward;
    }
}

