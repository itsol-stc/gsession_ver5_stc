package jp.groupsession.v2.api.man.plugin.useable;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * <br>[機  能] 複数指定したプラグインIDの中から利用可能なプラグイン情報を返すWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiManPluginUseableAction extends AbstractApiAction {
    /** ログ */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());

    @Override
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        ApiManPluginUseableForm form = (ApiManPluginUseableForm) aForm;

        int usrSid = umodel.getUsrsid();
        PluginConfig pconfig = getPluginConfig(req);

        PluginConfig userPconfig = getPluginConfigForMain(pconfig, con, usrSid);

        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        String[] checkIds = form.getPluginId();
        if (checkIds == null) {
            checkIds = new String[0];
        }

        //XMLデータ作成
        for (String pluginId : checkIds) {
            Plugin plugin = userPconfig.getPlugin(pluginId);
            if (plugin != null) {
                //Result
                Element result = new Element("Result");
                resultSet.addContent(result);

                //プラグインID
                Element plgid = new Element("Plgid");
                plgid.addContent(plugin.getId());
                result.addContent(plgid);

                //プラグイン名
                Element plgname = new Element("PlgName");
                plgname.addContent(plugin.getName(getRequestModel(req)));
                result.addContent(plgname);


            }
        }


        return doc;
    }
    @Override
    public boolean isWriteApiLog() {
        return true;
    }
}
