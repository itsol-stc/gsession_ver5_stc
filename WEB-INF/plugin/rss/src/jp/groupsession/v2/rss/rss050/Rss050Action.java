package jp.groupsession.v2.rss.rss050;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rss.AbstractRssAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * <br>[機  能] RSSリーダー 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss050Action extends AbstractRssAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss050Action.class);

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
        Rss050Form myForm = (Rss050Form) form;

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("backRssList")) {
            log__.debug("戻るボタンクリック");
            forward = __doBack(map, myForm);

        } else if (cmd.equals("editMain")) {
            log__.debug("メイン画面表示設定クリック");
            forward = map.findForward("mainEdit");

        } else if (cmd.equals("newRssDate")) {
            log__.debug("新着RSS表示日数設定クリック");
            forward = map.findForward("newRssDate");

        } else {
            log__.debug("初期表示処理");
            forward = map.getInputForward();
        }

        return forward;
    }

    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __doBack(ActionMapping map, Rss050Form form) {

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            //メイン個人設定メニューへ戻る。
            return map.findForward("mainPriSetting");
        }

        return map.findForward("backRssList");
    }
}