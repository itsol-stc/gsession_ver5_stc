package jp.groupsession.v2.cir.cir080kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cir.AbstractCircularAction;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.cir080.Cir080Biz;
import jp.groupsession.v2.cir.model.CirAdelModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 個人設定 自動削除設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir080knAction extends AbstractCircularAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir080knAction.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.groupsession.v2.cir.AbstractCircularAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        ActionForward forward = null;
        Cir080knForm thisForm = (Cir080knForm) form;

        con.setAutoCommit(true);
        Cir080Biz biz = new Cir080Biz(con);
        boolean canUseFlg = biz.canUseAutoDeleteSetting();
        con.setAutoCommit(false);
        if (!canUseFlg) {
            return __cantUseDsp(map, thisForm, req, res);
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //戻るボタン押下
        if (cmd.equals("backToInput")) {
            log__.debug("戻るボタン押下");
            return map.findForward("backToInput");
        //確定ボタン押下
        } else if (cmd.equals("update")) {
            log__.debug("確定ボタン押下");
            forward = __doUpdate(map, thisForm, req, res, con);
        } else {
            log__.debug("初期表示");
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Cir080knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        Cir080knParamModel paramMdl = new Cir080knParamModel();
        paramMdl.setParam(form);
        Cir080knBiz biz = new Cir080knBiz(con);
        //年選択、月選択の入力チェックを行う
        biz.updateIgnoreYearMonth(paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map ActionMapping
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return forward アクションフォワード
     * @throws Exception 実行時例外
     */
    public ActionForward __doUpdate(ActionMapping map,
                                     Cir080knForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con) throws Exception {

        ActionForward forward = null;

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            forward = getSubmitErrorPage(map, req);
            return forward;
        }

        con.setAutoCommit(false);
        boolean commit = false;

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textEdit = gsMsg.getMessage("cmn.change");

        try {

            Cir080knParamModel paramMdl = new Cir080knParamModel();
            paramMdl.setParam(form);

            //回覧板個人設定の更新
            Cir080knBiz biz = new Cir080knBiz(con);
            CirAdelModel delMdl = biz.updateAuteDelSetting(reqMdl, paramMdl);

            //ログ出力処理
            CirCommonBiz cirBiz = new CirCommonBiz(con);
            cirBiz.outPutLog(map, reqMdl,
                    textEdit, GSConstLog.LEVEL_INFO,
                    "[value]"
                    + delMdl.getCadJdelKbn()
                    + "-" + delMdl.getCadJdelYear()
                    + "-" + delMdl.getCadJdelMonth()
                    + ", "
                    + delMdl.getCadSdelKbn()
                    + "-" + delMdl.getCadSdelYear()
                    + "-" + delMdl.getCadSdelMonth()
                    + ", "
                    + delMdl.getCadDdelKbn()
                    + "-" + delMdl.getCadDdelYear()
                    + "-" + delMdl.getCadDdelMonth());

            //完了画面
            forward = __doCompDsp(map, form, req, res);
            commit = true;

            return forward;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

    }

    /**
     * <br>[機  能] 完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map,
                                       Cir080knForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res) {

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        GsMessage gsMsg = new GsMessage();
        String textAutoDel = gsMsg.getMessage(req, "cir.22");

        urlForward = map.findForward("backToList");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage("settei.kanryo.object", textAutoDel));

        //hiddenパラメータ
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] この画面が使用不可の場合のエラー画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward
     */
    private ActionForward __cantUseDsp(ActionMapping map,
                                        Cir080knForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res) {

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("backToList");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.cant.use.cir.auto.del"));

        //hiddenパラメータ
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}