package jp.groupsession.v2.cht.cht150kn;

import java.sql.Connection;
import java.util.List;

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
import jp.groupsession.v2.cht.AbstractChatAdminAction;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能]  カテゴリ作成編集確認のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht150knAction extends AbstractChatAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht150knAction.class);




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

   protected ActionForward _immigration(ActionMapping map, Cht150knForm form,
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
        Cht150knForm thisForm = (Cht150knForm) form;

        // アクセス制限
        ActionForward forward  = _immigration(map, thisForm, req);
        if (forward != null) {
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        if (cmd.equals("kakutei")) {
            log__.debug("登録追加");
            forward = __doKakutei(map, thisForm, req, res, con);
        } else if (cmd.equals("backInput")) {
            log__.debug("設定画面へ戻る");
            forward = map.findForward("cht150");
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
     * @param form Cht150knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cht150knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Cht150knParamModel paramMdl = new Cht150knParamModel();
        paramMdl.setParam(form);
        Cht150knBiz biz = new Cht150knBiz(con);
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
     * @param form Cht150knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doKakutei(ActionMapping map,
                                  Cht150knForm form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
                                  throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
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
        ActionErrors errors = form.validateCht150(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("cht150");
       }
        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);
        //ログインユーザSID取得
        BaseUserModel smodel = getSessionUserModel(req);
        int usrSid = smodel.getUsrsid();
        //登録または更新処理
        Cht150knBiz biz = new Cht150knBiz(con);
        boolean commit = false;
        try {
            Cht150knParamModel paramMdl = new Cht150knParamModel();
            paramMdl.setParam(form);
            if (form.getCht140ProcMode() == GSConstChat.CHAT_MODE_ADD) {
                biz.insert(paramMdl, usrSid, cntCon);
            } else if (form.getCht140ProcMode() == GSConstChat.CHAT_MODE_EDIT) {
                biz.update(paramMdl, usrSid, cntCon);
            } else {
                return map.findForward("cht140");
            }
            paramMdl.setFormData(form);
            __doOutLog(map, form, req, res, con, biz);
            commit = true;
        } catch (Exception e) {
            log__.error("カテゴリ登録更新に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            } else {
                con.commit();
            }
        }
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
                                         Cht150knForm form,
                                         HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;
        forwardOk = map.findForward("cht140");
        cmn999Form.setUrlOK(forwardOk.getPath());
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.category");
        if (form.getCht140ProcMode() == GSConstChat.CHAT_MODE_ADD) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object", msg));
        } else  {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object", msg));
        }
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("cht010SelectPartner", form.getCht010SelectPartner());
        cmn999Form.addHiddenParam("cht010SelectKbn", form.getCht010SelectKbn());

        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }


    /**
     * <br>[機  能] ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht150knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param biz Cht150knBiz
     * @throws Exception 実行時例外
     */
    private void __doOutLog(ActionMapping map,
            Cht150knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            Cht150knBiz biz)
            throws Exception {
        // ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        ChtBiz chtBiz = new ChtBiz(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.change");
        if (form.getCht140ProcMode() == GSConstChat.CHAT_MODE_ADD) {
            opCode = gsMsg.getMessage("cmn.entry");
        } else {
            opCode = gsMsg.getMessage("cmn.edit");
        }
        StringBuilder sb = new StringBuilder();
        // カテゴリ名
        sb.append("[" + gsMsg.getMessage("cht.01")
                + gsMsg.getMessage("cmn.category.name") + "] ");
        sb.append(form.getCht150CategoryName());
        sb.append(System.getProperty("line.separator"));
        // グループ名
        String[] sidList = form.getCht150ChtGroupSid();
        if (sidList != null && sidList.length >= 1) {
            sb.append("[" + gsMsg.getMessage("cht.cht150.01") + "] ");
            List<String> groupList = biz.getGroupList(sidList);
            StringBuilder str = new StringBuilder();
            for (String name: groupList) {
                if (str.length() > 0) {
                    str.append(",");
                }
                str.append(name);
            }
            sb.append(str.toString());
            sb.append(System.getProperty("line.separator"));
        }
        chtBiz.outPutLog(
                map, opCode, GSConstLog.LEVEL_INFO, sb.toString(),
                reqMdl, null);
    }

}
