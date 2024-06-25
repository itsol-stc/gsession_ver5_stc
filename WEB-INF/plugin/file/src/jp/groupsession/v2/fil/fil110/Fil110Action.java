package jp.groupsession.v2.fil.fil110;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] 個人設定メニュー画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil110Action extends AbstractFileAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil110Action.class);

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
        log__.debug("fil110Action START");

        ActionForward forward = null;
        Fil110Form thisForm = (Fil110Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fil110back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("fil110dspConf")) {
            //表示設定リンククリック
            forward = map.findForward("fil120");

        } else if (cmd.equals("fil110smailConf")) {
            //ショートメール通知設定リンククリック
            forward = map.findForward("fil130");

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
                                    Fil110Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {
        con.setAutoCommit(true);
        //ショートメール使用可能区分
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        CommonBiz cmnBiz = new CommonBiz();
        if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig)) {
            form.setFil110smailUseKbn(String.valueOf(GSConstMain.PLUGIN_USE));

            FilCommonBiz filBiz = new FilCommonBiz(getRequestModel(req), con);
            FileAconfModel aconfMd = filBiz.getFileAconfModel();
            form.setFil110SmailSendKbn(aconfMd.getFacSmailSendKbn());
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
    private ActionForward __doBack(ActionMapping map, Fil110Form form) {

        ActionForward forward = null;

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            forward = map.findForward("mainPriSetting");
        } else if (form.getBackDsp().equals(GSConstFile.MOVE_TO_FIL040)) {
            forward = map.findForward("fil040");
        } else {
            forward = map.findForward("cabinetMain");
        }
        return forward;
    }
}

