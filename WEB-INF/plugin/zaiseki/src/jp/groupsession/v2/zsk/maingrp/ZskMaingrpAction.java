package jp.groupsession.v2.zsk.maingrp;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.sch.sch040.Sch040Form;
import jp.groupsession.v2.sch.sch040kn.Sch040knForm;
import jp.groupsession.v2.zsk.AbstractZaisekiAction;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.zsk130.Zsk130Form;


/**
 * <br>[機  能] 在席管理(メイン画面表示用 メンバー)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */

public class ZskMaingrpAction extends AbstractZaisekiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZskMaingrpAction.class);
    /** 写真画像名 */
    public String imageFileName__ = "";

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /**
     * <p>
     * アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD ===> " + cmd);

        con.setAutoCommit(true);
        ZskMaingrpForm zskForm = (ZskMaingrpForm) form;
        //メイン画面表示フラグの判定
        if (!__isMainGrpDsp(zskForm, req, con)) {
            return map.getInputForward();
        }

        //在席管理・ショートメールプラグイン利用可能確認
        __setCanUsePluginFlg(zskForm, con, req);
        con.setAutoCommit(false);

        if (cmd.equals("msg")) {
            //ショートメール
            __doCreateMsg(map, zskForm, req, res, con);
            forward = map.findForward(GSConstZaiseki.SCR_SML_NEW);
        } else if (cmd.equals("schw_add")) {
            //スケジュール登録
            forward = __doScheduleAdd(map, zskForm, req, res, con);
        } else if (cmd.equals("schw_edit")) {
            //スケジュール修正・閲覧
            forward = __doScheduleEdit(map, zskForm, req, res, con);
        } else if (cmd.equals("mainDspSetting")) {
            //在席管理個人設定 メイン画面メンバー表示設定
            forward = __doPriConf(map, zskForm, req, res, con);
        } else if (cmd.equals("changeGrpCmb")) {
            //グループコンボ変更
            forward = __doChangeGrp(map, zskForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, zskForm, req, res, con);
        }
        return forward;
    }

    /**
     * 初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doInit(ActionMapping map, ZskMaingrpForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        BaseUserModel umodel = getSessionUserModel(req);
        ZskMaingrpBiz biz = new ZskMaingrpBiz(getRequestModel(req));
        PluginConfig pconfig = getPluginConfig(req);

        ZskMaingrpParamModel paramMdl = new ZskMaingrpParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con, pconfig, umodel, getAppRootPath());
        paramMdl.setFormData(form);

        form.setZskTopUrl(getPluginConfig(req).getPlugin(
                GSConstZaiseki.PLUGIN_ID_ZAISEKI).getTopMenuInfo().getUrl());
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * メイン画面表示の有無を判定する。
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private boolean __isMainGrpDsp(ZskMaingrpForm form,
            HttpServletRequest req, Connection con)
            throws Exception {

        BaseUserModel umodel = getSessionUserModel(req);
        ZskMaingrpBiz biz = new ZskMaingrpBiz(getRequestModel(req));
        boolean flg = false;
        ZskMaingrpParamModel paramMdl = new ZskMaingrpParamModel();
        paramMdl.setParam(form);
        flg = biz.isMainGrpDsp(paramMdl, con, umodel.getUsrsid());
        paramMdl.setFormData(form);

        return flg;
    }

    /**
     * 表示グループを経変更する。
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doChangeGrp(ActionMapping map, ZskMaingrpForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        return __doInit(map, form, req, res, con);
    }

    /**
     * 在席管理・ショートメールプラグインが利用可能かフォームへ設定する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param con コネクション
     * @param req リクエスト
     * @throws SQLException SQL実行時例外
     */
    private void __setCanUsePluginFlg(ZskMaingrpForm form, Connection con, HttpServletRequest req)
    throws SQLException {

        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        CommonBiz cmnBiz = new CommonBiz();
        //在席管理は利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_ZAISEKI, pconfig)) {
            form.setZaisekiUseOk(GSConstSchedule.PLUGIN_USE);
        } else {
            form.setZaisekiUseOk(GSConstSchedule.PLUGIN_NOT_USE);
        }
        //ショートメールは利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_SMAIL, pconfig)) {
            form.setSmailUseOk(GSConstSchedule.PLUGIN_USE);
        } else {
            form.setSmailUseOk(GSConstSchedule.PLUGIN_NOT_USE);
        }

        //スケジュールは利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_SCHEDULE, pconfig)) {
            form.setSchUseOk(GSConstSchedule.PLUGIN_USE);
        } else {
            form.setSchUseOk(GSConstSchedule.PLUGIN_NOT_USE);
        }
//
//        //メイン個人設定でメニュー項目が非表示に設定されていた場合は、非表示にする。
//        ZskMaingrpBiz biz = new ZskMaingrpBiz();
//        boolean dspFlg = false;
//        dspFlg = biz.isDspMenuPlugin(form, con,
//                getSessionUserModel(req).getUsrsid(), GSConstSchedule.PLUGIN_ID_SCHEDULE);
//        if (!dspFlg) {
//            form.setSchUseOk(GSConstSchedule.PLUGIN_NOT_USE);
//        }
//
//        dspFlg = biz.isDspMenuPlugin(form, con,
//                getSessionUserModel(req).getUsrsid(), GSConstSchedule.PLUGIN_ID_SMAIL);
//        if (!dspFlg) {
//            form.setSmailUseOk(GSConstSchedule.PLUGIN_NOT_USE);
//        }
    }

    /**
     * <br>ショートメール画面へ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doCreateMsg(ActionMapping map, ZskMaingrpForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {
        //パラメータ取得
        String[] selectUserSid = new  String[] {form.getZskSelectUsrSid()};

        //送信先を設定
        req.setAttribute("cmn120userSid", selectUserSid);
        req.setAttribute("sml010scriptFlg", "1");
    }

    /**
     * スケジュール登録画面へ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doScheduleAdd(ActionMapping map, ZskMaingrpForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        ActionForward forward = null;
        UDate nowDate = new UDate();

        log__.debug("メイン：スケジュール登録");
        Sch040Form schForm = new Sch040Form();
        schForm.setCmd(GSConstSchedule.CMD_ADD);
        schForm.setDspMod(GSConstSchedule.DSP_MOD_MAIN);
        schForm.setSch010DspDate(nowDate.getDateString());
        schForm.setSch010SelectDate(nowDate.getDateString());

        schForm.setSch010SelectUsrSid(String.valueOf(form.getZskSelectUsrSid()));
        schForm.setSch010SelectUsrKbn(String.valueOf(GSConstSchedule.USER_KBN_USER));

        schForm.setSchWeekDate(nowDate.getDateString());
        req.setAttribute("sch040Form", schForm);
        forward = map.findForward("sch040");
        return forward;
    }

    /**
     * スケジュール編集画面へ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doScheduleEdit(ActionMapping map, ZskMaingrpForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        ActionForward forward = null;
        UDate nowDate = new UDate();
        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);
        Sch010Biz biz = new Sch010Biz(reqMdl);
        if (biz.isEditOk(Integer.parseInt(form.getZskSelectSchSid()), reqMdl, con, false)) {
            log__.debug("メイン：スケジュール編集");
            Sch040Form schForm = new Sch040Form();
            schForm.setCmd(GSConstSchedule.CMD_EDIT);
            schForm.setDspMod(GSConstSchedule.DSP_MOD_MAIN);
            schForm.setSch010DspDate(nowDate.getDateString());
            schForm.setSch010SelectDate(nowDate.getDateString());
            schForm.setSch010SchSid(form.getZskSelectSchSid());
            schForm.setSch010SelectUsrSid(form.getZskSelectUsrSid());
            schForm.setSch010SelectUsrKbn(String.valueOf(GSConstSchedule.USER_KBN_USER));
            schForm.setSchWeekDate(nowDate.getDateString());
            req.setAttribute("sch040Form", schForm);
            forward = map.findForward("sch040");
        } else {
            log__.debug("メイン：スケジュール閲覧");
            Sch040knForm schForm = new Sch040knForm();
            schForm.setCmd(GSConstSchedule.CMD_EDIT);
            schForm.setDspMod(GSConstSchedule.DSP_MOD_MAIN);
            schForm.setSch010DspDate(nowDate.getDateString());
            schForm.setSch010SelectDate(nowDate.getDateString());
            schForm.setSch010SchSid(form.getZskSelectSchSid());
            schForm.setSch010SelectUsrSid(form.getZskSelectUsrSid());
            schForm.setSch010SelectUsrKbn(String.valueOf(GSConstSchedule.USER_KBN_USER));
            schForm.setSchWeekDate(nowDate.getDateString());
            req.setAttribute("sch040knForm", schForm);
            forward = map.findForward("sch040kn");
        }
        return forward;
    }

    /**
     * 在席管理個人設定画面へ遷移
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doPriConf(ActionMapping map, ZskMaingrpForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        ActionForward forward = null;

        log__.debug("メイン：スケジュール登録");
        Zsk130Form zsk130Form = new Zsk130Form();
        zsk130Form.setZsk130Mode(GSConstZaiseki.MODE_MAIN);

        req.setAttribute("zsk130Form", zsk130Form);
        forward = map.findForward("zsk130");
        return forward;
    }

}
