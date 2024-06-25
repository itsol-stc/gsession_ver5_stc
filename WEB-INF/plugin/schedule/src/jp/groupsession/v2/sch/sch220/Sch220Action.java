package jp.groupsession.v2.sch.sch220;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.AbstractScheduleAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] スケジュール 出欠確認一覧POPUPのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch220Action extends AbstractScheduleAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch220Action.class);

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

        log__.debug("START_SCH220");
        ActionForward forward = null;
        Sch220Form uform = (Sch220Form) form;

        //スケジュールSID取得
        String schSid = NullDefault.getString(req.getParameter("schSid"), "");
        log__.debug("schSid==>" + schSid);

        //スケジュール出欠確認一覧画面の表示
        forward = __doInit(map, uform, req, res, con, schSid);

        log__.debug("forward==>" + forward.getPath());
        log__.debug("END_SCH220");
        return forward;
    }

    /**
     * <br>初期表示を行う
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param schSid スケジュールSID
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Sch220Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String schSid)
    throws SQLException {
        con.setAutoCommit(true);
        ActionForward forward = null;
        RequestModel reqMdl = getRequestModel(req);
        Sch220Biz biz = new Sch220Biz(reqMdl);

        Sch220ParamModel paramMdl = new Sch220ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con, schSid);
        paramMdl.setFormData(form);

        forward = map.getInputForward();
        con.setAutoCommit(false);
        return forward;
    }
}
