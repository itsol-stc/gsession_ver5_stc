package jp.groupsession.v2.ntp.ntp191;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.ntp.AbstractNippouMstmntAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpContactDao;
import jp.groupsession.v2.ntp.dao.NtpContactSortDao;
import jp.groupsession.v2.ntp.model.NtpContactModel;
import jp.groupsession.v2.ntp.model.NtpContactSortModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] 日報 顧客源泉登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp191Action extends AbstractNippouMstmntAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp191Action.class);

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
        Ntp191Form ntpForm = (Ntp191Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("backNtp191")) {
            //戻るボタンクリック
            forward = map.findForward("ntp190");
        } else if (cmd.equals("ok")) {
            forward = __doRegistConfirmation(map, ntpForm, req, res, con);
        } else if (cmd.equals("addOk")) {
            forward = __doRegistOk(map, ntpForm, req, res, con);
        } else if (cmd.equals("del")) {
            forward = __doDeleteConfirmation(map, ntpForm, req, res, con);
        } else if (cmd.equals("deleteOk")) {
            forward = __doDeleteOk(map, ntpForm, req, res, con);
        } else if (cmd.equals("redraw")) {
            forward = map.getInputForward();
        } else {
            //初期表示
            forward = __doInit(map, ntpForm, req, res, con);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Ntp191Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException, Exception {
        int sessionUserSid = this.getSessionUserModel(req).getUsrsid();
        //採番
        MlCountMtController cntCon = getCountMtController(req);

        Ntp191Biz biz = new Ntp191Biz(cntCon, getRequestModel(req));

        Ntp191ParamModel paramMdl = new Ntp191ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 登録確認処理
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
    private ActionForward __doRegistConfirmation(ActionMapping map,
        Ntp191Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception, SQLException {

        // トランザクショントークン設定
        saveToken(req);

        ActionForward forward = null;

        //入力チェック
        ActionErrors errors = form.validateCheck(con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //登録確認画面を設定
        forward = __setConfirmationDsp(map, req, form,
            "=addOk", "touroku.kakunin.once",
            StringUtilHtml.transToHTmlPlusAmparsant(form.getNtp191ContactName()));
        return forward;
    }

    /**
     * <br>[機  能] 登録確認画面でOKボタン押下
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doRegistOk(ActionMapping map,
        Ntp191Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {

        //トランザクショントークンチェック
        if (!isTokenValid(req, true)) {
            log__.info("トランザクショントークンエラー");
            return getSubmitErrorPage(map, req);
        }

        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {
            int usrSid = this.getSessionUserModel(req).getUsrsid();
            NtpContactDao contactDao = new NtpContactDao(con);
            NtpContactModel contactMdl = __createNtpContact(usrSid);

            contactMdl.setNcnCode(form.getNtp191ContactCode());
            contactMdl.setNcnName(form.getNtp191ContactName());

            if (form.getNtp190ProcMode().equals(GSConstNippou.CMD_ADD)) {
                //追加モード
                MlCountMtController cntCon = getCountMtController(req);
                //SID採番
                int ngySid = (int) cntCon.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                    GSConstNippou.SBNSID_SUB_CONTACT, usrSid);
                contactMdl.setNcnSid(ngySid);
                contactDao.insert(contactMdl);

                //ソートテーブルに追加
                NtpContactSortDao sortDao = new NtpContactSortDao(con);
                NtpContactSortModel sortMdl = new NtpContactSortModel();
                sortMdl.setNcnSid(ngySid);
                sortMdl.setNcsSort(sortDao.getMaxSort());
                sortDao.insert(sortMdl);

            } else {
                //変更モード
                contactMdl.setNcnSid(form.getNtp190NcnSid());
                contactDao.update(contactMdl);
            }
            commitFlg = true;

            GsMessage gsMsg = new GsMessage();
            String entry = gsMsg.getMessage(req, "cmn.entry");
            String change = gsMsg.getMessage(req, "cmn.change");

            //ログ出力処理
            NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
            String opCode = "";
            if (form.getNtp190ProcMode().equals(GSConstNippou.CMD_ADD)) {
                opCode = entry;
            } else {
                opCode = change;
            }

            ntpBiz.outPutLog(
             map, req, res, opCode, GSConstLog.LEVEL_TRACE, form.getNtp191ContactName());

            //完了画面設定
            return __setCompDsp(map, req, form,
                "touroku.kanryo.object",
                StringUtilHtml.transToHTmlPlusAmparsant(form.getNtp191ContactName()));

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
        Ntp191Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception, SQLException {

        // トランザクショントークン設定
        saveToken(req);

        ActionForward forward = null;

        NtpContactDao contactDao = new NtpContactDao(con);
        NtpContactModel contactMdl = contactDao.select(form.getNtp190NcnSid());

        //削除確認画面を設定
        forward = __setConfirmationDsp(map, req, form,
            "=deleteOk", "sakujo.kakunin.once",
            StringUtilHtml.transToHTmlPlusAmparsant(contactMdl.getNcnName()));
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
        Ntp191Form form,
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
            NtpContactDao contactDao = new NtpContactDao(con);
            NtpContactModel contactMdl = contactDao.select(form.getNtp190NcnSid());
            contactDao.delete(form.getNtp190NcnSid());
            commitFlg = true;

            GsMessage gsMsg = new GsMessage();
            String delete = gsMsg.getMessage(req, "cmn.delete");

            //ログ出力処理
            NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
            ntpBiz.outPutLog(
                    map, req, res,
                    delete,
                    GSConstLog.LEVEL_TRACE, contactMdl.getNcnName());

            //完了画面設定
            return __setCompDsp(map, req, form,
                "sakujo.kanryo.object",
                StringUtilHtml.transToHTmlPlusAmparsant(contactMdl.getNcnName()));

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
        Ntp191Form form,
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

        cmn999Form.addHiddenParam("ntp190NcnSid", form.getNtp190NcnSid());
        cmn999Form.addHiddenParam("ntp190ProcMode", form.getNtp190ProcMode());
        cmn999Form.addHiddenParam("ntp191ContactCode", form.getNtp191ContactCode());
        cmn999Form.addHiddenParam("ntp191ContactName", form.getNtp191ContactName());
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
        Ntp191Form form,
        String mesParam,
        String mesValue) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("ntp190");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage(mesParam, mesValue));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] コンタクト情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpContactModel
     */
    private NtpContactModel __createNtpContact(int usrSid) {

        UDate nowDate = new UDate();
        NtpContactModel mdl = new NtpContactModel();
        mdl.setNcnAuid(usrSid);
        mdl.setNcnAdate(nowDate);
        mdl.setNcnEuid(usrSid);
        mdl.setNcnEdate(nowDate);
        return mdl;
    }
}

