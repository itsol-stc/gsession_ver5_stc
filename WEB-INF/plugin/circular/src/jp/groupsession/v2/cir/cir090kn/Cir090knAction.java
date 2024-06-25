package jp.groupsession.v2.cir.cir090kn;

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
import jp.groupsession.v2.cir.AbstractCircularAction;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 個人設定 手動削除確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir090knAction extends AbstractCircularAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir090knAction.class);

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
        Cir090knForm thisform = (Cir090knForm) form;

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
                                    Cir090knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        Cir090knParamModel paramMdl = new Cir090knParamModel();
        paramMdl.setParam(form);
        Cir090knBiz biz = new Cir090knBiz(con);
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
     *
     * @param map ActionMapping
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return forward アクションフォワード
    * @throws Exception 実行時例外
     */
    public ActionForward __doDelete(ActionMapping map,
                                     Cir090knForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con) throws Exception {

        ActionForward forward = null;

        //入力チェック
        ActionErrors errors = form.validatecir090(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("back_syudo_input");
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            forward = getSubmitErrorPage(map, req);
            return forward;
        }

        con.setAutoCommit(false);
        boolean commit = false;

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDel = gsMsg.getMessage("cmn.delete");

        try {

            Cir090knParamModel paramMdl = new Cir090knParamModel();
            paramMdl.setParam(form);
            Cir090knBiz biz = new Cir090knBiz(con);
            biz.updateSyudoDelSetting(reqMdl, paramMdl);
            paramMdl.setFormData(form);

            //ログ出力処理
            CirCommonBiz cirBiz = new CirCommonBiz(con);
            cirBiz.outPutLog(map, reqMdl,
                    textDel, GSConstLog.LEVEL_INFO,
                    "[value]"
                    + form.getCir090JdelKbn()
                    + "-" + form.getCir090JYear()
                    + "-" + form.getCir090JMonth()
                    + ", "
                    + form.getCir090SdelKbn()
                    + "-" + form.getCir090SYear()
                    + "-" + form.getCir090SMonth()
                    + ", "
                    + form.getCir090DdelKbn()
                    + "-" + form.getCir090DYear()
                    + "-" + form.getCir090DMonth()
                    );
            commit = true;

            forward = __doCompDsp(map, form, req, res);
            return forward;

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
                                       Cir090knForm form,
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

        urlForward = map.findForward("backToList");
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