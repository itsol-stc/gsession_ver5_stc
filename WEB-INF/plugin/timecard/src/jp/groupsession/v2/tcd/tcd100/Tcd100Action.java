package jp.groupsession.v2.tcd.tcd100;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.tcd.AbstractTimecardAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] タイムカード 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd100Action extends AbstractTimecardAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd100Action.class);

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
        log__.debug("Tcd100Action_START");
        ActionForward forward = null;
        Tcd100Form tcdForm = (Tcd100Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("basicSetting")) {
            //基本設定
            forward = map.findForward("tcd080");
        } else if (cmd.equals("tcd100back")) {
            //戻る
            forward = __doBack(map, tcdForm);
        } else {
            //メニュー表示
            forward = map.getInputForward();
        }
        log__.debug("Tcd100Action_END");
        return forward;
    }


    /**
     * <br>戻るボタンクリック時の処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return アクションフォーム
     */
    public ActionForward __doBack(ActionMapping map, Tcd100Form form) {
        ActionForward forward = null;

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            forward = map.findForward("mainPriSetting");
        } else {
            forward = map.findForward("tcd010");
        }

        return forward;
    }
}
