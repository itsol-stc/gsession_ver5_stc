package jp.groupsession.v2.api.timecard.edit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.dao.TcdTimezoneInfoDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;

/**
 * <br>[機  能] タイムカード情報の登録・変更を行うWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "timecard-edit",
plugin = "timecard", name = "タイムカード情報登録・変更",
url = "/api/timecard/edit.do", reqtype = "POST")
public class ApiTcdEditForm extends AbstractApiForm {
    /** 編集モード */
    private int cmdMode__ = GSConstTimecard.CMDMODE_EDIT;

    /** ユーザID */
    @ApiParam(name = "userId", viewName = "ユーザID")
    private String userId__ = null;
    /** 登録日 */
    @ApiParam(name = "targetDate", viewName = "登録日")
    private String targetDate__ = null;
    /** 時間帯 */
    @ApiParam(name = "timezoneType", viewName = "時間帯略称", required = false, confRequired = true)
    private String timezoneType__ = null;
    /** 打刻始業時間 */
    @ApiParam(name = "stampStartTime", viewName = "打刻始業時間", required = false, confRequired = true)
    private String stampStartTime__ = null;
    /** 打刻終業時間 */
    @ApiParam(name = "stampEndTime", viewName = "打刻終業時間", required = false, confRequired = true)
    private String stampEndTime__ = null;
    /** 始業時間 */
    @ApiParam(name = "startTime", viewName = "始業時間", required = false, confRequired = true)
    private String startTime__ = null;
    /** 終業時間 */
    @ApiParam(name = "endTime", viewName = "終業時間", required = false, confRequired = true)
    private String endTime__ = null;
    /** 備考 */
    private String remarksText__ = null;
    /** 遅刻区分 */
    @ApiParam(name = "chikokuFlg", viewName = "遅刻区分", required = false, confRequired = true)
    private String chikokuFlg__ = null;
    /** 早退区分 */
    @ApiParam(name = "soutaiFlg", viewName = "早退区分", required = false, confRequired = true)
    private String soutaiFlg__ = null;
    /** 休日区分 */
    @ApiParam(name = "holidayType", viewName = "休日区分", required = false, confRequired = true)
    private String holidayType__ = null;
    /** 休日日数 */
    @ApiParam(name = "holidayNum", viewName = "休日日数", required = false, confRequired = true)
    private String holidayNum__ = null;
    /** 休日内容 */
    @ApiParam(name = "holidayText", viewName = "休日内容", required = false, confRequired = true)
    private String holidayText__ = null;


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param admConf タイムカード管理者設定情報
     * @param userSid ユーザSID
     * @return エラー
     */
    public ActionErrors validateCheck(
            RequestModel reqMdl, 
            Connection con,
            TcdAdmConfModel admConf,
            int userSid) throws NumberFormatException, SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        CmnUsrmDao cumDao = new CmnUsrmDao(con);
        int targetSid = cumDao.selectLoginId(userId__);
        //ユーザID
        __validateUsrId(errors, msg, gsMsg, con, targetSid);

        //日付
        __validateTcdDate(errors, msg, gsMsg);

        //時間帯
        __validateTcdTimezone(errors, msg, gsMsg, reqMdl, con, admConf, targetSid);

        //打刻始業時間・打刻終業時間
        __validateTcdTimes(errors, msg, gsMsg,
                stampStartTime__,  stampEndTime__, true);

        //始業時間・終業時間
        __validateTcdTimes(errors, msg, gsMsg, startTime__, endTime__, false);

        //備考
        __validateTcdBiko(errors, msg, gsMsg);

        //遅刻
        __validateTcdChikoku(errors, msg, gsMsg);

        //早退
        __validateTcdSoutai(errors, msg, gsMsg);

        //休日区分
        TcdHolidayInfDao thiDao = new TcdHolidayInfDao(con);
        TcdHolidayInfModel thiMdl = thiDao.getHolidayDataWithName(
                NullDefault.getString(holidayType__, ""));
        __validateTcdHolKbn(errors, msg, gsMsg, con, thiMdl);

        //休日日数
        __validateTcdHolNum(errors, msg, gsMsg, thiMdl);

        //その他休日内容
        __validateTcdHolText(errors, msg, gsMsg);

        return errors;
    }

    /**
     * <br>[機  能] ユーザIDの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param msg アクションメッセージ
     * @param gsMsg GSメッセージ
     * @param con コネクション
     * @param targetSid 対象者ユーザSID
     */
    private void __validateUsrId(ActionErrors errors, ActionMessage msg,
            GsMessage gsMsg, Connection con, int targetSid) throws SQLException {

        String usrId = gsMsg.getMessage("cmn.user.id");
        //未入力チェック
        if (StringUtil.isNullZeroString(userId__)) {
            msg = new ActionMessage("error.input.required.text", usrId);
            errors.add("error.input.required.text", msg);
            return;
        }

        //存在チェック
        if (targetSid == 0) {
            msg = new ActionMessage("search.data.notfound", usrId);
            errors.add("search.data.notfound", msg);
        }
    }

    /**
     * <br>[機  能] 日付の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param msg アクションメッセージ
     * @param gsMsg GSメッセージ
     */
    private void __validateTcdDate(
            ActionErrors errors, ActionMessage msg, GsMessage gsMsg) {

        String strDate = gsMsg.getMessage("cmn.date2");
        //未入力チェック
        if (StringUtil.isNullZeroString(targetDate__)) {
            msg = new ActionMessage("error.input.required.text", strDate);
            errors.add("error.input.required.text", msg);
            return;
        }

        ArrayList<String> list = StringUtil.split("/", targetDate__);
        String sptYear = "";
        String sptMonth = "";
        String sptDay = "";
        boolean ymdFomatFlg = false;
        msg = new ActionMessage("error.input.notfound.date", strDate);

        //フォーマットチェック
        if (list.size() == 3) {
            sptYear = list.get(0);
            sptMonth = list.get(1);
            sptDay = list.get(2);
            try {
                targetDate__ = StringUtil.getStrYyyyMmDd(sptYear, sptMonth, sptDay);
                ymdFomatFlg = true;
            } catch (NumberFormatException e) {
                StrutsUtil.addMessage(errors, msg, "error.input.notfound.date");
                return;
            }
        }
        if (!ymdFomatFlg) {
            String textYyyyMmDd = gsMsg.getMessage("cmn.format.date2");
            msg = new ActionMessage(
                    "error.input.comp.text",  strDate, textYyyyMmDd);
            StrutsUtil.addMessage(errors, msg, "error.input.comp.text");
            return;
        }

        int iBYear = 0;
        int iBMonth = 0;
        int iBDay = 0;

        try {
            String year = targetDate__.substring(0, 4);
            String month = targetDate__.substring(4, 6);
            String day = targetDate__.substring(6, 8);
            iBYear = Integer.parseInt(year);
            iBMonth = Integer.parseInt(month);
            iBDay = Integer.parseInt(day);
        } catch (Exception e) {
            StrutsUtil.addMessage(errors, msg, "error.input.notfound.date");
        }

        //論理チェック
        UDate date = new UDate();
        date.setDate(iBYear, iBMonth, iBDay);
        if (date.getYear() != iBYear 
                || date.getMonth() != iBMonth
                || date.getIntDay() != iBDay) {
            StrutsUtil.addMessage(errors, msg, "error.input.notfound.date");
        }
    }

    /**
     * <br>[機  能] 時間帯の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param msg アクションメッセージ
     * @param gsMsg GSメッセージ
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param admConf タイムカード管理者設定情報
     * @param targetSid 対象者ユーザSID
     */
    private void __validateTcdTimezone(ActionErrors errors, 
            ActionMessage msg,  GsMessage gsMsg, RequestModel reqMdl,
            Connection con, TcdAdmConfModel admConf, int targetSid)
                    throws SQLException {

        TcdTimezoneInfoDao ttiDao = new TcdTimezoneInfoDao(con);
        ArrayList<TcdTimezoneInfoModel> timezoneList = ttiDao.getCanUseTimeZone(targetSid);

        if (!StringUtil.isNullZeroString(timezoneType__)) {
            boolean chk = false;
            for (TcdTimezoneInfoModel  model : timezoneList) {
                if (model.getTtiRyaku().equals(timezoneType__)) {
                    chk = true;
                    break;
                }
            }
            if (!chk) {
                String timezone = gsMsg.getMessage("tcd.tcd070.01");
                msg = new ActionMessage("tcd150.not.timezone", timezone);
                errors.add("tcd150.not.timezone", msg);
            }
        }
    }

    /**
     * <br>[機  能] 時間の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param msg アクションメッセージ
     * @param gsMsg GSメッセージ
     * @param startTime 開始時刻
     * @param endTime 終業時刻
     * @param dakokuFlg 打刻時間 true
     */
    private void __validateTcdTimes(
            ActionErrors errors, ActionMessage msg, GsMessage gsMsg,
            String startTime, String endTime, boolean dakokuFlg) {

        String timeStart = null;
        String timeEnd = null;
        if (dakokuFlg) {
            timeStart = gsMsg.getMessage("tcd.180");
            timeEnd = gsMsg.getMessage("tcd.181");
        } else {
            timeStart = gsMsg.getMessage("tcd.28");
            timeEnd = gsMsg.getMessage("tcd.24");
        }

        //フォーマットチェック
        boolean startResult = __isTimeCheck(errors, msg, gsMsg, startTime, timeStart);
        boolean endResult = __isTimeCheck(errors, msg, gsMsg, endTime, timeEnd);

        //フォーマットチェックに引っかかった際はreturn
        if (startResult || endResult) {
            return;
        }

        //終業時間のみが入力されている場合
        if (StringUtil.isNullZeroString(startTime)
                && !StringUtil.isNullZeroString(endTime) && !dakokuFlg) {
            msg = new ActionMessage("error.input.required.text", timeStart);
            errors.add("error.input.required.text", msg);
        }

        if (!StringUtil.isNullZeroString(startTime)
                && !StringUtil.isNullZeroString(endTime)) {
            String title = null;

            //始業時刻
            ArrayList<String> list = StringUtil.split(":", startTime);
            String hour = (String) list.get(0);
            String min = (String) list.get(1);
            int startHour = Integer.parseInt(hour);
            int startMin = Integer.parseInt(min);

            //終業時刻
            list = StringUtil.split(":", endTime);
            hour = (String) list.get(0);
            min = (String) list.get(1);
            int endHour = Integer.parseInt(hour);
            int endMin = Integer.parseInt(min);

            //大小チェック
            if (startHour > endHour || (startHour == endHour && startMin > endMin)) {
                String text = timeStart + " < " + timeEnd;
                title = timeStart + "・" + timeEnd;
                msg = new ActionMessage("error.input.comp.text", title, text);
                errors.add("error.input.comp.text", msg);
                return;

            }
            //24hチェック
            if (endHour - startHour >= 24 && endMin >= startMin) {
                title = timeStart + "～"  + timeEnd;
                String text = gsMsg.getMessage("tcd.161");
                msg = new ActionMessage("error.input.comp.text", title, text);
                errors.add("error.input.comp.text", msg);
            }
        }
    }

    /**
     * <br>[機  能] 時間のフォーマットチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param msg アクションメッセージ
     * @param gsMsg GSメッセージ
     * @param time 時間
     * @param timeKbnName 時間項目名
     * @return チェック結果 true:エラー有 false:正常
     */
    private boolean __isTimeCheck(ActionErrors errors, ActionMessage msg,
            GsMessage gsMsg, String time, String timeKbnName) {

        int iBHour = 0;
        int iBMin = 0;
        msg = new ActionMessage("error.input.notvalidate.data", timeKbnName);

        if (!StringUtil.isNullZeroString(time)) {

            if (!ValidateUtil.isTimeFormat(time)) {
                //半角数字(hh:mm形式)
                errors.add("error.input.notvalidate.data", msg);
                return true;
                
            } else {
                try {
                    ArrayList<String> list = StringUtil.split(":", time);
                    String hour = (String) list.get(0);
                    String min = (String) list.get(1);
                    iBHour = Integer.parseInt(hour);
                    iBMin = Integer.parseInt(min);

                } catch (Exception e) {
                    StrutsUtil.addMessage(errors, msg, "error.input.notvalidate.data");
                    return true;
                }

                //論理チェック
                if (iBHour < 0 || GSConstTimecard.MAX_TIMECARD_HOUR < iBHour) {
                    StrutsUtil.addMessage(errors, msg, "error.input.notvalidate.data");
                    return true;
                }
                if (iBMin < 0 || 59 < iBMin) {
                    StrutsUtil.addMessage(errors, msg, "error.input.notvalidate.data");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <br>[機  能] 備考の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param msg アクションメッセージ
     * @param gsMsg GSメッセージ
     */
    private void __validateTcdBiko(
            ActionErrors errors, ActionMessage msg, GsMessage gsMsg) {

        String biko = gsMsg.getMessage("cmn.memo");
        if (!StringUtil.isNullZeroString(remarksText__)) {
            //桁数チェック
            if (__checkRange(errors, remarksText__, "remarksText",
                    biko, GSConstTimecard.MAX_LENGTH_BIKO)) {
                //先頭スペースチェック
                if (ValidateUtil.isSpaceStart(remarksText__)) {
                    msg = new ActionMessage("error.input.spase.start", biko);
                    errors.add("remarksText", msg);
                    return;
                }
                __checkJisString(errors, remarksText__, "remarksText", biko);
            }
        }
    }

    /**
     * <br>[機  能] 遅刻の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param msg アクションメッセージ
     * @param gsMsg GSメッセージ
     */
    private void __validateTcdChikoku(
            ActionErrors errors, ActionMessage msg, GsMessage gsMsg) {

        String tikoku = gsMsg.getMessage("tcd.18");
        if (!StringUtil.isNullZeroString(chikokuFlg__)) {
            //数字チェック
            if (!ValidateUtil.isNumber(chikokuFlg__)) {
                msg = new ActionMessage("error.input.number.hankaku", tikoku);
                errors.add("error.input.number.hankaku", msg);
                return;
            }
            //遅刻区分チェック
            if (Integer.parseInt(chikokuFlg__) != GSConstTimecard.CHK_KBN_UNSELECT
                    && Integer.parseInt(chikokuFlg__) != GSConstTimecard.CHK_KBN_SELECT) {
                msg = new ActionMessage("error.input.format.text", tikoku);
                errors.add("error.input.format.text", msg);
            }
        }
    }

    /**
     * <br>[機  能] 早退の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param msg アクションメッセージ
     * @param gsMsg GSメッセージ
     */
    private void __validateTcdSoutai(
            ActionErrors errors, ActionMessage msg, GsMessage gsMsg) {

        String soutai = gsMsg.getMessage("tcd.22");
        if (!StringUtil.isNullZeroString(soutaiFlg__)) {
            //数字チェック
            if (!ValidateUtil.isNumber(soutaiFlg__)) {
                msg = new ActionMessage("error.input.number.hankaku", soutai);
                errors.add("error.input.number.hankaku", msg);
                return;
            }
            //早退区分チェック
            if (Integer.parseInt(soutaiFlg__) != GSConstTimecard.SOU_KBN_UNSELECT
                    && Integer.parseInt(soutaiFlg__) != GSConstTimecard.SOU_KBN_SELECT) {
                msg = new ActionMessage("error.input.format.text", soutai);
                errors.add("error.input.format.text", msg);
            }
        }
    }

    /**
     * <br>[機  能] 休日区分の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param msg アクションメッセージ
     * @param gsMsg GSメッセージ
     * @param con コネクション
     * @param thiMdl 休日区分モデル
     */
    private void __validateTcdHolKbn(ActionErrors errors, ActionMessage msg,
            GsMessage gsMsg, Connection con, TcdHolidayInfModel thiMdl)
                    throws SQLException {

        String holKbn = gsMsg.getMessage("tcd.40");
        if (!StringUtil.isNullZeroString(holidayType__)) {
            //存在チェック
            if (thiMdl == null) {
                msg = new ActionMessage("error.nothing.selected", holKbn);
                errors.add("error.nothing.selected", msg);
                return;
            }
            if (thiMdl.getThiSid() != GSConstTimecard.HOL_KBN_UNSELECT) {
                TcdHolidayInfDao holInfDao = new TcdHolidayInfDao(con);
                int cnt = holInfDao.existsHoliday(thiMdl.getThiSid(), true);
                //使用可能チェック
                if (cnt == 0) {
                    msg = new ActionMessage("tcd020.cannot.use.holkbn", holKbn);
                    errors.add("tcd020.cannot.use.holkbn", msg);
                }
            }
        }
    }

    /**
     * <br>[機  能] 休日日数の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param msg アクションメッセージ
     * @param gsMsg GSメッセージ
     * @param thiMdl 休日区分モデル
     */
    private void __validateTcdHolNum(ActionErrors errors, ActionMessage msg,
            GsMessage gsMsg, TcdHolidayInfModel thiMdl) {

        String nissu = gsMsg.getMessage("tcd.tcd020.06");
        //休日区分なし、休日日数のみの入力はNG
        if (StringUtil.isNullZeroString(holidayType__)
                && !StringUtil.isNullZeroString(holidayNum__)) {
            msg = new ActionMessage(
                    "error.input.required.text", gsMsg.getMessage("tcd.40"));
            errors.add("error.input.required.text", msg);
            return;
        }

        if (thiMdl != null && thiMdl.getThiSid() 
                != GSConstTimecard.HOL_KBN_UNSELECT) {

            //必須チェック
            if (StringUtil.isNullZeroString(holidayNum__)) {
                msg = new ActionMessage("error.input.required.text", nissu);
                errors.add("horidaynum", msg);
                return;
            }
            //桁数チェック
            if (__checkRange(errors, holidayNum__, "horidaynum",
                    nissu, GSConstTimecard.MAX_LENGTH_HOLDAYS)) {

                //先頭スペースチェック
                if (ValidateUtil.isSpaceStart(holidayNum__)) {
                    msg = new ActionMessage("error.input.spase.start", nissu);
                    errors.add("error.input.spase.start", msg);
                    return;
                }
                //数字・ドットチェック
                if (!ValidateUtil.isNumberDot(holidayNum__,
                        GSConstTimecard.MAX_LENGTH_HOLDAYS,
                        GSConstTimecard.MAX_LENGTH_HOLDAYS)) {
                    msg = new ActionMessage("error.input.number.text", nissu);
                    errors.add("horidaynum", msg);
                }
            }
        }
    }

    /**
     * <br>[機  能] 休日内容の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param msg アクションメッセージ
     * @param gsMsg GSメッセージ
     */
    private void __validateTcdHolText(
            ActionErrors errors, ActionMessage msg, GsMessage gsMsg) {

        String sonota = gsMsg.getMessage("tcd.tcd170.08");
        if (!StringUtil.isNullZeroString(holidayText__)) {

            //休日区分未入力チェック
            if (StringUtil.isNullZeroString(holidayType__)) {
                msg = new ActionMessage(
                        "tcd150.not.permitted.input.naiyo", sonota);
                errors.add("tcd150.not.permitted.input.naiyo", msg);
            }

            //桁数チェック
            if (__checkRange(errors, holidayText__, "horidayText",
                    sonota, GSConstTimecard.MAX_LENGTH_SONOTA)) {

                //先頭スペースチェック
                if (ValidateUtil.isSpaceStart(holidayText__)) {
                    msg = new ActionMessage("error.input.spase.start", sonota);
                    errors.add("error.input.spase.start", msg);
                    return;
                }
                __checkJisString(errors, holidayText__, "horidayText",  sonota);
            }
        }
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
            msg = new ActionMessage(
                    "error.input.njapan.text", elementName, nstr);
            errors.add(element + "error.input.njapan.text", msg);
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
     * <p>cmdMode を取得します。
     * @return cmdMode
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditParamModel#cmdMode__
     */
    public int getCmdMode() {
        return cmdMode__;
    }
    /**
     * <p>cmdMode をセットします。
     * @param cmdMode cmdMode
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditParamModel#cmdMode__
     */
    public void setCmdMode(int cmdMode) {
        cmdMode__ = cmdMode;
    }
    /**
     * <p>userId を取得します。
     * @return userId
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#userId__
     */
    public String getUserId() {
        return userId__;
    }
    /**
     * <p>userId をセットします。
     * @param userId userId
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#userId__
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }
    /**
     * <p>targetDate を取得します。
     * @return targetDate
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#targetDate__
     */
    public String getTargetDate() {
        return targetDate__;
    }
    /**
     * <p>targetDate をセットします。
     * @param targetDate targetDate
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#targetDate__
     */
    public void setTargetDate(String targetDate) {
        targetDate__ = targetDate;
    }
    /**
     * <p>timezoneType を取得します。
     * @return timezoneType
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#timezoneType__
     */
    public String getTimezoneType() {
        return timezoneType__;
    }
    /**
     * <p>timezoneType をセットします。
     * @param timezoneType timezoneType
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#timezoneType__
     */
    public void setTimezoneType(String timezoneType) {
        timezoneType__ = timezoneType;
    }
    /**
     * <p>stampStartTime を取得します。
     * @return stampStartTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#stampStartTime__
     */
    public String getStampStartTime() {
        return stampStartTime__;
    }
    /**
     * <p>stampStartTime をセットします。
     * @param stampStartTime stampStartTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#stampStartTime__
     */
    public void setStampStartTime(String stampStartTime) {
        stampStartTime__ = stampStartTime;
    }
    /**
     * <p>stampEndTime を取得します。
     * @return stampEndTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#stampEndTime__
     */
    public String getStampEndTime() {
        return stampEndTime__;
    }
    /**
     * <p>stampEndTime をセットします。
     * @param stampEndTime stampEndTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#stampEndTime__
     */
    public void setStampEndTime(String stampEndTime) {
        stampEndTime__ = stampEndTime;
    }
    /**
     * <p>startTime を取得します。
     * @return startTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#startTime__
     */
    public String getStartTime() {
        return startTime__;
    }
    /**
     * <p>startTime をセットします。
     * @param startTime startTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#startTime__
     */
    public void setStartTime(String startTime) {
        startTime__ = startTime;
    }
    /**
     * <p>endTime を取得します。
     * @return endTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#endTime__
     */
    public String getEndTime() {
        return endTime__;
    }
    /**
     * <p>endTime をセットします。
     * @param endTime endTime
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#endTime__
     */
    public void setEndTime(String endTime) {
        endTime__ = endTime;
    }
    /**
     * <p>remarksText を取得します。
     * @return remarksText
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#remarksText__
     */
    public String getRemarksText() {
        return remarksText__;
    }
    /**
     * <p>remarksText をセットします。
     * @param remarksText remarksText
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#remarksText__
     */
    public void setRemarksText(String remarksText) {
        remarksText__ = remarksText;
    }
    /**
     * <p>chikokuFlg を取得します。
     * @return chikokuFlg
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#chikokuFlg__
     */
    public String getChikokuFlg() {
        return chikokuFlg__;
    }
    /**
     * <p>chikokuFlg をセットします。
     * @param chikokuFlg chikokuFlg
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#chikokuFlg__
     */
    public void setChikokuFlg(String chikokuFlg) {
        chikokuFlg__ = chikokuFlg;
    }
    /**
     * <p>soutaiFlg を取得します。
     * @return soutaiFlg
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#soutaiFlg__
     */
    public String getSoutaiFlg() {
        return soutaiFlg__;
    }
    /**
     * <p>soutaiFlg をセットします。
     * @param soutaiFlg soutaiFlg
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#soutaiFlg__
     */
    public void setSoutaiFlg(String soutaiFlg) {
        soutaiFlg__ = soutaiFlg;
    }
    /**
     * <p>holidayType を取得します。
     * @return holidayType
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#holidayType__
     */
    public String getHolidayType() {
        return holidayType__;
    }
    /**
     * <p>holidayType をセットします。
     * @param holidayType holidayType
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#holidayType__
     */
    public void setHolidayType(String holidayType) {
        holidayType__ = holidayType;
    }
    /**
     * <p>holidayNum を取得します。
     * @return holidayNum
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#holidayNum__
     */
    public String getHolidayNum() {
        return holidayNum__;
    }
    /**
     * <p>holidayNum をセットします。
     * @param holidayNum holidayNum
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#holidayNum__
     */
    public void setHolidayNum(String holidayNum) {
        holidayNum__ = holidayNum;
    }
    /**
     * <p>holidayText を取得します。
     * @return holidayText
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#holidayText__
     */
    public String getHolidayText() {
        return holidayText__;
    }
    /**
     * <p>holidayText をセットします。
     * @param holidayText holidayText
     * @see jp.groupsession.v2.api.timecard.edit.ApiTcdEditForm#holidayText__
     */
    public void setHolidayText(String holidayText) {
        holidayText__ = holidayText;
    }
}