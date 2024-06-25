package jp.groupsession.v2.bmk.ptl020;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.bmk.AbstractBookmarkAdminAction;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>ポータル グループブックマーク管理画面のアクション
 * @author JTS
 */
public class BmkPtl020Action extends AbstractBookmarkAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkPtl020Action.class);

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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        BmkPtl020Form bmkForm = (BmkPtl020Form) form;

        if (cmd.equals("selectBmk")) {
            //グループ名クリック
            forward = __selectBmk(map, bmkForm, req, res, con);
        } else if (cmd.equals("bmkChangeCombo")) {
            //プラグインポートレットコンボ変更
            forward = __changeCombo(map, bmkForm, req, res, con);
        } else {
            log__.debug("初期表示");
            //初期表示
            forward = __doInit(map, bmkForm, req, con);
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
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        BmkPtl020Form form,
        HttpServletRequest req,
        Connection con
        )
        throws Exception {

        con.setAutoCommit(true);
        BmkPtl020Biz biz = new BmkPtl020Biz(getRequestModel(req));

        BmkPtl020ParamModel paramMdl = new BmkPtl020ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
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
        BmkPtl020Form form,
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
     * <br>[機  能] グループ名クリック時の処理
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
    private ActionForward __selectBmk(ActionMapping map,
        BmkPtl020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException, GSAuthenticateException {

        BmkPtl020Biz biz = new BmkPtl020Biz(getRequestModel(req));
        boolean commit = false;

        PluginConfig pconfig = getPluginConfig(req);
        PluginConfig mainPconfig = getPluginConfigForMain(pconfig, con, -1);

        try {

            //登録処理
            BmkPtl020ParamModel paramMdl = new BmkPtl020ParamModel();
            paramMdl.setParam(form);
            biz.insertData(paramMdl, con, mainPconfig);
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

        GsMessage gsMsg = new GsMessage();

        //ログ出力処理
        BookmarkBiz bmkBiz = new BookmarkBiz(con, getRequestModel(req));
        String opCode = gsMsg.getMessage(req, "cmn.entry");
        String pluginPortlet = gsMsg.getMessage(req, "plugin.portlet");

        bmkBiz.outPutLog(opCode, GSConstLog.LEVEL_INFO, pluginPortlet, map.getType());

        return map.getInputForward();
    }

}

