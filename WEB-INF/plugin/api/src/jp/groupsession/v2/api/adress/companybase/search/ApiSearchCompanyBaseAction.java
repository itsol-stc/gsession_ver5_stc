package jp.groupsession.v2.api.adress.companybase.search;


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

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr240.dao.Adr240Dao;
import jp.groupsession.v2.adr.adr240.model.Adr240Model;
import jp.groupsession.v2.adr.adr240.model.Adr240SearchModel;
import jp.groupsession.v2.adr.dao.AdrUconfDao;
import jp.groupsession.v2.adr.model.AdrUconfModel;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
/**
 * <br>[機  能] WEB API アドレス帳 企業拠点検索アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSearchCompanyBaseAction extends AbstractApiAction {

    /** ロガークラス */
    private static Log log__ = LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
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
        ApiSearchCompanyBaseForm thisForm = (ApiSearchCompanyBaseForm) form;
        ActionErrors err = thisForm.validateCheck();
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        Adr240SearchModel searchMdl = new Adr240SearchModel();

        //企業コード
        searchMdl.setCoCode(thisForm.getCode());
        //会社名
        searchMdl.setCoName(thisForm.getName());
        //会社名カナ
        searchMdl.setCoNameKn(thisForm.getIni());
        //支店・営業所名
        searchMdl.setCoBaseName(thisForm.getBase());
        //業種
        searchMdl.setAtiSid(Integer.parseInt(thisForm.getAtiSid()));
        //都道府県
        searchMdl.setTdfk(Integer.parseInt(thisForm.getTdfSid()));
        //備考
        searchMdl.setBiko(thisForm.getBiko());

        Adr240Dao dao = new Adr240Dao(con);
        //検索件数
        int searchCnt = dao.getSearchCount(searchMdl);

        //最大件数
        int maxCnt = Integer.parseInt(thisForm.getCount());
        BaseUserModel bumdl = getSessionUserModel(req);

        AdrUconfDao uconfdao = new AdrUconfDao(con);
        AdrUconfModel model = uconfdao.select(bumdl.getUsrsid());
        if (model != null && model.getAucComcount() != 0) {
            maxCnt = model.getAucComcount();
        } else {
            maxCnt = Integer.parseInt(GSConstAddress.DEFAULT_COMCOUNT);
        }



        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = Integer.parseInt(thisForm.getPage());
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        //会社一覧を取得
        searchMdl.setPage(page);
        searchMdl.setMaxViewCount(maxCnt);
        List<Adr240Model> list = dao.getSearchResultList(searchMdl);

        resultSet.setAttribute("Count", Integer.toString(list.size()));
        resultSet.setAttribute("TotalCount", Integer.toString(searchCnt));
        resultSet.setAttribute("Page", Integer.toString(page));
        resultSet.setAttribute("MaxPage", Integer.toString(maxPage));

        for (Adr240Model data : list) {
            Element result = new Element("Result");
            resultSet.addContent(result);
            result.addContent(_createElement("AcoSid", data.getAcoSid()));
            result.addContent(_createElement("AcoCode", data.getAcoCode()));
            result.addContent(_createElement("AcoName", data.getAcoName()));
            result.addContent(_createElement("AbaSid", data.getAbaSid()));
            result.addContent(_createElement("AbaName", data.getAbaName()));
        }



        return doc;
    }

}
