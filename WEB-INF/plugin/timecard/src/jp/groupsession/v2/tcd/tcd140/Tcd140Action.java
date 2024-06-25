package jp.groupsession.v2.tcd.tcd140;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardAdminAction;
import jp.groupsession.v2.tcd.TimecardBiz;


/**
 * <br>[機  能] ユーザ別時間帯設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd140Action extends AbstractTimecardAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd140Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
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
        //管理者権限チェック
        CommonBiz commonBiz = new CommonBiz();
        RequestModel reqMdl = getRequestModel(req);
        boolean isAdmin =
                commonBiz.isPluginAdmin(con,
                        reqMdl.getSmodel(),
                        GSConstTimecard.PLUGIN_ID_TIMECARD);
        if (!isAdmin) {
            return getNotAdminSeniPage(map, req);
        }
        //コマンド取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        cmd = cmd.trim();

        Tcd140Form thisForm = (Tcd140Form) form;

        if (cmd.equals("backList")) {
            //戻る
            forward = map.findForward("backList");
        } else if (cmd.equals("ok")) {
            //ok
            forward = __doKakunin(map, thisForm, req, res, con);
        } else if (cmd.equals("kakutei")) {
            //確定
            forward = __doKakutei(map, thisForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
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
    private ActionForward __doInit(ActionMapping map, Tcd140Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);
        Tcd140ParamModel paramMdl = new Tcd140ParamModel();
        paramMdl.setParam(form);
        Tcd140Biz biz = new Tcd140Biz(reqMdl);
        biz._setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }


    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 例外
     */
    private ActionForward __doKakunin(ActionMapping map, Tcd140Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("確認処理");
        //入力チェック
        ActionErrors errors = form.validateCheck(req, con);
        if (errors.size() > 0) {
            log__.debug("入力エラー");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        log__.debug("入力エラーなし");
        //トランザクショントークン設定
        saveToken(req);
        //共通メッセージ画面(OK キャンセル)を表示
        __setKakuninPageParam(map, req, form);
        return map.findForward("gf_msg");
    }


    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Tcd140Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("mine").getPath() + "?CMD=kakutei");
        cmn999Form.setUrlCancel(map.findForward("mine").getPath() + "?CMD=cancel");
        //メッセージセット
        GsMessage gsMsg = new GsMessage(req);
        String msgState = "edit.kakunin.once";
        String mkey1 = gsMsg.getMessage("tcd.tcd140.05");
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());
        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        cmn999Form.addHiddenParam("timezoneSid", form.getTimezoneSid());
        form.setHiddenParamTcd130(cmn999Form);
        form.setHiddenParamTcd140(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 例外
     */
    private ActionForward __doKakutei(
            ActionMapping map,
            Tcd140Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
            throws Exception {

        log__.debug("登録処理");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        ActionErrors errors = form.validateCheck(req, con);
        if (errors.size() > 0) {
            log__.debug("入力エラー");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        //DB更新
        RequestModel reqMdl = getRequestModel(req);
        Tcd140ParamModel paramMdl = new Tcd140ParamModel();
        Tcd140Biz biz = new Tcd140Biz(reqMdl);
        paramMdl.setParam(form);
        biz._setInsertData(paramMdl, con);
        paramMdl.setFormData(form);

        //ログ出力
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.change");
        StringBuilder sb = new StringBuilder();
        //選択ユーザ取得
        List<CmnUsrmInfModel> uList = new ArrayList<CmnUsrmInfModel>();
        UserBiz usrBiz = new UserBiz();
        String[] targetUser = null;
        if (form.getTcdSelectUserlist() == null
                || form.getTcdSelectUserlist().length == 0) {
            targetUser = new String[] {form.getTcdSelectedUser()};
        } else {
            targetUser = form.getTcdSelectUserlist();
        }
        uList = usrBiz.getUserList(con, targetUser);
        sb.append("[" + gsMsg.getMessage("cmn.user.name") + "] ");
        int i = 0;
        for (CmnUsrmInfModel usrMdl : uList) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(usrMdl.getUsiSei() + " " + usrMdl.getUsiMei());
            i++;
        }

        TimecardBiz cBiz = new TimecardBiz(reqMdl);
        cBiz.outPutTimecardLog(map, reqMdl, con,
                opCode, GSConstLog.LEVEL_INFO, sb.toString());

        //共通メッセージ画面(OK キャンセル)を表示
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
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Tcd140Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());
        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        cmn999Form.addHiddenParam("timezoneSid", form.getTimezoneSid());
        cmn999Form.addHiddenParam("selectDay", form.getSelectDay());
        form.setHiddenParamTcd130(cmn999Form);

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backList");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        GsMessage gsMsg = new GsMessage(req);
        String mkey1 = gsMsg.getMessage("tcd.tcd140.05");
        String msgState = "touroku.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());

        req.setAttribute("cmn999Form", cmn999Form);

    }

}
