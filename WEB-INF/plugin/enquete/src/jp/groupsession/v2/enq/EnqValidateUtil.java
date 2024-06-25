package jp.groupsession.v2.enq;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アンケートの入力チェック実装クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqValidateUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqValidateUtil.class);

    /**
     * <br>[機  能] アンケート種類名の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param typeNames 重複チェック用の種類名リスト
     * @return ActionErrors
     */
    public static ActionErrors validateTypeName(ActionErrors errors,
                                                String value,
                                                String paramName,
                                                String paramNameJpn,
                                                int maxLength,
                                                ArrayList<String> typeNames) {

        log__.debug("Validate:アンケート種類名");
        ActionMessage msg = null;

        // 未入力チェック
        if (StringUtil.isNullZeroString(value)) {
            msg = new ActionMessage("error.input.required.text", paramNameJpn);
            StrutsUtil.addMessage(errors, msg, paramNameJpn);
            return errors;
        }

        // MAX桁チェック
        if (value.length() > maxLength) {
            msg = new ActionMessage("error.input.length.text", paramNameJpn, maxLength);
            StrutsUtil.addMessage(errors, msg, paramNameJpn);
            return errors;
        }

        // スペースのみのチェック
        if (ValidateUtil.isSpace(value)) {
            msg = new ActionMessage("error.input.spase.only", paramNameJpn);
            StrutsUtil.addMessage(errors, msg, paramNameJpn);
            return errors;
        }

        // 先頭スペースチェック
        if (ValidateUtil.isSpaceStart(value)) {
            msg = new ActionMessage("error.input.spase.start", paramNameJpn);
            StrutsUtil.addMessage(errors, msg, paramNameJpn);
            return errors;
        }

        //タブ文字が含まれている
        if (ValidateUtil.isTab(value)) {
            String msgKey = "error.input.tab.text";
            msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(errors, msg, paramNameJpn);
            return errors;
        }

        // 重複チェック
        if (typeNames.indexOf(value) > -1) {
            msg = new ActionMessage("error.select.dup.list", paramNameJpn);
            StrutsUtil.addMessage(errors, msg, paramNameJpn);
            return errors;
        }

        return errors;
    }

    /**
    *
    * <br>[機  能] 登録件数のチェック
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param errors ActionErrors
    * @param count 登録件数
    * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
    * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
    * @return ActionErrors
    */
    public static ActionErrors validateTourokuCount(ActionErrors errors,
                                                    int count,
                                                    String paramName,
                                                    String paramNameJpn) {

        log__.debug("登録件数チェック処理");
        ActionMessage msg = null;

        // 登録件数が0
        if (count < 1) {
            msg = new ActionMessage("error.input.required.text", paramNameJpn);
            StrutsUtil.addMessage(errors, msg, paramName);
            return errors;
        }

        return errors;
    }

    /**
     * <br>[機  能] テキストボックスの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextBoxInput(
            ActionErrors errors,
            String target,
            String targetName,
            String targetJp,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        if (ValidateUtil.isSpace(target)) {
            //スペースのみ
            String msgKey = "error.input.spase.only";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return false;
        }

        if (ValidateUtil.isSpaceStart(target)) {
            //先頭スペース
            String msgKey = "error.input.spase.start";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return false;
        }

        if (ValidateUtil.isTab(target)) {
            //タブ文字が含まれている
            String msgKey = "error.input.tab.text";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return false;
        }

        if (maxLength > 0 && target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    targetJp, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(target);
            msg = new ActionMessage("error.input.njapan.text",
                    targetJp, nstr);
            StrutsUtil.addMessage(errors, msg,
                                     fieldFix + "error.input.njapan.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] テキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextarea(
            ActionErrors errors,
            String target,
            String targetName,
            String targetJp,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        //スペース・改行のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(target)) {
            msg = new ActionMessage("error.input.spase.cl.only", targetJp);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.cl.only");
            return true;
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(target);
            msg = new ActionMessage("error.input.njapan.text", targetJp, nstr);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.njapan.text");
            return true;
        }

        if (maxLength > 0 && target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetJp, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] 日付の論理チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors ActionErrors
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param year 年
     * @param month 月
     * @param day 日
     * @param neccesary 入力必須項目か
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateDate(
            RequestModel reqMdl,
            ActionErrors errors,
            String targetName,
            String targetJp,
            int year,
            int month,
            int day,
            boolean neccesary) {

        ActionMessage msg = null;
        String eprefix = "date";
        String fieldFix = targetName + ".";

        GsMessage gsMsg = new GsMessage(reqMdl);
        String inputedDate = gsMsg.getMessage("cmn.year", new String[] {String.valueOf(year)})
                            + month + gsMsg.getMessage("cmn.month")
                            + day + gsMsg.getMessage("cmn.day");

        if (year == GSConstWebmail.SELECT_DATECOMBO
                && month == GSConstWebmail.SELECT_DATECOMBO
                && day == GSConstWebmail.SELECT_DATECOMBO) {

            if (neccesary) {
                msg = new ActionMessage("error.select.required.text", targetJp);
                StrutsUtil.addMessage(errors, msg, eprefix + fieldFix
                        + "error.select.required.text");
            }
            return false;
        }

        if (year == GSConstWebmail.SELECT_DATECOMBO
                || month == GSConstWebmail.SELECT_DATECOMBO
                || day == GSConstWebmail.SELECT_DATECOMBO) {
            msg = new ActionMessage("error.select.required.text", targetJp);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldFix
                    + "error.select.required.text");
            return true;
        }

        int iBYear = year;
        int iBMonth = month;
        int iBDay = day;

        UDate date = new UDate();
        date.setDate(iBYear, iBMonth, iBDay);

        if (date.getYear() != iBYear
        || date.getMonth() != iBMonth
        || date.getIntDay() != iBDay) {
            msg = new ActionMessage("error.input.notfound.date",
                    targetJp + "（" + inputedDate + ")");
            StrutsUtil.addMessage(errors, msg, eprefix + fieldFix
                    + "error.input.notfound.date");
            return true;
        }
        //エラー無し
        return false;
    }

    /**
     * <br>[機  能] ラジオボタンの選択チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateRadioBtn(
            ActionErrors errors,
            String target,
            String targetName,
            String targetJp,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (!checkNoInput) {
            return false;
        }

        if (StringUtil.isNullZeroString(target)) {

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] チェックボックスの選択チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateCheckBox(ActionErrors errors,
                                           String[] target,
                                           String targetName,
                                           String targetJp,
                                           boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (!checkNoInput) {
            return false;
        }

        if (target == null || target.length < 1) {

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] テキストボックス（数値）の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] 入力範囲のチェックを行わない時は、空文字かnullで指定すること。
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param intLength 整数部の最大桁数
     * @param decLength 少数部の最大桁数
     * @param maxLength 最大桁数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateIntTextBox(ActionErrors errors,
                                             String target,
                                             String targetName,
                                             String targetJp,
                                             int intLength,
                                             int decLength,
                                             int maxLength,
                                             boolean checkNoInput) {
        ActionMessage msg = null;
        String fieldFix = targetName + ".";

        //未入力チェック
        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        //スペースのみ
        if (ValidateUtil.isSpace(target)) {
            String msgKey = "error.input.spase.only";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return true;
        }

        //先頭スペース
        if (ValidateUtil.isSpaceStart(target)) {
            String msgKey = "error.input.spase.start";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return true;
        }

        //タブ文字が含まれている
        if (ValidateUtil.isTab(target)) {
            String msgKey = "error.input.tab.text";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return true;
        }

        // 数値型かチェック
        try {
            Double.parseDouble(target);
        } catch (NumberFormatException ne) {
            String msgKey = "error.input.number.text";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(errors, msg, fieldFix + msgKey);
            return true;
        }

        // MAX桁チェック
        if (maxLength > 0 && target.length() > maxLength) {
            msg = new ActionMessage("error.input.length.text",
                    targetJp, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }
        
        String targetInt = null;
        String tadgetDce = null;
        if (target.indexOf(".") > -1) {
            targetInt = target.substring(0, target.indexOf("."));
            tadgetDce = target.substring(target.indexOf(".") + 1);
        } else {
            targetInt = target;
            
        }
        //整数部桁数チェック
        if (targetInt == null || targetInt.length() > intLength) {
            msg = new ActionMessage("error.input.format.length.number",
                    targetJp, "10", "5");
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.format.length.number");
            return true;
        }

        //小数部桁数チェック
        if (tadgetDce != null && tadgetDce.length() > decLength) {
            msg = new ActionMessage("error.input.format.length.number",
                    targetJp, "10", "5");
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.format.length.number");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] テキストボックス（数値）の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]両方のテキストボックスが未入力の場合エラーを返す
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param target1 入力範囲1
     * @param target2 入力範囲2
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateIntTextBox2(ActionErrors errors,
            String target,
            String targetName,
            String target1,
            String target2) {
        ActionMessage msg = null;
        //両方のテキストボックスが未入力の時エラーを返す
        if ((target1 == null || target1.equals(""))
                && (target2 == null || target2.equals(""))) {

            msg = new ActionMessage("error.input.comp.text2",
                    targetName,
                    "入力範囲 開始、または入力範囲 終了");
            StrutsUtil.addMessage(errors, msg, targetName);
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 日付の範囲チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param targetMin 入力範囲の最小値
     * @param targetMax 入力範囲の最大値
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateDateRenge(ActionErrors errors,
                                            String target,
                                            String targetName,
                                            String targetJp,
                                            String targetMin,
                                            String targetMax,
                                            boolean checkNoInput) {

        ActionMessage msg = null;
        String fieldFix = targetName + ".";
        String wkStr = "";
        String wkEnd = "";

        int targetVal = NullDefault.getInt(target, -1);
        if (targetMin.length() >= 11) {
            wkStr = targetMin.substring(0, 4)
                  + targetMin.substring(5, 7)
                  + targetMin.substring(8, 10);
        }
        if (targetMax.length() >= 11) {
            wkEnd = targetMax.substring(0, 4)
                  + targetMax.substring(5, 7)
                  + targetMax.substring(8, 10);
        }

        int minVal = NullDefault.getInt(wkStr, Integer.MIN_VALUE);
        int maxVal = NullDefault.getInt(wkEnd, Integer.MAX_VALUE);

        if (!checkNoInput && targetVal < 0) {
            return false;
        }

        // 最小値チェック
        if (targetVal < minVal) {
            String msgKey = "error.input.date.over";
            msg = new ActionMessage(msgKey, targetJp, targetMin);
            StrutsUtil.addMessage(errors, msg, fieldFix + msgKey);
            return true;
        }

        // 最大値チェック
        if (targetVal > maxVal) {
            String msgKey = "error.input.date.under";
            msg = new ActionMessage(msgKey, targetJp, targetMax);
            StrutsUtil.addMessage(errors, msg, fieldFix + msgKey);
            return true;
        }

        //エラー無し
        return false;
    }

    /**
     * <br>[機  能] 日付の論理チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors ActionErrors
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param fromYear 年 From
     * @param fromMonth 月 From
     * @param fromDay 日 From
     * @param toYear 年 To
     * @param toMonth 月 To
     * @param toDay 日 To
     */
    public static void validateDate(
            RequestModel reqMdl,
            ActionErrors errors,
            String targetName,
            String targetJp,
            int fromYear,
            int fromMonth,
            int fromDay,
            int toYear,
            int toMonth,
            int toDay) {
        validateDate(reqMdl, errors, targetName,
                targetJp, fromYear, fromMonth, fromDay, toYear, toMonth, toDay, -1);
        }


    /**
     * <br>[機  能] 日付の論理チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors ActionErrors
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param fromYear 年 From
     * @param fromMonth 月 From
     * @param fromDay 日 From
     * @param toYear 年 To
     * @param toMonth 月 To
     * @param toDay 日 To
     * @param checkFlg 公開開始日、結果公開期限、回答期限の整合性チェックフラグ
     */
    public static void validateDate(
            RequestModel reqMdl,
            ActionErrors errors,
            String targetName,
            String targetJp,
            int fromYear,
            int fromMonth,
            int fromDay,
            int toYear,
            int toMonth,
            int toDay,
            int checkFlg) {

        int errCnt = errors.size();
        GsMessage gsMsg = new GsMessage();
        if (checkFlg != 0 && checkFlg != 1 && checkFlg != 2) {
            //日付 From
            EnqValidateUtil.validateDate(reqMdl, errors,
                    targetName + "From",
                    targetJp + " " + gsMsg.getMessage("cmn.start"),
                    fromYear,
                    fromMonth,
                    fromDay,
                    false);

            //日付 To
            EnqValidateUtil.validateDate(reqMdl, errors,
                    targetName + "To",
                    targetJp + " " + gsMsg.getMessage("cmn.end"),
                    toYear,
                    toMonth,
                    toDay,
                    false);
        }

        boolean chkRng = false;
        if (fromYear != -1 && fromMonth != -1 && fromDay != -1
         && toYear != -1 && toMonth != -1 && toDay != -1) {
            chkRng = true;
        }

        //日付の大小チェック
        if (errors.size() == errCnt && chkRng) {
            UDate fromDate = new UDate();
            fromDate.setDate(fromYear, fromMonth, fromDay);
            UDate toDate = new UDate();
            toDate.setDate(toYear, toMonth, toDay);
            if (fromDate.compareDateYMD(toDate) == UDate.SMALL) {

                //公開開始日 結果公開期限エラー
                if (checkFlg == 0) {
                    ActionMessage msg = new ActionMessage("error.input.comp.text",
                            targetJp,
                            gsMsg.getMessage("enq.54"));
                    StrutsUtil.addMessage(errors, msg, targetName);

                } else if (checkFlg == 1) {
                    //公開開始日 回答期限エラー
                    ActionMessage msg = new ActionMessage("error.input.comp.text",
                            targetJp,
                            gsMsg.getMessage("enq.55"));
                    StrutsUtil.addMessage(errors, msg, targetName);

                } else if (checkFlg == 2) {
                    //公開開始日 回答期限エラー
                    ActionMessage msg = new ActionMessage("error.input.comp.text",
                            targetJp,
                            gsMsg.getMessage("enq.56"));
                    StrutsUtil.addMessage(errors, msg, targetName);

                } else if (checkFlg == 3) {
                    //初期値
                    ActionMessage msg = new ActionMessage("error.input.comp.text",
                            targetJp,
                            gsMsg.getMessage("cmn.into.input.range"));
                    StrutsUtil.addMessage(errors, msg, targetName);

                } else if (checkFlg == 4) {
                    //結果公開 開始日エラー
                    ActionMessage msg = new ActionMessage("error.input.comp.text",
                            targetJp,
                            gsMsg.getMessage("enq.76"));
                    StrutsUtil.addMessage(errors, msg, targetName);

                } else {
                    ActionMessage msg = new ActionMessage("error.input.comp.text",
                            targetJp,
                            gsMsg.getMessage("cmn.start.or.end2"));
                    StrutsUtil.addMessage(errors, msg, targetName);
                }
            }
        }
    }


    /**
     * <br>[機  能] 日付の論理チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors ActionErrors
     * @param targetName チェック対象名
     * @param targetJpFrom チェック対象名(日本語)
     * @param targetJpTo チェック対象名(日本語)
     * @param fromYear 年 From
     * @param fromMonth 月 From
     * @param fromDay 日 From
     * @param toYear 年 To
     * @param toMonth 月 To
     * @param toDay 日 To
     * @param kbn エラーチェック区分
     */
    public static void validateDateRange(
            RequestModel reqMdl,
            ActionErrors errors,
            String targetName,
            String targetJpFrom,
            String targetJpTo,
            int fromYear,
            int fromMonth,
            int fromDay,
            int toYear,
            int toMonth,
            int toDay,
            int kbn) {

        int errCnt = errors.size();
        GsMessage gsMsg = new GsMessage();
        String msg1 = "";
        String msg2 = "";

        //日付 From
        EnqValidateUtil.validateDate(reqMdl, errors,
                targetJpFrom,
                targetJpFrom,
                fromYear,
                fromMonth,
                fromDay,
                false);

        //日付 To
        EnqValidateUtil.validateDate(reqMdl, errors,
                targetJpTo,
                targetJpTo,
                toYear,
                toMonth,
                toDay,
                false);

        boolean chkRng = false;
        if (fromYear != -1 && fromMonth != -1 && fromDay != -1
         && toYear != -1 && toMonth != -1 && toDay != -1) {
            chkRng = true;
        }

        //日付の大小チェック
        if (kbn == 1) {
            msg1 = gsMsg.getMessage("ntp.10");
            msg2 = gsMsg.getMessage("cmn.input.range");
        } else {
            msg1 = gsMsg.getMessage("cmn.input.range");
            msg2 = gsMsg.getMessage("cmn.start.or.end2");
        }
        if (errors.size() == errCnt && chkRng) {
            UDate fromDate = new UDate();
            fromDate.setDate(fromYear, fromMonth, fromDay);
            UDate toDate = new UDate();
            toDate.setDate(toYear, toMonth, toDay);
            if (fromDate.compareDateYMD(toDate) == UDate.SMALL) {
                ActionMessage msg = new ActionMessage("error.input.comp.text", msg1, msg2);
                StrutsUtil.addMessage(errors, msg, msg1 + msg2);
            }
        }
    }

    /**
     * <br>[機  能] 数値の論理チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors ActionErrors
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param targetFrom 開始値
     * @param targetTo 終了値
     */
    public static void validateNumRange(
            RequestModel reqMdl,
            ActionErrors errors,
            String targetName,
            String targetJp,
            String targetFrom,
            String targetTo) {
        validateNumRange(reqMdl, errors, targetName, targetJp, targetFrom, targetTo, 0);
    }


    /**
     * <br>[機  能] 数値の論理チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors ActionErrors
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param targetFrom 開始値
     * @param targetTo 終了値
     * @param errorFlg エラーメッセージフラグ
     */
    public static void validateNumRange(
            RequestModel reqMdl,
            ActionErrors errors,
            String targetName,
            String targetJp,
            String targetFrom,
            String targetTo,
            int errorFlg) {

        GsMessage gsMsg = new GsMessage(reqMdl);

        int frVal = NullDefault.getInt(targetFrom, Integer.MIN_VALUE);
        int toVal = NullDefault.getInt(targetTo, Integer.MAX_VALUE);

        // 数値の大小チェック
        if (frVal > toVal) {
            ActionMessage msg = null;
            if (errorFlg == 0) {
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage("enq.41"),
                        gsMsg.getMessage("cmn.start.or.end2"));
            } else if (errorFlg == 1) {
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage("ntp.10"),
                        gsMsg.getMessage("cmn.into.input.range"));
            }

            StrutsUtil.addMessage(errors, msg, targetName);
        }
    }

    /**
     * <br>[機  能] 数値の論理チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param errors ActionErrors
     * @param targetVal 基準値
     * @param targetFrom 開始値
     * @param targetTo 終了値
     * @param flg 初期値の有無
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateIntRange(
            RequestModel reqMdl,
            ActionErrors errors,
            String targetVal,
            String targetFrom,
            String targetTo,
            boolean flg) {

        GsMessage gsMsg = new GsMessage(reqMdl);

        int val = NullDefault.getInt(targetVal, 0);
        int frVal = NullDefault.getInt(targetFrom, Integer.MIN_VALUE);
        int toVal = NullDefault.getInt(targetTo, Integer.MAX_VALUE);

        // 範囲のフォーマットチェック
        if (frVal > toVal) {
            ActionMessage msg = null;
            msg = new ActionMessage("error.input.comp.text", gsMsg.getMessage("cmn.input.range"),
                    gsMsg.getMessage("cmn.start.or.end2"));
            StrutsUtil.addMessage(errors, msg, "range");
            return true;
        }

        if (val < frVal || toVal < val) {
            ActionMessage msg = null;
            msg = new ActionMessage("error.input.comp.text",
                    gsMsg.getMessage("ntp.10"), gsMsg.getMessage("cmn.into.input.range"));
            StrutsUtil.addMessage(errors, msg, "init");
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 数値の論理チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param errors ActionErrors
     * @param targetVal 基準値
     * @param targetFrom 開始値
     * @param targetTo 終了値
     * @param flg 初期値の有無
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateDoubleRange(
            RequestModel reqMdl,
            ActionErrors errors,
            String targetVal,
            String targetFrom,
            String targetTo,
            boolean flg) {

        GsMessage gsMsg = new GsMessage(reqMdl);

        double val = NullDefault.getDouble(targetVal, 0);
        double frVal = NullDefault.getDouble(targetFrom, Long.MIN_VALUE);
        double toVal = NullDefault.getDouble(targetTo, Long.MAX_VALUE);

        // 範囲のフォーマットチェック
        if (frVal > toVal) {
            ActionMessage msg = null;
            msg = new ActionMessage("error.input.comp.text", gsMsg.getMessage("cmn.input.range"),
                    gsMsg.getMessage("cmn.start.or.end2"));
            StrutsUtil.addMessage(errors, msg, "range");
            return true;
        }

        if (val < frVal || toVal < val) {
            ActionMessage msg = null;
            msg = new ActionMessage("error.input.comp.text",
                    gsMsg.getMessage("ntp.10"), gsMsg.getMessage("cmn.into.input.range"));
            StrutsUtil.addMessage(errors, msg, "init");
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 日付の範囲チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetJp チェック対象名(日本語)
     * @param enqDefFlg 初期表示フラグ数
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateEnqDefFlg(ActionErrors errors,
                                            String target,
                                            String targetJp,
                                            int enqDefFlg) {

        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;

        if (enqDefFlg > 1) {
            msg = new ActionMessage("error.input.comp.text",
                    target,
                    "1つ" + gsMsg.getMessage("ntp.67"));
            StrutsUtil.addMessage(errors, msg, target);
            return true;
        }

        //エラー無し
        return false;
    }
}
