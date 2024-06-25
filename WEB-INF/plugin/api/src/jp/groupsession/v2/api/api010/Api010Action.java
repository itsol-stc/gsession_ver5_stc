package jp.groupsession.v2.api.api010;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsAction;
/**
 *
 * <br>[機  能] API管理者設定Action
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Api010Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Api010Action.class);

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
        Api010Form schForm = (Api010Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("api010Basic")) {
            //基本設定
            forward = map.findForward("basic");
        } else if (cmd.equals("api010Token")) {
            //トークン管理
            forward = map.findForward("token");
        } else if (cmd.equals("api010Back")) {
            //戻る
            forward = __doBack(map, schForm, req, res, con);
        } else {
            //メニュー表示
            forward = map.getInputForward();
        }
        return forward;
    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Api010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");
        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            return map.findForward("mainAdmSetting");
        }
        return map.findForward("back");
    }
    @Override
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }
    @Override
    public String getPluginId() {
        return GSConst.PLUGINID_API;
    }
}
