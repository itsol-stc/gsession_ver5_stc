package jp.groupsession.v2.ip.ipk060;

import java.io.File;
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
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.AbstractIpAction;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] IP管理 IPアドレスインポート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk060Action extends AbstractIpAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk060Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "ipk060";


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

        //サンプルダウンロードフラグ
        String sampleDownLoadFlg = NullDefault.getString(req.getParameter("sample"), "");
        sampleDownLoadFlg = sampleDownLoadFlg.trim();

        if (cmd.equals("fileDownload")) {
            log__.debug("ファイルダウンロード");
            return true;
        } else if (cmd.equals("ipk060_sample")) {
            if (downLoadFlg.equals("1")) {
                log__.debug("サンプルCSVファイルダウンロード");
                return true;
            }
        }

        return false;
    }

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
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
        ActionForward forward = null;
        Ipk060Form ipkForm = (Ipk060Form) form;
        log__.debug("START");

        //CMD
        String reqCmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        reqCmd = reqCmd.trim();

        if (!reqCmd.equals("ipk060_sample")) {
            //権限チェック
            String errorFlg = __isAccessOk(ipkForm, req, con);
            if (errorFlg.equals(IpkConst.ACCESS_ERROR_ADMIN)) {
                log__.debug("管理者権限無しエラー");
                return map.findForward("gf_power");
            }

            if (errorFlg.equals(IpkConst.ACCESS_ERROR_NO_PARAM)) {
                log__.debug("不正アクセスエラー");
                 return map.findForward("gf_submit");
            }
        }

        //コマンド
        String cmd = NullDefault.getString(ipkForm.getCMD(), "");
        log__.debug("=== cmd === " + cmd);

        //インポートボタンクリック(キャンセル)
        if (cmd.equals("importCancel")) {
            forward = __doInitAg(map, ipkForm, req, con);

        //インポートボタンクリック
        } else if (cmd.equals("iadImp")) {
            forward = __doImportKn(map, ipkForm, req, con);

        //インポートOKボタンクリック
        } else if (cmd.equals("importOk")) {
            forward = __doImport(map, ipkForm, req, res, con);

        //戻るボタンクリック
        } else if (cmd.equals("ipk060Return")) {
            forward = __doReturn(map, req);

        //添付ファイルリンククリック
        } else if (cmd.equals("fileDownload")) {
            forward = __doDownLoadTemp(map, ipkForm, req, res, con);

        //サンプルダウンロードリンククリック
        } else if (reqCmd.equals("ipk060_sample")) {
            log__.debug("サンプルダウンロードリンククリック");
            __doSampleDownLoad(map, req, res, con);

        //初期表示
        } else {
            forward = __doInit(map, ipkForm, req, con);
        }
        log__.info("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws GSException GS汎用実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Ipk060Form form,
        HttpServletRequest req,
        Connection con)
        throws SQLException, IOToolsException, TempFileException, GSException {

        try {
            con.setAutoCommit(true);

            //セッションユーザSIDを取得する。
            BaseUserModel userMdl = getSessionUserModel(req);
            if (userMdl == null) {
                throw new GSAuthenticateException("ユーザ情報の取得に失敗");
            }
            int usrSid = userMdl.getUsrsid();

            //テンポラリディレクトリの削除
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(getRequestModel(req),
                    IpkConst.PLUGIN_ID_IPKANRI, TEMP_DIRECTORY_ID);
            temp.createTempDir(getRequestModel(req),
                    IpkConst.PLUGIN_ID_IPKANRI, TEMP_DIRECTORY_ID);

            //ネットワーク一覧情報を取得
            Ipk060ParamModel paramMdl = new Ipk060ParamModel();
            paramMdl.setParam(form);
            Ipk060Biz biz = new Ipk060Biz();
            biz.setInitData(paramMdl, con, getRequestModel(req), usrSid);
            paramMdl.setFormData(form);
        } catch (SQLException e) {
            throw e;
        }
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 戻るボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @return ActionForward
     * @throws IOToolsException ファイルアクセス時例外
     */
    private ActionForward __doReturn(
        ActionMapping map,
        HttpServletRequest req)
    throws IOToolsException  {

        //テンポラリディレクトリの削除
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                IpkConst.PLUGIN_ID_IPKANRI, TEMP_DIRECTORY_ID);

        return map.findForward("ipk060Return");
    }
    /**
     * <br>[機  能] ユーザ追加処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doImport(ActionMapping map,
                                      Ipk060Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws Exception {


        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req), IpkConst.PLUGIN_ID_IPKANRI,
                TEMP_DIRECTORY_ID);

        Ipk060Biz ipk060Biz = new Ipk060Biz();
        //上書モード時
        if (form.getImportMode().equals(IpkConst.IMPORT_MODE_UWAGAKI)) {
            //登録されているIPアドレスを削除する。
            Ipk060ParamModel paramMdl = new Ipk060ParamModel();
            paramMdl.setParam(form);
            ipk060Biz.deleteNetwork(paramMdl, con, getSessionUserSid(req));
            paramMdl.setFormData(form);
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textImport = gsMsg.getMessage("cmn.import");

        boolean commit = false;
        try {
            con.setAutoCommit(false);
            MlCountMtController cntCon = null;
            //SID採番
            cntCon = getCountMtController(req);
            IadCsvImport iadCsvImport =
                new IadCsvImport(reqMdl, cntCon, userSid,
                        NullDefault.getInt(form.getNetSid(), 0),  con);

            long num = iadCsvImport.importCsv(tempDir);

            //ログ出力
            IpkBiz ipkBiz = new IpkBiz(con);
            ipkBiz.outPutLog(map, reqMdl,
                    textImport, GSConstLog.LEVEL_INFO, "[count]" + (num - 1));

            commit = true;
        } catch (Exception e) {
            log__.error("ユーザCSVの取り込みに失敗しました。" + e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
            //テンポラリディレクトリの削除
            temp.deleteTempPath(getRequestModel(req), IpkConst.PLUGIN_ID_IPKANRI,
                TEMP_DIRECTORY_ID);
        }

        //完了画面遷移
        __doImportCompDsp(map, form, req);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能]インポート画面へのアクセス権限の有無を判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return errorFlg 0:正常 1:アクセス権限無し 2:不正アクセス
     * @throws SQLException SQL実行時例外
     */
    private String __isAccessOk(Ipk060Form form, HttpServletRequest req, Connection con)
    throws SQLException {

        Ipk060ParamModel paramMdl = new Ipk060ParamModel();
        paramMdl.setParam(form);
        Ipk060Biz biz = new Ipk060Biz();
        String errorFlg = biz.isAccessOk(paramMdl, getRequestModel(req), con);
        paramMdl.setFormData(form);
        return errorFlg;
    }

    /**
     * <br>[機  能] 添付ファイルダウンロードの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownLoadTemp(
        ActionMapping map,
        Ipk060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        //ネットワークSID
        int netSid = NullDefault.getInt(form.getNetSid(), -1);
        //バイナリSID
        Long binSid = NullDefault.getLong(form.getBinSid(), 0);

        IpkBiz ipkBiz = new IpkBiz();
        //IP一覧画面のネットワーク詳細情報の添付ファイルがダウンロード可能かチェックする
        if (ipkBiz.isCheckDLNetworkForIp(
                con, getRequestModel(req), netSid, binSid, getSessionUserSid(req))) {

            try {
                ipkBiz.doDownLoadTemp(binSid, map, req, res, con, getAppRootPath(),
                        getRequestModel(req));
            } catch (SQLException se) {
                throw se;
            } catch (Exception e) {
                throw e;
            }
        }

        return null;
    }

    /**
     * <br>[機  能] インポート確認画面を表示する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doImportKn(ActionMapping map,
                                      Ipk060Form form,
                                      HttpServletRequest req,
                                      Connection con)
        throws Exception {

        con.setAutoCommit(true);

        int netSid = NullDefault.getInt(form.getNetSid(), 0);
        String mode = NullDefault.getString(form.getImportMode(), IpkConst.IMPORT_MODE_TUIKA);

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                IpkConst.PLUGIN_ID_IPKANRI, TEMP_DIRECTORY_ID);

        ActionErrors errors = form.validateCheck(getRequestModel(req), tempDir, con,
                                                netSid, mode);
        if (errors.size() > 0) {
            addErrors(req, errors);
            log__.debug("入力チェックNG");
            return __doInitAg(map, form, req, con);
        }
        return __doImportKnDsp(map, form, req);
    }

    /**
     * <br>[機  能] 画面を再表示する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return アクションフォーワード
     * @throws GSException GS汎用実行例外
     * @throws Exception 実行例外
     */
    private ActionForward __doInitAg(ActionMapping map,
                                      Ipk060Form form,
                                      HttpServletRequest req,
                                      Connection con)
        throws GSException, Exception {

        con.setAutoCommit(true);

        //セッションユーザSIDを取得する。
        BaseUserModel userMdl = getSessionUserModel(req);
        if (userMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }
        int usrSid = userMdl.getUsrsid();

        Ipk060ParamModel paramMdl = new Ipk060ParamModel();
        paramMdl.setParam(form);
        Ipk060Biz biz = new Ipk060Biz();
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                IpkConst.PLUGIN_ID_IPKANRI, TEMP_DIRECTORY_ID);
        //添付ファイル一覧を設定
        biz.setTempList(paramMdl, tempDir);
        //再表示の設定
        biz.setInitData(paramMdl, con, getRequestModel(req), usrSid);

        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] インポート確認画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doImportKnDsp(ActionMapping map, Ipk060Form form,
            HttpServletRequest req) {
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;
        ActionForward forwardCancel = null;

        GsMessage gsMsg = new GsMessage();
        String textNetwork = gsMsg.getMessage(req, "ipk.4");

        // トランザクショントークン設定
        this.saveToken(req);

        //インポート確認画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        forwardOK = map.findForward("iadImp");
        cmn999Form.setUrlOK(forwardOK.getPath());
        forwardCancel = map.findForward("impCancel");
        cmn999Form.setUrlCancel(forwardCancel.getPath());
        cmn999Form.setMessage(msgRes.getMessage("import.kakunin.object",
                textNetwork));
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        cmn999Form.addHiddenParam("CMD", form.getCMD());
        cmn999Form.addHiddenParam("netSid", form.getNetSid());
        cmn999Form.addHiddenParam("iadLimit", form.getIadLimit());
        cmn999Form.addHiddenParam("usedKbn", form.getUsedKbn());
        cmn999Form.addHiddenParam("iadPageNum", form.getIadPageNum());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("importMode", form.getImportMode());
        cmn999Form.addHiddenParam("deleteCheck", form.getDeleteCheck());
        cmn999Form.addHiddenParam("deleteAllCheck", form.getDeleteAllCheck());
        cmn999Form.addHiddenParam("netInfDspFlg", form.getNetInfDspFlg());
        return forward;
    }

    /**
     * <br>[機  能] インポート完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doImportCompDsp(ActionMapping map, Ipk060Form form,
            HttpServletRequest req) {
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;

        GsMessage gsMsg = new GsMessage();
        String textNetwork = gsMsg.getMessage(req, "ipk.4");

        //インポート完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        forwardOK = map.findForward("ipk060Return");
        cmn999Form.setUrlOK(forwardOK.getPath());
        cmn999Form.setMessage(msgRes.getMessage("import.kanryo.object",
                textNetwork));
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        cmn999Form.addHiddenParam("netSid", form.getNetSid());
        cmn999Form.addHiddenParam("iadLimit", form.getIadLimit());
        cmn999Form.addHiddenParam("usedKbn", form.getUsedKbn());
        cmn999Form.addHiddenParam("iadPageNum", form.getIadPageNum());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("netInfDspFlg", form.getNetInfDspFlg());
        return forward;
    }

    /**
     * <br>[機  能] サンプルCSVをダウンロード
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception ダウンロード時例外
     */
    private void __doSampleDownLoad(
            ActionMapping map, HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDownload = gsMsg.getMessage("cmn.download");

        String fileName = IpkConst.SAMPLE_IP_CSV;
        StringBuilder buf = new StringBuilder();
        buf.append(getAppRootPath());
        buf.append(File.separator);
        buf.append(IpkConst.PLUGIN_ID_IPKANRI);
        buf.append(File.separator);
        buf.append("doc");
        buf.append(File.separator);
        buf.append(fileName);
        String fullPath = buf.toString();
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //ログ出力
        IpkBiz ipkBiz = new IpkBiz(con);
        ipkBiz.outPutLog(map, reqMdl,
                textDownload, GSConstLog.LEVEL_INFO, fileName);
    }
}