package jp.groupsession.v2.sch.sch240kn;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.AbstractScheduleAdminAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.sch230.Sch230Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール 特例アクセス登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch240knAction extends AbstractScheduleAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch240knAction.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
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
        log__.debug("START");

        ActionForward forward = null;
        Sch240knForm thisForm = (Sch240knForm) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("decision")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("backInput")) {
            //戻るボタンクリック
            forward = map.findForward("backInput");

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Sch240knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Sch240knParamModel paramMdl = new Sch240knParamModel();
        paramMdl.setParam(form);
        Sch240knBiz biz = new Sch240knBiz();
        biz.setInitData(con, paramMdl, getRequestModel(req), getTempPath(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doDecision(ActionMapping map, Sch240knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        boolean commit = false;

        Sch240knParamModel paramMdl = new Sch240knParamModel();
        paramMdl.setParam(form);
        try {

            RequestModel reqMdl = getRequestModel(req);
            Sch240knBiz biz = new Sch240knBiz();
            biz.entryAccessData(con, paramMdl, getCountMtController(req),
                                            reqMdl);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("特例アクセス情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        String opCode = "";
        if (form.getSch230editMode() == GSConstWebmail.CMDMODE_ADD) {
            opCode = getInterMessage(req, "cmn.entry");
        } else {
            opCode = getInterMessage(req, "cmn.change");
        }

        //ログ出力
        SchCommonBiz schBiz = new SchCommonBiz(con, getRequestModel(req));
        GsMessage gsMsg = new GsMessage();
        String value = "";
        value += "[" + gsMsg.getMessage("cmn.title") + "] ";
        value += paramMdl.getSch240name();
        String dspName = gsMsg.getMessage("schedule.sch240kn.03");
        schBiz.outPutLogNoDspName(map, req, res, opCode,
                GSConstLog.LEVEL_INFO, value, null, dspName);

        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Sch240knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("backList");

        ((Sch230Form) form).setHiddenParam(cmn999Form);
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getSch230editMode() == GSConstWebmail.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getSch230editMode() == GSConstWebmail.CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(
                msgRes.getMessage(msgState, getInterMessage(req, "schedule.sch230.02")));
        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }
}
