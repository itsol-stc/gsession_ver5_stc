package jp.groupsession.v2.api.cmn.masta.tdfk;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 * <br>[機  能] WEB API 都道府県マスタ取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "common-getMstTdfk",
plugin = "common", name = "都道府県マスタ取得",
url = "/api/cmn/getMstTdfk.do",
reqtype = "GET")
public class ApiCmnGetMstTdfkForm extends AbstractApiForm {

}
