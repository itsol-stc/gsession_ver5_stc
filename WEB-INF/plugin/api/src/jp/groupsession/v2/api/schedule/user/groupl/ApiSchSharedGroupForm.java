package jp.groupsession.v2.api.schedule.user.groupl;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 *
 * <br>[機  能] スケジュール共有ユーザグループAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "schedule-r",
plugin = "schedule", name = "グループ一覧取得",
url = "/api/schedule/groupl.do", reqtype = "GET")
public class ApiSchSharedGroupForm extends AbstractApiForm {

}
