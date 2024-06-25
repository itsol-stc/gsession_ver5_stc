package jp.groupsession.v2.prj.ptl020;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.prj.AbstractProjectAdminAction;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] プロジェクト管理 プラグインポートレット TODO状態内訳のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjPtl020Action extends AbstractProjectAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjPtl020Action.class);

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return GSConstPortal.PLUGIN_ID;
    }

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
        PrjPtl020Form prjForm = (PrjPtl020Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("selectProject")) {
            //TODO状態内訳クリック
            forward = __selectProject(map, prjForm, req, res, con);

        } else if (cmd.equals("prjChangeCombo")) {
            //プラグインポートレットコンボ変更
            forward = __changeCombo(map, prjForm, req, res, con);
        } else if (cmd.equals("prevPage")) {
            //前ページクリック
            prjForm.setPrjptl020pageTop(prjForm.getPrjptl020pageTop() - 1);
            forward = __doInit(map, prjForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            prjForm.setPrjptl020pageTop(prjForm.getPrjptl020pageTop() + 1);
            forward = __doInit(map, prjForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, prjForm, req, res, con);

        }

        log__.debug("END");
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
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        PrjPtl020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        //ログインユーザSIDを取得
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        con.setAutoCommit(true);
        PrjPtl020Biz biz = new PrjPtl020Biz();

        PrjPtl020ParamModel paramMdl = new PrjPtl020ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(getRequestModel(req), paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] プラグインポートレットコンボ変更時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException 実行例外
     */
    private ActionForward __changeCombo(ActionMapping map,
        PrjPtl020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException {

        String screenId = form.getPtl080PluginPortlet();
        if (StringUtil.isNullZeroString(screenId)) {
            return map.getInputForward();
        }

        return map.findForward(screenId);
    }

    /**
     * <br>[機  能] TODO状態内訳クリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws GSAuthenticateException GS用汎実行例外
     */
    private ActionForward __selectProject(ActionMapping map,
        PrjPtl020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException, GSAuthenticateException {

        PrjPtl020Biz biz = new PrjPtl020Biz();
        boolean commit = false;

        PluginConfig pconfig = getPluginConfig(req);

        try {

            //登録処理
            PrjPtl020ParamModel paramMdl = new PrjPtl020ParamModel();
            paramMdl.setParam(form);
            biz.insertData(paramMdl, con, pconfig);
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("プラグイン追加処理エラー", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        GsMessage gsMsg = new GsMessage(req);

        //ログ出力処理
        PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
        String opCode = gsMsg.getMessage(req, "cmn.entry");
        String pluginPortlet = gsMsg.getMessage(req, "plugin.portlet");

        prjBiz.outPutLog(
                map, req, res, opCode, GSConstLog.LEVEL_INFO, pluginPortlet);

        return map.getInputForward();
    }

}

