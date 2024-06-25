package jp.groupsession.v2.api.ntp.shohin.nan;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.dao.NtpAnShohinDao;
import jp.groupsession.v2.ntp.dao.NtpShohinDao;
import jp.groupsession.v2.ntp.model.NtpShohinModel;
/**
 * <br>[機  能] WEBAPI 日報商品一覧取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNanShohinAction extends AbstractApiAction {
    /**ロガーオブジェクト*/
    private static Log log__ = LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
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
        ApiNanShohinForm thisForm = (ApiNanShohinForm) form;
        ActionErrors err = thisForm.validateCheck(con, req);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        NtpAnShohinDao anShohindao = new NtpAnShohinDao(con);
        int nanSid = Integer.parseInt(thisForm.getNanSid());
        String[] sidList = anShohindao.select(nanSid);
        NtpShohinDao dao = new NtpShohinDao(con);

        List<NtpShohinModel> list = dao.select(sidList);

        resultSet.setAttribute("TotalCount", Integer.toString(list.size()));
        for (NtpShohinModel ntpShohinModel__ : list) {
            Element result = new Element("Result");
            resultSet.addContent(result);
            result.addContent(_createElement("NhnSid", ntpShohinModel__.getNhnSid()));
            result.addContent(_createElement("NhnCode", ntpShohinModel__.getNhnCode()));
            result.addContent(_createElement("NhnName", ntpShohinModel__.getNhnName()));
            result.addContent(_createElement("NhnPriceSale", ntpShohinModel__.getNhnPriceSale()));
            result.addContent(_createElement("NhnPriceCost", ntpShohinModel__.getNhnPriceCost()));
            result.addContent(_createElement("NhnHosoku", ntpShohinModel__.getNhnHosoku()));
        }
        return doc;
    }
}
