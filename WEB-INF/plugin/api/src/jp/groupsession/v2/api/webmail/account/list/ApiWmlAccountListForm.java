package jp.groupsession.v2.api.webmail.account.list;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] WEBメールのアカウント一覧を取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-account/list",
plugin = "webmail", name = "アカウント詳細情報一覧取得",
url = "/api/webmail/account/list.do", reqtype = "GET")
public class ApiWmlAccountListForm extends AbstractApiForm {
}
