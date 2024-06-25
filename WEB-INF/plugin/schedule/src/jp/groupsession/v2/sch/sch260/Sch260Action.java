package jp.groupsession.v2.sch.sch260;

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
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 表示リスト登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch260Action extends AbstractScheduleAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch260Action.class);

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
        Sch260Form thisForm = (Sch260Form) form;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("confirm")) {
            forward = __doOK(map, thisForm, req, res, con);
        } else if (cmd.equals("sch260back")) {
            forward = map.findForward("backList");
        } else if (cmd.equals("sch260delete")) {
            forward = __doDelete(map, thisForm, req, res, con);
        } else if (cmd.equals("deleteListComp")) {
            forward = __doDeleteComp(map, thisForm, req, res, con);
        } else {
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
    private ActionForward __doInit(ActionMapping map, Sch260Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Sch260ParamModel paramMdl = new Sch260ParamModel();
        paramMdl.setParam(form);
        Sch260Biz biz = new Sch260Biz();
        RequestModel reqMdl = getRequestModel(req);
        int sessionUserSid = reqMdl.getSmodel().getUsrsid();
        //編集対象存在チェック
        if (paramMdl.getSch250editMode() == GSConst.CMDMODE_EDIT) {
            if (!biz._existList(con, sessionUserSid, paramMdl.getSch250editData())) {
                return __doEditNoneDataError(map, form, req);
            }
        }
        biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        //トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
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
    private ActionForward __doOK(ActionMapping map, Sch260Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {


        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //トークンチェック
        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }

        Sch260ParamModel paramMdl = new Sch260ParamModel();
        paramMdl.setParam(form);
        Sch260Biz biz = new Sch260Biz();
        RequestModel reqMdl = getRequestModel(req);
        int sessionUserSid = reqMdl.getSmodel().getUsrsid();

        //編集対象存在チェック
        if (paramMdl.getSch250editMode() == GSConst.CMDMODE_EDIT) {
            if (!biz._existList(con, sessionUserSid, paramMdl.getSch250editData())) {
                return __doEditNoneDataError(map, form, req);
            }
        }

        boolean commit = false;

        String opLogValue = "";
        try {

            opLogValue = biz.entryListData(con, paramMdl, getCountMtController(req),
                                            reqMdl);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("表示リストの登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        String opCode = "";
        if (form.getSch250editMode() == GSConstWebmail.CMDMODE_ADD) {
            opCode = getInterMessage(req, "cmn.entry");
        } else {
            opCode = getInterMessage(req, "cmn.change");
        }

        //ログ出力
        SchCommonBiz schBiz = new SchCommonBiz(con, getRequestModel(req));
        GsMessage gsMsg = new GsMessage();

        String dspName = gsMsg.getMessage("schedule.sch260.06");
        schBiz.outPutLogNoDspName(map, req, res, opCode,
                GSConstLog.LEVEL_INFO, opLogValue, null, dspName);

        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除ボタン押下時処理
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
    private ActionForward __doDelete(ActionMapping map, Sch260Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //トランザクショントークン設定
        saveToken(req);

        //削除対象の特例アクセスが存在するかを確認
        int usrSid = getRequestModel(req).getSmodel().getUsrsid();
        Sch260Biz biz = new Sch260Biz();
        if (!biz._existList(con, usrSid, form.getSch250editData())) {
            return __doDeleteNoneDataError(map, req, form);
        }

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
    }

    /**
     * <br>[機  能] 削除処理を行う(削除実行)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws GSException GS用汎実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteComp(
        ActionMapping map,
        Sch260Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);
        int usrSid = reqMdl.getSmodel().getUsrsid();

        //削除対象の表示リストが存在するかを確認
        Sch260Biz biz = new Sch260Biz();
        if (!biz._existList(con, usrSid, form.getSch250editData())) {
            return __doDeleteNoneDataError(map, req, form);
        }

        //表示リストを削除する
        boolean commit = false;
        //オペレーションログ内容
        String opLogValue = "";
        try {
            Sch260ParamModel paramMdl = new Sch260ParamModel();
            paramMdl.setParam(form);
            opLogValue = biz.deleteList(con, usrSid, paramMdl, reqMdl);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("表示リストの削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //ログ出力
        SchCommonBiz schBiz = new SchCommonBiz(con, getRequestModel(req));
        GsMessage gsMsg = new GsMessage();
        String opCode = gsMsg.getMessage("cmn.delete");
        String dspName = gsMsg.getMessage("schedule.sch260.06");
        schBiz.outPutLogNoDspName(map, req, res, opCode,
                GSConstLog.LEVEL_INFO, opLogValue, null, dspName);

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * <br>[機  能] 削除確認画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Sch260Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forward = map.findForward("mine");
        cmn999Form.setUrlCancel(forward.getPath());

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(forward.getPath() + "?" + GSConst.P_CMD + "=deleteListComp");

        //メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String msg = gsMsg.getMessage("cmn.confirm.msg.delete",
                                            new String[] {gsMsg.getMessage("schedule.sch260.04")});
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除完了画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Sch260Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("backList");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object",
                                gsMsg.getMessage(req, "schedule.sch260.04")));

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 編集対象が存在しない場合の画面表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doEditNoneDataError(
        ActionMapping map,
        Sch260Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("backList");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(
                msgRes.getMessage("error.none.edit.data",
                                gsMsg.getMessage(req, "schedule.sch260.04"),
                                gsMsg.getMessage("cmn.show")));

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除対象が存在しない場合のエラー画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __doDeleteNoneDataError(
        ActionMapping map,
        HttpServletRequest req,
        Sch260Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backList");

        //メッセージセット
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String msg = gsMsg.getMessage("schedule.sch260.04");
        String textOperation = gsMsg.getMessage("cmn.delete");
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                                        msg, textOperation));

        cmn999Form.setUrlOK(urlForward.getPath());
        req.setAttribute("cmn999Form", cmn999Form);

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
        Sch260Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("backList");

        //form.setHiddenParam(cmn999Form);
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getSch250editMode() == GSConstWebmail.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getSch250editMode() == GSConstWebmail.CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(
                msgRes.getMessage(msgState, getInterMessage(req, "schedule.sch260.04")));
        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }
}