package jp.groupsession.v2.tcd.tcd150kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.tcd150.Tcd150Form;

/**
 * <br>[機  能] タイムカード CSVインポート確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd150knAction extends AbstractGsAction {
    
    /** テンポラリディレクトリID*/
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
        Tcd150knForm thisForm = (Tcd150knForm)form;
        ActionForward forward = null;

        if (cmd.equals("commit")) {
            //確定ボタン押下時
            forward = __doImport(map, thisForm, req, res, con);
        } else if (cmd.equals("download")) {
            //ダウンロード処理
            forward = __doDownload(map, thisForm, req, res, con);
        } else if (cmd.equals("back_to_import_input")) {
            //戻るボタン押下時
            forward = map.findForward("tcd150knback");
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
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(
            ActionMapping map,
            Tcd150Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Tcd150knBiz biz = new Tcd150knBiz();
        Tcd150knParamModel paramMdl = new Tcd150knParamModel();
        String tempDir = biz._getTempDir(reqMdl, TEMP_DIRECTORY_ID);
        //使用権限のチェック
        if (!form.insertKengen(reqMdl, con)) {
            return  getSubmitErrorPage(map, req);
        }
        //再入力チェック
        ActionErrors errors = form.validateCheck(map, getRequestModel(req), tempDir, con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        paramMdl.setParam(form);
        biz._setInitData(paramMdl, reqMdl, con, TEMP_DIRECTORY_ID);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] ダウンロードを行う
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
    private ActionForward __doDownload(
            ActionMapping map,
            Tcd150Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, IOToolsException {

        RequestModel reqMdl = getRequestModel(req);
        Tcd150knBiz biz = new Tcd150knBiz();
        String tempDir = biz._getTempDir(reqMdl, TEMP_DIRECTORY_ID);
        String fileId = biz._getImportFileName(tempDir);

        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }

        String selectFile = fileId + "file";
        String fullPath = tempDir + selectFile;

        try {
            //オブジェクトファイルを取得
            ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
            Object fObj = objFile.load();
            Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
            String fileName = fMdl.getFileName();
            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);
            //ファイルのダウンロード
            TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * <br>[機  能] CSVファイルのインポートを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return アクションフォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doImport(
            ActionMapping map,
            Tcd150knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        //2重投稿チェック
        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);
        Tcd150knBiz biz = new Tcd150knBiz();

        String tempDir = biz._getTempDir(reqMdl, TEMP_DIRECTORY_ID);
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        
        //使用権限のチェック
        if (!form.insertKengen(reqMdl, con)) {
            return  getSubmitErrorPage(map, req);
        }
        
        //入力チェック
        ActionErrors errors = form.validateCheck(map, reqMdl, tempDir, con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return map.getInputForward();
        }
        
        Tcd150knParamModel paramMdl = new Tcd150knParamModel();
        paramMdl.setParam(form);

        boolean commit = false;
        try {
            //セッションユーザSID取得
            BaseUserModel umodel = getSessionUserModel(req);
            int userSid = umodel.getUsrsid();

            //システム日付取得
            UDate now = new UDate();

            int targetSid = Integer.parseInt(paramMdl.getTcd150SltUser());
            
            //ディスク容量計算マイナス分計算
            Tcd150knDiskSize ds = new Tcd150knDiskSize(con, reqMdl, targetSid);
            ds._deleteDiskSize(tempDir);
            //インポート
            Tcd150knImportCsv imp =
                new Tcd150knImportCsv(con, reqMdl, userSid, now, targetSid);
            long num = imp._importCsv(tempDir);
            //タイトル分減算
            num--;
            
            //ディスク容量計算プラス分計算
            ds._insertDiskSize();

            GsMessage gsMsg = new GsMessage();
            /** メッセージ インポート **/
            String strImport = gsMsg.getMessage(req, "cmn.import");

            //ログ出力処理
            TimecardBiz tcdBiz = new TimecardBiz();
            tcdBiz.outPutTimecardLog(map, reqMdl, con, strImport,
                    GSConstLog.LEVEL_INFO, "[count]" + num);

            commit = true;

            //完了画面遷移
            return __doImportComp(map, form, req, res, con);

        } catch (Exception e) {
            throw e;
        } finally {

            //テンポラリディレクトリのファイル削除を行う
            temp.deleteTempPath(getRequestModel(req),
                    GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID);

            if (commit) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] タイムカードインポート完了後の画面遷移設定
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
                                          Tcd150knForm form,
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
        //タイムカードデータ
        String textTimecardData = gsMsg.getMessage(req, "tcd.202");
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", textTimecardData));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("cmd", "ok");

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
