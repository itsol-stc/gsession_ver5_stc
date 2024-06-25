package jp.groupsession.v2.prj.prj140kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.prj.AbstractProjectTemplateAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.prj020.Prj020Action;
import jp.groupsession.v2.prj.prj140.Prj140Action;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトテンプレート登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj140knAction extends AbstractProjectTemplateAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj140knAction.class);
    /** CMD:選択ボタンクリック */
    public static final String CMD_SELECT_CLICK = Prj020Action.CMD_EDIT_CLICK;
    /** CMD:戻るボタンクリック(テンプレ選択時) */
    public static final String CMD_BACK = "backToTmpList";
    /** CMD:戻るボタンクリック */
    public static final String CMD_BACK_CLICK = Prj140Action.CMD_BACK_REDRAW;

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
        Prj140knForm thisForm = (Prj140knForm) form;

        forward = _immigration(map, form, req, res, con);
        if (forward != null) {
            return forward;
        }

        PrjCommonBiz prjBiz = new PrjCommonBiz(con, getRequestModel(req));
        int prtKbn = thisForm.getPrjTmpMode();

        if (thisForm.getPrjTmpMode() == GSConstProject.MODE_TMP_KYOYU) {
            prtKbn = GSConstProject.PRT_KBN_KYOYU;
        }
        if (thisForm.getPrjTmpMode() == GSConstProject.MODE_TMP_KOJIN) {
            prtKbn = GSConstProject.PRT_KBN_KOJIN;
        }

        BaseUserModel buMdl = getSessionUserModel(req);
        if (thisForm.getPrtSid() != 0
                && !prjBiz.validateCheckPower(
                        con, thisForm.getPrtSid(), prtKbn, buMdl)) {
            return getSubmitErrorPage(map, req);
        }

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        if (CMD_SELECT_CLICK.equals(cmd)) {
            log__.debug("選択ボタンクリック");
            forward = __doTemplateSelect(map, thisForm, req, res, con);

        } else if (CMD_BACK_CLICK.equals(cmd)) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward(CMD_BACK_CLICK);

        } else if (CMD_BACK.equals(cmd)) {
            log__.debug("戻るボタンクリック");
            forward = map.findForward(CMD_BACK);

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
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    private ActionForward __doInit(ActionMapping map,
                                    Prj140knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException, IOToolsException {

        con.setAutoCommit(true);

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        //初期表示情報を画面にセットする
        Prj140knBiz biz = new Prj140knBiz(con, getRequestModel(req));

        Prj140knParamModel paramMdl = new Prj140knParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, pconfig, userSid, getTempPath(req));
        paramMdl.setFormData(form);

        // トランザクショントークン設定
        this.saveToken(req);

        return map.getInputForward();
    }


    /**
     * <br>[機  能] 選択ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    private ActionForward __doTemplateSelect(ActionMapping map,
                                              Prj140knForm form,
                                              HttpServletRequest req,
                                              HttpServletResponse res,
                                              Connection con)
        throws SQLException, IOToolsException {

        Prj140knBiz biz = new Prj140knBiz(con, getRequestModel(req));

        Prj140knParamModel paramMdl = new Prj140knParamModel();
        paramMdl.setParam(form);
        biz.setTemplateData(paramMdl, getTempPath(req));
        paramMdl.setFormData(form);
        return map.findForward(CMD_SELECT_CLICK);
    }
}