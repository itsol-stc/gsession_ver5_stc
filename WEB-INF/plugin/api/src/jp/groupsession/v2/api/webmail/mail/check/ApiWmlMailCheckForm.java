package jp.groupsession.v2.api.webmail.mail.check;

import jp.groupsession.v2.api.webmail.mail.send.ApiWmlMailSendForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;

/**
 * <br>[機  能] WEBメールを送信前チェックするWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-mail/check",
plugin = "webmail", name = "メール送信チェック",
url = "/api/webmail/mail/check.do", reqtype = "GET")
public class ApiWmlMailCheckForm extends ApiWmlMailSendForm {
    /** 宛先メールアドレス */
    @ApiParam(name = "sendAdrTo", viewName = "宛先メールアドレス")
    private String[] sendAdrTo__  = null;
    /** 件名 */
    @ApiParam(name = "title", viewName = "件名")
    private String title__ = null;
    /** 本文 */
    @ApiParam(name = "body", viewName = "本文")
    private String body__ = null;

}
