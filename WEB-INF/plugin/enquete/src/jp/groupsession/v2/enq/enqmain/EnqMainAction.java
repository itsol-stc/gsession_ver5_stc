package jp.groupsession.v2.enq.enqmain;

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
import jp.groupsession.v2.enq.AbstractEnqueteAction;

/**
 *
 * <br>[機  能] アンケート メイン画面表示のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqMainAction extends AbstractEnqueteAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqMainAction.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {
        ActionForward forward = null;
        EnqMainForm thisForm = (EnqMainForm) form;
        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        log__.debug("CMD= " + cmd);

        if (cmd.equals("enqmainAnswer")) {
            forward = map.findForward("enqmainAnswer");
        } else {
        // 初期表示
        log__.debug("初期表示");
        forward = __doInit(map, thisForm, req, res, con);
        }
        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
                                   EnqMainForm form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException, Exception {

        con.setAutoCommit(true);
        try {
            EnqMainParamModel paramMdl = new EnqMainParamModel();
            paramMdl.setParam(form);
            EnqMainBiz biz = new EnqMainBiz();
            // コマンドパラメータ取得
            String cmd = NullDefault.getString(req.getParameter("CMD"), "");
            cmd = cmd.trim();
            biz.setInitData(paramMdl, con, getRequestModel(req));
            paramMdl.setFormData(form);
            //ショートメールプラグイン使用可否を設定
            form.setEnq010pluginSmailUse(_getUseSmailPluginKbn(req, con));


        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }
}
