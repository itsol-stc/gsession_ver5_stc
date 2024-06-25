package jp.groupsession.v2.prj.prj050;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.prj030.Prj030Biz;
import jp.groupsession.v2.prj.prj060.Prj060Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] TODO登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj050Action extends AbstractProjectAction {

    /** CMD:OKボタンクリック */
    public static final String CMD_OK_CLICK = "okClick";
    /** CMD:削除ボタンクリック */
    public static final String CMD_DEL_CLICK = "deleteClick";
    /** CMD:削除実行 */
    public static final String CMD_DEL_EXE = "deleteExe";

    /** CMD:戻るボタンクリック */
    public static final String CMD_BACK_CLICK = "backClick050";
    /** CMD:画面再表示(初期表示以外) */
    public static final String CMD_BACK_REDRAW = "backRedraw";
    /** CMD:プロジェクトコンボ変更 */
    public static final String CMD_CHANGE_PROJECT = "changeProject";
    /** CMD:メイン画面から遷移 */
    public static final String CMD_MAIN = "addTodo";

    /** CMD:担当者追加ボタンクリック */
    public static final String CMD_MEMBER_ADD_CLICK = "memberAdd";
    /** CMD:担当者削除ボタンクリック */
    public static final String CMD_MEMBER_REMOVE_CLICK = "memberRemove";

    /** CMD:添付削除ボタンクリック */
    public static final String CMD_ATTACH_REMOVE_CLICK = "attachRemove";

    /** CMD:画面切替 簡易入力 */
    public static final String CMD_TO_EASY = "toEasy";
    /** CMD:画面切替 詳細入力 */
    public static final String CMD_TO_DETAIL = "toDetail";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj050Action.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "prj050";

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("Prj050Action start");
        ActionForward forward = null;

        Prj050Form thisForm = (Prj050Form) form;

        //ショートメール、回覧プラグインの使用可否判定
        __canUsePlugin(map, thisForm, req, res, con);

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);
        if (CMD_OK_CLICK.equals(cmd)) {
            log__.debug("OKボタンクリック");
            forward = __doPushEntry(map, thisForm, req, res, con);

        } else if (CMD_DEL_CLICK.equals(cmd)) {
            log__.debug("削除ボタンクリック");
            forward = __doDeleteConf(map, thisForm, req, res, con);

        } else if (CMD_DEL_EXE.equals(cmd)) {
            log__.debug("削除実行");
            forward = __doDeleteExe(map, thisForm, req, res, con);

        } else if (CMD_BACK_CLICK.equals(cmd)) {
            log__.debug("戻るボタンクリック");
            forward = __doBack(map, thisForm, req, res, con);

        } else if (CMD_ATTACH_REMOVE_CLICK.equals(cmd)) {
            log__.debug("添付削除ボタンクリック");
            forward = __doAttachDelete(map, thisForm, req, res, con);

        } else if (CMD_BACK_REDRAW.equals(cmd)) {
            log__.debug("画面再表示(初期表示以外)");
            forward = __doDspSet(map, thisForm, req, res, con);

        } else if (CMD_CHANGE_PROJECT.equals(cmd)) {
            log__.debug("プロジェクトコンボ変更");
            forward = __doChangeProject(map, thisForm, req, res, con);

        } else if (CMD_MAIN.equals(cmd)) {
            log__.debug("メイン画面からの遷移");
            forward = __doInitToMain(map, thisForm, req, res, con);

        } else if (CMD_TO_EASY.equals(cmd)) {
            log__.debug("簡易入力画面へ遷移");
            thisForm.setPrj050elementKbn(GSConstProject.DSP_TODO_EASY);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (CMD_TO_DETAIL.equals(cmd)) {
            log__.debug("詳細入力画面へ遷移");
            thisForm.setPrj050elementKbn(GSConstProject.DSP_TODO_DETAIL);
            forward = __doInit(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("Prj050Action end");
        return forward;
    }

    /**
     * <br>[機  能] ショートメール、回覧プラグインが使用可能か判定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __canUsePlugin(ActionMapping map,
                                 Prj050Form form,
                                 HttpServletRequest req,
                                 HttpServletResponse res,
                                 Connection con) throws SQLException {

        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        Prj030Biz biz = new Prj030Biz(con, getRequestModel(req));

        Prj050ParamModel paramMdl = new Prj050ParamModel();
        paramMdl.setParam(form);
        biz.setUsePlugin(paramMdl, pconfig);
        paramMdl.setFormData(form);

    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doInit(ActionMapping map, Prj050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IOToolsException, IOException, TempFileException,
                    IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        con.setAutoCommit(true);
        //初期表示
        if (form.getPrj050dspKbn() == GSConstProject.DSP_FIRST) {

            //簡易登録 プロジェクトコンボ表示 自分が参加 & TODO作成権限がある
            if (form.getPrj050elementKbn() == GSConstProject.DSP_TODO_EASY) {
                form.setPrj050PrjListKbn(GSConstProject.PRJ_KBN_PARTICIPATION);
            }

            //テンポラリディレクトリの削除を行う
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(getRequestModel(req),
                    GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);
            temp.createTempDir(getRequestModel(req),
                    GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //初期表示情報を画面にセットする
        Prj050Biz biz = new Prj050Biz(con, getRequestModel(req));

        Prj050ParamModel paramMdl = new Prj050ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(
                paramMdl,
                getSessionUserModel(req),
                getAppRootPath(),
                getTempPath(req),
                userSid,
                getRequestModel(req),
                GroupSession.getResourceManager().getDomain(req));
        paramMdl.setFormData(form);

        //画面に常に表示する情報を取得する
        paramMdl = new Prj050ParamModel();
        paramMdl.setParam(form);
        biz.getDspData(paramMdl, getSessionUserModel(req), getTempPath(req));

        paramMdl.setFormData(form);

        //簡易入力画面と詳細入力画面の入力データを一致させる
        __setSameData(form, req);

        //トランザクショントークン設定
        this.saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws IOToolsException IOエラー
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doDspSet(ActionMapping map, Prj050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IOToolsException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        con.setAutoCommit(true);
        //画面に常に表示する情報を取得する
        Prj050Biz biz = new Prj050Biz(con, getRequestModel(req));
        Prj050ParamModel paramMdl = new Prj050ParamModel();
        paramMdl.setParam(form);
        biz.getDspData(paramMdl, getSessionUserModel(req), getTempPath(req));
        paramMdl.setFormData(form);

        //トランザクショントークン設定
        this.saveToken(req);

        return map.getInputForward();
    }
    /**
     * <br>[機  能] 初期表示処理(メイン画面からの遷移時)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doInitToMain(ActionMapping map, Prj050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IOToolsException, IOException, TempFileException,
                    IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //遷移元パラメータをを設定
        form.setPrj050scrId(GSConstProject.SCR_ID_MAIN);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doPushEntry(
        ActionMapping map,
        Prj050Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {
        ActionErrors errors = null;

        con.setAutoCommit(true);

        //簡易入力チェック
        if (form.getPrj050elementKbn() == GSConstProject.DSP_TODO_EASY) {
            // 簡易入力情報を詳細入力へ追加
            form.setPrj050title(form.getPrj050titleEasy());
            form.setPrj050naiyo(form.getPrj050naiyoEasy());
            form.setPrj050juyou(form.getPrj050juyouEasy());
        }

        errors = form.validate050(con, req); // 簡易入力・詳細入力 共通

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDspSet(map, form, req, res, con);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //登録、または更新処理を行う
        Prj050Biz biz = new Prj050Biz(con, getRequestModel(req));


        Prj050ParamModel paramMdl = new Prj050ParamModel();
        paramMdl.setParam(form);
        boolean dataexist =
                biz.doAddEdit(
                        paramMdl,
                        cntCon,
                        userSid,
                        getAppRootPath(),
                        getTempPath(req),
                        getPluginConfig(req));
        paramMdl.setFormData(form);

        //テンポラリディレクトリの削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);

        GsMessage gsMsg = new GsMessage(req);

        //ログ出力処理
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        String opCode = "";

        //処理モード
        String cmdMode = form.getPrj050cmdMode();
        if (cmdMode.equals(GSConstProject.CMD_MODE_ADD)) {
            //登録
            opCode = getInterMessage(req, "cmn.entry");

        } else if (cmdMode.equals(GSConstProject.CMD_MODE_EDIT)) {
            //更新
            opCode = getInterMessage(req, "cmn.change");
        }

        String todoTitle = form.getPrj050title();

        prjBiz.outPutLog(
                map, req, res, opCode,
                GSConstLog.LEVEL_TRACE,
                "[name]" + todoTitle);

        //登録・更新完了画面を表示
        return __setTourokuKanryoDsp(map, form, req, dataexist);
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteConf(
        ActionMapping map,
        Prj050Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
    }

    /**
     * [機  能] 削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Prj050Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("redraw");
        cmn999Form.setUrlCancel(
                forwardCancel.getPath() + "?" + GSConst.P_CMD + "=" + CMD_BACK_REDRAW);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=" + CMD_DEL_EXE);

        //メッセージ
        MessageResources msgRes = getResources(req);
        String msg = "";

        GsMessage gsMsg = new GsMessage(req);

        //TODOに紐付くデータがあるかチェック
        String[] todoSid = new String[] {String.valueOf(form.getPrj050todoSid())};
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        if (prjBiz.checkDataExist(todoSid)) {
            //TODOに紐付くデータがある
            String title = null;
            //画面モードごとのtitleの取得
            if (form.getPrj050elementKbn() == GSConstProject.DSP_TODO_DETAIL) {
                title = form.getPrj050title();
            } else {
                title = form.getPrj050titleEasy();
            }

            if (!StringUtil.isNullZeroStringSpace(title)) {
                msg = msgRes.getMessage("sakujo.kakunin.list",
                        GSConstProject.MSG_TODO,
                        StringUtilHtml.transToHTmlPlusAmparsant(title));
            } else {
                msg = msgRes.getMessage("sakujo.kakunin.once", GSConstProject.MSG_TODO);
            }

        } else {
            //TODO情報なし
            msg = msgRes.getMessage("sakujo.kakunin.once", GSConstProject.MSG_TODO);
        }
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 登録・更新完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param dataexist 画面遷移時、対象データがあるか(削除、プロジェクトSID変更済でないか)
     *                  true=データが存在する、false=存在しない
     * @return ActionForward
     */
    private ActionForward __setTourokuKanryoDsp(
            ActionMapping map,
            Prj050Form form,
            HttpServletRequest req,
            boolean dataexist) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        if (!StringUtil.isNullZeroStringSpace(form.getPrj060schUrl())) {
            //URLを内部リンクに変更
            String url = StringUtilHtml.transToText(form.getPrj060schUrl());
            if (url.indexOf("/" + GSConst.PLUGINID_SCH) != -1) {
                url = url.substring(url.indexOf("/" + GSConst.PLUGINID_SCH));
            } else if (url.indexOf("/" + GSConst.PLUGINID_MAIN) != -1) {
                url = url.substring(url.indexOf("/" + GSConst.PLUGINID_MAIN));
            }
            cmn999Form.setUrlOK(url);
        } else {
            ActionForward forwardOk = setBackForward(map, form, dataexist, req);
            cmn999Form.setUrlOK(forwardOk.getPath());
        }

        MessageResources msgRes = getResources(req);

        String cmdMode = form.getPrj050cmdMode();
        if (cmdMode.equals(GSConstProject.CMD_MODE_ADD)) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object", GSConstProject.MSG_TODO));
        } else if (cmdMode.equals(GSConstProject.CMD_MODE_EDIT)) {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object", GSConstProject.MSG_TODO));
        }

        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除処理を行う(削除実行)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteExe(
        ActionMapping map,
        Prj050Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        //TODOを削除する
        Prj050Biz biz = new Prj050Biz(con, getRequestModel(req));

        Prj050ParamModel paramMdl = new Prj050ParamModel();
        paramMdl.setParam(form);
        biz.deleteTodo(paramMdl, userSid);
        paramMdl.setFormData(form);

        //テンポラリディレクトリの削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);
        GsMessage gsMsg = new GsMessage(req);

        //ログ出力処理
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        String opCode = getInterMessage(req, "cmn.delete");

        String todoTitle = "";
        if (form.getPrj050elementKbn() == GSConstProject.DSP_TODO_DETAIL) {
            todoTitle = form.getPrj050title();
        } else {
            todoTitle = form.getPrj050titleEasy();
        }

        prjBiz.outPutLog(
                map, req, res, opCode,
                GSConstLog.LEVEL_TRACE,
                "[name]" + todoTitle);


        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Prj050Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        if (!StringUtil.isNullZeroStringSpace(form.getPrj060schUrl())) {
            //URLを内部リンクに変更
            String url = StringUtilHtml.transToText(form.getPrj060schUrl());
            if (url.indexOf("/" + GSConst.PLUGINID_SCH) != -1) {
                url = url.substring(url.indexOf("/" + GSConst.PLUGINID_SCH));
            } else if (url.indexOf("/" + GSConst.PLUGINID_MAIN) != -1) {
                url = url.substring(url.indexOf("/" + GSConst.PLUGINID_MAIN));
            }
            cmn999Form.setUrlOK(url);
        } else {
            ActionForward forwardOk = setBackForward(map, form, false, req);
            cmn999Form.setUrlOK(forwardOk.getPath());
        }

        //削除完了
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object", GSConstProject.MSG_TODO));

        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 直前に表示していた画面へ遷移する(ActionForwardを返す)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form Prj050Form
     * @param dataexist 画面遷移時、対象データがあるか(削除、プロジェクトSID変更済でないか)
     *                   true=データが存在する、false=存在しない
     * @param req リクエスト
     * @return ActionForward
     */
    public ActionForward setBackForward(
        ActionMapping map,
        Prj050Form form,
        boolean dataexist,
        HttpServletRequest req) {

        ActionForward forward = null;

        String prj050scrId =
            NullDefault.getString(form.getPrj050scrId(), GSConstProject.SCR_ID_PRJ010);

        if (prj050scrId.equals(GSConstProject.SCR_ID_PRJ010)) {
            //ダッシュボードへ遷移する
            forward = map.findForward(GSConstProject.SCR_INDEX);

        } else if (prj050scrId.equals(GSConstProject.SCR_ID_PRJ060)) {

            String prj060scrId =
                NullDefault.getString(form.getPrj060scrId(), GSConstProject.SCR_ID_PRJ010);

            Prj060Biz biz = new Prj060Biz(getRequestModel(req));

            if (dataexist) {
                //コマンドパラメータ取得
                String cmd = PrjCommonBiz.getCmdProperty(req);
                //データあり
                if (cmd.equals(Prj050Action.CMD_BACK_CLICK)) {
                    //戻るボタン押下時
                    //TODO参照へ遷移する
                    forward = map.findForward(GSConstProject.SCR_TODO_VIEW);

                } else {
                    forward = biz.getActionForward(prj060scrId, map);
                }

            } else {
                //データ削除済みの場合、さらに前の画面へ遷移する
                forward = biz.getActionForward(prj060scrId, map);
            }

        } else if (prj050scrId.equals(GSConstProject.SCR_ID_PRJ070)) {
            //TODO詳細検索へ遷移する
            forward = map.findForward(GSConstProject.SCR_TODO_SEARCH);

        } else if (prj050scrId.equals(GSConstProject.SCR_ID_PRJ030)) {
            //プロジェクトメインへ遷移する
            forward = map.findForward(GSConstProject.SCR_PRJ_MAIN);
        } else if (prj050scrId.equals(GSConstProject.SCR_ID_MAIN)) {
            //メインメニューへ遷移する
            forward = map.findForward(GSConstProject.SCR_MENU);
        }

        return forward;
    }

    /**
     * <br>[機  能] 戻るボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    private ActionForward __doBack(
        ActionMapping map,
        Prj050Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException {

        //テンポラリディレクトリの削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);

        return setBackForward(map, form, true, req);
    }

    /**
     * <br>[機  能] 添付削除ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @return ActionForward
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doAttachDelete(ActionMapping map, Prj050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IOToolsException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        //テンポラリディレクトリのファイル削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteFile(form.getPrj050tenpu(), getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);

        return __doDspSet(map, form, req, res, con);
    }

    /**
     * <br>[機  能] プロジェクトコンボ変更時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws IOToolsException IOエラー
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doChangeProject(ActionMapping map, Prj050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                throws SQLException, IOToolsException, IllegalAccessException,
                InvocationTargetException, NoSuchMethodException {

        con.setAutoCommit(true);

        //マイプロジェクト区分を設定
        Prj050Biz biz = new Prj050Biz(con, getRequestModel(req));
        BaseUserModel buMdl = getSessionUserModel(req);

        Prj050ParamModel paramMdl = new Prj050ParamModel();
        paramMdl.setParam(form);
        biz.setMyPrjKbn(paramMdl, buMdl);
        //プロジェクト変更時ラベルを初期値に変更する
        paramMdl.setPrj050cate(GSConstCommon.NUM_INIT);
        paramMdl.setFormData(form);

        return __doDspSet(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 簡易入力画面と詳細入力画面の入力データを一致させる
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @throws IOToolsException IOエラー
     */
    private void __setSameData(
        Prj050Form form, HttpServletRequest req) throws IOToolsException {
        GsMessage gsMsg = new GsMessage();
        //新規登録
        String textRegistration = gsMsg.getMessage(req, "cmn.new.registration");
        int dspHnt = form.getPrj050elementKbn();

        //詳細入力
        if (dspHnt == GSConstProject.DSP_TODO_DETAIL) {
            //プロジェクト表示区分 自分が参加 & TODO作成権限がある
            form.setPrj050PrjListKbn(form.getPrj050PrjListKbn());
            //タイトル
            form.setPrj050title(form.getPrj050titleEasy());
            //重要度
            form.setPrj050juyou(form.getPrj050juyouEasy());
            //内容
            form.setPrj050naiyo(form.getPrj050naiyoEasy());

        //簡易入力
        } else {

            //タイトル
            form.setPrj050titleEasy(form.getPrj050title());
            //重要度
            form.setPrj050juyouEasy(form.getPrj050juyou());
            //内容
            form.setPrj050naiyoEasy(form.getPrj050naiyo());
            //状態変更理由
            form.setPrj050statusCmtEasy(textRegistration);
        }
    }
}