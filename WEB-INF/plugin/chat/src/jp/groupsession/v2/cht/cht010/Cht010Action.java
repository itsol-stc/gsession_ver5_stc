package jp.groupsession.v2.cht.cht010;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cht.AbstractChatAction;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.biz.ChtMemberBiz;
import jp.groupsession.v2.cht.biz.ChtWebSocketBiz;
import jp.groupsession.v2.cht.dao.ChatDao;
import jp.groupsession.v2.cht.dao.ChtCategoryDao;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cht.dao.ChtUserPairDao;
import jp.groupsession.v2.cht.model.ChatInformationModel;
import jp.groupsession.v2.cht.model.ChatMessageModel;
import jp.groupsession.v2.cht.model.ChatMidokuModel;
import jp.groupsession.v2.cht.model.ChatUserInfModel;
import jp.groupsession.v2.cht.model.ChtAdmConfModel;
import jp.groupsession.v2.cht.model.ChtGroupInfModel;
import jp.groupsession.v2.cht.model.ChtPriConfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Form;
import jp.groupsession.v2.cmn.cmn110.Cmn110ParamModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.UserUtil;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 *
 * <br>[機  能] チャット一覧 のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht010Action extends AbstractChatAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht010Action.class);

    /**
     * <br>アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;
        Cht010Form thisForm = (Cht010Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (cmd.equals("sendMessage")) {
            // メッセージ送信
            __doSend(map, thisForm, req, res, con);
        } else if (cmd.equals("moreView")) {
            // もっと見る
            __doMoreView(map, thisForm, req, res, con);
        } else if (cmd.equals("changePartner")) {
            // 選択グループ変更
            __doChangePartner(map, thisForm, req, res, con);
        } else if (cmd.equals("enterSend")) {
            // enter送信変更
            __doEnterSend(map, thisForm, req, res, con);
        } else if (cmd.equals("changeGrp")) {
            // グループコンボ切り替え
            __doChangeGrp(map, thisForm, req, res, con);
        } else if (cmd.equals("favoriteChage")) {
            // お気に入り
            __doFavoriteChange(map, thisForm, req, res, con);
        } else if (cmd.equals("messageDelete")) {
            // メッセージ削除
            __doMessageDelete(map, thisForm, req, res, con);
        } else if (cmd.equals("messageEdit")) {
            // メッセージ編集
            __doMessageEdit(map, thisForm, req, res, con);
        } else if (cmd.equals("tempAdd")) {
            // 添付ファイル送信
            __doTempAdd(map, thisForm, req, res, con);
        } else if (cmd.equals("fileDownload")) {
            // 添付ファイルダウンロード
            forward = __doDownLoad(map, thisForm, req, res, con);
        } else if (cmd.equals("allTmpExp")) {
            // 添付ファイル一括ダウンロード
            forward = __exportAllTmp(map, thisForm, req, res, con);
        } else if (cmd.equals("scrollRead")) {
            // スクロール時読み込み処理
            __doScrollRead(map, thisForm, req, res, con);
        } else if (cmd.equals("updateKidoku")) {
            // 既読アップデート処理
            __doUpdateKidoku(map, thisForm, req, res, con);
        } else if (cmd.equals("groupConfInit")) {
            // グループ管理：表示
            __doGroupConfDsp(map, thisForm, req, res, con);
        } else if (cmd.equals("groupConfSearch")) {
            // グループ管理:検索
            __doGroupConfSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("groupConfAddEditDsp")) {
            // グループ管理：登録編集画面表示
            __doGroupAddEditDsp(map, thisForm, req, res, con);
        } else if (cmd.equals("groupConfAddEditGroup")) {
            // グループ管理：グループ作成編集
            __doAddEditGroup(map, thisForm, req, res, con);
        } else if (cmd.equals("groupConfDeleteGroup")) {
            // グループ管理：グループ削除
            __doDeleteGroup(map, thisForm, req, res, con);
        } else if (cmd.equals("groupConfViewMember")) {
            // グループ管理：グループメンバー表示
            forward = map.getInputForward();
        } else if (cmd.equals("getAdmConf")) {
            // 既読変更処理
            __doGetAdmConf(map, thisForm, req, res, con);
        } else if (cmd.equals("getPriConf")) {
            // 個人設定情報取得
            __doGetPriConf(map, thisForm, req, res, con);
        } else if (cmd.equals("getMidokuCnt")) {
            // 未読件数の取得
            __doGetMidokuCnt(map, thisForm, req, res, con);
        } else if (cmd.equals("admConf")) {
            // 管理者設定
            forward = map.findForward("cht020");
        } else if (cmd.equals("kojinEdit")) {
            // 個人設定
            forward = map.findForward("cht030");
        } else if (cmd.equals("getGroupName")) {
            // グループ名取得
            __doGroupName(map, thisForm, req, res, con);
        } else if (cmd.equals("reload")) {
            // 画面再読み込み
            forward = __doInit(map, thisForm, req, res, con, cmd);
        } else if (cmd.equals("pushDsp")) {
            forward = __doPushDsp(map, thisForm, req, res, con, cmd);
            // 左メニュータブ切り替え
        } else if (cmd.equals("changeTab")) {
            forward = __doChangeTab(map, thisForm, req, res, con, cmd);
        } else {
            // 初期表示
            forward = __doInit(map, thisForm, req, res, con, cmd);
        }
        return forward;
    }

    /**
     * <br>[機  能] 左メニュータブ切り替え
     * <br>[解  説] タブ切り替え時に非同期実行される。切り替えたタブの保管のみ行う
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param cmd コマンド
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doChangeTab(ActionMapping map, Cht010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con,
            String cmd) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionErrors errors = new ActionErrors();
        //2重投稿チェック
        if (!__tokenValidate(req)) {
            log__.info("２重投稿");
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.chat.token.validate",
                    gsMsg.getMessage("cht.cht010.54")), "token");
            addErrors(req, errors);
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_TOKEN_ERROR);
            return null;
        }

        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        biz.changeTab(paramMdl);
        paramMdl.setFormData(form);
        con.commit();
        __doRedrawData(map, form, req, res, con, Cht010Form.MODE_SUCCESS);
        return null;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param cmd コマンド
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cht010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con,
            String cmd)
            throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        String tempDir
            = tempUtil.getTempPath(reqMdl, GSConstChat.PLUGIN_ID_CHAT, GSConstChat.DIRID_CHT010);

        con.setAutoCommit(true);
        BaseUserModel userMdl = getSessionUserModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, userMdl, getPluginId());
        Cht010Biz biz = new Cht010Biz(con, getRequestModel(req));
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, adminUser, cmd);
        paramMdl.setFormData(form);

        //添付drag＆drop時のための処理
        //画面内にcmn110を生成し、添付の処理をcmn110で統一する
        Cmn110Form cmn110Form = new Cmn110Form();
        Cmn110Biz cmn110biz = new Cmn110Biz();
        Cmn110ParamModel cmn110Mdl = new Cmn110ParamModel();
        cmn110Mdl.setCmn110parentListName("cht010files");
        cmn110Mdl.setCmn110pluginId(GSConstChat.PLUGIN_ID_CHAT);
        cmn110Mdl.setCmn110Mode("2");
        cmn110Mdl.setTempDirId(GSConstChat.DIRID_CHT010);
        cmn110biz.setInitData(cmn110Mdl, con, tempDir);
        cmn110Mdl.setFormData(cmn110Form);
        req.setAttribute("cmn110Form", cmn110Form);
        con.setAutoCommit(false);

        tempUtil.deleteTempPath(reqMdl, GSConstChat.PLUGIN_ID_CHAT, GSConstChat.DIRID_CHT010);
        tempUtil.createTempDir(reqMdl, GSConstChat.PLUGIN_ID_CHAT, GSConstChat.DIRID_CHT010);

       //トランザクショントークン設定
       this.saveToken(req);

       //トークンをCookieに保存する
       //※Cookieにパス指定を行うとマルチテナント環境で動作しなくなる為、設定しない。
       Cookie cookie = new Cookie("token", "");
       HttpSession session = req.getSession();
       Object value = session.getAttribute(Globals.TRANSACTION_TOKEN_KEY);
       cookie.setValue(String.valueOf(value));
       cookie.setHttpOnly(true);
       res.addCookie(cookie);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 初期表示(プッシュ通知）
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param cmd コマンド
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doPushDsp(ActionMapping map, Cht010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con,
            String cmd)
            throws Exception {

        __doInit(map, form, req, res, con, cmd);
        RequestModel reqMdl = getRequestModel(req);
        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        biz.updatePriConf(paramMdl);
        paramMdl.setFormData(form);
        con.commit();
        return map.getInputForward();
    }



    /**
     * <br>[機  能] グループ名取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doGroupName(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        biz.getGroupName(paramMdl);
        paramMdl.setFormData(form);
        con.commit();
        __doRedrawData(map, form, req, res, con, Cht010Form.MODE_GET_GROUP);
    }

    /**
     * <br>[機  能] もっと見る押下時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doMoreView(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        biz.moreView(paramMdl);
        paramMdl.setFormData(form);
        con.commit();
        __doRedrawData(map, form, req, res, con, Cht010Form.MODE_MORE);
    }

    /**
     * <br>[機  能] グループコンボ切り替え時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doChangeGrp(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        biz.changeGrp(paramMdl, reqMdl.getSmodel().getUsrsid());
        paramMdl.setFormData(form);
        con.commit();
        __doRedrawData(map, form, req, res, con, Cht010Form.MODE_CHANGE_GROUP);

    }

    /**
     * <br>[機  能] チャット相手切り替え時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doChangePartner(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        boolean bComp = biz.partnerChange(paramMdl);
        paramMdl.setFormData(form);
        con.commit();
        if (bComp) {
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_PARTNER);
        } else {
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_ERROR);
        }
    }

    /**
     * <br>[機  能] Enter送信切り替え時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doEnterSend(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionErrors errors = new ActionErrors();
        //2重投稿チェック
        if (!__tokenValidate(req)) {
            log__.info("２重投稿");
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.chat.token.validate",
                    gsMsg.getMessage("cht.cht010.55")), "token");
            addErrors(req, errors);
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_TOKEN_ERROR);
            return;
        }

        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        biz.enterSend(paramMdl);
        paramMdl.setFormData(form);
        con.commit();
        __doRedrawData(map, form, req, res, con, Cht010Form.MODE_SUCCESS);
    }

    /**
     * <br>[機  能] 送信ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doSend(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        MlCountMtController cntCon = getCountMtController(req);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);

        //権限チェック
        String errorMsg = biz.sendCheck(paramMdl, false);
        if (!errorMsg.equals("")) {
            __doRedrawSendError(map, form, req, res, con, errorMsg);
            return;
        }

        ActionErrors errors = null;
        //入力チェック
        errors = form.validateSendAllText(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_ERROR);
            return;
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        errors = new ActionErrors();
        //2重投稿チェック
        if (!__tokenValidate(req)) {
            log__.info("２重投稿");
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.chat.token.validate",
                    gsMsg.getMessage("cht.cht010.51")), "token");
            addErrors(req, errors);
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_TOKEN_ERROR);
            return;
        }

        //登録処理
        List<String> binList = new ArrayList<String>();
        biz.sendMessage(paramMdl, cntCon, binList);
        paramMdl.setFormData(form);
        con.commit();
        __writeResp(res,
                biz.createJsonSendResp(paramMdl));
    }

    /**
     * <br>[機  能] 再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param errorMsg エラーメッセージ
     * @throws Exception 実行時例外
     */
    private void __doRedrawSendError(ActionMapping map,
                                      Cht010Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con,
                                      String errorMsg)
        throws Exception {
        JSONObject jsonData = new JSONObject();
        con.setAutoCommit(true);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        jsonData = JSONObject.fromObject(form);
        jsonData.element("success", false);
        jsonData.element("error", false);
        jsonData.element("errorAlert", true);
        jsonData.element("errorMsg", errorMsg);
        paramMdl.setFormData(form);
        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (IOException e) {
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] お気に入り変更時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doFavoriteChange(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionErrors errors = new ActionErrors();
        //2重投稿チェック
        if (!__tokenValidate(req)) {
            log__.info("２重投稿");
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.chat.token.validate",
                    gsMsg.getMessage("cht.cht010.56")), "token");
            addErrors(req, errors);
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_TOKEN_ERROR);
            return;
        }

        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        //登録処理・削除処理
        boolean bComp = biz.favoriteChange(paramMdl);
        paramMdl.setFormData(form);
        con.commit();
        if (bComp) {
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_FAVORITE);
        } else {
            String errorMsg = null;
            if (form.getCht010SelectKbn() == GSConstChat.CHAT_KBN_USER) {
                errorMsg = gsMsg.getMessage("cht.cht010.48");
            } else if (form.getCht010SelectKbn() == GSConstChat.CHAT_KBN_GROUP) {
                errorMsg = gsMsg.getMessage("cht.cht010.42");
            }
            __doRedrawSendError(map, form, req, res, con, errorMsg);
        }
    }

    /**
     * <br>[機  能] メッセージ削除の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doMessageDelete(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        //権限チェック
        String errorMsg = biz.sendCheck(paramMdl, false);
        if (!errorMsg.equals("")) {
            __doRedrawSendError(map, form, req, res, con, errorMsg);
            return;
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionErrors errors = new ActionErrors();
        //2重投稿チェック
        if (!__tokenValidate(req)) {
            log__.info("２重投稿");
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.chat.token.validate",
                    gsMsg.getMessage("cht.cht010.53")), "token");
            addErrors(req, errors);
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_TOKEN_ERROR);
            return;
        }
        //削除処理
        biz.messageDelete(paramMdl);
        paramMdl.setFormData(form);
        con.commit();
        __doRedrawData(map, form, req, res, con, Cht010Form.MODE_DELETE);
    }

    /**
     * <br>[機  能] メッセージ編集の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doMessageEdit(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        //権限チェック
        String errorMsg = biz.sendCheck(paramMdl, true);
        if (!errorMsg.equals("")) {
            __doRedrawSendError(map, form, req, res, con, errorMsg);
            return;
        }
        //入力チェック
        ActionErrors errors = form.validateSendText(getRequestModel(req), con, form);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_GRPCONF_ERROR);
            return;
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        errors = new ActionErrors();
        //2重投稿チェック
        if (!__tokenValidate(req)) {
            log__.info("２重投稿");
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.chat.token.validate",
                    gsMsg.getMessage("cht.cht010.52")), "token");
            addErrors(req, errors);
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_TOKEN_ERROR);
            return;
        }
        //編集処理
        ChatMessageModel mdl = biz.messageEdit(paramMdl);
        List<ChatMessageModel> list = new ArrayList<ChatMessageModel>();
        list.add(mdl);
        paramMdl.setCht010MessageList(list);
        paramMdl.setFormData(form);
        con.commit();
        __doRedrawData(map, form, req, res, con, Cht010Form.MODE_EDIT);
    }

    /**
     * <br>[機  能] 添付処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doTempAdd(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);

        String tempDir = "";
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        tempDir = tempUtil.getTempPath(reqMdl,
                                    GSConstChat.PLUGIN_ID_CHAT,
                                    GSConstChat.DIRID_CHT010);

        //権限チェック
        String errorMsg = biz.sendCheck(paramMdl, false);
        if (!errorMsg.equals("")) {
            __doRedrawSendError(map, form, req, res, con, errorMsg);
        } else {
            //添付登録
            MlCountMtController cntCon = getCountMtController(req);
            String appRootPath = getAppRootPath();


            List<Long> sid = biz.tempAdd(paramMdl, cntCon, appRootPath, tempDir);

            ArrayList<ArrayList<ChatMessageModel>> cmBiglist =
                    new ArrayList<ArrayList<ChatMessageModel>>();
            //画面表示処理
            for (int idx = 0; idx < sid.size(); idx++) {
                ArrayList<ChatMessageModel> cmList =
                        biz.messageDisp(paramMdl, cntCon, sid.get(idx));
                cmBiglist.add(cmList);
            }
            paramMdl.setFormData(form);
            con.commit();
            __doRedrawTempData(map, form, req, res, con, cmBiglist);
        }
        tempUtil.clearTempPath(reqMdl, GSConstChat.PLUGIN_ID_CHAT, GSConstChat.DIRID_CHT010);
    }

    /**
     * <br>[機  能] 再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param cmBigList 添付データごとのMessageData
     * @throws Exception 実行時例外
     */
    private void __doRedrawTempData(ActionMapping map,
                                      Cht010Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con,
                                      ArrayList<ArrayList<ChatMessageModel>> cmBigList)
        throws Exception {
        JSONObject jsonData = new JSONObject();
        con.setAutoCommit(true);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        jsonData = JSONObject.fromObject(form);
        jsonData.element("success", true);

        paramMdl.setFormData(form);
        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (IOException e) {
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }




    /**
     * <br>[機  能] 添付ファイルダウンロードの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownLoad(
                                    ActionMapping map,
                                    Cht010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws SQLException, Exception {

        CommonBiz cmnBiz = new CommonBiz();
        ChtBiz chtBiz = new ChtBiz(con);
        Cht010Biz biz = new Cht010Biz(con, getRequestModel(req));
        GsMessage gsMsg = new GsMessage(getRequestModel(req));

        try {
            Cht010ParamModel paramMdl = new Cht010ParamModel();
            paramMdl.setParam(form);
            //添付ファイルをダウンロード可能かチェックする
            int chk = biz.downloadCheck(paramMdl, false);
            if (chk == Cht010Biz.DOWNLOADCHECK_CODE_NONE) {
                return __setWarnPageParam(map, req, form, chk);
            } else if (chk == Cht010Biz.DOWNLOADCHECK_CODE_DELEATED) {
                return __setWarnPageParam(map, req, form, chk);
            } else if (chk == Cht010Biz.DOWNLOADCHECK_CODE_OK) {
                long binSid = biz.getDownloadBinSid(paramMdl);
                CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                        GroupSession.getResourceManager().getDomain(req));
                if (cbMdl != null) {
                    String download = gsMsg.getMessage("cmn.download");
                    //ログ出力処理
                    String name = biz.getPartnerName(paramMdl);

                    name = "[" + gsMsg.getMessage("cht.cht010.35") + "]" + name;

                    chtBiz.outPutLog(
                            map,
                            download, GSConstLog.LEVEL_INFO, name,
                            getRequestModel(req), "");
                    //時間のかかる処理の前にコネクションを破棄
                    JDBCUtil.closeConnectionAndNull(con);
                    //ファイルをダウンロードする
                    TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(),
                            Encoding.UTF_8);
                    return null;
                }
            }
            return getSubmitErrorPage(map, req);
        } catch (Exception e) {
            log__.error("添付ファイル一括ダウンロードに失敗" + e);
            throw e;
        }
    }

    /**
     * <br>[機  能] 添付ファイル一括ダウンロードボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     *  テンポラリディレクトリルール
     * ○一括ダウンロードファイル作成ディレクトリ
     *    プラグインID/セッションID/chtAllExp/タイトル/${表示順}_${ユーザ名}/各添付ファイル
     * ○ダウンロード用ZIPファイル一時保存ディレクトリ
     *    プラグインID/セッションID/chtAllExp/タイトル.zip
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __exportAllTmp(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {
        RequestModel reqMdl =  getRequestModel(req);
        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //テンポラリディレクトリパスを取得
        String tempDir = "";
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        tempDir = tempUtil.getTempPath(reqMdl,
                                    GSConstChat.PLUGIN_ID_CHAT,
                                    GSConstChat.DIRID_CHT010);
        log__.debug("テンポラリディレクトリ = " + tempDir);

        try {
            Cht010ParamModel paramMdl = new Cht010ParamModel();
            paramMdl.setParam(form);
            int chk = biz.downloadCheck(paramMdl, true);
            if (chk == Cht010Biz.DOWNLOADCHECK_CODE_NONE) {
                return __setWarnPageParam(map, req, form, chk);
            } else if (chk == Cht010Biz.DOWNLOADCHECK_CODE_DELEATED) {
                return __setWarnPageParam(map, req, form, chk);
            }
            if (paramMdl.getCht010AllTempSid() == null
                    || paramMdl.getCht010AllTempSid().length() == 0) {
                return getSubmitErrorPage(map, req);
            }
            String [] index = null;
            try {
                index = biz.makeAllTmpFile(paramMdl, getAppRootPath(), tempDir);
            } catch (IOToolsException | IOException e) {
                log__.error("勤務表一括出力ZIP作成に失敗", e);
                return __setWarnPageParam(map, req, form, Cht010Biz.DOWNLOADCHECK_CODE_ZIPERROR);

            }

            if (index == null) {
                return getSubmitErrorPage(map, req);
            }
            paramMdl.setFormData(form);

            String outFilePath = index[0];
            String outBookName = index[1];

            paramMdl.setParam(form);
            String name = biz.getPartnerName(paramMdl);

            name = "[" + gsMsg.getMessage("cht.cht010.35") + "]" + name;
            String download = gsMsg.getMessage("cht.allTmep.download.log");
            //ログ出力処理
            ChtBiz chtBiz = new ChtBiz(con);
            chtBiz.outPutLog(
                    map, download, GSConstLog.LEVEL_INFO,
                    name, reqMdl, "");

            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);

            TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);
        } catch (Exception e) {
            log__.error("添付ファイル一括ダウンロードに失敗" + e);
            throw e;
        } finally {
            //TEMPディレクトリ削除
            tempUtil.clearTempPath(reqMdl, GSConstChat.PLUGIN_ID_CHAT, GSConstChat.DIRID_CHT010);
        }
        return null;
    }

    /**
     * <br>[機  能] スクロールによる読み込み処理の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doScrollRead(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        biz.scrollRead(paramMdl);
        paramMdl.setFormData(form);
        con.commit();
        __doRedrawData(map, form, req, res, con, Cht010Form.MODE_SCROLL);
    }

    /**
     * <br>[機  能] 既読アップデート処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doUpdateKidoku(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);

        // アクセス権限チェック
        String errorMsg = biz.kidokuAccessCheck(paramMdl);
        if (!errorMsg.equals("")) {
            __doRedrawSendError(map, form, req, res, con, errorMsg);
            return;
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionErrors errors = new ActionErrors();
        //2重投稿チェック
        if (!__tokenValidate(req)) {
            log__.info("２重投稿");
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.chat.token.validate",
                    gsMsg.getMessage("cht.cht010.04")), "token");
            addErrors(req, errors);
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_TOKEN_ERROR);
            return;
        }
        int cnt = biz.kidokuUpdate(paramMdl);
        paramMdl.setFormData(form);
        con.commit();
        __doRedrawKidokuUpdate(map, form, req, res, con, cnt);
    }

    /**
     * <br>[機  能] 再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param cnt 未読件数
     * @throws Exception 実行時例外
     */
    private void __doRedrawKidokuUpdate(ActionMapping map,
                                      Cht010Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con,
                                      int cnt)
        throws Exception {
        JSONObject jsonData = new JSONObject();
        con.setAutoCommit(true);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        jsonData = JSONObject.fromObject(form);
        jsonData.element("success", true);
        jsonData.element("count", cnt);

        paramMdl.setFormData(form);
        __writeResp(res, jsonData);
    }
    /**
     *
     * <br>[機  能] jsonレスポンスの書き込み処理
     * <br>[解  説]
     * <br>[備  考]
     * @param res レスポンス
     * @param json jsonオブジェクト
     */
    private void __writeResp(HttpServletResponse res,
            JSONObject json) {
        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(json);
            out.flush();
        } catch (IOException e) {
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * <br>[機  能] 再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param mode 作成モード
     * @throws Exception 実行時例外
     */
    private void __doRedrawData(ActionMapping map,
                                      Cht010Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con,
                                      int mode)
        throws Exception {
        JSONObject jsonData = new JSONObject();
        con.setAutoCommit(true);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        if (mode != Cht010Form.MODE_SEND) {
            jsonData = JSONObject.fromObject(form);
        }
        if (mode != Cht010Form.MODE_ERROR && mode != Cht010Form.MODE_TOKEN_ERROR) {
            jsonData.element("success", true);
        }
        switch (mode) {
        case Cht010Form.MODE_SUCCESS:
            break;
        case Cht010Form.MODE_PARTNER:
            __doMessage(jsonData, req, paramMdl);
            break;
        case Cht010Form.MODE_MORE:
            __doMore(jsonData, paramMdl);
            break;
        case Cht010Form.MODE_SEND:
            break;
        case Cht010Form.MODE_FAVORITE:
            __doFavorite(jsonData, paramMdl);
            break;
        case Cht010Form.MODE_EDIT:
            break;
        case Cht010Form.MODE_SCROLL:
            __doScroll(jsonData, req, paramMdl);
            break;
        case Cht010Form.MODE_CHANGE_GROUP:
            __doChangeGroup(jsonData, paramMdl);
            break;
        case Cht010Form.MODE_ERROR:
            jsonData.element("error", true);

            List<String> messageList = __setErrorMessage(req);
            jsonData.element("errorSize", messageList.size());
            for (int nIdx = 0; nIdx < messageList.size(); nIdx++) {
                jsonData.element("errorMessage_" + nIdx, String.valueOf(messageList.get(nIdx)));
            }
            break;
        case Cht010Form.MODE_TOKEN_ERROR:
            jsonData.element("tokenError", true);
            List<String> tokenErrorList = __setErrorMessage(req);
            jsonData.element("errorSize", tokenErrorList.size());
            for (int nIdx = 0; nIdx < tokenErrorList.size(); nIdx++) {
                jsonData.element("errorMessage_" + nIdx, String.valueOf(tokenErrorList.get(nIdx)));
            }
            break;
        case Cht010Form.MODE_GET_GROUP:
            jsonData.element("groupName",
                    StringUtilHtml.transToHTmlPlusAmparsant(
                            paramMdl.getCht010ChtInfMdl().getChatName())
                    );
            break;
        case Cht010Form.MODE_DELETE:
        default:
        }
        paramMdl.setFormData(form);
        __writeResp(res, jsonData);
    }

    /**
     * <br>[機  能] チャット相手切り替え時の再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramMdl Cht010ParamModel
     * @param req リクエスト
     * @throws Exception 実行時例外
     */
    private void __doMessage(JSONObject jsonData,
            HttpServletRequest req, Cht010ParamModel paramMdl)
        throws Exception {

        jsonData.element("messageAreaDisp", String.valueOf(paramMdl.getCht010MessageAreaDisp()));
        jsonData.element("allDispFlg", String.valueOf(paramMdl.getCht010AllDispFlg()));
        jsonData.element("firstDate", paramMdl.getCht010FirstEntryDay());
        jsonData.element("favoriteFlg", paramMdl.getCht010FavoriteFlg());
        jsonData.element("enterFlg", String.valueOf(paramMdl.getCht010EnterSendFlg()));
        List<ChatMessageModel> mesList = paramMdl.getCht010MessageList();
        jsonData.element("size", mesList.size());
        jsonData.element("sessionSid", getRequestModel(req).getSmodel().getUsrsid());
        ChatInformationModel infMdl = paramMdl.getCht010ChtInfMdl();
        jsonData.element("chatKbn", String.valueOf(infMdl.getChatKbn()));
        jsonData.element("chatSid", String.valueOf(infMdl.getChatSid()));
        jsonData.element("chatName", StringUtilHtml.transToHTmlPlusAmparsant(infMdl.getChatName()));
        jsonData.element("generalGroupSize", String.valueOf(infMdl.getGeneralGroup().size()));
        jsonData.element("generalMemberSize", String.valueOf(infMdl.getGeneralMember().size()));
        jsonData.element("adminGroupSize", String.valueOf(infMdl.getAdminGroup().size()));
        jsonData.element("adminMemberSize", String.valueOf(infMdl.getAdminMember().size()));
        for (int idx = 0; idx < infMdl.getGeneralGroup().size(); idx++) {
            jsonData.element("generalGroup_"
        + idx, StringUtilHtml.transToHTmlPlusAmparsant(
                infMdl.getGeneralGroup().get(idx).getGrpName()));
        }
        for (int idx = 0; idx < infMdl.getGeneralMember().size(); idx++) {
            jsonData.element("generalMemberName_" + idx,
                    StringUtilHtml.transToHTmlPlusAmparsant(
                            UserUtil.makeName(
                                    infMdl.getGeneralMember().get(idx).getUsiSei(),
                                    infMdl.getGeneralMember().get(idx).getUsiMei()
                                    )
                            )
                    );
            jsonData.element("generalMemberUko_" + idx,
                    infMdl.getGeneralMember().get(idx).getUsrUkoFlg());
            jsonData.element("generalMemberJkbn_" + idx,
                    infMdl.getGeneralMember().get(idx).getUsrJkbn());
        }
        jsonData.element("chatArchive", String.valueOf(infMdl.getChatArchive()));
        jsonData.element("categorySid", String.valueOf(infMdl.getCategorySid()));
        jsonData.element("categoryName", infMdl.getCategoryName());
        jsonData.element("chatId",
                StringUtilHtml.transToHTmlPlusAmparsant(
                String.valueOf(infMdl.getChatId())));
        for (int idx = 0; idx < infMdl.getAdminGroup().size(); idx++) {
            jsonData.element("adminGroup_"
        + idx, StringUtilHtml.transToHTmlPlusAmparsant(
                infMdl.getAdminGroup().get(idx).getGrpName())
        );
        }
        for (int idx = 0; idx < infMdl.getAdminMember().size(); idx++) {
            jsonData.element("adminMemberName_" + idx,
                    StringUtilHtml.transToHTmlPlusAmparsant(
                            UserUtil.makeName(
                                    infMdl.getAdminMember().get(idx).getUsiSei(),
                                    infMdl.getAdminMember().get(idx).getUsiMei())
                            )
                    );
            jsonData.element("adminMemberUko_" + idx,
                    infMdl.getAdminMember().get(idx).getUsrUkoFlg());
            jsonData.element("adminMemberJkbn_" + idx,
                    infMdl.getAdminMember().get(idx).getUsrJkbn());
        }
        jsonData.element("chatBiko", infMdl.getChatBiko());
        jsonData.element("insertDate", String.valueOf(infMdl.getInsertDate()));
        jsonData.element("lastDate", String.valueOf(infMdl.getLastSendDate()));
        jsonData.element("strInsertDate", infMdl.getStrInsertDate());
        jsonData.element("strLastDate", infMdl.getStrLastSendDate());

        for (int nIdx = 0; nIdx < mesList.size(); nIdx++) {
            jsonData.element("messageSid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getMessageSid()));
            jsonData.element("selectSid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getSelectSid()));
            jsonData.element("usrSid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUsrSid()));
            jsonData.element("usrName_"
        + nIdx, StringUtilHtml.transToHTmlPlusAmparsant(mesList.get(nIdx).getUsrName()));
            jsonData.element("usrJkbn_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUsrJkbn()));
            jsonData.element("usrUkoFlg_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUsrUkoFlg()));
            jsonData.element("usrBinSid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUsrBinSid()));
            jsonData.element("usrPictKf_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUsrPictKf()));
            jsonData.element("messageText_"
        + nIdx, mesList.get(nIdx).getMessageText());
            jsonData.element("binSid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getBinSid()));
            jsonData.element("binFileName_"
        + nIdx, StringUtilHtml.transToHTmlPlusAmparsant(
                mesList.get(nIdx).getTmpFile().getBinFileName()));
            jsonData.element("binFileSizeDsp_"
        + nIdx, mesList.get(nIdx).getTmpFile().getBinFileSizeDsp());
            jsonData.element("messageKbn_"
        + nIdx, String.valueOf(mesList.get(nIdx).getMessageKbn()));
            jsonData.element("insertUid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getInsertUid()));
            jsonData.element("insertDate_"
        + nIdx, String.valueOf(mesList.get(nIdx).getInsertDate()));
            jsonData.element("updateUid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUpdateUid()));
            jsonData.element("updateDate_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUpdateDate()));
            jsonData.element("tmpFile_"
        + nIdx, String.valueOf(mesList.get(nIdx).getTmpFile()));
            jsonData.element("ownKidoku_"
        + nIdx, String.valueOf(mesList.get(nIdx).getOwnKidoku()));
            jsonData.element("partnerKidoku_"
        + nIdx, String.valueOf(mesList.get(nIdx).getPartnerKidoku()));
            jsonData.element("entryDay_"
        + nIdx, String.valueOf(mesList.get(nIdx).getEntryDay()));
            jsonData.element("entryTime_"
        + nIdx, String.valueOf(mesList.get(nIdx).getEntryTime()));
            jsonData.element("updateDay_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUpdateDay()));
            jsonData.element("updateTime_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUpdateTime()));
        }
    }

    /**
     * <br>[機  能] もっとみる再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramMdl Cht010ParamModel
     * @throws Exception 実行時例外
     */
    private void __doMore(JSONObject jsonData, Cht010ParamModel paramMdl)
        throws Exception {
        //トランザクショントークン設定
        List<ChatMidokuModel> cmList = paramMdl.getCht010MidokuList();
        jsonData.element("size", cmList.size());
        jsonData.element("buttonDisp", paramMdl.getCht010MoreView());

        for (int idx = 0; idx < cmList.size(); idx++) {
            jsonData.element("sid_" + idx, String.valueOf(cmList.get(idx).getMidokuSid()));
            jsonData.element("kbn_" + idx, String.valueOf(cmList.get(idx).getMidokuKbn()));
            jsonData.element("name_" + idx,
                    StringUtilHtml.transToHTmlPlusAmparsant(cmList.get(idx).getMidokuName())
                    );
            jsonData.element("count_" + idx, String.valueOf(cmList.get(idx).getMidokuCount()));
            jsonData.element("date_" + idx, String.valueOf(cmList.get(idx).getMidokuDate()));
            jsonData.element("dispDate_" + idx, cmList.get(idx).getMidokuDispDate());
            jsonData.element("archive_" + idx, cmList.get(idx).getArchiveFlg());
            jsonData.element("usrUkoFlg_" + idx, cmList.get(idx).getUsrUkoFlg());
            jsonData.element("usrJkbn_" + idx, cmList.get(idx).getMidokuJkbn());
        }
    }





    /**
     * <br>[機  能] お気に入り再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramMdl Cht010ParamModel
     * @throws Exception 実行時例外
     */
    private void __doFavorite(JSONObject jsonData, Cht010ParamModel paramMdl)
        throws Exception {
        if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_USER) {
            jsonData.element("size", paramMdl.getCht010FavoriteUser().size());
            for (int idx = 0; idx < paramMdl.getCht010FavoriteUser().size(); idx++) {
                jsonData.element("favUsrSid" + idx,
                        paramMdl.getCht010FavoriteUser().get(idx).getUsrSid());
                jsonData.element("favUsrName" + idx,
                        StringUtilHtml.transToHTmlPlusAmparsant(
                                UserUtil.makeName(
                                        paramMdl.getCht010FavoriteUser().get(idx).getUsiSei(),
                                        paramMdl.getCht010FavoriteUser().get(idx).getUsiMei()
                                        )
                                )
                        );
                jsonData.element("favUsrUko" + idx,
                        paramMdl.getCht010FavoriteUser().get(idx).getUsrUkoFlg());
                jsonData.element("favUsrJkbn" + idx,
                        paramMdl.getCht010FavoriteUser().get(idx).getUsrJkbn());
                jsonData.element("favUsrCnt" + idx,
                        paramMdl.getCht010FavoriteUser().get(idx).getChtUserCount());
            }
        } else if (paramMdl.getCht010SelectKbn() == GSConstChat.CHAT_KBN_GROUP) {
            jsonData.element("size", paramMdl.getCht010FavoriteGroup().size());
            for (int idx = 0; idx < paramMdl.getCht010FavoriteGroup().size(); idx++) {
                jsonData.element("favGrpSid" + idx,
                        paramMdl.getCht010FavoriteGroup().get(idx).getCgiSid());
                jsonData.element("favGrpName" + idx,
                        StringUtilHtml.transToHTmlPlusAmparsant(
                                paramMdl.getCht010FavoriteGroup().get(idx).getCgiName())
                        );
                jsonData.element("favGrpCnt" + idx,
                        paramMdl.getCht010FavoriteGroup().get(idx).getChtGroupCount());
            }
        }
    }


    /**
     * <br>[機  能] スクロール時の再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramMdl Cht010ParamModel
     * @param req リクエスト
     * @throws Exception 実行時例外
     */
    private void __doScroll(JSONObject jsonData,
            HttpServletRequest req, Cht010ParamModel paramMdl)
        throws Exception {

        List<ChatMessageModel> mesList = paramMdl.getCht010MessageList();
        jsonData.element("size", mesList.size());
        jsonData.element("sessionSid", getRequestModel(req).getSmodel().getUsrsid());
        jsonData.element("allDispFlg", String.valueOf(paramMdl.getCht010AllDispFlg()));

        for (int nIdx = 0; nIdx < mesList.size(); nIdx++) {
            jsonData.element("messageSid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getMessageSid()));
            jsonData.element("selectSid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getSelectSid()));
            jsonData.element("usrSid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUsrSid()));
            jsonData.element("usrName_"
        + nIdx, StringUtilHtml.transToHTmlPlusAmparsant(
                mesList.get(nIdx).getUsrName()));
            jsonData.element("usrJkbn_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUsrJkbn()));
            jsonData.element("usrUkoFlg_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUsrUkoFlg()));
            jsonData.element("usrBinSid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUsrBinSid()));
            jsonData.element("usrPictKf_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUsrPictKf()));
            jsonData.element("messageText_"
        + nIdx, mesList.get(nIdx).getMessageText());
            jsonData.element("binSid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getBinSid()));
            jsonData.element("binFileName_"
        + nIdx, StringUtilHtml.transToHTmlPlusAmparsant(
                mesList.get(nIdx).getTmpFile().getBinFileName()));
            jsonData.element("binFileSizeDsp_"
        + nIdx, mesList.get(nIdx).getTmpFile().getBinFileSizeDsp());
            jsonData.element("messageKbn_"
        + nIdx, String.valueOf(mesList.get(nIdx).getMessageKbn()));
            jsonData.element("insertUid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getInsertUid()));
            jsonData.element("insertDate_"
        + nIdx, String.valueOf(mesList.get(nIdx).getInsertDate()));
            jsonData.element("updateUid_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUpdateUid()));
            jsonData.element("updateDate_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUpdateDate()));
            jsonData.element("tmpFile_"
        + nIdx, String.valueOf(mesList.get(nIdx).getTmpFile()));
            jsonData.element("ownKidoku_"
        + nIdx, String.valueOf(mesList.get(nIdx).getOwnKidoku()));
            jsonData.element("partnerKidoku_"
        + nIdx, String.valueOf(mesList.get(nIdx).getPartnerKidoku()));
            jsonData.element("entryDay_"
        + nIdx, String.valueOf(mesList.get(nIdx).getEntryDay()));
            jsonData.element("entryTime_"
        + nIdx, String.valueOf(mesList.get(nIdx).getEntryTime()));
            jsonData.element("updateDay_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUpdateDay()));
            jsonData.element("updateTime_"
        + nIdx, String.valueOf(mesList.get(nIdx).getUpdateTime()));
        }
    }

    /**
     * <br>[機  能] グループコンボ変更時再描画処理
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param paramMdl Cht010ParamModel
     * @throws Exception 実行時例外
     */
    private void __doChangeGroup(JSONObject jsonData, Cht010ParamModel paramMdl)
        throws Exception {
        //トランザクショントークン設定
        List<ChatUserInfModel> usrList = paramMdl.getCht010UserList();
        jsonData.element("size", usrList.size());
        for (int idx = 0; idx < usrList.size(); idx++) {
            jsonData.element("usrUkoFlg_" + idx, usrList.get(idx).getUsrUkoFlg());
            jsonData.element("usrSid_" + idx, usrList.get(idx).getUsrSid());
            jsonData.element("usiPictKf_" + idx, usrList.get(idx).getUsiPictKf());
            jsonData.element("binSid_" + idx, usrList.get(idx).getBinSid());
            jsonData.element("usrUkoFlg_" + idx, usrList.get(idx).getUsrUkoFlg());
            jsonData.element("usrJkbn_" + idx, usrList.get(idx).getUsrJkbn());
            jsonData.element("usiSei_" + idx,
                    StringUtilHtml.transToHTmlPlusAmparsant(usrList.get(idx).getUsiSei())
                    );
            jsonData.element("usiMei_" + idx,
                    StringUtilHtml.transToHTmlPlusAmparsant(usrList.get(idx).getUsiMei())
                    );
            jsonData.element("chtUserCount_" + idx, usrList.get(idx).getChtUserCount());
        }
    }


    /**
     * <br>[機  能] グループ管理：初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doGroupConfDsp(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {
            RequestModel reqMdl = getRequestModel(req);
            Cht010Biz biz = new Cht010Biz(con, reqMdl);
            Cht010ParamModel paramMdl = new Cht010ParamModel();

            paramMdl.setParam(form);
            BaseUserModel userMdl = getSessionUserModel(req);
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminFlg = cmnBiz.isPluginAdmin(con, userMdl, getPluginId());
            int usrSid = getRequestModel(req).getSmodel().getUsrsid();
            if (adminFlg) {
                paramMdl.setAdminFlg(GSConst.USER_ADMIN);
            } else {
                paramMdl.setAdminFlg(GSConst.USER_NOT_ADMIN);
            }
            ChtBiz chtBiz = new ChtBiz(con);
            if (chtBiz.isCreateChtGroup(usrSid, adminFlg)) {
                paramMdl.setCht010GroupEditFlg(GSConstChat.CHAT_GROUP_EDIT);
            } else {
                paramMdl.setCht010GroupEditFlg(GSConstChat.CHAT_GROUP_NOT_EDIT);
            }
            biz.setGroupConf(paramMdl, adminFlg, userMdl.getUsrsid());
            biz.setGrpConfFormTag(paramMdl, usrSid);;
            paramMdl.setFormData(form);

            JSONObject json = biz.createJsonGroupConfInit(paramMdl);

            __doRespGroupConf(res, json);
     }

    /**
     * <br>[機  能] グループ管理:検索
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doGroupConfSearch(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {
        RequestModel reqMdl = getRequestModel(req);

        //管理者フラグ
        CommonBiz cmnBiz = new CommonBiz();
        BaseUserModel userMdl = getSessionUserModel(req);
        boolean adminFlg = cmnBiz.isPluginAdmin(con, userMdl, getPluginId());
        Cht010Biz biz = new Cht010Biz(con, reqMdl);

        //入力チェック
        ActionErrors errors
            =  form.validateGrpConfSearch(getRequestModel(req), con, adminFlg);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            List<String> messageList = __setErrorMessage(req);
            JSONObject json = biz.createJsonGroupConfError(messageList);
            __doRespGroupConf(res, json);
            return;
        }
        __doGroupConfDsp(map, form, req, res, con);
     }


    /**
     * <br>[機  能] 登録編集
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht010knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @throws Exception 実行時例外
     */
    public void __doAddEditGroup(ActionMapping map,
                                  Cht010Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
                                  throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        Cht010Biz biz = new Cht010Biz(con, reqMdl);
        //ログインユーザSID取得
        BaseUserModel smodel = getSessionUserModel(req);
        int usrSid = smodel.getUsrsid();
        //管理者フラグ
        CommonBiz cmnBiz = new CommonBiz();
        BaseUserModel userMdl = getSessionUserModel(req);
        boolean adminFlg = cmnBiz.isPluginAdmin(con, userMdl, getPluginId());

        //不正チェック
        ActionErrors errors =
                form.validateGroupConfLimitAddEdit(reqMdl, con, usrSid, adminFlg);
        if (!errors.isEmpty()) {
                addErrors(req, errors);
                List<String> messageList = __setErrorMessage(req);
                JSONObject json = biz.createJsonGroupConfGrpNotExist(messageList);
                __doRespGroupConf(res, json);
                return;
        }
        //入力チェック
        errors =  form.validateGroupConf(reqMdl, con, usrSid, adminFlg);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            List<String> messageList = __setErrorMessage(req);
            JSONObject json = biz.createJsonGroupConfError(messageList);
            __doRespGroupConf(res, json);
            return;
        }
        GsMessage gsMsg = new GsMessage(reqMdl);
        errors = new ActionErrors();
        //2重投稿チェック
        if (!__tokenValidate(req)) {
            log__.info("２重投稿");
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.chat.token.validate",
                    gsMsg.getMessage("cht.cht010.57")), "token");
            addErrors(req, errors);
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_TOKEN_ERROR);
            return;
        }
        // 検索項目情報設定
        form.chkGrpConfSearch(reqMdl, con, adminFlg);
        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);
        //登録
        boolean commit = false;
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);
        try {

            if (form.getCht010GrpConfProcMode() == GSConstChat.CHAT_MODE_ADD) {
                biz.insertGroup(paramMdl, usrSid, cntCon, adminFlg);
            } else  {
                biz.updateGroup(paramMdl, usrSid, cntCon, adminFlg);
            }
            commit = true;
            // 表示
            biz.setGroupConf(paramMdl, adminFlg, userMdl.getUsrsid());
            // ログ
            __doOutLog(map, form, req, res, con, usrSid, adminFlg);
        } catch (IOException e) {
            String message = "";
            if (form.getCht010GrpConfProcMode() == GSConstChat.CHAT_MODE_ADD) {
                message = gsMsg.getMessage("cmn.entry");
            } else {
                message = gsMsg.getMessage("cmn.edit");
            }
            ActionMessage msg
            =  new ActionMessage("error.fail", message);
            String eprefix = "addEditGroup";
            StrutsUtil.addMessage(errors, msg, eprefix);

            List<String> messageList = __setErrorMessage(req);
            JSONObject json = biz.createJsonGroupConfError(messageList);
            __doRespGroupConf(res, json);
        } finally {
            if (!commit) {
                con.rollback();
            } else {
                con.commit();
            }
        }
        biz.createNewGroupParam(paramMdl);

        JSONObject json = biz.createJsonGroupConfUpdate(paramMdl);

        //WebSocketによる変更通知
        ChtWebSocketBiz wsBiz = new ChtWebSocketBiz(con, reqMdl);
        wsBiz.editChatGroup(json,
                paramMdl.getCht010MemberUserSids(),
                paramMdl.getCht010OldMemberSids());

        paramMdl.setFormData(form);
        __doRespGroupConf(res, json);
    }

    /**
     * <br>[機  能] 削除
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __doDeleteGroup(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Cht010Biz biz = new Cht010Biz(con, getRequestModel(req));
        // ログインユーザSID取得
        BaseUserModel userMdl = getSessionUserModel(req);
        int usrSid = userMdl.getUsrsid();
        // システムorプラグイン管理者
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminFlg = cmnBiz.isPluginAdmin(con, userMdl, getPluginId());

        // 削除チェック
        ActionErrors errors
            = form.validateGroupConfDelete(reqMdl, con, usrSid, adminFlg);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            List<String> messageList = __setErrorMessage(req);
            JSONObject json = biz.createJsonGroupConfError(messageList);
            __doRespGroupConf(res, json);
            return;
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        errors = new ActionErrors();
        //2重投稿チェック
        if (!__tokenValidate(req)) {
            log__.info("２重投稿");
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.chat.token.validate",
                    gsMsg.getMessage("cht.cht010.58")), "token");
            addErrors(req, errors);
            __doRedrawData(map, form, req, res, con, Cht010Form.MODE_TOKEN_ERROR);
            return;
        }
        // 検索項目情報設定
        form.chkGrpConfSearch(reqMdl, con, adminFlg);
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);

        boolean commit = false;
        try {

            // グループチャットリアルタイム削除
            ChtBiz chtBiz = new ChtBiz(con);
            ChtMemberBiz memberBiz = new ChtMemberBiz(con);
            ChtGroupInfDao infDao = new ChtGroupInfDao(con);
            ChtGroupInfModel infMdl
                    = infDao.select(paramMdl.getCht010GrpConfCgiSid());
            String[] adminMembers = paramMdl.getCht010GrpConfMemberAdminSid();
            String[] generalMembers = paramMdl.getCht010GrpConfMemberGeneralSid();
            paramMdl.setCht010OldMemberSids(
                    memberBiz.createMemberUserSid(adminMembers, generalMembers));
            paramMdl.setCht010GrpConfProcMode(GSConstChat.CHAT_MODE_EDIT);
            // グループチャット論理削除
            biz.logicDeleteChtGroup(paramMdl,
                             getSessionUserSid(req));
            biz.setGroupConf(paramMdl, adminFlg, userMdl.getUsrsid());

            //ログ出力処理
            String msg = gsMsg.getMessage("cmn.delete");
            String opCode = msg;
            String logGroupName
            = " [" + gsMsg.getMessage("cmn.group.name") + "] " + infMdl.getCgiName();
            chtBiz.outPutLog(
                    map,
                    opCode,
                    GSConstLog.LEVEL_INFO,
                    logGroupName,
                    reqMdl,
                    null);
            commit = true;
        } catch (IOException e) {
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        JSONObject json = biz.createJsonGroupConfDelete(paramMdl);

        //WebSocketによる変更通知
        ChtWebSocketBiz wsBiz = new ChtWebSocketBiz(con, reqMdl);
        wsBiz.editChatGroup(json,
                paramMdl.getCht010MemberUserSids(),
                paramMdl.getCht010OldMemberSids());

        paramMdl.setFormData(form);
        __doRespGroupConf(res, json);
    }

    /**
     * <br>[機  能] 登録・編集画面表示
     * <br>[解  説]
     * <br>[備  考]
    * @param map アクションマッピング
    * @param form アクションフォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @throws Exception 実行例外
    */
    private void __doGroupAddEditDsp(ActionMapping map, Cht010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        // ログインユーザSID取得
        BaseUserModel userMdl = getSessionUserModel(req);
        int usrSid = userMdl.getUsrsid();

        Cht010Biz biz = new Cht010Biz(con, getRequestModel(req));
        // システムorプラグイン管理者
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminFlg = cmnBiz.isPluginAdmin(con, userMdl, getPluginId());
        ActionErrors errors
            = form.validateGroupConfLimitAddEdit(getRequestModel(req), con, usrSid, adminFlg);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            List<String> messageList = __setErrorMessage(req);

            JSONObject json = biz.createJsonGroupConfError(messageList);

            __doRespGroupConf(res, json);
            return;
        }
        Cht010ParamModel paramMdl = new Cht010ParamModel();
        paramMdl.setParam(form);

        biz.setAddEditData(paramMdl, adminFlg, usrSid);
        biz.setGrpConfFormTag(paramMdl, usrSid);

        JSONObject json = biz.createJsonGroupConfAddEdit(paramMdl);

        paramMdl.setFormData(form);
        __doRespGroupConf(res, json);
    }

    /**
     * <br>[機  能] チャットグループ操作 レスポンス送信
     * <br>[解  説]
     * <br>[備  考]
     * @param res レスポンス
     * @param jsonData json
     * @throws Exception 実行時例外
     */
    private void __doRespGroupConf(HttpServletResponse res,
                                         JSONObject jsonData)
        throws Exception {
        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (IOException e) {
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] エラーメッセージ取得
     * <br>[解  説]
     * <br>[備  考]
     * @param req HttpServletRequest
     * @throws Exception 実行時例外
     * @return エラーメッセージ
     */
    private List<String> __setErrorMessage(HttpServletRequest req) {
        ActionMessages errMessages = getErrors(req);
        List<String> errMsgList = new ArrayList<String>();
        MessageResources mres = getResources(req);
        @SuppressWarnings("rawtypes")
        Iterator it = errMessages.get();
        while (it.hasNext()) {
            ActionMessage error = (ActionMessage) it.next();
            errMsgList.add(mres.getMessage(error.getKey(), error.getValues()));
        }
        return errMsgList;
    }

    /**
     * <br>[機  能] ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cht010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param usrSid ユーザSID
     * @param adminFlg システム・プラグイン管理者
     * @throws Exception 実行時例外
     */
    private void __doOutLog(ActionMapping map,
            Cht010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int usrSid,
            boolean adminFlg)
            throws Exception {
        // ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        ChtBiz biz = new ChtBiz(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.change");
        if (form.getCht010GrpConfProcMode() == GSConstChat.CHAT_MODE_ADD) {
            opCode = gsMsg.getMessage("cmn.entry");
        } else {
            opCode = gsMsg.getMessage("cmn.edit");
        }

        StringBuilder sb = new StringBuilder();
        // グループID
        sb.append("[" + gsMsg.getMessage("cht.01")
                + gsMsg.getMessage("main.src.man220.6") + "] ");
            sb.append(form.getCht010GrpConfGroupId());
        sb.append(System.getProperty("line.separator"));

        // カテゴリ
        if (adminFlg) {
            sb.append("[" + gsMsg.getMessage("cmn.category") + "] ");
            ChtCategoryDao cateDao = new ChtCategoryDao(con);
            String categoryName = cateDao.select(form.getCht010GrpConfCategory()).getChcName();
            sb.append(categoryName);
            sb.append(System.getProperty("line.separator"));
        }

        // グループ名
        sb.append("[" + gsMsg.getMessage("cmn.group.name") + "] ");
        sb.append(form.getCht010GrpConfGroupName());
        sb.append(System.getProperty("line.separator"));

        // メンバー
        ChtMemberBiz memberBiz = new ChtMemberBiz(con);
        // 管理者
        String[] adminMember = form.getCht010GrpConfMemberAdminSid();
        List<UsrLabelValueBean> limitAdminList = memberBiz.getMemberLabel(adminMember);
        if (!limitAdminList.isEmpty()) {
            StringBuilder str = new StringBuilder();
            sb.append("[" + gsMsg.getMessage("cht.03") + "]");
            for (UsrLabelValueBean name: limitAdminList) {
                 if (str.length() > 0) {
                     str.append(",");
                 }
                 str.append(name.getLabel());
            }
            sb.append(str.toString());
            sb.append(System.getProperty("line.separator"));
        }
        //　一般
        String[] generalMember = form.getCht010GrpConfMemberGeneralSid();
        List<UsrLabelValueBean> limitGeneralList = memberBiz.getMemberLabel(generalMember);
        if (!limitGeneralList.isEmpty()) {
            StringBuilder str = new StringBuilder();
            sb.append("[" + gsMsg.getMessage("cht.04") + "]");
            for (UsrLabelValueBean name: limitGeneralList) {
                 if (str.length() > 0) {
                     str.append(",");
                 }
                 str.append(name.getLabel());
            }
            sb.append(str.toString());
            sb.append(System.getProperty("line.separator"));
        }
        // 備考
        sb.append("[" + gsMsg.getMessage("cmn.memo") + "] ");
        sb.append(form.getCht010GrpConfBiko());
        sb.append(System.getProperty("line.separator"));
        // アーカイブ
        if (form.getCht010GrpConfProcMode() == GSConstChat.CHAT_MODE_EDIT) {
            sb.append("[" + gsMsg.getMessage("cht.cht110.03") + "] ");
            if (form.getCht010GrpConfArchiveKbn() == GSConstChat.CHAT_ARCHIVE_MODE) {
                sb.append(gsMsg.getMessage("cht.cht110.01"));
            } else {
                sb.append(gsMsg.getMessage("cht.cht110.02"));
            }
        }
        biz.outPutLog(
                map, opCode, GSConstLog.LEVEL_INFO, sb.toString(),
                reqMdl, null);
    }

    /**
     * <br>[機  能] 既読変更処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doGetAdmConf(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        ChtBiz chtBiz = new ChtBiz(con);
        ChtAdmConfModel aconfMdl = chtBiz.getChtAconf();
        JSONObject jsonData = new JSONObject();
        jsonData.element("plugin", "chat");
        jsonData.element("type", "message");
        jsonData.element("success", true);
        jsonData.element("chatFlg", aconfMdl.getCacChatFlg());
        jsonData.element("groupFlg", aconfMdl.getCacGroupFlg());
        jsonData.element("groupKbn", aconfMdl.getCacGroupKbn());
        jsonData.element("readFlg", aconfMdl.getCacReadFlg());
        if (aconfMdl.getCacReadFlg() == GSConstChat.KIDOKU_DISP) {
            jsonData.element("selectSid", form.getCht010SelectPartner());
            jsonData.element("selectKbn", GSConstChat.CHAT_KBN_USER);
            jsonData.element("senderSid", -1);
            jsonData.element("readMsgSids", form.getCht010KidokuMessageSids());
            jsonData.element("command", "kidoku");
        }

        // WebSocketへデータ送信
        ChtWebSocketBiz wsBiz = new ChtWebSocketBiz(con, reqMdl);
        wsBiz.sendToWebSocket(jsonData);

        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (IOException e) {
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] 個人設定情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doGetPriConf(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {
            int usrSid = getRequestModel(req).getSmodel().getUsrsid();
            ChtBiz chtBiz = new ChtBiz(con);
            ChtPriConfModel uconfMdl = chtBiz.getChtUconf(usrSid);
            JSONObject jsonData = new JSONObject();
            jsonData.element("success", true);
            jsonData.element("dspFlg", uconfMdl.getCpcPushFlg());
            jsonData.element("dspTime", uconfMdl.getCpcPushTime());
            jsonData.element("ownUserSid", usrSid);
            PrintWriter out = null;
            try {
                res.setHeader("Cache-Control", "no-cache");
                res.setContentType("application/json;charset=UTF-8");
                out = res.getWriter();
                out.print(jsonData);
                out.flush();
            } catch (IOException e) {
            } finally {
                if (out != null) {
                    out.close();
                }
            }
     }

    /**
     * <br>[機  能] 未読件数取得
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doGetMidokuCnt(
        ActionMapping map,
        Cht010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ChatDao chtDao = new ChatDao(con);
        int midokuCount = 0;
        if (form.getCht010SelectKbn() == GSConstChat.CHAT_KBN_USER) {
            ChtUserPairDao cupDao = new ChtUserPairDao(con);
            int pairSid = cupDao.select(form.getCht010SelectPartner(),
                    form.getCht010EditUsrSid());
            midokuCount = chtDao.getMidokuCountPair(
                    form.getCht010EditUsrSid(), pairSid);
        } else if (form.getCht010SelectKbn() == GSConstChat.CHAT_KBN_GROUP) {
            midokuCount = chtDao.getMidokuCountGroup(
                    form.getCht010EditUsrSid(), form.getCht010SelectPartner());
        }

        JSONObject jsonData = new JSONObject();
        jsonData.element("success", true);
        jsonData.element("midokuCnt", midokuCount);
        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (IOException e) {
        } finally {
            if (out != null) {
                out.close();
            }
        }
     }



    /**
     * <br>[機  能] 警告ページへ遷移
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマップ
     * @param req リクエスト
     * @param form アクションフォーム
     * @param code ダウンロードエラーコード
     * @return ActionForward
     */
    private ActionForward __setWarnPageParam(
            ActionMapping map,
            HttpServletRequest req,
            Cht010Form form,
            int code) {

            Cmn999Form cmn999Form = new Cmn999Form();
            ActionForward urlForward = null;

            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setIcon(Cmn999Form.ICON_WARN);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
            urlForward = map.findForward("chat");
            cmn999Form.setUrlOK(urlForward.getPath());

            //メッセージセット
            GsMessage gsMsg = new GsMessage(req);
            MessageResources msgRes = getResources(req);
            String warnMsg = null;
            if (code == Cht010Biz.DOWNLOADCHECK_CODE_NONE) {
                warnMsg = msgRes.getMessage("error.nothing.selected",
                        gsMsg.getMessage("cmn.attach.file"));
            } else if (code == Cht010Biz.DOWNLOADCHECK_CODE_DELEATED) {
                warnMsg = msgRes.getMessage(
                        "error.none.edit.data",
                        gsMsg.getMessage("cht.cht010.18"),
                        gsMsg.getMessage("cmn.download"));
            } else if (code == Cht010Biz.DOWNLOADCHECK_CODE_ZIPERROR) {
                String msgState = "error.fail";
                warnMsg = msgRes.getMessage(msgState,
                        gsMsg.getMessage("cmn.zip.create"));

            }
            cmn999Form.setMessage(warnMsg);

            cmn999Form.addHiddenParam("cht010SelectPartner", form.getCht010SelectPartner());
            cmn999Form.addHiddenParam("cht010SelectKbn", form.getCht010SelectKbn());


            req.setAttribute("cmn999Form", cmn999Form);

            return map.findForward("gf_msg");
        }

    /**
     * <br>[機  能] トークンチェックを行うメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return トークンチェック結果
     */
    private boolean __tokenValidate(HttpServletRequest req) {

        String token = req.getParameter(Globals.TOKEN_KEY);

        if (token == null) {
            return false;
        }

        Cookie[] cookies = req.getCookies();
        String value = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                value = cookie.getValue();
                if (value.equals(token)) {
                    return true;
                }
            }
        }
        return false;
    }
}
