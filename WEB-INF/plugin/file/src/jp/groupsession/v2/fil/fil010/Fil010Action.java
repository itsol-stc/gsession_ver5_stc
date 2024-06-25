package jp.groupsession.v2.fil.fil010;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] キャビネット一覧画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil010Action extends AbstractFileAction {

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

        if (cmd.equals("fileDownload") || cmd.equals("getPhotoFile")) {
            log__.debug("添付ファイルダウンロード");
            return true;
        }
        return false;
    }

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil010Action.class);

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

        //個人設定が無い場合作成する
        BaseUserModel buMdl = getSessionUserModel(req);
        FilCommonBiz biz = new FilCommonBiz(getRequestModel(req), con);
        biz.getFileUconfModel(buMdl.getUsrsid());
        //管理者設定が無い場合は作成する
        biz.getFileAconfModel();

        log__.debug("fil010Action START");

        ActionForward forward = null;
        Fil010Form thisForm = (Fil010Form) form;
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil010atools")) {
            //管理者設定ボタンクリック
            forward = map.findForward("fil200");

        } else if (cmd.equals("fil010ptools")) {
            //個人設定ボタンクリック
            forward = map.findForward("fil110");

        } else if (cmd.equals("fil010cabinetDetail")) {
            //キャビネット詳細へ遷移
            forward = map.findForward("fil020");

        } else if (cmd.equals("fil010folderList")) {
            //フォルダ情報一覧へ遷移
            forward = map.findForward("fil040");

        } else if (cmd.equals("fil010scDelete")) {
            //ショートカット削除ボタンクリック
            forward = __doDelShortcut(map, thisForm, req, res, con);

        } else if (cmd.equals("fil010folderDetail")) {
            //フォルダ詳細へ遷移
            forward = map.findForward("fil050");

        } else if (cmd.equals("fil010addCabinet")) {
            //キャビネット登録へ遷移
            forward = map.findForward("fil030");

        } else if (cmd.equals("tradeTouroku")) {
            //取引情報登録へ遷移
            forward = map.findForward("fil300");

        } else if (cmd.equals("fil010fileDetail")) {
            //ファイル詳細へ遷移
            forward = map.findForward("fil070");

        } else if (cmd.equals("fil010addFile")) {
            //ファイル登録へ遷移
            forward = map.findForward("fil080");

        } else if (cmd.equals("fil010move")) {
            //フォルダ・ファイル移動へ遷移
            forward = map.findForward("fil090");

        } else if (cmd.equals("fil010search")) {
            //ファイル詳細検索へ遷移
            forward = map.findForward("fil100");

        } else if (cmd.equals("fileDownload")) {
            //添付ファイル名クリック
            forward = __doDownLoad(
                    map, thisForm, req, res, con, GSConstCommon.FILE_DOWNLOAD);

        } else if (cmd.equals("fileDownloadInline")) {
            //プレビュー用
            forward = __doDownLoad(
                    map, thisForm, req, res, con, GSConstCommon.FILE_DOWNLOAD_INLINE);

        } else if (cmd.equals("deleteExe")) {
            log__.debug("削除実行");
            forward = __doDeleteExe(map, thisForm, req, res, con);

        } else if (cmd.equals("fil010callList")) {
            //更新通知一覧へ遷移
            forward = map.findForward("fil240");

        } else if (cmd.equals("tempview")) {
            //アイコン画像ファイルの表示
            forward = __doTempView(map, thisForm, req, res, con);

        } else if (cmd.equals("fil010chnageCabinet")) {
            //表示キャビネットの切り替え
            forward = __doInit(map, thisForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Fil010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        Fil010Biz biz = new Fil010Biz(getRequestModel(req));

        Fil010ParamModel paramMdl = new Fil010ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] ショートカット削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDelShortcut(ActionMapping map,
                                    Fil010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        log__.debug("ショートカットの削除");

        ActionErrors errors = new ActionErrors();

        //入力チェック
        errors = form.validateFil010Check(req);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //削除するショートカットのタイトルを取得する
        Fil010Biz biz = new Fil010Biz(getRequestModel(req));

        Fil010ParamModel paramMdl = new Fil010ParamModel();
        paramMdl.setParam(form);
        String deleteShort = biz.getDeleteShortName(paramMdl, userSid, con);
        if (deleteShort.length() == 0) {
            GsMessage gsMsg = new GsMessage(getRequestModel(req));
            ActionMessage msg = new ActionMessage("error.edit.power.notfound",
                    gsMsg.getMessage("fil.2"),
                    gsMsg.getMessage("cmn.delete"));
            StrutsUtil.addMessage(errors, msg, "selectShortcut");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        paramMdl.setFormData(form);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con, deleteShort);

    }

    /**
     * [機  能] 削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @param deleteShort 削除するショートカット
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Fil010Form form,
        HttpServletRequest req,
        Connection con,
        String deleteShort) throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("cabinetMain");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteExe");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("cabinetMain");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //メッセージ
        String message = getInterMessage(req, "fil.2");

        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.list", message, deleteShort));

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
        Fil010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {
        RequestModel reqMdl = getRequestModel(req);

        BaseUserModel buMdl = reqMdl.getSmodel();

        //選択されたショートカットを削除する
        Fil010Biz biz = new Fil010Biz(reqMdl);

        Fil010ParamModel paramMdl = new Fil010ParamModel();
        paramMdl.setParam(form);
        String[] delInfSid = paramMdl.getFil010SelectDelLink();
        biz.deleteShort(paramMdl, buMdl.getUsrsid(), con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textShortcutOff = gsMsg.getMessage("fil.79");
        String textDelete = gsMsg.getMessage("cmn.delete");

        //ログ出力処理
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);

        StringBuilder sb = new StringBuilder();
        sb.append(textShortcutOff);
        sb.append("\r\n");
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "cmn.target"));
        sb.append("] ");

        for (int i = 0; i < delInfSid.length; i++) {
            sb.append("\r\n");
            sb.append(" ");
            sb.append(filBiz.getDirctoryPath(
                    NullDefault.getInt(delInfSid[i], 0)));
        }



        filBiz.outPutLog(textDelete, GSConstLog.LEVEL_TRACE, sb.toString(), map.getType());


        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req, cmd);
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
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
        Fil010Form form,
        HttpServletRequest req,
        String cmd) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("cabinetMain");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object",
                getInterMessage(req, "fil.2")));

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
     * @param downloadKbn ダウンロード区分
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownLoad(ActionMapping map,
                                        Fil010Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con,
                                        int downloadKbn) throws SQLException, Exception {

        Long binSid = NullDefault.getLong(form.getFileSid(), -1);

        //権限チェック
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req),  con);
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        boolean checkResult = filBiz.isDownloadAuthUser(binSid);

        //ファイルの論理削除チェック
        if (checkResult) {
            FileFileBinDao filBinDao = new FileFileBinDao(con);
            checkResult = filBinDao.existsBinFile(binSid, true, true);
        }

        if (!checkResult) {
            return getPowNoneErrorPage(map, req,
                    gsMsg.getMessage("cmn.reading"),
                    gsMsg.getMessage("cmn.download"));
        }
        CommonBiz cmnBiz = new CommonBiz();

        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                GroupSession.getResourceManager().getDomain(req));

        if (cbMdl == null) {
            return __doInit(map, form, req, res, con);
        }

        String textDownload = gsMsg.getMessage("cmn.download");

        //ログ出力処理
        filBiz.outPutLog(
                textDownload, GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(), map.getType(),
                String.valueOf(binSid));

        //集計用データを登録する
        filBiz.regFileDownloadLogCnt(getSessionUserSid(req), binSid, new UDate());

        //時間のかかる処理の前にコネクションを破棄
        JDBCUtil.closeConnectionAndNull(con);

        //ファイルをダウンロードする
        cbMdl.setBinFilekbn(GSConst.FILEKBN_FILE);
        if (downloadKbn == GSConstCommon.FILE_DOWNLOAD_INLINE) {
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        } else {
            TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
        }
        cbMdl.removeTempFile();

        return null;
    }

    /**
     * <br>[機  能] 添付ファイルをブラウザ内に表示処理
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
        Fil010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {
        RequestModel reqMdl = getRequestModel(req);

        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);
        int cabSid = NullDefault.getInt(form.getFil010SelectCabinet(), -1);
        //指定したバイナリのデータが取得可能
        if (fileBiz.isCheckFileIcon(
                cabSid, form.getFil010binSid())) {

            CommonBiz cmnBiz = new CommonBiz();
            CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, form.getFil010binSid(),
                    GroupSession.getResourceManager().getDomain(req));

            if (cbMdl != null) {
                JDBCUtil.closeConnectionAndNull(con);

                //ファイルをダウンロードする
                TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                        Encoding.UTF_8);
            }
        }
        return null;
    }

}