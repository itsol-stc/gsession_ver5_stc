package jp.groupsession.v2.cir.cir250;

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
import jp.groupsession.v2.cir.AbstractCircularAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 回覧板 基本設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir250Action extends AbstractCircularAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir250Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("START_Cir250");
        ActionForward forward = null;

        Cir250Form thisForm = (Cir250Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("ok")) {
            log__.debug("設定ボタン押下");
            forward = __doUpdate(map, thisForm, req, res, con);
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }
        log__.debug("END_Cir250");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Cir250Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        Cir250ParamModel paramMdl = new Cir250ParamModel();
        paramMdl.setParam(form);
        Cir250Biz biz = new Cir250Biz();
        biz.setInitData(getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        //トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * OKボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doUpdate(ActionMapping map, Cir250Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);
        ActionForward forward = null;

        boolean commit = false;
        try {

            //回覧板個人設定の更新
            Cir250ParamModel paramMdl = new Cir250ParamModel();
            paramMdl.setParam(form);
            Cir250Biz biz = new Cir250Biz();
            biz.updateSmlConf(reqMdl, paramMdl, con);
            paramMdl.setFormData(form);

            commit = true;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }
        return forward;
    }
}
