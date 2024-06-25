package jp.groupsession.v2.sch.sch081;

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
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.AbstractScheduleAdminAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール基本設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch081Action extends AbstractScheduleAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch081Action.class);

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
        Sch081Form schForm = (Sch081Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("sch081kakunin")) {
            //確認
            forward = __doKakunin(map, schForm, req, res, con);
        } else if (cmd.equals("sch081commit")) {
            //登録
            forward = __doCommit(map, schForm, req, res, con);
        } else if (cmd.equals("sch081back")) {
            //戻るボタンクリック
            forward = __doBack(map, schForm, req, res, con);
        } else {
            //初期表示
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
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Sch081Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws
            SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        log__.debug("確認処理");
        //入力チェック
        ActionErrors errors = form.validateCheck(map, req, con);
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
        Sch081Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());

        //メッセージセット
        String msgState = "edit.kakunin.once";
        GsMessage gsMsg = new GsMessage();
        //スケジュール基本設定
        String textSchBasicSetting = gsMsg.getMessage(req, "cmn.preferences");
        String mkey1 = textSchBasicSetting;
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        cmn999Form.addHiddenParam("cmd", "ok");
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("sch081Crange", form.getSch081Crange());
        cmn999Form.addHiddenParam("sch081HourDiv", form.getSch081HourDiv());
        cmn999Form.addHiddenParam("sch081ColorComment1", form.getSch081ColorComment1());
        cmn999Form.addHiddenParam("sch081ColorComment2", form.getSch081ColorComment2());
        cmn999Form.addHiddenParam("sch081ColorComment3", form.getSch081ColorComment3());
        cmn999Form.addHiddenParam("sch081ColorComment4", form.getSch081ColorComment4());
        cmn999Form.addHiddenParam("sch081ColorComment5", form.getSch081ColorComment5());
        cmn999Form.addHiddenParam("sch081colorKbn", form.getSch081colorKbn());
        cmn999Form.addHiddenParam("sch081ColorComment6", form.getSch081ColorComment6());
        cmn999Form.addHiddenParam("sch081ColorComment7", form.getSch081ColorComment7());
        cmn999Form.addHiddenParam("sch081ColorComment8", form.getSch081ColorComment8());
        cmn999Form.addHiddenParam("sch081ColorComment9", form.getSch081ColorComment9());
        cmn999Form.addHiddenParam("sch081ColorComment10", form.getSch081ColorComment10());
        cmn999Form.addHiddenParam("sch081AmFrHour", form.getSch081AmFrHour());
        cmn999Form.addHiddenParam("sch081AmFrMin", form.getSch081AmFrMin());
        cmn999Form.addHiddenParam("sch081AmToHour", form.getSch081AmToHour());
        cmn999Form.addHiddenParam("sch081AmToMin", form.getSch081AmToMin());
        cmn999Form.addHiddenParam("sch081PmFrHour", form.getSch081PmFrHour());
        cmn999Form.addHiddenParam("sch081PmFrMin", form.getSch081PmFrMin());
        cmn999Form.addHiddenParam("sch081PmToHour", form.getSch081PmToHour());
        cmn999Form.addHiddenParam("sch081PmToMin", form.getSch081PmToMin());
        cmn999Form.addHiddenParam("sch081AllDayFrHour", form.getSch081AllDayFrHour());
        cmn999Form.addHiddenParam("sch081AllDayFrMin", form.getSch081AllDayFrMin());
        cmn999Form.addHiddenParam("sch081AllDayToHour", form.getSch081AllDayToHour());
        cmn999Form.addHiddenParam("sch081AllDayToMin", form.getSch081AllDayToMin());
        cmn999Form.addHiddenParam("sch081RepeatKbnType", form.getSch081RepeatKbnType());
        cmn999Form.addHiddenParam("sch081RepeatKbn", form.getSch081RepeatKbn());
        cmn999Form.addHiddenParam("sch081RepeatMyKbn", form.getSch081RepeatMyKbn());

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
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Sch081Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("登録処理");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //DB更新
        RequestModel reqMdl = getRequestModel(req);
        BaseUserModel umodel = getSessionUserModel(req);
        Sch081Biz biz = new Sch081Biz(getRequestModel(req));
        Sch081ParamModel paramMdl = new Sch081ParamModel();
        paramMdl.setParam(form);
        biz.setCrange(paramMdl, getRequestModel(req), umodel, con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage();
        /** メッセージ 削除 **/
        String change = gsMsg.getMessage(req, "cmn.change");
        //ログ出力処理
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        String value = "";
        // 共有範囲
        value += "[" + gsMsg.getMessage("schedule.123") + "] ";
        String[] share = {
                gsMsg.getMessage("schedule.125"),
                gsMsg.getMessage("schedule.src.2")
        };
        value += share[Integer.parseInt(paramMdl.getSch081Crange())];
        value += "\r\n";
        // 時間単位
        value += "[" + gsMsg.getMessage("schedule.sch081.3") + "] ";
        value += gsMsg.getMessageVal0("schedule.sch081.2", paramMdl.getSch081HourDiv());
        value += "\r\n";
        // 時間マスタ
        value += "[" + gsMsg.getMessage("cmn.time.master") + "] \r\n";
        value += gsMsg.getMessage("cmn.am") + ":";
        String amFrom = paramMdl.getSch081AmFrHour() + "時" + paramMdl.getSch081AmFrMin() + "分";
        String amTo = paramMdl.getSch081AmToHour() + "時" + paramMdl.getSch081AmToMin() + "分";
        value += amFrom + "～" + amTo + "\r\n";
        value += gsMsg.getMessage("cmn.pm") + ":";
        String pmFrom = paramMdl.getSch081PmFrHour() + "時" + paramMdl.getSch081PmFrMin() + "分";
        String pmTo = paramMdl.getSch081PmToHour() + "時" + paramMdl.getSch081PmToMin() + "分";
        value += pmFrom + "～" + pmTo + "\r\n";
        value += gsMsg.getMessage("cmn.allday") + ":";
        String allFrom = paramMdl.getSch081AllDayFrHour() + "時"
                            + paramMdl.getSch081AllDayFrMin() + "分";
        String allTo = paramMdl.getSch081AllDayToHour() + "時"
                            + paramMdl.getSch081AllDayToMin() + "分";
        value += allFrom + "～" + allTo;
        value += "\r\n";
        // タイトルカラーコメント
        value += "[" + gsMsg.getMessage("schedule.128")
                + gsMsg.getMessage("cmn.comment") + "] \r\n";
        value += gsMsg.getMessage("schedule.128") + "1" + ":" + paramMdl.getSch081ColorComment1()
                 + "\r\n";
        value += gsMsg.getMessage("schedule.128") + "2" + ":" + paramMdl.getSch081ColorComment2()
                 + "\r\n";
        value += gsMsg.getMessage("schedule.128") + "3" + ":" + paramMdl.getSch081ColorComment3()
                 + "\r\n";
        value += gsMsg.getMessage("schedule.128") + "4" + ":" + paramMdl.getSch081ColorComment4()
                 + "\r\n";
        value += gsMsg.getMessage("schedule.128") + "5" + ":" + paramMdl.getSch081ColorComment5();
        if (paramMdl.getSch081colorKbn() == GSConstSchedule.SAD_MSG_ADD) {
            value += "\r\n";
            value += gsMsg.getMessage("schedule.128") + "6" + ":"
                            + paramMdl.getSch081ColorComment6() + "\r\n";
            value += gsMsg.getMessage("schedule.128") + "7" + ":"
                            + paramMdl.getSch081ColorComment7() + "\r\n";
            value += gsMsg.getMessage("schedule.128") + "8" + ":"
                            + paramMdl.getSch081ColorComment8() + "\r\n";
            value += gsMsg.getMessage("schedule.128") + "9" + ":"
                            + paramMdl.getSch081ColorComment9() + "\r\n";
            value += gsMsg.getMessage("schedule.128") + "10" + ":"
                            + paramMdl.getSch081ColorComment10();
        }
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("schedule.148") + "] ";
        String[] setting = {
                gsMsg.getMessage("cmn.set.eachuser"),
                gsMsg.getMessage("cmn.set.the.admin")
        };
        value += setting[paramMdl.getSch081RepeatKbnType()];
        value += "\r\n";
        String[] repeat = {
                gsMsg.getMessage("cmn.accepted"),
                gsMsg.getMessage("cmn.not"),
                gsMsg.getMessage("schedule.sch087.1")
        };
        value += repeat[paramMdl.getSch081RepeatKbn()];
        if (paramMdl.getSch081RepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG
                && paramMdl.getSch081RepeatMyKbn() == GSConstSchedule.SCH_REPEAT_MY_KBN_OK) {
            value += " " + gsMsg.getMessage("schedule.sch087.2");
        }

        schBiz.outPutLog(
                map, req, res,
                change, GSConstLog.LEVEL_INFO, value);

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
        Sch081Form form) {

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
        String textSchBasicSetting = gsMsg.getMessage(req, "cmn.preferences");
        String key1 = textSchBasicSetting;
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
     * <br>初期表処理
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
    private ActionForward __doInit(ActionMapping map, Sch081Form form, HttpServletRequest req,
            HttpServletResponse res, Connection con) throws SQLException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        log__.debug("初期表示");
        con.setAutoCommit(true);
        //初期値をセット
        Sch081Biz biz = new Sch081Biz(getRequestModel(req));

        Sch081ParamModel paramMdl = new Sch081ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
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
    private ActionForward __doBack(ActionMapping map, Sch081Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");
        return map.findForward("sch081back");
    }
}
