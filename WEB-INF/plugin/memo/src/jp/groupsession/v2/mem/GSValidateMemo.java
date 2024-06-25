package jp.groupsession.v2.mem;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.mem.dao.MemoLabelDao;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メモの入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateMemo {

    /**
     * <br>[機  能] ラベルの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param label ラベル
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     */
    public static ActionErrors validateLabel(ActionErrors errors, String label,
            RequestModel reqMdl) {
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String message = gsMsg.getMessage("cmn.label.name");

        //未入力チェック
        if (StringUtil.isNullZeroString(label)) {
            msg = new ActionMessage("error.input.required.text", message);
            StrutsUtil.addMessage(errors, msg, "title");
            return errors;
        }

        //MAX桁チェック
        if (label.length() > GSConstMemo.MAX_LABEL_LENGTH) {
            msg = new ActionMessage("error.input.length.text",
                    message,
                    GSConstMemo.MAX_LABEL_LENGTH);
            StrutsUtil.addMessage(errors, msg, "label");
        }
        //スペースのみチェック
        if (ValidateUtil.isSpace(label)) {
            msg = new ActionMessage("error.input.spase.only", message);
            StrutsUtil.addMessage(errors, msg, "label");
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(label)) {
            msg = new ActionMessage("error.input.spase.start", message);
            StrutsUtil.addMessage(errors, msg, "label");
        }
        //タブ文字が含まれている
        if (ValidateUtil.isTab(label)) {
            msg = new ActionMessage("error.input.tab.text", message);
            StrutsUtil.addMessage(errors, msg, "label");
        }
        //JIS第2水準チェック
        //利用不可能な文字を入力した場合
        if (!GSValidateUtil.isGsJapaneaseString(label)) {
            msg = new ActionMessage("error.input.njapan.text", message, ValidateUtil.getNotUniXAscii(label));
            StrutsUtil.addMessage(errors, msg, "label");
        }
        return errors;
    }

    /**
     * <br>[機  能] ラベルの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param labelSid 対象ラベルSid
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     * @throws SQLException 
     */
    public static ActionErrors existLabel(Connection con,
            ActionErrors errors, int labelSid, RequestModel reqMdl) throws SQLException {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        int usrSid = reqMdl.getSmodel().getUsrsid();

        //ラベルの存在チェック
        MemoLabelDao dao = new MemoLabelDao(con);
        if (labelSid != -1) {
            if (!dao.existLabel(labelSid, usrSid)) {
                msg = new ActionMessage("error.nothing.selected", gsMsg.getMessage("cmn.label"));
                StrutsUtil.addMessage(errors, msg, "label");
            }    
        }
        return errors;
    }
}