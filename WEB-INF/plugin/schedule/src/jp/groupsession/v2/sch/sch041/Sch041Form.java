package jp.groupsession.v2.sch.sch041;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.rsv.RelationBetweenScdAndRsvChkBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.rsv111.Rsv111Biz;
import jp.groupsession.v2.rsv.rsv210.Rsv210Model;
import jp.groupsession.v2.sch.GSValidateSchedule;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.sch040.Sch040Biz;
import jp.groupsession.v2.sch.sch040.Sch040Form;
import jp.groupsession.v2.sch.sch041kn.Sch041knBiz;
import jp.groupsession.v2.sch.ui.parts.reserve.ShisetuSelector;
import jp.groupsession.v2.sch.ui.parts.sameuserselect.SameUserSelector;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール繰り返し登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch041Form extends Sch040Form {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch041Form.class);

    /** 削除対象の会社ID */
    private String sch041delCompanyId__ = null;
    /** 削除対象の会社拠点ID */
    private String sch041delCompanyBaseId__ = null;
    /** タイトル色区分 */
    private int sch041colorKbn__ = GSConstSchedule.SAD_MSG_NO_ADD;

    /** 日リスト */
    private ArrayList < LabelValueBean > sch041DayLabel__ = null;
    /** 週リスト */
    private ArrayList < LabelValueBean > sch041WeekLabel__ = null;

    /** 拡張設定 日リスト */
    private ArrayList < LabelValueBean > sch041ExDayLabel__ = null;
    /** 拡張設定 日リスト */
    private ArrayList < LabelValueBean > sch041ExDayOfYearlyLabel__ = null;
    /** 拡張設定 日付設定区分リスト */
    private ArrayList < LabelValueBean > sch041ConfKbnLabel__ = null;

    /** 同時登録ユーザ選択 UI*/
    private SameUserSelector sch041SameUserUI__ =
        SameUserSelector.builder()
            //ユーザ選択 日本語名（入力チェック時に使用）
            .chainLabel(new GsMessageReq("schedule.117", null))
            //ユーザ選択タイプ
            .chainType(EnumSelectType.USER)
            .chainGrpType(EnumGroupSelectType.WITHMYGROUP)
            //選択対象設定
            .chainSelect(Select.builder()
                    //ユーザSID保管パラメータ名
                    .chainParameterName(
                            "sch041SvUsers")
                    )
            //グループ選択保管パラメータ名
            .chainGroupSelectionParamName("sch041GroupSid")
            //スケジュール登録対象ユーザSIDパラメータ名
            .chainOptionParameter(
                    SameUserSelector.OPTIONPARAMKEY_TARGETUSRSID,
                    "sch010SelectUsrSid"
                    )
            .build();
    /** 公開対象ユーザ選択 UI*/
    private UserGroupSelector sch041DisplayTargetUI__ =
            UserGroupSelector.builder()
                //ユーザ選択 日本語名（入力チェック時に使用）
                .chainLabel(new GsMessageReq("schedule.117", null))
                //ユーザ選択タイプ
                .chainType(EnumSelectType.USERGROUP)
                //選択対象設定
                .chainSelect(Select.builder()
                        //ユーザSID保管パラメータ名
                        .chainParameterName(
                                "sch041DisplayTarget")
                        )
                //グループ選択保管パラメータ名
                .chainGroupSelectionParamName("sch041DisplayTargetGroup")
                .build();
    /** 施設選択 UI*/
    private ShisetuSelector sch041SameReserveUI__ =
            ShisetuSelector.builder()
                //ユーザ選択 日本語名（入力チェック時に使用）
                .chainLabel(new GsMessageReq("schedule.117", null))
                //選択対象設定
                .chainSelect(Select.builder()
                        //ユーザSID保管パラメータ名
                        .chainParameterName(
                                "sch041SvReserve")
                        )
                //グループ選択保管パラメータ名
                .chainGroupSelectionParamName("sch040ReserveGroupSid")
                .build();


    /** リマインダー通知 通知設定欄モード */
    private SchEnumRemindMode sch041ReminderEditMode__;

    /**
     * <p>sch041ExDayLabel を取得します。
     * @return sch041ExDayLabel
     */
    public ArrayList<LabelValueBean> getSch041ExDayLabel() {
        return sch041ExDayLabel__;
    }

    /**
     * <p>sch041ExDayLabel をセットします。
     * @param sch041ExDayLabel sch041ExDayLabel
     */
    public void setSch041ExDayLabel(ArrayList<LabelValueBean> sch041ExDayLabel) {
        sch041ExDayLabel__ = sch041ExDayLabel;
    }

    /**
     * <p>sch041WeekLabel を取得します。
     * @return sch041WeekLabel
     */
    public ArrayList<LabelValueBean> getSch041WeekLabel() {
        return sch041WeekLabel__;
    }

    /**
     * <p>sch041WeekLabel をセットします。
     * @param sch041WeekLabel sch041WeekLabel
     */
    public void setSch041WeekLabel(ArrayList<LabelValueBean> sch041WeekLabel) {
        sch041WeekLabel__ = sch041WeekLabel;
    }

    /**
     * <p>sch041DayLabel を取得します。
     * @return sch041DayLabel
     */
    public ArrayList<LabelValueBean> getSch041DayLabel() {
        return sch041DayLabel__;
    }

    /**
     * <p>sch041DayLabel をセットします。
     * @param sch041DayLabel sch041DayLabel
     */
    public void setSch041DayLabel(ArrayList<LabelValueBean> sch041DayLabel) {
        sch041DayLabel__ = sch041DayLabel;
    }

    /**
     * <p>sch041ConfKbnLabel を取得します。
     * @return sch041ConfKbnLabel
     * @see jp.groupsession.v2.sch.sch041.Sch041Form#sch041ConfKbnLabel__
     */
    public ArrayList<LabelValueBean> getSch041ConfKbnLabel() {
        return sch041ConfKbnLabel__;
    }

    /**
     * <p>sch041ConfKbnLabel をセットします。
     * @param sch041ConfKbnLabel sch041ConfKbnLabel
     * @see jp.groupsession.v2.sch.sch041.Sch041Form#sch041ConfKbnLabel__
     */
    public void setSch041ConfKbnLabel(
            ArrayList<LabelValueBean> sch041ConfKbnLabel) {
        sch041ConfKbnLabel__ = sch041ConfKbnLabel;
    }

    /**
     * <p>sch041delCompanyBaseId を取得します。
     * @return sch041delCompanyBaseId
     */
    public String getSch041delCompanyBaseId() {
        return sch041delCompanyBaseId__;
    }

    /**
     * <p>sch041delCompanyBaseId をセットします。
     * @param sch041delCompanyBaseId sch041delCompanyBaseId
     */
    public void setSch041delCompanyBaseId(String sch041delCompanyBaseId) {
        sch041delCompanyBaseId__ = sch041delCompanyBaseId;
    }

    /**
     * <p>sch041delCompanyId を取得します。
     * @return sch041delCompanyId
     */
    public String getSch041delCompanyId() {
        return sch041delCompanyId__;
    }

    /**
     * <p>sch041delCompanyId をセットします。
     * @param sch041delCompanyId sch041delCompanyId
     */
    public void setSch041delCompanyId(String sch041delCompanyId) {
        sch041delCompanyId__ = sch041delCompanyId;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param oldMdl 編集前データ
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl, Connection con,
            ScheduleSearchModel oldMdl)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        String extKbn = getSch041ExtKbn();
        boolean extKbnOK = false;
        boolean getDateFlg = true;

        //毎月 日付指定チェックで使用
        boolean monKbn = extKbn.equals(String.valueOf(GSConstSchedule.EXTEND_KBN_MONTH));
        boolean monDayKbn = getSch041WeekOrDay().equals(
                String.valueOf(GSConstSchedule.EXTEND_MONTH_DAY));
        boolean frKbn = getSch041ConfKbn().equals(String.valueOf(GSConstSchedule.CONF_KBN_FROM));
        boolean toKbn = getSch041ConfKbn().equals(String.valueOf(GSConstSchedule.CONF_KBN_TO));
        if (extKbn == null
                || !ValidateUtil.isNumber(extKbn)
                || Integer.parseInt(extKbn) < GSConstSchedule.EXTEND_KBN_DAY
                || Integer.parseInt(extKbn) > GSConstSchedule.EXTEND_KBN_BIWEEK) {
            String kbn = gsMsg.getMessage("schedule.sch041.3");
            msg = new ActionMessage("error.input.notvalidate.data", kbn);
            errors.add("error.input.notvalidate.data", msg);
        } else {

            //拡張区分
            if (extKbn.equals(String.valueOf(GSConstSchedule.EXTEND_KBN_WEEK))
                    || extKbn.equals(String.valueOf(GSConstSchedule.EXTEND_KBN_BIWEEK))) {
                if (getSch041Dweek() == null) {
                    //曜日
                    String textYoubi = gsMsg.getMessage("cmn.dayofweek");
                    msg = new ActionMessage("error.select.required.text", textYoubi);
                    errors.add("error.select.required.text", msg);
                }
            } else if (monKbn) {
                boolean errorFlg = true;
                if (getSch041WeekOrDay().equals(
                        String.valueOf(GSConstSchedule.EXTEND_MONTH_WEEK))) {
                    for (int i = 1; i <= 5; i++) {
                        if (getSch041Week().equals(String.valueOf(i))) {
                            errorFlg = false;
                            break;
                        }
                    }
                    if (errorFlg) {
                        String textWeek = gsMsg.getMessage("cmn.week");
                        msg = new ActionMessage("error.input.notvalidate.data", textWeek);
                        errors.add("error.input.notvalidate.data", msg);
                    }
                    if (getSch041Dweek() == null) {
                        //曜日
                        String textYoubi = gsMsg.getMessage("cmn.dayofweek");
                        msg = new ActionMessage("error.select.required.text", textYoubi);
                        errors.add("error.select.required.text", msg);
                    }
                } else if (monDayKbn) {
                    int priKbn = Integer.parseInt(getSch041ConfKbn());
                    for (int i = 0; i < 3; i++) {
                        if (priKbn == i) {
                            break;
                        } else if (i == 2) {
                            String textPriKbn = gsMsg.getMessage("schedule.sch041.16");
                            msg = new ActionMessage("error.input.notvalidate.data", textPriKbn);
                            errors.add("error.input.notvalidate.data", msg);
                            getDateFlg = false;
                        }
                    }
                    int days = NullDefault.getInt(getSch041DayOfMonth(), 0);
                    if (frKbn || toKbn) {
                        if (days < 2 || days > 31) {
                            String targetDay = gsMsg.getMessage("schedule.sch041.4");
                            msg = new ActionMessage("error.input.notvalidate.data", targetDay);
                            errors.add("error.input.notvalidate.data", msg);
                        }
                    }
                }
            } else if (extKbn.equals(String.valueOf(GSConstSchedule.EXTEND_KBN_YEAR))) {
                if (!"99".equals(getSch041DayOfYearly())
                        && (!"2".equals(getSch041MonthOfYearly())
                                || !"29".equals(getSch041DayOfYearly()))) {
                    UDate iDate = new UDate();
                    int chkYear = iDate.getYear();
                    int chkMonth = Integer.parseInt(getSch041MonthOfYearly());
                    int chkDay = Integer.parseInt(getSch041DayOfYearly());
                    iDate.setDate(chkYear, chkMonth, chkDay);
                    if (iDate.getYear() != chkYear
                            || iDate.getMonth() != chkMonth
                            || iDate.getIntDay() != chkDay) {
                        String textPeriodStart = gsMsg.getMessage("schedule.sch041.10");
                        msg = new ActionMessage("error.input.notfound.date", textPeriodStart);
                        errors.add("error.input.notfound.date", msg);
                    }
                }
            }

            extKbnOK = errors.isEmpty();
        }

        //開始年月日チェックフラグ(true=入力OK、false=NG)
        boolean fromOk = false;

        UDate frDate = new UDate();
        String frDateStr = getSch041FrDate();
        if (frDateStr == null
                || !ValidateUtil.isSlashDateFormat(frDateStr)
                || !ValidateUtil.isExistDateYyyyMMdd(frDateStr.replaceAll("/", ""))) {
            String textPeriodStart = gsMsg.getMessage("schedule.src.43");
            msg = new ActionMessage("error.input.notfound.date", textPeriodStart);
            errors.add("error.input.notfound.date", msg);
        } else {
            frDate = UDate.getInstanceStr(getSch041FrDate());
            frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            fromOk = true;
        }

        //終了年月日チェックフラグ(true=入力OK、false=NG)
        boolean toOk = false;

        UDate toDate = new UDate();

        String toDateStr = getSch041ToDate();
        if (toDateStr == null
                || !ValidateUtil.isSlashDateFormat(toDateStr)
                || !ValidateUtil.isExistDateYyyyMMdd(toDateStr.replaceAll("/", ""))) {
            String textPeriodEnd = gsMsg.getMessage("schedule.src.44");
            msg = new ActionMessage("error.input.notfound.date", textPeriodEnd);
            errors.add("error.input.notfound.date", msg);
        } else {
            toDate = UDate.getInstanceStr(getSch041ToDate());
            toDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            toDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            toOk = true;
        }
        if (fromOk && toOk) {
            //from～to大小チェック
            if (frDate.compare(frDate, toDate) == UDate.SMALL) {
                //開始 ≦ 終了
                String textStartOrEnd2 = gsMsg.getMessage("cmn.start.or.end2");
                String textPeriodSetting = gsMsg.getMessage("schedule.src.42");
                msg = new ActionMessage("error.input.comp.text",
                                                 textPeriodSetting, textStartOrEnd2);
                errors.add("" + "error.input.comp.text", msg);
                fromOk = false;
                toOk = false;
            }

            //毎月 日付指定時 指定日付が開始～終了内にあるか
            int day = NullDefault.getInt(getSch041Day(), 0);
            if (day == GSConstCommon.LAST_DAY_OF_MONTH) {
                if (frKbn) {
                    day = UDateUtil.addMonthLastDay(frDate, -1).getIntDay();
                } else if (toKbn) {
                    day = toDate.getMaxDayOfMonth();
                }
            }
            String ddate = gsMsg.getMessage("schedule.sch041.12") + gsMsg.getMessage("cmn.of")
                    + gsMsg.getMessage("tcd.tcd010.15") + gsMsg.getMessage("cmn.day");
            String sDate = gsMsg.getMessage("schedule.sch041.8")
                    + gsMsg.getMessage("cmn.of") + gsMsg.getMessage("cmn.startdate");
            String eDate = gsMsg.getMessage("cmn.end.date");
            int iSDay = frDate.getIntDay();
            int iEDay = toDate.getIntDay();
            if (monKbn && monDayKbn && (frKbn || toKbn)
                    && UDateUtil.diffDay(frDate, toDate) < 30) {
                if (iSDay < iEDay && (day < iSDay || day > iEDay)) {
                    msg = new ActionMessage("error.input.lenge", ddate, sDate, eDate);
                    errors.add("error.input.lenge", msg);
                    getDateFlg = false;
                } else if (iSDay > iEDay
                        && (!(day > iSDay && day <= 31)
                        && !(day >= 1 && day <= iEDay))) {
                    msg = new ActionMessage("error.input.lenge", ddate, sDate, eDate);
                    errors.add("error.input.lenge", msg);
                    getDateFlg = false;
                }
            }
        }

        boolean timeOk = true;
        if (getSch041TimeKbn().equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {
            if (StringUtil.isNullZeroString(getSch041FrTime())
                    || StringUtil.isNullZeroString(getSch041ToTime())) {
                //時分を
                String textJifun = gsMsg.getMessage("cmn.time2");
                        msg = new ActionMessage("error.input.required.text", textJifun);
                        errors.add("" + "error.input.required.text", msg);
                        timeOk = false;
            }

            if (timeOk && (!ValidateUtil.isTimeFormat(getSch041FrTime())
                    || !ValidateUtil.isTimeFormat(getSch041ToTime()))) {
                String time = gsMsg.getMessage("cmn.time");
                msg = new ActionMessage("error.input.notvalidate.data", time);
                errors.add("" + "error.input.notvalidate.data", msg);
                timeOk = false;
            }
        }

        //個別チェックOKの場合
        if (timeOk && getSch041TimeKbn().equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {
            __setTime();
            UDate frTimeDate = new UDate();
            UDate toTimeDate = frTimeDate.cloneUDate();
            if (getSch041FrHour().equals("-1") && getSch041FrMin().equals("-1")) {
                frTimeDate.setHour(GSConstSchedule.DAY_START_HOUR);
                frTimeDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
            } else {
                frTimeDate.setHour(Integer.parseInt(getSch041FrHour()));
                frTimeDate.setMinute(Integer.parseInt(getSch041FrMin()));
            }
            if (getSch041ToHour().equals("-1") && getSch041ToMin().equals("-1")) {
                toTimeDate.setHour(GSConstSchedule.DAY_END_HOUR);
                toTimeDate.setMinute(GSConstSchedule.DAY_SYSMAX_MINUTES);
            } else {
                toTimeDate.setHour(Integer.parseInt(getSch041ToHour()));
                toTimeDate.setMinute(Integer.parseInt(getSch041ToMin()));
            }
            //from～to大小チェック
            if (frDate.compare(frTimeDate, toTimeDate) != UDate.LARGE) {
                //開始 < 終了
                String textStartLessEnd = gsMsg.getMessage("cmn.start.lessthan.end");
                //開始・終了
                String textStartEnd = gsMsg.getMessage("cmn.start.end");
                msg = new ActionMessage("error.input.comp.text", textStartEnd, textStartLessEnd);
                errors.add("" + "error.input.comp.text", msg);
            }
            //時間単位チェック
            int frTime = frTimeDate.getIntMinute();
            int toTime = toTimeDate.getIntMinute();
            errors = GSValidateSchedule.validateTimeUnit(
                    con, errors, gsMsg, frTime, toTime);
        }

        Sch041knBiz sch041knBiz = new Sch041knBiz(con, reqMdl);
        if (fromOk && toOk && extKbnOK && getDateFlg) {
            __setDate();
            //有効な日付があるかチェック
            HashMap<String, UDate> dateMap =
                    sch041knBiz.getInsertDateList(this, sessionUsrSid, con);
            if (dateMap.isEmpty()) {
                //日付
                String textDate = gsMsg.getMessage("cmn.date2");
                msg = new ActionMessage("search.data.notfound", textDate);
                errors.add("search.data.notfound", msg);
            }
        }
        //タイトルのチェック
        if (__checkNoInput(errors, getSch041Title(),
                           "sch041Title", gsMsg.getMessage("cmn.title"))) {
            if (__checkRange(
                    errors,
                    getSch041Title(),
                    "sch041Title",
                    gsMsg.getMessage("cmn.title"),
                    GSConstSchedule.MAX_LENGTH_TITLE)) {
                //先頭スペースチェック
                if (ValidateUtil.isSpaceStart(getSch041Title())) {
                    //タイトル
                    String textTitle = gsMsg.getMessage("cmn.title");
                    msg = new ActionMessage("error.input.spase.start",
                            textTitle);
                    StrutsUtil.addMessage(errors, msg, "sch041Title");
                //タブスペースチェック
                } else if (ValidateUtil.isTab(getSch041Title())) {
                    //タイトル
                    String textTitle = gsMsg.getMessage("cmn.title");
                    msg = new ActionMessage("error.input.tab.text",
                            textTitle);
                    StrutsUtil.addMessage(errors, msg, "sch041Title");
                } else {
                    __checkJisString(
                            errors,
                            getSch041Title(),
                            "sch041Title",
                            gsMsg.getMessage("cmn.title"));
                }
            }

        }
        boolean valueError = false;
        String content = gsMsg.getMessage("cmn.content");
        //内容のチェック
        if (__checkRangeTextarea(
                errors,
                getSch041Value(),
                "sch041Value",
                content,
                GSConstSchedule.MAX_LENGTH_VALUE)) {
            if (!StringUtil.isNullZeroString(getSch041Value())) {
                //スペースのみチェック
                if (!valueError && ValidateUtil.isSpaceOrKaigyou(getSch041Value())) {
                    msg = new ActionMessage("error.input.spase.cl.only", content);
                    StrutsUtil.addMessage(errors, msg, "sch041Value");
                    valueError = true;
                }
                //先頭スペースチェック
                if (!valueError && ValidateUtil.isSpaceStart(getSch041Value())) {
                    msg = new ActionMessage("error.input.spase.start",
                                            content);
                    StrutsUtil.addMessage(errors, msg, "sch041Value");
                    valueError = true;
                }
                if (!valueError) {
                    __checkJisString(
                            errors,
                            getSch041Value(),
                            "sch041Value",
                            content);
                }
            }
        }

        //リマインダー通知
        SchEnumRemindMode remindMode = SchEnumRemindMode.valueOf(
                NullDefault.getInt(getSch010SelectUsrKbn(), GSConstSchedule.USER_KBN_USER),
                sessionUsrSid,
                NullDefault.getInt(getSch010SelectUsrSid(), -1));

        if (remindMode.isAbleSelReminder()) {
            if (remindMode != SchEnumRemindMode.GROUP) {

                String reminderTime = gsMsg.getMessage("cmn.reminder")
                        + gsMsg.getMessage("cmn.time");
                boolean reminderTimeFlg = false;
                if (StringUtil.isNullZeroStringSpace(getSch041ReminderTime())
                        || !ValidateUtil.isNumber(getSch041ReminderTime())
                        || Integer.parseInt(getSch041ReminderTime())
                             < GSConstSchedule.REMINDER_TIME_NO
                        || Integer.parseInt(getSch041ReminderTime())
                             > GSConstSchedule.REMINDER_TIME_ONE_WEEK) {
                    reminderTimeFlg = true;
                }
                if (reminderTimeFlg) {
                    setSch041ReminderTime(String.valueOf(
                            GSConstSchedule.REMINDER_TIME_FIFTEEN_MINUTES));
                    String prefix = "sch041ReminderTime.";
                    msg = new ActionMessage("error.input.notvalidate.data", reminderTime);
                    errors.add(prefix + "error.input.notvalidate.data", msg);
                }

            } else if (remindMode == SchEnumRemindMode.GROUP) {
                //リマインダー通知グループラジオボタンチェック
                String reminder = gsMsg.getMessage("cmn.reminder");
                boolean reminderGroupFlg = false;
                if (StringUtil.isNullZeroStringSpace(getSch041TargetGroup())
                        || !ValidateUtil.isNumber(getSch041TargetGroup())
                        || Integer.parseInt(getSch041TargetGroup())
                             < GSConstSchedule.REMINDER_USE_NO
                        || Integer.parseInt(getSch041TargetGroup())
                             > GSConstSchedule.REMINDER_USE_YES) {
                    reminderGroupFlg = true;
                }
                if (reminderGroupFlg) {
                    String prefix = "sch091ReminderGroup.";
                    msg = new ActionMessage("error.input.notvalidate.data", reminder);
                    errors.add(prefix + "error.input.notvalidate.data", msg);
                }
            }
        }

        //公開区分
        __checkPublic(errors, gsMsg, con);

        //公開対象
        if (getSch041Public().equals(String.valueOf(GSConstSchedule.DSP_USRGRP))) {
            __checkDisplayTarget(errors, gsMsg, con);
        }

        boolean bikoError = false;
        String biko = gsMsg.getMessage("cmn.memo");
        //備考のチェック
        if (__checkRangeTextarea(
                errors,
                getSch041Biko(),
                "sch041Biko",
                biko,
                GSConstSchedule.MAX_LENGTH_BIKO)) {
            if (!StringUtil.isNullZeroString(getSch041Biko())) {
                //スペースのみチェック
                if (!bikoError && ValidateUtil.isSpaceOrKaigyou(getSch041Biko())) {
                    msg = new ActionMessage("error.input.spase.cl.only", biko);
                    StrutsUtil.addMessage(errors, msg, "sch041Biko");
                    bikoError = true;
                }
                //先頭スペースチェック
                if (!bikoError && ValidateUtil.isSpaceStart(getSch041Biko())) {
                    msg = new ActionMessage("error.input.spase.start", biko);
                    StrutsUtil.addMessage(errors, msg, "sch041Biko");
                    bikoError = true;
                }
                if (!bikoError) {
//                  JIS
                    __checkJisString(
                            errors,
                            getSch041Biko(),
                            "sch041Biko",
                            biko);
                }
            }
        }
        //同時登録スケジュールの編集権限チェック
        validateExSchPowerCheck(reqMdl, errors, con);

        if (errors.isEmpty()) {
            //スケジュール重複登録チェック
            errors = validateExSchRepeatCheck(reqMdl, errors, con, sessionUsrSid, oldMdl);
        }

        Sch040Biz sch040Biz = new Sch040Biz(con, reqMdl);

        //施設予約の編集権限チェック
        validateExResPowerCheck(reqMdl, errors, con);


        log__.info("施設予約チェックＳＴＡＲＴ");
        //施設予約チェック
        if (!errors.isEmpty()) {
            log__.info("!errors.isEmpty()");
            return errors;
        }
        if (getSch041SvReserve() == null
                || getSch041SvReserve().length < 1
                || getSch041TimeKbn().equals(String.valueOf(GSConstSchedule.TIME_NOT_EXIST))) {
            return errors;
        }

        String[] rsdSids = null;
        if (getSch041TimeKbn().equals(String.valueOf(GSConstSchedule.TIME_EXIST))) {
            rsdSids = getSch041SvReserve();
        }
        log__.info("rsdSids==>" + rsdSids.toString());
        if (rsdSids != null) {
            log__.info("rsdSids != null)");
            RsvSisYrkDao yrkDao = new RsvSisYrkDao(con);
            boolean errorFlg = false;

            Sch041ParamModel paramMdl = new Sch041ParamModel();
            paramMdl.setParam(this);
            HashMap<String, String> dayMap = sch041knBiz.getInsertStrDateList(
                    paramMdl, sessionUsrSid, con);
            paramMdl.setFormData(this);

            if (dayMap.isEmpty()) {
                //日付
                String textDate = gsMsg.getMessage("cmn.date2");
                log__.info("dayMap.isEmpty()");
                msg = new ActionMessage("search.data.notfound", textDate);
                errors.add("search.data.notfound", msg);
            } else {
                log__.info("dayMap.isEmpty() else ");
                ArrayList<String> dayList = new ArrayList<String>(dayMap.values());
                Collections.sort(dayList);
                Rsv111Biz biz = new Rsv111Biz(reqMdl, con);
                int i = 0;
                //施設ループ
                for (String rsdSid : rsdSids) {
                    log__.info("施設ＳＩＤ==>" + rsdSid);
                    int sisetuSid = Integer.parseInt(rsdSid);
                    Rsv210Model dataMdl = biz.getGroupCheckData(sisetuSid);
                    if (dataMdl != null) {

                        List<RsvSisYrkModel> ngList = new ArrayList<RsvSisYrkModel>();
                        List<RsvSisYrkModel> allNgList = new ArrayList<RsvSisYrkModel>();

                        //予約可能期限チェック(期限が設定されていればチェックする)
                        String kigen = dataMdl.getRsdProp6();
                        if (!StringUtil.isNullZeroString(kigen)) {

                            //施設グループ管理者の場合は予約可能期限チェックをパスする
                            RsvCommonBiz rsvBiz = new RsvCommonBiz();
                            if (!rsvBiz.isGroupAdmin(con, sisetuSid, sessionUsrSid)) {

                                UDate now = new UDate();
                                UDate udKigen = now.cloneUDate();
                                udKigen.addDay(Integer.parseInt(kigen));

                                String kigenYmd = udKigen.getDateString();

                                //ソート済の配列の最後(最後の日付)を取得
                                String lastDateStr = dayList.get(dayList.size() - 1);
                                UDate toDateUd = new UDate();
                                toDateUd.setDate(lastDateStr);
                                String chkYmd = toDateUd.getDateString();

                                if (Integer.parseInt(chkYmd) > Integer.parseInt(kigenYmd)) {
                                    //開始・終了
                                    String textDayAfter = gsMsg.getMessage("cmn.days.after");
                                    String kigenStr =
                                            "※"
                                                    + dataMdl.getRsdProp6()
                                                    + textDayAfter;

                                    msg = new ActionMessage("error.kigen.over2.sisetu", kigenStr);
                                    StrutsUtil.addMessage(errors, msg, "sisetu");
                                    errorFlg = true;
                                }
                            }
                        }
                        log__.info("施設重複のチェック==>" + dataMdl.getRsdProp7());
//                      重複のチェック(重複登録 = 不可の場合にチェック)
                        String tyohuku = dataMdl.getRsdProp7();
                        if (!errorFlg
                                && !StringUtil.isNullZeroString(tyohuku)
                                && Integer.parseInt(tyohuku) == GSConstReserve.PROP_KBN_HUKA) {
                            UDate resfrDate = null;
                            UDate restoDate = null;

                            if (rsdSids != null) {
                                //登録時刻を取得
                                for (String date : dayList) {
                                    log__.info("施設重複のチェック日付==>" + date);
                                    resfrDate = new UDate();
                                    resfrDate.setDate(date);
                                    resfrDate.setZeroHhMmSs();
                                    resfrDate.setHour(Integer.parseInt(getSch041FrHour()));
                                    resfrDate.setMinute(Integer.parseInt(getSch041FrMin()));
                                    restoDate = resfrDate.cloneUDate();
                                    restoDate.setZeroHhMmSs();
                                    restoDate.setHour(Integer.parseInt(getSch041ToHour()));
                                    restoDate.setMinute(Integer.parseInt(getSch041ToMin()));

                                    log__.info("処理モードCmd==>" + getCmd());

                                    //新規モード
                                    if (getCmd().equals(GSConstSchedule.CMD_ADD)) {
                                        for (String sid : rsdSids) {
                                            ngList = yrkDao.getYrkNgList(
                                                    -1,
                                                    Integer.parseInt(sid),
                                                    resfrDate,
                                                    restoDate);
                                            allNgList.addAll(ngList);
                                        }
                                    //編集モード
                                    } else if (getCmd().equals(GSConstSchedule.CMD_EDIT)) {
                                        ArrayList<RsvSisYrkModel> yrkList = null;
                                        if (oldMdl.getScdRsSid() != -1) {
                                            yrkList = yrkDao.getScheduleRserveData(
                                                    oldMdl.getScdRsSid()
                                                    );
                                        }
                                        RsvSisYrkModel yrkMdl = null;

                                        int rsrSid = -1;
                                        if (yrkList != null && !yrkList.isEmpty()) {
                                            rsrSid = yrkList.get(0).getRsrRsid();
                                        }

                                        for (String sid : rsdSids) {
                                            yrkMdl = getReserveData(yrkList, Integer.parseInt(sid));

                                            if (yrkMdl == null) {
                                                ngList = yrkDao.getSchYrkNgList(rsrSid, -1,
                                                        Integer.parseInt(rsdSid),
                                                        resfrDate,
                                                        restoDate);

                                            } else {

                                                ngList = yrkDao.getSchKakutyoYrkNgList(
                                                        yrkMdl.getRsrRsid(),
                                                        yrkMdl.getRsdSid(),
                                                        resfrDate,
                                                        restoDate);

//                                                if (!yrkDao.isYrkOk(
//                                                        yrkMdl.getRsySid(),
//                                                        yrkMdl.getRsdSid(),
//                                                        frDate,
//                                                        toDate)) {
//                                                    yrkOkFlg = false;
//                                                }
                                            }
                                            allNgList.addAll(ngList);

                                        }
                                    }

                                }
                            }
                        }
                        //重複チェック
                        if (allNgList != null && allNgList.size() > 0) {

                            String textSchedule = gsMsg.getMessage("cmn.reserve");
                            msg = new ActionMessage("error.input.dup", textSchedule);
                            StrutsUtil.addMessage(errors, msg, "rsv110YrkEr");


                            for (RsvSisYrkModel yrkModel : allNgList) {

                                String schTime = UDateUtil.getYymdJ(yrkModel.getRsyFrDate(),
                                                                    reqMdl);
                                schTime += UDateUtil.getSeparateHMJ(yrkModel.getRsyFrDate(),
                                                                    reqMdl);
                                schTime += "～";
                                schTime += UDateUtil.getYymdJ(yrkModel.getRsyToDate(), reqMdl);
                                schTime += UDateUtil.getSeparateHMJ(yrkModel.getRsyToDate(),
                                                                    reqMdl);


                                msg = new ActionMessage("error.input.dup.sch",
                                        schTime,
                                        StringUtilHtml.transToHTmlPlusAmparsant(
                                                yrkModel.getRsdName()),
                                        StringUtilHtml.transToHTmlPlusAmparsant(
                                                yrkModel.getRsyMok()));

                                StrutsUtil.addMessage(errors, msg,
                                        "rsv110YrkErr"
                                        + String.valueOf(yrkModel.getRsySid()) + String.valueOf(i));
                                i++;
                            }
                        }
                    }
                }



            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateExPowerCheck(
            RequestModel reqMdl,
            Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        //同時登録スケジュールの編集権限チェックを行う
        errors = validateExSchPowerCheck(reqMdl, errors, con);
        //同時登録施設予約の編集権限チェックを行う
        errors = validateExResPowerCheck(reqMdl, errors, con);
        return errors;
    }
    /**
     * <br>[機  能] 同時登録スケジュールの編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors アクションエラー
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateExSchPowerCheck(
            RequestModel reqMdl,
            ActionErrors errors,
            Connection con) throws SQLException {

        ActionMessage msg = null;

        int schSid = NullDefault.getInt(getSch010SchSid(), -1);
        log__.debug("スケジュールSID==>" + schSid);
        //同時登録スケジュールの編集権限チェック
        if (getCmd().equals(GSConstSchedule.CMD_EDIT)
                || getCmd().equals(GSConstSchedule.CMD_DELETE)) {
            //拡張登録されたスケジュール全てをチェック
            Sch041Biz biz = new Sch041Biz(reqMdl);
            if (!biz.isAllEditOkEx(schSid, reqMdl, con)) {
                msg = new ActionMessage("error.schedule.edit.cant.user");
                StrutsUtil.addMessage(errors, msg, "adduser");
            }
        }

        //同時登録スケジュールにスケジュール登録不可ユーザが含まれるかをチェック
        errors = validateSchNotAccessUser(getSch041SvUsers(), reqMdl, errors, con);

        return errors;
    }
    /**
     * <br>[機  能] 同時登録施設予約の編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors アクションエラー
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateExResPowerCheck(
            RequestModel reqMdl,
            ActionErrors errors,
            Connection con) throws SQLException {

        ActionMessage msg = null;
        int schSid = NullDefault.getInt(getSch010SchSid(), -1);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //アクセス権限チェック
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        CommonBiz cmnBiz = new CommonBiz();
        Sch040Biz biz = new Sch040Biz(con, reqMdl);


        //編集権限のない施設数を取得する。
        boolean rsvAdmin = cmnBiz.isPluginAdmin(con, usModel, GSConstSchedule.PLUGIN_ID_RESERVE);
        Sch041ParamModel paramMdl = new Sch041ParamModel();
        paramMdl.setParam(this);
        int count = biz.getCanNotEditRsvCount(paramMdl, usModel.getUsrsid(), con, rsvAdmin);
        paramMdl.setFormData(this);

        if (count > 0) {
            //施設予約アクセス権限なし
            msg = new ActionMessage("error.myself.auth");
            StrutsUtil.addMessage(errors, msg, "error.myself.auth");
            return errors;
        }

        //変更
        String textChange = gsMsg.getMessage("cmn.change");
        //施設予約エラーチェック
        //同時登録施設予約の編集権限チェック
        if (getCmd().equals(GSConstSchedule.CMD_EDIT)) {

            RelationBetweenScdAndRsvChkBiz rsvChkBiz
                = new RelationBetweenScdAndRsvChkBiz(reqMdl, con);
            int errorCd = rsvChkBiz.isRsvEdit(
                    schSid,
                    RelationBetweenScdAndRsvChkBiz.CHK_KBN_KURIKAESHI);
            if (errorCd == RelationBetweenScdAndRsvChkBiz.ERR_CD_SCD_CANNOT_EDIT) {
                //施設予約に対する編集
                String textRsvEdit = gsMsg.getMessage("schedule.src.32");
                msg = new ActionMessage("error.edit.power.user", textRsvEdit, textChange);
                StrutsUtil.addMessage(errors, msg, "addres");
            }

        }
        return errors;
    }
    /**
     * <br>[機  能] 指定された項目の未入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkNoInput(ActionErrors errors,
                                    String value,
                                    String element,
                                    String elementName) {
        boolean result = true;
        ActionMessage msg = null;

        if (StringUtil.isNullZeroString(value)) {
            msg = new ActionMessage("error.input.required.text", elementName);
            errors.add(element + "error.input.required.text", msg);
            result = false;
        } else {
            //スペースのみの入力かチェック
            if (ValidateUtil.isSpace(value)) {
                msg = new ActionMessage("error.input.spase.only", elementName);
                errors.add(element + "error.input.spase.only", msg);
                result = false;
            }

        }

        return result;
    }

    /**
     * <br>[機  能] 指定された項目の桁数チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @param range 桁数
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkRange(ActionErrors errors,
                                String value,
                                String element,
                                String elementName,
                                int range) {
        boolean result = true;
        ActionMessage msg = null;

        //MAX値を超えていないか
        if (value.length() > range) {
            msg = new ActionMessage("error.input.length.text", elementName,
                    String.valueOf(range));
            errors.add(element + "error.input.length.text", msg);
            result = false;
        }
        return result;
    }

    /**
     * <br>[機  能] 指定された項目の桁数チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @param range 桁数
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkRangeTextarea(ActionErrors errors,
                                String value,
                                String element,
                                String elementName,
                                int range) {
        boolean result = true;
        ActionMessage msg = null;

        //MAX値を超えていないか
        if (value.length() > range) {
            msg = new ActionMessage("error.input.length.textarea", elementName,
                    String.valueOf(range));
            errors.add(element + "error.input.length.textarea", msg);
            result = false;
            log__.debug("error:7");
        }
        return result;
    }

    /**
     * <br>[機  能] 指定された項目がJIS第2水準文字かチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkJisString(ActionErrors errors,
                                String value,
                                String element,
                                String elementName) {
        boolean result = true;
        ActionMessage msg = null;
        //JIS第2水準文字か
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(value);
            msg = new ActionMessage("error.input.njapan.text", elementName, nstr);
            errors.add(element + "error.input.njapan.text", msg);
            result = false;
        }
        return result;
    }

    /**
     * <br>[機  能] 公開区分の選択値チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param gsMsg GsMessage
     * @param con DBコネクション
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __checkPublic(
            ActionErrors errors, GsMessage gsMsg, Connection con) throws SQLException {

        ActionMessage msg = null;
        String textPublic = gsMsg.getMessage("cmn.public");

        if (getSch041Public() == null || !ValidateUtil.isNumber(getSch041Public())) {
            String prefix = "sch041Public.";
            msg = new ActionMessage("error.input.notvalidate.data", textPublic);
            errors.add(prefix + "error.input.notvalidate.data", msg);

            return errors;
        }

        int schKbn = NullDefault.getInt(getSch010SelectUsrKbn(), GSConstSchedule.USER_KBN_USER);
        int publicValue = Integer.parseInt(getSch041Public());
        boolean publicErrorFlg = false;
        if (schKbn == GSConstSchedule.USER_KBN_USER
                && (publicValue < GSConstSchedule.DSP_PUBLIC
                        || publicValue > GSConstSchedule.DSP_TITLE)) {
            publicErrorFlg = true;
        }
        if (schKbn == GSConstSchedule.USER_KBN_GROUP
                && (publicValue < GSConstSchedule.DSP_PUBLIC
                        || publicValue > GSConstSchedule.DSP_NOT_PUBLIC)) {
            publicErrorFlg = true;
        }
        if (publicErrorFlg) {
            String prefix = "sch041Public.";
            msg = new ActionMessage("error.input.notvalidate.data", textPublic);
            errors.add(prefix + "error.input.notvalidate.data", msg);
        }

        return errors;
    }

    /**
     * <br>[機  能] 公開対象の選択値チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param gsMsg GsMessage
     * @param con コネクション
     * @return ActionErrors
     */
    private ActionErrors __checkDisplayTarget(
            ActionErrors errors, GsMessage gsMsg, Connection con) throws SQLException {

        String[] displayTarget = getSch041DisplayTarget();
        ActionMessage msg = null;

        String textTarget = gsMsg.getMessage("schedule.36");
        String prefix = "sch041DisplayTarget.";
        //入力確認
        if (displayTarget == null || displayTarget.length < 1) {
            msg = new ActionMessage("error.select.required.text", textTarget);
            errors.add(prefix + "error.select.required.text", msg);
            return errors;
        }

        boolean errorFlg = true;
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        List<String> usrSids = new ArrayList<String>();
        for (String targetSid : displayTarget) {
            //グループ
            if (targetSid.startsWith("G")) {
                targetSid = targetSid.substring(1);
                if (!ValidateUtil.isNumber(targetSid)) {
                    break;
                }
                grpSids.add(Integer.parseInt(targetSid));
            } else {
            //ユーザ
                if (!ValidateUtil.isNumber(targetSid)) {
                    break;
                }
                usrSids.add(targetSid);
            }
            errorFlg = false;
        }

        if (errorFlg) {
            msg = new ActionMessage("error.input.notvalidate.data", textTarget);
            errors.add(prefix + "error.input.notvalidate.data", msg);
        }


        ArrayList<GroupModel> glist = new ArrayList<GroupModel>();
        ArrayList<BaseUserModel> ulist = new ArrayList<BaseUserModel>();

        //存在しないグループ・ユーザを除外した状態でチェック
        if (!grpSids.isEmpty()) {
            UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
            glist = gdao.selectGroupNmListOrderbyClass(grpSids);
        }
        if (!usrSids.isEmpty()) {
            UserBiz userBiz = new UserBiz();
            ulist = userBiz.getBaseUserList(con,
                                            usrSids.toArray(new String[usrSids.size()]));
        }

        if (ulist.isEmpty() && glist.isEmpty()) {
            msg = new ActionMessage("error.select.required.text", textTarget);
            StrutsUtil.addMessage(
                    errors, msg, "sch041DisplayTarget");
        }


        return errors;
    }

    /**
     * <br>[機  能] 同時登録スケジュールの重複登録チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors アクションエラー
     * @param con コネクション
     * @param sessionUsrSid ユーザSID
     * @param oldMdl 編集前データ
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateExSchRepeatCheck(
            RequestModel reqMdl,
            ActionErrors errors,
            Connection con,
            int sessionUsrSid,
            ScheduleSearchModel oldMdl
            ) throws SQLException {

        ActionMessage msg = null;

        Sch041Biz biz = new Sch041Biz(reqMdl);
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);

        //重複登録 NGスケジュール一覧を取得する。
        Sch041ParamModel paramMdl = new Sch041ParamModel();
        paramMdl.setParam(this);
        List<SchDataModel> rptSchList
        = biz.getExSchWarningList(
                reqMdl, paramMdl, sessionUsrSid, con, GSConstSchedule.SCH_REPEAT_KBN_NG, oldMdl);
        paramMdl.setFormData(this);


        if (rptSchList != null && rptSchList.size() > 0) {
            int i = 1;

            GsMessage gsMsg = new GsMessage(reqMdl);
            String textSchedule = gsMsg.getMessage("schedule.108");
            String title = "";

            msg = new ActionMessage("error.input.dup", textSchedule);
            StrutsUtil.addMessage(errors, msg, "error.input.dup");

            for (SchDataModel ngMdl : rptSchList) {

                //公開区分で判定してタイトルを取得
                title = schBiz.getDspTitle(ngMdl, sessionUsrSid);
                String schTime = UDateUtil.getYymdJ(ngMdl.getScdFrDate(), reqMdl);
                schTime += UDateUtil.getSeparateHMJ(ngMdl.getScdFrDate(), reqMdl);
                schTime += "～";
                schTime += UDateUtil.getYymdJ(ngMdl.getScdToDate(), reqMdl);
                schTime += UDateUtil.getSeparateHMJ(ngMdl.getScdToDate(), reqMdl);

                msg = new ActionMessage("error.input.dup.sch",
                        schTime,
                        StringUtilHtml.transToHTmlPlusAmparsant(title),
                        StringUtilHtml.transToHTmlPlusAmparsant(ngMdl.getScdUserName()));
                StrutsUtil.addMessage(errors, msg, "error.input.dup.sch" + i);
                i++;
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 年,月,日をそれぞれパラメータに設定する
     * <br>[解  説]
     * <br>[備  考]
     * @parma dateStr 日付文字列
     */
    private void __setDate() {
        String[] startDate = getSch041FrDate().split("/");
        setSch041FrYear(startDate[0]);
        setSch041FrMonth(startDate[1]);
        setSch041FrDay(startDate[2]);

        String[] endDate = getSch041ToDate().split("/");
        setSch041ToYear(endDate[0]);
        setSch041ToMonth(endDate[1]);
        setSch041ToDay(endDate[2]);
    }

    /**
     * <br>[機  能] 時, 分をそれぞれパラメータに設定する
     * <br>[解  説]
     * <br>[備  考]
     * @parma dateStr 日付文字列
     */
    private void __setTime() {
        String[] startTime = getSch041FrTime().split(":");
        setSch041FrHour(startTime[0]);
        setSch041FrMin(startTime[1]);

        String[] endTime = getSch041ToTime().split(":");
        setSch041ToHour(endTime[0]);
        setSch041ToMin(endTime[1]);
    }

    /**
     * <p>sch041colorKbn を取得します。
     * @return sch041colorKbn
     */
    public int getSch041colorKbn() {
        return sch041colorKbn__;
    }

    /**
     * <p>sch041colorKbn をセットします。
     * @param sch041colorKbn sch041colorKbn
     */
    public void setSch041colorKbn(int sch041colorKbn) {
        sch041colorKbn__ = sch041colorKbn;
    }

    /**
     * <p>sch041ExDayOfYearlyLabel を取得します。
     * @return sch041ExDayOfYearlyLabel
     */
    public ArrayList < LabelValueBean > getSch041ExDayOfYearlyLabel() {
        return sch041ExDayOfYearlyLabel__;
    }

    /**
     * <p>sch041ExDayOfYearlyLabel をセットします。
     * @param sch041ExDayOfYearlyLabel sch041ExDayOfYearlyLabel
     */
    public void setSch041ExDayOfYearlyLabel(ArrayList < LabelValueBean > sch041ExDayOfYearlyLabel) {
        sch041ExDayOfYearlyLabel__ = sch041ExDayOfYearlyLabel;
    }

    /**
     * <p>sch041ReminderEditMode を取得します。
     * @return sch041ReminderEditMode
     * @see jp.groupsession.v2.sch.sch041.Sch041Form#sch041ReminderEditMode__
     */
    public SchEnumRemindMode getSch041ReminderEditMode() {
        return sch041ReminderEditMode__;
    }

    /**
     * <p>sch041ReminderEditMode をセットします。
     * @param sch041ReminderEditMode sch041ReminderEditMode
     * @see jp.groupsession.v2.sch.sch041.Sch041Form#sch041ReminderEditMode__
     */
    public void setSch041ReminderEditMode(SchEnumRemindMode sch041ReminderEditMode) {
        sch041ReminderEditMode__ = sch041ReminderEditMode;
    }

    /**
     * <p>sch041SameUserUI を取得します。
     * @return sch041SameUserUI
     * @see jp.groupsession.v2.sch.sch041.Sch041Form#sch041SameUserUI__
     */
    public SameUserSelector getSch041SameUserUI() {
        return sch041SameUserUI__;
    }

    /**
     * <p>sch041SameUserUI をセットします。
     * @param sch041SameUserUI sch041SameUserUI
     * @see jp.groupsession.v2.sch.sch041.Sch041Form#sch041SameUserUI__
     */
    public void setSch041SameUserUI(SameUserSelector sch041SameUserUI) {
        sch041SameUserUI__ = sch041SameUserUI;
    }

    /**
     * <p>sch041DisplayTargetUI を取得します。
     * @return sch041DisplayTargetUI
     * @see jp.groupsession.v2.sch.sch041.Sch041Form#sch041DisplayTargetUI__
     */
    public UserGroupSelector getSch041DisplayTargetUI() {
        return sch041DisplayTargetUI__;
    }

    /**
     * <p>sch041DisplayTargetUI をセットします。
     * @param sch041DisplayTargetUI sch041DisplayTargetUI
     * @see jp.groupsession.v2.sch.sch041.Sch041Form#sch041DisplayTargetUI__
     */
    public void setSch041DisplayTargetUI(UserGroupSelector sch041DisplayTargetUI) {
        sch041DisplayTargetUI__ = sch041DisplayTargetUI;
    }

    /**
     * <p>sch041SameReserveUI を取得します。
     * @return sch041SameReserveUI
     * @see jp.groupsession.v2.sch.sch041.Sch041Form#sch041SameReserveUI__
     */
    public ShisetuSelector getSch041SameReserveUI() {
        return sch041SameReserveUI__;
    }

    /**
     * <p>sch041SameReserveUI をセットします。
     * @param sch041SameReserveUI sch041SameReserveUI
     * @see jp.groupsession.v2.sch.sch041.Sch041Form#sch041SameReserveUI__
     */
    public void setSch041SameReserveUI(ShisetuSelector sch041SameReserveUI) {
        sch041SameReserveUI__ = sch041SameReserveUI;
    }
}
