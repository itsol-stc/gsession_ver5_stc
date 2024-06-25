package jp.groupsession.v2.sch.sch091;

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
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumReminderTime;
import jp.groupsession.v2.sch.AbstractScheduleAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchColMsgDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchColMsgModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュール 個人設定 初期値設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch091Action extends AbstractScheduleAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch091Action.class);

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
        Sch091Form schForm = (Sch091Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("sch091kakunin")) {
            //確認
            forward = __doKakunin(map, schForm, req, res, con);
        } else if (cmd.equals("sch091commit")) {
            //登録 戻る
            forward = __doCommit(map, schForm, req, res, con);
        } else if (cmd.equals("sch091back")) {
            //戻る
            forward = __doBack(map, schForm, req, res, con);
        } else if (cmd.equals("sch091reload")) {
            //再表示
            //デフォルト
            forward = __doReload(map, schForm, req, res, con);
        } else {
            //デフォルト
            forward = __doInit(map, schForm, req, res, con);
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Sch091Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("確認");

        ActionErrors errors = form.validateCheck(map, req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面を表示
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
        Sch091Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());
        GsMessage gsMsg = new GsMessage();
        //スケジュール 初期値
        String textSchInit = gsMsg.getMessage(req, "cmn.default.setting");
        //メッセージセット
        String msgState = "edit.kakunin.once";
        String mkey1 = textSchInit;
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("sch091DefFrH", form.getSch091DefFrH());
        cmn999Form.addHiddenParam("sch091DefFrM", form.getSch091DefFrM());
        cmn999Form.addHiddenParam("sch091DefToH", form.getSch091DefToH());
        cmn999Form.addHiddenParam("sch091DefToM", form.getSch091DefToM());
        cmn999Form.addHiddenParam("sch091PubFlg", form.getSch091PubFlg());
        cmn999Form.addHiddenParam("sch091Edit", form.getSch091Edit());
        cmn999Form.addHiddenParam("sch091Fcolor", form.getSch091Fcolor());
        cmn999Form.addHiddenParam("sch091Same", form.getSch091Same());
        cmn999Form.addHiddenParam("sch091initFlg", form.getSch091initFlg());
        cmn999Form.addHiddenParam("sch091DefDsp", form.getSch091DefDsp());
        cmn999Form.addHiddenParam("sch091PubUsrGrpSid", form.getSch091PubUsrGrpSid());
        //リマインダー通知
        cmn999Form.addHiddenParam("sch091ReminderDspFlg", form.getSch091ReminderDspFlg());
        cmn999Form.addHiddenParam("sch091ReminderTime", form.getSch091ReminderTime());

        cmn999Form.addHiddenParam("dspMod", form.getDspMod());
        cmn999Form.addHiddenParam("listMod", form.getListMod());
        cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
        cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
        cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
        cmn999Form.addHiddenParam("sch010SelectUsrSid", form.getSch010SelectUsrSid());
        cmn999Form.addHiddenParam("sch010SelectUsrKbn", form.getSch010SelectUsrKbn());
        cmn999Form.addHiddenParam("sch010SelectDate", form.getSch010SelectDate());
        cmn999Form.addHiddenParam("sch020SelectUsrSid", form.getSch020SelectUsrSid());
        cmn999Form.addHiddenParam("sch030FromHour", form.getSch030FromHour());
        //一覧画面用
        cmn999Form.addHiddenParam("sch100PageNum", form.getSch100PageNum());
        cmn999Form.addHiddenParam("sch100Slt_page1", form.getSch100Slt_page1());
        cmn999Form.addHiddenParam("sch100Slt_page2", form.getSch100Slt_page2());
        cmn999Form.addHiddenParam("sch100OrderKey1", form.getSch100OrderKey1());
        cmn999Form.addHiddenParam("sch100SortKey1", form.getSch100SortKey1());
        cmn999Form.addHiddenParam("sch100OrderKey2", form.getSch100OrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SortKey2());

        cmn999Form.addHiddenParam("sch100SvSltGroup", form.getSch100SvSltGroup());
        cmn999Form.addHiddenParam("sch100SvSltUser", form.getSch100SvSltUser());
        cmn999Form.addHiddenParam("sch100SvSltStartYearFr", form.getSch100SvSltStartYearFr());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthFr", form.getSch100SvSltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltStartDayFr", form.getSch100SvSltStartDayFr());
        cmn999Form.addHiddenParam("sch100SvSltStartYearTo", form.getSch100SvSltStartYearTo());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthTo", form.getSch100SvSltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltStartDayTo", form.getSch100SvSltStartDayTo());
        cmn999Form.addHiddenParam("sch100SvSltEndYearFr", form.getSch100SvSltEndYearFr());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthFr", form.getSch100SvSltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltEndDayFr", form.getSch100SvSltEndDayFr());
        cmn999Form.addHiddenParam("sch100SvSltEndYearTo", form.getSch100SvSltEndYearTo());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthTo", form.getSch100SvSltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltEndDayTo", form.getSch100SvSltEndDayTo());
        cmn999Form.addHiddenParam("sch100SvKeyWordkbn", form.getSch100SvKeyWordkbn());
        cmn999Form.addHiddenParam("sch100SvKeyValue", form.getSch100SvKeyValue());
        cmn999Form.addHiddenParam("sch100SvOrderKey1", form.getSch100SvOrderKey1());
        cmn999Form.addHiddenParam("sch100SvSortKey1", form.getSch100SvSortKey1());
        cmn999Form.addHiddenParam("sch100SvOrderKey2", form.getSch100SvOrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SvSortKey2());

        cmn999Form.addHiddenParam("sch100SltGroup", form.getSch100SltGroup());
        cmn999Form.addHiddenParam("sch100SltUser", form.getSch100SltUser());
        cmn999Form.addHiddenParam("sch100SltStartYearFr", form.getSch100SltStartYearFr());
        cmn999Form.addHiddenParam("sch100SltStartMonthFr", form.getSch100SltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SltStartDayFr", form.getSch100SltStartDayFr());
        cmn999Form.addHiddenParam("sch100SltStartYearTo", form.getSch100SltStartYearTo());
        cmn999Form.addHiddenParam("sch100SltStartMonthTo", form.getSch100SltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SltStartDayTo", form.getSch100SltStartDayTo());
        cmn999Form.addHiddenParam("sch100SltEndYearFr", form.getSch100SltEndYearFr());
        cmn999Form.addHiddenParam("sch100SltEndMonthFr", form.getSch100SltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SltEndDayFr", form.getSch100SltEndDayFr());
        cmn999Form.addHiddenParam("sch100SltEndYearTo", form.getSch100SltEndYearTo());
        cmn999Form.addHiddenParam("sch100SltEndMonthTo", form.getSch100SltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SltEndDayTo", form.getSch100SltEndDayTo());
        cmn999Form.addHiddenParam("sch100KeyWordkbn", form.getSch100KeyWordkbn());
        cmn999Form.addHiddenParam("sch010searchWord", form.getSch010searchWord());
        cmn999Form.addHiddenParam("sch100SvSearchTarget", form.getSch100SvSearchTarget());
        cmn999Form.addHiddenParam("sch100SearchTarget", form.getSch100SearchTarget());
        cmn999Form.addHiddenParam("sch100SvBgcolor", form.getSch100SvBgcolor());
        cmn999Form.addHiddenParam("sch100Bgcolor", form.getSch100Bgcolor());
        cmn999Form.addHiddenParam("sch100CsvOutField", form.getSch100CsvOutField());

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
     * @throws Exception 実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Sch091Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("登録");
        ActionForward forward = null;
        RequestModel reqMdl = getRequestModel(req);

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        // 管理者設定を取得
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        SchAdmConfModel sacMdl = schBiz.getAdmConfModel(con);

        //DB更新
        Sch091Biz biz = new Sch091Biz(getRequestModel(req));
        BaseUserModel umodel = getSessionUserModel(req);
        Sch091ParamModel paramMdl = new Sch091ParamModel();
        paramMdl.setParam(form);
        biz.setPconfSetting(paramMdl, umodel, sacMdl, con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage();
        /** メッセージ 変更 **/
        String change = gsMsg.getMessage(req, "cmn.change");

        //ログ出力処理
        String value = "";
        // 時間
        if (sacMdl.getSadIniTimeStype() == GSConstSchedule.SAD_INITIME_STYPE_USER) {
            value += "[" + gsMsg.getMessage("cmn.time") + "] ";
            value += "\r\n";
            value += gsMsg.getMessage("cmn.starttime") + ":";
            value += paramMdl.getSch091DefFrH() + "時" + paramMdl.getSch091DefFrM() + "分";
            value += "\r\n";
            value += gsMsg.getMessage("cmn.endtime") + ":";
            value += paramMdl.getSch091DefToH() + "時" + paramMdl.getSch091DefToM() + "分";
            value += "\r\n";
        }
        // タイトル色
        value += "[" + gsMsg.getMessage("schedule.10") + "] ";
        String color = paramMdl.getSch091Fcolor();
        value += gsMsg.getMessage("schedule.128") + color;
        SchColMsgDao scmDao = new SchColMsgDao(con);
        SchColMsgModel scmMdl = scmDao.select(Integer.parseInt(color));
        if (scmMdl != null
                && scmMdl.getScmMsg() != null && scmMdl.getScmMsg().length() > 0) {
            value += ":" + scmMdl.getScmMsg();
        }
        // 編集権限
        if (sacMdl.getSadIniEditStype() == GSConstSchedule.SAD_INIEDIT_STYPE_USER) {
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.edit.permissions") + "] ";
            String[] edit = {
                    gsMsg.getMessage("cmn.nolimit"),
                    gsMsg.getMessage("cmn.only.principal.or.registant"),
                    gsMsg.getMessage("cmn.only.affiliation.group.membership")
            };
            value += edit[paramMdl.getSch091Edit()];
        }
        //リマインダー通知
        value += "\r\n";
        value += "[" + gsMsg.getMessage("cmn.reminder") + "] ";
        if (paramMdl.getSch091ReminderTime().equals(
                String.valueOf(GSConstSchedule.REMINDER_TIME_NO))) {
            value += gsMsg.getMessage("cmn.dont.notify");
        } else {
            value += "\r\n";
            value += gsMsg.getMessage("cmn.time");
            value += "：";
            value += gsMsg.getMessage(SchEnumReminderTime.valueOf(
                    Integer.parseInt(paramMdl.getSch091ReminderTime()))
                    .getMsgKey());
        }

        // 公開
        if (sacMdl.getSadInitPublicStype() == GSConstSchedule.SAD_INIPUBLIC_STYPE_USER) {
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.public") + "] ";
            String[] publicMsg = {
                    gsMsg.getMessage("cmn.public"),
                    gsMsg.getMessage("cmn.private"),
                    gsMsg.getMessage("schedule.5"),
                    gsMsg.getMessage("schedule.28"),
                    gsMsg.getMessage("schedule.37"),
                    gsMsg.getMessage("schedule.38")
            };
            value += publicMsg[paramMdl.getSch091PubFlg()];
            if (paramMdl.getSch091PubFlg() == GSConstSchedule.DSP_USRGRP) {
                value += "\r\n";
                value += "[" + gsMsg.getMessage("schedule.36") + "] ";
                CommonBiz cmnBiz = new CommonBiz();
                for (UsrLabelValueBean bean : cmnBiz.getUserLabelList(
                        con, paramMdl.getSch091PubUsrGrpSid())) {
                    value += "\r\n";
                    value += bean.getLabel();
                }
            }
        }
        // 同時修正
        if (sacMdl.getSadIniSameStype() == GSConstSchedule.SAD_INISAME_STYPE_USER) {
            value += "\r\n";
            value += "[" + gsMsg.getMessage("schedule.32") + "] ";
            String[] same = {
                    gsMsg.getMessage("schedule.33"),
                    gsMsg.getMessage("schedule.34")
            };
            value += same[paramMdl.getSch091Same()];
        }
        value += "\r\n[" + gsMsg.getMessage("cmn.initial.display") + "] ";
        String[] initDsp = {
                gsMsg.getMessage("cmn.days2"),
                gsMsg.getMessage("cmn.weeks"),
                gsMsg.getMessage("cmn.monthly"),
                gsMsg.getMessage("schedule.19")
        };
        value += initDsp[paramMdl.getSch091DefDsp()];

        schBiz.outPutLog(
                map, req, res,
                change, GSConstLog.LEVEL_INFO, value);

        //共通メッセージ画面(OK)を表示
        __setCompPageParam(map, req, form);
        forward = map.findForward("gf_msg");
        return forward;
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
        Sch091Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("sch091back");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        GsMessage gsMsg = new GsMessage();
        String key1 = gsMsg.getMessage(req, "cmn.default.setting");
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("dspMod", form.getDspMod());
        cmn999Form.addHiddenParam("listMod", form.getListMod());
        cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
        cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
        cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
        cmn999Form.addHiddenParam("sch010SelectUsrSid", form.getSch010SelectUsrSid());
        cmn999Form.addHiddenParam("sch010SelectUsrKbn", form.getSch010SelectUsrKbn());
        cmn999Form.addHiddenParam("sch010SelectDate", form.getSch010SelectDate());
        cmn999Form.addHiddenParam("sch020SelectUsrSid", form.getSch020SelectUsrSid());
        cmn999Form.addHiddenParam("sch030FromHour", form.getSch030FromHour());
        //一覧画面用
        cmn999Form.addHiddenParam("sch100PageNum", form.getSch100PageNum());
        cmn999Form.addHiddenParam("sch100Slt_page1", form.getSch100Slt_page1());
        cmn999Form.addHiddenParam("sch100Slt_page2", form.getSch100Slt_page2());
        cmn999Form.addHiddenParam("sch100OrderKey1", form.getSch100OrderKey1());
        cmn999Form.addHiddenParam("sch100SortKey1", form.getSch100SortKey1());
        cmn999Form.addHiddenParam("sch100OrderKey2", form.getSch100OrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SortKey2());

        cmn999Form.addHiddenParam("sch100SvSltGroup", form.getSch100SvSltGroup());
        cmn999Form.addHiddenParam("sch100SvSltUser", form.getSch100SvSltUser());
        cmn999Form.addHiddenParam("sch100SvSltStartYearFr", form.getSch100SvSltStartYearFr());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthFr", form.getSch100SvSltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltStartDayFr", form.getSch100SvSltStartDayFr());
        cmn999Form.addHiddenParam("sch100SvSltStartYearTo", form.getSch100SvSltStartYearTo());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthTo", form.getSch100SvSltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltStartDayTo", form.getSch100SvSltStartDayTo());
        cmn999Form.addHiddenParam("sch100SvSltEndYearFr", form.getSch100SvSltEndYearFr());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthFr", form.getSch100SvSltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltEndDayFr", form.getSch100SvSltEndDayFr());
        cmn999Form.addHiddenParam("sch100SvSltEndYearTo", form.getSch100SvSltEndYearTo());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthTo", form.getSch100SvSltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltEndDayTo", form.getSch100SvSltEndDayTo());
        cmn999Form.addHiddenParam("sch100SvKeyWordkbn", form.getSch100SvKeyWordkbn());
        cmn999Form.addHiddenParam("sch100SvKeyValue", form.getSch100SvKeyValue());
        cmn999Form.addHiddenParam("sch100SvOrderKey1", form.getSch100SvOrderKey1());
        cmn999Form.addHiddenParam("sch100SvSortKey1", form.getSch100SvSortKey1());
        cmn999Form.addHiddenParam("sch100SvOrderKey2", form.getSch100SvOrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SvSortKey2());

        cmn999Form.addHiddenParam("sch100SltGroup", form.getSch100SltGroup());
        cmn999Form.addHiddenParam("sch100SltUser", form.getSch100SltUser());
        cmn999Form.addHiddenParam("sch100SltStartYearFr", form.getSch100SltStartYearFr());
        cmn999Form.addHiddenParam("sch100SltStartMonthFr", form.getSch100SltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SltStartDayFr", form.getSch100SltStartDayFr());
        cmn999Form.addHiddenParam("sch100SltStartYearTo", form.getSch100SltStartYearTo());
        cmn999Form.addHiddenParam("sch100SltStartMonthTo", form.getSch100SltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SltStartDayTo", form.getSch100SltStartDayTo());
        cmn999Form.addHiddenParam("sch100SltEndYearFr", form.getSch100SltEndYearFr());
        cmn999Form.addHiddenParam("sch100SltEndMonthFr", form.getSch100SltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SltEndDayFr", form.getSch100SltEndDayFr());
        cmn999Form.addHiddenParam("sch100SltEndYearTo", form.getSch100SltEndYearTo());
        cmn999Form.addHiddenParam("sch100SltEndMonthTo", form.getSch100SltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SltEndDayTo", form.getSch100SltEndDayTo());
        cmn999Form.addHiddenParam("sch100KeyWordkbn", form.getSch100KeyWordkbn());
        cmn999Form.addHiddenParam("sch010searchWord", form.getSch010searchWord());
        cmn999Form.addHiddenParam("sch100SvSearchTarget", form.getSch100SvSearchTarget());
        cmn999Form.addHiddenParam("sch100SearchTarget", form.getSch100SearchTarget());
        cmn999Form.addHiddenParam("sch100SvBgcolor", form.getSch100SvBgcolor());
        cmn999Form.addHiddenParam("sch100Bgcolor", form.getSch100Bgcolor());
        cmn999Form.addHiddenParam("sch100CsvOutField", form.getSch100CsvOutField());

        req.setAttribute("cmn999Form", cmn999Form);

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
    private ActionForward __doBack(ActionMapping map, Sch091Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("戻る");
        ActionForward forward = null;
        forward = map.findForward("sch091back");
        return forward;
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
    private ActionForward __doInit(ActionMapping map, Sch091Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("初期表示");
        ActionForward forward = null;

        con.setAutoCommit(true);
        Sch091Biz biz = new Sch091Biz(getRequestModel(req));
        BaseUserModel umodel = getSessionUserModel(req);

        Sch091ParamModel paramMdl = new Sch091ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        forward = map.getInputForward();
        con.setAutoCommit(false);
        return forward;
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
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    private ActionForward __doReload(ActionMapping map, Sch091Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws
            SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        log__.debug("再表示");
        ActionForward forward = null;
        con.setAutoCommit(true);
        Sch091Biz biz = new Sch091Biz(getRequestModel(req));
        Sch091ParamModel paramMdl = new Sch091ParamModel();
        paramMdl.setParam(form);
        biz.setDspData(paramMdl, con);
        biz.setTitleColorMsg(paramMdl, con);
        GsMessage gsMsg = new GsMessage(req);
        paramMdl.setReminderTimeList(
                SchEnumReminderTime.labelList(gsMsg));
        paramMdl.setFormData(form);

        forward = map.getInputForward();
        con.setAutoCommit(false);
        return forward;
    }
}