package jp.groupsession.v2.enq.enq210kn;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.AbstractEnqueteAction;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.enq010.Enq010Form;
import jp.groupsession.v2.enq.enq210.Enq210Form;
import jp.groupsession.v2.enq.enq230.Enq230Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] アンケート 設問登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq210knAction extends AbstractEnqueteAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq210knAction.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "enq210";

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
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return !NullDefault.getString(req.getParameter("enq970BackPage"), "").equals("1");
    }

    /** プラグインが使用可能か判定します
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con DBコネクション
     * @return boolean true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    @Override
    protected boolean _isAccessPlugin(HttpServletRequest req, ActionForm form, Connection con)
    throws SQLException {
        if (NullDefault.getString(req.getParameter("enq970BackPage"), "").equals("1")) {
            // 発信アンケート管理から遷移時のみチェック
            if (_isSystemAdmin(req, con)) {
                return true; //システム管理者の場合はプラグインチェックをしない
            }
        }
        return super._isAccessPlugin(req, form, con);
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
     * @return フォワード
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        Enq210knForm thisForm = (Enq210knForm) form;

        boolean sendManager = thisForm.getEnq970BackPage() == 1;
        con.setAutoCommit(true);
        try {
            if (!sendManager) {
                //アンケート作成可能者以外はアクセス不可
                if (!_checkEnqCrtUser(con, req)) {
                    return getSubmitErrorPage(map, req);
                }

                //ユーザが編集対象アンケートを編集可能かを判定
                if (thisForm.getEnqEditMode() == GSConstEnquete.EDITMODE_EDIT) {
                    EnqCommonBiz enqBiz = new EnqCommonBiz();
                    long emnSid = thisForm.getEditEnqSid();

                    //ユーザが編集対象アンケートを編集可能かを判定
                    if (thisForm.getEnq210editMode() != Enq210Form.EDITMODE_TEMPLATE) {
                        if (!enqBiz.canEditEnquete(getRequestModel(req), con, emnSid)) {
                            return getSubmitErrorPage(map, req);
                        }
                    } else {
                        if (!enqBiz.checkExistEnquete(
                                getRequestModel(req), con, emnSid,
                                GSConstEnquete.DATA_KBN_TEMPLATE)) {
                            return getSubmitErrorPage(map, req);
                        }
                    }
                }
            }
        } finally {
            con.setAutoCommit(false);
        }

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        ActionForward forward = null;
        if (cmd.equals("enq210knback")) {
            log__.debug("戻る");
            if (thisForm.getEnq970BackPage() == 1) {
                forward = map.findForward("sendManager");
            } else {
                forward = map.findForward("enqEntry");
            }

        } else if (cmd.equals("decision")) {
            log__.debug("確定ボタン");

            //発信アンケート管理画面からの遷移の場合、登録を許可しない。
            if (sendManager) {
                return getAuthErrorPage(map, req);
            }

            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("checkEnq")) {
            __getPlanDataList(map, thisForm, req, res, con);
        } else {
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                   Enq210knForm form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws Exception {
        // 初期表示情報を取得
        con.setAutoCommit(true);
        try {
            Enq210knBiz biz = new Enq210knBiz();
            Enq210knParamModel paramModel = new Enq210knParamModel();
            paramModel.setParam(form);
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            biz.setInitData(paramModel, getRequestModel(req), con,
                                    getAppRootPath(),
                                    temp.getTempPath(getRequestModel(req),
                                            GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID));
            paramModel.setFormData(form);

            //ショートメールプラグイン使用可否を設定
            form.setEnq210pluginSmailUse(_getUseSmailPluginKbn(req, con));
        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
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
    private ActionForward __doDecision(ActionMapping map,
                                   Enq210knForm form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con) throws Exception {

        //編集対象アンケートが存在するかを確認
        Enq210knBiz biz = new Enq210knBiz();
        Enq210knParamModel paramModel = new Enq210knParamModel();
        paramModel.setParam(form);
        con.setAutoCommit(true);
        try {
            if (!biz.canEditEnquete(con, paramModel)) {
                return __doNoneDataError(map, req, form);
            }
        } finally {
            con.setAutoCommit(false);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        con.setAutoCommit(false);
        boolean commit = false;
        try {

            int editMode = paramModel.getEnq210editMode();
            //草稿の場合、配信に切り替える
            if (editMode == Enq210Form.EDITMODE_DRAFT) {
                paramModel.setEnq210editMode(Enq210Form.EDITMODE_NORMAL);
            }

            MlCountMtController cntCon = getCountMtController(req);
            String appRootPath = getAppRootPath();

            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            biz.entryEnqueteData(paramModel, reqMdl, con,
                    cntCon, appRootPath, temp.getTempPath(getRequestModel(req),
                            GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID));
            paramModel.setFormData(form);


            con.commit();
            commit = true;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }

        //テンポラリディレクトリを削除
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID);

        //オペレーションログの登録
        String opCode = "";
        if (form.getEnqEditMode() == GSConstEnquete.EDITMODE_EDIT) {
            opCode = getInterMessage(req, "cmn.change");
        } else {
            opCode = getInterMessage(req, "cmn.entry");
        }

        //オペレーションログ出力
        GsMessage gsMsg = new GsMessage(reqMdl);
        EnqCommonBiz enqlBiz = new EnqCommonBiz(con);
       if (form.getEnq210editMode() == Enq210knForm.EDITMODE_TEMPLATE) {
           enqlBiz.outPutLog(map, reqMdl, gsMsg.getMessage("enq.plugin"),
                   gsMsg.getMessage("project.add.temp"), opCode, GSConstLog.LEVEL_INFO,
                   "[title]" + form.getEnq210Title(), null, -1, form.getAnsEnqSid());
       } else {
           enqlBiz.outPutLog(map, reqMdl, gsMsg.getMessage("enq.plugin"),
                   opCode, GSConstLog.LEVEL_INFO,
                   "[title]" + form.getEnq210Title());
       }

        return __doCompEntry(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 登録完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     */
    private ActionForward __doCompEntry(ActionMapping map,
                                            Enq210knForm form,
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

        String msgText = gsMsg.getMessage("enq.plugin");
        ActionForward urlForward = null;
        if (form.getEnq210editMode() == Enq210Form.EDITMODE_TEMPLATE) {
            urlForward = map.findForward("enqTemplate");
            ((Enq230Form) form).setHiddenParam230(cmn999Form);
            ((Enq010Form) form).setHiddenParam(cmn999Form);

            msgText += " " + gsMsg.getMessage("cmn.template");
        } else {
            urlForward = map.findForward("enqList");
            ((Enq010Form) form).setHiddenParam(cmn999Form);
        }
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getEnqEditMode() == GSConstEnquete.EDITMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getEnqEditMode() == GSConstEnquete.EDITMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                                            msgText));

        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] 編集 / 複写対象が存在しない場合のエラー画面設定
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
        Enq210knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("enqList");

        //メッセージセット
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String enqName = gsMsg.getMessage("enq.plugin");
        if (form.getEnq210editMode() == Enq210Form.EDITMODE_TEMPLATE) {
            enqName = gsMsg.getMessage(req, "cmn.shared.template");
            urlForward = map.findForward("enqTemplate");
        }

        String textOperation = gsMsg.getMessage("cmn.change");
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                                        enqName, textOperation));

        cmn999Form.setUrlOK(urlForward.getPath());
        ((Enq230Form) form).setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] プランデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param thisForm フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __getPlanDataList(ActionMapping map,
        Enq210knForm thisForm,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {


        JSONObject jsonData = null;

        //コマンドパラメータ取得
        String appRootPath = getAppRootPath();
        Enq210knBiz biz = new Enq210knBiz();
        Enq210knParamModel paramModel = new Enq210knParamModel();
        paramModel.setParam(thisForm);
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        jsonData = biz.getChangeEnqQuection(paramModel.getEditEnqSid(), con, paramModel,
                temp.getTempPath(getRequestModel(req),
                        GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID), appRootPath,
                                                    getRequestModel(req));

        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(サポート プランデータ)");
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }
}
