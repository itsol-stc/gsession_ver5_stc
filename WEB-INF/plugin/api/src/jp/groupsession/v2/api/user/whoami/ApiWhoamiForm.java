package jp.groupsession.v2.api.user.whoami;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] 自分の個人情報を取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "user-whoami",
plugin = "user", name = "自分の個人情報を取得",
url = "/api/user/whoami.do", reqtype = "GET")
public class ApiWhoamiForm extends AbstractApiForm {
}
