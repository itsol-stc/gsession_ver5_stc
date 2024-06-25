package jp.groupsession.v2.sch.sch010;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrInoutModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.GSValidateSchedule;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.biz.SchEasyRegisterBiz;
import jp.groupsession.v2.sch.biz.SchErrorMessage;
import jp.groupsession.v2.sch.biz.SchUserGroupSelectInitBiz;
import jp.groupsession.v2.sch.model.SchEasyRegisterModel;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.sch.pdf.SchSyuPdfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml010.Sml010Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;

/**
 * <br>[機  能] スケジュール 週間画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch010Action extends AbstractScheduleAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch010Action.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "sch010";

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("pdf")) {
            log__.debug("PDFファイルダウンロード");
            return true;
        }
        return false;
    }

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

        log__.debug("START_SCH010");
        ActionForward forward = null;
        Sch010Form thisForm = (Sch010Form) form;
        __setCanUsePluginFlg(thisForm, req, con);
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        if (cmd.equals("kojin")) {
            //個人週間スケジュール
            forward = map.findForward("kojin");
        } else if (cmd.equals("day")) {
            //日間スケジュール
            forward = map.findForward("day");
        } else if (cmd.equals("month")) {
            //月間スケジュール
            forward = map.findForward("month");
        } else if (cmd.equals("list")) {
            //一覧スケジュール
            forward = map.findForward("list");
        } else if (cmd.equals("add")) {
            //スケジュール追加
            forward = map.findForward("add");
        } else if (cmd.equals("edit")) {
            con.setAutoCommit(true);
            //スケジュール修正・閲覧
            //編集権限チェック
            RequestModel reqMdl = getRequestModel(req);
            Sch010Biz biz = new Sch010Biz(reqMdl,
                    getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req)));
            if (biz.isEditOk(Integer.parseInt(thisForm.getSch010SchSid()), reqMdl, con, false)) {
                forward = map.findForward("edit");
            } else {
                forward = map.findForward("dsp");
            }

        } else if (cmd.equals("today")) {
            //今日ボタン
            __doMoveDays(thisForm, 0, true);
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_rd")) {
            //次日移動
            __doMoveDays(thisForm, 1, false);
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_ld")) {
            //前日移動
            __doMoveDays(thisForm, -1, false);
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_rw")) {
            //次週移動
            __doMoveDays(thisForm, 7, false);
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("move_lw")) {
            //前週移動
            __doMoveDays(thisForm, -7, false);
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("msg")) {
            //ショートメール
            __doCreateMsg(map, thisForm, req, res, con);
            forward = map.findForward("msg");
        } else if (cmd.equals("ktool")) {
            //管理者ツール
            forward = map.findForward("ktool");
        } else if (cmd.equals("pset")) {
            //個人設定
            forward = map.findForward("pset");
        } else if (cmd.equals("sch010Zaiseki")) {
            //在席
            __doZaiseki(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("sch010Fuzai")) {
            //不在
            __doFuzai(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("sch010Sonota")) {
            //その他
            __doSonota(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("search")) {
            //一覧画面へ
            forward = map.findForward("list");
        } else if (cmd.equals("reload")) {
            //再読込
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("import")) {
            //スケジュールインポート
            forward = map.findForward("imp");
        } else if (cmd.equals("pdf")) {
            //スケジュールPDF出力
            log__.debug("週間スケジュールＰＤＦファイルダウンロード");
            __doInit(map, thisForm, req, res, con);
            forward = __doDownLoadPdf(map, thisForm, req, res, con);
        } else if (cmd.equals("registerChk")) {
            //簡易登録チェック
            log__.debug("簡易登録チェック");
            __doRegisterCheck(map, thisForm, req, res, con);
        } else if (cmd.equals("registerDupChk")) {
            //簡易登録 重複チェック
            log__.debug("簡易登録チェック");
            __doRegisterDupCheck(map, thisForm, req, res, con);
        } else if (cmd.equals("easyRegister")) {
            //簡易登録
            log__.debug("簡易登録");
            forward = __doRegister(map, thisForm, req, res, con);
            __doInit(map, thisForm, req, res, con);
        } else if (cmd.equals("sch010IkkatuTouroku")) {
            //一括登録
            forward = __doIkkatsuEntry(map, thisForm, req, res, con);
        } else if (cmd.equals("sch010IkkatuRemove")) {
            //一括登録削除
            __doIkkatsuRemove(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else if (cmd.equals("sch010IkkatuList")) {
            //一括選択リスト取得
            __doIkkatsuList(map, thisForm, req, res, con);
        } else if (cmd.equals("040IkkatuFinish")) {
            thisForm.setSchIkkatsuViewMode(0);
            thisForm.setSchIkkatsuKakuninViewMode(0);
            thisForm.setSchIkkatuTorokuSaveKey(null);
            thisForm.setSchIkkatuTorokuKey(null);
            //スケジュール週間初期表示
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        } else {
            //スケジュール週間初期表示
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        }
        log__.debug("END_SCH010");
        return forward;
    }

    /**
     * <br>初期表示処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doInit(
            ActionMapping map,
            Sch010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException {
        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);
        Sch010Biz biz = new Sch010Biz(reqMdl,
                getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req)));

        Sch010ParamModel paramMdl = new Sch010ParamModel();
        paramMdl.setParam(form);

        String selectUsrSidStr = null;
        String selectGrpSidStr = paramMdl.getSch010DspGpSid();
        if (StringUtil.isNullZeroString(selectGrpSidStr)) {
            //グループ選択なし、ユーザ指定あり
            selectUsrSidStr = paramMdl.getSch010SelectUsrSid();
        }

        SchUserGroupSelectInitBiz selectInit = new SchUserGroupSelectInitBiz(reqMdl,
                con,
                selectUsrSidStr,
                selectGrpSidStr,
                null);

        selectInit.initGroup();
        SchErrorMessage emsg = selectInit.getErrorMsg();

        if (emsg != null) {
            ActionErrors errs = new ActionErrors();
            StrutsUtil.addMessage(errs, emsg.createMessage(), emsg.getEmsgKey());
            addErrors(req, errs);
        }
        paramMdl.setSch010DspGpSid(selectInit.getGrpSidStr());
        //グループコンボのラベルを取得する。
        List<SchLabelValueModel> groupLabel = selectInit.getBaseGrpLabelList();
        paramMdl.setSch010GpLabelList(groupLabel);

        biz.getInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
    }

    /**
     * <br>在席にする処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doZaiseki(ActionMapping map, Sch010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException {
        boolean commitFlg = false;
        con.setAutoCommit(false);

        UDate now = new UDate();
        BaseUserModel umodel = getSessionUserModel(req);
        CmnUsrInoutModel param = new CmnUsrInoutModel();

        int uid = NullDefault.getInt(form.getSch010SelectUsrSid(), -1);
        if (uid != -1) {
            param.setUioSid(uid);
            param.setUioStatus(GSConst.UIOSTS_IN);
            param.setUioBiko(null);
            param.setUioAuid(umodel.getUsrsid());
            param.setUioAdate(now);
            param.setUioEuid(umodel.getUsrsid());
            param.setUioEdate(now);
            try {
                ZsjCommonBiz zbiz = new ZsjCommonBiz(getRequestModel(req));
                zbiz.updateZskStatus(con, param);
                commitFlg = true;
            } catch (SQLException e) {
                log__.error("在席状況の更新に失敗しました。");
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }
        form.setSch010SelectUsrSid("");
        __doInit(map, form, req, res, con);
    }

    /**
     * <br>不在にする処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doFuzai(ActionMapping map, Sch010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException {
        boolean commitFlg = false;
        con.setAutoCommit(false);

        UDate now = new UDate();
        BaseUserModel umodel = getSessionUserModel(req);
        CmnUsrInoutModel param = new CmnUsrInoutModel();
        int uid = NullDefault.getInt(form.getSch010SelectUsrSid(), -1);
        if (uid != -1) {
            param.setUioSid(uid);
            param.setUioStatus(GSConst.UIOSTS_LEAVE);
            param.setUioBiko(null);
            param.setUioAuid(umodel.getUsrsid());
            param.setUioAdate(now);
            param.setUioEuid(umodel.getUsrsid());
            param.setUioEdate(now);
            try {
                ZsjCommonBiz zbiz = new ZsjCommonBiz(getRequestModel(req));
                zbiz.updateZskStatus(con, param);
                commitFlg = true;
            } catch (SQLException e) {
                log__.error("在席状況の更新に失敗しました。");
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }

        form.setSch010SelectUsrSid("");
        __doInit(map, form, req, res, con);
    }

    /**
     * <br>その他にする処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doSonota(ActionMapping map, Sch010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException {
        boolean commitFlg = false;
        con.setAutoCommit(false);

        UDate now = new UDate();
        BaseUserModel umodel = getSessionUserModel(req);
        CmnUsrInoutModel param = new CmnUsrInoutModel();

        int uid = NullDefault.getInt(form.getSch010SelectUsrSid(), -1);
        if (uid != -1) {
            param.setUioSid(uid);
            param.setUioStatus(GSConst.UIOSTS_ETC);
            param.setUioBiko(null);
            param.setUioAuid(umodel.getUsrsid());
            param.setUioAdate(now);
            param.setUioEuid(umodel.getUsrsid());
            param.setUioEdate(now);
            try {
                ZsjCommonBiz zbiz = new ZsjCommonBiz(getRequestModel(req));
                zbiz.updateZskStatus(con, param);
                commitFlg = true;
            } catch (SQLException e) {
                log__.error("在席状況の更新に失敗しました。");
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }

        form.setSch010SelectUsrSid("");
        __doInit(map, form, req, res, con);
    }


    /**
     * <br>表示日付の移動を行います
     * @param form アクションフォーム
     * @param moveDay 移動日数
     * @param today 今日へ移動
     * @throws SQLException SQL実行例外
     */
    private void __doMoveDays(
            Sch010Form form,
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
     * <br>メッセンジャー処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doCreateMsg(ActionMapping map, Sch010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException {

        log__.debug("週間==>メッセンジャー");
        //パラメータ取得
        String selectUserSid = form.getSch010SelectUsrSid();

        Sml010Form msgForm = new Sml010Form();
        msgForm.setSml010scriptFlg(GSConstSmail.SCRIPT_FIG_TRUE);
        msgForm.setSml010scriptKbn(GSConstSmail.SCRIPT_CREATE_MAIL);
        msgForm.setSml010scriptSelUsrSid(selectUserSid);

        req.setAttribute("sml010Form", msgForm);

    }

    /**
     * 在席管理・ショートメールプラグインが利用可能かフォームへ設定する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setCanUsePluginFlg(Sch010Form form, HttpServletRequest req, Connection con)
            throws SQLException {
        //プラグイン設定を取得する
        PluginConfig pconfig
        = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        CommonBiz cmnBiz = new CommonBiz();
        //在席管理は利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_ZAISEKI, pconfig)) {
            form.setZaisekiUseOk(GSConstSchedule.PLUGIN_USE);
        } else {
            form.setZaisekiUseOk(GSConstSchedule.PLUGIN_NOT_USE);
        }
        //ショートメールは利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_SMAIL, pconfig)) {
            form.setSmailUseOk(GSConstSchedule.PLUGIN_USE);
        } else {
            form.setSmailUseOk(GSConstSchedule.PLUGIN_NOT_USE);
        }
    }

    /**
     * <br>[機  能] PDFファイルダウンロード処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doDownLoadPdf(ActionMapping map,
            Sch010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, Exception {

        log__.debug("週間スケジュールＰＤＦファイルダウンロード処理");
        ActionForward forward = null;

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);

        //ディレクトリの作成
        File tmpDir = new File(tempDir);
        tmpDir.mkdirs();
        forward = __createPdf(map, form, req, res, con, tempDir);

        return forward;
    }

    /**
     * <br>[機  能] PDFファイルダウンロード処理を実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param outDir 出力先ディレクトリ
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     * @throws IOException ファイルの書き出しに失敗
     * @throws IOToolsException テンポラリディレクトリの削除に失敗
     * @throws TempFileException 添付ファイル情報の取得に失敗
     * @throws Exception 実行例外
     */
    private ActionForward __createPdf(ActionMapping map, Sch010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String outDir)
                    throws SQLException, IOException,
                    IOToolsException, TempFileException, Exception {

        log__.debug("ファイルダウンロード処理(PDF)");

        String dateStr = form.getSch010DspDate();
        //dateStrの半角数字チェック処理
        if (!ValidateUtil.isNumber(dateStr)) {
            return getSubmitErrorPage(map, req);
        }
        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();
        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String outTempDir = temp.getTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);

        RequestModel reqMdl = getRequestModel(req);
        Sch010Biz biz = new Sch010Biz(reqMdl);

        String tmpFileName =  dateStr.concat(GSConstCommon.ENDSTR_SAVEFILE);
        Sch010ParamModel paramMdl = new Sch010ParamModel();
        paramMdl.setParam(form);
        SchSyuPdfModel pdfMdl = biz.createSchSyuPdf(
                paramMdl, con, appRootPath, outTempDir, tmpFileName);
        paramMdl.setFormData(form);

        String outBookName = pdfMdl.getFileName();
        String outFilePath = IOTools.setEndPathChar(outTempDir)
                + tmpFileName;
        TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);
        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);
        GsMessage gsMsg = new GsMessage();
        String downloadPdf = gsMsg.getMessage(req, "cmn.pdf");
        //ログ出力処理
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        String logCode = "週間 PDF出力 grpSid ：" + form.getSch010DspGpSid();
        String dspName = gsMsg.getMessage("schedule.sch010.1");
        schBiz.outPutLogNoDspName(map, req, res, downloadPdf,
                GSConstLog.LEVEL_INFO, outBookName, logCode, dspName);

        return null;
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
            Sch010Form form,
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
            jsonData.element("warning", false);
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
            log__.error("簡易登録の入力チェック時に例外発生", e);
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
            Sch010Form form,
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
     * <br>一括登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doIkkatsuEntry(
            ActionMapping map,
            Sch010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException {

        //一括登録対象チェック
        String[] ikkatsuKey = form.getSchIkkatuTorokuKey();
        if (ikkatsuKey == null || ikkatsuKey.length == 0) {
            GsMessage gsMsg = new GsMessage(getRequestModel(req));
            ActionMessage msg = new ActionMessage("error.select.required.text",
                                                gsMsg.getMessage("schedule.173"));
            ActionErrors errors = new ActionErrors();
            StrutsUtil.addMessage(errors, msg, "schIkkatuTorokuKey");

            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            return map.getInputForward();
        }

        //編集権限チェック
        int userSid = getSessionUserSid(req);
        int usrKbn = GSConstSchedule.USER_KBN_USER;
        int selectUsrSid = 0;
        for (String key : ikkatsuKey) {
            String usrGrpSid = key.substring(9);
            if (usrGrpSid.substring(0, 1).equals("G")) {
                selectUsrSid = Integer.parseInt(usrGrpSid.substring(1));
                usrKbn = GSConstSchedule.USER_KBN_GROUP;
            } else {
                selectUsrSid = Integer.parseInt(usrGrpSid);
                usrKbn = GSConstSchedule.USER_KBN_USER;
            }
            if (GSValidateSchedule.validateSchAccessUser(
                    con, usrKbn, selectUsrSid, userSid)) {
                return getSubmitErrorPage(map, req);
            }
        }

        return map.findForward("add");
    }

    /**
     * <br>一括登録削除処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doIkkatsuRemove(
            ActionMapping map,
            Sch010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException {

        String removeKey = form.getSchIkkatsuRemoveKey();
        if (!StringUtil.isNullZeroString(removeKey)) {
            String[] ikkatsuKey = form.getSchIkkatuTorokuKey();
            if (ikkatsuKey != null) {
                List<String> keyList = new ArrayList<String>();
                keyList.addAll(Arrays.asList(ikkatsuKey));
                if (keyList.contains(removeKey)) {
                    keyList.remove(keyList.indexOf(removeKey));
                    form.setSchIkkatuTorokuKey(keyList.toArray(new String[keyList.size()]));
                }
            }
        }

        __doInit(map, form, req, res, con);
    }
    
    /**
     * <br>一括選択リスト取得
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception
     */
    private void __doIkkatsuList(
            ActionMapping map,
            Sch010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        Sch010ParamModel paramMdl = new Sch010ParamModel();
        paramMdl.setParam(form);
        Sch010Biz biz = new Sch010Biz(reqMdl);
        biz.getIkkatsuList(paramMdl, reqMdl, con);
        paramMdl.setFormData(form);

        JSONObject jsonData = new JSONObject();
        jsonData = JSONObject.fromObject(form);
        jsonData.element("success", true);
        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(草稿データ)");
            throw e;
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
            Sch010Form form,
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
                appRootPath, plconf, smailPluginUseFlg, form.getSch010DspDate(),
                form.getSch010DspGpSid(), form.getDspMod());

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
    private ActionForward __doCompDsp(ActionMapping map, Sch010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //スケジュール登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

       urlForward = map.findForward("register");

        GsMessage gsMsg = new GsMessage();
        //スケジュール
        String textSchedule = gsMsg.getMessage(req, "schedule.108");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                textSchedule));

        cmn999Form.addHiddenParam("dspMod", form.getDspMod());
        cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
        cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
        cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
        cmn999Form.addHiddenParam("sch010SelectUsrSid", form.getSch010SelectUsrSid());
        cmn999Form.addHiddenParam("sch010SelectUsrKbn", form.getSch010SelectUsrKbn());
        cmn999Form.addHiddenParam("sch010SelectDate", form.getSch010SelectDate());
        cmn999Form.addHiddenParam("sch010searchWord", form.getSch010searchWord());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.getInputForward();
        return forward;
    }
}