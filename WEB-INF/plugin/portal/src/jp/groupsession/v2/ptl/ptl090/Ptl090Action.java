package jp.groupsession.v2.ptl.ptl090;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
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
 * <br>[機  能] ポータル ポートレット管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl090Action extends AbstractPortalAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl090Action.class);

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
        Ptl090Form thisForm = (Ptl090Form) form;
        if (cmd.equals("confMenu")) {
            log__.debug("*** 戻るボタン押下");
            forward = map.findForward("confMenu");

        } else if (cmd.equals("sortUp")) {
            log__.debug("上へボタンクリック");
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_UP);

        } else if (cmd.equals("sortDown")) {
            log__.debug("下へボタンクリック");
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_DOWN);

        } else if (cmd.equals("portalManager")) {
            log__.debug("*** ポータル管理画面へ");
            forward = map.findForward("portalManager");

        } else if (cmd.equals("ptl090addPortlet")) {
            log__.debug("*** ポートレット登録");
            forward = map.findForward("addPortlet");

        } else if (cmd.equals("ptl090categoryList")) {
            log__.debug("*** ポートレットカテゴリ一覧");
            forward = map.findForward("categoryList");

        } else if (cmd.equals("ptl090addCategory")) {
            log__.debug("*** ポートレットカテゴリ登録");
            forward = map.findForward("addCategory");

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
     * @param form Rng060Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Ptl090Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);
        //検索条件保持
        form.saveSearchParm();

        Ptl090ParamModel paramMdl = new Ptl090ParamModel();
        paramMdl.setParam(form);
        Ptl090Biz biz = new Ptl090Biz(con);
        biz.initDsp(paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
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
    public ActionForward __doSortChange(ActionMapping map, Ptl090Form form,
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
            RequestModel reqMdl = getRequestModel(req);
            Ptl090ParamModel paramMdl = new Ptl090ParamModel();
            paramMdl.setParam(form);
            Ptl090Biz biz = new Ptl090Biz(con);
            biz.updateSort(paramMdl, reqMdl, sortKbn, userMdl.getUsrsid());
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.info("ポートレットの並び順更新に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String edit = gsMsg.getMessage("cmn.edit");
        String sort = gsMsg.getMessage("change.sort.order");

        //ログ出力処理
        PtlCommonBiz rngBiz = new PtlCommonBiz(con);
        rngBiz.outPutLog(
                map, reqMdl,
                edit, GSConstLog.LEVEL_INFO, sort);

        return __doInit(map, form, req, res, con);
    }
}
