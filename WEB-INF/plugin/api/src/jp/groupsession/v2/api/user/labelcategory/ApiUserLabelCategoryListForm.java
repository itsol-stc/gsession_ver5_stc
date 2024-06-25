package jp.groupsession.v2.api.user.labelcategory;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 *
 * <br>[機  能] API ユーザラベルカテゴリ一覧取得
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "user-labelcategorylist",
plugin = "user", name = "ラベルカテゴリ一覧取得",
url = "/api/user/labelcategorylist.do", reqtype = "GET")
public class ApiUserLabelCategoryListForm extends AbstractApiForm {

}
