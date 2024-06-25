package jp.groupsession.v2.ntp.ntp089;

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
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.ntp.AbstractNippouAdminAction;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.ntp088.Ntp088Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 テンプレート登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp089Action extends AbstractNippouAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp089Action.class);

    /**
     * <br>アクション実行
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
        ActionForward forward = null;

        Ntp089Form ntpForm = (Ntp089Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("ntp089back")) {
            //特例アクセス一覧画面へ戻る
            forward = map.findForward("ntp089back");

        } else if (cmd.equals("confirm")) {
            //確認画面
            forward = __doOK(map, ntpForm, req, res, con);

        } else if (cmd.equals("deleteAccess")) {
            //削除ボタンクリック
            forward = __doDelete(map, ntpForm, req, res, con);

        } else if (cmd.equals("deleteAccessComp")) {
            //削除ボタンクリック
            forward = __doDeleteComp(map, ntpForm, req, res, con);

        } else {
            //デフォルト
            forward = __doInit(map, ntpForm, req, res, con);
        }
        return forward;
    }

    /**
     *
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
    private ActionForward __doInit(ActionMapping map, Ntp089Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        con.setAutoCommit(true);
        Ntp089ParamModel paramMdl = new Ntp089ParamModel();
        paramMdl.setParam(form);
        Ntp089Biz ntpBiz = new Ntp089Biz();
        ntpBiz.setInitData(con, paramMdl, getRequestModel(req));
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
    private ActionForward __doOK(ActionMapping map, Ntp089Form form,
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
        Ntp089Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        // トランザクショントークン設定
        saveToken(req);

        //削除対象の特例アクセスが存在するかを確認
        Ntp088Biz biz = new Ntp088Biz();
        if (!biz.existNtpSpAccess(con, form.getNtp088editData())) {
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
        Ntp089Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //削除対象の特例アクセスが存在するかを確認
        Ntp088Biz ntp088biz = new Ntp088Biz();
        if (!ntp088biz.existNtpSpAccess(con, form.getNtp088editData())) {
            return __doNoneDataError(map, req, form);
        }

        //送付先リストを削除する
        boolean commit = false;
        try {
            Ntp089ParamModel paramMdl = new Ntp089ParamModel();
            paramMdl.setParam(form);
            Ntp089Biz ntp089biz = new Ntp089Biz();
            ntp089biz.deleteAccess(con, paramMdl);
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
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
        String opCode = "cmn.delete";
        ntpBiz.outPutLog(map, req, res, opCode, GSConstLog.LEVEL_INFO, "");

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
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
        Ntp089Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("ntp089back");

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
        Ntp089Form form,
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
        Ntp089Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("ntp089back");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object",
                                gsMsg.getMessage(req, "schedule.sch230.02")));

        ((Ntp089Form) form).setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

}

