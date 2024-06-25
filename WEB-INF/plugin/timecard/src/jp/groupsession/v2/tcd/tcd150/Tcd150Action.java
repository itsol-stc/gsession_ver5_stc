package jp.groupsession.v2.tcd.tcd150;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] タイムカード CSVインポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd150Action extends AbstractGsAction {

    /** テンポラリディレクトリID */
    private static final String TEMP_DIRECTORY_ID = "tcd150";

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
        Tcd150Form thisForm = (Tcd150Form) form;
        ActionForward forward = null;

        if (cmd.equals("import")) {
            //インポートボタン押下
            forward = __doImport(map, thisForm, req, res, con);
        } else if (cmd.equals("back")) {
            //戻るボタン押下
            forward = __doBack(map, thisForm, req, res, con);
        } else if (cmd.equals("delete")) {
            //削除ボタン押下
            forward = __doDelete(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd150_sample")) {
            //サンプルダウンロードリンククリック
            __doSampleDownLoad(req, res);
            forward = __doDsp(map, thisForm, req, res, con);
        } else if (cmd.equals("back_to_import_input")) {
            //確認画面で戻るボタン押下
           forward = __doDsp(map, thisForm, req, res, con);
        } else {
            //初期表示
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
     */
    private ActionForward __doInit(
            ActionMapping map,
            Tcd150Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, IOToolsException {

        // 表示権限チェック
        Tcd150Biz tcdBiz = new Tcd150Biz();
        if (!tcdBiz._dispKengen(getRequestModel(req), con)) {
            return  getSubmitErrorPage(map, req);
        }

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
     */
    private ActionForward __doDsp(ActionMapping map,
                                   Tcd150Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con) throws SQLException, IOToolsException {

        con.setAutoCommit(true);
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);

        RequestModel reqMdl = getRequestModel(req);
        Tcd150ParamModel paramMdl = new Tcd150ParamModel();
        paramMdl.setParam(form);

        //初期表示情報を画面にセットする
        Tcd150Biz biz = new Tcd150Biz();
        biz._setInitData(paramMdl, reqMdl, con, tempDir);
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
    private ActionForward __doImport(
            ActionMapping map,
            Tcd150Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);

        //使用権限のチェック
        if (!form.insertKengen(reqMdl, con)) {
            return  getSubmitErrorPage(map, req);
        }

        //CSVファイルチェック
        ActionErrors errors = form.validateCheck(map, reqMdl, tempDir, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        //トランザクショントークンの保存
        saveToken(req);

        return map.findForward("tcd150commit");
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
            Tcd150Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) {

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリの削除
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(reqMdl, GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);

        //タイムカード一覧画面に遷移
        return map.findForward("back");
    }

    /**
     * <br>[機  能] 削除ボタン押下時処理
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
     */
    private ActionForward __doDelete(
            ActionMapping map,
            Tcd150Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, IOToolsException {

        //テンポラリディレクトリ内ファイルの削除
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);
        temp.createTempDir(getRequestModel(req),
                GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);

        return __doDsp(map, form, req, res, con);
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

        String fileName = GSConstTimecard.SAMPLE_TCD_CSV_NAME;

        StringBuilder buf = new StringBuilder();
        buf.append(getAppRootPath());
        buf.append(File.separator);
        buf.append(GSConstTimecard.PLUGIN_ID_TIMECARD);
        buf.append(File.separator);
        buf.append("doc");
        buf.append(File.separator);
        buf.append(fileName);
        String fullPath = buf.toString();
        TempFileUtil.downloadAtachment(req, res, fullPath, "インポートサンプル.xlsx", Encoding.UTF_8);
    }

}
