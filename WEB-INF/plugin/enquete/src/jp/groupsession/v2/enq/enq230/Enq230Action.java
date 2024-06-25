package jp.groupsession.v2.enq.enq230;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.AbstractEnqueteAction;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.enq010.Enq010Biz;
import jp.groupsession.v2.enq.enq010.Enq010EnqueteModel;
import jp.groupsession.v2.enq.enq010.Enq010Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] テンプレート一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq230Action extends AbstractEnqueteAction  {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq230Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        //アンケート作成可能者以外はアクセス不可
        con.setAutoCommit(true);
        try {
            if (!_checkEnqCrtUser(con, req)) {
                return getSubmitErrorPage(map, req);
            }
        } finally {
            con.setAutoCommit(false);
        }

        ActionForward forward = null;
        Enq230Form thisForm = (Enq230Form) form;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        log__.debug("CMD = " + cmd);

        if (cmd.equals("enq230search")) {
            // 検索
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("enq230add")) {
            // 追加
            forward = map.findForward("enq230edit");

        } else if (cmd.equals("enq230edit")) {
            // 編集
            forward = map.findForward("enq230edit");

        } else if (cmd.equals("enq230delete")) {
            // 削除
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("enq230delEnquete_ok")) {
            // 削除処理
            forward = __doDeleteOk(map, thisForm, req, res, con);

        } else if (cmd.equals("enq230back")) {
            // 戻る
            forward = map.findForward(cmd);

        } else if (cmd.equals("arrow_left")) {
            // 前ページ
            forward = __doPrev(map, thisForm, req, res, con);

        } else if (cmd.equals("arrow_right")) {
            // 次ページ
            forward = __doNext(map, thisForm, req, res, con);

        } else {
            // 初期表示処理
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
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
     * @return フォワード
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
                                   Enq230Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException, Exception {

        log__.debug("初期表示処理");

        con.setAutoCommit(true);
        try {
            Enq230ParamModel paramMdl = new Enq230ParamModel();
            paramMdl.setParam(form);
            Enq230Biz biz = new Enq230Biz();
            biz.setInitData(paramMdl, getRequestModel(req), con);
            paramMdl.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行例外
     */
    private ActionForward __doSearch(ActionMapping map,
                                   Enq230Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException, Exception {

        log__.debug("検索処理");

        //検索条件の入力チェックを行う
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateSearch(reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        } else {
            //検索条件を保持
            Enq230ParamModel paramMdl = new Enq230ParamModel();
            paramMdl.setParam(form);
            Enq230Biz biz = new Enq230Biz();
            biz._setSearchParam(paramMdl);
            paramMdl.setFormData(form);
        }

        ActionForward forward = __doInit(map, form, req, res, con);
        if (errors.isEmpty()) {
            //検索結果が0件の場合、エラーメッセージを表示する。
            List<Enq010EnqueteModel> enqList = form.getEnq230EnqueteList();
            if (enqList == null || enqList.isEmpty()) {
                GsMessage gsMsg = new GsMessage(getRequestModel(req));
                StrutsUtil.addMessage(errors,
                            new ActionMessage("search.data.notfound",
                                                        gsMsg.getMessage("cmn.template")),
                            "enq230.list");
                addErrors(req, errors);
            }
        }

        return forward;
    }

    /**
     * <br>[機  能] 前ページクリック時の処理
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
    private ActionForward __doPrev(ActionMapping map,
                                   Enq230Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con) throws Exception {

        //ページ設定
        int page = form.getEnq230pageTop();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setEnq230pageTop(page);
        form.setEnq230pageBottom(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 次ページクリック時の処理
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
    private ActionForward __doNext(ActionMapping map,
                                   Enq230Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con) throws Exception {

        //ページ設定
        int page = form.getEnq230pageTop();
        page += 1;
        form.setEnq230pageTop(page);
        form.setEnq230pageBottom(page);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] テンプレート削除確認
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doDelete(ActionMapping map,
                                     Enq230Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con) throws Exception {

        log__.debug("テンプレート削除確認");
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionForward forward = null;

        //入力チェックを行う
        String[] selectEnqSid = form.getEnq230selectEnqSid();
        if (selectEnqSid == null || selectEnqSid.length <= 0) {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage("error.select.required.text",
                                                  gsMsg.getMessage(req, "cmn.template"));
            StrutsUtil.addMessage(errors, msg, "error.select.required.text");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //削除対象のテンプレートが存在するかを確認
        con.setAutoCommit(true);
        try {
            Enq010Biz biz010 = new Enq010Biz();
            for (String emnSid : selectEnqSid) {
                if (!biz010.existEnquete(con, Long.parseLong(emnSid))) {
                    return __doNoneDataError(map, req, form);
                }
            }
        } finally {
            con.setAutoCommit(false);
        }

        //トランザクショントークン設定
        saveToken(req);

        // 確認画面
        log__.debug("削除確認画面へ");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("enqTemplate");
        cmn999Form.setUrlOK(urlForward.getPath() + "?" + GSConst.P_CMD + "=enq230delEnquete_ok");
        cmn999Form.setUrlCancel(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.once",
                                            gsMsg.getMessage("cmn.template")));
        form.setHiddenParam(cmn999Form);
        form.setHiddenParam230(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");

        return forward;
    }

    /**
     * <br>[機  能] テンプレート削除実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDeleteOk(ActionMapping map,
                                       Enq230Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con) throws SQLException {

        log__.debug("テンプレート削除実行");

        //削除対象のテンプレートが存在するかを確認
        con.setAutoCommit(true);
        try {
            String[] selectEnqSid = form.getEnq230selectEnqSid();
            Enq010Biz biz010 = new Enq010Biz();
            for (String emnSid : selectEnqSid) {
                if (!biz010.existEnquete(con, Long.parseLong(emnSid))) {
                    return __doNoneDataError(map, req, form);
                }
            }
        } finally {
            con.setAutoCommit(false);
        }

        RequestModel reqMdl = getRequestModel(req);
        EnqCommonBiz enqBiz = new EnqCommonBiz(con);
        String delEnqName = "";

        // 二重投稿チェック
        if (!isTokenValid(req, true)) {
            log__.info("二重投稿");
            return getSubmitErrorPage(map, req);
        }

        // 削除処理
        Enq230ParamModel paramModel = new Enq230ParamModel();
        paramModel.setParam(form);
        Enq230Biz biz = new Enq230Biz();
        delEnqName = biz.doDelete(paramModel, reqMdl, con);
        paramModel.setFormData(form);

        //ログ出力
        if (delEnqName.length() > 1000) {
            delEnqName.substring(0, 1000);
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String pluginName = gsMsg.getMessage("enq.plugin");
        enqBiz.outPutLog(map, reqMdl, pluginName, gsMsg.getMessage("cmn.delete"),
                GSConstLog.LEVEL_INFO, "[title]" + delEnqName);

        return __doDeleteCompDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 削除完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     */
    private ActionForward __doDeleteCompDsp(ActionMapping map,
                                            Enq230Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con) {

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("enqTemplate");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object",
                gsMsg.getMessage("cmn.template")));

        form.setHiddenParam(cmn999Form);
        form.setHiddenParam230(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] 削除対象が存在しない場合のエラー画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    protected ActionForward __doNoneDataError(
        ActionMapping map,
        HttpServletRequest req,
        Enq230Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("enqList");

        //メッセージセット
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String enqName = gsMsg.getMessage("cmn.shared.template");
        String textOperation = gsMsg.getMessage("cmn.delete");
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                                        enqName, textOperation));

        cmn999Form.setUrlOK(urlForward.getPath());
        ((Enq010Form) form).setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}
