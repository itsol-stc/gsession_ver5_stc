package jp.groupsession.v2.fil.fil220;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.fil.AbstractFileAdminAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 管理者設定 キャビネット管理設定画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil220Action extends AbstractFileAdminAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil220Action.class);

    /**
     *<br>[機  能] アクションを実行する
     *<br>[解  説]
     *<br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {

        log__.debug("fil220Action START");

        ActionForward forward = null;
        Fil220Form thisForm = (Fil220Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        
        //キャビネット区分チェック
        if (thisForm.getFil220cabinetKbn() != GSConstFile.CABINET_KBN_PUBLIC
                && thisForm.getFil220cabinetKbn() != GSConstFile.CABINET_KBN_ERRL) {
            return getSubmitErrorPage(map, req);
        }

        if (cmd.equals("fil220back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("fil220editCabinet")) {
            //変更ボタンクリック
            forward = map.findForward("fil030");

        } else if (cmd.equals("fil220togetherEdit")) {
            //一括変更ボタンクリック
            forward = __doPackageEdit(map, thisForm, req, res, con);

        } else if (cmd.equals("fil220up")) {
            //上へボタンクリック
            forward = __doUpSort(map, thisForm, req, res, con);

        } else if (cmd.equals("fil220down")) {
            //下へボタンクリック
            forward = __doDownSort(map, thisForm, req, res, con);

        } else {
            //初期表示
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
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Fil220Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        con.setAutoCommit(true);
        Fil220Biz biz = new Fil220Biz(con);

        Fil220ParamModel paramMdl = new Fil220ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }
        
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 遷移元画面へ遷移する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(ActionMapping map, Fil220Form form) {

        ActionForward forward = null;

        forward = map.findForward("fil200");
        return forward;
    }

    /**
     * <br>[機  能] 一括変更ボタンクリック時の処理
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
     */
    private ActionForward __doPackageEdit(ActionMapping map,
                                    Fil220Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        //入力チェック
        ActionErrors errors = form.validateCheckPackageEdit(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        ActionForward forward = null;
        forward = map.findForward("fil030");
        return forward;
    }

    /**
     * <br>[機  能] 上へボタンクリック時の処理
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
     */
    private ActionForward __doUpSort(ActionMapping map,
                                    Fil220Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        //入力チェック
        ActionErrors errors = form.validateCheckSort(req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        //セッションユーザModel
        BaseUserModel buMdl = getSessionUserModel(req);

        Fil220Biz biz = new Fil220Biz(con);
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {

            Fil220ParamModel paramMdl = new Fil220ParamModel();
            paramMdl.setParam(form);
            biz.updateSort(paramMdl, buMdl, GSConst.SORT_UP);
            paramMdl.setFormData(form);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("表示順の更新に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        GsMessage gsMsg = new GsMessage();
        String textEdit = gsMsg.getMessage(req, "cmn.change");
        String textSortEdit = gsMsg.getMessage(req, "fil.77");

        //ログ出力処理
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        filBiz.outPutLog(textEdit,
                GSConstLog.LEVEL_INFO, textSortEdit, map.getType());

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 下へボタンクリック時の処理
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
     */
    private ActionForward __doDownSort(ActionMapping map,
                                    Fil220Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {


        //入力チェック
        ActionErrors errors = form.validateCheckSort(req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }
       
        //セッションユーザModel
        BaseUserModel buMdl = getSessionUserModel(req);

        Fil220Biz biz = new Fil220Biz(con);
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {

            Fil220ParamModel paramMdl = new Fil220ParamModel();
            paramMdl.setParam(form);
            biz.updateSort(paramMdl, buMdl, GSConst.SORT_DOWN);
            paramMdl.setFormData(form);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("表示順の更新に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        GsMessage gsMsg = new GsMessage();
        String textEdit = gsMsg.getMessage(req, "cmn.change");
        String textSortEdit = gsMsg.getMessage(req, "fil.77");

        //ログ出力処理
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        filBiz.outPutLog(textEdit,
                GSConstLog.LEVEL_INFO, textSortEdit, map.getType());

        return __doInit(map, form, req, res, con);
    }
}

