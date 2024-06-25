package jp.groupsession.v2.cir.cir240kn;

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
import jp.groupsession.v2.cir.AbstractCircularSubAction;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ラベル登録編集確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir240knAction extends AbstractCircularSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir240knAction.class);

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
        Cir240knForm thisForm = (Cir240knForm) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        // アクセス権限チェック
        CommonBiz cmnBiz = new CommonBiz();
        CirCommonBiz cirBiz = new CirCommonBiz();
        boolean adminFlg = cmnBiz.isPluginAdmin(
                con,
                getSessionUserModel(req),
                getPluginId());
        if (adminFlg) {
            CirAccountDao dao = new CirAccountDao(con);
            if (!dao.selectExistAccount(thisForm.getCirAccountSid())) {
                return getSubmitErrorPage(map, req);
            }
        } else {
            if (!cirBiz.canUseAccount(
                    con, getSessionUserSid(req), thisForm.getCirAccountSid())) {
                return getSubmitErrorPage(map, req);
            }
        }
        // 編集時ラベル存在チェック
        if (thisForm.getCir230LabelCmdMode() == GSConstCircular.CMDMODE_EDIT) {
            if (!cirBiz.checkExistLabel(con,
                    thisForm.getCir230EditLabelId(),
                    thisForm.getCirAccountSid())) {
                return getSubmitErrorPage(map, req);
            }
        }

        if (cmd.equals("decision")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("backInput")) {
            //戻るボタンクリック
            forward = map.findForward("backInput");

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
    public ActionForward __doInit(ActionMapping map, Cir240knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Cir240knParamModel paramMdl = new Cir240knParamModel();
        paramMdl.setParam(form);
        Cir240knBiz biz = new Cir240knBiz(con);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
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
    public ActionForward __doDecision(ActionMapping map, Cir240knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validate240Check(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("backInput");
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //登録処理
        MlCountMtController cntCon = getCountMtController(req);
        //登録、または更新処理を行う
        Cir240knParamModel paramMdl = new Cir240knParamModel();
        paramMdl.setParam(form);
        Cir240knBiz biz = new Cir240knBiz(con);
        biz.doAddEdit(paramMdl, userSid, cntCon);
        paramMdl.setFormData(form);

        //ログ出力処理
        CirCommonBiz cirBiz = new CirCommonBiz(con);
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = "";
        if (form.getCir230LabelCmdMode() == GSConstCircular.CMDMODE_ADD) {
            opCode = getInterMessage(req, "cmn.entry");
        } else if (form.getCir230LabelCmdMode() == GSConstCircular.CMDMODE_EDIT) {
            opCode = getInterMessage(req, "cmn.change");
        }
        StringBuilder sb = new StringBuilder();
        //アカウント名
        String accountName =  cirBiz.getAccountName(paramMdl.getCirAccountSid());
        sb.append("[" + gsMsg.getMessage("wml.96") + "] ");
        sb.append(accountName);
        sb.append(System.getProperty("line.separator"));
        //ラベル名
        sb.append("[" + gsMsg.getMessage("cmn.label.name") + "] ");
        sb.append(form.getCir240LabelName());
        //設定区分
        int logFlg = GSConstCircular.CIR_LOG_FLG_PREF;
        if (form.getCir230DspKbn() == GSConstCircular.DSPKBN_ADMIN) {
            logFlg = GSConstCircular.CIR_LOG_FLG_ADMIN;
        }
        cirBiz.outPutLog(map, reqMdl, opCode,
                GSConstLog.LEVEL_INFO, sb.toString(), logFlg);

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
        Cir240knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("confLabel");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getCir230LabelCmdMode() == GSConstCircular.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getCir230LabelCmdMode() == GSConstCircular.CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "cmn.label")));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setHiddenParam(cmn999Form);
        form.setHiddenParamCir230(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

    }
}
