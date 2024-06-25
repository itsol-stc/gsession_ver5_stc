package jp.groupsession.v2.api.reserve.sisetu.group;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 * <br>[機  能] 施設予約グループ取得WEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "reserve-group",
plugin = "reserve", name = "施設グループ取得",
url = "/api/reserve/group.do", reqtype = "GET")
public class ApiReserveGroupForm extends AbstractApiForm {

}
