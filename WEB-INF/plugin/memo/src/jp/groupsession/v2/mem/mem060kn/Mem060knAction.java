package jp.groupsession.v2.mem.mem060kn;

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
import jp.groupsession.v2.mem.AbstractMemoAction;
import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.biz.MemCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メモ帳 個人設定 ラベル登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem060knAction extends AbstractMemoAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Mem060knAction.class);

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
        Mem060knForm thisForm = (Mem060knForm) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        //ユーザSIDを取得
        int usrSid = getSessionUserSid(req);

        //ラベルの存在チェック
        Mem060knParamModel paramMdl = new Mem060knParamModel();
        paramMdl.setParam(form);
        Mem060knBiz biz = new Mem060knBiz();
        boolean result = biz._existLabelData(con, paramMdl, usrSid);
        if (!result) {
            return getSubmitErrorPage(map, req);
        }

        if (cmd.equals("mem060knNext")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("mem060knBack")) {
            //戻るボタンクリック
            forward = map.findForward("mem060knBack");

        } else {
            //初期表示
            forward = map.getInputForward();
        }

        return forward;
    }

    /**
     * <br>[機  能] 確定ボタンクリック時処理
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
    public ActionForward __doDecision(ActionMapping map, Mem060knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        //登録処理
        RequestModel reqMdl = getRequestModel(req);
        MlCountMtController cntCon = getCountMtController(req);
        //登録、または更新処理を行う
        Mem060knParamModel paramMdl = new Mem060knParamModel();
        paramMdl.setParam(form);
        Mem060knBiz biz = new Mem060knBiz(con);

        //処理モード(新規登録）
        if (paramMdl.getMemLabelCmdMode() == GSConstMemo.CMDMODE_ADD) {
            //新規登録
            log__.debug("新規登録");
            biz._doInsert(paramMdl, userSid, cntCon);

            //編集
        } else if (paramMdl.getMemLabelCmdMode() == GSConstMemo.CMDMODE_EDIT) {
            log__.debug("編集");
            biz._doUpdate(paramMdl, userSid);
        }
        paramMdl.setFormData(form);

        //ログ出力
        outPutCULog(map, form, req, con, reqMdl);

        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 個人設定ラベル登録(更新)のログ出力処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param con DB Connection
     * @param reqMdl リクエストモデル
     */
    private void outPutCULog(
            ActionMapping map,
            Mem060knForm form,
            HttpServletRequest req,
            Connection con,
            RequestModel reqMdl) {

        //ログ出力処理
        MemCommonBiz memBiz = new MemCommonBiz(con, reqMdl);
        StringBuilder values = new StringBuilder();
        GsMessage gsMsg = new GsMessage();

        //内容セット
        String opCode = "";
        if (form.getMemLabelCmdMode() == GSConstMemo.CMDMODE_ADD) {
            opCode = gsMsg.getMessage(req, "cmn.entry");
        } else if (form.getMemLabelCmdMode() == GSConstMemo.CMDMODE_EDIT) {
            opCode = gsMsg.getMessage(req, "cmn.change");
        }

        values.append("[" + gsMsg.getMessage("cmn.label.name") + "]"
                + NullDefault.getString(form.getMem060LabelName(), ""));

        memBiz.outPutLog(map, req, opCode, GSConstLog.LEVEL_INFO, values.toString());
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
            Mem060knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("mem050");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getMemLabelCmdMode() == GSConstMemo.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getMemLabelCmdMode() == GSConstMemo.CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "cmn.label")));
        cmn999Form.addHiddenParam("mem050SortRadio", form.getMem050SortRadio());

        req.setAttribute("cmn999Form", cmn999Form);
    }
}