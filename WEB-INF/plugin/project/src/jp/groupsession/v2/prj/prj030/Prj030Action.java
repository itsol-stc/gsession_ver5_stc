package jp.groupsession.v2.prj.prj030;

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
import jp.co.sjts.util.NullDefault;
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
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.ProjectUpdateBiz;
import jp.groupsession.v2.prj.dao.PrjPrjdataDao;
import jp.groupsession.v2.prj.model.PrjSmailModel;
import jp.groupsession.v2.prj.model.PrjSmailParamModel;
import jp.groupsession.v2.prj.prj010.Prj010Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクトメイン画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj030Action extends AbstractProjectAction {

    /** CMD:プロジェクト編集ボタンクリック */
    public static final String CMD_PRJ_EDIT_CLICK = "prjEditClick";
    /** CMD:戻るボタンクリック */
    public static final String CMD_BACK_CLICK = "back030";
    /** CMD:TODO登録ボタンクリック */
    public static final String CMD_TODO_ADD = "todoAdd";
    /** CMD:TODO名称リンククリック */
    public static final String CMD_TODO_TITLE_CLICK = "todoRef";
    /** CMD:TODO一覧ボタンクリック */
    public static final String CMD_TODO_SEARCH = "todoSearch";
    /** CMD:TODO編集アイコンクリック */
    public static final String CMD_TODO_EDIT = "todoEdit";
    /** CMD:回覧ボタンクリック */
    public static final String CMD_CIR_SEND = "cirSend";
    /** CMD:ショートメールボタンクリック */
    public static final String CMD_SML_SEND = "smlSend";
    /** CMD:プロジェクトメンバ設定ボタンクリック */
    public static final String CMD_MEMBER_EDIT = "memberEdit";
    /** CMD:TODOインポートボタンクリック */
    public static final String CMD_TODO_IMPORT = "todoImport";
    /** CMD:ツリーリンククリック */
    public static final String CMD_DETAIL_DIR = "detailDir";
    /** CMD:前ページ */
    public static final String CMD_PAGE_PREVEW = "prev";
    /** CMD:次ページ */
    public static final String CMD_PAGE_NEXT = "next";
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
    private static Log log__ = LogFactory.getLog(Prj030Action.class);

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

        log__.debug("Prj030Action start");
        ActionForward forward = null;

        Prj030Form thisForm = (Prj030Form) form;

        PrjCommonBiz prjBiz = new PrjCommonBiz(con, getRequestModel(req));
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(
                con, getRequestModel(req).getSmodel(), GSConstProject.PLUGIN_ID_PROJECT);

        if (!prjBiz.isAcsessPrj(
                con, thisForm.getPrj030prjSid(), getSessionUserSid(req), adminUser)) {
                return __doTransitionErrorPage(map, thisForm, req);
        }

        con.setAutoCommit(true);
        //ショートメール、回覧プラグインの使用可否判定
        __canUsePlugin(map, thisForm, req, res, con);
        con.setAutoCommit(false);

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        if (CMD_PRJ_EDIT_CLICK.equals(cmd)) {
            log__.debug("プロジェクト編集ボタンクリック");
            forward = map.findForward(CMD_PRJ_EDIT_CLICK);

        } else if (CMD_TODO_SEARCH.equals(cmd)) {
            log__.debug("TODO詳細検索ボタンクリック");
            forward = map.findForward(CMD_TODO_SEARCH);

        } else if (CMD_TODO_ADD.equals(cmd)) {
            log__.debug("TODO登録ボタンクリック");
            forward = map.findForward(CMD_TODO_ADD);

        } else if (CMD_DEL_TODO.equals(cmd)) {
            log__.debug("削除ボタン(TODO)クリック");
            forward = __doDeleteTodo(map, thisForm, req, res, con);

        } else if (CMD_DEL_TODO_EXE.equals(cmd)) {
            log__.debug("削除(TODO)実行");
            forward = __doDeleteTodoExe(map, thisForm, req, res, con);

        } else if (CMD_TODO_TITLE_CLICK.equals(cmd)) {
            log__.debug("TODOタイトルリンククリック");
            forward = map.findForward(CMD_TODO_TITLE_CLICK);

        } else if (CMD_TODO_EDIT.equals(cmd)) {
            log__.debug("TODO編集アイコンクリック");
            forward = map.findForward(CMD_TODO_EDIT);

        } else if (CMD_BACK_CLICK.equals(cmd)) {
            log__.debug("戻るボタンクリック");
            forward = __doBack(map, thisForm, req, res, con);

        } else if (CMD_CIR_SEND.equals(cmd)) {
            log__.debug("回覧ボタンクリック");
            forward = __doCirSend(map, thisForm, req, res, con);

        } else if (CMD_SML_SEND.equals(cmd)) {
            log__.debug("ショートメールボタンクリック");
            forward = __doSmlSend(map, thisForm, req, res, con);

        } else if (CMD_MEMBER_EDIT.equals(cmd)) {
            log__.debug("プロジェクトメンバー設定ボタンクリック");
            forward = map.findForward(CMD_MEMBER_EDIT);

        } else if (CMD_TODO_IMPORT.equals(cmd)) {
            log__.debug("TODOインポートボタンクリック");
            forward = map.findForward(CMD_TODO_IMPORT);

        } else if (CMD_PAGE_PREVEW.equals(cmd)) {
            log__.debug("前ページ");
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (CMD_PAGE_NEXT.equals(cmd)) {
            log__.debug("次ページ");
            forward = __doNext(map, thisForm, req, res, con);

        } else if (CMD_EDIT_STATUS.equals(cmd)) {
            log__.debug("変更ボタンクリック");
            forward = __doEditStatus(map, thisForm, req, res, con);

        } else if (CMD_EDIT_DATE.equals(cmd)) {
            log__.debug("日付変更");
            __doEditDate(map, thisForm, req, res, con);

        } else if (CMD_UPDATE_LIST.equals(cmd)) {
            log__.debug("TODOリスト更新");
            forward = __doInit(map, thisForm, req, res, con);

        } else if (CMD_DETAIL_DIR.equals(cmd)) {
            log__.debug("ツリーリンククリック");
            forward = map.findForward(CMD_DETAIL_DIR);

        } else if (cmd.equals("getImageFile")) {
            log__.debug("画像ダウンロード");
            forward = __doGetImageFile(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("Prj030Action end");
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
                                 Prj030Form form,
                                 HttpServletRequest req,
                                 HttpServletResponse res,
                                 Connection con) throws SQLException {

        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        Prj030Biz biz = new Prj030Biz(con, getRequestModel(req));

        Prj030ParamModel paramMdl = new Prj030ParamModel();
        paramMdl.setParam(form);
        biz.setUsePlugin(paramMdl, pconfig);
        paramMdl.setFormData(form);
    }

    /**
     * <br>[機  能] 初期表示処理
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
        Prj030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //パラメータを初期化
        form.validatePrm(req);

        con.setAutoCommit(true);
        //初期表示情報を画面にセットする
        Prj030Biz biz = new Prj030Biz(con, getRequestModel(req));
        Prj030ParamModel paramMdl = new Prj030ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, getSessionUserModel(req));
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        saveToken(req);
        return map.getInputForward();
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
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doBack(
        ActionMapping map,
        Prj030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        String prj030scrId =
            NullDefault.getStringZeroLength(
                    form.getPrj030scrId(), GSConstProject.SCR_ID_PRJ010);
        Prj030Biz biz = new Prj030Biz(con, getRequestModel(req));
        return biz.getActionForward(prj030scrId, map);
    }

    /**
     * <br>[機  能] 回覧ボタンクリック時の処理を行う
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
    private ActionForward __doCirSend(
        ActionMapping map,
        Prj030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        //入力チェック
        ActionErrors errors = form.validate030(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //回覧先を設定
        req.setAttribute("cmn120userSid", form.getPrj030sendMember());

        //回覧板新規作成へ
        return map.findForward(GSConstProject.SCR_CIR_NEW);
    }

    /**
     * <br>[機  能] ショートメールボタンクリック時の処理を行う
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
    private ActionForward __doSmlSend(
        ActionMapping map,
        Prj030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        //入力チェック
        ActionErrors errors = form.validate030(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //送信先を設定
        req.setAttribute("cmn120userSid", form.getPrj030sendMember());
        req.setAttribute("sml010scriptFlg", "1");

        //ショートメール作成へ
        return map.findForward(GSConstProject.SCR_SML_NEW);
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
        Prj030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ページ設定
        int page = form.getPrj030page1();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setPrj030page1(page);
        form.setPrj030page2(page);

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
        Prj030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ページ設定
        int page = form.getPrj030page1();
        page += 1;
        form.setPrj030page1(page);
        form.setPrj030page2(page);

        return __doInit(map, form, req, res, con);
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
                                      Prj030Form form,
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

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        ActionErrors errors = form.validateEditStatus(con, buMdl, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));

        boolean commitFlg = false;

        List<String> titleList = new ArrayList<String>();
        try {

            con.setAutoCommit(false);

            Prj030Biz biz = new Prj030Biz(con, getRequestModel(req));
            //TODOタイトルを取得する
            titleList = biz.getTargetName(con, form.getPrj030selectTodo());

            //状態変更履歴情報を登録する
            Prj030ParamModel paramMdl = new Prj030ParamModel();
            paramMdl.setParam(form);
            biz.editTodoStatus(paramMdl, cntCon, userSid);
            paramMdl.setFormData(form);

//            //状態 = 100%時
//            if (form.getPrj030selectEditStatus() == GSConstProject.STATUS_100) {
//                //実績情報を更新する
//                biz.doUpdateZisseki(form, userSid);
//            }

            commitFlg = true;
            con.commit();

            __canUsePlugin(map, form, req, res, con);
            if (form.isUseSmail()) {

                PrjPrjdataDao prjdataDao = new PrjPrjdataDao(con);
                int smailKbn = prjdataDao.getTodoSMailKbn(form.getPrj030prjSid());
                if (smailKbn == GSConstProject.TODO_MAIL_SEND_ADMIN) {
                    PrjSmailParamModel smlParamModel = new PrjSmailParamModel();
                    smlParamModel.setCmdMode(GSConstProject.CMD_MODE_ADD);
                    smlParamModel.setPrjSid(form.getPrj030prjSid());
                    smlParamModel.setUsrSid(userSid);

                    smlParamModel.setTarget(GSConstProject.SEND_LEADER);

                    smlParamModel.setHistory(textMultiEditRiku);
                    smlParamModel.setAppRoot(getAppRootPath());
                    smlParamModel.setTempDir(getTempPath(req));

                    commitFlg = false;
                    con.setAutoCommit(false);
                    for (String todoSid : form.getPrj030selectTodo()) {
                        smlParamModel.setTodoSid(Integer.parseInt(todoSid));
                        PrjSmailModel param = prjBiz.getSmailParamMdl(smlParamModel);

                        prjBiz.sendTodoEditMail(
                                con, cntCon, param, getAppRootPath(), getPluginConfig(req));
                    }

                    con.commit();
                    commitFlg = true;
                }
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
            if (idx != 0) {
                message += "\r\n";
            }
            message += "[" + getInterMessage(req, "cmn.title") + "]" + titleList.get(idx);
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
        Prj030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        ActionErrors errors = form.validateDelTodo(
                con, getSessionUserModel(req), getRequestModel(req));
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
        Prj030Form form,
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
        if (prjBiz.checkDataExist(form.getPrj030selectTodo())) {
            //TODOに紐付くデータがある
            msg = msgRes.getMessage("sakujo.kakunin.list",
                                    GSConstProject.MSG_TODO,
//                                    "TODOに紐付くデータも同時に削除されます。");
                                    prjBiz.getMsgTodoTitle(con, form.getPrj030selectTodo()), req);
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
        Prj030Form form,
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
            //TODO名を取得
            Prj030Biz biz = new Prj030Biz(con, getRequestModel(req));
            titleList = biz.getTargetName(con, form.getPrj030selectTodo());
            //TODO情報を削除する
            ProjectUpdateBiz projectBiz = new ProjectUpdateBiz(con);
            projectBiz.deleteTodo(form.getPrj030selectTodo(), userSid);

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
        String message = textTodo + "\r\n";
        for (int idx = 0; idx < titleList.size(); idx++) {
            if (idx != 0) {
                message += "\r\n";
            }
            message += "[" + getInterMessage(req, "cmn.title") + "]" + titleList.get(idx);
        }
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
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
                                            Prj030Form form,
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
        Prj030Form form,
        HttpServletRequest req,
        int type) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("redraw").getPath());
        GsMessage gsMsg = new GsMessage();

        //状態
        MessageResources msgRes = getResources(req);
        String msg = null;
        if (type == 1) {
            msg = msgRes.getMessage("sakujo.kanryo.object", GSConstProject.MSG_TODO);
        } else {
            String textStatus = gsMsg.getMessage(req, "cmn.status");
            msg = msgRes.getMessage("hensyu.henkou.kanryo.object",
                                textStatus);
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
                                      Prj030Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws SQLException, IOToolsException, IOException, Exception {

//        prjBiz.outPutLog(map, req, res,
//                getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_TRACE,
//                getInterMessage(req, "project.update.status.history"));
//        return __setKanryoDsp(map, form, req);
        if (form.getPrj030selectTodoStr() != null) {
            ArrayList<String> prmStrList = new ArrayList<String>();
            String prmStr = form.getPrj030selectTodoStr();
            //チェックボックスの値を解析
            while (prmStr.indexOf(',') > 0) {
                String newStr = prmStr.substring(0, prmStr.indexOf(','));
                prmStrList.add(newStr.replace(",", ""));
                prmStr = prmStr.substring(prmStr.indexOf(',') + 1);
            }
            prmStrList.add(prmStr);
            form.setPrj030selectTodo((String[]) prmStrList.toArray(new String[prmStrList.size()]));

            log__.debug("移動区分＝" + form.getPrj030chDateKbn());
            log__.debug("休日の反映＝" + form.getPrj030chDateHol());
            log__.debug("移動月" + form.getPrj030mvMonth());
            log__.debug("移動日" + form.getPrj030mvDay());
            for (String a : form.getPrj030selectTodo()) {
                log__.debug("変更するTODO=" + a);
            }


            GsMessage gsMsg = new GsMessage(req);
            //日付変更
            String textMultiEditRiku = gsMsg.getMessage(req, "project.date.change");
//            if (!isTokenValid(req, true)) {
//                log__.info("２重投稿");
//                return getSubmitErrorPage(map, req);
//            }

            BaseUserModel buMdl = getSessionUserModel(req);
            ActionErrors errors = form.validateEditDate(con, buMdl, getRequestModel(req));
            if (!errors.isEmpty()) {
                addErrors(req, errors);
//                return __doInit(map, form, req, res, con);
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
                Prj030Biz biz = new Prj030Biz(con, getRequestModel(req));

                Prj030ParamModel paramMdl = new Prj030ParamModel();
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
                    for (String sid : form.getPrj030selectTodo()) {
                        int smailKbn = prjdataDao.getTodoSMailKbn(form.getPrj030prjSid());
                        if (smailKbn == GSConstProject.TODO_MAIL_SEND_ADMIN) {
                            smlParamModel.setPrjSid(form.getPrj030prjSid());
                            smlParamModel.setTodoSid(Integer.valueOf(sid));
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

    /**
     * <br>[機  能] 存在チェックエラー画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doTransitionErrorPage(
        ActionMapping map,
        Prj030Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        MessageResources msgRes = getResources(req);
        urlForward = map.findForward("prjIndex");

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "error.none.edit.projectdata";
        cmn999Form.setMessage(msgRes.getMessage(msgState));
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}