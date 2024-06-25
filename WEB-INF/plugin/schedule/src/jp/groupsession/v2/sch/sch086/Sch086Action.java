package jp.groupsession.v2.sch.sch086;

import java.lang.reflect.InvocationTargetException;
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
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.AbstractScheduleAdminAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュール 管理者設定 スケジュール初期値設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Sch086Action extends AbstractScheduleAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch086Action.class);

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
                                         Connection con) throws Exception {

         ActionForward forward = null;
         Sch086Form schform = (Sch086Form) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         //OKボタン押下
         if (cmd.equals("sch086Ok")) {
             //確認
             forward = __doOk(map, schform, req, res, con);
         } else if (cmd.equals("sch086Entry")) {
             //登録
             forward = __doCommit(map, schform, req, res, con);
         //戻るボタン押下
         } else if (cmd.equals("back")) {
             log__.debug("戻るボタン押下");
             forward = map.findForward("ktool");
         //初期表示処理
         } else {
             log__.debug("初期表示処理");
             forward = __doInit(map, schform, req, res, con);
         }
         return forward;
     }

     /**
      * <br>[機  能] 初期表示を行う
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws SQLException SQL実行例外
      * @return ActionForward
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
      */
     private ActionForward __doInit(
             ActionMapping map,
             Sch086Form form,
             HttpServletRequest req,
             HttpServletResponse res,
             Connection con) throws SQLException,
             IllegalAccessException, InvocationTargetException, NoSuchMethodException {

         con.setAutoCommit(true);
         Sch086Biz biz = new Sch086Biz(getRequestModel(req));
         Sch086ParamModel paramMdl = new Sch086ParamModel();
         paramMdl.setParam(form);
         biz.setInitData(con, paramMdl);
         paramMdl.setFormData(form);

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
      * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
      */
     private ActionForward __doOk(ActionMapping map, Sch086Form form,
             HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException,
             IllegalAccessException, InvocationTargetException, NoSuchMethodException {

         log__.debug("確認処理");
         SchCommonBiz schCmnBiz = new SchCommonBiz(getRequestModel(req));
         int hourDiv = schCmnBiz.getDayScheduleHourMemoriMin(con);
         ActionErrors errors = form.validateCheck(req, hourDiv);
         if (!errors.isEmpty()) {
             addErrors(req, errors);
             return __doInit(map, form, req, res, con);
         }

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
         Sch086Form form) {

         Cmn999Form cmn999Form = new Cmn999Form();

         cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
         MessageResources msgRes = getResources(req);
         cmn999Form.setIcon(Cmn999Form.ICON_INFO);
         cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
         cmn999Form.setUrlOK(map.findForward("mine").getPath() + "?CMD=sch086Entry");
         cmn999Form.setUrlCancel(map.findForward("mine").getPath());

         //メッセージセット
         String msgState = "edit.kakunin.once";
         GsMessage gsMsg = new GsMessage();
         //スケジュール 初期値
         String textSchBasicSetting = gsMsg.getMessage(req, "schedule.131");
         String mkey1 = textSchBasicSetting;
         cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

         cmn999Form.addHiddenParam("cmd", "sch086Entry");
         cmn999Form.addHiddenParam("sch086init", form.getSch086init());
         cmn999Form.addHiddenParam("sch086TimeType", form.getSch086TimeType());
         cmn999Form.addHiddenParam("sch086DefFrH", form.getSch086DefFrH());
         cmn999Form.addHiddenParam("sch086DefFrM", form.getSch086DefFrM());
         cmn999Form.addHiddenParam("sch086DefToH", form.getSch086DefToH());
         cmn999Form.addHiddenParam("sch086DefToM", form.getSch086DefToM());
         cmn999Form.addHiddenParam("sch086EditType", form.getSch086EditType());
         cmn999Form.addHiddenParam("sch086Edit", form.getSch086Edit());
         cmn999Form.addHiddenParam("sch086PublicType", form.getSch086PublicType());
         cmn999Form.addHiddenParam("sch086Public", form.getSch086Public());
         cmn999Form.addHiddenParam("sch086SameType", form.getSch086SameType());
         cmn999Form.addHiddenParam("sch086Same", form.getSch086Same());
         cmn999Form.addHiddenParam("sch086PubUsrGrpSid", form.getSch086PubUsrGrpSid());

         form.setMsgFormParam(cmn999Form);

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
      * @throws SQLException SQL実行時例外
      */
     private ActionForward __doCommit(ActionMapping map, Sch086Form form,
             HttpServletRequest req, HttpServletResponse res, Connection con)
             throws SQLException {

         log__.debug("登録処理");

         RequestModel reqMdl = getRequestModel(req);

         //不正な画面遷移
         if (!isTokenValid(req, true)) {
             log__.info("２重投稿");
             return getSubmitErrorPage(map, req);
         }

         //DB更新
         boolean commit = false;
         Sch086ParamModel paramMdl = new Sch086ParamModel();
         paramMdl.setParam(form);
         try {
             Sch086Biz biz = new Sch086Biz(getRequestModel(req));
             biz.entryKbn(con, paramMdl, getSessionUserSid(req));
             paramMdl.setFormData(form);

             con.commit();
             commit = true;
         } finally {
             if (!commit) {
                 con.rollback();
             }
         }

         //メッセージ 削除
         GsMessage gsMsg = new GsMessage();
         String change = gsMsg.getMessage(req, "cmn.change");
         //ログ出力処理
         SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
         String value = "";
         String[] setting = {
                 gsMsg.getMessage("cmn.set.eachuser"),
                 gsMsg.getMessage("cmn.set.the.admin")
         };
         // 時間
         value += "[" + gsMsg.getMessage("cmn.time") + "] ";
         value += setting[paramMdl.getSch086TimeType()];
         if (paramMdl.getSch086TimeType() == GSConstSchedule.SAD_INITIME_STYPE_ADM) {
             value += "\r\n";
             value += gsMsg.getMessage("cmn.starttime") + ":";
             value += paramMdl.getSch086DefFrH() + "時" + paramMdl.getSch086DefFrM() + "分";
             value += "\r\n";
             value += gsMsg.getMessage("cmn.endtime") + ":";
             value += paramMdl.getSch086DefToH() + "時" + paramMdl.getSch086DefToM() + "分";
         }
         value += "\r\n";
         // 編集権限
         value += "[" + gsMsg.getMessage("cmn.edit.permissions") + "] ";
         value += setting[paramMdl.getSch086EditType()];
         if (paramMdl.getSch086EditType() == GSConstSchedule.SAD_INITIME_STYPE_ADM) {
             String[] edit = {
                     gsMsg.getMessage("cmn.nolimit"),
                     gsMsg.getMessage("cmn.only.principal.or.registant"),
                     gsMsg.getMessage("cmn.only.affiliation.group.membership")
             };
             value += "\r\n";
             value += edit[paramMdl.getSch086Edit()];
         }
         value += "\r\n";
         // 公開
         value += "[" + gsMsg.getMessage("cmn.public") + "] ";
         value += setting[paramMdl.getSch086PublicType()];
         if (paramMdl.getSch086PublicType() == GSConstSchedule.SAD_INITIME_STYPE_ADM) {
             String[] publicMsg = {
                     gsMsg.getMessage("cmn.public"),
                     gsMsg.getMessage("cmn.private"),
                     gsMsg.getMessage("schedule.5"),
                     gsMsg.getMessage("schedule.28"),
                     gsMsg.getMessage("schedule.37"),
                     gsMsg.getMessage("schedule.38")
             };
             value += "\r\n";
             value += publicMsg[paramMdl.getSch086Public()];
             if (paramMdl.getSch086Public() == GSConstSchedule.DSP_USRGRP) {
                 value += "\r\n";
                 value += "[" + gsMsg.getMessage("schedule.36") + "] ";
                 CommonBiz cmnBiz = new CommonBiz();
                 for (UsrLabelValueBean bean : cmnBiz.getUserLabelList(
                         con, paramMdl.getSch086PubUsrGrpSid())) {
                     value += "\r\n";
                     value += bean.getLabel();
                 };
             }
         }
         value += "\r\n";
         // 同時修正
         value += "[" + gsMsg.getMessage("schedule.32") + "] ";
         value += setting[paramMdl.getSch086SameType()];
         if (paramMdl.getSch086SameType() == GSConstSchedule.SAD_INITIME_STYPE_ADM) {
             String[] same = {
                     gsMsg.getMessage("schedule.33"),
                     gsMsg.getMessage("schedule.34")
             };
             value += "\r\n";
             value += same[paramMdl.getSch086Same()];
         }
         String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                           + gsMsg.getMessage("schedule.sch086.1");
         schBiz.outPutLogNoDspName(
                 map, req, res,
                 change, GSConstLog.LEVEL_INFO, value, null, dspName);

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
         Sch086Form form) {

         Cmn999Form cmn999Form = new Cmn999Form();
         ActionForward urlForward = null;

         cmn999Form.setType(Cmn999Form.TYPE_OK);
         MessageResources msgRes = getResources(req);
         cmn999Form.setIcon(Cmn999Form.ICON_INFO);
         cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
         urlForward = map.findForward("ktool");
         cmn999Form.setUrlOK(urlForward.getPath());

         //メッセージセット
         String msgState = "touroku.kanryo.object";
         GsMessage gsMsg = new GsMessage();
         //スケジュール基本設定
         String textSchBasicSetting = gsMsg.getMessage(req, "schedule.131");
         cmn999Form.setMessage(msgRes.getMessage(msgState, textSchBasicSetting));

         form.setMsgFormParam(cmn999Form);

         req.setAttribute("cmn999Form", cmn999Form);

     }
}