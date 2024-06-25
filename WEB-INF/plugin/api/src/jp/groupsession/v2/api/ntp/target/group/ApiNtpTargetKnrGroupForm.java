package jp.groupsession.v2.api.ntp.target.group;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 * 日報目標管理情報取得
 * <br>[機  能] WEBAPI 日報目標管理グループ取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-targetKnrGroup",
plugin = "nippou", name = "目標管理グループ取得",
url = "/api/ntp/targetKnrGroup.do", reqtype = "GET")
public class ApiNtpTargetKnrGroupForm extends AbstractApiForm {

}
