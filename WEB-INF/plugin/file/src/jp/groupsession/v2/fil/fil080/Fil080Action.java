package jp.groupsession.v2.fil.fil080;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.GSValidateFile;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileFileBinModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル登録画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil080Action extends AbstractFileAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil080Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "fil080";


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

        log__.debug("fil080Action START");

        ActionForward forward = null;
        Fil080Form thisForm = (Fil080Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "").trim();
        if (cmd.equals("calledWebmail")) {
            log__.debug("WEBメール連携");
            forward = __doCalledWebmail(map, thisForm, req, res, con);
            if (forward != null) {
                return forward;
            }
            cmd = "init";
        }

        con.setAutoCommit(true);
        if (thisForm.getFil080webmail() != 1) {
            //Webメール共有時の親ディレクトリ未選択時はチェック対象外
            //キャビネットアクセス権限チェック
            if (!__checkViewCabinet(thisForm, req, con)) {
                GsMessage gsMsg = new GsMessage(req);
                return getPowNoneErrorPage(
                        map, req,
                        gsMsg.getMessage("cmn.edit"),
                        gsMsg.getMessage("cmn.entry"));
            }
            int ecode = __checkEditDir(thisForm, req, con);
            if (ecode == FilCommonBiz.ERR_NONE_CAB_EDIT_POWER) {
                GsMessage gsMsg = new GsMessage(req);
                String actName = "";
                if (NullDefault.getInt(thisForm.getFil070DirSid(), -1) != -1) {
                    actName = gsMsg.getMessage("cmn.edit");
                } else {
                    actName = gsMsg.getMessage("fil.16");
                }
                return getPowNoneErrorPage(map, req,
                        gsMsg.getMessage("cmn.edit"),
                        actName);
            }
            if (ecode == FilCommonBiz.ERR_NOT_EXIST) {
                GsMessage gsMsg = new GsMessage(req);
                String targetName = "";
                if (NullDefault.getInt(thisForm.getFil070DirSid(), -1) != -1) {
                    targetName = gsMsg.getMessage("cmn.file");
                } else {
                    targetName = gsMsg.getMessage("cmn.folder");
                }
                return getCanNotViewNonePowerErrorPage(map, req, targetName);
            }
            if (ecode == FilCommonBiz.ERR_NOT_DIRKBN) {
                return getSubmitErrorPage(map, req);
            }
            //対象の状態区分チェック
            GsMessage gsMsg = new GsMessage(req);
            int cabSid = NullDefault.getInt(thisForm.getFil010SelectCabinet(), -1);
            FilCommonBiz cmnBiz = new FilCommonBiz(getRequestModel(req), con);
            if (!cmnBiz.checkCabinetDeleteKbn(cabSid)) {
                return getCanNotViewNonePowerErrorPage(map, req, gsMsg.getMessage("fil.23"));
            }
            int dirSid = NullDefault.getInt(thisForm.getFil070DirSid(), -1);
            if (dirSid != -1) {
                //ディレクトリの論理削除チェック
                if (!cmnBiz.checkDirectoryDeleteKbn(dirSid)) {
                    return getCanNotViewNonePowerErrorPage(
                            map, req, gsMsg.getMessage("fil.fil080.9"));
                }
            }
        }
        //ファイルロックチェック
        if (!__checkFileLock(thisForm, req, con)) {
            return __getFileLockErrorPage(map, req, thisForm);
        }

        con.setAutoCommit(false);

        if (cmd.equals("fil080back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm, req, con);

        } else if (cmd.equals("fil080add")) {
            //編集ボタンクリック
            forward = __doEdit(map, thisForm, req, res, con);

        } else if (cmd.equals("fil080delete")) {
            //削除ダイアログのOKボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("fil080changeCabinet")) {
            //キャビネットコンボ変更
            forward = __doChangeCabinet(map, thisForm, req, res, con);

        } else if (cmd.equals("changeDir")) {
            //登録先ディレクトリ変更
            String changeDirSid
                = NullDefault.getString(thisForm.getMoveToDir(),
                                        thisForm.getFil070ParentDirSid());

            thisForm.setFil010SelectDirSid(changeDirSid);
            thisForm.setFil070ParentDirSid(changeDirSid);
            thisForm.setSelectDir(changeDirSid);

            thisForm.setFil040SelectDelAll("0");
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
     * @throws Exception  実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Fil080Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        Fil080Biz biz = new Fil080Biz(con, reqMdl);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        if (form.getFil080InitFlg() == 0) {
            //テンポラリディレクトリの削除
            temp.deleteTempPath(getRequestModel(req), GSConstFile.PLUGIN_ID_FILE,
                    TEMP_DIRECTORY_ID);
            temp.createTempDir(getRequestModel(req), GSConstFile.PLUGIN_ID_FILE,
                    TEMP_DIRECTORY_ID);
        }

        //テンポラリディレクトリパスを取得
        String tempDir = temp.getTempPath(getRequestModel(req), GSConstFile.PLUGIN_ID_FILE,
                    TEMP_DIRECTORY_ID);

        //セッションユーザモデル
        BaseUserModel buMdl = getSessionUserModel(req);

        //初期表示を設定
        Fil080ParamModel paramMdl = new Fil080ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, tempDir, getAppRootPath(), buMdl);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        saveToken(req);

        return map.getInputForward();
    }
    /**
     * <br>[機  能] ディレクトリへの編集権限があるか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return エラーコード
     * @throws SQLException 実行例外
     */
    private int __checkEditDir(
            Fil080Form form,
            HttpServletRequest req,
            Connection con) throws SQLException {
        FilCommonBiz cmnBiz = new FilCommonBiz(getRequestModel(req), con);
        boolean targetParent = false;
        int dirSid = NullDefault.getInt(form.getFil070DirSid(), -1);
        if (dirSid < 1) {
            dirSid = NullDefault.getInt(form.getFil070ParentDirSid(), -1);
            targetParent = true;
        }

        FileCabinetModel fcbMdl = cmnBiz.getCabinetModel(dirSid);
        if (fcbMdl != null
        && fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON
        && fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
            //電帳法キャビネット かつ自動振り分け = あり の場合
            //キャビネット編集権限に対する編集権限チェック
            //※キャビネット編集権限チェックは事前に行われているため、判定OKとする
            return FilCommonBiz.ERR_NONE;
        }

        //ファイル保存先ディレクトリの編集権限チェック
        if (targetParent) {
            return cmnBiz.checkPowEditDir(dirSid, GSConstFile.DIRECTORY_FOLDER);
        } else {
            return cmnBiz.checkPowEditDir(dirSid, GSConstFile.DIRECTORY_FILE);
        }
    }

    /**
     * <br>[機  能] 遷移元画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイル操作時例外
     */
    private ActionForward __doBack(
            ActionMapping map, Fil080Form form, HttpServletRequest req, Connection con)
    throws SQLException, IOToolsException {

        ActionForward forward = null;

        RequestModel reqMdl = getRequestModel(req);
        Fil080Biz biz = new Fil080Biz(con, reqMdl);

        //テンポラリディレクトリのファイル削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),  GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

        if (form.getFil080Mode() == GSConstFile.MODE_ADD) {
            forward = map.findForward("fil040");
        } else {
            forward = map.findForward("fil070");
        }

        //セッションユーザモデル
        BaseUserModel buMdl = getSessionUserModel(req);

        //ディレクトリSID
        int dirSid = NullDefault.getInt(form.getFil070DirSid(), -1);

        //ロック区分を取得する。
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        if (dirSid >= 0 && filBiz.checkFileLock(dirSid)) {
            //ロックが有効の場合、編集キャンセルの為に無効にする
            Fil080ParamModel paramMdl = new Fil080ParamModel();
            paramMdl.setParam(form);
            biz.fileUnLock(paramMdl, buMdl.getUsrsid());
            paramMdl.setFormData(form);
        }

        return forward;
    }

    /**
     * <br>[機  能] 追加・編集ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception  実行時例外
     */
    private ActionForward __doEdit(ActionMapping map,
                                    Fil080Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        con.setAutoCommit(true);

        //ファイルロックチェック
        ActionForward forward = __checkFileLock(map, form, req, con);
        if (forward != null) {
            return forward;
        }

        // 2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        CommonBiz cmnBiz = new CommonBiz();

        // テンポラリディレクトリ
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

        boolean entryErrlFlg =
                form.getFil080Mode() == GSConstFile.MODE_ADD
                && form.getFil010DspCabinetKbn().equals(GSConstFile.DSP_CABINET_ERRL)
                && form.getFil040PersonalFlg() == 0;

        //仮登録、かつ自動振り分けの場合、キャビネットフォルダを登録先とする
        RequestModel reqMdl = getRequestModel(req);
        int usrSid = this.getSessionUserSid(req);

        Fil080ParamModel paramMdl = new Fil080ParamModel();
        paramMdl.setParam(form);

        Fil080Biz biz = new Fil080Biz(con, reqMdl, usrSid);
        if (entryErrlFlg) {
            form.setFil070ParentDirSid(biz.getErrlCabinetDirSid(paramMdl));
        }

        // 入力チェック
        ActionErrors errors = form.fil080validateCheck(tempDir, con,
                getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //仮登録ファイルの登録
        if (entryErrlFlg) {
            return __doInsertErrlFile(map, form, req, res, con);
        }

        // アプリケーションルートパス
        String appRootPath = getAppRootPath();
        // プラグイン設定
        PluginConfig plconf = getPluginConfig(req);
        PluginConfig pconfig = getPluginConfigForMain(plconf, con);
        boolean smailPluginUseFlg = cmnBiz
                .isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);

        // 採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        boolean commitFlg = false;
        int targetDirSid = -1;
        String targetLogDirPath = null;

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        try {
            // 登録処理
            // ログ出力用
            if (form.getFil080Mode() == GSConst.CMDMODE_EDIT) {
                targetLogDirPath = filBiz.getDirctoryPath(
                        NullDefault.getInt(paramMdl.getFil070DirSid(), -1));
            }
            targetDirSid = biz.registerData(paramMdl, tempDir, cntCon,
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
        String textEdit = gsMsg.getMessage(req, "cmn.change");
        String textEntry = gsMsg.getMessage(req, "cmn.entry");
        // ログ出力処理
        String opCode = "";

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "cmn.target"));
        sb.append("] ");

        if (form.getFil080Mode() == GSConst.CMDMODE_ADD) {
            opCode = textEntry;
            sb.append(filBiz.getDirctoryPath(targetDirSid));
        } else {
            opCode = textEdit;
            sb.append(targetLogDirPath);
            String editLogDirPath = filBiz.getDirctoryPath(targetDirSid);
            if (!targetLogDirPath.equals(editLogDirPath)) {
                sb.append("\r\n");
                sb.append("[");
                sb.append(gsMsg.getMessage(req, "fil.fil060kn.3"));
                sb.append("] ");
                sb.append(editLogDirPath);
            }
        }

        filBiz.outPutLog(opCode, GSConstLog.LEVEL_TRACE, sb.toString(),
                map.getType());

        // テンポラリディレクトリのファイルを削除する
        temp.deleteTempPath(getRequestModel(req),
                GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

        // 完了画面へ遷移
        return __setTourokuCompPageParam(map, req, form, null);
    }

    /**
     * <br>[機  能] ファイルの追加処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception  実行時例外
     */
    private ActionForward __doInsertErrlFile(
            ActionMapping map,
            Fil080Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        con.setAutoCommit(true);
        Fil080ParamModel paramMdl = new Fil080ParamModel();
        Fil080Biz biz = new Fil080Biz(con, getRequestModel(req), getSessionUserSid(req));
        // アプリケーションルートパス
        String appRootPath = getAppRootPath();

        // 採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

        boolean commitFlg = false;
        List<Integer> result = new ArrayList<Integer>();
        try {
            //登録処理
            paramMdl.setParam(form);
            result = biz.registerErrlData(paramMdl, tempDir, cntCon, appRootPath);
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
        String textEntry = gsMsg.getMessage(req, "cmn.entry");
        // ログ出力処理
        String opCode = "";

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "cmn.target"));
        sb.append("] ");

        opCode = textEntry;
        sb.append(biz.getErrlDirectoryPath(paramMdl));

        filBiz.outPutLog(opCode, GSConstLog.LEVEL_TRACE, sb.toString(),
                map.getType());

        // テンポラリディレクトリのファイルを削除する
        temp.deleteTempPath(getRequestModel(req),
                GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

        // 完了画面へ遷移
        return __setTourokuCompPageParam(map, req, form, result);
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Fil080Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws IOException, IOToolsException, SQLException, Exception {

        con.setAutoCommit(true);
        //ファイルロックチェック
        ActionForward forward = __checkFileLock(map, form, req, con);
        if (forward != null) {
            return forward;
        }

        //操作コメント 入力チェック
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        ActionErrors errors = new ActionErrors();
        GSValidateFile.validateTextarea(errors,
                                        form.getFil080OpeComment(),
                                        gsMsg.getMessage("fil.11"),
                                        GSConstFile.MAX_LENGTH_FILE_UP_CMT,
                                        false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        boolean commitFlg = false;
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        Fil080ParamModel paramMdl = new Fil080ParamModel();
        String dirPath;
        try {
            //ディレクトリを削除する。
            paramMdl.setParam(form);
            dirPath = filBiz.getDirctoryPath(
                    NullDefault.getInt(paramMdl.getFil070DirSid(), -1));

            filBiz.deleteDirectoryLogical(
                    Stream.of(form.getFil070DirSid())
                        .toArray(String[]::new),
                paramMdl.getFil080OpeComment());

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

        String textDel = gsMsg.getMessage(req, "cmn.delete");

        //ログ出力処理
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "cmn.target"));
        sb.append("] ");
        sb.append(dirPath);
        filBiz.outPutLog(
                textDel, GSConstLog.LEVEL_TRACE, sb.toString(), map.getType());

        //テンポラリディレクトリのファイル削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req), GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

        return __setCompPageParam(map, req, form);
    }

    /**
     * <br>[機  能] ファイルがロックされているか判定する。
     * <br>[解  説] 編集ユーザでロックされていない場合はロックする。
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException 実行例外
     * @return ActionForward
     */
    private boolean __checkFileLock(
        Fil080Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {
        boolean errorFlg = true;

        int dirSid = NullDefault.getInt(form.getFil070DirSid(), -1);
        if (dirSid == -1) {
            return errorFlg;
        }
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);

        //管理者設定ロック区分を取得する。
        int admLockKbn = filBiz.getLockKbnAdmin();
        form.setAdmLockKbn(admLockKbn);
        if (admLockKbn == GSConstFile.LOCK_KBN_OFF) {
            return errorFlg;
        }

        //ファイルにロックがかかっていないか判定する。
        errorFlg = filBiz.checkFileLock(dirSid);

        return errorFlg;
    }

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @return ActionForward アクションフォワード
     * @throws SQLException SQL実行時の例外
     */
    private ActionForward __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Fil080Form form,
        Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForwardOk = null;
        ActionForward urlForwardCancel = null;

        //メッセージセット
        String msgState = null;

        msgState = "sakujo.kakunin.once";
        if (form.getFil010DspCabinetKbn().equals(GSConstFile.DSP_CABINET_ERRL)
                && form.getFil040PersonalFlg() == 0) {
            // 10年経過していないか
            FileDirectoryDao dirDao = new FileDirectoryDao(con);
            boolean addMessageFlg = false;
            String[] sidList = new String[1];
            sidList[0] = (String.valueOf(form.getFil070DirSid()));
            List<FileDirectoryModel> dirList = dirDao.getNewDirectoryList(sidList);
            UDate checkDate = new UDate();
            checkDate.addYear(-10);
            if (dirList != null) {
                for (FileDirectoryModel mdl : dirList) {
                    if (checkDate.compare(checkDate, mdl.getFdrEdate()) > UDate.SMALL) {
                        addMessageFlg = true;
                    }
                }
            }
            if (addMessageFlg) {
                msgState = "delete.kakunin.errl";
            }
        }

        GsMessage gsMsg = new GsMessage();
        String textFile = gsMsg.getMessage(req, "cmn.file");

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForwardCancel = map.findForward("fil080");
        urlForwardOk = map.findForward("fil080deleteOk");
        cmn999Form.setUrlOK(urlForwardOk.getPath());
        cmn999Form.setUrlCancel(urlForwardCancel.getPath());

        cmn999Form.setMessage(msgRes.getMessage(msgState, textFile));

        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("backDspLow", form.getBackDspLow());
        cmn999Form.addHiddenParam("admVerKbn", form.getAdmVerKbn());
        cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
        cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
        cmn999Form.addHiddenParam("fil010DspCabinetKbn", form.getFil010DspCabinetKbn());
        cmn999Form.addHiddenParam("fil040PersonalFlg", form.getFil040PersonalFlg());
        cmn999Form.addHiddenParam("fil050SortKey", form.getFil050SortKey());
        cmn999Form.addHiddenParam("fil050OrderKey", form.getFil050OrderKey());
        cmn999Form.addHiddenParam("fil050SltDirSid", form.getFil050SltDirSid());
        cmn999Form.addHiddenParam("fil050SltDirVer", form.getFil050SltDirVer());
        cmn999Form.addHiddenParam("fil050DirSid", form.getFil050DirSid());

        cmn999Form.addHiddenParam("fil070DirSid", form.getFil070DirSid());
        cmn999Form.addHiddenParam("fil070ParentDirSid", form.getFil070ParentDirSid());

        cmn999Form.addHiddenParam("fil080PluginId", form.getFil080PluginId());
        cmn999Form.addHiddenParam("fil080Mode", form.getFil080Mode());
        cmn999Form.addHiddenParam("fil080VerKbn", form.getFil080VerKbn());
        cmn999Form.addHiddenParam("fil080Biko", form.getFil080Biko());
        cmn999Form.addHiddenParam("fil080UpCmt", form.getFil080UpCmt());
        cmn999Form.addHiddenParam("fil080PluralKbn", form.getFil080PluralKbn());
        cmn999Form.addHiddenParam("fil080SvPluralKbn", form.getFil080SvPluralKbn());
        cmn999Form.addHiddenParam("fil080TradeDate", form.getFil080TradeDate());
        cmn999Form.addHiddenParam("fil080TradeTarget", form.getFil080TradeTarget());
        cmn999Form.addHiddenParam("fil080TradeMoney", form.getFil080TradeMoney());
        cmn999Form.addHiddenParam("fil080TradeMoneyKbn", form.getFil080TradeMoneyKbn());
        cmn999Form.addHiddenParam("fil080TradeMoneyType", form.getFil080TradeMoneyType());

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

        cmn999Form.addHiddenParam("backDspCall", form.getBackDspCall());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward アクションフォワード
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Fil080Form form) throws IOToolsException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        if (form.getBackDspCall() == null || form.getBackDspCall().equals("")) {
            urlForward = map.findForward("fil040");
        } else {
            urlForward = map.findForward("fil240");
        }
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "sakujo.kanryo.object";

        GsMessage gsMsg = new GsMessage();
        String textFile = gsMsg.getMessage(req, "cmn.file");

        cmn999Form.setMessage(msgRes.getMessage(msgState, textFile));

        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("backDspLow", form.getBackDspLow());
        cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
        cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
        cmn999Form.addHiddenParam("fil010DspCabinetKbn", form.getFil010DspCabinetKbn());
        cmn999Form.addHiddenParam("fil070DirSid", form.getFil070DirSid());
        cmn999Form.addHiddenParam("fil070ParentDirSid", form.getFil070ParentDirSid());

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

        cmn999Form.addHiddenParam("backDspCall", form.getBackDspCall());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>
     * [機 能] 登録完了メッセージ画面遷移時のパラメータを設定 <br>
     * [解 説] <br>
     * [備 考]
     *
     * @param map
     *            マッピング
     * @param req
     *            リクエスト
     * @param form
     *            アクションフォーム
     * @param errlFile 追加した仮登録ファイル
     * @return ActionForward アクションフォワード
     * @throws IOToolsException
     *             添付ファイルの操作に失敗
     */
    private ActionForward __setTourokuCompPageParam(ActionMapping map, HttpServletRequest req,
            Fil080Form form, List<Integer> errlFile) throws IOToolsException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        GsMessage gsMsg = new GsMessage();

        // メッセージセット
        String msgState = null;
        if (form.getFil080Mode() == GSConstFile.MODE_ADD) {
            if (form.getFil010DspCabinetKbn().equals(GSConstFile.DSP_CABINET_ERRL)
                    && form.getFil040PersonalFlg() == 0) {
                if (form.getFil080webmail() == 1) {
                    msgState = "touroku.kanryo.errl.webmail";
                } else {
                    msgState = "touroku.kanryo.errl";
                    cmn999Form.setOkBtnValue(gsMsg.getMessage(req, "wml.12"));
                    cmn999Form.setCancelBtnValue(gsMsg.getMessage(req, "cmn.entry.later"));
                }
                urlForward = map.findForward("fil300");
            } else {
                msgState = "touroku.kanryo.object";
                urlForward = map.findForward("fil040");
            }
        } else {
            msgState = "hensyu.kanryo.object";
            urlForward = map.findForward("fil070");
        }

        if (form.getFil080webmail() == 1) {
            // WEBメールからの呼び出し
            cmn999Form = new Cmn999Form(
                    "javascript:window.parent.webmailEntrySubWindowClose();");
            cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
            cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);
            cmn999Form.setType(Cmn999Form.TYPE_OK);
        } else {
            if (form.getFil080Mode() == GSConstFile.MODE_ADD
                    && form.getFil010DspCabinetKbn().equals(GSConstFile.DSP_CABINET_ERRL)
                    && form.getFil040PersonalFlg() == 0) {
                cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
                cmn999Form.setUrlOK(urlForward.getPath());
                cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
                cmn999Form.addHiddenParam("fil300BeforeDspFlg", GSConstFile.BEFORE_DSP_FIL080);
                ArrayList<String> resultStr = (ArrayList<String>) errlFile.stream()
                        .map(mdl -> String.valueOf(mdl))
                        .collect(Collectors.toList());
                cmn999Form.addHiddenParam("fil300BeforeInsertFile", resultStr);
                urlForward = map.findForward("fil040");
                cmn999Form.setUrlCancel(urlForward.getPath());
            } else {
                cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
                cmn999Form.setUrlOK(urlForward.getPath());
                cmn999Form.setType(Cmn999Form.TYPE_OK);
            }
        }
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);

        String textFile = gsMsg.getMessage(req, "cmn.file");

        cmn999Form.setMessage(msgRes.getMessage(msgState, textFile));

        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("backDspLow", form.getBackDspLow());
        cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
        cmn999Form.addHiddenParam("fil010SelectDirSid",
                form.getFil010SelectDirSid());
        cmn999Form.addHiddenParam("fil010SelectCabinet",
                form.getFil010SelectCabinet());
        cmn999Form.addHiddenParam("fil010DspCabinetKbn", form.getFil010DspCabinetKbn());

        cmn999Form.addHiddenParam("fil040PersonalFlg",
                form.getFil040PersonalFlg());
        cmn999Form.addHiddenParam("fil040PersonalCabOwnerSid",
                form.getFil040PersonalCabOwnerSid());
        cmn999Form.addHiddenParam("fil040PersonalCabOwnerName",
                form.getFil040PersonalCabOwnerName());

        cmn999Form.addHiddenParam("fil070DirSid", form.getFil070DirSid());
        cmn999Form.addHiddenParam("fil070ParentDirSid",
                form.getFil070ParentDirSid());

        cmn999Form.addHiddenParam("fil100ChkTrgFolder",
                form.getFil100ChkTrgFolder());
        cmn999Form.addHiddenParam("fil100ChkTrgFile",
                form.getFil100ChkTrgFile());
        cmn999Form.addHiddenParam("fil100SearchMode",
                form.getFil100SearchMode());
        cmn999Form.addHiddenParam("fil100ChkWdTrgName",
                form.getFil100ChkWdTrgName());
        cmn999Form.addHiddenParam("fil100ChkWdTrgBiko",
                form.getFil100ChkWdTrgBiko());
        cmn999Form.addHiddenParam("fil100ChkWdTrgText",
                form.getFil100ChkWdTrgText());
        cmn999Form.addHiddenParam("fileSearchfromYear",
                form.getFileSearchfromYear());
        cmn999Form.addHiddenParam("fileSearchfromMonth",
                form.getFileSearchfromMonth());
        cmn999Form.addHiddenParam("fileSearchfromDay",
                form.getFileSearchfromDay());
        cmn999Form.addHiddenParam("fileSearchtoYear",
                form.getFileSearchtoYear());
        cmn999Form.addHiddenParam("fileSearchtoMonth",
                form.getFileSearchtoMonth());
        cmn999Form.addHiddenParam("fileSearchtoDay", form.getFileSearchtoDay());
        cmn999Form.addHiddenParam("fil100ChkOnOff", form.getFil100ChkOnOff());
        cmn999Form.addHiddenParam("fil100SvSltCabinetSid",
                form.getFil100SvSltCabinetSid());
        cmn999Form.addHiddenParam("fil100SvChkTrgFolder",
                form.getFil100SvChkTrgFolder());
        cmn999Form.addHiddenParam("fil100SvChkTrgFile",
                form.getFil100SvChkTrgFile());
        cmn999Form.addHiddenParam("fil100SvChkTrgDeleted",
                form.getFil100SvChkTrgDeleted());
        cmn999Form.addHiddenParam("fil100SvChkTrgDeletedFolder",
                form.getFil100SvChkTrgDeletedFolder());
        cmn999Form.addHiddenParam("fil100SvSearchMode",
                form.getFil100SvSearchMode());
        cmn999Form.addHiddenParam("fil100SvChkWdTrgName",
                form.getFil100SvChkWdTrgName());
        cmn999Form.addHiddenParam("fil100SvChkWdTrgBiko",
                form.getFil100SvChkWdTrgBiko());
        cmn999Form.addHiddenParam("fil100SvChkWdTrgText",
                form.getFil100SvChkWdTrgText());
        cmn999Form.addHiddenParam("fil100SvChkWdKeyWord",
                form.getFil100SvChkWdKeyWord());
        cmn999Form.addHiddenParam("fileSvSearchfromYear",
                form.getFileSvSearchfromYear());
        cmn999Form.addHiddenParam("fileSvSearchfromMonth",
                form.getFileSvSearchfromMonth());
        cmn999Form.addHiddenParam("fileSvSearchfromDay",
                form.getFileSvSearchfromDay());
        cmn999Form.addHiddenParam("fileSvSearchtoYear",
                form.getFileSvSearchtoYear());
        cmn999Form.addHiddenParam("fileSvSearchtoMonth",
                form.getFileSvSearchtoMonth());
        cmn999Form.addHiddenParam("fileSvSearchtoDay",
                form.getFileSvSearchtoDay());
        cmn999Form.addHiddenParam("fil100SvChkOnOff",
                form.getFil100SvChkOnOff());
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
        Fil080Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        FilCommonBiz cmnBiz = new FilCommonBiz(getRequestModel(req), con);
        int dirSid = NullDefault.getInt(form.getFil070DirSid(), -1);
        int fcbSid = cmnBiz.getCabinetSid(dirSid);

        //ディレクトリが未選択の場合、ディレクトリSIDを設定
        if (NullDefault.getInt(form.getFil010SelectDirSid(), -1) < 0) {
            form.setFil010SelectDirSid(String.valueOf(dirSid));
        }

        //キャビネットが未選択の場合、キャビネットSIDを設定
        if (NullDefault.getInt(form.getFil010SelectCabinet(), -1) < 0) {
            form.setFil010SelectCabinet(String.valueOf(fcbSid));
        } else {
            fcbSid = NullDefault.getInt(form.getFil010SelectCabinet(), -1);
        }

        //キャビネットにファイルを登録可能か確認
        Fil080Biz biz = new Fil080Biz(con, getRequestModel(req));
        return biz.canFileEntryCabinet(fcbSid);
    }


    /**
     * <br>[機  能] ファイルがロックされているか判定する。
     * <br>[解  説] 編集ユーザでロックされていない場合はロックする。
     * <br>[備  考]
     * @param map マップ
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException 実行例外
     * @return ActionForward
     */
    private ActionForward __checkFileLock(
        ActionMapping map,
        Fil080Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {
        ActionForward forward = null;
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);

        if (form.getFil080Mode() == GSConstFile.MODE_ADD) {
            return forward;
        }

        //管理者設定ロック区分を取得する。
        int admLockKbn = filBiz.getLockKbnAdmin();
        form.setAdmLockKbn(admLockKbn);
        if (admLockKbn == GSConstFile.LOCK_KBN_OFF) {
            //ロック機能が無効の場合
            return forward;
        }

        //セッションユーザモデル
        BaseUserModel buMdl = getSessionUserModel(req);
        int usrSid = buMdl.getUsrsid();

        //ディレクトリSID
        int dirSid = NullDefault.getInt(form.getFil070DirSid(), -1);

        FileFileBinDao fileBinDao = new FileFileBinDao(con);
        FileFileBinModel fileBinModel = fileBinDao.getNewFile(dirSid);

        if (fileBinModel == null) {
            //ディレクトリ情報が存在しない場合
            return getSubmitErrorPage(map, req);
        }

        //ロック区分ＯＦＦならばエラー
        if (fileBinModel.getFflLockKbn() == GSConstFile.LOCK_KBN_OFF) {
            return __getFileUnlockErrorPage(map, req, form);
        }

        //ログインユーザ以外がロックしている場合
        if (fileBinModel.getFflLockKbn() == GSConstFile.LOCK_KBN_ON
                && fileBinModel.getFflLockUser() != usrSid) {
            //編集ユーザがログインユーザと異なった場合
            return __getFileLockErrorPage(map, req, form);
        }

        return forward;
    }

    /**
     * <br>[機  能] キャビネットコンボ変更時の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception  実行時例外
     */
    private ActionForward __doChangeCabinet(ActionMapping map,
                                    Fil080Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception  {

        Fil080Biz biz = new Fil080Biz(con, getRequestModel(req));

        Fil080ParamModel paramMdl = new Fil080ParamModel();
        paramMdl.setParam(form);
        biz.setChangeCabinet(paramMdl, con);
        paramMdl.setFormData(form);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] WEBメール連携
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
    private ActionForward __doCalledWebmail(
        ActionMapping map,
        Fil080Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //対象メールを閲覧可能かを判定
        WmlDao wmlDao = new WmlDao(con);
        if (!wmlDao.canReadMail(form.getFil080webmailId(), getSessionUserSid(req))) {
            return getAuthErrorPageWithPopup(map, req);
        }

        //追加可能なキャビネットを設定
        RequestModel reqMdl = getRequestModel(req);
        //WEBメールの添付ファイル情報を設定
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req), GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);
        temp.createTempDir(getRequestModel(req), GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);
        String tempDir = temp.getTempPath(getRequestModel(req), GSConstFile.PLUGIN_ID_FILE,
                TEMP_DIRECTORY_ID);

        form.setFil080InitFlg(1);
        Fil080ParamModel paramMdl = new Fil080ParamModel();
        paramMdl.setParam(form);
        Fil080Biz biz = new Fil080Biz(con, reqMdl);
        biz.setWebmailData(paramMdl, getAppRootPath(), tempDir);
        paramMdl.setFormData(form);

        form.setFil080webmail(1);
        return null;
    }

    /**
     * <br>[機  能] ファイルロックエラーメッセージ画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward アクションフォワード
     */
    private ActionForward __getFileLockErrorPage(
            ActionMapping map, HttpServletRequest req, Fil080Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("fil070");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "error.file.lock";

        cmn999Form.setMessage(msgRes.getMessage(msgState));

        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("backDspLow", form.getBackDspLow());
        cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
        cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());

        cmn999Form.addHiddenParam("fil070DirSid", form.getFil070DirSid());
        cmn999Form.addHiddenParam("fil070ParentDirSid", form.getFil070ParentDirSid());

        cmn999Form.addHiddenParam("fil100ChkTrgFolder", form.getFil100ChkTrgFolder());
        cmn999Form.addHiddenParam("fil100ChkTrgFile", form.getFil100ChkTrgFile());
        cmn999Form.addHiddenParam("fil100SearchMode", form.getFil100SearchMode());
        cmn999Form.addHiddenParam("fil100ChkWdTrgName", form.getFil100ChkWdTrgName());
        cmn999Form.addHiddenParam("fil100ChkWdTrgBiko", form.getFil100ChkWdTrgBiko());
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
     * <br>[機  能] ファイルロック解除エラーメッセージ画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward アクションフォワード
     */
    private ActionForward __getFileUnlockErrorPage(
        ActionMapping map, HttpServletRequest req, Fil080Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("fil040");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "error.file.unlock";

        cmn999Form.setMessage(msgRes.getMessage(msgState));

        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("backDspLow", form.getBackDspLow());
        cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
        cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
        cmn999Form.addHiddenParam("fil070DirSid", form.getFil070DirSid());
        cmn999Form.addHiddenParam("fil070ParentDirSid", form.getFil070ParentDirSid());

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