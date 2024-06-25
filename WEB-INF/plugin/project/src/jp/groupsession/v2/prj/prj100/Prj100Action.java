package jp.groupsession.v2.prj.prj100;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.prj.AbstractProjectAdminAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] プロジェクト管理 管理者設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj100Action extends AbstractProjectAdminAction {

    /** CMD:戻るクリック */
    public static final String CMD_BACK = "back100";
    /** CMD:登録権限設定クリック */
    public static final String CMD_KENGEN_EDIT = "kengenEdit";
    /** CMD:プロジェクトテンプレート(共有)クリック */
    public static final String CMD_TMP_KYOYU = "tempKyoyu";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj100Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("Prj100Action start");
        ActionForward forward = null;

        Prj100Form thisForm = (Prj100Form) form;

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        if (CMD_BACK.equals(cmd)) {
            log__.debug("戻る");
            forward = __doBack(map, thisForm);

        } else if (CMD_KENGEN_EDIT.equals(cmd)) {
            log__.debug("登録権限設定");
            forward = map.findForward(CMD_KENGEN_EDIT);

        } else if (CMD_TMP_KYOYU.equals(cmd)) {
            log__.debug("プロジェクトテンプレート(共有)管理");
            forward = map.findForward(CMD_TMP_KYOYU);

        } else {
            log__.debug("初期表示");
            thisForm.setPrjTmpMode(GSConstProject.MODE_TMP_KYOYU);
            forward = map.getInputForward();
        }

        log__.debug("Prj100Action end");
        return forward;
    }

    /**
     * <br>[機  能] 戻る処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form フォーム
     * @return ActionForward
     */
    public ActionForward __doBack(
        ActionMapping map,
        Prj100Form form) {

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_ADM_SETTING) {
            return map.findForward("mainAdmSetting");
        }

        return map.findForward(CMD_BACK);
    }
}