package jp.groupsession.v2.fil.fil310;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.AbstractFileAdminAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル管理 外貨マスタ画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil310Action extends AbstractFileAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil310Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Fil310Form thisForm = (Fil310Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("fil310add")) {
            //追加ボタンクリック
            forward = map.findForward("fil320");

        } else if (cmd.equals("fil310back")) {
            //戻るボタンクリック
            forward = map.findForward("fil200");

        } else if (cmd.equals("fil310del")) {
            //削除ボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("fil310delOk")) {
            //削除確認画面でOKクリック
            forward = __doDeleteComp(map, thisForm, req, res, con);

        } else if (cmd.equals("fil310Up")) {
            //上へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_UP);

        } else if (cmd.equals("fil310Down")) {
            //下へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_DOWN);

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
    private ActionForward __doInit(ActionMapping map, Fil310Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        con.setAutoCommit(true);
        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
                (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        //ユーザSIDを取得
        int userSid = usModel.getUsrsid();

        Fil310ParamModel paramMdl = new Fil310ParamModel();
        paramMdl.setParam(form);
        Fil310Biz biz = new Fil310Biz();
        biz._setInitData(con, paramMdl, userSid, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
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
     * @throws Exception 
     */
    private ActionForward __doDelete(
            ActionMapping map,
            Fil310Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        //外貨の存在チェック
        Fil310ParamModel paramMdl = new Fil310ParamModel();
        paramMdl.setParam(form);
        Fil310Biz biz = new Fil310Biz();
        if (!biz._existGaika(con, paramMdl)) {
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateDelCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        //削除確認画面を表示
        __setKakuninDsp(map, form, req, con);
        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException 
     */
    private void __setKakuninDsp(
            ActionMapping map,
            Fil310Form form,
            HttpServletRequest req,
            Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        cmn999Form.setUrlCancel(map.findForward("fil310delCancel").getPath());
        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(map.findForward("fil310delOk").getPath());

        //メッセージ
        GsMessage gsMsg = new GsMessage();
        MessageResources msgRes = getResources(req);
        Fil310Biz biz = new Fil310Biz();
        int fmmSid = form.getFilDelGaikaId();
        String msg = msgRes.getMessage("sakujo.kakunin.list",
                gsMsg.getMessage("fil.fil310.2"), biz._getGaikaName(con, fmmSid));
        cmn999Form.setMessage(msg);

        form.setHiddenParamFil310(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
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
     * @return ActionForward
     * @throws Exception 
     */
    private ActionForward __doDeleteComp(
            ActionMapping map,
            Fil310Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        //外貨の存在チェック
        Fil310ParamModel paramMdl = new Fil310ParamModel();
        paramMdl.setParam(form);
        Fil310Biz biz = new Fil310Biz();
        if (!biz._existGaika(con, paramMdl)) {
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateDelCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログ用 外貨名退避
        int gaikaSid = paramMdl.getFilDelGaikaId();
        String delGaikaName = biz._getGaikaName(con, gaikaSid);

        boolean commitFlg = false;

        try {
            //外貨を削除する
            paramMdl.setParam(form);
            biz._deleteGaika(con, paramMdl);
            paramMdl.setFormData(form);
            //コミット実行
            con.commit();
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("削除失敗", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }
        //ログ出力
        __outPutDeleteLog(map, form, req, con, delGaikaName);

        //削除完了画面を表示
        __setKanryoDsp(map, form, req);
        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 削除完了画面のパラメータを設定
     * [解  説]
     * [備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     */
    private void __setKanryoDsp(
            ActionMapping map,
            Fil310Form form,
            HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(map.findForward("fil310").getPath());

        form.setFil310SortRadio(null);

        //削除完了
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage(
                "sakujo.kanryo.object", getInterMessage(req, "fil.fil310.2")));

        form.setHiddenParamFil310(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>[機  能] 外貨削除のログ出力処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form Mem050Form
     * @param req リクエスト
     * @param con コネクション
     * @param delGaikaName 削除対象の外貨名
     * @throws SQLException SQL実行例外
     */
    private void __outPutDeleteLog(
            ActionMapping map,
            Fil310Form form,
            HttpServletRequest req,
            Connection con,
            String delGaikaName) throws SQLException {

        //ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        FilCommonBiz fcBiz = new FilCommonBiz(reqMdl, con);
        StringBuilder values = new StringBuilder();
        GsMessage gsMsg = new GsMessage();

        //内容セット
        String opCode = gsMsg.getMessage(req, "cmn.delete");
        values.append("[" + gsMsg.getMessage("fil.fil310.1") + "] " + delGaikaName);

        fcBiz.outPutLog(opCode, GSConstLog.LEVEL_INFO, values.toString(), map.getType());
    }

    /**
     * <br>[機  能] 上へ/下へボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doSortChange(
            ActionMapping map,
            Fil310Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int changeKbn) throws Exception {

        //入力チェック
        ActionErrors errors = form.validateSortCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        Fil310Biz biz = new Fil310Biz();
        boolean commitFlg = false;
        boolean updateFlg = false;

        try {
            Fil310ParamModel paramMdl = new Fil310ParamModel();
            paramMdl.setParam(form);
            int userSid = getRequestModel(req).getSmodel().getUsrsid();
            updateFlg = biz._updateSort(con, paramMdl, userSid, changeKbn);
            paramMdl.setFormData(form);
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("順序入れ替え失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
        if (updateFlg) {
            //ログ出力
            __outPutUpdateLog(map, form, req, con, changeKbn);
        }
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 外貨順序更新のログ出力処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form Mem050Form
     * @param req リクエスト
     * @param con コネクション
     * @param changeKbn 更新区分
     * @throws SQLException SQL実行例外
     */
    private void __outPutUpdateLog(
            ActionMapping map,
            Fil310Form form,
            HttpServletRequest req,
            Connection con,
            int changeKbn) throws SQLException {

        //ログ出力
        RequestModel reqMdl = getRequestModel(req);
        Fil310Biz biz = new Fil310Biz();
        FilCommonBiz fcBiz = new FilCommonBiz(reqMdl, con);
        StringBuilder values = new StringBuilder();
        GsMessage gsMsg = new GsMessage(req);

        //内容セット
        String opCode = gsMsg.getMessage(req, "cmn.change");
        String sort = "";
        String[] selectKeyList = form.getFil310SortRadio().split(":");
        String targetName = biz._getGaikaName(con, Integer.parseInt(selectKeyList[0]));

        if (changeKbn == 0) {
            sort = gsMsg.getMessage("cmn.up");
        } else {
            sort = gsMsg.getMessage("cmn.down");
        }
        values.append("[" + gsMsg.getMessage("cmn.target") + "]" + targetName
                + "\r\n[" + gsMsg.getMessage("change.sort.order") + "]" + sort);

        fcBiz.outPutLog(opCode, GSConstLog.LEVEL_INFO, values.toString(), map.getType());
    }
}