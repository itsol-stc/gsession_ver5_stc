package jp.groupsession.v2.api.cmn.otplogin;

import java.io.IOException;
import java.net.SocketException;
import java.sql.Connection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.http.BasicAuthorizationUtil;
import jp.groupsession.v2.api.biz.ApiConfBiz;
import jp.groupsession.v2.api.biz.ApiTokenBiz;
import jp.groupsession.v2.api.cmn.login.ApiCmnLoginAction;
import jp.groupsession.v2.api.model.ApiTokenModel;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnOtpTokenDao;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.login.UserAgent;
import jp.groupsession.v2.cmn.login.biz.GSLoginBiz;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.plugin.api.biz.IApiConfBiz;
import jp.groupsession.v2.mbh.interfaces.MbhClassCreater;
import jp.groupsession.v2.mbh.interfaces.api.login.IMbhLoginHistoryBiz;
/**
 *
 * <br>[機  能] ワンタイムパスワード認証API アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCmnOtploginAction extends ApiCmnLoginAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiCmnOtploginAction.class);
    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        try {
            removeSession(req);

            ApiConfBiz confBiz = new ApiConfBiz();
            //トークン認証が使用不可の場合はエラーXML
            if (!confBiz.isAbleTokenAuth(req, con)) {
                writeLoginConfErrorResponse(map, req, res,
                        new ActionMessage("error.cant.use.token"),
                        "error.cant.use.token");
                return null;
            }

            log__.debug("TOKEN認証 START");
            //Authorizationヘッダがあるかどうか判定する
            if (!BasicAuthorizationUtil.canGetAuthorization(req)) {
                //ない場合
                setAuthErrroHeder(req, res);
                return null;
            }
            RequestModel reqMdl = getRequestModel(req);
            ApiTokenBiz tokenBiz = new ApiTokenBiz(con, reqMdl);

            String token = tokenBiz.readAuthrizationToken(req);

            OnetimePasswordBiz otpBiz = new OnetimePasswordBiz();
            LoginResultModel result = otpBiz.checkTokenLive(token, con);
            @SuppressWarnings("rawtypes")
            Iterator it = null;
            if (result.getErrCode() != LoginResultModel.ECODE_NONE) {
                it = result.getErrors().get();
                if (it.hasNext()) {
                    ActionMessage error = (ActionMessage) it.next();
                    writeLoginConfErrorResponse(map, req, res,
                            error,
                            error.getKey());
                    return null;
                }
            }

            log__.debug("TOKEN認証 END");
            ApiCmnOtploginForm thisForm = (ApiCmnOtploginForm) form;

            //入力チェック
            ActionErrors errors = new ActionErrors();
            errors = thisForm.validateLogin(map, getRequestModel(req));
            if (errors.size() > 0) {
                it = errors.get();
                if (it.hasNext()) {
                    ActionMessage error = (ActionMessage) it.next();
                    writeLoginConfErrorResponse(map, req, res,
                            error,
                            error.getKey());
                    return null;
                }
            }
            //ログイン処理を行う
            result = otpBiz.otpAuth(
                    token, thisForm.getOtPassword(), con);
            //ログイン処理に失敗 かつ ActionErrorsが設定されている場合
            if (!result.isResult() && result.getErrors() != null) {
                it = result.getErrors().get();
                if (it.hasNext()) {
                    ActionMessage error = (ActionMessage) it.next();
                    writeLoginConfErrorResponse(map, req, res,
                            error,
                            error.getKey());
                    return null;
                }
            }
            BaseUserModel smodel = result.getLoginUser();
            //認証後 個体識別番号制御によるファイアウォールチェック
            FirewallBiz firewall = FirewallBiz.getInstance();
            if (!firewall.additionalCheckForMbl(smodel, con, true)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
                return null;
            }

            if (!smodel.getLgid().equals(thisForm.getUserid())) {
                writeLoginConfErrorResponse(map, req, res,
                        new ActionMessage("error.notfound.otptoken"),
                        "error.notfound.otptoken");
                return null;
            }
            PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, smodel.getUsrsid());
            String pluginName
            = getPluginConfig(req).getPlugin(getPluginId()).getName(getRequestModel(req));

            //使用不可のプラグインへアクセスした場合
            if (!canAccessPlugin(pconfig.getPluginIdList())) {
                writeLoginConfErrorResponse(map, req, res,
                        new ActionMessage("error.cant.use.plugin", pluginName),
                        "error.cant.use.plugin");
                return null;
            }

            //ワンタイムパスワードを破棄
            CmnOtpTokenDao tokenDao = new CmnOtpTokenDao(con);
            tokenDao.delete(token);

            req.setAttribute(GSConstApi.ATTRKEY_REQUSER, smodel);
            req.setAttribute(GSConstApi.ATTRKEY_PCONF, pconfig);

            //ログイン共通処理を実行
            GSLoginBiz loginBiz = new GSLoginBiz();
            loginBiz.doLoginApi(req, smodel, con, pconfig);
            IMbhLoginHistoryBiz biz = new MbhClassCreater().createMbhLoginHistoryBiz();
            if (biz != null) {
                //ログイン履歴更新
                biz.writeLoginHistry(req, smodel, con, reqMdl);
            }
            //個体識別番号保管
            UserAgent agent = new UserAgent(req);
            loginBiz.saveUuidIfAutoSave(smodel.getUsrsid(), agent.getCuid(), con,
                    reqMdl.getDomain());

            //XML情報の取得
            Document doc = null;
            //トークンを生成し返す


            doc = createXml(form, req, res, con, smodel);
            writeResponse(map, req, res, doc);
        } catch (SocketException e) {
            //クライアントユーザがキャンセルした場合の例外
        } catch (IOException e) {
            Class<? extends IOException> ecs = e.getClass();
            String ename = ecs.getName();
            if ("org.apache.catalina.connector.ClientAbortException".equals(ename)) {
                //クライアントユーザがキャンセルした場合の例外(コンテナTomcat)
                //何もしない
            } else {
                throw e;
            }
        }
        return null;
    }

    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        RequestModel reqMdl = getRequestModel(req);
        IApiConfBiz confBiz = new ApiConfBiz();
        ApiTokenBiz tokenBiz = new ApiTokenBiz(con, reqMdl);
        ApiTokenModel tokenMdl = tokenBiz.saveToken(req, umodel, confBiz.getConf(con));

        //ルートエレメントResultSet
        Element result = new Element("Result");
        Document doc = new Document(result);

        Element token = new Element("Token");
        token.addContent(tokenMdl.getAptToken());
        result.addContent(token);

        log__.debug("createXml end");
        return doc;
    }

}
