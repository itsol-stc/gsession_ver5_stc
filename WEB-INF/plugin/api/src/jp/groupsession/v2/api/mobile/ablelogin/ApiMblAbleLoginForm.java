package jp.groupsession.v2.api.mobile.ablelogin;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] GSモバイルにログインできるかを判定するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "mobile-ablelogin",
plugin = "mobile", name = "GSモバイルのログイン可能判定",
url = "/api/mobile/ablelogin.do", reqtype = "GET")
public class ApiMblAbleLoginForm extends AbstractApiForm {

}
