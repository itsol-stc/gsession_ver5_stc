package jp.groupsession.v2.enq.enq970;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.AbstractEnqueteAdminAction;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.enq010.Enq010Biz;
import jp.groupsession.v2.enq.enq010.Enq010EnqueteModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 発信アンケート管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq970Action extends AbstractEnqueteAdminAction  {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq970Action.class);

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
        ActionForward forward = null;
        Enq970Form thisForm = (Enq970Form) form;
        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        if (cmd.equals("enq970back")) {
            //戻る
            forward = map.findForward("enqAdmConf");

        } else if (cmd.equals("enq970Search")) {
            //検索
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("enq970delEnquete")) {
            //削除
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("enq970delEnquete_ok")) {
            // 削除実行
            forward = __doDeleteOk(map, thisForm, req, res, con);

        } else if (cmd.equals("prevPage")) {
            //前ページクリック
            thisForm.setEnq970pageTop(thisForm.getEnq970pageTop() - 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            thisForm.setEnq970pageTop(thisForm.getEnq970pageTop() + 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("enq970detail")) {
            //タイトルリンククリック
            forward = map.findForward("enq970detail");

        } else if (cmd.equals("enq970result")) {
            //対象人数リンククリック
            forward = map.findForward("enq970result");

        } else {
            //初期表示処理
            log__.debug("初期表示処理");

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
                                   Enq970Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException, Exception {

        con.setAutoCommit(true);
        try {
            Enq970ParamModel paramMdl = new Enq970ParamModel();
            paramMdl.setParam(form);
            Enq970Biz biz = new Enq970Biz();
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
                                   Enq970Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException, Exception {

        //検索条件の入力チェックを行う
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateSearch(reqMdl);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        } else {
            //検索条件を保持
            form.setEnq970pageTop(1);
            form.setEnq970pageBottom(1);
            Enq970ParamModel paramMdl = new Enq970ParamModel();
            paramMdl.setParam(form);
            Enq970Biz biz = new Enq970Biz();
            biz._setSearchParam(paramMdl);
            paramMdl.setFormData(form);
        }

        ActionForward forward = __doInit(map, form, req, res, con);
        if (errors.isEmpty()) {
            //検索結果が0件の場合、エラーメッセージを表示する。
            List<Enq010EnqueteModel> enqList = form.getEnq010EnqueteList();
            if (enqList == null || enqList.isEmpty()) {
                GsMessage gsMsg = new GsMessage(getRequestModel(req));
                StrutsUtil.addMessage(errors,
                            new ActionMessage("search.data.notfound",
                                                        gsMsg.getMessage("enq.plugin")),
                            "enq970.list");
                addErrors(req, errors);
            }
        }

        return forward;
    }

    /**
     * <br>[機  能] 削除ボタンクリック時処理
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
    private ActionForward __doDelete(ActionMapping map,
                                   Enq970Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con) throws SQLException, Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String[] selectEnqSid = form.getEnq970selectEnqSid();

        //入力チェックを行う
        if (selectEnqSid == null || selectEnqSid.length <= 0) {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage(
                    "error.select.required.text", gsMsg.getMessage(req, "enq.plugin"));
            StrutsUtil.addMessage(errors, msg, "error.select.required.text");
            addErrors(req, errors);

            return __doInit(map, form, req, res, con);
        }

        //削除対象のアンケートが存在するかを確認
        Enq010Biz biz = new Enq010Biz();
        for (String enqSid : selectEnqSid) {
            if (!biz.existEnquete(con, Long.parseLong(enqSid))) {
                return __doNoneDataError(map, req, form);
            }
        }

        ActionForward forward = null;
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
        urlForward = map.findForward("mine");
        cmn999Form.setUrlOK(urlForward.getPath() + "?" + GSConst.P_CMD + "=enq970delEnquete_ok");
        cmn999Form.setUrlCancel(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.once",
                                            gsMsg.getMessage("enq.plugin")));
        form.setHiddenParam(cmn999Form);


        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");

        return forward;
    }

    /**
     * <br>[機  能] 削除処理
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
                                       Enq970Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con) throws SQLException {

        String[] selectEnqSid = form.getEnq970selectEnqSid();

        //削除対象のアンケートが存在するかを確認
        Enq010Biz biz = new Enq010Biz();
        for (String enqSid : selectEnqSid) {
            if (!biz.existEnquete(con, Long.parseLong(enqSid))) {
                return __doNoneDataError(map, req, form);
            }
        }

        String delEnqName = "";
        EnqCommonBiz enqBiz = new EnqCommonBiz(con);
        con.setAutoCommit(false);
        boolean commit = false;
        try {

            int userSid = getSessionUserSid(req);

            for (String enqSid : selectEnqSid) {
                if (delEnqName.length() > 0) {
                    delEnqName += ",";
                }
                delEnqName += enqBiz.deleteEnquete(Long.parseLong(enqSid), userSid, con);
            }
            con.commit();
            commit = true;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }

        //ログ出力
        if (delEnqName.length() > 1000) {
            delEnqName.substring(0, 1000);
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String pluginName = gsMsg.getMessage("enq.plugin");
        enqBiz.outPutLog(map, reqMdl, pluginName,
                gsMsg.getMessage("cmn.delete"), GSConstLog.LEVEL_INFO,
                "[" + gsMsg.getMessage("cmn.title") + "]" + delEnqName);

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
                                            Enq970Form form,
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
        urlForward = map.findForward("mine");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object",
                gsMsg.getMessage("enq.plugin")));

        form.setHiddenParam(cmn999Form);

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
    protected ActionForward __doNoneDataError(ActionMapping map,
                                              HttpServletRequest req,
                                              Enq970Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("mine");

        //メッセージセット
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String enqName = gsMsg.getMessage("enq.plugin");
        String textOperation = gsMsg.getMessage("cmn.delete");
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                                        enqName, textOperation));

        cmn999Form.setUrlOK(urlForward.getPath());
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}
