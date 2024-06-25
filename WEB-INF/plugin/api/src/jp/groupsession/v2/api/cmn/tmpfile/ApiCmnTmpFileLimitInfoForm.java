package jp.groupsession.v2.api.cmn.tmpfile;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 *
 * <br>[機  能] 添付ファイルサイズ制限取得API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "common-tmpfilelimit",
plugin = "common", name = "添付ファイルサイズ制限取得",
url = "/api/cmn/tmpfilelimit.do",
reqtype = "GET")
public class ApiCmnTmpFileLimitInfoForm extends AbstractApiForm {

}
