package jp.groupsession.v2.rsv.rsv280kn;

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
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.rsv.AbstractReserveAdminAction;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 管理者設定 施設予約初期値設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv280knAction extends AbstractReserveAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv280knAction.class);

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
        Rsv280knForm rsvForm = (Rsv280knForm) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("rsv280knkakutei")) {
            //登録 戻る
            forward = __doCommit(map, rsvForm, req, res, con);
        } else if (cmd.equals("rsv280knback")) {
            //戻る
            forward = map.findForward("backInput");
        } else {
            //初期表示
            forward = __doInit(map, rsvForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Rsv280knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("登録");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors
            = form.validateRsv280(con, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //DB更新
        Rsv280knBiz biz = new Rsv280knBiz(getRequestModel(req), con);

        Rsv280knParamModel paramMdl = new Rsv280knParamModel();
        paramMdl.setParam(form);
        biz.entryInitConfig(paramMdl, con, getSessionUserSid(req));
        paramMdl.setFormData(form);

        //ログ出力処理
        GsMessage gsMsg = new GsMessage();
        String msg = __getLogMessage(form, req);
        AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
        rsvBiz.outPutLog(map, req, res,
                gsMsg.getMessage(req, "cmn.change"), GSConstLog.LEVEL_INFO, msg);

        //完了画面のパラメータを設定。
        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @throws Exception 実行例外
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Rsv280knForm form) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backAdminMenu");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        GsMessage gsMsg = new GsMessage();
        String msgState = "touroku.kanryo.object";
        //施設予約初期値設定
        String key1 = gsMsg.getMessage(req, "cmn.default.setting");
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        form.setParamValue(cmn999Form);
        cmn999Form.addHiddenParam("rsv280PeriodKbn", form.getRsv280PeriodKbn());
        cmn999Form.addHiddenParam("rsv280DefFrH", form.getRsv280DefFrH());
        cmn999Form.addHiddenParam("rsv280DefFrM", form.getRsv280DefFrM());
        cmn999Form.addHiddenParam("rsv280DefToH", form.getRsv280DefToH());
        cmn999Form.addHiddenParam("rsv280DefToM", form.getRsv280DefToM());
        cmn999Form.addHiddenParam("rsv280EditKbn", form.getRsv280EditKbn());
        cmn999Form.addHiddenParam("rsv280Edit", form.getRsv280Edit());
        cmn999Form.addHiddenParam("rsv280PublicKbn", form.getRsv280PublicKbn());
        cmn999Form.addHiddenParam("rsv280Public", form.getRsv280Public());
        cmn999Form.addHiddenParam("rsv280initFlg", form.getRsv280initFlg());

        req.setAttribute("cmn999Form", cmn999Form);
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
    private ActionForward __doInit(ActionMapping map, Rsv280knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");
        con.setAutoCommit(true);
        Rsv280knBiz biz = new Rsv280knBiz(getRequestModel(req), con);

        Rsv280knParamModel paramMdl = new Rsv280knParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
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
        Rsv280knForm form,
        HttpServletRequest req) throws SQLException {

        GsMessage gsMsg = new GsMessage(req);
        String msg = "";

        //期間
        msg += "[" + gsMsg.getMessage("cmn.period") + "]";
        if (form.getRsv280PeriodKbn() == GSConstReserve.AUTH_ALL_USER) {
            msg += gsMsg.getMessage("cmn.set.eachuser");
        } else if (form.getRsv280PeriodKbn() == GSConstReserve.AUTH_ADMIN_USER) {
            msg += gsMsg.getMessage("cmn.set.the.admin");
            msg += "\r\n" + gsMsg.getMessage("cmn.am") + " : ";
            msg += form.getRsv280DefFrH() + gsMsg.getMessage("cmn.hour");
            String minute = String.valueOf(form.getRsv280DefFrM());
            if (minute.equals("0")) {
                minute = "00";
            }
            msg += minute + gsMsg.getMessage("cmn.minute");

            msg += "\r\n" + gsMsg.getMessage("cmn.pm") + " : ";
            msg += form.getRsv280DefToH() + gsMsg.getMessage("cmn.hour");
            minute = String.valueOf(form.getRsv280DefToM());
            if (minute.equals("0")) {
                minute = "00";
            }
            msg += minute + gsMsg.getMessage("cmn.minute");
        }
        //編集権限
        msg += "\r\n[" + gsMsg.getMessage("cmn.edit.permissions") + "]";
        if (form.getRsv280EditKbn() == GSConstReserve.AUTH_ALL_USER) {
            msg += gsMsg.getMessage("cmn.set.eachuser");
        } else if (form.getRsv280EditKbn() == GSConstReserve.AUTH_ADMIN_USER) {
            msg += gsMsg.getMessage("cmn.set.the.admin") + "\r\n";
            if (form.getRsv280Edit() == GSConstReserve.EDIT_AUTH_NONE) {
                msg += gsMsg.getMessage("cmn.nolimit");
            } else if (form.getRsv280Edit() == GSConstReserve.EDIT_AUTH_PER_AND_ADU) {
                msg += gsMsg.getMessage("cmn.only.principal.or.registant");
            } else if (form.getRsv280Edit() == GSConstReserve.EDIT_AUTH_GRP_AND_ADU) {
                msg += gsMsg.getMessage("cmn.only.affiliation.group.membership");
            }
        }
        //公開区分
        msg += "\r\n[" + gsMsg.getMessage("cmn.public.kbn") + "]";
        if (form.getRsv280PublicKbn() == GSConstReserve.AUTH_ALL_USER) {
            msg += gsMsg.getMessage("cmn.set.eachuser");
        } else if (form.getRsv280PublicKbn() == GSConstReserve.AUTH_ADMIN_USER) {
            msg += gsMsg.getMessage("cmn.set.the.admin") + "\r\n";
            if (form.getRsv280Public() == GSConstReserve.PUBLIC_KBN_ALL) {
                msg += gsMsg.getMessage("cmn.public");
            } else if (form.getRsv280Public() == GSConstReserve.PUBLIC_KBN_PLANS) {
                msg += gsMsg.getMessage("reserve.175");
            } else if (form.getRsv280Public() == GSConstReserve.PUBLIC_KBN_GROUP) {
                msg += gsMsg.getMessage("reserve.176");
            }
        }

        return msg;
    }
}
