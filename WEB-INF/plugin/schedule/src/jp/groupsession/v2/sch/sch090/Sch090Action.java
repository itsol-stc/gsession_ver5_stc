package jp.groupsession.v2.sch.sch090;

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
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchAdmConfModel;

/**
 * <br>[機  能] スケジュール 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch090Action extends AbstractScheduleAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch090Action.class);

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
        Sch090Form schForm = (Sch090Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("iniset")) {
            //初期値設定
            forward = map.findForward("iniset");
        } else if (cmd.equals("daydspset")) {
            //日間表示時間帯設定
            forward = map.findForward("daydspset");
        } else if (cmd.equals("gmdspset")) {
            //グループメンバ表示設定
            forward = map.findForward("gmdspset");
        } else if (cmd.equals("gsdspset")) {
            //グループスケジュール表示設定
            forward = map.findForward("gsdspset");
        } else if (cmd.equals("listdspset")) {
            //スケジュール一覧表示設定
            forward = map.findForward("listdspset");
        } else if (cmd.equals("smailset")) {
            //スケジュール　ショートメール通知設定
            forward = map.findForward("smailset");
        } else if (cmd.equals("repeatset")) {
            //スケジュール　重複登録設定
            forward = map.findForward("repeatset");
        } else if (cmd.equals("viewlistset")) {
            //スケジュール　表示リスト設定
            forward = map.findForward("viewlistset");
        }  else if (cmd.equals("sch090back")) {
            //戻る
            forward = __doBack(map, schForm, req, res, con);
        } else {
            //メニュー表示
            __setCanUsePluginFlg(schForm, req, con);

            //重複登録設定の編集を許可されているかを設定
            SchCommonBiz schBiz = new SchCommonBiz(getRequestModel(req));
            schForm.setSch090canEditRepertKbn(schBiz.canEditRepertKbn(con));

            // 管理者設定から、個人設定「グループメンバー表示」設定の表示判別を行う
            __setDspGroupMemberShow(schForm, req, con);

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
    private ActionForward __doBack(ActionMapping map, Sch090Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("戻る");
        ActionForward forward = null;
        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            //メイン個人設定メニュー画面へ遷移する。
            return map.findForward("mainPriSetting");
        }
        if (GSConstSchedule.DSP_MOD_LIST.equals(form.getListMod())) {
            log__.debug("listへ戻る");
            return map.findForward("040_list");
        }
        if (GSConstSchedule.DSP_MOD_MONTH.equals(form.getDspMod())) {
            //月間
            forward = map.findForward("040_month");
        } else if (GSConstSchedule.DSP_MOD_DAY.equals(form.getDspMod())) {
            //日間
            forward = map.findForward("040_day");
        } else if (GSConstSchedule.DSP_MOD_KOJIN_WEEK.equals(form.getDspMod())) {
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
    private void __setCanUsePluginFlg(Sch090Form form, HttpServletRequest req, Connection con)
    throws SQLException {
        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        SchCommonBiz biz = new SchCommonBiz(con, getRequestModel(req));
        SchAdmConfModel admMdl = biz.getAdmConfModel(con);

        CommonBiz cmnBiz = new CommonBiz();
        //在席管理は利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_ZAISEKI, pconfig)) {
            form.setZaisekiUseOk(GSConstSchedule.PLUGIN_USE);
        } else {
            form.setZaisekiUseOk(GSConstSchedule.PLUGIN_NOT_USE);
        }
        //ショートメールは利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_SMAIL, pconfig)
              && admMdl.getSadSmailSendKbn() == GSConstSchedule.SMAIL_SEND_KBN_USER) {
            form.setSmailUseOk(GSConstSchedule.PLUGIN_USE);
        } else {
            form.setSmailUseOk(GSConstSchedule.PLUGIN_NOT_USE);
        }

    }

    /**
     * <br>[機  能] 個人設定「グループメンバー表示設定」の表示判別を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setDspGroupMemberShow(Sch090Form form, HttpServletRequest req, Connection con)
    throws SQLException {

        // 管理者設定の各フラグを取得
        SchCommonBiz biz = new SchCommonBiz(con, getRequestModel(req));
        SchAdmConfModel admMdl = biz.getAdmConfModel(con);


        // 「グループメンバー表示設定」を表示するか判定
        if (admMdl.getSadSortKbn() == GSConstSchedule.MEM_DSP_ADM
         && admMdl.getSadDspGroup() == GSConstSchedule.SAD_DSP_GROUP_DEFGROUP) {
            form.setSch090dspEditGroupUser(GSConstSchedule.SCC_USER_NOT_SHOW);
        } else {
            form.setSch090dspEditGroupUser(GSConstSchedule.SCC_USER_SHOW);
        }
    }
}
