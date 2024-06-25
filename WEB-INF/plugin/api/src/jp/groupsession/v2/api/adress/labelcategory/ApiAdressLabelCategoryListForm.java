package jp.groupsession.v2.api.adress.labelcategory;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 *
 * <br>[機  能] ラベルカテゴリ一覧取得API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "address-labelcategorylist",
plugin = "address", name = "ラベルカテゴリ一覧取得",
url = "/api/address/labelcategorylist.do",
reqtype = "GET")
public class ApiAdressLabelCategoryListForm extends AbstractApiForm {

}
