package jp.groupsession.v2.cir.cir220;

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
import jp.groupsession.v2.cir.AbstractCircularSubAction;

/**
 * <br>[機  能] 管理者設定 回覧板登録制限設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir220Action extends AbstractCircularSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir220Action.class);

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }

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

        log__.debug("Cir220Action_START");

        ActionForward forward = null;
        Cir220Form cir220Form = (Cir220Form) form;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        log__.debug("CMD = " + cmd);

        // コマンドの判定
        if (cmd.equals("cir220ok")) {
            // OK
            forward = __doOk(map, cir220Form, req, res, con);

        } else if (cmd.equals("cir220back")) {
            // 戻る
            forward = map.findForward("backKtool");

        } else if ((cmd.equals("cir220knback")) || (cmd.equals("cir220ChangeGroup"))) {
            // 確認画面からの遷移 or グループ選択コンボ変更 or 全グループから選択
            forward = __doDsp(map, cir220Form, req, res, con);

        } else {
            //初期表示処理
            forward = __doInit(map, cir220Form, req, res, con);
        }

        log__.debug("Cir220Action_END");

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
                                   Cir220Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException {

        log__.debug("初期表示処理");

        // 初期表示情報を取得
        con.setAutoCommit(true);
        try {
            Cir220Biz biz = new Cir220Biz();
            Cir220ParamModel paramModel = new Cir220ParamModel();
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
                                  Cir220Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
    throws SQLException {

        log__.debug("再描画処理");

        // 初期表示情報を取得
        con.setAutoCommit(true);
        try {
            Cir220Biz biz = new Cir220Biz();
            Cir220ParamModel paramModel = new Cir220ParamModel();
            paramModel.setParam(form);
            biz.setInitData(paramModel, getRequestModel(req), con);
            paramModel.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

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
                                 Cir220Form form,
                                 HttpServletRequest req,
                                 HttpServletResponse res,
                                 Connection con)
    throws SQLException {

        log__.debug("登録処理（確認画面へ遷移）");

        // 入力チェック
        ActionErrors errors = form.validateCir220(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        return map.findForward("init_change_kakunin");
    }
}
