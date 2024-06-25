package jp.groupsession.v2.api.smail.countm;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] ショートメール未読件数を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCountMAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiCountMAction.class);

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

        //ショートメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstSmail.PLUGIN_ID_SMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstSmail.PLUGIN_ID_SMAIL));
            return null;
        }
        //未確認の件数を取得する。
        SmlCommonBiz biz = new SmlCommonBiz(getRequestModel(req));

        int count = 0;
        try {
            count = biz.getUnopenedMsgCnt(
                    biz.getDefaultAccount(con, umodel.getUsrsid()), con);
        } catch (SQLException e) {
            log__.error("未開封メッセージカウントの取得に失敗", e);
        }

        //Result
        Element result = new Element("Result");
        Document doc = new Document(result);

        //
        log__.debug("createXml start");
        result.addContent(Integer.toString(count));
        log__.debug("createXml end");
        return doc;
    }

}
