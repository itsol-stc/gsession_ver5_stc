package jp.groupsession.v2.cir.cir200kn;

import java.sql.Connection;
import java.sql.SQLException;

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
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 管理者設定 ショートメール通知設定確認画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir200knAction extends AbstractCircularSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir200knAction.class);

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }

    /**
     *<br>[機  能] アクションを実行する
     *<br>[解  説]
     *<br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {

        log__.debug("cir200knAction START");

        ActionForward forward = null;
        Cir200knForm thisForm = (Cir200knForm) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("cir200knback")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);
        } else if (cmd.equals("cir200knok")) {
            //OKボタンクリック
            forward = __doOk(map, thisForm, req, res, con);
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
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Cir200knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        if (!__canUseSmlConf(form, req, con)) {
            return getSubmitErrorPage(map, req);
        }
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 遷移元画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map, Cir200knForm form) {

        ActionForward forward = null;

        forward = map.findForward("backInput");
        return forward;
    }

    /**
     * <br>[機  能] OKボタンクリック時の処理
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
    private ActionForward __doOk(ActionMapping map,
                                    Cir200knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {
        if (!__canUseSmlConf(form, req, con)) {
            return getSubmitErrorPage(map, req);
        }
        ActionErrors errors = form.validateCheck(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("backInput");
        }

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //セッションユーザModel

        boolean commitFlg = false;
        try {

            //登録処理
            Cir200knParamModel paramMdl = new Cir200knParamModel();
            paramMdl.setParam(form);
            Cir200knBiz biz = new Cir200knBiz();
            biz.updateCirSmailSetting(paramMdl, con, getSessionUserSid(req));
            paramMdl.setFormData(form);
            commitFlg = true;

        } catch (Exception e) {
            log__.error("ショートメール通知設定登録処理エラー", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        GsMessage gsMsg = new GsMessage();
        String textEdit = gsMsg.getMessage(req, "cmn.change");

        //ログ出力処理
        CirCommonBiz cirBiz = new CirCommonBiz(con);
        cirBiz.outPutLog(map, getRequestModel(req), textEdit, GSConstLog.LEVEL_INFO, textEdit);

        //登録完了画面を設定する。
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
        Cir200knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("cirAdmConf");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";

        GsMessage gsMsg = new GsMessage();
        String textSmailSend = gsMsg.getMessage(req, "cmn.sml.notification.setting");

        cmn999Form.setMessage(msgRes.getMessage(msgState, textSmailSend));

        form.setHiddenParam(cmn999Form);
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());

        req.setAttribute("cmn999Form", cmn999Form);

    }
    /**
     * <br>[機  能] ショートメール設定が利用可能か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return true ショートメール使用可能
     *
     */
    private boolean __canUseSmlConf(Cir200knForm form, HttpServletRequest req, Connection con)
    throws SQLException {

        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con);
        CommonBiz cmnBiz = new CommonBiz();
        //ショートメールは利用可能か判定
        return cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig);
    }
}

