package jp.groupsession.v2.api.ntp.gyoumu;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 * <br>[機  能] WEBAPI日報 業務内容取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-gyoumu",
plugin = "nippou", name = "業務一覧取得",
url = "/api/ntp/gyoumu.do", reqtype = "GET")
public class ApiNtpGyoumuForm extends AbstractApiForm {

}
