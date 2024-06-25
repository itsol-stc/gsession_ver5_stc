package jp.groupsession.v2.api.ntp.anken.search;

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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp060.Ntp060AnkenDao;
import jp.groupsession.v2.ntp.ntp060.Ntp060AnkenModel;
import jp.groupsession.v2.ntp.ntp060.Ntp060SearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 日報 案件検索するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiAnkenSearchAction extends AbstractApiAction {
    /** ロガー */
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

        ApiAnkenSearchForm thisForm = (ApiAnkenSearchForm) form;
        ActionErrors err = thisForm.validateCheck(con, new GsMessage(getRequestModel(req)));
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        int maxCnt = Integer.parseInt(thisForm.getMaxCnt());
        int page = Integer.parseInt(thisForm.getPage());

      //検索モデルの設定
        Ntp060SearchModel searchModel = setAnkenSearchModel(
                (ApiAnkenSearchForm) form,
                con, getRequestModel(req));

        //最大件数
        Ntp060AnkenDao ankenDao = new Ntp060AnkenDao(con);
        int searchCnt = ankenDao.getAnkenCount(searchModel);

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

        List<Ntp060AnkenModel> list =
                (List<Ntp060AnkenModel>) ankenDao.select(searchModel,
                        page, maxCnt, getRequestModel(req));

        resultSet.setAttribute("Count", Integer.toString(list.size()));
        resultSet.setAttribute("TotalCount", Integer.toString(searchCnt));
        resultSet.setAttribute("Page", Integer.toString(page));
        resultSet.setAttribute("MaxPage", Integer.toString(maxPage));
        for (Ntp060AnkenModel ntp060AnkenModel__ : list) {
            Element result = new Element("Result");
            resultSet.addContent(result);

            result.addContent(_createElement("Date"
                    , UDateUtil.getSlashYYMD(ntp060AnkenModel__.getNanDate())));
            result.addContent(_createElement("NanSid", ntp060AnkenModel__.getNanSid()));
            result.addContent(_createElement("NanCode", ntp060AnkenModel__.getNanCode()));
            result.addContent(_createElement("NanName", ntp060AnkenModel__.getNanName()));
            result.addContent(_createElement("AcoSid", ntp060AnkenModel__.getAcoSid()));
            result.addContent(_createElement("AcoName", ntp060AnkenModel__.getNtp060CompanyName()));
            result.addContent(_createElement("AbaSid", ntp060AnkenModel__.getAbaSid()));
            result.addContent(_createElement("AbaName", ntp060AnkenModel__.getNtp060BaseName()));
            result.addContent(_createElement("NanMikomi", ntp060AnkenModel__.getNanMikomi()));
            result.addContent(_createElement("NanKinMitumori"
                    , ntp060AnkenModel__.getNanKinMitumori()));
            result.addContent(_createElement("NanDateMitumori"
                    , UDateUtil.getSlashYYMD(ntp060AnkenModel__.getNanMitumoriDate())));
            result.addContent(_createElement("NanDateJutyu"
                    , UDateUtil.getSlashYYMD(ntp060AnkenModel__.getNanJutyuDate())));

            result.addContent(_createElement("NanKinJutyu", ntp060AnkenModel__.getNanKinJutyu()));
            result.addContent(_createElement("NanSyodan", ntp060AnkenModel__.getNanSyodan()));

        }


        return doc;
    }

    /**
     * フォーム情報から検索モデルを生成します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @return Ntp060SearchModel 検索条件モデル
     * @throws SQLException SQL実行時例外
     */
    public Ntp060SearchModel setAnkenSearchModel(ApiAnkenSearchForm form,
            Connection con, RequestModel reqMdl) throws SQLException {
        //検索モデルの設定
        Ntp060SearchModel searchModel = new Ntp060SearchModel();

        String mitumori = NullDefault.getStringZeroLength(form.getMitumoriKingaku(), "-1");
        String jutyu = NullDefault.getStringZeroLength(form.getJutyuuKingaku(), "-1");

        searchModel.setNanCode(form.getNanCode());
        searchModel.setNanName(form.getNanName());
        searchModel.setNtp060AcoCode(form.getAcoCode());
        searchModel.setNtp060AcoName(form.getAcoName());
        searchModel.setNtp060AcoNameKana(form.getAcoNameIni());
        searchModel.setNtp060AbaName(form.getAbaName());
        searchModel.setNtp060ShohinName(form.getNhnName());
        searchModel.setNgpSid(Integer.parseInt(form.getNgpSid()));
        searchModel.setNtp060Mikomi(form.getNanMikomi());

        searchModel.setNanKinMitumori(Integer.parseInt(mitumori));
        searchModel.setNanKinJutyu(Integer.parseInt(jutyu));
        searchModel.setNhnKinMitumoriKbn(Integer.parseInt(form.getMitumorHiOrRow()));
        searchModel.setNhnKinJutyuKbn(Integer.parseInt(form.getJutyuuHiOrRow()));
        searchModel.setNtp060Syodan(form.getSyodan());
        searchModel.setNcnSid(Integer.parseInt(form.getNcnSid()));


        if (form.getDateFrom() != null) {
            UDate date = new UDate();
            date.setZeroHhMmSs();
            date.setDate(Integer.parseInt(form.getDateFrom().substring(0, 4)),
                    Integer.parseInt(form.getDateFrom().substring(5, 7)),
                    Integer.parseInt(form.getDateFrom().substring(8, 10)));
            searchModel.setNtp060FrDate(date);

        }
        if (form.getDateTo() != null) {
            UDate date = new UDate();
            date.setZeroHhMmSs();
            date.setDate(Integer.parseInt(form.getDateTo().substring(0, 4)),
                    Integer.parseInt(form.getDateTo().substring(5, 7)),
                    Integer.parseInt(form.getDateTo().substring(8, 10)));
            searchModel.setNtp060ToDate(date);
        }
        searchModel.setSortKey1(NullDefault.getInt(form.getSortKey()
                , GSConstNippou.SORT_KEY_NAN_ANKEN));
        searchModel.setOrderKey1(NullDefault.getInt(form.getOrder(), GSConst.ORDER_KEY_ASC));

        //管理者権限がある場合
        CommonBiz commonBiz = new CommonBiz();
        searchModel.setAdminSearch(
                commonBiz.isPluginAdmin(con,
                        reqMdl.getSmodel(),
                        GSConstNippou.PLUGIN_ID_NIPPOU)
                );
        searchModel.setSessionUsrSid(reqMdl.getSmodel().getUsrsid());


        return searchModel;
    }

}
