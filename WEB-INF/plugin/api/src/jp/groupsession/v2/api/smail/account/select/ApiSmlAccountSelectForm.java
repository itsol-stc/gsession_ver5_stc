package jp.groupsession.v2.api.smail.account.select;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] ショートメールアカウント一覧を取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "smail-account/select",
plugin = "smail", name = "ショートメールアカウント一覧取得",
url = "/api/smail/account/select.do", reqtype = "GET")
public class ApiSmlAccountSelectForm extends AbstractApiForm {

    /** グループSID*/
    private String grpSid__ = "-1";

    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public String getGrpSid() {
        return grpSid__;
    }
    /**
     * <p>grpSid をセットします。
     * @param grpSid ユーザグループSID
     */
    public void setGrpSid(String grpSid) {
        grpSid__ = grpSid;
    }
}
