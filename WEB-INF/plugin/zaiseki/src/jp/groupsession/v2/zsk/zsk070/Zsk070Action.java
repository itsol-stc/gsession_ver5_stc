package jp.groupsession.v2.zsk.zsk070;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.zsk.AbstractZaisekiAction;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;

/**
 * <br>[機  能] 在席管理 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk070Action extends AbstractZaisekiAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk070Action.class);

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

        Zsk070Form zskForm = (Zsk070Form) form;
        if (cmd.equals("zsk010")) {
            //戻る
            forward = __doBack(map, zskForm);
        } else if (cmd.equals("zsk080")) {
            //共有範囲設定
            forward = map.findForward("zsk080");
        } else if (cmd.equals("zsk090")) {
            //自動リロード設定
            forward = map.findForward("zsk090");
        } else if (cmd.equals("zsk130")) {
            //個人設定
            forward = map.findForward("zsk130");
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
    private ActionForward __doInit(ActionMapping map, Zsk070Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        resetToken(req);

        ZsjCommonBiz zskBiz = new ZsjCommonBiz(getRequestModel(req));
        form.setZsk070canMemEdit(zskBiz.canEditViewMember(con));
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
    private ActionForward __doBack(ActionMapping map, Zsk070Form form) {

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            return map.findForward("mainPriSetting");
        }

        return map.findForward("zsk010");
    }
}
