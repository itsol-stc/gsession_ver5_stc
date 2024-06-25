package jp.groupsession.v2.anp.anp030;


import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.anp.AbstractAnpiAction;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 個人設定メニュー画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp030Action extends AbstractAnpiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp030Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     *
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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        Anp030Form uform = (Anp030Form) form;

        if (cmd.equals("anp030back")) {
            //戻る
            forward = __doBack(map, uform, req, res, con);

        } else if (cmd.equals("anp030base")) {
            //表示設定
            forward = map.findForward("base");

        } else if (cmd.equals("anp030contact")) {
            //連絡先設定
            forward = map.findForward("contact");

        } else {
            //初期化
            forward = map.getInputForward();
        }

        return forward;
    }

    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Anp030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            //メイン管理者画面へ遷移する。
            return map.findForward("mainPriSetting");
        }

        return map.findForward("anpimain");
    }

}
