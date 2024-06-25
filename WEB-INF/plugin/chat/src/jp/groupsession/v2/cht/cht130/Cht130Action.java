package jp.groupsession.v2.cht.cht130;

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
import jp.groupsession.v2.cht.AbstractChatAction;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] チャット 表示設定 のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht130Action extends AbstractChatAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht130Action.class);

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
        ActionForward forward = null;
        Cht130Form thisForm = (Cht130Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (cmd.equals("ok")) {
            log__.debug("OKボタンクリック");
            forward = __doOk(map, thisForm, req, res, con);
        } else if (cmd.equals("backConfMenu")) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward("cht030");
        } else {
            //初期表示
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
     * @param form Cht120Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cht130Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        Cht130ParamModel paramMdl = new Cht130ParamModel();
        paramMdl.setParam(form);
        Cht130Biz biz = new Cht130Biz(con, getRequestModel(req));
        biz.setInitData(paramMdl, getSessionUserSid(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht130Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doOk(ActionMapping map,
                                Cht130Form form,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Connection con)
                                throws Exception {
        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCht130(getRequestModel(req), con, form, getSessionUserSid(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
      //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        boolean commit = false;
        try {
            Cht130ParamModel paramMdl = new Cht130ParamModel();
            paramMdl.setParam(form);
            Cht130Biz biz = new Cht130Biz(con, getRequestModel(req));
            biz.updateChatUserConf(paramMdl, userSid);
            paramMdl.setFormData(form);
            //ログ出力処理
            __doOutLog(map, form, req, res, con);
            commit = true;
        } catch (Exception e) {
            log__.error("表示設定更新エラー", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }
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
        Cht130Form form) {
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("cht030");
        cmn999Form.setUrlOK(urlForward.getPath());
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cht.cht030.03");
        //メッセージセット
        String msgState = "hensyu.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, msg));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("cht010SelectPartner", form.getCht010SelectPartner());
        cmn999Form.addHiddenParam("cht010SelectKbn", form.getCht010SelectKbn());
        req.setAttribute("cmn999Form", cmn999Form);

    }


    /**
     * <br>[機  能] ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht130Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @throws Exception 実行時例外
     */
    private void __doOutLog(ActionMapping map,
            Cht130Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
            throws Exception {
        // ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        ChtBiz biz = new ChtBiz(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.change");
        StringBuilder sb = new StringBuilder();
        // 受信時プッシュ通知
        sb.append("[" + gsMsg.getMessage("cmn.default") + gsMsg.getMessage("cmn.show") + "] ");
        // 前回開いていたチャット
        if (form.getCht130DefFlg() == GSConstChat.CHAT_DSP_DEFAULT) {
            sb.append(gsMsg.getMessage("cht.cht130.01"));
        } else if (form.getCht130DefFlg() == GSConstChat.CHAT_DSP_USER) {
            sb.append(gsMsg.getMessage("cmn.user") + ":");
            CmnUsrmInfDao dao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel mdl = dao.select(form.getCht130SelectSid());
            sb.append(mdl.getUsiSei() + mdl.getUsiMei());
        } else if (form.getCht130DefFlg() == GSConstChat.CHAT_DSP_CHATGROUP) {
            sb.append(gsMsg.getMessage("cmn.group") + ":");
            ChtGroupInfDao dao = new ChtGroupInfDao(con);
            String name = dao.select(form.getCht130SelectSid()).getCgiName();
            sb.append(name);
        }
        biz.outPutLog(
                map, opCode, GSConstLog.LEVEL_INFO, sb.toString(),
                reqMdl, null);
    }
}
