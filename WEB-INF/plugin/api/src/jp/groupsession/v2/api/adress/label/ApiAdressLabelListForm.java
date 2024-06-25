package jp.groupsession.v2.api.adress.label;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 *
 * <br>[機  能] ラベル一覧取得API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "address-labellist",
plugin = "address", name = "ラベル一覧取得",
url = "/api/address/labellist.do",
reqtype = "GET")
public class ApiAdressLabelListForm extends AbstractApiForm {
}
