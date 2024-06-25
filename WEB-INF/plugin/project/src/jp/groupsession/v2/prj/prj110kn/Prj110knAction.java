package jp.groupsession.v2.prj.prj110kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.prj.AbstractProjectAdminAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 管理者設定 登録権限設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj110knAction extends AbstractProjectAdminAction {

    /** CMD:戻るクリック */
    public static final String CMD_BACK = "back110kn";
    /** CMD:確定クリック */
    public static final String CMD_KAKUTEI = "kakutei";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj110knAction.class);

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

        log__.debug("Prj110knAction start");
        ActionForward forward = null;

        Prj110knForm thisForm = (Prj110knForm) form;

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        if (CMD_BACK.equals(cmd)) {
            log__.debug("戻る");
            forward = map.findForward(CMD_BACK);

        } else if (CMD_KAKUTEI.equals(cmd)) {
            log__.debug("確定");
            forward = __doKakutei(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("Prj110knAction end");
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
    private ActionForward __doInit(
        ActionMapping map,
        Prj110knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        Prj110knBiz biz = new Prj110knBiz(con, getRequestModel(req));
        Prj110knParamModel paramMdl = new Prj110knParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);

        //トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doKakutei(
        ActionMapping map,
        Prj110knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        ActionForward forward = null;

        //セッションユーザModel
        BaseUserModel buMdl = getSessionUserModel(req);

        boolean commit = false;
        try {

            if (!isTokenValid(req, true)) {
                log__.info("２重投稿");
                return getSubmitErrorPage(map, req);
            }

            //管理者設定情報を更新
            Prj110knBiz biz = new Prj110knBiz(con, getRequestModel(req));
            Prj110knParamModel paramMdl = new Prj110knParamModel();
            paramMdl.setParam(form);
            biz.update(paramMdl, buMdl.getUsrsid());
            paramMdl.setFormData(form);

            //表示件数設定完了画面へ
            forward = __doCompDsp(map, form, req);

            //ログ出力処理
            GsMessage gsMsg = new GsMessage(req);
            PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
            String opCode = getInterMessage(req, "cmn.change");
            String message = "[" + getInterMessage(req, "project.39") + "]";
            if (paramMdl.getPrj110edit() == GSConstProject.PRJ_EDIT_KENGEN_ADM) {
                message += getInterMessage(req, "project.prj110.1");
            } else {
                message += getInterMessage(req, "project.prj110.2");
            }

            prjBiz.outPutLog(
                    map, req, res, opCode, GSConstLog.LEVEL_INFO, message);
            commit = true;

        } catch (SQLException e) {
            log__.error("プロジェクト個人設定の更新に失敗", e);
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        return forward;
    }

    /**
     * <br>[機  能] 表示件数設定完了画面を表示する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doCompDsp(
        ActionMapping map,
        Prj110knForm form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        GsMessage gsMsg = new GsMessage();
        //実績 工数
        String textKengenEdit = gsMsg.getMessage(req, "project.src.25");
        //表示件数設定完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward(CMD_KAKUTEI);
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage("settei.kanryo.object", textKengenEdit));
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

}
