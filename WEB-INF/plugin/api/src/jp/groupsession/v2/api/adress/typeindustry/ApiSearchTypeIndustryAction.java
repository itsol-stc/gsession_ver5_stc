package jp.groupsession.v2.api.adress.typeindustry;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrBelongIndustryDao;
import jp.groupsession.v2.adr.dao.AdrTypeindustryDao;
import jp.groupsession.v2.adr.model.AdrTypeindustryModel;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

/**
 * <br>[機  能] WEB API アドレス帳 業種一覧取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSearchTypeIndustryAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSearchTypeIndustryAction.class);
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        //アドレス帳プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstAddress.PLUGIN_ID_ADDRESS, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstAddress.PLUGIN_ID_ADDRESS));
            return null;
        }
        //入力チェック
        //業種一覧取得
        AdrTypeindustryDao atiDao = new AdrTypeindustryDao(con);

        //AcoSid設定時 所属業種名称を取得する
        ApiSearchTypeIndustryForm myform = (ApiSearchTypeIndustryForm) form;
        List<Integer> atiSidList = null;
        //会社SID指定時に会社付属業種SID一覧の取得
        if (myform.getAcoSid() != -1) {

            AdrBelongIndustryDao blgIndustryDao = new AdrBelongIndustryDao(con);
            String[] atiSidStrList = blgIndustryDao.getAtiSidList(myform.getAcoSid());
            if (atiSidStrList != null) {
                atiSidList = new ArrayList<Integer>(atiSidStrList.length);
                for (int i = 0; i < atiSidStrList.length; i++) {
                    atiSidList.add(new Integer(atiSidStrList[i]));
                }
            }
        }


        List<AdrTypeindustryModel> atiList = atiDao.select();
        int count = atiList.size();

        for (AdrTypeindustryModel data : atiList) {
            if (myform.getAcoSid() != -1) {
                //会社SID指定時に会社付属業種SID一覧にないデータを除外
                if (atiSidList == null || !atiSidList.contains(new Integer(data.getAtiSid()))) {
                    count--;
                    continue;
                }
            }
            Element result = new Element("Result");
            resultSet.addContent(result);
            result.addContent(_createElement("AtiSid", data.getAtiSid()));
            result.addContent(_createElement("AtiName", data.getAtiName()));
            result.addContent(_createElement("AtiBiko", data.getAtiBiko()));
            result.addContent(_createElement("AtiSort", data.getAtiSort()));
        }
        Attribute atCount = new Attribute("TotalCount", Integer.toString(count));
        resultSet.setAttribute(atCount);

        return doc;
    }

}
