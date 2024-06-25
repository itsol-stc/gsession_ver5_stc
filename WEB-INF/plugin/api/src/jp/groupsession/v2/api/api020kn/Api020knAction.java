package jp.groupsession.v2.api.api020kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.api020.Api020Biz;
import jp.groupsession.v2.api.api020.Api020Form;
import jp.groupsession.v2.api.api020.Api020ParamModel;
import jp.groupsession.v2.api.biz.ApiCommonBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] API基本設定確認画面Action
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Api020knAction extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Api020knAction.class);

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
        if (cmd.equals("api020knKakutei")) {
            //確定
            forward = __doKakutei(map, thisForm, req, res, con);
        } else if (cmd.equals("api020knBack")) {
            //戻る
            forward = map.findForward("back");
        } else {
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
     * <br>[機  能] 確定処理を行う
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
    private ActionForward __doKakutei(ActionMapping map, Api020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        con.setAutoCommit(false);
        boolean commit = false;
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateCheck(reqMdl);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return map.findForward("back");
        }
        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }
        try {
            Api020ParamModel param = new Api020ParamModel();
            param.setParam(form);
            Api020Biz biz = new Api020Biz(getRequestModel(req), con);
            biz.doSaveConf(param);
            param.setFormData(form);

            GsMessage gsMsg = new GsMessage(req);
            /** メッセージ 変更 **/
            String change = gsMsg.getMessage("cmn.change");

            //ログ出力
            ApiCommonBiz cmnBiz = new ApiCommonBiz();
            cmnBiz.outPutApiLog(map, getRequestModel(req), gsMsg, con,
                    change, GSConstLog.LEVEL_INFO,
                    biz.outputLogMessage(param)
                    );
            con.commit();
            commit = true;

        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //完了画面遷移
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);

        String backForwardStr = "comp";
        ActionForward urlForward = map.findForward(backForwardStr);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        //パスワード
        String textPassWord = gsMsg.getMessage(req, "api.api020.1");
        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
             msgRes.getMessage("hensyu.henkou.kanryo.object", textPassWord));

        cmn999Form.addHiddenAll(form, form.getClass(), "");


        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     *
     * <br>[機  能] 描画前処理を行う
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
    @Override
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }
    @Override
    public String getPluginId() {
        return GSConst.PLUGINID_API;
    }
}
