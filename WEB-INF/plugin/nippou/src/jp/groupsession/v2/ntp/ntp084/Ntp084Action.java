package jp.groupsession.v2.ntp.ntp084;

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
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.AbstractNippouAdminAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 管理者設定 インポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp084Action extends AbstractNippouAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp084Action.class);

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

        if (cmd.equals("ntp084_sample")) {
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
         RequestModel reqMdl = getRequestModel(req);
         ActionForward forward = null;
         Ntp084Form ntpform = (Ntp084Form) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         //インポートボタン押下
         if (cmd.equals("import")) {
             log__.debug("インポートボタン押下");
             forward = __doImportCheck(map, ntpform, req, res, con);
         //サンプルダウンロードリンククリック
         } else if (cmd.equals("ntp084_sample")) {
             log__.debug("サンプルダウンロードリンククリック");
             __doSampleDownLoad(req, res, con);

             GsMessage gsMsg = new GsMessage();
             /** メッセージ ダウンロード **/
             String download = gsMsg.getMessage(req, "cmn.download");
             //ログ出力処理
             NtpCommonBiz ntpBiz = new NtpCommonBiz(con, reqMdl);
             ntpBiz.outPutLog(
                     map,
                     download, GSConstLog.LEVEL_INFO, "sample_admin.csv");

         //戻るボタン押下
         } else if (cmd.equals("ktool")) {
             log__.debug("戻るボタン押下");
             forward = map.findForward("ktool");

             //テンポラリディレクトリの削除
             Ntp084Biz biz = new Ntp084Biz(getRequestModel(req));
             biz.deleteTempDir();

         //確認で戻るボタン押下
         } else if (cmd.equals("back_to_import_input")) {
             log__.debug("確認画面で戻るボタン押下");
             forward = __doDsp(map, ntpform, req, res, con);
         //画面再表示(インポートファイルのアップロード)
         } else if (cmd.equals("reDisp")) {
             log__.debug("画面再表示");
             forward = __doDsp(map, ntpform, req, res, con);
         //初期表示処理
         } else {
             log__.debug("初期表示処理");
             forward = __doInit(map, ntpform, req, res, con);
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
                                     Ntp084Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws SQLException, IOToolsException {

         //テンポラリディレクトリパスを初期化
         Ntp084Biz biz = new Ntp084Biz(getRequestModel(req));
         biz.clearTempDir();

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
                                            Ntp084Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
         throws Exception {

         //テンポラリディレクトリパスを取得
         RequestModel reqMdl = getRequestModel(req);
         Ntp084Biz biz = new Ntp084Biz(reqMdl);
         String tempDir = biz.getTempDir();

         ActionErrors errors = form.validateCheck(
                 map, reqMdl, tempDir, con);
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
                                    Ntp084Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
         throws SQLException, IOToolsException {

         //初期表示情報を画面にセットする
         Ntp084Biz biz = new Ntp084Biz(getRequestModel(req));

         Ntp084ParamModel paramMdl = new Ntp084ParamModel();
         paramMdl.setParam(form);
         biz.setInitData(paramMdl);
         paramMdl.setFormData(form);

         //トランザクショントークン設定
         this.saveToken(req);

         return map.getInputForward();
     }

     /**
      * <br>[機  能] サンプルCSVをダウンロード
      * <br>[解  説]
      * <br>[備  考]
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws Exception ダウンロード時例外
      */
     private void __doSampleDownLoad(HttpServletRequest req, HttpServletResponse res,
                                       Connection con)
         throws Exception {

         String fileName = GSConstNippou.SAMPLE_NTP_CSV_NAME_ADM;

         StringBuffer buf = new StringBuffer();
         buf.append(getAppRootPath());
         buf.append(File.separator);
         buf.append(GSConstNippou.PLUGIN_ID_NIPPOU);
         buf.append(File.separator);
         buf.append("doc");
         buf.append(File.separator);
         buf.append(fileName);
         String fullPath = buf.toString();
         TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);
     }
}