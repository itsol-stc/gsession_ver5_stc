package jp.groupsession.v2.mem.mem020;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.mem.AbstractMemoAdminAction;

/**
 * <br>[機  能] メモ 管理者設定 メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem020Action extends AbstractMemoAdminAction {

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(
            ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        //画面遷移を表す
        //コマンドパラメータの取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        ActionForward forward = null;

        if (cmd.equals("mem020Atdel")) {
            //「自動削除設定」を押下したときの遷移先
            forward = map.findForward("mem020Atdel");

        } else if (cmd.equals("mem020Back")) {
            //戻るボタンを押下したときの遷移先
            forward = map.findForward("mem020Back");

        } else {
            forward = map.getInputForward();
        }

        return forward;
    }
}