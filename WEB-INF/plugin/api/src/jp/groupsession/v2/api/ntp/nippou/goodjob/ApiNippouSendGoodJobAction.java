package jp.groupsession.v2.api.ntp.nippou.goodjob;

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

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpGoodDao;
import jp.groupsession.v2.ntp.ntp030.Ntp030Biz;
import jp.groupsession.v2.ntp.ntp030.Ntp030Form;
import jp.groupsession.v2.ntp.ntp030.Ntp030ParamModel;
/**
 * <br>[機  能] WEBAPI 日報 いいね！追加アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouSendGoodJobAction extends AbstractApiAction {
    /**ロガークラス*/
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        RequestModel reqMdl = getRequestModel(req);
        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }
        Document doc = new Document();
        ApiNippouSendGoodJobForm thisForm = (ApiNippouSendGoodJobForm) form;
        ActionErrors err = thisForm.validateCheck(con, reqMdl);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }


        int nipSid = NullDefault.getInt(thisForm.getNipSid(), -1);
        if (umodel != null && nipSid > 0) {
            //セッションユーザSID
            int sessionUsrSid = umodel.getUsrsid();
            NtpGoodDao gDao = new NtpGoodDao(con);
            int cnt = 0;
            cnt = gDao.count(nipSid, sessionUsrSid);
            if (cnt > 0) {
                //この日報には「いいね!」しています。
//                ActionErrors errors = new ActionErrors();
//                StrutsUtil.addMessage(errors, msg, "nipSid");
                Element error = new Element("Errors");
                error.addContent(_createElement("Message", "この日報には「いいね!」しています。"));
                doc.addContent(error);
                return doc;
            }
            MlCountMtController cntCon = getCountMtController(req);
            Ntp030Biz biz = new Ntp030Biz(con, reqMdl, cntCon);


            Ntp030Form ntp030Form = new Ntp030Form();

            //プラグイン設定を取得する
            PluginConfig pconfig
                = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

            CommonBiz cmnBiz = new CommonBiz();

            //ショートメールは利用可能か判定
            if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_SMAIL, pconfig)) {
                ntp030Form.setSmailUseOk(GSConstNippou.PLUGIN_USE);
            } else {
                ntp030Form.setSmailUseOk(GSConstNippou.PLUGIN_NOT_USE);
            }

            boolean commitFlg = false;
            con.setAutoCommit(false);
            try {
                //コメント新規登録
                Ntp030ParamModel paramMdl = new Ntp030ParamModel();
                paramMdl.setParam(form);
                biz.insertGood(
                        paramMdl, nipSid, sessionUsrSid, getAppRootPath(), getPluginConfig(req),
                        reqMdl);
                paramMdl.setFormData(form);

                commitFlg = true;
            } catch (SQLException e) {
                log__.error("いいねの登録に失敗しました" + e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }

            //最新のいいねの件数を取得

            cnt = 0;
            cnt = gDao.count(nipSid);
            doc.addContent(_createElement("Result", cnt));
        }
        return doc;
    }
}
