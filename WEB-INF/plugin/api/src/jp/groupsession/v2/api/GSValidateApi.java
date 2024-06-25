package jp.groupsession.v2.api;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
/**
 *
 * <br>[機  能] WebApi汎用バリデート処理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateApi {
    /**
     *
     * <br>[機  能] SID入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param param 判定値
     * @param paramName パラメータ名
     * @param dispName 表示名
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateSid(ActionErrors errors,
            String param,
            String paramName,
            String dispName,
            boolean checkNoInput) {
        return validateSid(errors, param, paramName, dispName, checkNoInput, false);
    }
    /**
    *
    * <br>[機  能] SID入力チェック
    * <br>[解  説]
    * <br>[備  考]
    * @param errors ActionErrors
    * @param param 判定値
    * @param paramName パラメータ名
    * @param dispName 表示名
    * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
    * @param ableMainus マイナス値許可 true:許可する false:許可しない
    * @return チェック結果 true :エラー有り false :エラー無し
    */
   public static boolean validateSid(ActionErrors errors,
           String param,
           String paramName,
           String dispName,
           boolean checkNoInput,
           boolean ableMainus) {
        ActionMessage msg = null;
        if (StringUtil.isNullZeroString(param)) {
            if (!checkNoInput) {
                return false;
            }
            msg = new ActionMessage("error.input.required.text", dispName);
            StrutsUtil.addMessage(errors, msg, paramName);
            return true;
        }
        if (!ableMainus) {
            if (!GSValidateUtil.isNumber(param)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", dispName);
                StrutsUtil.addMessage(errors, msg, paramName);
                return true;
            }
        }
        if (!GSValidateUtil.isNumberHaifun(param)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", dispName);
            StrutsUtil.addMessage(errors, msg, paramName);
            return true;
        }

        return false;
    }
}
