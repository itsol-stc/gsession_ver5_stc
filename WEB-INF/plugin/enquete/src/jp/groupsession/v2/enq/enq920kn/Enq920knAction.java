package jp.groupsession.v2.enq.enq920kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.enq010.Enq010Form;
import jp.groupsession.v2.enq.enq920.Enq920Action;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] 管理者設定 アンケート種類設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq920knAction extends Enq920Action {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq920knAction.class);

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
     * @throws Exception 実行時例外O
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        log__.debug("Enq920knAction_START");

        ActionForward forward = null;
        Enq920knForm enq920knForm = (Enq920knForm) form;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        log__.debug("CMD = " + cmd);

        // コマンドの判定
        if (cmd.equals("enq920kncommit")) {
            // 登録
            forward = __doCommit(map, enq920knForm, req, res, con);

        } else if (cmd.equals("enq920knback")) {
            // 戻る
            forward = map.findForward(cmd);

        } else {
            //初期表示処理
            forward = __doInit(map, enq920knForm, req, res, con);
        }

        log__.debug("Enq920knAction_END");

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
                                   Enq920knForm form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws Exception {

        log__.debug("初期表示処理");

        // アンケート種類を設定
        con.setAutoCommit(true);
        try {
            Enq920knBiz biz = new Enq920knBiz();
            Enq920knParamModel paramModel = new Enq920knParamModel();
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
    private ActionForward __doCommit(ActionMapping map,
                                     Enq920knForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
    throws Exception {

        log__.debug("更新処理");

        RequestModel reqMdl = getRequestModel(req);
        MlCountMtController cntCon = getCountMtController(req);

        // 二重投稿チェック
        if (!isTokenValid(req, true)) {
            log__.info("二重投稿");
            return getSubmitErrorPage(map, req);
        }

        // 入力チェック
        ActionErrors errors = form.validateEnq920(reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        // 更新処理
        Enq920knBiz biz = new Enq920knBiz(reqMdl, con, cntCon);
        Enq920knParamModel paramModel = new Enq920knParamModel();
        paramModel.setParam(form);
        boolean flg = biz.doCommit(paramModel);
        paramModel.setFormData(form);
        if (!flg) {
            // 失敗
            return __setFailureDsp(map, form, req);
        }

        // オペレーションログ出力処理
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textEntry = gsMsg.getMessage("cmn.change");
        String pluginName = gsMsg.getMessage("enq.plugin");
        EnqCommonBiz enqBiz = new EnqCommonBiz(con);
        enqBiz.outPutLog(map, reqMdl, pluginName, textEntry,
                GSConstLog.LEVEL_TRACE, form.getTargetLog(reqMdl));

        return __setCommitDsp(map, form, req);
    }

    /**
     * <br>[機  能] 完了画面
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return アクションフォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __setCommitDsp(ActionMapping map,
                                         Enq920knForm form,
                                         HttpServletRequest req) throws Exception {

        log__.debug("完了画面表示処理");

        ActionForward forward = null;
        MessageResources msgRes = getResources(req);
        ActionForward urlForward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        // OKボタンクリック時遷移先
        urlForward = map.findForward("enq920kncommit");
        cmn999Form.setUrlOK(urlForward.getPath());

        // メッセージのセット
        GsMessage gsMsg = new GsMessage();
        String textEnq = gsMsg.getMessage(req, "enq.enq900.03");
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", textEnq));

        // 画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        ((Enq010Form) form).setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] 更新失敗画面
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return アクションフォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __setFailureDsp(ActionMapping map,
                                          Enq920knForm form,
                                          HttpServletRequest req) throws Exception {

        log__.debug("更新失敗画面表示処理");

        ActionForward forward = null;
        MessageResources msgRes = getResources(req);
        ActionForward urlForward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        // OKボタンクリック時遷移先
        urlForward = map.findForward("enq920knfailure");
        cmn999Form.setUrlOK(urlForward.getPath());

        // メッセージのセット
        GsMessage gsMsg = new GsMessage();
        String enqType = gsMsg.getMessage(req, "enq.enq920.05");
        String update = gsMsg.getMessage(req, "cmn.update");
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data", enqType, update));

        // 画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        ((Enq010Form) form).setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}
