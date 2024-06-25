package jp.groupsession.v2.prj.prj110;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.prj.AbstractProjectAdminAction;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.prj110kn.Prj110knAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] プロジェクト管理 管理者設定 登録権限設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj110Action extends AbstractProjectAdminAction {

    /** CMD:戻るクリック */
    public static final String CMD_BACK = "back110";
    /** CMD:OKクリック */
    public static final String CMD_OK = "ok";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj110Action.class);

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

        log__.debug("Prj110Action start");
        ActionForward forward = null;

        Prj110Form thisForm = (Prj110Form) form;

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        if (CMD_BACK.equals(cmd)) {
            log__.debug("戻る");
            forward = map.findForward(CMD_BACK);

        } else if (CMD_OK.equals(cmd)) {
            log__.debug("設定");
            forward = map.findForward(CMD_OK);

        } else if (Prj110knAction.CMD_BACK.equals(cmd)) {
            log__.debug("確認画面から戻る");
            forward = map.getInputForward();

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("Prj110Action end");
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
        Prj110Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        Prj110Biz biz = new Prj110Biz(con);

        Prj110ParamModel paramMdl = new Prj110ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

}
