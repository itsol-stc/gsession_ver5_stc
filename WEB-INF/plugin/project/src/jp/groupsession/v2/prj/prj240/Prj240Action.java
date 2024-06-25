package jp.groupsession.v2.prj.prj240;

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
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] ユーザ選択画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考] 各プラグインで使用されるユーザ選択機能です。
 *
 * @author JTS
 */
public class Prj240Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj240Action.class);
    /** 呼び出し元フォームパラメータ保存ファイル名 */
    private static final String FORM_FILENAME = "prj240Modelfile";
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "prj240";


    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("START_Prj240");
        ActionForward forward = null;

        Prj240Form thisForm = (Prj240Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        if (cmd.equals("doOk")) {
            log__.debug("OKボタン押下");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("doBack")) {
            log__.debug("呼び出し元画面へ遷移");
            String url = thisForm.getPrj240BackUrl();
            CommonBiz cmnBiz = new CommonBiz();
            if (!setFileEdit(getTempPath(req), thisForm, req)) {
                return getSubmitErrorPage(map, req);
            }
            PluginConfig pconfig = getPluginConfig(req);
            forward = cmnBiz.saveForwardUrl(map, pconfig, url);
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(getRequestModel(req),
                    GSConstCommon.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);

            if (thisForm.getPrj240userSidOld() != null) {
                for (String sid : thisForm.getPrj240userSidOld()) {
                    req.setAttribute(thisForm.getPrj240paramName(), sid);
                }
            }
        } else {
            saveFormData(getTempPath(req), thisForm, req);
            forward = map.getInputForward();
        }

        log__.debug("END_Prj240");
        return forward;
    }

    /**
     * <br>[機  能] OKボタン押下時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOException 呼び出し元画面のフォームパラメータの保存に失敗
     * @throws IOToolsException 呼び出し元画面のフォームパラメータの保存に失敗
     */
    private ActionForward __doOk(
        ActionMapping map,
        Prj240Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOException, IOToolsException {

        //追加済みユーザが選択されているかを確認する
        String[] userList = form.getPrj240userSid();
        if (userList == null || userList.length <= 0) {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg
                = new ActionMessage("error.select.required.text", form.getPrj240FunctionName());
            StrutsUtil.addMessage(errors, msg, "error.select.required.text");
            this.addErrors(req, errors);
            return map.getInputForward();
        }
        if (!setFileEdit(getTempPath(req), form, req)) {
            log__.info("不正なアクセス");
            return getSubmitErrorPage(map, req);
        }
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstCommon.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);

        CommonBiz cmnBiz = new CommonBiz();
        PluginConfig pconfig = getPluginConfig(req);
        String url = form.getPrj240BackUrl();
        return cmnBiz.saveForwardUrl(map, pconfig, url);
    }

    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param rootDir ルートディレクトリ
     * @param form フォーム
     * @param req リクエストモデル
     * @return boolean true=ファイル有り(正常に処理終了)、false=ファイルが存在しない
     * @throws IOException フォームパラメータの設定に失敗
     * @throws IOToolsException フォームパラメータの設定に失敗
     */
    public boolean setFileEdit(String rootDir, Prj240Form form, HttpServletRequest req)
    throws IOException, IOToolsException {

        String dirPath = __getTempDirPath(form, getRequestModel(req));

        if (dirPath.length() == 0) {
            return false;
        }
        if (!IOTools.isFileCheck(dirPath, FORM_FILENAME, false)) {
            return false;
        }

        setFormData(rootDir, form, req);
        removeFormDataFile(rootDir, form, req);
        return true;
    }

    /**
     * <br>[機  能] 保存した呼び出し元画面のフォームパラメータをリクエストに設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootDir ルートディレクトリ
     * @param form フォーム
     * @param req リクエストモデル
     * @throws IOException フォームパラメータの設定に失敗
     * @throws IOToolsException フォームパラメータの設定に失敗
     */
    public void setFormData(String rootDir, Prj240Form form, HttpServletRequest req)
    throws IOException, IOToolsException {

        File tempFile = __getTempFilePath(rootDir, form, req);

        ObjectFile objFile = new ObjectFile(tempFile.getParent(), tempFile.getName());
        Object formData = objFile.load();

        req.setAttribute(form.getPrj240FormKey(), formData);
    }


    /**
     * <br>[機  能] 呼び出し元画面のフォームパラメータをテンポラリディレクトリに保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootDir ルートディレクトリ
     * @param form フォーム
     * @param req リクエストモデル
     * @throws IOException フォームパラメータの保存に失敗
     * @throws IOToolsException フォームパラメータの保存に失敗
     */
    public void saveFormData(String rootDir, Prj240Form form, HttpServletRequest req)
    throws IOException, IOToolsException {
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstCommon.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID,
                form.getPrj240FormKey());
        temp.createTempDir(getRequestModel(req),
                GSConstCommon.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID,
                form.getPrj240FormKey());

        Object prj240ModelData = req.getAttribute(form.getPrj240FormKey());

        File tempFile = __getTempFilePath(rootDir, form, req);
        IOTools.isDirCheck(tempFile.getParent(), true);

        ObjectFile objFile = new ObjectFile(tempFile.getParent(), tempFile.getName());
        objFile.save(prj240ModelData);
    }

    /**
     * <br>[機  能] 呼び出し元画面のフォームパラメータファイルを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootDir ルートディレクトリ
     * @param form フォーム
     * @param req リクエスト
     * @throws IOException フォームパラメータの設定に失敗
     * @throws IOToolsException フォームパラメータの設定に失敗
     */
    public void removeFormDataFile(String rootDir,
                                   Prj240Form form,
                                   HttpServletRequest req)
                                   throws IOException, IOToolsException {

        //テンポラリディレクトリの削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstCommon.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);
    }

    /**
     * <br>[機  能] 呼び出し元フォームパラメータ保存ファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootDir ルートディレクトリ
     * @param form アクションフォーム
     * @param req リクエスト
     * @return テンポラリディレクトリパス
     */
    private File __getTempFilePath(String rootDir,
                                  Prj240Form form,
                                  HttpServletRequest req) {

        StringBuilder filePath = new StringBuilder("");
        String tempPath = __getTempDirPath(form, getRequestModel(req));
        if (tempPath.length() != 0) {
            filePath.append(__getTempDirPath(form, getRequestModel(req)));
            filePath.append(FORM_FILENAME);
        }
        return new File(filePath.toString());
    }

    /**
     * <br>[機  能] テンポラリディレクトリのパスを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param reqMdl リクエストモデル
     * @return String テンポラリディレクトリのパス
     */
    private String __getTempDirPath(Prj240Form form, RequestModel reqMdl) {

        String filePath = "";
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        filePath = temp.getTempPath(reqMdl,
                GSConstCommon.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID,
                form.getPrj240FormKey());
        return filePath;
    }
}
