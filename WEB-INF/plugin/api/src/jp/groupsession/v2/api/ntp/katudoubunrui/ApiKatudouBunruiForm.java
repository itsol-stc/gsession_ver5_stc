package jp.groupsession.v2.api.ntp.katudoubunrui;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 *
 * <br>[機  能] WEBAPI 日報 活動分類取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-katudouBunrui",
plugin = "nippou", name = "活動分類取得",
url = "/api/ntp/katudouBunrui.do", reqtype = "GET")
public class ApiKatudouBunruiForm extends AbstractApiForm {

}
