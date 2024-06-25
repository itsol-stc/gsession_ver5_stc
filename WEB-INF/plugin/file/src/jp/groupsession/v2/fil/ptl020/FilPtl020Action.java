package jp.groupsession.v2.fil.ptl020;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.fil.AbstractFileAdminAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <p>ポータル キャビネットツリー管理画面Action
 * @author JTS
 */
public class FilPtl020Action extends AbstractFileAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilPtl020Action.class);

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
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        FilPtl020Form ptlForm = (FilPtl020Form) form;

        if (cmd.equals("selectCabinet")) {
            //キャビネット名クリック
            forward = __selectCabinet(map, ptlForm, req, res, con);

        } else if (cmd.equals("backList")) {
            //戻るボタンクリック
            forward = map.findForward("ptlList");

        } else if (cmd.equals("filchangeCombo")) {
            //プラグインポートレットコンボ変更
            forward = __changeCombo(map, ptlForm, req, res, con);

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
     * @throws SQLException 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        FilPtl020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException {
        FilPtl020Biz biz = new FilPtl020Biz(con, getRequestModel(req));

        FilPtl020ParamModel paramMdl = new FilPtl020ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] キャビネット名クリック時の処理
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
     */
    private ActionForward __selectCabinet(ActionMapping map,
        FilPtl020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException, GSException {

        FilPtl020Biz biz = new FilPtl020Biz(con, getRequestModel(req));
        boolean commit = false;

        BaseUserModel usModel = getSessionUserModel(req);
        if (usModel == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        PluginConfig pconfig = getPluginConfig(req);

        try {

            //登録処理
            FilPtl020ParamModel paramMdl = new FilPtl020ParamModel();
            paramMdl.setParam(form);
            biz.insertData(paramMdl, pconfig);
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
        FilCommonBiz filCmnBiz = new FilCommonBiz(getRequestModel(req), con);
        String opCode = gsMsg.getMessage(req, "cmn.entry");
        String value = gsMsg.getMessage("plugin.portlet");
        String dspName = gsMsg.getMessage("ptl.ptl040.1") + " " + gsMsg.getMessage("fil.ptl020.1");

        filCmnBiz.outPutLogNoDspName(
                opCode, GSConstLog.LEVEL_INFO, value, map.getType(), null, dspName);

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
        FilPtl020Form form,
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
}

