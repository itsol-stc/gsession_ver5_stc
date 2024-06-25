package jp.groupsession.v2.api.schedule;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AbstractApiSchAction extends AbstractApiAction {

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.AbstractApiAction
     * #createXml(org.apache.struts.action.ActionForm,
     *  javax.servlet.http.HttpServletRequest
     *  , javax.servlet.http.HttpServletResponse
     *  , java.sql.Connection, jp.groupsession.v2.cmn.dao.BaseUserModel)
     */
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        return null;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.AbstractApiAction
     * #checkCantAccsessRelationPluginError(
     * javax.servlet.http.HttpServletRequest,
     *  org.apache.struts.action.ActionErrors)
     */
    @Override
    public ActionErrors checkCantAccsessRelationPluginError(
            HttpServletRequest req, ActionErrors errors) {
        //スケジュールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstSchedule.PLUGIN_ID_SCHEDULE, req)) {
            return addCantAccsessPluginError(req, errors, GSConstSchedule.PLUGIN_ID_SCHEDULE);
        }
        return errors;
    }

}
