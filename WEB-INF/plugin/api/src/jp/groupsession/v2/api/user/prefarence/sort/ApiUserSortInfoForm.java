package jp.groupsession.v2.api.user.prefarence.sort;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
*
* <br>[機  能] WEBAPI ユーザ情報ソート条件の取得
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
@ApiClass(id = "user-sortpref",
plugin = "user", name = "ユーザ情報の表示順条件取得",
url = "/api/user/sortpref.do", reqtype = "GET")
public class ApiUserSortInfoForm extends AbstractApiForm {

}
