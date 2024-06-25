package jp.groupsession.v2.ptl.ptl150kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
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
 * <br>[機  能] ポータル 管理者設定 初期値設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl150knAction extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl150knAction.class);

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

        Ptl150knForm thisForm = (Ptl150knForm) form;

        if (cmd.equals("back150")) {
            //戻るボタンクリック
            forward = map.findForward("back150");

        } else if (cmd.equals("doTouroku")) {
            //確定ボタンクリック
            forward = __doKakutei(map, thisForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
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
                                    Ptl150knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        ActionForward forward = null;

        forward = map.getInputForward();
        return forward;
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
    private ActionForward __doKakutei(ActionMapping map,
        Ptl150knForm form,
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

        RequestModel reqMdl = getRequestModel(req);

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Ptl150knParamModel paramMdl = new Ptl150knParamModel();
        paramMdl.setParam(form);
        Ptl150knBiz biz = new Ptl150knBiz();

        boolean commit = false;
        try {

            //更新処理
            int count = biz.updateData(paramMdl, con);

            if (count < 1) {
                //登録処理
                biz.insertData(paramMdl, con);
            }
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("ポータル初期値設定登録処理エラー", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //ログ出力処理
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = "";
        if (form.getPtlPortalSid() < 1) {
            opCode = gsMsg.getMessage("cmn.entry");
        } else {
            opCode = gsMsg.getMessage("cmn.change");
        }

        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        ptlBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_INFO, "");

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
        Ptl150knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("confMenu");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getPtlPortalSid() < 1) {
            msgState = "touroku.kanryo.object";
        } else {
            msgState = "hensyu.kanryo.object";
        }

        GsMessage gsMsg = new GsMessage();
        String initValue = gsMsg.getMessage(req, "cmn.default.setting");

        cmn999Form.setMessage(msgRes.getMessage(msgState, initValue));

        cmn999Form.addHiddenParam("ptlBackPage", form.getPtlBackPage());
        cmn999Form.addHiddenParam("ptlMainSid", form.getPtlMainSid());

        req.setAttribute("cmn999Form", cmn999Form);

    }
}
