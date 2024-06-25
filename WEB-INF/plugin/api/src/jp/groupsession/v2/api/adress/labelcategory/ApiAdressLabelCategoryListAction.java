package jp.groupsession.v2.api.adress.labelcategory;

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
import jp.groupsession.v2.adr.dao.AdrLabelCategoryDao;
import jp.groupsession.v2.adr.model.AdrLabelCategoryModel;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
/**
 *
 * <br>[機  能] ラベルカテゴリ一覧取得API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiAdressLabelCategoryListAction extends AbstractApiAction {
    /**ロガー*/
    private static Log log__ = LogFactory.getLog(ApiAdressLabelCategoryListAction.class);

    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        //アドレス帳プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstAddress.PLUGIN_ID_ADDRESS, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstAddress.PLUGIN_ID_ADDRESS));
            return null;
        }

        AdrLabelCategoryDao alcDao = new AdrLabelCategoryDao(con);

        List<AdrLabelCategoryModel> alcList = alcDao.select();
        if (alcList != null) {
            for (AdrLabelCategoryModel alc : alcList) {
                Element result = new Element("Result");
                resultSet.addContent(result);

                result.addContent(_createElement("AlcSid", alc.getAlcSid()));
                result.addContent(_createElement("AlcName", alc.getAlcName()));
                result.addContent(_createElement("AlcBiko", alc.getAlcBiko()));
                result.addContent(_createElement("AlcSort", alc.getAlcSort()));
            }
        }
        resultSet.setAttribute("TotalCount"
                , String.valueOf(resultSet.getContentSize()));
        log__.debug("createXml end");
        return doc;

    }

}
