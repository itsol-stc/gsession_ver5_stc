package jp.groupsession.v2.enq.enq320;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.enq310.Enq310Action;

/**
 * <br>[機  能] アンケート 結果確認（一覧）画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq320Action extends Enq310Action {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq320Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        ActionForward forward = null;
        Enq320Form enq320Form = (Enq320Form) form;

        // 結果公開の閲覧権限チェック
        con.setAutoCommit(true);
        try {
            EnqCommonBiz enqBiz = new EnqCommonBiz();
            if (!enqBiz.canViewResultEnquete(
                    getRequestModel(req), con, enq320Form.getAnsEnqSid())) {
                return getSubmitErrorPage(map, req);
            }
        } finally {
            con.setAutoCommit(false);
        }

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        log__.debug("CMD = " + cmd);

        if (cmd.equals("enq320back")) {
            //戻る
            forward = map.findForward("enq320back");

        } else if (cmd.equals("enq320search")) {
            //検索
            forward = __doSearch(map, enq320Form, req, res, con);

        } else if (cmd.equals("prevPage")) {
            //前ページクリック
            enq320Form.setEnq320pageTop(enq320Form.getEnq320pageTop() - 1);
            forward = __doInit(map, enq320Form, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            enq320Form.setEnq320pageTop(enq320Form.getEnq320pageTop() + 1);
            forward = __doInit(map, enq320Form, req, res, con);

        } else if (cmd.equals("enq320detail")) {
            //回答確認
            forward = map.findForward("enqAnswerKn");

        } else {
            //初期表示処理
            __setInitViewMode(enq320Form);
            forward = __doInit(map, enq320Form, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                   Enq320Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException {

        con.setAutoCommit(true);
        try {
            Enq320ParamModel paramMdl = new Enq320ParamModel();
            paramMdl.setParam(form);
            Enq320Biz biz = new Enq320Biz();
            biz.setInitData(paramMdl, getRequestModel(req), con);
            paramMdl.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doSearch(ActionMapping map,
                                   Enq320Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException {

        //検索条件の保持を行う
        form.setEnq320pageTop(1);
        form.setEnq320pageBottom(1);
        form.setEnq320svGroup(form.getEnq320group());
        form.setEnq320svUser(form.getEnq320user());
        form.setEnq320svStsAns(form.getEnq320stsAns());
        form.setEnq320svStsNon(form.getEnq320stsNon());

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 初期遷移時の検索チェックボックス設定
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @throws Exception 実行時例外
     */
    private void __setInitViewMode(Enq320Form form) throws Exception {

        // 初期遷移時の検索チェックボックス設定
        if (form == null) { return; }

        if (form.getEnq320viewMode() == Enq320Const.INIT_DSP_ANS) {
            form.setEnq320stsAns(1);
            form.setEnq320svStsAns(1);
        } else if (form.getEnq320viewMode() == Enq320Const.INIT_DSP_NON) {
            form.setEnq320stsNon(1);
            form.setEnq320svStsNon(1);
        }
    }
}
