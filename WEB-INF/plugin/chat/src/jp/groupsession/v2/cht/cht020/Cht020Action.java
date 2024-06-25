package jp.groupsession.v2.cht.cht020;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cht.AbstractChatAdminAction;
import jp.groupsession.v2.man.GSConstMain;

/**
 *
 * <br>[機  能] チャット 管理者設定 のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht020Action extends AbstractChatAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht020Action.class);

    /**
     * <br>アクション実行
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
        Cht020Form thisForm = (Cht020Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);


        if (cmd.equals("cht040")) {
            // 機能制限設定
            forward = map.findForward("cht040");
        } else if (cmd.equals("cht050")) {
            // 自動データ削除設定
            forward = map.findForward("cht050");
        }  else if (cmd.equals("cht060")) {
            // 手動データ削除
            forward = map.findForward("cht060");
        } else if (cmd.equals("cht070")) {
            // 統計情報
            forward = map.findForward("cht070");
        } else if (cmd.equals("cht080")) {
            // 特例アクセス設定
            forward = map.findForward("cht080");
        } else if (cmd.equals("cht100")) {
            //  グループ管理
            forward = map.findForward("cht100");
        } else if (cmd.equals("cht140")) {
            //  カテゴリ管理
            forward = map.findForward("cht140");
        } else if (cmd.equals("back")) {
            // 前の画面へ戻る
            if (thisForm.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
                forward = map.findForward("main");
            } else {
                forward = map.findForward("cht010");
            }
        } else {
            // 初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cht020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        return map.getInputForward();
    }

}
