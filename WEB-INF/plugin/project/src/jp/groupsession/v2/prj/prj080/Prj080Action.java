package jp.groupsession.v2.prj.prj080;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] プロジェクト管理 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj080Action extends AbstractProjectAction {

    /** CMD:戻るクリック */
    public static final String CMD_BACK = "back080";
    /** CMD:表示件数設定クリック */
    public static final String CMD_KENSU_EDIT = "kensuEdit";
    /** CMD:プロジェクトテンプレート(個人)クリック */
    public static final String CMD_TMP_KOJIN = "tempKojin";
    /** CMD:ダッシュボード初期値設定クリック */
    public static final String CMD_DASH_BOARD = "dashBoard";
    /** CMD:プロジェクトメイン初期値設定クリック */
    public static final String CMD_PRJ_MAIN = "projectMain";
    /** CMD:スケジュール表示設定クリック */
    public static final String CMD_PRJ_SCH = "projectSch";
    /** CMD:TODO登録画面初期値設定クリック */
    public static final String CMD_TODO_SET = "todoset";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj080Action.class);

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

        log__.debug("Prj080Action start");
        ActionForward forward = null;

        Prj080Form thisForm = (Prj080Form) form;

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        if (CMD_BACK.equals(cmd)) {
            log__.debug("戻る");
            forward = __doBack(map, thisForm);

        } else if (CMD_KENSU_EDIT.equals(cmd)) {
            log__.debug("表示件数設定");
            forward = map.findForward(CMD_KENSU_EDIT);

        } else if (CMD_TMP_KOJIN.equals(cmd)) {
            log__.debug("プロジェクトテンプレート(個人)管理");
            forward = map.findForward(CMD_TMP_KOJIN);

        } else if (CMD_DASH_BOARD.equals(cmd)) {
            log__.debug("ダッシュボード初期値設定");
            forward = map.findForward(CMD_DASH_BOARD);

        } else if (CMD_PRJ_MAIN.equals(cmd)) {
            log__.debug("プロジェクトメイン初期値設定");
            forward = map.findForward(CMD_PRJ_MAIN);

        } else if (CMD_PRJ_SCH.equals(cmd)) {
            log__.debug("スケジュール表示設定");
            forward = map.findForward(CMD_PRJ_SCH);

        } else if (CMD_TODO_SET.equals(cmd)) {
            log__.debug("TODO登録画面初期値設定");
            forward = map.findForward(CMD_TODO_SET);

        } else {
            log__.debug("初期表示");
            thisForm.setPrjTmpMode(GSConstProject.MODE_TMP_KOJIN);
            forward = map.getInputForward();
        }

        log__.debug("Prj080Action end");
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
    private ActionForward __doBack(ActionMapping map, Prj080Form form) {

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            //メイン個人設定メニューへ戻る。
            return map.findForward("mainPriSetting");
        }

        return map.findForward(CMD_BACK);
    }
}