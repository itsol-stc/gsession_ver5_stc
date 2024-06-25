package jp.groupsession.v2.ip.ipk040;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.AbstractIpAction;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.model.IpkAddModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] IP管理 IPアドレス一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk040Action extends AbstractIpAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk040Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "ipk040";

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

        if (cmd.equals("export")) {
            log__.debug("CSVファイルダウンロード");
            return true;
        } else if (cmd.equals("fileDownload")) {
            log__.debug("ファイルダウンロード");
            return true;
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
        Ipk040Form ipkForm = (Ipk040Form) form;
        log__.debug("START");

        //非公開ネットワークSID時の制御
        IpkBiz ipkBiz = new IpkBiz();
        if (!ipkBiz.isNotDspNetSid(
                NullDefault.getInt(ipkForm.getNetSid(), 0), getRequestModel(req), con)) {
            log__.debug("不正アクセスエラー");
            return map.findForward("gf_submit");
        }

        //コマンド
        String cmd = NullDefault.getString(ipkForm.getCMD(), "");
        log__.debug("===cmd=== " + cmd);
        //追加ボタンクリック
        if (cmd.equals("ipAdd")) {
            forward = map.findForward("ipAdd");
        //変更ボタンクリック
        } else if (cmd.equals("ipEdit")) {
            forward = map.findForward("ipEdit");
        //戻るボタンクリック
        } else if (cmd.equals("return")) {
            forward = __doReturn(map, req);
        //削除ボタンクリック
        } else if (cmd.equals("selectDelete")) {
            forward = __doDeleteIpadKnDsp(map, ipkForm, req, res, con);
        //削除OKボタンクリック
        } else if (cmd.equals("iadDeleteKn")) {
            forward = __doDeleteIpad(map, ipkForm, req, res, con);
        //右矢印クリック
        } else if (cmd.equals("arrorw_right")) {
            forward = __setNextPage(map, ipkForm, req, con);
        //左矢印クリック
        } else if (cmd.equals("arrorw_left")) {
            forward = __setBeforePage(map, ipkForm, req, con);
        //ページコンボ変更
        } else if (cmd.equals("pageSelect")) {
            forward = __doChangePageCmb(map, ipkForm, req, con);
        //状況表示、ソート、表示件数変更
        } else if (cmd.equals("pageSort")) {
            forward = __doChange(map, ipkForm, req, con);
        //インポートボタン押下
        } else if (cmd.equals("import")) {
            forward = map.findForward("import");
        //エクスポートボタン押下
        } else if (cmd.equals("export")) {
            __doDownLoad(map, ipkForm, req, res, con);
            return null;
        //添付ファイルリンククリック
        } else if (cmd.equals("fileDownload")) {
            __doDownLoadTemp(map, ipkForm, req, res, con);
            return null;
        //検索ボタンクリック
        } else if (cmd.equals("search")) {
            forward = map.findForward("search");
        //初期表示
        } else {
            return __doInit(map, ipkForm, req, con);
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
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doInit(ActionMapping map,
        Ipk040Form form,
        HttpServletRequest req,
        Connection con)
        throws SQLException, TempFileException {

        //IPアドレス一覧情報を取得
        try {
            Ipk040ParamModel paramMdl = new Ipk040ParamModel();
            paramMdl.setParam(form);
            Ipk040Biz biz = new Ipk040Biz();
            biz.setInitData(paramMdl, con, getRequestModel(req));
            paramMdl.setFormData(form);
        } catch (SQLException e) {
            throw e;
        }
        return map.getInputForward();
    }

    /**
     * <br>[機  能] IPアドレス一括削除処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     */
    private ActionForward __doDeleteIpad(ActionMapping map,
        Ipk040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //IPアドレス情報を削除
        Ipk040ParamModel paramMdl = new Ipk040ParamModel();
        paramMdl.setParam(form);
        Ipk040Biz biz = new Ipk040Biz();
        biz.deleteIpad(paramMdl, con, getSessionUserSid(req));
        paramMdl.setFormData(form);

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDel = gsMsg.getMessage("cmn.delete");

        //ログ出力
        IpkBiz ipkBiz = new IpkBiz(con);
        ipkBiz.outPutLog(map, reqMdl,
                textDel, GSConstLog.LEVEL_TRACE, "");

        return __doDeleteIpadCompDsp(map, form, req);
    }

    /**
     * <br>[機  能] 削除確認画面の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doDeleteIpadKnDsp(ActionMapping map, Ipk040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;
        ActionForward forwardCancel = null;

        //未選択エラー
        if (form.getDeleteCheck() == null || form.getDeleteCheck().length == 0) {
            forward = __doDeleteIpadNoDsp(map, form, req);
        } else {
            //ネットワーク削除の削除確認画面パラメータの設定
            MessageResources msgRes = getResources(req);
            cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
            cmn999Form.setIcon(Cmn999Form.ICON_INFO);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
            forwardOK = map.findForward("iadDeleteKn");
            cmn999Form.setUrlOK(forwardOK.getPath());
            forwardCancel = map.findForward("iadDeleteBack");
            cmn999Form.setUrlCancel(forwardCancel.getPath());
            cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.once",
                    getInterMessage(req, "cmn.ipaddress")));
            cmn999Form.addHiddenParam("netSid", form.getNetSid());
            cmn999Form.addHiddenParam("iadLimit", form.getIadLimit());
            cmn999Form.addHiddenParam("usedKbn", form.getUsedKbn());
            cmn999Form.addHiddenParam("iadPageNum", form.getIadPageNum());
            cmn999Form.addHiddenParam("ipk070KeyWord", form.getIpk070KeyWord());
            cmn999Form.addHiddenParam("deleteCheck", form.getDeleteCheck());
            cmn999Form.addHiddenParam("deleteAllCheck", form.getDeleteAllCheck());
            cmn999Form.addHiddenParam("orderKey", form.getOrderKey());
            cmn999Form.addHiddenParam("sortKey", form.getSortKey());
            req.setAttribute("cmn999Form", cmn999Form);
            forward = map.findForward("gf_msg");
            cmn999Form.addHiddenParam("netInfDspFlg", form.getNetInfDspFlg());
        }

        // トランザクショントークン設定
        saveToken(req);

        return forward;
    }

    /**
     * <br>[機  能] 削除完了画面の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doDeleteIpadCompDsp(ActionMapping map, Ipk040Form form,
            HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage();
        String textIpAd = gsMsg.getMessage(req, "ipk.6");

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;
        //ネットワーク削除の削除確認画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        forwardOK = map.findForward("iadDeleteBack");
        cmn999Form.setUrlOK(forwardOK.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object",
                                                 textIpAd));
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        cmn999Form.addHiddenParam("ipk070KeyWord", form.getIpk070KeyWord());
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
     * <br>[機  能] 次のページを表示する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __setNextPage(ActionMapping map,
        Ipk040Form form,
        HttpServletRequest req,
        Connection con)
    throws SQLException, TempFileException {

        //IPアドレス一覧情報を取得
        Ipk040ParamModel paramMdl = new Ipk040ParamModel();
        paramMdl.setParam(form);
        Ipk040Biz biz = new Ipk040Biz();
        biz.setNextPage(paramMdl);
        paramMdl.setFormData(form);
        return __doInit(map, form, req, con);
    }
    /**
     * <br>[機  能] 前のページを表示する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __setBeforePage(ActionMapping map,
        Ipk040Form form,
        HttpServletRequest req,
        Connection con)
    throws SQLException, TempFileException {

        //IPアドレス一覧情報を取得
        Ipk040ParamModel paramMdl = new Ipk040ParamModel();
        paramMdl.setParam(form);
        Ipk040Biz biz = new Ipk040Biz();
        biz.setBeforePage(paramMdl);
        paramMdl.setFormData(form);
        return __doInit(map, form, req, con);
    }

    /**
     * <br>[機  能] 戻るボタンクリック時の処理。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @return ActionForward フォワード
     * @throws IOToolsException ファイルアクセス時例外
     */
    private ActionForward __doReturn(ActionMapping map, HttpServletRequest req)
    throws IOToolsException  {

        //テンポラリディレクトリの削除
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req), IpkConst.PLUGIN_ID_IPKANRI, TEMP_DIRECTORY_ID);

        return map.findForward("return");
    }
    /**
     * <br>[機  能] 削除項目未選択時画面の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doDeleteIpadNoDsp(ActionMapping map, Ipk040Form form,
            HttpServletRequest req) {
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;

        GsMessage gsMsg = new GsMessage();
        String textIpAd = gsMsg.getMessage(req, "ipk.6");

        //ネットワーク削除の削除確認画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        forwardOK = map.findForward("iadDeleteBack");
        cmn999Form.setUrlOK(forwardOK.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.select.required.text",
                                                 textIpAd));
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        cmn999Form.addHiddenParam("netSid", form.getNetSid());
        cmn999Form.addHiddenParam("iadLimit", form.getIadLimit());
        cmn999Form.addHiddenParam("usedKbn", form.getUsedKbn());
        cmn999Form.addHiddenParam("ipk070KeyWord", form.getIpk070KeyWord());
        cmn999Form.addHiddenParam("iadPageNum", form.getIadPageNum());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("deleteAllCheck", form.getDeleteAllCheck());
        cmn999Form.addHiddenParam("netInfDspFlg", form.getNetInfDspFlg());
        return forward;
    }

    /**<br>[機　能]IPアドレス情報ダウンロードの処理
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
    private ActionForward __doDownLoad(
        ActionMapping map,
        Ipk040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;

        //表示件数が０件の場合エラーメッセージを表示する。
        if (form.getUsedKbn().equals(IpkConst.USEDKBN_ALL)) {
            if (form.getIadCount().equals(IpkConst.IPAD_TOUROKU_COUNT0)) {
                return __doExportNoDataDsp(map, form, req);
            }
        } else if (form.getUsedKbn().equals(IpkConst.USEDKBN_SIYOU)) {
            if (form.getIadCountUse().equals(IpkConst.IPAD_TOUROKU_COUNT0)) {
                return __doExportNoDataDsp(map, form, req);
            }
        } else if (form.getUsedKbn().equals(IpkConst.USEDKBN_MISIYOU)) {
            if (form.getIadCountNotUse().equals(IpkConst.IPAD_TOUROKU_COUNT0)) {
                return __doExportNoDataDsp(map, form, req);
            }
        }

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                IpkConst.PLUGIN_ID_IPKANRI, TEMP_DIRECTORY_ID);
        String fileName = IpkCsvWriter.FILE_NAME;
        String fullPath = tempDir + fileName;

        //エクスポート
        forward = __doExport(form, getSessionUserSid(req), con, tempDir, req);

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textExport = gsMsg.getMessage("cmn.export");

        //ログ出力処理
        IpkBiz ipkBiz = new IpkBiz(con);
        ipkBiz.outPutLog(map, reqMdl,
                textExport, GSConstLog.LEVEL_INFO, fileName);

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);

        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                IpkConst.PLUGIN_ID_IPKANRI, TEMP_DIRECTORY_ID);
        return forward;
    }

    /**
     * <br>[機  能] エクスポート処理を実行(氏名カナ)
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @param outDir 出力先ディレクトリ
     * @param req リクエスト
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doExport(Ipk040Form form,
            int sessionUsrSid, Connection con, String outDir, HttpServletRequest req)
            throws Exception {

        log__.debug("エクスポート処理(CSV)");
        Ipk040ParamModel paramMdl = new Ipk040ParamModel();
        paramMdl.setParam(form);
        Ipk040Biz biz = new Ipk040Biz();
        //検索条件をセット
        IpkAddModel searchMdl = biz.setIpkSearchModel(paramMdl, con);
        paramMdl.setParam(form);

        //CSVファイルを作成
        IpkCsvWriter write = new IpkCsvWriter();
        write.setSearchAddModel(searchMdl);
        write.setSessionUserSid(sessionUsrSid);
        write.outputCsv(con, outDir, getRequestModel(req));
        return null;
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
        Ipk040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        //ネットワークSID
        int netSid = NullDefault.getInt(form.getNetSid(), -1);
        //バイナリSID
        Long binSid = NullDefault.getLong(form.getBinSid(), 0);

        IpkBiz ipkBiz = new IpkBiz();
        //IPアドレス一覧のネットワーク情報の添付ファイルがダウンロード可能かチェックする
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
     * <br>[機  能] ページコンボ時変更の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doChangePageCmb(ActionMapping map,
        Ipk040Form form,
        HttpServletRequest req,
        Connection con)
        throws SQLException, TempFileException {

        //ページ数をセット
        Ipk040ParamModel paramMdl = new Ipk040ParamModel();
        paramMdl.setParam(form);
        Ipk040Biz biz = new Ipk040Biz();
        biz.setPageCmb(paramMdl);
        paramMdl.setFormData(form);
        return __doInit(map, form, req, con);
    }

    /**
     * <br>[機  能] 状況表示、ソート、表示件数変更時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doChange(ActionMapping map,
        Ipk040Form form,
        HttpServletRequest req,
        Connection con)
        throws SQLException, TempFileException {

        //チェックボックスにnullをセットする。
        Ipk040ParamModel paramMdl = new Ipk040ParamModel();
        paramMdl.setParam(form);
        Ipk040Biz biz = new Ipk040Biz();
        biz.setDeleteCheckNull(paramMdl);
        paramMdl.setFormData(form);
        return __doInit(map, form, req, con);
    }

    /**
     * <br>[機  能] エクスポートするデータが0件の場合の画面の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doExportNoDataDsp(ActionMapping map, Ipk040Form form,
            HttpServletRequest req) {
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;

        //エクスポートするデータが0件の場合
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        forwardOK = map.findForward("iadDeleteBack");
        cmn999Form.setUrlOK(forwardOK.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.export.nodata"));
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        cmn999Form.addHiddenParam("netSid", form.getNetSid());
        cmn999Form.addHiddenParam("iadLimit", form.getIadLimit());
        cmn999Form.addHiddenParam("usedKbn", form.getUsedKbn());
        cmn999Form.addHiddenParam("ipk070KeyWord", form.getIpk070KeyWord());
        cmn999Form.addHiddenParam("iadPageNum", form.getIadPageNum());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("netInfDspFlg", form.getNetInfDspFlg());
        return forward;
    }
}