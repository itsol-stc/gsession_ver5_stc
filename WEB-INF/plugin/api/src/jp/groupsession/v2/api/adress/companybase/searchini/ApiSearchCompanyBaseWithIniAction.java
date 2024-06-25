package jp.groupsession.v2.api.adress.companybase.searchini;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.Adr010Const;
import jp.groupsession.v2.adr.adr010.model.Adr010SearchModel;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.adress.companybase.dao.ApiCompanyBaseDao;
import jp.groupsession.v2.api.adress.companybase.model.ApiCompanyBaseModel;
import jp.groupsession.v2.api.adress.search.ApiAdrSearchDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

/**
 * <br>[機  能] WEB API アドレス帳 先頭一文字で絞り込んだ会社情報の一覧取得
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSearchCompanyBaseWithIniAction extends AbstractApiAction {
    /**ロガー*/
    private static Log log__ = LogFactory.getLog(ApiSearchCompanyBaseWithIniAction.class);

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

        ApiSearchCompanyBaseWithIniForm myForm = (ApiSearchCompanyBaseWithIniForm) form;
        ActionErrors err = myForm.validateCheck();
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }
        ApiCompanyBaseDao abaDao = new ApiCompanyBaseDao(con);
        ApiAdrSearchDao adrDao = new ApiAdrSearchDao(con);
        Adr010SearchModel model = __createSearchModel(myForm, umodel.getUsrsid(), con);
        int searchCnt = 0;
        if (NullDefault.getInt(myForm.getFlgNoAdress(), 0) == 0) {

            searchCnt = abaDao.countCompanyList(myForm.getAcoSini());
        } else {
            searchCnt = adrDao.countCompanyList(model);

        }

        int page = Integer.parseInt(myForm.getPage());

        int maxCnt = Integer.parseInt(myForm.getResults());
        if (maxCnt == 0) {
            resultSet.setAttribute("Count", "0");
            resultSet.setAttribute("TotalCount", Integer.toString(searchCnt));
            resultSet.setAttribute("Page", Integer.toString(page));
            resultSet.setAttribute("MaxPage", "0");
            return doc;
        }

        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        if (page < 1) {
            page = 1;
        }
        model.setPage(page);
        model.setMaxViewCount(maxCnt);

        List<ApiCompanyBaseModel> list;
        if (NullDefault.getInt(myForm.getFlgNoAdress(), 0) == 0) {
            list =
                abaDao.getCompanyBaseList(myForm.getAcoSini()
                        , (page - 1) * maxCnt
                        , Integer.parseInt(myForm.getResults()));
        } else {
            list =
                adrDao.getCompanyList(model);
        }
        resultSet.setAttribute("Count", Integer.toString(list.size()));
        resultSet.setAttribute("TotalCount", Integer.toString(searchCnt));
        resultSet.setAttribute("Page", Integer.toString(page));
        resultSet.setAttribute("MaxPage", Integer.toString(maxPage));

        for (ApiCompanyBaseModel data : list) {
            Element result = new Element("Result");
            resultSet.addContent(result);
            //会社情報
            result.addContent(_createElement("AcoSid", data.getAcoSid()));
            result.addContent(_createElement("AcoCode", data.getAcoCode()));
            result.addContent(_createElement("AcoName", data.getAcoName()));
            result.addContent(_createElement("AcoNameKn", data.getAcoNameKn()));
            result.addContent(_createElement("AcoUrl", data.getAcoUrl()));
            result.addContent(_createElement("AbaSid", data.getAbaSid()));
            result.addContent(_createElement("AbaName", data.getAbaName()));
            if (NullDefault.getString(data.getAbaPostno1(), "").length() > 0
                    && NullDefault.getString(data.getAbaPostno2(), "").length() > 0) {
                result.addContent(_createElement("AbaPostNo"
                    , data.getAbaPostno1() + "-" + data.getAbaPostno2()));
            } else {
                result.addContent(_createElement("AbaPostNo"
                        , ""));
            }
            result.addContent(_createElement("AbaTdfk", data.getTdfkName()));
            result.addContent(_createElement("AbaAdress1", data.getAbaAddr1()));
            result.addContent(_createElement("AbaAdress2", data.getAbaAddr2()));

        }

        return doc;
    }
    /**
    *
    * <br>[機  能] 検索モデル作成
    * <br>[解  説]
    * <br>[備  考]
    * @param form 頭文字
    * @param userSid 検索者ユーザSID
    * @param con コネクション
    * @return 検索モデル
    * @throws SQLException SQL実行時例外
    */
   private Adr010SearchModel __createSearchModel(
           ApiSearchCompanyBaseWithIniForm form,
           int userSid,
           Connection con)
           throws SQLException {
       Adr010SearchModel searchMdl = new Adr010SearchModel();
       searchMdl.setSessionUser(userSid);
       searchMdl.setSortKey(Adr010Const.SORTKEY_CONAME);
       searchMdl.setOrderKey(Adr010Const.ORDERKEY_ASC);
       searchMdl.setCmdMode(Adr010Const.CMDMODE_NAME);




       /** 会社名 頭文字 */
       searchMdl.setCnameKnHead(form.getAcoSini());



       return searchMdl;
   }

}
