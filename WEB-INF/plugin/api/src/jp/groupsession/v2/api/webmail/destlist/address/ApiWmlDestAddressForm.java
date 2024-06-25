package jp.groupsession.v2.api.webmail.destlist.address;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;

/**
 * <br>[機  能] WEBメール送信先リストのアドレス一覧を取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-destlist/address",
plugin = "webmail", name = "送信先リスト 送信先アドレス一覧取得",
url = "/api/webmail/destlist/address.do", reqtype = "GET")
public class ApiWmlDestAddressForm extends AbstractApiForm {
    /** 送信先リストSID*/
    @ApiParam(name = "wdlSid", viewName = "送信先リストSID")
    private int wdlSid__ = -1;

    /**
     * <p>wdlSid を取得します。
     * @return wdlSid
     */
    public int getWdlSid() {
        return wdlSid__;
    }
    /**
     * <p>wdlSid をセットします。
     * @param wdlSid 送信先リストSID
     */
    public void setWdlSid(int wdlSid) {
        wdlSid__ = wdlSid;
    }
}
