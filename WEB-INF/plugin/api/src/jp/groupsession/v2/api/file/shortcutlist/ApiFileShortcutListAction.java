package jp.groupsession.v2.api.file.shortcutlist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートカット一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileShortcutListAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiFileShortcutListAction.class);

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

        //ファイル管理プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstFile.PLUGIN_ID_FILE, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstFile.PLUGIN_ID_FILE));
            return null;
        }
        
        ApiFileShortcutListForm thisForm = (ApiFileShortcutListForm) form;
        GsMessage gsMsg = new GsMessage(req);
        ActionErrors errors = thisForm.validateShortcultList(con, gsMsg, reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }
        
        ArrayList<ApiFileShortcutListModel> shortcutList = null;
        ApiFileShortcutListBiz biz = new ApiFileShortcutListBiz(con, reqMdl);
        int errlKbn = thisForm.getCabinetKbn();
        try {
            shortcutList = biz.getShortcutInfoList(umodel, errlKbn);
        } catch (SQLException e) {
            log__.error("ファイル管理ショートカット一覧の取得に失敗", e);
        }

        //Result
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        Integer resultCnt = 0;
        if (shortcutList != null) {
            for (ApiFileShortcutListModel data : shortcutList) {
                Element result = new Element("Result");
                resultSet.addContent(result);
                result.addContent(_createElement("FdrSid",   data.getFdrSid()));
                result.addContent(_createElement("FdrParentSid",   data.getFdrParentSid()));
                result.addContent(_createElement("FdrName",   data.getFdrName()));
                result.addContent(_createElement("FdrKbn",   data.getFdrKbn()));
                result.addContent(_createElement("BinSid",  data.getBinSid()));
                result.addContent(_createElement("FileSize", data.getFileSize()));
                result.addContent(_createElement("FdrPath", data.getFdrPath()));
                result.addContent(_createElement("FdrEdate", data.getEditDate()));
                result.addContent(_createElement("LockKbn",  data.getFflLockKbn()));
                result.addContent(_createElement("LockUsrSid",  data.getFflLockUser()));
                result.addContent(_createElement("WriteKbn",  data.getWriteKbn()));
                result.addContent(_createElement("EditUsrKbn", data.getEditUsrKbn()));
                result.addContent(_createElement("EditUsrSid", data.getEditUsrSid()));
                result.addContent(_createElement("EditUsrName", data.getEditUsrName()));
                result.addContent(_createElement("EditUsrUkoFlg", data.getEditUsrUkoFlg()));
                result.addContent(_createElement("FcbErrlKbn", data.getCabinetKbn()));
            }
            resultCnt = Integer.valueOf(shortcutList.size());
        }
        resultSet.setAttribute("Count", Integer.toString(resultCnt));
        return doc;


    }

}
