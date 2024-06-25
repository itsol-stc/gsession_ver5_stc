package jp.groupsession.v2.prj.prj060;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
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
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.model.PrjSmailModel;
import jp.groupsession.v2.prj.model.PrjSmailParamModel;
import jp.groupsession.v2.prj.prj010.Prj010Biz;
import jp.groupsession.v2.prj.prj030.Prj030Biz;
import jp.groupsession.v2.prj.prj050.Prj050Action;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] TODO参照画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj060Action extends AbstractProjectAction {

    /** CMD:修正ボタンクリック */
    public static final String CMD_EDIT_CLICK = "editTodoClick";
    /** CMD:TODO削除ボタンクリック */
    public static final String CMD_DEL_CLICK = "deleteClick";
    /** CMD:TODO削除実行 */
    public static final String CMD_DEL_EXE = "deleteExe";
    /** CMD:戻るボタンクリック */
    public static final String CMD_BACK_CLICK = "backClick060";

    /** CMD:コメントボタンクリック */
    public static final String CMD_COMMENT_CLICK = "commentClick";
    /** CMD:コメント削除ボタンクリック */
    public static final String CMD_COMMENT_DELETE_CLICK = "commentDelClick";
    /** CMD:コメント削除実行 */
    public static final String CMD_COMMENT_DEL_EXE = "commentDeleteExe";

    /** CMD:更新（状態）ボタンクリック */
    public static final String CMD_STATUS_UPDATE_CLICK = "updateClick";
    /** CMD:状態削除ボタンクリック */
    public static final String CMD_STATUS_DELETE_CLICK = "statusDelClick";
    /** CMD:状態削除実行 */
    public static final String CMD_STATUS_DEL_EXE = "statusDeleteExe";

    /** CMD:添付ファイル名クリック */
    public static final String CMD_FILE_DL = "fileDl";
    /** CMD:画面再表示(初期表示以外) */
    public static final String CMD_BACK_REDRAW = "backRedraw";

    /** CMD:実績更新ボタンクリック */
    public static final String CMD_ZISSEKI_CLICK = "zissekiUpdate";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj060Action.class);

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

        if (CMD_FILE_DL.equals(cmd)) {
            log__.debug("添付ファイル名クリック");
            return true;

        }
        return false;
    }

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

        log__.debug("Prj060Action start");
        ActionForward forward = null;

        Prj060Form thisForm = (Prj060Form) form;

        con.setAutoCommit(true);
        //ショートメール、回覧プラグインの使用可否判定
        __canUsePlugin(map, thisForm, req, res, con);

        Prj060Biz biz = new Prj060Biz(con, getRequestModel(req));

        boolean existFlg = false;
        Prj060ParamModel paramMdl = new Prj060ParamModel();
        paramMdl.setParam(thisForm);
        existFlg = biz.isExistTodoData(paramMdl);
        paramMdl.setFormData(thisForm);

        if (!existFlg) {
            return __setTodoDataError(map, thisForm, req, res, con);
        }
        con.setAutoCommit(false);

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        if (CMD_EDIT_CLICK.equals(cmd)) {
            log__.debug("変更ボタンクリック");
            forward = map.findForward(CMD_EDIT_CLICK);

        } else if (CMD_DEL_CLICK.equals(cmd)) {
            log__.debug("削除ボタンクリック");
            forward = __doDeleteConf(map, thisForm, req, res, con);

        } else if (CMD_DEL_EXE.equals(cmd)) {
            log__.debug("削除実行");
            forward = __doDeleteExe(map, thisForm, req, res, con);

        } else if (CMD_BACK_CLICK.equals(cmd)) {
            log__.debug("戻るボタンクリック");
            forward = __doBack(map, thisForm, req, res, con);

        } else if (CMD_COMMENT_CLICK.equals(cmd)) {
            log__.debug("コメントするボタンクリック");
            forward = __doAddCmt(map, thisForm, req, res, con);

        } else if (CMD_STATUS_UPDATE_CLICK.equals(cmd)) {
            log__.debug("更新(状態)ボタンクリック");
            forward = __doAddHis(map, thisForm, req, res, con);

        } else if (CMD_FILE_DL.equals(cmd)) {
            log__.debug("添付ファイル名クリック");
            forward = __doDownLoad(map, thisForm, req, res, con);

        } else if (CMD_COMMENT_DELETE_CLICK.equals(cmd)) {
            log__.debug("コメント削除ボタンクリック");
            forward = __doCmtDeleteConf(map, thisForm, req, res, con);

        } else if (CMD_COMMENT_DEL_EXE.equals(cmd)) {
            log__.debug("コメント削除実行");
            forward = __doCmtDeleteExe(map, thisForm, req, res, con);

        } else if (CMD_STATUS_DELETE_CLICK.equals(cmd)) {
            log__.debug("状態削除ボタンクリック");
            forward = __doCmtDeleteStatus(map, thisForm, req, res, con);

        } else if (CMD_STATUS_DEL_EXE.equals(cmd)) {
            log__.debug("状態削除実行");
            forward = __doStatusDeleteExe(map, thisForm, req, res, con);

        } else if (CMD_BACK_REDRAW.equals(cmd) || Prj050Action.CMD_BACK_CLICK.equals(cmd)) {
            log__.debug("画面再表示(初期表示以外)");
            forward = __doDspSet(map, thisForm, req, res, con);

//        } else if (CMD_ZISSEKI_CLICK.equals(cmd)) {
//            log__.debug("実績更新ボタンクリック");
//            forward = __doUpdateZisseki(map, thisForm, req, res, con);

        } else if (cmd.equals("getImageFile")) {
            log__.debug("画像ダウンロード");
            forward = __doGetImageFile(map, thisForm, req, res, con);
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("Prj060Action end");
        return forward;
    }

    /**
     * <br>[機  能] ショートメール、回覧プラグインが使用可能か判定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __canUsePlugin(ActionMapping map,
                                 Prj060Form form,
                                 HttpServletRequest req,
                                 HttpServletResponse res,
                                 Connection con) throws SQLException {

        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        Prj030Biz biz = new Prj030Biz(con, getRequestModel(req));
        Prj060ParamModel paramMdl = new Prj060ParamModel();
        paramMdl.setParam(form);
        biz.setUsePlugin(paramMdl, pconfig);
        paramMdl.setFormData(form);

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
     * @throws UnsupportedEncodingException URLエンコードに失敗
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    private ActionForward __doInit(ActionMapping map, Prj060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, UnsupportedEncodingException,
                    IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        con.setAutoCommit(true);
        //初期表示情報を画面にセットする
        Prj060Biz biz = new Prj060Biz(con, getRequestModel(req));

        Prj060ParamModel paramMdl = new Prj060ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

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
     * @throws UnsupportedEncodingException URLエンコードに失敗
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    private ActionForward __doDspSet(ActionMapping map, Prj060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, UnsupportedEncodingException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        con.setAutoCommit(true);


        CommonBiz cmnBiz = new CommonBiz();
        BaseUserModel uMdl = getSessionUserModel(req);
        boolean adminUser = cmnBiz.isPluginAdmin(con, uMdl, getPluginId());
        //画面に常に表示する情報を取得する
        Prj060Biz biz = new Prj060Biz(con, getRequestModel(req));

        Prj060ParamModel paramMdl = new Prj060ParamModel();
        paramMdl.setParam(form);
        biz.getDspData(paramMdl, uMdl, adminUser);
        paramMdl.setFormData(form);

        // トランザクショントークン設定
        this.saveToken(req);
        con.setAutoCommit(false);
        return map.getInputForward();
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
     * @return ActionForward
     */
    private ActionForward __doBack(
        ActionMapping map,
        Prj060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) {

        String prj060scrId =
            NullDefault.getString(form.getPrj060scrId(), GSConstProject.SCR_ID_PRJ010);
        Prj060Biz biz = new Prj060Biz(getRequestModel(req));
        if (!StringUtil.isNullZeroString(form.getPrj060schUrl())) {
            //スケジュールへ遷移
            try {
                res.sendRedirect(form.getPrj060schUrl());
            } catch (IOException e) {
                return biz.getActionForward(prj060scrId, map);
            }
            return null;
        }

        return biz.getActionForward(prj060scrId, map);
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
        Prj060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        CommonBiz cmnBiz = new CommonBiz();
        RequestModel reqMdl = getRequestModel(req);

        boolean adminUser = cmnBiz.isPluginAdmin(
                con, reqMdl.getSmodel(), GSConstProject.PLUGIN_ID_PROJECT);

        GsMessage gsMsg = new GsMessage(reqMdl);
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, reqMdl);

        int prjSid = form.getPrj060prjSid();
        int ptdSid = form.getPrj060todoSid();
        Long binSid = form.getBinSid();

        //添付ファイルをダウンロード可能かチェックする
        if (prjBiz.isCheckDLPrjTemp(
                con, prjSid, ptdSid, binSid, getSessionUserSid(req), adminUser)) {

            //バイナリー情報を取得する
            CmnBinfModel cbMdl = cmnBiz.getBinInfo(
                    con, binSid, GroupSession.getResourceManager().getDomain(req));

            if (cbMdl != null) {

                //ログ出力処理
                prjBiz.outPutLog(map, req, res,
                        getInterMessage(req, "cmn.download"),
                        GSConstLog.LEVEL_INFO, cbMdl.getBinFileName(), String.valueOf(binSid));

                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);

                //ファイルをダウンロードする
                TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
            }
        }

        return null;
    }

    /**
     * <br>[機  能] TODO削除ボタンクリック時の処理を行う
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
    private ActionForward __doDeleteConf(
        ActionMapping map,
        Prj060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        // トランザクショントークン設定
        this.saveToken(req);

        //TODO削除確認画面を表示
        return __setTodoKakuninDsp(map, form, req, con);
    }

    /**
     * <br>[機  能] TODO削除処理を行う(削除実行)
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
        Prj060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);


        Prj060Biz biz = new Prj060Biz(con, getRequestModel(req));
        //TODOのタイトルを取得する
        String title = biz.getTargetName(con, form.getPrj060todoSid());

        //TODOを削除する
        Prj060ParamModel paramMdl = new Prj060ParamModel();
        paramMdl.setParam(form);
        biz.deleteTodo(paramMdl, userSid);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(req);

        //ログ出力処理
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        String opCode = getInterMessage(req, "cmn.delete");

        prjBiz.outPutLog(
                map, req, res, opCode,
                GSConstLog.LEVEL_TRACE,
                "[" + getInterMessage(req, "cmn.title") + "]" + title);


        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * <br>[機  能] コメント削除ボタンクリック時の処理を行う
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
    private ActionForward __doCmtDeleteConf(
        ActionMapping map,
        Prj060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        // トランザクショントークン設定
        this.saveToken(req);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
    }

    /**
     * <br>[機  能] コメント削除処理を行う(削除実行)
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
    private ActionForward __doCmtDeleteExe(
        ActionMapping map,
        Prj060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //TODOコメント情報を削除する
        Prj060Biz biz = new Prj060Biz(con, getRequestModel(req));

        Prj060ParamModel paramMdl = new Prj060ParamModel();
        paramMdl.setParam(form);
        biz.deleteCmt(paramMdl);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(req);

        //ログ出力処理
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        prjBiz.outPutLog(map, req, res,
                getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_TRACE,
                getInterMessage(req, "project.delete.comment"));

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] TODO削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setTodoKakuninDsp(
        ActionMapping map,
        Prj060Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

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

        GsMessage gsMsg = new GsMessage(req);

        //TODOに紐付くデータがあるかチェック
        String[] todoSid = new String[] {String.valueOf(form.getPrj060todoSid())};
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        if (prjBiz.checkDataExist(todoSid)) {
            //TODOに紐付くデータがある
            String todoTitle = new String();
            todoTitle = form.getPrj060TodoTitle().replace("<wbr/>", "");
            msg = msgRes.getMessage("sakujo.kakunin.list",
                                    GSConstProject.MSG_TODO,
                                    StringUtilHtml.transToHTml(todoTitle));
        } else {
            //TODO情報なし
            msg = msgRes.getMessage("sakujo.kakunin.once", GSConstProject.MSG_TODO);
        }
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
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
        Prj060Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {
        GsMessage gsMsg = new GsMessage();
        //状態変更履歴
        String textStatusHistory = gsMsg.getMessage(req, "project.src.41");
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

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);
        MessageResources msgRes = getResources(req);

        String msg = "";
        String urlOk = "";
        if (CMD_COMMENT_DELETE_CLICK.equals(cmd)) {
            //コメント
            String textComment = gsMsg.getMessage(req, "cmn.comment");
            //TODOコメント削除確認
            msg = msgRes.getMessage("sakujo.kakunin.once", textComment);
            urlOk = forwardOk.getPath() + "?" + GSConst.P_CMD + "=" + CMD_COMMENT_DEL_EXE;

        } else if (CMD_STATUS_DELETE_CLICK.equals(cmd)) {
            //状態削除確認
            msg = msgRes.getMessage("sakujo.kakunin.once", textStatusHistory);
            urlOk = forwardOk.getPath() + "?" + GSConst.P_CMD + "=" + CMD_STATUS_DEL_EXE;

        }
        cmn999Form.setMessage(msg);
        cmn999Form.setUrlOK(urlOk);


        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Prj060Form form,
        HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        //状態変更履歴
        String textStatusHistory = gsMsg.getMessage(req, "project.src.41");
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("dashboard");

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);
        MessageResources msgRes = getResources(req);
        //コメント
        String textComment = gsMsg.getMessage(req, "cmn.comment");
        String msg = "";
        Prj060Biz biz = new Prj060Biz(getRequestModel(req));
        if (CMD_DEL_EXE.equals(cmd)) {
            //TODO削除完了
            String prj060scrId =
                NullDefault.getString(form.getPrj060scrId(), GSConstProject.SCR_ID_PRJ010);
            forwardOk = biz.getActionForward(prj060scrId, map);
            msg = msgRes.getMessage("sakujo.kanryo.object", GSConstProject.MSG_TODO);

        } else if (CMD_COMMENT_DEL_EXE.equals(cmd)) {
            //TODOコメント削除完了
            forwardOk = map.findForward("redraw");
            msg = msgRes.getMessage("sakujo.kanryo.object", textComment);

        } else if (CMD_COMMENT_CLICK.equals(cmd)) {
            //TODOコメント登録完了
            forwardOk = map.findForward("redraw");
            msg = msgRes.getMessage("touroku.kanryo.object", textComment);

        } else if (CMD_STATUS_DEL_EXE.equals(cmd)) {
            //状態変更履歴削除完了
            forwardOk = map.findForward("redraw");
            msg = msgRes.getMessage("sakujo.kanryo.object", textStatusHistory);

        } else if (CMD_STATUS_UPDATE_CLICK.equals(cmd)) {
            //状態変更履歴登録完了
            String prj060scrId =
                NullDefault.getString(form.getPrj060scrId(), GSConstProject.SCR_ID_PRJ010);
            forwardOk = biz.getActionForward(prj060scrId, map);
            String textStatus = gsMsg.getMessage(req, "cmn.status");
            msg = msgRes.getMessage("hensyu.henkou.kanryo.object", textStatus);
        }
        if (!StringUtil.isNullZeroStringSpace(form.getPrj060schUrl())) {
            //URLを内部リンクに変更
            String url = StringUtilHtml.transToText(form.getPrj060schUrl());
            if (url.indexOf("/" + GSConst.PLUGINID_SCH) != -1) {
                url = url.substring(url.indexOf("/" + GSConst.PLUGINID_SCH));
            } else if (url.indexOf("/" + GSConst.PLUGINID_MAIN) != -1) {
                url = url.substring(url.indexOf("/" + GSConst.PLUGINID_MAIN));
            }
            cmn999Form.setUrlOK(url);
        } else {
            cmn999Form.setUrlOK(forwardOk.getPath());
        }

        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] コメントするクリック時処理
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
     * @throws Exception ショートメール送信時に例外発生
     * @throws UnsupportedEncodingException URLエンコードに失敗
     */
    private ActionForward __doAddCmt(ActionMapping map,
                                      Prj060Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws SQLException, IOToolsException, UnsupportedEncodingException, Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ActionErrors errors = form.validate060Cmt(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDspSet(map, form, req, res, con);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        //TODOコメント情報を登録する
        Prj060Biz biz = new Prj060Biz(con, getRequestModel(req));

        Prj060ParamModel paramMdl = new Prj060ParamModel();
        paramMdl.setParam(form);
        biz.doAddCmt(paramMdl, cntCon, userSid);
        paramMdl.setFormData(form);

        if (form.isUseSmail()
        && form.getPrj060CommentMailSend() != GSConstProject.NOT_SEND) {

            GsMessage gsMsg = new GsMessage(req);

            boolean commit = false;
            con.setAutoCommit(false);
            try {
                PrjSmailParamModel smlParamModel = new PrjSmailParamModel();
                smlParamModel.setCmdMode(form.getPrj050cmdMode());
                smlParamModel.setPrjSid(form.getPrj060prjSid());
                smlParamModel.setTodoSid(form.getPrj060todoSid());
                smlParamModel.setUsrSid(userSid);
                smlParamModel.setTarget(form.getPrj060CommentMailSend());
                smlParamModel.setHistory(form.getPrj060comment());
                smlParamModel.setAppRoot(getAppRootPath());
                smlParamModel.setTempDir(getTempPath(req));

                PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));

                PrjSmailModel param = prjBiz.getSmailParamMdl(smlParamModel);
                param.setNaiyo(form.getPrj060comment());
                prjBiz.sendTodoCommentMail(
                        con, cntCon, param, getAppRootPath(), getPluginConfig(req));

                con.commit();
                commit = true;
            } catch (Exception e) {
                log__.error("TODOコメント登録時のショートメール送信に失敗", e);
                throw e;
            } finally {
                if (!commit) {
                    JDBCUtil.rollback(con);
                }
            }
        }

        //コメントクリア
        form.setPrj060comment("");
        GsMessage gsMsg = new GsMessage(req);
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        prjBiz.outPutLog(map, req, res,
                getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_TRACE,
                getInterMessage(req, "project.add.comment"));

        return __doDspSet(map, form, req, res, con);
    }

//    /**
//     * <br>[機  能] 実績更新ボタンクリック時処理
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param map アクションマッピング
//     * @param form アクションフォーム
//     * @param req リクエスト
//     * @param res レスポンス
//     * @param con コネクション
//     * @return ActionForward
//     * @throws SQLException SQL実行例外
//     * @throws IOToolsException IOエラー
//     */
//    private ActionForward __doUpdateZisseki(
//        ActionMapping map,
//        Prj060Form form,
//        HttpServletRequest req,
//        HttpServletResponse res,
//        Connection con) throws SQLException, IOToolsException {
//
//        if (!isTokenValid(req, true)) {
//            log__.info("２重投稿");
//            return getSubmitErrorPage(map, req);
//        }
//
//        ActionErrors errors = form.validate060Zisseki();
//        if (!errors.isEmpty()) {
//            addErrors(req, errors);
//            return __doDspSet(map, form, req, res, con);
//        }
//
//        //ログインユーザSIDを取得
//        int userSid = 0;
//        BaseUserModel buMdl = getSessionUserModel(req);
//        if (buMdl != null) {
//            userSid = buMdl.getUsrsid();
//        }
//
//        //実績情報を更新する
//        Prj060Biz biz = new Prj060Biz(con);
//        biz.doUpdateZisseki(form, userSid);
//
//        return __doDspSet(map, form, req, res, con);
//    }

    /**
     * <br>[機  能] 更新(状態)クリック時処理
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
     * @throws IOException IOエラー
     * @throws Exception その他例外
     */
    private ActionForward __doAddHis(ActionMapping map,
                                      Prj060Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws SQLException, IOToolsException, IOException, Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ActionErrors errors = form.validate060Status(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDspSet(map, form, req, res, con);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        GsMessage gsMsg = new GsMessage(req);

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));

        boolean commitFlg = false;

        try {

            con.setAutoCommit(false);

            //状態変更履歴情報を登録する
            Prj060Biz biz = new Prj060Biz(con, getRequestModel(req));

            Prj060ParamModel paramMdl = new Prj060ParamModel();
            paramMdl.setParam(form);
            biz.doAddHis(paramMdl, cntCon, userSid);
            paramMdl.setFormData(form);

            //状態 = 100%時
            if (form.getPrj060status() == GSConstProject.STATUS_100) {
                //実績情報を更新する
                paramMdl = new Prj060ParamModel();
                paramMdl.setParam(form);
                biz.doUpdateZisseki(paramMdl, userSid);
                paramMdl.setFormData(form);

            }

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {

                con.commit();

                if (form.isUseSmail()
                        && form.getPrj060MailSend() != GSConstProject.NOT_SEND) {

                    PrjSmailParamModel smlParamModel = new PrjSmailParamModel();
                    smlParamModel.setCmdMode(form.getPrj050cmdMode());
                    smlParamModel.setPrjSid(form.getPrj060prjSid());
                    smlParamModel.setTodoSid(form.getPrj060todoSid());
                    smlParamModel.setUsrSid(userSid);
                    smlParamModel.setTarget(form.getPrj060MailSend());
                    smlParamModel.setHistory(form.getPrj060riyu());
                    smlParamModel.setAppRoot(getAppRootPath());
                    smlParamModel.setTempDir(getTempPath(req));
                    PrjSmailModel param = prjBiz.getSmailParamMdl(smlParamModel);

                    prjBiz.sendTodoEditMail(
                            con, cntCon, param, getAppRootPath(), getPluginConfig(req));
                }

                //状態変更理由クリア
                form.setPrj060riyu("");

            } else {
                JDBCUtil.rollback(con);
            }
        }
        prjBiz.outPutLog(map, req, res,
                getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_TRACE,
                getInterMessage(req, "project.update.status.history"));
        return __setKanryoDsp(map, form, req);
    }

    /**
     * <br>[機  能] 状態削除ボタンクリック時の処理を行う
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
    private ActionForward __doCmtDeleteStatus(
        ActionMapping map,
        Prj060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        // トランザクショントークン設定
        this.saveToken(req);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
    }

    /**
     * <br>[機  能] 状態変更履歴削除処理を行う(削除実行)
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
    private ActionForward __doStatusDeleteExe(
        ActionMapping map,
        Prj060Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //状態変更履歴情報を削除する
        Prj060Biz biz = new Prj060Biz(con, getRequestModel(req));

        Prj060ParamModel paramMdl = new Prj060ParamModel();
        paramMdl.setParam(form);
        biz.deleteStatus(paramMdl);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(req);
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        prjBiz.outPutLog(map, req, res,
                getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_TRACE,
                getInterMessage(req, "project.update.status.history.del"));
        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * <br>[機  能] TODOデータが存在しない場合のエラー画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setTodoDataError(ActionMapping map,
                                              Prj060Form form,
                                              HttpServletRequest req,
                                              HttpServletResponse res,
                                              Connection con)
        throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = map.findForward("prjIndex");

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        cmn999Form.setMessage(msgRes.getMessage("error.nothing.tododata"));
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
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
            Prj060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;
        //画像バイナリSIDとプロジェクトSIDの照合チェック
        Prj010Biz prj010Biz = new Prj010Biz(con, getRequestModel(req));
        boolean icoHnt = prj010Biz.cheIcoHnt(form.getPrj060prjSid(), form.getPrj010PrjBinSid());

        if (!icoHnt) {
            return null;

        } else {
            cbMdl = cmnBiz.getBinInfo(con, form.getPrj010PrjBinSid(),
                    GroupSession.getResourceManager().getDomain(req));
        }

        if (cbMdl != null) {
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                    Encoding.UTF_8);
        }
        return null;
    }
}