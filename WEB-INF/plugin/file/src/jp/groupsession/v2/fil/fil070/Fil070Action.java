package jp.groupsession.v2.fil.fil070;

import java.io.IOException;
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

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル詳細画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil070Action extends AbstractFileAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil070Action.class);

    /** モード　ショートカット追加 */
    public static final int MODE_SHORTCUT_ON = 0;
    /** モード　ショートカット削除 */
    public static final int MODE_SHORTCUT_OFF = 1;
    /** モード　復旧 */
    public static final int MODE_REPAIR = 5;

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

        if (cmd.equals("fileDownload")) {
            log__.debug("添付ファイルダウンロード");
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

        log__.debug("fil070Action START");

        ActionForward forward = null;
        Fil070Form thisForm = (Fil070Form) form;

        //キャビネットアクセス権限チェック
        if (!__checkViewCabinet(thisForm, req, con)) {
            return getCanNotViewErrorPage(map, req);
        }
        //ディレクトリアクセスチェック
        if (!__checkViewDirectory(thisForm, req, con, -1, true)) {
            GsMessage gsMsg = new GsMessage(req);
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.reading"),
                    gsMsg.getMessage("api.cmn.view"));
        }

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil070back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm, req);

        } else if (cmd.equals("fil070edit")) {
            //編集ボタンクリック
            forward = __doEdit(map, thisForm, req, res, con);

        } else if (cmd.equals("fil070dsp")) {
            //表示ボタンクリック
            forward = map.findForward("fil040");

        } else if (cmd.equals("shortcutOn")) {
            //ショートカット追加ボタンクリック
            forward = __setKakuninPageParam(map, req, res, thisForm, con, MODE_SHORTCUT_ON);

        } else if (cmd.equals("shortcutOnOk")) {
            //ショートカット追加OKボタンクリック
            forward = __doShortcutOn(map, thisForm, req, res, con);

        } else if (cmd.equals("shortcutOff")) {
            //ショートカット削除ボタンクリック
            forward = __setKakuninPageParam(map, req, res, thisForm, con, MODE_SHORTCUT_OFF);

        } else if (cmd.equals("shortcutOffOk")) {
            //ショートカット削除OKボタンクリック
            forward = __doShortcutOff(map, thisForm, req, res, con);

        } else if (cmd.equals("repairClick")) {
            //復旧ボタンクリック
            forward = __setKakuninPageParam(map, req, res, thisForm, con, MODE_REPAIR);

        } else if (cmd.equals("repairOk")) {
            //復旧OKボタンクリック
            forward = __doRepair(map, thisForm, req, res, con);

        } else if (cmd.equals("fileDownload")) {
            //添付ファイル名クリック
            forward = __doDownLoad(
                    map, thisForm, req, res, con, GSConstCommon.FILE_DOWNLOAD);

        } else if (cmd.equals("fileDownloadInline")) {
            //プレビュー用
            forward = __doDownLoad(
                    map, thisForm, req, res, con, GSConstCommon.FILE_DOWNLOAD_INLINE);

        } else if (cmd.equals("prev")) {
            log__.debug("前ページ");
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (cmd.equals("next")) {
            log__.debug("次ページ");
            forward = __doNext(map, thisForm, req, res, con);

        } else if (cmd.equals("fil070tabChange")) {
            //タブ切り替え
            thisForm.setFil070PageNum1(1);
            thisForm.setFil070PageNum2(1);
            forward = __doInit(map, thisForm, req, res, con);
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
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOException IOエラー
     */
    private ActionForward __doInit(ActionMapping map,
                                    Fil070Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, IOException {

        con.setAutoCommit(true);

        Fil070Biz biz = new Fil070Biz(con, getRequestModel(req));

        //セッションユーザModel
        BaseUserModel buMdl = getSessionUserModel(req);
        //管理者権限を設定する
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstFile.PLUGIN_ID_FILE);
        form.setFil070AdminFlg(String.valueOf(adminUser));
        //初期表示を設定
        Fil070ParamModel paramMdl = new Fil070ParamModel();
        paramMdl.setParam(form);
        if (!biz.setInitData(paramMdl, buMdl)) {
            GsMessage gsMsg = new GsMessage(req);
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.reading"),
                    gsMsg.getMessage("api.cmn.view"));
        }
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        //更新履歴の削除
        if (form.getBackDspLow().equals(GSConstFile.MOVE_TO_FIL010)
                || form.getBackDspLow().equals(GSConstFile.MOVE_TO_MAIN)) {
            boolean commitFlg = false;
            try {
                paramMdl = new Fil070ParamModel();
                paramMdl.setParam(form);
                biz.deleteCallData(paramMdl, buMdl);
                paramMdl.setFormData(form);

                commitFlg = true;
            } catch (SQLException e) {
                log__.error("SQLException", e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    JDBCUtil.rollback(con);
                }
            }
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] キャビネットへのアクセス権限があるか判定する。
     * <br>[解  説] 編集ユーザでロックされていない場合はロックする。
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException 実行例外
     * @return ActionForward
     */
    private boolean __checkViewCabinet(
        Fil070Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {
        boolean errorFlg = true;

        FilCommonBiz cmnBiz = new FilCommonBiz(getRequestModel(req), con);
        int dirSid = NullDefault.getInt(form.getFil070DirSid(), -1);
        int fcbSid = cmnBiz.getCabinetSid(dirSid);

        //キャビネットへのアクセス権限があるか判定する。
        errorFlg = cmnBiz.isAccessAuthUser(fcbSid, -1, true);
        return errorFlg;
    }

    /**
     * <br>[機  能] ディレクトリへのアクセス権限があるか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @param auth 権限区分
     * @param ignoreJkbnFlg true:論理削除を無視する
     * @return true:権限あり false:権限なし
     * @throws SQLException 実行例外
     */
    private boolean __checkViewDirectory(
            Fil070Form form,
            HttpServletRequest req,
            Connection con,
            int auth,
            boolean ignoreJkbnFlg) throws SQLException {

        boolean errorFlg = true;
        FilCommonBiz cmnBiz = new FilCommonBiz(getRequestModel(req), con);
        int dirSid = NullDefault.getInt(form.getFil070DirSid(), -1);
        int fcbSid = cmnBiz.getCabinetSid(dirSid);

        errorFlg = cmnBiz.isDirAccessAuthUser(fcbSid,
                dirSid,
                auth,
                ignoreJkbnFlg);

        return errorFlg;
    }

    /**
     * <br>[機  能] 遷移元画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map, Fil070Form form, HttpServletRequest req) {

        ActionForward forward = null;

        if (form.getBackDspLow().equals("filmain")) {
            forward = map.findForward("filmain");
        } else if (form.getBackDspLow().equals(GSConstFile.MOVE_TO_FIL010)) {
            forward = map.findForward(GSConstFile.MOVE_TO_FIL010);
        } else if (form.getBackDspLow().equals(GSConstFile.MOVE_TO_FIL100)) {
            forward = map.findForward(GSConstFile.MOVE_TO_FIL100);
        } else if (form.getBackDspLow().equals(GSConstFile.MOVE_TO_MAIN)) {
            forward = map.findForward(GSConstFile.MOVE_TO_MAIN);
        } else if (form.getBackDspLow().equals(GSConstFile.MOVE_TO_FIL240)) {
            forward = map.findForward(GSConstFile.MOVE_TO_FIL240);
        } else {
            forward = map.findForward(GSConstFile.MOVE_TO_FIL040);
        }

        return forward;
    }

    /**
     * <br>[機  能] 編集ボタンクリック時の処理
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
     * @throws IOException IOエラー
     */
    private ActionForward __doEdit(ActionMapping map,
                                    Fil070Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, IOException {

        //セッションユーザModel
        BaseUserModel buMdl = getSessionUserModel(req);

        //管理者権限を設定する
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstFile.PLUGIN_ID_FILE);
        form.setFil070AdminFlg(String.valueOf(adminUser));

        return map.findForward("fil080");

    }

    /**
     * <br>[機  能] ショートカット追加ボタンクリック時の処理
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
     */
    private ActionForward __doShortcutOn(ActionMapping map,
                                    Fil070Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        GsMessage gsMsg = new GsMessage(req);
        
        //セッションユーザModel
        BaseUserModel buMdl = getSessionUserModel(req);

        Fil070Biz biz = new Fil070Biz(con, getRequestModel(req));
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {

            //ショートカット情報の追加を行う。
            Fil070ParamModel paramMdl = new Fil070ParamModel();
            paramMdl.setParam(form);
            biz.updateShortcut(paramMdl, GSConstFile.SMAIL_SEND_ON, buMdl);
            paramMdl.setFormData(form);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        String textEdit = gsMsg.getMessage(req, "cmn.change");
        String textShortcutOn = gsMsg.getMessage(req, "fil.78");

        //ログ出力処理
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        String dirPath = filBiz.getDirctoryPath(
                NullDefault.getInt(form.getFil070DirSid(), 0),
                true);
        StringBuilder sb = new StringBuilder();
        sb.append(textShortcutOn);
        sb.append("\r\n");
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "cmn.target"));
        sb.append("] ");
        sb.append(dirPath);
        filBiz.outPutLog(
                textEdit, GSConstLog.LEVEL_TRACE, sb.toString(), map.getType());

        return __setCompPageParam(map, req, form, MODE_SHORTCUT_ON);

    }

    /**
     * <br>[機  能] ショートカット削除ボタンクリック時の処理
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
     */
    private ActionForward __doShortcutOff(ActionMapping map,
                                    Fil070Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        GsMessage gsMsg = new GsMessage(req);

        //セッションユーザModel
        BaseUserModel buMdl = getSessionUserModel(req);

        Fil070Biz biz = new Fil070Biz(con, getRequestModel(req));
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {

            //ショートカットの削除を行う。
            Fil070ParamModel paramMdl = new Fil070ParamModel();
            paramMdl.setParam(form);
            biz.updateShortcut(paramMdl, GSConstFile.SMAIL_SEND_OFF, buMdl);
            paramMdl.setFormData(form);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        String textEdit = gsMsg.getMessage(req, "cmn.change");
        String textShortcutOff = gsMsg.getMessage(req, "fil.79");

        //ログ出力処理
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        String dirPath = filBiz.getDirctoryPath(
                NullDefault.getInt(form.getFil070DirSid(), 0),
                true);
        StringBuilder sb = new StringBuilder();
        sb.append(textShortcutOff);
        sb.append("\r\n");
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "cmn.target"));
        sb.append("] ");
        sb.append(dirPath);

        filBiz.outPutLog(
                textEdit, GSConstLog.LEVEL_TRACE, sb.toString(), map.getType());

        return __setCompPageParam(map, req, form, MODE_SHORTCUT_OFF);

    }

    /**
     * <br>[機  能] 復旧ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     * @throws IOException ファイル操作例外
     * @throws IOToolsException ファイル操作例外
     */
    private ActionForward __doRepair(ActionMapping map,
                                    Fil070Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception, IOException, IOToolsException {
        GsMessage gsMsg = new GsMessage(req);
        //ディレクトリアクセスチェック
        ActionErrors errors = form.fil070RepairCheck(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.edit"),
                    gsMsg.getMessage("cmn.access"));
        }

        //セッションユーザModel
        BaseUserModel buMdl = getSessionUserModel(req);

        Fil070Biz biz = new Fil070Biz(con, getRequestModel(req));
        CommonBiz cmnBiz = new CommonBiz();
        boolean commitFlg = false;

        //プラグイン設定
        PluginConfig plconf = getPluginConfig(req);

        PluginConfig pconfig = getPluginConfigForMain(plconf, con);
        boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);
        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);
        try {

            //ファイル情報の復旧を行う。
            Fil070ParamModel paramMdl = new Fil070ParamModel();
            paramMdl.setParam(form);
            biz.updateRepair(
                    getRequestModel(req), paramMdl, buMdl, getAppRootPath(), cntCon,
                    plconf, smailPluginUseFlg);
            paramMdl.setFormData(form);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        String textEdit = gsMsg.getMessage(req, "cmn.change");
        String textRepair = gsMsg.getMessage(req, "fil.12");

        //ログ出力処理
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        String dirPath = filBiz.getDirctoryPath(
                NullDefault.getInt(form.getFil070DirSid(), 0),
                true);
        StringBuilder sb = new StringBuilder();
        sb.append(textRepair);
        sb.append("\r\n");
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "cmn.target"));
        sb.append("] ");
        sb.append(dirPath);

        filBiz.outPutLog(
                textEdit,
                GSConstLog.LEVEL_TRACE, sb.toString(), map.getType());

        return __setCompPageParam(map, req, form, MODE_REPAIR);

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
     * @throws IOException IOエラー
     * @return ActionForward
     */
    private ActionForward __doPrev(
        ActionMapping map,
        Fil070Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOException {

        //ページ設定
        int page = form.getFil070PageNum1();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setFil070PageNum1(page);
        form.setFil070PageNum2(page);

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
     * @throws IOException IOエラー
     * @return ActionForward
     */
    private ActionForward __doNext(
        ActionMapping map,
        Fil070Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOException {

        //ページ設定
        int page = form.getFil070PageNum1();
        page += 1;
        form.setFil070PageNum1(page);
        form.setFil070PageNum2(page);

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
    private ActionForward __doDownLoad(ActionMapping map,
            Fil070Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int downloadKbn) throws SQLException, Exception {

        //バイナリSID
        Long binSid = NullDefault.getLong(form.getFileSid(), -1);
        //権限チェック
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        if (!filBiz.isDownloadAuthUser(binSid, false, true)) {
            GsMessage gsMsg = new GsMessage(req);
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.reading"),
                    gsMsg.getMessage("cmn.download"));
        }
        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                GroupSession.getResourceManager().getDomain(req));

        if (cbMdl == null) {
            return __doInit(map, form, req, res, con);
        }

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String textDownload = gsMsg.getMessage("cmn.download");

        //ログ出力処理
        filBiz.outPutLog(
                textDownload, GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(), map.getType(),
                String.valueOf(binSid));

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

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param res レスポンス
     * @param form アクションフォーム
     * @param con コネクション
     * @param mode モード
     * @return ActionForward アクションフォワード
     * @throws SQLException SQL実行時の例外
     * @throws IOException IOエラー
     */
    private ActionForward __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        HttpServletResponse res,
        Fil070Form form,
        Connection con,
        int mode) throws SQLException, IOException {
        GsMessage gsMsg = new GsMessage(req);

        //ディレクトリアクセスチェック
        if (mode == MODE_REPAIR ) {
            ActionErrors errors = form.fil070RepairCheck(con, getRequestModel(req));
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }
        }

        con.setAutoCommit(true);
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForwardOk = null;
        ActionForward urlForwardCancel = null;


        //メッセージセット
        String msgState = null;
        String dspText = null;

        String textScut = gsMsg.getMessage(req, "fil.2");

        if (mode == MODE_SHORTCUT_ON) {
            msgState = "add.to.kakunin.once";
            dspText = textScut;
            urlForwardOk = map.findForward("fil070shortcutOnOk");
        } else if (mode == MODE_SHORTCUT_OFF) {
            msgState = "sakujo.from.kakunin.once";
            dspText = textScut;
            urlForwardOk = map.findForward("fil070shortcutOffOk");
        } else if (mode == MODE_REPAIR) {
            msgState = "add.new.version.kakunin.once";
            urlForwardOk = map.findForward("fil070repairOk");
        }

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForwardCancel = map.findForward("fil070");
        cmn999Form.setUrlOK(urlForwardOk.getPath());
        cmn999Form.setUrlCancel(urlForwardCancel.getPath());

        //フォルダ名を取得
        int dirSid = NullDefault.getInt(form.getFil070DirSid(), 0);
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);

        if (mode == MODE_REPAIR) {
            int version = NullDefault.getInt(form.getFil070SltDirVer(), 0);
            String dirName = StringUtilHtml.transToHTmlPlusAmparsant(
                    filBiz.getDirctoryName(dirSid, version));
            cmn999Form.setMessage(msgRes.getMessage(msgState, dirName));
        } else {
            String dirName = StringUtilHtml.transToHTmlPlusAmparsant(
                    filBiz.getDirctoryName(dirSid));
            cmn999Form.setMessage(msgRes.getMessage(msgState, dirName, dspText));
        }


        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("backDspLow", form.getBackDspLow());
        cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
        cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
        cmn999Form.addHiddenParam("fil010DspCabinetKbn", form.getFil010DspCabinetKbn());
        cmn999Form.addHiddenParam("fil070SortKey", form.getFil070SortKey());
        cmn999Form.addHiddenParam("fil070OrderKey", form.getFil070OrderKey());
        cmn999Form.addHiddenParam("fil070SltDirSid", form.getFil070SltDirSid());
        cmn999Form.addHiddenParam("fil070SltDirVer", form.getFil070SltDirVer());
        cmn999Form.addHiddenParam("fil070DirSid", form.getFil070DirSid());
        cmn999Form.addHiddenParam("fil070PageNum1", form.getFil070PageNum1());
        cmn999Form.addHiddenParam("fil070PageNum2", form.getFil070PageNum2());

        cmn999Form.addHiddenParam("fil100ChkTrgFolder", form.getFil100ChkTrgFolder());
        cmn999Form.addHiddenParam("fil100ChkTrgFile", form.getFil100ChkTrgFile());
        cmn999Form.addHiddenParam("fil100SearchMode", form.getFil100SearchMode());
        cmn999Form.addHiddenParam("fil100ChkWdTrgName", form.getFil100ChkWdTrgName());
        cmn999Form.addHiddenParam("fil100ChkWdTrgBiko", form.getFil100ChkWdTrgBiko());
        cmn999Form.addHiddenParam("fil100ChkWdTrgText", form.getFil100ChkWdTrgText());
        cmn999Form.addHiddenParam("fileSearchfromYear", form.getFileSearchfromYear());
        cmn999Form.addHiddenParam("fileSearchfromMonth", form.getFileSearchfromMonth());
        cmn999Form.addHiddenParam("fileSearchfromDay", form.getFileSearchfromDay());
        cmn999Form.addHiddenParam("fileSearchtoYear", form.getFileSearchtoYear());
        cmn999Form.addHiddenParam("fileSearchtoMonth", form.getFileSearchtoMonth());
        cmn999Form.addHiddenParam("fileSearchtoDay", form.getFileSearchtoDay());
        cmn999Form.addHiddenParam("fil100ChkOnOff", form.getFil100ChkOnOff());
        cmn999Form.addHiddenParam("fil100SvSltCabinetSid", form.getFil100SvSltCabinetSid());
        cmn999Form.addHiddenParam("fil100SvChkTrgFolder", form.getFil100SvChkTrgFolder());
        cmn999Form.addHiddenParam("fil100SvChkTrgFile", form.getFil100SvChkTrgFile());
        cmn999Form.addHiddenParam("fil100SvChkTrgDeleted", form.getFil100SvChkTrgDeleted());
        cmn999Form.addHiddenParam("fil100SvChkTrgDeletedFolder",
                                form.getFil100SvChkTrgDeletedFolder());
        cmn999Form.addHiddenParam("fil100SvSearchMode", form.getFil100SvSearchMode());
        cmn999Form.addHiddenParam("fil100SvChkWdTrgName", form.getFil100SvChkWdTrgName());
        cmn999Form.addHiddenParam("fil100SvChkWdTrgBiko", form.getFil100SvChkWdTrgBiko());
        cmn999Form.addHiddenParam("fil100SvChkWdTrgText", form.getFil100SvChkWdTrgText());
        cmn999Form.addHiddenParam("fil100SvChkWdKeyWord", form.getFil100SvChkWdKeyWord());
        cmn999Form.addHiddenParam("fileSvSearchfromYear", form.getFileSvSearchfromYear());
        cmn999Form.addHiddenParam("fileSvSearchfromMonth", form.getFileSvSearchfromMonth());
        cmn999Form.addHiddenParam("fileSvSearchfromDay", form.getFileSvSearchfromDay());
        cmn999Form.addHiddenParam("fileSvSearchtoYear", form.getFileSvSearchtoYear());
        cmn999Form.addHiddenParam("fileSvSearchtoMonth", form.getFileSvSearchtoMonth());
        cmn999Form.addHiddenParam("fileSvSearchtoDay", form.getFileSvSearchtoDay());
        cmn999Form.addHiddenParam("fil100SvChkOnOff", form.getFil100SvChkOnOff());
        cmn999Form.addHiddenParam("fil100sortKey", form.getFil100sortKey());
        cmn999Form.addHiddenParam("fil100orderKey", form.getFil100orderKey());
        cmn999Form.addHiddenParam("fil100pageNum1", form.getFil100pageNum1());
        cmn999Form.addHiddenParam("fil100pageNum2", form.getFil100pageNum2());
        cmn999Form.addHiddenParam("fil240PageNum", form.getFil240PageNum());
        cmn999Form.addHiddenParam("backDspCall", form.getBackDspCall());
        cmn999Form.addHiddenParam("fil100SltCabinetKbn", form.getFil100SltCabinetKbn());
        cmn999Form.addHiddenParam("fil100ChkTrgDeleted", form.getFil100ChkTrgDeleted());
        cmn999Form.addHiddenParam("fil100ChkTrgDeletedFolder", form.getFil100ChkTrgDeletedFolder());
        cmn999Form.addHiddenParam("fil100SearchTradeTarget", form.getFil100SearchTradeTarget());
        cmn999Form.addHiddenParam("fil100SearchTradeMoneyNoset",
                form.getFil100SearchTradeMoneyNoset());
        cmn999Form.addHiddenParam("fil100SearchTradeMoneyKbn", form.getFil100SearchTradeMoneyKbn());
        cmn999Form.addHiddenParam("fil100SearchTradeMoney", form.getFil100SearchTradeMoney());
        cmn999Form.addHiddenParam("fil100SearchTradeMoneyTo", form.getFil100SearchTradeMoneyTo());
        cmn999Form.addHiddenParam("fil100SearchTradeMoneyType",
                form.getFil100SearchTradeMoneyType());
        cmn999Form.addHiddenParam("fil100SearchTradeMoneyJudge",
                form.getFil100SearchTradeMoneyJudge());
        cmn999Form.addHiddenParam("fil100SearchTradeDateKbn", form.getFil100SearchTradeDateKbn());
        cmn999Form.addHiddenParam("fil100SearchTradeDateFrom", form.getFil100SearchTradeDateFrom());
        cmn999Form.addHiddenParam("fil100SearchTradeDateTo", form.getFil100SearchTradeDateTo());
        cmn999Form.addHiddenParam("fil100SvSearchTradeTarget", form.getFil100SvSearchTradeTarget());
        cmn999Form.addHiddenParam("fil100SvSearchTradeMoney", form.getFil100SvSearchTradeMoney());
        cmn999Form.addHiddenParam("fil100SvSearchTradeMoneyTo",
                form.getFil100SvSearchTradeMoneyTo());
        cmn999Form.addHiddenParam("fil100SvSearchTradeMoneyType",
                form.getFil100SvSearchTradeMoneyType());
        cmn999Form.addHiddenParam("fil100SvSearchTradeMoneyJudge",
                form.getFil100SvSearchTradeMoneyJudge());
        cmn999Form.addHiddenParam("fil100SvSearchTradeMoneyNoset",
                form.getFil100SvSearchTradeMoneyNoset());
        cmn999Form.addHiddenParam("fil100SvSearchTradeMoneyKbn",
                form.getFil100SvSearchTradeMoneyKbn());
        cmn999Form.addHiddenParam("fil100SvSearchTradeDateFrom",
                form.getFil100SvSearchTradeDateFrom());
        cmn999Form.addHiddenParam("fil100SvSearchTradeDateTo", form.getFil100SvSearchTradeDateTo());
        cmn999Form.addHiddenParam("fil100SvSearchTradeDateKbn",
                form.getFil100SvSearchTradeDateKbn());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param mode モード
     * @return ActionForward アクションフォワード
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Fil070Form form,
        int mode) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("fil070");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "cmn.kanryo.object";
        GsMessage gsMsg = new GsMessage();

        String dspText = null;
        if (mode == MODE_SHORTCUT_ON) {
            dspText = gsMsg.getMessage(req, "fil.scut.set");
        } else if (mode == MODE_SHORTCUT_OFF) {
            dspText = gsMsg.getMessage(req, "fil.scut.del");
        } else if (mode == MODE_REPAIR) {
            dspText = gsMsg.getMessage(req, "fil.file.add");
        }

        cmn999Form.setMessage(msgRes.getMessage(msgState, dspText));

        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("backDspLow", form.getBackDspLow());
        cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
        cmn999Form.addHiddenParam("fil010DspCabinetKbn", form.getFil010DspCabinetKbn());
        cmn999Form.addHiddenParam("fil070SortKey", form.getFil070SortKey());
        cmn999Form.addHiddenParam("fil070OrderKey", form.getFil070OrderKey());
        cmn999Form.addHiddenParam("fil070DirSid", form.getFil070DirSid());
        cmn999Form.addHiddenParam("fil070PageNum1", form.getFil070PageNum1());
        cmn999Form.addHiddenParam("fil070PageNum2", form.getFil070PageNum2());

        cmn999Form.addHiddenParam("fil100ChkTrgFolder", form.getFil100ChkTrgFolder());
        cmn999Form.addHiddenParam("fil100ChkTrgFile", form.getFil100ChkTrgFile());
        cmn999Form.addHiddenParam("fil100SearchMode", form.getFil100SearchMode());
        cmn999Form.addHiddenParam("fil100ChkWdTrgName", form.getFil100ChkWdTrgName());
        cmn999Form.addHiddenParam("fil100ChkWdTrgBiko", form.getFil100ChkWdTrgBiko());
        cmn999Form.addHiddenParam("fil100ChkWdTrgText", form.getFil100ChkWdTrgText());
        cmn999Form.addHiddenParam("fileSearchfromYear", form.getFileSearchfromYear());
        cmn999Form.addHiddenParam("fileSearchfromMonth", form.getFileSearchfromMonth());
        cmn999Form.addHiddenParam("fileSearchfromDay", form.getFileSearchfromDay());
        cmn999Form.addHiddenParam("fileSearchtoYear", form.getFileSearchtoYear());
        cmn999Form.addHiddenParam("fileSearchtoMonth", form.getFileSearchtoMonth());
        cmn999Form.addHiddenParam("fileSearchtoDay", form.getFileSearchtoDay());
        cmn999Form.addHiddenParam("fil100ChkOnOff", form.getFil100ChkOnOff());
        cmn999Form.addHiddenParam("fil100SvSltCabinetSid", form.getFil100SvSltCabinetSid());
        cmn999Form.addHiddenParam("fil100SvChkTrgFolder", form.getFil100SvChkTrgFolder());
        cmn999Form.addHiddenParam("fil100SvChkTrgFile", form.getFil100SvChkTrgFile());
        cmn999Form.addHiddenParam("fil100SvChkTrgDeleted", form.getFil100SvChkTrgDeleted());
        cmn999Form.addHiddenParam("fil100SvChkTrgDeletedFolder",
                                form.getFil100SvChkTrgDeletedFolder());
        cmn999Form.addHiddenParam("fil100SvSearchMode", form.getFil100SvSearchMode());
        cmn999Form.addHiddenParam("fil100SvChkWdTrgName", form.getFil100SvChkWdTrgName());
        cmn999Form.addHiddenParam("fil100SvChkWdTrgBiko", form.getFil100SvChkWdTrgBiko());
        cmn999Form.addHiddenParam("fil100SvChkWdTrgText", form.getFil100SvChkWdTrgText());
        cmn999Form.addHiddenParam("fil100SvChkWdKeyWord", form.getFil100SvChkWdKeyWord());
        cmn999Form.addHiddenParam("fileSvSearchfromYear", form.getFileSvSearchfromYear());
        cmn999Form.addHiddenParam("fileSvSearchfromMonth", form.getFileSvSearchfromMonth());
        cmn999Form.addHiddenParam("fileSvSearchfromDay", form.getFileSvSearchfromDay());
        cmn999Form.addHiddenParam("fileSvSearchtoYear", form.getFileSvSearchtoYear());
        cmn999Form.addHiddenParam("fileSvSearchtoMonth", form.getFileSvSearchtoMonth());
        cmn999Form.addHiddenParam("fileSvSearchtoDay", form.getFileSvSearchtoDay());
        cmn999Form.addHiddenParam("fil100SvChkOnOff", form.getFil100SvChkOnOff());
        cmn999Form.addHiddenParam("fil100sortKey", form.getFil100sortKey());
        cmn999Form.addHiddenParam("fil100orderKey", form.getFil100orderKey());
        cmn999Form.addHiddenParam("fil100pageNum1", form.getFil100pageNum1());
        cmn999Form.addHiddenParam("fil100pageNum2", form.getFil100pageNum2());
        cmn999Form.addHiddenParam("fil240PageNum", form.getFil240PageNum());
        cmn999Form.addHiddenParam("backDspCall", form.getBackDspCall());
        cmn999Form.addHiddenParam("fil100SltCabinetKbn", form.getFil100SltCabinetKbn());
        cmn999Form.addHiddenParam("fil100ChkTrgDeleted", form.getFil100ChkTrgDeleted());
        cmn999Form.addHiddenParam("fil100ChkTrgDeletedFolder", form.getFil100ChkTrgDeletedFolder());
        cmn999Form.addHiddenParam("fil100SearchTradeTarget", form.getFil100SearchTradeTarget());
        cmn999Form.addHiddenParam("fil100SearchTradeMoneyNoset",
                form.getFil100SearchTradeMoneyNoset());
        cmn999Form.addHiddenParam("fil100SearchTradeMoneyKbn", form.getFil100SearchTradeMoneyKbn());
        cmn999Form.addHiddenParam("fil100SearchTradeMoney", form.getFil100SearchTradeMoney());
        cmn999Form.addHiddenParam("fil100SearchTradeMoneyTo", form.getFil100SearchTradeMoneyTo());
        cmn999Form.addHiddenParam("fil100SearchTradeMoneyType",
                form.getFil100SearchTradeMoneyType());
        cmn999Form.addHiddenParam("fil100SearchTradeMoneyJudge",
                form.getFil100SearchTradeMoneyJudge());
        cmn999Form.addHiddenParam("fil100SearchTradeDateKbn", form.getFil100SearchTradeDateKbn());
        cmn999Form.addHiddenParam("fil100SearchTradeDateFrom", form.getFil100SearchTradeDateFrom());
        cmn999Form.addHiddenParam("fil100SearchTradeDateTo", form.getFil100SearchTradeDateTo());
        cmn999Form.addHiddenParam("fil100SvSearchTradeTarget", form.getFil100SvSearchTradeTarget());
        cmn999Form.addHiddenParam("fil100SvSearchTradeMoney", form.getFil100SvSearchTradeMoney());
        cmn999Form.addHiddenParam("fil100SvSearchTradeMoneyTo",
                form.getFil100SvSearchTradeMoneyTo());
        cmn999Form.addHiddenParam("fil100SvSearchTradeMoneyType",
                form.getFil100SvSearchTradeMoneyType());
        cmn999Form.addHiddenParam("fil100SvSearchTradeMoneyJudge",
                form.getFil100SvSearchTradeMoneyJudge());
        cmn999Form.addHiddenParam("fil100SvSearchTradeMoneyNoset",
                form.getFil100SvSearchTradeMoneyNoset());
        cmn999Form.addHiddenParam("fil100SvSearchTradeMoneyKbn",
                form.getFil100SvSearchTradeMoneyKbn());
        cmn999Form.addHiddenParam("fil100SvSearchTradeDateFrom",
                form.getFil100SvSearchTradeDateFrom());
        cmn999Form.addHiddenParam("fil100SvSearchTradeDateTo", form.getFil100SvSearchTradeDateTo());
        cmn999Form.addHiddenParam("fil100SvSearchTradeDateKbn",
                form.getFil100SvSearchTradeDateKbn());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

}