package jp.groupsession.v2.cir.cir180;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cir.AbstractCircularAction;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.cir230.Cir230Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 アカウントの管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir180Action extends AbstractCircularAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir180Action.class);

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
        Cir180Form thisForm = (Cir180Form) form;

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("confAccount")) {
            //追加ボタン
            forward = map.findForward("confAccount");

        } else if (cmd.equals("psnTool")) {
            //戻るボタンクリック
            if (thisForm.getCirAccountMode() == GSConstCircular.ACCOUNTMODE_PSNLSETTING) {
                log__.debug("アカウント処理モード 個人設定");
                forward = map.findForward("backInput");

            } else {
                log__.debug("アカウント処理モード 通常");
                forward = map.findForward("mailList");
            }

        } else if (cmd.equals("editAccount")) {
            //編集ボタンクリック
            forward = __doEdit(map, thisForm, req, res, con);

        } else if (cmd.equals("confLabel")) {
            //「ラベル」ボタンクリック
            forward = __doLabelClick(map, thisForm, req, res, con);

        } else if (cmd.equals("confFilter")) {
            //「フィルタ」ボタンクリック
            forward = map.findForward("confFilter");

        } else if (cmd.equals("upFilterData")) {
            //上へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_UP);

        } else if (cmd.equals("downFilterData")) {
            //下へボタンクリック
            forward = __doSortChange(map, thisForm, req, res, con, GSConst.SORT_DOWN);

        } else {
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
    public ActionForward __doInit(ActionMapping map, Cir180Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Cir180Biz biz = new Cir180Biz();

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        //ユーザSIDを取得
        int userSid = usModel.getUsrsid();

        //管理者ユーザかどうか
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, usModel, GSConstCircular.PLUGIN_ID_CIRCULAR);

        form.setCir010adminUser(adminUser);

        Cir180ParamModel paramMdl = new Cir180ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(con, paramMdl, userSid, getRequestModel(req));
        paramMdl.setFormData(form);
        
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 上へ/下へボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doSortChange(
        ActionMapping map,
        Cir180Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con,
        int changeKbn) throws Exception {
        
        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);
        boolean commitFlg = false;
        Cir180Biz biz = new Cir180Biz();
        boolean updateFlg = false;

        try {

            Cir180ParamModel paramMdl = new Cir180ParamModel();
            paramMdl.setParam(form);
            updateFlg = biz.updateSort(con, paramMdl, getSessionUserSid(req), changeKbn);
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
        if (updateFlg) {
            //ログ出力
            CirCommonBiz smlBiz = new CirCommonBiz(con);
            GsMessage gsMsg = new GsMessage(req);
            String accountName = biz.getTargetName(form.getCir180sortAccount(), con);
            String msg = "[" + gsMsg.getMessage("cir.cir150.2") + "]" + accountName;
            msg += "\r\n[" + gsMsg.getMessage("change.sort.order") + "]";
            if (changeKbn == 0) {
                msg += gsMsg.getMessage("cir.cir230.1");
            } else {
                msg += gsMsg.getMessage("cir.cir230.2");
            }
            smlBiz.outPutLog(map, getRequestModel(req),
                    getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_INFO,
                    msg);
        }
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 編集ボタンクリック時の処理を行う
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
    private ActionForward __doEdit(
        ActionMapping map,
        Cir180Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = map.findForward("confAccount");


        return forward;
    }

    /**
     * <br>[機  能] ラベルボタンクリック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Cir180Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doLabelClick(ActionMapping map,
                                Cir180Form form,
                                HttpServletRequest req,
                                HttpServletResponse res,
                                Connection con)
                                throws Exception {
        con.setAutoCommit(true);
        Cir180ParamModel paramMdl = new Cir180ParamModel();
        paramMdl.setParam(form);
        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        ActionErrors errors = new ActionErrors();
        errors = form.validateLabelCheck(con, userSid);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        // パラメータ設定
        Cir230Form form230 = new Cir230Form();
        form230.setCir230DspKbn(GSConstCircular.DSPKBN_PREF);
        req.setAttribute("cir230Form", form230);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.findForward("confLabel");
    }

}
