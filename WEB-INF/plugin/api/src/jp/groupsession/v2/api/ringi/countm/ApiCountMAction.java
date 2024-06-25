package jp.groupsession.v2.api.ringi.countm;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RingiDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] 稟議受信件数を取得するWEBAPIアクション
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

        //稟議プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_RINGI, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_RINGI));
            return null;
        }
        //受信件数を取得する。
        RingiDao dao = new RingiDao(con);
        int count = 0;
        try {
            count = dao.getRingiDataCount(umodel.getUsrsid(), RngConst.RNG_MODE_JYUSIN);
        } catch (SQLException e) {
            log__.error("受信カウントの取得に失敗", e);
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
