package jp.groupsession.v2.ntp.ntp060;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.ntp061.Ntp061Biz;
import jp.groupsession.v2.ntp.ntp061.Ntp061Form;
import jp.groupsession.v2.ntp.ntp061kn.Ntp061knForm;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 案件検索画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp060Action extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp060Action.class);
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
        }
        return false;
    }
    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Ntp060Form thisForm = (Ntp060Form) form;
        if (cmd.equals("backNtp060")) {
            log__.debug("*****戻るボタンクリック");
            forward = __doBack(map, thisForm, req, con);
        } else if (cmd.equals("masta")) {
            //マスタメンテ
            forward = map.findForward("masta");
        } else if (cmd.equals("bunseki")) {
            //分析
            forward = map.findForward("bunseki");
        } else if (cmd.equals("target")) {
            //目標設定画面へ
            forward = map.findForward("target");
        } else if (cmd.equals("add")) {
            log__.debug("*****登録ボタンクリック");
            forward = __moveAdd(map, thisForm, req, res, con);
        } else if (cmd.equals("edit")) {
            log__.debug("*****編集ボタンクリック");
            forward = __moveEditOrView(map, thisForm, req, res, con);
        } else if (cmd.equals("nippou")) {
            log__.debug("*****日報リンククリック");
            forward = map.findForward("ntp010");
        } else if (cmd.equals("search")) {
            log__.debug("*****検索ボタンクリック");
            thisForm.setNtp060PageTop(0);
            thisForm.setNtp060PageBottom(0);
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("prevPage")) {
            log__.debug("*****前ページクリック");
            thisForm.setNtp060PageTop(thisForm.getNtp060PageTop() - 1);
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            log__.debug("*****次ページクリック");
            thisForm.setNtp060PageTop(thisForm.getNtp060PageTop() + 1);
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("changePage")) {
            log__.debug("*****ページコンボ変更");
            forward = __doSearch(map, thisForm, req, res, con);
        } else if (cmd.equals("import")) {
            log__.debug("インポートボタン押下");
            forward = map.findForward("ntp062");
        } else if (cmd.equals("export")) {
            log__.debug("エクスポートボタン押下");
            __doDownLoad(map, thisForm, req, res, con);
        } else if (cmd.equals("changeGyomu")) {
            log__.debug("業務変更");
            int sessionUserSid = this.getSessionUserModel(req).getUsrsid();
            Ntp060Biz biz = new Ntp060Biz(con, getRequestModel(req));
            Ntp060ParamModel paramMdl = new Ntp060ParamModel();
            paramMdl.setParam(form);
            biz.setInitData(paramMdl, sessionUserSid, con);
            paramMdl.setFormData(form);

            forward = map.getInputForward();
        } else if (cmd.equals("ntpSearch")) {
            log__.debug("日報検索");
            forward = map.findForward("ntpsearch");
        } else if (cmd.equals("ktool")) {
            //管理者ツール
            forward = map.findForward("ktool");
        } else if (cmd.equals("pset")) {
            //個人設定
            forward = map.findForward("pset");
        } else {
            log__.debug("*****初期表示");
            forward = __doSearch(map, thisForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 検索処理
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
    private ActionForward __doSearch(ActionMapping map,
                                    Ntp060Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        ActionMessage msg = null;

        //テンポラリディレクトリパスを削除
        Ntp060Biz biz = new Ntp060Biz(con, getRequestModel(req));
        biz.deleteTempDir();

        int sessionUserSid = this.getSessionUserModel(req).getUsrsid();

        BaseUserModel umodel = getSessionUserModel(req);
        CommonBiz commonBiz = new CommonBiz();
        if (commonBiz.isPluginAdmin(con, umodel, GSConstNippou.PLUGIN_ID_NIPPOU)) {
            form.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            form.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }


        Ntp060ParamModel paramMdl = new Ntp060ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        paramMdl = new Ntp060ParamModel();
        paramMdl.setParam(form);
        biz.doSearch(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);

        log__.debug("*****商品件数 = " + form.getNtp060AnkenList().size());

        if (form.getNtp060AnkenList().size() == 0) {
            msg = new ActionMessage("search.data.notfound", "案件");
            StrutsUtil.addMessage(errors, msg, "ntp060NanSid__");
            addErrors(req, errors);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 案件情報ダウンロードの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private void __doDownLoad(
        ActionMapping map,
        Ntp060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        log__.debug("エクスポート処理");

        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);
        Ntp060Biz biz = new Ntp060Biz(con, reqMdl);
        String tempDir = biz.getTempDir();

        String fileName = Ntp060CsvWriter.FILE_NAME;
        String fullPath = tempDir + fileName;

        __doExport(map, form, req, res, con, tempDir);

        GsMessage gsMsg = new GsMessage();
        /** メッセージ エクスポート **/
        String export = gsMsg.getMessage(req, "cmn.export");
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
        ntpBiz.outPutLog(
                map, req, res, export, GSConstLog.LEVEL_TRACE, fileName);

        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        biz.deleteTempDir();
    }

    /**
     * <br>[機  能] エクスポート処理を実行(氏名カナ)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param outDir 出力先ディレクトリ
     * @throws Exception 実行例外
     */
    private void __doExport(ActionMapping map, Ntp060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con, String outDir)
            throws Exception {
        log__.debug("エクスポート処理(CSV)");

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        //セッションユーザSID
        int sessionUsrSid = usModel.getUsrsid();

        Ntp060Biz biz = new Ntp060Biz(con, getRequestModel(req));
        //検索条件をセット(SAVEより)
        Ntp060ParamModel paramMdl = new Ntp060ParamModel();
        paramMdl.setParam(form);
        Ntp060SearchModel searchMdl = biz.setAnkenSearchModel(paramMdl);
        paramMdl.setFormData(form);

        //CSVファイルを作成
        Ntp060CsvWriter write = new Ntp060CsvWriter();
        write.setSearchModel(searchMdl);
        write.setSessionUserSid(sessionUsrSid);
        write.setReqMdl(getRequestModel(req));
        write.outputCsv(con, outDir);
    }

    /**
     * <br>[機  能] 戻るボタンをクリック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map,
                                    Ntp060Form form,
                                    HttpServletRequest req,
                                    Connection con) {
        ActionForward forward = null;
        if (form.getNtp060ReturnPage() == null) {
            forward = map.findForward("ntp010");
        } else {
            forward = map.findForward(form.getNtp060ReturnPage());
        }

        //テンポラリディレクトリ削除
        Ntp060Biz biz = new Ntp060Biz(con, getRequestModel(req));
        biz.deleteTempDir();

        return forward;
    }
    /**
     * <br>[機  能] 新規作成ボタンをクリック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __moveAdd(ActionMapping map,
                                    Ntp060Form form, HttpServletRequest req,
                                    HttpServletResponse res, Connection con) throws Exception {
        ActionForward forward = map.findForward("ntp061");
        Ntp061Form ntpForm = new Ntp061Form();
        ntpForm.setNtp060ProcMode(GSConstNippou.CMD_ADD);
        req.setAttribute("ntp061", ntpForm);

        return forward;
    }
    /**
     * <br>[機  能] 戻るボタンをクリック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __moveEditOrView(ActionMapping map,
                                    Ntp060Form form, HttpServletRequest req,
                                    HttpServletResponse res, Connection con) throws Exception {
        ActionForward forward = null;
        Ntp061Biz biz = new Ntp061Biz(
                con, getCountMtController(req), getRequestModel(req));
        if (!biz.isViewable(form.getNtp060NanSid())) {
            //編集時,閲覧権限エラー
            GsMessage gsMsg = new GsMessage(req);
            return getPermissionError(map, null,
                    map.findForward("ntp060"),
                    gsMsg.getMessage("cmn.edit"),
                    req, res);
        }
        if (!biz.isEditable(form.getNtp060NanSid())) {
            //編集権限なし
            Ntp061knForm ntpForm = new Ntp061knForm();
            ntpForm.setNtp061ReturnPage("ntp060");
            ntpForm.setNtp060ProcMode(GSConstNippou.CMD_DSP);
            req.setAttribute("ntp061knForm", ntpForm);
            forward = map.findForward("ntp061kn");
            return forward;
        }
        Ntp061Form ntpForm = new Ntp061Form();
        ntpForm.setNtp061ReturnPage("ntp060");
        ntpForm.setNtp060ProcMode(GSConstNippou.CMD_EDIT);
        req.setAttribute("ntp061Form", ntpForm);
        forward = map.findForward("ntp061");
        return forward;
    }
}
