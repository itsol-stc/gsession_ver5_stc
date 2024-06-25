package jp.groupsession.v2.api.reserve.yoyaku.search;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiRsvSisYrkSearchAction extends AbstractApiAction {
    /** ログ */
    private static Log log__ =
            LogFactory.getLog(ApiRsvSisYrkSearchAction.class);

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.AbstractApiAction
     * #createXml(org.apache.struts.action.ActionForm
     * , javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse
     * , java.sql.Connection
     * , jp.groupsession.v2.cmn.dao.BaseUserModel)
     */
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        //施設予約プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_RESERVE, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_RESERVE));
            return null;
        }
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        ApiRsvSisYrkSearchForm aForm = (ApiRsvSisYrkSearchForm) form;
        ActionErrors errors = aForm.validate(gsMsg);
        if (!errors.isEmpty()) {
            log__.debug("エラーあり");
            addErrors(req, errors);
            return null;
        }


        ApiRsvSisYrkSearchParamModel paramMdl = new ApiRsvSisYrkSearchParamModel();
        paramMdl.setParam(form);
        ApiRsvSisYrkSearchBiz biz = new ApiRsvSisYrkSearchBiz();

        Document doc = biz.createDoc(con, reqMdl, paramMdl);

        log__.debug("createXml end");

        return doc;
    }

}
