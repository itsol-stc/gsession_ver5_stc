package jp.groupsession.v2.api.ntp.sharedgroup;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 * <br>[機  能] WEBAPI 日報用グループ一覧取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-usr/groupl",
plugin = "nippou", name = "日報用グループ一覧取得",
url = "/api/ntp/usr/groupl.do", reqtype = "GET")
public class ApiNtpGroupLForm extends AbstractApiForm {

}
