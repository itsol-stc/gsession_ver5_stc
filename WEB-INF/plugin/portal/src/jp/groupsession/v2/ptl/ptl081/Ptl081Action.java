package jp.groupsession.v2.ptl.ptl081;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ptl.AbstractPortalAdminAction;
import jp.groupsession.v2.ptl.PtlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ポータル ポートレット選択画面(ポップアップ)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl081Action extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl081Action.class);

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
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Ptl081Form ptlForm = (Ptl081Form) form;

        if (cmd.equals("selectPortlet")) {
            //ポートレット名クリック
            forward = __selectPortlet(map, ptlForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, ptlForm, req, res, con);
        }

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
     * @throws SQLException 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Ptl081Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws SQLException {

        Ptl081ParamModel paramMdl = new Ptl081ParamModel();
        paramMdl.setParam(form);
        Ptl081Biz biz = new Ptl081Biz();
        biz.setInitData(paramMdl, con, getRequestModel(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] ポートレット名クリック時の処理
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
    private ActionForward __selectPortlet(ActionMapping map,
        Ptl081Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException {

        Ptl081ParamModel paramMdl = new Ptl081ParamModel();
        paramMdl.setParam(form);
        Ptl081Biz biz = new Ptl081Biz();
        boolean commit = false;

        try {

            //登録処理
            biz.insertData(paramMdl, con);
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("ポートレット追加処理エラー", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        RequestModel reqMdl = getRequestModel(req);

        //ログ出力処理
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.entry");

        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        ptlBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_INFO, "");

        return map.getInputForward();
    }

}

