package jp.groupsession.v2.cht.cht080;

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
import jp.groupsession.v2.cht.AbstractChatAdminAction;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] チャット 特例アクセス一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht080Action extends AbstractChatAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht080Action.class);

    /**
     * <br>アクション実行
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
        ActionForward forward = null;

        Cht080Form chtForm = (Cht080Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");


        if (cmd.equals("cht080back")) {
            //管理者設定に戻る
            forward = map.findForward("cht020");

        } else if (cmd.equals("addSpAccess")) {
            //特例アクセス登録画面に移動する
            forward = map.findForward("cht090");

        } else if (cmd.equals("spAccessEdit")) {
            //特例アクセス登録画面に移動する
            forward = __doEdit(map, chtForm, req, res, con);

        }  else if (cmd.equals("spAccessDelete")) {
            //特例アクセス削除確認画面を表示する
            forward = __doDelete(map, chtForm, req, res, con);

        } else if (cmd.equals("doDeleteSpAccess")) {
            //特例アクセス削除を実行する
            forward = __doDeleteComp(map, chtForm, req, res, con);

        } else if (cmd.equals("search")) {
            //検索ボタンクリック
            forward = __doSearch(map, chtForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページボタンクリック
            forward = __doNext(map, chtForm, req, res, con);

        } else if (cmd.equals("prevPage")) {
            //前ページボタンクリック
            forward = __doPrev(map, chtForm, req, res, con);

        } else {
            //デフォルト
            forward = __doInit(map, chtForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cht080Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Cht080ParamModel paramMdl = new Cht080ParamModel();
        paramMdl.setParam(form);
        Cht080Biz biz = new Cht080Biz();
        biz.setInitData(con, paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doSearch(ActionMapping map, Cht080Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //入力チェック
        ActionErrors errors = form.validateCheck(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setCht080searchFlg(1);
        } else {
            form.setCht080svKeyword(form.getCht080keyword());
            form.setCht080searchFlg(0);
            form.setCht080pageTop(1);
            form.setCht080pageBottom(1);
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 編集ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doEdit(
        ActionMapping map,
        Cht080Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //削除対象の特例アクセスが存在するかを確認
        if (!form.existChtSpAccess(con, form.getCht080editData())) {
            return __doNoneDataError(map, req, form);
        }
        // トランザクショントークン設定
        saveToken(req);
        //特例アクセス編集画面を表示
        return  map.findForward("cht090");
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
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Cht080Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionErrors errors = form.validateDelete(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //削除対象の特例アクセスが存在するかを確認
        for (String chsSid : form.getCht080selectSpAccessList()) {
            if (!form.existChtSpAccess(con, Integer.parseInt(chsSid))) {
                return __doNoneDataError(map, req, form);
            }
        }

        // トランザクショントークン設定
        saveToken(req);

        //削除確認画面を表示
        return __setKakuninDsp(map, form, req, con);
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
        Cht080Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("mine");

        //メッセージセット
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String enqName = gsMsg.getMessage("cht.cht080.02");
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
     * <br>[機  能] 削除確認画面のパラメータセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __setKakuninDsp(
        ActionMapping map,
        Cht080Form form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCancel = map.findForward("mine");
        cmn999Form.setUrlCancel(forwardCancel.getPath());

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("mine");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=doDeleteSpAccess");

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        String msg = "";

        Cht080Biz biz = new Cht080Biz();
        msg = msgRes.getMessage("sakujo.kakunin.list",
                                gsMsg.getMessage(req, "schedule.sch230.02"),
                                biz.getAccessName(con, form.getCht080selectSpAccessList()));

        cmn999Form.setMessage(msg);

        cmn999Form.addHiddenParam("CMD", "search");
        form.setHiddenParam(cmn999Form);

        //画面パラメータをセット
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
     * @throws GSException GS用汎実行例外
     * @return ActionForward
     */
    private ActionForward __doDeleteComp(
        ActionMapping map,
        Cht080Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, GSException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //削除対象の特例アクセスが存在するかを確認
        Cht080Biz biz = new Cht080Biz();
        for (String nsaSid : form.getCht080selectSpAccessList()) {
            if (!form.existChtSpAccess(con, Integer.parseInt(nsaSid))) {
                return __doNoneDataError(map, req, form);
            }
        }

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        // 削除対象の特例アクセス名を取得する
        Cht080Dao cht080Dao = new Cht080Dao(con);
        List<String> titleList = cht080Dao.getAccessNameList(form.getCht080selectSpAccessList());

        // 特例アクセスを削除する
        Cht080ParamModel paramMdl = new Cht080ParamModel();
        paramMdl.setParam(form);

        boolean commit = false;
        try {
            biz.deleteAccess(con, paramMdl, userSid);
            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("特例アクセスの削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
        paramMdl.setFormData(form);

        //ログ出力
        ChtBiz chtBiz = new ChtBiz(con, getRequestModel(req));
        GsMessage gsMsg = new GsMessage(req);
        String opCode = gsMsg.getMessage("cmn.delete");

        //ログ出力処理
        StringBuilder sb = new StringBuilder();

        for (String name : titleList) {
            sb.append("[" + gsMsg.getMessage("schedule.sch240.04") + "]");
            sb.append(name);
            sb.append(System.getProperty("line.separator"));
        }
        chtBiz.outPutLog(map,
                opCode,
                GSConstLog.LEVEL_INFO,
                sb.toString(),
                getRequestModel(req),
                "");

        //削除完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 削除完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Cht080Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("mine");

        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);
        //削除完了
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(
                msgRes.getMessage("sakujo.kanryo.object",
                                gsMsg.getMessage(req, "schedule.sch230.02")));

        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
    private ActionForward __doNext(
        ActionMapping map,
        Cht080Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getCht080pageTop();
        page += 1;
        form.setCht080pageTop(page);
        form.setCht080pageBottom(page);

        return __doInit(map, form, req, res, con);
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
    private ActionForward __doPrev(
        ActionMapping map,
        Cht080Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //ページ設定
        int page = form.getCht080pageTop();
        page -= 1;
        if (page < 1) {
            page = 1;
        }
        form.setCht080pageTop(page);
        form.setCht080pageBottom(page);

        return __doInit(map, form, req, res, con);
    }

}

