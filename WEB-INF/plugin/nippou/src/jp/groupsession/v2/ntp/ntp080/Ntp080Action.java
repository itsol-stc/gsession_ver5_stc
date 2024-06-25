package jp.groupsession.v2.ntp.ntp080;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.ntp.AbstractNippouAdminAction;
import jp.groupsession.v2.ntp.GSConstNippou;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 日報 管理者設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp080Action extends AbstractNippouAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp080Action.class);

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
        Ntp080Form ntpForm = (Ntp080Form) form;
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
//        } else if (cmd.equals("master")) {
//            //日報マスタ登録
//            forward = map.findForward("master");
        } else if (cmd.equals("smlsetting")) {
            //ショートメール通知設定
            forward = map.findForward("smlsetting");
        } else if (cmd.equals("itmsetting")) {
            //項目設定
            forward = map.findForward("itmsetting");
        } else if (cmd.equals("ntp080back")) {
            //戻る
            forward = __doBack(map, ntpForm, req, res, con);
        } else if (cmd.equals("spAccess")) {
            forward = map.findForward("spAccess");
        } else {
            //メニュー表示
            if (__canUseSmlConf(ntpForm, req, con)) {
                ntpForm.setCanUseSml(GSConst.PLUGIN_USE);
            } else {
                ntpForm.setCanUseSml(GSConst.PLUGIN_NOT_USE);
            }
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
    private ActionForward __doBack(ActionMapping map, Ntp080Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            log__.debug("メイン管理者画面へ遷移する。");
            return map.findForward("mainAdmSetting");
        }

        ActionForward forward = null;
        if (GSConstNippou.DSP_MOD_LIST.equals(form.getListMod())) {
            log__.debug("listへ戻る");
            return map.findForward("040_list");
        }
        if (GSConstNippou.DSP_MOD_MONTH.equals(form.getDspMod())) {
            log__.debug("月間");
            forward = map.findForward("040_month");
        } else if (GSConstNippou.DSP_MOD_DAY.equals(form.getDspMod())) {
            log__.debug("日間");
            forward = map.findForward("040_day");
        } else if (GSConstNippou.DSP_MOD_MASTA.equals(form.getDspMod())) {
            log__.debug("マスタメンテ");
            forward = map.findForward("masta");
        } else if (GSConstNippou.DSP_MOD_ANKEN.equals(form.getDspMod())) {
            log__.debug("案件");
            forward = map.findForward("anken");
        } else if (GSConstNippou.DSP_MOD_TARGET.equals(form.getDspMod())) {
            log__.debug("目標");
            forward = map.findForward("target");
        } else if (GSConstNippou.DSP_MOD_BUNSEKI.equals(form.getDspMod())) {
            log__.debug("分析");
            forward = map.findForward("bunseki");
        } else {
            log__.debug("週間");
            forward = map.findForward("040_week");
        }
        return forward;
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
    private boolean __canUseSmlConf(Ntp080Form form, HttpServletRequest req, Connection con)
    throws SQLException {

        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con);
        CommonBiz cmnBiz = new CommonBiz();
        //ショートメールは利用可能か判定
        return cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig);
    }

}
