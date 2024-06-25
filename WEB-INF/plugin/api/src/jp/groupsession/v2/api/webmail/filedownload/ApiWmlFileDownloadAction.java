package jp.groupsession.v2.api.webmail.filedownload;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.IUseTempdirApi;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;

/**
 * <br>[機  能] ファイルのダウンロードを行うWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考] WEBメールの添付ファイルのみダウンロード可能です。
 *
 * @author JTS
 */
public class ApiWmlFileDownloadAction extends AbstractApiAction
implements IUseTempdirApi {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlFileDownloadAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param aform フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm aform, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");

        //WEBメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstWebmail.PLUGIN_ID_WEBMAIL));
            log__.info("FILE DL LISENCE ERR");
            return null;
        }

        ApiWmlFileDownloadForm form = (ApiWmlFileDownloadForm) aform;
        RequestModel reqMdl = getRequestModel(req);
        GsMessage     gsMsg = new GsMessage(reqMdl);

        long wmlSid  = form.getWmlSid();
        long binSid  = form.getWtfSid();
        int  userSid = getSessionUserSid(req);

        ActionErrors errors = form.validateCmnDownload(con, gsMsg);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }

        // メール閲覧可否チェック
        WmlDao wmlDao = new WmlDao(con);
        if (!wmlDao.canReadMail(wmlSid, userSid)) {
            // メール閲覧権限が無い為、アクセスエラー
            ActionMessage msg = new ActionMessage("search.notfound.tdfkcode",
                    gsMsg.getMessage(GSConstApi.TEXT_TEMP_FILE));
            StrutsUtil.addMessage(errors, msg, "wmlSid");
            addErrors(req, errors);
            return null;
        }

        boolean okFlg = false;

        WmlBiz wmlBiz = new WmlBiz();
        WmlTempfileModel fileMdl = wmlBiz.getTempFileData(con, wmlSid, binSid, reqMdl);

        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        //テンポラリディレクトリを削除する
        tempPathUtil.deleteTempPath(reqMdl, GSConst.PLUGINID_API, "wmldownload", "html");

        String tempDir = tempPathUtil.getTempPath(reqMdl, 
                GSConst.PLUGINID_API, "wmldownload", "html");

        if (fileMdl != null
         && fileMdl.getWtfJkbn() != GSConstWebmail.WMD_STATUS_DUST) {
            //ログ出力
            log__.debug("ログ出力　開始");
            ActionMapping map = new ActionMapping();
            map.setType(this.getClass().getCanonicalName());
            wmlBiz.outPutLog(map, reqMdl, con,
                    getInterMessage(req, "cmn.download"),
                    GSConstLog.LEVEL_INFO,
                    fileMdl.getWtfFileName(),
                    String.valueOf(binSid),
                    GSConstWebmail.WML_LOG_FLG_DOWNLOAD);
            log__.debug("ログ出力　完了");

            JDBCUtil.closeConnectionAndNull(con);
            log__.debug("コネクション切断");


            log__.debug("添付ファイルのダウンロード処理　実行");
            try {
                //ファイルをダウンロードする
                String charset = null;
                if (!StringUtil.isNullZeroString(fileMdl.getWtfCharset())) {
                    charset = fileMdl.getWtfCharset();
                }

                if (fileMdl.getWtfFileName().equals(GSConstWebmail.HTMLMAIL_FILENAME)) {
                    wmlBiz.downloadHtmlForWebmail(
                            req, res, fileMdl, getAppRootPath(), charset, tempDir, true);
                } else {
                    TempFileUtil.downloadAtachmentForWebmail(
                            req, res, fileMdl, getAppRootPath(), charset);
                }
                fileMdl.removeTempFile();
                log__.info("添付ファイルのダウンロードが完了しました。");
                okFlg = true;
            } catch (Throwable e) {
               log__.error("添付ファイルのダウンロードに失敗", e);
               con = null;
               con = getConnection(req);
            }
        } else {
            log__.error("添付ファイルが存在しないため、ダウンロードに失敗しました。");
            //throw new Exception("添付ファイルが存在しないため、ダウンロードに失敗しました。");
            //ファイルが存在しないか、削除されている場合
            //ActionMessage msg = new ActionMessage("error.input.notfound.file");
            ActionMessage msg = new ActionMessage("search.notfound.tdfkcode",
                    gsMsg.getMessage(GSConstApi.TEXT_TEMP_FILE));
            StrutsUtil.addMessage(errors, msg, "binSid");
            addErrors(req, errors);
            return null;
        }

        //ルートエレメントResultSet
        Element result = new Element("Result");
        Document doc = new Document(result);
        if (okFlg) {
            result.addContent("OK");
        } else {
            result.addContent("NG");
        }
        return doc;
    }
}
