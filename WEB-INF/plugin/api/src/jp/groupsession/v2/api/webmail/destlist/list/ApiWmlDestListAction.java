package jp.groupsession.v2.api.webmail.destlist.list;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.model.base.WmlDestlistModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] WEBメール送信先リスト一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWmlDestListAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlDestListAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param aForm フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        //WEBメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstWebmail.PLUGIN_ID_WEBMAIL));
            return null;
        }

        List<WmlDestlistModel> list = null;

        try {
            WebmailDao  wmlDao = new WebmailDao(con);
            list = wmlDao.getDestList(umodel.getUsrsid());
        } catch (SQLException e) {
            log__.error("WEBメール送信先リスト一覧の取得に失敗", e);
        }

        //Result
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        Integer resultCnt = 0;
        if (list != null) {
            for (WmlDestlistModel data : list) {
                Element result = new Element("Result");
                resultSet.addContent(result);
                result.addContent(_createElement("wdlSid",   data.getWdlSid()));
                result.addContent(_createElement("wdlName",  data.getWdlName()));
                result.addContent(_createElement("wdlBiko",  data.getWdlBiko()));
            }
            resultCnt = list.size();
        }
        resultSet.setAttribute("Count", Integer.toString(resultCnt));
        return doc;
    }

}
