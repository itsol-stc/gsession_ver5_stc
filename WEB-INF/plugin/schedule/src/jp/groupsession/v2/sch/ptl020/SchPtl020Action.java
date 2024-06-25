package jp.groupsession.v2.sch.ptl020;

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
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.sch.AbstractScheduleAdminAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール ポートレット グループスケジュール選択画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchPtl020Action extends AbstractScheduleAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchPtl020Action.class);

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
        SchPtl020Form ptlForm = (SchPtl020Form) form;

        if (cmd.equals("selectGroup")) {
            //グループ名クリック
            forward = __selectGroup(map, ptlForm, req, res, con);

        } else if (cmd.equals("backList")) {
            //戻るボタンクリック
            forward = map.findForward("ptlList");

        } else if (cmd.equals("schChangeCombo")) {
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
        SchPtl020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException {
        SchPtl020Biz biz = new SchPtl020Biz(getRequestModel(req));
        SchPtl020ParamModel paramMdl = new SchPtl020ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);



        return map.getInputForward();
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
     * @throws GSException GS用汎実行例外
     */
    private ActionForward __selectGroup(ActionMapping map,
        SchPtl020Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException, GSException {

        SchPtl020Biz biz = new SchPtl020Biz(getRequestModel(req));
        boolean commit = false;

        BaseUserModel usModel = getSessionUserModel(req);
        if (usModel == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        PluginConfig pconfig = getPluginConfig(req);

        try {

            //登録処理
            SchPtl020ParamModel paramMdl = new SchPtl020ParamModel();
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

        GsMessage gsMsg = new GsMessage();

        //ログ出力処理

        String msg = gsMsg.getMessage(req, "cmn.entry");

        SchCommonBiz schBiz = new SchCommonBiz(con, getRequestModel(req));
        String value = "";
        value += gsMsg.getMessage("plugin.portlet");
                String  dspName = gsMsg.getMessage("ptl.ptl040.1");
        schBiz.outPutLogNoDspName(map, req, res, msg,
                GSConstLog.LEVEL_INFO, value, null, dspName);
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
        SchPtl020Form form,
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

