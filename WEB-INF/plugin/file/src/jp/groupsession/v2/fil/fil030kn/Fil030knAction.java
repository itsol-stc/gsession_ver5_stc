package jp.groupsession.v2.fil.fil030kn;

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
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.AbstractFileSubAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil030.Fil030Biz;
import jp.groupsession.v2.fil.fil030.Fil030Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] キャビネット登録・編集確認画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil030knAction extends AbstractFileSubAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil030knAction.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "fil030";
    /** アイコン画像名 */
    public String imageFileName__ = "";
    /** アイコン画像保存名 */
    public String imageFileSaveName__ = "";

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
            log__.debug("アイコンファイルダウンロード");
            return true;
        }
        return false;
    }

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

        log__.debug("fil030knAction START");
        con.setAutoCommit(true);
        ActionForward forward = null;
        Fil030knForm thisForm = (Fil030knForm) form;
        RequestModel reqMdl = getRequestModel(req);
        //キャビネット登録・編集権限チェック
        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl, con);
        if (thisForm.getCmnMode().equals(GSConstFile.CMN_MODE_ADD)) {
            String cabinetKbn = thisForm.getFil010DspCabinetKbn();
            if (!cmnBiz.isCanCreateCabinetUser(cabinetKbn)) {
                return __doNonePowerError(map, thisForm, req, res, con);
            }
        } else if (thisForm.getCmnMode().equals(GSConstFile.CMN_MODE_MLT)) {
            if (thisForm.getFil220sltCheck() == null || thisForm.getFil220sltCheck().length < 1) {
                return getSubmitErrorPage(map, req);
            }
            for (String cadSidStr : thisForm.getFil220sltCheck()) {
                int cabSid = NullDefault.getInt(cadSidStr, -1);
                int ecode = cmnBiz.checkPowEditCab(cabSid);
                if (ecode == FilCommonBiz.ERR_NONE_CAB_EDIT_POWER) {
                    return __doNonePowerError(map, thisForm, req, res, con);
                }
                if (ecode == FilCommonBiz.ERR_NOT_EXIST) {
                    GsMessage gsMsg = new GsMessage(req);
                    String targetName = "";
                    targetName = gsMsg.getMessage("fil.23");
                    return getCanNotViewNonePowerErrorPage(map, req, targetName);
                }
                if (!cmnBiz.checkCabinetDeleteKbn(cabSid)) {
                    return __doNonePowerError(map, thisForm, req, res, con);
                }
            }
            Fil030Biz biz = new Fil030Biz(con, reqMdl);
            if (!biz.checkCabinetType(thisForm.getFil220sltCheck())) {
                return getSubmitErrorPage(map, req);
            }
        } else {
            int cabSid = NullDefault.getInt(thisForm.getFil030SelectCabinet(), -1);
            int ecode = cmnBiz.checkPowEditCab(cabSid);
            if (ecode == FilCommonBiz.ERR_NONE_CAB_EDIT_POWER) {
                return __doNonePowerError(map, thisForm, req, res, con);
            }
            if (ecode == FilCommonBiz.ERR_NOT_EXIST) {
                GsMessage gsMsg = new GsMessage(req);
                String targetName = "";
                targetName = gsMsg.getMessage("fil.23");
                return getCanNotViewNonePowerErrorPage(map, req, targetName);
            }
            if (!cmnBiz.checkCabinetDeleteKbn(cabSid)) {
                return __doNonePowerError(map, thisForm, req, res, con);
            }
        }

        con.setAutoCommit(false);

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil030knback")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("fil030knok")) {
            //OKボタンクリック
            forward = __doOk(map, thisForm, req, res, con);
        } else if (cmd.equals("getImageFile")) {
            //アイコン画像ファイルの表示
            forward = __doGetImageFile(map, thisForm, req, res, con);
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
     * @throws IOToolsException ファイルアクセス例外
     */
    private ActionForward __doInit(ActionMapping map,
            Fil030knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IOToolsException {
        con.setAutoCommit(true);
        Fil030knBiz biz = new Fil030knBiz(getRequestModel(req));

        Fil030knParamModel paramMdl = new Fil030knParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);


        paramMdl = new Fil030knParamModel();
        paramMdl.setParam(form);

        //マークファイル情報セット
        biz.setTempFilesMark(paramMdl, tempDir, con);

        paramMdl.setFormData(form);



        if (!NullDefault.getString(form.getFil030ImageName(), "").equals("")
                && !NullDefault.getString(form.getFil030ImageSaveName(), "").equals("")) {
            imageFileName__ = form.getFil030ImageName();
            imageFileSaveName__ = form.getFil030ImageSaveName();
        }

        //トランザクショントークン設定
        this.saveToken(req);
        con.setAutoCommit(false);
        return map.getInputForward();
    }
    /**
     * <br>[機  能] 登録処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doOk(ActionMapping map,
            Fil030knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {


        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        RequestModel reqMdl = getRequestModel(req);
        BaseUserModel usModel = reqMdl.getSmodel();
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);
        //入力チェック
        ActionErrors errors = form.validateCheck(map, req, con, usModel, tempDir);
        if (errors.size() > 0) {
            log__.debug("入力エラー");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        //キャビネット区分書き換えチェック
        Fil030knParamModel paramMdl = new Fil030knParamModel();
        paramMdl.setParam(form);
        Fil030Biz checkBiz = new Fil030Biz(con, getRequestModel(req));
        if (!checkBiz.checkCabinetEdit(paramMdl)) {
            return getSubmitErrorPage(map, req);
        }
        
        Fil030knBiz biz = new Fil030knBiz(getRequestModel(req));
        con.setAutoCommit(false);
        boolean commitFlg = false;
        try {
            MlCountMtController cntCon = getCountMtController(req);
            String appRoot = getAppRootPath();

            List<String> cabNames = biz.doOk(paramMdl, cntCon, tempDir,
                    appRoot, con, TEMP_DIRECTORY_ID);
            paramMdl.setFormData(form);

            GsMessage gsMsg = new GsMessage();
            String textEdit = gsMsg.getMessage(req, "cmn.change");
            String textEntry = gsMsg.getMessage(req, "cmn.entry");
            String textMultiEdit = gsMsg.getMessage(req, "fil.82");

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(gsMsg.getMessage(req, "fil.13"));
            sb.append("] ");
            String opCode = "";
            String logValue = "";
            if (form.getCmnMode().equals(GSConstFile.CMN_MODE_ADD)) {
                opCode = textEntry;
                logValue = sb.toString() + form.getFil030CabinetName();
            } else if (form.getCmnMode().equals(GSConstFile.CMN_MODE_EDT)) {
                opCode = textEdit;
                StringBuilder sblogValue = new StringBuilder();
                if (cabNames != null && !cabNames.isEmpty()) {
                    String oldCabName = cabNames.get(0);
                    if (!oldCabName.equals(paramMdl.getFil030CabinetName())) {
                        sblogValue.append("[");
                        sblogValue.append(gsMsg.getMessage(req, "fil.fil030kn.6"));
                        sblogValue.append("] ");
                        sblogValue.append(oldCabName);
                        sblogValue.append("\r\n");
                    }
                }
                sblogValue.append(sb.toString());
                sblogValue.append(form.getFil030CabinetName());
                logValue = sblogValue.toString();
            } else if (form.getCmnMode().equals(GSConstFile.CMN_MODE_MLT)) {
                opCode = textEdit;
                StringBuilder sblogValue = new StringBuilder();
                sblogValue.append(textMultiEdit);
                if (cabNames != null && !cabNames.isEmpty()) {
                    sblogValue.append("\r\n");
                    sblogValue.append("[");
                    sblogValue.append(gsMsg.getMessage(req, "cmn.target"));
                    sblogValue.append("]");
                    //listに格納したキャビネット名
                    for (String cabName : cabNames) {
                        sblogValue.append("\r\n ");
                        sblogValue.append(cabName);
                    }
                }
                logValue = sblogValue.toString();
            }
            //ログ出力処理
            FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
            filBiz.outPutLog(opCode,
                    GSConstLog.LEVEL_TRACE, logValue, map.getType());

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
        //完了画面設定
        return __setCompDsp(map, req, form);
    }
    /**
     * <br>[機  能] 遷移元画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map, Fil030knForm form) {

        ActionForward forward = null;
        forward = map.findForward("fil030");
        return forward;
    }
    /**
     * <br>[機  能] 完了画面
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
            Fil030knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        String procMode = NullDefault.getString(form.getCmnMode(), GSConstFile.CMN_MODE_ADD);
        String backDsp = NullDefault.getString(form.getBackDsp(), GSConstFile.MOVE_TO_FIL010);
        String fowardStr = "";

        //キャビネット一覧画面より
        if (backDsp.equals(GSConstFile.MOVE_TO_FIL020)) {
            cmn999Form.addHiddenParam("fil010SelectDelLink", form.getFil010SelectDelLink());
            cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
            fowardStr = "fil020";
        } else if (backDsp.equals(GSConstFile.MOVE_TO_FIL220)) {
            cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
            cmn999Form.addHiddenParam("fil010SelectDelLink", form.getFil010SelectDelLink());
            cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
            cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
            cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
            cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
            cmn999Form.addHiddenParam("fil040SelectDel", form.getFil040SelectDel());
            cmn999Form.addHiddenParam("fil220cabinetKbn", form.getFil220cabinetKbn());
            fowardStr = "fil220";
        } else if (backDsp.equals(GSConstFile.MOVE_TO_FIL280)) {
            cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
            cmn999Form.addHiddenParam("fil010SelectDelLink", form.getFil010SelectDelLink());
            cmn999Form.addHiddenParam("fil010SelectCabinet", form.getFil010SelectCabinet());
            cmn999Form.addHiddenParam("fil010SelectDirSid", form.getFil010SelectDirSid());
            cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
            cmn999Form.addHiddenParam("backDsp", form.getBackDsp());
            cmn999Form.addHiddenParam("fil040SelectDel", form.getFil040SelectDel());
            cmn999Form.addHiddenParam("fil280svKeyword", form.getFil280svKeyword());
            cmn999Form.addHiddenParam("fil280svGroup", form.getFil280svGroup());
            cmn999Form.addHiddenParam("fil280svUser", form.getFil280svUser());
            fowardStr = "fil280";
        } else {
            cmn999Form.addHiddenParam("fil010SelectDelLink", form.getFil010SelectDelLink());
            fowardStr = "fil010";
        }
        cmn999Form.addHiddenParam("filSearchWd", form.getFilSearchWd());
        cmn999Form.addHiddenParam("cmnMode", form.getCmnMode());
        cmn999Form.addHiddenParam("fil010DspCabinetKbn", form.getFil010DspCabinetKbn());

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward(fowardStr);
        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage();
        String textCabinet = gsMsg.getMessage(req, "fil.23");

        //メッセージ
        MessageResources msgRes = getResources(req);
        //登録完了
        if (procMode.equals(GSConstFile.CMN_MODE_ADD)) {
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object", textCabinet));
            //草稿保存完了
        } else if (procMode.equals(GSConstFile.CMN_MODE_EDT)) {
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object", textCabinet));
            //一括編集完了
        } else if (procMode.equals(GSConstFile.CMN_MODE_MLT)) {
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object", textCabinet));
        }

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>編集権限が無い場合のエラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doNonePowerError(ActionMapping map, Fil030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        GsMessage gsMsg = new GsMessage();
        String textTourokuHenkou = gsMsg.getMessage(req, "fil.91");
        String textCabinetSakusei = gsMsg.getMessage(req, "fil.64");

        //キャビネット登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("fil010");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.edit.power.user",
                textCabinetSakusei, textTourokuHenkou));

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] tempディレクトリの画像を読み込む
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
    private ActionForward __doGetImageFile(ActionMapping map,
            Fil030knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        //imageFileSaveName__の半角数字チェック処理
        if (!ValidateUtil.isNumber(imageFileSaveName__)) {
            return getSubmitErrorPage(map, req);
        }
        //テンポラリディレクトリパス
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstFile.PLUGIN_ID_FILE, TEMP_DIRECTORY_ID);
        String fullPath = IOTools.replaceFileSep(
                tempDir + imageFileSaveName__ + GSConstCommon.ENDSTR_SAVEFILE);

        //ダウンロード
        TempFileUtil.downloadInline(req, res, fullPath, imageFileName__, Encoding.UTF_8);

        return null;
    }
    @Override
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        Fil030knForm thisForm = (Fil030knForm) form;
        if (thisForm.getCmnMode().equals(GSConstFile.CMN_MODE_MLT)) {
            return false;
        }
        return super.canNotAdminAccess(req, form);
    }
}