package jp.groupsession.v2.ptl.ptl030;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ptl.AbstractPortalAdminAction;
import jp.groupsession.v2.ptl.PtlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ポータル ポータル管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl030Action extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl030Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
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
        log__.debug("START");

        ActionForward forward = null;
        Ptl030Form ptlForm = (Ptl030Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("ptl030up")) {
            //表示順上へボタンクリック
            forward = __doUpdateSortUp(req, res, ptlForm, con, map);

        } else if (cmd.equals("ptl030down")) {
            //表示順下へボタンクリック
            forward = __doUpdateSortDown(req, res, ptlForm, con, map);

        } else if (cmd.equals("ptl030add")) {
            //追加ボタンクリック
            forward = map.findForward("addPortal");

        } else if (cmd.equals("confMenu")) {
            //戻るボタンクリック
            forward = map.findForward("confMenu");

        } else if (cmd.equals("ptl030editPortal")) {
            //タイトルクリック
            forward = map.findForward("editPortal");

        } else if (cmd.equals("portletManager")) {
            //ポートレット管理ボタンクリック
            forward = map.findForward("portletManager");

        } else {
            //初期表示
            forward = __doInit(map, ptlForm, req, res, con);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Ptl030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        Ptl030ParamModel paramMdl = new Ptl030ParamModel();
        paramMdl.setParam(form);
        Ptl030Biz biz = new Ptl030Biz();
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 上へボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param form フォーム
     * @param con コネクション
     * @param map マッピング
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doUpdateSortUp(HttpServletRequest req, HttpServletResponse res,
            Ptl030Form form, Connection con, ActionMapping map)
        throws SQLException {

        ActionErrors errors = new ActionErrors();
        errors = form.validateCheckSort(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return getSubmitErrorPage(map, req);
        }
        
        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);
        boolean commitFlg = false;
        Ptl030Biz biz = new Ptl030Biz();

        try {

            Ptl030ParamModel paramMdl = new Ptl030ParamModel();
            paramMdl.setParam(form);
            biz.updateSort(paramMdl, con, GSConst.SORT_UP);
            paramMdl.setFormData(form);
            commitFlg = true;

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

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textEdit = gsMsg.getMessage("cmn.change");
        String textEditSort = gsMsg.getMessage("change.sort.order");

        //ログ出力処理
        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        ptlBiz.outPutLog(map, reqMdl,
                textEdit, GSConstLog.LEVEL_INFO, textEditSort);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 下へボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param form フォーム
     * @param con コネクション
     * @param map マッピング
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doUpdateSortDown(HttpServletRequest req, HttpServletResponse res,
            Ptl030Form form, Connection con, ActionMapping map)
        throws SQLException {

        ActionErrors errors = new ActionErrors();
        errors = form.validateCheckSort(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        
        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);
        boolean commitFlg = false;
        Ptl030Biz biz = new Ptl030Biz();

        try {

            Ptl030ParamModel paramMdl = new Ptl030ParamModel();
            paramMdl.setParam(form);
            biz.updateSort(paramMdl, con, GSConst.SORT_DOWN);
            paramMdl.setFormData(form);
            commitFlg = true;

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
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textEdit = gsMsg.getMessage("cmn.change");
        String textEditSort = gsMsg.getMessage("change.sort.order");

        //ログ出力処理
        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        ptlBiz.outPutLog(map, reqMdl,
                textEdit, GSConstLog.LEVEL_INFO, textEditSort);

        return __doInit(map, form, req, res, con);
    }
}

