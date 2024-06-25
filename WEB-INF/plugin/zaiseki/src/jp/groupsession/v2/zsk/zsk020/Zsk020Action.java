package jp.groupsession.v2.zsk.zsk020;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.zsk.AbstractZaisekiAdminAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 在席管理 管理者設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk020Action extends AbstractZaisekiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk020Action.class);

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

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Zsk020Form zskForm = (Zsk020Form) form;
        if (cmd.equals("zsk010")) {
            //戻る
            forward = __doBack(map, zskForm);
        } else if (cmd.equals("zsk030")) {
            //在席表設定
            forward = map.findForward("zsk030");
        } else if (cmd.equals("zsk060")) {
            //共有範囲設定
            forward = map.findForward("zsk060");
        } else if (cmd.equals("zsk110")) {
            //定時一括更新
            forward = map.findForward("zsk110");
        } else if (cmd.equals("zsk140")) {
            //座席表メンバー表示一括設定
            forward = map.findForward("zsk140");
        } else {
            //初期表示
            forward = __doInit(map, zskForm, req, res, con);
        }
        return forward;
    }

    /**
     * 初期表示処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doInit(ActionMapping map, Zsk020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        resetToken(req);
        return map.getInputForward();
    }

    /**
     * 戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return Forward
     */
    private ActionForward __doBack(ActionMapping map, Zsk020Form form) {
        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            //メイン管理者設定画面へ戻る。
            return map.findForward("mainAdmSetting");
        }
        return map.findForward("zsk010");

    }

}
