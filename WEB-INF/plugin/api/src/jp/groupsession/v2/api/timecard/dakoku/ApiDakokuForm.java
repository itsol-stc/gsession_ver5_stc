package jp.groupsession.v2.api.timecard.dakoku;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] タイムカードの始業終業時間の打刻を行うWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "timecard-dakoku",
plugin = "timecard", name = "タイムカード打刻",
url = "/api/timecard/dakoku.do", reqtype = "POST")
public class ApiDakokuForm extends AbstractApiForm {
}
