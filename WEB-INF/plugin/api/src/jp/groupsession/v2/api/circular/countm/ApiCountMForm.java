package jp.groupsession.v2.api.circular.countm;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] /circular/countmのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "circular-countm",
plugin = "circular", name = "回覧板未確認件数取得",
url = "/api/circular/countm.do",
reqtype = "GET")
public class ApiCountMForm extends AbstractApiForm {
}
