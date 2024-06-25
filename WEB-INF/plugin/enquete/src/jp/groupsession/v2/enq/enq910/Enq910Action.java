package jp.groupsession.v2.enq.enq910;

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
import jp.groupsession.v2.enq.AbstractEnqueteAdminAction;
import jp.groupsession.v2.enq.enq900.Enq900Action;

/**
 * <br>[機  能] 管理者設定 アンケート発信対象者設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq910Action extends AbstractEnqueteAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq900Action.class);

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

        log__.debug("Enq910Action_START");

        ActionForward forward = null;
        Enq910Form enq910Form = (Enq910Form) form;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        log__.debug("CMD = " + cmd);

        // コマンドの判定
        if (cmd.equals("enq910ok")) {
            // OK
            forward = __doOk(map, enq910Form, req, res, con);

        } else if (cmd.equals("enq910back")) {
            // 戻る
            forward = map.findForward(cmd);

        } else if ((cmd.equals("enq910knback")) || (cmd.equals("enq910ChangeGroup"))) {
            // 確認画面からの遷移 or グループ選択コンボ変更 or 全グループから選択
            forward = __doDsp(map, enq910Form, req, res, con);

        } else {
            //初期表示処理
            forward = __doInit(map, enq910Form, req, res, con);
        }

        log__.debug("Enq910Action_END");

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
                                   Enq910Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException {

        log__.debug("初期表示処理");

        // 初期表示情報を取得
        con.setAutoCommit(true);
        try {
            Enq910Biz biz = new Enq910Biz();
            Enq910ParamModel paramModel = new Enq910ParamModel();
            paramModel.setParam(form);
            biz.setConfData(paramModel, getRequestModel(req), con);
            paramModel.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 再描画処理
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
    private ActionForward __doDsp(ActionMapping map,
                                  Enq910Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
    throws SQLException {

        log__.debug("再描画処理");

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
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doOk(ActionMapping map,
                                 Enq910Form form,
                                 HttpServletRequest req,
                                 HttpServletResponse res,
                                 Connection con)
    throws SQLException {

        log__.debug("登録処理（確認画面へ遷移）");

        // 入力チェック
        ActionErrors errors = form.validateEnq910(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        return map.findForward("enq910ok");
    }
}
