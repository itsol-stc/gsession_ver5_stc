package jp.groupsession.v2.api.adress.belongcom;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.Adr010Const;
import jp.groupsession.v2.adr.adr010.model.Adr010SearchModel;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.adress.search.ApiAdrDetailModel;
import jp.groupsession.v2.api.adress.search.ApiAdrSearchDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
/**
 *
 * <br>[機  能] WEB API アドレス帳 アドレス一覧取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiAdressBelongCompanyAction extends AbstractApiAction {
    /** ログ */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());

    @Override
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        ApiAdressBelongCompanyForm form = (ApiAdressBelongCompanyForm) aForm;

        //アドレス帳プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstAddress.PLUGIN_ID_ADDRESS, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstAddress.PLUGIN_ID_ADDRESS));
            return null;
        }


        //アドレス帳一覧を取得する
        int acoSid = NullDefault.getInt(form.getAcoSid(), 0);
        int abaSid = NullDefault.getInt(form.getAbaSid(), 0);

        Adr010SearchModel searchMdl = new Adr010SearchModel();
        searchMdl.setSessionUser(umodel.getUsrsid());
        searchMdl.setCompanySid(acoSid);
        searchMdl.setCompanyBaseSid(abaSid);
        searchMdl.setSortKey(Adr010Const.SORTKEY_UNAME);
        searchMdl.setOrderKey(Adr010Const.ORDERKEY_ASC);
        searchMdl.setCmdMode(Adr010Const.CMDMODE_NAME);
        ApiAdrSearchDao apiAdrDao = new ApiAdrSearchDao(con);
        List<ApiAdrDetailModel> list = apiAdrDao.getSearchResultList(
                searchMdl, getRequestModel(req));

        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        if (list != null) {
            for (ApiAdrDetailModel data : list) {
                Element result = new Element("Result");
                resultSet.addContent(result);
                int adrSid = data.getAdrSid();
                result.addContent(_createElement("AdrSid", adrSid));
                result.addContent(_createElement("AdrName", data.getUserName()));
                result.addContent(_createElement("AdrNameKn", data.getUserNameKn()));
                result.addContent(_createElement("AdrSei", data.getSei()));
                result.addContent(_createElement("AdrSeiKn", data.getSeiKn()));
                result.addContent(_createElement("AdrMei", data.getMei()));
                result.addContent(_createElement("AdrMeiKn", data.getMeiKn()));
                result.addContent(_createElement("AdrTell1", data.getTel1()));
                result.addContent(_createElement("AdrMail1", data.getMail1()));
                result.addContent(_createElement("AdrFax1", data.getFax1()));
                result.addContent(_createElement("AdrTell2", data.getTel2()));
                result.addContent(_createElement("AdrMail2", data.getMail2()));
                result.addContent(_createElement("AdrFax2", data.getFax2()));
                result.addContent(_createElement("AdrTell3", data.getTel3()));
                result.addContent(_createElement("AdrMail3", data.getMail3()));
                result.addContent(_createElement("AdrFax3", data.getFax3()));
                result.addContent(_createElement("AdrTell1Naisen", data.getNai1()));
                result.addContent(_createElement("AdrTell2Naisen", data.getNai2()));
                result.addContent(_createElement("AdrTell3Naisen", data.getNai3()));
                result.addContent(_createElement("AdrTell1Comment", data.getTel1Comment()));
                result.addContent(_createElement("AdrMail1Comment", data.getMail1Comment()));
                result.addContent(_createElement("AdrFax1Comment", data.getFax1Comment()));
                result.addContent(_createElement("AdrTell2Comment", data.getTel2Comment()));
                result.addContent(_createElement("AdrMail2Comment", data.getMail2Comment()));
                result.addContent(_createElement("AdrFax2Comment", data.getFax2Comment()));
                result.addContent(_createElement("AdrTell3Comment", data.getTel3Comment()));
                result.addContent(_createElement("AdrMail3Comment", data.getMail3Comment()));
                result.addContent(_createElement("AdrFax3Comment", data.getFax3Comment()));
                result.addContent(_createElement("AdrSyozoku", data.getSyozoku()));
                result.addContent(_createElement("AdrYakusyoku", data.getPositionName()));
                result.addContent(_createElement("AdrBikou", data.getBiko()));

                //住所情報
                if (NullDefault.getString(data.getPostNo1(), "").length() > 0
                        && NullDefault.getString(data.getPostNo2(), "").length() > 0) {
                    result.addContent(_createElement(
                            "AdrPostNo", data.getPostNo1() + "-" + data.getPostNo2()));
                } else {
                    result.addContent(
                            _createElement("AdrPostNo", ""));
                }
                result.addContent(_createElement("AdrTdfk", data.getTdfk()));
                result.addContent(_createElement("AdrAdress1", data.getAddress1()));
                result.addContent(_createElement("AdrAdress2", data.getAddress2()));


                //会社SID
                result.addContent(_createElement("AcoSid", data.getAcoSid()));

                //会社情報
                result.addContent(_createElement("AcoName", data.getCompanyName()));
                result.addContent(_createElement("AcoNameKn", data.getCompanyNameKn()));
                result.addContent(_createElement("AcoPostno1", data.getCompanyPostNo1()));
                result.addContent(_createElement("AcoPostno2", data.getCompanyPostNo2()));
                result.addContent(_createElement("AcoTdfk", data.getCompanyTdfk()));
                result.addContent(_createElement("AcoAddr1", data.getCompanyAddress1()));
                result.addContent(_createElement("AcoAddr2", data.getCompanyAddress2()));
                result.addContent(_createElement("AcoUrl", data.getCompanyUrl()));

                //会社拠点SID
                result.addContent(_createElement("AbaSid",
                        data.getAbaSid()));

                //会社拠点情報
                result.addContent(_createElement("AbaName", data.getCompanyBaseName()));
                result.addContent(_createElement("AbaZip1", data.getCompanyBasePostNo1()));
                result.addContent(_createElement("AbaZip2", data.getCompanyBasePostNo2()));
                result.addContent(_createElement("AbaTdfk", data.getCompanyBaseTdfk()));
                result.addContent(_createElement("AbaAddress1", data.getCompanyBaseAddress1()));
                result.addContent(_createElement("AbaAddress2", data.getCompanyBaseAddress2()));

            }
            resultSet.setAttribute("Count", String.valueOf(list.size()));
        }

        return doc;
    }

}
