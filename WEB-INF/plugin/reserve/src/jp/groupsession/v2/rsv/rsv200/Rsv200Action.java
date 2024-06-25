package jp.groupsession.v2.rsv.rsv200;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.rsv.AbstractReserveSubAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 施設予約 施設一括設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv200Action extends AbstractReserveSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv200Action.class);

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
                                    Connection con) throws Exception {

        ActionForward forward = null;
        Rsv200Form rsvform = (Rsv200Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //OKボタン押下
        if (cmd.equals("ikkatu_settei_kakunin")) {
            log__.debug("OKボタン押下");
            forward = __doConfirmation(map, rsvform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("back_to_sisetu_settei")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("back_to_sisetu_settei");
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, rsvform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(ActionMapping map,
                                    Rsv200Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        con.setAutoCommit(true);
        Rsv200Biz biz = new Rsv200Biz(getRequestModel(req), con);

        boolean processFlg = false;

        Rsv200ParamModel paramMdl = new Rsv200ParamModel();
        paramMdl.setParam(form);
        processFlg = biz.isPossibleToProcess(paramMdl);
        paramMdl.setFormData(form);

        //処理権限判定
        if (!processFlg) {
            //処理権限無し
            return getSubmitErrorPage(map, req);
        }

        paramMdl = new Rsv200ParamModel();
        paramMdl.setParam(form);
        biz.setSisetuData(paramMdl);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタン押下処理
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
    private ActionForward __doConfirmation(ActionMapping map,
                                            Rsv200Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        saveToken(req);

        return map.findForward("ikkatu_settei_kakunin");
    }
}