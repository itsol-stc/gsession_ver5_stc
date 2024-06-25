package jp.groupsession.v2.sch.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

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
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.biz.SchEasyRegisterBiz;
import jp.groupsession.v2.sch.model.SchEasyRegisterModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.sch.sch010.Sch010Form;
import jp.groupsession.v2.sch.sch020.Sch020Form;
import jp.groupsession.v2.sch.sch030.Sch030Form;
import jp.groupsession.v2.sch.sch040.Sch040Form;
import jp.groupsession.v2.sch.sch040kn.Sch040knForm;
import jp.groupsession.v2.sch.sch200.Sch200Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール(メイン画面表示用)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchMainAction extends AbstractScheduleAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchMainAction.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /**
     * <br>[機  能] Connectionに設定する自動コミットモードを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return AutoCommit設定値
     */
    protected boolean _getAutoCommit() {
        return true;
    }

    /**
     * <p>
     * アクション実行
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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        SchMainForm uform = (SchMainForm) form;

        int sessionUsrSid = getRequestModel(req).getSmodel().getUsrsid();
        //adminユーザの簡易登録を制御
        if ((cmd.equals("registerChk")
                || cmd.equals("registerDupChk")
                || cmd.equals("easyRegister")) 
                && sessionUsrSid == 0) {
            return getSubmitErrorPage(map, req);
        }

        if (cmd.equals("schw_today")) {
            //今日ボタン
            __doMoveDaysForWeek(uform, 0, true);
            __doInit(map, uform, req, res, con, true);
            forward = map.getInputForward();
        } else if (cmd.equals("schw_move_rd")) {
            //次日移動
            __doMoveDaysForWeek(uform, 1, false);
            __doInit(map, uform, req, res, con, true);
            forward = map.getInputForward();
        } else if (cmd.equals("schw_move_ld")) {
            //前日移動
            __doMoveDaysForWeek(uform, -1, false);
            __doInit(map, uform, req, res, con, true);
            forward = map.getInputForward();
        } else if (cmd.equals("schw_move_rw")) {
            //次週移動
            __doMoveDaysForWeek(uform, 7, false);
            __doInit(map, uform, req, res, con, true);
            forward = map.getInputForward();
        } else if (cmd.equals("schw_move_lw")) {
            //前週移動
            __doMoveDaysForWeek(uform, -7, false);
            __doInit(map, uform, req, res, con, true);
            forward = map.getInputForward();
        } else if (cmd.equals("schw_add")) {
            //登録
            forward = __doScheduleAdd(map, uform, req, res, con);
        } else if (cmd.equals("schw_edit")) {
            //修正・閲覧
            forward = __doScheduleEdit(map, uform, req, res, con);
        } else if (cmd.equals("month")) {
            //月間一覧
            log__.debug("月間一覧へ遷移");
            forward = __doScheduleMonth(map, uform, req, res, con);
        } else if (cmd.equals("day")) {
            //月間一覧
            log__.debug("日間一覧へ遷移");
            forward = __doScheduleDay(map, uform, req, res, con);
        } else if (cmd.equals("kojin_week")) {
            //個人週間
            log__.debug("個人週間へ遷移");
            forward = __doScheduleKojinWeek(map, uform, req, res, con);
        } else if (cmd.equals("week")) {
            //個人週間
            log__.debug("週間へ遷移");
            forward = __doScheduleWeek(map, uform, req, res, con);
        } else if (cmd.equals("registerChk")) {
            //簡易登録チェック
            log__.debug("簡易登録チェック");
            __doRegisterCheck(map, uform, req, res, con);
        } else if (cmd.equals("registerDupChk")) {
            //簡易登録 重複チェック
            log__.debug("簡易登録チェック");
            __doRegisterDupCheck(map, uform, req, res, con);
        } else if (cmd.equals("easyRegister")) {
            //簡易登録
            log__.debug("簡易登録");
            forward = __doRegister(map, uform, req, res, con);
        } else {
            //初期表示
            __doInit(map, uform, req, res, con, false);
            forward = map.getInputForward();
        }
        return forward;
    }

    /**
     * 初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param changeDateFlg 日付変更フラグ false:変更なし true:変更あり
     * @throws SQLException SQL実行時例外
     */
    private void __doInit(
            ActionMapping map,
            SchMainForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            boolean changeDateFlg)
            throws SQLException {

        SchMainBiz biz = new SchMainBiz();
        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        biz.getInitData(form, getRequestModel(req), con, changeDateFlg, pconfig);
        form.setSchTopUrl(getPluginConfig(req).getPlugin(
                GSConstSchedule.PLUGIN_ID_SCHEDULE).getTopMenuInfo().getUrl());
    }

    /**
     * 週間スケジュールの表示日付の移動を行います
     * @param form アクションフォーム
     * @param moveDay 移動日数
     * @param today 今日へ移動
     */
    private void __doMoveDaysForWeek(SchMainForm form, int moveDay, boolean today) {
        String dspDate = "";
        if (today) {
            dspDate = new UDate().getDateString();
        } else {
            dspDate = NullDefault.getString(
                    form.getSchWeekDate(), new UDate().getDateString());
        }

        UDate udate = new UDate();
        udate.setDate(dspDate);
        udate.addDay(moveDay);
        form.setChangeDateFlg(1);
        form.setMoveKbn(1);
        form.setSchWeekDate(udate.getDateString());
    }
    /**
     * スケジュール登録画面へ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doScheduleAdd(ActionMapping map, SchMainForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        ActionForward forward = null;

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

//        Sch010Biz biz = new Sch010Biz();

        log__.debug("メイン：スケジュール登録");
        Sch040Form schForm = new Sch040Form();
        schForm.setCmd(GSConstSchedule.CMD_ADD);
        schForm.setDspMod(GSConstSchedule.DSP_MOD_MAIN);
        schForm.setSch010DspDate(form.getSchWeekDate());
        schForm.setSch010SelectDate(form.getSchSelectDate());

        schForm.setSch010SelectUsrSid(String.valueOf(sessionUsrSid));
        schForm.setSch010SelectUsrKbn(String.valueOf(GSConstSchedule.USER_KBN_USER));

        schForm.setSchWeekDate(form.getSchWeekDate());
        req.setAttribute("sch040Form", schForm);
        forward = map.findForward("sch040");
        return forward;
    }
    /**
     * スケジュール編集画面へ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doScheduleEdit(ActionMapping map, SchMainForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        ActionForward forward = null;

        RequestModel reqMdl = getRequestModel(req);
        Sch010Biz biz = new Sch010Biz(reqMdl);
        if (biz.isEditOk(Integer.parseInt(form.getSchSelectSchSid()), reqMdl, con, false)) {
            log__.debug("メイン：スケジュール編集");
            Sch040Form schForm = new Sch040Form();
            schForm.setCmd(GSConstSchedule.CMD_EDIT);
            schForm.setDspMod(GSConstSchedule.DSP_MOD_MAIN);
            schForm.setSch010DspDate(form.getSchWeekDate());
            schForm.setSch010SelectDate(form.getSchSelectDate());
            schForm.setSch010SchSid(form.getSchSelectSchSid());
            schForm.setSch010SelectUsrSid(form.getSchSelectUsrSid());
            schForm.setSch010SelectUsrKbn(form.getSchSelectUsrKbn());
            schForm.setSchWeekDate(form.getSchWeekDate());
//            schForm.setSchDailyDate(form.getSchDailyDate());
            req.setAttribute("sch040Form", schForm);
            forward = map.findForward("sch040");
        } else {
            log__.debug("メイン：スケジュール閲覧");
            Sch040knForm schForm = new Sch040knForm();
            schForm.setCmd(GSConstSchedule.CMD_EDIT);
            schForm.setDspMod(GSConstSchedule.DSP_MOD_MAIN);
            schForm.setSch010DspDate(form.getSchWeekDate());
            schForm.setSch010SelectDate(form.getSchSelectDate());
            schForm.setSch010SchSid(form.getSchSelectSchSid());
            schForm.setSch010SelectUsrSid(form.getSchSelectUsrSid());
            schForm.setSch010SelectUsrKbn(form.getSchSelectUsrKbn());
            schForm.setSchWeekDate(form.getSchWeekDate());
//            schForm.setSchDailyDate(form.getSchDailyDate());
            req.setAttribute("sch040knForm", schForm);
            forward = map.findForward("sch040kn");
        }
        return forward;
    }

    /**
     * スケジュール月間画面へ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doScheduleMonth(ActionMapping map, SchMainForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        ActionForward forward = null;
        RequestModel reqMdl = getRequestModel(req);
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid(); //セッションユーザSID
        log__.debug("メイン：スケジュール月間");
        Sch020Form schForm = new Sch020Form();
        schForm.setCmd("month");
        schForm.setSch010DspDate(form.getSchWeekDate());
        schForm.setChangeDateFlg(form.getChangeDateFlg());
        req.setAttribute("sch020Form", schForm);
        forward = map.findForward("sch020");
        schForm.setSch010SelectUsrSid(String.valueOf(sessionUsrSid));
        schForm.setSch010SelectUsrKbn(
                String.valueOf(
                        GSConstSchedule.USER_KBN_USER));

        return forward;
    }
    /**
     * スケジュール日間画面へ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doScheduleDay(ActionMapping map, SchMainForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        ActionForward forward = null;

        //セッション情報を取得
        RequestModel reqMdl = getRequestModel(req);
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid(); //セッションユーザSID

        log__.debug("メイン：スケジュール日間");
        Sch030Form schForm = new Sch030Form();
        schForm.setCmd("day");
        schForm.setSch010DspDate(form.getSchSelectDate());
        schForm.setSch010FromMain(GSConstSchedule.FROM_MAIN);
        schForm.setSch010SelectUsrSid(String.valueOf(sessionUsrSid));
        schForm.setSch010SelectUsrKbn(
                String.valueOf(
                        GSConstSchedule.USER_KBN_USER));
        req.setAttribute("sch030Form", schForm);
        forward = map.findForward("sch030");

        return forward;
    }
    /**
     * スケジュール個人週間画面へ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doScheduleKojinWeek(ActionMapping map, SchMainForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        ActionForward forward = null;

        RequestModel reqMdl = getRequestModel(req);
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid(); //セッションユーザSID
//        Sch010Biz biz = new Sch010Biz();
        log__.debug("メイン：スケジュール個人週間");
        Sch200Form schForm = new Sch200Form();
        schForm.setCmd("month");
        schForm.setSch010DspDate(form.getSchWeekDate());
        schForm.setChangeDateFlg(form.getChangeDateFlg());
        schForm.setSch010FromMain(GSConstSchedule.FROM_MAIN);
        schForm.setSch010SelectUsrSid(String.valueOf(sessionUsrSid));
        schForm.setSch010SelectUsrKbn(
                String.valueOf(
                        GSConstSchedule.USER_KBN_USER));
        req.setAttribute("sch200Form", schForm);
        forward = map.findForward("sch200");

        return forward;
    }
    /**
     * スケジュール週間画面へ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doScheduleWeek(ActionMapping map, SchMainForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        ActionForward forward = null;

        RequestModel reqMdl = getRequestModel(req);
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid(); //セッションユーザSID
//        Sch010Biz biz = new Sch010Biz();
        log__.debug("メイン：スケジュール週間");
        Sch010Form schForm = new Sch010Form();
        schForm.setCmd("month");
        schForm.setSch010DspDate(form.getSchWeekDate());
        schForm.setChangeDateFlg(form.getChangeDateFlg());
        schForm.setSch010FromMain(GSConstSchedule.FROM_MAIN);
        schForm.setSch010SelectUsrSid(String.valueOf(sessionUsrSid));
        schForm.setSch010SelectUsrKbn(
                String.valueOf(
                        GSConstSchedule.USER_KBN_USER));
        req.setAttribute("sch010Form", schForm);
        forward = map.findForward("sch010");

        return forward;
    }

    /**
     * <br>[機  能] 簡易登録前チェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private void __doRegisterCheck(ActionMapping map,
            SchMainForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        log__.debug("簡易登録前のチェック処理");
        JSONObject jsonData = new JSONObject();
        jsonData = JSONObject.fromObject(form);
        SchEasyRegisterBiz serBiz = new SchEasyRegisterBiz();
        SchEasyRegisterModel serForm = serBiz.createChkMdl(req);
        //validateチェック
        ActionErrors errors = serForm.validateCheck(
                getRequestModel(req), con);

        if (errors != null && errors.size() > 0) {
            StringBuilder sb = new StringBuilder();
            MessageResources mres = getResources(req);
            Iterator it = errors.get();
            while (it.hasNext()) {
                ActionMessage error = (ActionMessage) it.next();
                sb.append(mres.getMessage(error.getKey(), error.getValues()));
                jsonData.element("errors", sb.toString());
            }
            jsonData.element("validateError", true);
            jsonData.element("success", false);
        } else {
            jsonData.element("success", true);
            jsonData.element("validateError", false);
            log__.debug("トークン生成処理");
            this.saveToken(req);
            Cookie cookie = new Cookie("token", "");
            HttpSession session = req.getSession();
            Object value = session.getAttribute(Globals.TRANSACTION_TOKEN_KEY);
            cookie.setValue(String.valueOf(value));
            cookie.setHttpOnly(true);
            res.addCookie(cookie);
            jsonData.element("token", value);
        }
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
     * <br>[機  能] 簡易登録 重複チェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private void __doRegisterDupCheck(ActionMapping map,
            SchMainForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        log__.debug("簡易登録 重複チェック処理");
        JSONObject jsonData = new JSONObject();
        jsonData = JSONObject.fromObject(form);
        SchEasyRegisterBiz serBiz = new SchEasyRegisterBiz();
        SchEasyRegisterModel serForm = serBiz.createChkMdl(req);
        //validateチェック
        ActionErrors errors = serForm.validateDupCheck(
                getRequestModel(req), con);

        boolean warningFlg = false;
        if (errors.isEmpty()) {
          //重複警告チェック
          warningFlg = serForm.warningCheck(getRequestModel(req), con);
        }
        if (errors != null && errors.size() > 0) {
            jsonData.element("validateError", true);
            jsonData.element("success", false);
            jsonData.element("warning", false);
        } else if (warningFlg) {
            jsonData.element("warning", true);
            jsonData.element("success", false);
            jsonData.element("validateError", false);
        } else {
            jsonData.element("success", true);
            jsonData.element("warning", false);
            jsonData.element("validateError", false);
        }
        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (IOException e) {
            log__.error("簡易登録の重複チェック時に例外発生", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] 簡易登録処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward アクションフォワード
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doRegister(ActionMapping map,
            SchMainForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, Exception {

        log__.debug("簡易登録処理");

        if (!isTokenValid(req, false)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);
        MlCountMtController cntCon = getCountMtController(req);
        SchEasyRegisterBiz serBiz = new SchEasyRegisterBiz(con, reqMdl, cntCon);
        //アプリケーションRoot
        String appRootPath = getAppRootPath();
        //プラグイン設定
        PluginConfig plconf = getPluginConfig(req);
        PluginConfig pconfig = getPluginConfigForMain(plconf, con);
        CommonBiz cmnBiz = new CommonBiz();
        boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);
        SchEasyRegisterModel serMdl = form.getEasyRegister();
        serMdl.setSelectDate(NullDefault.getString(req.getParameter("selectDate"), ""));
        serMdl.setSelectSid(Integer.parseInt(req.getParameter("selectUser")));
        serMdl.setSelectKbn(Integer.parseInt(req.getParameter("selectKbn")));

        serBiz.insertScheduleDate(reqMdl, serMdl,
                appRootPath, plconf, smailPluginUseFlg, form.getSchWeekDate(),
                "-1", GSConstSchedule.DSP_MOD_WEEK);

        //ログ出力処理
        GsMessage gsMsg = new GsMessage();
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        String opLog = serBiz.logMessage(serMdl);
        schBiz.outPutLog(map, req, res, gsMsg.getMessage("cmn.entry"),
                GSConstLog.LEVEL_TRACE, opLog);
        return __doCompDsp(map, form, req, res, con);
    }

    /**
     * 登録・更新完了画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map, SchMainForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;
        ActionForward urlForward = null;

        Cmn999Form cmn999Form = new Cmn999Form();

        //スケジュール登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        if (form.getPtlSid() < 0) {
            urlForward = map.findForward("man001");
            cmn999Form.setUrlOK(urlForward.getPath());
        } else if (form.getPtlSid() == 0) {
            urlForward = map.findForward("man001");
            cmn999Form.setUrlOK(urlForward.getPath() + "?ptlMainSid=" + form.getPtlSid());
        } else {
            urlForward = map.findForward("ptl010");
            cmn999Form.setUrlOK(urlForward.getPath() + "?ptlMainSid=" + form.getPtlSid());
        }

        GsMessage gsMsg = new GsMessage();
        //スケジュール
        String textSchedule = gsMsg.getMessage(req, "schedule.108");
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                textSchedule));

        cmn999Form.addHiddenParam("schWeekDate", form.getSchWeekDate());
        cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
        cmn999Form.addHiddenParam("schSelectUsrSid", form.getSchSelectUsrSid());
        cmn999Form.addHiddenParam("schSelectUsrKbn", form.getSchSelectUsrKbn());
        cmn999Form.addHiddenParam("schSelectDate", form.getSchSelectDate());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}
