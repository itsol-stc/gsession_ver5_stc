package jp.groupsession.v2.ip.ipk020;

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
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.AbstractIpAction;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.model.IpkNetModel;
import jp.groupsession.v2.ip.model.ValidateCheckModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] IP管理 ネットワーク登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk020Action extends AbstractIpAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk020Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "ipk020";


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

        if (cmd.equals("fileDownload")) {
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
        Ipk020Form ipkForm = (Ipk020Form) form;
        //非公開ネットワークSID時の制御
        IpkBiz ipkBiz = new IpkBiz();
        if (!ipkBiz.isNotDspNetSid(NullDefault.getInt(ipkForm.getNetSid(), 0),
                getRequestModel(req), con)) {
            log__.debug("不正アクセスエラー");
            return map.findForward("gf_submit");
        }

        log__.debug("START");
        //コマンド
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        log__.debug("cmd = " + cmd);
        //戻るクリック
        if (cmd.equals("return")) {
            forward = __doReturn(map, req);

            //登録・変更ボタンクリック
        } else if (cmd.equals("netAdd") || cmd.equals("netEdit")) {
            forward = __doInsertNetwork(map, ipkForm, req, res, con);

            //ネットワークの削除OKボタンクリック 実際に削除
        } else if (cmd.equals("networkDeleteKn")) {
            forward = __doDelete(map, ipkForm, req, res, con);

            //ネットワークの削除ボタンクリック 確認画面表示
        } else if (cmd.equals("netDelete")) {
            forward = __doDeleteKn(map, ipkForm, req);

            //ネットワーク削除Canselボタンクリック
        } else if (cmd.equals("networkDeleteBack")) {
            forward = __doInitAg(map, ipkForm, req, con);

            //添付ファイルリンククリック
        } else if (cmd.equals("fileDownload")) {
            __doDownLoadTemp(map, ipkForm, req, res, con);

            return null;

            //ダウンロードボタンクリック(公開)
        } else if (cmd.equals("koukaiTempDownload")) {
            forward = __doDownloadKoukaiFile(map, ipkForm, req, res, con);

            //ダウンロードボタンクリック(非公開)
        } else if (cmd.equals("hikoukaiTempDownload")) {
            forward = __doDownloadHikoukaiFile(map, ipkForm, req, res, con);

            //初期表示
        } else {
            forward = __doInit(map, ipkForm, req, con);
        }
        log__.debug("END");
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
     * @throws IOException 添付ファイルの操作に失敗
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doInit(ActionMapping map,
            Ipk020Form form,
            HttpServletRequest req,
            Connection con)
                    throws IOException, SQLException, IOToolsException, TempFileException {

        try {
            RequestModel reqMdl = getRequestModel(req);
            Ipk020ParamModel paramMdl = new Ipk020ParamModel();
            paramMdl.setParam(form);
            Ipk020Biz biz = new Ipk020Biz();
            IpkBiz ipBiz = new IpkBiz();

            String cmd = NullDefault.getString(form.getCMD(), "");
            //管理者権限、ヘルプパラメータを設定する。
            if (cmd.equals("networkAdd")) {
                paramMdl.setIpk020AdminFlg(IpkConst.KANRI_KENGEN_ARI);
                paramMdl.setIpk020helpMode(IpkConst.IPK_HELP_TOUROKU);
            } else {
                paramMdl.setIpk020AdminFlg(ipBiz.isNetworkAdmin(
                        NullDefault.getInt(paramMdl.getNetSid(), 0), reqMdl, con));
                if (paramMdl.isIpk020AdminFlg()) {
                    paramMdl.setIpk020helpMode(IpkConst.IPK_HELP_HENSYUU);
                } else {
                    paramMdl.setIpk020helpMode(IpkConst.IPK_HELP_SYOUSAI);
                }
            }

            //テンポラリディレクトリの削除
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            __deleteTempDir(req);
            temp.createTempDir(getRequestModel(req), IpkConst.PLUGIN_ID_IPKANRI,
                    TEMP_DIRECTORY_ID, IpkConst.IPK_TEMP_KOUKAI);
            temp.createTempDir(getRequestModel(req), IpkConst.PLUGIN_ID_IPKANRI,
                    TEMP_DIRECTORY_ID, IpkConst.IPK_TEMP_HIKOUKAI);
            String tempDir = __getTempDir(req);

            String netSid = req.getParameter("netSid");
            if (StringUtil.isNullZeroString(netSid)) {
                netSid = "";
            } else {
                //ネットワーク情報をセットする。
                biz.setInitData(paramMdl, con, netSid, getAppRootPath(), tempDir, reqMdl);
            }
            //管理者情報をセットする。
            biz.setInitAdminData(paramMdl, con, reqMdl);

            paramMdl.setFormData(form);
        } catch (SQLException e) {
            throw e;
        }

        //トランザクショントークン設定
        saveToken(req);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 画面再表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doInitAg(ActionMapping map,
            Ipk020Form form,
            HttpServletRequest req,
            Connection con)
                    throws SQLException, IOToolsException, TempFileException {
        try {
            RequestModel reqMdl = getRequestModel(req);
            Ipk020ParamModel paramMdl = new Ipk020ParamModel();
            paramMdl.setParam(form);
            Ipk020Biz biz = new Ipk020Biz();
            IpkBiz ipBiz = new IpkBiz();

            //管理者権限を設定する。
            String netSid = NullDefault.getString(req.getParameter("netSid"), "");
            if (!StringUtil.isNullZeroStringSpace(netSid)) {
                paramMdl.setIpk020AdminFlg(ipBiz.isNetworkAdmin(
                        NullDefault.getInt(paramMdl.getNetSid(), 0), reqMdl, con));
            }

            //添付ファイル一覧を設定
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            String tempDir = temp.getTempPath(getRequestModel(req), IpkConst.PLUGIN_ID_IPKANRI,
                    TEMP_DIRECTORY_ID);
            biz.setTempList(paramMdl, tempDir, con, reqMdl);

            paramMdl.setFormData(form);
        } catch (SQLException e) {
            throw e;
        }

        //トランザクショントークン設定
        saveToken(req);
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
        __deleteTempDir(req);

        return map.findForward("return");
    }

    /**
     * <br>[機  能] 登録ボタンクリック時の処理
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
    private ActionForward __doInsertNetwork(
            ActionMapping map,
            Ipk020Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);
        Ipk020Biz biz = new Ipk020Biz();
        //入力チェックを行う
        ValidateCheckModel validateCheckModel
        = biz.getValidateModel(form.getNetad(), con);

        ActionErrors errors = null;
        errors = form.validateCheck(validateCheckModel, con, reqMdl);
        if (errors != null && !errors.isEmpty()) {
            log__.debug("入力チェック");
            addErrors(req, errors);
            return __doInitAg(map, form, req, con);
        }

        //セッションユーザSIDを取得する。
        int sessionUsrSid = getSessionUserSid(req);
        //ネットワークSidの採番をする。
        MlCountMtController cntCon = getCountMtController(req);
        int newNetSid = (int) cntCon.getSaibanNumber("ipkanri", "network", sessionUsrSid);

        ActionForward forward = null;

        Ipk020ParamModel paramMdl = new Ipk020ParamModel();
        paramMdl.setParam(form);

        //ネットワーク情報を設定する。
        IpkNetModel model = new IpkNetModel();
        model.setNewNetSid(newNetSid);
        model.setTempDir(__getTempDir(req));
        model.setAppRootPath(getAppRootPath());
        biz.setNetworkData(paramMdl, con, sessionUsrSid, cntCon, model);

        //ネットワーク管理者情報を設定する。
        biz.setAdmindata(paramMdl, con, sessionUsrSid, newNetSid);

        paramMdl.setFormData(form);


        //ログ出力処理
        String opCode = "";
        String cmd = NullDefault.getString(form.getCMD(),  "");
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (cmd.equals("netAdd")) {
            String textCreate = gsMsg.getMessage("cmn.entry");
            opCode = textCreate;
        } else if (cmd.equals("netEdit")) {
            String textEdit = gsMsg.getMessage("cmn.change");
            opCode = textEdit;
        }

        IpkBiz ipkBiz = new IpkBiz(con);
        ipkBiz.outPutLog(map, reqMdl,
                opCode, GSConstLog.LEVEL_TRACE, "[name]" + form.getNetName());

        //登録完了画面の設定
        forward = __doInsertNetworkCompDsp(map, req);

        //TEMPディレクトリ削除
        __deleteTempDir(req);
        return forward;
    }


    /**
     * <br>[機  能] 登録完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doInsertNetworkCompDsp(ActionMapping map,
            HttpServletRequest req) {
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        GsMessage gsMsg = new GsMessage();
        String textNetwork = gsMsg.getMessage(req, "ipk.4");

        //登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("return");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                textNetwork));
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] ネットワーク情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doDelete(ActionMapping map,
            Ipk020Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {
        ActionForward forward = null;

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        //ネットワーク一覧情報を削除する。
        Ipk020Biz biz = new Ipk020Biz();
        biz.deleteNetwork(NullDefault.getInt(form.getNetSid(), -2), con, getSessionUserSid(req));
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req), IpkConst.PLUGIN_ID_IPKANRI, TEMP_DIRECTORY_ID);

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDel = gsMsg.getMessage("cmn.delete");

        //ログ出力処理
        IpkBiz ipkBiz = new IpkBiz(con);
        ipkBiz.outPutLog(map, reqMdl,
                textDel, GSConstLog.LEVEL_TRACE, "[name]" + form.getNetName());

        //削除完了画面を表示する。
        forward = __doDeleteCompDsp(map, form, req);
        return forward;
    }

    /**
     * <br>[機  能] ダウンロードボタンクリック時の処理（公開）
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @return ActionForward
     */
    private ActionForward __doDownloadKoukaiFile(
            ActionMapping map,
            Ipk020Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception, IOToolsException {

        try {
            Ipk020Biz biz = new Ipk020Biz();
            String[] pathList = form.getIpk020KoukaiFiles();
            if (pathList == null || pathList.length == 0) {
                return __doInitAg(map, form, req, con);
            }
            String binFilePath = "";
            for (String path : pathList) {
                //pathの半角数字チェック処理
                if (!ValidateUtil.isNumber(path)) {
                    return getSubmitErrorPage(map, req);
                }
                binFilePath = path;
            }

            //コンボで選択されているファイル名を取得する。
            String fileName = biz.getDownloadFile(
                    __getTempDir(req) + IpkConst.IPK_TEMP_KOUKAI, binFilePath);

            //添付ファイル保存用のパスを取得する(フルパス)
            String filePath = __getTempDir(req)
                    + IpkConst.IPK_TEMP_KOUKAI + "/" +  binFilePath + "file";

            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String textDownload = gsMsg.getMessage("cmn.download");

            //ログ出力処理
            IpkBiz ipkBiz = new IpkBiz(con);
            ipkBiz.outPutLog(map, reqMdl,
                    textDownload, GSConstLog.LEVEL_INFO, fileName, form.getBinSid());

            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadAtachment(req, res, filePath, fileName,
                    Encoding.UTF_8);

            return null;

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] ダウンロードボタンクリック時の処理（非公開）
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @return ActionForward
     */
    private ActionForward __doDownloadHikoukaiFile(
            ActionMapping map,
            Ipk020Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception, IOToolsException {

        try {
            Ipk020Biz biz = new Ipk020Biz();
            String[] pathList = form.getIpk020HikoukaiFiles();
            if (pathList == null || pathList.length == 0) {
                return __doInitAg(map, form, req, con);
            }
            String binFilePath = "";
            for (String path : pathList) {
                //pathの半角数字チェック処理
                if (!ValidateUtil.isNumber(path)) {
                    return getSubmitErrorPage(map, req);
                }
                binFilePath = path;
            }

            //コンボで選択されているファイル名を取得する。
            String fileName = biz.getDownloadFile(
                    __getTempDir(req) + IpkConst.IPK_TEMP_HIKOUKAI, binFilePath);

            //添付ファイル保存用のパスを取得する(フルパス)
            String filePath = __getTempDir(req)
                    + IpkConst.IPK_TEMP_HIKOUKAI + "/" + binFilePath + "file";

            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String textDownload = gsMsg.getMessage("cmn.download");

            //ログ出力処理
            IpkBiz ipkBiz = new IpkBiz(con);
            ipkBiz.outPutLog(map, reqMdl,
                    textDownload, GSConstLog.LEVEL_INFO, fileName, form.getBinSid());

            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadAtachment(req, res, filePath, fileName,
                    Encoding.UTF_8);

            return null;

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] 削除確認画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doDeleteKn(ActionMapping map, Ipk020Form form,
            HttpServletRequest req) {
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;
        ActionForward forwardCancel = null;

        GsMessage gsMsg = new GsMessage();
        String textNetwork = gsMsg.getMessage(req, "ipk.4");

        //ネットワーク削除の削除確認画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        forwardOK = map.findForward("networkDeleteKn");
        cmn999Form.setUrlOK(forwardOK.getPath());
        forwardCancel = map.findForward("networkDeleteBack");
        cmn999Form.setUrlCancel(forwardCancel.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.once",
                textNetwork));
        cmn999Form.addHiddenParam("netSid", form.getNetSid());
        cmn999Form.addHiddenParam("CMD", form.getCMD());
        cmn999Form.addHiddenParam("netName", form.getNetName());
        cmn999Form.addHiddenParam("ipk020TempDsp", form.getIpk020TempDsp());
        cmn999Form.addHiddenParam("netNetad1", form.getNetNetad1());
        cmn999Form.addHiddenParam("netNetad2", form.getNetNetad2());
        cmn999Form.addHiddenParam("netNetad3", form.getNetNetad3());
        cmn999Form.addHiddenParam("netNetad4", form.getNetNetad4());
        cmn999Form.addHiddenParam("netSabnet1", form.getNetSabnet1());
        cmn999Form.addHiddenParam("netSabnet2", form.getNetSabnet2());
        cmn999Form.addHiddenParam("netSabnet3", form.getNetSabnet3());
        cmn999Form.addHiddenParam("netSabnet4", form.getNetSabnet4());
        cmn999Form.addHiddenParam("netSort", form.getNetSort());
        cmn999Form.addHiddenParam("netMsg", form.getNetMsg());
        cmn999Form.addHiddenParam("adminSidList", form.getAdminSidList());
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");

        //トランザクショントークン設定
        saveToken(req);

        return forward;
    }

    /**
     * <br>[機  能] 削除完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doDeleteCompDsp(ActionMapping map, Ipk020Form form,
            HttpServletRequest req) {
        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;

        GsMessage gsMsg = new GsMessage();
        String textNetwork = gsMsg.getMessage(req, "ipk.4");

        //ネットワーク削除の削除確認画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        forwardOK = map.findForward("networkDeleteComp");
        cmn999Form.setUrlOK(forwardOK.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object",
                textNetwork));
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        cmn999Form.addHiddenParam("netSid", form.getNetSid());
        return forward;
    }

    /**
     * <br>[機  能] 添付ファイルダウンロードの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
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
            Ipk020Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        //ネットワークSID
        int netSid = NullDefault.getInt(form.getNetSid(), -1);
        //バイナリSID
        Long binSid = NullDefault.getLong(form.getBinSid(), 0);
        IpkBiz ipkBiz = new IpkBiz();
        //ネットワーク詳細情報の添付ファイルがダウンロード可能かチェックする
        if (ipkBiz.isCheckDLNetwork(con, netSid, binSid, getSessionUserSid(req))) {
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
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return テンポラリディレクトリパス
     */
    private String __getTempDir(HttpServletRequest req) {

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        return temp.getTempPath(getRequestModel(req),
                IpkConst.PLUGIN_ID_IPKANRI, TEMP_DIRECTORY_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    private void __deleteTempDir(HttpServletRequest req) {

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                IpkConst.PLUGIN_ID_IPKANRI, TEMP_DIRECTORY_ID);
    }

}