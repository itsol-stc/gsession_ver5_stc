package jp.groupsession.v2.api.chat.send;

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

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.IUseTempdirApi;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] チャット投稿APIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiChatSendAction extends AbstractApiAction
implements IUseTempdirApi {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiChatSendAction.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "chatsend";

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
        //チャットプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstChat.PLUGIN_ID_CHAT, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstChat.PLUGIN_ID_CHAT));
            return null;
        }
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstChat.PLUGIN_ID_CHAT, TEMP_DIRECTORY_ID);
        ApiChatSendBiz biz = new ApiChatSendBiz(umodel.getUsrsid(), con, getRequestModel(req));
        ApiChatSendForm thisForm = (ApiChatSendForm) form;
        GsMessage gsMsg = new GsMessage(req);
        RequestModel reqMdl = getRequestModel(req);
        // 添付ファイル
        FormFile file = thisForm.getTmpFile();
        boolean fileFlg = true;
        String fileName = null;
        int    fileSize = 0;
        if (file != null) {
            fileName = file.getFileName();
            fileSize = file.getFileSize();
        }
        if (fileName == null || fileName.length() == 0 || fileSize == 0) {
            fileFlg = false;
        }
        //入力チェック
        ActionErrors errors
        = thisForm.validateChatApi(con, umodel, gsMsg, reqMdl, fileFlg);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }
        String tempDir = null;
        boolean commitFlg = false;

        try {
            //メッセージ送信
            MlCountMtController cntCon = getCountMtController(req);
            if (!StringUtil.isNullZeroStringSpace(thisForm.getBody())) {
                biz.sendMessage(thisForm, cntCon);
            }
            //添付ファイル送信
            if (fileFlg) {
                //テンポラリディレクトリパスを取得
                tempDir = temp.getTempPath(getRequestModel(req),
                        GSConstChat.PLUGIN_ID_CHAT, TEMP_DIRECTORY_ID);
                //アプリケーションのルートパス
                String appRootPath = getAppRootPath();
                biz.setTempFileAll(tempDir, thisForm);
                //登録処理
                biz.sendTempFile(thisForm, con, cntCon, appRootPath, tempDir,
                                getPluginConfig(req), getRequestModel(req));
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("メッセージの送信に失敗", e);
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
        if (fileFlg) {
            //テンポラリディレクトリのファイルを削除する
            try {
                temp.deleteTempPath(getRequestModel(req),
                        GSConstChat.PLUGIN_ID_CHAT, TEMP_DIRECTORY_ID);
            } catch (Exception e) {
                log__.warn("テンポラリディレクトリのファイル削除に失敗: " + tempDir);
                log__.warn("ファイル登録を続行します。");
            }
        }

        //Result
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


