package jp.groupsession.v2.ptl.ptl130kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ptl.AbstractPortalAdminAction;
import jp.groupsession.v2.ptl.PtlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] ポータル 権限設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl130knAction extends AbstractPortalAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl130knAction.class);

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
    */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Ptl130knForm rsvform = (Ptl130knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //確定ボタン押下
        if (cmd.equals("ptl130kakutei")) {
            log__.debug("確定ボタン押下");
            forward = __doKakutei(map, rsvform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("ptl130knback")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("ptl130knback");
        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, rsvform, req, res, con);
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
                                    Ptl130knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        ActionForward forward = null;

        forward = map.getInputForward();
        return forward;
    }

    /**
     * <br>[機  能] 確定ボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Adr040knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doKakutei(ActionMapping map, Ptl130knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ActionForward forward = null;

        //DB更新
        int count = 0;
        boolean commit = false;
        try {
            Ptl130knParamModel paramMdl = new Ptl130knParamModel();
            paramMdl.setParam(form);
            Ptl130knBiz biz = new Ptl130knBiz();
            count = biz.setPtlAconf(paramMdl, con);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }
        forward = __setCompPageParam(map, req, form);

        //ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = "";
        if (count < 1) {
            opCode = gsMsg.getMessage("cmn.entry");
        } else {
            opCode = gsMsg.getMessage("cmn.change");
        }

        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);
        ptlBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_INFO, "");

        return forward;
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Ptl130knForm form) {

        GsMessage gsMsg = new GsMessage();
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("ptl020");
        cmn999Form.setUrlOK(urlForward.getPath());


        //メッセージセット
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                gsMsg.getMessage(req, "cmn.setting.permissions")));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("ptlCmdMode", form.getPtlCmdMode());
        cmn999Form.addHiddenParam("ptlBackPage", form.getPtlBackPage());
        cmn999Form.addHiddenParam("ptlMainSid", form.getPtlMainSid());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}