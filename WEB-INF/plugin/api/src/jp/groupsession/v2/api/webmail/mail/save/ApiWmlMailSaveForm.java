package jp.groupsession.v2.api.webmail.mail.save;

import jp.groupsession.v2.api.webmail.mail.send.ApiWmlMailSendForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;


/**
 * <br>[機  能] WEBメールを草稿へ保存するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-mail/save",
plugin = "webmail", name = "メール草稿保存",
url = "/api/webmail/mail/save.do", reqtype = "POST")
public class ApiWmlMailSaveForm extends ApiWmlMailSendForm {
}
