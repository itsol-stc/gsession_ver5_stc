package jp.groupsession.v2.api.webmail.aclist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] WEBメールのアカウント一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiAclistAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiAclistAction.class);

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

        //WEBメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstWebmail.PLUGIN_ID_WEBMAIL));
            return null;
        }
        //ResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        int usid = umodel.getUsrsid();

        //アカウント一覧を取得する。
        try {
            ApiAclistDao accountDao = new ApiAclistDao(con);
            List<ApiAclistModel> accountList = accountDao.getAccountList(usid);
            if (accountList != null && !accountList.isEmpty()) {
                int[] wacSidList = new int[accountList.size()];
                for (int i = 0; i < wacSidList.length; i++) {
                    wacSidList[i] = accountList.get(i).getWacSid();
                }


                for (ApiAclistModel wmlAccountData : accountList) {
                   //Result
                    Element result = new Element("Result");
                    resultSet.addContent(result);
                    __setWmlElement(result, wmlAccountData);
                }
            }
        } catch (SQLException e) {
            log__.error("webmailのアカウント一覧取得に失敗", e);
        }

        return doc;
    }

    /**
     * <br>[機  能] webmailアカウント一覧をXMLのresult属性にセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param result エレメント
     * @param accountData WmlAccountModel
     * @throws Exception 実行例外
     */
    private void __setWmlElement(Element result, ApiAclistModel accountData)
            throws Exception {

        //WacSid
        result.addContent(_createElement("WacSid", accountData.getWacSid()));

        //WacName
        result.addContent(_createElement("WacName", accountData.getWacName()));

    }
}
