package jp.groupsession.v2.prj.prj190kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.prj.AbstractProjectAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 個人設定 ダッシュボード初期値設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj190knAction extends AbstractProjectAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj190knAction.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     * @see jp.co.sjts.util.struts.AbstractAction
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
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Prj190knForm thisForm = (Prj190knForm) form;

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        //設定
        if (cmd.equals("update")) {
            log__.debug("確定ボタン押下");
            forward = __doEdit(map, thisForm, req, res, con);
        //戻る
        } else if (cmd.equals("backPrj190")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("backPrj190");
        //初期表示
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(ActionMapping map,
                                    Prj190knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Prj190knBiz biz = new Prj190knBiz(con, getRequestModel(req));

        Prj190knParamModel paramMdl = new Prj190knParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, getSessionUserModel(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }


    /**
     * <br>[機  能] 確定ボタン押下
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doEdit(ActionMapping map,
                                    Prj190knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        ActionForward forward = null;

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            forward = getSubmitErrorPage(map, req);
            return forward;
        }

        //入力チェック
        ActionErrors errors = form.validatePrj190(con, getSessionUserModel(req), req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        con.setAutoCommit(false);
        boolean commit = false;

        try {

            Prj190knBiz biz = new Prj190knBiz(con, getRequestModel(req));

            Prj190knParamModel paramMdl = new Prj190knParamModel();
            paramMdl.setParam(form);
            int addEditFlg = biz.updateUserConf(paramMdl, getSessionUserModel(req));
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
            String message = "[" + getInterMessage(req, "project.11") + "]";
            message += "\r\n" + getInterMessage(req, "cmn.initial.display") + ":";
            if (paramMdl.getPrj190DefDsp() == GSConstProject.DSP_TODO) {
                message += getInterMessage(req, "project.prj220.1");
            } else {
                message += getInterMessage(req, "cmn.project");
            }
            message += "\r\n\r\n" + getInterMessage(req, "project.prj190.2") + "\r\n";
            message += getInterMessage(req, "cmn.date2") + ":";
            if (Integer.parseInt(paramMdl.getPrj190TodoDay()) == GSConstProject.DATE_ALL) {
                message += getInterMessage(req, "cmn.all") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190TodoDay()) == GSConstProject.DATE_TODAY) {
                message += getInterMessage(req, "cmn.today") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190TodoDay())
                    == GSConstProject.DATE_THE_PAST) {
                message += getInterMessage(req, "project.src.4") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190TodoDay())
                    == GSConstProject.DATE_THE_FUTURE) {
                message += getInterMessage(req, "project.src.3") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190TodoDay())
                    == GSConstProject.DATE_NOT_INPUT) {
                message += getInterMessage(req, "project.src.2") + "\r\n";
            }
            message += getInterMessage(req, "cmn.project") + ":";
            String target = "";
            if (Integer.parseInt(paramMdl.getPrj190TodoPrj()) == 0) {
                target = getInterMessage(req, "cmn.all");
            } else {
                target = biz.getTargetName(con, paramMdl.getPrj190TodoPrj());
            }
            message += target + "\r\n";
            message += getInterMessage(req, "cmn.status") + ":";
            if (Integer.parseInt(paramMdl.getPrj190TodoSts()) == GSConstProject.STATUS_ALL) {
                message += getInterMessage(req, "cmn.all") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190TodoSts())
                    == GSConstProject.STATUS_YOTEI_AND_MIKAN) {
                message += getInterMessage(req, "project.src.72") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190TodoSts())
                    == GSConstProject.STATUS_MIKAN) {
                message += getInterMessage(req, "project.src.71") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190TodoSts())
                    == GSConstProject.STATUS_KANRYO) {
                message += getInterMessage(req, "project.src.70") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190TodoSts())
                    == GSConstProject.STATUS_SINKO) {
                message += getInterMessage(req, "ntp.74") + "\r\n";
            }

            message += "\r\n" + getInterMessage(req, "project.prj190.2") + "\r\n";
            message += getInterMessage(req, "cmn.project") + ":";
            if (Integer.parseInt(paramMdl.getPrj190Project())
                    == GSConstProject.PRJ_MEMBER_NOT_END) {
                message += getInterMessage(req, "project.src.59");
            } else if (Integer.parseInt(paramMdl.getPrj190Project())
                    == GSConstProject.PRJ_MEMBER_END) {
                message += getInterMessage(req, "project.src.58");
            } else if (Integer.parseInt(paramMdl.getPrj190Project())
                    == GSConstProject.PRJ_MEMBER_ALL) {
                message += getInterMessage(req, "project.src.57");
            } else if (Integer.parseInt(paramMdl.getPrj190Project())
                    == GSConstProject.PRJ_OPEN_NOT_END) {
                message += getInterMessage(req, "project.src.63");
            } else if (Integer.parseInt(paramMdl.getPrj190Project())
                    == GSConstProject.PRJ_OPEN_END) {
                message += getInterMessage(req, "project.src.62");
            } else if (Integer.parseInt(paramMdl.getPrj190Project())
                    == GSConstProject.PRJ_OPEN_ALL) {
                message += getInterMessage(req, "project.src.61");
            } else if (Integer.parseInt(paramMdl.getPrj190Project())
                    == GSConstProject.PRJ_NOT_END_ALL) {
                message += getInterMessage(req, "project.src.60");
            } else if (Integer.parseInt(paramMdl.getPrj190Project())
                    == GSConstProject.PRJ_END_ALL) {
                message += getInterMessage(req, "project.src.53");
            } else if (Integer.parseInt(paramMdl.getPrj190Project())
                    == GSConstProject.PRJ_ALL) {
                message += getInterMessage(req, "cmn.allprojects");
            }

            message += "\r\n";
            message += "[" + getInterMessage(req, "project.12") + "]\r\n";
            message += "" + getInterMessage(req, "project.100") + ":";
            if (Integer.parseInt(paramMdl.getPrj190mainDspDate()) == GSConstProject.DATE_ALL) {
                message += getInterMessage(req, "cmn.all") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190mainDspDate())
                    == GSConstProject.DATE_TODAY) {
                message += getInterMessage(req, "cmn.today") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190mainDspDate())
                    == GSConstProject.DATE_THE_PAST) {
                message += getInterMessage(req, "project.src.4") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190mainDspDate())
                    == GSConstProject.DATE_THE_FUTURE) {
                message += getInterMessage(req, "project.src.3") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190mainDspDate())
                    == GSConstProject.DATE_NOT_INPUT) {
                message += getInterMessage(req, "project.src.2") + "\r\n";
            }
            message += "" + getInterMessage(req, "cmn.status") + ":";
            if (Integer.parseInt(paramMdl.getPrj190mainDspStatus())
                    == GSConstProject.STATUS_ALL) {
                message += getInterMessage(req, "cmn.all") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190mainDspStatus())
                    == GSConstProject.STATUS_YOTEI_AND_MIKAN) {
                message += getInterMessage(req, "project.src.72") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190mainDspStatus())
                    == GSConstProject.STATUS_0) {
                message += getInterMessage(req, "project.src.71") + "\r\n";
            } else if (Integer.parseInt(paramMdl.getPrj190mainDspStatus())
                    == GSConstProject.STATUS_100) {
                message += getInterMessage(req, "project.src.70") + "\r\n";
            }
            message += "" + getInterMessage(req, "cmn.member") + ":";
            if (Integer.parseInt(paramMdl.getPrj190mainDspMember())
                    == GSConstProject.MEMBER_ALL) {
                message += getInterMessage(req, "project.src.11");
            } else {
                message += getInterMessage(req, "project.src.12");
            }


            message += "\r\n";
            message += "[" + getInterMessage(req, "project.prj080.9") + "]\r\n";
            if (paramMdl.getPrj190todoInputKbn() == GSConstProject.DSP_TODO_EASY) {
                message += getInterMessage(req, "project.prj050.2");
            } else {
                message += getInterMessage(req, "project.prj050.1");
            }

            prjBiz.outPutLog(
                    map, req, res, opCode, GSConstLog.LEVEL_INFO, message);

            commit = true;

            forward = __doCompDsp(map, form, req);

            return forward;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 設定完了画面を表示する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map,
                                       Prj190knForm form,
                                       HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        GsMessage gsMsg = new GsMessage();
        //ダッシュボード初期値
        String textDashBoardInit = gsMsg.getMessage("cmn.default.setting");
        //表示件数設定完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("prj080");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage(
                        "settei.kanryo.object",
                        textDashBoardInit));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}