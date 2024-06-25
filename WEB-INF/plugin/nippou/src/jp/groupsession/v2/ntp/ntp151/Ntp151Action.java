package jp.groupsession.v2.ntp.ntp151;

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
import jp.groupsession.v2.ntp.dao.NtpProcessDao;
import jp.groupsession.v2.ntp.dao.NtpProcessSortDao;
import jp.groupsession.v2.ntp.model.NtpProcessModel;
import jp.groupsession.v2.ntp.model.NtpProcessSortModel;
import jp.groupsession.v2.ntp.ntp150.Ntp150ProcessDao;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] 日報 プロセス登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp151Action extends AbstractNippouMstmntAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp151Action.class);

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
        Ntp151Form ntpForm = (Ntp151Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("backNtp151")) {
            //戻るボタンクリック
            forward = map.findForward("ntp150");
        } else if (cmd.equals("ok")) {
            forward = __doRegistConfirmation(map, ntpForm, req, res, con);
        } else if (cmd.equals("addOk")) {
            forward = __doRegistOk(map, ntpForm, req, res, con);
        } else if (cmd.equals("del")) {
            forward = __doDeleteConfirmation(map, ntpForm, req, res, con);
        } else if (cmd.equals("deleteOk")) {
            forward = __doDeleteOk(map, ntpForm, req, res, con);
        } else if (cmd.equals("redraw")) {
            Ntp151Biz biz = new Ntp151Biz(getRequestModel(req));

            Ntp151ParamModel paramMdl = new Ntp151ParamModel();
            paramMdl.setParam(form);
            biz.setDspData(paramMdl, con);
            paramMdl.setFormData(form);

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
     * @throws SQLException 実行例外
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Ntp151Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException, Exception {
        int sessionUserSid = this.getSessionUserModel(req).getUsrsid();
        //採番
        MlCountMtController cntCon = getCountMtController(req);

        Ntp151Biz biz = new Ntp151Biz(cntCon, getRequestModel(req));

        Ntp151ParamModel paramMdl = new Ntp151ParamModel();
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
        Ntp151Form form,
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
            Ntp151Biz biz = new Ntp151Biz(getRequestModel(req));

            Ntp151ParamModel paramMdl = new Ntp151ParamModel();
            paramMdl.setParam(form);
            biz.setDspData(paramMdl, con);
            paramMdl.setFormData(form);

            return map.getInputForward();
        }

        //登録確認画面を設定
        forward = __setConfirmationDsp(map, req, form,
            "=addOk", "touroku.kakunin.once",
            StringUtilHtml.transToHTmlPlusAmparsant(form.getNtp151ProcessName()));
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
        Ntp151Form form,
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
            NtpProcessDao processDao = new NtpProcessDao(con);
            NtpProcessModel processMdl = __createNtpProcess(usrSid);

            processMdl.setNgpCode(form.getNtp151ProcessCode());
            processMdl.setNgpName(form.getNtp151ProcessName());
            processMdl.setNgpAccount(form.getNtp151Naiyo());
            processMdl.setNgySid(form.getNtp150NgySid());

            if (form.getNtp150ProcMode().equals(GSConstNippou.CMD_ADD)) {
                Ntp150ProcessDao process150Dao = new Ntp150ProcessDao(con);
                processMdl.setNgpSort(process150Dao.getMaxSort(form.getNtp150NgySid()));

                //追加モード
                MlCountMtController cntCon = getCountMtController(req);
                //SID採番
                int ngpSid = (int) cntCon.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                    GSConstNippou.SBNSID_SUB_PROCESS, usrSid);
                processMdl.setNgpSid(ngpSid);
                processDao.insert(processMdl);

                //ソートテーブルに追加
                NtpProcessSortDao sortDao = new NtpProcessSortDao(con);
                NtpProcessSortModel sortMdl = new NtpProcessSortModel();
                sortMdl.setNgpSid(ngpSid);
                sortMdl.setNgySid(form.getNtp150NgySid());
                sortMdl.setNpsSort(sortDao.getMaxSort(form.getNtp150NgySid()));
                sortDao.insert(sortMdl);

            } else {
                NtpProcessModel mdl = processDao.select(form.getNtp150NgpSid());
                processMdl.setNgpSort(mdl.getNgpSort());

                //変更モード
                processMdl.setNgpSid(form.getNtp150NgpSid());
                processDao.update(processMdl);
            }
            commitFlg = true;

            GsMessage gsMsg = new GsMessage();
            String entry = gsMsg.getMessage(req, "cmn.entry");
            String change = gsMsg.getMessage(req, "cmn.change");

            //ログ出力処理
            NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
            String opCode = "";
            if (form.getNtp150ProcMode().equals(GSConstNippou.CMD_ADD)) {
                opCode = entry;
            } else {
                opCode = change;
            }

            ntpBiz.outPutLog(
             map, req, res, opCode,
             GSConstLog.LEVEL_TRACE, form.getNtp151ProcessName());

            //完了画面設定
            return __setCompDsp(map, req, form,
                "touroku.kanryo.object",
                StringUtilHtml.transToHTmlPlusAmparsant(form.getNtp151ProcessName()));

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
        Ntp151Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception, SQLException {

        // トランザクショントークン設定
        saveToken(req);

        ActionForward forward = null;

        NtpProcessDao processDao = new NtpProcessDao(con);
        NtpProcessModel processMdl = processDao.select(form.getNtp150NgpSid());

        //削除確認画面を設定
        forward = __setConfirmationDsp(map, req, form,
            "=deleteOk", "sakujo.kakunin.once",
            StringUtilHtml.transToHTmlPlusAmparsant(processMdl.getNgpName()));
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
        Ntp151Form form,
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
            NtpProcessDao processDao = new NtpProcessDao(con);
            NtpProcessModel processMdl = processDao.select(form.getNtp150NgpSid());
            processDao.delete(form.getNtp150NgpSid());
            commitFlg = true;

            GsMessage gsMsg = new GsMessage();
            String delete = gsMsg.getMessage(req, "cmn.delete");

            //ログ出力処理
            NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
            ntpBiz.outPutLog(
                    map, req, res,
                    delete,
                    GSConstLog.LEVEL_TRACE, processMdl.getNgpName());

            //完了画面設定
            return __setCompDsp(map, req, form,
                "sakujo.kanryo.object",
                StringUtilHtml.transToHTmlPlusAmparsant(processMdl.getNgpName()));

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
        Ntp151Form form,
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

        cmn999Form.addHiddenParam("ntp150NgpSid", form.getNtp150NgpSid());
        cmn999Form.addHiddenParam("ntp150NgySid", form.getNtp150NgySid());
        cmn999Form.addHiddenParam("ntp150ProcMode", form.getNtp150ProcMode());
        cmn999Form.addHiddenParam("ntp151ProcessCode", form.getNtp151ProcessCode());
        cmn999Form.addHiddenParam("ntp151ProcessName", form.getNtp151ProcessName());
        cmn999Form.addHiddenParam("ntp151Naiyo", form.getNtp151Naiyo());
        cmn999Form.addHiddenParam("ntp150DispNgySid", form.getNtp150DispNgySid());
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
        Ntp151Form form,
        String mesParam,
        String mesValue) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("ntp150");
        cmn999Form.setUrlOK(forwardOk.getPath());
        form.setNtp150HiddenParam(cmn999Form);

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage(mesParam, mesValue));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] プロセス情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpProcessModel
     */
    private NtpProcessModel __createNtpProcess(int usrSid) {

        UDate nowDate = new UDate();
        NtpProcessModel mdl = new NtpProcessModel();
        mdl.setNgpAuid(usrSid);
        mdl.setNgpAdate(nowDate);
        mdl.setNgpEuid(usrSid);
        mdl.setNgpEdate(nowDate);
        return mdl;
    }
}

