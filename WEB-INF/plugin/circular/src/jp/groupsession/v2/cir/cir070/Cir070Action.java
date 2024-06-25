package jp.groupsession.v2.cir.cir070;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cir.AbstractCircularAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sml.sml120.Sml120Action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 回覧板 個人設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir070Action extends AbstractCircularAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml120Action.class);

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
     * @see jp.groupsession.v2.cir.AbstractCircularAction
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
        Cir070Form thisForm = (Cir070Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //戻るボタン押下
        if (cmd.equals("backToCirList")) {
            log__.debug("戻るボタン押下");
            forward = __doBack(map, thisForm);
        //回覧板基本設定
        } else if (cmd.equals("cirKsetting")) {
            log__.debug("回覧板基本設定");
            forward = map.findForward("basic");
        //回覧板自動削除設定
        } else if (cmd.equals("cirAutoDelete")) {
            log__.debug("回覧板自動削除設定");
            forward = map.findForward("cirAutoDelete");
        //回覧板手動削除
        } else if (cmd.equals("cirManualDelete")) {
            log__.debug("回覧板手動削除");
            forward = map.findForward("cirManualDelete");
        } else if (cmd.equals("cirIniset")) {
        //初期値設定
            log__.debug("初期値設定");
            forward = map.findForward("cirIniset");
        //アカウント管理
        } else if (cmd.equals("circularAccount")) {
            log__.debug("アカウント管理");
            forward = map.findForward("circularAccount");
        } else {
            log__.debug("初期表示");
            //forward = map.getInputForward();
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
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(ActionMapping map,
                                    Cir070Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        con.setAutoCommit(true);
        Cir070ParamModel paramMdl = new Cir070ParamModel();
        paramMdl.setParam(form);
        Cir070Biz biz = new Cir070Biz();
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

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
    private ActionForward __doBack(ActionMapping map, Cir070Form form) {

        if (form.getBackScreen() == GSConstMain.BACK_MAIN_PRI_SETTING) {
            return map.findForward("mainPriSetting");
        }

        return map.findForward("backToCirList");
    }

}