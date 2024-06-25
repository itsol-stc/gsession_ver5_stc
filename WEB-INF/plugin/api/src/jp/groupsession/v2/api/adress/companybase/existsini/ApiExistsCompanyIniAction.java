package jp.groupsession.v2.api.adress.companybase.existsini;

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
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.adress.companybase.dao.ApiCompanyBaseDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
/**
 * <br>[機  能] WEB API アドレス帳 企業名の存在するイニシャル取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiExistsCompanyIniAction extends AbstractApiAction {
    /**ロガー*/
    private static Log log__ = LogFactory.getLog(ApiExistsCompanyIniAction.class);
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
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
        ApiCompanyBaseDao dao = new ApiCompanyBaseDao(con);

        List<String> list = dao.getCompanyInitialList();
        for (String data : list) {
            Element result = new Element("Result");
            resultSet.addContent(result);

            result.addContent(_createElement("AcoSini", data));
        }
        return doc;
    }

}
