package jp.groupsession.v2.rsv.rsv320;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.rsv.AbstractReserveAdminAction;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 管理者設定 ショートメール通知設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv320Action extends AbstractReserveAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv320Action.class);

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
         Rsv320Form rsvform = (Rsv320Form) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         //設定ボタン押下
         if (cmd.equals("edit")) {
             log__.debug("設定ボタン押下");
             forward = __doSet(map, rsvform, req, res, con);
         //戻るボタン押下
         } else if (cmd.equals("back_to_kanri_menu")) {
             log__.debug("戻るボタン押下");
             forward = map.findForward("kanrisya_settei");
         //初期表示処理
         } else {
             log__.debug("初期表示処理");
             forward = __doInit(map, rsvform, req, res, con);
         }

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
     private ActionForward __doInit(ActionMapping map,
                                     Rsv320Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws SQLException {

         if (!__canUseSmlConf(form, req, con)) {
             return getSubmitErrorPage(map, req);
         }

         con.setAutoCommit(true);
         Rsv320Biz biz = new Rsv320Biz(getRequestModel(req), con);

         Rsv320ParamModel paramMdl = new Rsv320ParamModel();
         paramMdl.setParam(form);
         biz.setInitData(paramMdl);
         paramMdl.setFormData(form);

         //トランザクショントークン設定
         con.setAutoCommit(false);
         return map.getInputForward();
     }

     /**
      * <br>[機  能] 設定ボタンクリック時処理
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @return ActionForward 画面遷移先
      * @throws SQLException SQL実行時例外
      */
     private ActionForward __doSet(ActionMapping map,
                                    Rsv320Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
         throws SQLException {

         if (!__canUseSmlConf(form, req, con)) {
             return getSubmitErrorPage(map, req);
         }

         ActionForward forward = null;
         con.setAutoCommit(false);
         boolean commit = false;

         try {

             if (!isTokenValid(req, true)) {
                 log__.info("２重投稿");
                 return getSubmitErrorPage(map, req);
             }

             //個人設定の更新
             Rsv320Biz biz = new Rsv320Biz(getRequestModel(req), con);

             Rsv320ParamModel paramMdl = new Rsv320ParamModel();
             paramMdl.setParam(form);
             biz.updateSmailKbn(paramMdl);
             paramMdl.setFormData(form);

             //完了画面
             forward = __doCompDsp(map, form, req, res);
             commit = true;

         } catch (SQLException e) {
             log__.error("SQLException", e);
             throw e;
         } finally {
             if (commit) {
                 con.commit();
             } else {
                 con.rollback();
             }
         }

         //ログ出力処理
         GsMessage gsMsg = new GsMessage();
         String msg = __getLogMessage(form, req);
         AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
         rsvBiz.outPutLog(map, req, res,
                 gsMsg.getMessage(req, "cmn.change"), GSConstLog.LEVEL_INFO, msg);

         return forward;
     }

     /**
      * <br>[機  能] 完了画面設定
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @return ActionForward
      */
     private ActionForward __doCompDsp(ActionMapping map,
                                        Rsv320Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res) {

         ActionForward forward = null;
         Cmn999Form cmn999Form = new Cmn999Form();
         ActionForward urlForward = null;

         GsMessage gsMsg = new GsMessage();

         //完了画面パラメータの設定
         MessageResources msgRes = getResources(req);
         cmn999Form.setType(Cmn999Form.TYPE_OK);
         cmn999Form.setIcon(Cmn999Form.ICON_INFO);
         cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

         urlForward = map.findForward("kanrisya_settei");
         cmn999Form.setUrlOK(urlForward.getPath());
         cmn999Form.setMessage(
                 msgRes.getMessage("settei.kanryo.object",
                         gsMsg.getMessage(req, "shortmail.notification")));

         //hiddenパラメータ
         cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
         cmn999Form.addHiddenParam("rsvBackPgId", form.getRsvBackPgId());
         cmn999Form.addHiddenParam("rsvDspFrom", form.getRsvDspFrom());
         cmn999Form.addHiddenParam("rsvSelectedGrpSid", form.getRsvSelectedGrpSid());
         cmn999Form.addHiddenParam("rsvSelectedSisetuSid", form.getRsvSelectedSisetuSid());
         cmn999Form.addHiddenParam("rsv100InitFlg",
                 String.valueOf(form.isRsv100InitFlg()));
         cmn999Form.addHiddenParam("rsv100SearchFlg",
                 String.valueOf(form.isRsv100SearchFlg()));
         cmn999Form.addHiddenParam("rsv100SortKey", form.getRsv100SortKey());
         cmn999Form.addHiddenParam("rsv100OrderKey", form.getRsv100OrderKey());
         cmn999Form.addHiddenParam("rsv100PageTop", form.getRsv100PageTop());
         cmn999Form.addHiddenParam("rsv100PageBottom", form.getRsv100PageBottom());
         cmn999Form.addHiddenParam("rsv100selectedFromYear", form.getRsv100selectedFromYear());
         cmn999Form.addHiddenParam("rsv100selectedFromMonth", form.getRsv100selectedFromMonth());
         cmn999Form.addHiddenParam("rsv100selectedFromDay", form.getRsv100selectedFromDay());
         cmn999Form.addHiddenParam("rsv100selectedToYear", form.getRsv100selectedToYear());
         cmn999Form.addHiddenParam("rsv100selectedToMonth", form.getRsv100selectedToMonth());
         cmn999Form.addHiddenParam("rsv100selectedToDay", form.getRsv100selectedToDay());
         cmn999Form.addHiddenParam("rsv100KeyWord", form.getRsv100KeyWord());
         cmn999Form.addHiddenParam("rsv100SearchCondition", form.getRsv100SearchCondition());
         cmn999Form.addHiddenParam("rsv100TargetMok", form.getRsv100TargetMok());
         cmn999Form.addHiddenParam("rsv100TargetNiyo", form.getRsv100TargetNiyo());
         cmn999Form.addHiddenParam("rsv100CsvOutField", form.getRsv100CsvOutField());
         cmn999Form.addHiddenParam("rsv100SelectedKey1", form.getRsv100SelectedKey1());
         cmn999Form.addHiddenParam("rsv100SelectedKey2", form.getRsv100SelectedKey2());
         cmn999Form.addHiddenParam("rsv100SelectedKey1Sort", form.getRsv100SelectedKey1Sort());
         cmn999Form.addHiddenParam("rsv100SelectedKey2Sort", form.getRsv100SelectedKey2Sort());
         cmn999Form.addHiddenParam("rsvIkkatuTorokuKey", form.getRsvIkkatuTorokuKey());
         cmn999Form.addHiddenParam("rsv100svFromYear", form.getRsv100svFromYear());
         cmn999Form.addHiddenParam("rsv100svFromMonth", form.getRsv100svFromMonth());
         cmn999Form.addHiddenParam("rsv100svFromDay", form.getRsv100svFromDay());
         cmn999Form.addHiddenParam("rsv100svToYear", form.getRsv100svToYear());
         cmn999Form.addHiddenParam("rsv100svToMonth", form.getRsv100svToMonth());
         cmn999Form.addHiddenParam("rsv100svToDay", form.getRsv100svToDay());
         cmn999Form.addHiddenParam("rsv100svGrp1", form.getRsv100svGrp1());
         cmn999Form.addHiddenParam("rsv100svGrp2", form.getRsv100svGrp2());
         cmn999Form.addHiddenParam("rsv100svKeyWord", form.getRsv100svKeyWord());
         cmn999Form.addHiddenParam("rsv100svSearchCondition", form.getRsv100svSearchCondition());
         cmn999Form.addHiddenParam("rsv100svTargetMok", form.getRsv100svTargetMok());
         cmn999Form.addHiddenParam("rsv100svTargetNiyo", form.getRsv100svTargetNiyo());
         cmn999Form.addHiddenParam("rsv100svSelectedKey1", form.getRsv100svSelectedKey1());
         cmn999Form.addHiddenParam("rsv100svSelectedKey2", form.getRsv100svSelectedKey2());
         cmn999Form.addHiddenParam("rsv100svSelectedKey1Sort", form.getRsv100svSelectedKey1Sort());
         cmn999Form.addHiddenParam("rsv100svSelectedKey2Sort", form.getRsv100svSelectedKey2Sort());
         cmn999Form.addHiddenParam("rsv100SearchSvFlg",
                 String.valueOf(form.isRsv100SearchSvFlg()));

         cmn999Form.addHiddenParam("rsv100dateKbn", form.getRsv100dateKbn());
         cmn999Form.addHiddenParam("rsv100apprStatus", form.getRsv100apprStatus());
         cmn999Form.addHiddenParam("rsv100svDateKbn", form.getRsv100svDateKbn());
         cmn999Form.addHiddenParam("rsv100svApprStatus", form.getRsv100svApprStatus());

         cmn999Form.addHiddenParam("rsv010LearnMoreFlg", form.getRsv010LearnMoreFlg());
         cmn999Form.addHiddenParam("rsv010sisetuKeyword", form.getRsv010sisetuKeyword());
         cmn999Form.addHiddenParam("rsv010KeyWordkbn", form.getRsv010KeyWordkbn());
         cmn999Form.addHiddenParam("rsv010sisetuKeywordSisan", form.getRsv010sisetuKeywordSisan());
         cmn999Form.addHiddenParam("rsv010sisetuKeywordSisetu",
                 form.getRsv010sisetuKeywordSisetu());
         cmn999Form.addHiddenParam("rsv010sisetuKeywordBiko", form.getRsv010sisetuKeywordBiko());
         cmn999Form.addHiddenParam("rsv010sisetuKeywordNo", form.getRsv010sisetuKeywordNo());
         cmn999Form.addHiddenParam("rsv010sisetuKeywordIsbn", form.getRsv010sisetuKeywordIsbn());
         cmn999Form.addHiddenParam("rsv010sisetuFree", form.getRsv010sisetuFree());
         cmn999Form.addHiddenParam("rsv010sisetuFreeFromY", form.getRsv010sisetuFreeFromY());
         cmn999Form.addHiddenParam("rsv010sisetuFreeFromMo", form.getRsv010sisetuFreeFromMo());
         cmn999Form.addHiddenParam("rsv010sisetuFreeFromD", form.getRsv010sisetuFreeFromD());
         cmn999Form.addHiddenParam("rsv010sisetuFreeFromH", form.getRsv010sisetuFreeFromH());
         cmn999Form.addHiddenParam("rsv010sisetuFreeFromMi", form.getRsv010sisetuFreeFromMi());
         cmn999Form.addHiddenParam("rsv010sisetuFreeToY", form.getRsv010sisetuFreeToY());
         cmn999Form.addHiddenParam("rsv010sisetuFreeToMo", form.getRsv010sisetuFreeToMo());
         cmn999Form.addHiddenParam("rsv010sisetuFreeToD", form.getRsv010sisetuFreeToD());
         cmn999Form.addHiddenParam("rsv010sisetuFreeToH", form.getRsv010sisetuFreeToH());
         cmn999Form.addHiddenParam("rsv010sisetuFreeToMi", form.getRsv010sisetuFreeToMi());
         cmn999Form.addHiddenParam("rsv010sisetuKbn", form.getRsv010sisetuKbn());
         cmn999Form.addHiddenParam("rsv010grpNarrowDown", form.getRsv010grpNarrowDown());
         cmn999Form.addHiddenParam("rsv010sisetuSmoky", form.getRsv010sisetuSmoky());
         cmn999Form.addHiddenParam("rsv010sisetuChere", form.getRsv010sisetuChere());
         cmn999Form.addHiddenParam("rsv010sisetuTakeout", form.getRsv010sisetuTakeout());
         cmn999Form.addHiddenParam("rsv010svSisetuKeyword", form.getRsv010svSisetuKeyword());
         cmn999Form.addHiddenParam("rsv010svKeyWordkbn", form.getRsv010svKeyWordkbn());
         cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisan",
                 form.getRsv010svSisetuKeywordSisan());
         cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisetu",
                 form.getRsv010svSisetuKeywordSisetu());
         cmn999Form.addHiddenParam("rsv010svSisetuKeywordBiko",
                 form.getRsv010svSisetuKeywordBiko());
         cmn999Form.addHiddenParam("rsv010svSisetuKeywordNo", form.getRsv010svSisetuKeywordNo());
         cmn999Form.addHiddenParam("rsv010svSisetuKeywordIsbn",
                 form.getRsv010svSisetuKeywordIsbn());
         cmn999Form.addHiddenParam("rsv010svSisetuFree", form.getRsv010svSisetuFree());
         cmn999Form.addHiddenParam("rsv010svSisetuFreeFromY", form.getRsv010svSisetuFreeFromY());
         cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMo", form.getRsv010svSisetuFreeFromMo());
         cmn999Form.addHiddenParam("rsv010svSisetuFreeFromD", form.getRsv010svSisetuFreeFromD());
         cmn999Form.addHiddenParam("rsv010svSisetuFreeFromH", form.getRsv010svSisetuFreeFromH());
         cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMi", form.getRsv010svSisetuFreeFromMi());
         cmn999Form.addHiddenParam("rsv010svSisetuFreeToY", form.getRsv010svSisetuFreeToY());
         cmn999Form.addHiddenParam("rsv010svSisetuFreeToMo", form.getRsv010svSisetuFreeToMo());
         cmn999Form.addHiddenParam("rsv010svSisetuFreeToD", form.getRsv010svSisetuFreeToD());
         cmn999Form.addHiddenParam("rsv010svSisetuFreeToH", form.getRsv010svSisetuFreeToH());
         cmn999Form.addHiddenParam("rsv010svSisetuFreeToMi", form.getRsv010svSisetuFreeToMi());
         cmn999Form.addHiddenParam("rsv010svSisetuKbn", form.getRsv010svSisetuKbn());
         cmn999Form.addHiddenParam("rsv010svGrpNarrowDown", form.getRsv010svGrpNarrowDown());
         cmn999Form.addHiddenParam("rsv010svSisetuSmoky", form.getRsv010svSisetuSmoky());
         cmn999Form.addHiddenParam("rsv010svSisetuChere", form.getRsv010svSisetuChere());
         cmn999Form.addHiddenParam("rsv010svSisetuTakeout", form.getRsv010svSisetuTakeout());
         cmn999Form.addHiddenParam("rsv010InitFlg", form.getRsv010InitFlg());
         cmn999Form.addHiddenParam("rsv010SiborikomiFlg", form.getRsv010SiborikomiFlg());
         req.setAttribute("cmn999Form", cmn999Form);

         forward = map.findForward("gf_msg");
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
     private boolean __canUseSmlConf(Rsv320Form form, HttpServletRequest req, Connection con)
     throws SQLException {

         //プラグイン設定を取得する
         PluginConfig pconfig
             = getPluginConfigForMain(getPluginConfig(req), con);
         CommonBiz cmnBiz = new CommonBiz();
         //ショートメールは利用可能か判定
         return cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig);
     }

     /**
      * <br>[機  能] ログ作成
      * <br>[解  説]
      * <br>[備  考]
      * @param form アクションフォーム
      * @param req リクエスト
      * @return ActionForward
      * @throws SQLException SQLエラー
      */
     private String __getLogMessage(
         Rsv320Form form,
         HttpServletRequest req) throws SQLException {

         GsMessage gsMsg = new GsMessage(req);
         String msg = "";

         //ショートメール通知設定
         msg += "[" + gsMsg.getMessage("cmn.sml.notification.setting") + "]";
         if (form.getRsv320SmailSendKbn() == GSConstReserve.AUTH_ADMIN_USER) {
             msg += gsMsg.getMessage("cmn.set.the.admin") + "\r\n";
             if (form.getRsv320SmailSend() == GSConstReserve.RSU_ADM_SMAIL_SEND_NO) {
                 msg += gsMsg.getMessage("cmn.noset");
             } else if (form.getRsv320SmailSend() == GSConstReserve.RSU_ADM_SMAIL_SEND_OK) {
                 msg += gsMsg.getMessage("cmn.setting.do");
             }
         } else if (form.getRsv320SmailSendKbn() == GSConstReserve.AUTH_ALL_USER) {
             msg += gsMsg.getMessage("cmn.set.eachuser");
         }

         return msg;
     }

}