package jp.groupsession.v2.api.smail.mail.save;

import jp.groupsession.v2.api.smail.mail.send.ApiSmlMailSendForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;

/**
 * <br>[機  能] ショートメールを草稿を保存するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考] ※ショートメール送信APIフォームを引継
 *
 * @author JTS
 */
@ApiClass(id = "smail-mail/save",
plugin = "smail", name = "ショートメール草稿保存",
url = "/api/smail/mail/save.do", referenceFlg = false, reqtype = "POST")
public class ApiSmlMailSaveForm extends ApiSmlMailSendForm {

    /** アカウントSID */
    @ApiParam(name = "sacSid", viewName = "登録アカウントSID")
    private int sacSid__    = -1;

}
