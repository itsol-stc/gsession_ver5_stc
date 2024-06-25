package jp.groupsession.v2.ntp;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 日報の入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateNippou {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GSValidateNippou.class);

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
    public GSValidateNippou(HttpServletRequest req) {
        req__ = req;
    }


    /** epefix RSS */
    private static final String E_NIPPOU__ = "nippou";

    /**
     * <br>[機  能] テキストフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param maxLength 最大文字数
     * @param hisuFlg 入力が必須か true:必須 false:必須ではない
     * @return ActionErrors
     */
    public static ActionErrors validateCmnFieldText(
        ActionErrors errors,
        String checkObject,
        String field,
        String strField,
        int maxLength,
        boolean hisuFlg) {
        log__.debug(strField + " のチェックを行います。" + field);
        ActionMessage msg = null;

        String eprefix = E_NIPPOU__;
        String fieldfix = checkObject + ".";

        if (StringUtil.isNullZeroString(field)) {
            if (hisuFlg) {
                //未入力チェック
                msg = new ActionMessage("error.input.required.text", checkObject);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField);
            }
            return errors;
        }

        if (ValidateUtil.isSpace(field)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", checkObject);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (ValidateUtil.isSpaceStart(field)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);
            return errors;
        }

        //タブ文字が含まれている
        if (ValidateUtil.isTab(field)) {
            msg = new ActionMessage("error.input.tab.text", checkObject);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (field.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", checkObject,
                    maxLength);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (!GSValidateUtil.isGsJapaneaseStringTextArea(field)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(field);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] テキストフィールド(カナ)の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param maxLength 最大文字数
     * @param hisuFlg 入力が必須か true:必須 false:必須ではない
     * @return ActionErrors
     */
    public static ActionErrors validateCmnFieldTextKana(
        ActionErrors errors,
        String checkObject,
        String field,
        String strField,
        int maxLength,
        boolean hisuFlg) {
        log__.debug(strField + " のチェックを行います。" + field);
        ActionMessage msg = null;

        String eprefix = E_NIPPOU__;
        String fieldfix = checkObject + ".";

        if (StringUtil.isNullZeroString(field)) {
            if (hisuFlg) {
                //未入力チェック
                msg = new ActionMessage("error.input.required.text", checkObject);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField);
            }
            return errors;
        }

        if (ValidateUtil.isSpace(field)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", checkObject);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (ValidateUtil.isSpaceStart(field)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);
            return errors;
        }

        if (field.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", checkObject,
                    maxLength);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (!GSValidateUtil.isGsJapaneaseStringTextArea(field)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(field);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        validateKana(errors, field, strField, strField);

        return errors;
    }

    /**
     * <br>[機  能] 全角カナチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateKana(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (!GSValidateUtil.isGsWideKana(value)) {
            //全角カナチェック
            String msgKey = "error.input.kana.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] テキストフィールドの入力チェックを行う(数値)
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param maxLength 最大文字数
     * @param hisuFlg 入力が必須か true:必須 false:必須ではない
     * @return ActionErrors
     */
    public static ActionErrors validateCmnFieldTextNum(
        ActionErrors errors,
        String checkObject,
        String field,
        String strField,
        int maxLength,
        boolean hisuFlg) {
        log__.debug(strField + " のチェックを行います。" + field);
        ActionMessage msg = null;

        String eprefix = E_NIPPOU__;
        String fieldfix = checkObject + ".";

        if (StringUtil.isNullZeroString(field)) {
            if (hisuFlg) {
                //未入力チェック
                msg = new ActionMessage("error.input.required.text", checkObject);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField);
            }
            return errors;
        }

        if (ValidateUtil.isSpace(field)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", checkObject);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (ValidateUtil.isSpaceStart(field)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);
            return errors;
        }

        //カンマを排除
        field = field.replaceAll(",", "");

        if (ValidateUtil.isNumber(field)) {
            if (field.length() > maxLength) {
                //MAX桁チェック
                msg = new ActionMessage("error.input.length.number2", checkObject,
                        maxLength);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField);
                return errors;
            }
        } else {
            msg = new ActionMessage("error.input.length.number2", checkObject,
                    maxLength);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (!GSValidateUtil.isGsJapaneaseStringTextArea(field)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(field);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] テキストフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param maxLength 最大文字数
     * @param hisuFlg 入力が必須か true:必須 false:必須ではない
     * @return ActionErrors
     */
    public static ActionErrors validateCmnFieldTextNumber(
        ActionErrors errors,
        String checkObject,
        String field,
        String strField,
        int maxLength,
        boolean hisuFlg) {
        log__.debug(strField + " のチェックを行います。" + field);
        ActionMessage msg = null;

        String eprefix = E_NIPPOU__;
        String fieldfix = checkObject + ".";

        if (StringUtil.isNullZeroString(field)) {
            if (hisuFlg) {
                //未入力チェック
                msg = new ActionMessage("error.input.required.text", checkObject);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField);
            }
            return errors;
        }

        if (ValidateUtil.isSpace(field)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", checkObject);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (ValidateUtil.isSpaceStart(field)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);
            return errors;
        }

        if (field.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", checkObject,
                    maxLength);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (!GSValidateUtil.isNumber(field)) {
            //数字チェック
            msg = new ActionMessage("error.input.number.hankaku", checkObject);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }
        return errors;
    }
    /**
     * <br>[機  能] テキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param maxLength 最大文字数
     * @param hisuFlg 入力が必須か true:必須 false:必須ではない
     * @return ActionErrors
     */
    public static ActionErrors validateFieldTextArea(
            ActionErrors errors,
            String checkObject,
            String field,
            String strField,
            int maxLength,
            boolean hisuFlg) {
        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;

        String eprefix = E_NIPPOU__;
        String fieldfix = checkObject + ".";

        if (StringUtil.isNullZeroString(field)) {
            if (hisuFlg) {
                //未入力チェック
                msg = new ActionMessage("error.input.required.text", checkObject);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField);
            }
            return errors;
        }

        if (field.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.textarea", checkObject,
                    maxLength);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            //スペース、改行のみチェック
        } else if (ValidateUtil.isSpaceOrKaigyou(field)) {
            msg = new ActionMessage("error.input.spase.cl.only", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);

        } else if (ValidateUtil.isSpaceStart(field)) {
                //先頭スペースチェック
                msg = new ActionMessage("error.input.spase.start", checkObject);
                StrutsUtil.addMessage(errors, msg,
                        eprefix + fieldfix + strField);

        } else if (!GSValidateUtil.isGsJapaneaseStringTextArea(field)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(field);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
        }

        return errors;
    }

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

    /**
     * <br>[機  能] 未選択チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param params チェック対象
     * @param targetName 対象名
     * @param targetJp チェック対象名(日本語)
     * @return ActionErrors
     */
    public static ActionErrors validateNoSelect(
            ActionErrors errors,
            String[] params,
            String targetName,
            String targetJp) {

        if (params == null || params.length <= 0) {
            ActionMessage msg = new ActionMessage("error.select.required.text",
                    targetJp);
            StrutsUtil.addMessage(errors, msg, targetName + "error.select.required.text");
        }
        return errors;
    }

}
