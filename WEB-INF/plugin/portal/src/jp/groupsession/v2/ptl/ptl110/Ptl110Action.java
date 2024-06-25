package jp.groupsession.v2.ptl.ptl110;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ptl.AbstractPortalAdminAction;
import jp.groupsession.v2.ptl.PtlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ポータル ポートレットカテゴリ一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl110Action extends AbstractPortalAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl110Action.class);

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
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Ptl110Form thisForm = (Ptl110Form) form;

        if (cmd.equals("ptl110add")) {
            log__.debug("*** カテゴリ作成。");
            forward = map.findForward("addCategory");

        } else if (cmd.equals("ptl110edit")) {
            log__.debug("*** カテゴリ編集。");
            forward = map.findForward("editCategory");

        } else if (cmd.equals("sortUp")) {
            log__.debug("上へボタンクリック");
            forward = __doSortChange(map, thisForm, req, res, con, Ptl110Biz.SORT_UP);

        } else if (cmd.equals("sortDown")) {
            log__.debug("下へボタンクリック");
            forward = __doSortChange(map, thisForm, req, res, con, Ptl110Biz.SORT_DOWN);

        } else if (cmd.equals("ptl110back")) {
            log__.debug("*** 戻るボタンクリック");
            forward = map.findForward("backPortletList");

        } else {
            log__.debug("*** 初期表示を行います。");
            forward = __doInit(map, thisForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map,
                                  Ptl110Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
                                  throws Exception {

        //ログインユーザ情報を取得
        BaseUserModel userMdl = getSessionUserModel(req);
        if (userMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }
        String cmd = req.getParameter("CMD");

        Ptl110ParamModel paramMdl = new Ptl110ParamModel();
        paramMdl.setParam(form);
        Ptl110Biz biz = new Ptl110Biz();
        biz.init(paramMdl, con, userMdl.getUsrsid(), cmd);
        paramMdl.setFormData(form);
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }
        
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 並び順変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng060Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param sortKbn 処理区分
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doSortChange(ActionMapping map, Ptl110Form form,
                                        HttpServletRequest req, HttpServletResponse res,
                                        Connection con, int sortKbn)
            throws Exception {

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }
        
        boolean commit = false;
        try {
            //ログインユーザ情報を取得
            BaseUserModel userMdl = getSessionUserModel(req);
            if (userMdl == null) {
                throw new GSAuthenticateException("ユーザ情報の取得に失敗");
            }

            Ptl110ParamModel paramMdl = new Ptl110ParamModel();
            paramMdl.setParam(form);
            Ptl110Biz biz = new Ptl110Biz();
            biz.updateSort(con, paramMdl, sortKbn, userMdl.getUsrsid());
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.info("ポートレットカテゴリの並び順更新に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String edit = gsMsg.getMessage("cmn.edit");
        String msg = gsMsg.getMessage("change.sort.order");
        //ログ出力処理
        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        ptlBiz.outPutLog(
                map, reqMdl,
                edit, GSConstLog.LEVEL_INFO, msg);

        return __doInit(map, form, req, res, con);
    }

}
