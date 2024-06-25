package jp.groupsession.v2.cht.cht140;

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
 * <br>[機  能] チャット カテゴリ管理 のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht140Action extends AbstractChatAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht140Action.class);

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
        Cht140Form thisForm = (Cht140Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (cmd.equals("addEdit")) {
            log__.debug("カテゴリ追加編集");
            forward = __doClickLink(map, thisForm, req, res, con);
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
     * @param form Cht140Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cht140Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Cht140ParamModel paramMdl = new Cht140ParamModel();
        paramMdl.setParam(form);
        Cht140Biz biz = new Cht140Biz(con);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }


    /**
     * <br>[機  能] リンククリック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht140Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doClickLink(ActionMapping map,
                                Cht140Form form,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Connection con)
                                throws Exception {
        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCht140(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        return map.findForward("cht150");
    }
}
