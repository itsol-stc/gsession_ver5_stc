package jp.groupsession.v2.api.ntp.katudoubunrui;

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
import jp.groupsession.v2.ntp.dao.NtpKtbunruiDao;
import jp.groupsession.v2.ntp.model.NtpKtbunruiModel;
/**
 *
 * <br>[機  能] WEBAPI 日報 活動分類取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiKatudouBunruiAction extends AbstractApiAction {
    /**　ロガーオブジェクト*/
    private static Log log__ = LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        Element resultSet = new Element("ResultSet");
        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }
        Document doc = new Document(resultSet);

        NtpKtbunruiDao dao = new NtpKtbunruiDao(con);

        List<NtpKtbunruiModel> list = dao.select();
        resultSet.setAttribute("TotalCount", Integer.toString(list.size()));
        for (int i = 0; i < list.size(); i++) {
            NtpKtbunruiModel data = list.get(i);
            Element result = new Element("Result");
            resultSet.addContent(result);
            result.addContent(_createElement("NkbSid", data.getNkbSid()));
            result.addContent(_createElement("NkbCode", data.getNkbCode()));
            result.addContent(_createElement("NkbName", data.getNkbName()));
        }

        return doc;
    }

}
