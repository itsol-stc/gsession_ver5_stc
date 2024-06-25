package jp.groupsession.v2.api.ntp.prosess;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.dao.NtpProcessDao;
import jp.groupsession.v2.ntp.model.NtpProcessModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;
/**
 * <br>[機  能] WEBAPI 日報プロセス一覧取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNtpProsessAction extends AbstractApiAction {
    /**ロガーオブジェクト*/
    private static Log log__ = LogFactory.getLog(new Throwable()
        .getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }

        ApiNtpProsessForm thisForm = (ApiNtpProsessForm) form;
        ActionErrors err = thisForm.validateCheck(con, req);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        NtpProcessDao dao = new NtpProcessDao(con);
        int ngySid = Integer.parseInt(thisForm.getNgySid());
        if (ngySid < 0) {
            return doc;
        }
        List<NtpProcessModel> list = dao.getGyomuProcess(ngySid);
        resultSet.setAttribute("TotalCount", Integer.toString(list.size()));

        for (NtpProcessModel ntpProcessModel__ : list) {
            Element result = new Element("Result");
            resultSet.addContent(result);

            result.addContent(_createElement("NgpSid", ntpProcessModel__.getNgpSid()));
            result.addContent(_createElement("NgpCode", ntpProcessModel__.getNgpCode()));
            result.addContent(_createElement("NgpName", ntpProcessModel__.getNgpName()));
        }
        return doc;
    }

}
