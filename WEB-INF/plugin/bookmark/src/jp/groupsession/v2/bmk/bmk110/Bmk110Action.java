package jp.groupsession.v2.bmk.bmk110;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.bmk.AbstractBookmarkAdminAction;
import jp.groupsession.v2.bmk.dao.BmkAconfDao;
import jp.groupsession.v2.bmk.model.BmkAconfModel;

/**
 * <br>[機  能] 管理者設定 権限設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk110Action extends AbstractBookmarkAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk110Action.class);

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

        Bmk110Form bmkForm = (Bmk110Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("bmk110kakunin")) {
            //OKボタンクリック
            forward = __doKakunin(map, bmkForm, req, res, con);
        } else if (cmd.equals("bmk110back")) {
            //戻るボタンクリック
            forward = map.findForward("bmk100");
        } else if (cmd.equals("bmk110knback")) {
            //確認画面からの戻り
            forward = __doInitBack(map, bmkForm, req, res, con);
        } else if (cmd.equals("chgGrpEditKbn")) {
            forward = __doRedraw(map, bmkForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, bmkForm, req, res, con);
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
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Bmk110Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");

        //セッションユーザSIDを取得する。
        int sessionUserSid = getSessionUserModel(req).getUsrsid();

        con.setAutoCommit(true);
        Bmk110Biz biz = new Bmk110Biz();

        Bmk110ParamModel paramMdl = new Bmk110ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>再描画処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doRedraw(ActionMapping map, Bmk110Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");

        return map.getInputForward();
    }

    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Bmk110Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("確認");

        //入力チェック
        ActionErrors errors = form.validateCheck(req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doRedraw(map, form, req, res, con);
        }
        saveToken(req);
        return map.findForward("bmk110kn");
    }

    /**
     * <br>確認画面からの戻り処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInitBack(ActionMapping map, Bmk110Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");

        con.setAutoCommit(true);

        Bmk110Biz biz = new Bmk110Biz();

        BmkAconfDao dao = new BmkAconfDao(con);
        BmkAconfModel model = dao.selectAConf();

        Bmk110ParamModel paramMdl = new Bmk110ParamModel();
        paramMdl.setParam(form);
        biz.setDspData(paramMdl, model);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }
}
