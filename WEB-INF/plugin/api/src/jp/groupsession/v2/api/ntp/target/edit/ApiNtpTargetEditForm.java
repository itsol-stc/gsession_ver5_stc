package jp.groupsession.v2.api.ntp.target.edit;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.ntp010.Ntp010Biz;
import net.sf.json.JSONObject;
/**
 * <br>[機  能] WEBAPI 日報目標登録フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-editTarget",
plugin = "nippou", name = "目標登録",
url = "/api/ntp/editTarget.do", reqtype = "POST")
public class ApiNtpTargetEditForm extends AbstractApiForm {
    /** 目標JSON配列*/
    private String[] target__;

    /**
     * <p>target を取得します。
     * @return target
     */
    public String[] getTarget() {
        return target__;
    }

    /**
     * <p>target をセットします。
     * @param target target
     */
    public void setTarget(String[] target) {
        target__ = target;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, RequestModel reqMdl) throws SQLException {
        ActionErrors errors = new ActionErrors();
        Ntp010Biz ntp010biz = new Ntp010Biz(con, reqMdl);

        if (target__ != null) {
            for (int i = 0; i < target__.length; i++) {
                String json = target__[i];
                JSONObject receipt = JSONObject.fromObject(json);
                //目標SID   ntgSid
                GSValidateNippou.validateCmnFieldTextNumber(errors,
                        "ntgSid", receipt.optString("ntgSid"), "ntgSid", 15, true);
                //目標SID   usrSid
                GSValidateNippou.validateCmnFieldTextNumber(errors,
                        "usrSid", receipt.optString("usrSid"), "usrSid", 15, true);
                //目標年度    year
                GSValidateNippou.validateCmnFieldTextNumber(errors,
                        "year", receipt.optString("year"), "year", 4, true);
                //目標月 month
                GSValidateNippou.validateCmnFieldTextNumber(errors,
                        "month", receipt.optString("month"), "month", 2, true);
                //目標値 value
                GSValidateNippou.validateCmnFieldTextNumber(errors,
                        "目標", receipt.optString("value"), "value",
                        GSConstNippou.MAX_LENGTH_TARGET_DEF, true);
                //目標実績値   record
                GSValidateNippou.validateCmnFieldTextNumber(errors,
                        "実績", receipt.optString("record"), "record",
                        GSConstNippou.MAX_LENGTH_RECORD, true);
                if (errors.isEmpty()) {
                    //編集権確認
                    if (ntp010biz.isAddEditOk(receipt.optInt("usrSid"),
                            con) != GSConstNippou.AUTH_ADD_EDIT) {
                        ActionMessage msg = new ActionMessage(
                                "error.edit.power.user", "", "編集");
                        StrutsUtil.addMessage(errors, msg, "admFlg");
                    }

                }
            }
        }
        return errors;
    }
}
