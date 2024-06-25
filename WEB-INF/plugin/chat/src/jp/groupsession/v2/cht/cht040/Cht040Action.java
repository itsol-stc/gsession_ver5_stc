package jp.groupsession.v2.cht.cht040;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cht.AbstractChatAdminAction;

/**
 *
 * <br>[機  能] チャット 基本設定 のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht040Action extends AbstractChatAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht040Action.class);

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
        Cht040Form thisForm = (Cht040Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (cmd.equals("ok")) {
            log__.debug("確認画面へ遷移");
            forward = __doOk(map, thisForm, req, res, con);
        } else if (cmd.equals("backToAdmin")) {
            log__.debug("管理者設定へ戻る");
            forward = map.findForward("cht020");
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht040Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cht040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Cht040ParamModel paramMdl = new Cht040ParamModel();
        paramMdl.setParam(form);
        Cht040Biz biz = new Cht040Biz(con);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht040Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doOk(ActionMapping map,
                                Cht040Form form,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Connection con)
                                throws Exception {
        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCht040(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        // トランザクショントークン設定
        saveToken(req);
        return map.findForward("cht040kn");
    }

}
