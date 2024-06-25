package jp.groupsession.v2.bmk.bmk080;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.bmk.AbstractBookmarkAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>登録ランキング画面のアクションクラス
 * @author JTS
 */
public class Bmk080Action extends AbstractBookmarkAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk080Action.class);

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
        Bmk080Form bmkForm = (Bmk080Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("prevPage")) {
            //前ページクリック
            bmkForm.setBmk080PageTop(bmkForm.getBmk080PageTop() - 1);
            forward = __doInit(map, bmkForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            bmkForm.setBmk080PageTop(bmkForm.getBmk080PageTop() + 1);
            forward = __doInit(map, bmkForm, req, res, con);
        } else if (cmd.equals("changePage")) {
            //ページコンボ変更
            forward = __doInit(map, bmkForm, req, res, con);
        } else if (cmd.equals("bmkAdd")) {
            forward = map.findForward("bmk020");
        } else if (cmd.equals("commentList")) {
            forward = map.findForward("bmk070");
        } else if (cmd.equals("backPage")) {
            //戻るボタンクリック
            forward = map.findForward("bmk010");
        } else {
            //初期表示
            forward = __doInit(map, bmkForm, req, res, con);
        }

        log__.debug("END");
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
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Bmk080Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        con.setAutoCommit(true);
        Bmk080Biz biz = new Bmk080Biz(getRequestModel(req));

        Bmk080ParamModel paramMdl = new Bmk080ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, userSid);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }
}

