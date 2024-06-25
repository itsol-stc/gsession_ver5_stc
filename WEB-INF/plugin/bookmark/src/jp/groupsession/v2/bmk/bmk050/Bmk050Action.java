package jp.groupsession.v2.bmk.bmk050;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.bmk.AbstractBookmarkAction;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.bmk.bmk010.Bmk010Biz;
import jp.groupsession.v2.bmk.dao.BmkBelongLabelDao;
import jp.groupsession.v2.bmk.dao.BmkLabelDao;
import jp.groupsession.v2.bmk.model.BmkLabelModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ラベル管理画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk050Action extends AbstractBookmarkAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk050Action.class);

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

        Bmk050Form thisForm = (Bmk050Form) form;
        BookmarkBiz bmkBiz = new BookmarkBiz(con, getRequestModel(req));

        BaseUserModel model = this.getSessionUserModel(req);
        int bmkKbn = thisForm.getBmk010mode();
        int usrSid = this.getSessionUserModel(req).getUsrsid();
        int gSid = thisForm.getBmk010groupSid();

        // 編集権限チェック
        int targetSid = -1;
        if (bmkKbn == GSConstBookmark.BMK_KBN_KOJIN) {
            targetSid = usrSid;
        }
        if (bmkKbn == GSConstBookmark.BMK_KBN_GROUP) {
            targetSid = gSid;
        }
        if (!bmkBiz.isEditLabel(
                con, model, bmkKbn, targetSid, GSConstBookmark.BMK_MODE_TOUROKU, null)) {
            //権限エラー
            forward = getNotAdminSeniPage(map, req);
            return forward;
        }

        if (cmd.equals("bmk050back")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("bmk010");
        } else if (cmd.equals("bmk050add")) {
            log__.debug("追加ボタン押下");
            forward = map.findForward("bmk060");
        } else if (cmd.equals("bmk050del")) {
            log__.debug("削除ボタン押下");
            forward = __doDeleteConfirmation(map, thisForm, req, res, con);
        } else if (cmd.equals("deleteOk")) {
            forward = __doDeleteOk(map, thisForm, req, res, con);
        } else if (cmd.equals("bmk050edit")) {
            log__.debug("ラベルリンク押下");
            forward = map.findForward("bmk060");
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
                                    Bmk050Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        //セッションユーザSIDを取得する。
        int sessionUserSid = getSessionUserModel(req).getUsrsid();

        con.setAutoCommit(true);
        Bmk050Biz biz = new Bmk050Biz();

        Bmk050ParamModel paramMdl = new Bmk050ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 削除確認処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doDeleteConfirmation(ActionMapping map,
                                                  Bmk050Form form,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  Connection con)
        throws Exception, SQLException {

        int sessionUserSid = getSessionUserModel(req).getUsrsid();
        ActionForward forward = null;

        try {
            //削除対象選択チェック
            ActionErrors errors = form.validateCheck(req);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }
            //権限チェック
            if (!form.validateCheckPower(con, getRequestModel(req), getSessionUserModel(req))) {
                return getSubmitErrorPage(map, req);
            }

            Bmk050Biz biz = new Bmk050Biz();
            //削除対象のラベル一覧を取得する
            Bmk050ParamModel paramMdl = new Bmk050ParamModel();
            paramMdl.setParam(form);
            ArrayList<BmkLabelModel> delList =
                biz.getTargetLabelList(paramMdl, sessionUserSid, form.getBmk050DelSidList(), con);
            paramMdl.setFormData(form);


            //削除確認画面を設定
            forward = __setConfirmationDsp(map, req, form, delList);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }

        return forward;
    }

    /**
     * <br>[機  能] 確認画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @param delList 削除対象リスト
     * @return ActionForward フォワード
     */
    private ActionForward __setConfirmationDsp(ActionMapping map,
                                                HttpServletRequest req,
                                                Bmk050Form form,
                                                ArrayList<BmkLabelModel> delList) {

        MessageResources msgRes = getResources(req);
        String delMsg = "";
        GsMessage gsMsg = new GsMessage();

        if (!delList.isEmpty()) {
            String msg = gsMsg.getMessage(req, "cmn.delete") + gsMsg.getMessage(req, "cmn.label");
            String msg2 = gsMsg.getMessage(req, "wml.231");

            for (int i = 0; i < delList.size(); i++) {
                BmkLabelModel ret = delList.get(i);
                delMsg += msg2;
                delMsg += StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(ret.getBlbName(), ""));
                log__.debug(msg + "：" + ret.getBlbName());
                //最後の要素以外は改行を挿入
                if (i < delList.size() - 1) {
                    delMsg += "<br>";
                }
            }
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        ActionForward forwardOk = map.findForward("redraw");

        ActionForward forwardCancel = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteOk");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        String label = gsMsg.getMessage(req, "cmn.label");
        cmn999Form.setMessage(
                msgRes.getMessage(
                    "sakujo.kakunin.list", label, delMsg));

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        cmn999Form.addHiddenParam("bmk050DelSidList", form.getBmk050DelSidList());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] 削除確認画面でOKボタン押下
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 
     */
    private ActionForward __doDeleteOk(ActionMapping map,
                                        Bmk050Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {
            //削除対象選択チェック
            ActionErrors errors = form.validateCheck(req);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }
            //権限チェック
            if (!form.validateCheckPower(con, getRequestModel(req), getSessionUserModel(req))) {
                return getSubmitErrorPage(map, req);
            }

            //削除処理実行
            BmkBelongLabelDao belognDao = new BmkBelongLabelDao(con);
            belognDao.delete(form.getBmk050DelSidList());

            BmkLabelDao lblDao = new BmkLabelDao(con);
            lblDao.delete(form.getBmk050DelSidList());

            GsMessage gsMsg = new GsMessage();
            String delete = gsMsg.getMessage(req, "cmn.delete");

            //ログ出力処理
            BookmarkBiz bmkBiz = new BookmarkBiz(con, getRequestModel(req));
            String opCode = delete;

            bmkBiz.outPutLog(opCode,
                    GSConstLog.LEVEL_TRACE, "", map.getType());

            commitFlg = true;

            //完了画面設定
            return __setCompDsp(map, req, form);

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
    }
    /**
     * <br>[機  能] 削除完了画面
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
                                        Bmk050Form form) {

        GsMessage gsMsg = new GsMessage();
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath());

        String label = gsMsg.getMessage(req, "cmn.label");
        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", label));

        String searchLabel = Integer.toString(form.getBmk010searchLabel());
        String[] delLabel = form.getBmk050DelSidList();
        for (int i = 0; i < form.getBmk050DelSidList().length; i++) {
            if (searchLabel.equals(delLabel[i])) {
                //検索指定ラベルの削除
                form.setBmk010searchLabel(Bmk010Biz.INIT_VALUE);
                break;
            }
        }

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
