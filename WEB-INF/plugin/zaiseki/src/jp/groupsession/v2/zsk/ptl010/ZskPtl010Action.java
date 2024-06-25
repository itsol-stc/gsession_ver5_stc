package jp.groupsession.v2.zsk.ptl010;

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
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.sch.sch040.Sch040Form;
import jp.groupsession.v2.sch.sch040kn.Sch040knForm;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml010.Sml010Form;
import jp.groupsession.v2.zsk.AbstractZaisekiAction;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.zsk130.Zsk130Form;

/**
 * <br>[機  能] 在席管理 ポートレット グループメンバーのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZskPtl010Action extends AbstractZaisekiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZskPtl010Action.class);

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

        log__.debug("zskPtl010Action START");

        ActionForward forward = null;
        ZskPtl010Form thisForm = (ZskPtl010Form) form;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        con.setAutoCommit(true);

        //在席管理・ショートメールプラグイン利用可能確認
        __setCanUsePluginFlg(thisForm, con, req);
        con.setAutoCommit(false);

        if (cmd.equals("msg")) {
            //ショートメール
            __doCreateMsg(map, thisForm, req, res, con);
            forward = map.findForward("msg");
        } else if (cmd.equals("schw_add")) {
            //スケジュール登録
            forward = __doScheduleAdd(map, thisForm, req, res, con);
        } else if (cmd.equals("schw_edit")) {
            //スケジュール修正・閲覧
            forward = __doScheduleEdit(map, thisForm, req, res, con);
        } else if (cmd.equals("mainDspSetting")) {
            //在席管理個人設定 メイン画面メンバー表示設定
            forward = __doPriConf(map, thisForm, req, res, con);
        } else if (cmd.equals("changeGrpCmb")) {
            //グループコンボ変更
            forward = __doChangeGrp(map, thisForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
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
     * @throws GSException GS用汎実行例外
     * @return Forward
     */
    private ActionForward __doInit(ActionMapping map, ZskPtl010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception, GSException {

        con.setAutoCommit(true);
        BaseUserModel umodel = getSessionUserModel(req);
        if (umodel == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        ZskPtl010Biz biz = new ZskPtl010Biz(getRequestModel(req));
        PluginConfig pconfig = getPluginConfig(req);

        ZskPtl010ParamModel paramMdl = new ZskPtl010ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con, pconfig, umodel, getAppRootPath());
        paramMdl.setFormData(form);

        form.setZskTopUrl(getPluginConfig(req).getPlugin(
                GSConstZaiseki.PLUGIN_ID_ZAISEKI).getTopMenuInfo().getUrl());
        con.setAutoCommit(false);

        return map.getInputForward();
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
    private ActionForward __doChangeGrp(ActionMapping map, ZskPtl010Form form,
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
    private void __setCanUsePluginFlg(ZskPtl010Form form, Connection con, HttpServletRequest req)
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
    private void __doCreateMsg(ActionMapping map, ZskPtl010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        //パラメータ取得
        String selectUserSid = form.getZskSelectUsrSid();

//        Sml020Form msgForm = new Sml020Form();
//        msgForm.setSml020ProcMode(GSConstSmail.MSG_CREATE_MODE_MAIN);

        Sml010Form msgForm = new Sml010Form();
        msgForm.setSml010scriptFlg(GSConstSmail.SCRIPT_FIG_TRUE);
        msgForm.setSml010scriptKbn(GSConstSmail.SCRIPT_CREATE_MAIL);
        msgForm.setSml010scriptSelUsrSid(selectUserSid);

        req.setAttribute("sml010Form", msgForm);

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
    private ActionForward __doScheduleAdd(ActionMapping map, ZskPtl010Form form,
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
    private ActionForward __doScheduleEdit(ActionMapping map, ZskPtl010Form form,
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
    private ActionForward __doPriConf(ActionMapping map, ZskPtl010Form form,
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