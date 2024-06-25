package jp.groupsession.v2.api.adress.typeindustry;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] WEB API アドレス帳 業種一覧取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "address-searchTypeIndustry",
plugin = "address", name = "業種一覧取得",
url = "/api/address/searchTypeIndustry.do",
reqtype = "GET")
public class ApiSearchTypeIndustryForm extends AbstractApiForm {
    /** 会社SID */
    int acoSid__ = -1;

    /**
     * <p>acoSid を取得します。
     * @return acoSid
     */
    public int getAcoSid() {
        return acoSid__;
    }

    /**
     * <p>acoSid をセットします。
     * @param acoSid acoSid
     */
    public void setAcoSid(int acoSid) {
        this.acoSid__ = acoSid;
    }

}
