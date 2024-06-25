package jp.groupsession.v2.sch.sch230;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.sch.AbstractScheduleAdminAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchSpaccessModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール 特例アクセス管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch230Action extends AbstractScheduleAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch230Action.class);

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
        Sch230Form thisForm = (Sch230Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("addSpAccess")) {
            //追加ボタンクリック
            forward = map.findForward("entry");

        } else if (cmd.equals("spAccessDetail")) {
            //名称リンククリック
            forward = map.findForward("entry");

        } else if (cmd.equals("spAccessDelete")) {
            //削除ボタン
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("doDeleteSpAccess")) {
            //削除実行
            forward = __doDeleteComp(map, thisForm, req, res, con);

        } else if (cmd.equals("search")) {
            //検索ボタンクリック
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページボタンクリック
            forward = __doNext(map, thisForm, req, res, con);

        } else if (cmd.equals("prevPage")) {
            //前ページボタンクリック
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (cmd.equals("sch230back")) {
            //戻るボタンクリック
            forward = map.findForward("ktool");

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
    public ActionForward __doInit(ActionMapping map, Sch230Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Sch230ParamModel paramMdl = new Sch230ParamModel();
        paramMdl.setParam(form);
        Sch230Biz biz = new Sch230Biz();
        biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
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
    public ActionForward __doSearch(ActionMapping map, Sch230Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setSch230searchFlg(1);
        } else {
            form.setSch230svKeyword(form.getSch230keyword());
            form.setSch230searchFlg(0);
            form.setSch230pageTop(1);
            form.setSch230pageBottom(1);
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 前ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doPrev(
        ActionMapping map,
        Sch230Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getSch230pageTop();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setSch230pageTop(page);
        form.setSch230pageBottom(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 次ページクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doNext(
        ActionMapping map,
        Sch230Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getSch230pageTop();
        page += 1;
        form.setSch230pageTop(page);
        form.setSch230pageBottom(page);

        return __doInit(map, form, req, res, con);
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
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Sch230Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionErrors errors = form.validateDelete(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //削除対象の特例アクセスが存在するかを確認
        Sch230Biz biz = new Sch230Biz();
        for (String ssaSid : form.getSch230selectSpAccessList()) {
            if (!biz.existSchSpAccess(con, Integer.parseInt(ssaSid))) {
                return __doNoneDataError(map, req, form);
            }
        }

        // トランザクショントークン設定
        saveToken(req);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
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
        Sch230Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("mine");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("mine");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=doDeleteSpAccess");

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        String msg = "";

        Sch230Biz biz = new Sch230Biz();
        msg = msgRes.getMessage("sakujo.kakunin.list",
                                gsMsg.getMessage(req, "schedule.sch230.02"),
                                biz.getAccessName(con, form.getSch230selectSpAccessList()));

        cmn999Form.setMessage(msg);

        cmn999Form.addHiddenParam("CMD", "search");
        form.setHiddenParam(cmn999Form);

        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
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
        Sch230Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //削除対象の特例アクセスが存在するかを確認
        Sch230Biz biz = new Sch230Biz();
        for (String ssaSid : form.getSch230selectSpAccessList()) {
            if (!biz.existSchSpAccess(con, Integer.parseInt(ssaSid))) {
                return __doNoneDataError(map, req, form);
            }
        }

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        //特例アクセスを削除する
        Sch230ParamModel paramMdl = new Sch230ParamModel();
        paramMdl.setParam(form);

        boolean commit = false;
        // 削除対象特例アクセス
        List<SchSpaccessModel> delSpList = new ArrayList<SchSpaccessModel>();
        try {
            delSpList = biz.deleteAccess(con, paramMdl, userSid);
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
        paramMdl.setFormData(form);

        //ログ出力
        SchCommonBiz schBiz = new SchCommonBiz(con, getRequestModel(req));
        GsMessage gsMsg = new GsMessage();
        String opCode = gsMsg.getMessage("cmn.delete");
        String value = "";
        value += "[" + gsMsg.getMessage("cmn.delete") + "] ";
        for (int i = 0; i < delSpList.size(); i++) {
            if (i > 0) {
                value += ",";
            }
            value += delSpList.get(i).getSsaName();
        }
        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("schedule.sch230.01");
        schBiz.outPutLogNoDspName(map, req, res, opCode,
                GSConstLog.LEVEL_INFO, value, null, dspName);

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Sch230Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("mine");

        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object",
                                gsMsg.getMessage(req, "schedule.sch230.02")));

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
    private ActionForward __doNoneDataError(
        ActionMapping map,
        HttpServletRequest req,
        Sch230Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("mine");

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
