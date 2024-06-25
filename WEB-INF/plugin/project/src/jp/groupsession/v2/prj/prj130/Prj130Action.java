package jp.groupsession.v2.prj.prj130;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.prj.AbstractProjectTemplateAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.prj020.Prj020Action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトテンプレート画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj130Action extends AbstractProjectTemplateAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj130Action.class);
    /** CMD:戻るボタン押下 */
    public static final String CMD_BACK = Prj020Action.CMD_BACK_REDRAW;
    /** CMD:追加ボタン押下 */
    public static final String CMD_ADD = "tmpAdd";
    /** CMD:テンプレート名称リンク押下(編集) */
    public static final String CMD_TMP_SELECT = "tmpSelect";
    /** CMD:テンプレート名称リンク押下(確認 */
    public static final String CMD_TMP_CHECK = "tmpCheck";

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
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Prj130Form thisForm = (Prj130Form) form;

        forward = _immigration(map, form, req, res, con);
        if (forward != null) {
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        if (CMD_BACK.equals(cmd)) {
            log__.debug("戻るボタン押下");
            forward = __doBack(map, thisForm, req, res, con);
        } else if (CMD_ADD.equals(cmd)) {
            log__.debug("追加ボタン押下");
            forward = map.findForward(CMD_ADD);
        } else if (CMD_TMP_SELECT.equals(cmd)) {
            log__.debug("テンプレリンク押下(編集)");
            forward = map.findForward(CMD_TMP_SELECT);
        } else if (CMD_TMP_CHECK.equals(cmd)) {
            log__.debug("テンプレリンク押下(確認)");
            forward = map.findForward(CMD_TMP_CHECK);
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Prj130Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws SQLException {

        try {
            con.setAutoCommit(true);
            //テンプレート一覧リストセット
            Prj130Biz biz = new Prj130Biz(con);

            Prj130ParamModel paramMdl = new Prj130ParamModel();
            paramMdl.setParam(form);
            biz.setTmpList(paramMdl, getSessionUserModel(req).getUsrsid());
            paramMdl.setFormData(form);


        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 戻るボタン押下
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doBack(ActionMapping map,
                                    Prj130Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) {

        ActionForward forward = null;
        int tmpMode = form.getPrjTmpMode();

        //テンプレート表示モード = 個人テンプレート
        if (tmpMode == GSConstProject.MODE_TMP_KOJIN) {

            log__.debug("個人テンプレートモード");
            forward = map.findForward("backKojinSettei");

        //テンプレート表示モード = 共有テンプレート
        } else if (tmpMode == GSConstProject.MODE_TMP_KYOYU) {

            log__.debug("共有テンプレートモード");
            forward = map.findForward("backKanriSettei");

        //テンプレート表示モード = テンプレート選択
        } else if (tmpMode == GSConstProject.MODE_TMP_SELECT) {

            log__.debug("テンプレート選択モード");
            forward = map.findForward(CMD_BACK);

        }

        return forward;
    }
}