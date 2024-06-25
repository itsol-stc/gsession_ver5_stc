package jp.groupsession.v2.rsv.rsv070;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.rsv.AbstractReserveSubAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 施設予約 施設情報画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv070Action extends AbstractReserveSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv070Action.class);

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
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Rsv070Form rsvform = (Rsv070Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        log__.debug("初期表示処理");
        forward = __doInit(map, rsvform, req, res, con);

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
                                    Rsv070Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        try {
            con.setAutoCommit(true);
            Rsv070Biz biz = new Rsv070Biz(getRequestModel(req), con);

            Rsv070ParamModel paramMdl = new Rsv070ParamModel();
            paramMdl.setParam(form);
            biz.setInitData(paramMdl, getSessionUserModel(req).getUsrsid());
            paramMdl.setFormData(form);

            con.setAutoCommit(false);
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }

        return map.getInputForward();
    }
}