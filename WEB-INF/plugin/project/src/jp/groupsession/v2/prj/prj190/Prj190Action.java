package jp.groupsession.v2.prj.prj190;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.PrjCommonBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] プロジェクト管理 個人設定 ダッシュボード初期値設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj190Action extends AbstractProjectAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj190Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
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
        Prj190Form thisForm = (Prj190Form) form;

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        //設定
        if (cmd.equals("edit")) {
            log__.debug("OKボタン押下");
            forward = __doEdit(map, thisForm, req, res, con);
        //戻る
        } else if (cmd.equals("backKmenu")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("backKmenu");
        //リスト選択値変更
        } else if (cmd.equals("changeList")) {
            log__.debug("リスト選択値変更");
            forward = __doRedraw(map, thisForm, req, res, con);
        //確認画面から戻ってきた
        } else if (cmd.equals("backPrj190")) {
            log__.debug("確認画面から戻ってきた");
            forward = __doRedraw(map, thisForm, req, res, con);
        //初期表示
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
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doEdit(ActionMapping map,
                                    Prj190Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        //入力チェック
        ActionErrors errors = form.validatePrj190(con, getSessionUserModel(req), req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doRedraw(map, form, req, res, con);
        }

        saveToken(req);

        return map.findForward("prj190kn");
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(ActionMapping map,
                                    Prj190Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Prj190Biz biz = new Prj190Biz(con, getRequestModel(req));

        Prj190ParamModel paramMdl = new Prj190ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, getSessionUserModel(req));
        paramMdl.setFormData(form);


        return __doRedraw(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 毎回設定する値の設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doRedraw(ActionMapping map,
                                      Prj190Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws Exception {

        con.setAutoCommit(true);
        Prj190Biz biz = new Prj190Biz(con, getRequestModel(req));

        Prj190ParamModel paramMdl = new Prj190ParamModel();
        paramMdl.setParam(form);
        biz.setList(paramMdl, getSessionUserModel(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }
}