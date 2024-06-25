package jp.groupsession.v2.api.enquete.countm;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.enq.EnqMainInfoMessage;
import jp.groupsession.v2.enq.GSConstEnquete;
/**
 * <br>[機  能] アンケート未回答件数を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCountMAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiCountMAction.class);

    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        //回覧板プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstEnquete.PLUGIN_ID_ENQUETE, req)) {
            addErrors(req,
                        addCantAccsessPluginError(req, null,
                                GSConstEnquete.PLUGIN_ID_ENQUETE));
            return null;
        }


        //Result
        Element result = new Element("Result");
        Document doc = new Document(result);
        EnqMainInfoMessage biz = new EnqMainInfoMessage();

        int count = 0;
        try {
            count = biz.getCountUnanswered(con, getSessionUserSid(req));
        } catch (SQLException e) {
            log__.error("未回答アンケートカウントの取得に失敗", e);
        }
        //
        log__.debug("createXml start");
        result.addContent(Integer.toString(count));
        log__.debug("createXml end");
        return doc;
    }

}
