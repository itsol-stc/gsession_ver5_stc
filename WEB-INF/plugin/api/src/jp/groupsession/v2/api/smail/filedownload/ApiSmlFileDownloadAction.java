package jp.groupsession.v2.api.smail.filedownload;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.IUseTempdirApi;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイルのダウンロードを行うWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考] ショートメールの添付ファイルのみダウンロード可能です。
 *
 * @author JTS
 */
public class ApiSmlFileDownloadAction extends AbstractApiAction
implements IUseTempdirApi {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlFileDownloadAction.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "smlfiledownload";

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

        //ショートメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstSmail.PLUGIN_ID_SMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstSmail.PLUGIN_ID_SMAIL));
            return null;
        }
        ApiSmlFileDownloadForm thisForm = (ApiSmlFileDownloadForm) form;
        CommonBiz cmnBiz = new CommonBiz();
        Long binSid = NullDefault.getLong(thisForm.getBinSid(), -1);
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        SmlCommonBiz biz = new SmlCommonBiz(getRequestModel(req));

        //入力チェック
        ActionErrors errors = thisForm.validateCmnDownload(
                con, gsMsg, biz.getDefaultAccount(con, umodel.getUsrsid()));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }

        String tempDir = "";
        boolean okFlg = false;
        try {

            //DBよりファイル情報を取得する。
            CmnBinfModel binModel = cmnBiz.getBinInfo(con, binSid,
                    GroupSession.getResourceManager().getDomain(req));
            if (binModel == null
                    || binModel.getBinJkbn() == GSConst.JTKBN_DELETE
                    || binModel.getBinFilekbn() != GSConst.FILEKBN_COMMON) {
                //ファイルが存在しないか、削除されているか場合
                __errorFilenotFound(req, errors);
                return null;
            }

            //テンポラリディレクトリの削除を行う
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(getRequestModel(req),
                    GSConstSmail.PLUGIN_ID_SMAIL, TEMP_DIRECTORY_ID);

            //テンポラリディレクトリパスを取得
            tempDir = temp.getTempPath(getRequestModel(req),
                    GSConstSmail.PLUGIN_ID_SMAIL, TEMP_DIRECTORY_ID);

            //現在日付の文字列(YYYYMMDD)
            UDate now = new UDate();
            String dateStr = now.getDateString();

            //ファイルをテンポラリディレクトリに格納する。
            String filePath =
                cmnBiz.saveTempFile(dateStr, binModel, getAppRootPath(), tempDir, 1);

            if (StringUtil.isNullZeroString(filePath)) {
                //ファイルが存在しないか、削除されている場合
                __errorFilenotFound(req, errors);
                return null;
            }

            //ログ出力処理
            SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
            smlBiz.outPutApiLog(req, con, umodel.getUsrsid(), this.getClass().getCanonicalName(),
                    getInterMessage(req, "cmn.download"),
                    GSConstLog.LEVEL_INFO, binModel.getBinFileName());

            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルのダウンロード
            TempFileUtil.downloadAtachment(
                    req, res, filePath, binModel.getBinFileName(), Encoding.UTF_8);

            okFlg = true;
        } catch (SQLException e) {
            log__.error("SQL実行時エラー", e);
        } catch (IOToolsException e) {
            log__.error("ファイルのダウンロードに失敗", e);
        } finally {

            //テンポラリディレクトリの削除を行う
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(getRequestModel(req),
                    GSConstSmail.PLUGIN_ID_SMAIL, TEMP_DIRECTORY_ID);
        }

        //ルートエレメントResultSet
        Element result = new Element("Result");
        Document doc = new Document(result);
        if (okFlg) {
            result.addContent("OK");
        } else {
            result.addContent("NG");
        }

        log__.debug("createXml end");
        return doc;
    }

    /**
     * ファイルが存在しない場合のエラーを設定する。
     * @param req リクエスト
     * @param errors アクションエラー
     */
    private void __errorFilenotFound(HttpServletRequest req, ActionErrors errors) {

        //ファイルが存在しないか、削除されている場合
        ActionMessage msg = new ActionMessage("error.input.notfound.file");
        StrutsUtil.addMessage(errors, msg, "binSid");
        addErrors(req, errors);
    }
}
