package jp.groupsession.v2.api.webmail.destlist.list;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] WEBメール送信先リスト一覧を取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-destlist/list",
plugin = "webmail", name = "送信先リスト一覧取得",
url = "/api/webmail/destlist/list.do", reqtype = "GET")
public class ApiWmlDestListForm extends AbstractApiForm {
}
