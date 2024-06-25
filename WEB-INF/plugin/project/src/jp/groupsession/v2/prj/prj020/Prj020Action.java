package jp.groupsession.v2.prj.prj020;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.prj030.Prj030Biz;
import jp.groupsession.v2.prj.prj150.model.Prj150DspMdl;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj020Action extends AbstractProjectAction {

    /** CMD:テンプレート使用ボタンクリック */
    public static final String CMD_USE_TEMPLATE = "selectTemlate";
    /** CMD:OKボタンクリック */
    public static final String CMD_OK_CLICK = "okClick";
    /** CMD:削除ボタンクリック */
    public static final String CMD_DEL_CLICK = "deleteClick";
    /** CMD:削除実行 */
    public static final String CMD_DEL_EXE = "deleteExe";

    /** CMD:戻るボタンクリック */
    public static final String CMD_BACK_CLICK = "backClick020";
    /** CMD:画面再表示(初期表示以外) */
    public static final String CMD_BACK_REDRAW = "backRedraw";
    /** CMD:設定画面で設定ボタンクリック */
    public static final String CMD_EDIT_CLICK = "editClick";

    /** CMD:所属メンバー追加ボタンクリック */
    public static final String CMD_MEMBER_ADD_CLICK = "memberAdd";
    /** CMD:所属メンバー削除ボタンクリック */
    public static final String CMD_MEMBER_REMOVE_CLICK = "memberRemove";
    /** CMD:プロジェクト管理者追加ボタンクリック */
    public static final String CMD_ADMIN_ADD_CLICK = "adminAdd";
    /** CMD:プロジェクト管理者削除ボタンクリック */
    public static final String CMD_ADMIN_REMOVE_CLICK = "adminRemove";
    /** CMD:コピー元名称クリック */
    public static final String CMD_COPY_CLICK = "copyClick";

    /** CMD:プロジェクト状態拡張ボタンクリック */
    public static final String CMD_PRJ_STATUS_MODIFY_CLICK = "prjStatusModify";
    /** CMD:カテゴリ拡張ボタンクリック */
    public static final String CMD_CATEGORI_MODIFY_CLICK = "categoriModify";
    /** CMD:状態拡張ボタンクリック */
    public static final String CMD_STATUS_MODIFY_CLICK = "statusModify";

    /** CMD:プロジェクトメンバ設定ボタンクリック */
    public static final String CMD_MEMBER_EDIT = "memberEdit";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj020Action.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "prj020";

    /** アイコン画像名 */
    public String imageFileName__ = "";
    /** アイコン画像保存名 */
    public String imageFileSaveName__ = "";

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

        log__.debug("start");
        ActionForward forward = null;

        Prj020Form thisForm = (Prj020Form) form;

        //存在チェック
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, getRequestModel(req));
        if (thisForm.getPrj020cmdMode().equals(GSConstProject.CMD_MODE_EDIT)
                && !prjBiz.getPrjEditKengen(thisForm.getPrj020prjSid(), getSessionUserModel(req))) {
                return __doTransitionErrorPage(map, thisForm, req);
        }

        //登録・編集権限チェック
        GsMessage gsMsg = new GsMessage(req);
        PrjCommonBiz pcBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        if (!pcBiz.getProjectKengen(thisForm.getPrj020cmdMode(),
                                    thisForm.getPrj020prjSid(),
                                    getSessionUserModel(req))) {
            //権限なし
            return setPrjKengenError(map, req, thisForm.getPrj020cmdMode());
        }

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);
        log__.debug("prj020Cmd = " + cmd);
        if (CMD_OK_CLICK.equals(cmd)) {
            log__.debug("OKボタンクリック");
            forward = __doPushEntry(map, thisForm, req, res, con);

        } else if (CMD_DEL_CLICK.equals(cmd)) {
            log__.debug("削除ボタンクリック");
            forward = __doDeleteConf(map, thisForm, req, res, con);

        } else if (CMD_DEL_EXE.equals(cmd)) {
            log__.debug("削除実行");
            forward = __doDeleteExe(map, thisForm, req, res, con);

        } else if (CMD_BACK_CLICK.equals(cmd)) {
            log__.debug("戻るボタンクリック");
            forward = __doBack(map, thisForm, req, res, con);

        } else if (CMD_PRJ_STATUS_MODIFY_CLICK.equals(cmd)) {
            log__.debug("プロジェクト状態拡張ボタンクリック");
            forward = map.findForward(CMD_PRJ_STATUS_MODIFY_CLICK);

        } else if (CMD_CATEGORI_MODIFY_CLICK.equals(cmd)) {
            log__.debug("カテゴリ拡張ボタンクリック");
            forward = map.findForward(CMD_CATEGORI_MODIFY_CLICK);

        } else if (CMD_STATUS_MODIFY_CLICK.equals(cmd)) {
            log__.debug("状態拡張ボタンクリック");
            forward = map.findForward(CMD_STATUS_MODIFY_CLICK);

        } else if (CMD_COPY_CLICK.equals(cmd)) {
            log__.debug("コピー元名称クリック");
            forward = __doCopyClick(map, thisForm, req, res, con);

        } else if (CMD_BACK_REDRAW.equals(cmd) || CMD_EDIT_CLICK.equals(cmd)) {
            log__.debug("画面再表示(初期表示以外)");
            forward = __doRedraw(map, thisForm, req, res, con);

        } else if (CMD_USE_TEMPLATE.equals(cmd)) {
            log__.debug("テンプレート使用");
            forward = map.findForward(CMD_USE_TEMPLATE);

        } else if (CMD_MEMBER_EDIT.equals(cmd)) {
            log__.debug("プロジェクトメンバー設定ボタンクリック");
            forward = map.findForward(CMD_MEMBER_EDIT);
        } else if (cmd.equals("deleteCompany")) {
            //会社削除
            log__.debug("会社削除");
            forward = __doDeleteCompany(
                    map, thisForm, req,
                    res, con, getSessionUserModel(req).getUsrsid());
        } else if (cmd.equals("getImageFile")) {
            //画像ダウンロード
            log__.debug("画像ダウンロード");
            forward = __doGetImageFile(map, thisForm, req, res, con);
        } else if (cmd.equals("prj020tempdeleteMark")) {
            //添付削除
            log__.debug("添付削除");
            forward = __doTempDeleteMark(map, thisForm, req, res, con);
        } else {
            if (thisForm.isPrj020initFlg()) {
                log__.debug("初期表示");
                forward = __doInit(map, thisForm, req, res, con);
            } else {
                log__.debug("再表示");
                forward = __doRedraw(map, thisForm, req, res, con);
            }
        }

        log__.debug("end");
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
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException バイナリファイル操作時例外
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doInit(
            ActionMapping map, Prj020Form form, HttpServletRequest req,
            HttpServletResponse res, Connection con)
                    throws SQLException, IOToolsException, TempFileException, IOException,
                    IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        //アイコン画像の初期化
        imageFileName__ = "";
        imageFileSaveName__ = "";

        con.setAutoCommit(true);
        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        //テンポラリディレクトリの削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);
        temp.createTempDir(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID,
                GSConstProject.TEMP_STATUS_PRJ);
        temp.createTempDir(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID,
                GSConstProject.TEMP_ICN_PRJ);

        //アプリケーションのルートパス
        String appRootPath = getAppRootPath();

        //初期表示情報を画面にセットする
        Prj020Biz biz = new Prj020Biz(getRequestModel(req), con);

        Prj020ParamModel paramMdl = new Prj020ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, userSid, getTempPath(req), appRootPath,
                GroupSession.getResourceManager().getDomain(req),
                NullDefault.getString(req.getParameter("CMD"), ""));
        paramMdl.setFormData(form);

        //画像ファイルを設定
        if (!NullDefault.getString(form.getPrj020IcoName(), "").equals("")
                && !NullDefault.getString(form.getPrj020IcoSaveName(), "").equals("")) {
            imageFileName__ = form.getPrj020IcoName();
            imageFileSaveName__ = form.getPrj020IcoSaveName();
        }

        return __doDspSet(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws IOToolsException IOエラー
     */
    private ActionForward __doDspSet(
        ActionMapping map,
        Prj020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException {

        //テンポラリディレクトリ確認
        if (!PrjCommonBiz.existStatusFile(GSConstProject.MODE_TMP_OTHER, getRequestModel(req))) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(true);
        //プラグイン設定を取得する
        PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con);

        //画面に常に表示する情報を取得する
        Prj020Biz biz = new Prj020Biz(getRequestModel(req), con);

        Prj020ParamModel paramMdl = new Prj020ParamModel();
        paramMdl.setParam(form);
        biz.getDspData(paramMdl, pconfig);
        paramMdl.setFormData(form);


        //外部タグで設定したアドレスメンバーを表示する
        int addIdNullHntFlg = 0;

        //処理モード
        String cmdMode = form.getPrj020cmdMode();
        //アドレスIDがNullでないとき、フラグを立てる
        if (form.getPrj150AddressIdSv() != null && form.getPrj150AddressIdSv().length != 0) {
            addIdNullHntFlg = 1;
        }

        //新規登録
        if (cmdMode.equals(GSConstProject.CMD_MODE_ADD)) {
            if (addIdNullHntFlg == 1) {
                paramMdl = new Prj020ParamModel();
                paramMdl.setParam(form);
                biz.getCompanyDataSv(paramMdl, getSessionUserModel(req).getUsrsid());
                paramMdl.setFormData(form);
            } else {
                paramMdl = new Prj020ParamModel();
                paramMdl.setParam(form);
                biz.getCompanyData(paramMdl,
                        getSessionUserModel(req).getUsrsid(), form.getPrj020prjSid());
                paramMdl.setFormData(form);
            }

        //編集登録
        } else {
            if (addIdNullHntFlg != 1) {
                form.setPrj020AddMemAllDelFlg(1);
            } else {
                paramMdl = new Prj020ParamModel();
                paramMdl.setParam(form);
                biz.getCompanyDataSv(paramMdl, getSessionUserModel(req).getUsrsid());
                paramMdl.setFormData(form);
            }
        }

        // トランザクショントークン設定
        this.saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doPushEntry(
        ActionMapping map,
        Prj020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //テンポラリディレクトリ確認
        if (!PrjCommonBiz.existStatusFile(GSConstProject.MODE_TMP_OTHER, getRequestModel(req))) {
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validate020(con, getSessionUserModel(req), req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDspSet(map, form, req, res, con);
        }

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //アプリケーションのルートパス
        String appRoot = getAppRootPath();

        //登録、または更新処理を行う
        Prj020Biz biz = new Prj020Biz(getRequestModel(req), con);

        Prj020ParamModel paramMdl = new Prj020ParamModel();
        paramMdl.setParam(form);
        biz.doAddEdit(paramMdl, cntCon, userSid, getTempPath(req), appRoot);
        paramMdl.setFormData(form);


        //テンポラリディレクトリの削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);

        GsMessage gsMsg = new GsMessage(req);

        //ログ出力処理
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        String opCode = "";

        //処理モード
        String cmdMode = form.getPrj020cmdMode();
        if (cmdMode.equals(GSConstProject.CMD_MODE_ADD)) {
            //登録
            opCode = getInterMessage(req, "cmn.entry");

        } else if (cmdMode.equals(GSConstProject.CMD_MODE_EDIT)) {
            //更新
            opCode = getInterMessage(req, "cmn.change");
        }

        prjBiz.outPutLog(
                map, req, res, opCode,
                GSConstLog.LEVEL_TRACE,
                "[name]" + form.getPrj020prjName());

        //登録・更新完了画面を表示
        return __setTourokuKanryoDsp(map, form, req);
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @return ActionForward
     */
    private ActionForward __doDeleteConf(
        ActionMapping map,
        Prj020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException {

        ActionErrors errors = form.validateDelete020(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDspSet(map, form, req, res, con);
        }

        // トランザクショントークン設定
        //this.saveToken(req);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
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
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    private ActionForward __doDeleteExe(
        ActionMapping map,
        Prj020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //プロジェクトを削除する
        Prj020Biz biz = new Prj020Biz(getRequestModel(req), con);

        Prj020ParamModel paramMdl = new Prj020ParamModel();
        paramMdl.setParam(form);
        biz.deleteProject(paramMdl, userSid);
        paramMdl.setFormData(form);

        //オブジェクトファイルを削除する
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);

        //ログ出力処理
        GsMessage gsMsg = new GsMessage(req);
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        String opCode = getInterMessage(req, "cmn.delete");

        prjBiz.outPutLog(
                map, req, res, opCode,
                GSConstLog.LEVEL_TRACE,
                "[name]" + form.getPrj020prjName());


        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Prj020Form form,
        HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        //プロジェクト
        String textProject = gsMsg.getMessage(req, "cmn.project");

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        Prj020ParamModel paramMdl = new Prj020ParamModel();
        paramMdl.setParam(form);
        ActionForward forwardOk = setBackForward(map, paramMdl, false, req);
        paramMdl.setFormData(form);

        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", textProject));

        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 登録・更新完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setTourokuKanryoDsp(
        ActionMapping map,
        Prj020Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        Prj020ParamModel paramMdl = new Prj020ParamModel();
        paramMdl.setParam(form);
        ActionForward forwardOk = setBackForward(map, paramMdl, true, req);
        paramMdl.setFormData(form);


        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        //プロジェクト
        String textProject = gsMsg.getMessage(req, "cmn.project");
        String cmdMode = form.getPrj020cmdMode();
        if (cmdMode.equals(GSConstProject.CMD_MODE_ADD)) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object", textProject));
        } else if (cmdMode.equals(GSConstProject.CMD_MODE_EDIT)) {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object", textProject));
        }

        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 直前に表示していた画面へ遷移する(ActionForwardを返す)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param paramMdl Prj020ParamModel
     * @param dataexist 画面遷移時、対象データがあるか(削除済でないか)
     *                   true=データが存在する、false=存在しない
     * @param req リクエスト
     * @return ActionForward
     */
    public ActionForward setBackForward(
        ActionMapping map,
        Prj020ParamModel paramMdl,
        boolean dataexist,
        HttpServletRequest req) {

        ActionForward forward = null;
        String prj020scrId =
            NullDefault.getString(paramMdl.getPrj020scrId(), GSConstProject.SCR_ID_PRJ010);

        if (prj020scrId.equals(GSConstProject.SCR_ID_PRJ010)) {
            //ダッシュボードへ遷移する
            forward = map.findForward(GSConstProject.SCR_INDEX);

        } else if (prj020scrId.equals(GSConstProject.SCR_ID_PRJ040)) {
            //プロジェクト詳細検索へ遷移する
            forward = map.findForward(GSConstProject.SCR_PRJ_SEARCH);

        } else if (prj020scrId.equals(GSConstProject.SCR_ID_PRJ030)) {

            if (dataexist) {
                //データあり
                //プロジェクトメインへ遷移する
                forward = map.findForward(GSConstProject.SCR_PRJ_MAIN);

            } else {
                //データ削除済みの場合、さらに前の画面へ遷移する
                String prj030scrId =
                    NullDefault.getString(paramMdl.getPrj030scrId(), GSConstProject.SCR_ID_PRJ010);
                Prj030Biz biz030 = new Prj030Biz(getRequestModel(req));
                forward = biz030.getActionForward(prj030scrId, map);
            }
        }
        return forward;
    }

    /**
     * <br>[機  能] 戻るボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws IOToolsException IOエラー
     */
    private ActionForward __doBack(
        ActionMapping map,
        Prj020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, IOToolsException {

        //オブジェクトファイルを削除する
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);

        ActionForward forward = null;

        Prj020ParamModel paramMdl = new Prj020ParamModel();
        paramMdl.setParam(form);
        forward = setBackForward(map, paramMdl, true, req);
        paramMdl.setFormData(form);

        return forward;
    }

    /**
     * [機  能] 削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Prj020Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {
        GsMessage gsMsg = new GsMessage();
        //プロジェクトに紐付くTODO情報も同時に削除されます。
        String textDeleteMessage = gsMsg.getMessage(req, "project.src.87");
        con.setAutoCommit(true);
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("redraw");
        cmn999Form.setUrlCancel(
                forwardCancel.getPath() + "?" + GSConst.P_CMD + "=" + CMD_BACK_REDRAW);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=" + CMD_DEL_EXE);

        //メッセージ
        MessageResources msgRes = getResources(req);
        String msg = "";
        //プロジェクト
        String textProject = gsMsg.getMessage(req, "cmn.project");
        //プロジェクトに紐付くTODO情報があるかチェック
        Prj020Biz biz = new Prj020Biz(getRequestModel(req), con);

        boolean todoExsitFlg = false;

        Prj020ParamModel paramMdl = new Prj020ParamModel();
        paramMdl.setParam(form);
        todoExsitFlg = biz.checkTodoExist(paramMdl);
        paramMdl.setFormData(form);

        if (todoExsitFlg) {
            //プロジェクトに紐付くTODO情報がある
            msg = msgRes.getMessage("sakujo.kakunin.list",
                    textProject,
                                    textDeleteMessage);
        } else {
            //TODO情報なし
            msg = msgRes.getMessage("sakujo.kakunin.once", textProject);
        }
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] コピー元名称クリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doCopyClick(
            ActionMapping map, Prj020Form form, HttpServletRequest req,
            HttpServletResponse res, Connection con)
                    throws SQLException, IOToolsException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //コピー元のプロジェクト情報を画面にセットする
        Prj020Biz biz = new Prj020Biz(getRequestModel(req), con);

        Prj020ParamModel paramMdl = new Prj020ParamModel();
        paramMdl.setParam(form);
        biz.setCopyData(paramMdl, userSid, getTempPath(req));
        paramMdl.setFormData(form);

        return __doDspSet(map, form, req, res, con);
    }

    /**
     * <br>[機  能] アドレスメンバを削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param userSid ユーザSID
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doDeleteCompany(ActionMapping map,
                                            Prj020Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con,
                                            int userSid)
                                            throws Exception {


            //会社情報を設定
        con.setAutoCommit(true);
        Prj020Biz biz = new Prj020Biz(getRequestModel(req), con);
        Prj020ParamModel paramMdl = new Prj020ParamModel();
        paramMdl.setParam(form);
        biz.getCompanyDataSv(paramMdl, userSid);
        paramMdl.setFormData(form);

        List<Prj150DspMdl> companyList = form.getPrj020AddDataList();
        if (companyList != null && !companyList.isEmpty()) {
            List<String> newAddressIdList = new ArrayList<String>();

            for (Prj150DspMdl companyData : companyList) {
                //ユーザを削除
                if (form.getPrj020UsrDel() != companyData.getAdrSid()) {
                    newAddressIdList.add(String.valueOf(companyData.getAdrSid()));
                }
            }

            form.setPrj150AddressIdSv(
                 newAddressIdList.toArray(new String[newAddressIdList.size()]));
        }
        //初期表示処理
        return __doDspSet(map, form, req, res, con);

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
            Prj020Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        //imageFileSaveNameの半角数字チェック処理
        if (!ValidateUtil.isNumber(imageFileSaveName__)) {
            return getSubmitErrorPage(map, req);
        }

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String filePath = temp.getTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID,
                GSConstProject.TEMP_ICN_PRJ);

        String fullPath = IOTools.replaceFileSep(
                filePath + imageFileSaveName__ + GSConstCommon.ENDSTR_SAVEFILE);
        TempFileUtil.downloadInline(req, res, fullPath, imageFileName__, Encoding.UTF_8);

        return null;
    }

    /**
     * <br>[機  能] アイコン削除ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return ActionForward
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doTempDeleteMark(ActionMapping map, Prj020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IOToolsException, IOException,
                    TempFileException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        //テンポラリディレクトリのファイル削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.clearTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID,
                GSConstProject.TEMP_ICN_PRJ);

        imageFileName__ = "";
        imageFileSaveName__ = "";
        form.setPrj020IcoName(imageFileName__);
        form.setPrj020IcoSaveName(imageFileSaveName__);

        return __doRedraw(map, form, req, res, con);
    }
    /**
     * <br>[機  能] 再描画処理
     *
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ActionForward __doRedraw(ActionMapping map, Prj020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IOToolsException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        GsMessage gsMsg = new GsMessage(req);
        DateTimePickerBiz picker = new DateTimePickerBiz();
        String strDateNameJp = gsMsg.getMessage("cmn.start");
        String endDateNameJp = gsMsg.getMessage("cmn.end");

        picker.setDateParam(
                form,
                "prj020startDate",
                "prj020startYear",
                "prj020startMonth",
                "prj020startDay",
                strDateNameJp);
        picker.setDateParam(
                form,
                "prj020endDate",
                "prj020endYear",
                "prj020endMonth",
                "prj020endDay",
                endDateNameJp);

        if (form.getPrj020hdnMember() != null
                && form.getPrj020hdnMember().length > 0) {
            String[] memberSid = new String[form.getPrj020hdnMember().length];
            int index = 0;
            for (String sid : form.getPrj020hdnMember()) {
                memberSid[index] = sid.split(GSConst.GSESSION2_ID)[0];
                index++;
            }
            form.setPrj020hdnMemberSid(memberSid);
        }

        //メンバーに存在しないユーザは管理者から削除
        Map<String, String> sidMap = new HashMap<String, String>();
        if (form.getPrj020hdnMember() != null
                          && form.getPrj020hdnMember().length > 0) {
            for (String hdn : form.getPrj020hdnMember()) {
                String[] splitStr = hdn.split(GSConst.GSESSION2_ID);
                String keyValue = String.valueOf(splitStr[0]);
                sidMap.put(keyValue, keyValue);
            }
        }
        List<String> admList = new ArrayList<String>();
        if (form.getPrj020hdnAdmin() != null
                && form.getPrj020hdnAdmin().length > 0) {
            for (String hdn : form.getPrj020hdnAdmin()) {
                if (sidMap.get(hdn) != null) {
                    admList.add(hdn);
                }
            }
            form.setPrj020hdnAdmin(admList.toArray(new String[admList.size()]));
        }

        //画像ファイルを設定
        if (!NullDefault.getString(form.getPrj020IcoName(), "").equals("")
                && !NullDefault.getString(form.getPrj020IcoSaveName(), "").equals("")) {
            imageFileName__ = form.getPrj020IcoName();
            imageFileSaveName__ = form.getPrj020IcoSaveName();
        }

        return __doDspSet(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 存在チェックエラー画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doTransitionErrorPage(
        ActionMapping map,
        Prj020Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        MessageResources msgRes = getResources(req);
        urlForward = map.findForward("prjIndex");

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "error.none.edit.projectdata";
        cmn999Form.setMessage(msgRes.getMessage(msgState));
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}