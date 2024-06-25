package jp.groupsession.v2.ptl.ptl040;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ptl.AbstractPortalAdminAction;
import jp.groupsession.v2.ptl.PtlCommonBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] ポータル ポータル詳細画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl040Action extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl040Action.class);

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
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Ptl040Form ptlForm = (Ptl040Form) form;
        log__.debug("cmd = " + cmd);
        if (cmd.equals("ptl040editPortal")) {
            //編集ボタンクリック
            forward = map.findForward("editPortal");

        } else if (cmd.equals("ptl040editLayout")) {
            //レイアウトボタンクリック
            forward = map.findForward("editLayout");

        } else if (cmd.equals("ptl040back")) {
            //戻るボタンクリック
            forward = map.findForward("backPortalList");

        } else if (cmd.equals("setPosition")) {
            //プラグイン・ポートレット位置移動
            __doSavePosition(map, ptlForm, req, res, con);
            forward = null;

        } else if (cmd.equals("deletePosition")) {
            //プラグイン・ポートレット位置削除
            __doDelPosition(map, ptlForm, req, res, con);
            forward = null;

        } else if (cmd.equals("changeDsp")) {
            //プラグイン・ポートレット表示区分変更
            __changeDsp(map, ptlForm, req, res, con);
            forward = null;

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
     * @throws GSException GS用汎実行例外
     * @throws ClassNotFoundException 指定されたユーザリスナークラスが存在しない
     * @throws IllegalAccessException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Ptl040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException,
        GSException,
        ClassNotFoundException,
        IllegalAccessException,
        InstantiationException,
        Exception {

        Ptl040Biz biz = new Ptl040Biz();

        //ログインユーザSIDを取得
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        Ptl040ParamModel paramMdl = new Ptl040ParamModel();
        paramMdl.setParam(form);

        con.setAutoCommit(false);
        boolean commit = false;
        try {

            //インフォメーション位置情報がなければ、登録する。
            biz.insertInfomation(paramMdl, con, buMdl.getUsrsid());
            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("ポータルインフォメーション位置情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con);

        //初期表示処理
        biz.setInitData(paramMdl, con, pconfig, getRequestModel(req));

        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * 並び順変更処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @throws GSException GS用汎実行例外
     */
    private void __doSavePosition(ActionMapping map, Ptl040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception, GSException {

        //ログインユーザSIDを取得
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        con.setAutoCommit(false);
        boolean commit = false;
        try {
            Ptl040ParamModel paramMdl = new Ptl040ParamModel();
            paramMdl.setParam(form);

            Ptl040Biz biz = new Ptl040Biz();
            biz.savePosition(con, buMdl.getUsrsid(), paramMdl);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("ポータル位置情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        //ログ出力処理
        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        String opCode = getInterMessage(reqMdl, "cmn.change");
        //表示位置変更
        String msg = getInterMessage(reqMdl, "ptl.ptl040.2");

        ptlBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_INFO, msg);
    }

    /**
     * プラグイン・ポートレット削除処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     */
    private void __doDelPosition(ActionMapping map, Ptl040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(false);
        boolean commit = false;
        try {

            Ptl040ParamModel paramMdl = new Ptl040ParamModel();
            paramMdl.setParam(form);

            Ptl040Biz biz = new Ptl040Biz();
            biz.delPosition(con, paramMdl);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("ポータル位置情報の削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        //ログ出力処理
        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        String opCode = getInterMessage(reqMdl, "cmn.delete");

        ptlBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_INFO, "");
    }
    /**
     * プラグイン・ポートレット表示区分変更処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     */
    private void __changeDsp(ActionMapping map, Ptl040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(false);
        boolean commit = false;
        try {

            Ptl040ParamModel paramMdl = new Ptl040ParamModel();
            paramMdl.setParam(form);

            Ptl040Biz biz = new Ptl040Biz();
            biz.updateView(con, paramMdl);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("ポータル表示区分の削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        //ログ出力処理
        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        String opCode = getInterMessage(reqMdl, "cmn.change");
        //表示非表示変更
        String msg = getInterMessage(reqMdl, "ptl.ptl040.3");

        ptlBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_INFO, msg);
    }

}

