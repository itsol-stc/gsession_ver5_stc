package jp.groupsession.v2.man.man430;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.struts.AdminAction;

/**
 *
 * <br>[機  能]ユーザ連携自動インポート機能設定画面
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man430Action extends AdminAction {

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
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
        Man430Form thisForm = (Man430Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("430_back")) {
            //メイン管理者設定に戻る
            forward = map.findForward("430_back");
        } else if (cmd.equals("OK")) {
           //OKボタン押下
            forward = __doOK(thisForm, map, req, con);
        } else {
            //初期表示
            forward = __doDsp(thisForm, map, req, con);
        }

        return forward;
    }

    /**
     * <br>[機  能]表示処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOTools実行例外
     */
    private ActionForward __doDsp(Man430Form form,
            ActionMapping map,
            HttpServletRequest req,
            Connection con) throws SQLException, IOToolsException {

        ActionForward forward = null;
        Man430ParamModel paramMdl = new Man430ParamModel();
        Man430Biz biz = new Man430Biz();

        //初期表示用のデータを取得
        paramMdl.setParam(form);
        biz.setInitData(getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(form);

        forward = map.getInputForward();
        return forward;
    }

    /**
     *
     * <br>[機  能]OKボタン押下時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOTool実行例外
     */
    private ActionForward __doOK(Man430Form form,
            ActionMapping map,
            HttpServletRequest req,
            Connection con) throws SQLException, IOToolsException {

        ActionErrors errors = form.validateCheck(map, req, con);
        //入力チェック
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(form, map, req, con);
        }

        return map.findForward("OK");
    }

}
