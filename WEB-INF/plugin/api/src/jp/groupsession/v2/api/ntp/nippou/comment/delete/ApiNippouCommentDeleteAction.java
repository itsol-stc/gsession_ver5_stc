package jp.groupsession.v2.api.ntp.nippou.comment.delete;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.biz.NtpUsedDataBiz;
import jp.groupsession.v2.ntp.dao.NtpCommentDao;
/**
 * <br>[機  能] WEBAPI 日報 コメント削除アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouCommentDeleteAction extends AbstractApiAction {

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
        ApiNippouCommentDeleteForm thisForm = (ApiNippouCommentDeleteForm) form;

        ActionErrors errors = thisForm.validateCheck(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }
        Document doc = new Document();
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            int npcSid = Integer.parseInt(thisForm.getNpcSid());

            //日報コメント情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            NtpUsedDataBiz usedDataBiz = new NtpUsedDataBiz(con);
            usedDataBiz.insertNtpCommentSize(npcSid, false);

            //コメント削除
            NtpCommentDao npcDao = new NtpCommentDao(con);
            npcDao.delete(npcSid);

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("コメントの登録に失敗しました" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }
        doc.addContent(_createElement("Result", "OK"));
        return doc;
    }

}
