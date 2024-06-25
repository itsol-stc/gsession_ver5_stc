package jp.groupsession.v2.anp.anp100;


import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.anp.AbstractAnpiAdminAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>管理者設定・メールテンプレート編集画面のアクション
 * @author JTS
 */
public class Anp100Action extends AbstractAnpiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp100Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォアード
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        Anp100Form uform = (Anp100Form) form;

        //管理者権限確認
        if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
            return getNotAdminSeniPage(map, req);
        }

        if (cmd.equals("anp100back")) {
            //戻る
            forward = map.findForward("back");

        } else if (cmd.equals("anp100excute")) {
            //OK
            forward = __doUpdate(map, uform, req, res, con);

        } else if (cmd.equals("anp100delete")) {
            //削除
            forward = __doDeleteConf(map, uform, req, res, con);

        } else if (cmd.equals("delete_ok")) {
            //削除実行
            forward = __doDeleteExec(map, uform, req, res, con);
        } else if (cmd.equals("tempEdit")) {
            //一覧からの遷移
            forward = __doEdit(map, uform, req, res, con);
        } else {
            //初期化
            forward = __doInit(map, uform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォアード
     */
    private ActionForward __doInit(ActionMapping map, Anp100Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        return __refresh(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 編集データ表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォアード
     */
    private ActionForward __doEdit(ActionMapping map, Anp100Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //設定データ取得
        Anp100Biz biz = new Anp100Biz();
        Anp100ParamModel paramModel = new Anp100ParamModel();
        paramModel.setParam(form);
        biz.setTempData(paramModel, con);
        paramModel.setFormData(form);

        return __refresh(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 更新処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォアード
     */
    private ActionForward __doUpdate(ActionMapping map, Anp100Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("更新");

        //入力チェック
        ActionErrors errors = form.validateAnp100(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __refresh(map, form, req, res, con);
        }

        // トランザクショントークン設定
        this.saveToken(req);

        return map.findForward("excute");
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
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteConf(ActionMapping map, Anp100Form form,
                  HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //トランザクショントークン設定
        this.saveToken(req);

        //削除確認画面のパラメータセット
        return __setDeleteConfDsp(map, form, req, con);
    }

    /**
     * <br>[機  能] 削除確認メッセージ確認画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __setDeleteConfDsp(ActionMapping map, Anp100Form form,
                                    HttpServletRequest req, Connection con)
            throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //確認メッセージ画面パラメータの設定
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //遷移先（OK→本画面、CANCEL→遷移元画面へ）
        urlForward = map.findForward("edit");
        cmn999Form.setUrlOK(urlForward.getPath() + "?CMD=delete_ok");
        cmn999Form.setUrlCancel(urlForward.getPath() + "?CMD=delete_cancel");

        //メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kakunin.once", gsMsg.getMessage("anp.anp100.03")));

        //画面値のセーブ
        form.setHiddenParamAnp010(cmn999Form);
        form.setHiddenParamAnp100(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除完了処理を行う
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
    private ActionForward __doDeleteExec(ActionMapping map, Anp100Form form,
                         HttpServletRequest req, HttpServletResponse res, Connection con)
             throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(true);
        Anp100Biz biz = new Anp100Biz();
        Anp100ParamModel paramModel = new Anp100ParamModel();
        paramModel.setParam(form);
        biz.doDelete(paramModel, con);
        paramModel.setFormData(form);

        //ログ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);
        String opCode = gsMsg.getMessage("cmn.delete");
        String value = form.getAnp100Title();
        anpBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_INFO, value);

        //削除完了画面を表示
        return __setDeleteExecDsp(map, form, req);
    }

    /**
     * <br>[機  能] 削除完了メッセージ画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setDeleteExecDsp(ActionMapping map, Anp100Form form,
                                          HttpServletRequest req) {

        //メッセージ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        String msg = msgRes.getMessage("sakujo.kanryo.object", gsMsg.getMessage("anp.anp090.03"));

        return __setMsgDsp(map, form, req, msg);
    }

    /**
     * <br>[機  能] 表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォアード
     */
    private ActionForward __refresh(ActionMapping map, Anp100Form form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        return map.getInputForward();
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
    private ActionForward __setMsgDsp(ActionMapping map, Anp100Form form,
                                    HttpServletRequest req, String msg) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("list");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        cmn999Form.setMessage(msg);

        //画面値のセーブ
        form.setHiddenParamAnp010(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}
