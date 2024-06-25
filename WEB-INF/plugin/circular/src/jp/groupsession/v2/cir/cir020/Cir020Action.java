package jp.groupsession.v2.cir.cir020;

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
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cir.AbstractCircularAction;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.dao.CirInfDao;
import jp.groupsession.v2.cir.model.CirInfModel;
import jp.groupsession.v2.cir.pdf.CirDtlPdfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 受信確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir020Action extends AbstractCircularAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir020Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "cir020";

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
        String downLoadFlg = NullDefault.getString(req.getParameter("cir020downLoadFlg"), "");
        downLoadFlg = downLoadFlg.trim();

        if (cmd.equals("conf")) {
            if (downLoadFlg.equals("1")) {
                log__.debug("添付ファイルダウンロード");
                return true;
            }
        } else if (cmd.equals("allTmpExp")) {
            log__.debug("添付ファイル一括ダウンロード");
            return true;

        } else if (cmd.equals("downloadUsrTmp")) {
            log__.debug("ユーザ添付ファイルダウンロード");
            return true;

        } else if (cmd.equals("pdf")) {
            log__.debug("PDFファイルダウンロード");
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] アクション実行
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
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("START_Cir020");
        ActionForward forward = null;

        Cir020Form thisForm = (Cir020Form) form;
        CirCommonBiz biz = new CirCommonBiz();

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //ショートメールからの遷移時
        if (cmd.equals(GSConstCircular.CMD_SMAIL)) {
            __checkCircularSml(req, con, thisForm);
        }
        //戻る、次へ、前へ以外は回覧板の存在チェックを行う
        if (!cmd.equals("prev020") && !cmd.equals("next020")
                && !cmd.equals("back") && !__checkExist(thisForm, req, con, cmd)) {
            return getNotExistErrorPage(map, req);
        }

        //回覧板がゴミ箱に移動していないか判定
        if (cmd.equals(GSConstCircular.CMD_SMAIL)) {
            __confGomiKbn(thisForm, con);
        }

        //選択されているアカウントが使用可能かを判定する
        if (!biz.canUseAccount(
                con, getSessionUserSid(req), thisForm.getCirViewAccount())) {
            return getAuthErrorPage(map, req);
        }

        if (cmd.equals("prev020")) {
            log__.debug("前へ");
            forward = __doPrevNext(map, thisForm, req, res, con, GSConstCircular.VIEW_PREV);

        } else if (cmd.equals("next020")) {
            log__.debug("次へ");
            forward = __doPrevNext(map, thisForm, req, res, con, GSConstCircular.VIEW_NEXT);

        } else if (cmd.equals("conf")) {

            String downLoadFlg = NullDefault.getString(req.getParameter("cir020downLoadFlg"), "");
            downLoadFlg = downLoadFlg.trim();
            if (downLoadFlg.equals("1")) {
                log__.debug("添付ファイルダウンロード");
                return __doSendDownLoad(map, thisForm, req, res, con);
            }

            log__.debug("確認");
            forward = __doConf(map, thisForm, req, res, con);

        } else if (cmd.equals("memoEdit")) {
            log__.debug("確認後のメモ欄編集");
            forward = __doEdit(map, thisForm, req, res, con);

        } else if (cmd.equals("tempview")) {
            log__.debug("添付画像表示");
            forward = __doTempView(map, thisForm, req, res, con);

        } else if (cmd.equals("back")) {
            log__.debug("戻る");
            forward = __doBack(map, thisForm, req, res, con);

        } else if (cmd.equals("delete")) {
            log__.debug("削除");
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteExe")) {
            log__.debug("削除実行");
            forward = __doDeleteExe(map, thisForm, req, res, con);

        } else if (cmd.equals("comeback")) {
            log__.debug("元に戻す");
            forward = __doComeBack(map, thisForm, req, res, con);

        } else if (cmd.equals("comebackExe")) {
            log__.debug("元に戻す実行");
            forward = __doComeBackExe(map, thisForm, req, res, con);

        } else if (cmd.equals("changeGroup")) {
            forward = __doReload(map, thisForm, req, res, con);

        } else if (cmd.equals("downloadUsrTmp")) {
            log__.debug("回覧先ユーザ添付ダウンロード");
            forward = __doDownLoadUserTmp(map, thisForm, req, res, con);

        } else if (cmd.equals("allTmpExp")) {
            log__.debug("添付ファイル一括ダウンロード");
            forward = __exportAllTmp(map, thisForm, req, res, con);

        } else if (cmd.equals("pdf")) {
            log__.debug("PDFダウンロード");
            forward = __doDownLoadPdf(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Cir020");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルIO時実行例外
     * @throws IOException ファイル操作時実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException, TempFileException, IOException {

        if (form.isCirDelFlg()) {
            //テンポラリディレクトリのファイル削除を行う
            __deleteTempDir(req);
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.createTempDir(getRequestModel(req), GSConstCircular.PLUGIN_ID_CIRCULAR,
                    TEMP_DIRECTORY_ID, GSConstCircular.TEMP_DIR_KN);
        }

//        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        Object obj = req.getAttribute("cir010selectInfSid");
        if (obj != null) {
            String selectInfSid = (String) obj;
            form.setCir010selectInfSid(selectInfSid);
        }

        Cir020Biz biz = new Cir020Biz();
        //「前へ」「次へ」ボタンの設定を行う
        con.setAutoCommit(true);
        Cir020ParamModel paramMdl = new Cir020ParamModel();
        paramMdl.setParam(form);
        biz.setPrevNext(paramMdl, con,
                paramMdl.getCirViewAccount(), GSConstCircular.MODE_JUSIN, userSid);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return __doDsp(map, form, req, res, con, true);
    }

    /**
     * <br>[機  能] 再表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param initFlg 初期表示フラグ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイル操作実行例外
     * @throws IOException ファイル操作時実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return ActionForward
     */
    private ActionForward __doDsp(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        boolean initFlg) throws SQLException, IOToolsException, TempFileException, IOException {

        //テンポラリディレクトリパスを取得
        String tempDir = __getPluginIdTempDir(req);

        //初期表示情報を画面にセットする
        con.setAutoCommit(true);
        int userSid = getSessionUserSid(req);
        Cir020ParamModel paramMdl = new Cir020ParamModel();
        paramMdl.setParam(form);
        Cir020Biz biz = new Cir020Biz();
        biz.setInitData(paramMdl, con, userSid, initFlg, getPluginConfig(req),
                        getRequestModel(req), getAppRootPath(), tempDir);
        paramMdl.setFormData(form);

        //WEB検索プラグインの使用可/不可を設定
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        form.setCir020searchUse(CommonBiz.getWebSearchUse(pconfig));
        con.setAutoCommit(false);

        //トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 「前へ」「次へ」ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param mode 前へ、次へボタン区分
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doPrevNext(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int mode) throws Exception {

        //テンポラリディレクトリのファイル削除を行う
        __clearTempDir(req);

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        con.setAutoCommit(true);
        try {
            //「前」または「次」の回覧板SID、「前へ」「次へ」ボタンの設定を行う
            Cir020ParamModel paramMdl = new Cir020ParamModel();
            paramMdl.setParam(form);
            Cir020Biz biz = new Cir020Biz();
            String prevNextJsFlg =
                biz.changePrevNext(
                   paramMdl, con, paramMdl.getCirViewAccount(),
                   mode, GSConstCircular.MODE_JUSIN, userSid);
            paramMdl.setFormData(form);
            log__.debug("prevNextJsFlg = " + prevNextJsFlg);

            if (prevNextJsFlg.equals(GSConstCircular.MODE_SOUSIN)) {
                //「前」または「次」の回覧板が送信データの場合、
                //送信回覧板状況確認画面へ遷移
                req.setAttribute("cir010selectInfSid", form.getCir010selectInfSid());
                return map.findForward("sousin");
            }
        } finally {
            con.setAutoCommit(false);
        }

        return __doDsp(map, form, req, res, con, true);
    }

    /**
     * <br>[機  能] 回覧板配信時添付ファイルダウンロードの処理
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
    private ActionForward __doSendDownLoad(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        Long binSid = NullDefault.getLong(form.getCir020binSid(), -1);
        int cifSid = NullDefault.getInt(form.getCir010selectInfSid(), -1);
        int accountSid = form.getCirViewAccount();

        CirCommonBiz circularBiz = new CirCommonBiz();
        if (circularBiz.canDownloadCirBindata(
                con, cifSid, binSid, getSessionUserSid(req), accountSid)
            || circularBiz.checkExistence(con, cifSid, accountSid)) {
            return __doDownLoad(map, form, req, res, con);
        }

        return null;

    }

    /**
     * <br>[機  能] 回覧先ユーザ添付ファイルダウンロードの処理
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
    private ActionForward __doDownLoadUserTmp(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        Long binSid = NullDefault.getLong(form.getCir020binSid(), -1);
        int cifSid = NullDefault.getInt(form.getCir010selectInfSid(), -1);
        //選択アカウントSID
        int selAccountSid = form.getCirViewAccount();

        //ユーザ添付ファイルダウンロード時選択アカウントSID
        int dlAccountSid = NullDefault.getInt(form.getCir020cacSid(), -1);

        Cir020Biz biz = new Cir020Biz();
        CirCommonBiz cmnBiz = new CirCommonBiz();
        //ユーザ添付画像がダウンロード可能かチェックする
        if (biz.canDownloadCirBindata(
                con, cifSid, binSid, getSessionUserSid(req), selAccountSid, dlAccountSid)
           || cmnBiz.checkExistence(con, cifSid, selAccountSid)) {
            return __doDownLoad(map, form, req, res, con);
        }

        return null;

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
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownLoad(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        CommonBiz cmnBiz = new CommonBiz();
        Long binSid = NullDefault.getLong(form.getCir020binSid(), -1);

        if (binSid != null) {
            CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                    GroupSession.getResourceManager().getDomain(req));


            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String textDownload = gsMsg.getMessage("cmn.download");

            if (cbMdl != null) {
                //ログ出力処理
                CirCommonBiz cirBiz = new CirCommonBiz(con);
                cirBiz.outPutLog(map, reqMdl, textDownload,
                        GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(),
                        String.valueOf(binSid), GSConstCircular.CIR_LOG_FLG_DOWNLOAD);
                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);

                //ファイルをダウンロードする
                TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
                cbMdl.removeBinFileData();

            }
        }
        return null;

    }

    /**
     * <br>[機  能] 添付ファイル（表示画像）ダウンロードの処理
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
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        CommonBiz cmnBiz = new CommonBiz();
        Long binSid = NullDefault.getLong(form.getCir020binSid(), -1);
        int cifSid = NullDefault.getInt(form.getCir010selectInfSid(), -1);
        int accountSid = form.getCirViewAccount();

        CirCommonBiz circularBiz = new CirCommonBiz();
        if (circularBiz.canDownloadCirBindata(
                con, cifSid, binSid, getSessionUserSid(req), accountSid)
            || circularBiz.checkExistence(con, cifSid, accountSid)) {

            CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                    GroupSession.getResourceManager().getDomain(req));

            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        }
        return null;
    }

    /**
     * <br>[機  能] 戻るボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doBack(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        __deleteTempDir(req);
        //処理モードにより遷移先を取得する
        return __getForward(map, form);
    }

    /**
     * <br>[機  能] 確認ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doConf(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        RequestModel reqMdl = getRequestModel(req);

        //入力チェック
        ActionErrors errors = form.validateConfCheck(reqMdl, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);

            return __doReload(map, form, req, res, con);
        }
        log__.debug("入力エラーなし、登録を行う");

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        //テンポラリディレクトリパスを取得
        String tempDir = __getPluginIdTempDir(req);

        //アプリケーションルートパスの取得
        String appRootPath = getAppRootPath();

        //登録処理を行う
        Cir020ParamModel paramMdl = new Cir020ParamModel();
        paramMdl.setParam(form);
        Cir020Biz biz = new Cir020Biz();
        biz.doUpdate(
                paramMdl, con, reqMdl,
                paramMdl.getCirViewAccount(),
                userSid, cntCon, tempDir, appRootPath);

        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textKakunin = gsMsg.getMessage("cmn.check");

        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        if (cirSid > 0) {
            //ログ出力処理
            CirCommonBiz cirBiz = new CirCommonBiz(con);
            cirBiz.outPutLog(map, reqMdl,
                    textKakunin, GSConstLog.LEVEL_TRACE,
                    biz.getOperationLogForCommit(paramMdl, reqMdl, con));
        }
        __deleteTempDir(req);
        //完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * <br>[機  能] 確認後のメモ欄・回覧先ユーザ添付の更新処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doEdit(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        //入力チェック
        ActionErrors errors = form.validateConfCheck(reqMdl, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);

            return __doReload(map, form, req, res, con);
        }
        log__.debug("入力エラーなし、登録を行う");

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        //テンポラリディレクトリパスを取得
        String tempDir = __getPluginIdTempDir(req);

        //アプリケーションルートパスの取得
        String appRootPath = getAppRootPath();

        //登録処理(メモ欄・回覧先ユーザ添付)を行う
        Cir020ParamModel paramMdl = new Cir020ParamModel();
        paramMdl.setParam(form);
        Cir020Biz biz = new Cir020Biz();
        biz.doUpdateAns(
                paramMdl, con, reqMdl, paramMdl.getCirViewAccount(),
                userSid, cntCon, tempDir, appRootPath);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textChange = gsMsg.getMessage("cmn.change");
        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        if (cirSid > 0) {
            //ログ出力処理
            CirCommonBiz cirBiz = new CirCommonBiz(con);
            cirBiz.outPutLog(map, reqMdl,
                    textChange, GSConstLog.LEVEL_TRACE,
                    biz.getOperationLogForCommit(paramMdl, reqMdl, con));
        }
        //完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = __getForward(map, form);
        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String textKakuninJoho = gsMsg.getMessage("cir.37");

        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("touroku.kanryo.object", textKakuninJoho));

        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        //削除ボタン押下フラグを設定
        form.setCirDelFlg(false);

        con.setAutoCommit(true);
        Cir020ParamModel paramMdl = new Cir020ParamModel();
        paramMdl.setParam(form);
        Cir020Biz biz = new Cir020Biz();

        //回覧板情報を取得
        biz.getCirInf(paramMdl, con, paramMdl.getCirViewAccount());
        //削除する回覧板のタイトルを取得する
        String deleteCir = biz.getCirName(paramMdl, userSid, con, getRequestModel(req));
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        form.setCir020memo(NullDefault.getString(form.getCir020memo(), ""));

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, deleteCir, cmd);
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
     * @return ActionForward
     */
    private ActionForward __doDeleteExe(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //選択された回覧板を削除する
        Cir020ParamModel paramMdl = new Cir020ParamModel();
        paramMdl.setParam(form);
        Cir020Biz biz = new Cir020Biz();
        biz.deleteCir(paramMdl, con, paramMdl.getCirViewAccount());
        paramMdl.setFormData(form);

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDel = gsMsg.getMessage("cmn.delete");
        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        if (cirSid > 0) {
            CirInfDao ciDao = new CirInfDao(con);
            CirInfModel ciMdl = ciDao.getCirInfo(cirSid);

            if (ciMdl != null) {
                //ログ出力処理
                CirCommonBiz cirBiz = new CirCommonBiz(con);
                cirBiz.outPutLog(map, reqMdl, textDel, GSConstLog.LEVEL_TRACE,
                        "[title]" + ciMdl.getCifTitle());
            }
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        __deleteTempDir(req);

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req, cmd);
    }

    /**
     * <br>[機  能] 元に戻す時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doComeBack(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        con.setAutoCommit(true);
        Cir020ParamModel paramMdl = new Cir020ParamModel();
        paramMdl.setParam(form);
        Cir020Biz biz = new Cir020Biz();
        //回覧板情報を取得
        biz.getCirInf(paramMdl, con, paramMdl.getCirViewAccount());
        //元に戻す回覧板のタイトルを取得する
        String deleteCir = biz.getCirName(paramMdl, userSid, con, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //元に戻す確認画面を表示
        return __setKakuninDsp(map, form, req, deleteCir, cmd);
    }

    /**
     * <br>[機  能] 元に戻す処理を行う(元に戻す実行)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doComeBackExe(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //選択された回覧板を元に戻す
        Cir020ParamModel paramMdl = new Cir020ParamModel();
        paramMdl.setParam(form);
        Cir020Biz biz = new Cir020Biz();
        biz.comeBackCir(paramMdl, con, paramMdl.getCirViewAccount());
        paramMdl.setFormData(form);

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textReturn = gsMsg.getMessage("cmn.undo");
        String textEdit = gsMsg.getMessage("cmn.change");
        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        if (cirSid > 0) {
            CirInfDao ciDao = new CirInfDao(con);
            CirInfModel ciMdl = ciDao.getCirInfo(cirSid);

            if (ciMdl != null) {
                //ログ出力処理
                CirCommonBiz cirBiz = new CirCommonBiz(con);
                cirBiz.outPutLog(map, reqMdl, textEdit,
                        GSConstLog.LEVEL_TRACE, textReturn + "[title]" + ciMdl.getCifTitle());
            }
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //元に戻す完了画面を表示
        return __setKanryoDsp(map, form, req, cmd);
    }

    /**
     * [機  能] 削除、元に戻す完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param cmd コマンドパラメータ
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        String cmd) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = __getForward(map, form);
        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String textCir = gsMsg.getMessage("cir.5");

        MessageResources msgRes = getResources(req);
        if (cmd.equals("deleteExe")) {
            //削除完了
            cmn999Form.setMessage(
                    msgRes.getMessage("sakujo.kanryo.object", textCir));

        } else {
            //復帰(元に戻す)完了
            cmn999Form.setMessage(
                    msgRes.getMessage("move.former.object", textCir));
        }

        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 削除、元に戻す確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param cirStr 削除、元に戻す回覧板
     * @param cmd コマンドパラメータ
     * @return ActionForward
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        String cirStr,
        String cmd) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("init");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        GsMessage gsMsg = new GsMessage();
        String textCir = gsMsg.getMessage(req, "cir.5");

        //メッセージ
        MessageResources msgRes = getResources(req);
        if (cmd.equals("delete")) {

            String cmdMode = NullDefault.getString(form.getCir010cmdMode(), "");

            if (cmdMode.equals(GSConstCircular.MODE_JUSIN)
                || cmdMode.equals(GSConstCircular.MODE_JUSIN_MAIN)
                || cmdMode.equals(GSConstCircular.MODE_LABEL)) {

                //受信
                if (form.getCir020dspMdl().getCvwConf() == GSConstCircular.CONF_UNOPEN) {
                    //未確認データ
                    //削除確認
                    cmn999Form.setMessage(msgRes.getMessage(
                            "move.gomibako.cmn", textCir, cirStr));
                } else {
                    //確認済データ
                    //削除確認
                    cmn999Form.setMessage(msgRes.getMessage(
                            "move.gomibako.cmn", textCir, cirStr));
                }

            } else if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {
                //ゴミ箱
                //削除確認
                cmn999Form.setMessage(msgRes.getMessage(
                        "sakujo.kakunin.list", textCir, cirStr));
            }

            //OKボタンクリック時遷移先
            ActionForward forwardOk = map.findForward("init");
            cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteExe");

        } else {
            //元に戻す
            cmn999Form.setMessage(msgRes.getMessage(
                    "move.former.mail", textCir, cirStr));
            //OKボタンクリック時遷移先
            ActionForward forwardOk = map.findForward("init");
            cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=comebackExe");
        }

        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 画面の再表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doReload(
            ActionMapping map,
            Cir020Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {
        //「前へ」「次へ」ボタンの設定を行う
        con.setAutoCommit(true);
//      //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        Cir020ParamModel paramMdl = new Cir020ParamModel();
        paramMdl.setParam(form);
        Cir020Biz biz = new Cir020Biz();
        biz.setPrevNext(paramMdl, con, paramMdl.getCirViewAccount(),
                GSConstCircular.MODE_JUSIN, userSid);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return __doDsp(map, form, req, res, con, false);
    }

    /**
     * <br>[機  能] 回覧板の存在チェックを行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @param cmd コマンド
     * @return 回覧板が存在するか(true:存在する)
     * @throws GSException GS汎用実行例外
     * @throws SQLException SQL実行時例外
     */
    private boolean __checkExist(
            Cir020Form form,
            HttpServletRequest req,
            Connection con,
            String cmd) throws GSException, SQLException {

        Cir020Biz biz = new Cir020Biz();

        BaseUserModel userMdl = getSessionUserModel(req);
        if (userMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        Cir020ParamModel paramMdl = new Cir020ParamModel();
        paramMdl.setParam(form);
        boolean existCir = false;
        //送信確認画面からの遷移の場合、リクエストからSIDを取得する。
        if (cmd.equals("prev030") || cmd.equals("next030")) {
                Object obj = req.getAttribute("cir010selectInfSid");
                if (obj != null) {
                    String selectInfSid = (String) obj;
                    paramMdl.setCir010selectInfSid(selectInfSid);
                    existCir = biz.isExistCir(paramMdl, con, form.getCirViewAccount());
            }
        } else {
            existCir = biz.isExistCir(paramMdl, con, form.getCirViewAccount());
        }
        paramMdl.setFormData(form);

        return existCir;
    }

    /**
     * <br>[機  能] 処理モードにより遷移先を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form フォーム
     * @return ActionForward
     */
    private ActionForward __getForward(ActionMapping map, Cir020Form form) {

        ActionForward forward = null;

        //遷移元画面ID
        String cir060dspId = NullDefault.getString(form.getCir060dspId(), "");

        if (cir060dspId.equals("")) {

            //処理モードで処理を分岐
            String cmdMode = NullDefault.getString(form.getCir010cmdMode(), "");

            //回覧板一覧・メインから遷移
            if (cmdMode.equals(GSConstCircular.MODE_JUSIN)
            || cmdMode.equals(GSConstCircular.MODE_GOMI)
            || cmdMode.equals(GSConstCircular.MODE_LABEL)) {
                //受信
                forward = map.findForward("back");

            } else if (cmdMode.equals(GSConstCircular.MODE_JUSIN_MAIN)) {
                //受信(メイン)
                forward = map.findForward("gf_main");
            }

        } else {

            //回覧板詳細検索から遷移
            forward = map.findForward("search");
        }
        return forward;
    }

    /**
     * <br>[機  能] ショートメールからの遷移時
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __checkCircularSml(
        HttpServletRequest req,
        Connection con,
        Cir020Form form) throws Exception {

        if (form.getCir010selectInfSid() != null
                && ValidateUtil.isNumber(form.getCir010selectInfSid())
                && Integer.valueOf(form.getCir010selectInfSid()) > 0) {
            Cir020Biz biz = new Cir020Biz();
            Cir020ParamModel paramMdl = new Cir020ParamModel();
            paramMdl.setParam(form);
            biz.getViewAccount(paramMdl, con, getRequestModel(req));
            paramMdl.setFormData(form);
        }
    }

    /**
     *
     * <br>[機  能]回覧板がゴミ箱一覧にあるか判定
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __confGomiKbn(Cir020Form form, Connection con)
            throws SQLException {
        Cir020Biz biz = new Cir020Biz();

        //ゴミ箱モード判定
        int gomiKbn = biz.cirGomiConf(con,
                form.getCir010selectInfSid(),
                form.getCirViewAccount());
        if (gomiKbn == GSConstCircular.DSPKBN_DSP_NG) {
            form.setCir010cmdMode(GSConstCircular.MODE_GOMI);
        }
    }

    /**
     * <br>[機  能] 添付ファイル一括ダウンロードボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     *  テンポラリディレクトリルール
     * ○一括ダウンロードファイル作成ディレクトリ
     *    プラグインID/セッションID/cirAllExp/タイトル/${表示順}_${ユーザ名}/各添付ファイル
     * ○ダウンロード用ZIPファイル一時保存ディレクトリ
     *    プラグインID/セッションID/cirAllExp/タイトル.zip
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __exportAllTmp(
        ActionMapping map,
        Cir020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        int usrSid = getSessionUserSid(req);

        //回覧板SID
        int cirSid = NullDefault.getInt(form.getCir010selectInfSid(), -1);

        Cir020Biz biz = new Cir020Biz();
        if (!biz.canDownloadAllTmp(con, cirSid, usrSid, form.getCirViewAccount())) {
            return null;
        }

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req), GSConstCircular.PLUGIN_ID_CIRCULAR,
                TEMP_DIRECTORY_ID, GSConstCircular.TEMP_DIR_ZIP);

        log__.debug("テンポラリディレクトリ = " + tempDir);

        try {
            Cir020ParamModel paramMdl = new Cir020ParamModel();
            paramMdl.setParam(form);
            String [] index = null;
            try {
                index = biz.makeAllTmpFile(
                        paramMdl, con, getAppRootPath(),
                        tempDir, usrSid, paramMdl.getCirViewAccount(), getRequestModel(req));
            } catch (IOToolsException | IOException e) {
                log__.error("ZIP作成に失敗", e);
                return __setZipErrorDsp(map, req, form);

            }
            paramMdl.setFormData(form);

            String outFilePath = index[0];
            String outBookName = index[1];

            GsMessage gsMsg = new GsMessage(getRequestModel(req));
            //ログ出力処理
            CirCommonBiz cirBiz = new CirCommonBiz(con);
            cirBiz.outPutLog(
                    map, getRequestModel(req), gsMsg.getMessage("cir.allTmep.download.log"),
                    GSConstLog.LEVEL_INFO, outBookName);

            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);

            TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);

        } catch (Exception e) {
            log__.error("添付ファイル一括ダウンロードに失敗" + e);
            throw e;
        } finally {
            //TEMPディレクトリ削除
            temp.deleteTempPath(getRequestModel(req), GSConstCircular.PLUGIN_ID_CIRCULAR,
                    TEMP_DIRECTORY_ID, GSConstCircular.TEMP_DIR_ZIP);
        }

        return null;
    }

    /**
     * <br>[機  能] ファイル出力エラー画面設定処理
     * <br>[解  説] ファイル出力エラー画面のパラメータセット
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return メッセージ画面遷移
     * @throws Exception 実行時例外
     */
    private ActionForward __setZipErrorDsp(ActionMapping map,
            HttpServletRequest req, Cir020Form form) throws Exception {
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("init");

        cmn999Form.setUrlOK(urlForward.getPath());


        cmn999Form.addHiddenAll(form, form.getClass(), "");


        //メッセージセット
        String msgState = "error.fail";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, "cmn.zip.create")));
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認時添付用のテンポラリディレクトリを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return テンポラリディレクトリパス
     */
    private String __getPluginIdTempDir(HttpServletRequest req) {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstCircular.PLUGIN_ID_CIRCULAR, TEMP_DIRECTORY_ID, GSConstCircular.TEMP_DIR_KN);
        log__.debug("テンポラリディレクトリ = " + tempDir);
        return tempDir;
    }

    /**
     * <br>[機  能] テンポラリディレクトリの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    private void __deleteTempDir(HttpServletRequest req) {
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req), GSConstCircular.PLUGIN_ID_CIRCULAR,
                TEMP_DIRECTORY_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリ内のファイルの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    private void __clearTempDir(HttpServletRequest req) {
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.clearTempPath(getRequestModel(req), GSConstCircular.PLUGIN_ID_CIRCULAR,
                TEMP_DIRECTORY_ID, GSConstCircular.TEMP_DIR_KN);
    }


    /**
     * <br>[機  能] PDFファイルダウンロード処理を行います。
     * <br>[解  説] アクションフォーム
     * <br>[備  考]
     *  テンポラリディレクトリルール
     * ○ダウンロード用PDFファイル一時保存ディレクトリ
     *    プラグインID/セッションID/cirrecvpdf/cirrecv+回覧板SID.pdf
     * @param map アクションマッピング
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doDownLoadPdf(
            ActionMapping map,
            Cir020Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, Exception {

        //リクエスト情報を取得
        RequestModel reqMdl = getRequestModel(req);
        //ユーザSIDを取得
        int userSid = getSessionUserSid(req);
        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig =
                getPluginConfigForMain(getPluginConfig(req), con, userSid);

        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();
        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String outTempDir = temp.getTempPath(getRequestModel(req),
                GSConstCircular.PLUGIN_ID_CIRCULAR,
                TEMP_DIRECTORY_ID, GSConstCircular.TEMP_DIR_PDF);

        Cir020Biz biz = new Cir020Biz();
        //PDF生成
        Cir020ParamModel paramMdl = new Cir020ParamModel();
        paramMdl.setParam(form);
        CirDtlPdfModel pdfMdl = biz.createCirRecvPdf(
                paramMdl, con, userSid, appRootPath, outTempDir, pconfig, reqMdl);
        paramMdl.setFormData(form);

        String outBookName = pdfMdl.getFileName();
        String saveFileName = pdfMdl.getSaveFileName();
        String outFilePath = IOTools.setEndPathChar(outTempDir) + saveFileName;
        TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);

        //ログ出力
        GsMessage gsMsg = new GsMessage(reqMdl);
        String pdfDownload = gsMsg.getMessage("cmn.pdf");
        CirCommonBiz cirBiz = new CirCommonBiz(con);
        cirBiz.outPutLog(
                map, reqMdl, pdfDownload,
                GSConstLog.LEVEL_INFO, outBookName,
                Integer.parseInt(form.getCir010selectInfSid()),
                null, GSConstCircular.CIR_LOG_FLG_PDF);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstCircular.PLUGIN_ID_CIRCULAR,
                TEMP_DIRECTORY_ID, GSConstCircular.TEMP_DIR_PDF);

        return null;
    }
}
