package jp.groupsession.v2.ntp.ntp062kn;

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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.AbstractNippouAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.ntp062.Ntp062Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 インポート確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp062knAction extends AbstractNippouAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp062knAction.class);
    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     *
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("downLoad")) {
            log__.debug("取り込みCSVファイルダウンロード");
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
                                         Connection con) throws Exception {

        ActionForward forward = null;
        Ntp062knForm ntpform = (Ntp062knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //インポートボタン押下
        if (cmd.equals("doImport")) {
            log__.debug("インポートボタン押下");
            forward = __doImport(map, ntpform, req, res, con);
        //インポート完了
        } else if (cmd.equals("comp")) {
            log__.debug("インポート完了");
            forward = __doBack(map, ntpform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("back_to_import_input")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("back_to_import_input");
        //添付ダウンロード
        } else if (cmd.equals("downLoad")) {
            forward = __doDownLoad(map, ntpform, req, res, con);
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, ntpform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception CSV情報取得時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Ntp062knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Ntp062knBiz biz = new Ntp062knBiz(reqMdl, con);

        //テンポラリディレクトリパスを取得
        Ntp062Biz biz062 = new Ntp062Biz(con, reqMdl);
        String tempDir = biz062.getTempDir();

        //セッションユーザSID取得
        BaseUserModel umodel = getSessionUserModel(req);
        int userSid = umodel.getUsrsid();

        //再入力チェック
        ActionErrors errors = form.validateCheck(
                getRequestModel(req), tempDir, con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        Ntp062knParamModel paramMdl = new Ntp062knParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, umodel, con, tempDir);
        //取込みファイル名称取得
        biz.setImportFileName(paramMdl, tempDir);
        //登録対象名称取得
        biz.setImportTargetName(paramMdl, String.valueOf(userSid));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] インポート実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doImport(ActionMapping map,
                                      Ntp062knForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);
        Ntp062Biz biz062 = new Ntp062Biz(con, reqMdl);
        String tempDir = biz062.getTempDir();

        //再入力チェック
        ActionErrors errors = form.validateCheck(
               getRequestModel(req), tempDir, con);
        if (errors.size() > 0) {
            log__.debug("取込み前最チェックエラー");
            addErrors(req, errors);
            return map.getInputForward();
        }

        //取込み処理
        con.setAutoCommit(false);
        boolean commit = false;

        try {

            //セッションユーザSID取得
            BaseUserModel umodel = getSessionUserModel(req);
            int userSid = umodel.getUsrsid();

            //システム日付取得
            UDate now = new UDate();

            //採番用コネクション取得
            MlCountMtController cntCon = getCountMtController(req);

            //インポート
            Ntp062ImportCsv imp = new Ntp062ImportCsv(
                        con, req, userSid, now, cntCon, userSid, form.getSv_users(),
                        form.getNtp061ChkShohinSidList());
            imp.setNtp061NanPermitView(form.getNtp061NanPermitView());
            imp.setNtp061NanPermitEdit(form.getNtp061NanPermitEdit());

            imp.setNanPermitTargetView(form.getNtp061NanPermitUserView());
            if (form.getNtp061NanPermitView() == GSConstNippou.NAP_KBN_USERGROUP) {
                //閲覧権限 = 担当者および指定ユーザ の場合
                imp.setNanPermitTargetEdit(form.getNtp061NanPermitUserEdit());
            } else if (form.getNtp061NanPermitEdit() == GSConstNippou.NAP_KBN_USERGROUP) {
                //編集権限 = 担当者および指定ユーザ の場合
                imp.setNanPermitTargetEdit(form.getNtp061NanPermitEditUser());
            }

            imp.importCsv(tempDir);

            GsMessage gsMsg = new GsMessage();
            /** メッセージ インポート **/
            String strImport = gsMsg.getMessage(req, "cmn.import");

            //ログ出力処理
            NtpCommonBiz ntpBiz = new NtpCommonBiz(con, reqMdl);
            ntpBiz.outPutLog(
                    map, req, res,
                    strImport, GSConstLog.LEVEL_INFO, "");

            commit = true;

            //完了画面遷移
            return __doImportComp(map, form, req, res, con);

        } catch (Exception e) {
            log__.error("案件CSVの取り込みに失敗しました。" + e);
            throw e;
        } finally {

            //テンポラリディレクトリのファイル削除を行う
            biz062.deleteTempDir();

            if (commit) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] インポート完了後の画面遷移設定
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
    private ActionForward __doImportComp(ActionMapping map,
                                          Ntp062knForm form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("importComp");
        cmn999Form.setUrlOK(forwardOk.getPath());
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", "案件データ"));

        //画面パラメータをセット
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 戻るボタン落下時の画面遷移を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doBack(ActionMapping map,
                                    Ntp062knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) {

        ActionForward forward = null;

        forward = map.findForward("ntp060");

        return forward;
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
    private ActionForward __doDownLoad(ActionMapping map,
                                        Ntp062knForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException, Exception {


        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);
        Ntp062Biz biz062 = new Ntp062Biz(con, reqMdl);
        String tempDir = biz062.getTempDir();

        Ntp062knBiz biz = new Ntp062knBiz(getRequestModel(req), con);
        //取込みファイル名称取得
        String fileId = biz.getImportFileName(tempDir);
        log__.debug("tempDir==>" + tempDir);
        log__.debug("fileId==>" + fileId);
        //オブジェクトファイルを取得
        ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        Object fObj = objFile.load();
        Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
        //添付ファイル保存用のパスを取得する(フルパス)
        String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
        filePath = IOTools.replaceFileSep(filePath);

        GsMessage gsMsg = new GsMessage();
        /** メッセージ ダウンロード **/
        String download = gsMsg.getMessage(req, "cmn.download");

        //ログ出力処理
        NtpCommonBiz ntpBiz = new NtpCommonBiz(con, getRequestModel(req));
        ntpBiz.outPutLog(
                map, req, res,
                download, GSConstLog.LEVEL_INFO, fMdl.getFileName());

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);
        //ファイルをダウンロードする
        TempFileUtil.downloadAtachment(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);

        return null;
    }
}