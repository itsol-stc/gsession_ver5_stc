package jp.groupsession.v2.api.ntp.shohin;

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

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.model.NtpShohinModel;
import jp.groupsession.v2.ntp.ntp130.Ntp130SearchModel;
import jp.groupsession.v2.ntp.ntp130.Ntp130ShohinDao;
import jp.groupsession.v2.ntp.ntp130.Ntp130ShohinModel;
/**
 * <br>[機  能] WEBAPI 日報商品一覧検索アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiShohinSearchAction extends AbstractApiAction {
    /** ログ管理オブジェクト*/
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
        ApiShohinSearchForm thisForm = (ApiShohinSearchForm) form;
        ActionErrors err = thisForm.validateCheck(con, req);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        int maxCnt = Integer.parseInt(thisForm.getMaxCnt());


        //検索モデルの設定
        Ntp130SearchModel searchMdl = new Ntp130SearchModel();
        searchMdl.setNhnCode(thisForm.getNhnCode());
        searchMdl.setNhnName(thisForm.getNhnName());

        String price = NullDefault.getStringZeroLength(thisForm.getNhnPriceSale(), "-1");
        searchMdl.setNhnPriceSale(Integer.parseInt(price));
        searchMdl.setNhnPriceSaleKbn(Integer.parseInt(thisForm.getPriceHiOrRow()));

        String cost = NullDefault.getStringZeroLength(thisForm.getNhnPriceCost(), "-1");
        searchMdl.setNhnPriceCost(Integer.parseInt(cost));
        searchMdl.setNhnPriceCostKbn(Integer.parseInt(thisForm.getCostHiOrRow()));

        searchMdl.setSortKey1(Integer.parseInt(thisForm.getSortKey1()));
        searchMdl.setSortKey2(Integer.parseInt(thisForm.getSortKey2()));
        searchMdl.setOrderKey1(Integer.parseInt(thisForm.getOrderKey1()));
        searchMdl.setOrderKey2(Integer.parseInt(thisForm.getOrderKey2()));



        //最大件数
        Ntp130ShohinDao shohinDao = new Ntp130ShohinDao(con, getRequestModel(req));
        int searchCnt = shohinDao.getShohinCount(searchMdl);

        if (maxCnt == 0) {
            resultSet.setAttribute("Count", "0");
            resultSet.setAttribute("TotalCount", Integer.toString(searchCnt));
            resultSet.setAttribute("Page", thisForm.getPage());
            resultSet.setAttribute("MaxPage", "0");
            return doc;
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


        //検索モデルにて商品一覧の取得・設定
        List<Ntp130ShohinModel> list =
                (List<Ntp130ShohinModel>) shohinDao.select(searchMdl, page, maxCnt);
        resultSet.setAttribute("Count", Integer.toString(list.size()));
        resultSet.setAttribute("TotalCount", Integer.toString(searchCnt));
        resultSet.setAttribute("Page", Integer.toString(page));
        resultSet.setAttribute("MaxPage", Integer.toString(maxPage));

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
