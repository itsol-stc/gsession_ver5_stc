package jp.groupsession.v2.ntp.ntp231;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
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
import jp.groupsession.v2.ntp.dao.NtpTargetDao;
import jp.groupsession.v2.ntp.dao.NtpTargetSortDao;
import jp.groupsession.v2.ntp.dao.NtpTmpTargetDao;
import jp.groupsession.v2.ntp.model.NtpTargetModel;
import jp.groupsession.v2.ntp.model.NtpTargetSortModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] 日報 目標登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp231Action extends AbstractNippouMstmntAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp231Action.class);

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
        Ntp231Form ntpForm = (Ntp231Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("backNtp231")) {
            //戻るボタンクリック
            forward = map.findForward("ntp230");
        } else if (cmd.equals("ok")) {
            forward = __doRegistOk(map, ntpForm, req, res, con);
//        } else if (cmd.equals("addOk")) {
//            forward = __doRegistOk(map, ntpForm, req, res, con);
        } else if (cmd.equals("del")) {
            forward = __doDeleteConfirmation(map, ntpForm, req, res, con);
        } else if (cmd.equals("deleteOk")) {
            forward = __doDeleteOk(map, ntpForm, req, res, con);
        } else if (cmd.equals("redraw")) {
            forward = __doInit(map, ntpForm, req, res, con);
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
        Ntp231Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException, Exception {
        int sessionUserSid = this.getSessionUserModel(req).getUsrsid();
        //採番
        MlCountMtController cntCon = getCountMtController(req);

        // トランザクショントークン設定
        saveToken(req);

        Ntp231Biz biz = new Ntp231Biz(cntCon, getRequestModel(req));

        Ntp231ParamModel paramMdl = new Ntp231ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

//    /**
//     * <br>[機  能] 登録確認処理
//     * <br>[解  説]
//     * <br>[備  考]
//     *
//     * @param map マップ
//     * @param form フォーム
//     * @param req リクエスト
//     * @param res レスポンス
//     * @param con コネクション
//     * @return ActionForward フォワード
//     * @throws SQLException SQL実行時例外
//     * @throws Exception 実行時例外
//     */
//    private ActionForward __doRegistConfirmation(ActionMapping map,
//        Ntp231Form form,
//        HttpServletRequest req,
//        HttpServletResponse res,
//        Connection con)
//        throws Exception, SQLException {
//
//
//        ActionForward forward = null;
//
//        //入力チェック
//        ActionErrors errors = form.validateCheck(con);
//        if (!errors.isEmpty()) {
//            addErrors(req, errors);
//            return map.getInputForward();
//        }
//
//        //登録確認画面を設定
//        forward = __setConfirmationDsp(map, req, form,
//            "=addOk", "touroku.kakunin.once", form.getNtp191ContactName());
//        return forward;
//    }

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
        Ntp231Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //トランザクショントークンチェック
        if (!isTokenValid(req, true)) {
            log__.info("トランザクショントークンエラー");
            return getSubmitErrorPage(map, req);
        }

        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {
            int usrSid = this.getSessionUserModel(req).getUsrsid();
            NtpTargetDao targetDao = new NtpTargetDao(con);
            NtpTargetModel targetMdl = __createNtpTarget(usrSid);
            NtpTmpTargetDao tmpTrgDao = new NtpTmpTargetDao(con);

            targetMdl.setNtgName(form.getNtp231TargetName());
            targetMdl.setNtgUnit(form.getNtp231TargetUnit());
            targetMdl.setNtgDef(Long.valueOf(form.getNtp231TargetDef()));
            if (!StringUtil.isNullZeroStringSpace(form.getNtp231TargetDetail())) {
                targetMdl.setNtgDetail(form.getNtp231TargetDetail());
            }

            if (form.getNtp230ProcMode().equals(GSConstNippou.CMD_ADD)) {
                //追加モード
                MlCountMtController cntCon = getCountMtController(req);
                //SID採番
                int ntgSid = (int) cntCon.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                    GSConstNippou.SBNSID_SUB_TARGET, usrSid);
                targetMdl.setNtgSid(ntgSid);
                targetDao.insert(targetMdl);

                //ソートテーブルに追加
                NtpTargetSortDao sortDao = new NtpTargetSortDao(con);
                NtpTargetSortModel sortMdl = new NtpTargetSortModel();
                sortMdl.setNtgSid(ntgSid);
                sortMdl.setNtsSort(sortDao.getMaxSort());
                sortDao.insert(sortMdl);

                //テンプレートを設定
                if (form.getNtp231DspTemplate() != null) {
                    if (form.getNtp231DspTemplate().length > 0) {
                        for (String nttSid : form.getNtp231DspTemplate()) {
                            tmpTrgDao.insert(Integer.valueOf(nttSid), ntgSid);
                        }
                    }
                }


            } else {
                //変更モード
                targetMdl.setNtgSid(form.getNtp230NtgSid());
                targetDao.update(targetMdl);

                //テンプレートを設定
                tmpTrgDao.deleteTarget(form.getNtp230NtgSid());
                if (form.getNtp231DspTemplate() != null) {
                    if (form.getNtp231DspTemplate().length > 0) {
                        for (String nttSid : form.getNtp231DspTemplate()) {
                            tmpTrgDao.insert(Integer.valueOf(nttSid), form.getNtp230NtgSid());
                        }
                    }
                }
            }
            commitFlg = true;

            GsMessage gsMsg = new GsMessage();
            String entry = gsMsg.getMessage(req, "cmn.entry");
            String change = gsMsg.getMessage(req, "cmn.change");

            //ログ出力処理
            NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
            String opCode = "";
            if (form.getNtp230ProcMode().equals(GSConstNippou.CMD_ADD)) {
                opCode = entry;
            } else {
                opCode = change;
            }

            ntpBiz.outPutLog(
             map, req, res, opCode, GSConstLog.LEVEL_TRACE, form.getNtp231TargetName());

            //完了画面設定
            return __setCompDsp(map, req, form,
                "touroku.kanryo.object",
                StringUtilHtml.transToHTmlPlusAmparsant(form.getNtp231TargetName()));

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
        Ntp231Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception, SQLException {

        // トランザクショントークン設定
        saveToken(req);

        ActionForward forward = null;

        NtpTargetDao targetDao = new NtpTargetDao(con);
        NtpTargetModel targetMdl = targetDao.select(form.getNtp230NtgSid());

        //削除確認画面を設定
        forward = __setConfirmationDsp(map, req, form,
            "=deleteOk", "sakujo.kakunin.once",
            StringUtilHtml.transToHTmlPlusAmparsant(targetMdl.getNtgName()));
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
        Ntp231Form form,
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
            NtpTargetDao targetDao = new NtpTargetDao(con);
            NtpTargetModel targetMdl = targetDao.select(form.getNtp230NtgSid());
            targetDao.delete(form.getNtp230NtgSid());
            NtpTmpTargetDao tmpTargetDao = new NtpTmpTargetDao(con);
            tmpTargetDao.deleteTarget(form.getNtp230NtgSid());
            commitFlg = true;

            GsMessage gsMsg = new GsMessage();
            String delete = gsMsg.getMessage(req, "cmn.delete");

            //ログ出力処理
            NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
            ntpBiz.outPutLog(
                    map, req, res,
                    delete,
                    GSConstLog.LEVEL_TRACE, targetMdl.getNtgName());

            //完了画面設定
            return __setCompDsp(map, req, form,
                "sakujo.kanryo.object",
                StringUtilHtml.transToHTmlPlusAmparsant(targetMdl.getNtgName()));

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
        Ntp231Form form,
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
        if (form.getNtp231DspTemplate() != null && form.getNtp231DspTemplate().length > 0) {
            for (String dspTmp : form.getNtp231DspTemplate()) {
                cmn999Form.addHiddenParam("ntp231DspTemplate", dspTmp);
            }

        }

        cmn999Form.addHiddenParam("ntp231InitFlg", form.getNtp231InitFlg());
        cmn999Form.addHiddenParam("ntp231initDspFlg", form.getNtp231initDspFlg());
        cmn999Form.addHiddenParam("ntp230NtgSid", form.getNtp230NtgSid());
        cmn999Form.addHiddenParam("ntp230ProcMode", form.getNtp230ProcMode());
        cmn999Form.addHiddenParam("ntp231TargetName", form.getNtp231TargetName());
        cmn999Form.addHiddenParam("ntp231TargetUnit", form.getNtp231TargetUnit());
        cmn999Form.addHiddenParam("ntp231TargetDef", form.getNtp231TargetDef());
        cmn999Form.addHiddenParam("ntp231TargetDetail", form.getNtp231TargetDetail());
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了画面
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param mesParam メッセージパラメータ
     * @param mesValue メッセージ値
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
        HttpServletRequest req,
        Ntp231Form form,
        String mesParam,
        String mesValue) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("ntp230");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage(mesParam, mesValue));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] 目標情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpContactModel
     */
    private NtpTargetModel __createNtpTarget(int usrSid) {

        UDate nowDate = new UDate();
        NtpTargetModel mdl = new NtpTargetModel();
        mdl.setNtgAuid(usrSid);
        mdl.setNtgAdate(nowDate);
        mdl.setNtgEuid(usrSid);
        mdl.setNtgEdate(nowDate);
        return mdl;
    }
}

