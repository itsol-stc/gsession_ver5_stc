package jp.groupsession.v2.api.ntp.nippou.delete;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.ntp.nippou.edit.ApiNippouEditBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

/**
*
* <br>[機  能] WEBAPI 日報削除アクション
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class ApiNippouDeleteAction extends AbstractApiAction {
    /** ログ */
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
        ApiNippouDeleteForm thisForm = (ApiNippouDeleteForm) form;
        ActionErrors err = thisForm.validateCheck(con, getRequestModel(req));
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        ApiNippouEditBiz biz = new ApiNippouEditBiz(con, getRequestModel(req));
        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {
            biz.deleteNippou(Integer.parseInt(thisForm.getNipSid()), con);
            commitFlg = true;
        } catch (Exception e) {
            commitFlg = false;
            log__.error("日報削除に失敗しました" + e);
            throw e;
        } finally {
            if (!commitFlg) {
                con.rollback();
            } else {
                con.commit();
            }
        }
        Document doc = new Document();
        doc.addContent(_createElement("Result", "OK"));
        return doc;
    }

}
