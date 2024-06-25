package jp.groupsession.v2.api.webmail.labellist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;

/**
 * <br>[機  能] Webメールラベル一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWmlLabelListAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlLabelListAction.class);

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

        //WEBメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstWebmail.PLUGIN_ID_WEBMAIL));
            return null;
        }

        ApiWmlLabelListForm form = (ApiWmlLabelListForm) aForm;

        int wacSid  = form.getWacSid();

        List<WmlLabelModel> list = null;

        try {
            // アカウント使用可否チェック
            WmlDao wmlDao = new WmlDao(con);
            if (!wmlDao.canUseAccount(wacSid, getSessionUserSid(req))) {
                // アカウントがない場合
                RequestModel reqMdl = getRequestModel(req);
                GsMessage    gsMsg  = new GsMessage(reqMdl);
                ActionErrors errors = new ActionErrors();
                ActionMessage msg = new ActionMessage("search.data.notfound",
                                                      gsMsg.getMessage("wml.102"));
                StrutsUtil.addMessage(errors, msg, "account");
                addErrors(req, errors);
                return null;
            }

            WmlLabelDao  wmllblDao = new WmlLabelDao(con);
            list = wmllblDao.getLabelList(wacSid);
        } catch (SQLException e) {
            log__.error("WEBメールラベル一覧の取得に失敗", e);
        }

        //Result
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        Integer resultCnt = 0;
        if (list != null) {
            for (WmlLabelModel data : list) {
                Element result = new Element("Result");
                resultSet.addContent(result);
                result.addContent(_createElement("wacSid",   data.getWacSid()));
                result.addContent(_createElement("wlbSid",   data.getWlbSid()));
                result.addContent(_createElement("wlbName",  data.getWlbName()));
                result.addContent(_createElement("wlbOrder", data.getWlbOrder()));
            }
            resultCnt = list.size();
        }
        resultSet.setAttribute("Count", Integer.toString(resultCnt));
        return doc;
    }

}
