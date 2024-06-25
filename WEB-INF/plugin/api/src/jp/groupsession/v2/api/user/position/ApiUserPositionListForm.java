package jp.groupsession.v2.api.user.position;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 *
 * <br>[機  能] API ユーザ 役職一覧取得
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "user-positionlist",
plugin = "user", name = "役職一覧取得",
url = "/api/user/positionlist.do", reqtype = "GET")
public class ApiUserPositionListForm extends AbstractApiForm {

}
