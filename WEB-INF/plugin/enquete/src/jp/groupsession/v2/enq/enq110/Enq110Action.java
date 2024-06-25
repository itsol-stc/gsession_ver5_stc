package jp.groupsession.v2.enq.enq110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.enq.AbstractEnqueteAction;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqDescBinDao;
import jp.groupsession.v2.enq.enq010.Enq010Form;
import jp.groupsession.v2.enq.enq210.Enq210Biz;
import jp.groupsession.v2.enq.enq210.Enq210Form;
import jp.groupsession.v2.enq.enq230.Enq230Form;
import jp.groupsession.v2.enq.model.EnqDescBinModel;
import jp.groupsession.v2.enq.model.EnqMainListModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アンケート 回答画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq110Action extends AbstractEnqueteAction {

    /** ダウンロード種別 添付ファイル */
    private static final int DLTYPE_FILE__ = 0;
    /** ダウンロード種別 画像 */
    private static final int DLTYPE_IMAGE__ = 1;
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "enq210";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq110Action.class);

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
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "").trim();
        //ダウンロードフラグ
        String dlFlg = NullDefault.getString(req.getParameter("enq110DownloadFlg"), "").trim();

        if (cmd.equals("conf") && dlFlg.equals("1")) {
            log__.debug("添付ファイルダウンロード");
            return true;
        } else if (cmd.equals("getImageFile") && dlFlg.equals("1")) {
            log__.debug("添付画像ダウンロード");
            return true;
        } else if (cmd.equals("getBodyFile")) {
            log__.debug("説明内画像ファイルダウンロード");
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        ActionForward forward = null;
        Enq110Form enq110Form = (Enq110Form) form;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        log__.debug("CMD = " + cmd);

        con.setAutoCommit(true);
        try {
            if (enq110Form.getEnq110DspMode() == Enq110Const.DSP_MODE_PREVIEW) {
                //プレビューの場合、アンケート作成者以外は閲覧不可
                EnqCommonBiz enqBiz = new EnqCommonBiz();
                if (!enqBiz.isEnqCrtUser(con, getRequestModel(req))) {
                    return getSubmitErrorPage(map, req);
                }
            }
        } finally {
            con.setAutoCommit(false);
        }

        if (cmd.equals("enq110back")) {
            // 戻る
            forward = __doBack(map, enq110Form);

        } else if (cmd.equals("enq110answer")) {
            // アンケート回答
            if (StringUtil.isNullZeroString(enq110Form.getEnq110queDate())) {
                return getAuthErrorPage(map, req);
            } else {
                con.setAutoCommit(true);
                try {
                    //アンケート回答中に設問情報が更新された場合、エラーとする。
                    Enq110ParamModel paramMdl = new Enq110ParamModel();
                    paramMdl.setParam(enq110Form);
                    Enq110Biz biz110 = new Enq110Biz();
                    int checkResult = biz110.checkEnqQueData(con, paramMdl);
                    if (checkResult != Enq110Const.ENQ_QUE_OK) {
                        return __setEnqDataError(map, enq110Form, req, checkResult);
                    }
                } finally {
                    con.setAutoCommit(false);
                }
            }

            forward = __doAnswer(map, enq110Form, req, res, con);

        } else if (cmd.equals("enq110knback")) {
            // 確認画面からの遷移
            forward = __doDsp(map, enq110Form, req, res, con);

        } else if (cmd.equals("enq110commit")) {
            // プレビューからアンケート登録
            forward = __doCommit(map, enq110Form, req, res, con);

        } else if (cmd.equals("conf")) {
            // 添付ファイルダウンロード
            String dlFlg = NullDefault.getString(req.getParameter("enq110DownloadFlg"), "").trim();
            if (dlFlg.equals("1")) {
                forward = __doDownloadTempFile(map, enq110Form, req, res, con);
            }

        } else if (cmd.equals("getImageFile")) {
            // 添付画像ダウンロード
            forward = __doGetImageFile(map, enq110Form, req, res, con);

        } else if (cmd.equals("getPreTempFile")) {
            // プレビュー時のダウンロード
            forward = __doGetTempDirFile(map, enq110Form, req, res, con);

        //説明内画像ファイル表示
        } else if (cmd.equals("getBodyFile")) {
            __doGetBodyFile(map, enq110Form, req, res, con);

        } else {
            // 初期表示処理

            con.setAutoCommit(true);
            try {
                // アンケートに回答可能かチェック
                if (!cmd.equals("enq210preview")) {
                    Enq110Biz biz = new Enq110Biz();
                    int kbn = biz.canAnsEnquete(
                            getRequestModel(req), con, enq110Form.getAnsEnqSid());

                    // 回答権限無し
                    if (kbn == Enq110Const.ANS_KBN_WITHOUT_AUTHORITY) {
                        return getSubmitErrorPage(map, req);

                        // 未回答 & 回答期限切れ
                    } else if (kbn == Enq110Const.ANS_KBN_UNANS_NOT_CURRENT) {
                        return __doAnsExpired(map, enq110Form, req, res, con);

                        // 回答済 ＆ 回答期限切れ
                    } else if (kbn == Enq110Const.ANS_KBN_ANSED_NOT_CURRENT) {
                        return map.findForward("enqAnswerKn");

                    } else {
                        enq110Form.setEnq110InitMode(kbn);
                    }
                }
            } finally {
                con.setAutoCommit(false);
            }

            forward = __doInit(map, enq110Form, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 例外
     */
    private ActionForward __doInit(ActionMapping map,
            Enq110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        log__.debug("初期表示処理");

        con.setAutoCommit(true);
        try {
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            Enq110Biz biz = new Enq110Biz(getRequestModel(req), con,
                    getAppRootPath(),
                    temp.getTempPath(getRequestModel(req),
                            GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID));
            Enq110ParamModel paramModel = new Enq110ParamModel();
            paramModel.setParam(form);
            biz.setInitData(paramModel);
            paramModel.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 例外
     */
    private ActionForward __doDsp(ActionMapping map,
            Enq110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        log__.debug("再描画処理");

        // 入力値待避
        List<EnqMainListModel> wkList = form.getEnq110QueListToList();

        con.setAutoCommit(true);
        try {
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            Enq110Biz biz = new Enq110Biz(getRequestModel(req), con,
                    getAppRootPath(),
                    temp.getTempPath(getRequestModel(req),
                            GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID));
            Enq110ParamModel paramModel = new Enq110ParamModel();
            paramModel.setParam(form);
            biz.setInitData(paramModel);
            biz.setInputData(paramModel, wkList);
            paramModel.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 回答内容登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 例外
     */
    private ActionForward __doAnswer(ActionMapping map,
            Enq110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        // 入力チェック(日付文字列)
        ActionErrors errors = form.validateEnq110Date(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }
        
        // 入力チェック
        errors = form.validateEnq110(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        // トランザクショントークン設定
        saveToken(req);

        return map.findForward("enq110answer");
    }

    /**
     * <br>[機  能] 登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 例外
     */
    private ActionForward __doCommit(ActionMapping map,
            Enq110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        log__.debug("登録処理");

        Enq110Biz biz = null;
        con.setAutoCommit(true);
        try {
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            biz = new Enq110Biz(getRequestModel(req), con,
                    getAppRootPath(),
                    temp.getTempPath(getRequestModel(req),
                            GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID));

            // 編集対象アンケートが存在するかを確認
            if (!biz.canEditEnquete(con, form.getEnqEditMode(), form.getEditEnqSid())) {
                return __setNoneDataError(map, form, req);
            }

            // 二重投稿
            if (!isTokenValid(req, true)) {
                log__.info("二重投稿");
                return getSubmitErrorPage(map, req);
            }

            // 入力チェック
            ActionErrors errors = form.validateInput(
                    getRequestModel(req),
                    con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                saveToken(req);
                return __doInit(map, form, req, res, con);
            }
        } finally {
            con.setAutoCommit(false);
        }

        Enq110ParamModel paramModel = new Enq110ParamModel();
        paramModel.setParam(form);
        biz.doCommit(paramModel, getCountMtController(req));
        paramModel.setFormData(form);

        //テンポラリディレクトリを削除
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID);

        return __setCommitDsp(map, form, req);
    }

    /**
     * <br>[機  能] 後戻処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return フォワード
     * @throws Exception 例外
     */
    private ActionForward __doBack(ActionMapping map, Enq110Form form) throws Exception {

        log__.debug("後戻処理");

        String fwdName = "";
        // メイン画面へ戻る
        if (form.getEnq110BackTo() == GSConstEnquete.BACK_TO_MAIN) {
            fwdName = "backToMain";
        } else {
            if (form.getEnq110DspMode() == 1) {
                // 設問作成画面へ
                fwdName = "enq110preBack";
            } else {
                // アンケートメインへ
                fwdName = "enq110mainBack";
            }
        }

        return map.findForward(fwdName);
    }

    /**
     * <br>[機  能] アンケートの添付ファイルダウンロード処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 例外
     */
    private ActionForward __doDownloadTempFile(ActionMapping map,
            Enq110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        log__.debug("添付ファイルダウンロード処理画処理");

        Enq110Biz biz = new Enq110Biz();
        long binSid = NullDefault.getLong(form.getEnq110BinSid(), -1);
        long enqSid = form.getAnsEnqSid();

        boolean downloadBinFlg = false;
        con.setAutoCommit(true);
        try {
            downloadBinFlg = biz.canDownloadEnqBinData(getRequestModel(req), con, enqSid, binSid);
        } finally {
            con.setAutoCommit(false);
        }
        // ダウンロード可能かチェック
        if (downloadBinFlg) {
            return __doDownload(map, form, req, res, con, DLTYPE_FILE__);
        }

        return null;
    }

    /**
     * <br>[機  能] テンポラリディレクトリから、ファイルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 例外
     */
    private ActionForward __doGetTempDirFile(ActionMapping map,
            Enq110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        String fileId = form.getEnq110BinSid();
        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();

        String tempDir = "";
        Enq210Biz biz210 = new Enq210Biz();
        if (form.getEnq110PreTempDirKbn() == 0) {
            tempDir = biz210.getEnqTempDir(reqMdl);
        } else {
            tempDir = temp.getTempPath(getRequestModel(req),
                    GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID,
                    GSConstEnquete.ENQ_QUESTION, form.getEnq110TempDir());
        }

        Cmn110FileModel fMdl = __getCmn110FileModel(tempDir, fileId);

        //添付ファイル保存用のパスを取得する(フルパス)
        String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
        filePath = IOTools.replaceFileSep(filePath);

        // オペレーションログ出力
        GsMessage gsMsg = new GsMessage();
        String pluginName = gsMsg.getMessage("enq.plugin");
        String textDownload = gsMsg.getMessage("cmn.download");
        EnqCommonBiz enqBiz = new EnqCommonBiz(con);
        enqBiz.outPutLog(map, reqMdl, pluginName, textDownload,
                GSConstLog.LEVEL_INFO, fMdl.getFileName());

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);
        //ファイルをダウンロードする
        TempFileUtil.downloadAtachment(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);


        return null;
    }

    /**
     * <br>[機  能] 添付ファイルモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param tempDir テンポラリディレクトリ
     * @param fileId ファイルID
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private Cmn110FileModel __getCmn110FileModel(String tempDir,
            String fileId)
                    throws Exception {

        //オブジェクトファイルを取得
        ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        Object fObj = objFile.load();
        Cmn110FileModel fMdl = (Cmn110FileModel) fObj;

        return fMdl;
    }

    /**
     * <br>[機  能] アンケートの添付画像ダウンロード処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 例外
     */
    private ActionForward __doGetImageFile(ActionMapping map,
            Enq110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        log__.debug("アンケートの添付画像ダウンロード処理");

        Enq110Biz biz = new Enq110Biz();
        long binSid = NullDefault.getLong(form.getEnq110BinSid(), -1);
        long enqSid = form.getAnsEnqSid();

        boolean downloadBinFlg = false;
        con.setAutoCommit(true);
        try {
            downloadBinFlg = biz.canDownloadEnqBinData(getRequestModel(req), con, enqSid, binSid);
        } finally {
            con.setAutoCommit(false);
        }

        // ダウンロード可能かチェック
        if (downloadBinFlg) {
            return __doDownload(map, form, req, res, con, DLTYPE_IMAGE__);
        }

        return null;
    }

    /**
     * <br>[機  能] 添付ファイルダウンロード処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param dlType ファイル種別(添付ファイル or 画像)
     * @return フォワード
     * @throws Exception 例外
     */
    private ActionForward __doDownload(ActionMapping map,
            Enq110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            int dlType)
                    throws Exception {

        log__.debug("添付ファイルダウンロード処理画処理");

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        CommonBiz cmnBiz = new CommonBiz();

        // バイナリSID取得
        long binSid = NullDefault.getLong(form.getEnq110BinSid(), -1);
        if (binSid < 0) {
            return null;
        }

        // バイナリ情報取得
        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                GroupSession.getResourceManager().getDomain(req));
        String pluginName = gsMsg.getMessage("enq.plugin");
        String textDownload = gsMsg.getMessage("cmn.download");

        // ダウンロード処理
        if (cbMdl != null) {
            // オペレーションログ出力
            if (dlType != DLTYPE_IMAGE__) {
                EnqCommonBiz enqBiz = new EnqCommonBiz(con);
                enqBiz.outPutLog(map, reqMdl, pluginName, textDownload,
                        GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(),
                        String.valueOf(binSid),
                        GSConstEnquete.ENQ_LOG_FLG_DOWNLOAD, form.getAnsEnqSid());
            }

            // 時間のかかる処理の前にコネクションを廃棄
            if (con.getAutoCommit()) {
                con.setAutoCommit(false);
            }
            JDBCUtil.closeConnectionAndNull(con);

            // ファイルをダウンロードする
            TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        }

        return null;
    }
    
    /**
     * <br>[機  能] 説明内画像ファイルを表示する
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
    private ActionForward __doGetBodyFile(
            ActionMapping map,
            Enq110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, Exception {

        Enq110Biz biz = new Enq110Biz();
        RequestModel reqMdl = getRequestModel(req);
        long emnSid = form.getEnq110BodyEmnSid();

        //対象アンケートを参照できないユーザの場合は表示しない
        if (!reqMdl.getSmodel().isAdmin()) {
            int kbn = biz.canAnsEnquete(reqMdl, con, emnSid);
            if (kbn == Enq110Const.ANS_KBN_WITHOUT_AUTHORITY) {
                return null;
            }
        }

        EnqDescBinDao descBinDao = new EnqDescBinDao(con);
        EnqDescBinModel descBinMdl
            = descBinDao.select(emnSid, form.getEnq110BodyFileSid());

        if (descBinMdl == null || descBinMdl.getBinSid() <= 0L) {
            return null;
        }

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;

        cbMdl = cmnBiz.getBinInfo(con, descBinMdl.getBinSid(), reqMdl.getDomain());

        if (cbMdl != null) {
            JDBCUtil.closeConnectionAndNull(con);
            //ファイルをダウンロードする
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                                        Encoding.UTF_8);
        }
        return null;
    }

    /**
     * <br>[機  能] 回答期限切れによる、アンケート回答不可画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doAnsExpired(ActionMapping map,
            Enq110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        log__.debug("回答期限切れによる、アンケート回答不可画面表示処理");

        ActionForward forward = null;
        MessageResources msgRes = getResources(req);
        ActionForward urlForward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        // OKボタンクリック時遷移先
        urlForward = map.findForward("enq110ansExpired");
        cmn999Form.setUrlOK(urlForward.getPath());

        // メッセージのセット
        GsMessage gsMsg = new GsMessage();
        String kigen = gsMsg.getMessage("enq.19");
        cmn999Form.setMessage(msgRes.getMessage("error.cant.ans.expired", kigen));

        // 画面パラメータをセット
        cmn999Form.addHiddenParam("enqEditMode", form.getEnqEditMode());
        cmn999Form.addHiddenParam("enq010folder", form.getEnq010folder());
        cmn999Form.addHiddenParam("enq010subFolder", form.getEnq010subFolder());
        cmn999Form.addHiddenParam("enq010initFlg", form.getEnq010initFlg());
        cmn999Form.addHiddenParam("editEnqSid", form.getEditEnqSid());
        cmn999Form.addHiddenParam("enq010type", form.getEnq010type());
        cmn999Form.addHiddenParam("enq010keyword", form.getEnq010keyword());
        cmn999Form.addHiddenParam("enq010keywordType", form.getEnq010keywordType());
        cmn999Form.addHiddenParam("enq010sendGroup", form.getEnq010sendGroup());
        cmn999Form.addHiddenParam("enq010sendUser", form.getEnq010sendUser());
        cmn999Form.addHiddenParam("enq010sendInput", form.getEnq010sendInput());
        cmn999Form.addHiddenParam("enq010sendInputText", form.getEnq010sendInputText());
        cmn999Form.addHiddenParam("enq010makeDateKbn", form.getEnq010makeDateKbn());
        cmn999Form.addHiddenParam("enq010makeDateFromYear", form.getEnq010makeDateFromYear());
        cmn999Form.addHiddenParam("enq010makeDateFromMonth", form.getEnq010makeDateFromMonth());
        cmn999Form.addHiddenParam("enq010makeDateFromDay", form.getEnq010makeDateFromDay());
        cmn999Form.addHiddenParam("enq010makeDateToYear", form.getEnq010makeDateToYear());
        cmn999Form.addHiddenParam("enq010makeDateToMonth", form.getEnq010makeDateToMonth());
        cmn999Form.addHiddenParam("enq010makeDateToDay", form.getEnq010makeDateToDay());
        cmn999Form.addHiddenParam("enq010pubDateKbn", form.getEnq010pubDateKbn());
        cmn999Form.addHiddenParam("enq010pubDateFromYear", form.getEnq010pubDateFromYear());
        cmn999Form.addHiddenParam("enq010pubDateFromMonth", form.getEnq010pubDateFromMonth());
        cmn999Form.addHiddenParam("enq010pubDateFromDay", form.getEnq010pubDateFromDay());
        cmn999Form.addHiddenParam("enq010pubDateToYear", form.getEnq010pubDateToYear());
        cmn999Form.addHiddenParam("enq010pubDateToMonth", form.getEnq010pubDateToMonth());
        cmn999Form.addHiddenParam("enq010pubDateToDay", form.getEnq010pubDateToDay());
        cmn999Form.addHiddenParam("enq010ansDateKbn", form.getEnq010ansDateKbn());
        cmn999Form.addHiddenParam("enq010ansDateFromYear", form.getEnq010ansDateFromYear());
        cmn999Form.addHiddenParam("enq010ansDateFromMonth", form.getEnq010ansDateFromMonth());
        cmn999Form.addHiddenParam("enq010ansDateFromDay", form.getEnq010ansDateFromDay());
        cmn999Form.addHiddenParam("enq010ansDateToYear", form.getEnq010ansDateToYear());
        cmn999Form.addHiddenParam("enq010ansDateToMonth", form.getEnq010ansDateToMonth());
        cmn999Form.addHiddenParam("enq010ansDateToDay", form.getEnq010ansDateToDay());
        cmn999Form.addHiddenParam("enq010anony", form.getEnq010anony());
        cmn999Form.addHiddenParam("enq010svType", form.getEnq010svType());
        cmn999Form.addHiddenParam("enq010svKeyword", form.getEnq010svKeyword());
        cmn999Form.addHiddenParam("enq010svKeywordType", form.getEnq010svKeywordType());
        cmn999Form.addHiddenParam("enq010svSendGroup", form.getEnq010svSendGroup());
        cmn999Form.addHiddenParam("enq010svSendUser", form.getEnq010svSendUser());
        cmn999Form.addHiddenParam("enq010svSendInput", form.getEnq010svSendInput());
        cmn999Form.addHiddenParam("enq010svSendInputText", form.getEnq010svSendInputText());
        cmn999Form.addHiddenParam("enq010svMakeDateKbn", form.getEnq010svMakeDateKbn());
        cmn999Form.addHiddenParam("enq010svMakeDateFromYear", form.getEnq010svMakeDateFromYear());
        cmn999Form.addHiddenParam("enq010svMakeDateFromMonth", form.getEnq010svMakeDateFromMonth());
        cmn999Form.addHiddenParam("enq010svMakeDateFromDay", form.getEnq010svMakeDateFromDay());
        cmn999Form.addHiddenParam("enq010svMakeDateToYear", form.getEnq010svMakeDateToYear());
        cmn999Form.addHiddenParam("enq010svMakeDateToMonth", form.getEnq010svMakeDateToMonth());
        cmn999Form.addHiddenParam("enq010svMakeDateToDay", form.getEnq010svMakeDateToDay());
        cmn999Form.addHiddenParam("enq010svPubDateKbn", form.getEnq010svPubDateKbn());
        cmn999Form.addHiddenParam("enq010svPubDateFromYear", form.getEnq010svPubDateFromYear());
        cmn999Form.addHiddenParam("enq010svPubDateFromMonth", form.getEnq010svPubDateFromMonth());
        cmn999Form.addHiddenParam("enq010svPubDateFromDay", form.getEnq010svPubDateFromDay());
        cmn999Form.addHiddenParam("enq010svPubDateToYear", form.getEnq010svPubDateToYear());
        cmn999Form.addHiddenParam("enq010svPubDateToMonth", form.getEnq010svPubDateToMonth());
        cmn999Form.addHiddenParam("enq010svPubDateToDay", form.getEnq010svPubDateToDay());
        cmn999Form.addHiddenParam("enq010svAnsDateKbn", form.getEnq010svAnsDateKbn());
        cmn999Form.addHiddenParam("enq010svAnsDateFromYear", form.getEnq010svAnsDateFromYear());
        cmn999Form.addHiddenParam("enq010svAnsDateFromMonth", form.getEnq010svAnsDateFromMonth());
        cmn999Form.addHiddenParam("enq010svAnsDateFromDay", form.getEnq010svAnsDateFromDay());
        cmn999Form.addHiddenParam("enq010svAnsDateToYear", form.getEnq010svAnsDateToYear());
        cmn999Form.addHiddenParam("enq010svAnsDateToMonth", form.getEnq010svAnsDateToMonth());
        cmn999Form.addHiddenParam("enq010svAnsDateToDay", form.getEnq010svAnsDateToDay());
        cmn999Form.addHiddenParam("enq010svAnony", form.getEnq010svAnony());
        cmn999Form.addHiddenParam("enq010pageTop", form.getEnq010pageTop());
        cmn999Form.addHiddenParam("enq010pageBottom", form.getEnq010pageBottom());
        cmn999Form.addHiddenParam("enq010priority", form.getEnq010priority());
        cmn999Form.addHiddenParam("enq010status", form.getEnq010status());
        cmn999Form.addHiddenParam("enq010svPriority", form.getEnq010svPriority());
        cmn999Form.addHiddenParam("enq010svStatus", form.getEnq010svStatus());
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] 登録完了画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __setCommitDsp(ActionMapping map,
            Enq110Form form,
            HttpServletRequest req) throws Exception {

        log__.debug("登録完了画面表示処理");

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        String msgText = gsMsg.getMessage("enq.plugin");
        ActionForward urlForward = null;
        if (form.getEnq210editMode() == Enq210Form.EDITMODE_TEMPLATE) {
            urlForward = map.findForward("enqTemplate");
            ((Enq230Form) form).setHiddenParam(cmn999Form);

            msgText += " " + gsMsg.getMessage("cmn.template");
        } else {
            urlForward = map.findForward("enqList");
            ((Enq010Form) form).setHiddenParam(cmn999Form);
        }
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getEnqEditMode() == GSConstEnquete.EDITMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getEnqEditMode() == GSConstEnquete.EDITMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                msgText));

        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] 編集 / 複写対象が存在しない場合のエラー画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __setNoneDataError(
            ActionMapping map,
            Enq110Form form,
            HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("enqList");

        //メッセージセット
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String enqName = gsMsg.getMessage("enq.plugin");
        if (form.getEnq210editMode() == Enq210Form.EDITMODE_TEMPLATE) {
            enqName = gsMsg.getMessage(req, "cmn.shared.template");
            urlForward = map.findForward("enqTemplate");
        }

        String textOperation = gsMsg.getMessage("cmn.change");
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                enqName, textOperation));

        cmn999Form.setUrlOK(urlForward.getPath());
        ((Enq230Form) form).setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] アンケート設問情報エラー画面
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param errType エラー種類
     * @return アクションフォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __setEnqDataError(ActionMapping map,
            Enq110Form form,
            HttpServletRequest req,
            int errType) throws Exception {

        log__.debug("アンケート設問情報エラー画面表示処理");

        ActionForward forward = null;
        ActionForward urlForward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        // OKボタンクリック時遷移先
        urlForward = map.findForward("enqList");
        cmn999Form.setUrlOK(urlForward.getPath());

        // メッセージのセット
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        cmn999Form.setMessage(gsMsg.getMessage("enq.71"));
        if (errType == Enq110Const.ENQ_QUE_NODATA) {
            cmn999Form.setMessage(gsMsg.getMessage("enq.73"));
        }

        // 画面パラメータをセット
        ((Enq010Form) form).setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}
