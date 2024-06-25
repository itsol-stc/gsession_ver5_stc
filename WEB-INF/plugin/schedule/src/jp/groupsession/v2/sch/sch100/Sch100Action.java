package jp.groupsession.v2.sch.sch100;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

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
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.biz.SchErrorMessage;
import jp.groupsession.v2.sch.biz.SchUserGroupSelectInitBiz;
import jp.groupsession.v2.sch.pdf.SchListPdfModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch100Action extends AbstractScheduleAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch100Action.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "sch100";
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
        //ダウンロードフラグ
        String downLoadFlg = NullDefault.getString(req.getParameter("csvOut"), "");
        downLoadFlg = downLoadFlg.trim();

        if (cmd.equals("sch100export")) {
            log__.debug("CSVファイルダウンロード");
            return true;
        } else if (cmd.equals("pdf")) {
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

        log__.debug("START_SCH100");
        ActionForward forward = null;
        Sch100Form uform = (Sch100Form) form;
        RequestModel reqMdl = getRequestModel(req);

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD==>" + cmd);
        if (cmd.equals("100_back")) {
            //戻る
            forward = __doBack(map, uform, req, res, con);
        } else if (cmd.equals("day")) {
            //日間スケジュール
            forward = map.findForward("100_day");
        } else if (cmd.equals("week")) {
            //週間スケジュール
            forward = map.findForward("100_week");
        } else if (cmd.equals("month")) {
            //月間スケジュール
            forward = map.findForward("100_month");
        } else if (cmd.equals("kojin")) {
            //個人週間スケジュール
            forward = map.findForward("100_kojin");
        } else if (cmd.equals("edit")) {
            //スケジュール修正・閲覧
            con.setAutoCommit(true);
            //編集権限チェック
            Sch010Biz biz = new Sch010Biz(reqMdl);
            if (biz.isEditOk(Integer.parseInt(uform.getSch010SchSid()), reqMdl, con, false)) {
                forward = map.findForward("100_edit");
            } else {
                forward = map.findForward("100_dsp");
            }
        } else if (cmd.equals("ktool")) {
            //管理者ツール
            forward = map.findForward("ktool");
        } else if (cmd.equals("pset")) {
            //個人設定
            forward = map.findForward("pset");
        //ページコンボ変更
        } else if (cmd.equals("changeMode")) {
            log__.debug("ページコンボ変更");
            forward = __doChangeMode(map, uform, req, res, con);
        //左矢印押下
        } else if (cmd.equals("arrorw_left")) {
            log__.debug("左矢印ボタン押下");
            forward = __doPageMinus(map, uform, req, res, con);
        //右矢印押下
        } else if (cmd.equals("arrorw_right")) {
            log__.debug("右矢印ボタン押下");
            forward = __doPagePlus(map, uform, req, res, con);
        } else if (cmd.equals("sch100search")) {
            log__.debug("検索ボタン押下");
            __doSearch(map, uform, req, res, con, true);
            forward = map.getInputForward();
        } else if (cmd.equals("sch100export")) {
            log__.debug("エクスポートボタン押下");
            forward = __doDownLoad(map, uform, req, res, con);
        } else if (cmd.equals("pushDel")) {
            //削除ボタン
            forward = __doPushDelete(map, uform, req, res, con);

        } else if (cmd.equals("doDelSchData")) {
          //削除実行
            forward = __doDeleteComp(map, uform, req, res, con);
        } else if (cmd.equals("research")) {
            log__.debug("再表示");
            __doSearch(map, uform, req, res, con, false);
            forward = map.getInputForward();
        } else if (cmd.equals("import")) {
            //スケジュールインポート
            forward = map.findForward("100_imp");
        //PDF出力
        } else if (cmd.equals("pdf")) {
           log__.debug("ＰＤＦファイルダウンロード");
           forward = __doDownLoadPdf(map, uform, req, res, con);

       } else if (cmd.equals("040IkkatuFinish")) {
           uform.setSchIkkatsuViewMode(0);
           uform.setSchIkkatsuKakuninViewMode(0);
           uform.setSchIkkatuTorokuSaveKey(null);
           uform.setSchIkkatuTorokuKey(null);
           //スケジュール日間表示
           __doInit(map, uform, req, res, con);
           forward = map.getInputForward();

        } else {
            //検索対象
            Sch100Biz biz = new Sch100Biz(reqMdl);

            Sch100ParamModel paramMdl = new Sch100ParamModel();
            paramMdl.setParam(form);
            biz.setDefultSearchTarget(paramMdl);
            //タイトルカラー
            biz.setDefultBgcolor(paramMdl, con);
            //CSV出力項目
            biz.setDefultCsvOut(paramMdl);
            paramMdl.setFormData(form);

            //初期表示
            __doSearch(map, uform, req, res, con, true);
            forward = map.getInputForward();
        }
        log__.debug("END_SCH100");
        return forward;
    }

    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    private void __doInit(ActionMapping map, Sch100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        RequestModel reqMdl = getRequestModel(req);
        Sch100Biz biz = new Sch100Biz(reqMdl);

        Sch100ParamModel paramMdl = new Sch100ParamModel();
        paramMdl.setParam(form);


        String selectUsrSidStr = NullDefault.getString(
                paramMdl.getSch100SltUser(),
                paramMdl.getSch010SelectUsrSid()
                );
        String selectGrpSidStr = NullDefault.getString(
                paramMdl.getSch100SltGroup(),
                paramMdl.getSch010DspGpSid()
                );
        SchUserGroupSelectInitBiz selectInit = new SchUserGroupSelectInitBiz(reqMdl,
                con,
                selectUsrSidStr,
                selectGrpSidStr,
                biz);

        selectInit.initUserAndGroup();
        SchErrorMessage emsg = selectInit.getErrorMsg();

        if (emsg != null) {
            ActionErrors errs = new ActionErrors();
            StrutsUtil.addMessage(errs, emsg.createMessage(), emsg.getEmsgKey());
            addErrors(req, errs);
        }

        paramMdl.setSch010DspGpSid(selectInit.getGrpSidStr());
        paramMdl.setSch010SelectUsrSid(selectInit.getUsrSidStr());
        paramMdl.setSch100SltGroup(String.valueOf(paramMdl.getSch010DspGpSid()));
        paramMdl.setSch100SltUser(String.valueOf(paramMdl.getSch010SelectUsrSid()));
        //グループコンボのラベルを取得する。
        paramMdl.setSch100GroupLabel(selectInit.getBaseGrpLabelList());
        paramMdl.setSch100UserLabel(selectInit.getBaseUsrLabelList());

        biz.getInitData(paramMdl, con);
        paramMdl.setFormData(form);
    }

    /**
     * <br>検索処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param save パラメータをセーブする場合true
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    private void __doSearch(ActionMapping map, Sch100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, boolean save)
    throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Sch100Biz biz = new Sch100Biz(reqMdl);
        Sch100ParamModel paramMdl = new Sch100ParamModel();
        __doInit(map, form, req, res, con);

        if (save) {
            //入力チェック
            ActionErrors errors = form.validateSch100Check(map, req);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return;
            }
            form.setSch100PageNum(1);
            //検索条件SAVE
            form.saveSearchParm();

            //出力項目が全て未選択の場合、初期状態(全て選択)に設定する
            String[] csvField = form.getSch100CsvOutField();
            if (csvField == null || csvField.length < 1) {
                csvField = Sch100Biz.getDefultCsvOut();
                form.setSch100CsvOutField(csvField);
            }
        }
        //不正検索チェック
        if (!form.validateSch100Research()) {
            ActionErrors errors = new ActionErrors();
            GsMessage gsMsg = new GsMessage();
            //スケジュール情報
            String textScheduleInfo = gsMsg.getMessage(req, "schedule.src.91");
            ActionMessage msg = new ActionMessage("search.data.notfound", textScheduleInfo,
                    "resultCnt");
            errors.add("resultCnt" + "error.input.length.text", msg);
            addErrors(req, errors);
            return;
        }

        paramMdl.setParam(form);

        int count = biz.getSearchResult(paramMdl, con);
        paramMdl.setFormData(form);

        if (count < 1) {
            ActionErrors errors = new ActionErrors();
            GsMessage gsMsg = new GsMessage();
            //スケジュール情報
            String textScheduleInfo = gsMsg.getMessage(req, "schedule.src.91");
            ActionMessage msg = new ActionMessage("search.data.notfound", textScheduleInfo,
                    "resultCnt");
            errors.add("resultCnt" + "error.input.length.text", msg);
            addErrors(req, errors);
            return;
        }
    }

    /**
     * <br>リクエストを解析し画面遷移先を取得する
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移
     */
    private ActionForward __doBack(ActionMapping map, Sch100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {

        ActionForward forward = null;

        String dspMod = form.getDspMod();
        if (dspMod.equals(GSConstSchedule.DSP_MOD_WEEK)) {
            forward = map.findForward("100_week");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_MONTH)) {
            forward = map.findForward("100_month");
        } else if (dspMod.equals(GSConstSchedule.DSP_MOD_DAY)) {
            forward = map.findForward("100_day");
        }
        return forward;
    }


    /**
     * <br>[機  能] ページコンボ変更処理
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
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    private ActionForward __doChangeMode(
            ActionMapping map,
            Sch100Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {
        __doSearch(map, form, req, res, con, false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 左矢印押下処理
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
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    private ActionForward __doPageMinus(
            ActionMapping map,
            Sch100Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        //ページ数取得
        int page = form.getSch100PageNum();
        page -= 1;
        if (page < 1) {
            page = 1;
        }

        //調整後ページ数セット
        form.setSch100PageNum(page);
        __doSearch(map, form, req, res, con, false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 右矢印押下処理
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
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    private ActionForward __doPagePlus(
            ActionMapping map,
            Sch100Form form,
            HttpServletRequest req,
            HttpServletResponse res, 
            Connection con) 
                    throws SQLException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        //ページ数取得
        int page = form.getSch100PageNum();
        page += 1;

        //調整後ページ数セット
        form.setSch100PageNum(page);
        __doSearch(map, form, req, res, con, false);
        return map.getInputForward();
    }

    /**スケジュール情報ダウンロードの処理
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
        Sch100Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        log__.debug("エクスポート処理");

        ActionErrors errors = form.validateCsvOutCheck(req);
        //CSV出力チェック
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            return map.getInputForward();
        }
        //不正検索チェック
        if (!form.validateSch100Research()) {
            __doInit(map, form, req, res, con);
            GsMessage gsMsg = new GsMessage();
            //スケジュール情報
            String textScheduleInfo = gsMsg.getMessage(req, "schedule.src.91");
            ActionMessage msg = new ActionMessage("search.data.notfound", textScheduleInfo,
                    "resultCnt");
            errors.add("resultCnt" + "error.input.length.text", msg);
            addErrors(req, errors);
            return map.getInputForward();

        }

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);
        String fileName = SchCsvWriter.FILE_NAME;
        String fullPath = tempDir + fileName;

        con.setAutoCommit(false);
        __doExport(map, form, req, res, con, tempDir);

        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);

        GsMessage gsMsg = new GsMessage();
        /** メッセージ エクスポート **/
        String export = gsMsg.getMessage(req, "cmn.export");

        //ログ出力処理
        SchCommonBiz schBiz = new SchCommonBiz(con, getRequestModel(req));
        schBiz.outPutLog(
                map, req, res,
                export, GSConstLog.LEVEL_INFO, fileName);

        return null;
    }
    /**
     * <br>[機  能] エクスポート処理を実行(氏名カナ)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param outDir 出力先ディレクトリ
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doExport(ActionMapping map, Sch100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String outDir)
            throws Exception {
        log__.debug("エクスポート処理(CSV)");
        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        Sch100Biz biz = new Sch100Biz(getRequestModel(req));
        //検索条件をセット(SAVEより)
        Sch100ParamModel paramMdl = new Sch100ParamModel();
        paramMdl.setParam(form);
        ScheduleListSearchModel searchMdl =
            biz.setScheduleListSearchModel(paramMdl, con, sessionUsrSid);
        paramMdl.setFormData(form);

        //CSVファイルを作成
        SchCsvWriter write = new SchCsvWriter(req, form);
        write.setSearchModel(searchMdl);
        write.setSessionUserSid(sessionUsrSid);
        write.outputCsv(con, outDir);

        return null;
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
            Sch100Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {

        log__.debug("ファイルダウンロード処理(スケジュール一覧PDF)");

        RequestModel reqMdl = getRequestModel(req);


        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();
        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String outTempDir = temp.getTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);

        //不正検索チェック
        if (!form.validateSch100Research()) {
            __doInit(map, form, req, res, con);

            ActionErrors errors = new ActionErrors();
            GsMessage gsMsg = new GsMessage();
            //スケジュール情報
            String textScheduleInfo = gsMsg.getMessage(req, "schedule.src.91");
            ActionMessage msg = new ActionMessage("search.data.notfound", textScheduleInfo,
                    "resultCnt");
            errors.add("resultCnt" + "error.input.length.text", msg);
            addErrors(req, errors);
            return map.getInputForward();

        }

        Sch100Biz biz = new Sch100Biz(reqMdl);
        //PDF生成
        Sch100ParamModel paramMdl = new Sch100ParamModel();
        paramMdl.setParam(form);
        SchListPdfModel pdfMdl =
                biz.createSchListPdf(
                        paramMdl, appRootPath, outTempDir, con);
        paramMdl.setFormData(form);

        String outBookName = pdfMdl.getFileName();
        String saveFileName = pdfMdl.getSaveFileName();
        String outFilePath = IOTools.setEndPathChar(outTempDir) + saveFileName;
        //ログ出力処理
        SchCommonBiz schBiz = new SchCommonBiz(con, getRequestModel(req));
        GsMessage gsMsg = new GsMessage(req);
        String logCode = "一覧 PDF出力 grpSid：" + form.getSch100SltGroup()
                + "、usrSid：" + form.getSch100SltUser();
        schBiz.outPutLog(
                map, req, res,
                gsMsg.getMessage("cmn.pdf"), GSConstLog.LEVEL_INFO, outBookName, logCode);
        TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);
        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstSchedule.PLUGIN_ID_SCHEDULE, TEMP_DIRECTORY_ID);

        return null;
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
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doPushDelete(
        ActionMapping map,
        Sch100Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //入力チェック
        ActionErrors errors = form.validatePowerCheck(getRequestModel(req), con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            log__.debug("入力チェックエラー");
            __doSearch(map, form, req, res, con, false);
            return  map.getInputForward();
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
        Sch100Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("100_research");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("mine");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=doDelSchData");

        //メッセージ
        GsMessage gsMsg = new GsMessage(req);
        String msg = "";

        //削除スケジュールに「同時登録された施設予約」が存在する場合、メッセージを変更する
        Sch100Biz biz = new Sch100Biz(getRequestModel(req));
        if (biz.existsRelatedRsvYrk(con, form.getSch100SelectScdSid())) {
            msg = gsMsg.getMessage("schedule.sch100.18");
        } else {
            msg = gsMsg.getMessage("schedule.sch100.17");
        }
        cmn999Form.setMessage(msg);

        form.setHiddenParam(cmn999Form);
        //画面パラメータをセット
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
     * @throws GSException GS用汎実行例外
     * @return ActionForward
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    private ActionForward __doDeleteComp(
        ActionMapping map,
        Sch100Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException,
    IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validatePowerCheck(getRequestModel(req), con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            log__.debug("入力チェックエラー");
            __doSearch(map, form, req, res, con, false);
            return  map.getInputForward();
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        RequestModel reqMdl = getRequestModel(req);
        Sch100Biz biz = new Sch100Biz(reqMdl);
        Sch100ParamModel paramMdl = new Sch100ParamModel();

        paramMdl.setParam(form);
        int count = biz.deleteSchData(con, paramMdl, userSid);
        paramMdl.setFormData(form);

        //ログ出力
        GsMessage gsMsg = new GsMessage(req);
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        String delete = gsMsg.getMessage("cmn.delete");
        StringBuilder sbValue = new StringBuilder();
        sbValue.append("[" + gsMsg.getMessage("user.usr033kn.2") + "]");
        sbValue.append(count + gsMsg.getMessage("cmn.number"));

        schBiz.outPutLog(
                map, req, res,
                delete, GSConstLog.LEVEL_TRACE, sbValue.toString());

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
        Sch100Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("100_research");

        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object",
                                gsMsg.getMessage(req, "schedule.108")));

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}