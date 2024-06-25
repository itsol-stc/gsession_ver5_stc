package jp.groupsession.v2.tcd.tcd060;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.AbstractTimecardAdminAction;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.model.TcdTimezoneMeiModel;


/**
 * <br>[機  能] タイムカード 管理者設定 時間帯設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd060Action extends AbstractTimecardAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd060Action.class);

    /**
     *<br>[機  能]tcd060Action
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

        Tcd060Form thisForm = (Tcd060Form) form;

        if (cmd.equals("tcd060_back")) {
            thisForm.setTcd060initFlg(GSConstTimecard.DSP_INIT);
            thisForm.setTimezoneCmdMode(GSConst.CMDMODE_ADD);
            //戻る
            forward = map.findForward("tcd060_back");
        } else if  (cmd.equals("changeTimeZoneList")) {
            //非同期：時間帯一覧登録編集
            __doAjaxAddEditTimeZoneList(map, thisForm, req, res, con);
        } else if  (cmd.equals("deleteTimeZoneList")) {
            //非同期：時間帯一覧削除
            __doAjaxDeleteTimeZoneList(map, thisForm, req, res, con);
        } else if  (cmd.equals("tcd060_ok")) {
            //時間帯情報登録編集
            forward = __doAddEditTimeZoneInf(map, thisForm, req, res, con);
        } else if (cmd.equals("kakuteiAdd")) {
            //登録処理
            int mode = GSConstTimecard.CMDMODE_ADD;
            forward = __doCommit(map, thisForm, req, res, con, mode);
        } else if (cmd.equals("kakuteiEdit")) {
            //編集処理
            int mode = GSConstTimecard.CMDMODE_EDIT;
            forward = __doCommit(map, thisForm, req, res, con, mode);
        } else if (cmd.equals("tcd130")) {
            //tcd130へ画面遷移
            forward = map.findForward("tcd130");
        } else if (cmd.equals("tcd130back")) {
             thisForm.setTimezoneCmdMode(GSConst.CMDMODE_EDIT);
            forward = __doInit(map, thisForm, req, res, con);
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
    private ActionForward __doInit(ActionMapping map, Tcd060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);
        Tcd060ParamModel paramMdl = new Tcd060ParamModel();
        paramMdl.setParam(form);
        Tcd060Biz biz = new Tcd060Biz(reqMdl);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 時間帯情報登録編集処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param mode 0:新規　1:編集
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Tcd060Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con,
            int mode) throws Exception   {
        log__.debug("処理");

        RequestModel reqMdl = getRequestModel(req);

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        //エラーチェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(reqMdl, con);

        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        Tcd060Biz biz = new Tcd060Biz(reqMdl);
        Tcd060ParamModel paramMdl = new Tcd060ParamModel();
        paramMdl.setParam(form);

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
        //DB更新
        boolean commit = false;
        try {
            MlCountMtController cntCon = getCountMtController(req);
            biz._updateTcdTimezoneInfo(paramMdl, userSid, cntCon, con, mode);
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

         //ログ出力
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msgState = null;
        if (mode == GSConstTimecard.CMDMODE_EDIT) {
            msgState = gsMsg.getMessage("cmn.edit");
        } else {
            msgState = gsMsg.getMessage("cmn.add");
        }
        StringBuilder sb = new StringBuilder();
        //時間帯名称
        sb.append("[" + gsMsg.getMessage("tcd.tcd120.01") + "] ");
        sb.append(form.getTcd060Name());
        sb.append("\r\n" + "[" + gsMsg.getMessage("tcd.tcd120.02") + "] ");
        sb.append(form.getTcd060Ryaku());
        sb.append("\r\n" + "[" + gsMsg.getMessage("tcd.tcd060.06") + "] ");
        if( form.getTcd060UseFlg() == GSConstTimecard.TIMEZONE_USE_KBN_NG) {
            sb.append(gsMsg.getMessage("tcd.tcd060.10"));
        } else {
            sb.append(gsMsg.getMessage("tcd.tcd060.09"));
        }
        sb.append("\r\n" + "[" + gsMsg.getMessage("tcd.tcd060.07") + "] ");
        if( form.getTcd060UseFlg() == GSConstTimecard.HOLKBN_WEEKDAY) {
            sb.append(gsMsg.getMessage("tcd.tcd060.12"));
        } else {
            sb.append(gsMsg.getMessage("tcd.tcd060.13"));
        }
        sb.append(System.getProperty("line.separator"));
        TimecardBiz cBiz = new TimecardBiz(reqMdl);
        cBiz.outPutTimecardLog(
                map,
                reqMdl,
                con,
                msgState,
                GSConstLog.LEVEL_INFO,
                sb.toString());
        //共通メッセージ画面(OK キャンセル)を表示
        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 非同期：時間帯情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @throws Exception SQL実行時例外
     */
    private void __doAjaxAddEditTimeZoneList(
            ActionMapping map,
            Tcd060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception {

        res.setContentType("text/json; charset=UTF-8");
        PrintWriter out = null;
        RequestModel reqMdl = getRequestModel(req);

        boolean errorFlg = false;
        //入力チェック
        JSONObject jsonData = new JSONObject();
        ActionErrors errors = form.validateDialogCheck(getRequestModel(req));
        if (!errors.isEmpty()) {
            log__.debug("入力エラー");
            addErrors(req, errors);
            errorFlg = true;
        }
        try {
            //設定
            boolean successFlg = false;
            if (!errorFlg) {
                Tcd060ParamModel paramMdl = new Tcd060ParamModel();
                paramMdl.setParam(form);
                Tcd060Biz biz = new Tcd060Biz(reqMdl);
                successFlg = biz._setTimeZoneList(paramMdl);
                paramMdl.setFormData(form);
            }
            //formをJsonに格納
            jsonData = JSONObject.fromObject(form);
            if (errorFlg) {
                jsonData.element("validateError", true);
                __setJsonErrorMessage(jsonData, req);
            } else {
                if (successFlg) {
                    jsonData.element("success", true);
                    __setJsonTimeZoneInf(jsonData, form.getTcd060TimezoneMeiList());
                } else {
                    jsonData.element("error", true);
                }
            }
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] 非同期：時間帯情報削除
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @throws Exception SQL実行時例外
     */
    private void __doAjaxDeleteTimeZoneList(
            ActionMapping map,
            Tcd060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws Exception  {
        res.setContentType("text/json; charset=UTF-8");
        PrintWriter out = null;
        RequestModel reqMdl = getRequestModel(req);
        try {
            boolean successFlg = false;
            Tcd060ParamModel paramMdl = new Tcd060ParamModel();
            paramMdl.setParam(form);
            Tcd060Biz biz = new Tcd060Biz(reqMdl);
            successFlg = biz._deleteTimeZoneList(paramMdl);
            paramMdl.setFormData(form);
            JSONObject jsonData = new JSONObject();
            jsonData = JSONObject.fromObject(form);
            if (successFlg) {
                jsonData.element("success", true);
                __setJsonTimeZoneInf(jsonData, form.getTcd060TimezoneMeiList());
            } else {
                jsonData.element("error", true);
            }
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData);
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗");
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * <br>[機  能] 時間帯一覧hiddenパラメータ生成
     * <br>[解  説]
     * <br>[備  考]
     * @param tzmList 時間帯情報一覧
     * @param jsonData JSONObject
     * @throws Exception 実行時例外
     * @return JSONデータ
     */
    private JSONObject __setJsonTimeZoneInf(
            JSONObject jsonData,
            ArrayList<TcdTimezoneMeiModel> tzmList) {
        if (tzmList != null && !tzmList.isEmpty()) {
            int index = 0;
                jsonData.element("hidden_timezone_list", tzmList.size());
            for (TcdTimezoneMeiModel mdl:tzmList) {
                jsonData.element("fr_time" + index,
                        mdl.getFrTime().toLocalTime().toString());
                jsonData.element("to_time" + index,
                        mdl.getToTime().toLocalTime().toString());
                index++;
            }
        }
        return jsonData;
    }

    /**
     * <br>[機  能] エラーメッセージ生成
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONObject
     * @param req HttpServletRequest
     * @throws Exception 実行時例外
     * @return エラーメッセージ
     */
    private JSONObject __setJsonErrorMessage(JSONObject jsonData, HttpServletRequest req) {
        // エラーメッセージ取得
        ActionMessages errMessages = getErrors(req);
        List<String> errMsgList = new ArrayList<String>();
        MessageResources mres = getResources(req);
        @SuppressWarnings("rawtypes")
        Iterator it = errMessages.get();
        while (it.hasNext()) {
            ActionMessage error = (ActionMessage) it.next();
            errMsgList.add(mres.getMessage(error.getKey(), error.getValues()));
        }
        // Jsonに格納
        jsonData.element("errMessage_size", errMsgList.size());
        for (int nIdx = 0; nIdx < errMsgList.size(); nIdx++) {
        jsonData.element("errMessage_"
        + nIdx, String.valueOf(errMsgList.get(nIdx)));
        }

        return jsonData;
    }


    /**
     * <br>[機  能] 時間帯情報登録編集
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
    private ActionForward __doAddEditTimeZoneInf(
            ActionMapping map,
            Tcd060Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
            throws Exception {

        ActionErrors errors = form.validateCheck(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            log__.debug("入力エラー");
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        //トランザクショントークン設定
        saveToken(req);
        //モード判定
        int mode = form.getTimezoneCmdMode();
        // 確認画面に遷移
        __setKakuninPageParam(map, req, form, mode, con);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param mode 登録編集削除モード
     * @throws SQLException
     */
    private void __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Tcd060Form form,
        int mode,
        Connection con) throws SQLException {
        String cmd = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        //メッセージセット
        GsMessage gsMsg = new GsMessage(req);
        String msgState = null;
        if (mode == GSConstTimecard.CMDMODE_EDIT) {
            cmd = "?CMD=kakuteiEdit";
            msgState = "edit.kakunin.once";
        } else {
            cmd = "?CMD=kakuteiAdd";
            msgState = "touroku.kakunin.once";
        }
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("mine").getPath() + cmd);
        cmn999Form.setUrlCancel(map.findForward("mine").getPath() + "?CMD=cancel");
        String mKey = gsMsg.getMessage("tcd.47");
        cmn999Form.setMessage(msgRes.getMessage(msgState, mKey));
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());
        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        form.setHiddenParamTcd060(cmn999Form);
        form.setHiddenParamTcd120(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
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
            Tcd060Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("year", form.getYear());
        cmn999Form.addHiddenParam("month", form.getMonth());
        cmn999Form.addHiddenParam("tcdDspFrom", form.getTcdDspFrom());
        cmn999Form.addHiddenParam("usrSid", form.getUsrSid());
        cmn999Form.addHiddenParam("sltGroupSid", form.getSltGroupSid());
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("tcd060_back");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        GsMessage gsMsg = new GsMessage(req);
        String mkey1 = gsMsg.getMessage("tcd.47");
        String msgState = "settei.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());

        req.setAttribute("cmn999Form", cmn999Form);
    }
}
