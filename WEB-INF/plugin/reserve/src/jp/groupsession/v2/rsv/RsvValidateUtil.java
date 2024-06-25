package jp.groupsession.v2.rsv;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 入力チェックに関係する機能を実装したクラス
 * <br>[解  説] 施設予約についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvValidateUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvValidateUtil.class);

    /**
     * <br>[機  能] テキストフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTextField(
        ActionErrors errors,
        String value,
        String paramName,
        String paramNameJpn,
        int maxLength,
        boolean necessary) {

        log__.debug(paramNameJpn + " のチェックを行います。");

        int startErrCount = errors.size();

        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //スペースのみチェック
        if (!validateSpaceOnly(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //先頭スペースチェック
        if (!validateInitialSpace(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //最大長チェック
        validateMaxLength(errors, value, paramName, paramNameJpn, maxLength);

        //JIS第二水準チェック
        validateGsJapanese(errors, value, paramName, paramNameJpn);

        return startErrCount == errors.size();
    }

    /**
     * <br>[機  能] 未入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateNecessary(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (StringUtil.isNullZeroString(value)) {
            //未入力チェック
            String msgKey = "error.input.required.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] スペースのみチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateSpaceOnly(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (ValidateUtil.isSpace(value)) {
            String msgKey = "error.input.spase.only";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 先頭スペースチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateInitialSpace(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (ValidateUtil.isSpaceStart(value)) {
            String msgKey = "error.input.spase.start";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 最大長を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大長
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateMaxLength(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn,
                                int maxLength) {

        String fieldfix = paramName + ".";

        if (value.length() > maxLength) {
            String msgKey = "error.input.length.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 利用可能文字(JIS第二水準)チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateGsJapanese(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(value);

            String msgKey = "error.input.njapan.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn, nstr);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 時間単位の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param errors ActionErrors
     * @param gsMsg GsMessage
     * @param frTime 開始時刻
     * @param toTime 終了時刻
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTimeUnit(
            Connection con, ActionErrors errors,
            GsMessage gsMsg, int frTime, int toTime) throws SQLException {

        ActionMessage msg = null;
        boolean errorFlg = false;
        RsvCommonBiz rsvBiz = new RsvCommonBiz();
        int timeUnit = rsvBiz.getHourDivision(con);
        int hourDiv = GSConstReserve.DF_HOUR_DIVISION;
        String unitMsg = gsMsg.getMessage("reserve.rsv220.4");

        if (timeUnit == GSConstReserve.HOUR_DIVISION5) {
            hourDiv = GSConstReserve.HOUR_DIVISION5;
            unitMsg = gsMsg.getMessage("reserve.rsv220.3");
        }
        if (timeUnit == GSConstReserve.HOUR_DIVISION15) {
            hourDiv = GSConstReserve.HOUR_DIVISION15;
            unitMsg = gsMsg.getMessage("reserve.rsv220.5");
        }

        if (frTime % hourDiv != 0) {
            String startTime = gsMsg.getMessage("cmn.starttime");
            msg = new ActionMessage("error.input.comp.text", startTime, unitMsg);
            errors.add("error.input.comp.text", msg);
            errorFlg = true;
        }
        if (toTime % hourDiv != 0) {
            String endTime = gsMsg.getMessage("cmn.endtime");
            msg = new ActionMessage("error.input.comp.text", endTime, unitMsg);
            errors.add("error.input.comp.text", msg);
            errorFlg = true;
        }
        if (errorFlg) {
            return false;
        }
        return true;
    }
}