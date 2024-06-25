package jp.groupsession.v2.prj.prj140;

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

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.prj.AbstractProjectTemplateAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.prj020.Prj020Action;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトテンプレート登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj140Action extends AbstractProjectTemplateAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj140Action.class);
    /** CMD:OKボタンクリック */
    public static final String CMD_OK_CLICK = "okClick";
    /** CMD:削除ボタンクリック */
    public static final String CMD_DEL_CLICK = "deleteClick";
    /** CMD:削除実行 */
    public static final String CMD_DEL_EXE = "deleteExe";

    /** CMD:戻るボタンクリック */
    public static final String CMD_BACK_CLICK = "backFromInputTmp";
    /** CMD:画面再表示(初期表示以外) */
    public static final String CMD_BACK_REDRAW = Prj020Action.CMD_BACK_REDRAW;
    /** CMD:設定画面で設定ボタンクリック */
    public static final String CMD_EDIT_CLICK = "tmpEditClick";

    /** CMD:所属メンバー追加ボタンクリック */
    public static final String CMD_MEMBER_ADD_CLICK = "memberAdd";
    /** CMD:所属メンバー削除ボタンクリック */
    public static final String CMD_MEMBER_REMOVE_CLICK = "memberRemove";
    /** CMD:プロジェクト管理者追加ボタンクリック */
    public static final String CMD_ADMIN_ADD_CLICK = "adminAdd";
    /** CMD:プロジェクト管理者削除ボタンクリック */
    public static final String CMD_ADMIN_REMOVE_CLICK = "adminRemove";

    /** CMD:プロジェクト状態拡張ボタンクリック */
    public static final String CMD_PRJ_STATUS_MODIFY_CLICK = "prjStatusModify";
    /** CMD:カテゴリ拡張ボタンクリック */
    public static final String CMD_CATEGORI_MODIFY_CLICK = "categoriModify";
    /** CMD:状態拡張ボタンクリック */
    public static final String CMD_STATUS_MODIFY_CLICK = "statusModify";
    /** CMD:プロジェクトメンバ設定ボタンクリック */
    public static final String CMD_MEMBER_EDIT = "memberEdit";
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "prj140";

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
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Prj140Form thisForm = (Prj140Form) form;

        forward = _immigration(map, form, req, res, con);
        if (forward != null) {
            return forward;
        }

        PrjCommonBiz prjBiz = new PrjCommonBiz(con, getRequestModel(req));
        int prtKbn = thisForm.getPrjTmpMode();

        if (thisForm.getPrjTmpMode() == GSConstProject.MODE_TMP_KYOYU) {
            prtKbn = GSConstProject.PRT_KBN_KYOYU;
        }
        if (thisForm.getPrjTmpMode() == GSConstProject.MODE_TMP_KOJIN) {
            prtKbn = GSConstProject.PRT_KBN_KOJIN;
        }

        BaseUserModel buMdl = getSessionUserModel(req);
        if (thisForm.getPrtSid() != 0
                && !prjBiz.validateCheckPower(
                        con, thisForm.getPrtSid(), prtKbn, buMdl)) {
            return getSubmitErrorPage(map, req);
        }

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

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

        } else if (CMD_MEMBER_EDIT.equals(cmd)) {
            log__.debug("プロジェクトメンバー設定ボタンクリック");
            forward = map.findForward(CMD_MEMBER_EDIT);

        } else if (CMD_BACK_REDRAW.equals(cmd) || CMD_EDIT_CLICK.equals(cmd)) {
            log__.debug("画面再表示(初期表示以外)");
            forward = __doRedraw(map, thisForm, req, res, con);

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
     *
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
    private ActionForward __doInit(ActionMapping map, Prj140Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IOToolsException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        con.setAutoCommit(true);
        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);
        temp.createTempDir(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID,
                GSConstProject.TEMP_STATUS_PRJ);

        //初期表示情報を画面にセットする
        Prj140Biz biz = new Prj140Biz(con, getRequestModel(req));

        Prj140ParamModel paramMdl = new Prj140ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, userSid, getTempPath(req));
        paramMdl.setFormData(form);

        // トランザクショントークン設定
        this.saveToken(req);

        return __doDspSet(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     * @throws IOToolsException IOエラー
     */
    private ActionForward __doDspSet(ActionMapping map,
                                      Prj140Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws SQLException, IOToolsException {

        con.setAutoCommit(true);
        //テンポラリディレクトリ確認
        if (!PrjCommonBiz.existStatusFile(GSConstProject.MODE_TMP_KOJIN, getRequestModel(req))) {
            return getSubmitErrorPage(map, req);
        }
        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        //画面に常に表示する情報を取得する
        Prj140Biz biz = new Prj140Biz(con, getRequestModel(req));

        Prj140ParamModel paramMdl = new Prj140ParamModel();
        paramMdl.setParam(form);
        biz.getDspData(paramMdl, pconfig, getTempPath(req));
        paramMdl.setFormData(form);

        // トランザクショントークン設定
        this.saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 戻るボタン押下時
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws IOToolsException IOエラー
     */
    private ActionForward __doBack(ActionMapping map,
                                    Prj140Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws IOToolsException {

        //オブジェクトファイルを削除する
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);

        return map.findForward("backTmpList");
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
    private ActionForward __doRedraw(ActionMapping map, Prj140Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IOToolsException, IllegalAccessException,
                    InvocationTargetException, NoSuchMethodException {

        GsMessage gsMsg = new GsMessage(req);
        DateTimePickerBiz picker = new DateTimePickerBiz();
        String strDateNameJp = gsMsg.getMessage("cmn.start");
        String endDateNameJp = gsMsg.getMessage("cmn.end");

        picker.setDateParam(
                form,
                "prj140startDate",
                "prj140startYear",
                "prj140startMonth",
                "prj140startDay",
                strDateNameJp);
        picker.setDateParam(
                form,
                "prj140endDate",
                "prj140endYear",
                "prj140endMonth",
                "prj140endDay",
                endDateNameJp);

        if (form.getPrj140hdnMember() != null
                && form.getPrj140hdnMember().length > 0) {
            String[] memberSid = new String[form.getPrj140hdnMember().length];
            int index = 0;
            for (String sid : form.getPrj140hdnMember()) {
                memberSid[index] = sid.split(GSConst.GSESSION2_ID)[0];
                index++;
            }
            form.setPrj140hdnMemberSid(memberSid);
        }

        //メンバーに存在しないユーザは管理者から削除
        Map<String, String> sidMap = new HashMap<String, String>();
        if (form.getPrj140hdnMember() != null
                          && form.getPrj140hdnMember().length > 0) {
            for (String hdn : form.getPrj140hdnMember()) {
                String[] splitStr = hdn.split(GSConst.GSESSION2_ID);
                String keyValue = String.valueOf(splitStr[0]);
                sidMap.put(keyValue, keyValue);
            }
        }
        List<String> admList = new ArrayList<String>();
        if (form.getPrj140hdnAdmin() != null
                && form.getPrj140hdnAdmin().length > 0) {
            for (String hdn : form.getPrj140hdnAdmin()) {
                if (sidMap.get(hdn) != null) {
                    admList.add(hdn);
                }
            }
            form.setPrj140hdnAdmin(admList.toArray(new String[admList.size()]));
        }
        return __doDspSet(map, form, req, res, con);
    }
    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doPushEntry(ActionMapping map,
                                         Prj140Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws Exception {

        con.setAutoCommit(true);

        //テンポラリディレクトリ確認
        if (!PrjCommonBiz.existStatusFile(GSConstProject.MODE_TMP_KOJIN, getRequestModel(req))) {
            return getSubmitErrorPage(map, req);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ActionErrors errors = form.validate140(con, getSessionUserModel(req), req);
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

        //登録、または更新処理を行う
        Prj140Biz biz = new Prj140Biz(con, getRequestModel(req));

        Prj140ParamModel paramMdl = new Prj140ParamModel();
        paramMdl.setParam(form);
        biz.doAddEdit(paramMdl, cntCon, userSid, getTempPath(req));
        paramMdl.setFormData(form);

        //オブジェクトファイルを削除する
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstProject.PLUGIN_ID_PROJECT, TEMP_DIRECTORY_ID);

        //ログ出力処理
        GsMessage gsMsg = new GsMessage(req);
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        String opCode = "";

        if (form.getPrtSid() < 1) {
            opCode = getInterMessage(req, "cmn.entry");
        } else {
            opCode = getInterMessage(req, "cmn.change");
        }

        prjBiz.outPutLog(
                map, req, res, opCode,
                GSConstLog.LEVEL_INFO,
                "[name]" + form.getPrj140prtTmpName());

        //登録・更新完了画面を表示
        return __setTourokuKanryoDsp(map, form, req);
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @return ActionForward
     */
    private ActionForward __doDeleteConf(ActionMapping map,
                                          Prj140Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con)
        throws SQLException, IOToolsException {

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
    }

    /**
     * <br>[機  能] 削除処理を行う(削除実行)
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
     * @throws IOToolsException IOエラー
     */
    private ActionForward __doDeleteExe(ActionMapping map,
                                         Prj140Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws SQLException, IOToolsException {

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
        Prj140Biz biz = new Prj140Biz(con, getRequestModel(req));

        Prj140ParamModel paramMdl = new Prj140ParamModel();
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
                GSConstLog.LEVEL_INFO,
                "[name]" + form.getPrj140prtTmpName());

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
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
    private ActionForward __setKakuninDsp(ActionMapping map,
                                           Prj140Form form,
                                           HttpServletRequest req,
                                           Connection con)
        throws SQLException {

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
        GsMessage gsMsg = new GsMessage();
        //プロジェクトテンプレート
        String textProjectTmp = gsMsg.getMessage(req, "project.prj130.1");
        //メッセージ
        MessageResources msgRes = getResources(req);
        String msg = msgRes.getMessage("sakujo.kakunin.once", textProjectTmp);
        cmn999Form.setMessage(msg);

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
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
    private ActionForward __setKanryoDsp(ActionMapping map,
                                          Prj140Form form,
                                          HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(map.findForward("backTmpList").getPath());

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        //プロジェクトテンプレート
        String textProjectTmp = gsMsg.getMessage(req, "project.prj130.1");
        //削除完了
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", textProjectTmp));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 登録・更新完了画面のパラメータセット
     * [解  説]
     * [備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setTourokuKanryoDsp(ActionMapping map,
                                          Prj140Form form,
                                          HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(map.findForward("backTmpList").getPath());
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        //プロジェクトテンプレート
        String textProjectTmp = gsMsg.getMessage(req, "project.prj130.1");
        int prtSid = form.getPrtSid();
        if (prtSid < 1) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object", textProjectTmp));
        } else {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object", textProjectTmp));
        }

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());

        //画面パラメータをセット
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}