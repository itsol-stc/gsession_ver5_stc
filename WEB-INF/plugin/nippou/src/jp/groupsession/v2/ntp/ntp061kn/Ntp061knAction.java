package jp.groupsession.v2.ntp.ntp061kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp061.Ntp061Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 日報 案件登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp061knAction extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp061knAction.class);

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
     * @see jp.co.sjts.util.struts.AbstractAction
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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Ntp061knForm thisForm = (Ntp061knForm) form;
        log__.debug("*****ntp061ReturnPage = " + thisForm.getNtp061ReturnPage());

        if (GSConstNippou.CMD_EDIT.equals(thisForm.getNtp060ProcMode())) {
            Ntp061Biz biz = new Ntp061Biz(
                    con, getCountMtController(req), getRequestModel(req));
            if (!biz.isEditable(thisForm.getNtp060NanSid())) {
                //編集時,閲覧権限エラー
                    GsMessage gsMsg = new GsMessage(req);
                    return getPermissionError(map, null,
                            map.findForward("ntp060"),
                            gsMsg.getMessage("cmn.edit"),
                            req, res);
            }
        }
        //閲覧モード時
        if (GSConstNippou.CMD_DSP.equals(thisForm.getNtp060ProcMode())) {
            Ntp061Biz biz = new Ntp061Biz(
                    con, getCountMtController(req), getRequestModel(req));
            if (!biz.isViewable(thisForm.getNtp060NanSid())) {
                //閲覧時,閲覧権限エラー
                GsMessage gsMsg = new GsMessage(req);
                return getPermissionError(map, null,
                        map.findForward("ntp060"),
                        gsMsg.getMessage("cmn.reading"),
                        req, res);
            }
        }
        if (cmd.equals("backNtp061kn")) {
            if (GSConstNippou.CMD_DSP.equals(thisForm.getNtp060ProcMode())) {
                forward = map.findForward(thisForm.getNtp061ReturnPage());
            } else {
                forward = map.findForward("ntp061");
            }
        } else if (cmd.equals("backNtp200")) {
            forward = map.findForward("ntp200");
        } else if (cmd.equals("addOkPop")) {
            forward = __doRegistOkPop(map, thisForm, req, res, con, 0);
        } else if (cmd.equals("addOkPopNtp")) {
            forward = __doRegistOkPop(map, thisForm, req, res, con, 1);
        } else {
            log__.debug("*****初期表示");
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
                                    Ntp061knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        BaseUserModel buMdl = getSessionUserModel(req);

        // トランザクショントークン設定
        saveToken(req);

        if (NullDefault.getString(req.getParameter("CMD"), "").equals(GSConstNippou.POP_UP)) {
            //ポップアップ区分
            form.setNtp061PopKbn(1);
        }

        Ntp061knBiz biz = new Ntp061knBiz(
                con, getCountMtController(req), getRequestModel(req));

        Ntp061knParamModel paramMdl = new Ntp061knParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, buMdl, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 登録確認画面でOKボタン押下
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param backKbn 戻り先 0:案件選択画面 1:日報登録画面
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doRegistOkPop(ActionMapping map,
        Ntp061knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int backKbn)
        throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {
            int usrSid = this.getSessionUserModel(req).getUsrsid();
            MlCountMtController cntCon = getCountMtController(req);

            Ntp061Biz biz = new Ntp061Biz(con, cntCon, getRequestModel(req));
            Ntp061knParamModel param = new Ntp061knParamModel();
            param.setParam(form);
            int nanSid = biz.doResistAnkenPop(usrSid, param);
            param.setFormData(form);

            commitFlg = true;

            //完了
            if (backKbn == 0) {
                //案件選択画面へ遷移
                form.setNtp061AddCompFlg(1);
            } else {
                //日報登録画面へ遷移
                form.setNtp061AnkenSid(nanSid);
                form.setNtp061AddCompFlg(2);
            }

            return __doInit(map, form, req, res, con);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }
}
