package jp.groupsession.v2.ntp.ntp120;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.AbstractNippouMstmntAction;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp130.Ntp130Form;

/**
 * <br>[機  能] 日報 マスタメンテナンス画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp120Action extends AbstractNippouMstmntAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp120Action.class);

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
                                        Connection con)
        throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Ntp120Form thisForm = (Ntp120Form) form;

        if (cmd.equals("backNtp120")) {
            forward = map.findForward("ntp080");
        } else if (cmd.equals("ntp130")) {
            Ntp130Form ntp130Form = new Ntp130Form();
            ntp130Form.setNtp130ReturnPage("ntp120");
            ntp130Form.setNtp130DspMode("search");
            req.setAttribute("ntp130Form", ntp130Form);
            forward = map.findForward("ntp130");
        } else if (cmd.equals("ntp140")) {
            forward = map.findForward("ntp140");
        } else if (cmd.equals("nippou")) {
            forward = map.findForward("ntp010");
        } else if (cmd.equals("anken")) {
            forward = map.findForward("ntp060");
        } else if (cmd.equals("bunseki")) {
            //分析
            forward = map.findForward("bunseki");
        } else if (cmd.equals("target")) {
            //目標設定画面へ
            forward = map.findForward("target");
        } else if (cmd.equals("ntp150")) {
            forward = map.findForward("ntp150");
        } else if (cmd.equals("ntp170")) {
            forward = map.findForward("ntp170");
        } else if (cmd.equals("ntp180")) {
            forward = map.findForward("ntp180");
        } else if (cmd.equals("ntp190")) {
            forward = map.findForward("ntp190");
        } else if (cmd.equals("ntp230")) {
            forward = map.findForward("ntp230");
        } else if (cmd.equals("ktool")) {
            //管理者ツール
            forward = map.findForward("ktool");
        } else if (cmd.equals("pset")) {
            //個人設定
            forward = map.findForward("pset");
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Ntp120Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        int sessionUserSid = this.getSessionUserModel(req).getUsrsid();

        BaseUserModel umodel = getSessionUserModel(req);
        CommonBiz commonBiz = new CommonBiz();
        if (commonBiz.isPluginAdmin(con, umodel, GSConstNippou.PLUGIN_ID_NIPPOU)) {
            form.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            form.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }

        Ntp120Biz biz = new Ntp120Biz(con, getRequestModel(req));

        Ntp120ParamModel paramMdl = new Ntp120ParamModel();
        paramMdl.setParam(form);
        //取込みファイル名称取得
        biz.setInitData(paramMdl, sessionUserSid, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }
}
