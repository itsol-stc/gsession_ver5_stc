package jp.groupsession.v2.api.smail.account.list;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] 使用可能なショートメールアカウント一覧を取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "smail-account/list",
plugin = "smail", name = "使用可能なショートメールアカウント一覧取得",
url = "/api/smail/account/list.do", reqtype = "GET")
public class ApiSmlAccountListForm extends AbstractApiForm {
}
