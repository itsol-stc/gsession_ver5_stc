package jp.groupsession.v2.prj;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] プロジェクト管理の入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateProject {

    /** 年フィールドのMAX文字数 */
    public static final int MAX_LENGTH_YEAR = 4;
    /** 月フィールドのMAX文字数 */
    public static final int MAX_LENGTH_MONTH = 2;
    /** 日フィールドのMAX文字数 */
    public static final int MAX_LENGTH_DAY = 2;
    /** 時フィールドのMAX文字数 */
    public static final int MAX_LENGTH_HOUR = 2;
    /** 分フィールドのMAX文字数 */
    public static final int MAX_LENGTH_MINUTES = 2;
    /** リクエスト */
    private HttpServletRequest req__ = null;
    /**
     * <p>Set Connection
     * @param req HttpServletRequest
     */
    public GSValidateProject(HttpServletRequest req) {
        req__ = req;
    }
//  ---------------------------------------------------------

    /**
     * <br>[機  能] 年の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param year 年
     * @param targetName チェック対象
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateFieldYear(
            ActionErrors errors,
            String targetName,
            String year,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String eprefix = "year.";
        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(year)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldFix + "error.input.required.text");
            return true;
        }

        if (!ValidateUtil.isNumber(year)) {
            //半角数字チェック
            msg = new ActionMessage("error.input.number.text", targetName);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + fieldFix + "error.input.number.text");
            return true;
        }

        if (year.length() > MAX_LENGTH_YEAR) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName,
                    MAX_LENGTH_YEAR);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] 月の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param month 月
     * @param targetName チェック対象
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateFieldMonth(
            ActionErrors errors,
            String targetName,
            String month,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String eprefix = "month.";
        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(month)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldFix + "error.input.required.text");
            return true;
        }

        if (!ValidateUtil.isNumber(month)) {
            //半角数字チェック
            msg = new ActionMessage("error.input.number.text", targetName);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + fieldFix + "error.input.number.text");
            return true;
        }

        if (month.length() > MAX_LENGTH_MONTH) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName,
                    MAX_LENGTH_MONTH);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] 日の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param day 日
     * @param targetName チェック対象
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateFieldDay(
            ActionErrors errors,
            String targetName,
            String day,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String eprefix = "day.";
        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(day)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldFix + "error.input.required.text");
            return true;

        }

        if (!ValidateUtil.isNumber(day)) {
            //半角数字チェック
            msg = new ActionMessage("error.input.number.text", targetName);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + fieldFix + "error.input.number.text");
            return true;
        }

        if (day.length() > MAX_LENGTH_DAY) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName,
                    MAX_LENGTH_DAY);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] 日付の論理チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param targetName チェック対象
     * @param year 年
     * @param month 月
     * @param day 日
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public boolean validateDate(
            ActionErrors errors,
            String targetName,
            String year,
            String month,
            String day) {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        //年
        String textYear = gsMsg.getMessage(req__, "cmn.year", year);
        //月
        String textMonth = gsMsg.getMessage(req__, "cmn.month");
        //日
        String textDay = gsMsg.getMessage(req__, "cmn.day");

        String eprefix = "date";
        String fieldFix = targetName + ".";
        String inputedDate = textYear + month + textMonth + day + textDay;
        int iBYear = Integer.parseInt(year);
        int iBMonth = Integer.parseInt(month);
        int iBDay = Integer.parseInt(day);

        UDate date = new UDate();
        date.setDate(iBYear, iBMonth, iBDay);
        if (date.getYear() != iBYear
        || date.getMonth() != iBMonth
        || date.getIntDay() != iBDay) {
            msg = new ActionMessage("error.input.notfound.date",
                    targetName + "（" + inputedDate + ")");
            StrutsUtil.addMessage(errors, msg, eprefix + fieldFix
                    + "error.input.notfound.date");
            return true;
        }
        //エラー無し
        return false;
    }

    /**
     * <br>[機  能] 日付の論理チェックを行います
     * <br>[解  説]
     * <br>[備  考] 年月の組み合わせチェック
     * @param errors ActionErrors
     * @param targetName チェック対象
     * @param year 年
     * @param month 月
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public boolean validateDate(ActionErrors errors,
                                             String targetName,
                                             String year,
                                             String month) {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        //年
        String textYear = gsMsg.getMessage(req__, "cmn.year", year);
        //月
        String textMonth = gsMsg.getMessage(req__, "cmn.month");

        String eprefix = "date";
        String fieldFix = targetName + ".";
        String inputedDate = textYear + month + textMonth;
        int iBYear = Integer.parseInt(year);
        int iBMonth = Integer.parseInt(month);
        int iBDay = 1;

        UDate date = new UDate();
        date.setDate(iBYear, iBMonth, iBDay);
        if (date.getYear() != iBYear
        || date.getMonth() != iBMonth
        || date.getIntDay() != iBDay) {
            msg = new ActionMessage("error.input.notfound.date",
                    targetName + "（" + inputedDate + ")");
            StrutsUtil.addMessage(errors, msg, eprefix + fieldFix
                    + "error.input.notfound.date");
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 年、月の入力チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param targetName チェック対象
     * @param year 年
     * @param month 月
     * @param checkNoInput 年、月の未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public boolean validateYM(
            ActionErrors errors,
            String targetName,
            String year,
            String month,
            boolean checkNoInput) {
        GsMessage gsMsg = new GsMessage();
        //年
        String textYear = gsMsg.getMessage(req__, "cmn.year2");
        //月
        String textMonth = gsMsg.getMessage(req__, "cmn.month");

        boolean checkInputYear = true;
        boolean checkInputMonth = true;
        if (!checkNoInput) {
            //未入力チェック = false の場合、チェック対象以外が入力されていた時に
            //未入力チェックを行う
            checkInputYear = !StringUtil.isNullZeroString(month);
            checkInputMonth = !StringUtil.isNullZeroString(year);
        }

        boolean errorFlgY = validateFieldYear(errors, targetName.concat(" " + textYear),
                                            year, checkInputYear);
        boolean errorFlgM = validateFieldMonth(errors, targetName.concat(" " + textMonth),
                                            month, checkInputMonth);

        if (!errorFlgY && !errorFlgM
        && !StringUtil.isNullZeroString(year)
        && !StringUtil.isNullZeroString(month)) {

            return validateDate(errors, targetName,
                                            year, month);
        } else {
            return true;
        }

    }

    /**
     * <br>[機  能] 年、月、日の入力チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param targetName チェック対象
     * @param year 年
     * @param month 月
     * @param day 日
     * @param checkNoInput 年、月、日の未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public boolean validateYMD(
            ActionErrors errors,
            String targetName,
            String year,
            String month,
            String day,
            boolean checkNoInput) {
        GsMessage gsMsg = new GsMessage();
        //年
        String textYear = gsMsg.getMessage(req__, "cmn.year2");
        //月
        String textMonth = gsMsg.getMessage(req__, "cmn.month");
        //日
        String textDay = gsMsg.getMessage(req__, "cmn.day");

        boolean checkInputYear = true;
        boolean checkInputMonth = true;
        boolean checkInputDay = true;
        if (!checkNoInput) {
            //未入力チェック = false の場合、チェック対象以外が入力されていた時に
            //未入力チェックを行う
            checkInputYear =
                !StringUtil.isNullZeroString(month) || !StringUtil.isNullZeroString(day);
            checkInputMonth =
                !StringUtil.isNullZeroString(year) || !StringUtil.isNullZeroString(day);
            checkInputDay =
                !StringUtil.isNullZeroString(year) || !StringUtil.isNullZeroString(month);
        }

        boolean errorFlgY = validateFieldYear(errors, targetName.concat(" " + textYear),
                                            year, checkInputYear);

        boolean errorFlgM = validateFieldMonth(errors, targetName.concat(" " + textMonth),
                                            month, checkInputMonth);

        boolean errorFlgD = validateFieldDay(errors, targetName.concat(" " + textDay),
                                            day, checkInputDay);

        if (!errorFlgY && !errorFlgM && !errorFlgD
        && !StringUtil.isNullZeroString(year)
        && !StringUtil.isNullZeroString(month)
        && !StringUtil.isNullZeroString(day)) {
            return validateDate(errors, targetName,
                                            year, month, day);
        } else {
            return true;
        }

    }

    /**
     * <br>[機  能] 時の入力チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param hour 時
     * @param targetName チェック対象
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateFieldHour(
        ActionErrors errors,
        String targetName,
        String hour,
        boolean checkNoInput) {

        ActionMessage msg = null;
        String eprefix = "hour";
        String fieldfix = targetName + ".";

        if (StringUtil.isNullZeroString(hour)) {
            if (!checkNoInput) {
                return false;
            }
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + "error.input.required.text");
            return true;
        }

        if (!ValidateUtil.isNumber(hour)) {
            //半角数字チェック
            msg = new ActionMessage("error.input.number.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + "error.input.number.text");
            return true;
        }

        if (hour.length() > MAX_LENGTH_HOUR) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName,
                    MAX_LENGTH_HOUR);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + "error.input.length.text");
            return true;
        } else {
            int iHour = Integer.parseInt(hour.trim());
            if (iHour < 0 || 23 < iHour) {
                //範囲チェック
                msg = new ActionMessage("error.input.comp.text",
                        targetName, "0 ～ 23");
                StrutsUtil.addMessage(errors, msg,
                        eprefix + fieldfix + "error.input.comp.text");
                return true;
            }
        }

        //入力エラー無し
        return false;
    }


    /**
     * <br>[機  能] 分の入力チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param minute 分
     * @param targetName チェック対象
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateFieldMinutes(
        ActionErrors errors,
        String targetName,
        String minute,
        boolean checkNoInput) {

        ActionMessage msg = null;
        String eprefix = "minutes";
        String fieldfix = targetName + ".";

        if (StringUtil.isNullZeroString(minute)) {
            if (!checkNoInput) {
                return false;
            }
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + "error.input.required.text");
            return true;

        }

        if (!ValidateUtil.isNumber(minute)) {
            //半角数字チェック
            msg = new ActionMessage("error.input.number.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + "error.input.number.text");
            return true;
        }

        if (minute.length() > MAX_LENGTH_MINUTES) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName,
                    MAX_LENGTH_MINUTES);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + "error.input.length.text");
            return true;

        } else {
            int iMinute = Integer.parseInt(minute.trim());
            if (iMinute < 0 || 59 < iMinute) {
                //範囲チェック
                msg = new ActionMessage("error.input.comp.text",
                        targetName, "0 ～ 59");
                StrutsUtil.addMessage(errors, msg,
                        eprefix + fieldfix + "error.input.comp.text");
                return true;
            }
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] 年月日大小チェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param targetNameS チェック対象(開始日付)
     * @param targetNameE チェック対象(終了日付)
     * @param startDate 開始日付
     * @param endDate 終了日付
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateDataRange(ActionErrors errors,
                                                   String targetNameS,
                                                   String targetNameE,
                                                   UDate startDate,
                                                   UDate endDate) {
        ActionMessage msg = null;
        String eprefix = "dateRange.";
        String fieldFix = targetNameS + "." + targetNameE + ".";

        if (endDate.compareDateYMD(startDate) == UDate.LARGE) {
            msg = new ActionMessage(
                    "error.input.comp.text",
                    targetNameS + "・" + targetNameE, targetNameS + "<" + targetNameE);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldFix + "error.input.comp.text");
            return true;
        }

        //入力エラー無し
        return false;
    }


    /**
     * <br>[機  能] 年月日時間大小チェック
     * <br>[解  説] 同一日時はOKとする
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param targetNameS チェック対象(開始日付)
     * @param targetNameE チェック対象(終了日付)
     * @param startDate 開始日付時間
     * @param endDate 終了日付時間
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTimeRange(
            ActionErrors errors,
            String targetNameS,
            String targetNameE,
            UDate startDate,
            UDate endDate) {
        ActionMessage msg = null;
        String eprefix = "timeRange.";
        String fieldFix = targetNameS + "." + targetNameE + ".";

        if (endDate.compareDateYMDHM(startDate) == UDate.LARGE) {
            msg = new ActionMessage(
                    "error.input.comp.text",
                    targetNameS + "・" + targetNameE, targetNameS + "<" + targetNameE);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldFix + "error.input.comp.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] 年月日時間大小チェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param targetNameS チェック対象(開始日付)
     * @param targetNameE チェック対象(終了日付)
     * @param startDate 開始日付時間
     * @param endDate 終了日付時間
     * @param rangeFlg true=同一日時OKとする、false=同一日時NGとする
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateDateTimeRange(
        ActionErrors errors,
        String targetNameS,
        String targetNameE,
        UDate startDate,
        UDate endDate,
        boolean rangeFlg) {

        if (rangeFlg) {
            //同一日時OKとする
            return validateTimeRange(errors, targetNameS, targetNameE, startDate, endDate);

        } else {
            //同一日時NGとする
            ActionMessage msg = null;
            String eprefix = "timeRange.";
            String fieldFix = targetNameS + "." + targetNameE + ".";

            if (endDate.compareDateYMDHM(startDate) != UDate.SMALL) {
                msg = new ActionMessage(
                        "error.input.comp.text",
                        targetNameS + "・" + targetNameE, targetNameS + "<" + targetNameE);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldFix + "error.input.comp.text");
                return true;
            }
        }

        return false;
    }

//---------------------------------------------------------

    /**
     * <br>[機  能] チェックボックスが選択されているかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target エラーの場合に表示するターゲット名
     * @param mailSid 削除するショートメッセージSID
     * @return ActionErrors
     */
    public static ActionErrors validateCheckBoxMessage(ActionErrors errors,
                                                         String target,
                                                         String[] mailSid) {
        ActionMessage msg = null;

        //未選択チェック
        if (mailSid == null) {
            msg = new ActionMessage("error.select.required.text", target);
            StrutsUtil.addMessage(errors, msg, "mailSid");
        } else {
            if (mailSid.length < 1) {
                msg = new ActionMessage("error.select.required.text", target);
                StrutsUtil.addMessage(errors, msg, "mailSid");
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] テキストボックス（オールタイプ）の入力チェックを行う（汎用）
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextBoxInput(
            ActionErrors errors,
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        //スペースのみチェック
        if (ValidateUtil.isSpace(target)) {
            msg = new ActionMessage("error.input.spase.only", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.only");
            return true;
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(target)) {
            msg = new ActionMessage("error.input.spase.start", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.start");
            return true;
        }
        if (ValidateUtil.isTab(target)) {
            //タブスペースチェック
            msg = new ActionMessage("error.input.tab.text", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.tab.text");
            return true;
        }        
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(target);
            msg = new ActionMessage("error.input.njapan.text", targetName, nstr);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.njapan.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] テキストボックス（オールタイプ）の入力チェックを行う（繰り返し）
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @param rowNum 行数
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public boolean validateRowsTextBoxInput(
            ActionErrors errors,
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput,
            int rowNum) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();

        String fieldFix = targetName + "." + rowNum;

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage(req__, "cmn.no.of", String.valueOf(rowNum), targetName));
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        //スペースのみチェック
        if (ValidateUtil.isSpace(target)) {
            msg = new ActionMessage("error.input.spase.only",
                    gsMsg.getMessage(req__, "cmn.no.of", String.valueOf(rowNum), targetName));
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.only");
            return true;
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(target)) {
            msg = new ActionMessage("error.input.spase.start",
                    gsMsg.getMessage(req__, "cmn.no.of", String.valueOf(rowNum), targetName));
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.start");
            return true;
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(target);
            msg = new ActionMessage("error.input.njapan.text",
                    gsMsg.getMessage(req__, "cmn.no.of", String.valueOf(rowNum), targetName), nstr);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.njapan.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage(
                    "error.input.length.text",
                    gsMsg.getMessage(
                            req__, "cmn.no.of", String.valueOf(rowNum), targetName), maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] テキストボックス（数字タイプ）の入力チェックを行う（汎用）
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextBoxInputNum(
            ActionErrors errors,
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        if (!ValidateUtil.isNumber(target)) {
            //半角数字チェック
            msg = new ActionMessage("error.input.number.hankaku", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.number.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName, maxLength);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] テキストボックス（数字タイプ）の入力チェックを行う（汎用）
     * <br>[解  説] 最大値、最小値の判定も行う
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param min 最小値
     * @param max 最大値
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextBoxInputNumLenge(
            ActionErrors errors,
            String target,
            String targetName,
            int min,
            int max,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        if (!ValidateUtil.isNumber(target)) {
            //半角数字チェック
            msg = new ActionMessage("error.input.number.text", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.number.text");
            return true;
        }

        if (NullDefault.getInt(target, -1) < min || NullDefault.getInt(target, -1) > max) {
            //入力範囲チェック
            msg = new ActionMessage("error.input.lenge", targetName, min, max);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.lenge");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] テキストボックス（数字タイプ）の大小チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param targetFrom チェック対象From
     * @param targetTo チェック対象To
     * @param targetNameFrom チェック対象名称From
     * @param targetNameTo チェック対象名称To
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextBoxFromTo(
            ActionErrors errors,
            String targetFrom,
            String targetTo,
            String targetNameFrom,
            String targetNameTo) {
        ActionMessage msg = null;

        String fieldFix = targetNameFrom + "." + targetNameTo + ".";

        if (NullDefault.getLong(targetFrom, -1) > NullDefault.getLong(targetTo, -1)) {
            msg = new ActionMessage("error.input.fromto.input", targetNameTo, targetNameFrom);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.fromto.input");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] テキストボックス（英数字タイプ）の入力チェックを行う（汎用）
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @param req リクエスト
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextBoxInputArphaNum(
            ActionErrors errors,
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput,
            HttpServletRequest req) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";
        GsMessage gsMsg = new GsMessage();
        //半角英字
        String textEnglish = gsMsg.getMessage(req, "cmn.english");
        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        if (!ValidateUtil.isAlpha(target)) {
            //半角英字チェック
            msg = new ActionMessage("error.input.comp.text", targetName, textEnglish);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.comp.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName, maxLength);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] テキストエリアの入力チェックを行う（汎用）
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextarea(
            ActionErrors errors,
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }
        //スペース・改行のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(target)) {
            msg = new ActionMessage("error.input.spase.cl.only", targetName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.cl.only");
            return true;
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(target);
            msg = new ActionMessage("error.input.njapan.text", targetName, nstr);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.njapan.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetName, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] 検索ソート順の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param sortKey1 検査ソートKey1
     * @param sortKey2 検査ソートKey2
     * @param sortKeyName ソートキー名称
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateSearchSortOrder(
            ActionErrors errors,
            String sortKey1,
            String sortKey2,
            String sortKeyName) {
        ActionMessage msg = null;

        String fieldFix = sortKeyName + ".";

        if (sortKey1.equals(sortKey2)) {
            //同一キー指定チェック
            msg = new ActionMessage("error.select.dup.list", sortKeyName);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.select.dup.list");
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 検索対象の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param keyWord キーワード
     * @param searchTarget 検索対象
     * @return true: エラーあり false: エラーなし
     */
    public boolean validateSearchTarget(
        ActionErrors errors,
        String keyWord,
        String[] searchTarget) {

        ActionMessage msg = null;

        //キーワード未入力時はチェックなし
        if (NullDefault.getString(keyWord, "").length() < 1) {
            return false;
        }
        GsMessage gsMsg = new GsMessage();
        //検索対象
        String textSearch = gsMsg.getMessage(req__, "cmn.search2");
        if (searchTarget == null || searchTarget.length < 1) {
            //未選択の場合エラー
            msg = new ActionMessage(
                    "error.select.required.text", textSearch);
            StrutsUtil.addMessage(errors, msg, "target");
            return true;
        }

        return false;
    }

}