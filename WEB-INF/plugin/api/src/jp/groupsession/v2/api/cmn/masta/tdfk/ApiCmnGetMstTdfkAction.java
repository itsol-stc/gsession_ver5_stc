package jp.groupsession.v2.api.cmn.masta.tdfk;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.cmn.getholiday.ApiCmnGetHolidayAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;
/**
 * <br>[機  能] WEB API 都道府県マスタ取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCmnGetMstTdfkAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiCmnGetHolidayAction.class);

    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        CmnTdfkDao dao = new CmnTdfkDao(con);
        Map<Integer, CmnTdfkModel> tdfkMap = dao.getTdfkMap();
        Collection<CmnTdfkModel> colection = tdfkMap.values();
        Iterator<CmnTdfkModel> it = colection.iterator();
        while (it.hasNext()) {
            Element result = new Element("Result");
            resultSet.addContent(result);
            CmnTdfkModel data = it.next();
            result.addContent(_createElement("TdfSid", data.getTdfSid()));
            result.addContent(_createElement("TdfName", data.getTdfName()));
        }
        return doc;
    }

}
