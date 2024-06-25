package jp.groupsession.v2.api.adress.companybase.existsini;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 * <br>[機  能] WEB API アドレス帳 企業名の存在するイニシャル取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "address-existsCompanySini",
plugin = "address", name = "有効会社拠点取得（会社名カナ先頭１文字）",
url = "/api/address/existsCompanySini.do", reqtype = "GET")
public class ApiExistsCompanyIniForm extends AbstractApiForm {

}
