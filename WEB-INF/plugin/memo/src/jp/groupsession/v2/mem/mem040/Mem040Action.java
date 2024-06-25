package jp.groupsession.v2.mem.mem040;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.mem.AbstractMemoAction;

/**
 * <br>[機  能] メモ帳 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem040Action extends AbstractMemoAction {

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
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

        if (cmd.equals("mem040Label")) {
            //「ラベル管理」クリック
            forward = map.findForward("mem040Label");

        } else if (cmd.equals("mem040Back")) {
            //戻るボタンクリック
            forward = map.findForward("mem040Back");

        } else {
            //初期表示
            forward = map.getInputForward();
        }
        return forward;
    }
}