package jp.groupsession.v2.api.ntp.shareduser;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 * <br>[機  能] WEBAPI 日報用ユーザ一覧取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-usr/belong",
plugin = "nippou", name = "日報用ユーザ一覧取得",
url = "/api/ntp/usr/belong.do", reqtype = "GET")
public class ApiNtpBelongForm extends AbstractApiForm {
    /** グループSID */
    private String grpSid__ = null;

    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public String getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(String grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateUsrBelong(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        if (StringUtil.isNullZeroString(grpSid__)) {
            //未入力
            msg = new ActionMessage("error.input.required.text",
                                    getInterMessage(req, GSConstApi.GROUP_SID_STRING));
            StrutsUtil.addMessage(errors, msg, "grpSid");
        }

         return errors;
    }
}
