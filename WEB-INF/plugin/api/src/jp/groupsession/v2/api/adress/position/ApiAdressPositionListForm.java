package jp.groupsession.v2.api.adress.position;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 *
 * <br>[機  能] API アドレス帳 役職一覧取得
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "address-positionlist",
plugin = "address", name = "役職一覧取得",
url = "/api/address/positionlist.do",
reqtype = "GET")
public class ApiAdressPositionListForm extends AbstractApiForm {

}
