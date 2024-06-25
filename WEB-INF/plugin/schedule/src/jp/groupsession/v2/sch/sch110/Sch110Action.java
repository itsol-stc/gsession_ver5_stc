package jp.groupsession.v2.sch.sch110;

import java.io.File;
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

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュールインポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Sch110Action extends AbstractScheduleAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch110Action.class);
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

        if (cmd.equals("sch110_sample")) {
            log__.debug("サンプルCSVファイルダウンロード");
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
         Sch110Form schform = (Sch110Form) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         //インポートボタン押下
         if (cmd.equals("110_import")) {
             log__.debug("インポートボタン押下");
             forward = __doImportCheck(map, schform, req, res, con);
         //サンプルダウンロードリンククリック
         } else if (cmd.equals("sch110_sample")) {
             log__.debug("サンプルダウンロードリンククリック");
             __doSampleDownLoad(req, res);

             GsMessage gsMsg = new GsMessage();
             /** メッセージ ダウンロード **/
             String download = gsMsg.getMessage(req, "cmn.download");

             //ログ出力処理
             SchCommonBiz schBiz = new SchCommonBiz(con, getRequestModel(req));
             schBiz.outPutLog(
                     map, req, res,
                     download, GSConstLog.LEVEL_INFO, "sample.xls");
         //戻るボタン押下
         } else if (cmd.equals("back")) {
             log__.debug("戻るボタン押下");
             //テンポラリディレクトリの削除を行う
             GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
             temp.deleteTempPath(getRequestModel(req),
                     GSConstReserve.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);
             forward = __doBack(map, schform, req, res, con);
         //確認で戻るボタン押下
         } else if (cmd.equals("back_to_import_input")) {
             log__.debug("確認画面で戻るボタン押下");
             forward = __doDsp(map, schform, req, res, con);
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
      * @throws SQLException SQL実行例外
      * @return ActionForward
      * @throws IOToolsException 取込みファイル操作時例外
      */
     private ActionForward __doInit(ActionMapping map,
                                     Sch110Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws SQLException, IOToolsException {

         con.setAutoCommit(true);
         //テンポラリディレクトリの削除を行う
         GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
         temp.deleteTempPath(getRequestModel(req),
                 GSConstReserve.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);
         temp.createTempDir(getRequestModel(req),
                 GSConstReserve.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);

         return __doDsp(map, form, req, res, con);
     }

     /**
      * <br>[機  能] インポートボタン押下時処理
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @return ActionForward
     * @throws Exception インポート処理時例外
      */
     private ActionForward __doImportCheck(ActionMapping map,
                                            Sch110Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
         throws Exception {

         //テンポラリディレクトリパスを取得
         GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
         String tempDir = temp.getTempPath(getRequestModel(req),
                 GSConstReserve.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);

         ActionErrors errors = form.validateCheck(map, getRequestModel(req), tempDir, con);
         if (!errors.isEmpty()) {
             addErrors(req, errors);
             return __doDsp(map, form, req, res, con);
         }

         return map.findForward("doImport");
     }

     /**
      * <br>[機  能] 再表示を行う
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行例外
      * @throws IOToolsException ファイルアクセス時例外
      * @return ActionForward
      */
     private ActionForward __doDsp(ActionMapping map,
                                    Sch110Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
         throws SQLException, IOToolsException {
         con.setAutoCommit(true);
         //テンポラリディレクトリパスを取得
         GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
         String tempDir = temp.getTempPath(getRequestModel(req),
                 GSConstReserve.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);

         //初期表示情報を画面にセットする
         Sch110Biz biz = new Sch110Biz(getRequestModel(req), con);
         Sch110ParamModel paramMdl = new Sch110ParamModel();

         // グループメンバー存在チェック
         BaseUserModel usModel = getSessionUserModel(req);
         int sessionUsrSid = usModel.getUsrsid();
         paramMdl.setParam(form);
         ActionErrors errors = biz.validateGroupMemberExistCheck(paramMdl, sessionUsrSid);
         paramMdl.setFormData(form);

         if (errors.size() > 0) {
             log__.debug("入力エラー");
             addErrors(req, errors);
         }

         paramMdl.setParam(form);
         biz.setInitData(paramMdl, tempDir);
         paramMdl.setFormData(form);

         //トランザクショントークン設定
         this.saveToken(req);
         con.setAutoCommit(false);
         return map.getInputForward();
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
                                     Sch110Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con) {

         ActionForward forward = null;

         String dspMod = form.getDspMod();
         String listMod = form.getListMod();
         if (listMod.equals(GSConstSchedule.DSP_MOD_LIST)) {
             forward = map.findForward("110_list");
             return forward;
         }
         if (dspMod.equals(GSConstSchedule.DSP_MOD_WEEK)) {
             forward = map.findForward("110_week");
         } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MONTH)) {
             forward = map.findForward("110_month");
         } else if (dspMod.equals(GSConstSchedule.DSP_MOD_DAY)) {
             forward = map.findForward("110_day");
         }
         return forward;
     }

     /**
      * <br>[機  能] サンプルCSVをダウンロード
      * <br>[解  説]
      * <br>[備  考]
      * @param req リクエスト
      * @param res レスポンス
      * @throws Exception ダウンロード時例外
      */
     private void __doSampleDownLoad(HttpServletRequest req, HttpServletResponse res)
         throws Exception {

         String fileName = GSConstSchedule.SAMPLE_SCH_CSV_NAME;

         StringBuilder buf = new StringBuilder();
         buf.append(getAppRootPath());
         buf.append(File.separator);
         buf.append(GSConstSchedule.PLUGIN_ID_SCHEDULE);
         buf.append(File.separator);
         buf.append("doc");
         buf.append(File.separator);
         buf.append(fileName);
         String fullPath = buf.toString();
         TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);
     }
}