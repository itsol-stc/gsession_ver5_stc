package jp.groupsession.v2.tcd.main;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.TimecardUtil;
import jp.groupsession.v2.tcd.model.TcdTcdataModel;

/**
 * <br>[機  能] タイムカード(メイン画面表示用)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdMainAction extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdMainAction.class);

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
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
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
        log__.debug("CMD = " + cmd);

        int sessionUsrSid = getRequestModel(req).getSmodel().getUsrsid();
        //adminユーザのタイムカード打刻を制御
        if (cmd.equals("tcdEdit")
                && sessionUsrSid == 0) {
            return getSubmitErrorPage(map, req);
        }

        TcdMainForm tcdForm = (TcdMainForm) form;
        if (cmd.equals("tcdEdit")) {
            //タイムカード設定
            forward = __doTcdEdit(map, tcdForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, tcdForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 初期表処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doInit(ActionMapping map, TcdMainForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        BaseUserModel umodel = getSessionUserModel(req);
        if (umodel == null) {
            return null;
        }
        int sessionUserSid = umodel.getUsrsid();

        RequestModel reqMdl = getRequestModel(req);
        TcdMainParamModel paramMdl = new TcdMainParamModel();
        paramMdl.setParam(form);
        TcdMainBiz biz = new TcdMainBiz();
        PluginConfig pconfig = getPluginConfig(req);
        biz.setInitData(paramMdl, reqMdl, con, pconfig, umodel);
        paramMdl.setFormData(form);

        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(sessionUserSid, con);

        //不正データチェック
        if (form.validateMain(sessionUserSid, con, admConf, reqMdl)) {
            form.setTcdStatus(String.valueOf(GSConstTimecard.STATUS_FAIL));
        }
        form.setTcdTopUrl(getPluginConfig(req).getPlugin(
                GSConstTimecard.PLUGIN_ID_TIMECARD).getTopMenuInfo().getUrl());

        con.setAutoCommit(false);

        saveToken(req);

        //トークンをCookieに保存する
        //※Cookieにパス指定を行うとマルチテナント環境で動作しなくなる為、設定しない。
        Cookie cookie = new Cookie("token", "");
        HttpSession session = req.getSession();
        Object value = session.getAttribute(Globals.TRANSACTION_TOKEN_KEY);
        cookie.setValue(String.valueOf(value));
        cookie.setHttpOnly(true);
        res.addCookie(cookie);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] タイムカード 始業・終業ボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    public ActionForward __doTcdEdit(ActionMapping map,
                                      TcdMainForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
    throws Exception {

        BaseUserModel umodel = getSessionUserModel(req);
        int sessionUserSid = umodel.getUsrsid();
        con.setAutoCommit(false);
        boolean commitFlg = false;

        RequestModel reqMdl = getRequestModel(req);
        TcdTcdataModel tcMdl = null;
        UDate dDate = null;
        int kbn = 1;
        try {
            //二重投稿チェック
            if (!__tokenValidate(req)) {
                log__.info("２重投稿");
                return getSubmitErrorPage(map, req);
            }

            //入力チェック
            ActionErrors errors = form.validateChkMan001(sessionUserSid, con, reqMdl);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                __doInit(map, form, req, res, con);
                return map.findForward("redraw");
            }

            //タイムカード情報更新
            TcdMainParamModel paramMdl = new TcdMainParamModel();
            paramMdl.setParam(form);
            TcdMainBiz biz = new TcdMainBiz();
            tcMdl = biz.updateTcd(paramMdl, reqMdl, con);
            paramMdl.setFormData(form);

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

        if (tcMdl != null) {
            dDate = tcMdl.getTcdDate();
            if (tcMdl.getTcdStrikeOuttime() != null) {
                TimecardUtil.setTime(dDate, tcMdl.getTcdStrikeOuttime());
                kbn = 2;
            } else {
                TimecardUtil.setTime(dDate, tcMdl.getTcdStrikeIntime());
            }
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String edit = gsMsg.getMessage("cmn.edit");
        //ログ出力
        TimecardBiz tcdBiz = new TimecardBiz(reqMdl);
        String opLog =  tcdBiz.opLogContent(kbn, null, dDate);
        tcdBiz.outPutTimecardLog(map, reqMdl, con, edit, GSConstLog.LEVEL_TRACE, opLog);

        //完了画面の設定
        return __setCompDsp(map, req, form);
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
                                        TcdMainForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(map.findForward("redraw").getPath());

        GsMessage gsMsg = new GsMessage();
        String timecard = gsMsg.getMessage(req, "tcd.50");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
             msgRes.getMessage("hensyu.kanryo.object", timecard));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] トークンチェックを行うメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return トークンチェック結果
     */
    private boolean __tokenValidate(HttpServletRequest req) {

        String token = req.getParameter(Globals.TOKEN_KEY);
        if (token == null) {
            return false;
        }

        Cookie[] cookies = req.getCookies();
        String value = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                value = cookie.getValue();
                if (value.equals(token)) {
                    return true;
                }
            }
        }
        return false;
    }

}
