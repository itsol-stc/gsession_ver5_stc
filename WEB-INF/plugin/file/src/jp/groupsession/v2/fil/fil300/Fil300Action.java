package jp.groupsession.v2.fil.fil300;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.FilTreeBiz;
import jp.groupsession.v2.fil.FilTreeForm;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileErrlAdddataDao;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;
import net.sf.json.JSONObject;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil300Action extends AbstractFileAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil300Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "fil300";
    /** テンポラリディレクトリID サブ(テンプレート用) */
    private static final String TEMP_DIRECTORY_ID_TEMP = "template";

    /** サンプルファイル名 */
    private static final String SAMPLE_FILE_NAME = "fileTransactionImport.xlsx";
    /** 出力用サンプルファイル名 */
    private static final String SAMPLE_OUTFILE_NAME = "取引情報取込み用ファイル.xlsx";

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

        log__.debug("fil300Action START");

        ActionForward forward = null;
        Fil300Form thisForm = (Fil300Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil300download")) {
            //保存先が存在する場合にダウンロードリンククリック
            forward = __doDownloadSample(map, thisForm, req, res, con, true);
        } else if (cmd.equals("fil300downloadNoSavePath")) {
            //保存先が存在しないファイル表示時にダウンロードリンククリック
            forward = __doDownloadSample(map, thisForm, req, res, con, false);
        } else if (cmd.equals("fil300importCheck")) {
            //取込みボタンクリック
            forward = __doImportCheck(map, thisForm, req, res, con);
        } else if (cmd.equals("fil300import")) {
            //確定ボタンクリック
            forward = __doImport(map, thisForm, req, res, con);
        } else if (cmd.equals("fil300selectDir")) {
            //フォルダまたはキャビネットクリック時
            forward = __doSyosaiDir(map, thisForm, req, res, con);
        }  else if (cmd.equals("fil300noParent")) {
            //保存先が存在しないファイルを押下時
            forward = __doSyosaiNoSavePath(map, thisForm, req, res, con);
        } else if (cmd.equals("fil300insertFile")) {
            //登録ボタンクリック
            forward = __doAddFile(map, thisForm, req, res, con);
        } else if (cmd.equals("fil300deleteFile")) {
            //削除ボタンクリック
            forward = __doDeleteFile(map, thisForm, req, res, con);
        } else if (cmd.equals("fil300getFileInfo")) {
            //ファイル情報取得
            forward = __doGetFileInfo(map, thisForm, req, res, con);
        } else if (cmd.equals("fil300getFolderPath")) {
            //フォルダパス取得
            forward = __doGetFolderPath(map, thisForm, req, res, con);
        } else if (cmd.equals("fil300downloadImage")) {
            //ファイル画像取得
            forward = __doDownLoadImage(map, thisForm, req, res, con);
        } else if (cmd.equals("fil300back")) {
            //ファイル画像取得
            forward = __doBack(map, thisForm, req, res, con);
        } else if (cmd.equals("fil330")) {
            //一括削除画面へ遷移
            forward = map.findForward("fil330");
        }  else if (cmd.equals("fil300moveAdd")) {
            //登録後の画面遷移処理
            forward = __doMoveAdd(map, thisForm, req, res, con);
        }  else if (cmd.equals("fil300moveDelete")) {
            //削除後の画面遷移処理
            forward = __doMoveDelete(map, thisForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 表示処理
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
    private ActionForward __doInit(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //編集可能な仮登録ファイルが存在するかを確認
        Fil300Biz biz = new Fil300Biz(getRequestModel(req), con);
        if (!biz.isErrlFile(con)) {
            GsMessage gsMsg = new GsMessage(req);
            return getTargetNotfoundPage(
                    map,
                    form,
                    req,
                    __doBack(map, form, req, res, con),
                    gsMsg.getMessage("fil.187"),
                    gsMsg.getMessage("cmn.entry"));
        }

        Fil300ParamModel paramMdl = new Fil300ParamModel();
        paramMdl.setParam(form);

        //フォルダ登録画面から遷移した場合、登録対象の権限チェックを行う
        if (form.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
            List<String> errlList080 = biz.getErrlFile080(paramMdl);
            if (errlList080.isEmpty()) {
                GsMessage gsMsg = new GsMessage(req);
                return getTargetNotfoundPage(
                        map,
                        form,
                        req,
                        __doBack(map, form, req, res, con),
                        gsMsg.getMessage("fil.187"),
                        gsMsg.getMessage("cmn.entry"));
            }
        }

        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
        pathUtil.deleteTempPath(getRequestModel(req),
                GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);
        pathUtil.createTempDir(getRequestModel(req),
                GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

         biz.setInitData(paramMdl);
         paramMdl.setFormData(form);

         saveToken(req);

         return map.getInputForward();
    }

    /**
     * <br>[機  能] 戻るボタン押下時処理
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
    private ActionForward __doBack(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
        pathUtil.deleteTempPath(
                getRequestModel(req), GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

        ActionForward forward = map.findForward("fil040");
        if (form.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL010) {
            if (form.getBackDsp().equals(GSConstFile.MOVE_TO_MAIN)) {
                forward = map.findForward("main");
            } else if (form.getBackDsp().equals(GSConstFile.MOVE_TO_FIL040)) {
                forward = map.findForward("fil040");
            } else {
                forward = map.findForward("cabinetMain");
            }
        }

        return forward;
    }

    /**
     * <br>[機  能] 登録処理後の画面遷移処理
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
    private ActionForward __doMoveAdd(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
        pathUtil.deleteTempPath(
                getRequestModel(req), GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

        ActionForward forward = map.findForward("fil040");
        if (form.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL010) {
            if (form.getBackDsp().equals(GSConstFile.MOVE_TO_MAIN)) {
                forward = map.findForward("main");
            } else if (form.getBackDsp().equals(GSConstFile.MOVE_TO_FIL040)) {
                forward = map.findForward("fil040");
            } else {
                forward = map.findForward("cabinetMain");
            }
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(forward.getPath());
        cmn999Form.setMessage(msgRes.getMessage(
                "touroku.kanryo.object", gsMsg.getMessage("fil.185")));

        cmn999Form.addHiddenParam("fil300BeforeDspFlg", form.getFil300BeforeDspFlg());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除処理後の画面遷移処理
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
    private ActionForward __doMoveDelete(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
        pathUtil.deleteTempPath(
                getRequestModel(req), GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

        ActionForward forward = map.findForward("fil040");
        if (form.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL010) {
            if (form.getBackDsp().equals(GSConstFile.MOVE_TO_MAIN)) {
                forward = map.findForward("main");
            } else if (form.getBackDsp().equals(GSConstFile.MOVE_TO_FIL040)) {
                forward = map.findForward("fil040");
            } else {
                forward = map.findForward("cabinetMain");
            }
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(forward.getPath());
        cmn999Form.setMessage(msgRes.getMessage(
                "sakujo.kanryo.object", gsMsg.getMessage("cmn.file")));

        cmn999Form.addHiddenParam("fil300BeforeDspFlg", form.getFil300BeforeDspFlg());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] サンプルダウンロード処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param savePathFlg true:保存先が存在する, false:保存先が存在しない
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doDownloadSample(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, boolean savePathFlg)
            throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(req);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getTempPath(
                reqMdl, GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID, TEMP_DIRECTORY_ID_TEMP);


        String fileName = SAMPLE_FILE_NAME;
        String outFileName = SAMPLE_OUTFILE_NAME;

        Fil300ParamModel paramMdl = new Fil300ParamModel();
        paramMdl.setParam(form);
        Fil300Biz thisBiz = new Fil300Biz(reqMdl, con);
        boolean result = thisBiz.createSampleXls(paramMdl, getAppRootPath(), tempDir, savePathFlg);

        //出力対象のデータが存在しない場合、エラーメッセージを表示する
        if (!result) {
            form.setFil300SelectDir(-1);
            form.setFil300SelectCabinet(null);
            ActionErrors errors = new ActionErrors();
            StrutsUtil.addMessage(errors,
                    new ActionMessage(
                            "error.edit.power.notfound",
                            gsMsg.getMessage("cmn.folder"),
                            gsMsg.getMessage("cmn.show")
                            ),
                    "error.edit.power.notfound");
            addErrors(req, errors);

            return __doInit(map, form, req, res, con);
        }

        try {
            String outFilePath = IOTools.setEndPathChar(tempDir) + fileName;
            TempFileUtil.downloadAtachment(req, res, outFilePath, outFileName, Encoding.UTF_8);

            //TEMPディレクトリ削除
            tempPathUtil.deleteTempPath(
                    reqMdl, GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID, TEMP_DIRECTORY_ID_TEMP);

            //ログ出力
            String textDownload = gsMsg.getMessage("cmn.download");
            filBiz.outPutLog(
                    textDownload, GSConstLog.LEVEL_INFO, SAMPLE_FILE_NAME, map.getType());
        } catch (Exception e) {
            log__.error("エクセル勤務表出力の出力に失敗", e);
        }

        return null;
    }

    /**
     * <br>[機  能] インポートボタン押下時の表示処理
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
    private ActionForward __doImportCheck(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getTempPath(
                reqMdl, GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

        //入力チェック
        JSONObject jsonObject = JSONObject.fromObject(form);
        ActionErrors errors = form.validateImport(map, reqMdl, tempDir, con);
        int errorSize = errors.size();
        if (!errors.isEmpty()) {
            MessageResources msgRes = getResources(req);
            int errorNum = 0;
            @SuppressWarnings("unchecked")
            Iterator<ActionMessage> iterator = errors.get();
            while (iterator.hasNext()) {
                ActionMessage error = ((ActionMessage) iterator.next());
                String str = msgRes.getMessage(error.getKey(), error.getValues());
                jsonObject.element("error_" + errorNum, str);
                errorNum++;
            }
            jsonObject.element("fileErrorSize", errorSize);
        }

        int invalidCount = 0;
        List<FileErrlAdddataDspModel> dspList = new ArrayList<FileErrlAdddataDspModel>();
        if (errors.isEmpty()) {
            Fil300ParamModel paramMdl = new Fil300ParamModel();
            paramMdl.setParam(form);

            //インポートファイルの設定
            List<String> beforeInsertFileList = null;
            if (form.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
                Fil300Biz biz = new Fil300Biz(reqMdl, con);
                beforeInsertFileList = biz.getErrlFile080(paramMdl);
            }

            Set<Integer> set = new HashSet<Integer>();
            Fil300ReadCsv imp = new Fil300ReadCsv(
                    con, reqMdl, getResources(req), paramMdl.getFil300SelectDir(), set,
                    beforeInsertFileList);

            imp._importCsv(tempDir);
            dspList = imp.getDspList();
            int page = paramMdl.getFil300ImpPage();
            int count = dspList.size();
            if (count > GSConstFile.ERRL_IMPORT_MAXCOUNT) {
                errors = new ActionErrors();
                ActionMessage msg =
                        new ActionMessage(
                                "error.import.maxcount.over");
                StrutsUtil.addMessage(errors, msg, "error.import.maxcount.over");
                MessageResources msgRes = getResources(req);
                    String str = msgRes.getMessage(msg.getKey(), msg.getValues());
                jsonObject.element("error_" + 0, str);
                jsonObject.element("fileErrorSize", 1);
                dspList = new ArrayList<FileErrlAdddataDspModel>();
            } else {
                double maxPage = Math.ceil(count * 1.0 / GSConstFile.MAX_DSP_ERRL_ADDDATA);
                jsonObject.element("maxPage", (int) maxPage);

                //表示件数を15件までに変更
                int maxCount = page * GSConstFile.MAX_DSP_ERRL_ADDDATA;
                if (count - 1 < page * GSConstFile.MAX_DSP_ERRL_ADDDATA) {
                    maxCount = (int) count;
                }
                dspList = dspList.subList((page - 1) * GSConstFile.MAX_DSP_ERRL_ADDDATA, maxCount);

                invalidCount = imp.getInvalidCount();
            }
        }

        PrintWriter out = null;
        try {
            for (int idx = 0; idx < dspList.size(); idx++) {
                FileErrlAdddataDspModel dspMdl = dspList.get(idx);
                jsonObject.element("sid_" + idx, dspMdl.getDspId());
                jsonObject.element("fileName_" + idx,
                        StringUtilHtml.transToHTmlPlusAmparsant(dspMdl.getFileName()));
                jsonObject.element("tradeDate_" + idx, dspMdl.getTradeDate());
                jsonObject.element("tradeTarget_" + idx,
                        StringUtilHtml.transToHTmlPlusAmparsant(dspMdl.getTradeTarget()));
                jsonObject.element("tradeMoney_" + idx,
                        StringUtilHtml.transToHTmlPlusAmparsant(dspMdl.getTradeMoey()));
                jsonObject.element("errorFlg_" + idx, dspMdl.getErrorFlg());
                jsonObject.element("errorText_" + idx, dspMdl.getErrorText());
                jsonObject.element("parentExistFlg_" + idx, dspMdl.isParentExistFlg());
            }
            jsonObject.element("listSize", dspList.size());
            jsonObject.element("invalidCount", invalidCount);
            if (invalidCount > 0) {
                jsonObject.element("invalidMessage",
                        gsMsg.getMessage("fil.178", new String[] {String.valueOf(invalidCount)}));
            }
            jsonObject.element("success", true);

            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonObject.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("ファイル管理 取引情報登録 一括登録に失敗", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }

        return null;
    }

    /**
     * <br>[機  能] 確定ボタン押下時の登録処理
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
    private ActionForward __doImport(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        //指定されたディレクトリを含むキャビネットに対し、権限チェックを行う

        int fcbSid = 0;
        int selectDirSid = form.getFil300SelectDir();
        if (selectDirSid > 0) {
            fcbSid = filBiz.getCabinetSid(selectDirSid);
        }
        if (fcbSid == 0) {
            fcbSid = Integer.parseInt(form.getFil300SelectCabinet());
        }

        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getTempPath(
                reqMdl, GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

        //取り込みファイルチェック
        ActionErrors errors = form.validateImport(map, reqMdl, tempDir, con);
        if (errors.isEmpty()) {
            List<FileErrlAdddataDspModel> dspList = new ArrayList<FileErrlAdddataDspModel>();
            Fil300ParamModel paramMdl = new Fil300ParamModel();
            paramMdl.setParam(form);

            //インポートファイルの設定
            List<String> beforeInsertFileList = null;
            if (form.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
                Fil300Biz biz = new Fil300Biz(reqMdl, con);
                beforeInsertFileList = biz.getErrlFile080(paramMdl);
            }

            Set<Integer> set = new HashSet<Integer>();
            Fil300ReadCsv imp = new Fil300ReadCsv(
                    con, reqMdl, getResources(req), paramMdl.getFil300SelectDir(), set,
                    beforeInsertFileList);

            imp._importCsv(tempDir);
            dspList = imp.getDspList();

            //取り込みデータ件数チェック
            int count = dspList.size();
            if (count > GSConstFile.ERRL_IMPORT_MAXCOUNT) {
                errors = new ActionErrors();
                ActionMessage msg =
                        new ActionMessage(
                                "error.import.maxcount.over");
                StrutsUtil.addMessage(errors, msg, "error.import.maxcount.over");
            }

        }

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setFil300SelectCabinet("-1");
            form.setFil300SelectDir(-1);

            return __doInit(map, form, req, res, con);
        }

        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }

        Fil300Biz biz = new Fil300Biz(reqMdl, con);
        Fil300ParamModel paramMdl = new Fil300ParamModel();
        paramMdl.setParam(form);

        MessageResources msgRes = getResources(req);

        con.setAutoCommit(false);
        int count = 0;
        MlCountMtController cntCon = getCountMtController(req);

        // アプリケーションルートパス
        String appRootPath = getAppRootPath();
        // プラグイン設定
        CommonBiz cmnBiz = new CommonBiz();
        PluginConfig plconf = getPluginConfig(req);
        PluginConfig pconfig = getPluginConfigForMain(plconf, con);
        boolean smailPluginUseFlg = cmnBiz
                .isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);

        count = biz.insertImport(paramMdl, msgRes, tempDir, cntCon,
                appRootPath, plconf, smailPluginUseFlg);
        con.commit();

        con.setAutoCommit(true);

        //ログ出力
        String textEntry = gsMsg.getMessage("cmn.entry");
        StringBuilder sb = new StringBuilder();
        sb.append(gsMsg.getMessage("fil.fil010.8"));
        sb.append(":");
        sb.append(count);
        sb.append(gsMsg.getMessage("cmn.number"));
        filBiz.outPutLog(
                textEntry, GSConstLog.LEVEL_INFO, sb.toString(), map.getType());

        //仮登録ファイル残数チェック
        ActionForward urlOkForward = map.findForward("fil300");
        if (biz.getErrlFileCount(paramMdl) == 0) {
            if (form.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL010) {
                if (form.getBackDsp().equals(GSConstFile.MOVE_TO_MAIN)) {
                    urlOkForward = map.findForward("main");
                } else if (form.getBackDsp().equals(GSConstFile.MOVE_TO_FIL040)) {
                    urlOkForward = map.findForward("fil040");
                } else {
                    urlOkForward = map.findForward("cabinetMain");
                }
            } else {
                urlOkForward = map.findForward("fil040");
            }
        }

        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
        pathUtil.deleteTempPath(reqMdl, GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);

        //スケジュール登録完了画面パラメータの設定
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(urlOkForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage(
                "touroku.kanryo.object", gsMsg.getMessage("fil.185")));

        cmn999Form.addHiddenParam("fil300BeforeDspFlg", form.getFil300BeforeDspFlg());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] キャビネットまたはフォルダ押下時の表示処理
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
    private ActionForward __doSyosaiDir(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        Fil300Biz biz = new Fil300Biz(reqMdl, con);
        Fil300ParamModel paramMdl = new Fil300ParamModel();
        paramMdl.setParam(form);
        long maxCount = biz.setAddFileList(paramMdl, true);

        //表示対象のファイルが1件も存在しない場合、不正なフォルダが選択されたと判定
        if (maxCount <= 0) {
            log__.info("ファイル詳細表示時に権限チェックでリターン");
            form.setFil300SelectDir(-1);
            form.setFil300SelectCabinet(null);

            ActionErrors errors = new ActionErrors();
            StrutsUtil.addMessage(errors,
                    new ActionMessage(
                            "error.edit.power.notfound",
                            gsMsg.getMessage("cmn.folder"),
                            gsMsg.getMessage("cmn.show")
                            ),
                    "error.edit.power.notfound");
            addErrors(req, errors);

            return __doInit(map, form, req, res, con);
        }

        PrintWriter out = null;
        try {
            JSONObject jsonObject = null;
            List<FileErrlAdddataModel> dspList = paramMdl.getFil300AddFileDspList();


            jsonObject = JSONObject.fromObject(form);
            for (int idx = 0; idx < dspList.size(); idx++) {
                FileErrlAdddataModel dspMdl = dspList.get(idx);
                jsonObject.element("sid_" + idx, dspMdl.getFeaSid());
                jsonObject.element("fileName_" + idx, dspMdl.getFdrName());
                jsonObject.element("fileExtension_" + idx, dspMdl.getFflExt().substring(1));
                jsonObject.element("binSid_" + idx, dspMdl.getBinSid());
            }
            jsonObject.element("listSize", dspList.size());
            jsonObject.element("maxPage",
                    Math.ceil(maxCount * 1.0 / GSConstFile.MAX_DSP_ERRL_ADDDATA));
            jsonObject.element("maxCount", maxCount);

            jsonObject.element("success", true);

            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonObject.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("取引情報データの取得に失敗");
        } finally {
            if (out != null) {
                out.close();
            }
        }

        return null;
    }

    /**
     * <br>[機  能] 保存先が存在しないファイルを押下したときの処理
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
    private ActionForward __doSyosaiNoSavePath(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //保存先が存在しないファイルは管理者のみ操作可能
        if (!__isAdminUser(con, req)) {
            log__.info("ファイル詳細表示時に権限チェックでリターン");
            form.setFil300SelectDir(-1);
            form.setFil300SelectCabinet(null);

            ActionErrors errors = new ActionErrors();
            StrutsUtil.addMessage(errors,
                    new ActionMessage(
                            "error.edit.power.notfound",
                            gsMsg.getMessage("cmn.folder"),
                            gsMsg.getMessage("cmn.show")
                            ),
                    "error.edit.power.notfound");
            addErrors(req, errors);

            return __doInit(map, form, req, res, con);
        }

        Fil300Biz biz = new Fil300Biz(reqMdl, con);
        Fil300ParamModel paramMdl = new Fil300ParamModel();
        paramMdl.setParam(form);
        long maxCount = biz.setAddFileList(paramMdl, false);

        PrintWriter out = null;
        try {
            JSONObject jsonObject = null;
            List<FileErrlAdddataModel> dspList = paramMdl.getFil300AddFileDspList();

            jsonObject = JSONObject.fromObject(form);
            for (int idx = 0; idx < dspList.size(); idx++) {
                FileErrlAdddataModel dspMdl = dspList.get(idx);
                jsonObject.element("sid_" + idx, dspMdl.getFeaSid());
                jsonObject.element("fileName_" + idx, dspMdl.getFdrName());
                jsonObject.element("fileExtension_" + idx, dspMdl.getFflExt().substring(1));
                jsonObject.element("binSid_" + idx, dspMdl.getBinSid());
            }
            jsonObject.element("listSize", dspList.size());
            jsonObject.element("maxPage",
                    Math.ceil(maxCount * 1.0 / GSConstFile.MAX_DSP_ERRL_ADDDATA));
            jsonObject.element("maxCount", maxCount);
            jsonObject.element("success", true);

            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonObject.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("取引情報データの取得に失敗");
        } finally {
            if (out != null) {
                out.close();
            }
        }

        return null;
    }

    /**
     * <br>[機  能] 登録ボタン押下時の処理
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
    private ActionForward __doAddFile(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        MessageResources msgRes = getResources(req);

        Fil300ParamModel paramMdl = new Fil300ParamModel();
        paramMdl.setParam(form);

        Fil300Biz biz = new Fil300Biz(reqMdl, con);

        //仮登録ファイルの権限チェック
        int feaSid = form.getFil300SelectFile();
        if (!biz.isErrlAuth(feaSid, paramMdl)) {
            log__.info("ファイル登録時に権限チェックでリターン");

            JSONObject jsonObject = JSONObject.fromObject(form);
            ActionMessage message =
                    new ActionMessage(
                            "error.edit.power.notfound",
                            gsMsg.getMessage("fil.187"),
                            gsMsg.getMessage("cmn.entry")
                            );
            jsonObject.element("error_0",
                    msgRes.getMessage(message.getKey(), message.getValues()));

            jsonObject.element("errorSize", 1);
            jsonObject.element("feaAuthError", true);

            __writeResponse(res, jsonObject);
            return null;
        }


        //入力チェック
        ActionErrors errors = form.validateFileInsert(con, reqMdl);

        if (!errors.isEmpty()) {
            ActionMessages messages = new ActionMessages(errors);
            @SuppressWarnings("unchecked")
            Iterator<ActionMessage> iterator = messages.get();
            int count = 0;
            int errorSize = errors.size();
            JSONObject jsonObject = JSONObject.fromObject(form);
            while (iterator.hasNext()) {
                ActionMessage message = iterator.next();
                jsonObject.element("error_" + count,
                        msgRes.getMessage(message.getKey(), message.getValues()));
                count++;
            }
            jsonObject.element("errorSize", errorSize);
            __writeResponse(res, jsonObject);
            return null;
        }

        //保存先チェック
        errors = form.validateSavePath(con, reqMdl);
        if (!errors.isEmpty()) {
            ActionMessages messages = new ActionMessages(errors);
            @SuppressWarnings("unchecked")
            Iterator<ActionMessage> iterator = messages.get();
            int count = 0;
            int errorSize = errors.size();
            JSONObject jsonObject = JSONObject.fromObject(form);
            while (iterator.hasNext()) {
                ActionMessage message = iterator.next();
                jsonObject.element("error_" + count,
                        msgRes.getMessage(message.getKey(), message.getValues()));
                count++;
            }
            jsonObject.element("errorSize", errorSize);
            jsonObject.element("noSavePath", true);

            biz.setInitData(paramMdl);
            paramMdl.setFormData(form);
            __setResponseTree(jsonObject, form);
            __writeResponse(res, jsonObject);

            return null;
        }

        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);
        String resultFile = null;
        List<Integer> delParentDir = new ArrayList<Integer>();
        //登録前に親フォルダを取得
        delParentDir = biz.beforeFile(paramMdl);

        // アプリケーションルートパス
        String appRootPath = getAppRootPath();
        // プラグイン設定
        CommonBiz cmnBiz = new CommonBiz();
        PluginConfig plconf = getPluginConfig(req);
        PluginConfig pconfig = getPluginConfigForMain(plconf, con);
        boolean smailPluginUseFlg = cmnBiz
                .isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);

        //データの登録
        resultFile = biz.insertFile(paramMdl, getCountMtController(req),
                appRootPath, plconf, smailPluginUseFlg);
        con.commit();

        con.setAutoCommit(true);

        //ログ出力
        if (StringUtil.isNullZeroString(resultFile)) {
            String textEntry = gsMsg.getMessage("cmn.entry");
            StringBuilder sb = new StringBuilder();
            sb.append("fil.fil010.8");
            sb.append(":");
            sb.append(resultFile);
            filBiz.outPutLog(
                    textEntry, GSConstLog.LEVEL_INFO, sb.toString(), map.getType());
        }

        //仮登録ファイル残数チェック
        JSONObject jsonObject = JSONObject.fromObject(form);
        String forward = "fil300";
        long errlFileCount = biz.getErrlFileCount(paramMdl);
        if (errlFileCount > 0) {
            biz.setInitData(paramMdl);
            paramMdl.setFormData(form);
            if (delParentDir != null && !delParentDir.isEmpty()) {
                List<Integer> delFolder = __checkExistFolder(delParentDir, form);
                for (int i = 0; i < delFolder.size(); i++) {
                    jsonObject.element("delFolder_" + i, delFolder.get(i));
                }
                jsonObject.element("delFolderSize", delFolder.size());
            }
            __setResponseTree(jsonObject, form);
            if (!StringUtil.isNullZeroString(resultFile)) {
                jsonObject.element("message", msgRes.getMessage(
                        "touroku.kanryo.object", gsMsg.getMessage("fil.185")));
            } else {
                jsonObject.element("message", msgRes.getMessage(
                        "error.input.timecard.exist", gsMsg.getMessage("fil.185")));
            }
        }
        if (errlFileCount == 0) {
            if (paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL010) {
                if (paramMdl.getBackDsp().equals(GSConstFile.MOVE_TO_MAIN)) {
                    forward = "main";
                } else if (paramMdl.getBackDsp().equals(GSConstFile.MOVE_TO_FIL040)) {
                    forward = "fil040";
                } else {
                    forward = "cabinetMain";
                }
            } else {
                forward = "fil040";
            }
        }

        //レスポンスの作成
        jsonObject.element("success", true);
        jsonObject.element("forward", forward);
        saveToken(req);
        String saved = (String) req.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY);
        jsonObject.element("token", saved);

        __writeResponse(res, jsonObject);

        return null;
    }

    /**
     * <br>[機  能] ファイルダウンロードの処理
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
    private ActionForward __doDownLoadImage(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        //仮登録ファイルの存在チェック
        int feaSid = form.getFil300SelectFile();
        FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con);
        FileErrlAdddataModel feaMdl = feaDao.select(feaSid);
        if (feaMdl == null) {
            return __doInit(map, form, req, res, con);
        }

        //仮登録ファイルの権限チェック
        RequestModel reqMdl = getRequestModel(req);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        if (!filBiz.checkEditErrlData(feaSid)) {
            return __doInit(map, form, req, res, con);
        }

        //バイナリ情報取得
        CommonBiz cmnBiz = new CommonBiz();
        long binSid = feaMdl.getBinSid();
        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                GroupSession.getResourceManager().getDomain(req));
        if (cbMdl == null) {
            return __doInit(map, form, req, res, con);
        }

        //ログ出力処理
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDownload = gsMsg.getMessage("cmn.download");
        filBiz.outPutLog(
                textDownload, GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(), map.getType(),
                String.valueOf(binSid));

        //集計用データを登録する
        filBiz.regFileDownloadLogCnt(getSessionUserSid(req), binSid, new UDate());

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);

        //ファイルをダウンロードする
        cbMdl.setBinFilekbn(GSConst.FILEKBN_FILE);
        TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);

        cbMdl.removeTempFile();
        return null;
    }

    /**
     * <br>[機  能] 削除ボタン押下時の処理
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
    private ActionForward __doDeleteFile(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        //仮登録ファイルの権限チェック
        int feaSid = form.getFil300SelectFile();
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        Fil300Biz biz = new Fil300Biz(reqMdl, con);
        Fil300ParamModel paramMdl = new Fil300ParamModel();
        paramMdl.setParam(form);

        if (!biz.isErrlAuth(feaSid, paramMdl)) {
            log__.info("ファイル削除時に権限チェックでリターン");
            form.setFil300SelectFile(-1);

            ActionErrors errors = new ActionErrors();
            StrutsUtil.addMessage(errors,
                    new ActionMessage(
                            "error.edit.power.notfound",
                            gsMsg.getMessage("fil.187"),
                            gsMsg.getMessage("cmn.delete")
                            ),
                    "error.edit.power.notfound");
            addErrors(req, errors);

            return __doInit(map, form, req, res, con);
        }

        if (!isTokenValid(req, true)) {
            return getSubmitErrorPage(map, req);
        }

        //データの削除
        con.setAutoCommit(false);
        String resultFile = null;
        List<Integer> delParentDir = new ArrayList<Integer>();
            //削除前のディレクトリの取得
            delParentDir = biz.beforeFile(paramMdl);

            //データの削除
            resultFile = biz.deleteFile(paramMdl);
            con.commit();

        con.setAutoCommit(true);

        //ログ出力
        boolean deleteFlg = !StringUtil.isNullZeroString(resultFile);
        if (deleteFlg) {
            String textEntry = gsMsg.getMessage("cmn.delete");
            StringBuilder sb = new StringBuilder();
            sb.append("fil.41");
            sb.append(":");
            sb.append(resultFile);

            FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
            filBiz.outPutLog(
                    textEntry, GSConstLog.LEVEL_INFO, sb.toString(), map.getType());
        }

        //仮登録ファイル残数チェック
        JSONObject jsonObject = JSONObject.fromObject(form);
        long errlFileCount = biz.getErrlFileCount(paramMdl);
        String forward = "fil300";
        if (errlFileCount > 0) {
            biz.setInitData(paramMdl);
            paramMdl.setFormData(form);
            if (delParentDir != null && !delParentDir.isEmpty()) {
                List<Integer> delFolder = __checkExistFolder(delParentDir, form);
                for (int i = 0; i < delFolder.size(); i++) {
                    jsonObject.element("delFolder_" + i, delFolder.get(i));
                }
                jsonObject.element("delFolderSize", delFolder.size());
            }
            __setResponseTree(jsonObject, form);
            MessageResources msgRes = getResources(req);
            if (deleteFlg) {
                jsonObject.element("message", msgRes.getMessage(
                        "sakujo.kanryo.object", gsMsg.getMessage("cmn.file")));
            } else {
                jsonObject.element("message", msgRes.getMessage("error.not.exist.file"));
            }
        }
        if (errlFileCount == 0) {
            if (paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL010) {
                if (paramMdl.getBackDsp().equals(GSConstFile.MOVE_TO_MAIN)) {
                    forward = "main";
                } else if (paramMdl.getBackDsp().equals(GSConstFile.MOVE_TO_FIL040)) {
                    forward = "fil040";
                } else {
                    forward = "cabinetMain";
                }
            } else {
                forward = "fil040";
            }
        }

        //レスポンスの作成
        jsonObject.element("success", true);
        jsonObject.element("forward", forward);
        saveToken(req);
        String saved = (String) req.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY);
        jsonObject.element("token", saved);

        __writeResponse(res, jsonObject);

        return null;
    }

    /**
     * <br>[機  能] ファイル情報の取得
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
    private ActionForward __doGetFileInfo(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //仮登録ファイルの権限チェック
        int feaSid = form.getFil300SelectFile();
        if (!filBiz.checkEditErrlData(feaSid)) {
            log__.info("ファイル詳細表示時に権限チェックでリターン");
            form.setFil300SelectDir(-1);
            form.setFil300SelectCabinet(null);
            form.setFil300SelectFile(-1);

            ActionErrors errors = new ActionErrors();
            StrutsUtil.addMessage(errors,
                    new ActionMessage(
                            "error.edit.power.notfound",
                            gsMsg.getMessage("cmn.folder"),
                            gsMsg.getMessage("cmn.show")
                            ),
                    "error.edit.power.notfound");
            addErrors(req, errors);

            return __doInit(map, form, req, res, con);
        }

        //画面上で指定されたキャビネットが仮登録ファイルと関連するかを確認
        int fcbSid = NullDefault.getInt(form.getFil300SelectCabinet(), -1);
        FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con);
        FileErrlAdddataModel feaMdl = feaDao.select(feaSid);
        if (feaMdl.getFcbSid() != fcbSid) {
            log__.info("選択されたキャビネットが仮登録ファイル保存先と一致しない");
            form.setFil300SelectDir(-1);
            form.setFil300SelectCabinet(null);
            form.setFil300SelectFile(-1);

            ActionErrors errors = new ActionErrors();
            StrutsUtil.addMessage(errors,
                    new ActionMessage(
                            "error.edit.power.notfound",
                            gsMsg.getMessage("cmn.folder"),
                            gsMsg.getMessage("cmn.show")
                            ),
                    "error.edit.power.notfound");
            addErrors(req, errors);

            return __doInit(map, form, req, res, con);
        }

        Fil300ParamModel paramMdl = new Fil300ParamModel();
        paramMdl.setParam(form);

        PrintWriter out = null;
        try {
            JSONObject jsonObject = null;
            jsonObject = JSONObject.fromObject(form);
            String fileName = feaMdl.getFdrName();
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            jsonObject.element("fileName", fileName);
            jsonObject.element("fileExt", feaMdl.getFflExt());
            FileCabinetDao fcbDao = new FileCabinetDao(con);
            FileCabinetModel fcbMdl = fcbDao.select(fcbSid);
            FileDirectoryDao fdrDao = new FileDirectoryDao(con);

            FileDirectoryModel parentMdl = fdrDao.getNewDirectory(feaMdl.getFdrParentSid());
            if (fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_NOT_USE
                    && (parentMdl == null || parentMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE)) {
                jsonObject.element("cabinetName", fcbMdl.getFcbName());

                //システム管理者、またはファイル管理プラグイン管理者かを判定
                CommonBiz  commonBiz = new CommonBiz();
                BaseUserModel smodel = getSessionUserModel(req);
                boolean adminUser
                    = commonBiz.isPluginAdmin(con, smodel, GSConstFile.PLUGIN_ID_FILE);

                //[保存先]欄に表示する保存先ディレクトリ選択ツリーの情報を取得
                FilTreeBiz treeBiz = new FilTreeBiz(con);
                Map<Integer, String[]> treeMap =
                        treeBiz.getFileTreeMapForMove(
                                reqMdl,
                                fcbSid,
                                List.of(),
                                adminUser);
                for (int level = 0; level <= GSConstFile.DIRECTORY_LEVEL_10; level++) {
                    String[] tree = treeMap.get(level);
                    if (tree == null || tree.length == 0) {
                        break;
                    }
                    jsonObject.element("treeSavePathLv" + level,
                        tree);
                }

            }
            if (fcbMdl != null
                    && fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
                jsonObject.element("filePathCabinet",
                        StringUtilHtml.transToHTmlPlusAmparsant(fcbMdl.getFcbName()));

                __setFilePathFolder(jsonObject, fcbMdl.getFcbSortFolder1(), 1);
                __setFilePathFolder(jsonObject, fcbMdl.getFcbSortFolder2(), 2);
                __setFilePathFolder(jsonObject, fcbMdl.getFcbSortFolder3(), 3);

                jsonObject.element("defGrpName", feaMdl.getFeaDefgrpName());
            }
            jsonObject.element("success", true);

            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonObject.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("取引情報データの取得に失敗");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }

        return null;
    }

    /**
     * 指定した振り分け先フォルダに応じた設定値をJSONObjectに設定する
     * @param jsonObject JSONObject
     * @param sortFolder 振り分け先フォルダ
     * @param level 階層
     */
    private void __setFilePathFolder(JSONObject jsonObject, int sortFolder, int level) {
        String value = null;
        switch (sortFolder) {
            case GSConstFile.SORT_FOLDER_UPLOAD_DATE:
                value = "uploadDate";
                break;
            case GSConstFile.SORT_FOLDER_TRADE_DATE:
                value = "tradeDate";
                break;
            case GSConstFile.SORT_FOLDER_TRADE_TARGET:
                value = "tradeTarget";
                break;
            case GSConstFile.SORT_FOLDER_TRADE_DEFGROUP:
                value = "tradeDefgroup";
                break;
            default:
                break;
        }

        if (value != null) {
            jsonObject.element("filePathFolder" + level, value);
        }
    }

    /**
     * <br>[機  能] jsonレスポンスの書き込み
     * <br>[解  説]
     * <br>[備  考]
     * @param res レスポンス
     * @param jsonObject Ajaxレスポンス対象
     * @throws Exception 実行例外
     */
    private void __writeResponse(HttpServletResponse res, JSONObject jsonObject) throws Exception {

        if (jsonObject == null) {
            return;
        }

        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonObject.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("取引情報データの取得に失敗");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] 登録，削除したファイルの親フォルダの内、一覧から削除するフォルダを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param folderSid フォルダSID一覧
     * @param form パラメータ情報
     * @return 一覧から削除するフォルダ
     * @throws Exception 実行例外
     */
    private List<Integer> __checkExistFolder(
            List<Integer> folderSid, Fil300Form form) throws Exception {

        List<Integer> ret = new ArrayList<Integer>();
        Class<?> treeParam = FilTreeForm.class;

        Set<Integer> checked = new HashSet<Integer>();
        String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
        for (int level = 0; level <= GSConstFile.DIRECTORY_LEVEL_10; level++) {
            Method treeGetter = treeParam.getMethod("getTreeFormLv" + level);
            String[] value = (String[]) treeGetter.invoke(form);
            if (value == null || value.length < 1) {
                break;
            }
            for (String text : value) {
                text = text.substring(0, text.indexOf(sep));
                checked.add(NullDefault.getInt(text, -1));
            }
        }
        //仮登録ファイルが削除されたことで、無くなったフォルダを取得
        for (int fdrSid : folderSid) {
            if (!checked.contains(fdrSid)) {
                ret.add(fdrSid);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] jsonレスポンスの書き込み
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonObject Ajaxレスポンス対象
     * @param form パラメータ情報
     * @throws Exception 実行例外
     */
    private void __setResponseTree(
            JSONObject jsonObject, Fil300Form form) {

        if (jsonObject == null) {
            return;
        }

        String[] tree = form.getTreeFormLv0();
        if (tree == null || tree.length == 0) {
            return;
        }
        for (int idx = 0; idx < tree.length; idx++) {
            jsonObject.element("treeFormLv0" + "_" + idx, tree[idx]);
        }
        jsonObject.element("treeFormLv0Size", tree.length);

        tree = form.getTreeFormLv1();
        if (tree == null || tree.length == 0) {
            return;
        }
        for (int idx = 0; idx < tree.length; idx++) {
            jsonObject.element("treeFormLv1" + "_" + idx, tree[idx]);
        }
        jsonObject.element("treeFormLv1Size", tree.length);

        tree = form.getTreeFormLv2();
        if (tree == null || tree.length == 0) {
            return;
        }
        for (int idx = 0; idx < tree.length; idx++) {
            jsonObject.element("treeFormLv2" + "_" + idx, tree[idx]);
        }
        jsonObject.element("treeFormLv2Size", tree.length);

        tree = form.getTreeFormLv3();
        if (tree == null || tree.length == 0) {
            return;
        }
        for (int idx = 0; idx < tree.length; idx++) {
            jsonObject.element("treeFormLv3" + "_" + idx, tree[idx]);
        }
        jsonObject.element("treeFormLv3Size", tree.length);

        tree = form.getTreeFormLv4();
        if (tree == null || tree.length == 0) {
            return;
        }
        for (int idx = 0; idx < tree.length; idx++) {
            jsonObject.element("treeFormLv4" + "_" + idx, tree[idx]);
        }
        jsonObject.element("treeFormLv4Size", tree.length);

        tree = form.getTreeFormLv5();
        if (tree == null || tree.length == 0) {
            return;
        }
        for (int idx = 0; idx < tree.length; idx++) {
            jsonObject.element("treeFormLv5" + "_" + idx, tree[idx]);
        }
        jsonObject.element("treeFormLv5Size", tree.length);

        tree = form.getTreeFormLv6();
        if (tree == null || tree.length == 0) {
            return;
        }
        for (int idx = 0; idx < tree.length; idx++) {
            jsonObject.element("treeFormLv6" + "_" + idx, tree[idx]);
        }
        jsonObject.element("treeFormLv6Size", tree.length);

        tree = form.getTreeFormLv7();
        if (tree == null || tree.length == 0) {
            return;
        }
        for (int idx = 0; idx < tree.length; idx++) {
            jsonObject.element("treeFormLv7" + "_" + idx, tree[idx]);
        }
        jsonObject.element("treeFormLv7Size", tree.length);

        tree = form.getTreeFormLv8();
        if (tree == null || tree.length == 0) {
            return;
        }
        for (int idx = 0; idx < tree.length; idx++) {
            jsonObject.element("treeFormLv8" + "_" + idx, tree[idx]);
        }
        jsonObject.element("treeFormLv8Size", tree.length);

        tree = form.getTreeFormLv9();
        if (tree == null || tree.length == 0) {
            return;
        }
        for (int idx = 0; idx < tree.length; idx++) {
            jsonObject.element("treeFormLv9" + "_" + idx, tree[idx]);
        }
        jsonObject.element("treeFormLv9Size", tree.length);

        tree = form.getTreeFormLv10();
        if (tree == null || tree.length == 0) {
            return;
        }
        for (int idx = 0; idx < tree.length; idx++) {
            jsonObject.element("treeFormLv10" + "_" + idx, tree[idx]);
        }
        jsonObject.element("treeFormLv10Size", tree.length);
    }

    /**
     * <br>[機  能] フォルダパスの取得
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
    private ActionForward __doGetFolderPath(ActionMapping map, Fil300Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        int fcbSid = filBiz.getCabinetSid(form.getFil300SelectDir());
        if (!filBiz.isEditCabinetUser(fcbSid, form.getFil300SelectDir())) {
            PrintWriter out = null;
            try {
                JSONObject jsonObject = JSONObject.fromObject(form);
                jsonObject.element("filePath", "");

                MessageResources msgRes = getResources(req);


                jsonObject.element("error",
                        msgRes.getMessage("error.edit.power.notfound",
                        gsMsg.getMessage("cmn.folder"),
                        gsMsg.getMessage("cmn.select")
                        ));

                res.setHeader("Cache-Control", "no-cache");
                res.setContentType("application/json;charset=UTF-8");
                out = res.getWriter();
                out.print(jsonObject.toString());
                out.flush();
            } catch (Exception e) {
                log__.error("取引情報データの取得に失敗");
                throw e;
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return null;
        }

        Fil300ParamModel paramMdl = new Fil300ParamModel();
        paramMdl.setParam(form);

        PrintWriter out = null;
        try {
            String pathStr = filBiz.getDirctoryPath(paramMdl.getFil300SelectDir());
            JSONObject jsonObject = JSONObject.fromObject(form);
            jsonObject.element("filePath", pathStr);
            jsonObject.element("success", true);

            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonObject.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("取引情報データの取得に失敗");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }

        return null;
    }

    /**
     * <br>[機  能] ユーザがシステム管理者、またはファイル管理プラグイン管理者かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return true:管理者ユーザ、false:一般ユーザ
     * @throws SQLExcetion　SQL実行時例外
     */
    private boolean __isAdminUser(Connection con, HttpServletRequest req)
    throws SQLException {
        CommonBiz  commonBiz = new CommonBiz();
        BaseUserModel umodel = getSessionUserModel(req);
        return commonBiz.isPluginAdmin(con, umodel, GSConstFile.PLUGIN_ID_FILE);
    }
}
