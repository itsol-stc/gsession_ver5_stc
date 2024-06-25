package jp.groupsession.v2.ntp.ntp086;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ntp.AbstractNippouAdminAction;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 日報 日報テンプレート一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp086Action extends AbstractNippouAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp086Action.class);

    /**
     * <br>アクション実行
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
        ActionForward forward = null;

        Ntp086Form ntpForm = (Ntp086Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("ntp086add")) {
            //追加ボタンクリック
            forward = map.findForward("ntp086add");
        } else if (cmd.equals("edit")) {
            //テンプレートリンククリック
            forward = map.findForward("ntp086edit");
        } else if (cmd.equals("ntp086back")) {
            //戻るボタンクリック
            forward = __doBack(map, ntpForm, req, res, con);
        } else if (cmd.equals("upTemplateData")) {
            //上へボタンクリック
            forward = __doSortChange(map, ntpForm, req, res, con, GSConst.SORT_UP);
        } else if (cmd.equals("downTemplateData")) {
            //下へボタンクリック
            forward = __doSortChange(map, ntpForm, req, res, con, GSConst.SORT_DOWN);
        } else if (cmd.equals("getUsrList")) {
            //ユーザ一覧取得
            __getUsrList(map, ntpForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, ntpForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Ntp086Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("初期表示");
        Ntp086Biz biz = new Ntp086Biz(con, getRequestModel(req));
        Ntp086ParamModel paramMdl = new Ntp086ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Ntp086Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");
        return map.findForward("ktool");
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
        Ntp086Form form,
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

            Ntp086Biz biz = new Ntp086Biz(con, getRequestModel(req));

            Ntp086ParamModel paramMdl = new Ntp086ParamModel();
            paramMdl.setParam(form);
            biz.updateSort(
                    con, paramMdl, getSessionUserModel(req).getUsrsid(), changeKbn);
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

    /**
     * <br>ユーザ一覧取得
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private void __getUsrList(
            ActionMapping map,
            Ntp086Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        JSONObject jsonData = null;
        Ntp086Biz biz = new Ntp086Biz(con, getRequestModel(req));

        //テンプレートSID取得
        String tmpSid = NullDefault.getString(req.getParameter("tmpSid"), "");
        //ページ番号取得
        String pageNum = NullDefault.getString(req.getParameter("pageNum"), "");

        if (!StringUtil.isNullZeroStringSpace(tmpSid)) {
            //データ取得
            jsonData = biz.getJsonUsrList(
                    con, Integer.valueOf(tmpSid),  Integer.valueOf(pageNum));
        }

        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(確定or編集キャンセル)");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}