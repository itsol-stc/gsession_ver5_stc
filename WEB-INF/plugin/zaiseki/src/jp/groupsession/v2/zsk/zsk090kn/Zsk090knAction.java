package jp.groupsession.v2.zsk.zsk090kn;

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

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.AbstractZaisekiAction;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;

/**
 * <br>[機  能] 在席管理 個人設定 自動リロード時間設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Zsk090knAction extends AbstractZaisekiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk090knAction.class);

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
        Zsk090knForm rsvform = (Zsk090knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //確定ボタン押下
        if (cmd.equals("zsk090add")) {
            log__.debug("確定ボタン押下");
            forward = __doKakutei(map, rsvform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("zsk090knBack")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("zsk090");
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
                                    Zsk090knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        con.setAutoCommit(true);
        Zsk090knBiz biz = new Zsk090knBiz(con, getRequestModel(req));

        Zsk090knParamModel paramMdl = new Zsk090knParamModel();
        paramMdl.setParam(form);
        biz.initDsp(paramMdl);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Zsk090knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doKakutei(ActionMapping map, Zsk090knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;
        boolean commit = false;
        try {
            Zsk090knBiz biz = new Zsk090knBiz(con, getRequestModel(req));
            //個人設定の登録
            Zsk090knParamModel paramMdl = new Zsk090knParamModel();
            paramMdl.setParam(form);
            biz.registPrivate(paramMdl, getSessionUserModel(req).getUsrsid());
            paramMdl.setFormData(form);

            forward = __setCompPageParam(map, req, form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("個人設定の登録に失敗", e);
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.edit");
        String logMsg = __getLogMessage(form, req);

        //ログ出力
        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(getRequestModel(req));
        cmnBiz.outPutLog(con,
                         msg, GSConstLog.LEVEL_INFO, logMsg, map.getType());
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
        Zsk090knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("zsk090upcomp");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.preferences2");

        //メッセージセット
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", msg));
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("selectZifSid", form.getSelectZifSid());
        cmn999Form.addHiddenParam("uioStatus", form.getUioStatus());
        cmn999Form.addHiddenParam("uioStatusBiko", form.getUioStatusBiko());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] ログ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     * @throws SQLException SQLエラー
     */
    private String __getLogMessage(
        Zsk090knForm form,
        HttpServletRequest req) throws SQLException {

        GsMessage gsMsg = new GsMessage(req);
        String msg = "";
        //自動リロード時間
        msg += "[" + gsMsg.getMessage("cmn.auto.reload.time") + "]";
        if (form.getZsk090ReloadTime().equals(GSConstZaiseki.AUTORELOAD_1)) {
            msg += GSConstZaiseki.AUTORELOAD_TEXT_1;
        } else if (form.getZsk090ReloadTime().equals(GSConstZaiseki.AUTORELOAD_3)) {
            msg += GSConstZaiseki.AUTORELOAD_TEXT_3;
        } else if (form.getZsk090ReloadTime().equals(GSConstZaiseki.AUTORELOAD_5)) {
            msg += GSConstZaiseki.AUTORELOAD_TEXT_5;
        } else if (form.getZsk090ReloadTime().equals(GSConstZaiseki.AUTORELOAD_10)) {
            msg += GSConstZaiseki.AUTORELOAD_TEXT_10;
        } else if (form.getZsk090ReloadTime().equals(GSConstZaiseki.AUTORELOAD_20)) {
            msg += GSConstZaiseki.AUTORELOAD_TEXT_20;
        } else if (form.getZsk090ReloadTime().equals(GSConstZaiseki.AUTORELOAD_30)) {
            msg += GSConstZaiseki.AUTORELOAD_TEXT_30;
        } else if (form.getZsk090ReloadTime().equals(GSConstZaiseki.AUTORELOAD_40)) {
            msg += GSConstZaiseki.AUTORELOAD_TEXT_40;
        } else if (form.getZsk090ReloadTime().equals(GSConstZaiseki.AUTORELOAD_50)) {
            msg += GSConstZaiseki.AUTORELOAD_TEXT_50;
        } else if (form.getZsk090ReloadTime().equals(GSConstZaiseki.AUTORELOAD_60)) {
            msg += GSConstZaiseki.AUTORELOAD_TEXT_60;
        } else if (form.getZsk090ReloadTime().equals(GSConstZaiseki.AUTORELOAD_0)) {
            msg += GSConstZaiseki.AUTORELOAD_TEXT_0;
        }
        return msg;
    }
}