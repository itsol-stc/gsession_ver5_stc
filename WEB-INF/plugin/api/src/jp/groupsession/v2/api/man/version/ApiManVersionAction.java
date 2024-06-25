package jp.groupsession.v2.api.man.version;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
/**
 *
 * <br>[機  能] GSバージョンを取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiManVersionAction extends AbstractApiAction {

    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        return new Document(_createElement("Result", GSConst.VERSION));
    }
    @Override
    public boolean isWriteApiLog() {
        return true;
    }
}
