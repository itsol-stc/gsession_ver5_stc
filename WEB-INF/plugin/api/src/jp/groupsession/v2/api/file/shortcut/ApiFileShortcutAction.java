package jp.groupsession.v2.api.file.shortcut;

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
import jp.groupsession.v2.fil.fil050.Fil050Biz;
import jp.groupsession.v2.fil.fil050.Fil050ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートカットの登録・削除を行うWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileShortcutAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiFileShortcutAction.class);

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

        ApiFileShortcutForm thisForm = (ApiFileShortcutForm) form;
        GsMessage gsMsg = new GsMessage(reqMdl.getLocale());

        //ファイル管理プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstFile.PLUGIN_ID_FILE, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstFile.PLUGIN_ID_FILE));
            return null;
        }
        // ユーザSID
        int usrSid = umodel.getUsrsid();
        int addDeleteKbn = NullDefault.getInt(thisForm.getShcKbn(), -1);
        int dirSid = NullDefault.getInt(thisForm.getFdrSid(), -1);
        //アクセス権限チェック
        ActionErrors errors = thisForm.validateAccessCheck(con, reqMdl, usrSid);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }

        boolean commitFlg = false;
        try {

            Fil050Biz fil050Biz = new Fil050Biz(con, reqMdl);
            Fil050ParamModel paramMdl = new Fil050ParamModel();
            paramMdl.setFil050DirSid(thisForm.getFdrSid());
            // ログ出力用
            FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);
            String logMsgDirPath = fileBiz.getDirctoryPath(dirSid);
            if (addDeleteKbn == GSConstFile.SHORTCUT_ADD) {
                //登録
                fil050Biz.updateShortcut(paramMdl, GSConstFile.SHORTCUT_ON, umodel);
            } else {
                //削除
                fil050Biz.updateShortcut(paramMdl, GSConstFile.SHORTCUT_OFF, umodel);
            }

            //ログ出力処理

            String textEntry = "";

            if (addDeleteKbn == GSConstFile.SHORTCUT_ADD) {
                textEntry =  gsMsg.getMessage("cmn.entry");
            } else {
                textEntry = gsMsg.getMessage("cmn.delete");
            }
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(gsMsg.getMessage(req, "cmn.target"));
            sb.append("] ");
            sb.append(logMsgDirPath);
            fileBiz.outPutApiLog(umodel.getUsrsid(), this.getClass().getCanonicalName(),
                    textEntry, GSConstLog.LEVEL_TRACE, sb.toString());
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
