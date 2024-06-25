package jp.groupsession.v2.api.cmn.login;

import jp.groupsession.v2.api.AbstractApiForm;
/**
 *
 * <br>[機  能] 一次認証API フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
import jp.groupsession.v2.cmn.annotation.ApiClass;

@ApiClass(id = "common-login",
plugin = "common", name = "一時認証を実行",
url = "/api/cmn/login.do",
reqtype = "POST")
public class ApiCmnLoginForm extends AbstractApiForm {

}
