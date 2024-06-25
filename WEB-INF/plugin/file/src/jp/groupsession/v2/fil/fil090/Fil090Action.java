package jp.groupsession.v2.fil.fil090;

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
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] フォルダ・ファイル移動画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil090Action extends AbstractFileAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil090Action.class);

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

        log__.debug("fil090Action START");

        ActionForward forward = null;
        Fil090Form thisForm = (Fil090Form) form;

        con.setAutoCommit(true);
        //キャビネットアクセス権限チェック
        if (!__checkViewCabinet(thisForm, req, con)) {
            return getCanNotViewErrorPage(map, req);
        }
        //選択ファイル権限チェック
        if (!thisForm.checkSltDirAccess(con, getRequestModel(req))) {
            GsMessage gsMsg = new GsMessage(req);
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.edit"),
                    gsMsg.getMessage("cmn.move"));
        }

        con.setAutoCommit(false);

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        if (cmd.equals("fil090back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm, req);

        } else if (cmd.equals("fil090move")) {
            //移動ボタンクリック
            forward = __doMove(map, thisForm, req, res, con);

        } else if (cmd.equals("detailDir")) {
            //移動先フォルダ選択
            forward = __doSelectDir(map, thisForm, req, res, con);

        } else if (cmd.equals("changeDir")) {
            //移動先フォルダ選択
            forward = __doSelectDir(map, thisForm, req, res, con);

        } else if (cmd.equals("fileDownload")) {
            //添付ファイル名クリック
            forward = __doDownLoad(
                    map, thisForm, req, res, con, GSConstCommon.FILE_DOWNLOAD);

        } else if (cmd.equals("fileDownloadInline")) {
            //プレビュー用
            forward = __doDownLoad(
                    map, thisForm, req, res, con, GSConstCommon.FILE_DOWNLOAD_INLINE);

        } else if (cmd.equals("selectCabinet")) {
            //キャビネット選択
            forward = __doSelectCab(map, thisForm, req, res, con);
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
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Fil090Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, IOToolsException, IOException, TempFileException {

        con.setAutoCommit(true);
        //セッションユーザモデル
        BaseUserModel buMdl = getSessionUserModel(req);

        //初期表示を設定する。
        Fil090Biz biz = new Fil090Biz(getRequestModel(req), con);

        Fil090ParamModel paramMdl = new Fil090ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, buMdl);
        paramMdl.setFormData(form);

        if ((form.getFil090FileLabelList() == null
                || form.getFil090FileLabelList().size() == 0)
                && (form.getFil090FolderNameList() == null
                || form.getFil090FolderNameList().size() == 0)
                && (form.getFil090DirName() == null)) {
            // 移動元ファイル/フォルダが存在しなかった場合、エラー
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);

        saveToken(req);

        return map.getInputForward();
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
     * @throws IOToolsException ファイル操作時例外
     */
    private ActionForward __doBack(ActionMapping map, Fil090Form form, HttpServletRequest req)
    throws IOToolsException {

        ActionForward forward = null;
        forward = map.findForward("fil040");

        return forward;
    }

    /**
     * <br>[機  能] 移動確認画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doMove(ActionMapping map,
            Fil090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
    throws SQLException, IOToolsException, IOException, TempFileException {

        ActionErrors errors = null;
        con.setAutoCommit(true);
        BaseUserModel buMdl = getSessionUserModel(req);
        //入力チェック
        errors = form.fil090validateCheck(con, getRequestModel(req), buMdl.getUsrsid());

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        } else {
            form.setSelectDir(form.getMoveToDir());
        }
        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        Fil090Biz biz = new Fil090Biz(getRequestModel(req), con);

        //セッションユーザモデル

        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        boolean commitFlg = false;
        con.setAutoCommit(false);
        Fil090ParamModel paramMdl = new Fil090ParamModel();
        String targetDirPath = "";
        try {
            paramMdl.setParam(form);

            FileDirectoryDao dirDao = new FileDirectoryDao(con);
            if (form.getFil090SelectPluralKbn() == Fil090Biz.MOVE_PLURAL_NO) {
                // ログ出力用親ディレクトリパス取得
                int targetDirSid = NullDefault.getInt(paramMdl.getFil090DirSid(), -1);
                FileDirectoryModel myDir = dirDao.getNewDirectory(targetDirSid);
                if (myDir != null) {
                    targetDirPath = filBiz.getDirctoryPath(
                            myDir.getFdrParentSid());
                }
                //ディレクトリ移動処理（単一移動）
                biz.moveDir(paramMdl, buMdl);
            } else {
                // ログ出力用親ディレクトリパス取得
                String[] strDirSids = paramMdl.getFil040SelectDel();
                int targetDirSid = -1;
                if (strDirSids != null && strDirSids.length > 0) {
                    targetDirSid = NullDefault.getInt(strDirSids[0], -1);
                }
                FileDirectoryModel myDir = dirDao.getNewDirectory(targetDirSid);
                if (myDir != null) {
                    targetDirPath = filBiz.getDirctoryPath(
                            myDir.getFdrParentSid());
                }

                //ディレクトリ移動処理（一括移動）
                biz.moveDirPlural(paramMdl, buMdl, getAppRootPath());

            }
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

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String textEdit = gsMsg.getMessage("cmn.change");

        //ログ出力処理

        filBiz.outPutLog(
                textEdit, GSConstLog.LEVEL_TRACE,
                biz.valueOpLog(paramMdl, targetDirPath), map.getType());

        return __setCompPageParam(map, req, form);
    }


    /**
     * <br>[機  能] キャビネットを選択する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doSelectCab(ActionMapping map,
            Fil090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
    throws SQLException, IOToolsException, IOException, TempFileException {
        ActionErrors errors = null;
        con.setAutoCommit(true);
        BaseUserModel buMdl = getSessionUserModel(req);
        //入力チェック
        errors = form.fil090validateCabSelectCheck(
                con,
                getRequestModel(req),
                buMdl);

        if (!errors.isEmpty()) {
            addErrors(req, errors);

        }
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] キャビネットを選択する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doSelectDir(ActionMapping map,
            Fil090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
    throws SQLException, IOToolsException, IOException, TempFileException {
        ActionErrors errors = null;
        con.setAutoCommit(true);
        BaseUserModel buMdl = getSessionUserModel(req);
        //入力チェック
        errors = form.fil090validateDirbSelectCheck(
                con,
                getRequestModel(req),
                buMdl.getUsrsid());

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setMoveToDir(null);
        }
        form.setSelectDir(form.getMoveToDir());
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
    private ActionForward __doDownLoad(
        ActionMapping map,
        Fil090Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int downloadKbn) throws SQLException, Exception {

        Long binSid = NullDefault.getLong(form.getFileSid(), -1);
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);

        //ファイルがダウンロード可能かチェックする
        if (!filBiz.isDownloadAuthUser(binSid)) {
            return getCanNotViewErrorPage(map, req);
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
        Fil090Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {
        boolean errorFlg = true;

        FilCommonBiz cmnBiz = new FilCommonBiz(getRequestModel(req), con);
        int dirSid = NullDefault.getInt(form.getFil090DirSid(), -1);
        int fcbSid = cmnBiz.getCabinetSid(dirSid);

        //ディレクトリが未選択の場合、ディレクトリSIDを設定
        if (NullDefault.getInt(form.getFil010SelectDirSid(), -1) < 0) {
            form.setFil010SelectDirSid(String.valueOf(dirSid));
        }

        //キャビネットが未選択の場合、キャビネットSIDを設定
        if (NullDefault.getInt(form.getFil010SelectCabinet(), -1) < 0) {
            form.setFil010SelectCabinet(String.valueOf(fcbSid));
        }

        //キャビネットへのアクセス権限があるか判定する。
        errorFlg = cmnBiz.isAccessAuthUser(
                                        NullDefault.getInt(form.getFil010SelectCabinet(), fcbSid));
        return errorFlg;
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward アクションフォワード
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Fil090Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("fil040");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;

        msgState = "cmn.kanryo.object";

        GsMessage gsMsg = new GsMessage();
        String textFolder = gsMsg.getMessage(req, "cmn.folder");
        String textFile = gsMsg.getMessage(req, "cmn.file");

        String dir = "";
        if (form.getFil090Mode().equals(String.valueOf(GSConstFile.DIRECTORY_FOLDER))) {
            dir = textFolder;
        } else {
            dir = textFile;
        }

        cmn999Form.setMessage(msgRes.getMessage(msgState, gsMsg.getMessage(req, "fil.move", dir)));

        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}
