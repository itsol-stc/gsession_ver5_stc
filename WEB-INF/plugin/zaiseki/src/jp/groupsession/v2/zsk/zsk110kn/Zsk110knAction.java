package jp.groupsession.v2.zsk.zsk110kn;

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

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.AbstractZaisekiAdminAction;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;

/**
 * <br>[機  能] 在席管理 管理者設定 定時一括更新確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk110knAction extends AbstractZaisekiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk110knAction.class);

    /**
     * <br>アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;
        Zsk110knForm zskForm = (Zsk110knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("zsk110knOk")) {
            //確定ボタンクリック
            forward = __doKakutei(map, zskForm, req, res, con);
        } else if (cmd.equals("zsk110knBack")) {
            //戻るボタンクリック
            forward = __doBack(map, zskForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, zskForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doKakutei(ActionMapping map, Zsk110knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        ActionErrors errors = null;
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        //再度入力チェックを行う。
        errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        BaseUserModel umodel = getSessionUserModel(req);
        Zsk110knBiz biz = new Zsk110knBiz();
        boolean commit = false;
        con.setAutoCommit(false);
        try {

            Zsk110knParamModel paramMdl = new Zsk110knParamModel();
            paramMdl.setParam(form);
            biz.dataSetting(paramMdl, con, umodel.getUsrsid());
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("定時一括変更設定の登録に失敗", e);
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.change");
        String logMsg = __getLogMessage(form, req);

        //ログ出力
        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(getRequestModel(req));
        cmnBiz.outPutLog(con,
                msg, GSConstLog.LEVEL_INFO, logMsg, map.getType());

        return __setCompPageParam(map, req, form);
    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Zsk110knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");

        return map.findForward("zsk110");
    }

    /**
     * <br>初期表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Zsk110knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");
        con.setAutoCommit(true);
        Zsk110knBiz biz = new Zsk110knBiz();

        Zsk110knParamModel paramMdl = new Zsk110knParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
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
        Zsk110knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("zsk020");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.preferences");

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
        Zsk110knForm form,
        HttpServletRequest req) throws SQLException {

        GsMessage gsMsg = new GsMessage(req);
        String msg = "";
        //定時一括更新
        msg += "[" + gsMsg.getMessage("zsk.11") + "]";
        if (form.getZsk110UpdateKbn() == GSConstZaiseki.FIXED_UPDATE_ON) {
            msg += gsMsg.getMessage("cmn.setting.do");
            //開始時刻
            msg += "\r\n[" + gsMsg.getMessage("cmn.starttime") + "]";
            switch (form.getZsk110StartTime()) {
                case GSConstZaiseki.START_TIME_0 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_0) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_1 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_1) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_2 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_2) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_3 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_3) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_4 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_4) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_5 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_5) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_6 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_6) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_7 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_7) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_8 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_8) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_9 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_9) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_10 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_10) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_11 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_11) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_12 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_12) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_13 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_13) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_14 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_14) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_15 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_15) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_16 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_16) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_17 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_17) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_18 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_18) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_19 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_19) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_20 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_20) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_21 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_21) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_22 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_22) + gsMsg.getMessage("cmn.hour");
                    break;
                case GSConstZaiseki.START_TIME_23 :
                    msg += String.valueOf(
                            GSConstZaiseki.START_TIME_23) + gsMsg.getMessage("cmn.hour");
                    break;
                default :
                    break;
            }
            //在席状況
            msg += "\r\n[" + gsMsg.getMessage("zsk.20") + "]";
            if (form.getZsk110Status() == GSConstZaiseki.STATUS_OTHER) {
                msg += gsMsg.getMessage("cmn.other");
            } else if (form.getZsk110Status() == GSConstZaiseki.STATUS_PRESENCE) {
                msg += gsMsg.getMessage("cmn.zaiseki");
            } else if (form.getZsk110Status() == GSConstZaiseki.STATUS_ABSENCE) {
                msg += gsMsg.getMessage("cmn.absence");
            }
            //在席コメント
            msg += "\r\n[" + gsMsg.getMessage("zsk.23") + "]";
            msg += form.getZsk110Msg();
        } else if (form.getZsk110UpdateKbn() == GSConstZaiseki.FIXED_UPDATE_OFF) {
            msg += gsMsg.getMessage("cmn.noset");
        }
        return msg;
    }
}
