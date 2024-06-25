package jp.groupsession.v2.cir.cir100;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cir.AbstractCircularAdminAction;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.model.CirInitModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 回覧板 管理者設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir100Action extends AbstractCircularAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir100Action.class);

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
        Cir100Form cirForm = (Cir100Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD==>" + cmd);

        cirForm.setCirAccountMode(GSConstCircular.ACCOUNTMODE_COMMON);

        if (cmd.equals("cir100back")) {
            //戻る
            forward = __doBack(map, cirForm, req, res, con);
        } else if (cmd.equals("cirAutoDelete")) {
            //メール自動削除設定
            forward = map.findForward("cirAutoDelete");
        } else if (cmd.equals("cirManualDelete")) {
            //メール手動削除設定
            forward = map.findForward("cirManualDelete");
        } else if (cmd.equals("cirIniset")) {
            //初期値設定
            forward = map.findForward("cirIniset");
        } else if (cmd.equals("accountList")) {
            //アカウント管理
            forward = map.findForward("accountList");
        } else if (cmd.equals("cirConfAccount")) {
            //アカウント管理
            forward = map.findForward("cirConfAccount");
        } else if (cmd.equals("cirAdminConf")) {
            //アカウント管理
            forward = map.findForward("cirAdminConf");
        } else if (cmd.equals("cirLogCount")) {
            //統計情報
            forward = map.findForward("cirLogCount");
        } else if (cmd.equals("cirSender")) {
            //回覧板送信対象者設定
            forward = map.findForward("cirSender");
        } else {
            //メニュー表示
            CirCommonBiz cirBiz = new CirCommonBiz(con);
            CirInitModel mdl = new CirInitModel();
            mdl = cirBiz.getCirInitConf(getRequestModel(req).getSmodel().getUsrsid(), con);
            cirForm.setCir100autoDelKbn(mdl.getCinAutoDelKbn());
            if (__canUseSmlConf(cirForm, req, con)) {
                cirForm.setCanSmlUse(GSConst.PLUGIN_USE);
            } else {
                cirForm.setCanSmlUse(GSConst.PLUGIN_NOT_USE);
            }

            //GS管理者情報を設定
            BaseUserModel buMdl = getSessionUserModel(req);
            cirForm.setCir100GsAdminFlg(buMdl.getAdminFlg());

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
     */
    private ActionForward __doBack(ActionMapping map, Cir100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        log__.debug("戻る");
        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            return map.findForward("mainAdmSetting");
        }
        return map.findForward("back");
    }
    /**
     * <br>[機  能] ショートメール設定が利用可能か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return true ショートメール使用可能
     *
     */
    private boolean __canUseSmlConf(Cir100Form form, HttpServletRequest req, Connection con)
    throws SQLException {

        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con);
        CommonBiz cmnBiz = new CommonBiz();
        //ショートメールは利用可能か判定
        return cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig);
    }
}
