package jp.groupsession.v2.enq.enq010;

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
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.AbstractEnqueteAction;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アンケート一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq010Action extends AbstractEnqueteAction  {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq010Action.class);

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
        Enq010Form thisForm = (Enq010Form) form;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        if (cmd.equals("enq010Search")) {
            //検索
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("enq010SearchSimple")) {
            //検索(簡易検索)
            forward = __doSearchSimple(map, thisForm, req, res, con);

        } else if (cmd.equals("enq010chkAnsOverSimple")) {
            //検索(簡易検索 回答期限切れを含むフラグ変更時)

            forward = __doChangeAnsOverSimple(map, thisForm, req, res, con);

        } else if (cmd.equals("enq010Add")) {
            //新規追加
            log__.debug("新規追加");
            forward = map.findForward("editEnquete");

        } else if (cmd.equals("enq010Edit")) {
            //編集
            log__.debug("編集");
            forward = map.findForward("editEnquete");

        } else if (cmd.equals("enq010delEnquete")) {
            //削除
            if (!_checkEnqCrtUser(con, req)) {
                return getSubmitErrorPage(map, req);
            }
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("enq010delEnquete_ok")) {
            // 削除実行
            if (!_checkEnqCrtUser(con, req)) {
                return getSubmitErrorPage(map, req);
            }
            forward = __doDeleteOk(map, thisForm, req, res, con);

        } else if (cmd.equals("enq010Answer")) {
            //回答
            log__.debug("回答");
            forward = map.findForward("enqAnswer");

        } else if (cmd.equals("enq010Answered")) {
            // 回答済確認
            log__.debug("回答済確認");
            forward = map.findForward("enqAnswerKn");

        } else if (cmd.equals("enq010Result")) {
            //結果確認
            log__.debug("結果確認");
            forward = map.findForward("enq010Result");

        } else if (cmd.equals("enq010admConf")) {
            // 管理者設定
            log__.debug("管理者設定");
            forward = map.findForward("enqAdmConf");

        } else if (cmd.equals("enq010priConf")) {
            // 個人設定
            forward = map.findForward("enqPriConf");

        } else if (cmd.equals("prevPage")) {
            //前ページクリック
            thisForm.setEnq010pageTop(thisForm.getEnq010pageTop() - 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            thisForm.setEnq010pageTop(thisForm.getEnq010pageTop() + 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("editTemplate")) {
            forward = map.findForward(cmd);

        } else if (cmd.equals("selectTemplate")) {
            forward = map.findForward(cmd);

        } else if (cmd.equals("enq010ChangeFolder")) {
            //表示フォルダー変更
            forward = __doChangeFolder(map, thisForm, req, res, con);

        } else if (cmd.equals("enq010SendSmail")) {
            //ショートメール通知
            forward = __doSendSmail(map, thisForm, req, res, con);

        } else {
            //初期表示処理
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
                                   Enq010Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException, Exception {

        con.setAutoCommit(true);
        try {
            Enq010ParamModel paramMdl = new Enq010ParamModel();
            paramMdl.setParam(form);
            Enq010Biz biz = new Enq010Biz();
            // コマンドパラメータ取得
            String cmd = NullDefault.getString(req.getParameter("CMD"), "");
            cmd = cmd.trim();
            biz.setInitData(paramMdl, getRequestModel(req), con, cmd);
            paramMdl.setFormData(form);
            
            //検索条件の入力チェックを行う
            RequestModel reqMdl = getRequestModel(req);
            ActionErrors errors = form.validateSearch(reqMdl);
            
            //ショートメールプラグイン使用可否を設定
            form.setEnq010pluginSmailUse(_getUseSmailPluginKbn(req, con));

            if (errors.isEmpty()) {
                //検索結果が0件の場合、エラーメッセージを表示する。
                List<Enq010EnqueteModel> enqList = form.getEnq010EnqueteList();
                if (enqList == null || enqList.isEmpty()) {
                    GsMessage gsMsg = new GsMessage(getRequestModel(req));
                    StrutsUtil.addMessage(errors,
                                new ActionMessage("search.data.notfound",
                                                            gsMsg.getMessage("enq.plugin")),
                                "enq010.list");
                    addErrors(req, errors);
                }
            }

        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 表示フォルダー変更処理
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
    private ActionForward __doChangeFolder(ActionMapping map,
                                           Enq010Form form,
                                           HttpServletRequest req,
                                           HttpServletResponse res,
                                           Connection con)
    throws SQLException, Exception {

        log__.debug("表示フォルダー変更処理");

        // 表示ページを1ページ目に設定する
        form.setEnq010pageTop(1);
        form.setEnq010pageBottom(1);

        return __doInit(map, form, req, res, con);
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
                                   Enq010Form form,
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
            form.setEnq010pageTop(1);
            form.setEnq010pageBottom(1);
            Enq010ParamModel paramMdl = new Enq010ParamModel();
            paramMdl.setParam(form);
            Enq010Biz biz = new Enq010Biz();
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
                            "enq010.list");
                addErrors(req, errors);
            }
        }

        return forward;
    }
    /**
     * <br>[機  能] 回答可能のみ表示フラグ変更(簡易検索)処理
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
    private ActionForward __doChangeAnsOverSimple(ActionMapping map,
            Enq010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
    throws SQLException, Exception {

        if (!StringUtil.isNullZeroString(form.getEnq010svKeywordSimple())) {
            RequestModel reqMdl = getRequestModel(req);
            ActionErrors errors = form.validateSearchSimple(reqMdl, true);

            form.setEnq010pageTop(1);
            form.setEnq010pageBottom(1);
            form.setEnq010svStatusAnsOverSimple(form.getEnq010statusAnsOverSimple());
            form.setEnq010svStatusAnsOver(null);
            ActionForward forward = __doInit(map, form, req, res, con);

            //検索結果が0件の場合、エラーメッセージを表示する。
            List<Enq010EnqueteModel> enqList = form.getEnq010EnqueteList();
            if (enqList == null || enqList.isEmpty()) {
                GsMessage gsMsg = new GsMessage(getRequestModel(req));
                StrutsUtil.addMessage(errors,
                            new ActionMessage("search.data.notfound",
                                                        gsMsg.getMessage("enq.plugin")),
                            "enq010.list");
                addErrors(req, errors);
            }
            return forward;

        } else {
            return __doSearchSimple(map, form, req, res, con, true);
        }
    }
    /**
     * <br>[機  能] 検索(簡易検索)処理
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
    private ActionForward __doSearchSimple(ActionMapping map,
                                   Enq010Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException, Exception {
        return __doSearchSimple(map, form, req, res, con, false);
    }
    /**
     * <br>[機  能] 検索(簡易検索)処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param changeAnsOverFlg 回答期限切れを含むフラグ変更時true
     * @return フォワード
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行例外
     */
    private ActionForward __doSearchSimple(ActionMapping map,
                                   Enq010Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con,
                                   boolean changeAnsOverFlg)
    throws SQLException, Exception {

        //検索条件の入力チェックを行う
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateSearchSimple(reqMdl, changeAnsOverFlg);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        } else {
            //検索条件を保持
            form.setEnq010pageTop(1);
            form.setEnq010pageBottom(1);
            Enq010ParamModel paramMdl = new Enq010ParamModel();
            paramMdl.setParam(form);
            Enq010Biz biz = new Enq010Biz();
            biz._setSearchParamSimple(paramMdl);
            paramMdl.setFormData(form);
        }

        ActionForward forward = __doInit(map, form, req, res, con);

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
                                   Enq010Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con) throws SQLException, Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //入力チェックを行う
        String[] selectEnqSid = form.getEnq010selectEnqSid();
        if (selectEnqSid == null || selectEnqSid.length <= 0) {
            ActionErrors errors = new ActionErrors();
            ActionMessage msg
            = new ActionMessage("error.select.required.text",
                                            gsMsg.getMessage(req, "enq.plugin"));
            StrutsUtil.addMessage(errors, msg, "error.select.required.text");
            addErrors(req, errors);

            return __doInit(map, form, req, res, con);
        }

        //削除対象のアンケートが存在するかを確認
        Enq010Biz biz = new Enq010Biz();
        for (String emnSid : selectEnqSid) {
            if (!biz.existEnquete(con, Long.parseLong(emnSid))) {
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
        urlForward = map.findForward("enqList");
        cmn999Form.setUrlOK(urlForward.getPath() + "?" + GSConst.P_CMD + "=enq010delEnquete_ok");
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
                                       Enq010Form form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con) throws SQLException {

        //削除対象のアンケートが存在するかを確認
        Enq010Biz biz = new Enq010Biz();
        for (String selectEnqSid : form.getEnq010selectEnqSid()) {
            if (!biz.existEnquete(con, Long.parseLong(selectEnqSid))) {
                return __doNoneDataError(map, req, form);
            }
        }

        String delEnqName = "";
        EnqCommonBiz enqBiz = new EnqCommonBiz(con);
        con.setAutoCommit(false);
        boolean commit = false;
        try {

            int userSid = getSessionUserSid(req);

            for (String selectEnqSid : form.getEnq010selectEnqSid()) {
                if (delEnqName.length() > 0) {
                    delEnqName += ",";
                }
                delEnqName += enqBiz.deleteEnquete(Long.parseLong(selectEnqSid), userSid, con);
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
                "[title]" + delEnqName);

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
                                            Enq010Form form,
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
        urlForward = map.findForward("enqList");
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
    private ActionForward __doNoneDataError(
        ActionMapping map,
        HttpServletRequest req,
        Enq010Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("enqList");

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

    /**
     * <br>[機  能] 確定ボタンクリック時処理
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
    private ActionForward __doSendSmail(ActionMapping map,
                                   Enq010Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con) throws Exception {

        //アンケート作成を許可されていない、もしくは対象アンケートの作成者ではない場合
        //不正なアクセスとして処理する
        long emnSid = form.getEnq010smailEnquate();
        EnqCommonBiz enqBiz = new EnqCommonBiz();
        if (!_checkEnqCrtUser(con, req)
        || !enqBiz.canEditEnquete(getRequestModel(req), con, emnSid)) {
            return getSubmitErrorPage(map, req);
        }

        //ショートメールプラグインを使用できない場合
        //不正なアクセスとして処理する
        if (_getUseSmailPluginKbn(req, con) != GSConst.PLUGIN_USE) {
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        String enqTitle = null;
        con.setAutoCommit(false);
        boolean commit = false;
        try {

            MlCountMtController cntCon = getCountMtController(req);
            String appRootPath = getAppRootPath();

            enqTitle = enqBiz.sendSmailInfo(reqMdl, con,
                                            cntCon,
                                            appRootPath,
                                            getPluginConfig(req),
                                            getSessionUserSid(req),
                                            emnSid,
                                            form.getEnq010ansedSendKbn());

            con.commit();
            commit = true;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }

        //オペレーションログの登録
        String opCode = getInterMessage(req, "shortmail.notification");

        //オペレーションログ出力
        GsMessage gsMsg = new GsMessage(reqMdl);
        EnqCommonBiz enqlBiz = new EnqCommonBiz(con);
       enqlBiz.outPutLog(map, reqMdl, gsMsg.getMessage("enq.plugin"),
               opCode, GSConstLog.LEVEL_INFO,
               "[title]" + enqTitle);

        return __doCompSmailInfo(map, form, req, res, con);
    }

    /**
     * <br>[機  能] ショートメール通知完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     */
    private ActionForward __doCompSmailInfo(ActionMapping map,
                                            Enq010Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con) {

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        ActionForward urlForward = null;
        urlForward = map.findForward("enqList");
        ((Enq010Form) form).setHiddenParam(cmn999Form);
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        cmn999Form.setMessage(
                msgRes.getMessage("cmn.kanryo.object",
                                            gsMsg.getMessage("shortmail.notification")));

        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        return forward;
    }
}
