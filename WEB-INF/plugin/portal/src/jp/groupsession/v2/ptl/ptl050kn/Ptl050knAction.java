package jp.groupsession.v2.ptl.ptl050kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
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
 * <br>[機  能] ポータル ポータル登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl050knAction extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl050knAction.class);

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
        Ptl050knForm ptlForm = (Ptl050knForm) form;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("ptl050knOk")) {
            //確定ボタンクリック
            forward = __doDecision(map, ptlForm, req, res, con);

        } else if (cmd.equals("ptl050knBack")) {
            //戻るボタンクリック
            forward = map.findForward("backAddPortal");

        } else {
            //初期表示
            forward = __doInit(map, ptlForm, req, res, con);
        }

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
     */
    private ActionForward __doInit(ActionMapping map,
        Ptl050knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException {

        con.setAutoCommit(true);
        Ptl050knParamModel paramMdl = new Ptl050knParamModel();
        paramMdl.setParam(form);
        Ptl050knBiz biz = new Ptl050knBiz();
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doDecision(ActionMapping map,
        Ptl050knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //ログインユーザSIDを取得
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        Ptl050knBiz biz = new Ptl050knBiz();
        boolean commit = false;

        try {
            MlCountMtController cntCon = getCountMtController(req);

            Ptl050knParamModel paramMdl = new Ptl050knParamModel();
            paramMdl.setParam(form);
            if (form.getPtlPortalSid() < 1) {

                //登録処理
                biz.insertData(paramMdl, con, cntCon, buMdl.getUsrsid());

            } else {

                //更新処理
                biz.updateData(paramMdl, con, cntCon, buMdl.getUsrsid());

            }
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("ポータル登録処理エラー", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //ログ出力処理
        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        String opCode = "";
        if (form.getPtlPortalSid() < 1) {
            opCode = gsMsg.getMessage("cmn.entry");
        } else {
            opCode = gsMsg.getMessage("cmn.change");
        }

        ptlBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_INFO,
                "[name]" + form.getPtl050name());

        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
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
        Ptl050knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backPortalList");
        cmn999Form.setUrlOK(urlForward.getPath());

        cmn999Form.addHiddenParam("ptlBackPage", form.getPtlBackPage());
        cmn999Form.addHiddenParam("ptlMainSid", form.getPtlMainSid());

        //メッセージセット
        String msgState = null;
        if (form.getPtlPortalSid() < 1) {
            msgState = "touroku.kanryo.object";
        } else {
            msgState = "hensyu.kanryo.object";
        }

        GsMessage gsMsg = new GsMessage();
        String textPortal = gsMsg.getMessage(req, "ptl.1");

        cmn999Form.setMessage(msgRes.getMessage(msgState, textPortal));

        req.setAttribute("cmn999Form", cmn999Form);

    }
}

