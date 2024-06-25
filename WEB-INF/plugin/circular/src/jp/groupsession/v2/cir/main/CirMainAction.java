package jp.groupsession.v2.cir.main;

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
import jp.groupsession.v2.cir.AbstractCircularAction;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;

/**
 * <br>[機  能] 回覧板(メイン画面表示用)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirMainAction extends AbstractCircularAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirMainAction.class);

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
        CirMainForm cirForm = (CirMainForm) form;
        CirCommonBiz biz = new CirCommonBiz();
        //アカウントが未選択の場合、デフォルトアカウントを設定する
        if (cirForm.getCirViewAccount() <= 0) {
            cirForm.setCirViewAccount(
                    biz.getDefaultAccount(con, getSessionUserSid(req)));
        }

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("detail")) {
            log__.debug("*** タイトル文字列押下。");
            forward = map.findForward("detail");
        } else {
            //初期表示
            forward = __doInit(map, cirForm, req, res, con);
        }
        log__.debug("END");
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
        CirMainForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //初期表示情報を画面にセットする
        con.setAutoCommit(true);
        CirMainParamModel paramMdl = new CirMainParamModel();
        paramMdl.setParam(form);
        CirMainBiz biz = new CirMainBiz();
        biz.setInitData(paramMdl, con, paramMdl.getCirViewAccount(), getRequestModel(req));
        paramMdl.setFormData(form);

        form.setCirTopUrl(getPluginConfig(req).getPlugin(
                GSConstCircular.PLUGIN_ID_CIRCULAR).getTopMenuInfo().getUrl());
        con.setAutoCommit(false);

        return map.getInputForward();
    }

}

