package jp.groupsession.v2.fil.fil280;

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
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.fil.AbstractFileAdminAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 キャビネット管理設定画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil280Action extends AbstractFileAdminAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil280Action.class);

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

        log__.debug("fil280Action START");

        ActionForward forward = null;
        Fil280Form thisForm = (Fil280Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil280back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("fil280editCabinet")) {
            //変更ボタンクリック
            forward = map.findForward("fil030");

        } else if (cmd.equals("fil280togetherEdit")) {
            //一括変更ボタンクリック
            forward = __doPackageEdit(map, thisForm, req, res, con);

        } else if (cmd.equals("fil280up")) {
            //上へボタンクリック
            forward = __doUpSort(map, thisForm, req, res, con);

        } else if (cmd.equals("fil280down")) {
            //下へボタンクリック
            forward = __doDownSort(map, thisForm, req, res, con);

        } else if (cmd.equals("fil280search")) {
            //キャビネット検索
            forward = __doSearch(map, thisForm, req, res, con);

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
                                    Fil280Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        con.setAutoCommit(true);
        Fil280Biz biz = new Fil280Biz(con, req);

        Fil280ParamModel paramMdl = new Fil280ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        if (paramMdl.getFil280cabinetList() == null
         || paramMdl.getFil280cabinetList().isEmpty()) {
            GsMessage gsMsg = new GsMessage();
            ActionErrors errors = new ActionErrors();
            ActionMessage msg = new ActionMessage(
                    "search.data.notfound", gsMsg.getMessage("fil.23"));
            StrutsUtil.addMessage(errors, msg,  gsMsg.getMessage("fil.23"));
            addErrors(req, errors);
        }

        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        con.setAutoCommit(false);
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
    private ActionForward __doBack(ActionMapping map, Fil280Form form) {

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
                                    Fil280Form form,
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
                                    Fil280Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        //入力チェック
        ActionErrors errors = form.validateCheckSort(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        //セッションユーザSID
        int userSid = getSessionUserModel(req).getUsrsid();

        Fil280Biz biz = new Fil280Biz(con, req);
        boolean commitFlg = false;
        con.setAutoCommit(false);
        Fil280ParamModel paramMdl = new Fil280ParamModel();
        paramMdl.setParam(form);
        boolean updateFlg = false;
        try {

            updateFlg = biz.updateSort(paramMdl, userSid, true);
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

        //ログ出力処理
        if (updateFlg) {
            FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
            String value = "";
            value += "[" + gsMsg.getMessage("fil.146") + "] ";
            FileCabinetDao cabinetDao = new FileCabinetDao(con);
            FileCabinetModel cabinetMdl =
                    cabinetDao.select(Integer.parseInt(paramMdl.getFil280sltRadio()));
            value += cabinetMdl.getFcbName();
            value += "\r\n" + "[" + gsMsg.getMessage("change.sort.order") + "] "
                        + gsMsg.getMessage("cmn.up");
            String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                    + gsMsg.getMessage("fil.143");
            filBiz.outPutLogNoDspName(textEdit,
                    GSConstLog.LEVEL_INFO, value, map.getType(), null, dspName);
        }

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
                                    Fil280Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {


        //入力チェック
        ActionErrors errors = form.validateCheckSort(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }


        //セッションユーザSID
        int userSid = getSessionUserModel(req).getUsrsid();

        Fil280Biz biz = new Fil280Biz(con, req);
        boolean commitFlg = false;
        con.setAutoCommit(false);
        Fil280ParamModel paramMdl = new Fil280ParamModel();
        paramMdl.setParam(form);
        boolean updateFlg = false;
        try {

            updateFlg = biz.updateSort(paramMdl, userSid, false);
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

        //ログ出力処理
        if (updateFlg) {
            FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
            String value = "";
            value += "[" + gsMsg.getMessage("fil.146") + "] ";
            FileCabinetDao cabinetDao = new FileCabinetDao(con);
            FileCabinetModel cabinetMdl =
                    cabinetDao.select(Integer.parseInt(paramMdl.getFil280sltRadio()));
            value += cabinetMdl.getFcbName();
            value += "\r\n" + "[" + gsMsg.getMessage("change.sort.order") + "] "
                        + gsMsg.getMessage("cmn.down");
            String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                    + gsMsg.getMessage("fil.143");
            filBiz.outPutLogNoDspName(textEdit,
                    GSConstLog.LEVEL_INFO, value, map.getType(), null, dspName);
        }

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 個人キャビネット絞込み検索の処理
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
    private ActionForward __doSearch(ActionMapping map,
                                    Fil280Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        //入力チェック
        ActionErrors errors = form.validateCheckSearch(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            form.setFil280searchFlg(1);
            return __doInit(map, form, req, res, con);
        }

        form.setFil280svKeyword(__getEscKeyword(form.getFil280keyword()));
        form.setFil280svGroup(form.getFil280group());
        form.setFil280svUser(form.getFil280user());

        //検索実行フラグON
        form.setFil280searchFlg(0);

        Fil280Biz biz = new Fil280Biz(con, req);
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {

            Fil280ParamModel paramMdl = new Fil280ParamModel();
            paramMdl.setParam(form);
            biz.setInitData(paramMdl);
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
        String textSortEdit = gsMsg.getMessage(req, "cmn.search");

        //ログ出力処理
        FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("fil.143");
        filBiz.outPutLogNoDspName(textEdit,
                GSConstLog.LEVEL_INFO, textSortEdit, map.getType(), null, dspName);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] スペース、タブを除去する
     * <br>[解  説] " ","　","\t"を空文字で置換する
     * <br>[備  考]
     * @param str キーワード
     * @return エスケープ処理後の文字列
     */
    private String __getEscKeyword(String str) {
        str = str.replaceAll(" ", "");
        str = str.replaceAll("　", "");
        str = str.replaceAll("\t", "");
        return str;
    }
}

