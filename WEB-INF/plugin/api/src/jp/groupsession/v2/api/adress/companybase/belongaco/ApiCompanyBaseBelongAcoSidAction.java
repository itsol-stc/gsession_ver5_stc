package jp.groupsession.v2.api.adress.companybase.belongaco;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

/**
 * <br>[機  能] WEB API アドレス帳 会社拠点情報取得のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCompanyBaseBelongAcoSidAction extends AbstractApiAction {
    /** ロガークラス*/
    private static Log log__ = LogFactory.getLog(ApiCompanyBaseBelongAcoSidAction.class);
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        //アドレス帳プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstAddress.PLUGIN_ID_ADDRESS, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstAddress.PLUGIN_ID_ADDRESS));
            return null;
        }

        ApiCompanyBaseBelongAcoSidForm myForm = (ApiCompanyBaseBelongAcoSidForm) form;
        ActionErrors err = myForm.validateCheck();
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        //会社拠点情報
        AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
        List<AdrCompanyBaseModel> baseList =
                companyBaseDao.getCompanyBaseList(Integer.parseInt(myForm.getAcoSid()));

        resultSet.setAttribute("TotalCount", Integer.toString(baseList.size()));

        for (AdrCompanyBaseModel baseModel : baseList) {
            Element result = new Element("Result");
            resultSet.addContent(result);

            result.addContent(_createElement("AbaSid", baseModel.getAbaSid()));
            result.addContent(_createElement("AbaType", baseModel.getAbaType()));
            result.addContent(_createElement("AbaName", baseModel.getAbaName()));
            result.addContent(_createElement("TdfSid", baseModel.getTdfSid()));
            result.addContent(_createElement("AbaPostno1", baseModel.getAbaPostno1()));
            result.addContent(_createElement("AbaPostno2", baseModel.getAbaPostno2()));
            result.addContent(_createElement("AbaAddr1", baseModel.getAbaAddr1()));
            result.addContent(_createElement("AbaAddr2", baseModel.getAbaAddr2()));
            result.addContent(_createElement("AbaBiko", baseModel.getAbaBiko()));

        }

        return doc;
    }

}
