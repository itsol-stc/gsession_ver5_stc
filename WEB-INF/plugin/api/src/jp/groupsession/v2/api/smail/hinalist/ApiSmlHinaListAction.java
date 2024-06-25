package jp.groupsession.v2.api.smail.hinalist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailBiz;
import jp.groupsession.v2.sml.model.SmlHinaModel;
import jp.groupsession.v2.sml.dao.SmlHinaDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] ショートメールひな形一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlHinaListAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlHinaListAction.class);

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

        ApiSmlHinaListForm form = (ApiSmlHinaListForm) aForm;

        GsMessage gsMsg = new GsMessage(req);
        int sacSid  = form.getSacSid();
        int shnType = form.getShnType();
        int userSid = getSessionUserSid(req);


        // アカウントチェック
        ApiSmlMailBiz biz = new ApiSmlMailBiz();
        ActionErrors  err = biz.validateCheckSmlAccount(con, gsMsg, userSid, sacSid);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        List<SmlHinaModel> list = null;

        try {
            SmlHinaDao  smlHnDao = new SmlHinaDao(con);
            list = smlHnDao.getHinaList(sacSid);
        } catch (SQLException e) {
            log__.error("ショートメールひな形一覧の取得に失敗", e);
        }

        //Result
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        Integer resultCnt = 0;
        if (list != null) {
            for (SmlHinaModel data : list) {
                if (shnType < 0 || shnType == data.getShnCkbn()) {
                    Element result = new Element("Result");
                    resultSet.addContent(result);
                    result.addContent(_createElement("sacSid",  data.getSacSid()));
                    result.addContent(_createElement("shnSid",  data.getShnSid()));
                    result.addContent(_createElement("shnName", data.getShnHname()));
                    result.addContent(_createElement("shnType", data.getShnCkbn()));
                    result.addContent(_createElement("title",   data.getShnTitle()));
                    result.addContent(_createElement("mark",    data.getShnMark()));
                    result.addContent(_createElement("body",    data.getShnBody()));
                    //result.addContent(_createElement("shnJkbn",  data.getShnJkbn()));
                    //result.addContent(_createElement("shnAuid",  data.getShnAuid()));
                    //result.addContent(_createElement("shnAdate", data.getShnAdate()));
                    //result.addContent(_createElement("shnEuid",  data.getShnEuid()));
                    //result.addContent(_createElement("shnEdate", data.getShnEdate()));
                    //result.addContent(_createElement("shnHnameDsp", data.getShnHnameDsp()));
                    resultCnt++;
                }
            }
        }
        resultSet.setAttribute("Count", Integer.toString(resultCnt));
        return doc;
    }

}
