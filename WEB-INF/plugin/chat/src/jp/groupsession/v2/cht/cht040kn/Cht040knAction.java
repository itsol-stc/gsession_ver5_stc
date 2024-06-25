package jp.groupsession.v2.cht.cht040kn;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
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
import jp.groupsession.v2.cht.biz.ChtMemberBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 *
 * <br>[機  能] チャット 基本設定確認 のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */

public class Cht040knAction extends AbstractChatAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht040knAction.class);

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
        Cht040knForm thisForm = (Cht040knForm) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        if (cmd.equals("kakutei")) {
            log__.debug("確定ボタンクリック");
            forward = __doKakutei(map, thisForm, req, res, con);
        } else if (cmd.equals("backInput")) {
            log__.debug("設定画面に戻る");
            forward = map.findForward("cht040");
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
     * @param form Cht040knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cht040knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        Cht040knParamModel paramMdl = new Cht040knParamModel();
        paramMdl.setParam(form);
        Cht040knBiz biz = new Cht040knBiz(con, getRequestModel(req));
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht040knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doKakutei(ActionMapping map,
                                  Cht040knForm form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
                                  throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        //入力チェック
        ActionErrors errors = form.validateCht040(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("cht040");
       }
        //登録または更新処理
        Cht040knBiz biz = new Cht040knBiz(con, getRequestModel(req));
        boolean commit = false;
        try {
            log__.info("START");
            Cht040knParamModel paramMdl = new Cht040knParamModel();
            paramMdl.setParam(form);
            biz.entryAdmConf(paramMdl, getSessionUserSid(req));
            biz.entryTargetConf(paramMdl);
            paramMdl.setFormData(form);
            __doOutLog(map, form, req, res, con);
            commit = true;
            log__.info("END");
        }  catch (Exception e) {
            log__.error("チャット管理者設定基本設定の登録に失敗", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }
        return __setKanryoDsp(map, form, req);
    }


    /**
     * <br>[機  能] 完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(ActionMapping map,
                                       Cht040knForm form,
                                       HttpServletRequest req) {
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        //完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        GsMessage gsMsg = new GsMessage();
        String textBaseConf = gsMsg.getMessage(req, "cmn.preferences");
        urlForward = map.findForward("cht020");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage("touroku.kanryo.object", textBaseConf));
        //hiddenパラメータ
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("cht010SelectPartner", form.getCht010SelectPartner());
        cmn999Form.addHiddenParam("cht010SelectKbn", form.getCht010SelectKbn());
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht040knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @throws Exception 実行時例外
     */
    private void __doOutLog(ActionMapping map,
            Cht040knForm form,
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
        // ユーザ間チャット
        sb.append("[" + gsMsg.getMessage("cht.cht040.01") + "] ");
        if (form.getCht040BetweenUsers() == GSConstChat.PERMIT_BETWEEN_USERS) {
            sb.append(gsMsg.getMessage("cmn.not.limit"));
        } else {
            sb.append(gsMsg.getMessage("cmn.do.limit"));
        }
        sb.append(System.getProperty("line.separator"));
        // グループの作成
        boolean limitFlg = false;
        sb.append("[" + gsMsg.getMessage("cht.cht040.02") + "] ");
        if (form.getCht040CreateGroup() == GSConstChat.PERMIT_CREATE_GROUP) {
            sb.append(gsMsg.getMessage("cmn.not.limit"));
        } else {
            sb.append(gsMsg.getMessage("cmn.do.limit"));
            limitFlg = true;
        }
        sb.append(System.getProperty("line.separator"));
        // 制限対象
        if (limitFlg) {
            if (form.getCht040HowToLimit() == GSConstChat.TARGET_LIMIT) {
                sb.append(gsMsg.getMessage("cmn.access.permit"));
            } else {
                sb.append(gsMsg.getMessage("cmn.access.limit"));

            }
            sb.append(System.getProperty("line.separator"));

            // グループ、ユーザ名取得
            Cht040knBiz biz040kn = new Cht040knBiz(con, reqMdl);
            Cht040knParamModel paramMdl = new Cht040knParamModel();
            paramMdl.setFormData(form);
            biz040kn.setInitData(paramMdl);

            UserGroupSelectModel useSelectMdl = paramMdl.getCht040knTargetUser();
            HashMap<String, String[]> useListMap = useSelectMdl.getSelected();
            String[] useList = useListMap.get(GSConstChat.GROUP_USE_LIMIT);
            ChtMemberBiz memberBiz = new ChtMemberBiz(con);
            List<UsrLabelValueBean> labelList = memberBiz.getMemberLabel(useList);
            List<String> grpList = new ArrayList<String>();
            List<String> usrList = new ArrayList<String>();
            for (UsrLabelValueBean label:labelList) {
                if (label.getValue().startsWith(GSConstChat.GROUP_HEADSTR)) {
                    grpList.add(label.getLabel());
                } else {
                    usrList.add(label.getLabel());
                }
            }
            // グループ
            if (!grpList.isEmpty()) {
                StringBuilder str = new StringBuilder();
                sb.append("  [" + gsMsg.getMessage("cmn.group") + "]");
                for (String name: grpList) {
                     if (str.length() > 0) {
                         str.append(",");
                     }
                     str.append(name);
                }
                sb.append(str.toString());
                sb.append(System.getProperty("line.separator"));
            }
            // ユーザ
            if (!usrList.isEmpty()) {
                StringBuilder str = new StringBuilder();
                sb.append("  [" + gsMsg.getMessage("cmn.user") + "]");
                for (String name: usrList) {
                    if (str.length() > 0) {
                        str.append(",");
                    }
                    str.append(name);
                }
                sb.append(str.toString());
                sb.append(System.getProperty("line.separator"));
            }

         }
         // 既読表示
         sb.append("[" + gsMsg.getMessage("cht.cht040.03") + "] ");
         if (form.getCht040AlreadyRead() == GSConstChat.KIDOKU_DISP) {
             sb.append(gsMsg.getMessage("cmn.display.ok"));
         } else {
             sb.append(gsMsg.getMessage("cmn.dont.show"));
         }

         biz.outPutLog(
                 map, opCode, GSConstLog.LEVEL_INFO, sb.toString(),
                 reqMdl, null);
    }

}
