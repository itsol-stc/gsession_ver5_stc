package jp.groupsession.v2.tcd.tcd180kn;

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
import jp.co.sjts.util.ValidateUtil;
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
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardAdminAction;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.tcd180.Tcd180Form;

/**
 * <br>[機  能] タイムカード 勤務表フォーマット登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd180knAction extends AbstractTimecardAdminAction {

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "tcd180";
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd180knAction.class);

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
        log__.debug("CMD = " + cmd);
        Tcd180knForm thisForm = (Tcd180knForm) form;
        ActionForward forward = null;

        if (cmd.equals("commit")) {
            forward = __doImport(map, thisForm, req, res, con);
        } else if (cmd.equals("download")) {
            forward = __doDownload(map, thisForm, req, res, con);
        } else if (cmd.equals("tcd180knBack")) {
            forward = map.findForward("tcd180knBack");
        } else {
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
            Tcd180Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Tcd180knBiz biz = new Tcd180knBiz(reqMdl);
        Tcd180knParamModel paramMdl = new Tcd180knParamModel();
        paramMdl.setParam(form);
        biz._setInitData(paramMdl, con, TEMP_DIRECTORY_ID);
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
            Tcd180Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, IOToolsException {

        RequestModel reqMdl = getRequestModel(req);
        Tcd180knBiz biz = new Tcd180knBiz(reqMdl);
        String tempDir = biz._getTempDir(TEMP_DIRECTORY_ID);
        String fileId = biz._getImportFileName(tempDir);

        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }

        String selectFile = fileId + GSConstCommon.ENDSTR_SAVEFILE;
        String fullPath = tempDir + selectFile;

        try {
            //オブジェクトファイルを取得
            ObjectFile objFile = new ObjectFile(tempDir,
                    fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
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
     * <br>[機  能] ファイルの登録を行う
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
            Tcd180knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        //2重投稿チェック
        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }
        RequestModel reqMdl = getRequestModel(req);
        Tcd180knBiz biz = new Tcd180knBiz(reqMdl);
        Tcd180knParamModel paramMdl = new Tcd180knParamModel();
        paramMdl.setParam(form);
        String tempDir = biz._getTempDir(TEMP_DIRECTORY_ID);
        //入力チェック
        ActionErrors errors = form.validateCheck(map, reqMdl, tempDir, con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return map.findForward("tcd180knBack");
        }

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        //アプリケーションのルートパス
        String appRoot = getAppRootPath();
        MlCountMtController cntCon = getCountMtController(req);

        boolean commit = false;
        try {
            //テンポラリディレクトリの生成を行う
            temp.createTempDir(getRequestModel(req),
                    GSConstTimecard.PLUGIN_ID_TIMECARD, TEMP_DIRECTORY_ID,
                    GSConstTimecard.TCD_TEMP_FORMAT_EDIT);

            biz._insert(paramMdl, con, appRoot, tempDir, cntCon);

            //ログ出力処理
            TimecardBiz tcdBiz = new TimecardBiz();
            String msgState = null;
            GsMessage gsMsg = new GsMessage(reqMdl);
            msgState = gsMsg.getMessage("cmn.change");
            StringBuilder sb = new StringBuilder();
            if (paramMdl.getTcd180Use() == 0) {
                sb.append("[" + gsMsg.getMessage("tcd.tcd060.06") + "]" 
                + gsMsg.getMessage("tcd.tcd180.11"));
            } else {
                sb.append("[" + gsMsg.getMessage("tcd.tcd060.06") + "]" 
                + gsMsg.getMessage("tcd.tcd180.12")
                + System.getProperty("line.separator"));
                sb.append("[" + gsMsg.getMessage("tcd.204") + "]"
                + paramMdl.getFileName());
            }
            tcdBiz.outPutTimecardLog(map, reqMdl, con, msgState,
                    GSConstLog.LEVEL_TRACE, sb.toString());
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
     * <br>[機  能] フォーマットファイル登録完了画面設定
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
                                          Tcd180knForm form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("tcd030menu");
        cmn999Form.setUrlOK(forwardOk.getPath());
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "tcd.206");
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", msg));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("cmd", "ok");
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());
        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
