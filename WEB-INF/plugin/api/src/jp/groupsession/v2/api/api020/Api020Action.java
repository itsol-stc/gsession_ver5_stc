package jp.groupsession.v2.api.api020;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;
/**
 *
 * <br>[機  能] API 基本設定 アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Api020Action extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Api020Action.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");
        ActionForward forward = null;
        Api020Form thisForm = (Api020Form) form;

        //前提設定
        __doPrepareSetting(thisForm, req, con);

        con.setAutoCommit(true);

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("api010Basic")) {
            //初期化
            forward = __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("dsp")) {
            //再描画
            forward = __doDsp(map, thisForm, req, res, con);
        } else if (cmd.equals("api020Ok")) {
            //OK
            forward = __doOk(map, thisForm, req, res, con);
        } else if (cmd.equals("api020Back")) {
            //戻る
            forward = map.findForward("back");
        } else {
            //メニュー表示
            forward = __doDsp(map, thisForm, req, res, con);
        }
        log__.debug("END");
        return forward;
    }
    /**
     *
     * <br>[機  能] 前提設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __doPrepareSetting(Api020Form form, HttpServletRequest req,
            Connection con) throws Exception {
        Api020ParamModel param = new Api020ParamModel();
        param.setParam(form);
        Api020Biz biz = new Api020Biz(getRequestModel(req), con);
        biz.doPrepareSetting(param);
        param.setFormData(form);
    }
    /**
     *
     * <br>[機  能] 確認画面遷移処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param map map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doOk(ActionMapping map, Api020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateCheck(reqMdl);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }
        saveToken(req);
        //確認画面遷移
        return map.findForward("ok");
    }
    /**
     *
     * <br>[機  能] 描画設定処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param map map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doDsp(ActionMapping map, Api020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        Api020ParamModel param = new Api020ParamModel();
        param.setParam(form);
        Api020Biz biz = new Api020Biz(getRequestModel(req), con);
        biz.doDsp(param);

        param.setFormData(form);
        return map.getInputForward();
    }
    /**
     *
     * <br>[機  能] 初期化処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param map map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return 遷移先
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Api020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        Api020ParamModel param = new Api020ParamModel();
        param.setParam(form);
        Api020Biz biz = new Api020Biz(getRequestModel(req), con);
        biz.doInit(param);
        param.setFormData(form);
        return __doDsp(map, form, req, res, con);
    }
    @Override
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }
    @Override
    public String getPluginId() {
        return GSConst.PLUGINID_API;
    }
}
