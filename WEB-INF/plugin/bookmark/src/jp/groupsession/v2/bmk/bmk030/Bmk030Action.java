package jp.groupsession.v2.bmk.bmk030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.bmk.AbstractBookmarkAction;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ブックマーク登録画面アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk030Action extends AbstractBookmarkAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk030Action.class);

    /** 遷移元画面 */
    private String returnPage__;

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Bmk030Form thisForm = (Bmk030Form) form;

        //編集権限チェック
        if (thisForm.getProcMode() == GSConstBookmark.BMK_MODE_EDIT) { 
            BookmarkBiz bmkBiz = new BookmarkBiz(con, getRequestModel(req));
            List<Integer> bmkSidList = new ArrayList<Integer>();
            BaseUserModel buMdl = getSessionUserModel(req);
            bmkSidList.add(thisForm.getEditBmkSid());

            int mode = thisForm.getBmk030mode();
            int targetSid = -1;
            if (mode == GSConstBookmark.BMK_KBN_KOJIN) {
                targetSid = buMdl.getUsrsid();
            }
            if (mode == GSConstBookmark.BMK_KBN_GROUP) {
                targetSid = thisForm.getBmk030groupSid();
            }

            if (!bmkBiz.isEditBookmark(
                    con, buMdl, mode, targetSid,
                    GSConstBookmark.BMK_MODE_EDIT, bmkSidList)) {
                return getSubmitErrorPage(map, req);
            }
        }

        //遷移元画面セット
        if (thisForm.getBmk070ReturnPage().equals("bmk070")) {
            returnPage__ = thisForm.getBmk070ReturnPage();
        } else if (thisForm.getBmk070ReturnPage().equals("bmk150")) {
            if (thisForm.isBmk070ToBmk150DspFlg()) {
                //評価・コメントページから遷移
                returnPage__ = "bmk070";
            } else {
                //ブックマーク登録ページから遷移
                returnPage__ = thisForm.getReturnPage();
            }
        } else {
            returnPage__ = thisForm.getReturnPage();
        }

        if (cmd.equals("bmk030pushOk")) {
            log__.debug("OKボタン押下");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("bmk030pushDelete")) {
            log__.debug("削除ボタン押下");
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteExe")) {
            log__.debug("削除実行");
            forward = __doDeleteExe(map, thisForm, req, res, con);

        } else if (cmd.equals("bmk030pushBack")) {
            log__.debug("戻るボタン押下");
            if (thisForm.getProcMode() == GSConstBookmark.BMK_MODE_TOUROKU) {
                log__.debug("戻り先 ===> bmk020");
                forward = map.findForward("bmk020");
            } else {
                log__.debug("戻り先 ===> " + returnPage__);
                forward = map.findForward(returnPage__);
            }

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Bmk030Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        //セッションユーザSIDを取得する。
        int sessionUserSid = this.getSessionUserModel(req).getUsrsid();

        con.setAutoCommit(true);
        Bmk030Biz biz = new Bmk030Biz(getRequestModel(req));

        Bmk030ParamModel paramMdl = new Bmk030ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, getSessionUserModel(req), sessionUserSid);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        
        // トランザクショントークン設定
        saveToken(req);
        
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doOk(
        ActionMapping map,
        Bmk030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception, SQLException {

        BaseUserModel buMdl = getSessionUserModel(req);

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.validateBmk020(con);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        errors = form.validateBmk030(con, buMdl, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //採番コントローラ
        MlCountMtController cntCon = getCountMtController(req);

        //ログインユーザSIDを取得
        int userSid = 0;
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //登録・更新処理を行う
        Bmk030Biz biz = new Bmk030Biz(getRequestModel(req));

        Bmk030ParamModel paramMdl = new Bmk030ParamModel();
        paramMdl.setParam(form);
        biz.doAddEdit(paramMdl, con, cntCon, userSid);
        paramMdl.setFormData(form);

        //ログ出力処理
        BookmarkBiz bmkBiz = new BookmarkBiz(con, getRequestModel(req));
        String opCode = "";

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.entry");
        String msg2 = gsMsg.getMessage(req, "cmn.edit");

        //新規登録
        if (form.getProcMode() == GSConstBookmark.BMK_MODE_TOUROKU) {
            opCode = msg;
        //編集
        } else {
            opCode = msg2;
        }

        bmkBiz.outPutLog(opCode,
               GSConstLog.LEVEL_TRACE,
               "[title]" + form.getBmk030title(), map.getType());

        //登録・更新完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * <br>[機  能] 削除ボタン押下
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Bmk030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception, SQLException {

        //削除するブックマークのタイトルを取得する
        con.setAutoCommit(true);
        Bmk030Biz biz = new Bmk030Biz(getRequestModel(req));

        Bmk030ParamModel paramMdl = new Bmk030ParamModel();
        paramMdl.setParam(form);
        String deleteBmk = biz.getDeleteBmkName(con, paramMdl);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, deleteBmk);
    }

    /**
     * [機  能] 削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param deleteBmk 削除するブックマーク
     * @return ActionForward
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Bmk030Form form,
        HttpServletRequest req,
        String deleteBmk) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("bmk030");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteExe");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("bmk030");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage(
                "sakujo.kakunin.list",
                StringUtilHtml.transToHTmlPlusAmparsant(form.getBmk030modeName()),
                StringUtilHtml.transToHTmlPlusAmparsant(deleteBmk)));

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        form.setHiddenParamBmk030(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    
    /**
     * [機  能] 完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Bmk030Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward(returnPage__);
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);

        int procMode = form.getProcMode();
        if (procMode == GSConstBookmark.BMK_MODE_TOUROKU) {
            //登録完了
            cmn999Form.setMessage(
                    msgRes.getMessage("touroku.kanryo.object",
                            StringUtilHtml.transToHTmlPlusAmparsant(form.getBmk030modeName())));
        } else if (procMode == GSConstBookmark.BMK_MODE_EDIT) {
            //更新完了
            cmn999Form.setMessage(
                    msgRes.getMessage("hensyu.kanryo.object",
                            StringUtilHtml.transToHTmlPlusAmparsant(form.getBmk030modeName())));
        }

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        cmn999Form.addHiddenParam("bmk070Page", form.getBmk070Page());
        cmn999Form.addHiddenParam("bmk070PageTop", form.getBmk070PageTop());
        cmn999Form.addHiddenParam("bmk070PageBottom", form.getBmk070PageBottom());
        cmn999Form.addHiddenParam("bmk070OrderKey", form.getBmk070OrderKey());
        cmn999Form.addHiddenParam("bmk070SortKey", form.getBmk070SortKey());
        cmn999Form.addHiddenParam("bmk070ReturnPage", form.getBmk070ReturnPage());
        cmn999Form.addHiddenParam("bmk080Page", form.getBmk080Page());
        cmn999Form.addHiddenParam("bmk080PageTop", form.getBmk080PageTop());
        cmn999Form.addHiddenParam("bmk080PageBottom", form.getBmk080PageBottom());
        cmn999Form.addHiddenParam("bmk150PageNum", form.getBmk150PageNum());
        cmn999Form.addHiddenParam("bmk070ToBmk150DspFlg",
                String.valueOf(form.isBmk070ToBmk150DspFlg()));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }


    /**
     * <br>[機  能] 削除処理を行う(削除実行)
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
    private ActionForward __doDeleteExe(
        ActionMapping map,
        Bmk030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        BaseUserModel buMdl = getSessionUserModel(req);

        //選択されたブックマークを削除する
        Bmk030Biz biz = new Bmk030Biz(getRequestModel(req));

        Bmk030ParamModel paramMdl = new Bmk030ParamModel();
        paramMdl.setParam(form);
        biz.deleteBmk(paramMdl, buMdl.getUsrsid(), con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.delete");

        //ログ出力処理
        BookmarkBiz bmkBiz = new BookmarkBiz(con, getRequestModel(req));
        String opCode = msg;
        bmkBiz.outPutLog(opCode,
               GSConstLog.LEVEL_TRACE,
               "[title]" + form.getBmk030title(), map.getType());

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req, cmd);
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param cmd コマンドパラメータ
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Bmk030Form form,
        HttpServletRequest req,
        String cmd) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward(returnPage__);
        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "bmk.43");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object", msg));

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        form.setHiddenParamBmk030(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}