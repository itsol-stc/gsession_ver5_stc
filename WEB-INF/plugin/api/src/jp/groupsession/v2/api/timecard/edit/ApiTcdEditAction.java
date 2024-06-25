package jp.groupsession.v2.api.timecard.edit;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.man.plugin.ApiManPluginAction;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.model.TcdTcdataModel;

/**
 * <br>[機  能] タイムカード情報の登録・変更を行うWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiTcdEditAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiManPluginAction.class);

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

        //タイムカードプラグインアクセス権限
        if (!canAccsessSelectPlugin(
                GSConstTimecard.PLUGIN_ID_TIMECARD, req)) {
            addErrors(req, addCantAccsessPluginError(
                    req, null, GSConstTimecard.PLUGIN_ID_TIMECARD));

            return null;
        }

        ApiTcdEditForm thisForm = (ApiTcdEditForm) form;
        RequestModel reqMdl = getRequestModel(req);
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        int userSid = umodel.getUsrsid();
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(userSid, con);

        //入力チェック
        ActionErrors errors = thisForm.validateCheck(reqMdl, con, admConf, userSid);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return null;
        }

        //タイムカード情報を登録・更新
        boolean commit = false;
        TcdTcdataModel tcMdl = null;
        try {
            ApiTcdEditParamModel paramMdl = new ApiTcdEditParamModel();
            paramMdl.setParam(form);
            ApiTcdEditBiz ateBiz = new ApiTcdEditBiz();
            tcMdl = ateBiz.updateTcdTcdata(
                    paramMdl, umodel, con, reqMdl);
            paramMdl.setFormData(form);
            commit = true;

        } catch (SQLException e) {
            //SQL実行時例外
            log__.error("タイムカード更新に失敗" + e);
            con.rollback();
            throw e;
        } finally {
            if (commit) {
                con.commit();
            }
        }

        //ログ出力
        UDate dDate = tcMdl.getTcdDate();
        int targetSid = tcMdl.getUsrSid();
        TimecardBiz biz = new TimecardBiz(reqMdl);
        String message = null;

        if (thisForm.getCmdMode() == GSConstTimecard.CMDMODE_ADD) {
            message = getInterMessage(req, "cmn.entry");
        } else {
            message = getInterMessage(req, "cmn.edit");
        }
        String opLog = __opLogContent(con, reqMdl, dDate, targetSid);        
        biz.outPutApiLog(reqMdl, con, umodel.getUsrsid(),
                this.getClass().getCanonicalName(),
                message, GSConstLog.LEVEL_TRACE, opLog);
        //Result
        Element result = new Element("Result");
        Document doc = new Document(result);

        result.addContent("OK");
        return doc;
    }

    /**
     * <br>[機  能] オペレーションログ内容作成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param date 日付
     * @param userSid ユーザSID
     * @return 内容
     */
   private String __opLogContent(Connection con, RequestModel reqMdl,
           UDate date, int userSid) throws SQLException {

       GsMessage gsMsg = new GsMessage(reqMdl);
       StringBuilder value = new StringBuilder();
       //ユーザ名取得
       CmnUsrmInfDao cmnDao = new CmnUsrmInfDao(con);
       CmnUsrmInfModel cmnMdl = cmnDao.select(userSid);

       value.append("[" + gsMsg.getMessage("cmn.user.name") + "] ");
       value.append(cmnMdl.getUsiName());
       value.append("\r\n[" + gsMsg.getMessage("cmn.date2") + "] ");
       value.append(date.getStrYear() + gsMsg.getMessage("cmn.year2"));
       value.append(date.getStrMonth() + gsMsg.getMessage("cmn.month"));
       value.append(date.getStrDay() + gsMsg.getMessage("cmn.day"));

       return value.toString();
   }
}