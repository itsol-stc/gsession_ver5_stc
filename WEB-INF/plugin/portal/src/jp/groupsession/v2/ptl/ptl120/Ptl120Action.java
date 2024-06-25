package jp.groupsession.v2.ptl.ptl120;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ptl.AbstractPortalAdminAction;
import jp.groupsession.v2.ptl.PtlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] ポータル ポートレットカテゴリ登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl120Action extends AbstractPortalAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl120Action.class);

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
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Ptl120Form thisForm = (Ptl120Form) form;
        if (cmd.equals("ptl120back")) {
            log__.debug("*** 戻るボタンクリック");
            if (thisForm.getPtlPlcBack() == 1) {
                forward = map.findForward("backCategoryList");
            } else {
                forward = map.findForward("backPortletList");
            }

        } else if (cmd.equals("ptl120ok")) {
            log__.debug("*** OKボタンクリック。");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("delete")) {
            log__.debug("*** 削除ボタンクリック。");
            forward = __setDeleteDsp(map, req, thisForm, con, res);

        } else if (cmd.equals("delexe")) {
            log__.debug("*** 削除確認OK。");
            forward = __doDelete(map, thisForm, req, res, con);

        } else {
            log__.debug("*** 初期表示を行います。");
            forward = __doInit(map, thisForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map,
                                  Ptl120Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
                                  throws Exception {

        //ログインユーザ情報を取得
        BaseUserModel userMdl = getSessionUserModel(req);
        if (userMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }
        String cmd = req.getParameter("CMD");

        Ptl120ParamModel paramMdl = new Ptl120ParamModel();
        paramMdl.setParam(form);
        Ptl120Biz biz = new Ptl120Biz();
        biz.init(paramMdl, con, userMdl.getUsrsid(), cmd);
        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doOk(ActionMapping map,
                                Ptl120Form form,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Connection con)
                                throws Exception {

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validatePtl120(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        return map.findForward("moveConfirm");
    }

    /**
     * <br>[機  能] 削除確認画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param con Connection
     * @param res HttpServletResponse
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __setDeleteDsp(ActionMapping map,
                                          HttpServletRequest req,
                                          Ptl120Form form,
                                          Connection con,
                                          HttpServletResponse res) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;
        forwardOk = map.findForward("delOk");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=delexe");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("delback");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //メッセージ
        int editSid = form.getPtlPtlCategorytSid();
        MessageResources msgRes = getResources(req);
        Ptl120Biz biz = new Ptl120Biz();
        String msg = biz.getDeleteCatMsg(con, editSid, msgRes, getRequestModel(req));

        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        cmn999Form.addHiddenParam("ptl120init", form.getPtl120init());
        cmn999Form.addHiddenParam("ptl120name", form.getPtl120name());
        cmn999Form.addHiddenParam("ptl120biko", form.getPtl120biko());

        cmn999Form.addHiddenParam("ptlCmdMode", form.getPtlCmdMode());
        cmn999Form.addHiddenParam("ptl090category", form.getPtl090category());
        cmn999Form.addHiddenParam("ptl090svCategory", form.getPtl090svCategory());
        cmn999Form.addHiddenParam("ptl090sortPortlet", form.getPtl090sortPortlet());
        cmn999Form.addHiddenParam("ptlPlcBack", form.getPtlPlcBack());
        cmn999Form.addHiddenParam("ptlPtlCategorytSid", form.getPtlPtlCategorytSid());
        cmn999Form.addHiddenParam("ptlPlcBack", form.getPtlPlcBack());

        cmn999Form.addHiddenParam("ptlBackPage", form.getPtlBackPage());
        cmn999Form.addHiddenParam("ptlMainSid", form.getPtlMainSid());

        //カテゴリ一覧画面から遷移の場合
        if (form.getPtlPlcBack() == 1) {
            cmn999Form.addHiddenParam("ptl110sortPltCategory", form.getPtl110sortPltCategory());
        }
        req.setAttribute("cmn999Form", cmn999Form);

        // トランザクショントークン設定
        saveToken(req);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Ptl120Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {


        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ActionForward forward = null;
        boolean commit = false;
        try {
            Ptl120ParamModel paramMdl = new Ptl120ParamModel();
            paramMdl.setParam(form);
            Ptl120Biz biz = new Ptl120Biz();
            //カテゴリ削除
            biz.deleteCategory(paramMdl,
                             form.getPtlPtlCategorytSid(),
                             con);
            paramMdl.setFormData(form);

            forward = __setCompDsp(map, req, form);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("ポートレットカテゴリ情報の削除に失敗", e);
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("cmn.delete");

        String opCode = msg;

        //ログ出力処理
        PtlCommonBiz rngBiz = new PtlCommonBiz(con);
        rngBiz.outPutLog(
                map, reqMdl, opCode,
                GSConstLog.LEVEL_INFO,
                "[title]" + form.getPtl120name());

        return forward;
    }

    /**
     * <br>[機  能] 削除完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
                                        HttpServletRequest req,
                                        Ptl120Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;
        if (form.getPtlPlcBack() == 1) {
            forwardOk = map.findForward("backCategoryList");
        } else {
            forwardOk = map.findForward("backPortletList");
        }
        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "ptl.ptl110.1");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", msg));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("ptlCmdMode", form.getPtlCmdMode());
        cmn999Form.addHiddenParam("ptl090category", form.getPtl090category());
        cmn999Form.addHiddenParam("ptl090svCategory", form.getPtl090svCategory());
        cmn999Form.addHiddenParam("ptl090sortPortlet", form.getPtl090sortPortlet());
        cmn999Form.addHiddenParam("ptlPlcBack", form.getPtlPlcBack());

        cmn999Form.addHiddenParam("ptlBackPage", form.getPtlBackPage());
        cmn999Form.addHiddenParam("ptlMainSid", form.getPtlMainSid());

        //カテゴリ一覧画面から遷移の場合
        if (form.getPtlPlcBack() == 1) {
            cmn999Form.addHiddenParam("ptl110sortPltCategory", form.getPtl110sortPltCategory());
        }

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

}