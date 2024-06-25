package jp.groupsession.v2.api.enquete.countm;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] /enquate/countmのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "enquete-countm",
plugin = "enquete", name = "アンケート未回答件数取得",
url = "/api/enquete/countm.do",
reqtype = "GET")
public class ApiCountMForm extends AbstractApiForm {
}
