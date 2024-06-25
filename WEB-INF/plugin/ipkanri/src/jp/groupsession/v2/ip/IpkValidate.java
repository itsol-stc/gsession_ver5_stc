package jp.groupsession.v2.ip;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.ip.ipk020.SubNetMaskCheck;
import jp.groupsession.v2.ip.ipk050.IpAddressCheck;
import jp.groupsession.v2.ip.model.ValidateCheckModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 入力チェックに関係する機能を実装したクラス
 * <br>[解  説] IP管理についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkValidate {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IpkValidate.class);

    /** epefix IP管理*/
    private static final String IPK_E_IPKANRI__ = "ipkanri";

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

        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;

        String eprefix = IPK_E_IPKANRI__;
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
     * <br>[機  能] 数字のみ入力可能なテキストフィールドの入力チェックを行う
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
    public static ActionErrors validateintAddressFieldText(
        ActionErrors errors,
        String checkObject,
        String field,
        String strField,
        int maxLength,
        boolean hisuFlg) {

        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;
        String eprefix = IPK_E_IPKANRI__;
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

        if (!GSValidateUtil.isNumber(field)) {
            //数字かどうかチェックする
            msg = new ActionMessage("error.input.number.text", checkObject);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
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

        if (NullDefault.getInt(field, 256) > 255 || NullDefault.getInt(field, -1) < 0) {
            //0以上255以下の数字になってるかチェックする
            msg = new ActionMessage("error.input.addhani.text", checkObject, 0, 255);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] 数字のみ入力可能なテキストフィールドの入力チェックを行う
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
    public static ActionErrors validateCmnintFieldText(
        ActionErrors errors,
        String checkObject,
        String field,
        String strField,
        int maxLength,
        boolean hisuFlg) {

        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;
        String eprefix = IPK_E_IPKANRI__;
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

        if (!GSValidateUtil.isNumber(field)) {
            //数字かどうかチェックする
            msg = new ActionMessage("error.input.number.text", checkObject);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
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
        return errors;
    }

    /**
     * <br>[機  能] サブネットマスクテキストフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @return ActionErrors
     */
    public static ActionErrors validateSubnetFieldText(
        ActionErrors errors,
        String checkObject,
        String field,
        String strField) {
        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;
        String eprefix = IPK_E_IPKANRI__;
        String fieldfix = checkObject + ".";

        if (!SubNetMaskCheck.checkSubNetMask(field)) {
            msg = new ActionMessage("error.input.notvalidate.data", checkObject);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] IPアドレスの入力チェックを行う(全体)
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param model チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @return ActionErrors
     */
    public static ActionErrors validateIpadAllFieldText(
        ActionErrors errors,
        String checkObject,
        ValidateCheckModel model,
        String strField) {
        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;
        String eprefix = IPK_E_IPKANRI__;
        String fieldfix = checkObject + ".";
        //範囲チェック
        if (!IpAddressCheck.ipadCheck(
                model.getIpAddress(), model.getNetworkAddress(), model.getSubnetMask())) {
            msg = new ActionMessage("error.input.notvalidate.data", checkObject);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        //存在チェック
        if (!IpAddressCheck.ipadExistCheck(model.getIpadCount())) {
            msg = new ActionMessage("error.input.timecard.exist", checkObject);
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

        String eprefix = IPK_E_IPKANRI__;
        String fieldfix = checkObject + ".";
        if (field == null) {
            return errors;
        }

        if (field.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.textarea", checkObject,
                    maxLength);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);

        } else if (!GSValidateUtil.isGsJapaneaseStringTextArea(field)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(field);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
        }

        if (!field.equals("")) {
            //スペース、改行のみチェック
            if (ValidateUtil.isSpaceOrKaigyou(field)) {
                msg = new ActionMessage("error.input.spase.cl.only", checkObject);
                StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 検索テキストフィールドの入力チェックを行う
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
    public static ActionErrors validateSearchFieldText(
        ActionErrors errors,
        String checkObject,
        String field,
        String strField,
        int maxLength,
        boolean hisuFlg) {

        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;

        String eprefix = IPK_E_IPKANRI__;
        String fieldfix = checkObject + ".";

        if (ValidateUtil.isSpaceStart(field)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);
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
}