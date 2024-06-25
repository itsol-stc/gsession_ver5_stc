package jp.groupsession.v2.api.webmail.account.defaultinput;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] WEBメールのデフォルトアカウントを取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-account/defaultinput",
plugin = "webmail", name = "初期アカウント設定情報取得",
url = "/api/webmail/account/defaultinput.do",
reqtype = "GET")
public class ApiWmlAccountDefInputForm extends AbstractApiForm {

    /** アカウントSID*/
    private int wacSid__  = -1;

    /**
     * <p>wacSid を取得します。
     * @return wacSid
     */
    public int getWacSid() {
        return wacSid__;
    }

    /**
     * <p>wacSid をセットします。
     * @param wacSid アカウントSID
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }

}
