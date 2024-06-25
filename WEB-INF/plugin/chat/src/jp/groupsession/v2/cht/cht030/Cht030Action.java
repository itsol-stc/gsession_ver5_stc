package jp.groupsession.v2.cht.cht030;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cht.AbstractChatAction;
import jp.groupsession.v2.man.GSConstMain;

/**
 *
 * <br>[機  能] チャット 個人設定 のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht030Action extends AbstractChatAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht030Action.class);

    /**
     * <br>アクション実行
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
        ActionForward forward = null;
        Cht030Form thisForm = (Cht030Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (cmd.equals("cht120")) {
            // 通知設定
            forward = map.findForward("cht120");
        } else if (cmd.equals("cht130")) {
            // 表示設定
            forward = map.findForward("cht130");
        } else if (cmd.equals("cht010")) {
            //戻るボタン
            forward = __doBack(map, thisForm);
        } else {
            // 初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Cht030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __doBack(ActionMapping map, Cht030Form form) {

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            return map.findForward("mainPriSetting");
        }

        return map.findForward("cht010");
    }

}
