package jp.groupsession.v2.cir.cir170kn;

import java.io.IOException;
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

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cir.AbstractCircularSubAction;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.cir010.Cir010Form;
import jp.groupsession.v2.cir.cir170.CircularCsvModel;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板アカウントインポート確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir170knAction extends AbstractCircularSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir170knAction.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "cir170";

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
        Cir170knForm thisForm = (Cir170knForm) form;

        //管理者権限チェック
        if (!_checkAuth(map, req, con)) {
            return map.findForward("gf_power");
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("decision")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("backInput")) {
            //戻るボタンクリック
            forward = map.findForward("backInput");

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
     * @param map ActionMapping
     * @param form Rng020knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cir170knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //テンポラリディレクトリを取得
        String tempDir = __getTempDir(req);

        try {
            con.setAutoCommit(true);
            Cir170knParamModel paramMdl = new Cir170knParamModel();
            paramMdl.setParam(form);
            Cir170knBiz biz = new Cir170knBiz();
            biz.setInitData(con, paramMdl, tempDir);
            paramMdl.setFormData(form);

        } catch (SQLException se) {
            throw se;
        } catch (IOException ioe) {
            throw ioe;
        } catch (IOToolsException iote) {
            throw iote;
        }
        // トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] アカウント登録処理を行う
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
    private ActionForward __doDecision(
                            ActionMapping map,
                            Cir170knForm form,
                            HttpServletRequest req,
                            HttpServletResponse res,
                            Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェックを行う
        ActionErrors errors = null;
        //テンポラリディレクトリを取得
        String tempDir = __getTempDir(req);
        errors = form.validateCheck(con, req, tempDir);
        if (errors != null && !errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("backInput");
        }

        boolean commit = false;
        RequestModel reqMdl = getRequestModel(req);
        List<CircularCsvModel> accountList = null;
        try {
            Cir170knBiz biz = new Cir170knBiz();
            accountList = biz.addAccount(con, reqMdl, tempDir,
                            getCountMtController(req), getSessionUserSid(req));

            con.commit();
            commit = true;

        } catch (Exception e) {
            log__.error("アカウント情報のインポートに失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(getRequestModel(req),
                    GSConstCircular.PLUGIN_ID_CIRCULAR, TEMP_DIRECTORY_ID);
        }

        //ログ出力
        if (accountList != null) {
            GsMessage gsMsg = new GsMessage(req);
            StringBuilder buf = new StringBuilder();
            for (CircularCsvModel mdl : accountList) {
                if (buf.length() > 0) {
                    buf.append("\r\n");
                }
                buf.append("[" + gsMsg.getMessage("cir.cir150.2") + "]" + mdl.getAccountName());
            }
            //ログ出力
            CirCommonBiz cirBiz = new CirCommonBiz(con);
            cirBiz.outPutLog(map, reqMdl,
                    getInterMessage(req, "cmn.entry"), GSConstLog.LEVEL_INFO,
                    StringUtil.trimRengeString(buf.toString(), 3000));
        }
        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Cir170knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("cirAccountManager");
        if (form.getCirAccountMode() == GSConstCircular.CAC_TYPE_USER) {
            urlForward = map.findForward("cirAccountManager");
        }

        ((Cir010Form) form).setHiddenParam(cmn999Form);
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getCirCmdMode() == GSConstCircular.CMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getCirCmdMode() == GSConstCircular.CMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "wml.wml160.05")));
        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>[機  能]テンポラリディレクトリを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return tempdir
     */
    private String __getTempDir(HttpServletRequest req) {
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        return temp.getTempPath(getRequestModel(req),
                GSConstCircular.PLUGIN_ID_CIRCULAR, TEMP_DIRECTORY_ID);

    }

}
