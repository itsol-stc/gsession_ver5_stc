package jp.groupsession.v2.ptl.ptl160;

import java.io.File;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.GSAttachFileNotExistException;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.ptl.AbstractPortalAdminAction;
import jp.groupsession.v2.ptl.PtlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ポータル ポートレット画像選択ポップアップのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl160Action extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl160Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "ptl160";

    /**
     * <br>[機  能] アクション実行
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
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;

        Ptl160Form thisForm = (Ptl160Form) form;

        //ファイルサイズがMAXサイズを超えた場合、共通メッセージ画面でエラーメッセージを表示
        Object obj = req.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
        if (obj != null) {
            Boolean maxlength = (Boolean) obj;
            if (maxlength.booleanValue()) {
                return getFileSizeErrorPage(map, req, res);
            }
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fileUpload")) {
            log__.debug("添付ボタンクリック");
            forward = __doUpload(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws IOToolsException 添付ファイル操作時例外
     */
    private ActionForward __doInit(
        ActionMapping map,
        Ptl160Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException {

        //初期表示情報を画面にセットする
        con.setAutoCommit(true);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstPortal.PLUGIN_ID, TEMP_DIRECTORY_ID, "portletImage");
        temp.createTempDir(getRequestModel(req),
                GSConstPortal.PLUGIN_ID, TEMP_DIRECTORY_ID, "portletImage");
        Ptl160Biz biz = new Ptl160Biz();
        biz.setInitData(form, con);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 指定された添付ファイルをテンポラリファイルに保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws IOException ファイルアクセス時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws GSException GS用汎実行例外
     */
    private ActionForward __doUpload(ActionMapping map,
        Ptl160Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws SQLException, IOException, IOToolsException, GSException {

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパス
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(reqMdl, GSConstPortal.PLUGIN_ID,
                TEMP_DIRECTORY_ID, "portletImage");

        if (temp.checkTempPath(reqMdl, GSConstPortal.PLUGIN_ID, TEMP_DIRECTORY_ID, "portletImage")
                == false) {
            throw new IOToolsException("テンポラリディレクトリパスが不正 : tempDir = " + tempDir);
        }


        String fileName = "";
        try {

            //入力チェック
            con.setAutoCommit(true);
            ActionErrors errors = form.validate110(con, req);
            con.setAutoCommit(false);
            if (!errors.isEmpty()) {
                super.addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }

            FormFile formFile = form.getPtl160file();
            ActionMessage msg = null;

            //単一の画像アップロードモード
            if (!Cmn110Biz.isExtensionOk(formFile.getFileName())) {
                //BMP,JPG,JPEG,GIF,PNG以外のファイルならばエラー
                msg = new ActionMessage("error.select2.required.extent");
                errors.add("error.select2.required.extent", msg);
                super.addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }
            fileName = (new File(formFile.getFileName())).getName();
            form.setPtl160tempName(fileName);

            //アプリケーションのルートパス
            String appRootPath = getAppRootPath();

            //採番コントローラー
            MlCountMtController cntCon = getCountMtController(req);

            //ログインユーザSIDを取得
            int userSid = 0;
            BaseUserModel buMdl = getSessionUserModel(req);
            if (buMdl == null) {
                throw new GSAuthenticateException("ユーザ情報の取得に失敗");
            }

            boolean commit = false;
            try {
                //添付ファイルアップロード
                TempFileUtil.upload(
                        formFile, tempDir, IOTools.replaceFileSep(formFile.getFileName()));

                //画像登録
                Ptl160Biz biz = new Ptl160Biz();
                biz.entryImageData(con, form, tempDir, appRootPath, userSid, cntCon);

                con.commit();
                form.setPtl160Decision(1);
                commit = true;

                //ログ出力処理
                GsMessage gsMsg = new GsMessage(reqMdl);
                PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
                String opCode = "";

                if (form.getPtlCmdMode() == GSConstPortal.CMD_MODE_ADD) {
                    opCode = gsMsg.getMessage("cmn.entry");
                } else if (form.getPtlCmdMode() == GSConstPortal.CMD_MODE_EDIT) {
                    opCode = gsMsg.getMessage("cmn.edit");
                }

                ptlBiz.outPutLog(
                        map, reqMdl, opCode,
                        GSConstLog.LEVEL_INFO,
                        "[name]" + fileName);


            } catch (SQLException e) {
                log__.error("ポートレット画像登録処理エラー", e);
                throw e;
            } finally {
                if (!commit) {
                    con.rollback();
                }
            }
        } catch (TempFileException e) {
            GSAttachFileNotExistException cause =
                    GSAttachFileNotExistException.searchCause(e);
            if (cause == null) {
                log__.error("Exception", e);
                throw new IOException(e);
            }
            ActionMessage msg = null;
            ActionErrors errors = new ActionErrors();
            msg = new ActionMessage("error.save.attach.filenotexist");
            errors.add("error.save.attach.filenotexist", msg);
            super.addErrors(req, errors);

        } catch (IOException e) {
            log__.error("IOException", e);
            throw e;
        } catch (IOToolsException e) {
            log__.error("IOToolsException", e);
            throw e;
        } catch (Exception e) {
            log__.error("Exception", e);
            throw new IOException(e);
        } finally {
            try {
                //テンポラリディレクトリのファイル削除を行う
                temp.deleteTempPath(getRequestModel(req),
                        GSConstPortal.PLUGIN_ID, TEMP_DIRECTORY_ID, "portletImage");
            } catch (Exception e) {
            }
        }


        return __doInit(map, form, req, res, con);
    }
}