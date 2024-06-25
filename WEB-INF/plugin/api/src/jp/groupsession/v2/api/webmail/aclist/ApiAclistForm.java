package jp.groupsession.v2.api.webmail.aclist;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] WEBメールのアカウント一覧を取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-aclist",
plugin = "webmail", name = "アカウント一覧取得",
url = "/api/webmail/aclist.do", reqtype = "GET")
public class ApiAclistForm extends AbstractApiForm {
}
