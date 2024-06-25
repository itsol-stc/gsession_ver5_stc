package jp.groupsession.v2.rss.rss070;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rss.AbstractRssAdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * <br>[機  能] RSSリーダー 管理者設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss070Action extends AbstractRssAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss070Action.class);

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

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        Rss070Form thisForm = (Rss070Form) form;

        if (cmd.equals("backMain")) {
            log__.debug("戻るボタンクリック");
            //戻る
            forward = __doBack(map, thisForm);

        } else if (cmd.equals("rssMaintenance")) {
            log__.debug("メンテナンス画面表示設定クリック");
            forward = map.findForward("rssMaintenance");
        } else {
            log__.debug("初期表示処理");
            forward = map.getInputForward();
        }
        return forward;
    }

    /**
     * 戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return Forward
     */
    private ActionForward __doBack(ActionMapping map, Rss070Form form) {
        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            //メイン管理者設定画面へ戻る。
            return map.findForward("mainAdmSetting");
        }
        return map.findForward("backMain");

    }
}