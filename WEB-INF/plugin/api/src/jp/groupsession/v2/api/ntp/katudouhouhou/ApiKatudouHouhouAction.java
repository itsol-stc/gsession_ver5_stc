package jp.groupsession.v2.api.ntp.katudouhouhou;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.dao.NtpKthouhouDao;
import jp.groupsession.v2.ntp.model.NtpKthouhouModel;
/**
 *
 * <br>[機  能] WEBAPI 日報 活動方法取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiKatudouHouhouAction extends AbstractApiAction {
    /** ロガーオブジェクト*/
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
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
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        NtpKthouhouDao dao = new NtpKthouhouDao(con);

        List<NtpKthouhouModel> list = dao.select();
        resultSet.setAttribute("TotalCount", Integer.toString(list.size()));
        for (int i = 0; i < list.size(); i++) {
            NtpKthouhouModel data = list.get(i);
            Element result = new Element("Result");
            resultSet.addContent(result);
            result.addContent(_createElement("NkhSid", data.getNkhSid()));
            result.addContent(_createElement("NkhCode", data.getNkhCode()));
            result.addContent(_createElement("NkhName", data.getNkhName()));
        }


        return doc;
    }

}
