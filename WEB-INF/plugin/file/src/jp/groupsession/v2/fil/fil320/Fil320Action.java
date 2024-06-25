package jp.groupsession.v2.fil.fil320;

import java.sql.Connection;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.AbstractFileAdminAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル管理 外貨登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil320Action extends AbstractFileAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil320Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Fil320Form thisForm = (Fil320Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("fil320entry")) {
            //登録ボタンクリック
            forward = __doConfirm(map, thisForm, req, res, con);

        } else if (cmd.equals("fil320back")) {
            //戻るボタンクリック
            forward = map.findForward("fil310");

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
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
    public ActionForward __doInit(ActionMapping map, Fil320Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        con.setAutoCommit(true);

        // トランザクショントークン設定
        this.saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 登録ボタンクリック時処理
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
    public ActionForward __doConfirm(ActionMapping map, Fil320Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //登録処理
        RequestModel reqMdl = getRequestModel(req);
        MlCountMtController cntCon = getCountMtController(req);
        //登録、または更新処理を行う
        Fil320ParamModel paramMdl = new Fil320ParamModel();
        paramMdl.setParam(form);
        Fil320Biz biz = new Fil320Biz(con);

        log__.debug("新規登録");
        biz._doInsert(paramMdl, cntCon);

        paramMdl.setFormData(form);

        //ログ出力
        __outPutAddLog(map, form, req, con, reqMdl);

        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 登録完了画面のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
            ActionMapping map,
            HttpServletRequest req,
            Fil320Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        ActionForward urlForward = null;
        urlForward = map.findForward("fil310");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        MessageResources msgRes = getResources(req);
        String msgState = "touroku.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(
                msgState, getInterMessage(req, "fil.fil310.2")));

        cmn999Form.addHiddenParam("fil310SortRadio", form.getFil310SortRadio());
        form.setHiddenParamFil310(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>[機  能] 外貨登録のログ出力処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param con DB Connection
     * @param reqMdl リクエストモデル
     */
    private void __outPutAddLog(
            ActionMapping map,
            Fil320Form form,
            HttpServletRequest req,
            Connection con,
            RequestModel reqMdl) {

        //ログ出力処理
        FilCommonBiz fcBiz = new FilCommonBiz(reqMdl, con);
        StringBuilder values = new StringBuilder();
        GsMessage gsMsg = new GsMessage();

        //内容セット
        String opCode = gsMsg.getMessage(req, "cmn.entry");

        values.append("[" + gsMsg.getMessage("fil.fil310.1") + "]"
                + NullDefault.getString(form.getFil320GaikaName(), ""));

        fcBiz.outPutLog(opCode, GSConstLog.LEVEL_INFO, values.toString(), map.getType());
    }
}