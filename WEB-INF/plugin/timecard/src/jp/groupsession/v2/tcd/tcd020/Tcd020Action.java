package jp.groupsession.v2.tcd.tcd020;

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
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardAction;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.model.TcdTcdataModel;


/**
 * <br>[機  能] タイムカード編集画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd020Action extends AbstractTimecardAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd020Action.class);

    /**
     *<br>[機  能]tcd020knAction
     *<br>[解  説]
     *<br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {

        ActionForward forward = null;

        //コマンド取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        cmd = cmd.trim();

        Tcd020Form tcd020Form = (Tcd020Form) form;

        //指定したユーザのタイムカードを閲覧可能かを確認
        RequestModel reqMdl = getRequestModel(req);
        TimecardBiz tcdBiz = new TimecardBiz(reqMdl);
        int tcdUserSid = NullDefault.getInt(tcd020Form.getUsrSid(), -1);
        if (!tcdBiz.canViewTimecard(con, tcdUserSid, reqMdl.getSmodel())) {
            return getAuthErrorPage(map, req);
        }

        if (cmd.equals("back")) {
            //戻る
            forward = map.findForward("back");
        } else if (cmd.equals("submit")) {
            //設定ボタン
            forward = __doSubmit(map, tcd020Form, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, tcd020Form, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Tcd020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);
        Tcd020ParamModel paramMdl = new Tcd020ParamModel();
        paramMdl.setParam(form);
        Tcd020Biz biz = new Tcd020Biz();
        biz.setInitData(paramMdl, reqMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 設定ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doSubmit(ActionMapping map, Tcd020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        ActionForward forward = null;

        //セッション情報を取得
        BaseUserModel usModel = getSessionUserModel(req);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //入力チェック
        RequestModel reqMdl = getRequestModel(req);
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(sessionUsrSid, con);
        ActionErrors ereors = form.validateCheck(reqMdl, admConf, con);
        if (!ereors.isEmpty()) {
            addErrors(req, ereors);
            return __doInit(map, form, req, res, con);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //タイムカード情報を登録・更新
        boolean commit = false;
        TcdTcdataModel tcMdl = null;
        try {
            Tcd020ParamModel paramMdl = new Tcd020ParamModel();
            paramMdl.setParam(form);
            Tcd020Biz biz = new Tcd020Biz();
            tcMdl = biz.updateTcdTcdata(paramMdl, usModel, con, reqMdl);
            commit = true;

        } catch (SQLException e) {
            //SQL実行時例外
            log__.error("タイムカード更新に失敗しました。" + e);
            con.rollback();
            throw e;
        } finally {
            if (commit) {
                con.commit();
            }
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String edit = gsMsg.getMessage("cmn.edit");

        String[] days = null;
        //ログ出力
        if (form.getSelectDay() == null || form.getSelectDay().length == 0) {
            days = new String[1];
            days[0] = form.getEditDay();
        } else {
            days = form.getSelectDay();
        }
        TimecardBiz cBiz = new TimecardBiz(reqMdl);
        String opLog = cBiz.opLogContent(3, days, tcMdl.getTcdDate());
        cBiz.outPutTimecardLog(map, reqMdl, con, edit, GSConstLog.LEVEL_TRACE,
                opLog);

        //登録完了画面へ
        forward = __doUpdateCompDsp(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>[機  能] 登録完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doUpdateCompDsp(ActionMapping map, Tcd020Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("back");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "tcd.50");

        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", msg));

        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());

        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        cmn999Form.addHiddenParam("selectDay", form.getSelectDay());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}
