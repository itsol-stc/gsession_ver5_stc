package jp.groupsession.v2.api.ntp.contact;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 * <br>[機  能] WEBAPI 日報 コンタクト取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-contact",
plugin = "nippou", name = "コンタクト一覧取得",
url = "/api/ntp/contact.do", reqtype = "GET")
public class ApiNtpContactForm extends AbstractApiForm {

}
