package jp.groupsession.v2.prj.prj090;

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
import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 個人設定 表示件数設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj090Action extends AbstractProjectAction {

    /** CMD:戻るクリック */
    public static final String CMD_BACK = "back090";
    /** CMD:設定クリック */
    public static final String CMD_EDIT = "edit";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj090Action.class);

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

        log__.debug("Prj090Action start");
        ActionForward forward = null;

        Prj090Form thisForm = (Prj090Form) form;

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        if (CMD_BACK.equals(cmd)) {
            log__.debug("戻る");
            forward = map.findForward(CMD_BACK);

        } else if (CMD_EDIT.equals(cmd)) {
            log__.debug("設定");
            forward = __doSet(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("Prj090Action end");
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
        Prj090Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        //セッションユーザModel
        BaseUserModel buMdl = getSessionUserModel(req);

        Prj090Biz biz = new Prj090Biz(con);

        Prj090ParamModel paramMdl = new Prj090ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, buMdl.getUsrsid());
        paramMdl.setFormData(form);

        //トランザクショントークン設定
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 設定ボタンクリック時処理
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
    private ActionForward __doSet(
        ActionMapping map,
        Prj090Form form,
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

            //プロジェクト個人設定の更新
            Prj090Biz biz = new Prj090Biz(con);

            Prj090ParamModel paramMdl = new Prj090ParamModel();
            paramMdl.setParam(form);
            int addEditFlg = biz.update(paramMdl, buMdl.getUsrsid());
            paramMdl.setFormData(form);

            //ログ出力処理
            GsMessage gsMsg = new GsMessage(req);
            PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
            String opCode = "";

            if (addEditFlg == Integer.parseInt(GSConstProject.CMD_MODE_ADD)) {
                opCode = getInterMessage(req, "cmn.entry");
            } else {
                opCode = getInterMessage(req, "cmn.change");
            }

            String message = "[" + getInterMessage(req, "project.prj090.1") + "]"
            + paramMdl.getPrj090prjViewCnt() + "\r\n[" + getInterMessage(req, "project.prj090.3")
            + "]" + paramMdl.getPrj090todoViewCnt();
            message += "\r\n[" + getInterMessage(req, "schedule.108")
            + getInterMessage(req, "cmn.display.settings") + "]";
            if (paramMdl.getPrj090SchDsp() == GSConstProject.DSP_TODO_SHOW) {
                message += getInterMessage(req, "cmn.display.ok");
            } else {
                message += getInterMessage(req, "cmn.dont.show");
            }

            prjBiz.outPutLog(
                    map, req, res, opCode, GSConstLog.LEVEL_INFO, message);

            //表示件数設定完了画面へ
            forward = __doCompDsp(map, form, req);
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
        Prj090Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        GsMessage gsMsg = new GsMessage();
        //表示件数
        String textNumberDisp = gsMsg.getMessage(req, "cmn.display.settings");
        //表示件数設定完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward(CMD_BACK);
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage("settei.kanryo.object", textNumberDisp));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

}
