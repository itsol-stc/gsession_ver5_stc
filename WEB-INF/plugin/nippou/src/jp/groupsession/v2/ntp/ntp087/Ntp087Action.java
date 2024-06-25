package jp.groupsession.v2.ntp.ntp087;

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
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.ntp.AbstractNippouAdminAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpTemplateDao;
import jp.groupsession.v2.ntp.dao.NtpTmpMemberDao;
import jp.groupsession.v2.ntp.dao.NtpTmpTargetDao;
import jp.groupsession.v2.ntp.model.NtpTemplateModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 テンプレート登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp087Action extends AbstractNippouAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp087Action.class);

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

        Ntp087Form ntpForm = (Ntp087Form) form;
        if (form == null) {
            log__.debug(" form is null ");
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("ntp087ok")) {
            //OK
            forward = __doOk(map, ntpForm, req, res, con);
        } else if (cmd.equals("edit")) {
            //編集
            ntpForm.setNtp087ProcMode(GSConstNippou.CMD_EDIT);
            forward = __doInit(map, ntpForm, req, res, con);
        } else if (cmd.equals("backNtp087")) {
            //戻る
            forward = __doBack(map, ntpForm, req, res, con);
        } else if (cmd.equals("del")) {
            forward = __doDeleteConfirmation(map, ntpForm, req, res, con);
        } else if (cmd.equals("deleteOk")) {
            forward = __doDeleteOk(map, ntpForm, req, res, con);
        } else {
            //デフォルト
            forward = __doInit(map, ntpForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>初期表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Ntp087Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        // トランザクショントークン設定
        saveToken(req);

        log__.debug("初期表示");
        log__.debug("form = " + form);
        ActionForward forward = null;

        Ntp087Biz biz = new Ntp087Biz(con, getRequestModel(req));
        BaseUserModel umodel = getSessionUserModel(req);

        Ntp087ParamModel paramMdl = new Ntp087ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        forward = map.getInputForward();
        return forward;
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行例外
     */
    private ActionForward __doOk(ActionMapping map, Ntp087Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("登録");
        ActionForward forward = null;

        ActionErrors errors = form.validateCheck();
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //DB更新
        Ntp087Biz biz = new Ntp087Biz(con, getRequestModel(req));
        BaseUserModel umodel = getSessionUserModel(req);



        boolean commitFlg = false;

        try {
            MlCountMtController cntCon = getCountMtController(req);

            Ntp087ParamModel paramMdl = new Ntp087ParamModel();
            paramMdl.setParam(form);
            biz.setTemplate(paramMdl, umodel, con, cntCon);
            paramMdl.setFormData(form);

            GsMessage gsMsg = new GsMessage();
            String entry = gsMsg.getMessage(req, "cmn.entry");
            String change = gsMsg.getMessage(req, "cmn.change");

            String opCode = "";
            if (form.getNtp087ProcMode().equals(GSConstNippou.CMD_ADD)) {
                opCode = entry;
            } else {
                opCode = change;
            }
            //ログ出力処理
            NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
            ntpBiz.outPutLog(
                    map, req, res,
                    opCode, GSConstLog.LEVEL_INFO, form.getNtp087TemplateName());

            commitFlg = true;

            //共通メッセージ画面(OK)を表示
            __setCompPageParam(map, req, form);
            forward = map.findForward("gf_msg");

        } catch (Exception e) {
            log__.error("", e);
            throw e;
        } finally {
            if (commitFlg) {
            con.commit();
            }
        }

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
        Ntp087Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("ntp086");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        String key1 = "日報テンプレート";
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Ntp087Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("戻る");
        ActionForward forward = null;
        forward = map.findForward("ntp086");
        return forward;
    }

    /**
     * <br>[機  能] 削除確認処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteConfirmation(ActionMapping map,
        Ntp087Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception, SQLException {

        // トランザクショントークン設定
        saveToken(req);

        ActionForward forward = null;

        NtpTemplateDao templateDao = new NtpTemplateDao(con);
        NtpTemplateModel templateMdl = templateDao.select(form.getNtp086NttSid());

        //削除確認画面を設定
        forward = __setConfirmationDsp(map, req, form,
            "=deleteOk", "sakujo.kakunin.once",
            StringUtilHtml.transToHTmlPlusAmparsant(templateMdl.getNttName()));
        return forward;
    }

    /**
     * <br>[機  能] 削除確認画面でOKボタン押下
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDeleteOk(ActionMapping map,
        Ntp087Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws SQLException {

        //トランザクショントークンチェック
        if (!isTokenValid(req, true)) {
            log__.info("トランザクショントークンエラー");
            return getSubmitErrorPage(map, req);
        }

        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {

            //削除処理実行
            NtpTemplateDao templateDao = new NtpTemplateDao(con);
            NtpTmpMemberDao tmpMemberDao = new NtpTmpMemberDao(con);
            NtpTmpTargetDao tmpTargetDao = new NtpTmpTargetDao(con);
            NtpTemplateModel templateMdl = templateDao.select(form.getNtp086NttSid());
            templateDao.delete(form.getNtp086NttSid());
            tmpMemberDao.delete(form.getNtp086NttSid());
            tmpTargetDao.delete(form.getNtp086NttSid());
            commitFlg = true;

            GsMessage gsMsg = new GsMessage();
            String delete = gsMsg.getMessage(req, "cmn.delete");

            //ログ出力処理
            NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
            ntpBiz.outPutLog(
                    map, req, res,
                    delete,
                    GSConstLog.LEVEL_INFO, templateMdl.getNttName());

            //完了画面設定
            return __setCompDsp(map, req, form,
                "sakujo.kanryo.object",
                StringUtilHtml.transToHTmlPlusAmparsant(templateMdl.getNttName()));

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 確認画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param cmd リクエストコマンドパラメータ
     * @param mesParam メッセージパラメータ
     * @param mesValue メッセージ値
     * @return ActionForward フォワード
     */
    private ActionForward __setConfirmationDsp(ActionMapping map,
        HttpServletRequest req,
        Ntp087Form form,
        String cmd,
        String mesParam,
        String mesValue) {

        MessageResources msgRes = getResources(req);

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        ActionForward forwardOk = map.findForward("redraw");
        ActionForward forwardCancel = map.findForward("redraw");

        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + cmd);
        cmn999Form.setUrlCancel(forwardCancel.getPath() + "?" + GSConst.P_CMD + "=redraw");

        cmn999Form.setMessage(msgRes.getMessage(mesParam, mesValue));

        cmn999Form.addHiddenParam("ntp086NttSid", form.getNtp086NttSid());
        cmn999Form.addHiddenParam("ntp087ProcMode", form.getNtp087ProcMode());
        cmn999Form.addHiddenParam("ntp087TemplateName", form.getNtp087TemplateName());
        cmn999Form.addHiddenParam("ntp087Detail", form.getNtp087Detail());
        cmn999Form.addHiddenParam("ntp087InitFlg", form.getNtp087InitFlg());
        cmn999Form.addHiddenParam("ntp087initDspFlg", form.getNtp087initDspFlg());
        cmn999Form.addHiddenParam("ntp087groupSid", form.getNtp087groupSid());

        if (form.getNtp087DspItem() != null && form.getNtp087DspItem().length > 0) {
            for (String dspItem : form.getNtp087DspItem()) {
                cmn999Form.addHiddenParam("ntp087DspItem", dspItem);
            }
        }

        if (form.getNtp087DspTarget() != null && form.getNtp087DspTarget().length > 0) {
            for (String dspTarget : form.getNtp087DspTarget()) {
                cmn999Form.addHiddenParam("ntp087DspTarget", dspTarget);
            }
        }

        if (form.getNtp087memberSid() != null && form.getNtp087memberSid().length > 0) {
            for (String memSid : form.getNtp087memberSid()) {
                cmn999Form.addHiddenParam("ntp087memberSid", memSid);
            }
        }

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param mesParam メッセージパラメータ
     * @param mesValue メッセージ値
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
        HttpServletRequest req,
        Ntp087Form form,
        String mesParam,
        String mesValue) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("ntp086");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage(mesParam, mesValue));

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}

