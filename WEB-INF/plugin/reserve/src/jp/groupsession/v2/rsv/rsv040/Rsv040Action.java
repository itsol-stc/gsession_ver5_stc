package jp.groupsession.v2.rsv.rsv040;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rsv.AbstractReserveAdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 施設予約 管理者設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv040Action extends AbstractReserveAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv040Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
     public ActionForward executeAction(ActionMapping map,
                                         ActionForm form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con) throws Exception {

         ActionForward forward = null;
         Rsv040Form rsvform = (Rsv040Form) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         //戻るボタン押下
         if (cmd.equals("rsv040_back_to_menu")) {
             log__.debug("戻るボタン押下");
             forward = __doBack(map, rsvform, req, res, con);

         //施設予約基本設定リンククリック
         } else if (cmd.equals("kihon_settei")) {
             log__.debug("施設予約基本設定リンククリック");
             forward = map.findForward("kihon_settei");

         //施設グループ情報設定
         } else if (cmd.equals("rsv040_sisetu_settei")) {
             log__.debug("施設グループ情報設定リンククリック");
             forward = map.findForward("sisetu_settei");

         //自動データ削除設定リンククリック
         } else if (cmd.equals("batch_settei")) {
             log__.debug("自動データ削除設定リンククリック");
             forward = map.findForward("batch_settei");

         //手動データ削除リンククリック
         } else if (cmd.equals("data_sakuzyo")) {
             log__.debug("手動データ削除リンククリック");
             forward = map.findForward("data_sakuzyo");

         //施設予約インポートリンククリック
         } else if (cmd.equals("yoyaku_import")) {
             log__.debug("施設予約インポートリンククリック");
             forward = map.findForward("yoyaku_import");

         //初期値設定リンククリック
         } else if (cmd.equals("init_settei")) {
             log__.debug("初期値設定リンククリック");
             forward = map.findForward("init_settei");

         //日間表示時間帯デフォルト設定リンククリック
         } else if (cmd.equals("zikantai_settei")) {
             log__.debug("日間表示時間帯デフォルト設定リンククリック");
             forward = map.findForward("zikantai_settei");

         //ショートメール通知設定リンククリック
         } else if (cmd.equals("smail_settei")) {
             log__.debug("ショートメール通知設定リンククリック");
             forward = map.findForward("smail_settei");

         //初期表示処理
         } else {
             log__.debug("初期表示処理");

             //管理者設定画面に戻るフラグ
             rsvform.setRsvBackToAdminSetting(1);

             if (__canUseSmlConf(rsvform, req, con)) {
                 rsvform.setCanUseSmlKbn(GSConst.PLUGIN_USE);
             } else {
                 rsvform.setCanUseSmlKbn(GSConst.PLUGIN_NOT_USE);
             }
             forward = map.getInputForward();
         }

         return forward;
     }

     /**
      * <br>[機  能] 戻るボタン処理
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map マップ
      * @param form フォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @return ActionForward フォワード
      */
     private ActionForward __doBack(ActionMapping map,
                                     Rsv040Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con) {

         ActionForward forward = null;

         if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
             return map.findForward("mainAdmSetting");
         }
         String backPgId =
             NullDefault.getStringZeroLength(
                     form.getRsvBackPgId(), GSConstReserve.DSP_ID_RSV010);

         //予約一覧[週間]画面へ戻る
         if (backPgId.equals(GSConstReserve.DSP_ID_RSV010)) {
             forward = map.findForward("back_to_syukan");
         //予約一覧[日間]画面へ戻る
         } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV020)) {
             forward = map.findForward("back_to_nikkan");
         //予約一覧[月間]画面へ戻る
         } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV030)) {
             forward = map.findForward("back_to_gekkan");
         //施設利用状況照会画面へ戻る
         } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV100)) {
             forward = map.findForward("back_to_itiran");
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
     private boolean __canUseSmlConf(Rsv040Form form, HttpServletRequest req, Connection con)
     throws SQLException {

         //プラグイン設定を取得する
         PluginConfig pconfig
             = getPluginConfigForMain(getPluginConfig(req), con);
         CommonBiz cmnBiz = new CommonBiz();
         //ショートメールは利用可能か判定
         return cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig);
     }

}