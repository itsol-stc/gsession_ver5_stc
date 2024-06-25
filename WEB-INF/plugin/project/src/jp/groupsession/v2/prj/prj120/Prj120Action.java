package jp.groupsession.v2.prj.prj120;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.prj.AbstractProjectAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] プロジェクト管理 プロジェクト選択ポップアップのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj120Action extends AbstractProjectAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj120Action.class);

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

        log__.debug("Prj120Action start");
        ActionForward forward = null;

        Prj120Form thisForm = (Prj120Form) form;

        log__.debug("初期表示");
        forward = __doInit(map, thisForm, req, res, con);

        log__.debug("Prj120Action end");
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
        Prj120Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        //初期表示情報を画面にセットする
        Prj120Biz biz = new Prj120Biz(con, getRequestModel(req));
        Prj120ParamModel paramMdl = new Prj120ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, getSessionUserModel(req));
        paramMdl.setFormData(form);
        return map.getInputForward();
    }

}
