package jp.groupsession.v2.api.ntp.anken.delete;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.dao.NtpAnMemberDao;
import jp.groupsession.v2.ntp.dao.NtpAnMemberHistoryDao;
import jp.groupsession.v2.ntp.dao.NtpAnShohinDao;
import jp.groupsession.v2.ntp.dao.NtpAnShohinHistoryDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenHistoryDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenPermitDao;
import jp.groupsession.v2.ntp.ntp061.Ntp061Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 * <br>[機  能] 日報 案件削除するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNtpAnkenDeleteAction extends AbstractApiAction {

    /**ロガー クラス*/
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

        ApiNtpAnkenDeleteForm thisForm = (ApiNtpAnkenDeleteForm) form;

        ActionErrors err = thisForm.validateCheck(con, getRequestModel(req));
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }
        int nanSid = Integer.parseInt(thisForm.getNanSid());

        Ntp061Biz biz = new Ntp061Biz(
                con, getCountMtController(req), getRequestModel(req));
        if (!biz.isEditable(nanSid)) {
            GsMessage gsMsg = new GsMessage(req);
            ActionMessage msg = new ActionMessage("error.edit.power.user",
                    gsMsg.getMessage("cmn.edit"), gsMsg.getMessage("cmn.edit"));
            StrutsUtil.addMessage(err, msg, "error.edit.power.user");
            addErrors(req, err);
            return null;
        }

        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {

            NtpAnkenDao ankenDao = new NtpAnkenDao(con);
            NtpAnkenHistoryDao ankenHisDao = new NtpAnkenHistoryDao(con);
            ankenDao.delete(nanSid);
            ankenHisDao.deleteAnken(nanSid);

            //案件商品情報の削除
            NtpAnShohinDao anShohinDao = new NtpAnShohinDao(con);
            NtpAnShohinHistoryDao anShohinHisDao = new NtpAnShohinHistoryDao(con);
            anShohinDao.delete(nanSid);
            anShohinHisDao.deleteAnken(nanSid);

            //メンバー削除
            NtpAnMemberDao anMemberDao = new NtpAnMemberDao(con);
            NtpAnMemberHistoryDao anMemberHisDao = new NtpAnMemberHistoryDao(con);
            anMemberDao.delete(nanSid);
            anMemberHisDao.deleteAnken(nanSid);

            //案件権限情報削除
            NtpAnkenPermitDao napDao = new NtpAnkenPermitDao(con);
            napDao.delete(nanSid);


            commitFlg = true;


        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
        Element result = new Element("Result");
        Document doc = new Document(result);

        result.addContent("OK");

        return doc;
    }

}
