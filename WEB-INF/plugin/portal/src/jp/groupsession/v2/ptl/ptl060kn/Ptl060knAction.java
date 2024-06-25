package jp.groupsession.v2.ptl.ptl060kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
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
 * <br>[機  能] ポータル レイアウト設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl060knAction extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl060knAction.class);

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
        Ptl060knForm ptlForm = (Ptl060knForm) form;

        if (cmd.equals("ptl060knOk")) {
            //確定ボタンリック
            forward = __doDecision(map, ptlForm, req, res, con);

        } else if (cmd.equals("ptl060knBack")) {
            //戻るボタンクリック
            forward = map.findForward("backEditLayout");

        } else {
            //初期表示
            forward = map.getInputForward();
        }

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
     * @throws SQLException SQL実行例外
     * @throws GSException GS用汎実行例外
     */
    private ActionForward __doDecision(ActionMapping map,
        Ptl060knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws SQLException, GSException {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        Ptl060knParamModel paramMdl = new Ptl060knParamModel();
        paramMdl.setParam(form);

        Ptl060knBiz biz = new Ptl060knBiz();
        boolean commit = false;

        try {

            //更新処理
            biz.updateData(paramMdl, con, userSid);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("レイアウト変更処理エラー", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        String ptlName = biz.getPortalName(con, paramMdl);
        paramMdl.setFormData(form);
        //ログ出力処理
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.change");

        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        ptlBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_INFO, "[name]" + ptlName);

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
        Ptl060knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backPortalDetail");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "hensyu.kanryo.object";

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String textLayout = gsMsg.getMessage("ptl.5");

        cmn999Form.setMessage(msgRes.getMessage(msgState, textLayout));

        cmn999Form.addHiddenParam("ptlPortalSid", form.getPtlPortalSid());
        cmn999Form.addHiddenParam("ptl030sortPortal", form.getPtl030sortPortal());

        cmn999Form.addHiddenParam("ptlBackPage", form.getPtlBackPage());
        cmn999Form.addHiddenParam("ptlMainSid", form.getPtlMainSid());

        req.setAttribute("cmn999Form", cmn999Form);

    }
}

