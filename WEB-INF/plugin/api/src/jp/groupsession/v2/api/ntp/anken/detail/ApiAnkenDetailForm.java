package jp.groupsession.v2.api.ntp.anken.detail;


import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.GSValidateApi;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.ntp.GSConstNippou;
/**
 * <br>[機  能] 日報 案件詳細取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-ankenDetail",
plugin = "nippou", name = "案件詳細情報取得",
url = "/api/ntp/ankenDetail.do", reqtype = "GET")
public class ApiAnkenDetailForm extends AbstractApiForm {
    /** 案件Sid */
    @ApiParam(name = "nanSid", viewName = "案件SID")
    private String nanSid__;

    /**
     * <p>nanSid を取得します。
     * @return nanSid
     */
    public String getNanSid() {
        return nanSid__;
    }

    /**
     * <p>nanSid をセットします。
     * @param nanSid nanSid
     */
    public void setNanSid(String nanSid) {
        nanSid__ = nanSid;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return errors エラー
     */
    public ActionErrors validateCheck() {
        ActionErrors errors = new ActionErrors();
        GSValidateApi.validateSid(errors, nanSid__, "nanSid", GSConstNippou.TEXT_ANKEN_SID, true);
        return errors;
    }

}
