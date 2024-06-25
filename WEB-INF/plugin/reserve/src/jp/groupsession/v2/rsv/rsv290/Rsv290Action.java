package jp.groupsession.v2.rsv.rsv290;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.rsv.AbstractReserveAdminAction;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 管理者設定 日間表示時間帯デフォルト設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv290Action extends AbstractReserveAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv290Action.class);

    /**
    * <br>[機  能] アクションを実行する
    * <br>[解  説]
    * <br>[備  考]
    * @param map ActionMapping
    * @param form ActionForm
    * @param req HttpServletRequest
    * @param res HttpServletResponse
    * @param con DB Connection
    * @return ActionForward
    * @throws Exception 実行時例外
    * @see jp.co.sjts.util.struts.AbstractAction
    * @see #executeAction(org.apache.struts.action.ActionMapping,
    *                      org.apache.struts.action.ActionForm,
    *                      javax.servlet.http.HttpServletRequest,
    *                      javax.servlet.http.HttpServletResponse,
    *                      java.sql.Connection)
    */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Rsv290Form rsvform = (Rsv290Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //OKボタン押下
        if (cmd.equals("zikantai_settei_kakunin")) {
            log__.debug("OKボタン押下");
            //OK
            forward = __doOk(map, rsvform, req, res, con);
        } else if (cmd.equals("changeOk")) {
            //確認画面からの遷移
            forward = __doCommit(map, rsvform, req, res, con);

        //戻るボタンクリック
        } else if (cmd.equals("backAdminMenu")) {
            forward = map.findForward("adminMenu");
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, rsvform, req, res, con);
        }

        return forward;
    }
    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Rsv290Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Rsv290Biz biz = new Rsv290Biz(con, getRequestModel(req));

        Rsv290ParamModel paramMdl = new Rsv290ParamModel();
        paramMdl.setParam(form);
        biz.initDsp(paramMdl, getSessionUserModel(req).getUsrsid());
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタン押下時
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doOk(ActionMapping map,
                                    Rsv290Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        saveToken(req);

        //共通メッセージ画面を表示
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("mine").getPath() + "?CMD=changeOk");
        cmn999Form.setUrlCancel(map.findForward("mine").getPath());

        //メッセージセット
        String msgState = "edit.kakunin.once";
        GsMessage gsMsg = new GsMessage();

        String mkey1 = gsMsg.getMessage(req, "cmn.display.settings");
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        form.setParamValue(cmn999Form);
        cmn999Form.addHiddenParam("rsv290DateKbn", form.getRsv290DateKbn());
        cmn999Form.addHiddenParam("rsv290SelectedFromSid", form.getRsv290SelectedFromSid());
        cmn999Form.addHiddenParam("rsv290SelectedToSid", form.getRsv290SelectedToSid());
        cmn999Form.addHiddenParam("rsv290initDspFlg", form.getRsv290initDspFlg());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommit(
        ActionMapping map,
        Rsv290Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        ActionForward forward = null;

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }

        boolean commit = false;
        try {
            //DB更新
            Rsv290Biz biz = new Rsv290Biz(con, getRequestModel(req));

            Rsv290ParamModel paramMdl = new Rsv290ParamModel();
            paramMdl.setParam(form);
            biz.updateAdmConf(paramMdl, getSessionUserSid(req));
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //ログ出力処理
        GsMessage gsMsg = new GsMessage();
        String msg = __getLogMessage(form, req);
        AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
        rsvBiz.outPutLog(map, req, res,
                gsMsg.getMessage(req, "cmn.change"), GSConstLog.LEVEL_INFO, msg);

        //共通メッセージ画面(OK)を表示
        __setCompPageParam(map, req, form);
        forward = map.findForward("gf_msg");
        return forward;
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
        Rsv290Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("adminMenu");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        GsMessage gsMsg = new GsMessage();

        String mkey1 = gsMsg.getMessage(req, "cmn.display.settings");
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));
        form.setParamValue(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>[機  能] ログ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     * @throws SQLException SQLエラー
     */
    private String __getLogMessage(
        Rsv290Form form,
        HttpServletRequest req) throws SQLException {

        GsMessage gsMsg = new GsMessage(req);
        String msg = "";

        msg += "[" + gsMsg.getMessage("cmn.period") + "]";
        if (form.getRsv290DateKbn() == GSConstReserve.AUTH_ADMIN_USER) {
            msg += gsMsg.getMessage("cmn.set.the.admin");
        } else if (form.getRsv290DateKbn() == GSConstReserve.AUTH_ALL_USER) {
            msg += gsMsg.getMessage("cmn.set.eachuser");
        }

        //期間
        msg += "\r\n" + gsMsg.getMessage("cmn.starttime") + " : ";
        msg += form.getRsv290SelectedFromSid() + gsMsg.getMessage("cmn.hour");

        msg += "\r\n" + gsMsg.getMessage("cmn.endtime") + " : ";
        msg += form.getRsv290SelectedToSid() + gsMsg.getMessage("cmn.hour");
        return msg;
    }
}