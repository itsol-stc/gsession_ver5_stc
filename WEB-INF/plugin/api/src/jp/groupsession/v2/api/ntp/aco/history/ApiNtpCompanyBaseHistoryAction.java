package jp.groupsession.v2.api.ntp.aco.history;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.ntp040.Ntp040Dao;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040AddressModel;
/**
 * <br>[機  能] 日報で利用した企業拠点履歴を返すWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNtpCompanyBaseHistoryAction extends AbstractApiAction {

    /** ロガークラス */
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

        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        ApiNtpCompanyBaseHistoryForm thisForm = (ApiNtpCompanyBaseHistoryForm) form;
        ActionErrors err = thisForm.validateCheck();
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        List<Ntp040AddressModel> adrList = null;
        List<Ntp040AddressModel> adrMapList = new ArrayList<Ntp040AddressModel>();
        List<Ntp040AddressModel> adrDataList = new ArrayList<Ntp040AddressModel>();
        Ntp040AddressModel adrMdl = null;
        BaseUserModel bumdl = umodel;
        Ntp040Dao dao = new Ntp040Dao(con);
        adrList = dao.getNtpAdrHistory(bumdl.getUsrsid());

        if (!adrList.isEmpty()) {

            Map<String, Ntp040AddressModel> adrMap
                        = new LinkedHashMap<String, Ntp040AddressModel>();

            for (Ntp040AddressModel mdl : adrList) {
                String key = mdl.getCompanySid() + "_" + mdl.getCompanyBaseSid();
                if (adrMap.get(key) == null) {
                    adrMap.put(key, mdl);
                }
            }

            Set<String> keySet = adrMap.keySet();
            Iterator<String> keyIte = keySet.iterator();
            while (keyIte.hasNext()) {

                adrMdl = new Ntp040AddressModel();
                String adrkey = (String) keyIte.next();
                adrMdl = adrMap.get(adrkey);

                //会社情報
                AdrCompanyDao companyDao = new AdrCompanyDao(con);
                AdrCompanyModel companyModel = companyDao.select(adrMdl.getCompanySid());

                if (companyModel != null) {
                    adrMdl.setCompanySid(companyModel.getAcoSid());
                    adrMdl.setCompanyCode(companyModel.getAcoCode());
                    adrMdl.setCompanyName(companyModel.getAcoName());
                }

                //会社拠点情報
                AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
                AdrCompanyBaseModel companyBaseMdl = new AdrCompanyBaseModel();
                companyBaseMdl = companyBaseDao.select(adrMdl.getCompanyBaseSid());
                if (companyBaseMdl != null) {
                    adrMdl.setCompanyBaseSid(companyBaseMdl.getAbaSid());
                    adrMdl.setCompanyBaseName(companyBaseMdl.getAbaName());
                }
                if (!StringUtil.isNullZeroStringSpace(adrMdl.getCompanyName())) {
                    adrMapList.add(adrMdl);
                }
            }
            //最大件数
            int adrHistoryLimit = Integer.parseInt(thisForm.getCount());

            int maxCount = adrMapList.size();
            int maxPageNum = PageUtil.getPageCount(maxCount, adrHistoryLimit);
            int offset = 0;
            int pageNum = Integer.valueOf(thisForm.getPage());
            if (pageNum == 0) {
                pageNum = 1;

            } else if (pageNum > maxPageNum) {
                pageNum = maxPageNum;
            }

            offset = (pageNum - 1) * adrHistoryLimit;
            int maxRow = adrHistoryLimit;

            if (offset > 1) {
                maxRow = offset + adrHistoryLimit;
            }

            if (!adrMapList.isEmpty()) {

                if (maxRow > adrMapList.size()) {
                    maxRow = adrMapList.size();
                }

                for (int i = offset; i < maxRow; i++) {
                    adrDataList.add(adrMapList.get(i));
                }
            }

            resultSet.setAttribute("Count", Integer.toString(adrDataList.size()));
            resultSet.setAttribute("TotalCount", Integer.toString(maxCount));
            resultSet.setAttribute("Page", Integer.toString(pageNum));
            resultSet.setAttribute("MaxPage", Integer.toString(maxPageNum));

            for (Ntp040AddressModel data : adrDataList) {
                Element result = new Element("Result");
                resultSet.addContent(result);
                result.addContent(_createElement("AcoSid", data.getCompanySid()));
                result.addContent(_createElement("AcoCode", data.getCompanyCode()));
                result.addContent(_createElement("AcoName", data.getCompanyName()));
                result.addContent(_createElement("AbaSid", data.getCompanyBaseSid()));
                result.addContent(_createElement("AbaName", data.getCompanyBaseName()));
            }

        }


        return doc;
    }

}
