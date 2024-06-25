package jp.groupsession.v2.zsk.zsk040kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.AbstractZaisekiAdminAction;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.GSValidateZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;
import jp.groupsession.v2.zsk.zsk040.Zsk040Biz;

/**
 * <br>[機  能] 在席管理 座席表登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk040knAction extends AbstractZaisekiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk040knAction.class);

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

        if (cmd.equals("fileDownload")) {
            log__.debug("添付ファイルダウンロード");
            return true;

        }
        return false;
    }
    /**
     * <br>アクション実行
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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Zsk040knForm zskForm = (Zsk040knForm) form;
        if (cmd.equals("zsk040")) {
            //戻る
            forward = map.findForward("zsk040");
        } else if (cmd.equals("cmn999")) {
            //確定
            forward = __doCommit(map, zskForm, req, res, con);
        } else if (cmd.equals("fileDownload")) {
            log__.debug("添付ファイルダウンロード");
            forward = __doDownLoad(map, zskForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, zskForm, req, res, con);
        }
        return forward;
    }

    /**
     * 初期表示処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doInit(ActionMapping map, Zsk040knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        //テンポラリディレクトリ取得
        Zsk040Biz biz040 = new Zsk040Biz();
        String tempDir = biz040.getTempDir(getRequestModel(req));

        Zsk040knParamModel paramMdl = new Zsk040knParamModel();
        paramMdl.setParam(form);
        Zsk040knBiz biz = new Zsk040knBiz();
        biz.setInitData(paramMdl, con, getAppRootPath(), tempDir);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }
    /**
     * 座席表情報を登録します。
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doCommit(ActionMapping map, Zsk040knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //テンポラリディレクトリ取得
        RequestModel reqMdl = getRequestModel(req);
        Zsk040Biz biz040 = new Zsk040Biz();
        String tempDir = biz040.getTempDir(reqMdl);

        // 入力チェック
        ActionErrors errors = new ActionErrors();
        GSValidateZaiseki valZsk = new GSValidateZaiseki();
        ArrayList<String> list = valZsk.getTempFileName(tempDir);
        String fileName = "";
        if (list.size() > 0) {
            fileName = list.get(0);
        }

        errors = form.validateCheck(fileName, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        // 登録処理
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        Zsk040knBiz biz = new Zsk040knBiz();

        MlCountMtController cntCon = getCountMtController(req);
        boolean commit = false;
        try {

            //登録処理
            //座席情報SIDを取得
            Zsk040knParamModel paramMdl = new Zsk040knParamModel();
            paramMdl.setParam(form);
            biz.insertZifData(paramMdl, con, cntCon, userSid,
                    this.getAppRootPath(), tempDir);
            paramMdl.setFormData(form);

            commit = true;
        } catch (Exception e) {
            log__.error("スレッド作成処理エラー", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.entry");

        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(getRequestModel(req));
        cmnBiz.outPutLog(con,
                msg, GSConstLog.LEVEL_INFO, "[name]" + form.getZsk040name(), map.getType()
                );

        //テンポラリディレクトリ内のファイルを削除
        biz040.deleteTempDir(reqMdl);

        return __setCompPageParam(map, req, form);
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
    private ActionForward __doDownLoad(
            ActionMapping map,
            Zsk040knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        String fileId = form.getZsk040knTmpFileId();
        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }


        RequestModel reqMdl = getRequestModel(req);
        Zsk040Biz biz040 = new Zsk040Biz();
        String tempDir = biz040.getTempDir(reqMdl);

        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(getRequestModel(req));

        log__.debug("tempDir==>" + tempDir);
        log__.debug("tempFile==>" + fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        Object fObj = objFile.load();
        Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
        //添付ファイル保存用のパスを取得する(フルパス)
        String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
        filePath = IOTools.replaceFileSep(filePath);
        //ファイルをダウンロードする
        TempFileUtil.downloadAtachment(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.download");

        //ログ出力
        cmnBiz.outPutLog(con,
                msg, GSConstLog.LEVEL_INFO, fMdl.getFileName(), map.getType(),
                fileId, GSConstZaiseki.ZSK_LOG_FLG_DOWNLOAD);

        return null;
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __setCompPageParam(
            ActionMapping map,
            HttpServletRequest req,
            Zsk040knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);

        GsMessage gsMsg = new GsMessage();
        String message = gsMsg.getMessage(req, "zsk.29");

        //メッセージセット
        String msgState = null;
        urlForward = map.findForward("zasekiList");
        msgState = "touroku.kanryo.object";

        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage(msgState, message));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("selectZifSid", form.getSelectZifSid());
        cmn999Form.addHiddenParam("uioStatus", form.getUioStatus());
        cmn999Form.addHiddenParam("uioStatusBiko", form.getUioStatusBiko());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
