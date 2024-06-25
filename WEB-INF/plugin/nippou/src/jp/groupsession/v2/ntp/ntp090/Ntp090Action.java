package jp.groupsession.v2.ntp.ntp090;

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
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;

/**
 * <br>[機  能] 日報 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp090Action extends AbstractNippouAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp090Action.class);

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
        Ntp090Form ntpForm = (Ntp090Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("iniset")) {
            //初期値設定
            forward = map.findForward("iniset");
        } else if (cmd.equals("daydspset")) {
            //日間表示時間帯設定
            forward = map.findForward("daydspset");
        } else if (cmd.equals("listdspset")) {
            //日報一覧表示設定
            forward = map.findForward("listdspset");
        } else if (cmd.equals("smailset")) {
            //日報　スケジュール表示設定
            forward = map.findForward("smailset");
        } else if (cmd.equals("targetset")) {
            //日報　目標値設定
            forward = map.findForward("targetset");
        } else if (cmd.equals("ntp090back")) {
            //戻る
            forward = __doBack(map, ntpForm, req, res, con);
        } else {
            //メニュー表示
            __setCanUsePluginFlg(ntpForm, req, con);

            //ショートメール通知の設定を許可されているかを設定
            NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
            if (ntpBiz.canEditSmlKbn(con)) {
                ntpForm.setNtp090SmlNoticeKbn(GSConstNippou.SML_NOTICE_USR);
            } else {
                ntpForm.setNtp090SmlNoticeKbn(GSConstNippou.SML_NOTICE_ADM);
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
    private ActionForward __doBack(ActionMapping map, Ntp090Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("戻る");
        ActionForward forward = null;
        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            //メイン個人設定メニュー画面へ遷移する。
            return map.findForward("mainPriSetting");
        }
        if (GSConstNippou.DSP_MOD_LIST.equals(form.getListMod())) {
            log__.debug("listへ戻る");
            return map.findForward("040_list");
        }
        if (GSConstNippou.DSP_MOD_MONTH.equals(form.getDspMod())) {
            //月間
            forward = map.findForward("040_month");
        } else if (GSConstNippou.DSP_MOD_DAY.equals(form.getDspMod())) {
            //日間
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
    private void __setCanUsePluginFlg(Ntp090Form form, HttpServletRequest req, Connection con)
    throws SQLException {
        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        CommonBiz cmnBiz = new CommonBiz();
        //在席管理は利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstNippou.PLUGIN_ID_ZAISEKI, pconfig)) {
            form.setZaisekiUseOk(GSConstNippou.PLUGIN_USE);
        } else {
            form.setZaisekiUseOk(GSConstNippou.PLUGIN_NOT_USE);
        }
        //ショートメールは利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstNippou.PLUGIN_ID_SMAIL, pconfig)) {
            form.setSmailUseOk(GSConstNippou.PLUGIN_USE);
        } else {
            form.setSmailUseOk(GSConstNippou.PLUGIN_NOT_USE);
        }

    }
}
