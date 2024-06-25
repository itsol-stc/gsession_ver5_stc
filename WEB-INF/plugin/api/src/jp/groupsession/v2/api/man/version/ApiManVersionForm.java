package jp.groupsession.v2.api.man.version;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 *
 * <br>[機  能] GSのバージョン情報を返すWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "main-version",
plugin = "main", name = "GSバージョン",
url = "/api/main/version.do", reqtype = "GET")
public class ApiManVersionForm extends AbstractApiForm {

}
