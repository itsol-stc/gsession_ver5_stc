package jp.groupsession.v2.fil.fil100;

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

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル詳細検索画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil100Action extends AbstractFileAction {

    /** CMD:検索ボタンクリック */
    public static final String CMD_SEARCH = "searchClick";
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil100Action.class);

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

        if (cmd.equals("fileNameClick")) {
            log__.debug("ファイル名クリック");
            return true;
        }
        return false;
    }

    /**
     *<br>[機  能] アクションを実行する
     *<br>[解  説]
     *<br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {

        log__.debug("fil100Action START");

        ActionForward forward = null;
        Fil100Form thisForm = (Fil100Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        if (cmd.equals("fil100back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("fil100folderDetail")) {
            //フォルダリンククリック
            forward = map.findForward("fil050");

        } else if (cmd.equals("searchClick")) {
            //検索ボタンクリック
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("fil100PagePreview")) {
            //前ページクリック
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (cmd.equals("fil100PageNext")) {
            //次ページクリック
            forward = __doNext(map, thisForm, req, res, con);

        } else if (cmd.equals("fileNameClick")) {
            //ファイルリンククリック
            forward = __doDownloadFile(
                    map, thisForm, req, res, con, GSConstCommon.FILE_DOWNLOAD);

        } else if (cmd.equals("fileDownloadInline")) {
            //プレビュー用
            forward = __doDownloadFile(
                    map, thisForm, req, res, con, GSConstCommon.FILE_DOWNLOAD_INLINE);

        } else if (cmd.equals("fil100folderDetail")) {
            //フォルダ詳細ボタンクリック
            forward = map.findForward("fil050");

        } else if (cmd.equals("fil100fileDetail")) {
            //ファイル詳細ボタンクリック
            forward = map.findForward("fil070");

        } else if (cmd.equals("fil010search")) {
            //キャビネット一覧画面から検索
            thisForm.initSearchKbn();
            if (StringUtil.isNullZeroString(thisForm.getFilSearchWd())) {
                //キーワード未入力時は検索を実行しない
                return __doDsp(map, thisForm, req, con, getRequestModel(req).getSmodel());
            } else {
                forward = __doSearch(map, thisForm, req, res, con);
            }

        } else if (cmd.equals("fil040search")) {
            //フォルダ一覧画面から検索
            thisForm.initSearchKbn();
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("fil100cabinetKbnChange")) {
            //キャビネット区分変更
            forward = __doDsp(map, thisForm, req, con, getRequestModel(req).getSmodel());
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Fil100Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, IOToolsException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        con.setAutoCommit(true);
        //初期表示情報を画面にセットする
        Fil100Biz biz = new Fil100Biz(con, getRequestModel(req));
        form.initSearchKbn();

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        //初期表示処理
        Fil100ParamModel paramMdl = new Fil100ParamModel();
        paramMdl.setParam(form);

        //検索しない時の処理
        if (form.getSearchFlg() == GSConstFile.SEARCH_EXECUTE_FALSE) {
            return __doDsp(map, form, req, con, usModel);
        }

        //アプリケーションルートパス
        String appRootPath = getAppRootPath();
        getFileInitConfigData(appRootPath, paramMdl);

        //検索処理
        ActionErrors errors = biz.setInitData(paramMdl, con, usModel, getAppRootPath());
        paramMdl.setFormData(form);

        //検索値入力チェック
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setSearchFlg(GSConstFile.SEARCH_EXECUTE_FALSE);
        }

        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        form.setFil100searchUse(CommonBiz.getWebSearchUse(pconfig));

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 遷移元画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map, Fil100Form form) {

        ActionForward forward = null;
        if (form.getBackDsp().equals(GSConstFile.MOVE_TO_FIL040)) {
            forward = map.findForward("fil040");
        } else {
            forward = map.findForward("cabinetMain");
        }
        return forward;
    }

    /**
     * <br>[機  能] 検索処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doSearch(ActionMapping map,
            Fil100Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, IOToolsException,
                IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        log__.debug("検索開始");
        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        ActionErrors errors = new ActionErrors();

        //入力チェック
        RequestModel reqMdl = getRequestModel(req);
        errors = form.validateFil100Check(con, req, reqMdl);

        if (!errors.isEmpty()) {

            addErrors(req, errors);
            form.setSearchFlg(GSConstFile.SEARCH_EXECUTE_FALSE);
            return __doDsp(map, form, req, con, usModel);
        }

        form.setFil100pageNum1(1);
        //検索条件SAVE
        form.saveSearchParm();

        //検索実行フラグON
        form.setSearchFlg(GSConstFile.SEARCH_EXECUTE_TRUE);

        String appRootPath = getAppRootPath();
        //検索警告表示
        Fil100ParamModel paramMdl = new Fil100ParamModel();
        paramMdl.setParam(form);
        Fil100Biz biz = new Fil100Biz(con, reqMdl);
        if (biz.isDspWarn(paramMdl, con, usModel, appRootPath)) {
            log__.debug("検索警告を表示");
            paramMdl.setFormData(form);
            form.setFil100WarnDspFlg(1);
            return __doDsp(map, form, req, con, usModel);
        }

        log__.debug("検索終了");
        //再表示
        return __doInit(map, form, req, res, con);

    }

    /**
     * <br>[機  能] 再表示を行う
     * <br>[解  説] 検索を行わない
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @param usModel セッションユーザ情報
     * @throws SQLException 実行例外
     * @return ActionForward フォワード
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doDsp(
        ActionMapping map,
        Fil100Form form,
        HttpServletRequest req,
        Connection con,
        BaseUserModel usModel) throws SQLException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        FileAconfDao aconfDao = new FileAconfDao(con);
        FileAconfModel aconf = aconfDao.select();

        con.setAutoCommit(true);
        //初期表示情報を画面にセットする
        Fil100Biz biz = new Fil100Biz(con, getRequestModel(req));
        Fil100ParamModel paramMdl = new Fil100ParamModel();
        paramMdl.setParam(form);

        //アプリケーションルートパス
        String appRootPath = getAppRootPath();
        getFileInitConfigData(appRootPath, paramMdl);
        biz.setCombo(paramMdl, con, usModel, aconf);
        paramMdl.setFormData(form);

        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        form.setFil100searchUse(CommonBiz.getWebSearchUse(pconfig));
        con.setAutoCommit(false);
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
     * @throws IOToolsException ファイルアクセス時例外
     * @return ActionForward
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doPrev(
        ActionMapping map,
        Fil100Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //ページ設定
        int page = form.getFil100pageNum1();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setFil100pageNum1(page);
        form.setFil100pageNum2(page);

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
     * @throws IOToolsException ファイルアクセス時例外
     * @return ActionForward
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    private ActionForward __doNext(
        ActionMapping map,
        Fil100Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //ページ設定
        int page = form.getFil100pageNum1();
        page += 1;
        form.setFil100pageNum1(page);
        form.setFil100pageNum2(page);

        return __doInit(map, form, req, res, con);
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
     * @param downloadKbn ダウンロード区分
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownloadFile(
        ActionMapping map,
        Fil100Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int downloadKbn) throws SQLException, Exception {

        Long binSid = NullDefault.getLong(form.getBinSid(), -1);

        //バイナリー情報を取得する
        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                GroupSession.getResourceManager().getDomain(req));

        if (cbMdl == null || (
                form.getFil100SltCabinetKbn() == GSConstFile.CABINET_KBN_PUBLIC
                && cbMdl.getBinJkbn() == GSConst.JTKBN_DELETE)) {
            return __doInit(map, form, req, res, con);
        }

        //ファイルが閲覧可能かを判定
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        //権限チェック
        if (!filBiz.isDownloadAuthUser(binSid, true, true)) {
            GsMessage gsMsg = new GsMessage(req);
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.reading"),
                    gsMsg.getMessage("cmn.download"));
        }

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String textDownload = gsMsg.getMessage("cmn.download");

        //ログ出力処理
        filBiz.outPutLog(
                textDownload, GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(), map.getType());

        //集計用データを登録する
        filBiz.regFileDownloadLogCnt(getSessionUserSid(req), binSid, new UDate());

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);

        //ファイルをダウンロードする
        if (downloadKbn == GSConstCommon.FILE_DOWNLOAD_INLINE) {
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        } else {
            TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        }
        cbMdl.removeTempFile();
        return null;
    }
}