package jp.groupsession.v2.anp.anp020;


import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.GSHttpServletRequestWrapper;
import jp.groupsession.v2.anp.AbstractAnpiAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp999.Anp999Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 安否状況入力画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Anp020Action extends AbstractAnpiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp020Action.class);

    /**
     * <br>[機  能] セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        String mode = NullDefault.getString(((Anp020Form) form).getRmode(), "");
        log__.debug("rmode = " + mode);

        return (mode.equals(GSConstAnpi.REMOTE_MODE));
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
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        Anp020Form uform = (Anp020Form) form;

        if (!uform.validateParamAnp020()) {
            log__.info("パラメータエラー");
            return getSubmitErrorPage(map, req);
        }

        FirewallBiz fwBiz = FirewallBiz.getInstance();
        if (fwBiz.additionalCheckForAnpAncer(__getAccessUsrModel(uform, req, con), con) == false) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }


        if (cmd.equals("anp020back")) {
            //戻る
            return map.findForward("back");

        } else if (cmd.equals("anp020excute")) {
            //登録
            forward = __doUpdate(map, uform, req, res, con);

        } else if (cmd.equals("anp020knback")) {
            //確認画面からの戻り時
            forward = __refresh(map, uform, req, res, con);

        } else {
            //初期化
            forward = __doInit(map, uform, req, res, con);
        }
        GSHttpServletRequestWrapper wrapper
        = new GSHttpServletRequestWrapper(req);
        String domain = GroupSession.getResourceManager().getDomain(req);
        if (!StringUtil.isNullZeroString(domain)) {
            if (wrapper.getSession(false) == null) {
                wrapper.getSession(true);
            }
            wrapper.getSession(false).setAttribute("domain", domain);
        }

        return forward;
    }
    /**
     *
     * <br>[機  能] ユーザモデルを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param form
     * @param req
     * @param con
     * @return
     * @throws SQLException
     * @throws NumberFormatException
     */
    private BaseUserModel __getAccessUsrModel(Anp020Form form,
            HttpServletRequest req,
            Connection con) throws NumberFormatException, SQLException {

        if (canNoSessionAccess(req, form)) {
            //セッション管理なしの場合は、パラメータユーザSID
            AuthDao adao = new AuthDao(con);
            BaseUserModel smodel = null;
            smodel = adao.selectLogin(Integer.valueOf(form.getUserSid()));

            return smodel;

        } else {
            //セッション管理ありの場合は、セッションユーザSID
            HttpSession session = req.getSession();
            BaseUserModel usModel = (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
            return usModel;
        }
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private ActionForward __doInit(ActionMapping map, Anp020Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Anp020ParamModel paramModel = new Anp020ParamModel();
        paramModel.setParam(form);
        //アクセス日時を更新
        if (canNoSessionAccess(req, form)) {
            Anp020Biz biz = new Anp020Biz();
            biz.doAccess(paramModel, con, __getAccessUsrSid(form, req));
        }

        //安否状況データ取得
        Anp020Biz biz = new Anp020Biz();
        boolean exist = biz.setAnpiData(paramModel, con);

        paramModel.setFormData(form);

        //該当データがない場合、エラーメッセージ画面に遷移する
        if (!exist) {
            return __setReadErrorDsp(map, form, req, con);
        }

        return __refresh(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private ActionForward __doUpdate(ActionMapping map, Anp020Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェック
        ActionErrors errors = form.validateAnp020(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __refresh(map, form, req, res, con);
        }

        // トランザクショントークン設定
        this.saveToken(req);

        return map.findForward("excute");
    }

    /**
     * <br>[機  能] 表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private ActionForward __refresh(ActionMapping map, Anp020Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        boolean isAdmin = false;
        if (!canNoSessionAccess(req, form)) {
            isAdmin = AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con);
        }

        //初期データ取得
        Anp020Biz biz = new Anp020Biz();
        Anp020ParamModel paramModel = new Anp020ParamModel();
        paramModel.setParam(form);
        boolean exist = biz.setInitData(paramModel, con, __getAccessUsrSid(form, req), isAdmin);
        paramModel.setFormData(form);

        //該当データがない場合、エラーメッセージ画面に遷移する
        if (!exist) {
            return __setReadErrorDsp(map, form, req, con);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 読込エラー画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __setReadErrorDsp(ActionMapping map, Anp020Form form,
                                  HttpServletRequest req, Connection con)
            throws SQLException {

        //メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String msg = gsMsg.getMessage("anp.anp021.01");

        return __setMsgDsp(map, form, req, msg);
    }

    /**
     * <br>[機  能] メッセージ画面のパラメータセット
     * <br>[解  説] OKボタンのみのメッセージ画面を表示する
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param msg メッセージ文字列
     * @return ActionForward
     */
    private ActionForward __setMsgDsp(ActionMapping map, Anp020Form form,
                                    HttpServletRequest req, String msg) {


        //OKボタンクリック時遷移先
        if (canNoSessionAccess(req, form)) {
            GsMessage gsMsg = new GsMessage(getRequestModel(req));
            Anp999Form msgForm = new Anp999Form();
            msgForm.setUserSid(form.getUserSid());
            msgForm.setAnpiSid(form.getAnpiSid());

            msgForm.setIcon(Anp999Form.ICON_INFO);
            msgForm.setType(Anp999Form.TYPE_OK);

            //OKボタンクリック時遷移先
            msgForm.setOkBtnValue(gsMsg.getMessage("anp.reenter"));

            //メッセージ
            msgForm.setMessage(msg);

            req.setAttribute("anp999Form", msgForm);

            return map.findForward("anpimb_gf_msg");


        } else {
            Cmn999Form cmn999Form = new Cmn999Form();
            cmn999Form.setIcon(Cmn999Form.ICON_WARN);
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            //安否確認メインへ
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
            ActionForward forwardOk = map.findForward("anpimain");
            cmn999Form.setUrlOK(forwardOk.getPath());
            //画面値のセーブ
            form.setHiddenParamAnp010(cmn999Form);
            //メッセージ
            cmn999Form.setMessage(msg);

            req.setAttribute("cmn999Form", cmn999Form);

            return map.findForward("gf_msg");
        }

    }

    /**
     * <br>[機  能] アクセスしているユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @throws Exception 例外
     * @return アクションフォーム
     */
    private int __getAccessUsrSid(Anp020Form form, HttpServletRequest req)
            throws Exception {

        if (canNoSessionAccess(req, form)) {
            //セッション管理なしの場合は、パラメータユーザSID
            return Integer.valueOf(form.getUserSid());

        } else {
            //セッション管理ありの場合は、セッションユーザSID
            HttpSession session = req.getSession();
            BaseUserModel usModel = (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
            return usModel.getUsrsid();
        }
    }
}
