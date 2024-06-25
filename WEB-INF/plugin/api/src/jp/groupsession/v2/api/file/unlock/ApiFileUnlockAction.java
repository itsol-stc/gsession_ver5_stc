package jp.groupsession.v2.api.file.unlock;

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
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイルのロック解除を行うWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileUnlockAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiFileUnlockAction.class);

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

        ApiFileUnlockForm thisForm = (ApiFileUnlockForm) form;

        //ファイル管理プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstFile.PLUGIN_ID_FILE, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstFile.PLUGIN_ID_FILE));
            return null;
        }
        //入力チェック
        ActionErrors errors = thisForm.validateFileLock(con, reqMdl, gsMsg);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }


        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);
        //ディレクトリSID
        int dirSid = NullDefault.getInt(thisForm.getFdrSid(), 0);
        int usrSid = umodel.getUsrsid();
        FileDirectoryModel model = null;
        boolean commitFlg = false;
        try {
            //最新のバージョンを取得
            FileDirectoryDao dao = new FileDirectoryDao(con);
            model = dao.getNewDirectory(dirSid);
            int version = model.getFdrVersion();

            //カレント情報取得
            fileBiz.updateLockKbn(dirSid, version, GSConstFile.LOCK_KBN_OFF, usrSid);

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("ファイルロック解除に失敗", e);
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
        sb.append(fileBiz.getDirctoryPath(
                NullDefault.getInt(thisForm.getFdrSid(), -1)));

        fileBiz.outPutApiLog(umodel.getUsrsid(), this.getClass().getCanonicalName(),
                getInterMessage(req, "cmn.change"),
                GSConstLog.LEVEL_TRACE, sb.toString());

        //ルートエレメントResult
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
