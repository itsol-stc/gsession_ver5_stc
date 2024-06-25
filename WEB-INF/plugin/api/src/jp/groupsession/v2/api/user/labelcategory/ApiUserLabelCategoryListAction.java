package jp.groupsession.v2.api.user.labelcategory;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrCategoryDao;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrCategoryModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;
/**
 *
 * <br>[機  能] API ユーザラベルカテゴリ一覧取得
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiUserLabelCategoryListAction extends AbstractApiAction {

    /**ロガー*/
    private static Log log__ = LogFactory.getLog(ApiUserLabelCategoryListAction.class);

    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        CmnLabelUsrCategoryDao dao = new CmnLabelUsrCategoryDao(con);

        List<CmnLabelUsrCategoryModel> list = dao.select();

        if (list != null) {
            for (CmnLabelUsrCategoryModel lbl : list) {
                Element result = new Element("Result");
                resultSet.addContent(result);

                result.addContent(_createElement("LucSid", lbl.getLucSid()));
                result.addContent(_createElement("LucName", lbl.getLucName()));
                result.addContent(_createElement("LucBiko", lbl.getLucBiko()));
                result.addContent(_createElement("LucSort", lbl.getLucSort()));
            }
        }
        resultSet.setAttribute("TotalCount"
                , String.valueOf(resultSet.getContentSize()));
        log__.debug("createXml end");
        return doc;

    }

}
