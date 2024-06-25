package jp.groupsession.v2.sch.sch200;

import java.io.PrintWriter;
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
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.biz.SchErrorMessage;
import jp.groupsession.v2.sch.biz.SchUserGroupSelectInitBiz;
import jp.groupsession.v2.sch.dao.SchPriConfDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.model.SchRepeatKbnModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュール 個人週間画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch200Action extends AbstractScheduleAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch200Action.class);

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

        log__.debug("START_SCH200");
        ActionForward forward = null;
        Sch200Form uform = (Sch200Form) form;
        RequestModel reqMdl = getRequestModel(req);

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        uform.setCmd(NullDefault.getString(uform.getCmd(), ""));
        if (cmd.equals("day")) {
            //日間スケジュール
            forward = map.findForward("200_day");
        } else if (cmd.equals("week")) {
            //週間スケジュール
            forward = map.findForward("200_week");
        } else if (cmd.equals("month")) {
            //月間スケジュール
            forward = map.findForward("200_month");
        } else if (cmd.equals("add")) {
            //スケジュール追加
            forward = map.findForward("200_add");
            log__.debug("取得した日付＝" + uform.getSch010SelectDate());
        } else if (cmd.equals("edit")) {
            //スケジュール修正・閲覧
            con.setAutoCommit(true);
            //編集権限チェック
            Sch010Biz biz = new Sch010Biz(reqMdl);
            if (biz.isEditOk(Integer.parseInt(uform.getSch010SchSid()), reqMdl, con, false)) {
                forward = map.findForward("200_edit");
            } else {
                forward = map.findForward("200_dsp");
            }
        } else if (cmd.equals("drop") || cmd.equals("resize")) {
            forward = __doCheck(map, uform, req, res, con);
        } else if (cmd.equals("list")) {
            //スケジュール一覧
            forward = map.findForward("200_list");
        } else if (cmd.equals("search")) {
            //一覧画面へ
            forward = map.findForward("200_list");
        } else if (cmd.equals("today")) {
            //今日ボタン
            __doMoveMonth(uform, 0, true);
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_rw")) {
            //次週移動
            __doMoveDays(uform, 7, false);
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_lw")) {
            //前週移動
            __doMoveDays(uform, -7, false);
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_rd")) {
            //次日移動
            __doMoveDays(uform, 1, false);
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_ld")) {
            //前日移動
            __doMoveDays(uform, -1, false);
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("ktool")) {
            //管理者ツール
            forward = map.findForward("ktool");
        } else if (cmd.equals("pset")) {
            //個人設定
            forward = map.findForward("pset");
        } else if (cmd.equals("200_ok")) {
            //スケジュール登録
            forward = __doOk(map, uform, req, res, con);
        } else if (cmd.equals("getSchData")) {
            //スケジュールデータ取得
            forward = __doSchData(map, uform, req, res, con);
        } else if (cmd.equals("repetCheck")) {
            //スケジュール画面変更時重複チェック
            __doRepeatCheck(map, uform, req, res, con);
            return null;
        } else if (cmd.equals("existCheck")) {
            //スケジュール存在チェック
            __doExistCheck(map, uform, req, res, con);
            return null;
        } else {
            if (cmd.equals("chgroup")) {
                uform.setSch200ChGroupFlg(1);
            }
            //スケジュール月間表示
            __doInit(map, uform, req, res, con);
            forward = map.getInputForward();
        }
        log__.debug("END_SCH200");
        return forward;
    }

    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map, Sch200Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        ActionForward forward = null;
        con.setAutoCommit(true);
        MlCountMtController cntCon = getCountMtController(req);
        Sch200Biz biz = new Sch200Biz(con, cntCon, reqMdl);

        Sch200ParamModel paramMdl = new Sch200ParamModel();
        paramMdl.setParam(form);

        String selectUsrSidStr = NullDefault.getString(
                paramMdl.getSch100SelectUsrSid(),
                paramMdl.getSch010SelectUsrSid()
                );
        String selectGrpSidStr = paramMdl.getSch010DspGpSid();
        SchUserGroupSelectInitBiz selectInit = new SchUserGroupSelectInitBiz(reqMdl,
                con,
                selectUsrSidStr,
                selectGrpSidStr,
                null);

        selectInit.initUserAndGroup();
        SchErrorMessage emsg = selectInit.getErrorMsg();

        if (emsg != null) {
            ActionErrors errs = new ActionErrors();
            StrutsUtil.addMessage(errs, emsg.createMessage(), emsg.getEmsgKey());
            addErrors(req, errs);
        }

        String initGrpSidStr = selectInit.getGrpSidStr();
        String initUsrSidStr = selectInit.getUsrSidStr();

        //表示グループの初期値 = 表示グループ かつ 共有範囲 = 所属グループ内のみ共有可の場合
        //表示グループ/ユーザをグループ: デフォルトグループ、ユーザ:セッションユーザに設定する
        if (SchCommonBiz.isDspListSid(initGrpSidStr)
        && StringUtil.isNullZeroString(paramMdl.getSch010DspGpSid())) {
            SchCommonBiz schCmnBiz = new SchCommonBiz(reqMdl);
            SchAdmConfModel adminConf = schCmnBiz.getAdmConfModel(con);
            if (adminConf.getSadCrange() == GSConstSchedule.CRANGE_SHARE_GROUP) {
                int sessionUsrSid = getSessionUserSid(req);
                boolean userExistFlg = false;
                if (selectInit.getBaseUsrLabelList().size() > 0) {
                    userExistFlg = true;
                }
                initUsrSidStr = String.valueOf(sessionUsrSid);
                GroupBiz grpBiz = new GroupBiz();
                if (!userExistFlg) {
                    initGrpSidStr = String.valueOf(grpBiz.getDefaultGroupSid(sessionUsrSid, con));
                    selectInit = new SchUserGroupSelectInitBiz(reqMdl,
                            con,
                            initUsrSidStr,
                            initGrpSidStr,
                            null);

                    selectInit.initUserAndGroup();

                }
            }

        }

        paramMdl.setSch010DspGpSid(initGrpSidStr);
        paramMdl.setSch010SelectUsrSid(initUsrSidStr);

        //グループコンボのラベルを取得する。
        paramMdl.setSch010GpLabelList(selectInit.getBaseGrpLabelList());

        //ユーザコンボのラベルを取得する
        List<UsrLabelValueBean> userLabel = selectInit.getBaseUsrLabelList();
        List<UsrLabelValueBean> userOnlyLabel = new ArrayList<UsrLabelValueBean>();
        for (UsrLabelValueBean label : userLabel) {
            //ユーザコンボのラベルにグループが存在する場合、除外ずる
            if (!label.getValue().startsWith("G")) {
                userOnlyLabel.add(label);
            }
        }
        paramMdl.setSch200UsrLabelList(userOnlyLabel);

        biz.getInitData(paramMdl);
        paramMdl.setFormData(form);
        paramMdl.setIniDsp(GSConstSchedule.NOT_INIT_FLG);

        con.setAutoCommit(false);
        return forward;
    }

    /**
     * <br>表示日付の移動を行います
     * @param form アクションフォーム
     * @param moveMonth 移動月数
     * @param today 今日へ移動
     */
    private void __doMoveMonth(Sch200Form form, int moveMonth, boolean today) {
        String dspDate = "";
        if (today) {
            dspDate = new UDate().getDateString();
        } else {
            dspDate = NullDefault.getString(
                    form.getSch010DspDate(), new UDate().getDateString());
        }

        UDate udate = new UDate();
        udate.setDate(dspDate);
        UDate rsDate = udate.cloneUDate();
        rsDate = UDateUtil.addMonthLastDay(rsDate, moveMonth);

        int iSYear = rsDate.getYear();
        int iSMonth = rsDate.getMonth();
        int iSDay = udate.getIntDay();
        rsDate.setDay(udate.getIntDay());
        //日付論理エラーの場合、月末日を設定
        if (rsDate.getYear() != iSYear
         || rsDate.getMonth() != iSMonth
         || rsDate.getIntDay() != iSDay) {
            rsDate = udate.cloneUDate();
            rsDate = UDateUtil.addMonthLastDay(rsDate, moveMonth);
        }
        form.setSch010DspDate(rsDate.getDateString());
    }
    /**
     * <br>表示日付の移動を行います
     * @param form アクションフォーム
     * @param moveDay 移動日数
     * @param today 今日へ移動
     * @throws SQLException SQL実行例外
     */
    private void __doMoveDays(
            Sch200Form form,
            int moveDay,
            boolean today) throws SQLException {

        String dspDate = "";
        if (today) {
            dspDate = new UDate().getDateString();
        } else {
            dspDate = NullDefault.getString(
                    form.getSch010DspDate(), new UDate().getDateString());
        }

        UDate udate = new UDate();
        udate.setDate(dspDate);
        udate.addDay(moveDay);
        form.setChangeDateFlg(1);
        form.setSch010DspDate(udate.getDateString());
    }

    /**
     * <br>編集権限チェック
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doCheck(ActionMapping map, Sch200Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        con.setAutoCommit(true);
        //編集権限チェック
        RequestModel reqMdl = getRequestModel(req);
        Sch010Biz biz = new Sch010Biz(reqMdl);
        if (biz.isEditOk(Integer.parseInt(form.getSch010SchSid()), reqMdl, con, false)) {
            form.setCmd(NullDefault.getString(req.getParameter("CMD"), ""));
            if (form.getSch200Cancel() == 0) {
                __doOk(map, form, req, res, con);
            }
        } else {
            //編集権限なし
            ActionErrors errors = new ActionErrors();
            errors.add("sch200EditAuth", new ActionMessage("error.schedule.auth"));
            addErrors(req, errors);
        }
        __doInit(map, form, req, res, con);
        return map.getInputForward();
    }

    /**
     * <br>ドラッグアンドドロップ処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doOk(ActionMapping map, Sch200Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        ActionForward forward = null;
        RequestModel reqMdl = getRequestModel(req);

//      管理者設定を取得
        SchCommonBiz biz = new SchCommonBiz(reqMdl);
        SchAdmConfModel adminConf = biz.getAdmConfModel(con);
        MlCountMtController cntCon = getCountMtController(req);
        Sch200Biz sch200biz = new Sch200Biz(con, cntCon, reqMdl);
        //スケジュール存在チェック
        ScheduleSearchModel schMdl = sch200biz.getSchData(
                Integer.parseInt(form.getSch010SchSid()), adminConf, con);

        if (schMdl != null) {
            forward = __doCommit(map, form, req, res, con, schMdl);

            GsMessage gsMsg = new GsMessage();
            /** メッセージ 登録 **/
            String entry = gsMsg.getMessage(req, "cmn.entry");
            /** メッセージ 変更 **/
            String change = gsMsg.getMessage(req, "cmn.change");

            //ログ出力処理
            SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
            String opCode = "";
            if (form.getCmd().equals(GSConstSchedule.CMD_ADD)) {
                opCode = entry;
            } else if (form.getCmd().equals(GSConstSchedule.CMD_EDIT)) {
                opCode = change;
            }
            opCode = change;
            String value = "";
            if (form.getCmd().equals("drop")) {
                value += gsMsg.getMessage("schedule.sch200.27");
            } else if (form.getCmd().equals("resize")) {
                value += gsMsg.getMessage("schedule.sch200.28");
            }
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.title") + "] ";
            value += schMdl.getScdTitle();
            String dspName = gsMsg.getMessage("mainscreeninfo.schedule.schmain");
            schBiz.outPutLogNoDspName(
              map, req, res, opCode, GSConstLog.LEVEL_TRACE, value, null, dspName);
        }
        return forward;
    }
    /**
     * <br>処理モードによって登録・修正処理を行う
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param oldMdl 編集前データ
     * @return ActionForward 画面遷移
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Sch200Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con,
            ScheduleSearchModel oldMdl)
    throws Exception {
        ActionForward forward = null;

        //セッション情報を取得
        RequestModel reqMdl = getRequestModel(req);
        BaseUserModel usModel = getSessionUserModel(req);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        //アプリケーションRoot
        String appRootPath = getAppRootPath();
        //プラグイン設定
        PluginConfig plconf = getPluginConfig(req);

        MlCountMtController cntCon = getCountMtController(req);
        Sch200Biz biz = new Sch200Biz(con, cntCon, reqMdl);
        boolean commitFlg = false;
        con.setAutoCommit(false);

        PluginConfig pconfig = getPluginConfigForMain(plconf, con);
        CommonBiz cmnBiz = new CommonBiz();
        boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);

        try {
            Sch200ParamModel paramMdl = new Sch200ParamModel();
            paramMdl.setParam(form);
            //会社情報の設定
            biz.setCompanyData(paramMdl, con, sessionUsrSid);
            //登録
            biz.updateScheduleDate(paramMdl,
                        sessionUsrSid, appRootPath, plconf, smailPluginUseFlg, oldMdl);
            paramMdl.setFormData(form);

            ActionErrors errors = form.getSch200ActionErrors();
            if (errors != null) {
                if (errors.size() > 0) {
                    log__.debug("入力エラー");
                    addErrors(req, errors);
                }
            }

            con.commit();
            commitFlg = true;
        } catch (Exception e) {
            log__.error("スケジュール登録に失敗しました" + e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }
        //forward = __doCompDsp(map, form, req, res, con);
        forward = map.getInputForward();
        return forward;
    }
    /**
     * <br>[機  能] スケジュールデータ取得
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doSchData(ActionMapping map, Sch200Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Sch200Biz biz = new Sch200Biz(reqMdl);

        Sch200ParamModel paramMdl = new Sch200ParamModel();
        paramMdl.setParam(form);
        biz.setJsonData(paramMdl, res, con);
        paramMdl.setFormData(form);

        return null;
    }
    /**
     * <br>重複チェック
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行時例外
     */
    private void __doRepeatCheck(ActionMapping map, Sch200Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {
        int checkFlg = 0;
        //セッション情報を取得
        RequestModel reqMdl = getRequestModel(req);
        BaseUserModel usModel = getSessionUserModel(req);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        int usrSid = NullDefault.getInt(form.getSch010SelectUsrSid(), sessionUsrSid);
        //個人設定を取得する。
        SchPriConfDao priConfDao = new SchPriConfDao(con);
        SchPriConfModel priModel = priConfDao.select(Integer.valueOf(usrSid));

        Sch200Biz sch200biz = new Sch200Biz(con, reqMdl);
        //管理者設定を取得
        SchCommonBiz cmnBiz = new SchCommonBiz(reqMdl);
        SchAdmConfModel adminConf = cmnBiz.getAdmConfModel(con);
        //編集前スケジュール取得
        ScheduleSearchModel oldMdl =
            sch200biz.getSchData(Integer.parseInt(form.getSch010SchSid()), adminConf, con);

        SchCommonBiz schBiz = new SchCommonBiz(getRequestModel(req));
        SchRepeatKbnModel repertMdl
            = schBiz.getRepertKbn(con, priModel, usrSid);


        Sch200ParamModel paramMdl = new Sch200ParamModel();
        paramMdl.setParam(form);

        if (oldMdl == null) {
            checkFlg = -1;
        } else if ((sch200biz.__doDupWarningCheck(
                paramMdl, reqMdl, con, usrSid, sessionUsrSid, oldMdl) != null
                && repertMdl.getRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG
                && repertMdl.getRepeatMyKbn() == GSConstSchedule.SCH_REPEAT_MY_KBN_NG)
                || (paramMdl.getSch200CantAddUserFlg() == 1)) {
            checkFlg = 1;
        } else if ((sch200biz
                .__doDupWarningCheck(paramMdl, reqMdl, con, usrSid, sessionUsrSid, oldMdl) != null
                && repertMdl.getRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG
                && repertMdl.getRepeatMyKbn() == GSConstSchedule.SCH_REPEAT_MY_KBN_OK
                && sessionUsrSid == usrSid)
                || (paramMdl.getSch200WarningAddUserFlg() == 1)
                ) {
            checkFlg = 3;
        } else if (sch200biz
                .__doDupWarningCheck(paramMdl, reqMdl, con, usrSid, sessionUsrSid, oldMdl) != null
                && repertMdl.getRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG
                && repertMdl.getRepeatMyKbn() == GSConstSchedule.SCH_REPEAT_MY_KBN_OK
                && sessionUsrSid != usrSid) {
            checkFlg = 1;
        } else if ((sch200biz
                .__doDupWarningCheck(paramMdl, reqMdl, con, usrSid, sessionUsrSid, oldMdl) != null
                && repertMdl.getRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_WARNING)
                || (paramMdl.getSch200WarningAddUserFlg() == 1)) {
            checkFlg = 2;
        }

        paramMdl.setFormData(form);

        PrintWriter writer = null;
        try {
            res.setContentType("text/txt; charset=UTF-8");
            writer = res.getWriter();
            writer.println("{\"result\" : \"" + checkFlg + "\"}");
            writer.flush();
        } catch (Exception e) {
            log__.debug("重複チェック失敗");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    /**
     * <br>スケジュール存在チェック
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行時例外
     */
    private void __doExistCheck(ActionMapping map, Sch200Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {
        int checkFlg = 0;

        RequestModel reqMdl = getRequestModel(req);
        Sch200Biz sch200biz = new Sch200Biz(con, reqMdl);
        //管理者設定を取得
        SchCommonBiz cmnBiz = new SchCommonBiz(reqMdl);
        SchAdmConfModel adminConf = cmnBiz.getAdmConfModel(con);
        //編集前スケジュール取得
        ScheduleSearchModel oldMdl =
            sch200biz.getSchData(Integer.parseInt(form.getSch010SchSid()), adminConf, con);

        if (oldMdl == null) {
            checkFlg = -1;
        }
        PrintWriter writer = null;
        try {
            res.setContentType("text/txt; charset=UTF-8");
            writer = res.getWriter();
            writer.println("{\"result\" : \"" + checkFlg + "\"}");
            writer.flush();
        } catch (Exception e) {
            log__.debug("重複チェック失敗");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
