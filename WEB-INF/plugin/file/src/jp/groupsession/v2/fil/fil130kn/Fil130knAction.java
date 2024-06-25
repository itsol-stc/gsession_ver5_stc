package jp.groupsession.v2.fil.fil130kn;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 個人設定 ショートメール通知設定確認画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil130knAction extends AbstractFileAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil130knAction.class);

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

        log__.debug("fil130knAction START");

        ActionForward forward = null;
        Fil130knForm thisForm = (Fil130knForm) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil130knback")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("fil130knok")) {
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
                                    Fil130knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

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
    private ActionForward __doBack(ActionMapping map, Fil130knForm form) {

        ActionForward forward = null;

        forward = map.findForward("fil130");
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
                                    Fil130knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //セッションユーザModel
        BaseUserModel buMdl = getSessionUserModel(req);

        Fil130knBiz biz = new Fil130knBiz(con);

        boolean commitFlg = false;
        Fil130knParamModel paramMdl = new Fil130knParamModel();
        paramMdl.setParam(form);
        try {

            //登録処理
            biz.registerData(paramMdl, buMdl);
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
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        String value = "";
        value += "[" + gsMsg.getMessage("shortmail.notification") + "] ";
        String[] notify = {
                gsMsg.getMessage("cmn.dont.notify"),
                gsMsg.getMessage("cmn.notify")
        };
        value += notify[Integer.parseInt(paramMdl.getFil130SmailSend())];
        filBiz.outPutLog(textEdit, GSConstLog.LEVEL_INFO, value, map.getType());

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
        Fil130knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("fil110");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";

        GsMessage gsMsg = new GsMessage();
        String textSmailSend = gsMsg.getMessage(req, "cmn.sml.notification.setting");

        cmn999Form.setMessage(msgRes.getMessage(msgState, textSmailSend));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
        cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
        cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
        cmn999Form.addHiddenParam("fil040SelectDel", form.getFil040SelectDel());
        cmn999Form.addHiddenParam("fil010SelectDelLink", form.getFil010SelectDelLink());

        req.setAttribute("cmn999Form", cmn999Form);

    }
}

