package jp.groupsession.v2.tcd.tcd180;

import java.io.File;
import java.io.IOException;
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
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardAdminAction;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.excel.TimeCardXlsParametarModel;

/**
 * <br>[機  能] タイムカード 勤務表フォーマット登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd180Action extends AbstractTimecardAdminAction {

    /** テンポラリディレクトリID */
    private static final String TEMP_DIRECTORY_ID = "tcd180";
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd180Action.class);


    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Tcd180Form thisForm = (Tcd180Form) form;
        ActionForward forward = null;

        //OKボタン押下
        if (cmd.equals("tcd180confirm")) {
            forward = __doOk(map, thisForm, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("tcd180back")) {
            forward = __doBack(map, thisForm, req, res, con);
        //添付ファイルコンボ削除
        } else if (cmd.equals("deleteTemp")) {
            forward = __doDeleteTemp(map, thisForm, req, res, con);
        //現在登録されているフォーマットダウンロード
        } else if (cmd.equals("tcd180_format")) {
            forward = __doFormatDownLoad(map, thisForm, req, res, con);
        //フォーマット作成方法についてダウンロード
        } else if (cmd.equals("how_to_make_format")) {
            forward = __doHowToDownLoad(req, res);
        //フォーマット例ファイルダウンロード
        } else if (cmd.equals("tcd180_example")) {
            forward = __doSampleDownLoad(map, req, res, thisForm, con);
        //確認画面から戻るボタン押下
        } else if (cmd.equals("tcd180knBack")) {
            forward = __doDsp(map, thisForm, req, res, con);
        //初期表示
        } else {
            thisForm.setTcd180InitFlg(true);
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行例外
     * @throws IOToolsException 取込みファイル操作時例外
     * @throws IOException
     * @throws TempFileException
     */
    private ActionForward __doInit(
            ActionMapping map,
            Tcd180Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, IOToolsException, TempFileException, IOException {

        //テンポラリディレクトリの削除,生成を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);
        temp.createTempDir(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 画面表示を行う
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
     * @throws IOException
     * @throws TempFileException
     */
    private ActionForward __doDsp(ActionMapping map,
                                   Tcd180Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con) throws 
    SQLException, IOToolsException, TempFileException, IOException {

        con.setAutoCommit(true);
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);

        RequestModel reqMdl = getRequestModel(req);
        Tcd180ParamModel paramMdl = new Tcd180ParamModel();
        paramMdl.setParam(form);

        //初期表示情報を画面にセットする
        Tcd180Biz biz = new Tcd180Biz(reqMdl);
        biz._setInitData(paramMdl, con, tempDir);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 各種チェックを行い、インポート登録確認画面に遷移する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doOk(
            ActionMapping map,
            Tcd180Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);

        //ファイルチェック
        ActionErrors errors = form.validateCheck(map, reqMdl, tempDir, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        //トランザクショントークンの保存
        saveToken(req);

        return map.findForward("tcd180confirm");
    }

    /**
     * <br>[機  能] 戻るボタン押下時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return アクションフォワード
     */
    private ActionForward __doBack(
            ActionMapping map,
            Tcd180Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) {

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリの削除
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(reqMdl, GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);

        //タイムカード一覧画面に遷移
        return map.findForward("tcd180back");
    }

    /**
     * <br>[機  能] 添付ファイル削除ボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException
     * @throws TempFileException
     */
    private ActionForward __doDeleteTemp(ActionMapping map, Tcd180Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) 
                    throws IOToolsException, SQLException, TempFileException, IOException {
      //テンポラリディレクトリ内ファイルの削除
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);
        temp.createTempDir(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] フォーマットサンプルをダウンロード
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param req リクエスト
     * @param res レスポンス
     * @param form フォーム
     * @param con コネクション
     * @return null
     * @throws Exception ダウンロード時例外
     */
    private ActionForward __doSampleDownLoad(ActionMapping map, HttpServletRequest req,
            HttpServletResponse res, Tcd180Form form, Connection con)
        throws Exception {
        
        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();

        //プラグイン固有のテンポラリパス取得
        RequestModel reqMdl = getRequestModel(req);
        TimecardBiz tcdCmnBiz = new TimecardBiz();
        String outTempDir = tcdCmnBiz.getTempDir(reqMdl, TEMP_DIRECTORY_ID);

        //勤務表出力
        TimeCardXlsParametarModel parmModel = new TimeCardXlsParametarModel();

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg2 = gsMsg.getMessage("cmn.export");

        Tcd180Biz biz = new Tcd180Biz(reqMdl);
        int kansan = biz._getKansan(con);

        String fileName = "workReportFormat.xlsx";
        if (kansan == GSConstTimecard.TIMECARD_SINSU[1]) {
            fileName = "workReportFormat60.xlsx";
        }
        String outBookTmpName = fileName.substring(0, fileName.length() - 5) 
                + GSConstCommon.ENDSTR_SAVEFILE;
        parmModel.setOutBookName(fileName);
        parmModel.setOutBookTmpName(outBookTmpName);
        parmModel.setAppRootPath(appRootPath);
        parmModel.setOutTempDir(outTempDir);
        parmModel.setCon(con);

        TimecardBiz timecardBiz = new TimecardBiz(reqMdl);
        timecardBiz.createTimeCardHina(parmModel, "tcd180");
        try {
            String outFilePath = IOTools.setEndPathChar(outTempDir) + outBookTmpName;
            TempFileUtil.downloadAtachment(req, res, outFilePath, fileName, Encoding.UTF_8);

            //TEMPディレクトリ削除
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(getRequestModel(req),
                    GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);
            temp.createTempDir(getRequestModel(req),
                    GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);
            
            //ログ出力
            TimecardBiz cBiz = new TimecardBiz(reqMdl);
            cBiz.outPutTimecardLog(map, reqMdl, con, msg2, GSConstLog.LEVEL_INFO,
                    fileName);
        } catch (Exception e) {
            log__.error("エクセル勤務表出力の出力に失敗", e);
        }
        log__.debug("end");
        return null;
    }
    
    
    /**
     * <br>[機  能] フォーマット作成方法をダウンロード
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @return null
     * @throws Exception ダウンロード時例外
     */
    private ActionForward __doHowToDownLoad(HttpServletRequest req,
            HttpServletResponse res) throws Exception {
        String fileName = GSConstTimecard.SAMPLE_INSTRUCTIONS;

        StringBuilder buf = new StringBuilder();
        buf.append(getAppRootPath());
        buf.append(File.separator);
        buf.append("WEB-INF");
        buf.append(File.separator);
        buf.append("plugin");
        buf.append(File.separator);
        buf.append(GSConstTimecard.PLUGIN_ID_TIMECARD);
        buf.append(File.separator);
        buf.append("file");
        buf.append(File.separator);
        buf.append(fileName);
        String fullPath = buf.toString();
        TempFileUtil.downloadAtachment(req, res, fullPath,
                                        "フォーマット作成方法について.pdf",
                                        Encoding.UTF_8);
        return null;
    }
    /**
     * <br>[機  能] 現在登録されているフォーマットをダウンロード
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return null
     * @throws Exception ダウンロード時例外
     */
    private ActionForward __doFormatDownLoad(ActionMapping map,
            Tcd180Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);

        //アプリケーションのルートパス
        String appRootPath = getAppRootPath();
        RequestModel reqMdl = getRequestModel(req);
        Tcd180ParamModel paramMdl = new Tcd180ParamModel();
        paramMdl.setParam(form);
        //初期表示情報を画面にセットする
        Tcd180Biz biz = new Tcd180Biz(reqMdl);
        CmnBinfModel cbMdl = null;
        cbMdl = biz._getDownloadData(paramMdl, con, tempDir, appRootPath);
        paramMdl.setFormData(form);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msgState = gsMsg.getMessage("cmn.download");
        TimecardBiz cBiz = new TimecardBiz(reqMdl);
        cBiz.outPutTimecardLog(
                map,
                reqMdl,
                con,
                msgState,
                GSConstLog.LEVEL_INFO,
                cbMdl.getBinFileName());
        JDBCUtil.closeConnectionAndNull(con);
        TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(),
                Encoding.UTF_8);
        return null;
    }

}
