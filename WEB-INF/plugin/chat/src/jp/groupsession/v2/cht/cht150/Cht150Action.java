package jp.groupsession.v2.cht.cht150;

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
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cht.AbstractChatAdminAction;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] チャット カテゴリ作成編集 のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht150Action extends AbstractChatAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht150Action.class);

    /**
    *
    * <br>[機  能] アクション実行前の事前処理 アクセス制限を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param map ActionMapping
    * @param form ActionForm
    * @param req HttpServletRequest
    * @return ActionForward
    * @throws Exception 実行時例外
    */

   protected ActionForward _immigration(ActionMapping map, Cht150Form form,
           HttpServletRequest req) throws Exception {
       if (form.getCht140ProcMode() < GSConstChat.CHAT_MODE_ADD
               || form.getCht140ProcMode() > GSConstChat.CHAT_MODE_EDIT) {
           return getSubmitErrorPage(map, req);
       }
       return null;
   }

    /**
     * <br>アクション実行
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
        Cht150Form thisForm = (Cht150Form) form;
        // アクセス制限
        ActionForward forward  = _immigration(map, thisForm, req);
        if (forward != null) {
            return forward;
        }
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        if (cmd.equals("ok")) {
            log__.debug("OKボタンクリック");
            forward = __doOk(map, thisForm, req, res, con);
        } else if (cmd.equals("backToInput")) {
            log__.debug("カテゴリ管理");
            forward = map.findForward("cht140");
        } else if (cmd.equals("delete")) {
            log__.debug("削除ボタンクリック。");
            forward = __setDeleteDsp(map, req, thisForm, con, res);
        } else if (cmd.equals("delexe")) {
            log__.debug("削除確認OK。");
            forward = __doDelete(map, thisForm, req, res, con);
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht140Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cht150Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Cht150ParamModel paramMdl = new Cht150ParamModel();
        paramMdl.setParam(form);
        Cht150Biz biz = new Cht150Biz(con);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht150Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doOk(ActionMapping map,
                                Cht150Form form,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Connection con)
                                throws Exception {

        // 存在チェック
        if (form.getCht140ProcMode() == GSConstChat.CHAT_MODE_EDIT) {
            ActionErrors errors = new ActionErrors();
            errors = form.validateExistCategoryCht150(getRequestModel(req), con, form);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return getSubmitErrorPage(map, req);
            }
        }
        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCht150(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        // トランザクショントークン設定
        saveToken(req);
        return map.findForward("cht150kn");
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
                                          Cht150Form form,
                                          Connection con,
                                          HttpServletResponse res) throws Exception {

        //削除チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateExistCategoryCht150(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return getSubmitErrorPage(map, req);
        }

        GsMessage gsMsg = new GsMessage(req);

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("cht150");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=delexe");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("cht150");
        cmn999Form.setUrlCancel(forwardCancel.getPath());
        //メッセージ
        Cht150Biz biz = new Cht150Biz(con);
        String categoryName = biz.getCategoryName(form.getCht140CategoryLink());
        MessageResources msgRes = getResources(req);
        String msg = "";
        String chat = gsMsg.getMessage("cmn.category");
        msg = msgRes.getMessage("sakujo.kakunin.list", chat,
                StringUtilHtml.transToHTmlPlusAmparsant(categoryName));
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("cht010SelectPartner", form.getCht010SelectPartner());
        cmn999Form.addHiddenParam("cht010SelectKbn", form.getCht010SelectKbn());
        cmn999Form.addHiddenParam("cht140CategoryLink", form.getCht140CategoryLink());
        cmn999Form.addHiddenParam("cht140ProcMode", form.getCht140ProcMode());
        cmn999Form.addHiddenParam("cht150CategoryName", form.getCht150CategoryName());
        cmn999Form.addHiddenParam("cht150InitFlg", form.getCht150InitFlg());
        cmn999Form.addHiddenParam("cht150CategorySid", form.getCht150CategorySid());

        String[] selectSid = form.getCht150ChtGroupSid();
        if (selectSid != null) {
            for (String sid:selectSid) {
                cmn999Form.addHiddenParam("cht150ChtGroupSid", sid);
            }
        }

        req.setAttribute("cmn999Form", cmn999Form);
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
        Cht150Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateExistCategoryCht150(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return getSubmitErrorPage(map, req);
        }

        ActionForward forward = null;
        boolean commit = false;
        try {
            Cht150ParamModel paramMdl = new Cht150ParamModel();
            paramMdl.setParam(form);
            Cht150Biz biz = new Cht150Biz(con);
            // カテゴリ削除
            String categoryName = biz.getCategoryName(form.getCht140CategoryLink());
            int count = biz.deleteCategory(paramMdl,
                             getSessionUserSid(req));
            if (count == 0) {
                // 削除失敗
                return getSubmitErrorPage(map, req);
            }
            paramMdl.setFormData(form);
            forward = __setCompDsp(map, req, form);
            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String msg = gsMsg.getMessage("cmn.delete");
            String opCode = msg;

            //ログ出力処理
            ChtBiz chtBiz = new ChtBiz(con);
            String logCategoryName = "[" + gsMsg.getMessage("cmn.category") + "] " + categoryName;
            chtBiz.outPutLog(
                    map,
                    opCode,
                    GSConstLog.LEVEL_INFO,
                    logCategoryName,
                    reqMdl,
                    null);
            commit = true;
        } catch (Exception e) {
            log__.error("カテゴリ削除に失敗", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }
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
                                        Cht150Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;
        forwardOk = map.findForward("cht140");
        cmn999Form.setUrlOK(forwardOk.getPath());
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.category");
        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", msg));
        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("cht010SelectPartner", form.getCht010SelectPartner());
        cmn999Form.addHiddenParam("cht010SelectKbn", form.getCht010SelectKbn());
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

}
