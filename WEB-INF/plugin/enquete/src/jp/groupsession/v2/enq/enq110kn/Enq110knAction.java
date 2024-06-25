package jp.groupsession.v2.enq.enq110kn;

import java.sql.Connection;

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
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.enq.AbstractEnqueteAction;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqMainDao;
import jp.groupsession.v2.enq.enq010.Enq010Form;
import jp.groupsession.v2.enq.enq110.Enq110Biz;
import jp.groupsession.v2.enq.enq110.Enq110Const;
import jp.groupsession.v2.enq.model.EnqMainModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アンケート 回答確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq110knAction extends AbstractEnqueteAction {

    /** ダウンロード種別 添付ファイル */
    private static final int DLTYPE_FILE__ = 0;
    /** ダウンロード種別 画像 */
    private static final int DLTYPE_IMAGE__ = 1;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq110knAction.class);

    /** 遷移元の画面ID アンケートメイン */
    private static final String BACK_TO_ENQ010 = "enq010";
    /** 遷移元の画面ID アンケート回答 */
    private static final String BACK_TO_ENQ110 = "enq110";
    /** 遷移元の画面ID アンケート結果確認（一覧） */
    private static final String BACK_TO_ENQ320 = "enq320";

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
        String dlFlg = NullDefault.getString(req.getParameter("enq110knDownloadFlg"), "").trim();

        if (cmd.equals("conf") && dlFlg.equals("1")) {
            log__.debug("添付ファイルダウンロード");
            return true;
        } else if (cmd.equals("getImageFile") && dlFlg.equals("1")) {
            log__.debug("添付画像ダウンロード");
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
        Enq110knForm enq110knForm = (Enq110knForm) form;

        con.setAutoCommit(true);
        try {
            // 遷移元保持パラメータより、戻り先の判定
            long emnSid = enq110knForm.getAnsEnqSid();
            String back = enq110knForm.getEnq110knSvBackScreen();
            if (StringUtil.isNullZeroString(back)) {
                back = __getSvBackScreen(enq110knForm, req);
            }
            if (back.equals(BACK_TO_ENQ320)) {
                // 結果確認（一覧）
                //指定したアンケートが匿名アンケートの場合、不正なアクセスとして扱う
                EnqMainDao enqMainDao = new EnqMainDao(con);
                EnqMainModel enqMainMdl = enqMainDao.select(emnSid);
                if (enqMainMdl == null || enqMainMdl.getEmnAnony() == GSConstEnquete.ANONYMUS_ON) {
                    return getSubmitErrorPage(map, req);
                }
            } else if (back.equals(BACK_TO_ENQ110)) {
                //アンケート回答
                //公開期間外の場合、不正なアクセスとして扱う
                EnqMainDao enqMainDao = new EnqMainDao(con);
                EnqMainModel enqMainMdl = enqMainDao.select(emnSid);
                UDate now = new UDate();
                if (enqMainMdl == null) {
                    return __setEnqDataError(map, enq110knForm, req, Enq110Const.ENQ_QUE_NODATA);
                } else {
                    if (enqMainMdl.getEmnOpenEnd() != null
                            && enqMainMdl.getEmnOpenEnd().compareDateYMD(now) == UDate.LARGE) {
                        return getSubmitErrorPage(map, req);
                    }
                }
            } else {
                //指定したアンケートが自身のアンケートではない場合、不正なアクセスとして扱う
                String answerId = enq110knForm.getEnq110answer();
                if (answerId != null && !answerId.equals(String.valueOf(getSessionUserSid(req)))) {
                    return getSubmitErrorPage(map, req);
                }
            }
        } finally {
            con.setAutoCommit(false);
        }

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        enq110knForm.setCmd(cmd);
        log__.debug("CMD = " + cmd);

        if (cmd.equals("enq110knback")) {
            // 戻る
            forward = __doBack(map, enq110knForm);

        } else if (cmd.equals("enq110kncommit")) {
            if (StringUtil.isNullZeroString(enq110knForm.getEnq110queDate())) {
                return getAuthErrorPage(map, req);
            } else {
                con.setAutoCommit(true);
                try {
                    //アンケート回答中に設問情報が更新された場合、エラーとする。
                    Enq110knParamModel paramMdl = new Enq110knParamModel();
                    paramMdl.setParam(enq110knForm);
                    Enq110Biz biz110 = new Enq110Biz();
                    int checkResult = biz110.checkEnqQueData(con, paramMdl);
                    if (checkResult != Enq110Const.ENQ_QUE_OK) {
                        return __setEnqDataError(map, enq110knForm, req, checkResult);
                    }
                } finally {
                    con.setAutoCommit(false);
                }
            }

            // アンケート回答
            forward = __doCommit(map, enq110knForm, req, res, con);

        } else if (cmd.equals("conf")) {
            // 添付ファイルダウンロード
            String dlFlg = NullDefault.getString(
                    req.getParameter("enq110DownloadFlg"), "").trim();
            if (dlFlg.equals("1")) {
                forward = __doDownloadTempFile(map, enq110knForm, req, res, con);
            }

        } else if (cmd.equals("getImageFile")) {
            // 添付画像ダウンロード
            forward = __doGetImageFile(map, enq110knForm, req, res, con);

        } else {
            // 初期表示処理
            forward = __doInit(map, enq110knForm, req, res, con);
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
            Enq110knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        log__.debug("初期表示処理");

        // 遷移元画面の保持
        __setSvBackScreen(form, req);

        // 表示モードの設定
        int dspMode = 0;
        if (!NullDefault.getString(req.getParameter("CMD"), "").trim().equals("enq110answer")) {
            dspMode = GSConstEnquete.EAM_STS_KBN_YES;
        }

        con.setAutoCommit(true);
        try {
            Enq110knBiz biz = new Enq110knBiz(getRequestModel(req), con);
            Enq110knParamModel paramModel = new Enq110knParamModel();
            paramModel.setParam(form);
            biz.setInitData(paramModel, dspMode);
            paramModel.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

        return __doDsp(map, form, req, res, con);
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
            Enq110knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        log__.debug("再描画処理");

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
    private ActionForward __doCommit(ActionMapping map,
            Enq110knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        log__.debug("回答アンケート登録処理");

        RequestModel reqMdl = getRequestModel(req);

        // 二重投稿チェック
        if (!isTokenValid(req, true)) {
            log__.info("二重投稿");
            return getSubmitErrorPage(map, req);
        }

        // 入力チェック
        ActionErrors errors = form.validateEnq110(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        // 更新処理
        Enq110knBiz biz = new Enq110knBiz(reqMdl, con);
        Enq110knParamModel paramModel = new Enq110knParamModel();
        paramModel.setParam(form);
        boolean flg = biz.doCommit(paramModel);
        paramModel.setFormData(form);
        if (!flg) {
            // 更新失敗
            return __setFailureDsp(map, form, req);
        }

        // オペレーションログの登録
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textEnrty = gsMsg.getMessage("enq.22");
        String pluginName = gsMsg.getMessage("enq.plugin");
        EnqCommonBiz enqBiz = new EnqCommonBiz(con);
        enqBiz.outPutLog(map, reqMdl, pluginName, textEnrty,
                GSConstLog.LEVEL_TRACE, form.getTargetLog(reqMdl));

        return __setCommitDsp(map, form, req);
    }

    /**
     * <br>[機  能] 完了画面
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return アクションフォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __setCommitDsp(ActionMapping map,
            Enq110knForm form,
            HttpServletRequest req) throws Exception {

        log__.debug("完了画面表示処理");

        ActionForward forward = null;
        MessageResources msgRes = getResources(req);
        ActionForward urlForward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        // OKボタンクリック時遷移先
        if (form.getEnq110BackTo() == GSConstEnquete.BACK_TO_ENQUETE) {
            urlForward = map.findForward("enq110kncommit");
        } else {
            urlForward = map.findForward("backToMain");
        }
        cmn999Form.setUrlOK(urlForward.getPath());

        // メッセージのセット
        cmn999Form.setMessage(msgRes.getMessage(
                "kanryo.answer.enquete", StringUtilHtml.transToHTml(form.getEnq110Title())));

        // 画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);


        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] 更新失敗画面
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return アクションフォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __setFailureDsp(ActionMapping map,
            Enq110knForm form,
            HttpServletRequest req) throws Exception {

        log__.debug("更新失敗画面表示処理");

        ActionForward forward = null;
        MessageResources msgRes = getResources(req);
        ActionForward urlForward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        // OKボタンクリック時遷移先
        urlForward = map.findForward("enq110knfailure");
        cmn999Form.setUrlOK(urlForward.getPath());

        // メッセージのセット
        GsMessage gsMsg = new GsMessage();
        String enqType = gsMsg.getMessage(req, "enq.36");
        String update = gsMsg.getMessage(req, "cmn.update");
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data", enqType, update));

        // 画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] 遷移元画面の保持処理
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     */
    private void __setSvBackScreen(Enq110knForm form, HttpServletRequest req)  {
        form.setEnq110knSvBackScreen(__getSvBackScreen(form, req));
    }


    /**
     * <br>[機  能] 遷移元画面を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @return 遷移元画面
     */
    private String __getSvBackScreen(Enq110knForm form, HttpServletRequest req) {

        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        String backScreen = null;
        // CMDから、遷移元画面の判定
        if (cmd.equals("enq320detail")) {
            // 結果確認（一覧）
            backScreen = BACK_TO_ENQ320;
        } else if (cmd.equals("enq110answer")) {
            // アンケート回答
            backScreen = BACK_TO_ENQ110;
        } else {
            // アンケートメイン
            backScreen = BACK_TO_ENQ010;
        }
        return backScreen;
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
    private ActionForward __doBack(ActionMapping map, Enq110knForm form) throws Exception {

        log__.debug("後戻処理");

        String fwdName = "";
        String back = form.getEnq110knSvBackScreen();

        // 遷移元保持パラメータより、戻り先の判定
        if (back.equals(BACK_TO_ENQ320)) {
            // 結果確認（一覧）
            fwdName = "enq110knbackResult";
        } else if (back.equals(BACK_TO_ENQ110)) {
            // アンケート回答画面
            fwdName = "enq110knback";
        } else {
            // アンケートメイン
            fwdName = "enq110knbackMain";
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
            Enq110knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        log__.debug("添付ファイルダウンロード処理画処理");

        Enq110knBiz biz = new Enq110knBiz();
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
            Enq110knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        log__.debug("アンケートの添付画像ダウンロード処理");

        Enq110knBiz biz = new Enq110knBiz();
        long binSid = NullDefault.getLong(form.getEnq110BinSid(), -1);
        long enqSid = form.getAnsEnqSid();

        // ダウンロード可能かチェック
        boolean downloadBinFlg = false;
        con.setAutoCommit(true);
        try {
            downloadBinFlg = biz.canDownloadEnqBinData(getRequestModel(req), con, enqSid, binSid);
        } finally {
            con.setAutoCommit(false);
        }
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
            Enq110knForm form,
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
                        String.valueOf(binSid), GSConstEnquete.ENQ_LOG_FLG_DOWNLOAD,
                        form.getAnsEnqSid());
            }

            // 時間のかかる処理の前にコネクションを廃棄
            JDBCUtil.closeConnectionAndNull(con);

            // ファイルをダウンロードする
            TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        }

        return null;
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
            Enq110knForm form,
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
