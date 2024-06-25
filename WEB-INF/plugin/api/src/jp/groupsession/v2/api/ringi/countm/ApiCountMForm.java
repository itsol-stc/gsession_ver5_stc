package jp.groupsession.v2.api.ringi.countm;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] 稟議受信件数を取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "ringi-countm",
plugin = "ringi", name = "稟議受信件数取得",
url = "/api/ringi/countm.do", reqtype = "GET")
public class ApiCountMForm extends AbstractApiForm {
}
