package jp.groupsession.v2.enq.enq960kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.AbstractEnqueteAdminAction;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.enq010.Enq010Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] 管理者設定 アンケート自動削除確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq960knAction extends AbstractEnqueteAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq960knAction.class);

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

        log__.debug("Enq960knAction");

        ActionForward forward = null;
        Enq960knForm enq960knForm = (Enq960knForm) form;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        log__.debug("CMD = " + cmd);

        // コマンドの判定
        if (cmd.equals("enq960kncommit")) {
            // 自動削除
            forward = __doUpdate(map, enq960knForm, req, res, con);

        } else if (cmd.equals("enq960knback")) {
            // 戻る
            forward = map.findForward(cmd);

        } else {
            //初期表示処理
            forward = __doInit(map, enq960knForm, req, res, con);

        }
        log__.debug("Enq960knAction");

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
                                   Enq960knForm form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws Exception {

        log__.debug("初期表示処理");

        con.setAutoCommit(true);
        try {
            Enq960knBiz biz = new Enq960knBiz(con);
            Enq960knParamModel paramMdl = new Enq960knParamModel();
            paramMdl.setParam(form);
            //年選択、月選択の入力チェックを行う
            biz.changeEgnoreYearMonth(paramMdl);
            paramMdl.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map ActionMapping
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return forward アクションフォワード
    * @throws Exception 実行時例外
     */
    public ActionForward __doUpdate(ActionMapping map,
                                     Enq960knForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con) throws Exception {

        ActionForward forward = null;

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            forward = getSubmitErrorPage(map, req);
            return forward;
        }

        con.setAutoCommit(false);
        boolean commit = false;
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        try {

            //アンケート自動削除設定の更新
            Enq960knParamModel paramMdl = new Enq960knParamModel();
            paramMdl.setParam(form);
            Enq960knBiz biz = new Enq960knBiz(con);
            biz.updateAuteDelSetting(reqMdl, paramMdl);
            paramMdl.setFormData(form);

            commit = true;


        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
        }

        //オペレーションログ登録
        String textDelete = gsMsg.getMessage("cmn.delete");
        String pluginName = gsMsg.getMessage("enq.plugin");
        EnqCommonBiz enqBiz = new EnqCommonBiz(con);
        enqBiz.outPutLog(map, reqMdl, pluginName, textDelete,
                GSConstLog.LEVEL_TRACE, form.getLogText(reqMdl));

            //完了画面
            forward = __doCompDsp(map, form, req, res);

            return forward;
    }

    /**
     * <br>[機  能] 完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map,
                                       Enq960knForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res) {
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String enqAutoDel = gsMsg.getMessage("enq.enq960kn.01");

        //完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("enq960kncommit");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage("settei.kanryo.object", enqAutoDel));

        //hiddenパラメータ
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        ((Enq010Form) form).setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}
