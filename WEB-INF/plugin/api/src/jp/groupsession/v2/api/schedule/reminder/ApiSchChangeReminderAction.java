package jp.groupsession.v2.api.schedule.reminder;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

import jp.groupsession.v2.api.schedule.AbstractApiSchAction;
import jp.groupsession.v2.api.schedule.edit.ApiSchEditBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール リマインダー編集 WEBAPI アクション
 * <br>[解  説] 自身のユーザスケジュールのリマインダー情報を更新する。
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchChangeReminderAction extends AbstractApiSchAction {
    /** ログ */
    private static Log log__ =
            LogFactory.getLog(ApiSchChangeReminderAction.class);
    @Override
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");

        ApiSchChangeReminderForm form = (ApiSchChangeReminderForm) aForm;


        int sessionUsrSid = umodel.getUsrsid(); //セッションユーザSID
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        ActionErrors err = form.validateCheck(gsMsg);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }
        MlCountMtController cntCon = getCountMtController(req);
        ApiSchEditBiz eBiz = new ApiSchEditBiz(con, reqMdl, cntCon);
        int scdSid = Integer.parseInt(form.getSchSid());

        //対象ユーザチェック
        err = eBiz.validateUserCheck(err, scdSid, sessionUsrSid, gsMsg.getMessage("cmn.delete"));
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        ApiSchChangeReminderParamModel param = new ApiSchChangeReminderParamModel();
        param.setParam(aForm);

        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            ApiSchChangeReminderBiz biz = new ApiSchChangeReminderBiz(con, reqMdl);
            biz.updateScheduleReminder(param, sessionUsrSid);
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("スケジュール更新に失敗しました" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        Document doc = new Document();
        doc.addContent(_createElement("Result", "OK"));
        return doc;
    }
}
