package jp.groupsession.v2.api.user.position;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;
/**
 *
 * <br>[機  能] API ユーザ 役職一覧取得
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiUserPositionListAction extends AbstractApiAction {

    /**ロガー*/
    private static Log log__ = LogFactory.getLog(ApiUserPositionListAction.class);

    @Override
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");

        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        CmnPositionDao dao = new CmnPositionDao(con);

        List<CmnPositionModel> list = dao.getPosList(false);
        if (list != null) {
            for (CmnPositionModel aps : list) {
                Element result = new Element("Result");
                resultSet.addContent(result);

                result.addContent(_createElement("PosSid", aps.getPosSid()));
                result.addContent(_createElement("PosName", aps.getPosName()));
                result.addContent(_createElement("PosBiko", aps.getPosBiko()));
                result.addContent(_createElement("PosSort", aps.getPosSort()));
            }
        }
        resultSet.setAttribute("TotalCount"
                , String.valueOf(resultSet.getContentSize()));

        log__.debug("createXml end");

        return doc;
    }

}
