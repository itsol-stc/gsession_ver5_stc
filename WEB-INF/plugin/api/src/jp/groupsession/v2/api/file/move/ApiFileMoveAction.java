package jp.groupsession.v2.api.file.move;

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
import jp.groupsession.v2.fil.fil090.Fil090Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] フォルダ・ファイルの移動を行うWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileMoveAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiFileMoveAction.class);

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

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        ApiFileMoveForm thisForm = (ApiFileMoveForm) form;

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        //ファイル管理プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstFile.PLUGIN_ID_FILE, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstFile.PLUGIN_ID_FILE));
            return null;
        }

        //入力チェック
        ActionErrors errors = thisForm.validateFileMove(con, gsMsg,
                umodel.getUsrsid(), getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }
        boolean commitFlg = false;
        String beforeDirPath = "";
        String afterDirPath = "";
        try {
            int dirSid = NullDefault.getInt(thisForm.getFdrSid(), -1);
            int parentSid = NullDefault.getInt(thisForm.getFdrParentSid(), -1);
            beforeDirPath = filBiz.getDirctoryPath(dirSid);
            Fil090Biz fil090Biz = new Fil090Biz(getRequestModel(req), con);
            //移動処理
            fil090Biz.moveDir(
                    dirSid,
                    parentSid,
                    0,
                    umodel.getUsrsid());
            afterDirPath = filBiz.getDirctoryPath(dirSid);
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
        StringBuilder sbLogMsg = new StringBuilder();
        sbLogMsg.append("[");
        sbLogMsg.append(gsMsg.getMessage(req, "fil.154"));
        sbLogMsg.append("] ");
        sbLogMsg.append(beforeDirPath);
        sbLogMsg.append("\r\n");
        sbLogMsg.append("[");
        sbLogMsg.append(gsMsg.getMessage(req, "fil.155"));
        sbLogMsg.append("] ");
        sbLogMsg.append(afterDirPath);

        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);
        fileBiz.outPutApiLog(umodel.getUsrsid(), this.getClass().getCanonicalName(),
                getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_TRACE, sbLogMsg.toString());

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
