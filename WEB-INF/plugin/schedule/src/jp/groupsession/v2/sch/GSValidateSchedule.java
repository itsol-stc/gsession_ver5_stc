package jp.groupsession.v2.sch;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSValidateUser;

/**
 * <br>[機  能] スケジュールの入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateSchedule {

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
     * <br>[機  能] 数字入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateNumber(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn,
                                int maxLength) {

        ActionMessage msg = null;

        String fieldFix = paramName + ".";

        if (StringUtil.isNullZeroString(value)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        if (value.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    paramNameJpn, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        if (!GSValidateUtil.isNumber(value)) {
            //半角数字チェック
            String msgKey = "error.input.number.hankaku";
            msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 数字入力チェックを行う（未入力チェックをしない）
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateNumber2(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn,
                                int maxLength) {

        ActionMessage msg = null;

        String fieldFix = paramName + ".";

        if (StringUtil.isNullZeroString(value)) {
            //未入力チェック
            return false;
        }

        if (value.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    paramNameJpn, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        if (!GSValidateUtil.isNumber(value)) {
            //半角数字チェック
            String msgKey = "error.input.number.hankaku";
            msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return false;
        }

        return true;
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
        ActionMessage msg = null;

        String eprefix = "schedule";
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

        if (ValidateUtil.isTab(field)) {
            //タブスペースチェック
            msg = new ActionMessage("error.input.tab.text", checkObject);
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
        return errors;
    }

    /**
     * <br>[機  能] 指定したユーザ・グループに対する存在・権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrKbn ユーザ区分
     * @param selectUserSid 選択ユーザSID
     * @param sessionUserSid セッションユーザSID
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public static boolean validateSchAccessUser(
            Connection con,
            int usrKbn,
            int selectUserSid,
            int sessionUserSid) throws SQLException {

        SchDao schDao = new SchDao(con);
        boolean errorFlg = false;
        if (usrKbn == GSConstSchedule.USER_KBN_GROUP) {
            //グループSID存在チェック
            boolean grpExist = GSValidateUser.existGroup(selectUserSid, con);
            if (!grpExist) {
                errorFlg = true;
            }
            //グループスケジュール登録権限チェック
            if (!schDao.canRegistGroupSchedule(selectUserSid, sessionUserSid)) {
                errorFlg = true;
            }
        } else {
            //ユーザSID存在チェック
            CmnUsrmDao cuDao = new CmnUsrmDao(con);
            boolean usrExist = cuDao.isExistUser(selectUserSid);
            if (!usrExist) {
                errorFlg = true;
            }
            //ユーザスケジュール登録権限チェック
            if (!schDao.canRegistUserSchedule(selectUserSid, sessionUserSid)) {
                errorFlg = true;
            }
        }
        return errorFlg;
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
     * @return ActionErrors
     */
    public static ActionErrors validateTimeUnit(
            Connection con, ActionErrors errors,
            GsMessage gsMsg, int frTime, int toTime) throws SQLException {

        ActionMessage msg = null;

        SchCommonBiz schCmnBiz = new SchCommonBiz();
        SchAdmConfModel sacMdl = schCmnBiz.getAdmConfModel(con);
        int timeUnit = sacMdl.getSadHourDiv();

        int hourDiv = GSConstSchedule.DF_HOUR_DIVISION;
        String unitMsg = gsMsg.getMessage("schedule.40");

        if (timeUnit == GSConstSchedule.HOUR_DIVISION5) {
            hourDiv = GSConstSchedule.HOUR_DIVISION5;
            unitMsg = gsMsg.getMessage("schedule.39");
        }
        if (timeUnit == GSConstSchedule.HOUR_DIVISION15) {
            hourDiv = GSConstSchedule.HOUR_DIVISION15;
            unitMsg = gsMsg.getMessage("schedule.41");
        }

        if (frTime % hourDiv != 0) {
            String startTime = gsMsg.getMessage("cmn.starttime");
            msg = new ActionMessage("error.input.comp.text", startTime, unitMsg);
            errors.add("error.input.comp.text", msg);
        }
        if (toTime % hourDiv != 0) {
            String endTime = gsMsg.getMessage("cmn.endtime");
            msg = new ActionMessage("error.input.comp.text", endTime, unitMsg);
            errors.add("error.input.comp.text", msg);
        }
        return errors;
    }
}