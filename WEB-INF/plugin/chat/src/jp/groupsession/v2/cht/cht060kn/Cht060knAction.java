package jp.groupsession.v2.cht.cht060kn;

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
import jp.groupsession.v2.cht.AbstractChatAdminAction;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] チャット手動データ削除設定確認 のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht060knAction extends AbstractChatAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht060knAction.class);

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
        Cht060knForm thisForm = (Cht060knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (cmd.equals("kakutei")) {
            //確定ボタンクリック
            forward = __dokakutei(map, thisForm, req, res, con);
        } else if (cmd.equals("backInput")) {
            //管理者設定へ戻る
            forward = map.findForward("cht060");
        } else {
            // 初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht060knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cht060knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
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
    public ActionForward __dokakutei(ActionMapping map, Cht060knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateCht060(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("cht060");
       }

        Cht060knBiz biz = new Cht060knBiz(con);
        RequestModel reqMdl = getRequestModel(req);

        //手動削除を行う
        Cht060knParamModel paramMdl = new Cht060knParamModel();
        paramMdl.setParam(form);


        boolean commit = false;

        try {
            biz.setDelteData(reqMdl, paramMdl, getSessionUserSid(req));

            __doOutLog(map, form, req, res, con);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("チャット 手動削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
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
                                       Cht060knForm form,
                                       HttpServletRequest req) {
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("cht020");
        cmn999Form.setUrlOK(urlForward.getPath());
        //メッセージセット
        String msgState = null;
        msgState = "cmn.kanryo.object";
        cmn999Form.setMessage(
                msgRes.getMessage(msgState, getInterMessage(req, "cmn.manual.delete2")));
        //hiddenパラメータ
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("cht010SelectPartner", form.getCht010SelectPartner());
        cmn999Form.addHiddenParam("cht010SelectKbn", form.getCht010SelectKbn());
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht060knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @throws Exception 実行時例外
     */
    private void __doOutLog(ActionMapping map,
            Cht060knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
            throws Exception {
        // ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        ChtBiz biz = new ChtBiz(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.delete");

        StringBuilder sb = new StringBuilder();
        sb.append(form.getCht060ReferenceYear());
        sb.append(gsMsg.getMessage("cmn.year2"));
        sb.append(form.getCht060ReferenceMonth());
        sb.append(gsMsg.getMessage("cmn.month"));
        sb.append(gsMsg.getMessage("wml.73"));
        biz.outPutLog(
                map, opCode, GSConstLog.LEVEL_INFO, sb.toString(),
                reqMdl, null);
    }

}
