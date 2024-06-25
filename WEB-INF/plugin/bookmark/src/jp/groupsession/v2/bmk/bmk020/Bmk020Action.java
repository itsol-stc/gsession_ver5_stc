package jp.groupsession.v2.bmk.bmk020;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.bmk.AbstractBookmarkAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ブックマーク登録_URL入力画面アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk020Action extends AbstractBookmarkAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk020Action.class);

    /** 遷移元画面 */
    private String returnPage__;

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
     * @see jp.co.sjts.util.struts.AbstractAction
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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Bmk020Form thisForm = (Bmk020Form) form;

        //遷移元画面セット
        if (thisForm.getBmk070ReturnPage().equals("bmk070")
                || thisForm.getBmk070ReturnPage().equals("bmk150")) {
            returnPage__ = thisForm.getBmk070ReturnPage();
        } else {
            returnPage__ = thisForm.getReturnPage();
        }

        if (cmd.equals("bmk020pushOk")) {
            log__.debug("OKボタン押下");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("bmk020pushBack")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward(returnPage__);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Bmk020Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Bmk020Biz biz = new Bmk020Biz();

        Bmk020ParamModel paramMdl = new Bmk020ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, getSessionUserModel(req));
        paramMdl.setFormData(form);

        form.setBmk030InitFlg(true);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doOk(
        ActionMapping map,
        Bmk020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //入力チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.validateBmk020(con);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        // トランザクショントークン設定
        saveToken(req);

        return map.findForward("bmk030");
    }
}