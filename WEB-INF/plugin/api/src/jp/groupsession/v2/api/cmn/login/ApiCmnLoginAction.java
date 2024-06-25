package jp.groupsession.v2.api.cmn.login;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import jp.co.sjts.util.http.BasicAuthorizationUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.biz.ApiConfBiz;
import jp.groupsession.v2.api.biz.ApiTokenBiz;
import jp.groupsession.v2.api.model.ApiTokenModel;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.login.LoginModel;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.login.biz.GSLoginBiz;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.login.otp.OtpSendResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.plugin.api.biz.IApiConfBiz;
import net.sf.json.JSON;
import net.sf.json.JSONException;
import net.sf.json.xml.XMLSerializer;
/**
 *
 * <br>[機  能]一次認証API アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCmnLoginAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiCmnLoginAction.class);
    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        try {
            //旧セッション破棄
            removeSession(req);

            ApiConfBiz confBiz = new ApiConfBiz();
            //トークン認証が使用不可の場合はエラーXML
            if (!confBiz.isAbleTokenAuth(req, con)) {
                writeLoginConfErrorResponse(map, req, res,
                        new ActionMessage("error.cant.use.token"),
                        "error.cant.use.token");
                return null;
            }

            log__.debug("BASIC認証 START");
            //BASIC認証によるアクセス判定
            //Authorizationヘッダがあるかどうか判定する
            if (!BasicAuthorizationUtil.canGetAuthorization(req)) {
                //ない場合
                setAuthErrroHeder(req, res);
                return null;
            }
            ILogin loginBiz = _getLoginInstance();
            LoginModel loginModel = __createLoginModel(map, req, con);

            LoginResultModel resultModel = loginBiz.idpassAuthApi(loginModel);

            req.setAttribute(GSConstApi.ATTRKEY_LOGINRESULT, resultModel);

            log__.debug("BASIC認証 END");

            //ログインエラー （ログイン停止、ID＆パスワードエラー）
            if (resultModel.getLoginUser() == null) {
                setAuthErrroHeder(req, res);
                writeApiInfoLog(req, "ログイン停止、ID＆パスワードエラー");
                return null;
            }

            //ログインユーザの所属グループが未設定だった場合、ログイン不可
            if (resultModel.getErrCode() == LoginResultModel.ECODE_BELONGGRP_NONE) {
                writeApiInfoLog(req, "所属グループが未設定");
                writeLoginConfErrorResponse(
                    map, req, res,
                    new ActionMessage("error.auth.notset.belonggroup.api"),
                    "error.auth.notset.belonggroup.api");
                return null;
            }

            //ログインユーザのデフォルトグループが未設定だった場合、ログイン不可
            if (resultModel.getErrCode() == LoginResultModel.ECODE_DEFGRP_NONE) {
                writeApiInfoLog(req, "デフォルトグループが未設定");
                writeLoginConfErrorResponse(
                    map, req, res,
                    new ActionMessage("error.auth.notset.defgroup.api"),
                    "error.auth.notset.defgroup.api");
                return null;
            }

            //認証後 個体識別番号制御によるファイアウォールチェック
            FirewallBiz firewall = FirewallBiz.getInstance();
            if (!firewall.additionalCheckForMbl(resultModel.getLoginUser(), con, true)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
                return null;
            }

            //別システムによるSSO連携が求められた場合
            if (resultModel.getErrCode() == LoginResultModel.ECODE_NEED_SSO) {
                writeApiInfoLog(req, "SSO連携が必要");
                return __createNeedSSOXml(map, resultModel, req, res);
            }

            //ワンタイムパスワード入力が求められた場合
            if (resultModel.getErrCode() == LoginResultModel.ECODE_NEED_OTP) {
                writeApiInfoLog(req, "ワンタイムパスワード認証が必要");
                return __createOtpTokenXml(map, resultModel, req, res, con);
            }

            BaseUserModel smodel = resultModel.getLoginUser();

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
            req.setAttribute(GSConstApi.ATTRKEY_REQUSER, smodel);
            req.setAttribute(GSConstApi.ATTRKEY_PCONF, pconfig);

            GSLoginBiz cmnLoginBiz = new GSLoginBiz();
            //ログイン共通処理
            cmnLoginBiz.doLoginApi(req, smodel, con, pconfig);

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
    /**
     * <br>[機  能]SSOログイン時に使用する情報をかえす
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param resultModel ログイン結果
     * @param req リクエスト
     * @param res レスポンス
     * @return null
     * @throws IOException 実行時例外
     */
    private ActionForward __createNeedSSOXml(ActionMapping map,
            LoginResultModel resultModel, HttpServletRequest req,
            HttpServletResponse res) throws IOException {
        Document doc = null;
        Element result = new Element("Result");


        Element token = new Element("Token");
        token.addContent("");
        result.addContent(token);
        Element typeElm = new Element("Type");
        typeElm.addContent(Integer.toString(GSConstApi.TOKEN_TYPE_AUTH));
        result.addContent(typeElm);
        Element logintype = new Element("LoginType");
        logintype.addContent(resultModel.getLoginType());
        result.addContent(logintype);

        doc = new Document(result);
        // Result
        writeResponse(map, req, res, doc);
        return null;

    }
    /**
     * <br>[機  能]ワンタイムパスワードログイン時に使用する情報をかえす
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param resultModel ログイン結果
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return null
     * @throws IOException 実行時例外
     * @throws SQLException  実行時例外
     */
    private ActionForward __createOtpTokenXml(ActionMapping map,
            LoginResultModel resultModel, HttpServletRequest req,
            HttpServletResponse res, Connection con) throws IOException, SQLException  {
        log__.debug("createXml start");
        try {
            OnetimePasswordBiz onetimePasswordBiz = new OnetimePasswordBiz();
            OtpSendResult otpResult = onetimePasswordBiz.sendOtp(resultModel.getLoginUser(),
                    getRequestModel(req), con);



            switch (otpResult.getEcode()) {
                case OtpSendResult.ECODE_NONE:
                    //ルートエレメントResultSet
                    Element result = new Element("Result");
                    Document doc = new Document(result);

                    Element type = new Element("Type");
                    type.addContent(Integer.toString(GSConstApi.TOKEN_TYPE_OTP));
                    result.addContent(type);

                    Element token = new Element("Token");
                    token.addContent(otpResult.getOtpToken());
                    result.addContent(token);

                    Element from = new Element("SendMailFrom");
                    from.addContent(otpResult.getOtpSendMailFrom());
                    result.addContent(from);

                    Element to = new Element("SendMailTo");
                    to.addContent(otpResult.getOtpSendMailTo());
                    result.addContent(to);
                    Element subject = new Element("SendMailSubject");
                    subject.addContent(otpResult.getOtpSendMailSubject());
                    result.addContent(subject);

                    Element date = new Element("SendMailDate");
                    date.addContent(otpResult.getOtpSendDateStr());
                    result.addContent(date);
                    LoginResultModel loginResult =
                            (LoginResultModel) req.getAttribute(GSConstApi.ATTRKEY_LOGINRESULT);
                    Element logintype = new Element("LoginType");
                    logintype.addContent(loginResult.getLoginType());
                    result.addContent(logintype);

                    writeResponse(map, req, res, doc);
                    return null;
                case OtpSendResult.ECODE_SENDERROR:
                    writeLoginConfErrorResponse(map, req, res,
                            new ActionMessage("error.smtp.otppass.send"),
                            "error.smtp.otppass.send");
                    return null;
                case OtpSendResult.ECODE_NOADDRES_CANTEDIT:
                case OtpSendResult.ECODE_NOADDRES_ABLEEDIT:
                    writeLoginConfErrorResponse(map, req, res,
                            new ActionMessage("error.need.address.otpass.send"),
                            "error.brank.address.otpass.send");
                    return null;
                default:
                    writeLoginConfErrorResponse(map, req, res,
                            new ActionMessage("error.need.address.otpass.send"),
                            "error.brank.address.otpass.send");
                    return null;
            }

        } finally {
            log__.debug("createXml end");
        }
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

        Element type = new Element("Type");
        type.addContent(Integer.toString(GSConstApi.TOKEN_TYPE_AUTH));
        result.addContent(type);

        Element token = new Element("Token");
        token.addContent(tokenMdl.getAptToken());
        result.addContent(token);

        LoginResultModel loginResult =
                (LoginResultModel) req.getAttribute(GSConstApi.ATTRKEY_LOGINRESULT);
        Element logintype = new Element("LoginType");
        logintype.addContent(loginResult.getLoginType());
        result.addContent(logintype);


        log__.debug("createXml end");
        return doc;
    }
    /**
     * <br>[機  能] 認証設定エラーのレスポンスを出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param res レスポンス
     * @param msg エラーメッセージ
     * @param ecode エラーコード
     * @exception IOException IOエラー
     */
    public void writeLoginConfErrorResponse(ActionMapping map, HttpServletRequest req,
        HttpServletResponse res, ActionMessage msg,
        String ecode) throws IOException {

        //XML出力基本情報
        OutputStream out = res.getOutputStream();
        res.setContentType("text/xml" + "; charset=" + "UTF-8");

        //XMLフォーマット
        XMLOutputter xout = new XMLOutputter();
        //フォーマットを設定する。
        Format format = xout.getFormat();
        format.setEncoding("UTF-8");
        format.setLineSeparator("\r\n");
        format.setIndent("    ");
        format.setOmitDeclaration(false);
        format.setOmitEncoding(false);
        format.setExpandEmptyElements(true);

        //エラー内容の出力
        //ルートエレメントResultSet
        Element oerrors = new Element("Errors");
        Document doc = new Document(oerrors);

        //URL情報をルートエレメントの属性としてセット
        String url = __getApiUrl(map, req);
        Attribute atUrl = new Attribute("url", url);
        oerrors.setAttribute(atUrl);
        Element oEcode = new Element("Ecode");
        oEcode.addContent(ecode);
        oerrors.addContent(oEcode);

        MessageResources mres = getResources(req);
        Element oMessage = new Element("Message");
        oMessage.addContent(mres.getMessage(msg.getKey(), msg.getValues()));
        oerrors.addContent(oMessage);

        String type = "xml";

        if (req.getParameter("type") != null
                && req.getParameter("type").equals("1")) {
            type = "json";
        }


        if (type.equals("json")) {
            String strXml = xout.outputString(doc);
            String strJSON = "";
            try {
            //net.sf.json.xml.XMLSerializer#readで読み込み、JSONオブジェクトを作成
                XMLSerializer xmlSerializer = new XMLSerializer();
                JSON json = xmlSerializer.read(strXml);

                strJSON = json.toString();
            } catch (JSONException je) {
                strJSON = "failed toJSON from xml : " + strXml;
            }
            //レスポンスを返す
            res.setContentType("text/html" + "; charset=" + "UTF-8");
            OutputStreamWriter outWriter = new OutputStreamWriter(out, "UTF-8");
            outWriter.write(strJSON);
            outWriter.close();
        } else {
            xout.output(doc, out);
        }
    }
    /**
     * <br>[機  能] APIのURLを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @return APIのURL
     */
    private String __getApiUrl(ActionMapping map, HttpServletRequest req) {
        StringBuilder buf = new StringBuilder();
        String cpath = req.getContextPath();
        if (cpath != null) {
            buf.append(cpath);
        }

        String mapPath = map.getPath();
        if (mapPath != null) {
            buf.append(mapPath);
        }
        if (buf.length() > 0) {
            buf.append(".do");
        }
        log__.debug("web api url = " + buf.toString());
        return buf.toString();
    }
    /**
     * <p>ログイン処理時に使用する情報を設定する
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     * @return アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    private LoginModel __createLoginModel(ActionMapping map,
                                                            HttpServletRequest req,
                                                            Connection con)
    throws SQLException {
        //ログイン処理に必要な情報を設定する
        LoginModel loginModel = new LoginModel();
        String loginId = BasicAuthorizationUtil.getUserID(req);
        String password = BasicAuthorizationUtil.getPassword(req);
        loginModel.setLoginId(loginId);
        loginModel.setPassword(password);
        loginModel.setCon(con);
        loginModel.setReq(req);
        loginModel.setMap(map);
        //使用可能プラグイン情報を取得
        PluginConfig pconf = getPluginConfigForMain(getPluginConfig(req), con);
        loginModel.setPluginConfig(pconf);

        return loginModel;
    }
    @Override
    public boolean isWriteApiLog() {
        return true;
    }
}
