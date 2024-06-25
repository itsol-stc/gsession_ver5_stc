package jp.groupsession.v2.enq.enq310;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.AbstractEnqueteAction;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.csv.EnqCsvModel;
import jp.groupsession.v2.enq.csv.EnqCsvSubModel;
import jp.groupsession.v2.enq.csv.EnqCsvWriter;
import jp.groupsession.v2.enq.pdf.EnqPdfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アンケート 結果確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq310Action extends AbstractEnqueteAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq310Action.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "enq310";

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("export") || cmd.equals("pdf")) {
            return true;

        }
        return false;
    }

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        ActionForward forward = null;
        Enq310Form enq310Form = (Enq310Form) form;

        // 結果公開の閲覧権限チェック
        con.setAutoCommit(true);
        try {
            EnqCommonBiz enqBiz = new EnqCommonBiz();
            if (!enqBiz.canViewResultEnquete(
                    getRequestModel(req), con, enq310Form.getAnsEnqSid())) {
                return getSubmitErrorPage(map, req);
            }
        } finally {
            con.setAutoCommit(false);
        }

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();

        if (cmd.equals("enq310backTo020")) {
            //戻る
            log__.debug("戻る");
            if (enq310Form.getEnq970BackPage() == 1) {
                forward = map.findForward("sendManager");
            } else {
                forward = map.findForward("enqList");
            }

        } else if (cmd.equals("enq310list")) {
            //結果一覧
            log__.debug("結果一覧");
            forward = map.findForward("enq310list");

        } else if (cmd.equals("enq310detail")) {
            //結果詳細
            log__.debug("結果詳細");
            forward = map.findForward("enq310detail");

            //エクスポートボタン押下
        } else if (cmd.equals("export")) {
                log__.debug("エクスポートボタン押下");
                forward = __doExport(map, enq310Form, req, res, con);

        } else if (cmd.equals("pdf")) {
            //PDF出力
            log__.debug("PDF出力");
            forward = __doDownLoadPdf(map, enq310Form, req, res, con);

        } else {
            //初期表示処理
            log__.debug("初期表示処理");

            forward = __doInit(map, enq310Form, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                   Enq310Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException {

        con.setAutoCommit(true);
        try {
            Enq310ParamModel paramMdl = new Enq310ParamModel();
            paramMdl.setParam(form);
            Enq310Biz biz = new Enq310Biz();
            biz.setInitData(paramMdl, getRequestModel(req), con);
            paramMdl.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] PDFファイルダウンロード処理を行います。
     * <br>[解  説]
     * <br>[備  考]
     *  テンポラリディレクトリルール
     * ○ダウンロードファイル生成ディレクトリ
     *    プラグインID/セッションID/enqpdf/アンケートSID.pdf
     * @param map アクションマッピング
     * @param enq310Form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     * @throws IOException ファイルの書き出しに失敗
     * @throws IOToolsException テンポラリディレクトリの削除に失敗
     * @throws TempFileException 添付ファイル情報の取得に失敗
     * @throws Exception 実行例外
     */
    private ActionForward __doDownLoadPdf(ActionMapping map, Enq310Form enq310Form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IOException, IOToolsException, TempFileException, Exception {

        //リクエスト情報を取得
        RequestModel reqMdl = getRequestModel(req);
        //ユーザSIDを取得
        int userSid = getSessionUserSid(req);

        PluginConfig pconfig = null;
        try {
            //管理者設定を反映したプラグイン設定情報を取得
            pconfig = getPluginConfigForMain(getPluginConfig(req), con, userSid);
        } finally {
            con.setAutoCommit(false);
        }

        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String outTempDir = temp.getTempPath(getRequestModel(req),
                GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID);

        Enq310ParamModel paramMdl = new Enq310ParamModel();
        paramMdl.setParam(enq310Form);

        //PDF生成
        Enq310Biz biz = new Enq310Biz();
        EnqPdfModel pdfMdl =
                biz.createEnqPdf(paramMdl, con, userSid, appRootPath, outTempDir,
                                        pconfig, reqMdl);
        paramMdl.setFormData(enq310Form);

        String outBookName = pdfMdl.getFileName();
        String saveFileName = pdfMdl.getSvFileName();
        String outFilePath = IOTools.setEndPathChar(outTempDir) + saveFileName;
        TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);

        //TEMPディレクトリの削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID);

        //ログ出力処理
        GsMessage gsMsg = new GsMessage(reqMdl);
        EnqCommonBiz enqlBiz = new EnqCommonBiz(con);
        enqlBiz.outPutLog(map, reqMdl, gsMsg.getMessage("enq.plugin"),
                gsMsg.getMessage("cmn.pdf"), GSConstLog.LEVEL_INFO,
                gsMsg.getMessage("cmn.pdf"),
                null, GSConstEnquete.ENQ_LOG_FLG_PDF, enq310Form.getAnsEnqSid());

        return null;
    }

    /**
     * <br>[機  能] エクスポートボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     * ○ダウンロードファイル生成ディレクトリ
     *    プラグインID/セッションID/アンケートSID.csv
     * @param map アクションマッピング
     * @param enq310Form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
    * @throws Exception インポート処理時例外
     */
    private ActionForward __doExport(ActionMapping map,
                                           Enq310Form enq310Form,
                                           HttpServletRequest req,
                                           HttpServletResponse res,
                                           Connection con)
        throws Exception {

        //リクエスト情報を取得
        RequestModel reqMdl = getRequestModel(req);


        String tempDir = null;
        String fullPath = null;
        String fileName = null;

        con.setAutoCommit(true);
        try {
            //テンポラリディレクトリパスを取得
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            tempDir = temp.getTempPath(getRequestModel(req),
                    GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID);

            //CSV出力用モデルに出力値をセットする
            ArrayList<EnqCsvModel> enqCsvMdlList = new ArrayList<EnqCsvModel>();
            EnqCsvSubModel enqCsvSubMdl = new EnqCsvSubModel();

            Enq310ParamModel paramMdl = new Enq310ParamModel();
            paramMdl.setParam(enq310Form);

            Enq310Biz biz310 = new Enq310Biz();
            enqCsvMdlList = biz310.getCsvMdlList(paramMdl, con, reqMdl);
            enqCsvSubMdl = biz310.getCsvSubMdl(paramMdl, con);

            fileName = biz310.fileNameCheck(enqCsvSubMdl.getEnqTitle()) + ".csv";
            String csvFilePath = "enqCsv_" + biz310.fileNameCheck(
                    String.valueOf(enqCsvSubMdl.getEnqCsvSid())) + ".csv";
            fullPath = tempDir + csvFilePath;

            EnqCsvWriter writer = new EnqCsvWriter(
                    con, enqCsvMdlList, enqCsvSubMdl, reqMdl);
            writer.outputCsv(tempDir, csvFilePath);
        } finally {
            con.setAutoCommit(false);
        }

        //ダウンロード
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //テンポラリディレクトリの削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID);

        //ログ出力処理
        GsMessage gsMsg = new GsMessage(reqMdl);
        EnqCommonBiz enqlBiz = new EnqCommonBiz(con);
        enqlBiz.outPutLog(map, reqMdl, gsMsg.getMessage("enq.plugin"),
                gsMsg.getMessage("cmn.export"), GSConstLog.LEVEL_INFO,
                gsMsg.getMessage("cmn.export"));

        return null;
    }
}
