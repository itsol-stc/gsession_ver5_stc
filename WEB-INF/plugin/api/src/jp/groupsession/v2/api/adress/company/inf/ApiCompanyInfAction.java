package jp.groupsession.v2.api.adress.company.inf;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrBelongIndustryDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.dao.AdrTypeindustryDao;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.adr.model.AdrTypeindustryModel;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.ApiDataTypeUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
/**
 * <br>[機  能] WEB API アドレス帳 会社情報取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCompanyInfAction extends AbstractApiAction {
    /** ロガークラス */
    private static Log log__ = LogFactory.getLog(ApiCompanyInfAction.class);
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");
        //アドレス帳プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstAddress.PLUGIN_ID_ADDRESS, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstAddress.PLUGIN_ID_ADDRESS));
            return null;
        }

        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        ApiCompanyInfForm myform = (ApiCompanyInfForm) form;
        ActionErrors err = myform.validateCheck();
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        //会社情報
        String[] sids = myform.getAcoSid();
        for (String acoSidStr : sids) {
            int acoSid = Integer.parseInt(acoSidStr);
            AdrCompanyDao companyDao = new AdrCompanyDao(con);
            AdrCompanyModel companyModel = companyDao.select(acoSid);


            if (companyModel != null) {
                Element result = new Element("Result");
                resultSet.addContent(result);


                result.addContent(_createElement("AcoSid", companyModel.getAcoSid()));
                result.addContent(_createElement("AcoCode", companyModel.getAcoCode()));
                result.addContent(_createElement("AcoName", companyModel.getAcoName()));
                result.addContent(_createElement("AcoNameKn", companyModel.getAcoNameKn()));
                result.addContent(_createElement("AcoPostno1", companyModel.getAcoPostno1()));
                result.addContent(_createElement("AcoPostno2", companyModel.getAcoPostno2()));
                result.addContent(_createElement("TdfSid", companyModel.getTdfSid()));
                result.addContent(_createElement("AcoAddr1", companyModel.getAcoAddr1()));
                result.addContent(_createElement("AcoAddr2", companyModel.getAcoAddr2()));
                result.addContent(_createElement("AcoUrl", companyModel.getAcoUrl()));
                result.addContent(_createElement("AcoBiko", companyModel.getAcoBiko()));
                //AddDate
                Element addDate = new Element("AddDateTime");
                addDate.addContent(ApiDataTypeUtil.getDateTime(companyModel.getAcoAdate()));
                result.addContent(addDate);
                //EditDate
                Element editDate = new Element("EditDateTime");
                editDate.addContent(ApiDataTypeUtil.getDateTime(companyModel.getAcoEdate()));
                result.addContent(editDate);

                Element atiSet = new Element("AtiSet");
                result.addContent(atiSet);


                //会社SID指定時に会社付属業種SID一覧の取得
                AdrBelongIndustryDao blgIndustryDao = new AdrBelongIndustryDao(con);
                String[] atiSidStrList = blgIndustryDao.getAtiSidList(acoSid);
                List<Integer> atiSidList = null;

                if (atiSidStrList != null) {
                    atiSidList = new ArrayList<Integer>(atiSidStrList.length);
                    for (int i = 0; i < atiSidStrList.length; i++) {
                        atiSidList.add(Integer.valueOf(atiSidStrList[i]));
                    }
                }
                AdrTypeindustryDao atiDao = new AdrTypeindustryDao(con);


                List<AdrTypeindustryModel> atiList = atiDao.select();

                for (AdrTypeindustryModel data : atiList) {
                    //会社SID指定時に会社付属業種SID一覧にないデータを除外
                    if (atiSidList == null
                    || !atiSidList.contains(Integer.valueOf(data.getAtiSid()))) {
                        continue;
                    }
                    Element atiData = new Element("AtiData");
                    atiSet.addContent(atiData);
                    atiData.addContent(_createElement("AtiSid", data.getAtiSid()));
                    atiData.addContent(_createElement("AtiName", data.getAtiName()));
                    atiData.addContent(_createElement("AtiBiko", data.getAtiBiko()));
                    atiData.addContent(_createElement("AtiSort", data.getAtiSort()));
                }
                atiSet.setAttribute("Count", String.valueOf(atiSet.getContentSize()));

            }

        }
        resultSet.setAttribute("Count", String.valueOf(resultSet.getContentSize()));


        return doc;
    }

}
