package jp.groupsession.v2.sch.sch250;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sch.AbstractScheduleAction;

/**
 * <br>[機  能] 表示リスト設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch250Action extends AbstractScheduleAction {
    
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch250Action.class);

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
        Sch250Form thisForm = (Sch250Form) form;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        
        if (cmd.equals("sch250add")) {
            //追加ボタンクリック
            forward = map.findForward("addViewList");
        } else if (cmd.equals("sch250back")) {
            //戻るボタンクリック
            forward = map.findForward("sch250back");
        } else if (cmd.equals("sch250up")) {
            //上へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_UP);
        } else if (cmd.equals("sch250down")) {
            //下へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_DOWN);
        } else if (cmd.equals("listDetail")) {
            //名称リンククリック
            forward = map.findForward("addViewList");
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }
        return forward;
    }
    
    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Sch250Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {
        
        con.setAutoCommit(true);
        Sch250ParamModel paramMdl = new Sch250ParamModel();
        paramMdl.setParam(form);
        Sch250Biz biz = new Sch250Biz();
        int usrSid = getRequestModel(req).getSmodel().getUsrsid();
        biz.setInitData(con, paramMdl, usrSid);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        
        saveToken(req);
        
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
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doSortChange(
        ActionMapping map,
        Sch250Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int changeKbn) throws SQLException {

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }
        
        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {
            Sch250Biz biz = new Sch250Biz();

            Sch250ParamModel paramMdl = new Sch250ParamModel();
            paramMdl.setParam(form);
            int usrSid = getRequestModel(req).getSmodel().getUsrsid();
            biz.updateSort(con, paramMdl, usrSid, changeKbn);
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
