package jp.groupsession.v2.api.adress.position;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.model.AdrPositionModel;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
/**
 *
 * <br>[機  能] API アドレス帳 役職一覧取得
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiAdressPositionListAction extends AbstractApiAction {

    /**ロガー*/
    private static Log log__ = LogFactory.getLog(ApiAdressPositionListAction.class);

    @Override
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        //アドレス帳プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstAddress.PLUGIN_ID_ADDRESS, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstAddress.PLUGIN_ID_ADDRESS));
            return null;
        }

        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        AdrPositionDao apsDao = new AdrPositionDao(con);

        List<AdrPositionModel> apsList = apsDao.select();
        if (apsList != null) {
            for (AdrPositionModel aps : apsList) {
                Element result = new Element("Result");
                resultSet.addContent(result);

                result.addContent(_createElement("ApsSid", aps.getApsSid()));
                result.addContent(_createElement("ApsName", aps.getApsName()));
                result.addContent(_createElement("ApsBiko", aps.getApsBiko()));
                result.addContent(_createElement("ApsSort", aps.getApsSort()));
            }
        }
        resultSet.setAttribute("TotalCount"
                , String.valueOf(resultSet.getContentSize()));

        log__.debug("createXml end");

        return doc;
    }

}
