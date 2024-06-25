package jp.groupsession.v2.sch.sch080;

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
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sch.AbstractScheduleAdminAction;

/**
 * <br>[機  能] スケジュール 管理者設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch080Action extends AbstractScheduleAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch080Action.class);

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
        Sch080Form schForm = (Sch080Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("crange")) {
            //共有範囲設定
            forward = map.findForward("crange");
        } else if (cmd.equals("adel")) {
            //自動データ削除
            forward = map.findForward("adel");
        } else if (cmd.equals("sdel")) {
            //手動データ削除
            forward = map.findForward("sdel");
        } else if (cmd.equals("simp")) {
            //インポート
            forward = map.findForward("simp");
        } else if (cmd.equals("gmdspset")) {
            //表示設定
            forward = map.findForward("gmdspset");
        } else if (cmd.equals("iniset")) {
            //初期値設定
            forward = map.findForward("iniset");
        } else if (cmd.equals("smailAdmset")) {
            //ショートメール通知設定
            forward = map.findForward("smailAdmset");
        } else if (cmd.equals("spAccess")) {
            //特例アクセス設定
            forward = map.findForward("spAccess");
        } else if (cmd.equals("sch080back")) {
            //戻る
            forward = __doBack(map, schForm, req, res, con);
        } else {
            //メニュー表示
            __setCanUsePluginFlg(schForm, req, con);
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
    private ActionForward __doBack(ActionMapping map, Sch080Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            //メイン管理者画面へ遷移する。
            return map.findForward("mainAdmSetting");
        }

        ActionForward forward = null;
        if (form.getListMod().equals(GSConstSchedule.DSP_MOD_LIST)) {
            return map.findForward("040_list");
        }
        if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_MONTH)) {
            //月間
            forward = map.findForward("040_month");
        } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_DAY)) {
            //日間
            forward = map.findForward("040_day");
        } else if (form.getDspMod().equals(GSConstSchedule.DSP_MOD_KOJIN_WEEK)) {
            //個人週間
            forward = map.findForward("040_kojin_week");
        } else {
            //週間
            forward = map.findForward("040_week");
        }
        return forward;
    }

    /**
     * <br>[機  能] 在席管理・ショートメールプラグインが利用可能かフォームへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setCanUsePluginFlg(Sch080Form form, HttpServletRequest req, Connection con)
    throws SQLException {
        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con);
        CommonBiz cmnBiz = new CommonBiz();
        //在席管理は利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_ZAISEKI, pconfig)) {
            form.setZaisekiUseOk(GSConstSchedule.PLUGIN_USE);
        } else {
            form.setZaisekiUseOk(GSConstSchedule.PLUGIN_NOT_USE);
        }
        //ショートメールは利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_SMAIL, pconfig)) {
            form.setSmailUseOk(GSConstSchedule.PLUGIN_USE);
        } else {
            form.setSmailUseOk(GSConstSchedule.PLUGIN_NOT_USE);
        }

    }
}
