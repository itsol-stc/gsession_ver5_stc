package jp.groupsession.v2.ip.ipk050;

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
import jp.groupsession.v2.ip.model.IpkAddModel;
import jp.groupsession.v2.ip.model.ValidateCheckModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] IP管理 IPアドレス登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk050Action extends AbstractIpAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk050Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "ipk050";

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

        if (cmd.equals("fileDownload") || cmd.equals("ipAdrFileDownload")) {
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
        Ipk050Form ipkForm = (Ipk050Form) form;
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
        log__.debug("CMD =" + cmd);
        if (cmd.equals("ipReturn")) {
            //戻るボタンクリック
            forward = __doReturn(map, req, ipkForm);

        } else if (cmd.equals("iadEdit") || cmd.equals("iadAdd")) {
            //登録ボタンクリック
            forward = __doInsert(map, ipkForm, req, res, con);

        } else if (cmd.equals("ipAdd")) {
            //追加ボタンクリック
            forward = __doInit(map, ipkForm, req, con);

        } else if (cmd.equals("ipEdit")) {
            //変更ボタンクリック
            forward = __doInit(map, ipkForm, req, con);

        } else if (cmd.equals("fileDownload")) {
            //添付ファイルリンククリック(ネットワーク情報)
            forward = __doDownLoadTemp(map, ipkForm, req, res, con);

        } else if (cmd.equals("ipAdrFileDownload")) {
            //添付ファイルリンククリック(IPアドレス情報)
            forward = __doDownLoadTempIpAdr(map, ipkForm, req, res, con);

        } else if (cmd.equals("ipadDelete")) {
            //削除ボタンクリック
            forward = __doDeleteIpadKnDsp(map, ipkForm, req);

        } else if (cmd.equals("iadDeleteKn")) {
            //削除OKボタンクリック
            forward = __doDeleteIpad(map, ipkForm, req, res, con);

        } else if (cmd.equals("iadDeleteBack")) {
            //削除cancelボタンクリック
            forward = __doInitAg(map, ipkForm, req, con);

        } else if (cmd.equals("koukaiTempDownload")) {
            //ダウンロードボタンクリック(公開)
            forward = __doDownloadKoukaiFile(map, ipkForm, req, res, con);

        } else if (cmd.equals("hikoukaiTempDownload")) {
            //ダウンロードボタンクリック(非公開)
            forward = __doDownloadHikoukaiFile(map, ipkForm, req, res, con);

        } else {
            //初期表示
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
     * @throws IOException 添付ファイルの操作に失敗
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return ActionForward フォワード
     */
    private ActionForward __doInit(ActionMapping map,
            Ipk050Form form,
            HttpServletRequest req,
            Connection con)
                    throws IOException, SQLException, IOToolsException, TempFileException {

        try {
            //テンポラリディレクトリの削除
            __deleteTempDir(req);
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.createTempDir(getRequestModel(req), IpkConst.PLUGIN_ID_IPKANRI,
                    TEMP_DIRECTORY_ID);
            temp.createTempDir(getRequestModel(req), IpkConst.PLUGIN_ID_IPKANRI,
                    TEMP_DIRECTORY_ID, IpkConst.IPK_TEMP_KOUKAI);
            temp.createTempDir(getRequestModel(req), IpkConst.PLUGIN_ID_IPKANRI,
                    TEMP_DIRECTORY_ID, IpkConst.IPK_TEMP_HIKOUKAI);


            if (NullDefault.getInt(form.getReturnCmd(), 0) == IpkConst.IPK_PAGEMODE_IPAD) {
                form.setIpk050NetSid(form.getNetSid());
            }

            RequestModel reqMdl = getRequestModel(req);
            Ipk050ParamModel paramMdl = new Ipk050ParamModel();
            paramMdl.setParam(form);
            Ipk050Biz biz = new Ipk050Biz();

            //管理権限を設定する。(GS、全ネットワーク管理者：true)
            paramMdl.setIpk050AdminFlg(
                    biz.isIpadAdmin(
                            NullDefault.getInt(paramMdl.getIpk050NetSid(), 0),
                            NullDefault.getInt(paramMdl.getIadSid(), 0),
                            reqMdl, con));

            //管理権限を設定する。(GS、全ネットワーク、ネットワーク管理者：true)
            paramMdl.setIpk050NetAdminFlg(
                    biz.isIpadAdmin(
                            NullDefault.getInt(paramMdl.getIpk050NetSid(), 0),
                            IpkConst.IADSID_NETWORK_TOUROKU,
                            reqMdl, con));

            //初期表示情報を画面にセットする。
            biz.setInitData(paramMdl, con, getAppRootPath(), __getTempDir(req), reqMdl);

            //管理者情報をセットする。
            biz.setInitAdminData(paramMdl, con, reqMdl);

            paramMdl.setFormData(form);

            //ヘルプパラメータを設定する。
            String cmd = form.getCMD();
            if (form.isIpk050AdminFlg()) {
                if (cmd.equals("ipEdit") || cmd.equals("ipk070detail")) {
                    form.setIpk050helpMode(IpkConst.IPK_HELP_HENSYUU);
                    form.setIpk050DspKbn(IpkConst.IPK_HELP_HENSYUU);
                } else {
                    form.setIpk050helpMode(IpkConst.IPK_HELP_TOUROKU);
                    form.setIpk050DspKbn(IpkConst.IPK_HELP_TOUROKU);
                }
            } else {
                form.setIpk050helpMode(IpkConst.IPK_HELP_SYOUSAI);
                form.setIpk050DspKbn(IpkConst.IPK_HELP_SYOUSAI);
            }

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
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doInitAg(ActionMapping map,
            Ipk050Form form,
            HttpServletRequest req,
            Connection con)
                    throws IOToolsException, IOException, SQLException, TempFileException {

        try {
            RequestModel reqMdl = getRequestModel(req);
            Ipk050ParamModel paramMdl = new Ipk050ParamModel();
            paramMdl.setParam(form);
            Ipk050Biz biz = new Ipk050Biz();

            //管理権限を設定する。
            paramMdl.setIpk050AdminFlg(
                    biz.isIpadAdmin(
                            NullDefault.getInt(paramMdl.getIpk050NetSid(), 0),
                            NullDefault.getInt(paramMdl.getIadSid(), 0),
                            reqMdl, con));

            //管理権限を設定する。
            paramMdl.setIpk050NetAdminFlg(
                    biz.isIpadAdmin(
                            NullDefault.getInt(paramMdl.getIpk050NetSid(), 0),
                            IpkConst.IADSID_NETWORK_TOUROKU, reqMdl, con));

            //画面表示情報を設定する。
            biz.setInitDataAg(paramMdl, con, getAppRootPath(), __getTempDir(req), reqMdl);

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
     * @param form アクションフォーム
     * @return ActionForward
     * @throws IOToolsException ファイルアクセス時例外
     */
    private ActionForward __doReturn(
            ActionMapping map,
            HttpServletRequest req,
            Ipk050Form form)
                    throws IOToolsException  {

        ActionForward forward = null;

        //テンポラリディレクトリの削除
        __deleteTempDir(req);

        if (NullDefault.getInt(form.getReturnCmd(), 0) == IpkConst.IPK_PAGEMODE_SEARCH) {
            forward = map.findForward("ipk050ReturnSearch");
        } else {
            forward = map.findForward("ipk050Return");
        }
        return forward;
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
    private ActionForward __doInsert(
            ActionMapping map,
            Ipk050Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        //入力チェックを行う
        ActionErrors errors = null;
        errors = form.validateCheck(con, reqMdl);
        if (errors != null && !errors.isEmpty()) {
            addErrors(req, errors);
            return __doInitAg(map, form, req, con);
        }

        Ipk050ParamModel paramMdl = new Ipk050ParamModel();
        paramMdl.setParam(form);
        Ipk050Biz biz = new Ipk050Biz();

        //存在チェック用モデルの作成
        ValidateCheckModel model = biz.getvalidateCheckModel(paramMdl, con);
        paramMdl.setFormData(form);

        errors = form.validateCheck(model, reqMdl);

        //IPアドレスの存在チェック
        if (errors != null && !errors.isEmpty()) {
            addErrors(req, errors);
            return __doInitAg(map, form, req, con);
        }
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);
        ActionForward forward = null;

        int sessionUsrSid = getSessionUserSid(req);
        //IPアドレスSidの採番をする。
        int newIadSid = (int) cntCon.getSaibanNumber(
                IpkConst.PLUGIN_ID_IPKANRI, IpkConst.IPADDRESS, sessionUsrSid);

        IpkAddModel addModel = new IpkAddModel();
        addModel.setNewIadSid(newIadSid);
        addModel.setAppRootPath(getAppRootPath());
        addModel.setTempDir(__getTempDir(req));

        //IPアドレス情報の登録
        biz.setiadUpdate(paramMdl, con, sessionUsrSid, cntCon, addModel);

        //IPアドレス使用者情報の登録
        biz.setIadAdminUpdate(paramMdl, con, newIadSid, sessionUsrSid);

        paramMdl.setFormData(form);


        //ログ出力処理
        String opCode = "";
        String cmd = NullDefault.getString(form.getCMD(),  "");
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (cmd.equals("iadAdd")) {
            String textCreate = gsMsg.getMessage("cmn.entry");
            opCode = textCreate;
        } else if (cmd.equals("iadEdit")) {
            String textEdit = gsMsg.getMessage("cmn.change");
            opCode = textEdit;
        }

        IpkBiz ipkBiz = new IpkBiz(con);
        ipkBiz.outPutLog(map, reqMdl,
                opCode, GSConstLog.LEVEL_TRACE, "[name]" + form.getIadMachineName());

        forward = __doInsertCompDsp(map, form, req);

        //TEMPディレクトリ削除
        __deleteTempDir(req);
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
     * @throws IOException 入出力時例外
     * @return ActionForward
     */
    private ActionForward __doDownloadKoukaiFile(
            ActionMapping map,
            Ipk050Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception, IOToolsException, IOException {

        try {
            Ipk050Biz biz = new Ipk050Biz();
            String[] pathList = form.getIpk050KoukaiFiles();
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
            String fileName = biz.getDownloadFile(__getTempDir(req)
                    + IpkConst.IPK_TEMP_KOUKAI, binFilePath);

            //添付ファイル保存用のパスを取得する(フルパス)
            String filePath = __getTempDir(req)
                    + IpkConst.IPK_TEMP_KOUKAI + "/" +  binFilePath + "file";

            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String textDownload = gsMsg.getMessage("cmn.download");

            //ログ出力
            IpkBiz ipkBiz = new IpkBiz(con);
            ipkBiz.outPutLog(map, reqMdl,
                    textDownload, GSConstLog.LEVEL_INFO, fileName, form.getBinSid());

            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);
            con = null;

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
     * @throws IOException 入出力時例外
     * @return ActionForward
     */
    private ActionForward __doDownloadHikoukaiFile(
            ActionMapping map,
            Ipk050Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception, IOToolsException, IOException {

        try {
            Ipk050Biz biz = new Ipk050Biz();
            String[] pathList = form.getIpk050HikoukaiFiles();
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
            String fileName = biz.getDownloadFile(__getTempDir(req)
                    + "/" + IpkConst.IPK_TEMP_HIKOUKAI, binFilePath);

            //添付ファイル保存用のパスを取得する(フルパス)
            String filePath = __getTempDir(req)
                    + IpkConst.IPK_TEMP_HIKOUKAI + "/" + binFilePath + "file";

            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String textDownload = gsMsg.getMessage("cmn.download");

            //ログ出力
            IpkBiz ipkBiz = new IpkBiz(con);
            ipkBiz.outPutLog(map, reqMdl,
                    textDownload, GSConstLog.LEVEL_INFO, fileName, form.getBinSid());

            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);
            con = null;

            //ファイルをダウンロードする
            TempFileUtil.downloadAtachment(req, res, filePath, fileName,
                    Encoding.UTF_8);

            return null;

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] 登録完了画面の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doInsertCompDsp(ActionMapping map, Ipk050Form form,
            HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage();
        String textIpad = gsMsg.getMessage(req, "ipk.6");

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        //登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        if (NullDefault.getInt(form.getReturnCmd(), 0) == IpkConst.IPK_PAGEMODE_SEARCH) {
            urlForward = map.findForward("ipk050ReturnSearch");
        } else {
            urlForward = map.findForward("ipk050Return");
        }
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                textIpad));
        cmn999Form.addHiddenParam("netSid", form.getNetSid());
        cmn999Form.addHiddenParam("ipk050NetSid", form.getIpk050NetSid());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());
        cmn999Form.addHiddenParam("iadPageNum", form.getIadPageNum());
        cmn999Form.addHiddenParam("maxPageNum", form.getMaxPageNum());
        cmn999Form.addHiddenParam("usedKbn", form.getUsedKbn());
        cmn999Form.addHiddenParam("iadLimit", form.getIadLimit());
        cmn999Form.addHiddenParam("deleteAllCheck", form.getDeleteAllCheck());
        cmn999Form.addHiddenParam("deleteCheck", form.getDeleteCheck());
        cmn999Form.addHiddenParam("netInfDspFlg", form.getNetInfDspFlg());
        cmn999Form.addHiddenParam("returnCmd", form.getReturnCmd());
        cmn999Form.addHiddenParam("ipk070PageNow", form.getIpk070PageNow());
        cmn999Form.addHiddenParam("ipk070MaxPageNum", form.getIpk070MaxPageNum());
        cmn999Form.addHiddenParam("ipk070SltNet", form.getIpk070SltNet());
        cmn999Form.addHiddenParam("ipk070SltUser", form.getIpk070SltUser());
        cmn999Form.addHiddenParam("ipk070SltGroup", form.getIpk070SltGroup());
        cmn999Form.addHiddenParam("ipk070SearchTarget", form.getIpk070SearchTarget());
        cmn999Form.addHiddenParam("ipk070SearchSortKey1", form.getIpk070SearchSortKey1());
        cmn999Form.addHiddenParam("ipk070SearchSortKey2", form.getIpk070SearchSortKey2());
        cmn999Form.addHiddenParam("ipk070SearchOrderKey1", form.getIpk070SearchOrderKey1());
        cmn999Form.addHiddenParam("ipk070SearchOrderKey2", form.getIpk070SearchOrderKey2());
        cmn999Form.addHiddenParam("ipk070KeyWord", form.getIpk070KeyWord());
        cmn999Form.addHiddenParam("ipk070SvNetSid", form.getIpk070SvNetSid());
        cmn999Form.addHiddenParam("ipk070KeyWordkbn", form.getIpk070KeyWordkbn());
        cmn999Form.addHiddenParam("ipk070SvSltNet", form.getIpk070SvSltNet());
        cmn999Form.addHiddenParam("ipk070SvGrpSid", form.getIpk070SvGrpSid());
        cmn999Form.addHiddenParam("ipk070SvUsrSid", form.getIpk070SvUsrSid());
        cmn999Form.addHiddenParam("ipk070SvSearchTarget", form.getIpk070SvSearchTarget());
        cmn999Form.addHiddenParam("ipk070SvSearchSortKey1", form.getIpk070SvSearchSortKey1());
        cmn999Form.addHiddenParam("ipk070SvSearchSortKey2", form.getIpk070SvSearchSortKey2());
        cmn999Form.addHiddenParam("ipk070SvSearchOrderKey1", form.getIpk070SvSearchOrderKey1());
        cmn999Form.addHiddenParam("ipk070SvSearchOrderKey2", form.getIpk070SvSearchOrderKey2());
        cmn999Form.addHiddenParam("ipk070SvKeyWord", form.getIpk070SvKeyWord());
        cmn999Form.addHiddenParam("ipk070SvKeyWordkbn", form.getIpk070SvKeyWordkbn());

        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] IPアドレスの削除処理を行う
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
            Ipk050Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        //IPアドレス情報を削除
        Ipk050Biz biz = new Ipk050Biz();
        int netSid = NullDefault.getInt(form.getIpk050NetSid(), 0);
        int iadSid = NullDefault.getInt(form.getIadSid(), 0);
        biz.deleteIpad(netSid, iadSid, con, getSessionUserSid(req));
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req), IpkConst.PLUGIN_ID_IPKANRI, TEMP_DIRECTORY_ID);

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDel = gsMsg.getMessage("cmn.delete");

        //ログ出力
        IpkBiz ipkBiz = new IpkBiz(con);
        ipkBiz.outPutLog(map, reqMdl,
                textDel, GSConstLog.LEVEL_TRACE, "[name]" + form.getIadMachineName());

        return __doDeleteIpadCompDsp(map, form, req);
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
            Ipk050Form form,
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
    private ActionForward __doDownLoadTempIpAdr(
            ActionMapping map,
            Ipk050Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        //ネットワークSID
        int netSid = NullDefault.getInt(form.getNetSid(), -1);
        //IPアドレスSID
        int iadSid = NullDefault.getInt(form.getIadSid(), -2);
        //バイナリSID
        Long binSid = NullDefault.getLong(form.getBinSid(), 0);
        IpkBiz ipkBiz = new IpkBiz();
        //IPアドレス詳細画面でIPアドレス情報の添付ファイルがダウンロード可能かチェックする
        if (ipkBiz.isCheckDLIpAddress(
                con, getRequestModel(req), netSid, iadSid, binSid, getSessionUserSid(req))) {

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
     * <br>[機  能] 削除確認画面の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doDeleteIpadKnDsp(ActionMapping map, Ipk050Form form,
            HttpServletRequest req) {

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;
        ActionForward forwardCancel = null;

        // トランザクショントークン設定
        this.saveToken(req);

        GsMessage gsMsg = new GsMessage();
        String textIpad = gsMsg.getMessage(req, "ipk.6");

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
                textIpad));
        cmn999Form.addHiddenParam("netSid", form.getNetSid());
        cmn999Form.addHiddenParam("ipk050NetSid", form.getIpk050NetSid());
        cmn999Form.addHiddenParam("iadSid", form.getIadSid());
        cmn999Form.addHiddenParam("iadLimit", form.getIadLimit());
        cmn999Form.addHiddenParam("usedKbn", form.getUsedKbn());
        cmn999Form.addHiddenParam("iadPageNum", form.getIadPageNum());
        cmn999Form.addHiddenParam("deleteCheck", form.getDeleteCheck());
        cmn999Form.addHiddenParam("deleteAllCheck", form.getDeleteAllCheck());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("textNum", form.getTextNum());
        cmn999Form.addHiddenParam("iadAddText1", form.getIadAddText1());
        cmn999Form.addHiddenParam("iadAddText2", form.getIadAddText2());
        cmn999Form.addHiddenParam("iadAddText3", form.getIadAddText3());
        cmn999Form.addHiddenParam("iadAddText4", form.getIadAddText4());
        cmn999Form.addHiddenParam("iadMachineName", form.getIadMachineName());
        cmn999Form.addHiddenParam("iadUse", form.getIadUse());
        cmn999Form.addHiddenParam("iadMsg", form.getIadMsg());
        cmn999Form.addHiddenParam("iadPrtMngNum", form.getIadPrtMngNum());
        cmn999Form.addHiddenParam("adminSidList", form.getAdminSidList());
        cmn999Form.addHiddenParam("netInfDspFlg", form.getNetInfDspFlg());
        cmn999Form.addHiddenParam("cpuSelect", form.getCpuSelect());
        cmn999Form.addHiddenParam("memorySelect", form.getMemorySelect());
        cmn999Form.addHiddenParam("hdSelect", form.getHdSelect());
        cmn999Form.addHiddenParam("returnCmd", form.getReturnCmd());

        cmn999Form.addHiddenParam("ipk070PageNow", form.getIpk070PageNow());
        cmn999Form.addHiddenParam("ipk070MaxPageNum", form.getIpk070MaxPageNum());
        cmn999Form.addHiddenParam("ipk070SltNet", form.getIpk070SltNet());
        cmn999Form.addHiddenParam("ipk070SvGrpSid", form.getIpk070SvGrpSid());
        cmn999Form.addHiddenParam("ipk070SltUser", form.getIpk070SltUser());
        cmn999Form.addHiddenParam("ipk070SltGroup", form.getIpk070SltGroup());
        cmn999Form.addHiddenParam("ipk070SearchTarget", form.getIpk070SearchTarget());
        cmn999Form.addHiddenParam("ipk070SearchSortKey1", form.getIpk070SearchSortKey1());
        cmn999Form.addHiddenParam("ipk070SearchSortKey2", form.getIpk070SearchSortKey2());
        cmn999Form.addHiddenParam("ipk070SearchOrderKey1", form.getIpk070SearchOrderKey1());
        cmn999Form.addHiddenParam("ipk070SearchOrderKey2", form.getIpk070SearchOrderKey2());
        cmn999Form.addHiddenParam("ipk070KeyWord", form.getIpk070KeyWord());
        cmn999Form.addHiddenParam("ipk070SvNetSid", form.getIpk070SvNetSid());
        cmn999Form.addHiddenParam("ipk070KeyWordkbn", form.getIpk070KeyWordkbn());
        cmn999Form.addHiddenParam("ipk070SvSltNet", form.getIpk070SvSltNet());
        cmn999Form.addHiddenParam("ipk070SvUsrSid", form.getIpk070SvUsrSid());
        cmn999Form.addHiddenParam("ipk070SvSearchTarget", form.getIpk070SvSearchTarget());
        cmn999Form.addHiddenParam("ipk070SvSearchSortKey1", form.getIpk070SvSearchSortKey1());
        cmn999Form.addHiddenParam("ipk070SvSearchSortKey2", form.getIpk070SvSearchSortKey2());
        cmn999Form.addHiddenParam("ipk070SvSearchOrderKey1", form.getIpk070SvSearchOrderKey1());
        cmn999Form.addHiddenParam("ipk070SvSearchOrderKey2", form.getIpk070SvSearchOrderKey2());
        cmn999Form.addHiddenParam("ipk070SvKeyWord", form.getIpk070SvKeyWord());
        cmn999Form.addHiddenParam("ipk070SvKeyWordkbn", form.getIpk070SvKeyWordkbn());

        cmn999Form.addHiddenParam("ipk050DspKbn", form.getIpk050DspKbn());

        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");

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
    private ActionForward __doDeleteIpadCompDsp(ActionMapping map, Ipk050Form form,
            HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage();
        String textIpad = gsMsg.getMessage(req, "ipk.6");

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward forwardOK = null;
        //ネットワーク削除の削除完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        //遷移元ページへ画面遷移する。
        if (NullDefault.getInt(form.getReturnCmd(), 0) == IpkConst.IPK_PAGEMODE_SEARCH) {
            forwardOK = map.findForward("ipk050ReturnSearch");
        } else {
            forwardOK = map.findForward("iadDeleteComp");
        }
        cmn999Form.setUrlOK(forwardOK.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object",
                textIpad));
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        cmn999Form.addHiddenParam("netSid", form.getNetSid());
        cmn999Form.addHiddenParam("ipk050NetSid", form.getIpk050NetSid());
        cmn999Form.addHiddenParam("iadSid", form.getIadSid());
        cmn999Form.addHiddenParam("iadLimit", form.getIadLimit());
        cmn999Form.addHiddenParam("usedKbn", form.getUsedKbn());
        cmn999Form.addHiddenParam("iadPageNum", form.getIadPageNum());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("returnCmd", form.getReturnCmd());

        cmn999Form.addHiddenParam("ipk070PageNow", form.getIpk070PageNow());
        cmn999Form.addHiddenParam("ipk070MaxPageNum", form.getIpk070MaxPageNum());
        cmn999Form.addHiddenParam("ipk070SltNet", form.getIpk070SltNet());
        cmn999Form.addHiddenParam("ipk070SltUser", form.getIpk070SltUser());
        cmn999Form.addHiddenParam("ipk070SltGroup", form.getIpk070SltGroup());
        cmn999Form.addHiddenParam("ipk070SearchTarget", form.getIpk070SearchTarget());
        cmn999Form.addHiddenParam("ipk070SearchSortKey1", form.getIpk070SearchSortKey1());
        cmn999Form.addHiddenParam("ipk070SearchSortKey2", form.getIpk070SearchSortKey2());
        cmn999Form.addHiddenParam("ipk070SearchOrderKey1", form.getIpk070SearchOrderKey1());
        cmn999Form.addHiddenParam("ipk070SearchOrderKey2", form.getIpk070SearchOrderKey2());
        cmn999Form.addHiddenParam("ipk070KeyWord", form.getIpk070KeyWord());
        cmn999Form.addHiddenParam("ipk070SvNetSid", form.getIpk070SvNetSid());
        cmn999Form.addHiddenParam("ipk070KeyWordkbn", form.getIpk070KeyWordkbn());
        cmn999Form.addHiddenParam("ipk070SvSltNet", form.getIpk070SvSltNet());
        cmn999Form.addHiddenParam("ipk070SvGrpSid", form.getIpk070SvGrpSid());
        cmn999Form.addHiddenParam("ipk070SvUsrSid", form.getIpk070SvUsrSid());
        cmn999Form.addHiddenParam("ipk070SvSearchTarget", form.getIpk070SvSearchTarget());
        cmn999Form.addHiddenParam("ipk070SvSearchSortKey1", form.getIpk070SvSearchSortKey1());
        cmn999Form.addHiddenParam("ipk070SvSearchSortKey2", form.getIpk070SvSearchSortKey2());
        cmn999Form.addHiddenParam("ipk070SvSearchOrderKey1", form.getIpk070SvSearchOrderKey1());
        cmn999Form.addHiddenParam("ipk070SvSearchOrderKey2", form.getIpk070SvSearchOrderKey2());
        cmn999Form.addHiddenParam("ipk070SvKeyWord", form.getIpk070SvKeyWord());
        cmn999Form.addHiddenParam("ipk070SvKeyWordkbn", form.getIpk070SvKeyWordkbn());

        return forward;
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