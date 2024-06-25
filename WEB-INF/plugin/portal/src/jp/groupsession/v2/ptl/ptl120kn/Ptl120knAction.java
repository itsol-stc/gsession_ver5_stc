package jp.groupsession.v2.ptl.ptl120kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstPortal;
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
 * <br>[機  能] ポータル ポートレットカテゴリ登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl120knAction extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl120knAction.class);

    /** 遷移元画面 ポートレット管理 */
    public static final int PORTLET_SETTING = 0;
    /** 遷移元画面 ポートレットカテゴリ一覧 */
    public static final int PORTLET_CATEGORY = 1;

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
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Ptl120knForm thisForm = (Ptl120knForm) form;

        if (cmd.equals("ptl120knOk")) {
            //「確定」ボタンクリック
            forward = __doKakutei(map, thisForm, req, res, con);

        } else if (cmd.equals("ptl120knBack")) {
            //戻るボタンクリック
            forward = map.findForward("backAddCategory");

        } else {
            //初期表示
            forward = map.getInputForward();
        }

        return forward;
    }

    /**
     * <br>[機  能] 確定ボタンクリック時
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
    public ActionForward __doKakutei(ActionMapping map,
                                  Ptl120knForm form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
                                  throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        //入力チェック
        ActionErrors errors = form.validatePtl120(reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
       }

        boolean commit = false;
        try {

            //採番コントローラ
            MlCountMtController cntCon = getCountMtController(req);

            //ログインユーザSID取得
            BaseUserModel userMdl = getSessionUserModel(req);
            if (userMdl == null) {
                throw new GSAuthenticateException("ユーザ情報の取得に失敗");
            }
            int usrSid = userMdl.getUsrsid();

            //登録または更新処理
            Ptl120knParamModel paramMdl = new Ptl120knParamModel();
            paramMdl.setParam(form);
            Ptl120knBiz biz = new Ptl120knBiz();
            if (form.getPtlCmdMode() == GSConstPortal.CMD_MODE_ADD) {
                biz.insert(paramMdl, con, usrSid, cntCon);
            } else if (form.getPtlCmdMode() == GSConstPortal.CMD_MODE_EDIT) {
                biz.update(paramMdl, con, usrSid, cntCon);
            }
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("ポートレットカテゴリ情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String entry = gsMsg.getMessage("cmn.entry");
        String edit = gsMsg.getMessage("cmn.edit");

        String opCode = "";

        if (form.getPtlCmdMode() == GSConstPortal.CMD_MODE_ADD) {
            opCode = entry;
        } else if (form.getPtlCmdMode() == GSConstPortal.CMD_MODE_EDIT) {
            opCode = edit;
        }

        //ログ出力
        PtlCommonBiz ptlCmnBiz = new PtlCommonBiz(con);
        ptlCmnBiz.outPutLog(map, reqMdl, opCode, GSConstLog.LEVEL_INFO,
                "[CategoryName]" + form.getPtl120name());


        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(ActionMapping map,
                                         Ptl120knForm form,
                                         HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;

        if (form.getPtlPlcBack() == PORTLET_SETTING) {
            //ポートレット管理画面へ
            forwardOk = map.findForward("portletList");
        } else {
            //ポートレットカテゴリ一覧へ
            forwardOk = map.findForward("categoryList");
        }
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.category");

        if (form.getPtlCmdMode() == GSConstPortal.CMD_MODE_ADD) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object", msg));
        } else if (form.getPtlCmdMode() == GSConstPortal.CMD_MODE_EDIT) {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object", msg));
        }

        cmn999Form.addHiddenParam("ptlCmdMode", form.getPtlCmdMode());
        cmn999Form.addHiddenParam("ptl090category", form.getPtl090category());
        cmn999Form.addHiddenParam("ptl090svCategory", form.getPtl090svCategory());
        cmn999Form.addHiddenParam("ptl090sortPortlet", form.getPtl090sortPortlet());
        if (form.getPtlPlcBack() == PORTLET_CATEGORY) {
            cmn999Form.addHiddenParam("ptl110sortPltCategory", form.getPtl110sortPltCategory());
        }

        cmn999Form.addHiddenParam("ptlBackPage", form.getPtlBackPage());
        cmn999Form.addHiddenParam("ptlMainSid", form.getPtlMainSid());

        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }


}

