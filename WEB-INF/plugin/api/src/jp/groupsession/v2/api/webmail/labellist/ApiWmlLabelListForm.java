package jp.groupsession.v2.api.webmail.labellist;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;

/**
 * <br>[機  能] WEBメールラベル一覧を取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-labellist",
plugin = "webmail", name = "ラベル一覧取得",
url = "/api/webmail/labellist.do", reqtype = "GET")
public class ApiWmlLabelListForm extends AbstractApiForm {
    /** アカウントSID*/
    @ApiParam(name = "wacSid", viewName = "アカウントSID")
    private int wacSid__ = -1;

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
