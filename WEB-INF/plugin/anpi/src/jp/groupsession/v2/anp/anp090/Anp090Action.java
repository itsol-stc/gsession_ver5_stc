package jp.groupsession.v2.anp.anp090;


import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.anp.AbstractAnpiAdminAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 管理者設定・メールテンプレート選択画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp090Action extends AbstractAnpiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp090Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォアード
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        Anp090Form uform = (Anp090Form) form;

        //管理者権限確認
        if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
            return getNotAdminSeniPage(map, req);
        }

        if (cmd.equals("anp090back")) {
            //戻る
            forward = map.findForward("back");

        } else if (cmd.equals("anp090edit")) {
            //追加・編集
            forward = map.findForward("edit");
        } else if (cmd.equals("upTemplateData")) {
            //上へボタンクリック
            forward = __doSortChange(map, uform, req, res, con, GSConstAnpi.SORT_UP);
        } else if (cmd.equals("downTemplateData")) {
            //下へボタンクリック
            forward = __doSortChange(map, uform, req, res, con, GSConstAnpi.SORT_DOWN);
        } else {
            //初期化
            forward = __doInit(map, uform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォアード
     */
    private ActionForward __doInit(ActionMapping map, Anp090Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        return __refresh(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォアード
     */
    private ActionForward __refresh(ActionMapping map, Anp090Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //初期データ取得
        Anp090Biz biz = new Anp090Biz();
        Anp090ParamModel paramModel = new Anp090ParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, con);
        paramModel.setFormData(form);
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
        Anp090Form form,
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

            Anp090Biz biz = new Anp090Biz();
            Anp090ParamModel paramModel = new Anp090ParamModel();
            paramModel.setParam(form);
            biz.updateSort(con, paramModel, getSessionUserModel(req).getUsrsid(), changeKbn);
            paramModel.setFormData(form);
            commitFlg = true;

            //ログ
            GsMessage gsMsg = new GsMessage(getRequestModel(req));
            AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);
            String opCode = gsMsg.getMessage("cmn.change");
            String value = gsMsg.getMessage("change.sort.order");
            anpBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_INFO, value);

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
