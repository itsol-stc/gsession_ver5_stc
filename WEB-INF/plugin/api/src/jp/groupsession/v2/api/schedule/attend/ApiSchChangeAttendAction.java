package jp.groupsession.v2.api.schedule.attend;

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
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.sch040.Sch040Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール 出欠確認応答編集 WEBAPI アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchChangeAttendAction extends AbstractApiSchAction {
    /** ログ */
    private static Log log__ =
            LogFactory.getLog(ApiSchChangeAttendAction.class);
    @Override
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");

        ApiSchChangeAttendForm form = (ApiSchChangeAttendForm) aForm;


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

        ScheduleSearchModel oldMdl = null;
        //編集前データ取得
        if (scdSid > 0) {
            SchCommonBiz commonBiz = new SchCommonBiz(reqMdl);
            SchAdmConfModel adminConf = commonBiz.getAdmConfModel(con);
            Sch040Biz sch040biz = new Sch040Biz(con, reqMdl);
            oldMdl = sch040biz.getSchData(scdSid, adminConf, con);
        }

        err = eBiz.validateExistData(err, gsMsg.getMessage("cmn.delete"), oldMdl);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        //編集権減確認
        err = form.validatePowerCheck(req, reqMdl, con);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        ApiSchChangeAttendParamModel param = new ApiSchChangeAttendParamModel();
        param.setParam(aForm);

        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            ApiSchChangeAttendBiz biz = new ApiSchChangeAttendBiz(con, reqMdl, cntCon);
            //アプリケーションRoot
            String appRootPath = getAppRootPath();
            //プラグイン設定
            PluginConfig plconf = getPluginConfig(req);

            PluginConfig pconfig = getPluginConfigForMain(plconf, con);
            CommonBiz cmnBiz = new CommonBiz();
            boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);

            biz.updateScheduleDateAns(reqMdl,
                    param,
                    sessionUsrSid,
                    appRootPath,
                    plconf,
                    smailPluginUseFlg);
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("スケジュール登録に失敗しました" + e);
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
