package jp.groupsession.v2.sch.sch240;

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
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.sch.AbstractScheduleAdminAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchSpaccessModel;
import jp.groupsession.v2.sch.sch230.Sch230Biz;
import jp.groupsession.v2.sch.sch230.Sch230Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール 特例アクセス登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch240Action extends AbstractScheduleAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch240Action.class);

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
        Sch240Form thisForm = (Sch240Form) form;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("confirm")) {
            //OKボタンクリック
            forward = __doOK(map, thisForm, req, res, con);

        } else if (cmd.equals("beforePage")) {
            //戻るボタンクリック
            forward = map.findForward("backList");

        } else if (cmd.equals("deleteAccess")) {
            //削除ボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteAccessComp")) {
            //削除確認画面からの遷移
            forward = __doDeleteComp(map, thisForm, req, res, con);

        } else if (cmd.equals("addEditUser")) {
            //追加(許可ユーザ 追加・変更・削除)ボタンクリック
            forward = __doAddEditUser(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteEditUser")) {
            //削除(許可ユーザ 追加・変更・削除)ボタンクリック
            forward = __doDelEditUser(map, thisForm, req, res, con);

        } else if (cmd.equals("addAccessUser")) {
            //追加(許可ユーザ 閲覧)ボタンクリック
            forward = __doAddAccessUser(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteAccessUser")) {
            //削除(許可ユーザ 閲覧)ボタンクリック
            forward = __doDelAccessUser(map, thisForm, req, res, con);

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
    private ActionForward __doInit(ActionMapping map, Sch240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Sch240ParamModel paramMdl = new Sch240ParamModel();
        paramMdl.setParam(form);
        Sch240Biz biz = new Sch240Biz();
        biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

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
    private ActionForward __doOK(ActionMapping map, Sch240Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        // トランザクショントークン設定
        saveToken(req);

        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        return map.findForward("confirm");
    }

    /**
     * <br>[機  能] 追加(許可ユーザ 追加・変更・削除)ボタン押下時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doAddEditUser(
        ActionMapping map,
        Sch240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        Sch240Biz biz = new Sch240Biz();
        form.setSch240editUser(
                biz.getAddMember(form.getSch240editUser(),
                                    form.getSch240accessUserNoSelect()));
        return  __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 削除(許可ユーザ追加・変更・削除)ボタン押下時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDelEditUser(
        ActionMapping map,
        Sch240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        CommonBiz cmnBiz = new CommonBiz();
        form.setSch240editUser(
                cmnBiz.getDeleteMember(form.getSch240editUserSelect(),
                                    form.getSch240editUser()));

        return  __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 追加(許可ユーザ 閲覧)ボタン押下時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doAddAccessUser(
        ActionMapping map,
        Sch240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        Sch240Biz biz = new Sch240Biz();
        form.setSch240accessUser(
                biz.getAddMember(form.getSch240accessUser(),
                                    form.getSch240accessUserNoSelect()));
        return  __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 削除(許可ユーザ 閲覧)ボタン押下時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDelAccessUser(
        ActionMapping map,
        Sch240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        CommonBiz cmnBiz = new CommonBiz();
        form.setSch240accessUser(
                cmnBiz.getDeleteMember(form.getSch240accessUserSelect(),
                                    form.getSch240accessUser()));

        return  __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Sch240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        // トランザクショントークン設定
        saveToken(req);

        //削除対象の特例アクセスが存在するかを確認
        Sch230Biz biz230 = new Sch230Biz();
        if (!biz230.existSchSpAccess(con, form.getSch230editData())) {
            return __doNoneDataError(map, req, form);
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
        Sch240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //削除対象の特例アクセスが存在するかを確認
        Sch230Biz biz230 = new Sch230Biz();
        if (!biz230.existSchSpAccess(con, form.getSch230editData())) {
            return __doNoneDataError(map, req, form);
        }

        //送付先リストを削除する
        boolean commit = false;
        // 削除対象
        SchSpaccessModel delSpMdl = new SchSpaccessModel();
        try {
            Sch240ParamModel paramMdl = new Sch240ParamModel();
            paramMdl.setParam(form);
            Sch240Biz biz = new Sch240Biz();
            delSpMdl = biz.deleteAccess(con, paramMdl);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("特例アクセスの削除に失敗", e);
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
        String value = "";
        value += "[" + gsMsg.getMessage("schedule.sch240.04") + "] " + delSpMdl.getSsaName();
        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("schedule.sch240.09");
        schBiz.outPutLogNoDspName(map, req, res, opCode,
                GSConstLog.LEVEL_INFO, value, null, dspName);

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
        Sch240Form form,
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
        cmn999Form.setUrlOK(forward.getPath() + "?" + GSConst.P_CMD + "=deleteAccessComp");

        //メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String msg = gsMsg.getMessage("cmn.confirm.msg.delete",
                                            new String[] {gsMsg.getMessage("schedule.sch230.02")});
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
        Sch240Form form,
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
                                gsMsg.getMessage(req, "schedule.sch230.02")));

        ((Sch240Form) form).setHiddenParam(cmn999Form);
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
    private ActionForward __doNoneDataError(
        ActionMapping map,
        HttpServletRequest req,
        Sch230Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backList");

        //メッセージセット
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String enqName = gsMsg.getMessage("schedule.sch230.02");
        String textOperation = gsMsg.getMessage("cmn.delete");
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                                        enqName, textOperation));

        cmn999Form.setUrlOK(urlForward.getPath());
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}
