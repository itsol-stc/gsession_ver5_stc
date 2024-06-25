package jp.groupsession.v2.api.smail.list;

import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 受信ショートメールリストを取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlListForm extends AbstractApiForm {

    /** モード */
    private String mode__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg GsMessage
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateSmlList(GsMessage gsMsg) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        if (StringUtil.isNullZeroString(mode__)) {
            //未入力
            msg = new ActionMessage("error.input.required.text",
                                    gsMsg.getMessage(GSConstApi.TEXT_MODE));
            StrutsUtil.addMessage(errors, msg, "mode");

        } else if (!ValidateUtil.isNumber(mode__)) {
            //数字チェック
            msg = new ActionMessage(
                    "error.input.number.hankaku", GSConstApi.TEXT_MODE);
            StrutsUtil.addMessage(errors, msg, "mode");

        } else if (NullDefault.getInt(mode__, -1) != 0 && NullDefault.getInt(mode__, -1) != 1) {
            //0と1以外はエラー
            msg = new ActionMessage(
                    "error.input.notvalidate.data", GSConstApi.TEXT_MODE);
            StrutsUtil.addMessage(errors, msg, "mode");

        }

        return errors;
    }

    /**
     * @return mode
     */
    public String getMode() {
        return mode__;
    }

    /**
     * @param mode 設定する mode
     */
    public void setMode(String mode) {
        mode__ = mode;
    }

}
