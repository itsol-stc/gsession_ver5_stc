package jp.groupsession.v2.api.smail.account.list;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.smail.account.ApiSmlAccountDao;
import jp.groupsession.v2.api.smail.account.ApiSmlAccountModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlBanDestDao;
import jp.groupsession.v2.sml.dao.SmlPushUconfDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlPushUconfModel;

/**
 * <br>[機  能] 使用可能なショートメールアカウント一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlAccountListAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlAccountListAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param aForm フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        //ショートメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstSmail.PLUGIN_ID_SMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstSmail.PLUGIN_ID_SMAIL));
            return null;
        }

        List<SmlAccountModel> list = null;
        Map<Integer, Integer> pushFlgMap = new HashMap<>();
        Map<Integer, Integer> midokuMap = new HashMap<>();
        HashMap<Integer, List<ApiSmlAccountModel>> autoMap = null;
        HashMap<Integer, Integer> ukoFlgMap = null;

        int userSid = getSessionUserSid(req);
        try {
            SmlAccountDao    sacDao = new SmlAccountDao(con);
            SmlPushUconfDao  spuDao = new SmlPushUconfDao(con);

            // 自分が使用可能なｱｶｳﾝﾄ
            list = sacDao.getAccountList(userSid);
            if (list != null && list.size() > 0) {
                List<Integer> sacSidList = new ArrayList<Integer>();
                for (int i = 0; i < list.size(); i++) {
                    sacSidList.add(list.get(i).getSacSid());
                }
                autoMap = this.getSendToList(con, userSid, sacSidList);

                ApiSmlAccountDao  asaDao = new ApiSmlAccountDao(con);
                ukoFlgMap = asaDao.getUkoFlgMap(sacSidList);
            }
            // Push通知の利用フラグ取得
            ArrayList<SmlPushUconfModel> spuList = spuDao.select(userSid);
            for (SmlPushUconfModel spuMdl : spuList) {
                pushFlgMap.put(spuMdl.getSacSid(), spuMdl.getSpuPushuse());
            }
            //未確認の件数を取得する。
            SmlCommonBiz biz = new SmlCommonBiz(getRequestModel(req));
            midokuMap = biz.getAccountUnopenedMsgCnt(list, con);
        } catch (SQLException e) {
            log__.error("ショートメール使用可能アカウント一覧の取得に失敗", e);
        }

        //Result
        Element resultSet  = new Element("ResultSet");
        Document doc = new Document(resultSet);
        Integer resultCnt = 0;
        if (list != null) {
            for (SmlAccountModel data : list) {
                Element result = new Element("Result");
                resultSet.addContent(result);

                List<ApiSmlAccountModel> autoList = null;
                if (autoMap != null) {
                    autoList = autoMap.get(data.getSacSid());
                }
                this.setSmlAccountElement(
                        result, data, autoList, ukoFlgMap, pushFlgMap, midokuMap, true, req);
            }
            resultCnt = list.size();
        }
        resultSet.setAttribute("Count", Integer.toString(resultCnt));
        return doc;
    }

    /**
     * <br>[機  能] ショートメール アカウント自動送信先一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con        コネクション
     * @param userSid    ユーザSID
     * @param sacSidList 使用可能アカウントSID一覧
     * @return アカウントSIDをキーにした自動送信先情報(SmlAccountAutoDestModel)一覧
     * @throws SQLException 実行例外
     */
    public HashMap<Integer, List<ApiSmlAccountModel>> getSendToList(Connection con,
                                                                         int userSid,
                                                                         List<Integer> sacSidList)
                throws SQLException {

        ApiSmlAccountDao  asaDao = new ApiSmlAccountDao(con);
        SmlAccountDao     sacDao = new SmlAccountDao(con);

        List<Integer> accSidList = new ArrayList<Integer>();

        // 自動送信先情報一覧を取得
        HashMap<Integer, List<ApiSmlAccountModel>> ret
                                                  = asaDao.getAutoDestListMap(sacSidList);

        if (ret != null && ret.size() > 0) {
            Map<Integer, Integer> banSidMap = null;
            HashMap<Integer, Integer> ukoFlgMap = null;
            Map<Integer, SmlAccountModel> sacMap = new HashMap<Integer, SmlAccountModel>();

            // 全ての自動宛先SIDを取得(重複無し)
            for (Integer key : ret.keySet()) {
                List<ApiSmlAccountModel> value = ret.get(key);
                if (value != null && value.size() > 0) {
                    for (ApiSmlAccountModel mdl : value) {
                        Integer saaSid = Integer.valueOf(mdl.getSaaSid());
                        if (!accSidList.contains(saaSid)) { // 重複チェック
                            accSidList.add(saaSid);
                        }
                    }
                }
            }

            // 送信制限されているアカウントSIDをリストから除外
            SmlBanDestDao banDao = new SmlBanDestDao(con);
            List<Integer> banSidList = banDao.getBanDestUsrSidList(userSid); // 除外ユーザSIDを取得

            // ユーザSID → アカウントSID へ変換する為の一覧データを取得
            if (banSidList != null && banSidList.size() > 0) {
                banSidMap = asaDao.selectFromUsrSids(banSidList);
            }

            if (banSidMap != null) {
                for (int i = accSidList.size() - 1; i >= 0; i--) {
                    Integer sacSid = accSidList.get(i);
                    if (banSidMap.containsValue(sacSid)) {
                        accSidList.remove(i); // 該当するアカウントSIDを除外
                    }
                }
            }

            // 自動宛先のアカウントSID一覧から無効ユーザを調査
            if (accSidList.size() > 0) {
                List<SmlAccountModel> atesakiList = sacDao.select(accSidList);
                for (SmlAccountModel mdl : atesakiList) {
                    sacMap.put(mdl.getSacSid(), mdl);
                }

                // 抽出したアカウントのうち、無効ユーザのアカウントSIDを抽出
                ukoFlgMap = asaDao.getUkoFlgMap(accSidList);
            }

            // 制限リストにあるアカウントをデータ一覧から除外
            for (Integer key : ret.keySet()) {
                List<ApiSmlAccountModel> value = ret.get(key);
                for (int i = value.size() - 1; i >= 0; i--) {
                    ApiSmlAccountModel mdl = value.get(i);
                    Integer limitSid = Integer.valueOf(mdl.getSaaSid());
                    if (!accSidList.contains(limitSid)) {
                        value.remove(i); // 該当するアカウントSIDがない場合 → 除外
                    } else {
                        // アカウント情報更新
                        if (sacMap.containsKey(limitSid)) {
                            mdl.setSaaName(sacMap.get(limitSid).getSacName());  // アカウント名取得
                        }
                        if (ukoFlgMap != null && ukoFlgMap.containsKey(limitSid)) {
                            mdl.setUkoFlg(ukoFlgMap.get(limitSid).intValue()); // 無効フラグ取得
                        }
                    }
                }
            }

        }

        return ret;
    }

    /**
     * <br>[機  能] ショートメール アカウント情報をXMLのresult属性にセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param result     エレメント
     * @param acMdl      アカウント情報
     * @param autoList   自動宛先一覧
     * @param ukoFlgMap  ユーザ無効フラグ一覧
     * @param pushFlgMap Push通知フラグ一覧
     * @param midokuMap  未読件数一覧
     * @param addParamFlg  パラメータ追加フラグ（プッシュ通知フラグ/未読件数）
     * @param req        リクエスト
     * @throws Exception 実行例外
     */
    protected void setSmlAccountElement(Element result, SmlAccountModel acMdl,
                         List<ApiSmlAccountModel> autoList,
                         HashMap<Integer, Integer> ukoFlgMap,
                         Map<Integer, Integer> pushFlgMap,
                         Map<Integer, Integer> midokuMap,
                         boolean addParamFlg,
                         HttpServletRequest req)
            throws Exception {


        if (acMdl != null) {
            Integer sacSid = Integer.valueOf(acMdl.getSacSid());
            int     ukoFlg = GSConst.YUKOMUKO_YUKO;
            if (ukoFlgMap != null && ukoFlgMap.containsKey(sacSid)) {
                ukoFlg = ukoFlgMap.get(sacSid).intValue();
            }

            result.addContent(_createElement("sacSid",   sacSid));
            result.addContent(_createElement("sacType",  (acMdl.getUsrSid() > 0 ? 0 : 1)));
            result.addContent(_createElement("sacName",  acMdl.getSacName()));
            result.addContent(_createElement("sacUkoFlg", ukoFlg));
            result.addContent(_createElement("theme",    acMdl.getSacTheme()));
            result.addContent(_createElement("mailType", acMdl.getSacSendMailtype()));

            if (addParamFlg) {
                //Push通知フラグ
                if (pushFlgMap.containsKey(sacSid)) {
                    result.addContent(_createElement("pushFlg", pushFlgMap.get(sacSid)));
                } else {
                    result.addContent(_createElement("pushFlg", GSConstSmail.ACCOUNT_PUSH_OFF));
                }
                //未読件数
                if (midokuMap.containsKey(sacSid)) {
                    result.addContent(_createElement("midokuCount", midokuMap.get(sacSid)));
                } else {
                    result.addContent(_createElement("midokuCount", 0));
                }
            }

            String quotesStr = "";
            if (acMdl.getSacQuotes() != GSConstSmail.SAC_QUOTES_NONE) {
                // 引用符が「なし」以外は文字列取得
                quotesStr = SmlCommonBiz.getViewMailQuotes(acMdl.getSacQuotes(),
                                                            getRequestModel(req));
            }
            result.addContent(_createElement("quotes",   quotesStr));

            if (autoList != null) {
                Element autoDestSet = new Element("AutoDestSet");
                result.addContent(autoDestSet);
                for (ApiSmlAccountModel mdl : autoList) {
                    Element elm = new Element("AutoDest");
                    elm.addContent(_createElement("saaSid",  mdl.getSaaSid()));
                    elm.addContent(_createElement("saaType", mdl.getSaaType()));
                    elm.addContent(_createElement("saaName", mdl.getSaaName()));
                    elm.addContent(_createElement("saaUkoFlg", mdl.getUkoFlg()));
                    autoDestSet.addContent(elm);
                }
                autoDestSet.setAttribute("Count", Integer.toString(autoList.size()));
            }
        }
    }
}
