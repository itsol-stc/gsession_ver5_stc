package jp.groupsession.v2.ntp.ntp140;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ntp.AbstractNippouMstmntAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 日報 業種一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp140Action extends AbstractNippouMstmntAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp140Action.class);

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

        Ntp140Form thisForm = (Ntp140Form) form;

        if (cmd.equals("backNtp140")) {
            forward = map.findForward("ntp120");
        } else if (cmd.equals("add") || cmd.equals("edit")) {
            forward = map.findForward("ntp141");
        } else if (cmd.equals("upGyomuData")) {
            //上へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_UP);
        } else if (cmd.equals("downGyomuData")) {
            //下へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_DOWN);
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
                                    Ntp140Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        int sessionUserSid = this.getSessionUserModel(req).getUsrsid();
        Ntp140Biz biz = new Ntp140Biz(getRequestModel(req));

        Ntp140ParamModel paramMdl = new Ntp140ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);
        
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }
        
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 上へ/下へボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doSortChange(
        ActionMapping map,
        Ntp140Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int changeKbn) throws Exception {

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }
        
        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {

            Ntp140Biz biz = new Ntp140Biz(getRequestModel(req));

            Ntp140ParamModel paramMdl = new Ntp140ParamModel();
            paramMdl.setParam(form);
            biz.updateSort(con, paramMdl, getSessionUserModel(req).getUsrsid(), changeKbn);
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

        return __doInit(map, form, req, res, con);
    }
}
