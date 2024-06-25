package jp.groupsession.v2.api.adress.search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.dao.AdrPersonchargeDao;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.adr.model.AdrPersonchargeModel;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.ApiDataTypeUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
/**
 *
 * <br>[機  能] アドレス検索 WEBAPI
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiAdrSearchAction extends AbstractApiAction {
    /**ロガー*/
    private static Log log__ = LogFactory.getLog(ApiAdrSearchAction.class);

    @Override
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        Element resultSet = new Element("ResultSet");
        //アドレス帳プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstAddress.PLUGIN_ID_ADDRESS, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstAddress.PLUGIN_ID_ADDRESS));
            return null;
        }
        Document doc = new Document(resultSet);

        ApiAdrSearchForm form = (ApiAdrSearchForm) aForm;
        ActionErrors err = form.validateCheck();
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        Adr010SearchModel searchMdl = __createSearchModel(form, umodel.getUsrsid(), con);

        ApiAdrSearchDao apiAdrDao = new ApiAdrSearchDao(con);

        //最大件数
        int searchCnt = apiAdrDao.getSearchCount(searchMdl);
        int maxCnt = NullDefault.getInt(form.getResults(), 50);

        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = NullDefault.getInt(form.getPage(), 1);
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }

        searchMdl.setPage(page);
        searchMdl.setMaxViewCount(maxCnt);

        List<ApiAdrDetailModel> list = apiAdrDao.getSearchResultList(searchMdl,
                                                                    getRequestModel(req));
        resultSet.setAttribute("Count", Integer.toString(list.size()));
        resultSet.setAttribute("TotalCount", Integer.toString(searchCnt));
        resultSet.setAttribute("Page", Integer.toString(page));
        resultSet.setAttribute("MaxPage", Integer.toString(maxPage));

        Map<String, CmnUsrmInfModel> userMap = new HashMap<String, CmnUsrmInfModel>();

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
            //AddDate
            Element addDate = new Element("AddDateTime");
            addDate.addContent(ApiDataTypeUtil.getDateTime(data.getAdate()));
            result.addContent(addDate);
            //EditDate
            Element editDate = new Element("EditDateTime");
            editDate.addContent(ApiDataTypeUtil.getDateTime(data.getEdate()));
            result.addContent(editDate);

            //住所情報
            result.addContent(_createElement("AdrZip1", data.getPostNo1()));
            result.addContent(_createElement("AdrZip2", data.getPostNo2()));
            result.addContent(_createElement("AdrTdfk", data.getTdfk()));
            result.addContent(_createElement("AdrAdress1", data.getAddress1()));
            result.addContent(_createElement("AdrAdress2", data.getAddress2()));

            //会社情報
            result.addContent(_createElement("AcoSid", data.getAcoSid()));
            result.addContent(_createElement("AcoName", data.getCompanyName()));
            result.addContent(_createElement("AcoNameKn", data.getCompanyNameKn()));
            result.addContent(_createElement("AcoPostno1", data.getCompanyPostNo1()));
            result.addContent(_createElement("AcoPostno2", data.getCompanyPostNo2()));
            result.addContent(_createElement("AcoTdfk", data.getCompanyTdfk()));
            result.addContent(_createElement("AcoAddr1", data.getCompanyAddress1()));
            result.addContent(_createElement("AcoAddr2", data.getCompanyAddress2()));
            result.addContent(_createElement("AcoUrl", data.getCompanyUrl()));

            result.addContent(_createElement("AbaSid", data.getAbaSid()));
            result.addContent(_createElement("AbaName", data.getCompanyBaseName()));
            result.addContent(_createElement("AbaZip1", data.getCompanyBasePostNo1()));
            result.addContent(_createElement("AbaZip2", data.getCompanyBasePostNo2()));
            result.addContent(_createElement("AbaTdfk", data.getCompanyBaseTdfk()));
            result.addContent(_createElement("AbaAdress1", data.getCompanyBaseAddress1()));
            result.addContent(_createElement("AbaAdress2", data.getCompanyBaseAddress2()));

            //担当者の設定
            AdrPersonchargeDao tantoDao = new AdrPersonchargeDao(con);
            List<AdrPersonchargeModel> tantoDataList
                    = tantoDao.getTantoListForAddress(adrSid);
            Element tantoSet = new Element("TantoSet");
            result.addContent(tantoSet);
            tantoSet.setAttribute("Count", Integer.toString(tantoDataList.size()));
            for (AdrPersonchargeModel tantoData : tantoDataList) {
                Element tanto = new Element("Tanto");
                tantoSet.addContent(tanto);

                tanto.addContent(_createElement("UsrSid", tantoData.getUsrSid()));
                CmnUsrmInfModel user;
                String usrSidStr = String.valueOf(tantoData.getUsrSid());
                if (!userMap.containsKey(usrSidStr)) {
                    CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con);
                    //user = usrDao.select(tantoData.getUsrSid());
                    user = usrDao.selectUserNameAndJtkbn(tantoData.getUsrSid());
                    userMap.put(usrSidStr, user);
                }
                user = userMap.get(usrSidStr);
                tanto.addContent(_createElement("UsrName",
                        user.getUsiSei() + " " + user.getUsiMei()));
                tanto.addContent(_createElement("UsrUkoFlg", user.getUsrUkoFlg()));
            }

            //ラベルの設定
            AdrLabelDao labelDao = new AdrLabelDao(con);
            List<AdrLabelModel> labelDataList
                    = labelDao.selectBelongLabelList(adrSid);
            Element labelSet = new Element("LabelSet");
            result.addContent(labelSet);
            labelSet.setAttribute("Count", Integer.toString(labelDataList.size()));
            for (AdrLabelModel labelData : labelDataList) {
                Element label = new Element("Label");
                labelSet.addContent(label);

                label.addContent(_createElement("AlbSid", labelData.getAlbSid()));
                label.addContent(_createElement("AlbName", labelData.getAlbName()));
                label.addContent(_createElement("AlcSid", labelData.getAlcSid()));
            }

        }

        log__.debug("createXml end");

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
            ApiAdrSearchForm form,
            int userSid,
            Connection con)
            throws SQLException {
        Adr010SearchModel searchMdl = new Adr010SearchModel();
        searchMdl.setSessionUser(userSid);
        searchMdl.setSortKey(NullDefault.getInt(form.getSortKey(), Adr010Const.SORTKEY_UNAME));
        searchMdl.setOrderKey(NullDefault.getInt(form.getOrderKey(), Adr010Const.ORDERKEY_ASC));
        searchMdl.setCmdMode(Adr010Const.CMDMODE_NAME);

        /** 企業コード */
        searchMdl.setCoCode(form.getCoCode());
        /** 会社名 */
        searchMdl.setCoName(form.getCoName());
        /** 会社名カナ */
        searchMdl.setCoNameKn(form.getCoNameKn());
        /** 支店・営業所名 */
        searchMdl.setCoBaseName(form.getCoBaseName());
        /** 業種 */
        searchMdl.setAtiSid(NullDefault.getInt(form.getAtiSid(), -1));
        /** 都道府県 */
        searchMdl.setTdfk(NullDefault.getInt(form.getTdfk(), -1));
        /** 備考 */
        searchMdl.setBiko(form.getBiko());


        /** 氏名カナ 頭文字 */
        searchMdl.setUnameKnHead(form.getUnameKnHead());

        /** 会社名 頭文字 */
        searchMdl.setCnameKnHead(form.getCnameKnHead());

        /** グループ */
        searchMdl.setGroup(NullDefault.getInt(form.getGroup(), -1));
        /** ユーザ */
        searchMdl.setUser(NullDefault.getInt(form.getUser(), -1));

        /** 氏名 姓 */
        searchMdl.setUnameSei(form.getUnameSei());
        /** 氏名 名 */
        searchMdl.setUnameMei(form.getUnameMei());
        /** 氏名カナ 姓 */
        searchMdl.setUnameSeiKn(form.getUnameSeiKn());
        /** 氏名カナ 名 */
        searchMdl.setUnameMeiKn(form.getUnameMeiKn());
        /** 役職 */
        searchMdl.setPosition(NullDefault.getInt(form.getPosition(), -1));
        /** E-MAIL */
        searchMdl.setMail(form.getMail());

        /** ラベル */
        searchMdl.setLabel(form.getLabel());

        /** 会社SID */
        searchMdl.setCompanySid(
                (form.getCompanySid() == null ? null
                        : Integer.valueOf(NullDefault.getInt(form.getCompanySid(), 0))));

        /** 会社拠点SID */
        searchMdl.setCompanySid(
                (form.getCompanyBaseSid() == null ? null
                        : Integer.valueOf(NullDefault.getInt(form.getCompanyBaseSid(), 0))));


        return searchMdl;
    }

}
