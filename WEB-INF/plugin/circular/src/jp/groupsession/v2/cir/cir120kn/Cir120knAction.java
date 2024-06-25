package jp.groupsession.v2.cir.cir120kn;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cir.AbstractCircularAdminAction;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 管理者設定 手動削除確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir120knAction extends AbstractCircularAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir120knAction.class);

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
        Cir120knForm thisform = (Cir120knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //確定ボタン押下
        if (cmd.equals("delete")) {
            log__.debug("確定ボタン押下");
            forward = __doDelete(map, thisform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("back_syudo_input")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("back_syudo_input");
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, thisform, req, res, con);
        }

        return forward;
    }


    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Cir120knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        Cir120knParamModel paramMdl = new Cir120knParamModel();
        paramMdl.setParam(form);
        Cir120knBiz biz = new Cir120knBiz(con);
        biz.getInitData(getRequestModel(req), paramMdl);
        //年選択、月選択の入力チェックを行う
        biz.updateIgnoreYearMonth(paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }


    /**
     * <br>[機  能] 確定ボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return forward アクションフォワード
     * @throws Exception 実行時例外
     */
    public ActionForward __doDelete(ActionMapping map,
                                     Cir120knForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con) throws Exception {

        ActionForward forward = null;

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            forward = getSubmitErrorPage(map, req);
            return forward;
        }

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDel = gsMsg.getMessage("cmn.delete");

        con.setAutoCommit(false);
        boolean commit = false;

        try {

            Cir120knParamModel paramMdl = new Cir120knParamModel();
            paramMdl.setParam(form);
            Cir120knBiz biz = new Cir120knBiz(con);
            biz.updateSyudoDelSetting(paramMdl, getRequestModel(req));
            paramMdl.setFormData(form);

            commit = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        //ログ出力処理
        CirCommonBiz cirBiz = new CirCommonBiz(con);
        cirBiz.outPutLog(map, reqMdl,
                textDel, GSConstLog.LEVEL_INFO,
                "[value]"
                + form.getCir120JdelKbn()
                + "-" + form.getCir120JYear()
                + "-" + form.getCir120JMonth()
                + ", "
                + form.getCir120SdelKbn()
                + "-" + form.getCir120SYear()
                + "-" + form.getCir120SMonth()
                + ", "
                + form.getCir120DdelKbn()
                + "-" + form.getCir120DYear()
                + "-" + form.getCir120DMonth());
        forward = __doCompDsp(map, form, req, res);
        return forward;
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
                                       Cir120knForm form,
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
        String textCir = gsMsg.getMessage(req, "cir.5");

        urlForward = map.findForward("backToKtool");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", textCir));

        //hiddenパラメータ
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}