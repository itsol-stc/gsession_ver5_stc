package jp.groupsession.v2.bmk.bmk010;

import java.sql.Connection;
import java.sql.SQLException;

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
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ブックマーク画面アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk010Action extends AbstractBookmarkAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk010Action.class);

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

        Bmk010Form thisForm = (Bmk010Form) form;

        if (cmd.equals("adminMenu")) {
            log__.debug("管理者設定");
            forward = map.findForward("adminMenu");

        } else if (cmd.equals("kojinMenu")) {
            log__.debug("個人設定");
            forward = map.findForward("kojinMenu");

        } else if (cmd.equals("groupMenu")) {
            log__.debug("グループ編集権限設定");
            forward = map.findForward("groupMenu");

        } else if (cmd.equals("registUrl")) {
            log__.debug("ブックマーク登録_URL入力");
            forward = map.findForward("registUrl");

        } else if (cmd.equals("registBookmark")) {
            log__.debug("ブックマーク登録");
            forward = map.findForward("registBookmark");

        } else if (cmd.equals("labelList")) {
            log__.debug("ラベル管理");
            forward = map.findForward("labelList");

        } else if (cmd.equals("commentList")) {
            log__.debug("コメント・評価");
            forward = map.findForward("commentList");

        } else if (cmd.equals("rankingList")) {
            log__.debug("ランキング");
            forward = map.findForward("rankingList");

        } else if (cmd.equals("newBookmark")) {
            log__.debug("新着ブックマーク一覧");
            forward = map.findForward("newBookmark");

        } else if (cmd.equals("changeTab")) {
            log__.debug("タブ変更");
            __initData(map, thisForm, req, res, con);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("selectLabel")) {
            log__.debug("ラベル抽出");
            __initDelCheckBox(map, thisForm, req, res, con);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("grpChange")) {
            log__.debug("グループコンボ変更");
            __initSelectData(map, thisForm, req, res, con);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("usrChange")) {
            log__.debug("ユーザコンボ変更");
            __initSelectData(map, thisForm, req, res, con);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("prevPage")) {
            log__.debug("前ページクリック");
            thisForm.setBmk010page(thisForm.getBmk010page() - 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            log__.debug("次ページクリック");
            thisForm.setBmk010page(thisForm.getBmk010page() + 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("delete")) {
            log__.debug("削除ボタン押下");
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteExe")) {
            log__.debug("削除実行");
            forward = __doDeleteExe(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteComp")) {
            log__.debug("削除完了");
            thisForm.setBmk010delInfSid(null);
            forward = __doInit(map, thisForm, req, res, con);
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
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Bmk010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Bmk010Biz biz = new Bmk010Biz(getRequestModel(req));

        Bmk010ParamModel paramMdl = new Bmk010ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        form.setBmk010searchUse(CommonBiz.getWebSearchUse(pconfig));
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 値クリア
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __initData(ActionMapping map,
                              Bmk010Form form,
                              HttpServletRequest req,
                              HttpServletResponse res,
                              Connection con) throws Exception {

        Bmk010Biz biz = new Bmk010Biz(getRequestModel(req));

        Bmk010ParamModel paramMdl = new Bmk010ParamModel();
        paramMdl.setParam(form);
        biz.initData(paramMdl);
        paramMdl.setFormData(form);
    }

    /**
     * <br>[機  能] 抽出条件クリア
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __initSelectData(ActionMapping map,
                              Bmk010Form form,
                              HttpServletRequest req,
                              HttpServletResponse res,
                              Connection con) throws Exception {

        Bmk010Biz biz = new Bmk010Biz(getRequestModel(req));

        Bmk010ParamModel paramMdl = new Bmk010ParamModel();
        paramMdl.setParam(form);
        biz.initSelectData(paramMdl);
        paramMdl.setFormData(form);
    }

    /**
     * <br>[機  能] 削除チェックボックスクリア
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     */
    private void __initDelCheckBox(ActionMapping map,
                              Bmk010Form form,
                              HttpServletRequest req,
                              HttpServletResponse res,
                              Connection con) throws Exception {

        Bmk010Biz biz = new Bmk010Biz(getRequestModel(req));

        Bmk010ParamModel paramMdl = new Bmk010ParamModel();
        paramMdl.setParam(form);
        biz.initDelCheckBox(paramMdl);
        paramMdl.setFormData(form);
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
        Bmk010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception, SQLException {

        BaseUserModel buMdl = getSessionUserModel(req);

        //入力チェック
        ActionErrors errors = form.validateCheckBmk010(con, getRequestModel(req), buMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        
        Bmk010Biz biz = new Bmk010Biz(getRequestModel(req));
        Bmk010ParamModel paramMdl = new Bmk010ParamModel();
        paramMdl.setParam(form);
        con.setAutoCommit(true);
        
        //存在しないものを削除対象から削除する
        biz.removeNotExistBmk(paramMdl, con);

        //削除するブックマークのタイトルを取得する
        String deleteBmk = biz.getDeleteBmkName(paramMdl, userSid, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        
        saveToken(req);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con, deleteBmk);
    }

    /**
     * [機  能] 削除確認画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @param deleteBmk 削除するブックマーク
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Bmk010Form form,
        HttpServletRequest req,
        Connection con,
        String deleteBmk) throws SQLException {

        con.setAutoCommit(true);
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("bmkMain");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteExe");

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("bmkMain");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "bmk.43");
        String colon = gsMsg.getMessage(req, "wml.215");

        //メッセージ
        String bmkNmae = msg;
        if (form.getBmk010mode() == GSConstBookmark.BMK_KBN_KOJIN) {
            bmkNmae = gsMsg.getMessage(req, "bmk.30");
        } else if (form.getBmk010mode() == GSConstBookmark.BMK_KBN_GROUP) {
            CmnGroupmDao gDao = new CmnGroupmDao(con);
            CmnGroupmModel gModel = gDao.select(form.getBmk010groupSid());
            bmkNmae = gsMsg.getMessage(req, "bmk.51") + colon
                    + StringUtilHtml.transToHTmlPlusAmparsant(gModel.getGrpName());
        } else if (form.getBmk010mode() == GSConstBookmark.BMK_KBN_KYOYU) {
            bmkNmae = gsMsg.getMessage(req, "bmk.34") + colon;
        }
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.list", bmkNmae,
                StringUtilHtml.transToHTmlPlusAmparsant(deleteBmk)));
        

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);

        con.setAutoCommit(false);
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
        Bmk010Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {
        
        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        BaseUserModel buMdl = getSessionUserModel(req);
        //権限チェック
        ActionErrors errors = form.validateCheckBmk010(con, getRequestModel(req), buMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return getSubmitErrorPage(map, req);
        }

        //選択されたブックマークを削除する
        Bmk010Biz biz = new Bmk010Biz(getRequestModel(req));

        Bmk010ParamModel paramMdl = new Bmk010ParamModel();
        paramMdl.setParam(form);
        biz.removeNotExistBmk(paramMdl, con);
        biz.deleteBmk(paramMdl, buMdl.getUsrsid(), con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.delete");

        //ログ出力処理
        BookmarkBiz bmkBiz = new BookmarkBiz(con, getRequestModel(req));
        String opCode = msg;

        bmkBiz.outPutLog(opCode, GSConstLog.LEVEL_TRACE, "", map.getType());

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
        Bmk010Form form,
        HttpServletRequest req,
        String cmd) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("deleteComp");
        cmn999Form.setUrlOK(forwardOk.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "bmk.43");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object", msg));

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}