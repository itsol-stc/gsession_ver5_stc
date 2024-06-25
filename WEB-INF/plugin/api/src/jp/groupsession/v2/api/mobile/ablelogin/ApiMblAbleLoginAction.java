package jp.groupsession.v2.api.mobile.ablelogin;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;
/**
 * <br>[機  能] GSモバイルにログインできるかを判定するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiMblAbleLoginAction extends AbstractApiAction {

    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        PluginConfig userPconfig = getPluginConfigForMain(
                getPluginConfig(req), con, umodel.getUsrsid());

        List<String> usePluginIDs = userPconfig.getPluginIdList();

        int kbn;
        kbn = 0;
        for (String pluginId : usePluginIDs) {
            if (GSConst.PLUGIN_ID_MOBILE.equals(pluginId)) {
                //使用可
                kbn = 1;
                break;
            }
        }
        if (kbn == 1) {
            CmnUsrmInfDao dao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel model = dao.select(umodel.getUsrsid());

            kbn = (model.getUsiMblUse() == 0 ? 1 : 0);
        }
        //ルートエレメントResultSet
        Element result = new Element("Result");
        Document doc = new Document(result);
        if (kbn == 1) {
            result.addContent("OK");
        } else {
            result.addContent("NG");
        }
        return doc;
    }

}
