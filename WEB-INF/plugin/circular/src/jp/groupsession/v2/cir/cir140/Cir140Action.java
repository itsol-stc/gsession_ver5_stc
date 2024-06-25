package jp.groupsession.v2.cir.cir140;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cir.AbstractCircularAdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 回覧板 管理者設定 初期値設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir140Action extends AbstractCircularAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir140Action.class);

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
        Cir140Form thisForm = (Cir140Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("Cir140Action CMD==>" + cmd);

        //OKボタン押下
        if (cmd.equals("init_change_kakunin")) {
            log__.debug("OKボタン押下");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("backKtool")) {
            log__.debug("戻るボタン押下");
            return map.findForward("backKtool");

        } else if (cmd.equals("memoKbnChange")) {
            log__.debug("メモ欄修正期限ラジオクリック");
            forward = __doReDsp(map, thisForm, req, res, con);

        } else if (cmd.equals("cir140reload")) {
            log__.debug("確認画面からバック時の再表示");
            forward = __doReDsp(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(ActionMapping map,
                                    Cir140Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Cir140ParamModel paramMdl = new Cir140ParamModel();
        paramMdl.setParam(form);
        Cir140Biz biz = new Cir140Biz();
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 再表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doReDsp(
        ActionMapping map,
        Cir140Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        return map.getInputForward();
    }



    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward 画面遷移先
     */
    private ActionForward __doOk(ActionMapping map,
                                  Cir140Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con) throws Exception {


        // トランザクショントークン設定
        saveToken(req);

        return map.findForward("init_change_kakunin");
    }
}
