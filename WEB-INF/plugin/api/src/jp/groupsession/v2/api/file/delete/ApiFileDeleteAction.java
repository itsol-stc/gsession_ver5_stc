package jp.groupsession.v2.api.file.delete;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイルの削除を行うWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileDeleteAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiFileDeleteAction.class);

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

        ApiFileDeleteForm thisForm = (ApiFileDeleteForm) form;
        RequestModel reqMdl = getRequestModel(req);
        ApiFileDeleteBiz biz = new ApiFileDeleteBiz(reqMdl, con);
        GsMessage gsMsg = new GsMessage(reqMdl.getLocale());
        //ファイル管理プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstFile.PLUGIN_ID_FILE, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstFile.PLUGIN_ID_FILE));
            return null;
        }

        //入力チェック
        ActionErrors errors = thisForm.validateFileUpload(con, gsMsg, reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }

        boolean commitFlg = false;
        String logMsgFilePath = "";
        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);
        try {
            logMsgFilePath = fileBiz.getDirctoryPath(
                    NullDefault.getInt(thisForm.getFdrSid(), -1), true);
            //ディレクトリを削除する。
            biz.deleteFile(
                    NullDefault.getInt(thisForm.getFdrSid(), -1),
                    umodel.getUsrsid(),
                    thisForm.getComment());

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
        sb.append(logMsgFilePath);

        fileBiz.outPutApiLog(umodel.getUsrsid(), this.getClass().getCanonicalName(),
                getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_TRACE, sb.toString());

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
