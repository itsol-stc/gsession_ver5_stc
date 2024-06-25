package jp.groupsession.v2.api.user.label;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 *
 * <br>[機  能] API ユーザラベル一覧取得
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "user-labellist",
plugin = "user", name = "ラベル一覧取得",
url = "/api/user/labellist.do", reqtype = "GET")
public class ApiUserLabelListForm extends AbstractApiForm {

}
