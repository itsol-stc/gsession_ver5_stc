package jp.groupsession.v2.prj.prj010;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.http.BrowserUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.ProjectUpdateBiz;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.model.PrjSmailModel;
import jp.groupsession.v2.prj.model.PrjSmailParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 ダッシュボード画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj010Action extends AbstractProjectAction {

    /** CMD:プロジェクトタブクリック */
    public static final String CMD_TAB_PROJECT = "tabProjectClick";
    /** CMD:TODOタブクリック */
    public static final String CMD_TAB_TODO = "tabTodoClick";

    /** CMD:初期表示・再表示 */
    public static final String CMD_PAGE_INIT = "init";
    /** CMD:前ページ */
    public static final String CMD_PAGE_PREVEW = "prev";
    /** CMD:次ページ */
    public static final String CMD_PAGE_NEXT = "next";

    /** CMD:プロジェクト登録ボタンクリック */
    public static final String CMD_PRJ_ADD = "prjAdd";
    /** CMD:プロジェクト名称リンククリック */
    public static final String CMD_PRJ_TITLE_CLICK = "prjMain";
    /** CMD:プロジェクト一覧・詳細検索ボタンクリック */
    public static final String CMD_PRJ_SEARCH = "prjSearch";

    /** CMD:TODO登録ボタンクリック */
    public static final String CMD_TODO_ADD = "todoAdd";
    /** CMD:TODO編集ボタンクリック */
    public static final String CMD_TODO_EDIT = "todoEdit";
    /** CMD:TODO名称リンククリック */
    public static final String CMD_TODO_TITLE_CLICK = "todoRef";
    /** CMD:TODO詳細検索ボタンクリック */
    public static final String CMD_TODO_SEARCH = "todoSearch";

    /** CMD:管理者設定ボタンクリック */
    public static final String CMD_ADMIN_CONFIG = "adminConf";
    /** CMD:個人設定ボタンクリック */
    public static final String CMD_PERSONAL_CONFIG = "personalConf";

    /** CMD:プロジェクトコンボ変更時 */
    public static final String CMD_PRJ_CMB_CHANGE = "changeTodoPrj";
    /** CMD:コンボ変更時 */
    public static final String CMD_CHANGE_COMBO = "changeCombo";

    /** CMD:削除(TODO情報) */
    public static final String CMD_DEL_TODO = "delTodo";
    /** CMD:削除実行(TODO情報) */
    public static final String CMD_DEL_TODO_EXE = "delTodoExe";
    /** CMD:画面再表示(初期表示以外) */
    public static final String CMD_BACK_REDRAW = "backRedraw";

    /** CMD:状態変更 */
    public static final String CMD_EDIT_STATUS = "editStatus";
    /** CMD:日付変更 */
    public static final String CMD_EDIT_DATE = "editDate";
    /** CMD:日付変更 */
    public static final String CMD_UPDATE_LIST = "updateTodoList";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj010Action.class);

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

        log__.debug("start");
        ActionForward forward = null;

        Prj010Form thisForm = (Prj010Form) form;

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        if (CMD_PERSONAL_CONFIG.equals(cmd)) {
            log__.debug("個人設定");
            forward = map.findForward(CMD_PERSONAL_CONFIG);

        } else if (CMD_ADMIN_CONFIG.equals(cmd)) {
            log__.debug("管理者設定");
            forward = map.findForward(CMD_ADMIN_CONFIG);

        } else if (CMD_PRJ_ADD.equals(cmd)) {
            log__.debug("プロジェクト登録");
            forward = map.findForward(CMD_PRJ_ADD);

        } else if (CMD_TODO_ADD.equals(cmd)) {
            log__.debug("TODO登録");
            forward = map.findForward(CMD_TODO_ADD);

        } else if (CMD_TODO_EDIT.equals(cmd)) {
            log__.debug("TODO編集ボタンクリック");
            forward = __doTodoEditClick(map, thisForm, req, res, con);

        } else if (CMD_DEL_TODO.equals(cmd)) {
            log__.debug("削除ボタン(TODO)クリック");
            forward = __doDeleteTodo(map, thisForm, req, res, con);

        } else if (CMD_DEL_TODO_EXE.equals(cmd)) {
            log__.debug("削除(TODO)実行");
            forward = __doDeleteTodoExe(map, thisForm, req, res, con);

        } else if (CMD_PRJ_TITLE_CLICK.equals(cmd)) {
            log__.debug("プロジェクトタイトルクリック");
            forward = __doPrjTitleClick(map, thisForm, req, res, con);

        } else if (CMD_TODO_TITLE_CLICK.equals(cmd)) {
            log__.debug("TODOタイトルクリック");
            forward = __doTodoTitleClick(map, thisForm, req, res, con);

        } else if (CMD_PRJ_SEARCH.equals(cmd)) {
            log__.debug("プロジェクト詳細検索");
            forward = __doProjectSearch(map, thisForm, req, res, con);

        } else if (CMD_TODO_SEARCH.equals(cmd)) {
            log__.debug("TODO詳細検索");
            forward = __doTodoSearch(map, thisForm, req, res, con);

        } else if (CMD_PAGE_PREVEW.equals(cmd)) {
            log__.debug("前ページ");
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (CMD_PAGE_NEXT.equals(cmd)) {
            log__.debug("次ページ");
            forward = __doNext(map, thisForm, req, res, con);

        } else if (CMD_TAB_PROJECT.equals(cmd)) {
            log__.debug("プロジェクトタブクリック");
            forward = __doProjectTabClick(map, thisForm, req, res, con);

        } else if (CMD_TAB_TODO.equals(cmd)) {
            log__.debug("TODOタブクリック");
            forward = __doTodoTabClick(map, thisForm, req, res, con);

        } else if (CMD_PRJ_CMB_CHANGE.equals(cmd)) {

            thisForm.setSelectingTodoSts(String.valueOf(GSConstProject.STATUS_ALL));
            forward = __doInit(map, thisForm, req, res, con);

        } else if (CMD_EDIT_STATUS.equals(cmd)) {
            log__.debug("変更ボタンクリック");
            forward = __doEditStatus(map, thisForm, req, res, con);

        } else if (CMD_EDIT_DATE.equals(cmd)) {
            log__.debug("日付変更");
            __doEditDate(map, thisForm, req, res, con);

        } else if (CMD_UPDATE_LIST.equals(cmd)) {
            log__.debug("TODOリスト更新");
            forward = __doInit(map, thisForm, req, res, con);

        } else if (CMD_CHANGE_COMBO.equals(cmd)) {

            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("getImageFile")) {
            log__.debug("画像ダウンロード");
            forward = __doGetImageFile(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("end");
        return forward;
    }

    /**
     * <br>[機  能] 初期処理を行う
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
    private ActionForward __doInit(
        ActionMapping map,
        Prj010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //パラメータを初期化
        form.validatePrm(req);

        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser
            = cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConstProject.PLUGIN_ID_PROJECT);
        form.setAdmin(adminUser);

        //初期表示情報を画面にセットする
        Prj010Biz biz = new Prj010Biz(con, getRequestModel(req));
        Prj010ParamModel paramMdl = new Prj010ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        //ブラウザの判定
        if (BrowserUtil.isIe(req)) {
            form.setPrj010IeFlg(1);
        }

        saveToken(req);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 前ページクリック時の処理
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
    private ActionForward __doPrev(
        ActionMapping map,
        Prj010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ページ設定
        int page = form.getPrj010page1();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setPrj010page1(page);
        form.setPrj010page2(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 次ページクリック時の処理
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
    private ActionForward __doNext(
        ActionMapping map,
        Prj010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ページ設定
        int page = form.getPrj010page1();
        page += 1;
        form.setPrj010page1(page);
        form.setPrj010page2(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] プロジェクトタブクリック時の処理
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
    private ActionForward __doProjectTabClick(
        ActionMapping map,
        Prj010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        form.setPrj010cmdMode(GSConstProject.MODE_PROJECT);
        form.setPrj010page1(1);
        form.setPrj010page2(1);
        form.setPrj010order(GSConst.ORDER_KEY_ASC);
        form.setPrj010sort(GSConstProject.SORT_PRJECT_START);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 送信済みタブクリック時の処理
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
    private ActionForward __doTodoTabClick(
        ActionMapping map,
        Prj010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        form.setPrj010cmdMode(GSConstProject.MODE_TODO);
        form.setPrj010page1(1);
        form.setPrj010page2(1);
        form.setPrj010order(GSConst.ORDER_KEY_DESC);
        form.setPrj010sort(GSConstProject.SORT_TODO_WEIGHT);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] プロジェクトタイトルクリック時の処理
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
    private ActionForward __doPrjTitleClick(
        ActionMapping map,
        Prj010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        ActionForward forward = null;

        forward = map.findForward(CMD_PRJ_TITLE_CLICK);
        return forward;
    }

    /**
     * <br>[機  能] TODOタイトルクリック時の処理
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
    private ActionForward __doTodoTitleClick(
        ActionMapping map,
        Prj010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        ActionForward forward = null;

        forward = map.findForward(CMD_TODO_TITLE_CLICK);
        return forward;
    }

    /**
     * <br>[機  能] TODO編集ボタンクリック時の処理
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
    private ActionForward __doTodoEditClick(
        ActionMapping map,
        Prj010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        ActionForward forward = null;

        forward = map.findForward(CMD_TODO_EDIT);
        return forward;
    }

    /**
     * <br>[機  能] プロジェクト検索ボタンクリック時の処理を行う
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
    private ActionForward __doProjectSearch(
        ActionMapping map,
        Prj010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //プロジェクト検索画面へ遷移する
        return map.findForward(CMD_PRJ_SEARCH);
    }

    /**
     * <br>[機  能] TODO検索ボタンクリック時の処理を行う
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
    private ActionForward __doTodoSearch(
        ActionMapping map,
        Prj010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //TODO検索画面へ遷移する
        return map.findForward(CMD_TODO_SEARCH);
    }
    /**
     * <br>[機  能] 更新(状態)クリック時処理
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
     * @throws Exception その他例外
     */
    private ActionForward __doEditStatus(ActionMapping map,
                                      Prj010Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws SQLException, IOToolsException, IOException, Exception {
        GsMessage gsMsg = new GsMessage(req);
        //一括更新
        String textMultiEditRiku = gsMsg.getMessage(req, "project.src.74");
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        BaseUserModel buMdl = getSessionUserModel(req);
        ActionErrors errors = form.validateEditStatus(con, buMdl, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));

        boolean commitFlg = false;

        List<String> titleList = new ArrayList<String>();

        try {

            con.setAutoCommit(false);

            //状態変更履歴情報を登録する
            Prj010Biz biz = new Prj010Biz(con, getRequestModel(req));

            Prj010ParamModel paramMdl = new Prj010ParamModel();
            paramMdl.setParam(form);
            biz.editTodoStatus(paramMdl, cntCon, userSid);
            paramMdl.setFormData(form);

            String[] todoSid = Prj010Biz.getTodoSid(paramMdl);
            titleList = biz.getTargetName(con, todoSid);
            commitFlg = true;
            con.commit();

            PluginConfig pconfig
                = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
            boolean useSmail = pconfig.getPlugin(GSConstProject.PLUGIN_ID_SMAIL) != null;

            if (useSmail) {

                PrjPrjdataDao prjdataDao = new PrjPrjdataDao(con);
                PrjSmailParamModel smlParamModel = new PrjSmailParamModel();
                smlParamModel.setCmdMode(GSConstProject.CMD_MODE_ADD);
                smlParamModel.setUsrSid(userSid);

                smlParamModel.setTarget(GSConstProject.SEND_LEADER);

                smlParamModel.setHistory(textMultiEditRiku);
                smlParamModel.setAppRoot(getAppRootPath());
                smlParamModel.setTempDir(getTempPath(req));

                commitFlg = false;
                con.setAutoCommit(false);
                for (String sid : form.getPrj010selectTodo()) {
                    int[] sids = Prj010Biz.formatSid(sid);
                    int smailKbn = prjdataDao.getTodoSMailKbn(sids[0]);
                    if (smailKbn == GSConstProject.TODO_MAIL_SEND_ADMIN) {

                        smlParamModel.setPrjSid(sids[0]);
                        smlParamModel.setTodoSid(sids[1]);
                        PrjSmailModel param = prjBiz.getSmailParamMdl(smlParamModel);

                        prjBiz.sendTodoEditMail(
                                con, cntCon, param, getAppRootPath(), getPluginConfig(req));
                    }
                }
                con.commit();
                commitFlg = true;
            }

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }
        String message = getInterMessage(req, "project.update.status.history") + "\r\n";
        for (int idx = 0; idx < titleList.size(); idx++) {
            String title = titleList.get(idx);
            if (idx != 0) {
                message += "\r\n";
            }
            message += "[" + getInterMessage(req, "cmn.title") + "]" + title;
        }
        prjBiz.outPutLog(map, req, res,
                getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_TRACE,
                message);
        return __setKanryoDsp(map, form, req, 0);
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
    private ActionForward __doDeleteTodo(
        ActionMapping map,
        Prj010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        ActionErrors errors = form.validateDelTodo(con, getSessionUserModel(req),
                getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
    }

    /**
     * <br>[機  能] 削除確認画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Prj010Form form,
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
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=" + CMD_DEL_TODO_EXE);

        //メッセージ
        MessageResources msgRes = getResources(req);
        String msg = "";

        GsMessage gsMsg = new GsMessage(req);

        //TODOに紐付くデータがあるかチェック
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));

        String[] todoSid = null;
        Prj010ParamModel paramMdl = new Prj010ParamModel();
        paramMdl.setParam(form);
        todoSid = Prj010Biz.getTodoSid(paramMdl);
        paramMdl.setFormData(form);

        if (prjBiz.checkDataExist(todoSid)) {
            //TODOに紐付くデータがある
            msg = msgRes.getMessage("sakujo.kakunin.list",
                                    GSConstProject.MSG_TODO,
//                                    "TODOに紐付くデータも同時に削除されます。");
                                    prjBiz.getMsgTodoTitle(con, todoSid));
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
    private ActionForward __doDeleteTodoExe(
        ActionMapping map,
        Prj010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {
        GsMessage gsMsg = new GsMessage(req);
        //TODO情報
        String textTodo = gsMsg.getMessage(req, "project.src.86");
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ActionErrors errors = form.validateDelTodo(
                con, getSessionUserModel(req), getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        //プロジェクトを削除する

        boolean commitFlg = false;
        List<String> titleList = new ArrayList<String>();

        try {
            //TODO情報を削除する
            ProjectUpdateBiz projectBiz = new ProjectUpdateBiz(con);
            String[] todoSid = null;
            Prj010ParamModel paramMdl = new Prj010ParamModel();
            paramMdl.setParam(form);
            todoSid = Prj010Biz.getTodoSid(paramMdl);
            Prj010Biz biz = new Prj010Biz(con, getRequestModel(req));
            titleList = biz.getTargetName(con, todoSid);
            paramMdl.setFormData(form);
            projectBiz.deleteTodo(todoSid, userSid);

            con.commit();
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

        //ログ出力処理
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        String message = "";
        for (int idx = 0; idx < titleList.size(); idx++) {
            if (idx != 0) {
                message += "\r\n";
            }
            String title = titleList.get(idx);
            message += "[" + getInterMessage(req, "cmn.title") + "]" + title;
        }
        message = textTodo + "\r\n" + message;
        prjBiz.outPutLog(
                map, req, res,
                getInterMessage(req, "cmn.delete"),
                GSConstLog.LEVEL_TRACE,
                message);


        //削除完了画面を表示
        return __setKanryoDsp(map, form, req, 1);
    }

    /**
     * <br>[機  能] tempディレクトリの画像を読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetImageFile(ActionMapping map,
                                            Prj010Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;
        //画像バイナリSIDとプロジェクトSIDの照合チェック
        Prj010Biz prj010Biz = new Prj010Biz(con, getRequestModel(req));
        boolean icoHnt = prj010Biz.cheIcoHnt(form.getPrj010PrjSid(), form.getPrj010PrjBinSid());

        if (!icoHnt) {
            return null;

        } else {
            cbMdl = cmnBiz.getBinInfo(con, form.getPrj010PrjBinSid(),
                    GroupSession.getResourceManager().getDomain(req));
        }

        if (cbMdl != null) {
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                                        Encoding.UTF_8);
        }
        return null;
    }

    /**
     * [機  能] 完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param type 操作種別 0:状態変更 1:TODO削除
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Prj010Form form,
        HttpServletRequest req,
        int type) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("redraw").getPath());

        MessageResources msgRes = getResources(req);
        String msg = null;
        if (type == 1) {
            msg = msgRes.getMessage("sakujo.kanryo.object", GSConstProject.MSG_TODO);
        } else {
            //状態
            GsMessage gsMsg = new GsMessage();
            String textStatus = gsMsg.getMessage(req, "cmn.status");
            msg = msgRes.getMessage("hensyu.henkou.kanryo.object", textStatus);
        }
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] TODO日付変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws IOException IOエラー
     * @throws Exception その他例外
     */
    private void __doEditDate(ActionMapping map,
                                      Prj010Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws SQLException, IOToolsException, IOException, Exception {

        if (form.getPrj010selectTodoStr() != null) {
            ArrayList<String> prmStrList = new ArrayList<String>();
            String prmStr = form.getPrj010selectTodoStr();
            //チェックボックスの値を解析
            while (prmStr.indexOf(',') > 0) {
                String newStr = prmStr.substring(0, prmStr.indexOf(','));
                prmStrList.add(newStr.replace(",", ""));
                prmStr = prmStr.substring(prmStr.indexOf(',') + 1);
            }
            prmStrList.add(prmStr);
            form.setPrj010selectTodo((String[]) prmStrList.toArray(new String[prmStrList.size()]));

            log__.debug("移動区分＝" + form.getPrj010chDateKbn());
            log__.debug("休日の反映＝" + form.getPrj010chDateHol());
            log__.debug("移動月" + form.getPrj010mvMonth());
            log__.debug("移動月" + form.getPrj010mvDay());
            for (String a : form.getPrj010selectTodo()) {
                log__.debug("変更するTODO=" + a);
            }


            GsMessage gsMsg = new GsMessage(req);
            //日付変更
            String textMultiEditRiku = gsMsg.getMessage(req, "project.date.change");

            BaseUserModel buMdl = getSessionUserModel(req);
            ActionErrors errors = form.validateEditDate(con, buMdl, getRequestModel(req));
            if (!errors.isEmpty()) {
                addErrors(req, errors);
            }

            //ログインユーザSIDを取得
            int userSid = 0;
            if (buMdl != null) {
                userSid = buMdl.getUsrsid();
            }

            //採番コントローラ
            MlCountMtController cntCon = getCountMtController(req);
            PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));

            boolean commitFlg = false;

            try {

                con.setAutoCommit(false);

                //日付変更履歴情報を登録する
                Prj010Biz biz = new Prj010Biz(con, getRequestModel(req));

                Prj010ParamModel paramMdl = new Prj010ParamModel();
                paramMdl.setParam(form);
                biz.editTodoDate(paramMdl, cntCon, userSid);
                paramMdl.setFormData(form);

                commitFlg = true;
                con.commit();

                PluginConfig pconfig
                    = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
                boolean useSmail = pconfig.getPlugin(GSConstProject.PLUGIN_ID_SMAIL) != null;

                if (useSmail) {

                    PrjPrjdataDao prjdataDao = new PrjPrjdataDao(con);
                    PrjSmailParamModel smlParamModel = new PrjSmailParamModel();
                    smlParamModel.setCmdMode(GSConstProject.CMD_MODE_ADD);
                    smlParamModel.setUsrSid(userSid);

                    smlParamModel.setTarget(GSConstProject.SEND_LEADER);

                    smlParamModel.setHistory(textMultiEditRiku);
                    smlParamModel.setAppRoot(getAppRootPath());
                    smlParamModel.setTempDir(getTempPath(req));

                    commitFlg = false;
                    con.setAutoCommit(false);
                    for (String sid : form.getPrj010selectTodo()) {
                        int[] sids = Prj010Biz.formatSid(sid);
                        int smailKbn = prjdataDao.getTodoSMailKbn(sids[0]);
                        if (smailKbn == GSConstProject.TODO_MAIL_SEND_ADMIN) {

                            smlParamModel.setPrjSid(sids[0]);
                            smlParamModel.setTodoSid(sids[1]);
                            PrjSmailModel param = prjBiz.getSmailParamMdl(smlParamModel);

                            prjBiz.sendTodoEditMail(
                                    con, cntCon, param, getAppRootPath(), getPluginConfig(req));
                        }
                    }
                    con.commit();
                    commitFlg = true;
                }

            } catch (SQLException e) {
                log__.error("SQLException", e);
                throw e;
            } finally {
                if (!commitFlg) {
                    JDBCUtil.rollback(con);
                }
            }
        }
     }
}