package jp.groupsession.v2.enq.enq920;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.AbstractEnqueteAdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 管理者設定 アンケート種類設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq920Action extends AbstractEnqueteAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq920Action.class);

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

        log__.debug("Enq920Action_START");

        ActionForward forward = null;
        Enq920Form enq920Form = (Enq920Form) form;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        log__.debug("CMD = " + cmd);

        // コマンドの判定
        if (cmd.equals("enq920ok")) {
            // 登録
            forward = __doOk(map, enq920Form, req, res, con);

        } else if (cmd.equals("enq920back")) {
            // 戻る
            forward = map.findForward(cmd);

        } else if (cmd.equals("enq920knback")) {
            // 確認画面からの遷移
            forward = __doDsp(map, enq920Form, req, res, con);

        } else {
            //初期表示処理
            forward = __doInit(map, enq920Form, req, res, con);
        }

        log__.debug("Enq920Action_END");

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
     *  @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                   Enq920Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws Exception {

        log__.debug("初期表示処理");

        // アンケート種類を設定
        con.setAutoCommit(true);
        try {
            Enq920Biz biz = new Enq920Biz();
            Enq920ParamModel paramModel = new Enq920ParamModel();
            paramModel.setParam(form);
            biz.setInitData(paramModel, getRequestModel(req), con);
            paramModel.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 描画処理
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
    private ActionForward __doDsp(ActionMapping map,
                                   Enq920Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws Exception {

        log__.debug("描画処理");

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 登録処理（確認画面へ）
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
    private ActionForward __doOk(ActionMapping map,
                                 Enq920Form form,
                                 HttpServletRequest req,
                                 HttpServletResponse res,
                                 Connection con)
    throws Exception {

        log__.debug("登録処理（確認画面へ遷移）");

        RequestModel reqMdl = getRequestModel(req);

        // 種類リストの並び替え
        Enq920Biz biz = new Enq920Biz();
        Enq920ParamModel paramModel = new Enq920ParamModel();
        paramModel.setParam(form);
        biz.setSortTypeList(paramModel);
        paramModel.setFormData(form);

        // 入力チェック
        ActionErrors errors = form.validateEnq920(reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        return map.findForward("enq920ok");
    }

}
