package jp.groupsession.v2.ptl.ptl100;

import java.io.File;
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
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ptl.AbstractPortalAdminAction;
import jp.groupsession.v2.ptl.PtlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ポータル ポートレット登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl100Action extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl100Action.class);

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
        log__.debug("START");

        ActionForward forward = null;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Ptl100Form thisForm = (Ptl100Form) form;

        if (cmd.equals("ptl100add")) {
            //「OK」クリック
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("ptl100back")) {
            //戻るボタンクリック
            Ptl100Biz biz = new Ptl100Biz();
            biz.deletePortletTempDir(getRequestModel(req));

            forward = map.findForward("backPortletList");

        } else if (cmd.equals("delete")) {
            //削除ボタンクリック
            forward = __setDeleteDsp(map, req, thisForm, con, res);

        } else if (cmd.equals("delexe")) {
            log__.debug("*** 削除確認OK。");
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteImage")) {
            //ポートレット画像削除
            forward = __doDeleteImage(map, thisForm, req, res, con);

        } else if (cmd.equals("getBodyFile")) {
            //内容欄内画像ファイルの表示
            forward = __doGetBodyFile(map, thisForm, req, res, con);

        } else {

            // トランザクショントークン設定
            saveToken(req);

            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map,
                                  Ptl100Form form,
                                  HttpServletRequest req,
                                  HttpServletResponse res,
                                  Connection con)
                                  throws Exception {

        Ptl100ParamModel paramMdl = new Ptl100ParamModel();
        paramMdl.setParam(form);
        Ptl100Biz biz = new Ptl100Biz();
        biz.setInitData(paramMdl, con, getAppRootPath(), getRequestModel(req));
        paramMdl.setFormData(form);
        // トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doOk(ActionMapping map,
                                Ptl100Form form,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Connection con)
                                throws Exception {

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validatePtl100(getRequestModel(req), getAppRootPath());
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        return map.findForward("moveConfirm");
    }
    /**
     * <br>[機  能] 削除確認画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param con Connection
     * @param res HttpServletResponse
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __setDeleteDsp(ActionMapping map,
                                          HttpServletRequest req,
                                          Ptl100Form form,
                                          Connection con,
                                          HttpServletResponse res) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;
        forwardOk = map.findForward("delOk");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=delexe");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("delback");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //メッセージ
        int editSid = form.getPtlPortletSid();
        MessageResources msgRes = getResources(req);
        Ptl100Biz biz = new Ptl100Biz();
        String msg = biz.getDeletePltMsg(con, editSid, msgRes, getRequestModel(req));
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        cmn999Form.addHiddenParam("ptlCmdMode", form.getPtlCmdMode());
        cmn999Form.addHiddenParam("ptl090category", form.getPtl090category());
        cmn999Form.addHiddenParam("ptl090svCategory", form.getPtl090svCategory());
        cmn999Form.addHiddenParam("ptl090sortPortlet", form.getPtl090sortPortlet());
        cmn999Form.addHiddenParam("ptlPortletSid", form.getPtlPortletSid());
        cmn999Form.addHiddenParam("ptl100init", form.getPtl100init());
        cmn999Form.addHiddenParam("ptl100name", form.getPtl100name());
        cmn999Form.addHiddenParam("ptl100category", form.getPtl100category());
        cmn999Form.addHiddenParam("ptl100border", form.getPtl100border());
        cmn999Form.addHiddenParam("ptl100content", form.getPtl100content());
        cmn999Form.addHiddenParam("ptl100contentHtml", form.getPtl100contentHtml());
        cmn999Form.addHiddenParam("ptl100description", form.getPtl100description());
        cmn999Form.addHiddenParam("ptl100contentType", form.getPtl100contentType());
        cmn999Form.addHiddenParam("ptl100contentUrl", form.getPtl100contentUrl());

        cmn999Form.addHiddenParam("ptlBackPage", form.getPtlBackPage());
        cmn999Form.addHiddenParam("ptlMainSid", form.getPtlMainSid());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Ptl100Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;
        Ptl100Biz biz = new Ptl100Biz();
        boolean commit = false;
        try {
            Ptl100ParamModel paramMdl = new Ptl100ParamModel();
            paramMdl.setParam(form);
            //ポートレット削除
            biz.deletePortlet(paramMdl,
                             form.getPtlPortletSid(),
                             con,
                             getSessionUserSid(req));
            paramMdl.setFormData(form);

            forward = __setCompDsp(map, req, form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("ポートレット情報の削除に失敗", e);
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //テンポラリディレクトリ削除
        RequestModel reqMdl = getRequestModel(req);
        biz.deletePortletTempDir(reqMdl);

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("cmn.delete");

        String opCode = msg;

        //ログ出力処理
        PtlCommonBiz rngBiz = new PtlCommonBiz(con);
        rngBiz.outPutLog(
                map, reqMdl, opCode,
                GSConstLog.LEVEL_INFO,
                "[title]" + form.getPtl100name());

        return forward;
    }
    
    /**
     * <br>[機  能] 内容欄内の画像ファイルを読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetBodyFile(
            ActionMapping map,
            Ptl100Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        String tempSaveId = form.getPtl100TempSaveId();
        //tempSaveIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(tempSaveId)) {
            return getSubmitErrorPage(map, req);
        }
        RequestModel reqMdl = getRequestModel(req);

        Ptl100Biz biz = new Ptl100Biz();

        String tempDir = biz.getPortletBodyTempDir(reqMdl, tempSaveId);
        File bodyFileDir = new File(tempDir);
        File[] files = bodyFileDir.listFiles();
        if (files == null || files.length < 1) {
            return null;
        }

        String downFilePath = "";
        String downFileName = "";
        String tempFileName = "";
        for (File file : files) {
            tempFileName = file.getName();

            if (tempFileName.endsWith(GSConstCommon.ENDSTR_SAVEFILE)) {
                downFilePath = IOTools.replaceFileSep(tempDir + "/" + file.getName());

            } else if (tempFileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                ObjectFile objFile = new ObjectFile(tempDir, tempFileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                downFileName = fMdl.getFileName();
            }
        }
        TempFileUtil.downloadInline(req, res, downFilePath, downFileName, Encoding.UTF_8);

        return null;
    }

    /**
     * <br>[機  能] ポートレット画像削除
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteImage(ActionMapping map,
            Ptl100Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        Ptl100ParamModel paramMdl = new Ptl100ParamModel();
        paramMdl.setParam(form);
        Ptl100Biz biz = new Ptl100Biz();
        //ポートレット画像ファイル削除
        biz.deleteContentImageFile(paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 削除完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __setCompDsp(ActionMapping map,
                                        HttpServletRequest req,
                                        Ptl100Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = null;
        forwardOk = map.findForward("backPortletList");
        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage();
        String portlet = gsMsg.getMessage(req, "ptl.3");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", portlet));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("ptlCmdMode", form.getPtlCmdMode());
        cmn999Form.addHiddenParam("ptl090category", form.getPtl090category());
        cmn999Form.addHiddenParam("ptl090svCategory", form.getPtl090svCategory());
        cmn999Form.addHiddenParam("ptl090sortPortlet", form.getPtl090sortPortlet());

        cmn999Form.addHiddenParam("ptlBackPage", form.getPtlBackPage());
        cmn999Form.addHiddenParam("ptlMainSid", form.getPtlMainSid());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

}

