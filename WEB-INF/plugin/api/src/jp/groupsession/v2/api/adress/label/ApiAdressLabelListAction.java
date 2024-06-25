package jp.groupsession.v2.api.adress.label;

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
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
/**
 *
 * <br>[機  能] ラベル一覧取得API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiAdressLabelListAction extends AbstractApiAction {

    /**ロガー*/
    private static Log log__ = LogFactory.getLog(ApiAdressLabelListAction.class);

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

        AdrLabelDao aldDao = new AdrLabelDao(con);

        List<AdrLabelModel> aldList = aldDao.select();
        if (aldList != null) {
            for (AdrLabelModel ald : aldList) {
                Element result = new Element("Result");
                resultSet.addContent(result);

                result.addContent(_createElement("AlbSid", ald.getAlbSid()));
                result.addContent(_createElement("AlbName", ald.getAlbName()));
                result.addContent(_createElement("AlbBiko", ald.getAlbBiko()));
                result.addContent(_createElement("AlbSort", ald.getAlbSort()));
                result.addContent(_createElement("AlcSid", ald.getAlcSid()));
            }
        }
        resultSet.setAttribute("TotalCount"
                , String.valueOf(resultSet.getContentSize()));

        log__.debug("createXml end");

        return doc;
    }

}
