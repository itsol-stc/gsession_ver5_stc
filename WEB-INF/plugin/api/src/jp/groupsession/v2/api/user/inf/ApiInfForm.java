package jp.groupsession.v2.api.user.inf;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.GSValidateApi;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ユーザ情報を取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "user-inf",
plugin = "user", name = "指定ユーザの個人情報取得",
url = "/api/user/inf.do", reqtype = "GET")
public class ApiInfForm extends AbstractApiForm {

    /** ユーザSID */
    @ApiParam(name = "usrSid", viewName = "ユーザSID")
    private String usrSid__ = null;

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public String getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(String usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg GsMessage
     * @return エラー
     */
    public ActionErrors validateUsrInf(GsMessage gsMsg) {
        ActionErrors errors = new ActionErrors();
        GSValidateApi.validateSid(errors, usrSid__,
                "usrSid",
                gsMsg.getMessage(GSConstApi.USER_SID_STRING),
                true);

         return errors;
    }
}
