package jp.groupsession.v2.ptl.ptl150;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.ptl.AbstractPortalAdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ポータル 管理者設定 初期値設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl150Action extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl150Action.class);

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

        Ptl150Form thisForm = (Ptl150Form) form;

        if (cmd.equals("confMenu")) {
            //戻るボタンクリック
            forward = map.findForward("confMenu");

        } else if (cmd.equals("doKakutei")) {
            //「OK」ボタンクリック
            forward = __doOk(map, req);

        } else {
            //初期表示
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
                                    Ptl150Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(false);
        int usrSid = getSessionUserSid(req);

        Ptl150ParamModel paramMdl = new Ptl150ParamModel();
        paramMdl.setParam(form);
        Ptl150Biz biz = new Ptl150Biz();
        biz.initDsp(con, paramMdl, usrSid);
        paramMdl.setFormData(form);

        con.setAutoCommit(true);
        return map.getInputForward();
    }

    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param req リクエスト
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doOk(ActionMapping map, HttpServletRequest req)
                                   throws SQLException {
        log__.debug("確認");

        saveToken(req);

        return map.findForward("doKakutei");
    }

}
