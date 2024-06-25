package jp.groupsession.v2.api.file.update;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.IUseTempdirApi;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイルの更新を行うWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileUpdateAction extends AbstractApiAction
implements IUseTempdirApi {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiFileUpdateAction.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "fileupdate";

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");

        //ファイル管理プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstFile.PLUGIN_ID_FILE, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstFile.PLUGIN_ID_FILE));
            return null;
        }
        RequestModel reqMdl = getRequestModel(req);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);
        ApiFileUpdateBiz biz = new ApiFileUpdateBiz(reqMdl, con);
        ApiFileUpdateForm thisForm = (ApiFileUpdateForm) form;
        ApiFileUpdateParamModel param = new ApiFileUpdateParamModel();
        param.setParam(thisForm);

        GsMessage gsMsg = new GsMessage(reqMdl);
        CommonBiz cmnBiz = new CommonBiz();

        //入力チェック
        ActionErrors errors = thisForm.validateFileUpdate(
                                 con, reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }

        //ファイルをロックする。
        biz.fileLock(NullDefault.getInt(thisForm.getFdrSid(), -1), umodel.getUsrsid());


        //テンポラリディレクトリ
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);
        //アプリケーションルートパス
        String appRootPath = getAppRootPath();
        //プラグイン設定取得
        PluginConfig plconf = getPluginConfig(req);
        PluginConfig pconfig = getPluginConfigForMain(plconf, con);
        boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        boolean commitFlg = false;
        String logTargetDirPath = null;
        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);
        try {

            //テンポラリディレクトリに保存する。
            FormFile formFile = thisForm.getUploadFile();
            boolean fileExistFlg =
                    formFile != null && !StringUtil.isNullZeroString(formFile.getFileName());
            if (fileExistFlg) {
                biz.setTempFile(tempDir, formFile);
            }

            //更新処理
            logTargetDirPath = fileBiz.getDirctoryPath(
                    NullDefault.getInt(thisForm.getFdrSid(), -1));
            biz.registerData(
                    param,
                    tempDir,
                    cntCon,
                    appRootPath,
                    plconf,
                    smailPluginUseFlg);

            //ロックを解除する。
            ApiFileUpdateBiz lockbiz = new ApiFileUpdateBiz(reqMdl, con);
            lockbiz.fileUnlock(NullDefault.getInt(thisForm.getFdrSid(), -1));

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;

        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        //ログ出力処理
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "cmn.target"));
        sb.append("] ");
        sb.append(logTargetDirPath);
        String logEditDirPath = fileBiz.getDirctoryPath(
                NullDefault.getInt(thisForm.getFdrSid(), -1));
        if (!logTargetDirPath.equals(logEditDirPath)) {
            sb.append("\r\n");
            sb.append("[");
            sb.append(gsMsg.getMessage(req, "fil.fil060kn.3"));
            sb.append("] ");
            sb.append(logEditDirPath);
        }
        fileBiz.outPutApiLog(umodel.getUsrsid(), this.getClass().getCanonicalName(),
                getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_TRACE, sb.toString());

        //テンポラリディレクトリのファイルを削除する
        temp.deleteTempPath(getRequestModel(req),
                GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

        //ルートエレメントResultSet
        Element result = new Element("Result");
        Document doc = new Document(result);
        if (commitFlg) {
            result.addContent("OK");
        } else {
            result.addContent("NG");
        }

        log__.debug("createXml end");
        return doc;
    }

}
