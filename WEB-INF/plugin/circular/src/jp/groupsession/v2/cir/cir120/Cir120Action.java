package jp.groupsession.v2.cir.cir120;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cir.AbstractCircularAdminAction;

/**
 * <br>[機  能] 回覧板 管理者設定 手動削除画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir120Action extends AbstractCircularAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir120Action.class);

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
     * @see jp.groupsession.v2.cir.AbstractCircularAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        ActionForward forward = null;
        Cir120Form thisform = (Cir120Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //OKボタン押下
        if (cmd.equals("syudo_del_kakunin")) {
            log__.debug("OKボタン押下");
            forward = __doOk(map, thisform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("backKtool")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("backKtool");
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, thisform, req, res, con);
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
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Cir120Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws SQLException {

        Cir120ParamModel paramMdl = new Cir120ParamModel();
        paramMdl.setParam(form);
        Cir120Biz biz = new Cir120Biz();
        biz.setInitData(getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(form);

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
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doOk(ActionMapping map,
                                  Cir120Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con) throws SQLException {

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        saveToken(req);
        return map.findForward("syudo_del_kakunin");
    }
}