package jp.groupsession.v2.fil.fil060;

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

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] フォルダ登録画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil060Action extends AbstractFileAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil060Action.class);

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

        log__.debug("fil060Action START");

        ActionForward forward = null;
        Fil060Form thisForm = (Fil060Form) form;

        //キャビネットアクセス権限チェック
        con.setAutoCommit(true);
        if (!__checkViewCabinet(thisForm, req, con)) {
            return getCanNotViewErrorPage(map, req);
        }
        //ディレクトリアクセスチェック
        int ecode = __checkEditDir(thisForm, req, con);
        if (ecode == FilCommonBiz.ERR_NONE_CAB_EDIT_POWER) {
            GsMessage gsMsg = new GsMessage(req);
            String actName = "";
            if (NullDefault.getInt(thisForm.getFil050DirSid(), -1) != -1) {
                actName = gsMsg.getMessage("cmn.edit");
            } else {
                actName = gsMsg.getMessage("fil.39");
            }
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.edit"),
                    actName);

        }
        if (ecode == FilCommonBiz.ERR_NOT_EXIST) {
            GsMessage gsMsg = new GsMessage(req);
            String targetName = "";
            targetName = gsMsg.getMessage("cmn.folder");
            return getCanNotViewNonePowerErrorPage(map, req, targetName);
        }
        if (ecode == FilCommonBiz.ERR_NOT_DIRKBN) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil060back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm, req);

        } else if (cmd.equals("fil060add")) {
            //フォルダ追加ボタンクリック
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("fil060delete")) {
            //削除ボタンクリック
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteOk")) {
            //ディレクトリ削除確認OKボタンクリック
            forward = __doDeleteOk(map, thisForm, req, res, con);

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
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Fil060Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        con.setAutoCommit(true);
        Fil060Biz biz = new Fil060Biz(con, getRequestModel(req));

        //セッションユーザモデル
        BaseUserModel buMdl = getSessionUserModel(req);

        //初期表示を設定
        Fil060ParamModel paramMdl = new Fil060ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, getAppRootPath(), buMdl);
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
            Fil060Form form,
            HttpServletRequest req,
            Connection con) throws SQLException {
        FilCommonBiz cmnBiz = new FilCommonBiz(getRequestModel(req), con);
        int dirSid = NullDefault.getInt(form.getFil050DirSid(), -1);
        if (dirSid < 1) {
            dirSid = NullDefault.getInt(form.getFil050ParentDirSid(), -1);
        }
        return cmnBiz.checkPowEditDir(dirSid, GSConstFile.DIRECTORY_FOLDER);
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doOk(ActionMapping map,
                                    Fil060Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        RequestModel reqMdl = getRequestModel(req);
        //入力チェック
        ActionErrors errors = form.fil060validateCheck(con, reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //セッションユーザモデル
        BaseUserModel buMdl = getSessionUserModel(req);

        //アプリケーションルートパス
        String appRootPath = getAppRootPath();

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        boolean commitFlg = false;

        Fil060ParamModel paramMdl = new Fil060ParamModel();
        int targetDirSid = -1;
        String targetLogDirPath = null;
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        Fil060Biz biz = new Fil060Biz(con, getRequestModel(req));
        try {
            //登録編集処理
            paramMdl.setParam(form);
            //ログ出力用
            if (form.getFil060CmdMode() == GSConst.CMDMODE_EDIT) {
                targetLogDirPath = filBiz.getDirctoryPath(
                        NullDefault.getInt(paramMdl.getFil050DirSid(), -1), true);
            }
            targetDirSid = biz.registerData(paramMdl, buMdl, cntCon, appRootPath);
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
        //ログ出力処理

        String opCode = "";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "cmn.target"));
        sb.append("] ");

        if (form.getFil060CmdMode() == GSConst.CMDMODE_ADD) {
            opCode = textEntry;
            sb.append(filBiz.getDirctoryPath(
                    targetDirSid, true));
        } else {
            opCode = textEdit;
            sb.append(targetLogDirPath);
            String editLogDirPath = filBiz.getDirctoryPath(
                    targetDirSid, true);
            if (!targetLogDirPath.equals(editLogDirPath)) {
                sb.append("\r\n");
                sb.append("[");
                sb.append(gsMsg.getMessage(req, "fil.fil060.3"));
                sb.append("] ");
                sb.append(editLogDirPath);
            }
        }


        filBiz.outPutLog(
                opCode, GSConstLog.LEVEL_TRACE, sb.toString(), map.getType());

        //完了画面へ遷移
        return __setTourokuCompPageParam(map, req, form);
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
    private ActionForward __doBack(ActionMapping map, Fil060Form form, HttpServletRequest req)
    throws IOToolsException {

        ActionForward forward = null;
        if (form.getFil060CmdMode() == GSConstFile.MODE_ADD) {
            forward = map.findForward("fil040");
        } else {
            forward = map.findForward("fil050");
        }

        return forward;
    }

    /**
     * <br>[機  能] フォルダ削除ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception  実行時例外
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Fil060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        con.setAutoCommit(true);
        //入力チェック
        ActionErrors errors = form.checkDelFolder(con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        return __setKakuninPageParam(map, req, form, con);
    }

    /**
     * <br>[機  能] 削除OKボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws SQLException 実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteOk(
        ActionMapping map,
        Fil060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws IOException, IOToolsException, SQLException {

        Fil060Biz biz = new Fil060Biz(con, getRequestModel(req));

        //セッションユーザモデル
        RequestModel reqMdl = getRequestModel(req);
        BaseUserModel buMdl = getSessionUserModel(req);

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        boolean commitFlg = false;
        Fil060ParamModel paramMdl = new Fil060ParamModel();
        String dirPath;
        try {
            //ディレクトリを削除する。
            paramMdl.setParam(form);
            dirPath = filBiz.getDirctoryPath(
                    NullDefault.getInt(paramMdl.getFil050DirSid(), -1), true);
            biz.deleteDirectory(paramMdl, buMdl.getUsrsid());
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
        String textDel = gsMsg.getMessage(req, "cmn.delete");

        //ログ出力処理
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "cmn.target"));
        sb.append("] ");
        sb.append(dirPath);

        filBiz.outPutLog(
                textDel, GSConstLog.LEVEL_TRACE, sb.toString(), map.getType());

        return __setCompPageParam(map, req, form);
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
        Fil060Form form,
        Connection con) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForwardOk = null;
        ActionForward urlForwardCancel = null;

        //メッセージセット
        String msgState = null;

        msgState = "sakujo.kakunin.once";

        GsMessage gsMsg = new GsMessage();
        String textFolder = gsMsg.getMessage(req, "cmn.folder");

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForwardCancel = map.findForward("fil060");
        urlForwardOk = map.findForward("fil060deleteOk");
        cmn999Form.setUrlOK(urlForwardOk.getPath());
        cmn999Form.setUrlCancel(urlForwardCancel.getPath());

        cmn999Form.setMessage(msgRes.getMessage(msgState, textFolder));

        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("backDspLow", form.getBackDspLow());
        cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
        cmn999Form.addHiddenParam("fil010DspCabinetKbn", form.getFil010DspCabinetKbn());
        cmn999Form.addHiddenParam("fil050SortKey", form.getFil050SortKey());
        cmn999Form.addHiddenParam("fil050OrderKey", form.getFil050OrderKey());
        cmn999Form.addHiddenParam("fil050SltDirSid", form.getFil050SltDirSid());
        cmn999Form.addHiddenParam("fil050SltDirVer", form.getFil050SltDirVer());
        cmn999Form.addHiddenParam("fil050DirSid", form.getFil050DirSid());
        cmn999Form.addHiddenParam("fil050ParentDirSid", form.getFil050ParentDirSid());

        cmn999Form.addHiddenParam("fil060DirName", form.getFil060DirName());
        cmn999Form.addHiddenParam("fil060Biko", form.getFil060Biko());
        cmn999Form.addHiddenParam("fil060PluginId", form.getFil060PluginId());

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
        cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
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
     * @return ActionForward アクションフォワード
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Fil060Form form) throws IOToolsException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("fil040");

        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "sakujo.kanryo.object";

        GsMessage gsMsg = new GsMessage();
        String textFolder = gsMsg.getMessage(req, "cmn.folder");

        cmn999Form.setMessage(msgRes.getMessage(msgState, textFolder));

        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("backDspLow", form.getBackDspLow());
        cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
        cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
        cmn999Form.addHiddenParam("fil010DspCabinetKbn", form.getFil010DspCabinetKbn());

        cmn999Form.addHiddenParam("fil050SortKey", form.getFil050SortKey());
        cmn999Form.addHiddenParam("fil050OrderKey", form.getFil050OrderKey());
        cmn999Form.addHiddenParam("fil050DirSid", form.getFil050DirSid());

        cmn999Form.addHiddenParam("fil100ChkTrgFolder", form.getFil100ChkTrgFolder());
        cmn999Form.addHiddenParam("fil100ChkTrgFile", form.getFil100ChkTrgFile());
        cmn999Form.addHiddenParam("fil100ChkTrgDeletedFolder", form.getFil100ChkTrgDeletedFolder());
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
        cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
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
     * @return ActionForward アクションフォワード
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    private ActionForward __setTourokuCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Fil060Form form) throws IOToolsException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        if (form.getFil060CmdMode() == GSConstFile.MODE_ADD) {
            urlForward = map.findForward("fil040");
        } else {
            urlForward = map.findForward("fil050");
        }
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;

        if (form.getFil060CmdMode() == GSConstFile.MODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else {
            msgState = "hensyu.kanryo.object";
        }

        GsMessage gsMsg = new GsMessage();
        String textFolder = gsMsg.getMessage(req, "cmn.folder");

        cmn999Form.setMessage(msgRes.getMessage(msgState, textFolder));

        cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
        cmn999Form.addHiddenParam("backDspLow", form.getBackDspLow());
        cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
        cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
        cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
        cmn999Form.addHiddenParam("fil040PersonalFlg", form.getFil040PersonalFlg());
        cmn999Form.addHiddenParam("fil040PersonalCabOwnerSid", form.getFil040PersonalCabOwnerSid());
        cmn999Form.addHiddenParam("fil040PersonalCabOwnerName",
                                                        form.getFil040PersonalCabOwnerName());
        cmn999Form.addHiddenParam("fil050SortKey", form.getFil050SortKey());
        cmn999Form.addHiddenParam("fil050OrderKey", form.getFil050OrderKey());
        cmn999Form.addHiddenParam("fil050DirSid", form.getFil050DirSid());

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
        cmn999Form.addHiddenParam("fil100SltCabinetKbn", form.getFil100SltCabinetKbn());
        cmn999Form.addHiddenParam("fil100ChkTrgDeleted", form.getFil100ChkTrgDeleted());
        cmn999Form.addHiddenParam("fil100ChkTrgDeletedFolder", form.getFil100ChkTrgDeletedFolder());
        cmn999Form.addHiddenParam("fil100SearchTradeTarget", form.getFil100SearchTradeTarget());
        cmn999Form.addHiddenParam("fil100SearchTradeMoneyKbn", form.getFil100SearchTradeMoneyKbn());
        cmn999Form.addHiddenParam("fil100SearchTradeMoneyNoset",
                form.getFil100SearchTradeMoneyNoset());
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
        Fil060Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {
        boolean errorFlg = true;

        FilCommonBiz cmnBiz = new FilCommonBiz(getRequestModel(req), con);
        int dirSid = NullDefault.getInt(form.getFil050DirSid(), -1);
        int fcbSid = cmnBiz.getCabinetSid(dirSid);

        //キャビネットへのアクセス権限があるか判定する。
        errorFlg = cmnBiz.isAccessAuthUser(NullDefault.getInt(
                                               form.getFil010SelectCabinet(), fcbSid));
        return errorFlg;
    }
}