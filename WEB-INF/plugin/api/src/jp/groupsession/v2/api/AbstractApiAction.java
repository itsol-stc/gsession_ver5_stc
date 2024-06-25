package jp.groupsession.v2.api;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.http.BasicAuthorizationUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.biz.ApiConfBiz;
import jp.groupsession.v2.api.biz.ApiTokenBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.exception.DBBackupGuardException;
import jp.groupsession.v2.cmn.exception.GSAttachFileNotExistException;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.login.LoginModel;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import net.sf.json.JSON;
import net.sf.json.JSONException;
import net.sf.json.xml.XMLSerializer;

/**
 * <br>[機  能] WEB API共通アクション
 * <br>[解  説] セッションは確立していなくてもOKだが、BASIC認証による認証が必要。
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractApiAction extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AbstractApiAction.class);

    /** プラグインID */
    private static final String PLUGIN_ID = "api";

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }

    /**
     * <br>[機  能] セッション管理後の動作を記述する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    @SuppressWarnings("all")
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        try {
            removeSession(req);
//            req.getSession(true);

            //認証方法判定
            //Authorizationヘッダがあるかどうか判定する
            if (!BasicAuthorizationUtil.canGetAuthorization(req)) {
                //ない場合
                setAuthErrroHeder(req, res);
                return null;
            }

            String auth = req.getHeader("Authorization");
            ILogin login = _getLoginInstance();
            LoginResultModel result;
            //BASIC認証
            if (auth.startsWith("Basic")) {
                writeApiInfoLog(req, "BASIC認証 START");
                ApiConfBiz confBiz = new ApiConfBiz();
                //ベーシック認証が使用不可の場合はエラーXML
                if (!confBiz.isAbleBasicAuth(req, con)) {
                    __writeLoginConfErrorResponse(map, req, res,
                            new ActionMessage("error.cant.use.basic"),
                            "error.cant.use.basic");
                    return null;
                }
                result = __authBasic(map, req, con);
                writeApiInfoLog(req, "BASIC認証 END");
            //トークン認証
            } else if (auth.startsWith("Bearer")) {
                writeApiInfoLog(req, "トークン認証 START");
                ApiConfBiz confBiz = new ApiConfBiz();
                //トークン認証が使用不可の場合はエラーXML
                if (!confBiz.isAbleTokenAuth(req, con)) {
                    __writeLoginConfErrorResponse(map, req, res,
                            new ActionMessage("error.cant.use.token"),
                            "error.cant.use.token");
                    return null;
                }

                result = __authToken(map, req, con);

                writeApiInfoLog(req, "トークン認証 END");
            } else {
                setAuthErrroHeder(req, res);
                return null;
            }

            //認証後 個体識別番号制御によるファイアウォールチェック
            FirewallBiz firewall = FirewallBiz.getInstance();
            if (!firewall.additionalCheckForMbl(result.getLoginUser(), con, false)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
                return null;
            }
            if (result.getErrCode() != LoginResultModel.ECODE_NONE) {
                writeLoginErrorResponce(map, req, res, result);
                return null;
            }

            BaseUserModel umodel = result.getLoginUser();
            if (umodel == null) {
                //該当ユーザがいない場合
                writeApiInfoLog(req, "該当ユーザがいない");
                setAuthErrroHeder(req, res);
                return null;
            }
            writeApiInfoLog(req, "認証 成功");

            req.setAttribute(GSConstApi.ATTRKEY_REQUSER, umodel);
            PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
            req.setAttribute(GSConstApi.ATTRKEY_PCONF, pconfig);

            ActionErrors errors = null;
            //使用不可のプラグインへアクセスした場合
            if (!canAccessPlugin(pconfig.getPluginIdList())) {
                errors = addCantAccsessPluginError(req, null, getPluginId());
                addErrors(req, errors);
                ActionMessages errMessages = getErrors(req);
                writeErrorResponse(map, req, res, errMessages);
                return null;
            }

            errors = checkCantAccsessRelationPluginError(req, errors);
            if (errors != null && !errors.isEmpty()) {
                addErrors(req, errors);
                ActionMessages errMessages = getErrors(req);
                writeErrorResponse(map, req, res, errMessages);
                return null;
            }

            if (this instanceof IUseTempdirApi) {
                req.getSession(true);
            }
            Document doc = null;
            //XML情報の取得
            doc = createXml(form, req, res, con, umodel);

            ActionMessages errMessages = getErrors(req);
            if (errMessages != null && !errMessages.isEmpty()) {
                //エラーXML出力
                writeErrorResponse(map, req, res, errMessages);
            } else {
                //XML出力
                writeResponse(map, req, res, doc);
            }
        } catch (SocketException e) {
            //クライアントユーザがキャンセルした場合の例外
        } catch (IOException e) {
            Class ecs = e.getClass();
            String ename = ecs.getName();
            if ("org.apache.catalina.connector.ClientAbortException".equals(ename)) {
                //クライアントユーザがキャンセルした場合の例外(コンテナTomcat)
                //何もしない
            } else {
                throw e;
            }
        } catch (DBBackupGuardException e) {
            //バックアップ中にアクセスした場合、エラーメッセージを返す
            ActionMessage msg = new ActionMessage("error.backing.up");
            ActionErrors errors = new ActionErrors();
            StrutsUtil.addMessage(errors, msg, "error.backing.up");
            ActionMessages errMessages = getErrors(req);
            errMessages.add(errors);
            writeErrorResponse(map, req, res, errMessages);
        } catch (Exception e) {
            Throwable cause = GSAttachFileNotExistException.searchCause(e);
            if (cause != null) {
                log__.error("", cause);
                ActionMessage msg = new ActionMessage("error.save.attach.filenotexist.api");
                ActionErrors errors = new ActionErrors();
                StrutsUtil.addMessage(errors, msg, "error.save.attach.filenotexist");

                ActionMessages errMessages = getErrors(req);
                errMessages.add(errors);
                writeErrorResponse(map, req, res, errMessages);
            } else {
                throw e;
            }

        } finally {
            //テンポラリディレクトリを使用するAPIはテンポラリディレクトリ用に
            //作ったsessionを破棄する
            if (this instanceof IUseTempdirApi) {
                RequestModel reqMdl = getRequestModel(req);
                String tempDir = GroupSession.getResourceManager().getTempPath(reqMdl.getDomain())
                        + File.separator + reqMdl.getSession().getId();
                IOTools.deleteDir(tempDir);
                removeSession(req);
            }
        }
        return null;
    }

    /**
     * <br>[機  能] 特定例外エラー画面遷移設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map リクエストマップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return 遷移先エラー画面
     * @throws Exception 実行時例外
     */
    @Override
    public ActionForward forwardBackupProgressPage(ActionMapping map,
            ActionForm form, HttpServletRequest req, HttpServletResponse res)
    throws Exception {

        ActionMessage msg = new ActionMessage("error.backing.up");
        ActionErrors errors = new ActionErrors();
        StrutsUtil.addMessage(errors, msg, "error.backing.up");

        ActionMessages errMessages = getErrors(req);
        errMessages.add(errors);
        writeErrorResponse(map, req, res, errMessages);

        return null;
    }

    /**
     *
     * <br>[機  能] 認証エラー時のレスポンス生成
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param result 認証結果
     * @throws IOException XML出力に失敗
     */
    public void writeLoginErrorResponce(ActionMapping map,
            HttpServletRequest req, HttpServletResponse res,
            LoginResultModel result) throws IOException {
        switch (result.getErrCode()) {
            case LoginResultModel.ECODE_TOKENNONE:
                __writeLoginConfErrorResponse(map, req, res,
                        new ActionMessage("error.notfound.token"),
                        "error.notfound.token");
                return;

            case LoginResultModel.ECODE_TOKENTIMEOVER:
                __writeLoginConfErrorResponse(map, req, res,
                        new ActionMessage("error.timeover.token"),
                        "error.timeover.token");
                return;

            case LoginResultModel.ECODE_BELONGGRP_NONE:
                __writeLoginConfErrorResponse(map, req, res,
                        new ActionMessage("error.auth.notset.belonggroup.api"),
                        "error.auth.notset.belonggroup.api");
                return;

            case LoginResultModel.ECODE_DEFGRP_NONE:
                __writeLoginConfErrorResponse(map, req, res,
                        new ActionMessage("error.auth.notset.defgroup.api"),
                        "error.auth.notset.defgroup.api");
                return;
            default:
        }
        writeApiInfoLog(req, "login_ecode=" + result.getErrCode());
        //ログインエラー
        setAuthErrroHeder(req, res);

    }

    /**
     *
     * <br>[機  能] Token認証を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @param con コネクション
     * @return 認証結果
     * @throws SQLException 実行時例外
     */
    private LoginResultModel __authToken(ActionMapping map,
            HttpServletRequest req, Connection con) throws SQLException {

        ApiTokenBiz tokenBiz = new ApiTokenBiz(con, getRequestModel(req));
        String token = tokenBiz.readAuthrizationToken(req);
        LoginResultModel ret = tokenBiz.authToken(token, con);

        return ret;
    }

    /**
     * <br>[機  能] Basic認証のエラーヘッダーをレスポンスにセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @throws IOException IOエラー
     */
    public void setAuthErrroHeder(HttpServletRequest req,
            HttpServletResponse res) throws IOException {

        writeApiInfoLog(req, "認証エラー");
        String cpath = req.getContextPath();
        res.setHeader("WWW-Authenticate", "Basic realm=\"" + cpath + "\"");
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
    /**
     * <br>[機  能] AuthorizationヘッダからID・パスワードを取得し一致するユーザ情報を返す。一致する情報が存在しない場合はnullを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @param con DBコネクション
     * @throws Exception SQL例外等
     * @return ユーザ情報。認証に失敗した場合はnullを返す。
     */
    public LoginResultModel getLoginResultModel(
            ActionMapping map, HttpServletRequest req, Connection con) throws Exception {
        return __authBasic(map, req, con);
    }

    /**
     * <br>[機  能] BASIC認証を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @param con DBコネクション
     * @throws Exception SQL例外等
     * @return ユーザ情報。認証に失敗した場合はnullを返す。
     */
    private LoginResultModel __authBasic(
            ActionMapping map, HttpServletRequest req, Connection con) throws Exception {

        String loginId = null;
        LoginResultModel resultModel = null;
        try {
            loginId = BasicAuthorizationUtil.getUserID(req);
            String password = BasicAuthorizationUtil.getPassword(req);

            //ログイン処理に必要な情報を設定する
            LoginModel loginModel = new LoginModel();
            loginModel.setLoginId(loginId);
            loginModel.setPassword(password);
            loginModel.setCon(con);
            loginModel.setReq(req);
            loginModel.setMap(map);
            //使用可能プラグイン情報を取得
            PluginConfig pconf = getPluginConfigForMain(getPluginConfig(req), con);
            loginModel.setPluginConfig(pconf);
            //ログイン処理を行う
            ILogin loginBiz = _getLoginInstance();
            resultModel = loginBiz.idpassAuthApi(loginModel);

            log__.debug("loginId = " + loginId);
            log__.debug("password = " + password);
        } catch (SQLException e) {
            log__.error("SQL実行エラー:ログイン処理の実行に失敗", e);
            throw e;
        }

        return resultModel;
    }


    /**
     * <br>[機  能] Xmlのレスポンスを出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param res レスポンス
     * @param doc jdomドキュメント
     * @exception IOException IOエラー
     */
    public void writeResponse(ActionMapping map, HttpServletRequest req,
        HttpServletResponse res, Document doc) throws IOException {

        if (doc == null) {
            return;
        }

        //
        //URL情報をルートエレメントの属性としてセット
        String url = __getApiUrl(map, req);
        Element el = doc.getRootElement();
        Attribute at = new Attribute("url", url);
        el.setAttribute(at);

        //XML出力基本情報
        OutputStream out = res.getOutputStream();
        res.setContentType("text/xml" + "; charset=" + "UTF-8");

        //
        XMLOutputter xout = new XMLOutputter();
        //フォーマットを設定する。
        Format format = xout.getFormat();
        format.setEncoding("UTF-8");
        format.setLineSeparator("\r\n");
        format.setIndent("    ");
        format.setOmitDeclaration(false);
        format.setOmitEncoding(false);
        format.setExpandEmptyElements(true);

        String type = "xml";

        if (req.getParameter("type") != null && req.getParameter("type").equals("1")) {
            type = "json";
        }


        if (type.equals("json")) {
            //jsonの場合
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
     * <p>セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }

    /** プラグインが使用可能か判定します
     * @param pluginId 判定するプラグインID
     * @param req リクエスト
     * @return boolean true:使用可能 false:使用不可
     */
    public boolean canAccsessSelectPlugin(String pluginId, HttpServletRequest req) {
        if (pluginId == null) {
            return true;
        }
        PluginConfig pconfig = (PluginConfig) req.getAttribute(GSConstApi.ATTRKEY_PCONF);
        if (pconfig != null && pconfig.getPlugin(pluginId) != null) {
            return true;
        }
        return false;
    }
    /** プラグインが使用不可能な場合のエラーメッセージを返します
     * @param req リクエスト
     * @param errors ActionErrors
     * @param pluginId 判定するプラグインID
     * @return ActionErrors エラーメッセージを追加したActionErrors
     */
    public final ActionErrors addCantAccsessPluginError(HttpServletRequest req,
            ActionErrors errors,
            String pluginId) {
        if (errors == null) {
            errors = new ActionErrors();
        }
        String pluginName
        = getPluginConfig(req).getPlugin(pluginId).getName(getRequestModel(req));
        ActionMessage msg = new ActionMessage("error.cant.use.plugin", pluginName);
        StrutsUtil.addMessage(errors, msg, "error.cant.use.plugin");
        return errors;
    }
    /** 関連プラグインが使用不可能な場合のエラーメッセージを設定します
     * 継承して利用します
     * @param req リクエスト
     * @param errors ActionErrors
     * @return ActionErrors エラーメッセージを追加したActionErrors
     */
    public ActionErrors checkCantAccsessRelationPluginError(
            HttpServletRequest req,
            ActionErrors errors) {
        if (errors == null) {
            errors = new ActionErrors();
        }
        return errors;
    }

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
    public abstract Document createXml(
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        BaseUserModel umodel
        )
        throws Exception;

    /**
     * <br>[機  能] Refererのチェックは行わない。
     * <br>[解  説] AppResourceのサーバ名称と比較します。
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @return 判定結果 true:正常 false:不正
     */
    public boolean checkReferer(ActionMapping map, HttpServletRequest req) {
        return true;
    }

    /**
     * <br>[機  能] Xmlのレスポンスを出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param res レスポンス
     * @param errors エラーメッセージ
     * @exception IOException IOエラー
     */
    @SuppressWarnings("all")
    public void writeErrorResponse(ActionMapping map, HttpServletRequest req,
        HttpServletResponse res, ActionMessages errors) throws IOException {

        List<String> errMsgList = new ArrayList<String>();
        MessageResources mres = getResources(req);

        Iterator it = errors.get();
        while (it.hasNext()) {
            ActionMessage error = (ActionMessage) it.next();
            errMsgList.add(mres.getMessage(error.getKey(), error.getValues()));

        }

        writeErrorResponse(map, req, res, errMsgList);
    }

    /**
     * <br>[機  能] Xmlのレスポンスを出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param res レスポンス
     * @param errorMsgList エラーメッセージ一覧
     * @exception IOException IOエラー
     */
    public void writeErrorResponse(ActionMapping map, HttpServletRequest req,
        HttpServletResponse res, List<String> errorMsgList) throws IOException {
        //400 Bad Request
//        String cpath = req.getContextPath();
//        res.sendError(HttpServletResponse.SC_BAD_REQUEST);

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

        for (String errMsg : errorMsgList) {
            writeApiInfoLog(req, errMsg);
            Element oMessage = new Element("Message");
            oMessage.addContent(errMsg);
            oerrors.addContent(oMessage);
        }

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
     * [機  能] executeメソッド実行時に例外(Exception)が発生した場合の処理<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param e executeメソッド実行時に発生した例外(Exception)
     * @return true:Exceptionをthrowする false:Exceptionをthrowしない
     */
    protected boolean _doExcecuteException(ActionMapping map,
                                            ActionForm form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con,
                                            Exception e) {

        try {
            List<String> errMsgList = new ArrayList<String>();
            errMsgList.add(getInterMessage(req, "cmn.unpredictable.exception"));

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            errMsgList.add(sw.toString());

            writeErrorResponse(map, req, res, errMsgList);
        } catch (IOException ie) {
            log__.error("例外XMLの作成に失敗", e);
        }

        return false;
    }

    /**
     * [機  能] executeメソッド実行時に例外(Throwable)が発生した場合の処理<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param e executeメソッド実行時に発生した例外 or エラー(Throwable)
     * @return true:Exceptionをthrowする false:Exceptionをthrowしない
     */
    protected boolean _doExcecuteThrowable(ActionMapping map,
                                            ActionForm form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con,
                                            Throwable e) {

        try {
            List<String> errMsgList = new ArrayList<String>();
            errMsgList.add(getInterMessage(req, "cmn.unpredictable.exception"));

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            errMsgList.add(sw.toString());

            writeErrorResponse(map, req, res, errMsgList);
        } catch (IOException ie) {
            log__.error("例外XMLの作成に失敗", e);
        }

        return false;
    }
    /**
     * <br>[機  能] org.jdom2.Elementインスタンスを生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param name 名称
     * @param content 内容
     * @return org.jdom2.Elementインスタンス
     */
    protected Element _createElement(String name, int content) {
        Element element = new Element(name);
        element.addContent(String.valueOf(content));
        return element;
    }

    /**
     * <br>[機  能] org.jdom2.Elementインスタンスを生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param name 名称
     * @param content 内容
     * @return org.jdom2.Elementインスタンス
     */
    protected Element _createElement(String name, Long content) {
        Element element = new Element(name);
        element.addContent(String.valueOf(content));
        return element;
    }

    /**
     * <br>[機  能] org.jdom2.Elementインスタンスを生成します。
     * <br>[解  説]
     * <br>[備  考] content内のXML利用不可文字は削除されます。
     * @param name 名称
     * @param content 内容
     * @return org.jdom2.Elementインスタンス
     */
    protected Element _createElement(String name, String content) {
        Element element = new Element(name);
        element.addContent(StringUtil.removeInvalidCharacterForXml(content));
        return element;
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
    private void __writeLoginConfErrorResponse(ActionMapping map, HttpServletRequest req,
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
        String smes = mres.getMessage(msg.getKey(), msg.getValues());

        log__.info(__getLogHead(req) + smes);

        oMessage.addContent(smes);

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
    @Override
    public boolean isSession(HttpServletRequest req) {
        return false;
    }
    @Override
    protected final boolean _isAccessPlugin(HttpServletRequest req, ActionForm form,
            Connection con) throws SQLException {
        return true;
    }
    @Override
    public final BaseUserModel getSessionUserModel(HttpServletRequest req) {
        Object tmp = req.getAttribute(GSConstApi.ATTRKEY_REQUSER);
        if (tmp == null) {
            return null;
        }
        BaseUserModel smodel = (BaseUserModel) tmp;
        return smodel;
    }
    @Override
    public int getSessionUserSid(HttpServletRequest req) {
        BaseUserModel smodel = getSessionUserModel(req);
        if (smodel == null) {
            return -1;
        }
        return smodel.getUsrsid();
    }
    @Override
    public RequestModel getRequestModel(HttpServletRequest req) {
        RequestModel reqMdl = super.getRequestModel(req);
        //APIではSessionは使用しない
//        reqMdl.setSession(null);
        reqMdl.setSmodel(getSessionUserModel(req));
        return reqMdl;
    }

    @Override
    public void writeStartLog(HttpServletRequest req) {
        if (!isWriteApiLog()) {
            return;
        }
        super.writeStartLog(req);

        String logHead = __getLogHead(req);


        log__.info(logHead + "--------- START -------------");
        String ip = null;
        //IPアドレス
        try {
            ip = CommonBiz.getRemoteAddr(req);
        } catch (RuntimeException e) {
        }

        log__.info(logHead + "ipadress = " + ip);
        BaseUserModel smodel = null;

        String cookie = req.getHeader("Cookie");
        log__.info(logHead + "Cookie = " + cookie);

        HttpSession session = req.getSession(false);
        if (session != null) {
            Object tmp = session.getAttribute(GSConst.SESSION_KEY);
            if (tmp != null) {
                smodel = (BaseUserModel) tmp;
            }
        }
        if (smodel != null) {
            log__.info(logHead + "start sessionUserSid = " + smodel.getUsrsid());
            log__.info(logHead + "start sessionLoginId = " + smodel.getLgid());
            log__.info(logHead + "start sessionDomain = " + smodel.getDomain());

        } else {
            log__.info(logHead + "start session = null");

        }
        String auth = req.getHeader("Authorization");

        log__.info(logHead + "auth = " + auth);


    }
    /**
     *
     * <br>[機  能] APIログ固定 冒頭部作成
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return PIログ固定 冒頭部
     */
    private String __getLogHead(HttpServletRequest req) {
        String domain = null;
        try {
            domain = GroupSession.getResourceManager().getDomainCheck(req);
        } catch (Exception e1) {
        }
        long threadId = Thread.currentThread().getId();
        String actionPath = (String) req.getAttribute(GSConst.REQ_ATTRKEY_ACTIONPATH);
        String logHead = domain  + actionPath + "(" + threadId + ") : ";
        return logHead;
    }

    /**
    *
    * <br>[機  能] APIログ出力フラグ
    * <br>[解  説]
    * <br>[備  考]
    * @return 出力可否
    */
    public boolean isWriteApiLog() {
        return false;
    }
    @Override
    public void writeEndLog(HttpServletRequest req) {
        if (!isWriteApiLog()) {
            return;
        }
        String logHead = __getLogHead(req);

        BaseUserModel smodel = null;
        smodel = super.getSessionUserModel(req);
        if (smodel != null) {
            log__.info(logHead + "finish sessionUserSid = " + smodel.getUsrsid());
            log__.info(logHead + "finish sessionLoginId = " + smodel.getLgid());
            log__.info(logHead + "finish sessionDomain = " + smodel.getDomain());

        } else {
            log__.info(logHead + "finish session = null");

        }

        log__.info(logHead + "--------- END -------------");
        super.writeEndLog(req);
    }
    /**
     *
     * <br>[機  能] API用InfoLogの出力
     * <br>[解  説] API毎の出力対象フラグで制御できる
     * <br>[備  考]
     * @param req リクエスト
     * @param mes 出力メッセージ
     */
    public void writeApiInfoLog(HttpServletRequest req, String mes) {
        if (!isWriteApiLog()) {
            return;
        }
        String logHead = __getLogHead(req);
        log__.info(logHead + mes);
    }

    @Override
    protected void _saveCheckedDomain(HttpServletRequest req) {
        if (req.getSession(false) != null) {
            super._saveCheckedDomain(req);
        }
    }
}