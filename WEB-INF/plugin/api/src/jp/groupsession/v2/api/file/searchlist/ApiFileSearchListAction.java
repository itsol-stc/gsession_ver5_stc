package jp.groupsession.v2.api.file.searchlist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;

/**
 * <br>[機  能] キーワード検索ディレクトリ一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileSearchListAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiFileSearchListAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");

        ApiFileSearchListForm thisForm = (ApiFileSearchListForm) form;
        RequestModel reqMdl = getRequestModel(req);
        // ファイル管理プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstFile.PLUGIN_ID_FILE, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstFile.PLUGIN_ID_FILE));
            return null;
        }
        // 入力チェック
        ActionErrors errors = thisForm.validateSearchList(con, reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }
        // 検索
        ArrayList<ApiFileSearchModel> searchResultList = new ArrayList<ApiFileSearchModel>();
        try {
            ApiFileSearchListBiz biz = new ApiFileSearchListBiz(con);
            searchResultList.addAll(biz.getSearchResultList(thisForm.getKeyword(),
                    thisForm.getFcbSid(), thisForm.getFdrSid(), thisForm.getFcbKbn(), umodel));
        } catch (SQLException e) {
            log__.error("ディレクトリ検索一覧の取得に失敗", e);
        }

        // ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        int count = 0;
        if (searchResultList != null && !searchResultList.isEmpty()) {
            // XMLデータ作成
            for (ApiFileSearchModel data : searchResultList) {
                Element result = new Element("Result");
                resultSet.addContent(result);
                result.addContent(_createElement("FcbSid",   data.getFcbSid()));
                result.addContent(_createElement("FdrSid",   data.getFdrSid()));
                result.addContent(_createElement("FdrName",   data.getFdrName()));
                result.addContent(_createElement("FdrParentSid",   data.getFdrParentSid()));
                result.addContent(_createElement("FdrKbn",   data.getFdrKbn()));
                result.addContent(_createElement("BinSid",  data.getBinSid()));
                result.addContent(_createElement("FileSize", data.getFileSize()));
                result.addContent(_createElement("FdrEdate", data.getEditDate()));
                result.addContent(_createElement("LockKbn",  data.getFflLockKbn()));
                result.addContent(_createElement("LockUsrSid",  data.getFflLockUser()));
                result.addContent(_createElement("WriteKbn",  data.getWriteKbn()));
                result.addContent(_createElement("EditUsrKbn", data.getEditUsrKbn()));
                result.addContent(_createElement("EditUsrSid", data.getEditUsrSid()));
                result.addContent(_createElement("EditUsrName", data.getEditUsrName()));
                result.addContent(_createElement("EditUsrUkoFlg", data.getEditUsrUkoFlg()));
                
                if (data.getErrlKbn() == GSConstFile.ERRL_KBN_ON
                        && data.getFdrKbn() == GSConstFile.DIRECTORY_KBN_FILE) {
                    String tradeDate = "";
                    String tradeTarget = "";
                    String money = "";
                    String moneyName = "";
                    if (data.getFdrTradeDate() != null) {
                        tradeDate = data.getFdrTradeDate().getDateString("/");
                    }
                    
                    if (data.getFdrTradeTarget() != null) {
                        tradeTarget = data.getFdrTradeTarget();
                    }
                    
                    if (data.getFdrTradeMoney() != null
                            && data.getFdrTradeMoneyKbn() == GSConstFile.MONEY_KBN_ON) {
                        money = StringUtil.toCommaUnderZeroTrim(
                                data.getFdrTradeMoney().toPlainString());
                    }
                    
                    if (data.getFdrErrlMoneyType() != null) {
                        moneyName = data.getFdrErrlMoneyType();
                    }
                    
                    result.addContent(_createElement("FdrErrlDate", tradeDate));
                    result.addContent(_createElement("FdrErrlTarget", tradeTarget));
                    result.addContent(_createElement("FdrErrlMoney", money));
                    result.addContent(_createElement("FdrErrlMoneyType", moneyName));
                }
            }
            count = searchResultList.size();
        }
        resultSet.setAttribute("Count", Integer.toString(count));
        log__.debug("createXml end");
        return doc;
    }
}
