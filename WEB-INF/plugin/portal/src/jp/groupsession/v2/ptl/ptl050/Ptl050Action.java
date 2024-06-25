package jp.groupsession.v2.ptl.ptl050;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.dao.base.PtlPortalDao;
import jp.groupsession.v2.man.model.base.PtlPortalModel;
import jp.groupsession.v2.ptl.AbstractPortalAdminAction;
import jp.groupsession.v2.ptl.PtlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ポータル ポータル登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl050Action extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl050Action.class);

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
        Ptl050Form ptlForm = (Ptl050Form) form;
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("ptl050ok")) {
            //OKボタンクリック
            forward = __doConfirm(map, ptlForm, req, res, con);

        } else if (cmd.equals("ptl050back")) {
            //戻るボタンクリック
            forward = __doBack(map, ptlForm, req, res, con);

        } else if (cmd.equals("ptl050delete")) {
            //削除ボタンクリック
            forward = __doDeleteKn(map, ptlForm, req, res, con);

        } else if (cmd.equals("ptl050deleteOk")) {
            //削除実行
            forward = __doDelete(map, ptlForm, req, res, con);

        } else if (cmd.equals("backRedraw")) {
            //再読込
            forward = __doRedraw(map, ptlForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, ptlForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Ptl050Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        ) throws SQLException {

        Ptl050ParamModel paramMdl = new Ptl050ParamModel();
        paramMdl.setParam(form);

        Ptl050Biz biz = new Ptl050Biz();
        biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] ＯＫボタンクリック時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doConfirm(ActionMapping map,
        Ptl050Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        saveToken(req);
        return map.findForward("moveConfirm");
    }

    /**
     * <br>[機  能] 戻るボタンクリック時処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map,
        Ptl050Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        ) {

        ActionForward forward = null;
        if (form.getPtlPortalSid() < 1) {
            //ポータル管理画面へ遷移
            forward = map.findForward("backPortalList");
        } else {
            //ポータル詳細画面へ遷移
            forward = map.findForward("portalDetail");
        }

        return forward;
    }

    /**
     * <br>削除ボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private ActionForward __doDeleteKn(ActionMapping map, Ptl050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException, TempFileException {

        ActionForward forward = null;
        // トランザクショントークン設定
        this.saveToken(req);

        GsMessage gsMsg = new GsMessage();
        String textportal = gsMsg.getMessage(req, "ptl.1");

        //確認画面へ
        log__.debug("削除確認画面へ");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("deleteOk");
        cmn999Form.setUrlOK(urlForward.getPath());
        urlForward = map.findForward("delBack");
        cmn999Form.setUrlCancel(urlForward.getPath());

        //ポータル名を取得する。
        PtlPortalDao ptlDao = new PtlPortalDao(con);
        PtlPortalModel ptlModel = ptlDao.select(form.getPtlPortalSid());
        String ptlName = "";
        if (ptlModel != null) {
            ptlName = ptlModel.getPtlName();
        }

        cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.list",
                textportal,
                StringUtilHtml.transToHTmlPlusAmparsant(ptlName)));

        cmn999Form.addHiddenParam("ptlPortalSid", form.getPtlPortalSid());
        cmn999Form.addHiddenParam("ptl030sortPortal", form.getPtl030sortPortal());

        cmn999Form.addHiddenParam("ptl050init", form.getPtl050init());
        cmn999Form.addHiddenParam("ptl050name", form.getPtl050name());
        cmn999Form.addHiddenParam("ptl050openKbn", form.getPtl050openKbn());
        cmn999Form.addHiddenParam("ptl050description", form.getPtl050description());
        cmn999Form.addHiddenParam("ptl050accessKbn", form.getPtl050accessKbn());
        cmn999Form.addHiddenParam("ptl050accessKbnGroup", form.getPtl050accessKbnGroup());

        cmn999Form.addHiddenParam("ptlBackPage", form.getPtlBackPage());
        cmn999Form.addHiddenParam("ptlMainSid", form.getPtlMainSid());

        //アクセス権限 閲覧ユーザ
        cmn999Form.addHiddenParam("ptl050memberSid", form.getPtl050memberSid());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
    /**
     * <br>削除ボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doDelete(ActionMapping map, Ptl050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {

            Ptl050ParamModel paramMdl = new Ptl050ParamModel();
            paramMdl.setParam(form);

            //削除処理
            Ptl050Biz biz = new Ptl050Biz();
            biz.deletePortal(paramMdl, con);
            paramMdl.setFormData(form);
            commitFlg = true;
        } catch (Exception e) {
            log__.error("ポータル削除に失敗しました" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        RequestModel reqMdl = getRequestModel(req);

        //ログ出力処理
        String textDel = getInterMessage(reqMdl, "cmn.delete");

        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        ptlBiz.outPutLog(
                map, reqMdl, textDel, GSConstLog.LEVEL_INFO,
                "[title]" + form.getPtl050name());

        //完了画面に遷移する。
        return __setCompDsp(map, req, form);
    }

    /**
     * <br>[機  能] 完了画面
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
                                        Ptl050Form form) {

        GsMessage gsMsg = new GsMessage();
        String textPortal = gsMsg.getMessage(req, "ptl.1");

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        cmn999Form.addHiddenParam("ptlPortalSid", form.getPtlPortalSid());
        cmn999Form.addHiddenParam("ptl030sortPortal", form.getPtl030sortPortal());

        cmn999Form.addHiddenParam("ptlBackPage", form.getPtlBackPage());
        cmn999Form.addHiddenParam("ptlMainSid", form.getPtlMainSid());

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("backPortalList");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object", textPortal));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 再描画処理
     *
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    private ActionForward __doRedraw(ActionMapping map,
                                         Ptl050Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws SQLException, IOToolsException {

        //メンバーに存在しないユーザは管理者から削除
        Map<String, String> sidMap = new HashMap<String, String>();
        if (form.getPtl050memberSid() != null
                          && form.getPtl050memberSid().length > 0) {
            for (String hdn : form.getPtl050memberSid()) {
                String[] splitStr = hdn.split(GSConst.GSESSION2_ID);
                String keyValue = String.valueOf(splitStr[0]);
                sidMap.put(keyValue, keyValue);
            }
        }

        return __doInit(map, form, req, res, con);
    }
}

