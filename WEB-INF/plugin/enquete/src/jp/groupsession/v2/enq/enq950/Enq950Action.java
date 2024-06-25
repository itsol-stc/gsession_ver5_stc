package jp.groupsession.v2.enq.enq950;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.enq.AbstractEnqueteAdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 管理者設定 アンケート手動削除画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq950Action extends AbstractEnqueteAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq950Action.class);

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

        log__.debug("Enq950Action_START");

        ActionForward forward = null;
        Enq950Form enq950Form = (Enq950Form) form;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        log__.debug("CMD = " + cmd);

        // コマンドの判定
        if (cmd.equals("enq950ok")) {
            // 手動削除
            forward = __doDelete(map, enq950Form, req, res, con);

        } else if (cmd.equals("enq950back")) {
            // 戻る
            forward = map.findForward(cmd);

        } else {
            //初期表示処理
            forward = __doInit(map, enq950Form, req, res, con);
        }

        log__.debug("Enq950Action_END");

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
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                   Enq950Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws Exception {

        log__.debug("初期表示処理");

        // 初期表示情報を取得
        con.setAutoCommit(true);
        try {
            Enq950Biz biz = new Enq950Biz();
            Enq950ParamModel paramModel = new Enq950ParamModel();
            paramModel.setParam(form);
            biz.setInitData(paramModel, getRequestModel(req), con);
            paramModel.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 更新処理
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
    private ActionForward __doDelete(ActionMapping map,
                                     Enq950Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
    throws Exception {

        log__.debug("更新処理（確認画面へ遷移）");

        // トランザクショントークン
        saveToken(req);

        return map.findForward("enq950ok");
    }
}
