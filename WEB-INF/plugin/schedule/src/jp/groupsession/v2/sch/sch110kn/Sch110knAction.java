package jp.groupsession.v2.sch.sch110kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュールインポート確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Sch110knAction extends AbstractScheduleAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch110knAction.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "sch110";
    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     *
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("downLoad")) {
            log__.debug("取り込みCSVファイルダウンロード");
                return true;
        }
        return false;
    }
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
         Sch110knForm schform = (Sch110knForm) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         //インポートボタン押下
         if (cmd.equals("doImport")) {
             log__.debug("インポートボタン押下");
             forward = __doImport(map, schform, req, res, con);
         //インポート完了
         } else if (cmd.equals("comp")) {
             log__.debug("インポート完了");
             forward = __doBack(map, schform, req, res, con);
         //戻るボタン押下
         } else if (cmd.equals("back_to_import_input")) {
             log__.debug("戻るボタン押下");
             forward = map.findForward("back_to_import_input");
         //添付ダウンロード
         } else if (cmd.equals("downLoad")) {
             forward = __doDownLoad(map, schform, req, res, con);
         //初期表示処理
         } else {
             log__.debug("初期表示処理");
             forward = __doInit(map, schform, req, res, con);
         }

         return forward;
     }

     /**
      * <br>[機  能] 初期表示を行う
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @return ActionForward
     * @throws Exception CSV情報取得時例外
      */
     private ActionForward __doInit(ActionMapping map,
                                     Sch110knForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws Exception {

         con.setAutoCommit(true);
         Sch110knBiz biz = new Sch110knBiz(con, getRequestModel(req));

         //テンポラリディレクトリパスを取得
         GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
         String tempDir = temp.getTempPath(getRequestModel(req),
                 GSConstReserve.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);

         //再入力チェック
         ActionErrors errors = form.validateCheck(map, getRequestModel(req), tempDir, con);
         if (errors.size() > 0) {
             addErrors(req, errors);
             return map.getInputForward();
         }

         Sch110knParamModel paramMdl = new Sch110knParamModel();
         paramMdl.setParam(form);
         //取込みファイル名称取得
         biz.setImportFileName(paramMdl, tempDir);
         //登録対象名称取得
         biz.setImportTargetName(paramMdl);
         paramMdl.setFormData(form);

         return map.getInputForward();
     }

     /**
      * <br>[機  能] インポート実行
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws Exception 実行例外
      * @return アクションフォーワード
      */
     private ActionForward __doImport(ActionMapping map,
                                       Sch110knForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
         throws Exception {

         if (!isTokenValid(req, true)) {
             log__.info("２重投稿");
             return getSubmitErrorPage(map, req);
         }

         //テンポラリディレクトリパスを取得
         GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
         String tempDir = temp.getTempPath(getRequestModel(req),
                 GSConstReserve.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);

         //再入力チェック
         ActionErrors errors = form.validateCheck(
                 map, getRequestModel(req), tempDir, con);
         if (errors.size() > 0) {
             log__.debug("取込み前最チェックエラー");
             addErrors(req, errors);
             return map.findForward("back_to_import_input");
         }

         //取込み処理
         con.setAutoCommit(false);
         boolean commit = false;

         RequestModel reqMdl = getRequestModel(req);
         try {

             //セッションユーザSID取得
             BaseUserModel umodel = getSessionUserModel(req);
             int userSid = umodel.getUsrsid();

             //システム日付取得
             UDate now = new UDate();

             //採番用コネクション取得
             MlCountMtController cntCon = getCountMtController(req);

             //登録対象を取得
             int id = NullDefault.getInt(form.getSch110SltUser(), -1);
             int kbn = -1;
             if (id == -1) {
                 id = NullDefault.getInt(form.getSch110SltGroup(), -1);
                 kbn = GSConstSchedule.USER_KBN_GROUP;
             } else {
                 kbn = GSConstSchedule.USER_KBN_USER;
             }

             //インポート
             Sch110ImportCsv imp =
                 new Sch110ImportCsv(con, userSid, now, cntCon, id, kbn, getRequestModel(req));
             long num = imp.importCsv(tempDir);
             //タイトル分減算
             num--;

             GsMessage gsMsg = new GsMessage();
             /** メッセージ インポート **/
             String strImport = gsMsg.getMessage(req, "cmn.import");

             //ログ出力処理
             SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
             schBiz.outPutLog(
                     map, req, res,
                     strImport, GSConstLog.LEVEL_INFO, "[count]" + num);

             commit = true;

             //完了画面遷移
             return __doImportComp(map, form, req, res, con);

         } catch (Exception e) {
             log__.error("施設CSVの取り込みに失敗しました。" + e);
             throw e;
         } finally {

             //テンポラリディレクトリのファイル削除を行う
             temp.deleteTempPath(getRequestModel(req),
                     GSConstReserve.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);

             if (commit) {
                 con.commit();
             } else {
                 JDBCUtil.rollback(con);
             }
         }
     }

     /**
      * <br>[機  能] 施設インポート完了後の画面遷移設定
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map マップ
      * @param form フォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @return ActionForward フォワード
      * @throws Exception 実行時例外
      */
     private ActionForward __doImportComp(ActionMapping map,
                                           Sch110knForm form,
                                           HttpServletRequest req,
                                           HttpServletResponse res,
                                           Connection con) throws Exception {

         Cmn999Form cmn999Form = new Cmn999Form();
         cmn999Form.setType(Cmn999Form.TYPE_OK);
         cmn999Form.setIcon(Cmn999Form.ICON_INFO);
         cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

         //OKボタンクリック時遷移先
         ActionForward forwardOk = map.findForward("importComp");
         cmn999Form.setUrlOK(forwardOk.getPath());
         MessageResources msgRes = getResources(req);
         GsMessage gsMsg = new GsMessage();
         //スケジュールデータ
         String textScheduleData = gsMsg.getMessage(req, "schedule.src.74");
         cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", textScheduleData));

         //画面パラメータをセット
         cmn999Form.addHiddenParam("cmd", "ok");

         cmn999Form.addHiddenParam("sch110SltGroup", form.getSch110SltGroup());
         cmn999Form.addHiddenParam("sch110SltUser", form.getSch110SltUser());

         cmn999Form.addHiddenParam("dspMod", form.getDspMod());
         cmn999Form.addHiddenParam("listMod", form.getListMod());
         cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
         cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
         cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
         cmn999Form.addHiddenParam("sch010SelectUsrSid", form.getSch010SelectUsrSid());
         cmn999Form.addHiddenParam("sch010SelectUsrKbn", form.getSch010SelectUsrKbn());
         cmn999Form.addHiddenParam("sch010SelectDate", form.getSch010SelectDate());
         cmn999Form.addHiddenParam("sch020SelectUsrSid", form.getSch020SelectUsrSid());
         cmn999Form.addHiddenParam("sch030FromHour", form.getSch030FromHour());
         //一覧画面用
         cmn999Form.addHiddenParam("sch100PageNum", form.getSch100PageNum());
         cmn999Form.addHiddenParam("sch100Slt_page1", form.getSch100Slt_page1());
         cmn999Form.addHiddenParam("sch100Slt_page2", form.getSch100Slt_page2());
         cmn999Form.addHiddenParam("sch100OrderKey1", form.getSch100OrderKey1());
         cmn999Form.addHiddenParam("sch100SortKey1", form.getSch100SortKey1());
         cmn999Form.addHiddenParam("sch100OrderKey2", form.getSch100OrderKey2());
         cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SortKey2());

         cmn999Form.addHiddenParam("sch100SvSltGroup", form.getSch100SvSltGroup());
         cmn999Form.addHiddenParam("sch100SvSltUser", form.getSch100SvSltUser());
         cmn999Form.addHiddenParam("sch100SvSltStartYearFr", form.getSch100SvSltStartYearFr());
         cmn999Form.addHiddenParam("sch100SvSltStartMonthFr", form.getSch100SvSltStartMonthFr());
         cmn999Form.addHiddenParam("sch100SvSltStartDayFr", form.getSch100SvSltStartDayFr());
         cmn999Form.addHiddenParam("sch100SvSltStartYearTo", form.getSch100SvSltStartYearTo());
         cmn999Form.addHiddenParam("sch100SvSltStartMonthTo", form.getSch100SvSltStartMonthTo());
         cmn999Form.addHiddenParam("sch100SvSltStartDayTo", form.getSch100SvSltStartDayTo());
         cmn999Form.addHiddenParam("sch100SvSltEndYearFr", form.getSch100SvSltEndYearFr());
         cmn999Form.addHiddenParam("sch100SvSltEndMonthFr", form.getSch100SvSltEndMonthFr());
         cmn999Form.addHiddenParam("sch100SvSltEndDayFr", form.getSch100SvSltEndDayFr());
         cmn999Form.addHiddenParam("sch100SvSltEndYearTo", form.getSch100SvSltEndYearTo());
         cmn999Form.addHiddenParam("sch100SvSltEndMonthTo", form.getSch100SvSltEndMonthTo());
         cmn999Form.addHiddenParam("sch100SvSltEndDayTo", form.getSch100SvSltEndDayTo());
         cmn999Form.addHiddenParam("sch100SvKeyWordkbn", form.getSch100SvKeyWordkbn());
         cmn999Form.addHiddenParam("sch100SvKeyValue", form.getSch100SvKeyValue());
         cmn999Form.addHiddenParam("sch100SvOrderKey1", form.getSch100SvOrderKey1());
         cmn999Form.addHiddenParam("sch100SvSortKey1", form.getSch100SvSortKey1());
         cmn999Form.addHiddenParam("sch100SvOrderKey2", form.getSch100SvOrderKey2());
         cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SvSortKey2());
         cmn999Form.addHiddenParam("sch100SvBgcolor", form.getSch100SvBgcolor());

         cmn999Form.addHiddenParam("sch100SltGroup", form.getSch100SltGroup());
         cmn999Form.addHiddenParam("sch100SltUser", form.getSch100SltUser());
         cmn999Form.addHiddenParam("sch100SltStartYearFr", form.getSch100SltStartYearFr());
         cmn999Form.addHiddenParam("sch100SltStartMonthFr", form.getSch100SltStartMonthFr());
         cmn999Form.addHiddenParam("sch100SltStartDayFr", form.getSch100SltStartDayFr());
         cmn999Form.addHiddenParam("sch100SltStartYearTo", form.getSch100SltStartYearTo());
         cmn999Form.addHiddenParam("sch100SltStartMonthTo", form.getSch100SltStartMonthTo());
         cmn999Form.addHiddenParam("sch100SltStartDayTo", form.getSch100SltStartDayTo());
         cmn999Form.addHiddenParam("sch100SltEndYearFr", form.getSch100SltEndYearFr());
         cmn999Form.addHiddenParam("sch100SltEndMonthFr", form.getSch100SltEndMonthFr());
         cmn999Form.addHiddenParam("sch100SltEndDayFr", form.getSch100SltEndDayFr());
         cmn999Form.addHiddenParam("sch100SltEndYearTo", form.getSch100SltEndYearTo());
         cmn999Form.addHiddenParam("sch100SltEndMonthTo", form.getSch100SltEndMonthTo());
         cmn999Form.addHiddenParam("sch100SltEndDayTo", form.getSch100SltEndDayTo());
         cmn999Form.addHiddenParam("sch100KeyWordkbn", form.getSch100KeyWordkbn());
         cmn999Form.addHiddenParam("sch010searchWord", form.getSch010searchWord());
         cmn999Form.addHiddenParam("sch100SvSearchTarget", form.getSch100SvSearchTarget());
         cmn999Form.addHiddenParam("sch100SearchTarget", form.getSch100SearchTarget());
         cmn999Form.addHiddenParam("sch100Bgcolor", form.getSch100Bgcolor());
         cmn999Form.addHiddenParam("sch100CsvOutField", form.getSch100CsvOutField());

         req.setAttribute("cmn999Form", cmn999Form);

         return map.findForward("gf_msg");
     }

     /**
      * <br>[機  能] 戻るボタン落下時の画面遷移を行う
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @return ActionForward
      */
     private ActionForward __doBack(ActionMapping map,
                                     Sch110knForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con) {

         ActionForward forward = null;

         String dspMod = form.getDspMod();
         String listMod = form.getListMod();
         if (listMod.equals(GSConstSchedule.DSP_MOD_LIST)) {
             forward = map.findForward("110kn_list");
             return forward;
         }
         if (dspMod.equals(GSConstSchedule.DSP_MOD_WEEK)) {
             forward = map.findForward("110kn_week");
         } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MONTH)) {
             forward = map.findForward("110kn_month");
         } else if (dspMod.equals(GSConstSchedule.DSP_MOD_DAY)) {
             forward = map.findForward("110kn_day");
         }
         return forward;
     }

     /**
      * <br>[機  能] 添付ファイルダウンロードの処理
      * <br>[解  説]
      * <br>[備  考]
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行例外
      * @throws Exception 実行時例外
      * @return ActionForward
      */
     private ActionForward __doDownLoad(ActionMapping map,
                                         Sch110knForm form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
         throws SQLException, Exception {
         //テンポラリディレクトリパスを取得
         RequestModel reqMdl = getRequestModel(req);
         GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
         String tempDir = temp.getTempPath(getRequestModel(req),
                 GSConstReserve.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);
         Sch110knBiz biz = new Sch110knBiz(con, getRequestModel(req));
         //取込みファイル名称取得
         String fileId = biz.getImportFileName(tempDir);
         log__.debug("tempDir==>" + tempDir);
         log__.debug("fileId==>" + fileId);
         //オブジェクトファイルを取得
         ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
         Object fObj = objFile.load();
         Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
         //添付ファイル保存用のパスを取得する(フルパス)
         String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
         filePath = IOTools.replaceFileSep(filePath);

         GsMessage gsMsg = new GsMessage();
         /** メッセージ ダウンロード **/
         String download = gsMsg.getMessage(req, "cmn.download");

         //ログ出力処理
         SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
         schBiz.outPutLog(
                 map, req, res,
                 download, GSConstLog.LEVEL_INFO, fMdl.getFileName());

         //時間のかかる処理の前にコネクションを破棄
         JDBCUtil.closeConnectionAndNull(con);
         //ファイルをダウンロードする
         TempFileUtil.downloadAtachment(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);



         return null;
     }
}