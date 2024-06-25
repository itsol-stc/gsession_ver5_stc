package jp.groupsession.v2.mem.mem050;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.mem.AbstractMemoAction;
import jp.groupsession.v2.mem.biz.MemCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メモ帳 ラベルの管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem050Action extends AbstractMemoAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Mem050Action.class);

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
        Mem050Form thisForm = (Mem050Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("mem050Next")) {
            //追加ボタン、修正ボタンクリック
            forward = __doConfig(map, thisForm, req, res, con);

        } else if (cmd.equals("mem050Back")) {
            //戻るボタンクリック
            forward = map.findForward("mem050Back");

        } else if (cmd.equals("mem050Delete")) {
            //削除ボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("mem050commit")) {
            //削除確認画面でOKクリック
            forward = __doDeleteComp(map, thisForm, req, res, con);

        } else if (cmd.equals("mem050Up")) {
            //上へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_UP);

        } else if (cmd.equals("mem050Down")) {
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
    private ActionForward __doInit(ActionMapping map, Mem050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        con.setAutoCommit(true);
        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
                (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        //ユーザSIDを取得
        int userSid = usModel.getUsrsid();

        Mem050ParamModel paramMdl = new Mem050ParamModel();
        paramMdl.setParam(form);
        Mem050Biz biz = new Mem050Biz();
        biz._setInitData(con, paramMdl, userSid, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 追加ボタン、修正ボタンクリック時処理
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
    public ActionForward __doConfig(ActionMapping map, Mem050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        return map.findForward("mem050Next");
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
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doDelete(
            ActionMapping map,
            Mem050Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException {

        // トランザクショントークン設定
        saveToken(req);

        //削除確認画面を表示
        __setKakuninDsp(map, form, req);
        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     */
    private void __setKakuninDsp(
            ActionMapping map,
            Mem050Form form,
            HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage();
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        //キャンセルボタンクリック時遷移先
        cmn999Form.setUrlCancel(map.findForward("deleteLabelCancel").getPath());
        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(map.findForward("deleteLabelOk").getPath());

        //メッセージ
        String msg = gsMsg.getMessage(req, "memo.mem050.2");
        cmn999Form.setMessage(msg);

        cmn999Form.addHiddenParam("mem050SortRadio", form.getMem050SortRadio());
        cmn999Form.addHiddenParam("memEditLabelId", form.getMemEditLabelId());
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
     * @throws SQLException SQL実行例外
     * @throws GSException GS用汎実行例外
     */
    private ActionForward __doDeleteComp(
            ActionMapping map,
            Mem050Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, GSException {

        //ユーザSIDを取得
        int usrSid = getSessionUserSid(req);

        //ラベルの存在チェック
        Mem050ParamModel paramMdl = new Mem050ParamModel();
        paramMdl.setParam(form);
        Mem050Biz biz = new Mem050Biz();
        boolean result = biz._exist050LabelData(con, paramMdl, usrSid);
        if (!result) {
            return getSubmitErrorPage(map, req);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログ用 ラベル名退避
        int lbSid = form.getMemEditLabelId();
        String delLabelName = biz._getLabelName(con, lbSid);

        boolean commitFlg = false;

        try {
            //ラベルを削除する
            paramMdl.setParam(form);
            biz._deleteLabel(con, paramMdl);
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
        __outPutDeleteLog(map, form, req, con, delLabelName);

        //削除完了画面を表示
        __setKanryoDsp(map, form, req);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 個人設定ラベル削除のログ出力処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form Mem050Form
     * @param req リクエスト
     * @param con コネクション
     * @param delLabelName 削除対象のラベル名
     * @throws SQLException SQL実行例外
     */
    private void __outPutDeleteLog(
            ActionMapping map,
            Mem050Form form,
            HttpServletRequest req,
            Connection con,
            String delLabelName) throws SQLException {

        //ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        MemCommonBiz mcBiz = new MemCommonBiz(con, reqMdl);
        StringBuilder values = new StringBuilder();
        GsMessage gsMsg = new GsMessage();

        //内容セット
        String opCode = gsMsg.getMessage(req, "cmn.delete");
        values.append("[" + gsMsg.getMessage("cmn.label.name") + "] " + delLabelName);

        mcBiz.outPutLog(map, req, opCode, GSConstLog.LEVEL_INFO, values.toString());
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説]
     * [備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     */
    private void __setKanryoDsp(
            ActionMapping map,
            Mem050Form form,
            HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(map.findForward("mem050").getPath());
        form.setMem050SortRadio(null);

        MessageResources msgRes = getResources(req);
        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", getInterMessage(req, "cmn.label")));
        cmn999Form.addHiddenParam("mem050SortRadio", form.getMem050SortRadio());

        req.setAttribute("cmn999Form", cmn999Form);
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
            Mem050Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int changeKbn) throws Exception {

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        Mem050Biz biz = new Mem050Biz();
        boolean commitFlg = false;
        boolean updateFlg = false;

        try {
            Mem050ParamModel paramMdl = new Mem050ParamModel();
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
     * <br>[機  能] 個人設定ラベル順序更新のログ出力処理
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
            Mem050Form form,
            HttpServletRequest req,
            Connection con,
            int changeKbn) throws SQLException {

        //ログ出力
        RequestModel reqMdl = getRequestModel(req);
        Mem050Biz biz = new Mem050Biz();
        MemCommonBiz mcBiz = new MemCommonBiz(con, reqMdl);
        StringBuilder values = new StringBuilder();
        GsMessage gsMsg = new GsMessage(req);

        //内容セット
        String opCode = gsMsg.getMessage(req, "cmn.change");
        String sort = "";
        String[] selectKeyList = form.getMem050SortRadio().split(":");
        String targetName = biz._getLabelName(con, Integer.parseInt(selectKeyList[0]));

        if (changeKbn == 0) {
            sort = gsMsg.getMessage("cmn.up");
        } else {
            sort = gsMsg.getMessage("cmn.down");
        }
        values.append("[" + gsMsg.getMessage("cmn.target") + "]" + targetName
                + "\r\n[" + gsMsg.getMessage("change.sort.order") + "]" + sort);

        mcBiz.outPutLog(map, req, opCode, GSConstLog.LEVEL_INFO, values.toString());
    }
}