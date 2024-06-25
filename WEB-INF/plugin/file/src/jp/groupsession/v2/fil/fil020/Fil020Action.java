package jp.groupsession.v2.fil.fil020;

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
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] キャビネット詳細画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil020Action extends AbstractFileAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil020Action.class);
    /** ショートカット登録モード*/
    private static final int SHORT_UPDATE = 0;
    /** 更新通知登録モード*/
    private static final int CALL_UPDATE = 1;
    /** 復元確認モード*/
    private static final int REST_UPDATE = 2;
    /** ショートカット削除モード*/
    private static final int SHORT_DELETE = 3;
    /** 更新通知削除モード*/
    private static final int CALL_DELETE = 4;

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

        if (cmd.equals("fileDownload") || cmd.equals("fileDownloadReki")
                || cmd.equals("getPhotoFile")) {
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

        log__.debug("fil020Action START");

        ActionForward forward = null;
        Fil020Form thisForm = (Fil020Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //キャビネットアクセス権限チェック
        if (!__checkViewCabinet(thisForm, con, req)) {
            return getCanNotViewErrorPage(map, req);
        }

        if (cmd.equals("fil020back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("fil020edit")) {
            //編集ボタンクリック
            forward = __doEditCabinet(map, thisForm, req, res, con);
        } else if (cmd.equals("fileDownload")) {
            //ファイルダウンロード
            forward = __doDownLoad(
                    map, thisForm, req, res, con, GSConstCommon.FILE_DOWNLOAD);
        } else if (cmd.equals("fileDownloadReki")) {
//          添付ファイル名クリック(更新履歴一覧)
            forward = __doDownLoad(
                    map, thisForm, req, res, con, GSConstCommon.FILE_DOWNLOAD);

        } else if (cmd.equals("fileDownloadInline")) {
            //プレビュー用
            forward = __doDownLoad(
                    map, thisForm, req, res, con, GSConstCommon.FILE_DOWNLOAD_INLINE);
        } else if (cmd.equals("fil020short")) {
            //ショートカット更新
            forward = __doKakuninMsg(map, thisForm, req, res, con, SHORT_UPDATE);
        } else if (cmd.equals("fil020shortUpdate")) {
            //ショートカット更新実行
            forward = __doShortCutUpdate(map, thisForm, req, res, con);
        } else if (cmd.equals("fil020call")) {
            //更新通知設定
            forward = __doKakuninMsg(map, thisForm, req, res, con, CALL_UPDATE);
        } else if (cmd.equals("fil020callUpdate")) {
            //更新通知設定更新
            forward = __doCallUpdate(map, thisForm, req, res, con);
        } else if (cmd.equals("fil020tabChange")) {
            //タブ切り替え
            forward = __doTabChange(map, thisForm, req, res, con);
        } else if (cmd.equals("fil020rest")) {
            //復元
            forward = __doKakuninMsg(map, thisForm, req, res, con, REST_UPDATE);
        } else if (cmd.equals("fil020restUpdate")) {
            //復元実行
            forward = __doRestUpdate(map, thisForm, req, res, con);
        } else if (cmd.equals("prev")) {
            forward = __doPrev(map, thisForm, req, res, con);
        } else if (cmd.equals("next")) {
            forward = __doNext(map, thisForm, req, res, con);
        } else if (cmd.equals("tempview")) {
            //アイコン画像ファイルの表示
            forward = __doTempView(map, thisForm, req, res, con);
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
     * @throws IOToolsException ファイルアクセス例外
     * @throws IOException ファイルアクセス例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Fil020Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, IOException, IOToolsException, TempFileException {
        con.setAutoCommit(true);
        Fil020Biz biz = new Fil020Biz(con, getRequestModel(req));
//      セッションユーザModel
        BaseUserModel buMdl = getSessionUserModel(req);

        Fil020ParamModel paramMdl = new Fil020ParamModel();
        paramMdl.setParam(form);
        if (!biz.setInitData(paramMdl, buMdl, getAppRootPath(), con)) {
            GsMessage gsMsg = new GsMessage(req);
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.reading"),
                    gsMsg.getMessage("api.cmn.view"));
        }
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] キャビネットへのアクセス権限があるか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param con コネクション
     * @param req リクエスト
     * @return authFlg true:編集権限あり　false:編集権限なし
     * @throws SQLException SQL実行時例外
     */
    private boolean __checkViewCabinet(Fil020Form form, Connection con, HttpServletRequest req)
            throws SQLException {

        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);

        int cabSid = NullDefault.getInt(form.getFil010SelectCabinet(), -1);
        return filBiz.isAccessAuthUser(cabSid);
    }

    /**
     * <br>[機  能] キャビネット編集画面へ遷移
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
     * @throws IOToolsException ファイルアクセス例外
     */
    private ActionForward __doEditCabinet(ActionMapping map,
                                    Fil020Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, IOToolsException {

        con.setAutoCommit(true);
        //編集権限チェック
        int fcbSid = NullDefault.getInt(form.getFil010SelectCabinet(), -1);
        FilCommonBiz cmnBiz = new FilCommonBiz(getRequestModel(req), con);
        if (cmnBiz.isEditCabinetUser(fcbSid)) {
            return map.findForward("fil030");
        } else {
            //編集権限無しエラー
            return __doNonePowerError(map, form, req, res, con);
        }

    }

    /**
     * <br>[機  能] 確認画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param msgKbn メッセージ区分
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException ファイルアクセス例外
     */
    private ActionForward __doKakuninMsg(ActionMapping map,
                                    Fil020Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con,
                                    int msgKbn)
        throws SQLException, IOToolsException, IOException, TempFileException {
        con.setAutoCommit(true);
        ActionForward forward = null;
        RequestModel reqMdl = getRequestModel(req);
        //ディレクトリアクセスチェック
        if (msgKbn == REST_UPDATE) {
            ActionErrors errors = form.fil020RepairCheck(con, reqMdl);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }
        }

        // トランザクショントークン設定
        this.saveToken(req);

        //確認画面へ
        log__.debug("更新確認画面へ");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);


        //メッセージ設定
        Fil020Biz  biz = new Fil020Biz(con, getRequestModel(req));
        int fcbSid = NullDefault.getInt(form.getFil010SelectCabinet(), -1);
        BaseUserModel buMdl = getSessionUserModel(req);

        GsMessage gsMsg = new GsMessage();

        switch (msgKbn) {
        case SHORT_UPDATE:
            urlForward = map.findForward("fil020shortUpdate");
            String textShortcut = gsMsg.getMessage(req, "fil.2");
            if (biz.isShortCutSetting(fcbSid, buMdl.getUsrsid(), con)) {
                //ショートカット削除確認メッセージ
                cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.once",
                        textShortcut));
            } else {
                //ショートカット登録確認メッセージ
                cmn999Form.setMessage(msgRes.getMessage("touroku.kakunin.once",
                        textShortcut));
            }
            break;
        case CALL_UPDATE:
            //キャビネット名を取得
            FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
            String cabinetName = filBiz.getCabinetName(fcbSid, true);
            urlForward = map.findForward("fil020callUpdate");
            String textCall = gsMsg.getMessage(req, "fil.1");
            if (biz.isCallSetting(fcbSid, buMdl.getUsrsid(), con)) {
                //通知設定削除確認メッセージ
                if (form.getFil020CallLevelKbn() == GSConstFile.CALL_LEVEL_ON) {
                    cmn999Form.setMessage(msgRes.getMessage("kaijo.folder.all.kakunin.once",
                            cabinetName, textCall));
                } else {
                    cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.once", textCall));
                }
           } else {
                //通知設定登録確認メッセージ
                if (form.getFil020CallLevelKbn() == GSConstFile.CALL_LEVEL_ON) {
                    cmn999Form.setMessage(msgRes.getMessage("set.folder.all.kakunin.once",
                            cabinetName, textCall));
                } else {
                    cmn999Form.setMessage(msgRes.getMessage("touroku.kakunin.once", textCall));
                }
            }
            break;
        case REST_UPDATE:
            String textSelectFile = gsMsg.getMessage(req, "fil.92");
            urlForward = map.findForward("fil020restUpdate");
            cmn999Form.setMessage(msgRes.getMessage("add.new.version.kakunin.once",
                    textSelectFile));
            break;
        default:
            break;
        }
        cmn999Form.setUrlOK(urlForward.getPath());
        urlForward = map.findForward("fil020cancel");
        cmn999Form.setUrlCancel(urlForward.getPath());
        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("cmnMode", form.getCmnMode());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
        cmn999Form.addHiddenParam("fil010DspCabinetKbn", form.getFil010DspCabinetKbn());
        cmn999Form.addHiddenParam("fil010SelectDelLink", form.getFil010SelectDelLink());
        //
        cmn999Form.addHiddenParam("fil020SortKey", form.getFil020SortKey());
        cmn999Form.addHiddenParam("fil020OrderKey", form.getFil020OrderKey());
        cmn999Form.addHiddenParam("fil020SltDirSid", form.getFil020SltDirSid());
        cmn999Form.addHiddenParam("fil020SltDirVer", form.getFil020SltDirVer());
        cmn999Form.addHiddenParam("fil020DspMode", form.getFil020DspMode());
        cmn999Form.addHiddenParam("fil020Slt_page1", form.getFil020Slt_page1());
        cmn999Form.addHiddenParam("fil020Slt_page2", form.getFil020Slt_page2());
        cmn999Form.addHiddenParam("fil020CallLevelKbn", form.getFil020CallLevelKbn());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * 登録・更新完了画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param mode 更新する項目　0=ショートカット 1=更新通知 2=ファイル復元
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map, Fil020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, int mode) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //キャビネット登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("fil020cancel");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String textShortcut = gsMsg.getMessage(req, "fil.2");
        String textCall = gsMsg.getMessage(req, "fil.1");

        //更新完了メッセージ
        switch (mode) {
        case SHORT_UPDATE:
            cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                    textShortcut));
            break;
        case SHORT_DELETE:
            cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object",
                    textShortcut));
            break;
        case CALL_UPDATE:
            cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                    textCall));
            break;
        case CALL_DELETE:
            cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object",
                    textCall));
            break;
        case REST_UPDATE:
            String textRestFile = gsMsg.getMessage(req, "fil.72");
            cmn999Form.setMessage(msgRes.getMessage("cmn.kanryo.object",
                    textRestFile));
            break;
        default:
            break;
        }

        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("cmnMode", form.getCmnMode());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
        cmn999Form.addHiddenParam("fil010DspCabinetKbn", form.getFil010DspCabinetKbn());
        cmn999Form.addHiddenParam("fil010SelectDelLink", form.getFil010SelectDelLink());
        //
        cmn999Form.addHiddenParam("fil020SortKey", form.getFil020SortKey());
        cmn999Form.addHiddenParam("fil020OrderKey", form.getFil020OrderKey());
        cmn999Form.addHiddenParam("fil020DspMode", form.getFil020DspMode());
        cmn999Form.addHiddenParam("fil020Slt_page1", form.getFil020Slt_page1());
        cmn999Form.addHiddenParam("fil020Slt_page2", form.getFil020Slt_page2());
        cmn999Form.addHiddenParam("fil020CallLevelKbn", form.getFil020CallLevelKbn());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] ショートカット更新処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス例外
     */
    private ActionForward __doShortCutUpdate(ActionMapping map,
                                    Fil020Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, IOToolsException {

        ActionForward forward = null;
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        RequestModel reqMdl = getRequestModel(req);
        //更新通知設定
        boolean commitFlg = false;
        Fil020Biz  biz = new Fil020Biz(con, getRequestModel(req));
        int fcbSid = NullDefault.getInt(form.getFil010SelectCabinet(), -1);
        BaseUserModel buMdl = getSessionUserModel(req);
        String logValue = "";

        GsMessage gsMsg = new GsMessage();
        try {
            if (biz.isShortCutSetting(fcbSid, buMdl.getUsrsid(), con)) {
                String textDelShortcut = gsMsg.getMessage(req, "fil.79");
                biz.deleteShortCut(fcbSid, buMdl.getUsrsid(), con);
                forward = __doCompDsp(map, form, req, res, con, SHORT_DELETE);
                logValue = textDelShortcut;
            } else {
                String textAddShortcut = gsMsg.getMessage(req, "fil.78");
                biz.insertShortCut(fcbSid, buMdl.getUsrsid(), con);
                forward = __doCompDsp(map, form, req, res, con, SHORT_UPDATE);
                logValue = textAddShortcut;
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("ショートカットの更新に失敗しました" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        //ログ出力処理
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        String textEdit = gsMsg.getMessage(req, "cmn.change");

        StringBuilder sb = new StringBuilder();
        sb.append(logValue);
        sb.append("\r\n");
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "fil.13"));
        sb.append("] ");
        sb.append(filBiz.getCabinetName(fcbSid, true));


        filBiz.outPutLog(textEdit, GSConstLog.LEVEL_TRACE, sb.toString(), map.getType());

        return forward;
    }

    /**
     * <br>[機  能] 更新通知設定の更新処理
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
    private ActionForward __doCallUpdate(ActionMapping map,
                                    Fil020Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws SQLException {
        RequestModel reqMdl = getRequestModel(req);
        ActionForward forward = null;
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        //更新通知設定
        boolean commitFlg = false;
        Fil020Biz  biz = new Fil020Biz(con, reqMdl);
        int fcbSid = NullDefault.getInt(form.getFil010SelectCabinet(), -1);
        int callLevelKbn = form.getFil020CallLevelKbn();
        BaseUserModel buMdl = getSessionUserModel(req);
        String logValue = "";
        GsMessage gsMsg = new GsMessage();
        try {
            if (biz.isCallSetting(fcbSid, buMdl.getUsrsid(), con)) {
                String textDelCall = gsMsg.getMessage(req, "fil.81");
                biz.deleteCallConf(fcbSid, buMdl.getUsrsid(), con, callLevelKbn);
                forward = __doCompDsp(map, form, req, res, con, CALL_DELETE);
                logValue = textDelCall;
            } else {
                String textAddCall = gsMsg.getMessage(req, "fil.80");
                biz.updateCall(fcbSid, buMdl.getUsrsid(), con, callLevelKbn);
                forward = __doCompDsp(map, form, req, res, con, CALL_UPDATE);
                logValue = textAddCall;
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("更新通知設定の更新に失敗しました" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        String textEdit = gsMsg.getMessage(req, "cmn.change");

        //ログ出力処理
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        StringBuilder sb = new StringBuilder();
        sb.append(logValue);
        sb.append("\r\n");
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "fil.13"));
        sb.append("] ");
        sb.append(filBiz.getCabinetName(fcbSid, true));
        if (callLevelKbn  > GSConstFile.CALL_LEVEL_OFF) {
            sb.append("\r\n");
            sb.append(" ");
            sb.append(gsMsg.getMessage(req, "fil.7"));
        }

        filBiz.outPutLog(textEdit, GSConstLog.LEVEL_TRACE, sb.toString(), map.getType());

        return forward;
    }
    /**
     * <br>[機  能] 復元の更新処理
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
     */
    private ActionForward __doRestUpdate(ActionMapping map,
                                    Fil020Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        //権限チェック
        ActionErrors errors = form.fil020RepairCheck(con, reqMdl);
        if (!errors.isEmpty()) {
            GsMessage gsMsg = new GsMessage(req);
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.edit"),
                    gsMsg.getMessage("cmn.access"));
        }
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //セッションユーザModel
        BaseUserModel buMdl = getSessionUserModel(req);

        Fil020Biz biz = new Fil020Biz(con, reqMdl);
        boolean commitFlg = false;
        //アプリケーションRoot
        String appRootPath = getAppRootPath();
        //プラグイン設定
        PluginConfig plconf = getPluginConfig(req);

        PluginConfig pconfig = getPluginConfigForMain(plconf, con);
        CommonBiz cmnBiz = new CommonBiz();
        boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);
        int dirSid = NullDefault.getInt(form.getFil020SltDirSid(), 0);
        try {

            //採番コントローラ
            MlCountMtController cntCon = getCountMtController(req);
            //ファイル情報の復旧を行う。
            Fil020ParamModel paramMdl = new Fil020ParamModel();
            paramMdl.setParam(form);
            biz.updateRepair(getRequestModel(req), paramMdl, buMdl, cntCon,
                    appRootPath, plconf, smailPluginUseFlg);
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

        GsMessage gsMsg = new GsMessage();
        String textRepair = gsMsg.getMessage(req, "fil.12");
        String textEdit = gsMsg.getMessage(req, "cmn.change");

        //ログ出力処理
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        StringBuilder sb = new StringBuilder();
        sb.append(textRepair);
        sb.append("\r\n");
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "cmn.target"));
        sb.append("] ");
        sb.append(filBiz.getDirctoryPath(dirSid, true));
        filBiz.outPutLog(textEdit,
                GSConstLog.LEVEL_TRACE, sb.toString(), map.getType());

        return __doCompDsp(map, form, req, res, con, REST_UPDATE);
    }
    /**
     * <br>[機  能] タブ変更時の処理
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
     * @throws IOToolsException ファイルアクセス例外
     * @throws IOException ファイルアクセス例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doTabChange(ActionMapping map,
                                    Fil020Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, IOException, IOToolsException, TempFileException {

//        if (NullDefault.getString(
//                form.getFil020DspMode(), GSConstFile.DSP_MODE_HIST).equals(
//                        GSConstFile.DSP_MODE_HIST)) {
//            form.setFil020DspMode(GSConstFile.DSP_MODE_USER);
//        } else {
//            form.setFil020DspMode(GSConstFile.DSP_MODE_HIST);
//        }
        form.setFil020Slt_page1(1);
        form.setFil020Slt_page2(1);
        return __doInit(map, form, req, res, con);
    }


    /**
     * <br>[機  能] 遷移元画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map, Fil020Form form) {

        ActionForward forward = null;

        forward = map.findForward("cabinetMain");
        return forward;
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
    private ActionForward __doDownLoad(
        ActionMapping map,
        Fil020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int downloadKbn) throws SQLException, Exception {
        GsMessage gsMsg = new GsMessage(req);

        //バイナリSID
        Long binSid = NullDefault.getLong(form.getFileSid(), -1);
        //権限チェック
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        if (!filBiz.isDownloadAuthUser(binSid, false)) {
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.reading"),
                    gsMsg.getMessage("cmn.download"));
        }

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                GroupSession.getResourceManager().getDomain(req));

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

    /**
     * <br>編集権限が無い場合のエラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doNonePowerError(ActionMapping map, Fil020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        GsMessage gsMsg = new GsMessage();
        String textEditCabinet = gsMsg.getMessage(req, "fil.51");
        String textEditCabinet2 = gsMsg.getMessage(req, "fil.fil020.7");

        //スケジュール登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("fil010");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.edit.power.user",
                textEditCabinet, textEditCabinet2));

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
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
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return ActionForward
     */
    private ActionForward __doPrev(
        ActionMapping map,
        Fil020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException, IOException, TempFileException {

        //ページ設定
        int page = form.getFil020Slt_page1();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setFil020Slt_page1(page);
        form.setFil020Slt_page2(page);

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
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return ActionForward
     */
    private ActionForward __doNext(
        ActionMapping map,
        Fil020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException, IOException, TempFileException {

        //ページ設定
        int page = form.getFil020Slt_page1();
        page += 1;
        form.setFil020Slt_page1(page);
        form.setFil020Slt_page2(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 添付ファイルをブラウザ内に表示処理
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
    private ActionForward __doTempView(
        ActionMapping map,
        Fil020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, form.getFil020binSid(),
                GroupSession.getResourceManager().getDomain(req));

        if (cbMdl != null) {
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                                        Encoding.UTF_8);
        }
        return null;
    }
}

